package diego.soro.model2.modules.configuration.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * DTO for module validation responses
 * Standardized validation result format
 */
@Data
@Builder
public class ModuleValidationDTO {
    private boolean isValid;
    private List<String> errors;
    private List<String> warnings;
    private String validationLevel; // STRICT, MODERATE, LENIENT
    private String message;

    public static ModuleValidationDTO valid() {
        return ModuleValidationDTO.builder()
                .isValid(true)
                .message("Validation passed")
                .validationLevel("STRICT")
                .build();
    }

    public static ModuleValidationDTO invalid(List<String> errors) {
        return ModuleValidationDTO.builder()
                .isValid(false)
                .errors(errors)
                .message("Validation failed")
                .validationLevel("STRICT")
                .build();
    }
}