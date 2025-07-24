package diego.soro.model2.modules.configuration.dto;

import diego.soro.model2.modules.configuration.domain.ModuleName;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

/**
 * Result DTO for module activation execution
 * Contains execution status and activated modules information
 */
@Data
@Builder
public class ModuleActivationResult {
    private boolean success;
    private ModuleName targetModule;
    private List<ModuleName> activatedModules;
    private BigDecimal totalCost;
    private LocalDateTime activationTimestamp;
    private String message;
    private String errorCode;

    public static ModuleActivationResult success(ModuleName targetModule, List<ModuleName> activated, BigDecimal cost) {
        return ModuleActivationResult.builder()
                .success(true)
                .targetModule(targetModule)
                .activatedModules(activated)
                .totalCost(cost)
                .activationTimestamp(LocalDateTime.now())
                .message(String.format("Successfully activated %d modules", activated.size()))
                .build();
    }

    public static ModuleActivationResult failure(ModuleName targetModule, String error, String errorCode) {
        return ModuleActivationResult.builder()
                .success(false)
                .targetModule(targetModule)
                .activatedModules(Collections.emptyList())
                .totalCost(BigDecimal.ZERO)
                .activationTimestamp(LocalDateTime.now())
                .message(error)
                .errorCode(errorCode)
                .build();
    }
}
