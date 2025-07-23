package diego.soro.model2.modules.configuration.domain;

import diego.soro.model2.core.BaseEntity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * Entity que define las dependencias entre módulos del sistema G-ADMIN
 *
 * Esta tabla almacena la matriz de dependencias que determina qué módulos
 * requieren otros módulos para funcionar correctamente.
 *
 * Ejemplo: KITCHEN_MANAGEMENT requiere SALES y MENU para funcionar
 *
 * @author G-ADMIN Development Team
 * @since Task 1.2 - Module Dependency Management System
 */
@Entity
@Table(name = "module_dependencies",
        indexes = {
                @Index(name = "idx_module_dependencies_module_name", columnList = "module_name"),
                @Index(name = "idx_module_dependencies_depends_on", columnList = "depends_on_module"),
                @Index(name = "idx_module_dependencies_required", columnList = "is_required"),
                @Index(name = "idx_module_dependencies_company", columnList = "company_id")
        })
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class ModuleDependency extends BaseEntity {

    /**
     * El módulo que tiene la dependencia
     * Ejemplo: KITCHEN_MANAGEMENT
     */
    @NotNull
    @Column(name = "module_name", nullable = false)
    @Enumerated(EnumType.STRING)
    private ModuleName moduleName;

    /**
     * El módulo del cual depende
     * Ejemplo: SALES (requerido por KITCHEN_MANAGEMENT)
     */
    @NotNull
    @Column(name = "depends_on_module", nullable = false)
    @Enumerated(EnumType.STRING)
    private ModuleName dependsOnModule;

    /**
     * Indica si la dependencia es obligatoria
     * true = El módulo no puede funcionar sin esta dependencia
     * false = Dependencia opcional que mejora funcionalidad
     */
    @NotNull
    @Column(name = "is_required", nullable = false)
    private Boolean isRequired;

    /**
     * Explicación de por qué existe esta dependencia
     * Útil para debugging y documentación
     *
     * Ejemplo: "Kitchen management needs sales data to process orders"
     */
    @Column(name = "reason", length = 500)
    private String reason;

    /**
     * Override equals para usar solo el ID (UUID)
     * Esto previene problemas de performance con JPA/Hibernate
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ModuleDependency that)) return false;

        // Solo usar ID para equals, evitando problemas de lazy loading
        return getId() != null && getId().equals(that.getId());
    }

    /**
     * Override hashCode para usar solo el ID (UUID)
     * Consistente con equals, previene problemas en Collections
     */
    @Override
    public int hashCode() {
        // Usar hash del UUID para consistency
        return getId() != null ? getId().hashCode() : getClass().hashCode();
    }

    /**
     * Constructor específico para dependencias requeridas
     * @param moduleName el módulo que tiene la dependencia
     * @param dependsOnModule el módulo requerido
     * @param reason explicación de la dependencia
     */
    public static ModuleDependency required(ModuleName moduleName, ModuleName dependsOnModule, String reason) {
        return ModuleDependency.builder()
                .moduleName(moduleName)
                .dependsOnModule(dependsOnModule)
                .isRequired(true)
                .reason(reason)
                .build();
    }

    /**
     * Constructor específico para dependencias opcionales
     * @param moduleName el módulo que tiene la dependencia
     * @param dependsOnModule el módulo opcional
     * @param reason explicación de la dependencia
     */
    public static ModuleDependency optional(ModuleName moduleName, ModuleName dependsOnModule, String reason) {
        return ModuleDependency.builder()
                .moduleName(moduleName)
                .dependsOnModule(dependsOnModule)
                .isRequired(false)
                .reason(reason)
                .build();
    }

    /**
     * Verifica si esta dependencia bloquea la activación
     * @return true si es requerida y podría bloquear activación
     */
    public boolean isBlocking() {
        return Boolean.TRUE.equals(isRequired);
    }

    /**
     * Obtiene descripción legible de la dependencia
     * @return string descriptivo para logs y UI
     */
    public String getDescription() {
        String type = Boolean.TRUE.equals(isRequired) ? "requires" : "optionally uses";
        return String.format("%s %s %s",
                moduleName.getDisplayName(),
                type,
                dependsOnModule.getDisplayName());
    }
}