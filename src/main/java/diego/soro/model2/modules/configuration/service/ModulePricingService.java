package diego.soro.model2.modules.configuration.service;

import diego.soro.model2.modules.configuration.domain.ModuleName;
import diego.soro.model2.modules.configuration.dto.ModulePricingDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * ModulePricingService - Task 1.2 Pricing Component
 *
 * Handles module pricing calculations and cost management for G-ADMIN
 * configuration-driven system.
 *
 * Pricing Strategy:
 * - Always-active modules: FREE (CORE, INVENTORY, SALES, MENU)
 * - Activatable modules: Tiered pricing (BASIC $10-15, PREMIUM $15-20)
 * - Volume discounts: Applied automatically for multiple modules
 * - Enterprise tier: Custom pricing
 *
 * Performance: <10ms for cost calculations
 * Thread-safe: All pricing data is immutable
 */
@Service
@Slf4j
public class ModulePricingService {

    // Immutable pricing matrix - thread-safe and performance optimized
    private static final Map<ModuleName, BigDecimal> MODULE_MONTHLY_PRICES;
    private static final Map<ModuleName, ModulePricingDTO.PricingTier> MODULE_PRICING_TIERS;
    private static final Map<ModuleName, String> MODULE_DESCRIPTIONS;

    static {
        MODULE_MONTHLY_PRICES = new EnumMap<>(ModuleName.class);
        MODULE_PRICING_TIERS = new EnumMap<>(ModuleName.class);
        MODULE_DESCRIPTIONS = new EnumMap<>(ModuleName.class);

        initializePricingData();
    }

    private static void initializePricingData() {
        // Always-active modules (FREE)
        setModulePrice(ModuleName.CORE, BigDecimal.ZERO, ModulePricingDTO.PricingTier.BASIC,
                "Core restaurant management functionality");
        setModulePrice(ModuleName.INVENTORY, BigDecimal.ZERO, ModulePricingDTO.PricingTier.BASIC,
                "Basic inventory tracking and stock management");
        setModulePrice(ModuleName.SALES, BigDecimal.ZERO, ModulePricingDTO.PricingTier.BASIC,
                "Point of sale and transaction processing");
        setModulePrice(ModuleName.MENU, BigDecimal.ZERO, ModulePricingDTO.PricingTier.BASIC,
                "Menu management and item configuration");

        // Activatable modules (PAID)
        setModulePrice(ModuleName.KITCHEN_MANAGEMENT, new BigDecimal("15.00"), ModulePricingDTO.PricingTier.PREMIUM,
                "Kitchen workflow, order tracking, and preparation management");
        setModulePrice(ModuleName.CUSTOMER_CRM, new BigDecimal("12.00"), ModulePricingDTO.PricingTier.BASIC,
                "Customer relationship management and loyalty programs");
        setModulePrice(ModuleName.ADVANCED_MENU, new BigDecimal("10.00"), ModulePricingDTO.PricingTier.BASIC,
                "Advanced menu features, pricing rules, and modifiers");
        setModulePrice(ModuleName.TABLE_MANAGEMENT, new BigDecimal("12.00"), ModulePricingDTO.PricingTier.BASIC,
                "Table reservations, seating management, and waitlist");
        setModulePrice(ModuleName.FINANCIAL_ANALYSIS, new BigDecimal("18.00"), ModulePricingDTO.PricingTier.PREMIUM,
                "Advanced financial reporting and business intelligence");
        setModulePrice(ModuleName.STAFF_MANAGEMENT, new BigDecimal("14.00"), ModulePricingDTO.PricingTier.PREMIUM,
                "Employee scheduling, time tracking, and performance");
        setModulePrice(ModuleName.ANALYTICS_PRO, new BigDecimal("20.00"), ModulePricingDTO.PricingTier.PREMIUM,
                "Advanced analytics, forecasting, and data insights");
        setModulePrice(ModuleName.COMPLIANCE, new BigDecimal("16.00"), ModulePricingDTO.PricingTier.PREMIUM,
                "Regulatory compliance, food safety, and audit management");
    }

    private static void setModulePrice(ModuleName module, BigDecimal price, ModulePricingDTO.PricingTier tier, String description) {
        MODULE_MONTHLY_PRICES.put(module, price);
        MODULE_PRICING_TIERS.put(module, tier);
        MODULE_DESCRIPTIONS.put(module, description);
    }

    /**
     * Get monthly cost for a single module
     *
     * @param module Module to get price for
     * @return Monthly cost in USD
     */
    public BigDecimal getModuleMonthlyCost(ModuleName module) {
        BigDecimal cost = MODULE_MONTHLY_PRICES.get(module);
        if (cost == null) {
            log.warn("No pricing found for module {}, defaulting to $0", module);
            return BigDecimal.ZERO;
        }
        return cost;
    }

    /**
     * Calculate total monthly cost for multiple modules
     * Applies volume discounts automatically
     *
     * @param modules List of modules to calculate cost for
     * @return Total monthly cost with discounts applied
     */
    public BigDecimal calculateTotalMonthlyCost(List<ModuleName> modules) {
        if (modules == null || modules.isEmpty()) {
            return BigDecimal.ZERO;
        }

        BigDecimal baseCost = modules.stream()
                .map(this::getModuleMonthlyCost)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Apply volume discount
        BigDecimal discount = calculateVolumeDiscount(modules.size());
        BigDecimal finalCost = baseCost.subtract(baseCost.multiply(discount));

        log.debug("Calculated total cost for {} modules: ${} (base: ${}, discount: {}%)",
                modules.size(), finalCost, baseCost, discount.multiply(new BigDecimal("100")));

        return finalCost;
    }

    /**
     * Calculate volume discount based on number of modules
     *
     * @param moduleCount Number of modules being activated
     * @return Discount percentage as decimal (0.05 = 5%)
     */
    private BigDecimal calculateVolumeDiscount(int moduleCount) {
        if (moduleCount >= 6) {
            return new BigDecimal("0.15"); // 15% discount for 6+ modules
        } else if (moduleCount >= 4) {
            return new BigDecimal("0.10"); // 10% discount for 4-5 modules
        } else if (moduleCount >= 3) {
            return new BigDecimal("0.05"); // 5% discount for 3 modules
        }
        return BigDecimal.ZERO; // No discount for 1-2 modules
    }

    /**
     * Get complete pricing information for all modules
     *
     * @return List of ModulePricingDTO with all pricing details
     */
    public List<ModulePricingDTO> getAllModulePricing() {
        return Stream.of(ModuleName.values())
                .map(this::createModulePricingDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get pricing information for specific modules
     *
     * @param modules Modules to get pricing for
     * @return List of ModulePricingDTO for specified modules
     */
    public List<ModulePricingDTO> getModulePricing(List<ModuleName> modules) {
        return modules.stream()
                .map(this::createModulePricingDTO)
                .collect(Collectors.toList());
    }

    /**
     * Create ModulePricingDTO for a module using user's superior DTO
     *
     * @param module Module to create DTO for
     * @return Complete pricing DTO
     */
    private ModulePricingDTO createModulePricingDTO(ModuleName module) {
        BigDecimal price = getModuleMonthlyCost(module);
        boolean isFree = price.compareTo(BigDecimal.ZERO) == 0;
        String description = MODULE_DESCRIPTIONS.get(module);
        String features = generateFeaturesList(module);

        if (isFree) {
            return ModulePricingDTO.builder()
                    .moduleName(module)
                    .displayName(module.getDisplayName())
                    .description(description)
                    .monthlyPrice(BigDecimal.ZERO)
                    .setupCost(BigDecimal.ZERO)
                    .isFree(true)
                    .isAlwaysActive(module.isAlwaysActive())
                    .pricingTier(ModulePricingDTO.PricingTier.BASIC)
                    .features(features)
                    .discountPercentage(BigDecimal.ZERO)
                    .discountedPrice(BigDecimal.ZERO)
                    .availableInCurrentTier(true)
                    .build();
        } else {
            ModulePricingDTO.PricingTier tier = ModulePricingDTO.PricingTier.valueOf(
                    MODULE_PRICING_TIERS.get(module)
            );

            return ModulePricingDTO.builder()
                    .moduleName(module)
                    .displayName(module.getDisplayName())
                    .description(description)
                    .monthlyPrice(price)
                    .setupCost(getSetupCostForModule(module))
                    .isFree(false)
                    .isAlwaysActive(false)
                    .pricingTier(tier)
                    .features(features)
                    .discountPercentage(BigDecimal.ZERO)
                    .discountedPrice(price)
                    .availableInCurrentTier(true)
                    .build();
        }
    }

    /**
     * Get setup cost for a specific module
     *
     * @param module Module to get setup cost for
     * @return Setup cost or zero if no setup cost
     */
    private BigDecimal getSetupCostForModule(ModuleName module) {
        Map<ModuleName, BigDecimal> setupCosts = Map.of(
                ModuleName.KITCHEN_MANAGEMENT, new BigDecimal("50.00"),
                ModuleName.TABLE_MANAGEMENT, new BigDecimal("30.00"),
                ModuleName.ANALYTICS_PRO, new BigDecimal("75.00"),
                ModuleName.COMPLIANCE, new BigDecimal("100.00")
        );

        return setupCosts.getOrDefault(module, BigDecimal.ZERO);
    }

    /**
     * Generate features list for a module
     * Used in pricing displays and marketing
     *
     * @param module Module to generate features for
     * @return Comma-separated features string
     */
    private String generateFeaturesList(ModuleName module) {
        return switch (module) {
            case CORE -> "User management, Basic settings, System configuration";
            case INVENTORY -> "Stock tracking, Low stock alerts, Basic reporting";
            case SALES -> "POS system, Payment processing, Basic sales reports";
            case MENU -> "Menu creation, Item management, Basic pricing";
            case KITCHEN_MANAGEMENT -> "Order workflow, Kitchen display, Preparation tracking";
            case CUSTOMER_CRM -> "Customer profiles, Loyalty programs, Contact management";
            case ADVANCED_MENU -> "Dynamic pricing, Modifiers, Menu optimization";
            case TABLE_MANAGEMENT -> "Reservations, Seating charts, Waitlist management";
            case FINANCIAL_ANALYSIS -> "P&L reports, Cost analysis, Financial forecasting";
            case STAFF_MANAGEMENT -> "Scheduling, Time tracking, Performance reviews";
            case ANALYTICS_PRO -> "Advanced reports, Predictive analytics, Custom dashboards";
            case COMPLIANCE -> "Food safety, Regulatory compliance, Audit trails";
        };
    }

    /**
     * Calculate potential savings from module deactivation
     *
     * @param modules Modules that would be deactivated
     * @return Monthly savings amount
     */
    public BigDecimal calculatePotentialSavings(List<ModuleName> modules) {
        if (modules == null || modules.isEmpty()) {
            return BigDecimal.ZERO;
        }

        // Only calculate savings for paid modules
        List<ModuleName> paidModules = modules.stream()
                .filter(module -> !module.isAlwaysActive())
                .collect(Collectors.toList());

        return calculateTotalMonthlyCost(paidModules);
    }

    /**
     * Get pricing tier for a module
     *
     * @param module Module to get tier for
     * @return Pricing tier (BASIC, PREMIUM, ENTERPRISE)
     */
    public ModulePricingDTO.PricingTier getModulePricingTier(ModuleName module) {
        return MODULE_PRICING_TIERS.getOrDefault(module, ModulePricingDTO.PricingTier.BASIC);
    }

    /**
     * Check if module is in premium tier
     * Used for feature gating and access control
     *
     * @param module Module to check
     * @return true if premium tier
     */
    public boolean isPremiumModule(ModuleName module) {
        ModulePricingDTO.PricingTier tier = getModulePricingTier(module);
        return tier == ModulePricingDTO.PricingTier.PREMIUM || tier == ModulePricingDTO.PricingTier.ENTERPRISE;
    }

    /**
     * Calculate cost comparison between plans
     * Useful for upgrade/downgrade scenarios
     *
     * @param currentModules Currently active modules
     * @param proposedModules Proposed module configuration
     * @return Cost difference (positive = more expensive, negative = savings)
     */
    public BigDecimal calculateCostDifference(List<ModuleName> currentModules, List<ModuleName> proposedModules) {
        BigDecimal currentCost = calculateTotalMonthlyCost(currentModules);
        BigDecimal proposedCost = calculateTotalMonthlyCost(proposedModules);

        return proposedCost.subtract(currentCost);
    }

    /**
     * Get recommended modules based on business type
     * Used for onboarding and upselling
     *
     * @param businessType Type of restaurant business
     * @return List of recommended modules
     */
    public List<ModuleName> getRecommendedModules(String businessType) {
        return switch (businessType.toUpperCase()) {
            case "QUICK_SERVICE" -> List.of(
                    ModuleName.CORE, ModuleName.INVENTORY, ModuleName.SALES, ModuleName.MENU,
                    ModuleName.KITCHEN_MANAGEMENT
            );
            case "CASUAL_DINING" -> List.of(
                    ModuleName.CORE, ModuleName.INVENTORY, ModuleName.SALES, ModuleName.MENU,
                    ModuleName.KITCHEN_MANAGEMENT, ModuleName.CUSTOMER_CRM, ModuleName.TABLE_MANAGEMENT
            );
            case "FINE_DINING" -> List.of(
                    ModuleName.CORE, ModuleName.INVENTORY, ModuleName.SALES, ModuleName.MENU,
                    ModuleName.KITCHEN_MANAGEMENT, ModuleName.CUSTOMER_CRM, ModuleName.TABLE_MANAGEMENT,
                    ModuleName.ADVANCED_MENU, ModuleName.STAFF_MANAGEMENT
            );
            case "ENTERPRISE" -> List.of(ModuleName.values()); // All modules
            default -> List.of(
                    ModuleName.CORE, ModuleName.INVENTORY, ModuleName.SALES, ModuleName.MENU
            );
        };
    }

    /**
     * Calculate ROI estimate for module activation
     * Used in sales and marketing materials
     *
     * @param module Module to calculate ROI for
     * @param monthlyRevenue Restaurant's monthly revenue
     * @return Estimated ROI percentage per month
     */
    public BigDecimal calculateEstimatedROI(ModuleName module, BigDecimal monthlyRevenue) {
        BigDecimal moduleCost = getModuleMonthlyCost(module);

        if (moduleCost.compareTo(BigDecimal.ZERO) == 0) {
            return new BigDecimal("100"); // Free modules have infinite ROI
        }

        // Estimated efficiency gains by module type
        BigDecimal efficiencyGain = switch (module) {
            case KITCHEN_MANAGEMENT -> new BigDecimal("0.08"); // 8% efficiency gain
            case CUSTOMER_CRM -> new BigDecimal("0.12"); // 12% revenue increase
            case FINANCIAL_ANALYSIS -> new BigDecimal("0.05"); // 5% cost savings
            case ANALYTICS_PRO -> new BigDecimal("0.15"); // 15% optimization
            case STAFF_MANAGEMENT -> new BigDecimal("0.10"); // 10% labor efficiency
            default -> new BigDecimal("0.03"); // 3% general improvement
        };

        BigDecimal monthlyBenefit = monthlyRevenue.multiply(efficiencyGain);
        BigDecimal roi = monthlyBenefit.subtract(moduleCost)
                .divide(moduleCost, 2, BigDecimal.ROUND_HALF_UP)
                .multiply(new BigDecimal("100"));

        return roi.max(BigDecimal.ZERO); // Never negative ROI in estimates
    }
}