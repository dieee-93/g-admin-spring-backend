package diego.soro.model2.core.configuration;

import diego.soro.model2.core.BaseEntity.BaseEntity;
import diego.soro.model2.core.Company.Company;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

/**
 * Company Configuration entity for G-ADMIN SaaS platform
 *
 * Manages module activation/deactivation per company following
 * the configuration-driven architecture pattern.
 *
 * Design Decisions:
 * - @OneToOne with Company (1 configuration per company)
 * - Boolean fields for type safety and performance
 * - Core modules always enabled (business requirement)
 * - Activable modules for pay-per-module model
 *
 * @see diego.soro.model2.core.Company.Company
 * @see SubscriptionTier
 */
@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "company_configuration",
        uniqueConstraints = @UniqueConstraint(columnNames = "company_id")
)
public class CompanyConfiguration extends BaseEntity {

    // ===== COMPANY RELATIONSHIP =====

    @OneToOne(optional = false)
    @JoinColumn(name = "company_id", unique = true, nullable = false)
    private Company company;

    // ===== SUBSCRIPTION TIER =====

    /**
     * Current subscription tier for feature toggle decisions
     * Synced from CompanySubscription entity for performance
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SubscriptionTier tier = SubscriptionTier.LITE;

    // ===== CORE MODULES (Always Enabled) =====

    /**
     * Core module: User management, roles, permissions
     * Always enabled - required for basic system operation
     */
    @Column(nullable = false)
    private Boolean coreEnabled = true;

    /**
     * Inventory module: Stock management, products, suppliers
     * Always enabled - core business functionality
     */
    @Column(nullable = false)
    private Boolean inventoryEnabled = true;

    /**
     * Sales/POS module: Order management, payments
     * Always enabled - core business functionality
     */
    @Column(nullable = false)
    private Boolean salesEnabled = true;

    /**
     * Basic menu module: Simple menu management
     * Always enabled - required for restaurant operations
     */
    @Column(nullable = false)
    private Boolean menuEnabled = true;

    // ===== ACTIVABLE MODULES (Pay-per-Module) =====

    /**
     * Advanced menu module: Dynamic pricing, analytics, nutritional info
     * Activable module - requires menu base module
     */
    @Column(nullable = false)
    private Boolean menuAdvancedEnabled = false;

    /**
     * Kitchen management module: Recipe costing, station optimization
     * Activable module - requires sales + menu modules
     */
    @Column(nullable = false)
    private Boolean kitchenEnabled = false;

    /**
     * Customer management module: CRM, loyalty programs
     * Activable module - requires sales module
     */
    @Column(nullable = false)
    private Boolean customerEnabled = false;

    /**
     * Table management module: Reservations, waitlist optimization
     * Activable module - requires customer module
     */
    @Column(nullable = false)
    private Boolean tableEnabled = false;

    /**
     * Financial management module: AFIP integration, cost analysis
     * Activable module - requires sales + inventory modules
     */
    @Column(nullable = false)
    private Boolean financialEnabled = false;

    /**
     * Staff management module: Scheduling, time tracking, performance
     * Activable module - requires core module only
     */
    @Column(nullable = false)
    private Boolean staffEnabled = false;

    /**
     * Analytics module: Advanced reporting, ML insights, predictions
     * Activable module - requires sales + inventory modules
     */
    @Column(nullable = false)
    private Boolean analyticsEnabled = false;

    /**
     * Notifications module: WhatsApp, SMS, email automation
     * Activable module - requires customer module
     */
    @Column(nullable = false)
    private Boolean notificationsEnabled = false;

    /**
     * Compliance module: HACCP, auditing, temperature monitoring
     * Enterprise-only module - requires kitchen + staff modules
     */
    @Column(nullable = false)
    private Boolean complianceEnabled = false;

    // ===== CONFIGURATION METADATA =====

    /**
     * Last modification timestamp for configuration changes
     * Used for auditing and sync with billing system
     */
    @Column
    private LocalDateTime lastConfigurationChange;

    /**
     * User who made the last configuration change
     * Used for auditing and support purposes
     */
    @Column
    private String lastModifiedBy;

    /**
     * Configuration notes or reason for changes
     * Optional field for administrative purposes
     */
    @Column(length = 500)
    private String configurationNotes;

    // ===== HELPER METHODS =====

    /**
     * Updates configuration metadata when changes are made
     */
    @PreUpdate
    @PrePersist
    protected void updateConfigurationMetadata() {
        this.lastConfigurationChange = LocalDateTime.now();
        // lastModifiedBy will be set by audit system via @LastModifiedBy
    }

    /**
     * Checks if a module is enabled by name
     * Useful for dynamic module checking
     */
    public boolean isModuleEnabled(String moduleName) {
        return switch (moduleName.toLowerCase()) {
            case "core" -> coreEnabled;
            case "inventory" -> inventoryEnabled;
            case "sales" -> salesEnabled;
            case "menu" -> menuEnabled;
            case "menu-advanced", "menuadvanced" -> menuAdvancedEnabled;
            case "kitchen" -> kitchenEnabled;
            case "customer" -> customerEnabled;
            case "table" -> tableEnabled;
            case "financial" -> financialEnabled;
            case "staff" -> staffEnabled;
            case "analytics" -> analyticsEnabled;
            case "notifications" -> notificationsEnabled;
            case "compliance" -> complianceEnabled;
            default -> false;
        };
    }

    /**
     * Gets the number of active modules (excluding core modules)
     * Used for billing calculations
     */
    public int getActiveModuleCount() {
        int count = 0;
        if (menuAdvancedEnabled) count++;
        if (kitchenEnabled) count++;
        if (customerEnabled) count++;
        if (tableEnabled) count++;
        if (financialEnabled) count++;
        if (staffEnabled) count++;
        if (analyticsEnabled) count++;
        if (notificationsEnabled) count++;
        if (complianceEnabled) count++;
        return count;
    }
}