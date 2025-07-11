package diego.soro.model2.core.Permission.service;

import diego.soro.model2.core.Permission.Permission;
import diego.soro.model2.core.Permission.CreatePermissionDTO;
import diego.soro.model2.core.Permission.PermissionMapper;
import diego.soro.model2.core.Permission.repository.PermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PermissionService {

    private final PermissionRepository permissionRepository;
    private final PermissionMapper permissionMapper;

    public Permission createPermission(CreatePermissionDTO dto) {
        Permission permission = permissionMapper.toEntity(dto);
        return permissionRepository.save(permission);
    }
    public List<Permission> findAll() {
        return permissionRepository.findAll();
    }

    public Permission findByIdOrThrow(java.util.UUID id) {
        return permissionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Permission not found"));
    }
}