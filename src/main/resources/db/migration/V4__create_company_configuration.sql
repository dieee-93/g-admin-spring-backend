-- Migration: Create company_configuration table
-- Version: V4__create_company_configuration.sql
-- Description: Implements configuration-driven architecture for G-ADMIN SaaS platform
-- Compatible with both SQLite and PostgreSQL

CREATE TABLE IF NOT EXISTS company_configuration (
    -- Primary key and inheritance from BaseEntity
    -- Note: Using UUID type for PostgreSQL, VARCHAR(36) for SQLite compatibility
    id VARCHAR(36) PRIMARY KEY DEFAULT (LOWER(HEX(RANDOMBLOB(4))) || '-' || LOWER(HEX(RANDOMBLOB(2))) || '-4' || SUBSTR(LOWER(HEX(RANDOMBLOB(2))),2) || '-' || SUBSTR('89ab',ABS(RANDOM()) % 4 + 1, 1) || SUBSTR(LOWER(HEX(RANDOMBLOB(2))),2) || '-' || LOWER(HEX(RANDOMBLOB(6)))),
    tenant_id VARCHAR(36),
    branch_id VARCHAR(36) NOT NULL,
    version INTEGER DEFAULT 0,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    active BOOLEAN NOT NULL DEFAULT 1,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),

    -- Company relationship (unique constraint ensures 1:1)
    company_id VARCHAR(36) NOT NULL UNIQUE,

    -- Subscription tier
    tier VARCHAR(20) NOT NULL DEFAULT 'LITE',

    -- Core modules (always enabled)
    core_enabled BOOLEAN NOT NULL DEFAULT 1,
    inventory_enabled BOOLEAN NOT NULL DEFAULT 1,
    sales_enabled BOOLEAN NOT NULL DEFAULT 1,
    menu_enabled BOOLEAN NOT NULL DEFAULT 1,

    -- Activable modules (pay-per-module)
    menu_advanced_enabled BOOLEAN NOT NULL DEFAULT 0,
    kitchen_enabled BOOLEAN NOT NULL DEFAULT 0,
    customer_enabled BOOLEAN NOT NULL DEFAULT 0,
    table_enabled BOOLEAN NOT NULL DEFAULT 0,
    financial_enabled BOOLEAN NOT NULL DEFAULT 0,
    staff_enabled BOOLEAN NOT NULL DEFAULT 0,
    analytics_enabled BOOLEAN NOT NULL DEFAULT 0,
    notifications_enabled BOOLEAN NOT NULL DEFAULT 0,
    compliance_enabled BOOLEAN NOT NULL DEFAULT 0,

    -- Configuration metadata
    last_configuration_change TIMESTAMP,
    last_modified_by VARCHAR(255),
    configuration_notes VARCHAR(500),

    -- Foreign key constraints
    FOREIGN KEY (company_id) REFERENCES company(id) ON DELETE CASCADE
);

-- Create indexes for performance
CREATE INDEX IF NOT EXISTS idx_company_configuration_company_id ON company_configuration(company_id);
CREATE INDEX IF NOT EXISTS idx_company_configuration_tier ON company_configuration(tier);
CREATE INDEX IF NOT EXISTS idx_company_configuration_active ON company_configuration(active);

-- Indexes for module queries (used in analytics)
CREATE INDEX IF NOT EXISTS idx_company_configuration_kitchen_enabled ON company_configuration(kitchen_enabled);
CREATE INDEX IF NOT EXISTS idx_company_configuration_customer_enabled ON company_configuration(customer_enabled);
CREATE INDEX IF NOT EXISTS idx_company_configuration_analytics_enabled ON company_configuration(analytics_enabled);

-- Insert default configurations for existing companies (if any)
-- This ensures backward compatibility
INSERT OR IGNORE INTO company_configuration (
    company_id,
    tier,
    branch_id,
    last_modified_by,
    configuration_notes
)
SELECT
    c.id as company_id,
    'LITE' as tier,
    c.id as branch_id, -- Default: company acts as its own branch
    'migration' as last_modified_by,
    'Default configuration created during migration' as configuration_notes
FROM company c
WHERE NOT EXISTS (
    SELECT 1 FROM company_configuration cc WHERE cc.company_id = c.id
);