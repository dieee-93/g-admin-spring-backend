# G-ADMIN Reference: Módulos Activables

**Documento**: /reference/activable-modules.md  
**Clasificación**: MEDIA VOLATILIDAD (especificaciones que evolucionan)  
**Fuente**: Consolidación Master Context Enriquecido + v3.1 + Living Document  
**Status**: RECOVERED - Phase 2 Activable Modules

---

## 💰 **MÓDULOS PAY-PER-MODULE**

Estos módulos se pueden **activar/desactivar** por configuración según el tier del cliente y generar ingresos adicionales.

### **Dependency Matrix (Auto-Resolution):**
```yaml
Kitchen Management: [sales, inventory, menu]  # Full restaurant workflow
Customer Management: [sales]                  # Needs sales for customer tracking
Table Management: [customer]                  # Reservations need customers
Financial Management: [sales, inventory]      # Cost analysis needs both
Staff Management: [core]                     # Only needs user management
Advanced Analytics: [sales, inventory]       # Needs data to analyze
Notifications: [customer]                    # Needs customers to notify
Compliance: [kitchen, staff]                # Enterprise only - full operations
```

---

## 4. **KITCHEN MANAGEMENT MODULE** 💰 ACTIVABLE

### **Business Value:**
- **Pricing Tier**: Pro+ ($15/mes additional)
- **Value Proposition**: Reduce kitchen wait times by 40%, recipe costing automation
- **Configuration Key**: `modules.kitchen.enabled`
- **Dependencies**: sales, inventory, menu modules (auto-activated)

### **Responsabilidades:**
- Optimización del flujo de cocina con algoritmos inteligentes
- Gestión de recetas con control de costos automático
- Coordinación entre estaciones de trabajo
- Control de tiempos y capacidad en tiempo real
- Integración con Kitchen Display Systems (KDS)

### **Entidades Principales:**

#### **Recipe Entity**
```java
@Entity
@Table(name = "recipes")
public class Recipe extends BaseEntity {
    @Column(nullable = false)
    private String name;
    
    private String description;
    
    @ManyToOne
    @JoinColumn(name = "category_id")
    private RecipeCategory category;
    
    // Timing
    @Column(nullable = false)
    private Integer preparationTimeMinutes = 0;
    
    @Column(nullable = false)
    private Integer cookingTimeMinutes = 0;
    
    @Column(nullable = false)
    private Integer totalTimeMinutes = 0; // prep + cooking
    
    // Difficulty and yield
    @Enumerated(EnumType.STRING)
    private DifficultyLevel difficultyLevel = DifficultyLevel.MEDIUM;
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal yieldQuantity = BigDecimal.ONE; // Number of portions
    
    private String yieldUnit = "PORTION";
    
    // Instructions
    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    private List<RecipeStep> instructions = new ArrayList<>();
    
    // Costing (calculated)
    @Column(precision = 10, scale = 2)
    private BigDecimal costPerPortion;
    
    @Column(precision = 10, scale = 2)
    private BigDecimal laborCostPerPortion;
    
    @Column(precision = 10, scale = 2)
    private BigDecimal totalCostPerPortion;
    
    private Instant lastCostCalculation;
    
    // Recipe status
    @Column(nullable = false)
    private Boolean isActive = true;
    
    @Column(nullable = false)
    private Boolean isPublic = false; // Can be used by other branches
    
    private String chefNotes;
    
    // Nutritional information (optional)
    private Integer caloriesPerPortion;
    private BigDecimal proteinPerPortion;
    private BigDecimal fatPerPortion;
    private BigDecimal carbsPerPortion;
    
    // Allergen information
    @ElementCollection
    @Enumerated(EnumType.STRING)
    private Set<AllergenType> allergens = new HashSet<>();
    
    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL)
    private List<RecipeIngredient> ingredients = new ArrayList<>();
    
    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL)
    private List<RecipeEquipment> requiredEquipment = new ArrayList<>();
}

enum DifficultyLevel {
    EASY, MEDIUM, HARD, EXPERT
}

@Embeddable
class RecipeStep {
    private Integer stepNumber;
    private String instruction;
    private Integer timeMinutes;
    private String station; // PREP, GRILL, FRYER, etc.
    private String equipment;
    private String temperature;
    private String notes;
}
```

#### **RecipeIngredient Entity**
```java
@Entity
@Table(name = "recipe_ingredients")
public class RecipeIngredient extends BaseEntity {
    @ManyToOne(optional = false)
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id")
    private Product product;
    
    @Column(nullable = false, precision = 10, scale = 4)
    private BigDecimal quantity;
    
    @Column(nullable = false)
    private String unit; // Same as product unit or converted
    
    // Preparation method
    private String preparationMethod; // "diced", "sliced", "whole", etc.
    
    @Column(nullable = false)
    private Boolean isCritical = false; // Cannot be substituted
    
    @Column(nullable = false)
    private Boolean isOptional = false;
    
    // Alternative ingredients
    @ManyToOne
    @JoinColumn(name = "substitute_product_id")
    private Product substituteProduct;
    
    private String notes;
    
    // Calculated cost (updated when product cost changes)
    @Column(precision = 10, scale = 2)
    private BigDecimal costAtCalculation;
    
    private Instant lastCostUpdate;
}
```

#### **KitchenStation Entity**
```java
@Entity
@Table(name = "kitchen_stations")
public class KitchenStation extends BaseEntity {
    @Column(nullable = false)
    private String name;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StationType stationType;
    
    private String description;
    
    // Capacity management
    @Column(nullable = false)
    private Integer maxConcurrentOrders = 5;
    
    @Column(nullable = false)
    private Integer currentLoad = 0; // Updated in real-time
    
    // Equipment and capabilities
    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    private List<Equipment> equipment = new ArrayList<>();
    
    // Staff assignment
    @ElementCollection
    private Set<UUID> assignedStaffIds = new HashSet<>();
    
    @Column(nullable = false)
    private Integer requiredStaffCount = 1;
    
    // Performance metrics
    @Column(precision = 5, scale = 2)
    private BigDecimal averageOrderTimeMinutes = new BigDecimal("15.00");
    
    @Column(precision = 3, scale = 2)
    private BigDecimal efficiencyRating = new BigDecimal("1.00"); // 0-2 scale
    
    // Station status
    @Column(nullable = false)
    private Boolean isActive = true;
    
    @Column(nullable = false)
    private Boolean isOperational = true;
    
    private String statusNotes;
    
    // Physical location
    private String location; // "Kitchen Left", "Kitchen Center", etc.
    private Integer displayOrder = 0;
}

enum StationType {
    PREP, GRILL, FRYER, SAUTE, SALAD, DESSERT, EXPEDITE, DISHWASH, BEVERAGE
}

@Embeddable
class Equipment {
    private String name;
    private String model;
    private Boolean isWorking = true;
    private String maintenanceNotes;
}
```

#### **KitchenOrder Entity**
```java
@Entity
@Table(name = "kitchen_orders")
public class KitchenOrder extends BaseEntity {
    @ManyToOne(optional = false)
    @JoinColumn(name = "order_id")
    private Order order;
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "order_item_id")
    private OrderItem orderItem;
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "station_id")
    private KitchenStation station;
    
    @ManyToOne
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal quantity;
    
    // Priority management
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Priority priority = Priority.NORMAL;
    
    @Column(nullable = false)
    private Integer priorityScore = 50; // 0-100, higher = more urgent
    
    // Status tracking
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private KitchenOrderStatus status = KitchenOrderStatus.QUEUED;
    
    // Timing
    private Instant queuedAt;
    private Instant startedAt;
    private Instant estimatedCompletionAt;
    private Instant actualCompletionAt;
    
    @Column(nullable = false)
    private Integer estimatedMinutes = 15;
    
    // Staff assignment
    private UUID assignedStaffId;
    private String assignedStaffName;
    
    // Special instructions
    private String preparationNotes;
    private String specialInstructions;
    
    // Dependencies (for complex orders)
    @ManyToOne
    @JoinColumn(name = "depends_on_kitchen_order_id")
    private KitchenOrder dependsOn;
    
    @OneToMany(mappedBy = "dependsOn")
    private List<KitchenOrder> dependentOrders = new ArrayList<>();
}

enum Priority {
    LOW, NORMAL, HIGH, URGENT, RUSH
}

enum KitchenOrderStatus {
    QUEUED, PREPARING, READY, EXPEDITED, CANCELLED, ON_HOLD
}
```

### **APIs Kitchen Management Module:**

#### **Recipe Management APIs**
```yaml
GET /api/kitchen/recipes:
  filters: category_id, difficulty, active_only, search
  response: { recipes[], pagination }
  
POST /api/kitchen/recipes:
  body: { name, category_id, preparationTime, ingredients[], instructions[] }
  response: { recipe, calculatedCost }
  
PUT /api/kitchen/recipes/{id}/cost-calculation:
  response: { recipe, newCostPerPortion, costBreakdown }
  
GET /api/kitchen/recipes/{id}/cost-analysis:
  response: { costBreakdown, profitability, recommendations }
```

#### **Kitchen Operations APIs**
```yaml
GET /api/kitchen/orders/queue:
  filters: station_id, priority, status
  response: { kitchenOrders[], totalEstimatedTime }
  
POST /api/kitchen/orders/{id}/start:
  body: { assigned_staff_id, estimated_minutes? }
  response: { kitchenOrder, stationLoad, queueImpact }
  
PUT /api/kitchen/orders/{id}/complete:
  body: { actual_completion_time, quality_notes? }
  response: { kitchenOrder, performanceMetrics }
  
GET /api/kitchen/stations/performance:
  params: date_range, station_id?
  response: { stations[], metrics, bottlenecks[] }
```

#### **Station Management APIs**
```yaml
GET /api/kitchen/stations:
  filters: station_type, is_operational, branch_id
  response: { stations[], currentLoads, staffAssignments }
  
PUT /api/kitchen/stations/{id}/staff:
  body: { assigned_staff_ids[] }
  response: { station, staffingLevel, capabilities }
  
POST /api/kitchen/stations/{id}/status:
  body: { is_operational, status_notes }
  response: { station, impactedOrders[] }
```

### **Events Kitchen Management Module:**
```yaml
Critical Events:
  - KitchenOrderStarted: { kitchenOrderId, stationId, staffId, estimatedCompletion }
  - KitchenOrderCompleted: { kitchenOrderId, actualTime, estimatedTime, efficiency }
  - StationOverloaded: { stationId, currentLoad, maxCapacity, delayImpact }
  - RecipeCostChanged: { recipeId, oldCost, newCost, marginImpact }
  - StationStatusChanged: { stationId, isOperational, impactedOrders[] }
  - PriorityOrderQueued: { kitchenOrderId, priority, queuePosition }
  - StationEfficiencyAlert: { stationId, efficiencyRating, trends }
```

---

## 5. **CUSTOMER MANAGEMENT MODULE** 💰 ACTIVABLE

### **Business Value:**
- **Pricing Tier**: Pro+ ($10/mes additional)
- **Value Proposition**: Increase customer retention by 25%, automated loyalty programs
- **Configuration Key**: `modules.customer.enabled`
- **Dependencies**: sales module (auto-activated)

### **Responsabilidades:**
- CRM gastronómico especializado con segmentación automática
- Programas de fidelización configurables
- Tracking de comportamiento y preferencias
- Personalización de experiencias y ofertas
- Análisis Customer Lifetime Value (CLV)

### **Entidades Principales:**

#### **Customer Entity**
```java
@Entity
@Table(name = "customers")
public class Customer extends BaseEntity {
    @Column(nullable = false)
    private String firstName;
    
    @Column(nullable = false)
    private String lastName;
    
    @Column(nullable = false, unique = true)
    private String email;
    
    @Column(nullable = false)
    private String phoneNumber;
    
    private LocalDate birthdate;
    private String gender; // M, F, Other, Prefer not to say
    
    // Preferences and restrictions
    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    private List<String> dietaryPreferences = new ArrayList<>(); // vegetarian, vegan, etc.
    
    @ElementCollection
    @Enumerated(EnumType.STRING)
    private Set<AllergenType> allergies = new HashSet<>();
    
    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    private Map<String, Object> preferences = new HashMap<>(); // spice level, favorite items, etc.
    
    // Address information
    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    private List<CustomerAddress> addresses = new ArrayList<>();
    
    // Loyalty program
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal loyaltyPoints = BigDecimal.ZERO;
    
    @Enumerated(EnumType.STRING)
    private LoyaltyTier loyaltyTier = LoyaltyTier.BRONZE;
    
    private Instant loyaltyJoinDate;
    
    // Spending and visit metrics
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal totalSpent = BigDecimal.ZERO;
    
    @Column(nullable = false)
    private Integer totalOrders = 0;
    
    @Column(nullable = false)
    private Integer totalVisits = 0;
    
    @Column(precision = 10, scale = 2)
    private BigDecimal averageOrderValue = BigDecimal.ZERO;
    
    private Instant lastVisit;
    private Instant lastOrder;
    
    // Behavioral data
    @ManyToOne
    @JoinColumn(name = "preferred_branch_id")
    private Branch preferredBranch;
    
    @Enumerated(EnumType.STRING)
    private OrderChannel preferredChannel = OrderChannel.DINE_IN;
    
    private String preferredOrderTime; // "lunch", "dinner", "late_night"
    
    // Marketing preferences
    @Column(nullable = false)
    private Boolean emailMarketing = true;
    
    @Column(nullable = false)
    private Boolean smsMarketing = true;
    
    @Column(nullable = false)
    private Boolean pushNotifications = true;
    
    private String preferredLanguage = "es";
    
    // Customer lifecycle
    @Enumerated(EnumType.STRING)
    private CustomerStatus status = CustomerStatus.ACTIVE;
    
    @Enumerated(EnumType.STRING)
    private CustomerSegment segment = CustomerSegment.REGULAR;
    
    private Instant segmentLastUpdated;
    
    // External integrations
    private String externalCustomerId; // From delivery platforms
    private String socialMediaHandle;
    
    // Notes and tags
    private String notes;
    
    @ElementCollection
    private Set<String> tags = new HashSet<>(); // "VIP", "Complainer", "Influencer", etc.
}

enum LoyaltyTier {
    BRONZE, SILVER, GOLD, PLATINUM, VIP
}

enum CustomerStatus {
    ACTIVE, INACTIVE, CHURNED, BLACKLISTED
}

enum CustomerSegment {
    NEW, REGULAR, VIP, AT_RISK, CHURNED, HIGH_VALUE, PRICE_SENSITIVE
}

@Embeddable
class CustomerAddress {
    private String type; // HOME, WORK, OTHER
    private String street;
    private String number;
    private String apartment;
    private String neighborhood;
    private String city;
    private String postalCode;
    private String reference;
    private Boolean isDefault = false;
    private BigDecimal latitude;
    private BigDecimal longitude;
}
```

#### **LoyaltyProgram Entity**
```java
@Entity
@Table(name = "loyalty_programs")
public class LoyaltyProgram extends BaseEntity {
    @Column(nullable = false)
    private String name;
    
    private String description;
    
    // Program configuration
    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal pointsPerDollar = BigDecimal.ONE; // Points earned per dollar spent
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal dollarValuePerPoint = new BigDecimal("0.01"); // Redemption value
    
    // Tier configuration
    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    private Map<LoyaltyTier, TierConfiguration> tierThresholds = new HashMap<>();
    
    // Benefits configuration
    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    private Map<LoyaltyTier, List<String>> tierBenefits = new HashMap<>();
    
    // Program rules
    @Column(nullable = false)
    private Integer pointsExpirationMonths = 12;
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal minimumRedemptionPoints = new BigDecimal("100");
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal maximumRedemptionPercentage = new BigDecimal("50.00"); // Max % of order to pay with points
    
    // Special promotions
    @Column(nullable = false)
    private Boolean birthdayBonus = true;
    
    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal birthdayMultiplier = new BigDecimal("2.00");
    
    @Column(nullable = false)
    private Boolean referralBonus = true;
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal referralPoints = new BigDecimal("500");
    
    // Program status
    @Column(nullable = false)
    private Boolean isActive = true;
    
    private Instant startDate;
    private Instant endDate;
}

@Embeddable
class TierConfiguration {
    private BigDecimal spendingThreshold;
    private Integer ordersThreshold;
    private BigDecimal pointsMultiplier;
    private String tierColor;
    private String tierIcon;
}
```

#### **LoyaltyTransaction Entity**
```java
@Entity
@Table(name = "loyalty_transactions")
public class LoyaltyTransaction extends BaseEntity {
    @ManyToOne(optional = false)
    @JoinColumn(name = "customer_id")
    private Customer customer;
    
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LoyaltyTransactionType transactionType;
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal points;
    
    @Column(precision = 10, scale = 2)
    private BigDecimal dollarValue; // For redemptions
    
    private String description;
    
    // Expiration tracking
    private Instant expiresAt;
    
    @Column(nullable = false)
    private Boolean isExpired = false;
    
    // Reference information
    private String referenceType; // ORDER, BIRTHDAY, REFERRAL, MANUAL, etc.
    private String referenceId;
    
    // Balances after transaction
    @Column(precision = 10, scale = 2)
    private BigDecimal balanceAfterTransaction;
    
    private UUID processedByUserId;
}

enum LoyaltyTransactionType {
    EARNED, REDEEMED, EXPIRED, ADJUSTED, BONUS, REFERRAL, BIRTHDAY
}
```

#### **CustomerSegment Entity (Dynamic Segmentation)**
```java
@Entity
@Table(name = "customer_segments")
public class CustomerSegmentDefinition extends BaseEntity {
    @Column(nullable = false)
    private String name;
    
    private String description;
    private String color;
    
    // Segmentation criteria
    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    private SegmentCriteria criteria;
    
    @Column(nullable = false)
    private Boolean isAutomatic = true; // Auto-assign vs manual
    
    @Column(nullable = false)
    private Boolean isActive = true;
    
    // Current statistics
    @Column(nullable = false)
    private Integer customerCount = 0;
    
    private Instant lastCalculated;
    
    // Marketing settings
    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    private Map<String, Object> marketingSettings = new HashMap<>();
}

@Embeddable
class SegmentCriteria {
    private BigDecimal minTotalSpent;
    private BigDecimal maxTotalSpent;
    private Integer minOrders;
    private Integer maxOrders;
    private Integer daysSinceLastOrder;
    private LoyaltyTier loyaltyTier;
    private List<String> requiredTags;
    private List<String> excludedTags;
    private OrderChannel preferredChannel;
    private Integer minVisits;
    private BigDecimal minAverageOrderValue;
    private Integer daysSinceFirstOrder;
}
```

### **APIs Customer Management Module:**

#### **Customer Management APIs**
```yaml
GET /api/customers:
  filters: segment, loyalty_tier, status, search, last_visit_range
  response: { customers[], pagination, segmentStats }
  
POST /api/customers:
  body: { firstName, lastName, email, phoneNumber, preferences }
  response: { customer, loyaltyWelcomeBonus }
  
GET /api/customers/{id}/profile:
  response: { customer, orderHistory[], loyaltyStatus, recommendations }
  
PUT /api/customers/{id}/preferences:
  body: { dietaryPreferences, allergies, marketing_preferences }
  response: { customer, personalizationImpact }
  
GET /api/customers/{id}/analytics:
  response: { clv, orderPatterns, preferences, churnRisk }
```

#### **Loyalty Program APIs**
```yaml
POST /api/loyalty/earn:
  body: { customer_id, order_id, base_amount }
  response: { transaction, pointsEarned, newBalance, tierStatus }
  
POST /api/loyalty/redeem:
  body: { customer_id, points_to_redeem, order_id }
  response: { transaction, dollarValue, newBalance }
  
GET /api/loyalty/balance/{customer_id}:
  response: { currentBalance, expiringPoints[], tierStatus, nextTierRequirements }
  
POST /api/loyalty/special-bonus:
  body: { customer_id, bonus_type, points, reason }
  response: { transaction, newBalance, notification_sent }
```

#### **Segmentation APIs**
```yaml
GET /api/customers/segments:
  response: { segments[], customerCounts, trends }
  
POST /api/customers/segments:
  body: { name, criteria, marketing_settings }
  response: { segment, estimatedCustomers }
  
POST /api/customers/segments/{id}/recalculate:
  response: { segment, customersAdded, customersRemoved }
  
GET /api/customers/segments/{id}/members:
  response: { customers[], analytics, exportOptions }
```

### **Events Customer Management Module:**
```yaml
Customer Lifecycle Events:
  - CustomerRegistered: { customerId, source, loyaltyJoinBonus }
  - LoyaltyPointsEarned: { customerId, points, orderId, newBalance }
  - LoyaltyPointsRedeemed: { customerId, points, dollarValue, orderId }
  - LoyaltyTierUpgraded: { customerId, oldTier, newTier, newBenefits }
  - CustomerSegmentUpdated: { customerId, oldSegment, newSegment, triggers }
  - CustomerChurnRiskDetected: { customerId, riskScore, recommendedActions }
  - CustomerReactivated: { customerId, daysSinceLastOrder, reactivationMethod }
  - BirthdayBonusAwarded: { customerId, bonusPoints, personalizedOffer }
```

---

## 6. **TABLE MANAGEMENT MODULE** 💰 ACTIVABLE

### **Business Value:**
- **Pricing Tier**: Pro+ ($10/mes additional)
- **Value Proposition**: Optimize table turnover by 30%, reduce wait times
- **Configuration Key**: `modules.table.enabled`
- **Dependencies**: customer module (auto-activated)

### **Responsabilidades:**
- Gestión inteligente de mesas y reservas
- Optimización automática de ocupación y rotación
- Control de turnos y lista de espera dinámica
- Coordinación con delivery para capacidad total
- Análisis de patrones de ocupación y ingresos por mesa

### **Entidades Principales:**

#### **Table Entity**
```java
@Entity
@Table(name = "tables")
public class Table extends BaseEntity {
    @Column(nullable = false)
    private String tableNumber;
    
    @Column(nullable = false)
    private Integer capacity; // Maximum seats
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TableType tableType = TableType.REGULAR;
    
    // Physical attributes
    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    private TablePosition position; // x, y coordinates for floor plan
    
    @ElementCollection
    @Enumerated(EnumType.STRING)
    private Set<TableAmenity> amenities = new HashSet<>();
    
    // Current status
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TableStatus status = TableStatus.AVAILABLE;
    
    private Instant statusChangedAt;
    private UUID statusChangedByUserId;
    
    // Reservation tracking
    @OneToOne(mappedBy = "table")
    private Reservation currentReservation;
    
    @ManyToOne
    @JoinColumn(name = "current_order_id")
    private Order currentOrder;
    
    // Performance metrics
    @Column(precision = 5, scale = 2)
    private BigDecimal averageTurnoverMinutes = new BigDecimal("90.00");
    
    @Column(precision = 10, scale = 2)
    private BigDecimal averageRevenuePerTurn = BigDecimal.ZERO;
    
    @Column(nullable = false)
    private Integer turnsToday = 0;
    
    @Column(precision = 10, scale = 2)
    private BigDecimal revenueToday = BigDecimal.ZERO;
    
    // Table preferences
    @Column(nullable = false)
    private Boolean isVipTable = false;
    
    @Column(nullable = false)
    private Boolean allowsSmoking = false;
    
    @Column(nullable = false)
    private Boolean isAccessible = true;
    
    @Column(nullable = false)
    private Boolean hasView = false;
    
    private String location; // "Window", "Garden", "Main floor", etc.
    private String notes;
    
    // Operational settings
    @Column(nullable = false)
    private Boolean isActive = true;
    
    @Column(nullable = false)
    private Integer minimumPartySize = 1;
    
    @Column(nullable = false)
    private Integer maximumPartySize; // Usually same as capacity
    
    @Column(nullable = false)
    private Integer setupTimeMinutes = 5; // Time needed to clean and set up
}

enum TableType {
    REGULAR, BAR, BOOTH, OUTDOOR, PRIVATE_DINING, COUNTER, HIGH_TOP
}

enum TableStatus {
    AVAILABLE, OCCUPIED, RESERVED, CLEANING, OUT_OF_SERVICE
}

enum TableAmenity {
    WINDOW_VIEW, GARDEN_VIEW, QUIET_AREA, NEAR_BAR, WHEELCHAIR_ACCESSIBLE, 
    ELECTRICAL_OUTLET, FIREPLACE, TV_VISIBLE, ROMANTIC_SETTING
}

@Embeddable
class TablePosition {
    private Double x;
    private Double y;
    private String floor; // "Ground", "Second", "Terrace"
    private String section; // "A", "B", "VIP", "Bar"
}
```

#### **Reservation Entity**
```java
@Entity
@Table(name = "reservations")
public class Reservation extends BaseEntity {
    @ManyToOne(optional = false)
    @JoinColumn(name = "customer_id")
    private Customer customer;
    
    @ManyToOne
    @JoinColumn(name = "table_id")
    private Table table; // Null if not yet assigned
    
    // Reservation details
    @Column(nullable = false)
    private Instant reservationDateTime;
    
    @Column(nullable = false)
    private Integer partySize;
    
    @Column(nullable = false)
    private Integer durationMinutes = 120; // Estimated dining duration
    
    // Contact information
    @Column(nullable = false)
    private String contactName;
    
    @Column(nullable = false)
    private String contactPhone;
    
    private String contactEmail;
    
    // Reservation status
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReservationStatus status = ReservationStatus.PENDING;
    
    // Special requests
    private String specialRequests;
    private String dietaryRequirements;
    private String celebrationNote; // Birthday, anniversary, etc.
    
    // Table preferences
    @ElementCollection
    @Enumerated(EnumType.STRING)
    private Set<TableAmenity> preferredAmenities = new HashSet<>();
    
    @Enumerated(EnumType.STRING)
    private TableType preferredTableType;
    
    // Confirmation and reminders
    private Instant confirmedAt;
    private Boolean reminderSent = false;
    private Instant reminderSentAt;
    
    // Arrival tracking
    private Instant arrivedAt;
    private Instant seatedAt;
    private Instant departedAt;
    
    // No-show tracking
    @Column(nullable = false)
    private Integer noShowGracePeriodMinutes = 15;
    
    private Boolean isNoShow = false;
    private Instant noShowRecordedAt;
    
    // Related order
    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;
    
    // Revenue tracking
    @Column(precision = 10, scale = 2)
    private BigDecimal finalBillAmount;
    
    // Source tracking
    @Enumerated(EnumType.STRING)
    private ReservationSource source = ReservationSource.PHONE;
    
    private String sourceReference; // OpenTable ID, website booking ID, etc.
    
    // Staff assignment
    private UUID takenByUserId;
    private UUID assignedWaiterId;
    
    private String internalNotes;
}

enum ReservationStatus {
    PENDING, CONFIRMED, SEATED, COMPLETED, CANCELLED, NO_SHOW, MODIFIED
}

enum ReservationSource {
    PHONE, WEBSITE, WALK_IN, OPENTABLE, RESY, GOOGLE, SOCIAL_MEDIA, APP
}
```

#### **Waitlist Entity**
```java
@Entity
@Table(name = "waitlist")
public class Waitlist extends BaseEntity {
    @Column(nullable = false)
    private String customerName;
    
    @Column(nullable = false)
    private String customerPhone;
    
    private String customerEmail;
    
    @Column(nullable = false)
    private Integer partySize;
    
    // Wait time management
    @Column(nullable = false)
    private Instant joinedAt;
    
    @Column(nullable = false)
    private Integer estimatedWaitMinutes;
    
    private Instant estimatedSeatTime; // joinedAt + estimatedWaitMinutes
    
    @Column(nullable = false)
    private Integer position; // Current position in queue
    
    // Table preferences
    @ElementCollection
    @Enumerated(EnumType.STRING)
    private Set<TableAmenity> preferredAmenities = new HashSet<>();
    
    @Enumerated(EnumType.STRING)
    private TableType preferredTableType;
    
    // Status tracking
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private WaitlistStatus status = WaitlistStatus.WAITING;
    
    // Communication tracking
    @Column(nullable = false)
    private Boolean notificationSent = false;
    
    private Instant notificationSentAt;
    
    @Column(nullable = false)
    private Integer notificationMethod = 1; // 1=SMS, 2=Call, 3=Both
    
    @Column(nullable = false)
    private Boolean customerResponded = false;
    
    private Instant customerResponseAt;
    
    // Conversion tracking
    private UUID convertedToReservationId;
    private UUID assignedTableId;
    private Instant seatedAt;
    
    // Special notes
    private String specialRequests;
    private String notes;
    
    // Staff tracking
    private UUID addedByUserId;
    private UUID lastUpdatedByUserId;
}

enum WaitlistStatus {
    WAITING, NOTIFIED, READY, SEATED, CANCELLED, NO_RESPONSE, EXPIRED
}
```

#### **FloorPlan Entity**
```java
@Entity
@Table(name = "floor_plans")
public class FloorPlan extends BaseEntity {
    @Column(nullable = false)
    private String name;
    
    private String description;
    
    // Floor plan metadata
    @Column(nullable = false)
    private Integer totalTables;
    
    @Column(nullable = false)
    private Integer totalCapacity;
    
    // Layout configuration
    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    private FloorPlanLayout layout;
    
    // Operational schedule
    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    private Map<String, Boolean> activeDays = new HashMap<>(); // MON=true, TUE=true, etc.
    
    private LocalTime activeFromTime;
    private LocalTime activeToTime;
    
    // Status
    @Column(nullable = false)
    private Boolean isActive = true;
    
    @Column(nullable = false)
    private Boolean isDefault = false;
    
    private Instant lastModified;
    private UUID lastModifiedByUserId;
}

@Embeddable
class FloorPlanLayout {
    private Integer width;
    private Integer height;
    private String backgroundImage;
    private List<TableLayout> tableLayouts;
    private List<FloorElement> elements; // walls, doors, bar, etc.
}

@Embeddable
class TableLayout {
    private UUID tableId;
    private Double x;
    private Double y;
    private Double rotation;
    private String shape; // "circle", "square", "rectangle"
}
```

### **APIs Table Management Module:**

#### **Table Management APIs**
```yaml
GET /api/tables:
  filters: status, table_type, capacity_min, capacity_max, amenities
  response: { tables[], floorPlan, occupancyRate }
  
PUT /api/tables/{id}/status:
  body: { status, notes?, estimated_available_time? }
  response: { table, impactedReservations[], waitlistUpdates }
  
GET /api/tables/availability:
  params: date_time, party_size, duration_minutes?
  response: { availableTables[], nextAvailableTime, waitlistOption }
  
POST /api/tables/{id}/assign-order:
  body: { order_id }
  response: { table, order, estimatedTurnover }
```

#### **Reservation Management APIs**
```yaml
GET /api/reservations:
  filters: date_range, status, customer_id, table_id
  response: { reservations[], todayStats, upcomingAlerts }
  
POST /api/reservations:
  body: { customer_id, reservation_date_time, party_size, special_requests }
  response: { reservation, tableAssignment?, waitlistPosition? }
  
PUT /api/reservations/{id}/confirm:
  body: { confirmed_by_customer, table_assignment? }
  response: { reservation, confirmationSent, reminderScheduled }
  
PUT /api/reservations/{id}/seat:
  body: { table_id, actual_party_size? }
  response: { reservation, table, orderCreated, waitlistImpact }
  
POST /api/reservations/{id}/no-show:
  response: { reservation, tableReleased, waitlistNotified }
```

#### **Waitlist Management APIs**
```yaml
GET /api/waitlist:
  filters: status, party_size, join_time_range
  response: { waitlistEntries[], averageWaitTime, projectedSeating }
  
POST /api/waitlist:
  body: { customer_name, phone, party_size, preferences }
  response: { waitlistEntry, estimatedWaitTime, position }
  
PUT /api/waitlist/{id}/notify:
  body: { notification_method, custom_message? }
  response: { waitlistEntry, notificationSent, responseDeadline }
  
PUT /api/waitlist/{id}/seat:
  body: { table_id }
  response: { waitlistEntry, reservation?, table, order? }
```

#### **Analytics and Optimization APIs**
```yaml
GET /api/tables/analytics:
  params: date_range, table_id?
  response: { turnoverRates, revenuePerTable, occupancyPatterns, optimization_suggestions }
  
GET /api/tables/optimization:
  params: target_date_time, current_reservations, projected_walk_ins
  response: { suggestedTableAssignments, waitTimeOptimization, revenueProjection }
  
POST /api/tables/bulk-status-update:
  body: { table_updates[] }
  response: { updated_tables[], impacted_reservations[], waitlist_notifications }
```

### **Events Table Management Module:**
```yaml
Table Operations Events:
  - TableStatusChanged: { tableId, oldStatus, newStatus, estimatedAvailableTime }
  - ReservationCreated: { reservationId, customerId, dateTime, partySize, tableAssigned? }
  - ReservationConfirmed: { reservationId, confirmationMethod, reminderScheduled }
  - CustomerSeated: { reservationId?, tableId, actualPartySize, waitTime }
  - TableTurnoverCompleted: { tableId, turnoverMinutes, revenue, cleanupTime }
  - WaitlistJoined: { waitlistId, customerInfo, estimatedWait, position }
  - WaitlistNotified: { waitlistId, notificationMethod, responseDeadline }
  - NoShowRecorded: { reservationId, customerId, impactMetrics }
  - PeakHoursDetected: { dateTime, occupancyRate, waitlistLength, actionRecommended }
  - TableOptimizationSuggestion: { currentLayout, suggestedChanges, projectedImprovement }
```

---

## 🎯 **INFORMACIÓN PRESERVADA - RESUMEN PARTE 2**

**✅ COMPLETADO - Activable Modules Reference (Parte 2):**
- **Kitchen Management**: Recipe costing, station optimization, KDS integration
- **Customer Management**: CRM, loyalty programs, segmentation automática
- **Table Management**: Reservas inteligentes, waitlist, optimización de rotación

**📊 MÉTRICAS DE RECUPERACIÓN PARTE 2:**
- **12 entidades principales** completamente especificadas
- **35+ endpoints críticos** documentados para módulos activables
- **Business value propositions** claros para pricing
- **Dependency matrix** para auto-resolution

**🔄 PRÓXIMO PASO:**
Crear **PARTE 3**: `/reference/enterprise-modules.md` (Financial, Staff, Analytics, Notifications, Compliance)

¿Continúo con PARTE 3 para completar todos los módulos activables?