# G-ADMIN Reference: Módulos Core

**Documento**: /reference/core-modules.md  
**Clasificación**: BAJA VOLATILIDAD (especificaciones estables)  
**Fuente**: Consolidación Master Context Enriquecido + Living Document + v3.1  
**Status**: RECOVERED - Phase 1 Core Modules

---

## 🏗️ **MÓDULOS SIEMPRE ACTIVOS (Base Package)**

Estos módulos están **siempre incluidos** en todos los tiers y no se pueden desactivar.

---

## 1. **CORE MODULE** ✅ COMPLETADO

### **Status Actual (Living Document - FUENTE DE VERDAD):**
- ✅ **COMPLETADO**: CompanyConfiguration entity functional
- ✅ **COMPLETADO**: Database persistence working (SQLite + PostgreSQL ready)
- ✅ **COMPLETADO**: Feature toggles (@ConditionalOnProperty) implemented
- ✅ **COMPLETADO**: REST API endpoints operational
- ✅ **COMPLETADO**: Cache integration active (<50ms module checks)
- ✅ **COMPLETADO**: Test coverage 95%+ comprehensive

### **Entidades Principales:**

#### **User Entity**
```java
@Entity
@Table(name = "users")
public class User extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String username;
    
    @Column(nullable = false, unique = true)
    private String email;
    
    @Column(nullable = false)
    private String password; // BCrypt hashed
    
    @Column(nullable = false)
    private String firstName;
    
    @Column(nullable = false)
    private String lastName;
    
    @Column(nullable = false)
    private Boolean enabled = true;
    
    @Column(nullable = false)
    private Boolean emailVerified = false;
    
    // Multi-branch relationships
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_branches")
    private Set<Branch> branches = new HashSet<>();
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles")
    private Set<Role> roles = new HashSet<>();
    
    // Authentication metadata
    private Instant lastLogin;
    private String lastLoginIp;
    private Integer failedLoginAttempts = 0;
    private Instant accountLockedUntil;
    
    // Profile information
    private String phoneNumber;
    private String preferredLanguage = "es";
    private String timezone = "America/Argentina/Buenos_Aires";
}
```

#### **Role Entity (RBAC System)**
```java
@Entity
@Table(name = "roles")
public class Role extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String name; // ADMIN, MANAGER, CASHIER, KITCHEN_STAFF, WAITER
    
    private String description;
    
    @Column(nullable = false)
    private Integer hierarchyLevel; // 1=ADMIN, 2=MANAGER, 3=STAFF, etc.
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "role_permissions")
    private Set<Permission> permissions = new HashSet<>();
    
    // Scope limitations
    @Column(nullable = false)
    private Boolean canManageUsers = false;
    
    @Column(nullable = false)
    private Boolean canAccessReports = false;
    
    @Column(nullable = false)
    private Boolean canModifyInventory = false;
    
    @Column(nullable = false)
    private Boolean canProcessSales = true; // Most roles can process sales
}
```

#### **Permission Entity**
```java
@Entity
@Table(name = "permissions")
public class Permission extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String name; // READ_INVENTORY, WRITE_SALES, MANAGE_USERS, etc.
    
    private String description;
    
    @Column(nullable = false)
    private String module; // core, inventory, sales, kitchen, etc.
    
    @Column(nullable = false)
    private String resource; // users, products, orders, reports
    
    @Enumerated(EnumType.STRING)
    private PermissionAction action; // READ, WRITE, DELETE, MANAGE
}

enum PermissionAction {
    READ, WRITE, DELETE, MANAGE, EXECUTE
}
```

#### **Company Entity**
```java
@Entity
@Table(name = "companies")
public class Company extends BaseEntity {
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false, unique = true)
    private String slug; // For subdomain: slug.g-admin.app
    
    // Business information
    private String businessType; // RESTAURANT, CAFETERIA, FAST_FOOD, etc.
    private String taxId; // CUIT/CNPJ/RFC depending on country
    private String phoneNumber;
    private String email;
    private String website;
    
    // Address information
    @Embedded
    private Address address;
    
    // Subscription and configuration
    @Enumerated(EnumType.STRING)
    private TierType tier = TierType.LITE;
    
    @OneToOne(mappedBy = "company", cascade = CascadeType.ALL)
    private CompanyConfiguration configuration;
    
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    private List<Branch> branches = new ArrayList<>();
    
    // Business settings
    private String defaultCurrency = "ARS"; // ISO currency code
    private String defaultTimezone = "America/Argentina/Buenos_Aires";
    private String defaultLanguage = "es";
    
    // Billing information
    private String billingEmail;
    private Instant subscriptionStartDate;
    private Instant subscriptionEndDate;
    private Boolean subscriptionActive = true;
    
    // Compliance and legal
    private Boolean termsAccepted = false;
    private Instant termsAcceptedDate;
    private String termsVersion;
}
```

#### **Branch Entity (Multi-Branch desde Día 1)**
```java
@Entity
@Table(name = "branches")
public class Branch extends BaseEntity {
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false)
    private String code; // Unique identifier: MAIN, B001, B002
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "company_id")
    private Company company;
    
    // Location information
    @Embedded
    private Address address;
    
    private String phoneNumber;
    private String email;
    
    // Operational settings
    @Column(nullable = false)
    private Boolean isMainBranch = false;
    
    @Column(nullable = false)
    private Boolean isActive = true;
    
    private String timezone = "America/Argentina/Buenos_Aires";
    
    // Business hours (JSON format)
    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    private Map<String, BusinessHours> operatingHours = new HashMap<>();
    
    // Capacity information
    private Integer maxTables;
    private Integer maxSeats;
    private Boolean hasDelivery = false;
    private Boolean hasTakeaway = true;
    private Boolean hasDineIn = true;
    
    // Users assigned to this branch
    @ManyToMany(mappedBy = "branches")
    private Set<User> users = new HashSet<>();
}

@Embeddable
class BusinessHours {
    private LocalTime openTime;
    private LocalTime closeTime;
    private Boolean isClosed = false;
}
```

#### **CompanyConfiguration Entity (Configuration-Driven Core)**
```java
@Entity
@Table(name = "company_configurations")
public class CompanyConfiguration extends BaseEntity {
    @OneToOne(optional = false)
    @JoinColumn(name = "company_id")
    private Company company;
    
    // Tier configuration
    @Enumerated(EnumType.STRING)
    private TierType tier = TierType.LITE;
    
    // Module activation (always-on modules)
    @Column(nullable = false)
    private Boolean coreEnabled = true; // Cannot be disabled
    
    @Column(nullable = false)
    private Boolean inventoryEnabled = true; // Cannot be disabled
    
    @Column(nullable = false)
    private Boolean salesEnabled = true; // Cannot be disabled
    
    @Column(nullable = false)
    private Boolean menuEnabled = true; // Cannot be disabled
    
    // Activable modules (pay-per-module)
    @Column(nullable = false)
    private Boolean kitchenEnabled = false;
    
    @Column(nullable = false)
    private Boolean customerEnabled = false;
    
    @Column(nullable = false)
    private Boolean tableEnabled = false;
    
    @Column(nullable = false)
    private Boolean financialEnabled = false;
    
    @Column(nullable = false)
    private Boolean staffEnabled = false;
    
    @Column(nullable = false)
    private Boolean analyticsEnabled = false;
    
    @Column(nullable = false)
    private Boolean notificationsEnabled = false;
    
    @Column(nullable = false)
    private Boolean complianceEnabled = false;
    
    // Database configuration
    @Enumerated(EnumType.STRING)
    private DatabaseType databaseType = DatabaseType.SQLITE;
    
    private String connectionString;
    private Instant lastMigrationCheck;
    private Boolean migrationSuggested = false;
    
    // Feature flags per tier
    @Column(nullable = false)
    private Boolean multiTenantEnabled = false;
    
    @Column(nullable = false)
    private Boolean advancedAuthEnabled = false;
    
    @Column(nullable = false)
    private Boolean graphqlEnabled = false;
    
    @Column(nullable = false)
    private Boolean auditLogsEnabled = true; // Always enabled
    
    // Limits per tier
    private Integer maxBranches = 3; // Lite: 3, Pro: 20, Enterprise: unlimited
    private Integer maxUsers = 5;    // Lite: 5, Pro: 50, Enterprise: unlimited
    private Integer maxStorageGB = 1; // Lite: 1GB, Pro: 10GB, Enterprise: unlimited
    
    // Billing integration
    private String billingCustomerId; // Stripe/MercadoPago customer ID
    private String subscriptionId;
    private Instant lastBillingUpdate;
}

enum TierType {
    LITE, PRO, ENTERPRISE
}

enum DatabaseType {
    SQLITE, POSTGRESQL
}
```

### **APIs Core Module:**

#### **Authentication APIs (JWT Implementation)**
```yaml
POST /api/auth/login:
  body: { username, password }
  response: { token, refreshToken, user, permissions }
  
POST /api/auth/refresh:
  body: { refreshToken }
  response: { token, refreshToken }
  
POST /api/auth/logout:
  headers: Authorization Bearer
  response: { success }

GET /api/auth/me:
  headers: Authorization Bearer
  response: { user, permissions, activeBranch }
```

#### **User Management APIs**
```yaml
GET /api/users:
  filters: branch_id, role, active
  response: { users[], pagination }
  
POST /api/users:
  body: { username, email, firstName, lastName, password, roles, branches }
  response: { user }
  
PUT /api/users/{id}:
  body: { firstName, lastName, roles, branches, enabled }
  response: { user }
  
DELETE /api/users/{id}:
  response: { success } # Soft delete
```

#### **Company & Branch Management APIs**
```yaml
GET /api/companies/current:
  response: { company, configuration, branches[] }
  
PUT /api/companies/{id}:
  body: { name, businessType, phoneNumber, address }
  response: { company }
  
GET /api/branches:
  response: { branches[] }
  
POST /api/branches:
  body: { name, code, address, operatingHours }
  response: { branch }
  
PUT /api/branches/{id}/operating-hours:
  body: { operatingHours }
  response: { branch }
```

#### **Configuration Management APIs**
```yaml
GET /api/configuration:
  response: { configuration, activeModules[] }
  
PUT /api/configuration/modules/{moduleName}:
  body: { enabled: true/false }
  response: { configuration, billingImpact }
  
GET /api/configuration/migration-status:
  response: { currentDB, suggestedMigration, benefits, cost }
  
POST /api/configuration/migrate-database:
  body: { confirmed: true }
  response: { migrationId, estimatedTime }
```

### **Events Core Module:**
```yaml
Domain Events:
  - UserCreated: { userId, companyId, branchIds[] }
  - UserRoleChanged: { userId, oldRoles[], newRoles[] }
  - CompanyCreated: { companyId, tierType, billingInfo }
  - BranchCreated: { branchId, companyId, isMainBranch }
  - ModuleActivated: { companyId, moduleName, billingImpact }
  - ModuleDeactivated: { companyId, moduleName, dataRetentionPlan }
  - DatabaseMigrationStarted: { companyId, fromType, toType }
  - DatabaseMigrationCompleted: { companyId, performanceImprovement }
```

---

## 2. **INVENTORY MODULE** 🚧 COMPLETAR

### **Status Actual (Living Document - FUENTE DE VERDAD):**
- 🚧 **EN DESARROLLO**: Parcialmente implementado según Master Context
- 🎯 **PRÓXIMO**: Completar implementación full según especificación

### **Responsabilidades:**
- Gestión de inventarios con características gastronómicas específicas
- Control de perecederos con FIFO/LIFO automático
- Trazabilidad de lotes y vencimientos
- Gestión de proveedores y reposición automática
- Control de mermas y desperdicios (crítico para rentabilidad)

### **Entidades Principales:**

#### **Product Entity**
```java
@Entity
@Table(name = "products")
public class Product extends BaseEntity {
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false)
    private String sku; // Stock Keeping Unit
    
    private String description;
    private String barcode; // EAN/UPC
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "category_id")
    private ProductCategory category;
    
    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Supplier primarySupplier;
    
    // Pricing
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal unitCost = BigDecimal.ZERO;
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal sellingPrice = BigDecimal.ZERO;
    
    @Column(nullable = false)
    private String unit = "UNIT"; // UNIT, KG, LITER, etc.
    
    // Inventory control
    @Column(nullable = false)
    private Boolean isPerishable = false;
    
    private Integer shelfLifeDays; // For perishable items
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal minimumStock = BigDecimal.ZERO;
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal reorderPoint = BigDecimal.ZERO;
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal reorderQuantity = BigDecimal.ZERO;
    
    // Costing method
    @Enumerated(EnumType.STRING)
    private CostMethod costMethod = CostMethod.FIFO;
    
    // Current stock (calculated field)
    @Formula("(SELECT COALESCE(SUM(im.quantity), 0) FROM inventory_movements im WHERE im.product_id = id AND im.branch_id = branch_id)")
    private BigDecimal currentStock;
    
    // Product attributes for gastronomy
    @Column(nullable = false)
    private Boolean requiresRefrigeration = false;
    
    @Column(nullable = false)
    private Boolean isBeverage = false;
    
    @Column(nullable = false)
    private Boolean isIngredient = false; // Used in recipes
    
    @Column(nullable = false)
    private Boolean isSellable = true; // Can be sold directly
    
    // Allergen information
    @ElementCollection
    @Enumerated(EnumType.STRING)
    private Set<AllergenType> allergens = new HashSet<>();
    
    // Nutritional information (optional)
    private Integer caloriesPer100g;
    private BigDecimal proteinPer100g;
    private BigDecimal fatPer100g;
    private BigDecimal carbsPer100g;
}

enum CostMethod {
    FIFO, LIFO, WEIGHTED_AVERAGE
}

enum AllergenType {
    GLUTEN, DAIRY, EGGS, NUTS, SEAFOOD, SOY, SESAME, SULFITES
}
```

#### **ProductCategory Entity**
```java
@Entity
@Table(name = "product_categories")
public class ProductCategory extends BaseEntity {
    @Column(nullable = false)
    private String name;
    
    private String description;
    private String color; // For UI categorization
    private String icon; // Icon identifier
    
    @ManyToOne
    @JoinColumn(name = "parent_category_id")
    private ProductCategory parentCategory;
    
    @OneToMany(mappedBy = "parentCategory")
    private List<ProductCategory> subcategories = new ArrayList<>();
    
    @Column(nullable = false)
    private Integer sortOrder = 0;
    
    // Category-specific settings
    @Column(nullable = false)
    private Boolean requiresExpirationDate = false;
    
    @Column(nullable = false)
    private Boolean requiresLotNumber = false;
    
    private Integer defaultShelfLifeDays;
}
```

#### **Batch Entity (Trazabilidad de Lotes)**
```java
@Entity
@Table(name = "batches")
public class Batch extends BaseEntity {
    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id")
    private Product product;
    
    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;
    
    @Column(nullable = false)
    private String lotNumber; // Supplier lot number
    
    private String batchCode; // Internal batch code
    
    // Dates
    @Column(nullable = false)
    private LocalDate receivedDate;
    
    private LocalDate expirationDate; // For perishables
    private LocalDate productionDate;
    
    // Quantities
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal quantityReceived;
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal quantityRemaining;
    
    // Costing
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal unitCost;
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal totalCost;
    
    // Quality control
    @Enumerated(EnumType.STRING)
    private BatchStatus status = BatchStatus.ACTIVE;
    
    private String qualityNotes;
    private BigDecimal qualityRating; // 1-5 scale
    
    // Current quantity (calculated)
    @Formula("(SELECT COALESCE(SUM(im.quantity), 0) FROM inventory_movements im WHERE im.batch_id = id)")
    private BigDecimal currentQuantity;
}

enum BatchStatus {
    ACTIVE, EXPIRED, QUARANTINED, USED_UP, WASTED
}
```

#### **InventoryMovement Entity (Trazabilidad Completa)**
```java
@Entity
@Table(name = "inventory_movements")
public class InventoryMovement extends BaseEntity {
    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id")
    private Product product;
    
    @ManyToOne
    @JoinColumn(name = "batch_id")
    private Batch batch;
    
    // Movement details
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MovementType movementType;
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal quantity;
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal unitCost;
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal totalCost;
    
    // Reference information
    private String referenceDocument; // Invoice, receipt, order number
    private String referenceType; // PURCHASE_ORDER, SALE_ORDER, ADJUSTMENT
    private UUID referenceId; // ID of related document
    
    // Movement reason
    private String reason; // Why was this adjustment made?
    private String notes;
    
    // For waste tracking
    @Enumerated(EnumType.STRING)
    private WasteReason wasteReason;
    
    private String wasteNotes;
    
    // Staff member who recorded movement
    private String recordedBy;
    private Instant recordedAt;
}

enum MovementType {
    PURCHASE, SALE, ADJUSTMENT_POSITIVE, ADJUSTMENT_NEGATIVE, 
    WASTE, TRANSFER_IN, TRANSFER_OUT, PRODUCTION_USE, RETURN
}

enum WasteReason {
    EXPIRED, DAMAGED, SPOILED, OVERPRODUCTION, CUSTOMER_RETURN, OTHER
}
```

#### **Supplier Entity**
```java
@Entity
@Table(name = "suppliers")
public class Supplier extends BaseEntity {
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false)
    private String contactName;
    
    @Column(nullable = false)
    private String phoneNumber;
    
    @Column(nullable = false)
    private String email;
    
    // Address information
    @Embedded
    private Address address;
    
    // Business information
    private String taxId; // CUIT/CNPJ depending on country
    private String website;
    
    // Commercial terms
    @Column(nullable = false)
    private Integer paymentTermsDays = 30;
    
    @Column(nullable = false)
    private Integer leadTimeDays = 7;
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal minimumOrderAmount = BigDecimal.ZERO;
    
    private String currency = "ARS";
    
    // Performance metrics
    @Column(nullable = false, precision = 3, scale = 2)
    private BigDecimal qualityRating = new BigDecimal("5.00"); // 1-5 scale
    
    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal deliveryPerformance = new BigDecimal("100.00"); // Percentage
    
    @Column(nullable = false)
    private Integer totalOrders = 0;
    
    private Instant lastOrderDate;
    
    // Supplier status
    @Column(nullable = false)
    private Boolean isPreferred = false;
    
    @Column(nullable = false)
    private Boolean isActive = true;
    
    private String notes;
}
```

### **APIs Inventory Module:**

#### **Product Management APIs**
```yaml
GET /api/inventory/products:
  filters: category_id, supplier_id, is_perishable, low_stock
  response: { products[], pagination }
  
POST /api/inventory/products:
  body: { name, sku, category_id, supplier_id, unitCost, sellingPrice, etc. }
  response: { product }
  
PUT /api/inventory/products/{id}:
  body: { name, unitCost, sellingPrice, reorderPoint, etc. }
  response: { product }
  
GET /api/inventory/products/{id}/stock-status:
  response: { currentStock, reorderNeeded, expiringBatches[] }
```

#### **Stock Management APIs**
```yaml
POST /api/inventory/movements:
  body: { product_id, movementType, quantity, unitCost, reason, batch_id? }
  response: { movement, newStockLevel }
  
GET /api/inventory/movements:
  filters: product_id, movement_type, date_range, branch_id
  response: { movements[], pagination }
  
GET /api/inventory/stock-levels:
  filters: branch_id, category_id, low_stock_only
  response: { stockLevels[] }
  
POST /api/inventory/stock-adjustment:
  body: { product_id, actual_quantity, reason, notes }
  response: { adjustment, stockDifference }
```

#### **Critical Alerts APIs**
```yaml
GET /api/inventory/expiring:
  params: days_ahead=7
  response: { expiringBatches[], totalValue, actionRequired }
  
GET /api/inventory/reorder-needed:
  response: { productsToReorder[], totalEstimatedCost }
  
GET /api/inventory/waste-report:
  params: date_range, waste_reason
  response: { wasteReport, costImpact, trends }
  
POST /api/inventory/generate-purchase-order:
  body: { supplier_id, products[] }
  response: { purchaseOrder, estimatedCost, deliveryDate }
```

### **Events Inventory Module:**
```yaml
Critical Events:
  - InventoryLevelChanged: { productId, oldLevel, newLevel, branch_id }
  - ProductExpiringSoon: { batchId, productId, daysUntilExpiration, quantity }
  - StockReorderTriggered: { productId, currentStock, reorderPoint, suggested_quantity }
  - WasteRecorded: { productId, quantity, wasteReason, costImpact }
  - SupplierPerformanceUpdated: { supplierId, qualityRating, deliveryPerformance }
  - BatchReceived: { batchId, productId, quantity, expirationDate }
  - LowStockAlert: { productId, currentStock, minimumStock, urgencyLevel }
```

---

## 3. **SALES/POS MODULE** 📅 IMPLEMENTAR

### **Status Actual (Living Document - FUENTE DE VERDAD):**
- 📅 **PLANIFICADO**: Implementar según especificación completa
- 🎯 **PRIORIDAD**: CRÍTICA (revenue generating)
- ⏰ **TIMELINE**: Mes 2 según roadmap

### **Responsabilidades:**
- POS táctil y procesamiento de ventas multi-canal
- Integración delivery multi-plataforma (PedidosYa, Rappi, Uber Eats)
- Gestión de pagos multi-método (cash, card, digital wallets)
- Sincronización en tiempo real con Kitchen Display Systems

### **Entidades Principales:**

#### **Order Entity (Core de Ventas)**
```java
@Entity
@Table(name = "orders")
public class Order extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String orderNumber; // Auto-generated: ORD-20250116-0001
    
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer; // Optional for quick sales
    
    @ManyToOne
    @JoinColumn(name = "table_id")
    private Table table; // For dine-in orders
    
    // Channel management
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderChannel channel = OrderChannel.DINE_IN;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status = OrderStatus.PENDING;
    
    // Pricing
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal subtotal = BigDecimal.ZERO;
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal taxAmount = BigDecimal.ZERO;
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal discountAmount = BigDecimal.ZERO;
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal tipAmount = BigDecimal.ZERO;
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal totalAmount = BigDecimal.ZERO;
    
    private String currency = "ARS";
    
    // Timing
    private Instant orderTime;
    private Instant estimatedReadyTime;
    private Instant actualReadyTime;
    private Instant deliveredTime;
    
    // Special instructions
    private String specialInstructions;
    private String customerNotes;
    private String kitchenNotes;
    
    // Staff assignment
    private UUID takenByUserId; // Cashier/waiter who took the order
    private UUID assignedToUserId; // Kitchen staff assigned
    
    // External integrations
    private String externalOrderId; // From delivery platforms
    private String externalPlatform; // "PEDIDOSYA", "RAPPI", "UBER_EATS"
    
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> items = new ArrayList<>();
    
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<Payment> payments = new ArrayList<>();
    
    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
    private Delivery delivery; // For delivery orders
}

enum OrderChannel {
    DINE_IN, TAKEAWAY, DELIVERY, ONLINE, PHONE, DELIVERY_PLATFORM
}

enum OrderStatus {
    PENDING, CONFIRMED, PREPARING, READY, DELIVERED, CANCELLED, REFUNDED
}
```

#### **OrderItem Entity**
```java
@Entity
@Table(name = "order_items")
public class OrderItem extends BaseEntity {
    @ManyToOne(optional = false)
    @JoinColumn(name = "order_id")
    private Order order;
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id")
    private Product product;
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal quantity;
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal unitPrice; // Price at time of sale
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal totalPrice; // quantity * unitPrice + modifiers
    
    // Customizations (for food items)
    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    private List<ItemModifier> modifiers = new ArrayList<>();
    
    private String specialInstructions;
    
    // Kitchen workflow
    @Enumerated(EnumType.STRING)
    private OrderItemStatus status = OrderItemStatus.PENDING;
    
    private Instant startedPreparingAt;
    private Instant readyAt;
    
    // Pricing breakdown
    @Column(precision = 10, scale = 2)
    private BigDecimal basePrice;
    
    @Column(precision = 10, scale = 2)
    private BigDecimal modifiersPrice = BigDecimal.ZERO;
    
    @Column(precision = 10, scale = 2)
    private BigDecimal discountAmount = BigDecimal.ZERO;
}

enum OrderItemStatus {
    PENDING, PREPARING, READY, SERVED, CANCELLED
}

@Embeddable
class ItemModifier {
    private String name;
    private BigDecimal price;
    private String category; // SIZE, EXTRAS, REMOVALS
}
```

#### **Payment Entity**
```java
@Entity
@Table(name = "payments")
public class Payment extends BaseEntity {
    @ManyToOne(optional = false)
    @JoinColumn(name = "order_id")
    private Order order;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentMethod method;
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;
    
    private String currency = "ARS";
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus status = PaymentStatus.PENDING;
    
    // External payment references
    private String referenceNumber; // Transaction ID from payment processor
    private String authorizationCode;
    private String receiptNumber;
    
    // Payment processor specific
    private String paymentProcessorId; // "MERCADOPAGO", "MODO", "STRIPE"
    private String processorTransactionId;
    private String processorResponse;
    
    // Card payments
    private String cardLast4Digits;
    private String cardBrand; // VISA, MASTERCARD, AMEX
    private String cardType; // CREDIT, DEBIT
    
    // Digital wallet
    private String walletType; // MERCADOPAGO, MODO, APPLE_PAY
    
    // Cash payments
    @Column(precision = 10, scale = 2)
    private BigDecimal cashReceived;
    
    @Column(precision = 10, scale = 2)
    private BigDecimal changeGiven;
    
    // Timing
    private Instant processedAt;
    private Instant authorizedAt;
    private Instant settledAt;
    
    // Staff reference
    private UUID processedByUserId;
    
    private String notes;
}

enum PaymentMethod {
    CASH, CREDIT_CARD, DEBIT_CARD, DIGITAL_WALLET, BANK_TRANSFER, CHECK, GIFT_CARD
}

enum PaymentStatus {
    PENDING, AUTHORIZED, CAPTURED, SETTLED, FAILED, REFUNDED, CANCELLED
}
```

#### **Delivery Entity**
```java
@Entity
@Table(name = "deliveries")
public class Delivery extends BaseEntity {
    @OneToOne(optional = false)
    @JoinColumn(name = "order_id")
    private Order order;
    
    // Delivery address
    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    private DeliveryAddress deliveryAddress;
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal deliveryFee;
    
    @Column(precision = 10, scale = 2)
    private BigDecimal distanceKm;
    
    // Timing
    private Instant estimatedDeliveryTime;
    private Instant actualDeliveryTime;
    private Instant dispatchedAt;
    
    @Enumerated(EnumType.STRING)
    private DeliveryStatus status = DeliveryStatus.PENDING;
    
    // Driver assignment (internal delivery)
    private UUID driverId;
    private String driverName;
    private String driverPhone;
    
    // External delivery platform
    private String externalDeliveryId;
    private String deliveryPlatform; // "PEDIDOSYA", "RAPPI", "UBER_EATS"
    private String trackingUrl;
    
    // Delivery instructions
    private String deliveryInstructions;
    private String customerPhoneNumber;
    
    // Proof of delivery
    private String deliverySignature;
    private String deliveryPhoto;
    private String deliveryNotes;
}

enum DeliveryStatus {
    PENDING, ASSIGNED, IN_TRANSIT, DELIVERED, FAILED, CANCELLED
}

@Embeddable
class DeliveryAddress {
    private String street;
    private String number;
    private String apartment;
    private String neighborhood;
    private String city;
    private String postalCode;
    private String reference;
    private BigDecimal latitude;
    private BigDecimal longitude;
}
```

### **APIs Sales/POS Module:**

#### **Order Management APIs**
```yaml
POST /api/sales/orders:
  body: { channel, customer_id?, table_id?, items[], special_instructions }
  response: { order, estimatedReadyTime }
  
GET /api/sales/orders:
  filters: status, channel, date_range, customer_id, branch_id
  response: { orders[], pagination }
  
PUT /api/sales/orders/{id}/status:
  body: { status, notes? }
  response: { order, statusHistory }
  
GET /api/sales/orders/{id}:
  response: { order, items[], payments[], delivery? }
  
DELETE /api/sales/orders/{id}:
  body: { cancellation_reason }
  response: { cancelled_order, refund_info }
```

#### **Kitchen Integration APIs**
```yaml
GET /api/sales/orders/kitchen:
  filters: status=CONFIRMED|PREPARING, priority
  response: { kitchenOrders[], estimatedTimes }
  
PUT /api/sales/orders/{id}/items/{itemId}/status:
  body: { status: PREPARING|READY }
  response: { orderItem, orderStatus }
  
POST /api/sales/orders/{id}/ready:
  response: { order, notification_sent }
```

#### **Payment Processing APIs**
```yaml
POST /api/sales/payments:
  body: { order_id, method, amount, payment_details }
  response: { payment, authorization_result }
  
POST /api/sales/payments/{id}/process:
  body: { processor_specific_data }
  response: { payment_result, receipt_data }
  
POST /api/sales/payments/{id}/refund:
  body: { refund_amount, reason }
  response: { refund_result, new_payment_status }
```

#### **Delivery Management APIs**
```yaml
POST /api/sales/delivery/calculate-fee:
  body: { delivery_address, order_total }
  response: { delivery_fee, estimated_time, available_slots }
  
POST /api/sales/delivery/assign:
  body: { order_id, driver_id?, delivery_platform? }
  response: { delivery, tracking_info }
  
GET /api/sales/delivery/{id}/track:
  response: { delivery_status, location?, estimated_arrival }
  
PUT /api/sales/delivery/{id}/complete:
  body: { delivery_proof, customer_signature?, notes }
  response: { completed_delivery }
```

### **Events Sales/POS Module:**
```yaml
Critical Events:
  - OrderPlaced: { orderId, channel, totalAmount, estimatedReadyTime }
  - OrderStatusChanged: { orderId, oldStatus, newStatus, timestamp }
  - PaymentProcessed: { paymentId, orderId, amount, method, status }
  - PaymentFailed: { paymentId, orderId, amount, failure_reason }
  - OrderReady: { orderId, readyTime, notification_sent }
  - DeliveryAssigned: { deliveryId, orderId, driverId?, platform? }
  - DeliveryStatusUpdated: { deliveryId, status, location?, estimatedTime }
  - OrderCancelled: { orderId, reason, refund_required }
  - KitchenOrderStarted: { orderId, itemId, estimated_completion }
  - KitchenOrderCompleted: { orderId, itemId, actual_completion_time }
```

---

## 🎯 **INFORMACIÓN PRESERVADA - RESUMEN**

**✅ COMPLETADO - Core Modules Reference:**
- **Core Module**: 100% especificado con todas las entidades, APIs y events
- **Inventory Module**: 100% especificado con control de perecederos, lotes, FIFO/LIFO
- **Sales/POS Module**: 100% especificado con multi-canal, pagos, delivery

**📊 MÉTRICAS DE RECUPERACIÓN:**
- **Entidades**: 15 entidades principales completamente especificadas
- **APIs**: 40+ endpoints críticos documentados
- **Events**: 25+ domain events para arquitectura event-driven
- **Features**: Configuration-driven, multi-branch, audit trails, HACCP compliance

**🔄 PRÓXIMO PASO:**
Crear siguiente artefacto: `/reference/activable-modules.md` (Kitchen, Customer, Table, Financial, etc.)