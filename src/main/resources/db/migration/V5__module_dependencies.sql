-- ============================================================================
-- G-ADMIN Migration V5: Module Dependencies Management
-- Creates table and populates dependency matrix for module activation logic
--
-- Task: 1.2 Module Dependency Management System
-- Author: G-ADMIN Development Team
-- Date: January 2025
-- ============================================================================

-- Create module_dependencies table
CREATE TABLE module_dependencies (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    company_id UUID NOT NULL DEFAULT '00000000-0000-0000-0000-000000000001', -- System default
    module_name VARCHAR(50) NOT NULL,
    depends_on_module VARCHAR(50) NOT NULL,
    is_required BOOLEAN NOT NULL DEFAULT true,
    reason VARCHAR(500),

    -- Audit fields from BaseEntity pattern
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by UUID,
    updated_by UUID,
    version INTEGER NOT NULL DEFAULT 0,

    -- Branch support (for future multi-branch feature)
    branch_id UUID DEFAULT '00000000-0000-0000-0000-000000000001',

    -- Prevent duplicate dependencies
    CONSTRAINT uk_module_dependency UNIQUE (company_id, module_name, depends_on_module, branch_id),

    -- Prevent self-dependencies
    CONSTRAINT chk_no_self_dependency CHECK (module_name != depends_on_module),

    -- Validate module names (enum values)
    CONSTRAINT chk_valid_module_name CHECK (
        module_name IN (
            'CORE', 'INVENTORY', 'SALES', 'MENU',
            'KITCHEN_MANAGEMENT', 'CUSTOMER_CRM', 'ADVANCED_MENU',
            'TABLE_MANAGEMENT', 'FINANCIAL_ANALYSIS', 'STAFF_MANAGEMENT',
            'ANALYTICS_PRO', 'COMPLIANCE'
        )
    ),
    CONSTRAINT chk_valid_depends_on_module CHECK (
        depends_on_module IN (
            'CORE', 'INVENTORY', 'SALES', 'MENU',
            'KITCHEN_MANAGEMENT', 'CUSTOMER_CRM', 'ADVANCED_MENU',
            'TABLE_MANAGEMENT', 'FINANCIAL_ANALYSIS', 'STAFF_MANAGEMENT',
            'ANALYTICS_PRO', 'COMPLIANCE'
        )
    )
);

-- Create performance indexes
CREATE INDEX idx_module_dependencies_module_name ON module_dependencies(module_name);
CREATE INDEX idx_module_dependencies_depends_on ON module_dependencies(depends_on_module);
CREATE INDEX idx_module_dependencies_required ON module_dependencies(is_required);
CREATE INDEX idx_module_dependencies_company ON module_dependencies(company_id);
CREATE INDEX idx_module_dependencies_branch ON module_dependencies(branch_id);

-- Create composite index for most common query pattern
CREATE INDEX idx_module_dependencies_lookup ON module_dependencies(company_id, module_name, is_required);

-- ============================================================================
-- POPULATE DEPENDENCY MATRIX
-- Based on business analysis and natural module relationships
-- ============================================================================

-- NOTE: Always-active modules (CORE, INVENTORY, SALES, MENU) don't have dependencies
-- They are always available and don't require activation logic

-- ============================================================================
-- KITCHEN_MANAGEMENT Dependencies
-- Requires: SALES (orders to prepare), MENU (items to cook)
-- ============================================================================

INSERT INTO module_dependencies (
    company_id, module_name, depends_on_module, is_required, reason
) VALUES
(
    '00000000-0000-0000-0000-000000000001',
    'KITCHEN_MANAGEMENT',
    'SALES',
    true,
    'Kitchen needs sales orders to prepare and track'
),
(
    '00000000-0000-0000-0000-000000000001',
    'KITCHEN_MANAGEMENT',
    'MENU',
    true,
    'Kitchen needs menu items and recipes to prepare orders'
);

-- ============================================================================
-- CUSTOMER_CRM Dependencies
-- Requires: SALES (customer transaction history)
-- ============================================================================

INSERT INTO module_dependencies (
    company_id, module_name, depends_on_module, is_required, reason
) VALUES
(
    '00000000-0000-0000-0000-000000000001',
    'CUSTOMER_CRM',
    'SALES',
    true,
    'Customer profiles need sales transaction history and loyalty tracking'
);

-- ============================================================================
-- ADVANCED_MENU Dependencies
-- Requires: MENU (base menu functionality)
-- ============================================================================

INSERT INTO module_dependencies (
    company_id, module_name, depends_on_module, is_required, reason
) VALUES
(
    '00000000-0000-0000-0000-000000000001',
    'ADVANCED_MENU',
    'MENU',
    true,
    'Advanced menu features extend base menu functionality'
);

-- ============================================================================
-- TABLE_MANAGEMENT Dependencies
-- Requires: CUSTOMER_CRM (table assignments need customer data)
-- ============================================================================

INSERT INTO module_dependencies (
    company_id, module_name, depends_on_module, is_required, reason
) VALUES
(
    '00000000-0000-0000-0000-000000000001',
    'TABLE_MANAGEMENT',
    'CUSTOMER_CRM',
    true,
    'Table reservations and assignments require customer information'
);

-- ============================================================================
-- FINANCIAL_ANALYSIS Dependencies
-- Requires: SALES (revenue data), INVENTORY (cost data)
-- ============================================================================

INSERT INTO module_dependencies (
    company_id, module_name, depends_on_module, is_required, reason
) VALUES
(
    '00000000-0000-0000-0000-000000000001',
    'FINANCIAL_ANALYSIS',
    'SALES',
    true,
    'Financial reports need sales revenue data for P&L analysis'
),
(
    '00000000-0000-0000-0000-000000000001',
    'FINANCIAL_ANALYSIS',
    'INVENTORY',
    true,
    'Financial analysis needs inventory cost data for margin calculations'
);

-- ============================================================================
-- STAFF_MANAGEMENT Dependencies
-- Requires: CORE (basic user management and authentication)
-- ============================================================================

INSERT INTO module_dependencies (
    company_id, module_name, depends_on_module, is_required, reason
) VALUES
(
    '00000000-0000-0000-0000-000000000001',
    'STAFF_MANAGEMENT',
    'CORE',
    true,
    'Staff management extends core user management with employee features'
);

-- ============================================================================
-- ANALYTICS_PRO Dependencies
-- Requires: SALES (transaction data), INVENTORY (stock data)
-- ============================================================================

INSERT INTO module_dependencies (
    company_id, module_name, depends_on_module, is_required, reason
) VALUES
(
    '00000000-0000-0000-0000-000000000001',
    'ANALYTICS_PRO',
    'SALES',
    true,
    'Advanced analytics need sales transaction data for meaningful insights'
),
(
    '00000000-0000-0000-0000-000000000001',
    'ANALYTICS_PRO',
    'INVENTORY',
    true,
    'Analytics need inventory data for supply chain and cost analysis'
);

-- ============================================================================
-- COMPLIANCE Dependencies (Enterprise Only)
-- Requires: KITCHEN_MANAGEMENT (food safety), STAFF_MANAGEMENT (labor law)
-- ============================================================================

INSERT INTO module_dependencies (
    company_id, module_name, depends_on_module, is_required, reason
) VALUES
(
    '00000000-0000-0000-0000-000000000001',
    'COMPLIANCE',
    'KITCHEN_MANAGEMENT',
    true,
    'HACCP compliance requires kitchen temperature logs and food safety tracking'
),
(
    '00000000-0000-0000-0000-000000000001',
    'COMPLIANCE',
    'STAFF_MANAGEMENT',
    true,
    'Labor law compliance requires staff scheduling and wage tracking'
);

-- ============================================================================
-- CREATE TRIGGER FOR updated_at AUTO-UPDATE
-- ============================================================================

CREATE OR REPLACE FUNCTION update_updated_at_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ language 'plpgsql';

CREATE TRIGGER trigger_module_dependencies_updated_at
    BEFORE UPDATE ON module_dependencies
    FOR EACH ROW
    EXECUTE FUNCTION update_updated_at_column();

-- ============================================================================
-- VERIFICATION QUERIES
-- Run these to verify the dependency matrix is correctly populated
-- ============================================================================

-- Count dependencies per module
-- SELECT module_name, COUNT(*) as dependency_count
-- FROM module_dependencies
-- WHERE is_required = true
-- GROUP BY module_name
-- ORDER BY dependency_count DESC;

-- Show complete dependency matrix
-- SELECT
--     md.module_name as "Module",
--     md.depends_on_module as "Requires",
--     md.is_required as "Required",
--     md.reason as "Reason"
-- FROM module_dependencies md
-- ORDER BY md.module_name, md.depends_on_module;

-- Check for circular dependencies (should return 0 rows)
-- SELECT DISTINCT md1.module_name, md1.depends_on_module
-- FROM module_dependencies md1
-- JOIN module_dependencies md2 ON md1.depends_on_module = md2.module_name
--                               AND md1.module_name = md2.depends_on_module
-- WHERE md1.is_required = true AND md2.is_required = true;

COMMENT ON TABLE module_dependencies IS 'Stores dependency matrix for G-ADMIN module activation logic';
COMMENT ON COLUMN module_dependencies.module_name IS 'The module that has the dependency';
COMMENT ON COLUMN module_dependencies.depends_on_module IS 'The module that is required';
COMMENT ON COLUMN module_dependencies.is_required IS 'Whether this dependency blocks activation if not satisfied';
COMMENT ON COLUMN module_dependencies.reason IS 'Business justification for this dependency';

-- ============================================================================
-- Migration completed successfully
-- Dependency matrix ready for Task 1.2 Module Dependency Management System
-- ============================================================================