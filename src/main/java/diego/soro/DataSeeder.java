package diego.soro;

import diego.soro.model2.core.Permission.Permission;
import diego.soro.model2.core.Permission.PermissionCode;
import diego.soro.model2.core.Permission.repository.PermissionRepository;
import diego.soro.model2.core.Role.Role;
import diego.soro.model2.core.Role.repository.RoleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(DataSeeder.class);
    private final PermissionRepository permissionRepository;
    private final RoleRepository roleRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        seedPermissions();
        seedRoles();
    }

    private void seedPermissions() {
        if (permissionRepository.count() == 0) {
            List<Permission> permissions = Arrays.stream(PermissionCode.values())
                    .map(code -> Permission.builder()
                            .code(code.name())
                            .name(code.name().replace("_", " ").toLowerCase())
                            .description("Auto-generated permission: " + code.name())
                            .isSystem(true)
                            .build())
                    .toList();
                logger.info("Permisos iniciales creados.");
        }
    }

    private void seedRoles() {
        if (roleRepository.count() > 0) return;

        Map<String, Set<String>> rolePermissionsMap = new LinkedHashMap<>();
        rolePermissionsMap.put("ROLE_CUSTOMER", Set.of("SALE_CREATE"));
        rolePermissionsMap.put("ROLE_CASHIER", Set.of("SALE_CREATE", "SALE_VIEW", "STOCK_VIEW"));
        rolePermissionsMap.put("ROLE_MANAGER", Set.of("SALE_CREATE", "SALE_VIEW", "STOCK_VIEW", "USER_VIEW", "BRANCH_VIEW"));
        rolePermissionsMap.put("ROLE_ADMIN", Set.of("SALE_CREATE", "SALE_VIEW", "STOCK_VIEW", "USER_VIEW", "USER_EDIT", "BRANCH_VIEW", "ROLE_VIEW", "ROLE_ASSIGN"));
        rolePermissionsMap.put("ROLE_SUPER_ADMIN", Arrays.stream(PermissionCode.values()).map(Enum::name).collect(Collectors.toSet()));

        Map<String, Role> roleMap = new HashMap<>();

        for (Map.Entry<String, Set<String>> entry : rolePermissionsMap.entrySet()) {
            Set<Permission> perms = permissionRepository.findByCodeIn(entry.getValue());
            Role role = Role.builder()
                    .name(entry.getKey())
                    .description("Auto-generated " + entry.getKey())
                    .permissions(perms)
                    .isSystem(true)
                    .build();

            roleMap.put(entry.getKey(), roleRepository.save(role));
        }

        // Asignar jerarquía (parentRole)
        roleMap.get("ROLE_CASHIER").setParentRole(roleMap.get("ROLE_CUSTOMER"));
        roleMap.get("ROLE_MANAGER").setParentRole(roleMap.get("ROLE_CASHIER"));
        roleMap.get("ROLE_ADMIN").setParentRole(roleMap.get("ROLE_MANAGER"));
        roleMap.get("ROLE_SUPER_ADMIN").setParentRole(roleMap.get("ROLE_ADMIN"));

        roleRepository.saveAll(roleMap.values());

        logger.info("Roles iniciales creados con jerarquía.");
    }
}