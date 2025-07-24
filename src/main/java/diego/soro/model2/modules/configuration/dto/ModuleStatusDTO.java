package diego.soro.model2.modules.configuration.dto;

import diego.soro.model2.modules.configuration.domain.ModuleName;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO for complete module status information
 * Used in module dashboard and status endpoints
 */
@Data
@Builder
public class ModuleStatusDTO {
    private diego.soro.model2.modules.configuration.domain.ModuleName moduleName;
    private String displayName;
    private boolean isActive;
    private boolean isAlwaysActive;
    private boolean canDeactivate;
    private LocalDateTime activatedDate;
    private LocalDateTime lastUsed;
    private List<ModuleName> dependencies;
    private List<ModuleName> dependents;
    private String status; // ACTIVE, INACTIVE, PENDING
    private String healthStatus; // HEALTHY, WARNING, ERROR
}

