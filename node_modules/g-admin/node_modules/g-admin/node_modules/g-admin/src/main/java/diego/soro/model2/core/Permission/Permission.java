package diego.soro.model2.core.Permission;

import diego.soro.model2.core.BaseEntity.BaseEntity;
import diego.soro.model2.core.Role.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class Permission extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String code; // Ej: USER_CREATE, INVENTORY_VIEW

    @Column(nullable = false)
    private String name; // Ej: Crear usuario

    private String description;

    private Boolean isSystem = false;

    @ManyToMany(mappedBy = "permissions")
    private Set<Role> roles = new HashSet<>();
}