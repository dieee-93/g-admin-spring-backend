package diego.soro.model2.core.Permission;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreatePermissionDTO {
    private String code;        // Ej: "USER_CREATE"
    private String name;        // Ej: "Crear Usuario"
    private String description;
    private Boolean isSystem = false;
}