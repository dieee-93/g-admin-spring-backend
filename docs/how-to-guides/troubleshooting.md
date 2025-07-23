# G-ADMIN How-To Guides - Solutions & Troubleshooting (CORRECTED)

**Practical solutions and troubleshooting for G-ADMIN platform**  
**Last Updated**: January 2025  
**Document Type**: How-To Guides (Diátaxis Framework)  
**Status**: CORRECTED - GraphQL conventions and practical solutions updated

---

## 📋 **Document Purpose & Continuity Instructions**

### **For Future AI Conversations:**
```
CONTEXT RECOVERY PROMPT:
"I'm continuing G-ADMIN how-to guides documentation. This contains practical solutions and troubleshooting recovered from 124+ artifacts. The project is a configuration-driven restaurant management platform. These are hands-on solutions for common problems in deployment, module management, performance optimization, and integrations. Following ADR-001 Diátaxis framework. All GraphQL types now use GQL suffix convention."
```

### **Guide Categories:**
- ✅ **Module Management**: Activation, dependency resolution, troubleshooting
- ✅ **Database Operations**: Migration, backup, performance tuning
- ✅ **Deployment**: Cloud deployment, configuration, monitoring
- ✅ **Integrations**: Delivery platforms, payments, fiscal compliance
- ✅ **Performance**: Optimization, caching, query tuning
- ✅ **Security**: Authentication setup, permissions, audit

---

## 🔧 **Module Management How-To**

### **How to Activate a Module with Dependencies**

#### **Problem**: Need to activate kitchen module but getting dependency errors
```bash
# Error message example:
"Cannot activate kitchen module. Missing dependencies: sales, menu"
```

#### **Solution with GraphQL (CORRECTED)**:
```graphql
# Step 1: Check current configuration
query CheckModuleStatus($companyId: ID!) {
  companyConfiguration(companyId: $companyId) {
    salesEnabled
    menuEnabled
    kitchenEnabled
    tier
  }
}

# Step 2: Check module dependencies
query GetModuleInfo($companyId: ID!, $moduleName: String!) {
  moduleStatus(companyId: $companyId, moduleName: $moduleName) {
    moduleName
    enabled
    available
    dependencies
    dependents
  }
}

# Step 3: Activate module with dependency resolution
mutation ActivateKitchenModule($companyId: ID!) {
  activateModule(companyId: $companyId, moduleName: "kitchen") {
    success
    message
    requiresConfirmation
    missingDependencies
    activatedModules
  }
}
```

#### **Expected Response with Dependencies**:
```json
{
  "data": {
    "activateModule": {
      "success": false,
      "requiresConfirmation": true,
      "message": "Activating kitchen requires: sales, menu. Continue? (+$25/month)",
      "missingDependencies": ["sales", "menu"],
      "activatedModules": []
    }
  }
}
```

#### **Auto-activation with Confirmation**:
```java
@Service
public class ModuleActivationWorkflow {
    
    private final ConfigurationService configurationService;
    private final ModuleDependencyService dependencyService;
    private final ModulePricingService pricingService;
    
    public ModuleActivationResultGQL activateWithDependencies(UUID companyId, String moduleName, boolean confirmed) {
        CompanyConfigurationGQL config = configurationService.getConfiguration(companyId);
        
        if (!confirmed) {
            return checkDependenciesAndPricing(companyId, moduleName, config);
        }
        
        // Activate all dependencies first
        List<String> dependencies = dependencyService.findMissingDependencies(moduleName, config);
        List<String> activatedModules = new ArrayList<>();
        
        for (String dep : dependencies) {
            ModuleActivationResultGQL result = configurationService.activateModule(companyId, dep);
            if (result.isSuccess()) {
                activatedModules.add(dep);
            }
        }
        
        // Then activate target module
        ModuleActivationResultGQL result = configurationService.activateModule(companyId, moduleName);
        if (result.isSuccess()) {
            activatedModules.add(moduleName);
        }
        
        return ModuleActivationResultGQL.builder()
                .success(result.isSuccess())
                .message(String.format("Successfully activated: %s", String.join(", ", activatedModules)))
                .activatedModules(activatedModules)
                .build();
    }
    
    private ModuleActivationResultGQL checkDependenciesAndPricing(UUID companyId, String moduleName, CompanyConfigurationGQL config) {
        List<String> missing = dependencyService.findMissingDependencies(moduleName, config);
        BigDecimal additionalCost = pricingService.calculateAdditionalCost(missing);
        
        String message = String.format(
            "Activating %s requires: %s. Additional cost: $%.2f/month. Continue?", 
            moduleName, 
            String.join(", ", missing),
            additionalCost
        );
        
        return ModuleActivationResultGQL.builder()
                .success(false)
                .requiresConfirmation(true)
                .message(message)
                .missingDependencies(missing)
                .build();
    }
}
```

---

### **How to Troubleshoot Module Activation Failures**

#### **Problem**: Module activation fails with unclear error messages

#### **Diagnostic Steps with REST API**:
```bash
# 1. Check current configuration
curl -X GET "http://localhost:8080/api/v1/configuration/company/{companyId}" \
  -H "Authorization: Bearer {jwt_token}"

# 2. Verify module dependencies
curl -X GET "http://localhost:8080/api/v1/configuration/company/{companyId}/module/kitchen/dependencies" \
  -H "Authorization: Bearer {jwt_token}"

# 3. Check tier limitations
curl -X GET "http://localhost:8080/api/v1/configuration/company/{companyId}/tier-limits" \
  -H "Authorization: Bearer {jwt_token}"
```

#### **Common Issues and Solutions**:

**Issue 1: Tier Restriction**
```json
// Error Response:
{
  "error": "MODULE_NOT_AVAILABLE_IN_TIER",
  "message": "Analytics module requires Pro tier or higher",
  "currentTier": "lite",
  "requiredTier": "pro"
}

// Solution: Upgrade tier first
```

**Issue 2: Database Constraint Violation**
```java
// Check database constraints
@Service
public class ModuleTroubleshootingService {
    
    public DiagnosticResultGQL diagnoseModuleIssue(UUID companyId, String moduleName) {
        List<String> issues = new ArrayList<>();
        
        // Check company exists
        if (!companyRepository.existsById(companyId)) {
            issues.add("Company not found");
        }
        
        // Check configuration exists
        CompanyConfigurationGQL config = configurationService.getConfiguration(companyId);
        if (config == null) {
            issues.add("Company configuration missing");
        }
        
        // Check tier compatibility
        if (!isModuleCompatibleWithTier(moduleName, config.getTier())) {
            issues.add(String.format("Module %s not available in %s tier", moduleName, config.getTier()));
        }
        
        // Check dependencies
        List<String> missingDeps = dependencyService.findMissingDependencies(moduleName, config);
        if (!missingDeps.isEmpty()) {
            issues.add(String.format("Missing dependencies: %s", String.join(", ", missingDeps)));
        }
        
        return DiagnosticResultGQL.builder()
                .success(issues.isEmpty())
                .issues(issues)
                .recommendations(generateRecommendations(issues))
                .build();
    }
}
```

---

### **How to Calculate Module Pricing**

#### **Problem**: Need to show accurate pricing before module activation

#### **Pricing Calculation Service**:
```java
@Service
public class ModulePricingService {
    
    private static final Map<String, BigDecimal> MODULE_PRICES = Map.of(
        "menu-advanced", new BigDecimal("1500.00"),
        "kitchen", new BigDecimal("2000.00"),
        "customer", new BigDecimal("1000.00"),
        "table", new BigDecimal("800.00"),
        "financial", new BigDecimal("1200.00"),
        "staff", new BigDecimal("1000.00"),
        "analytics", new BigDecimal("1500.00"),
        "notifications", new BigDecimal("500.00"),
        "compliance", new BigDecimal("2000.00")
    );
    
    public ModulePricingResultGQL calculateActivationCost(UUID companyId, String moduleName) {
        CompanyConfigurationGQL config = configurationService.getConfiguration(companyId);
        
        // Find all modules that need to be activated
        List<String> toActivate = new ArrayList<>();
        toActivate.add(moduleName);
        toActivate.addAll(dependencyService.findMissingDependencies(moduleName, config));
        
        // Calculate costs
        BigDecimal totalCost = BigDecimal.ZERO;
        List<ModuleCostGQL> breakdown = new ArrayList<>();
        
        for (String module : toActivate) {
            BigDecimal cost = MODULE_PRICES.getOrDefault(module, BigDecimal.ZERO);
            totalCost = totalCost.add(cost);
            
            breakdown.add(ModuleCostGQL.builder()
                    .moduleName(module)
                    .monthlyCost(cost)
                    .isRequired(dependencyService.isRequired(module, moduleName))
                    .build());
        }
        
        return ModulePricingResultGQL.builder()
                .moduleName(moduleName)
                .totalMonthlyCost(totalCost)
                .costBreakdown(breakdown)
                .currency("ARS")
                .build();
    }
}
```

#### **GraphQL Query for Pricing**:
```graphql
query CalculateModuleCost($companyId: ID!, $moduleName: String!) {
  calculateModuleCost(companyId: $companyId, moduleName: $moduleName) {
    moduleName
    totalMonthlyCost
    currency
    costBreakdown {
      moduleName
      monthlyCost
      isRequired
    }
  }
}
```

---

## 🗄️ **Database Operations How-To**

### **How to Migrate from SQLite to PostgreSQL**

#### **Problem**: Need to migrate production data from SQLite to PostgreSQL for better performance

#### **Pre-Migration Assessment**:
```java
@Service
public class MigrationAssessmentService {
    
    public MigrationAssessmentGQL assessMigrationReadiness(UUID companyId) {
        DatabaseMetricsGQL metrics = getDatabaseMetrics();
        
        boolean shouldMigrate = 
            metrics.getDatabaseSizeBytes() > 2_000_000_000L || // 2GB
            metrics.getAverageQueryTimeMs() > 2000L ||         // 2 seconds
            metrics.getConcurrentUsers() > 5 ||
            metrics.getMonthlyOrders() > 10_000;
            
        return MigrationAssessmentGQL.builder()
                .shouldMigrate(shouldMigrate)
                .currentDatabaseSize(metrics.getDatabaseSizeBytes())
                .averageQueryTime(metrics.getAverageQueryTimeMs())
                .estimatedDowntimeMinutes(calculateDowntime(metrics))
                .complexity(assessComplexity(metrics))
                .recommendations(generateRecommendations(metrics))
                .build();
    }
}
```

#### **Step-by-Step Migration Process**:

**Step 1: Backup Current SQLite Database**
```bash
#!/bin/bash
# backup-sqlite.sh

DB_PATH="./data/g-admin.db"
BACKUP_DIR="./backups"
TIMESTAMP=$(date +%Y%m%d_%H%M%S)

# Create backup directory
mkdir -p $BACKUP_DIR

# Create backup
sqlite3 $DB_PATH ".backup '$BACKUP_DIR/g-admin-backup-$TIMESTAMP.db'"

# Verify backup
ORIGINAL_SIZE=$(stat -c%s "$DB_PATH")
BACKUP_SIZE=$(stat -c%s "$BACKUP_DIR/g-admin-backup-$TIMESTAMP.db")

if [ "$ORIGINAL_SIZE" -eq "$BACKUP_SIZE" ]; then
    echo "✅ Backup successful: g-admin-backup-$TIMESTAMP.db"
else
    echo "❌ Backup failed: Size mismatch"
    exit 1
fi
```

**Step 2: Setup PostgreSQL Database**
```sql
-- setup-postgresql.sql
-- Connect to PostgreSQL as superuser
CREATE DATABASE gadmin_production;
CREATE USER gadmin_user WITH PASSWORD 'secure_password_here';
GRANT ALL PRIVILEGES ON DATABASE gadmin_production TO gadmin_user;

-- Connect to new database
\c gadmin_production;
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Grant schema permissions
GRANT ALL ON SCHEMA public TO gadmin_user;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO gadmin_user;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO gadmin_user;
```

**Step 3: Data Migration Service**:
```java
@Service
public class DatabaseMigrationService {
    
    @Value("${migration.source.url}")
    private String sourceUrl;
    
    @Value("${migration.target.url}")
    private String targetUrl;
    
    public MigrationResultGQL migrateSQLiteToPostgreSQL(UUID companyId) {
        MigrationResultGQL.MigrationResultGQLBuilder result = MigrationResultGQL.builder();
        
        try {
            log.info("Starting migration for company: {}", companyId);
            
            // 1. Validate source database
            validateSourceDatabase();
            
            // 2. Create target schema using Flyway
            createTargetSchema();
            
            // 3. Migrate data table by table
            List<String> migratedTables = migrateTableData(companyId);
            
            // 4. Verify data integrity
            verifyDataIntegrity(companyId);
            
            // 5. Update application configuration
            updateApplicationConfig(companyId);
            
            return result
                    .success(true)
                    .message("Migration completed successfully")
                    .migratedTables(migratedTables)
                    .downtimeMinutes(calculateActualDowntime())
                    .build();
                    
        } catch (Exception e) {
            log.error("Migration failed for company: {}", companyId, e);
            return result
                    .success(false)
                    .message("Migration failed: " + e.getMessage())
                    .build();
        }
    }
    
    private void validateSourceDatabase() {
        // Check SQLite database integrity
        try (Connection conn = DriverManager.getConnection(sourceUrl)) {
            try (Statement stmt = conn.createStatement()) {
                ResultSet rs = stmt.executeQuery("PRAGMA integrity_check");
                if (!rs.next() || !"ok".equals(rs.getString(1))) {
                    throw new RuntimeException("Source database integrity check failed");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Cannot validate source database", e);
        }
    }
    
    private List<String> migrateTableData(UUID companyId) {
        List<String> tables = Arrays.asList(
            "companies", "branches", "users", "roles", "permissions",
            "company_configurations", "products", "product_categories",
            "orders", "order_items", "customers", "payments"
        );
        
        List<String> migrated = new ArrayList<>();
        
        for (String table : tables) {
            try {
                int rowCount = migrateTable(table, companyId);
                migrated.add(String.format("%s (%d rows)", table, rowCount));
                log.info("Migrated table {} with {} rows", table, rowCount);
            } catch (Exception e) {
                log.error("Failed to migrate table: {}", table, e);
                throw new RuntimeException("Migration failed at table: " + table, e);
            }
        }
        
        return migrated;
    }
    
    private int migrateTable(String tableName, UUID companyId) throws SQLException {
        String selectSql = String.format("SELECT * FROM %s WHERE branch_id IN (SELECT id FROM branches WHERE company_id = ?)", tableName);
        String insertSql = buildInsertSql(tableName);
        
        int migratedRows = 0;
        
        try (Connection source = DriverManager.getConnection(sourceUrl);
             Connection target = DriverManager.getConnection(targetUrl);
             PreparedStatement selectStmt = source.prepareStatement(selectSql);
             PreparedStatement insertStmt = target.prepareStatement(insertSql)) {
            
            selectStmt.setString(1, companyId.toString());
            ResultSet rs = selectStmt.executeQuery();
            
            while (rs.next()) {
                mapResultSetToInsert(rs, insertStmt, tableName);
                insertStmt.addBatch();
                migratedRows++;
                
                if (migratedRows % 1000 == 0) {
                    insertStmt.executeBatch();
                }
            }
            
            insertStmt.executeBatch();
        }
        
        return migratedRows;
    }
}
```

**Step 4: Update Application Configuration**:
```yaml
# Update application.yml for PostgreSQL
spring:
  profiles:
    active: pro
  datasource:
    url: jdbc:postgresql://localhost:5432/gadmin_production
    username: gadmin_user
    password: ${DATABASE_PASSWORD}
    driver-class-name: org.postgresql.Driver
  jpa:
    properties:
      hibernate:
        dialect: org.hibernate.dialect.PostgreSQLDialect
    show-sql: false
  cache:
    type: redis
```

---

### **How to Backup and Restore Database**

#### **Problem**: Need reliable backup strategy for production data

#### **PostgreSQL Backup Script**:
```bash
#!/bin/bash
# backup-g-admin.sh

DB_NAME="gadmin_production"
DB_USER="gadmin_user"
DB_HOST="${DB_HOST:-localhost}"
BACKUP_DIR="/var/backups/g-admin"
DATE=$(date +%Y%m%d_%H%M%S)
RETENTION_DAYS=30

# Create backup directory
mkdir -p $BACKUP_DIR

echo "Starting G-ADMIN database backup..."

# Full database backup
pg_dump -h $DB_HOST -U $DB_USER -d $DB_NAME \
    --clean --create --if-exists \
    --format=custom \
    --compress=9 \
    --verbose \
    --file="$BACKUP_DIR/g-admin-full-$DATE.backup"

if [ $? -eq 0 ]; then
    echo "✅ Full backup completed: g-admin-full-$DATE.backup"
else
    echo "❌ Full backup failed"
    exit 1
fi

# Schema-only backup
pg_dump -h $DB_HOST -U $DB_USER -d $DB_NAME \
    --schema-only \
    --format=custom \
    --file="$BACKUP_DIR/g-admin-schema-$DATE.backup"

echo "✅ Schema backup completed: g-admin-schema-$DATE.backup"

# Data-only backup
pg_dump -h $DB_HOST -U $DB_USER -d $DB_NAME \
    --data-only \
    --format=custom \
    --file="$BACKUP_DIR/g-admin-data-$DATE.backup"

echo "✅ Data backup completed: g-admin-data-$DATE.backup"

# Compress old backups (older than 7 days)
find $BACKUP_DIR -name "*.backup" -mtime +7 -exec gzip {} \;

# Remove backups older than retention period
find $BACKUP_DIR -name "*.backup.gz" -mtime +$RETENTION_DAYS -delete

# Create backup manifest
cat > "$BACKUP_DIR/manifest-$DATE.json" << EOF
{
  "backup_date": "$(date -Iseconds)",
  "database": "$DB_NAME",
  "full_backup": "g-admin-full-$DATE.backup",
  "schema_backup": "g-admin-schema-$DATE.backup",
  "data_backup": "g-admin-data-$DATE.backup",
  "backup_size_mb": $(du -m "$BACKUP_DIR/g-admin-full-$DATE.backup" | cut -f1)
}
EOF

echo "✅ Backup manifest created: manifest-$DATE.json"
echo "📊 Backup completed successfully"
```

#### **Backup Verification Script**:
```bash
#!/bin/bash
# verify-backup.sh

BACKUP_FILE=$1
TEST_DB="gadmin_test_restore_$(date +%s)"

if [ -z "$BACKUP_FILE" ]; then
    echo "Usage: $0 <backup_file>"
    exit 1
fi

echo "🔍 Verifying backup: $BACKUP_FILE"

# Create test database
createdb $TEST_DB

# Restore backup to test database
pg_restore -d $TEST_DB $BACKUP_FILE

if [ $? -ne 0 ]; then
    echo "❌ Backup verification failed: Cannot restore"
    dropdb $TEST_DB
    exit 1
fi

# Verify critical tables
TABLES=("companies" "users" "orders" "products" "company_configurations")
for table in "${TABLES[@]}"; do
    count=$(psql -d $TEST_DB -t -c "SELECT COUNT(*) FROM $table;" 2>/dev/null | tr -d ' ')
    if [ -z "$count" ]; then
        echo "❌ Table $table: Missing or inaccessible"
    else
        echo "✅ Table $table: $count records"
    fi
done

# Verify referential integrity
integrity_check=$(psql -d $TEST_DB -t -c "
    SELECT COUNT(*) FROM information_schema.table_constraints 
    WHERE constraint_type = 'FOREIGN KEY';" | tr -d ' ')

echo "✅ Foreign key constraints: $integrity_check"

# Cleanup
dropdb $TEST_DB

echo "✅ Backup verification completed successfully"
```

#### **Automated Backup with Cron**:
```bash
# Add to crontab: sudo crontab -e

# Daily backup at 2 AM
0 2 * * * /opt/g-admin/scripts/backup-g-admin.sh >> /var/log/g-admin-backup.log 2>&1

# Weekly verification on Sunday at 3 AM
0 3 * * 0 /opt/g-admin/scripts/verify-backup.sh /var/backups/g-admin/g-admin-full-$(date +\%Y\%m\%d -d '1 day ago')*.backup >> /var/log/g-admin-backup.log 2>&1

# Monthly cleanup of old logs
0 4 1 * * find /var/log -name "g-admin-backup.log*" -mtime +90 -delete
```

---

## 🚀 **Cloud Deployment How-To**

### **How to Deploy to Render.com**

#### **Problem**: Need one-click deployment to cloud platform

#### **Step-by-Step Deployment**:

**Step 1: Prepare Application for Cloud**
```yaml
# render.yaml - Render deployment configuration
services:
  - type: web
    name: g-admin-backend
    runtime: java
    buildCommand: ./mvnw clean package -DskipTests
    startCommand: java -jar target/g-admin-backend-*.jar
    env: production
    plan: starter
    region: oregon
    healthCheckPath: /actuator/health
    envVars:
      - key: DATABASE_URL
        fromDatabase:
          name: g-admin-postgres
          property: connectionString
      - key: DATABASE_USERNAME
        fromDatabase:
          name: g-admin-postgres
          property: user
      - key: DATABASE_PASSWORD
        fromDatabase:
          name: g-admin-postgres
          property: password
      - key: APP_TIER
        value: pro
      - key: SPRING_PROFILES_ACTIVE
        value: pro
      - key: JWT_SECRET
        generateValue: true
      - key: CACHE_TYPE
        value: redis
      - key: REDIS_URL
        fromService:
          type: redis
          name: g-admin-redis
          property: connectionString

databases:
  - name: g-admin-postgres
    databaseName: gadmin
    user: gadmin_user
    plan: starter
    region: oregon

services:
  - type: redis
    name: g-admin-redis
    plan: starter
    region: oregon
    maxmemoryPolicy: allkeys-lru
```

**Step 2: Environment-Specific Configuration**
```yaml
# src/main/resources/application-production.yml
spring:
  datasource:
    url: ${DATABASE_URL}
    username: ${DATABASE_USERNAME}
    password: ${DATABASE_PASSWORD}
    driver-class-name: org.postgresql.Driver
    hikari:
      maximum-pool-size: 10
      minimum-idle: 2
      connection-timeout: 30000
      idle-timeout: 600000
      max-lifetime: 1800000
      
  jpa:
    hibernate:
      ddl-auto: validate
    properties:
      hibernate:
        dialect: org.hibernate.dialect.PostgreSQLDialect
        format_sql: false
        jdbc:
          batch_size: 25
        order_inserts: true
        order_updates: true
        
  cache:
    type: redis
    redis:
      time-to-live: 600000
      cache-null-values: false
      
  flyway:
    enabled: true
    validate-on-migrate: true
    clean-disabled: true
    
logging:
  level:
    root: INFO
    com.gadmin: INFO
    org.springframework.security: WARN
  pattern:
    console: "%d{yyyy-MM-dd HH:mm:ss} - %msg%n"
    
management:
  endpoints:
    web:
      exposure:
        include: health,info,metrics,prometheus
  endpoint:
    health:
      show-details: always
  metrics:
    export:
      prometheus:
        enabled: true
        
server:
  port: ${PORT:8080}
  compression:
    enabled: true
    mime-types: application/json,application/xml,text/html,text/xml,text/plain,application/javascript,text/css
```

**Step 3: Health Check Endpoint**:
```java
@RestController
@RequestMapping("/actuator")
public class HealthController {
    
    @Autowired
    private DataSource dataSource;
    
    @Autowired
    private CacheManager cacheManager;
    
    @GetMapping("/health")
    public ResponseEntity<HealthStatusGQL> health() {
        HealthStatusGQL.HealthStatusGQLBuilder status = HealthStatusGQL.builder();
        
        try {
            // Check database connectivity
            try (Connection conn = dataSource.getConnection()) {
                boolean dbHealthy = conn.isValid(5);
                status.database(dbHealthy ? "UP" : "DOWN");
            }
            
            // Check cache connectivity
            try {
                Cache configCache = cacheManager.getCache("company-config");
                status.cache(configCache != null ? "UP" : "DOWN");
            } catch (Exception e) {
                status.cache("DOWN");
            }
            
            // Overall status
            boolean allHealthy = "UP".equals(status.build().getDatabase()) && 
                               "UP".equals(status.build().getCache());
            
            return ResponseEntity
                    .status(allHealthy ? HttpStatus.OK : HttpStatus.SERVICE_UNAVAILABLE)
                    .body(status
                            .status(allHealthy ? "UP" : "DOWN")
                            .timestamp(Instant.now())
                            .build());
                            
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body(status
                            .status("DOWN")
                            .database("DOWN")
                            .cache("DOWN")
                            .timestamp(Instant.now())
                            .build());
        }
    }
}
```

---

### **How to Setup Environment Variables**

#### **Problem**: Need to configure different environments securely

#### **Environment Configuration Strategy**:
```bash
# .env.local (for local development)
DATABASE_URL=jdbc:sqlite:./data/g-admin.db
DATABASE_USERNAME=
DATABASE_PASSWORD=
APP_TIER=lite
SPRING_PROFILES_ACTIVE=lite
JWT_SECRET=dev-secret-key-change-in-production
CACHE_TYPE=simple

# .env.staging
DATABASE_URL=jdbc:postgresql://staging-db:5432/gadmin_staging
DATABASE_USERNAME=gadmin_staging
DATABASE_PASSWORD=${STAGING_DB_PASSWORD}
APP_TIER=pro
SPRING_PROFILES_ACTIVE=pro
JWT_SECRET=${STAGING_JWT_SECRET}
CACHE_TYPE=redis
REDIS_URL=redis://staging-redis:6379

# .env.production
DATABASE_URL=${PRODUCTION_DATABASE_URL}
DATABASE_USERNAME=${PRODUCTION_DATABASE_USERNAME}
DATABASE_PASSWORD=${PRODUCTION_DATABASE_PASSWORD}
APP_TIER=enterprise
SPRING_PROFILES_ACTIVE=production
JWT_SECRET=${PRODUCTION_JWT_SECRET}
CACHE_TYPE=redis
REDIS_URL=${PRODUCTION_REDIS_URL}

# Application-specific
CORS_ALLOWED_ORIGINS=https://app.g-admin.com,https://admin.g-admin.com
GRAPHQL_ENABLED=true
MULTI_TENANT=true
ADVANCED_AUTH=true

# Integration settings
MERCADOPAGO_ACCESS_TOKEN=${MERCADOPAGO_ACCESS_TOKEN}
MERCADOPAGO_CLIENT_ID=${MERCADOPAGO_CLIENT_ID}
MERCADOPAGO_CLIENT_SECRET=${MERCADOPAGO_CLIENT_SECRET}

PEDIDOSYA_API_KEY=${PEDIDOSYA_API_KEY}
PEDIDOSYA_SECRET=${PEDIDOSYA_SECRET}

UBER_EATS_API_KEY=${UBER_EATS_API_KEY}
UBER_EATS_SECRET=${UBER_EATS_SECRET}

# Monitoring
NEW_RELIC_LICENSE_KEY=${NEW_RELIC_LICENSE_KEY}
SENTRY_DSN=${SENTRY_DSN}
```

#### **Configuration Validation Service**:
```java
@Component
@ConfigurationProperties(prefix = "app")
@Validated
public class AppConfigurationProperties {
    
    @NotBlank
    private String tier;
    
    @NotBlank
    private String deployment;
    
    @Valid
    private DatabaseConfig database = new DatabaseConfig();
    
    @Valid
    private SecurityConfig security = new SecurityConfig();
    
    @Valid
    private IntegrationConfig integration = new IntegrationConfig();
    
    @PostConstruct
    public void validateConfiguration() {
        log.info("Validating G-ADMIN configuration...");
        
        // Validate tier
        if (!Arrays.asList("lite", "pro", "enterprise").contains(tier)) {
            throw new IllegalArgumentException("Invalid tier: " + tier);
        }
        
        // Validate database configuration
        if ("pro".equals(tier) || "enterprise".equals(tier)) {
            if (!"postgresql".equals(database.getType())) {
                throw new IllegalArgumentException("Pro/Enterprise tiers require PostgreSQL");
            }
        }
        
        // Validate security configuration
        if (security.getJwt().getSecret().length() < 32) {
            throw new IllegalArgumentException("JWT secret must be at least 32 characters");
        }
        
        log.info("✅ Configuration validation passed for tier: {}", tier);
    }
    
    // Getters and setters...
}
```

---

## 🔗 **Integration How-To**

### **How to Setup PedidosYa Integration**

#### **Problem**: Need to integrate with PedidosYa delivery platform for order automation

#### **Step 1: Configure PedidosYa Credentials**
```yaml
# application-pro.yml
app:
  integrations:
    pedidosya:
      enabled: ${PEDIDOSYA_ENABLED:false}
      api-key: ${PEDIDOSYA_API_KEY}
      secret: ${PEDIDOSYA_SECRET}
      base-url: ${PEDIDOSYA_BASE_URL:https://api.pedidosya.com/v1}
      webhook-secret: ${PEDIDOSYA_WEBHOOK_SECRET}
      restaurant-id: ${PEDIDOSYA_RESTAURANT_ID}
      auto-accept-orders: ${PEDIDOSYA_AUTO_ACCEPT:true}
      menu-sync-interval: ${PEDIDOSYA_MENU_SYNC_INTERVAL:PT15M}
```

#### **Step 2: Implement PedidosYa Service**:
```java
@Service
@ConditionalOnProperty(name = "app.integrations.pedidosya.enabled", havingValue = "true")
public class PedidosYaIntegrationService {
    
    @Value("${app.integrations.pedidosya.api-key}")
    private String apiKey;
    
    @Value("${app.integrations.pedidosya.base-url}")
    private String baseUrl;
    
    @Value("${app.integrations.pedidosya.restaurant-id}")
    private String restaurantId;
    
    private final RestTemplate restTemplate;
    private final ProductService productService;
    private final OrderService orderService;
    private final ProductMapper productMapper;
    
    @Scheduled(fixedDelayString = "${app.integrations.pedidosya.menu-sync-interval}")
    public void syncMenuToPedidosYa() {
        try {
            log.info("Starting PedidosYa menu sync...");
            
            List<ProductGQL> activeProducts = productService.getActiveProducts();
            List<PedidosYaMenuItem> menuItems = activeProducts.stream()
                    .map(this::convertToMenusYaFormat)
                    .collect(Collectors.toList());
            
            PedidosYaMenuUpdateRequest request = PedidosYaMenuUpdateRequest.builder()
                    .restaurantId(restaurantId)
                    .menuItems(menuItems)
                    .lastModified(Instant.now())
                    .build();
            
            ResponseEntity<PedidosYaResponse> response = restTemplate.exchange(
                    baseUrl + "/restaurants/{restaurantId}/menu",
                    HttpMethod.PUT,
                    new HttpEntity<>(request, createHeaders()),
                    PedidosYaResponse.class,
                    restaurantId
            );
            
            if (response.getStatusCode().is2xxSuccessful()) {
                log.info("✅ PedidosYa menu sync completed successfully");
            } else {
                log.error("❌ PedidosYa menu sync failed: {}", response.getBody());
            }
            
        } catch (Exception e) {
            log.error("❌ PedidosYa menu sync failed", e);
        }
    }
    
    public void updateProductAvailability(UUID productId, boolean available) {
        try {
            ProductGQL product = productService.getProduct(productId);
            
            PedidosYaAvailabilityUpdate update = PedidosYaAvailabilityUpdate.builder()
                    .productId(product.getSku())
                    .available(available)
                    .timestamp(Instant.now())
                    .build();
            
            restTemplate.exchange(
                    baseUrl + "/restaurants/{restaurantId}/products/{productId}/availability",
                    HttpMethod.PUT,
                    new HttpEntity<>(update, createHeaders()),
                    Void.class,
                    restaurantId,
                    product.getSku()
            );
            
            log.info("Updated PedidosYa availability for product {}: {}", product.getName(), available);
            
        } catch (Exception e) {
            log.error("Failed to update PedidosYa availability for product: {}", productId, e);
        }
    }
    
    private PedidosYaMenuItem convertToMenusYaFormat(ProductGQL product) {
        return PedidosYaMenuItem.builder()
                .id(product.getSku())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getSellingPrice())
                .available(product.getCurrentStock().compareTo(BigDecimal.ZERO) > 0)
                .category(product.getCategory().getName())
                .imageUrl(product.getImageUrl())
                .modifiers(convertModifiers(product))
                .build();
    }
    
    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);
        headers.set("X-Restaurant-ID", restaurantId);
        return headers;
    }
}
```

#### **Step 3: Webhook Handler for Orders**:
```java
@RestController
@RequestMapping("/webhooks/pedidosya")
@ConditionalOnProperty(name = "app.integrations.pedidosya.enabled", havingValue = "true")
public class PedidosYaWebhookController {
    
    @Value("${app.integrations.pedidosya.webhook-secret}")
    private String webhookSecret;
    
    private final OrderService orderService;
    private final CustomerService customerService;
    private final PedidosYaIntegrationService pedidosYaService;
    
    @PostMapping("/orders")
    public ResponseEntity<Void> handleOrderWebhook(
            @RequestHeader("X-PedidosYa-Signature") String signature,
            @RequestBody PedidosYaOrderWebhook webhook) {
        
        try {
            // Verify webhook signature
            if (!verifySignature(webhook, signature)) {
                log.warn("Invalid PedidosYa webhook signature");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            
            log.info("Processing PedidosYa order webhook: {}", webhook.getOrderId());
            
            // Process based on webhook type
            switch (webhook.getEventType()) {
                case "order.created":
                    handleNewOrder(webhook);
                    break;
                case "order.updated":
                    handleOrderUpdate(webhook);
                    break;
                case "order.cancelled":
                    handleOrderCancellation(webhook);
                    break;
                default:
                    log.warn("Unknown PedidosYa webhook event type: {}", webhook.getEventType());
            }
            
            return ResponseEntity.ok().build();
            
        } catch (Exception e) {
            log.error("Error processing PedidosYa webhook", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    private void handleNewOrder(PedidosYaOrderWebhook webhook) {
        // Create customer if not exists
        CustomerGQL customer = customerService.findOrCreateCustomer(
                webhook.getCustomer().getEmail(),
                webhook.getCustomer().getPhone(),
                webhook.getCustomer().getName()
        );
        
        // Create order
        CreateOrderGQLInput orderInput = CreateOrderGQLInput.builder()
                .customerId(customer.getId())
                .orderType(OrderTypeGQL.DELIVERY)
                .notes("PedidosYa Order: " + webhook.getOrderId())
                .items(convertOrderItems(webhook.getItems()))
                .build();
        
        OrderGQL order = orderService.createOrder(orderInput);
        
        // Store PedidosYa reference
        orderService.addExternalReference(order.getId(), "pedidosya", webhook.getOrderId());
        
        log.info("✅ Created G-ADMIN order {} from PedidosYa order {}", 
                order.getOrderNumber(), webhook.getOrderId());
    }
    
    private boolean verifySignature(PedidosYaOrderWebhook webhook, String signature) {
        try {
            String payload = objectMapper.writeValueAsString(webhook);
            String expectedSignature = calculateHmacSha256(payload, webhookSecret);
            return MessageDigest.isEqual(
                    signature.getBytes(StandardCharsets.UTF_8),
                    expectedSignature.getBytes(StandardCharsets.UTF_8)
            );
        } catch (Exception e) {
            log.error("Error verifying PedidosYa webhook signature", e);
            return false;
        }
    }
}
```

---

### **How to Setup Payment Processing with Mercado Pago**

#### **Problem**: Need to process payments through Mercado Pago in Argentina

#### **Step 1: Configure Mercado Pago**:
```yaml
# application.yml
app:
  payments:
    mercadopago:
      enabled: ${MERCADOPAGO_ENABLED:false}
      access-token: ${MERCADOPAGO_ACCESS_TOKEN}
      client-id: ${MERCADOPAGO_CLIENT_ID}
      client-secret: ${MERCADOPAGO_CLIENT_SECRET}
      webhook-secret: ${MERCADOPAGO_WEBHOOK_SECRET}
      environment: ${MERCADOPAGO_ENVIRONMENT:sandbox}
      currency: ARS
      payment-methods:
        - visa
        - mastercard
        - american_express
        - mercadopago_cuenta
        - rapipago
        - pagofacil
```

#### **Step 2: Payment Service Implementation**:
```java
@Service
@ConditionalOnProperty(name = "app.payments.mercadopago.enabled", havingValue = "true")
public class MercadoPagoPaymentService implements PaymentProcessor {
    
    @Value("${app.payments.mercadopago.access-token}")
    private String accessToken;
    
    @Value("${app.payments.mercadopago.webhook-secret}")
    private String webhookSecret;
    
    private final RestTemplate restTemplate;
    private final OrderService orderService;
    
    @PostConstruct
    public void initialize() {
        // Configure Mercado Pago SDK
        MercadoPago.SDK.setAccessToken(accessToken);
        log.info("✅ Mercado Pago payment processor initialized");
    }
    
    @Override
    public PaymentResultGQL processPayment(ProcessPaymentGQLInput request) {
        try {
            log.info("Processing Mercado Pago payment for order: {}", request.getOrderId());
            
            OrderGQL order = orderService.getOrder(request.getOrderId());
            
            // Create payment preference
            Preference preference = new Preference();
            preference.setBackUrls(createBackUrls(order.getId()));
            preference.setNotificationUrl(createNotificationUrl());
            preference.setAutoReturn("approved");
            
            // Add order items
            List<Item> items = order.getItems().stream()
                    .map(this::convertToMercadoPagoItem)
                    .collect(Collectors.toList());
            preference.setItems(items);
            
            // Set payer info
            if (order.getCustomer() != null) {
                Payer payer = new Payer();
                payer.setEmail(order.getCustomer().getEmail());
                payer.setName(order.getCustomer().getFirstName());
                payer.setSurname(order.getCustomer().getLastName());
                preference.setPayer(payer);
            }
            
            // Save preference
            preference.save();
            
            // Create payment record
            PaymentGQL payment = createPaymentRecord(order, preference.getId(), request.getAmount());
            
            return PaymentResultGQL.builder()
                    .success(true)
                    .payment(payment)
                    .transactionId(preference.getId())
                    .paymentUrl(preference.getInitPoint())
                    .message("Payment preference created successfully")
                    .build();
                    
        } catch (Exception e) {
            log.error("Mercado Pago payment processing failed for order: {}", request.getOrderId(), e);
            
            return PaymentResultGQL.builder()
                    .success(false)
                    .message("Payment processing failed: " + e.getMessage())
                    .errorCode("MERCADOPAGO_ERROR")
                    .build();
        }
    }
    
    private Item convertToMercadoPagoItem(OrderItemGQL orderItem) {
        Item item = new Item();
        item.setId(orderItem.getProduct().getSku());
        item.setTitle(orderItem.getProduct().getName());
        item.setDescription(orderItem.getProduct().getDescription());
        item.setQuantity(orderItem.getQuantity().intValue());
        item.setUnitPrice(orderItem.getUnitPrice().floatValue());
        item.setCurrencyId("ARS");
        return item;
    }
    
    private BackUrls createBackUrls(UUID orderId) {
        String baseUrl = "https://app.g-admin.com";
        BackUrls backUrls = new BackUrls();
        backUrls.setSuccess(baseUrl + "/payment/success?order=" + orderId);
        backUrls.setFailure(baseUrl + "/payment/failure?order=" + orderId);
        backUrls.setPending(baseUrl + "/payment/pending?order=" + orderId);
        return backUrls;
    }
}
```

#### **Step 3: Payment Webhook Handler**:
```java
@RestController
@RequestMapping("/webhooks/mercadopago")
public class MercadoPagoWebhookController {
    
    private final MercadoPagoPaymentService paymentService;
    private final OrderService orderService;
    private final InvoiceService invoiceService;
    
    @PostMapping("/payments")
    public ResponseEntity<Void> handlePaymentNotification(
            @RequestHeader("X-Signature") String signature,
            @RequestBody MercadoPagoNotification notification) {
        
        try {
            log.info("Processing Mercado Pago notification: {}", notification.getId());
            
            // Verify notification signature
            if (!paymentService.verifyNotificationSignature(notification, signature)) {
                log.warn("Invalid Mercado Pago notification signature");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            
            // Process notification based on type
            switch (notification.getType()) {
                case "payment":
                    handlePaymentNotification(notification);
                    break;
                case "plan":
                    handlePlanNotification(notification);
                    break;
                case "subscription":
                    handleSubscriptionNotification(notification);
                    break;
                default:
                    log.warn("Unknown Mercado Pago notification type: {}", notification.getType());
            }
            
            return ResponseEntity.ok().build();
            
        } catch (Exception e) {
            log.error("Error processing Mercado Pago notification", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    private void handlePaymentNotification(MercadoPagoNotification notification) {
        try {
            // Get payment details from Mercado Pago
            Payment payment = Payment.findById(notification.getDataId());
            
            // Find corresponding order
            String preferenceId = payment.getAdditionalInfo().get("preference_id").toString();
            PaymentGQL localPayment = paymentService.findByTransactionId(preferenceId);
            
            if (localPayment == null) {
                log.error("Payment not found for preference ID: {}", preferenceId);
                return;
            }
            
            // Update payment status
            PaymentStatusGQL newStatus = mapMercadoPagoStatus(payment.getStatus());
            paymentService.updatePaymentStatus(localPayment.getId(), newStatus);
            
            // If payment approved, complete order and generate invoice
            if (newStatus == PaymentStatusGQL.COMPLETED) {
                OrderGQL order = orderService.getOrder(localPayment.getOrder().getId());
                orderService.completeOrder(order.getId());
                
                // Generate AFIP invoice if enabled
                if (invoiceService.isAfipEnabled()) {
                    invoiceService.generateElectronicInvoice(order);
                }
                
                log.info("✅ Payment completed for order: {}", order.getOrderNumber());
            }
            
        } catch (Exception e) {
            log.error("Error handling Mercado Pago payment notification", e);
        }
    }
}
```

---

## ⚡ **Performance Optimization How-To**

### **How to Optimize GraphQL N+1 Queries**

#### **Problem**: GraphQL queries causing excessive database calls

#### **Identify N+1 Query Issues**:
```java
// Problem query example
@DgsQuery
public List<OrderGQL> orders(@InputArgument String branchId) {
    // This will cause N+1 queries:
    // 1 query to get orders
    // N queries to get order items for each order
    // N queries to get product for each item
    return orderService.getOrdersByBranch(UUID.fromString(branchId));
}

// Solution: Use DataLoader pattern
@DgsQuery
public List<OrderGQL> orders(@InputArgument String branchId) {
    return orderService.getOrdersByBranchWithItems(UUID.fromString(branchId));
}
```

#### **Implement DataLoader for Products**:
```java
@Component
public class ProductDataLoader {
    
    private final ProductService productService;
    
    public DataLoader<UUID, ProductGQL> createProductDataLoader() {
        return DataLoader.newMappedDataLoader((Set<UUID> productIds) -> {
            
            // Batch load all products in single query
            List<ProductGQL> products = productService.findByIds(new ArrayList<>(productIds));
            
            // Convert to map for DataLoader
            Map<UUID, ProductGQL> productMap = products.stream()
                    .collect(Collectors.toMap(
                            product -> UUID.fromString(product.getId()),
                            Function.identity()
                    ));
                    
            return CompletableFuture.completedFuture(productMap);
        });
    }
}

@DgsComponent
public class OrderDataFetcher {
    
    @DgsData(parentType = "OrderItemGQL", field = "product")
    public CompletableFuture<ProductGQL> getProduct(
            @DgsDataFetchingEnvironment DataFetchingEnvironment dfe) {
        
        OrderItemGQL orderItem = dfe.getSource();
        DataLoader<UUID, ProductGQL> dataLoader = dfe.getDataLoader("product");
        
        return dataLoader.load(UUID.fromString(orderItem.getProductId()));
    }
}

@Configuration
public class DataLoaderConfiguration {
    
    @Bean
    public DataLoaderRegistry dataLoaderRegistry(
            ProductDataLoader productDataLoader,
            CustomerDataLoader customerDataLoader) {
        
        DataLoaderRegistry registry = new DataLoaderRegistry();
        registry.register("product", productDataLoader.createProductDataLoader());
        registry.register("customer", customerDataLoader.createCustomerDataLoader());
        return registry;
    }
}
```

#### **Optimize Repository Queries**:
```java
@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {
    
    // ❌ BAD: Will cause N+1 queries
    @Query("SELECT o FROM Order o WHERE o.branchId = :branchId")
    List<Order> findByBranchId(@Param("branchId") UUID branchId);
    
    // ✅ GOOD: Fetch with joins to avoid N+1
    @Query("SELECT o FROM Order o " +
           "LEFT JOIN FETCH o.orderItems oi " +
           "LEFT JOIN FETCH oi.product p " +
           "LEFT JOIN FETCH p.category " +
           "LEFT JOIN FETCH o.customer " +
           "WHERE o.branchId = :branchId " +
           "ORDER BY o.orderDate DESC")
    List<Order> findByBranchIdWithDetails(@Param("branchId") UUID branchId);
    
    // ✅ GOOD: Use pagination for large result sets
    @Query("SELECT o FROM Order o " +
           "LEFT JOIN FETCH o.orderItems oi " +
           "LEFT JOIN FETCH oi.product " +
           "WHERE o.branchId = :branchId " +
           "AND o.orderDate >= :dateFrom " +
           "ORDER BY o.orderDate DESC")
    Page<Order> findByBranchIdAndDateRange(
            @Param("branchId") UUID branchId,
            @Param("dateFrom") LocalDateTime dateFrom,
            Pageable pageable);
}
```

---

### **How to Implement Effective Caching**

#### **Problem**: Slow response times for frequently accessed data

#### **Cache Configuration by Tier (CORRECTED)**:
```yaml
# application.yml - CORRECTED cache configuration
spring:
  cache:
    type: ${CACHE_TYPE:simple}
    cache-names:
      - company-config
      - user-permissions
      - menu-items
      - product-inventory
      - module-status
      - customer-data
    caffeine:
      spec: maximumSize=1000,expireAfterWrite=30m
```

#### **Service-Level Caching**:
```java
@Service
public class MenuService {
    
    // Cache menu items for 15 minutes
    @Cacheable(value = "menu-items", key = "#branchId + '_' + #categoryId")
    public List<ProductGQL> getActiveMenuItems(UUID branchId, UUID categoryId) {
        return productRepository.findActiveByBranchAndCategory(branchId, categoryId)
                .stream()
                .map(productMapper::entityToGql)
                .collect(Collectors.toList());
    }
    
    // Cache full menu for 30 minutes
    @Cacheable(value = "menu-items", key = "#branchId + '_full'")
    public MenuStructureGQL getFullMenu(UUID branchId) {
        List<ProductCategoryGQL> categories = categoryService.getActiveCategories();
        
        return MenuStructureGQL.builder()
                .categories(categories)
                .lastModified(Instant.now())
                .build();
    }
    
    // Evict cache when menu changes
    @CacheEvict(value = "menu-items", key = "#branchId + '_*'")
    public void invalidateMenuCache(UUID branchId) {
        log.info("Invalidated menu cache for branch: {}", branchId);
    }
    
    // Update cache entry
    @CachePut(value = "menu-items", key = "#product.branchId + '_' + #product.category.id")
    public ProductGQL updateMenuItemCache(ProductGQL product) {
        return product;
    }
}
```

#### **Redis Configuration for Pro/Enterprise**:
```java
@Configuration
@ConditionalOnProperty(name = "spring.cache.type", havingValue = "redis")
public class RedisCacheConfiguration {
    
    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(30))
                .serializeKeysWith(RedisSerializationContext.SerializationPair
                        .fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair
                        .fromSerializer(new GenericJackson2JsonRedisSerializer()));
        
        // Different TTL for different cache types
        Map<String, RedisCacheConfiguration> cacheConfigurations = new HashMap<>();
        cacheConfigurations.put("company-config", config.entryTtl(Duration.ofHours(4)));
        cacheConfigurations.put("user-permissions", config.entryTtl(Duration.ofHours(1)));
        cacheConfigurations.put("menu-items", config.entryTtl(Duration.ofMinutes(15)));
        cacheConfigurations.put("product-inventory", config.entryTtl(Duration.ofMinutes(5)));
        cacheConfigurations.put("module-status", config.entryTtl(Duration.ofHours(2)));
        
        return RedisCacheManager.builder(connectionFactory)
                .cacheDefaults(config)
                .withInitialCacheConfigurations(cacheConfigurations)
                .build();
    }
}
```

#### **Cache Monitoring and Metrics**:
```java
@Component
public class CacheMetricsCollector {
    
    private final CacheManager cacheManager;
    private final MeterRegistry meterRegistry;
    
    @EventListener
    public void onCacheEvent(CacheEvent event) {
        String cacheName = event.getCacheName();
        String eventType = event.getClass().getSimpleName();
        
        Timer.Sample sample = Timer.start(meterRegistry);
        sample.stop(Timer.builder("cache.operation")
                .tag("cache", cacheName)
                .tag("operation", eventType)
                .register(meterRegistry));
                
        // Track cache hit/miss ratios
        if (event instanceof CacheHitEvent) {
            meterRegistry.counter("cache.hit", "cache", cacheName).increment();
        } else if (event instanceof CacheMissEvent) {
            meterRegistry.counter("cache.miss", "cache", cacheName).increment();
        }
    }
    
    @Scheduled(fixedRate = 60000) // Every minute
    public void collectCacheStatistics() {
        for (String cacheName : cacheManager.getCacheNames()) {
            Cache cache = cacheManager.getCache(cacheName);
            if (cache instanceof CaffeineCache) {
                com.github.benmanes.caffeine.cache.Cache<Object, Object> nativeCache = 
                        ((CaffeineCache) cache).getNativeCache();
                
                CacheStats stats = nativeCache.stats();
                
                meterRegistry.gauge("cache.size", Tags.of("cache", cacheName), nativeCache.estimatedSize());
                meterRegistry.gauge("cache.hit.ratio", Tags.of("cache", cacheName), stats.hitRate());
                meterRegistry.gauge("cache.eviction.count", Tags.of("cache", cacheName), stats.evictionCount());
            }
        }
    }
}
```

---

## 🔒 **Security & Authentication How-To**

### **How to Setup JWT Authentication for Lite Tier**

#### **Problem**: Need simple authentication without external dependencies

#### **JWT Service Implementation**:
```java
@Service
@ConditionalOnProperty(name = "app.tier", havingValue = "lite")
public class JwtAuthenticationService {
    
    @Value("${spring.security.jwt.secret}")
    private String jwtSecret;
    
    @Value("${spring.security.jwt.expiration}")
    private long jwtExpirationMs;
    
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    
    public AuthenticationResponseGQL authenticate(AuthenticationRequestGQL request) {
        try {
            // Validate credentials
            UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
            
            if (!passwordEncoder.matches(request.getPassword(), userDetails.getPassword())) {
                throw new BadCredentialsException("Invalid credentials");
            }
            
            // Generate JWT token
            String token = generateToken(userDetails);
            
            // Get user information
            UserGQL user = userService.findByEmail(request.getEmail());
            
            return AuthenticationResponseGQL.builder()
                    .success(true)
                    .token(token)
                    .expiresIn(jwtExpirationMs / 1000) // Convert to seconds
                    .user(user)
                    .permissions(getUserPermissions(user))
                    .build();
                    
        } catch (Exception e) {
            log.error("Authentication failed for user: {}", request.getEmail(), e);
            
            return AuthenticationResponseGQL.builder()
                    .success(false)
                    .message("Authentication failed: " + e.getMessage())
                    .build();
        }
    }
    
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("authorities", userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList()));
        
        return createToken(claims, userDetails.getUsername());
    }
    
    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }
    
    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
    
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }
    
    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }
    
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
    }
    
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }
    
    private Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }
}
```

#### **Security Configuration for Lite Tier**:
```java
@Configuration
@EnableWebSecurity
@ConditionalOnProperty(name = "app.tier", havingValue = "lite")
public class LiteSecurityConfig {
    
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtRequestFilter jwtRequestFilter;
    private final UserDetailsService userDetailsService;
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
    
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/v1/auth/**").permitAll()
                .requestMatchers("/api/v1/public/**").permitAll()
                .requestMatchers("/graphql").authenticated()
                .requestMatchers("/graphiql/**").hasRole("ADMIN")
                .requestMatchers("/actuator/health").permitAll()
                .requestMatchers("/actuator/**").hasRole("ADMIN")
                .anyRequest().authenticated()
            )
            .exceptionHandling(ex -> ex.authenticationEntryPoint(jwtAuthenticationEntryPoint))
            .authenticationProvider(authenticationProvider())
            .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
            
        return http.build();
    }
    
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(Arrays.asList(
            "http://localhost:3000",
            "http://localhost:3001", 
            "https://*.g-admin.app",
            "https://*.g-admin.com"
        ));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
```

#### **Authentication GraphQL Resolvers**:
```graphql
# Add to schema
extend type Mutation {
  authenticate(input: AuthenticationRequestGQLInput!): AuthenticationResponseGQL!
  refreshToken(token: String!): AuthenticationResponseGQL!
  logout: Boolean!
}

type AuthenticationResponseGQL {
  success: Boolean!
  token: String
  expiresIn: Int
  user: UserGQL
  permissions: [String!]
  message: String
}

input AuthenticationRequestGQLInput {
  email: String!
  password: String!
  rememberMe: Boolean
}
```

```java
@DgsComponent
public class AuthenticationDataFetcher {
    
    private final JwtAuthenticationService authService;
    
    @DgsMutation
    public AuthenticationResponseGQL authenticate(@InputArgument AuthenticationRequestGQLInput input) {
        return authService.authenticate(input);
    }
    
    @DgsMutation
    public AuthenticationResponseGQL refreshToken(@InputArgument String token) {
        return authService.refreshToken(token);
    }
    
    @DgsMutation
    public Boolean logout() {
        // For JWT, logout is client-side token removal
        // For session-based auth, would invalidate session
        SecurityContextHolder.clearContext();
        return true;
    }
}
```

---

**This corrected how-to guide document now follows all established GraphQL conventions with GQL suffix types, proper schema composition, MapStruct references, and practical solutions for common G-ADMIN platform challenges.**