package diego.soro.model2.modules.configuration.dto;

import diego.soro.model2.modules.configuration.domain.ModuleName;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * Información de precios de módulos *
 * Contiene toda la información de pricing para un módulo específico,
 * incluyendo precios, características, y metadata de facturación.
 *
 * @author G-ADMIN Development Team
 * @since Task 1.2 - Module Dependency Management System
 */
@Getter
@Builder
@ToString
public class ModulePricingDTO {

    /**
     * El módulo al que aplica este pricing
     */
    private final ModuleName moduleName;

    /**
     * Nombre amigable para mostrar en UI
     */
    private final String displayName;

    /**
     * Descripción detallada del módulo
     */
    private final String description;

    /**
     * Precio mensual en USD
     */
    private final BigDecimal monthlyPrice;

    /**
     * Costo de setup único (si aplica)
     */
    private final BigDecimal setupCost;

    /**
     * Indica si el módulo es gratuito
     */
    private final boolean isFree;

    /**
     * Indica si es un módulo siempre activo
     */
    private final boolean isAlwaysActive;

    /**
     * Tier de pricing (BASIC, PREMIUM, ENTERPRISE)
     */
    private final PricingTier pricingTier;

    /**
     * Lista de características principales del módulo
     */
    private final String features;

    /**
     * Descuento aplicable (si hay promociones activas)
     */
    private final BigDecimal discountPercentage;

    /**
     * Precio con descuento aplicado
     */
    private final BigDecimal discountedPrice;

    /**
     * Indica si está disponible en el tier actual del cliente
     */
    private final boolean availableInCurrentTier;

    /**
     * Tiers de pricing disponibles
     */
    public enum PricingTier {
        BASIC("Basic tier - functionality essential"),
        PREMIUM("Premium tier - advanced features"),
        ENTERPRISE("Enterprise tier - full functionality");

        private final String description;

        PricingTier(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }

    /**
     * Factory method para módulo gratuito
     */
    public static ModulePricingDTO free(ModuleName module, String displayName, String description) {
        return ModulePricingDTO.builder()
                .moduleName(module)
                .displayName(displayName)
                .description(description)
                .monthlyPrice(BigDecimal.ZERO)
                .setupCost(BigDecimal.ZERO)
                .isFree(true)
                .isAlwaysActive(module.isAlwaysActive())
                .pricingTier(PricingTier.BASIC)
                .discountPercentage(BigDecimal.ZERO)
                .discountedPrice(BigDecimal.ZERO)
                .availableInCurrentTier(true)
                .build();
    }

    /**
     * Factory method para módulo pago
     */
    public static ModulePricingDTO paid(
            ModuleName module,
            String displayName,
            BigDecimal monthlyPrice,
            PricingTier tier) {
        return ModulePricingDTO.builder()
                .moduleName(module)
                .displayName(displayName)
                .monthlyPrice(monthlyPrice)
                .setupCost(BigDecimal.ZERO)
                .isFree(false)
                .isAlwaysActive(false)
                .pricingTier(tier)
                .discountPercentage(BigDecimal.ZERO)
                .discountedPrice(monthlyPrice)
                .availableInCurrentTier(true)
                .build();
    }

    /**
     * Obtiene el precio efectivo (con descuento si aplica)
     */
    public BigDecimal getEffectivePrice() {
        return discountedPrice != null ? discountedPrice : monthlyPrice;
    }

    /**
     * Verifica si tiene descuento aplicado
     */
    public boolean hasDiscount() {
        return discountPercentage != null &&
                discountPercentage.compareTo(BigDecimal.ZERO) > 0;
    }

    /**
     * Verifica si es un módulo premium
     */
    public boolean isPremiumTier() {
        return pricingTier == PricingTier.PREMIUM || pricingTier == PricingTier.ENTERPRISE;
    }

    /**
     * Calcula ahorro mensual si hay descuento
     */
    public BigDecimal getMonthlySavings() {
        if (!hasDiscount()) {
            return BigDecimal.ZERO;
        }
        return monthlyPrice.subtract(discountedPrice);
    }
}
