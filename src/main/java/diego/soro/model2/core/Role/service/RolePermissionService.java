package diego.soro.model2.core.Role.service;

import diego.soro.model2.core.Permission.Permission;
import diego.soro.model2.core.Role.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RolePermissionService {

    /**
     * Obtiene todos los permisos de un rol incluyendo los heredados de roles padre
     */
    public Set<Permission> getAllPermissions(Role role) {
        Set<Permission> allPermissions = new HashSet<>(role.getPermissions());
        
        Role parent = role.getParentRole();
        while (parent != null) {
            allPermissions.addAll(parent.getPermissions());
            parent = parent.getParentRole();
        }
        
        return allPermissions;
    }

    /**
     * Verifica si un rol tiene un permiso específico (directo o heredado)
     */
    public boolean hasPermission(Role role, String permissionCode) {
        return getAllPermissions(role).stream()
                .anyMatch(p -> p.getCode().equals(permissionCode));
    }
}