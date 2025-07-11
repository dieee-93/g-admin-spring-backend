package diego.soro.model2.core.Role;

import diego.soro.model2.core.BaseEntity.BaseEntity;
import diego.soro.model2.core.Permission.Permission;
import diego.soro.model2.core.User.User;
import jakarta.persistence.*;
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
public class Role extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String name;

    private String description;

    private Boolean isSystem = false;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "role_permission",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    private Set<Permission> permissions = new HashSet<>();

    @ManyToOne
    private Role parentRole;
}
