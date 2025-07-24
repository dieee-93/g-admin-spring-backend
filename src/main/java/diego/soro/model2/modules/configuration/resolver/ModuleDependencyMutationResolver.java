package diego.soro.model2.modules.configuration.resolver;

import diego.soro.model2.modules.configuration.domain.ModuleName;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;
import diego.soro.model2.modules.configuration.service.ModuleDependencyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.UUID;

/**
 * GraphQL Mutation Resolver for Module Dependencies
 * Handles module activation and deactivation operations
 */
@DgsComponent
@RequiredArgsConstructor
@Slf4j
public class ModuleDependencyMutationResolver {

    private final ModuleDependencyService dependencyService;

    @DgsMutation
    @PreAuthorize("hasPermission(#input.companyId, 'Company', 'MODULE_ACTIVATE')")
    public ModuleActivationResultGQL activateModule(@InputArgument ModuleActivationInputGQL input) {

        log.info("GraphQL activateModule: companyId={}, module={}, confirmCascade={}",
                input.getCompanyId(), input.getModuleName(), input.getConfirmCascade());

        ModuleName module = ModuleName.valueOf(input.getModuleName().name());

        // Create activation plan
        DependencyActivationPlan plan = dependencyService.createActivationPlan(
                input.getCompanyId(), module);

        // Check if confirmation is required
        if (plan.isRequiresConfirmation() && !input.getConfirmCascade()) {
            return ModuleActivationResultGQL.builder()
                    .success(false)
                    .targetModule(input.getModuleName())
                    .activatedModules(java.util.Collections.emptyList())
                    .totalCost(java.math.BigDecimal.ZERO)
                    .activationTimestamp(java.time.LocalDateTime.now())
                    .message("Cascade activation requires confirmation. Missing dependencies: " +
                            plan.getMissingDependencies() + ". Total cost: $" + plan.getTotalMonthlyCost())
                    .errorCode("CONFIRMATION_REQUIRED")
                    .build();
        }

        // Execute activation
        ModuleActivationResult result = dependencyService.executeActivationPlan(
                input.getCompanyId(), plan);

        return mapToGraphQLActivationResult(result, input.getModuleName());
    }

    @DgsMutation
    @PreAuthorize("hasPermission(#companyId, 'Company', 'MODULE_DEACTIVATE')")
    public ModuleActivationResultGQL deactivateModule(
            @InputArgument UUID companyId,
            @InputArgument ModuleNameGQL moduleName,
            @InputArgument Boolean confirmImpact) {

        log.info("GraphQL deactivateModule: companyId={}, module={}, confirmImpact={}",
                companyId, moduleName, confirmImpact);

        ModuleName module = ModuleName.valueOf(moduleName.name());

        // Validate deactivation impact
        DeactivationValidationResult validation = dependencyService.validateDeactivation(companyId, module);

        if (!validation.isCanDeactivate() && !confirmImpact) {
            return ModuleActivationResultGQL.builder()
                    .success(false)
                    .targetModule(moduleName)
                    .activatedModules(java.util.Collections.emptyList())
                    .totalCost(java.math.BigDecimal.ZERO)
                    .activationTimestamp(java.time.LocalDateTime.now())
                    .message("Deactivation requires confirmation. " + validation.getWarningMessage())
                    .errorCode("IMPACT_CONFIRMATION_REQUIRED")
                    .build();
        }

        // TODO: Implement actual deactivation logic in service
        // For now, return success simulation
        return ModuleActivationResultGQL.builder()
                .success(true)
                .targetModule(moduleName)
                .activatedModules(java.util.Collections.emptyList())
                .totalCost(validation.getMonthlySavings().negate()) // Negative cost = savings
                .activationTimestamp(java.time.LocalDateTime.now())
                .message("Module deactivated successfully. Monthly savings: $" + validation.getMonthlySavings())
                .build();
    }

    private ModuleActivationResultGQL mapToGraphQLActivationResult(
            ModuleActivationResult result, ModuleNameGQL targetModule) {
        return ModuleActivationResultGQL.builder()
                .success(result.isSuccess())
                .targetModule(targetModule)
                .activatedModules(result.getActivatedModules().stream()
                        .map(m -> ModuleNameGQL.valueOf(m.name()))
                        .toList())
                .totalCost(result.getTotalCost())
                .activationTimestamp(result.getActivationTimestamp())
                .message(result.getMessage())
                .errorCode(result.getErrorCode())
                .build();
    }
}