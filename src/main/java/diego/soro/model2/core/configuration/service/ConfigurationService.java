package diego.soro.model2.core.configuration.service;

import diego.soro.model2.core.Company.Company;
import diego.soro.model2.core.Company.repository.CompanyRepository;
import diego.soro.model2.core.configuration.CompanyConfiguration;
import diego.soro.model2.core.configuration.SubscriptionTier;
import diego.soro.model2.core.configuration.repository.CompanyConfigurationRepository;
import diego.soro.model2.core.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.UUID;

/**
 * Service for managing company configurations
 *
 * Provides business logic for module activation/deactivation
 * following the configuration-driven architecture pattern.
 *
 * Key features:
 * - Cached configuration retrieval for performance
 * - Default configuration creation
 * - Module state management
 * - Configuration validation
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class ConfigurationService {

    private final CompanyConfigurationRepository configurationRepository;
    private final CompanyRepository companyRepository;

    // ===== CONFIGURATION RETRIEVAL =====

    /**
     * Gets company configuration with caching for performance
     * Creates default configuration if none exists
     *
     * @param companyId the company UUID
     * @return the company configuration
     */
    @Cacheable(value = "company-config", key = "#companyId")
    public CompanyConfiguration getConfiguration(UUID companyId) {
        return configurationRepository.findByCompanyId(companyId)
                .orElseGet(() -> createDefaultConfiguration(companyId));
    }

    /**
     * Gets configuration with company entity loaded
     * Useful when you need both configuration and company data
     *
     * @param companyId the company UUID
     * @return the configuration with company loaded
     */
    public CompanyConfiguration getConfigurationWithCompany(UUID companyId) {
        return configurationRepository.findByCompanyIdWithCompany(companyId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Configuration not found for company: " + companyId));
    }

    /**
     * Checks if a module is enabled for a company
     * Primary method for feature toggles
     *
     * @param companyId the company UUID
     * @param moduleName the module name
     * @return true if module is enabled, false otherwise
     */
    @Cacheable(value = "module-status", key = "#companyId + '-' + #moduleName")
    public boolean isModuleEnabled(UUID companyId, String moduleName) {
        CompanyConfiguration config = getConfiguration(companyId);
        return config.isModuleEnabled(moduleName);
    }

    // ===== CONFIGURATION MANAGEMENT =====

    /**
     * Updates module configuration for a company
     * Invalidates cache after update
     *
     * @param companyId the company UUID
     * @param modules map of module names to enabled status
     * @param modifiedBy user making the changes
     * @return updated configuration
     */
    @Transactional
    @CacheEvict(value = {"company-config", "module-status"}, key = "#companyId")
    public CompanyConfiguration updateConfiguration(UUID companyId,
                                                    Map<String, Boolean> modules,
                                                    String modifiedBy) {
        CompanyConfiguration config = getConfiguration(companyId);

        // Update activable modules (core modules are always enabled)
        updateModuleStates(config, modules);

        // Update metadata
        config.setLastModifiedBy(modifiedBy);
        config.setConfigurationNotes("Module configuration updated");

        CompanyConfiguration saved = configurationRepository.save(config);

        log.info("Configuration updated for company {} by user {}: {} modules changed",
                companyId, modifiedBy, modules.size());

        return saved;
    }

    /**
     * Updates subscription tier for a company
     * This method would typically be called from billing system integration
     *
     * @param companyId the company UUID
     * @param newTier the new subscription tier
     * @return updated configuration
     */
    @Transactional
    @CacheEvict(value = {"company-config", "module-status"}, key = "#companyId")
    public CompanyConfiguration updateTier(UUID companyId, SubscriptionTier newTier) {
        CompanyConfiguration config = getConfiguration(companyId);
        SubscriptionTier oldTier = config.getTier();

        config.setTier(newTier);
        config.setConfigurationNotes(
                String.format("Tier updated from %s to %s", oldTier, newTier));

        // Handle tier downgrade - disable features not available in lower tier
        if (isDowngrade(oldTier, newTier)) {
            handleTierDowngrade(config, newTier);
        }

        CompanyConfiguration saved = configurationRepository.save(config);

        log.info("Tier updated for company {} from {} to {}", companyId, oldTier, newTier);

        return saved;
    }

    // ===== PRIVATE HELPER METHODS =====

    /**
     * Creates default configuration for a company
     * Called when no configuration exists
     */
    @Transactional
    private CompanyConfiguration createDefaultConfiguration(UUID companyId) {
        log.info("Creating default configuration for company {}", companyId);

        // Fetch the actual Company entity
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Company not found with id: " + companyId));

        CompanyConfiguration config = CompanyConfiguration.builder()
                .company(company)
                .tier(SubscriptionTier.LITE)
                .lastModifiedBy("system")
                .configurationNotes("Default configuration created")
                .active(true)
                .build();

        return configurationRepository.save(config);
    }

    /**
     * Updates individual module states from the provided map
     */
    private void updateModuleStates(CompanyConfiguration config, Map<String, Boolean> modules) {
        modules.forEach((moduleName, enabled) -> {
            switch (moduleName.toLowerCase()) {
                case "menu-advanced", "menuadvanced" -> config.setMenuAdvancedEnabled(enabled);
                case "kitchen" -> config.setKitchenEnabled(enabled);
                case "customer" -> config.setCustomerEnabled(enabled);
                case "table" -> config.setTableEnabled(enabled);
                case "financial" -> config.setFinancialEnabled(enabled);
                case "staff" -> config.setStaffEnabled(enabled);
                case "analytics" -> config.setAnalyticsEnabled(enabled);
                case "notifications" -> config.setNotificationsEnabled(enabled);
                case "compliance" -> config.setComplianceEnabled(enabled);
                default -> log.warn("Unknown module name: {}", moduleName);
            }
        });
    }

    /**
     * Checks if tier change is a downgrade
     */
    private boolean isDowngrade(SubscriptionTier from, SubscriptionTier to) {
        return from.ordinal() > to.ordinal();
    }

    /**
     * Handles tier downgrade by disabling incompatible features
     */
    private void handleTierDowngrade(CompanyConfiguration config, SubscriptionTier newTier) {
        switch (newTier) {
            case LITE -> {
                // Lite tier: Disable all paid modules
                config.setMenuAdvancedEnabled(false);
                config.setKitchenEnabled(false);
                config.setCustomerEnabled(false);
                config.setTableEnabled(false);
                config.setFinancialEnabled(false);
                config.setStaffEnabled(false);
                config.setAnalyticsEnabled(false);
                config.setNotificationsEnabled(false);
                config.setComplianceEnabled(false);
            }
            case PRO -> {
                // Pro tier: Only disable enterprise-only features
                config.setComplianceEnabled(false);
            }
            case ENTERPRISE -> {
                // Enterprise: All features available (no downgrades)
            }
        }

        log.info("Tier downgrade: disabled incompatible features for tier {}", newTier);
    }
}