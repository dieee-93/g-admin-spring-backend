package diego.soro.model2.core.configuration.repository;

import diego.soro.model2.core.configuration.CompanyConfiguration;
import diego.soro.model2.core.configuration.SubscriptionTier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository for CompanyConfiguration entity
 *
 * Provides data access methods for company configuration management
 * following the configuration-driven architecture pattern.
 */
@Repository
public interface CompanyConfigurationRepository extends JpaRepository<CompanyConfiguration, UUID> {

    /**
     * Finds configuration by company ID
     * Primary method for retrieving company configuration
     *
     * @param companyId the company UUID
     * @return Optional containing the configuration if found
     */
    Optional<CompanyConfiguration> findByCompanyId(UUID companyId);

    /**
     * Finds configuration by company ID with company entity loaded
     * Useful when you need both configuration and company data
     *
     * @param companyId the company UUID
     * @return Optional containing the configuration with company loaded
     */
    @Query("SELECT cc FROM CompanyConfiguration cc JOIN FETCH cc.company WHERE cc.company.id = :companyId")
    Optional<CompanyConfiguration> findByCompanyIdWithCompany(@Param("companyId") UUID companyId);

    /**
     * Checks if configuration exists for a company
     * Useful for validation before creating new configurations
     *
     * @param companyId the company UUID
     * @return true if configuration exists, false otherwise
     */
    boolean existsByCompanyId(UUID companyId);

    /**
     * Finds all configurations by subscription tier
     * Useful for bulk operations or analytics by tier
     *
     * @param tier the subscription tier
     * @return List of configurations for the specified tier
     */
    List<CompanyConfiguration> findByTier(SubscriptionTier tier);

    /**
     * Finds configurations where a specific module is enabled
     * Useful for feature usage analytics and module management
     *
     * Example usage:
     * - findByKitchenEnabledTrue() for kitchen module analytics
     * - findByCustomerEnabledTrue() for customer module metrics
     */

    // Kitchen module
    List<CompanyConfiguration> findByKitchenEnabledTrue();

    // Customer module
    List<CompanyConfiguration> findByCustomerEnabledTrue();

    // Advanced menu module
    List<CompanyConfiguration> findByMenuAdvancedEnabledTrue();

    // Analytics module
    List<CompanyConfiguration> findByAnalyticsEnabledTrue();

    /**
     * Counts configurations by subscription tier
     * Useful for business metrics and tier distribution analysis
     *
     * @param tier the subscription tier
     * @return count of configurations for the specified tier
     */
    long countByTier(SubscriptionTier tier);

    /**
     * Counts configurations where a specific module is enabled
     * Useful for module adoption metrics
     *
     * @return count of configurations with the module enabled
     */

    // Kitchen module adoption
    long countByKitchenEnabledTrue();

    // Customer module adoption
    long countByCustomerEnabledTrue();

    // Advanced menu adoption
    long countByMenuAdvancedEnabledTrue();

    /**
     * Custom query to find configurations with multiple modules enabled
     * Useful for identifying customers using multiple paid modules
     *
     * @param moduleCount minimum number of activable modules enabled
     * @return List of configurations with at least the specified number of modules
     */
    @Query("""
        SELECT cc FROM CompanyConfiguration cc 
        WHERE (CASE WHEN cc.menuAdvancedEnabled = true THEN 1 ELSE 0 END +
               CASE WHEN cc.kitchenEnabled = true THEN 1 ELSE 0 END +
               CASE WHEN cc.customerEnabled = true THEN 1 ELSE 0 END +
               CASE WHEN cc.tableEnabled = true THEN 1 ELSE 0 END +
               CASE WHEN cc.financialEnabled = true THEN 1 ELSE 0 END +
               CASE WHEN cc.staffEnabled = true THEN 1 ELSE 0 END +
               CASE WHEN cc.analyticsEnabled = true THEN 1 ELSE 0 END +
               CASE WHEN cc.notificationsEnabled = true THEN 1 ELSE 0 END +
               CASE WHEN cc.complianceEnabled = true THEN 1 ELSE 0 END) >= :moduleCount
        """)
    List<CompanyConfiguration> findByActiveModuleCountGreaterThanEqual(@Param("moduleCount") int moduleCount);

    /**
     * Finds configurations that need tier upgrade suggestions
     * Based on active modules vs current tier limitations
     *
     * @return List of configurations that might benefit from tier upgrade
     */
    @Query("""
        SELECT cc FROM CompanyConfiguration cc 
        WHERE cc.tier = 'LITE' AND (
            cc.menuAdvancedEnabled = true OR 
            cc.kitchenEnabled = true OR 
            cc.customerEnabled = true OR
            cc.tableEnabled = true OR
            cc.financialEnabled = true OR
            cc.staffEnabled = true OR
            cc.analyticsEnabled = true OR
            cc.notificationsEnabled = true
        )
        """)
    List<CompanyConfiguration> findLiteTierWithPaidModules();
}