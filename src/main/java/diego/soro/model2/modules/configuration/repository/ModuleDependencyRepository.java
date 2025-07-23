package diego.soro.model2.modules.configuration.repository;


import diego.soro.model2.modules.configuration.domain.ModuleDependency;
import diego.soro.model2.modules.configuration.domain.ModuleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository para gestión de dependencias entre módulos
 *
 * Proporciona métodos optimizados para consultar la matriz de dependencias
 * y resolver activaciones de módulos.
 *
 * @author G-ADMIN Development Team
 * @since Task 1.2 - Module Dependency Management System
 */
@Repository
public interface ModuleDependencyRepository extends JpaRepository<ModuleDependency, UUID> {

    /**
     * Encuentra todas las dependencias de un módulo específico
     *
     * @param moduleName el módulo para el cual buscar dependencias
     * @return lista de dependencias (tanto requeridas como opcionales)
     */
    List<ModuleDependency> findByModuleName(ModuleName moduleName);

    /**
     * Encuentra todos los módulos que dependen de un módulo específico
     * Útil para validar desactivaciones (cascading effects)
     *
     * @param dependsOnModule el módulo del cual otros dependen
     * @return lista de módulos que serían afectados por desactivación
     */
    List<ModuleDependency> findByDependsOnModule(ModuleName dependsOnModule);

    /**
     * Encuentra SOLO las dependencias requeridas de un módulo
     * Esta es la query más importante para validación de activación
     *
     * @param module el módulo para el cual buscar dependencias obligatorias
     * @return lista de dependencias que DEBEN estar activas
     */
    @Query("SELECT md FROM ModuleDependency md WHERE md.moduleName = :module AND md.isRequired = true")
    List<ModuleDependency> findRequiredDependencies(@Param("module") ModuleName module);

    /**
     * Encuentra dependencias opcionales de un módulo
     * Para features que mejoran funcionalidad pero no son obligatorias
     *
     * @param module el módulo para el cual buscar dependencias opcionales
     * @return lista de dependencias que mejorarían funcionalidad
     */
    @Query("SELECT md FROM ModuleDependency md WHERE md.moduleName = :module AND md.isRequired = false")
    List<ModuleDependency> findOptionalDependencies(@Param("module") ModuleName module);

    /**
     * Encuentra módulos que requieren obligatoriamente un módulo específico
     * Útil para warnings al desactivar módulos
     *
     * @param dependsOnModule el módulo que se quiere desactivar
     * @return lista de módulos que se romperían sin esta dependencia
     */
    @Query("SELECT md FROM ModuleDependency md WHERE md.dependsOnModule = :dependsOnModule AND md.isRequired = true")
    List<ModuleDependency> findRequiredBy(@Param("dependsOnModule") ModuleName dependsOnModule);

    /**
     * Verifica si existe una dependencia específica entre dos módulos
     *
     * @param moduleName el módulo que tiene la dependencia
     * @param dependsOnModule el módulo requerido
     * @return Optional con la dependencia si existe
     */
    Optional<ModuleDependency> findByModuleNameAndDependsOnModule(
            ModuleName moduleName,
            ModuleName dependsOnModule
    );

    /**
     * Verifica si un módulo requiere obligatoriamente otro módulo
     * Método de conveniencia para validaciones rápidas
     *
     * @param moduleName el módulo que tiene la dependencia
     * @param dependsOnModule el módulo potencialmente requerido
     * @return true si existe dependencia requerida
     */
    @Query("SELECT COUNT(md) > 0 FROM ModuleDependency md WHERE md.moduleName = :moduleName AND md.dependsOnModule = :dependsOnModule AND md.isRequired = true")
    boolean existsRequiredDependency(
            @Param("moduleName") ModuleName moduleName,
            @Param("dependsOnModule") ModuleName dependsOnModule
    );

    /**
     * Obtiene todas las dependencias del sistema para análisis
     * Útil para debugging y dashboards administrativos
     *
     * @return matriz completa de dependencias
     */
    @Query("SELECT md FROM ModuleDependency md ORDER BY md.moduleName, md.dependsOnModule")
    List<ModuleDependency> findAllDependenciesOrdered();

    /**
     * Cuenta dependencias requeridas para un módulo
     * Útil para métricas y validaciones rápidas
     *
     * @param moduleName el módulo a analizar
     * @return número de dependencias obligatorias
     */
    @Query("SELECT COUNT(md) FROM ModuleDependency md WHERE md.moduleName = :moduleName AND md.isRequired = true")
    long countRequiredDependencies(@Param("moduleName") ModuleName moduleName);

    /**
     * Encuentra dependencias circulares (debugging)
     * Esta query ayuda a detectar problemas en la matriz de dependencias
     *
     * @param moduleName módulo a verificar
     * @return dependencias que podrían crear ciclos
     */
    @Query("""
        SELECT md FROM ModuleDependency md 
        WHERE md.moduleName = :moduleName 
        AND EXISTS (
            SELECT md2 FROM ModuleDependency md2 
            WHERE md2.moduleName = md.dependsOnModule 
            AND md2.dependsOnModule = :moduleName
        )
        """)
    List<ModuleDependency> findCircularDependencies(@Param("moduleName") ModuleName moduleName);
}
