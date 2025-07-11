package diego.soro.model2.core.Role.service;

import diego.soro.model2.core.Permission.Permission;
import diego.soro.model2.core.Permission.service.PermissionService;
import diego.soro.model2.core.Role.Role;
import diego.soro.model2.core.Role.CreateRoleDTO;
import diego.soro.model2.core.Role.RoleMapper;
import diego.soro.model2.core.Role.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import diego.soro.model2.core.exception.EntityNotFoundException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;
    private final PermissionService permissionService;

    @Transactional
    public Role createRole(CreateRoleDTO dto) {
        Role role = roleMapper.toEntity(dto);

        // Asignar parent si viene
        if (dto.getParentRoleId() != null) {
            Role parent = roleRepository.findById(dto.getParentRoleId())
                    .orElseThrow(() -> new EntityNotFoundException("Parent role not found with id: " + dto.getParentRoleId()));
            role.setParentRole(parent);
        }

        // Asignar permisos
        if (dto.getPermissionIds() != null && !dto.getPermissionIds().isEmpty()) {
            Set<UUID> ids = dto.getPermissionIds();
            Set<Permission> permissions = new HashSet<>();
            for (UUID id : ids) {
                permissions.add(permissionService.findByIdOrThrow(id));
            }
            role.setPermissions(permissions);
        }

        return roleRepository.save(role);
    }
    @Transactional
    public Role updateRole(Role role) {
        return roleRepository.save(role);
    }
    @Transactional
    public void deleteRole(UUID id) {
        roleRepository.deleteById(id);
    }
    public List<Role> findAll() {
        return roleRepository.findAll();
    }
    public Role findByIdOrThrow(UUID id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Role not found with id: " + id));
    }
}