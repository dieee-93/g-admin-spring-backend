package diego.soro.model2.modules.configuration.domain;

public enum ModuleName {

    // ========== ALWAYS ACTIVE MODULES ==========
    // Estos módulos están siempre activos y no requieren activación

    /**
     * Módulo core - Funcionalidad básica del sistema
     * Always active - Base para todos los demás módulos
     */
    CORE,

    /**
     * Módulo de inventario - Gestión de stock básica
     * Always active - Fundamental para operaciones de restaurante
     */
    INVENTORY,

    /**
     * Módulo de ventas - Gestión de transacciones
     * Always active - Core business functionality
     */
    SALES,

    /**
     * Módulo de menú - Gestión básica de productos
     * Always active - Esencial para cualquier restaurante
     */
    MENU,

    // ========== ACTIVATABLE MODULES ==========
    // Estos módulos pueden ser activados/desactivados por configuración

    /**
     * Gestión avanzada de cocina
     * Requiere: SALES, MENU
     * Funcionalidad: Orders workflow, kitchen display, prep times
     */
    KITCHEN_MANAGEMENT,

    /**
     * Sistema CRM de clientes
     * Requiere: SALES
     * Funcionalidad: Customer profiles, loyalty, history
     */
    CUSTOMER_CRM,

    /**
     * Menú avanzado con recetas
     * Requiere: MENU
     * Funcionalidad: Recipe management, modifiers, combos
     */
    ADVANCED_MENU,

    /**
     * Gestión de mesas y reservas
     * Requiere: CUSTOMER_CRM
     * Funcionalidad: Table assignments, reservations, floor plan
     */
    TABLE_MANAGEMENT,

    /**
     * Análisis financiero avanzado
     * Requiere: SALES, INVENTORY
     * Funcionalidad: P&L reports, cost analysis, forecasting
     */
    FINANCIAL_ANALYSIS,

    /**
     * Gestión de personal
     * Requiere: CORE
     * Funcionalidad: Employee management, scheduling, payroll
     */
    STAFF_MANAGEMENT,

    /**
     * Analytics profesional
     * Requiere: SALES, INVENTORY
     * Funcionalidad: Advanced reporting, trends, predictive analytics
     */
    ANALYTICS_PRO,

    /**
     * Compliance y regulaciones (Enterprise only)
     * Requiere: KITCHEN_MANAGEMENT, STAFF_MANAGEMENT
     * Funcionalidad: HACCP, food safety, labor law compliance
     */
    COMPLIANCE;

    /**
     * Verifica si el módulo está siempre activo
     * @return true si es un módulo core que no requiere activación
     */
    public boolean isAlwaysActive() {
        return this == CORE || this == INVENTORY || this == SALES || this == MENU;
    }

    /**
     * Verifica si el módulo es activatable
     * @return true si el módulo puede ser activado/desactivado
     */
    public boolean isActivatable() {
        return !isAlwaysActive();
    }

    /**
     * Verifica si el módulo requiere tier Enterprise
     * @return true si es exclusive para Enterprise tier
     */
    public boolean isEnterpriseOnly() {
        return this == COMPLIANCE;
    }

    /**
     * Obtiene el nombre display del módulo
     * @return nombre legible para UI
     */
    public String getDisplayName() {
        return switch (this) {
            case CORE -> "Core System";
            case INVENTORY -> "Inventory Management";
            case SALES -> "Sales Management";
            case MENU -> "Menu Management";
            case KITCHEN_MANAGEMENT -> "Kitchen Management";
            case CUSTOMER_CRM -> "Customer CRM";
            case ADVANCED_MENU -> "Advanced Menu";
            case TABLE_MANAGEMENT -> "Table Management";
            case FINANCIAL_ANALYSIS -> "Financial Analysis";
            case STAFF_MANAGEMENT -> "Staff Management";
            case ANALYTICS_PRO -> "Analytics Pro";
            case COMPLIANCE -> "Compliance & Regulations";
        };
    }
}