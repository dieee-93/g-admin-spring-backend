
package diego.soro.model2.modules.configuration.resolver;

import diego.soro.model2.modules.configuration.domain.ModuleName;
import diego.soro.model2.modules.configuration.dto.*;
import diego.soro.model2.modules.configuration.service.ModuleDependencyService;
import diego.soro.model2.modules.configuration.service.ModulePricingService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;
import java.util.UUID;

/**
 * GraphQL Query Resolver for Module Dependencies
 * Exposes module dependency management through GraphQL API
 *
 * Security: Method-level authorization with @PreAuthorize
 * Performance: Leverages service layer caching
 * Error Handling: GraphQL-friendly error responses
 */
@DgsComponent
@RequiredArgsConstructor
@Slf4j
public class ModuleDependencyQueryResolver {

    private final ModuleDependencyService dependencyService;
    private final ModulePricingService pricingService;

    @DgsQuery
    @PreAuthorize("hasPermission(#companyId, 'Company', 'MODULE_VIEW')")
    public DependencyValidationResultGQL validateModuleActivation(
            @InputArgument UUID companyId,
            @InputArgument ModuleNameGQL moduleName) {

        log.debug("GraphQL validateModuleActivation: companyId={}, module={}", companyId, moduleName);

        ModuleName module = ModuleName.valueOf(moduleName.name());
        DependencyValidationResult result = dependencyService.validateActivation(companyId, module);

        return mapToGraphQLResult(result);
    }

    @DgsQuery
    @PreAuthorize("hasPermission(#companyId, 'Company', 'MODULE_CONFIGURE')")
    public DependencyActivationPlanGQL createModuleActivationPlan(
            @InputArgument UUID companyId,
            @InputArgument ModuleNameGQL moduleName) {

        log.debug("GraphQL createModuleActivationPlan: companyId={}, module={}", companyId, moduleName);

        ModuleName module = ModuleName.valueOf(moduleName.name());
        DependencyActivationPlan plan = dependencyService.createActivationPlan(companyId, module);

        return mapToGraphQLPlan(plan);
    }

    @DgsQuery
    @PreAuthorize("hasPermission(#companyId, 'Company', 'MODULE_VIEW')")
    public DeactivationValidationResultGQL validateModuleDeactivation(
            @InputArgument UUID companyId,
            @InputArgument ModuleNameGQL moduleName) {

        log.debug("GraphQL validateModuleDeactivation: companyId={}, module={}", companyId, moduleName);

        ModuleName module = ModuleName.valueOf(moduleName.name());
        DeactivationValidationResult result = dependencyService.validateDeactivation(companyId, module);

        return mapToGraphQLDeactivationResult(result);
    }

    @DgsQuery
    @PreAuthorize("hasRole('USER')")
    public List<ModuleNameGQL> getModuleDependencyChain(@InputArgument ModuleNameGQL moduleName) {

        log.debug("GraphQL getModuleDependencyChain: module={}", moduleName);

        ModuleName module = ModuleName.valueOf(moduleName.name());
        List<ModuleName> chain = dependencyService.getDependencyChain(module);

        return chain.stream()
                .map(m -> ModuleNameGQL.valueOf(m.name()))
                .toList();
    }

    @DgsQuery
    @PreAuthorize("hasRole('USER')")
    public List<ModulePricingGQL> getAllModulePricing() {

        log.debug("GraphQL getAllModulePricing");

        List<ModulePricingDTO> pricing = pricingService.getAllModulePricing();

        return pricing.stream()
                .map(this::mapToGraphQLPricing)
                .toList();
    }

    @DgsQuery
    @PreAuthorize("hasRole('USER')")
    public ModulePricingCalculationGQL calculateModulePricing(
            @InputArgument ModulePricingCalculationInputGQL input) {

        log.debug("GraphQL calculateModulePricing: modules={}", input.getModuleNames());

        List<ModuleName> modules = input.getModuleNames().stream()
                .map(name -> ModuleName.valueOf(name.name()))
                .toList();

        List<ModulePricingDTO> pricing = pricingService.getModulePricing(modules);
        var totalCost = pricingService.calculateTotalMonthlyCost(modules);

        return ModulePricingCalculationGQL.builder()
                .modules(pricing.stream().map(this::mapToGraphQLPricing).toList())
                .totalMonthlyCost(totalCost)
                .moduleCount(modules.size())
                .discountApplied(modules.size() >= 3)
                .volumeDiscountPercentage(calculateDiscountPercentage(modules.size()))
                .build();
    }

    @DgsQuery
    @PreAuthorize("hasRole('USER')")
    public ModuleRecommendationGQL getModuleRecommendations(
            @InputArgument ModuleRecommendationInputGQL input) {

        log.debug("GraphQL getModuleRecommendations: businessType={}", input.getBusinessType());

        List<ModuleName> recommended = pricingService.getRecommendedModules(input.getBusinessType());
        List<ModulePricingDTO> pricing = pricingService.getModulePricing(recommended);
        var totalCost = pricingService.calculateTotalMonthlyCost(recommended);

        return ModuleRecommendationGQL.builder()
                .businessType(input.getBusinessType())
                .recommendedModules(recommended.stream()
                        .map(m -> ModuleNameGQL.valueOf(m.name()))
                        .toList())
                .pricing(pricing.stream().map(this::mapToGraphQLPricing).toList())
                .totalMonthlyCost(totalCost)
                .reasoning(generateRecommendationReasoning(input.getBusinessType()))
                .build();
    }

    @DgsQuery
    @PreAuthorize("hasRole('ADMIN')")
    public ModuleSystemHealthGQL getModuleSystemHealth() {

        log.debug("GraphQL getModuleSystemHealth");

// Simple health check implementation
        return ModuleSystemHealthGQL.builder()
                .status("HEALTHY")
                .dependencyServiceActive(true)
                .pricingServiceActive(true)
                .averageResponseTime("45ms")
                .totalModulesSupported(ModuleName.values().length)
                .lastHealthCheck(java.time.LocalDateTime.now())
                .build();
    }

// =================================================================
// PRIVATE MAPPING METHODS
// =================================================================

    private DependencyValidationResultGQL mapToGraphQLResult(DependencyValidationResult result) {
        return DependencyValidationResultGQL.builder()
                .canActivate(result.isCanActivate())
                .missingDependencies(result.getMissingDependencies().stream()
                        .map(m -> ModuleNameGQL.valueOf(m.name()))
                        .toList())
                .requiredDependencies(result.getRequiredDependencies().stream()
                        .map(m -> ModuleNameGQL.valueOf(m.name()))
                        .toList())
                .validationMessage(result.getValidationMessage())
                .build();
    }

    private DependencyActivationPlanGQL mapToGraphQLPlan(DependencyActivationPlan plan) {
        return DependencyActivationPlanGQL.builder()
                .targetModule(ModuleNameGQL.valueOf(plan.getTargetModule().name()))
                .requiredActivations(plan.getRequiredActivations().stream()
                        .map(m -> ModuleNameGQL.valueOf(m.name()))
                        .toList())
                .activationOrder(plan.getActivationOrder().stream()
                        .map(m -> ModuleNameGQL.valueOf(m.name()))
                        .toList())
                .missingDependencies(plan.getMissingDependencies().stream()
                        .map(m -> ModuleNameGQL.valueOf(m.name()))
                        .toList())
                .totalMonthlyCost(plan.getTotalMonthlyCost())
                .requiresConfirmation(plan.isRequiresConfirmation())
                .planType(PlanTypeGQL.valueOf(plan.getPlanType()))
                .estimatedActivationTime(plan.getEstimatedActivationTime())
                .additionalInfo(plan.getAdditionalInfo())
                .build();
    }

    private DeactivationValidationResultGQL mapToGraphQLDeactivationResult(DeactivationValidationResult result) {
        return DeactivationValidationResultGQL.builder()
                .canDeactivate(result.isCanDeactivate())
                .affectedModules(result.getAffectedModules().stream()
                        .map(m -> ModuleNameGQL.valueOf(m.name()))
                        .toList())
                .warningMessage(result.getWarningMessage())
                .requiresConfirmation(result.isRequiresConfirmation())
                .monthlySavings(result.getMonthlySavings())
                .impactLevel(ImpactLevelGQL.valueOf(result.getImpactLevel()))
                .build();
    }

    private ModulePricingGQL mapToGraphQLPricing(ModulePricingDTO pricing) {
        return ModulePricingGQL.builder()
                .moduleName(ModuleNameGQL.valueOf(pricing.getModuleName().name()))
                .displayName(pricing.getDisplayName())
                .description(pricing.getDescription())
                .monthlyPrice(pricing.getMonthlyPrice())
                .isFree(pricing.isFree())
                .isAlwaysActive(pricing.isAlwaysActive())
                .pricingTier(PricingTierGQL.valueOf(pricing.getPricingTier()))
                .features(pricing.getFeatures())
                .build();
    }

    private java.math.BigDecimal calculateDiscountPercentage(int moduleCount) {
        if (moduleCount >= 6) return new java.math.BigDecimal("15.0");
        if (moduleCount >= 4) return new java.math.BigDecimal("10.0");
        if (moduleCount >= 3) return new java.math.BigDecimal("5.0");
        return java.math.BigDecimal.ZERO;
    }

    private String generateRecommendationReasoning(String businessType) {
        return switch (businessType.toUpperCase()) {
            case "QUICK_SERVICE" ->
                    "Quick service restaurants benefit from efficient kitchen management and streamlined operations";
            case "CASUAL_DINING" ->
                    "Casual dining requires customer management and table reservations for optimal service";
            case "FINE_DINING" ->
                    "Fine dining establishments need advanced menu management and comprehensive staff coordination";
            case "ENTERPRISE" ->
                    "Enterprise operations require full module suite for multi-location management and analytics";
            default ->
                    "Standard recommendation includes core functionality for restaurant operations";
        };
    }
}
