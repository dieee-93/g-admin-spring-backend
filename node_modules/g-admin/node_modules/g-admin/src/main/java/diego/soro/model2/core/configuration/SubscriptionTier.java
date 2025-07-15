package diego.soro.model2.core.configuration;

/**
 * Subscription tiers for G-ADMIN SaaS platform
 * Used for feature toggles and pricing model
 */
public enum SubscriptionTier {

    /**
     * Lite tier: Basic features for small businesses
     * - Core modules only
     * - SQLite database
     * - Basic authentication
     * - Up to 3 branches, 5 users
     */
    LITE,

    /**
     * Pro tier: Advanced features for growing businesses
     * - All modules activable
     * - PostgreSQL suggested
     * - OAuth2 authentication
     * - Up to 20 branches, unlimited users
     */
    PRO,

    /**
     * Enterprise tier: Full features for large organizations
     * - All modules + enterprise features
     * - PostgreSQL cluster + Redis
     * - Keycloak authentication
     * - Unlimited branches, multi-tenant
     */
    ENTERPRISE
}