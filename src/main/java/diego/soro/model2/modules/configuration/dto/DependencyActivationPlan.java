package diego.soro.model2.modules.configuration.dto;

import diego.soro.model2.modules.configuration.domain.ModuleName;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Plan de activación de módulos con dependencias en cascada
 *
 * Contiene el plan completo para activar un módulo, incluyendo todas las
 * dependencias que deben ser activadas, el orden de activación, y los costos
 * asociados.
 *
 * @author G-ADMIN Development Team
 * @since Task 1.2 - Module Dependency Management System
 */
@Getter
@Builder
@ToString
public class DependencyActivationPlan {

    /**
     * El módulo objetivo que el usuario quiere activar
     */
    private final ModuleName targetModule;

    /**
     * Lista ordenada de módulos que deben ser activados antes del módulo objetivo
     * El orden importa para evitar violaciones de dependencias durante activación
     */
    private final List<ModuleName> requiredActivations;

    /**
     * Lista de módulos que ya están activos y satisfacen dependencias
     * Útil para mostrar al usuario qué dependencias ya están cubiertas
     */
    private final List<ModuleName> alreadyActive;

    /**
     * Costo mensual total de activar todos los módulos requeridos
     * Incluye el módulo objetivo + todas las dependencias faltantes
     */
    private final BigDecimal totalMonthlyCost;

    /**
     * Costo de setup único (si aplica)
     * Algunos módulos pueden tener costos de configuración inicial
     */
    private final BigDecimal setupCost;

    /**
     * Indica si requiere confirmación del usuario antes de proceder
     * true = activación en cascada, usuario debe confirmar costos
     * false = activación directa sin dependencias adicionales
     */
    private final boolean requiresConfirmation;

    /**
     * Tipo de plan de activación
     */
    private final ActivationType activationType;

    /**
     * Mensaje descriptivo del plan para mostrar al usuario
     */
    private final String description;

    /**
     * Estimación de tiempo de activación en minutos
     */
    private final Integer estimatedActivationTimeMinutes;

    /**
     * Tipos de activación disponibles
     */
    public enum ActivationType {
        DIRECT("Direct activation - no dependencies needed"),
        CASCADE("Cascade activation - dependencies will be activated"),
        BLOCKED("Activation blocked - dependencies not available in current tier");

        private final String description;

        ActivationType(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }

    /**
     * Factory method para activación directa sin dependencias
     * @param module el módulo que puede ser activado directamente
     * @return plan para activación inmediata
     */
    public static DependencyActivationPlan directActivation(ModuleName module) {
        return DependencyActivationPlan.builder()
                .targetModule(module)
                .requiredActivations(List.of())
                .alreadyActive(List.of())
                .totalMonthlyCost(BigDecimal.ZERO) // Solo costo del módulo objetivo
                .setupCost(BigDecimal.ZERO)
                .requiresConfirmation(false)
                .activationType(ActivationType.DIRECT)
                .description(String.format("Ready to activate %s immediately", module.getDisplayName()))
                .estimatedActivationTimeMinutes(1)
                .build();
    }

    /**
     * Factory method para activación en cascada con dependencias
     * @param targetModule el módulo objetivo
     * @param requiredActivations lista de módulos que deben activarse primero
     * @param totalCost costo mensual total
     * @return plan de activación en cascada
     */
    public static DependencyActivationPlan cascadeActivation(
            ModuleName targetModule,
            List<ModuleName> requiredActivations,
            BigDecimal totalCost) {

        String dependencyNames = requiredActivations.stream()
                .map(ModuleName::getDisplayName)
                .collect(Collectors.joining(", "));

        String description = String.format(
                "To activate %s, we need to first activate: %s. Total monthly cost: $%.2f",
                targetModule.getDisplayName(),
                dependencyNames,
                totalCost
        );

        return DependencyActivationPlan.builder()
                .targetModule(targetModule)
                .requiredActivations(requiredActivations)
                .alreadyActive(List.of())
                .totalMonthlyCost(totalCost)
                .setupCost(BigDecimal.ZERO)
                .requiresConfirmation(true)
                .activationType(ActivationType.CASCADE)
                .description(description)
                .estimatedActivationTimeMinutes(requiredActivations.size() * 2 + 1)
                .build();
    }

    /**
     * Factory method para activación bloqueada
     * @param module el módulo que no puede ser activado
     * @param reason razón del bloqueo
     * @return plan indicando bloqueo
     */
    public static DependencyActivationPlan blocked(ModuleName module, String reason) {
        return DependencyActivationPlan.builder()
                .targetModule(module)
                .requiredActivations(List.of())
                .alreadyActive(List.of())
                .totalMonthlyCost(BigDecimal.ZERO)
                .setupCost(BigDecimal.ZERO)
                .requiresConfirmation(false)
                .activationType(ActivationType.BLOCKED)
                .description(String.format("Cannot activate %s: %s", module.getDisplayName(), reason))
                .estimatedActivationTimeMinutes(0)
                .build();
    }

    /**
     * Verifica si es una activación simple sin dependencias
     * @return true si no requiere activaciones adicionales
     */
    public boolean isDirectActivation() {
        return activationType == ActivationType.DIRECT;
    }

    /**
     * Verifica si requiere activaciones en cascada
     * @return true si hay dependencias que activar
     */
    public boolean isCascadeActivation() {
        return activationType == ActivationType.CASCADE;
    }

    /**
     * Verifica si la activación está bloqueada
     * @return true si no puede proceder
     */
    public boolean isBlocked() {
        return activationType == ActivationType.BLOCKED;
    }

    /**
     * Obtiene el número total de módulos que serán activados
     * @return count incluyendo módulo objetivo + dependencias
     */
    public int getTotalModulesCount() {
        return requiredActivations.size() + 1; // +1 for target module
    }

    /**
     * Obtiene costo total incluyendo setup
     * @return suma de costo mensual + setup
     */
    public BigDecimal getTotalCost() {
        return totalMonthlyCost.add(setupCost != null ? setupCost : BigDecimal.ZERO);
    }

    /**
     * Verifica si tiene costos asociados
     * @return true si hay costos adicionales
     */
    public boolean hasCosts() {
        return totalMonthlyCost.compareTo(BigDecimal.ZERO) > 0 ||
                (setupCost != null && setupCost.compareTo(BigDecimal.ZERO) > 0);
    }

    /**
     * Obtiene lista de todos los módulos que serán activados
     * @return lista completa incluyendo dependencias + módulo objetivo
     */
    public List<ModuleName> getAllModulesInPlan() {
        return Stream.concat(
                requiredActivations.stream(),
                Stream.of(targetModule)
        ).collect(Collectors.toList());
    }
}