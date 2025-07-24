package diego.soro.model2.modules.configuration.dto;

import diego.soro.model2.modules.configuration.domain.ModuleName;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

/**
 * Result DTO for module deactivation validation
 * Contains impact analysis and affected modules information
 */
@Data
@Builder
public class DeactivationValidationResult {
    private boolean canDeactivate;
    private List<ModuleName> affectedModules;
    private String warningMessage;
    private boolean requiresConfirmation;
    private BigDecimal monthlySavings;
    private String impactLevel; // LOW, MEDIUM, HIGH

    public static DeactivationValidationResult safe(ModuleName module, BigDecimal savings) {
        return DeactivationValidationResult.builder()
                .canDeactivate(true)
                .affectedModules(Collections.emptyList())
                .warningMessage("Safe to deactivate - no dependencies")
                .requiresConfirmation(false)
                .monthlySavings(savings)
                .impactLevel("LOW")
                .build();
    }

    public static DeactivationValidationResult unsafe(List<ModuleName> affected, String warning, BigDecimal savings) {
        return DeactivationValidationResult.builder()
                .canDeactivate(false)
                .affectedModules(affected)
                .warningMessage(warning)
                .requiresConfirmation(true)
                .monthlySavings(savings)
                .impactLevel(affected.size() > 3 ? "HIGH" : "MEDIUM")
                .build();
    }

    public static DeactivationValidationResult alwaysActive(ModuleName module) {
        return DeactivationValidationResult.builder()
                .canDeactivate(false)
                .affectedModules(Collections.emptyList())
                .warningMessage("Cannot deactivate always-active modules")
                .requiresConfirmation(false)
                .monthlySavings(BigDecimal.ZERO)
                .impactLevel("HIGH")
                .build();
    }
}