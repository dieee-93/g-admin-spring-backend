package diego.soro.model2.modules.configuration.dto;

import diego.soro.model2.modules.configuration.domain.ModuleDependency;
import diego.soro.model2.modules.configuration.domain.ModuleName;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Resultado de validación de dependencias de módulos
 *
 * Contiene toda la información necesaria para determinar si un módulo
 * puede ser activado y qué dependencias faltan si no es posible.
 *
 * @author G-ADMIN Development Team
 * @since Task 1.2 - Module Dependency Management System
 */
@Getter
@Builder
@ToString
public class DependencyValidationResult {

    /**
     * Indica si el módulo puede ser activado directamente
     * true = todas las dependencias están satisfechas
     * false = faltan dependencias requeridas
     */
    private final boolean canActivate;

    /**
     * Lista de módulos que faltan para poder activar el módulo objetivo
     * Solo incluye dependencias requeridas, no opcionales
     */
    private final List<ModuleName> missingDependencies;

    /**
     * Lista completa de dependencias requeridas para el módulo
     * Incluye tanto las que están satisfechas como las que faltan
     */
    private final List<ModuleDependency> requiredDependencies;

    /**
     * Lista de dependencias opcionales que mejorarían funcionalidad
     * No bloquean activación pero proporcionan features adicionales
     */
    private final List<ModuleDependency> optionalDependencies;

    /**
     * Mensaje descriptivo del estado de validación
     * Útil para mostrar al usuario o logs
     */
    private final String message;

    /**
     * Factory method para crear resultado de activación exitosa
     * @param module el módulo que puede ser activado
     * @return resultado indicando activación directa posible
     */
    public static DependencyValidationResult canActivate(ModuleName module) {
        return DependencyValidationResult.builder()
                .canActivate(true)
                .missingDependencies(List.of())
                .requiredDependencies(List.of())
                .optionalDependencies(List.of())
                .message(String.format("Module %s can be activated directly", module.getDisplayName()))
                .build();
    }

    /**
     * Factory method para crear resultado con dependencias faltantes
     * @param module el módulo que no puede ser activado
     * @param missingDependencies lista de dependencias que faltan
     * @param requiredDependencies lista completa de dependencias requeridas
     * @return resultado indicando qué falta para activación
     */
    public static DependencyValidationResult missingDependencies(
            ModuleName module,
            List<ModuleName> missingDependencies,
            List<ModuleDependency> requiredDependencies) {

        String dependencyNames = missingDependencies.stream()
                .map(ModuleName::getDisplayName)
                .collect(Collectors.joining(", "));

        String message = String.format(
                "Cannot activate %s. Missing required dependencies: %s",
                module.getDisplayName(),
                dependencyNames
        );

        return DependencyValidationResult.builder()
                .canActivate(false)
                .missingDependencies(missingDependencies)
                .requiredDependencies(requiredDependencies)
                .optionalDependencies(List.of())
                .message(message)
                .build();
    }

    /**
     * Verifica si hay dependencias faltantes
     * @return true si faltan dependencias requeridas
     */
    public boolean hasMissingDependencies() {
        return !missingDependencies.isEmpty();
    }

    /**
     * Obtiene count de dependencias faltantes
     * @return número de dependencias que faltan
     */
    public int getMissingDependenciesCount() {
        return missingDependencies.size();
    }

    /**
     * Verifica si hay dependencias opcionales disponibles
     * @return true si hay dependencias opcionales que podrían mejorar funcionalidad
     */
    public boolean hasOptionalDependencies() {
        return optionalDependencies != null && !optionalDependencies.isEmpty();
    }

    /**
     * Obtiene lista de nombres de dependencias faltantes para UI
     * @return nombres de display de módulos faltantes
     */
    public List<String> getMissingDependencyNames() {
        return missingDependencies.stream()
                .map(ModuleName::getDisplayName)
                .collect(Collectors.toList());
    }

    /**
     * Obtiene mensaje corto para notificaciones
     * @return mensaje conciso del estado
     */
    public String getShortMessage() {
        if (canActivate) {
            return "Ready to activate";
        }
        return String.format("Missing %d dependencies", getMissingDependenciesCount());
    }
}