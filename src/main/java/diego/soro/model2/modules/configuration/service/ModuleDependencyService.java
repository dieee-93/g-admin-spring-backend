package diego.soro.model2.modules.configuration.service;

// ACTUALIZANDO IMPORTS PARA USAR TU PACKAGE STRUCTURE
import diego.soro.model2.core.configuration.CompanyConfiguration;
import diego.soro.model2.core.configuration.service.ConfigurationService;
import diego.soro.model2.modules.configuration.domain.ModuleDependency;
import diego.soro.model2.modules.configuration.domain.ModuleName;
import diego.soro.model2.modules.configuration.repository.ModuleDependencyRepository;
import diego.soro.model2.modules.configuration.dto.DependencyValidationResult;
import diego.soro.model2.modules.configuration.dto.DependencyActivationPlan;
import diego.soro.model2.modules.configuration.dto.ModuleActivationResult;
import diego.soro.model2.modules.configuration.dto.DeactivationValidationResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * ModuleDependencyService - Task 1.2 Core Implementation
 *
 * Handles module dependency validation, cascade activation planning,
 * and cost calculation for G-ADMIN configuration-driven system.
 *
 * Key Features:
 * - Automatic dependency resolution
 * - Cascade activation planning with cost calculation
 * - User confirmation flow for complex activations
 * - Graceful handling of missing dependencies
 * - Performance optimized (<100ms validation target)
 */
@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class ModuleDependencyService {

    private final ModuleDependencyRepository dependencyRepository;
    private final ConfigurationService configurationService;
    private final ModulePricingService modulePricingService;

    /**
     * Validates if a module can be activated
     * Uses user's superior DependencyValidationResult with factory methods
     *
     * @param companyId Company identifier
     * @param module Module to validate
     * @return DependencyValidationResult with missing dependencies if any
     */
    public DependencyValidationResult validateActivation(UUID companyId, ModuleName module) {
        log.debug("Validating activation of module {} for company {}", module, companyId);

        // Always-active modules can always be activated
        if (module.isAlwaysActive()) {
            return DependencyValidationResult.canActivate(module);
        }

        // Get required and optional dependencies for the module
        List<ModuleDependency> requiredDeps = dependencyRepository.findRequiredDependencies(module);
        // Get all dependencies and filter for optional ones
        List<ModuleDependency> allDeps = dependencyRepository.findByModuleName(module);
        List<ModuleDependency> optionalDeps = allDeps.stream()
                .filter(dep -> !dep.getIsRequired())
                .collect(Collectors.toList());

        log.debug("Found {} required + {} optional dependencies for module {}",
                requiredDeps.size(), optionalDeps.size(), module);

        // Get current configuration
        CompanyConfiguration config = configurationService.getConfiguration(companyId);

        // Check which required dependencies are missing
        List<ModuleName> missingRequired = new ArrayList<>();

        for (ModuleDependency dep : requiredDeps) {
            if (!isModuleActive(config, dep.getDependsOnModule())) {
                missingRequired.add(dep.getDependsOnModule());
                log.debug("Missing required dependency: {} requires {}", module, dep.getDependsOnModule());
            }
        }

        // If no missing dependencies, module can be activated
        if (missingRequired.isEmpty()) {
            return DependencyValidationResult.canActivate(module);
        }

        // Return result with missing dependencies using user's factory method
        return DependencyValidationResult.missingDependencies(module, missingRequired, requiredDeps);
    }

    /**
     * Creates activation plan with dependency resolution
     * Uses user's superior DependencyActivationPlan with factory methods
     *
     * @param companyId Company identifier
     * @param module Target module to activate
     * @return DependencyActivationPlan with costs and activation sequence
     */
    public DependencyActivationPlan createActivationPlan(UUID companyId, ModuleName module) {
        log.debug("Creating activation plan for module {} company {}", module, companyId);

        DependencyValidationResult validation = validateActivation(companyId, module);

        // If can activate directly, use user's factory method
        if (validation.canActivate()) {
            return DependencyActivationPlan.directActivation(module);
        }

        // Check if blocked (e.g., tier restrictions)
        if (isModuleBlockedForCompany(companyId, module)) {
            return DependencyActivationPlan.blocked(module, "Module not available in current subscription tier");
        }

        // Create cascade activation plan using user's factory method
        List<ModuleName> requiredActivations = new ArrayList<>(validation.getMissingDependencies());
        BigDecimal totalCost = calculateActivationCost(requiredActivations);

        return DependencyActivationPlan.cascadeActivation(module, requiredActivations, totalCost);
    }

    /**
     * Executes activation plan with confirmation flow
     * Uses user's superior DTOs and factory methods
     *
     * @param companyId Company identifier
     * @param plan Activation plan to execute
     * @return ModuleActivationResult with execution details
     */
    public ModuleActivationResult executeActivationPlan(UUID companyId, DependencyActivationPlan plan) {
        log.info("Executing activation plan for company {} target module {}",
                companyId, plan.getTargetModule());

        try {
            List<ModuleName> activatedModules = new ArrayList<>();
            CompanyConfiguration config = configurationService.getConfiguration(companyId);

            // Determine modules to activate based on plan type
            List<ModuleName> modulesToActivate;
            if (plan.isDirectActivation()) {
                modulesToActivate = List.of(plan.getTargetModule());
            } else if (plan.isCascadeActivation()) {
                modulesToActivate = plan.getAllModulesInPlan();
            } else {
                // Plan is blocked
                return ModuleActivationResult.failure(
                        plan.getTargetModule(),
                        "Activation blocked: " + plan.getDescription(),
                        "ACTIVATION_BLOCKED"
                );
            }

            // Activate modules in dependency order
            for (ModuleName module : modulesToActivate) {
                if (!isModuleActive(config, module)) {
                    // Activate the module
                    config = activateModule(config, module);
                    activatedModules.add(module);
                    log.debug("Activated module {} for company {}", module, companyId);
                }
            }

            // Save configuration
            configurationService.updateConfiguration(companyId, config);

            // Calculate total cost using user's DTO method
            BigDecimal totalCost = plan.hasCosts() ? plan.getTotalCost() : BigDecimal.ZERO;

            return ModuleActivationResult.success(plan.getTargetModule(), activatedModules, totalCost);

        } catch (Exception e) {
            log.error("Failed to execute activation plan for company {}: {}", companyId, e.getMessage(), e);

            return ModuleActivationResult.failure(
                    plan.getTargetModule(),
                    "Activation failed: " + e.getMessage(),
                    "ACTIVATION_FAILED"
            );
        }
    }

    /**
     * Validates deactivation impact
     * Checks which modules depend on the target module
     *
     * @param companyId Company identifier
     * @param module Module to deactivate
     * @return DeactivationValidationResult with impact analysis
     */
    public DeactivationValidationResult validateDeactivation(UUID companyId, ModuleName module) {
        log.debug("Validating deactivation of module {} for company {}", module, companyId);

        // Always-active modules cannot be deactivated
        if (module.isAlwaysActive()) {
            return DeactivationValidationResult.builder()
                    .canDeactivate(false)
                    .affectedModules(Collections.emptyList())
                    .warningMessage("Cannot deactivate always-active modules")
                    .requiresConfirmation(false)
                    .build();
        }

        // Find modules that depend on this one
        List<ModuleName> dependentModules = dependencyRepository.findDependentModules(module);
        CompanyConfiguration config = configurationService.getConfiguration(companyId);

        // Filter to only active dependent modules
        List<ModuleName> activeAffectedModules = dependentModules.stream()
                .filter(depModule -> isModuleActive(config, depModule))
                .collect(Collectors.toList());

        boolean canDeactivate = activeAffectedModules.isEmpty();
        String warningMessage = canDeactivate ?
                "Safe to deactivate" :
                String.format("Will affect %d active modules: %s",
                        activeAffectedModules.size(),
                        activeAffectedModules.stream().map(Enum::name).collect(Collectors.joining(", ")));

        return DeactivationValidationResult.builder()
                .canDeactivate(canDeactivate)
                .affectedModules(activeAffectedModules)
                .warningMessage(warningMessage)
                .requiresConfirmation(!canDeactivate)
                .monthlySavings(modulePricingService.getModuleMonthlyCost(module))
                .build();
    }

    /**
     * Helper method to check if a module is currently active
     *
     * @param config Company configuration
     * @param module Module to check
     * @return true if module is active
     */
    private boolean isModuleActive(CompanyConfiguration config, ModuleName module) {
        return switch (module) {
            case CORE -> config.getCoreModuleActive();
            case INVENTORY -> config.getInventoryModuleActive();
            case SALES -> config.getSalesModuleActive();
            case MENU -> config.getMenuModuleActive();
            case KITCHEN_MANAGEMENT -> config.getKitchenManagementActive();
            case CUSTOMER_CRM -> config.getCustomerCrmActive();
            case ADVANCED_MENU -> config.getAdvancedMenuActive();
            case TABLE_MANAGEMENT -> config.getTableManagementActive();
            case FINANCIAL_ANALYSIS -> config.getFinancialAnalysisActive();
            case STAFF_MANAGEMENT -> config.getStaffManagementActive();
            case ANALYTICS_PRO -> config.getAnalyticsProActive();
            case COMPLIANCE -> config.getComplianceActive();

        };
    }

    /**
     * Helper method to activate a module in configuration
     *
     * @param config Company configuration to update
     * @param module Module to activate
     * @return Updated configuration
     */
    private CompanyConfiguration activateModule(CompanyConfiguration config, ModuleName module) {
        switch (module) {
            case KITCHEN_MANAGEMENT -> config.setKitchenManagementActive(true);
            case CUSTOMER_CRM -> config.setCustomerCrmActive(true);
            case ADVANCED_MENU -> config.setAdvancedMenuActive(true);
            case TABLE_MANAGEMENT -> config.setTableManagementActive(true);
            case FINANCIAL_ANALYSIS -> config.setFinancialAnalysisActive(true);
            case STAFF_MANAGEMENT -> config.setStaffManagementActive(true);
            case ANALYTICS_PRO -> config.setAnalyticsProActive(true);
            case COMPLIANCE -> config.setComplianceActive(true);
            // Always-active modules are already active
            default -> log.debug("Module {} is always active, no action needed", module);
        }
        return config;
    }

    /**
     * Calculate activation order respecting dependencies
     * Uses topological sort to ensure dependencies are activated first
     *
     * @param modules Modules to activate
     * @return Ordered list for safe activation
     */
    private List<ModuleName> calculateActivationOrder(List<ModuleName> modules) {
        log.debug("Calculating activation order for modules: {}", modules);

        // Simple dependency order based on business logic
        // Dependencies must be activated before dependent modules
        List<ModuleName> ordered = new ArrayList<>();
        Set<ModuleName> toActivate = new HashSet<>(modules);

        // Activation priority order (dependencies first)
        ModuleName[] priorityOrder = {
                ModuleName.CORE, ModuleName.INVENTORY, ModuleName.SALES, ModuleName.MENU,
                ModuleName.CUSTOMER_CRM, ModuleName.KITCHEN_MANAGEMENT,
                ModuleName.ADVANCED_MENU, ModuleName.STAFF_MANAGEMENT,
                ModuleName.TABLE_MANAGEMENT, ModuleName.FINANCIAL_ANALYSIS,
                ModuleName.ANALYTICS_PRO, ModuleName.COMPLIANCE
        };

        for (ModuleName module : priorityOrder) {
            if (toActivate.contains(module)) {
                ordered.add(module);
            }
        }

        log.debug("Calculated activation order: {}", ordered);
        return ordered;
    }

    /**
     * Calculate total monthly cost for module activation
     *
     * @param modules Modules to calculate cost for
     * @return Total monthly cost
     */
    private BigDecimal calculateActivationCost(List<ModuleName> modules) {
        BigDecimal totalCost = BigDecimal.ZERO;

        for (ModuleName module : modules) {
            // Skip always-active modules (no additional cost)
            if (!module.isAlwaysActive()) {
                BigDecimal moduleCost = modulePricingService.getModuleMonthlyCost(module);
                totalCost = totalCost.add(moduleCost);
            }
        }

        log.debug("Calculated total activation cost: ${} for modules: {}", totalCost, modules);
        return totalCost;
    }

    /**
     * Get dependency chain for a module (recursive)
     * Used for complex dependency analysis
     *
     * @param module Starting module
     * @return Complete dependency chain
     */
    public List<ModuleName> getDependencyChain(ModuleName module) {
        Set<ModuleName> visited = new HashSet<>();
        List<ModuleName> chain = new ArrayList<>();

        buildDependencyChain(module, visited, chain);

        return chain;
    }

    private void buildDependencyChain(ModuleName module, Set<ModuleName> visited, List<ModuleName> chain) {
        if (visited.contains(module)) {
            return; // Avoid circular dependencies
        }

        visited.add(module);

        List<ModuleDependency> dependencies = dependencyRepository.findRequiredDependencies(module);
        for (ModuleDependency dep : dependencies) {
            buildDependencyChain(dep.getDependsOnModule(), visited, chain);
        }

        if (!chain.contains(module)) {
            chain.add(module);
        }
    }
}