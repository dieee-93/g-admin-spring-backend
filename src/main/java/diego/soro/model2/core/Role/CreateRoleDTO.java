package diego.soro.model2.core.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Set;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateRoleDTO {
    @NotBlank(message = "Role name is required")
    @Size(max = 100, message = "Role name must not exceed 100 characters")
    private String name;
    
    @Size(max = 255, message = "Description must not exceed 255 characters")
    private String description;
    
    private Boolean isSystem = false;
    private UUID parentRoleId;
    private Set<UUID> permissionIds;
}