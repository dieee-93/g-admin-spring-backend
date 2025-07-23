G-ADMIN Reference: Módulos Enterprise
Documento: /reference/enterprise-modules.md
Clasificación: BAJA VOLATILIDAD (especificaciones estables) + MEDIA (integraciones)
Fuente: Consolidación Master Context Enriquecido + v3.1 + Living Document
Status: RECOVERED - Phase 3 Enterprise Modules

🏢 MÓDULOS ENTERPRISE (Pro+ y Enterprise Tier)
Estos módulos representan características avanzadas que generan el mayor valor agregado y diferenciación competitiva.

7. FINANCIAL MANAGEMENT MODULE 💰 ACTIVABLE
   Business Value:

Pricing Tier: Pro+ ($20/mes additional)
Value Proposition: AFIP compliance automático + control de costos 20% más eficiente
Configuration Key: modules.financial.enabled
Dependencies: sales, inventory modules (auto-activated)

Responsabilidades:

Facturación electrónica AFIP (Argentina) - CRÍTICO para compliance
Control de costos y análisis de márgenes en tiempo real
Reportes financieros automatizados con insights gastronómicos
Integración bancaria para conciliación automática
Cost analysis por producto/receta con alertas de rentabilidad

Entidades Principales:
Invoice Entity (AFIP Integration)
java@Entity
@Table(name = "invoices")
public class Invoice extends BaseEntity {
@Column(nullable = false, unique = true)
private String invoiceNumber; // Sequential per invoice type

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AFIPInvoiceType invoiceType = AFIPInvoiceType.FACTURA_B; // A, B, C, E
    
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer; // Optional for Tipo C (consumidor final)
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "order_id")
    private Order order;
    
    // Amounts (all in ARS for AFIP)
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal subtotal;
    
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal vatAmount; // IVA 21%
    
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal otherTaxes = BigDecimal.ZERO; // Ingresos Brutos, etc.
    
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal totalAmount;
    
    @Column(nullable = false)
    private String currency = "ARS";
    
    @Column(precision = 12, scale = 6)
    private BigDecimal exchangeRate = BigDecimal.ONE; // For foreign currency
    
    // AFIP Specific Fields
    @Column(unique = true)
    private String caeNumber; // Código de Autorización Electrónica
    
    private LocalDate caeDueDate; // CAE expiration date
    
    @Column(nullable = false)
    private Integer pointOfSale = 1; // Punto de venta AFIP
    
    private String qrCode; // QR code for invoice verification
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private InvoiceStatus status = InvoiceStatus.DRAFT;
    
    // Dates
    @Column(nullable = false)
    private LocalDate issueDate;
    
    private LocalDate serviceDate; // Fecha de servicio (can be different)
    private LocalDate dueDate; // For credit sales
    
    // Customer tax information (for Tipo A invoices)
    private String customerTaxId; // CUIT
    private String customerTaxCondition; // Responsable Inscripto, Monotributista, etc.
    private String customerAddress;
    
    // Payment information
    @Enumerated(EnumType.STRING)
    private PaymentCondition paymentCondition = PaymentCondition.CASH;
    
    private Integer paymentTermDays = 0;
    
    // AFIP Response
    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    private AFIPResponse afipResponse;
    
    // Internal tracking
    private UUID generatedByUserId;
    private Instant sentToAFIPAt;
    private Instant customerNotifiedAt;
    
    private String notes;
    
    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL)
    private List<InvoiceItem> items = new ArrayList<>();
}

enum AFIPInvoiceType {
FACTURA_A, FACTURA_B, FACTURA_C, FACTURA_E,
NOTA_CREDITO_A, NOTA_CREDITO_B, NOTA_CREDITO_C,
NOTA_DEBITO_A, NOTA_DEBITO_B, NOTA_DEBITO_C
}

enum InvoiceStatus {
DRAFT, SENT_TO_AFIP, APPROVED, REJECTED, CANCELLED, REFUNDED
}

enum PaymentCondition {
CASH, CREDIT, ACCOUNT_CURRENT
}

@Embeddable
class AFIPResponse {
private String result; // A (Approved), R (Rejected)
private String cae;
private LocalDate caeDueDate;
private List<String> observations;
private List<String> errors;
private Instant responseDateTime;
private String transactionId;
}
InvoiceItem Entity
java@Entity
@Table(name = "invoice_items")
public class InvoiceItem extends BaseEntity {
@ManyToOne(optional = false)
@JoinColumn(name = "invoice_id")
private Invoice invoice;

    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id")
    private Product product;
    
    @Column(nullable = false)
    private String description; // Product name at time of invoice
    
    @Column(nullable = false, precision = 10, scale = 4)
    private BigDecimal quantity;
    
    @Column(nullable = false)
    private String unit = "UNIT";
    
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal unitPrice;
    
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal subtotal; // quantity * unitPrice
    
    // Tax information
    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal vatRate = new BigDecimal("21.00"); // IVA percentage
    
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal vatAmount;
    
    @Column(precision = 12, scale = 2)
    private BigDecimal otherTaxes = BigDecimal.ZERO;
    
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal totalAmount; // subtotal + vatAmount + otherTaxes
    
    // AFIP classification
    private String afipProductCode; // Código de producto AFIP si aplicable
    private String ncmCode; // Nomenclatura Común del Mercosur
}
ProductCostAnalysis Entity
java@Entity
@Table(name = "product_cost_analysis")
public class ProductCostAnalysis extends BaseEntity {
@ManyToOne(optional = false)
@JoinColumn(name = "product_id")
private Product product;

    @ManyToOne
    @JoinColumn(name = "recipe_id")
    private Recipe recipe; // For recipe-based products
    
    // Cost breakdown
    @Column(nullable = false, precision = 12, scale = 4)
    private BigDecimal ingredientCost = BigDecimal.ZERO;
    
    @Column(nullable = false, precision = 12, scale = 4)
    private BigDecimal laborCost = BigDecimal.ZERO;
    
    @Column(nullable = false, precision = 12, scale = 4)
    private BigDecimal overheadCost = BigDecimal.ZERO; // Rent, utilities, etc.
    
    @Column(nullable = false, precision = 12, scale = 4)
    private BigDecimal packagingCost = BigDecimal.ZERO;
    
    @Column(nullable = false, precision = 12, scale = 4)
    private BigDecimal totalCost = BigDecimal.ZERO;
    
    // Pricing analysis
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal sellingPrice;
    
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal grossMarginAmount;
    
    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal grossMarginPercentage;
    
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal netMarginAmount; // After all costs
    
    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal netMarginPercentage;
    
    // Target analysis
    @Column(precision = 5, scale = 2)
    private BigDecimal targetMarginPercentage = new BigDecimal("35.00");
    
    @Column(precision = 12, scale = 2)
    private BigDecimal suggestedPrice;
    
    // Calculation metadata
    @Column(nullable = false)
    private Instant calculatedAt;
    
    @Column(nullable = false)
    private Boolean isActive = true; // Latest calculation
    
    @Enumerated(EnumType.STRING)
    private CalculationMethod calculationMethod = CalculationMethod.AUTOMATIC;
    
    private String notes;
    
    // Alerts
    @Column(nullable = false)
    private Boolean marginBelowTarget = false;
    
    @Column(nullable = false)
    private Boolean costIncreaseAlert = false;
    
    private BigDecimal previousTotalCost;
    private BigDecimal costVariationPercentage;
}

enum CalculationMethod {
AUTOMATIC, MANUAL, RECIPE_BASED, MARKET_AVERAGE
}
FinancialReport Entity
java@Entity
@Table(name = "financial_reports")
public class FinancialReport extends BaseEntity {
@Column(nullable = false)
private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReportType reportType;
    
    @Column(nullable = false)
    private LocalDate periodStart;
    
    @Column(nullable = false)
    private LocalDate periodEnd;
    
    // Report data (JSON for flexibility)
    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    private ReportData data;
    
    // Metadata
    @Column(nullable = false)
    private Instant generatedAt;
    
    private UUID generatedByUserId;
    
    @Enumerated(EnumType.STRING)
    private ReportStatus status = ReportStatus.COMPLETED;
    
    // Schedule information (for recurring reports)
    @Column(nullable = false)
    private Boolean isScheduled = false;
    
    private String cronExpression; // For scheduled reports
    private Instant nextRunAt;
    
    // Distribution
    @ElementCollection
    private Set<String> emailRecipients = new HashSet<>();
    
    @Column(nullable = false)
    private Boolean autoSend = false;
    
    // File information
    private String filePath; // PDF/Excel file path
    private String fileName;
    private Long fileSizeBytes;
    
    private String notes;
}

enum ReportType {
DAILY_SALES, WEEKLY_SALES, MONTHLY_SALES,
FOOD_COST_ANALYSIS, LABOR_COST_ANALYSIS,
PROFIT_LOSS, CASH_FLOW, TAX_SUMMARY,
PRODUCT_PROFITABILITY, CUSTOMER_ANALYSIS,
INVENTORY_VALUATION, AFIP_SUMMARY
}

enum ReportStatus {
PENDING, RUNNING, COMPLETED, FAILED, CANCELLED
}

@Embeddable
class ReportData {
// Summary metrics
private BigDecimal totalRevenue;
private BigDecimal totalCosts;
private BigDecimal grossProfit;
private BigDecimal netProfit;
private BigDecimal profitMargin;

    // Detailed breakdowns (varies by report type)
    private Map<String, BigDecimal> revenueByCategory;
    private Map<String, BigDecimal> costsByCategory;
    private Map<String, Object> additionalMetrics;
    
    // Charts and visualizations data
    private List<ChartData> charts;
    private List<TableData> tables;
}
BankTransaction Entity
java@Entity
@Table(name = "bank_transactions")
public class BankTransaction extends BaseEntity {
@Column(nullable = false)
private String bankAccountNumber;

    @Column(nullable = false)
    private String transactionId; // Bank's transaction ID
    
    @Column(nullable = false)
    private LocalDate transactionDate;
    
    @Column(nullable = false)
    private LocalDate valueDate; // Fecha valor
    
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal amount;
    
    @Column(nullable = false)
    private String currency = "ARS";
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType transactionType;
    
    private String description; // Bank's description
    private String reference; // Bank reference number
    
    // Reconciliation
    @ManyToOne
    @JoinColumn(name = "payment_id")
    private Payment reconciledPayment; // If matched with a payment
    
    @Column(nullable = false)
    private Boolean isReconciled = false;
    
    private Instant reconciledAt;
    private UUID reconciledByUserId;
    
    // Classification
    @Enumerated(EnumType.STRING)
    private TransactionCategory category;
    
    private String categoryNotes;
    
    // Balance tracking
    @Column(precision = 12, scale = 2)
    private BigDecimal balanceAfterTransaction;
    
    // Import metadata
    private String importBatchId; // For bulk imports
    private Instant importedAt;
}

enum TransactionType {
DEBIT, CREDIT, TRANSFER_IN, TRANSFER_OUT, FEE, INTEREST
}

enum TransactionCategory {
SALES_REVENUE, SUPPLIER_PAYMENT, SALARY, RENT, UTILITIES,
TAX_PAYMENT, LOAN_PAYMENT, EQUIPMENT, OTHER
}
APIs Financial Management Module:
Invoice Management APIs (AFIP Integration)
yamlPOST /api/financial/invoices:
body: { order_id, customer_tax_info?, invoice_type }
response: { invoice, afip_pre_validation, estimated_cae_time }

POST /api/financial/invoices/{id}/send-to-afip:
response: { afip_response, cae_number?, qr_code?, errors[] }

GET /api/financial/invoices:
filters: date_range, status, invoice_type, customer_id
response: { invoices[], afip_stats, tax_summary }

GET /api/financial/invoices/{id}/pdf:
response: { pdf_url, qr_code_included, afip_compliant }

POST /api/financial/invoices/{id}/cancel:
body: { cancellation_reason }
response: { credit_note, afip_notification }
Cost Analysis APIs
yamlGET /api/financial/cost-analysis:
filters: product_id, date_range, margin_below_target
response: { cost_analyses[], alerts[], recommendations[] }

POST /api/financial/cost-analysis/recalculate:
body: { product_ids[], include_labor_costs, overhead_percentage }
response: { updated_analyses[], margin_impacts[], pricing_suggestions[] }

GET /api/financial/profitability:
params: period, group_by=product|category|branch
response: { profitability_report, top_performers[], underperformers[] }

POST /api/financial/pricing-optimization:
body: { product_id, target_margin, market_constraints }
response: { price_suggestions[], volume_impact_estimate, revenue_projection }
Reporting APIs
yamlPOST /api/financial/reports/generate:
body: { report_type, period_start, period_end, filters, format }
response: { report_id, estimated_completion_time, recipients[] }

GET /api/financial/reports:
filters: report_type, date_range, status
response: { reports[], scheduled_reports[], storage_usage }

GET /api/financial/reports/{id}/download:
response: { file_download_url, expiration_time }

POST /api/financial/reports/schedule:
body: { report_type, cron_expression, recipients[], auto_send }
response: { scheduled_report, next_run_time }
Bank Reconciliation APIs
yamlPOST /api/financial/bank-transactions/import:
body: { bank_file_content, bank_format, account_number }
response: { import_summary, unreconciled_transactions[], auto_matches[] }

GET /api/financial/reconciliation/pending:
params: account_number?, date_range?
response: { pending_transactions[], suggested_matches[], manual_review_needed[] }

POST /api/financial/reconciliation/match:
body: { bank_transaction_id, payment_id, match_confidence }
response: { reconciliation_result, balance_impact }

GET /api/financial/cash-flow:
params: period, projection_days?
response: { cash_flow_statement, projections[], liquidity_alerts[] }
Events Financial Management Module:
yamlCritical Events:
- InvoiceIssued: { invoiceId, afip_required, total_amount, customer_id }
- AFIPResponseReceived: { invoiceId, cae_number?, success, errors[] }
- PaymentReceived: { paymentId, amount, method, bank_transaction_id? }
- CostAnalysisUpdated: { productId, old_margin, new_margin, alert_triggered }
- MarginBelowTarget: { productId, current_margin, target_margin, action_required }
- ReportGenerated: { reportId, report_type, recipients[], file_size }
- BankTransactionImported: { import_batch_id, transaction_count, auto_reconciled_count }
- ReconciliationCompleted: { transaction_id, payment_id, reconciled_amount }
- TaxDeadlineAlert: { deadline_date, pending_invoices[], estimated_tax_amount }
- CashFlowAlert: { alert_type, projected_shortage?, recommended_actions[] }

8. STAFF MANAGEMENT MODULE 💰 ACTIVABLE
   Business Value:

Pricing Tier: Pro+ ($12/mes additional)
Value Proposition: Optimize labor costs by 20%, automated scheduling
Configuration Key: modules.staff.enabled
Dependencies: core module only

Responsabilidades:

Gestión integral de personal con roles específicos gastronómicos
Horarios y control de asistencia con geolocalización
Performance tracking y evaluaciones periódicas
Control de acceso por roles y módulos
Integración con payroll y compliance laboral

Entidades Principales:
Employee Entity
java@Entity
@Table(name = "employees")
public class Employee extends BaseEntity {
@OneToOne(optional = false)
@JoinColumn(name = "user_id")
private User user; // Links to User in Core module

    @Column(nullable = false, unique = true)
    private String employeeCode; // EMP-001, EMP-002, etc.
    
    // Personal information
    @Column(nullable = false)
    private String firstName;
    
    @Column(nullable = false)
    private String lastName;
    
    private String nationalId; // DNI, SSN, etc.
    private LocalDate birthDate;
    private String gender;
    
    // Contact information
    @Column(nullable = false)
    private String phoneNumber;
    
    private String alternatePhone;
    private String personalEmail;
    
    @Embedded
    private Address homeAddress;
    
    // Emergency contact
    @Embedded
    private EmergencyContact emergencyContact;
    
    // Employment details
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EmploymentType employmentType = EmploymentType.FULL_TIME;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Position position;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Department department;
    
    @Column(nullable = false)
    private LocalDate hireDate;
    
    private LocalDate terminationDate;
    
    @Enumerated(EnumType.STRING)
    private EmploymentStatus status = EmploymentStatus.ACTIVE;
    
    // Compensation
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal hourlyRate = BigDecimal.ZERO;
    
    @Column(precision = 10, scale = 2)
    private BigDecimal monthlyBaseSalary = BigDecimal.ZERO;
    
    @Column(nullable = false)
    private String currency = "ARS";
    
    @Column(nullable = false)
    private Boolean isEligibleForOvertime = true;
    
    @Column(precision = 5, scale = 2)
    private BigDecimal overtimeMultiplier = new BigDecimal("1.50");
    
    // Skills and certifications
    @ElementCollection
    @Enumerated(EnumType.STRING)
    private Set<Skill> skills = new HashSet<>();
    
    @ElementCollection
    private Set<String> certifications = new HashSet<>();
    
    private LocalDate lastFoodHandlingCertification;
    private LocalDate nextMandatoryTraining;
    
    // Performance metrics
    @Column(precision = 3, scale = 2)
    private BigDecimal performanceRating = new BigDecimal("3.00"); // 1-5 scale
    
    private LocalDate lastPerformanceReview;
    private LocalDate nextPerformanceReview;
    
    // Work preferences
    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    private WorkPreferences workPreferences;
    
    // Assigned branches and permissions
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "employee_branches")
    private Set<Branch> assignedBranches = new HashSet<>();
    
    // Manager hierarchy
    @ManyToOne
    @JoinColumn(name = "manager_id")
    private Employee manager;
    
    @OneToMany(mappedBy = "manager")
    private List<Employee> directReports = new ArrayList<>();
    
    // Status tracking
    @Column(nullable = false)
    private Boolean canLogin = true;
    
    @Column(nullable = false)
    private Boolean requiresTimeClockValidation = true;
    
    private String notes;
}

enum EmploymentType {
FULL_TIME, PART_TIME, TEMPORARY, SEASONAL, CONTRACTOR
}

enum Position {
OWNER, GENERAL_MANAGER, ASSISTANT_MANAGER, SHIFT_SUPERVISOR,
HEAD_CHEF, SOUS_CHEF, LINE_COOK, PREP_COOK, DISHWASHER,
SERVER, HOST_HOSTESS, BARTENDER, BARISTA,
CASHIER, FOOD_RUNNER, BUSSER, CLEANER, DELIVERY_DRIVER, OTHER
}

enum Department {
MANAGEMENT, KITCHEN, FRONT_OF_HOUSE, BAR, CLEANING, DELIVERY, ADMINISTRATION
}

enum EmploymentStatus {
ACTIVE, ON_LEAVE, SUSPENDED, TERMINATED, RESIGNED
}

enum Skill {
COOKING, GRILLING, BAKING, BARISTA, BARTENDING, POS_OPERATION,
CUSTOMER_SERVICE, LEADERSHIP, FOOD_SAFETY, INVENTORY_MANAGEMENT,
CASH_HANDLING, LANGUAGES_BILINGUAL, FOOD_PREPARATION, CLEANING
}

@Embeddable
class EmergencyContact {
private String name;
private String relationship;
private String phoneNumber;
private String alternatePhone;
}

@Embeddable
class WorkPreferences {
private List<String> preferredShifts; // MORNING, AFTERNOON, EVENING, NIGHT
private List<Integer> preferredDays; // 1=Monday, 7=Sunday
private Integer maxHoursPerWeek;
private Boolean canWorkWeekends;
private Boolean canWorkHolidays;
private String transportationMethod;
}
Schedule Entity
java@Entity
@Table(name = "schedules")
public class Schedule extends BaseEntity {
@ManyToOne(optional = false)
@JoinColumn(name = "employee_id")
private Employee employee;

    @ManyToOne(optional = false)
    @JoinColumn(name = "branch_id")
    private Branch branch;
    
    // Schedule details
    @Column(nullable = false)
    private LocalDate scheduleDate;
    
    @Column(nullable = false)
    private LocalTime shiftStart;
    
    @Column(nullable = false)
    private LocalTime shiftEnd;
    
    @Column(nullable = false)
    private LocalTime breakStart;
    
    @Column(nullable = false)
    private LocalTime breakEnd;
    
    @Column(nullable = false)
    private Integer breakDurationMinutes = 30;
    
    // Position and department for this shift
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Position assignedPosition;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Department assignedDepartment;
    
    // Status tracking
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ScheduleStatus status = ScheduleStatus.SCHEDULED;
    
    // Planned vs actual hours
    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal plannedHours;
    
    @Column(precision = 5, scale = 2)
    private BigDecimal actualHours = BigDecimal.ZERO;
    
    @Column(precision = 5, scale = 2)
    private BigDecimal overtimeHours = BigDecimal.ZERO;
    
    // Change tracking
    private UUID createdByUserId;
    private UUID approvedByUserId;
    private Instant approvedAt;
    
    // Notes and special instructions
    private String notes;
    private String specialInstructions;
    
    // Related time entries
    @OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL)
    private List<TimeEntry> timeEntries = new ArrayList<>();
}

enum ScheduleStatus {
SCHEDULED, CONFIRMED, COMPLETED, CANCELLED, NO_SHOW, MODIFIED
}
TimeEntry Entity
java@Entity
@Table(name = "time_entries")
public class TimeEntry extends BaseEntity {
@ManyToOne(optional = false)
@JoinColumn(name = "employee_id")
private Employee employee;

    @ManyToOne
    @JoinColumn(name = "schedule_id")
    private Schedule schedule; // Reference to scheduled shift
    
    // Time tracking
    @Column(nullable = false)
    private Instant clockIn;
    
    private Instant clockOut;
    
    // Break tracking
    private Instant breakStart;
    private Instant breakEnd;
    
    @Column(precision = 5, scale = 2)
    private BigDecimal totalBreakMinutes = BigDecimal.ZERO;
    
    // Calculated hours
    @Column(precision = 5, scale = 2)
    private BigDecimal workedHours = BigDecimal.ZERO;
    
    @Column(precision = 5, scale = 2)
    private BigDecimal regularHours = BigDecimal.ZERO;
    
    @Column(precision = 5, scale = 2)
    private BigDecimal overtimeHours = BigDecimal.ZERO;
    
    // Geolocation validation
    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    private LocationData clockInLocation;
    
    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    private LocationData clockOutLocation;
    
    // Validation and approval
    @Column(nullable = false)
    private Boolean isLocationValidated = false;
    
    @Column(nullable = false)
    private Boolean requiresApproval = false;
    
    @Column(nullable = false)
    private Boolean isApproved = false;
    
    private UUID approvedByUserId;
    private Instant approvedAt;
    
    // Discrepancy tracking
    @Column(nullable = false)
    private Boolean hasDiscrepancy = false;
    
    private String discrepancyReason;
    private String discrepancyNotes;
    
    // Adjustments
    @Column(precision = 5, scale = 2)
    private BigDecimal adjustedHours = BigDecimal.ZERO;
    
    private String adjustmentReason;
    private UUID adjustedByUserId;
    
    // Device and method tracking
    private String clockInMethod; // MOBILE_APP, TABLET, WEB, BIOMETRIC
    private String clockInDeviceId;
    private String clockInIP;
    
    private String notes;
}

@Embeddable
class LocationData {
private Double latitude;
private Double longitude;
private Double accuracy;
private String address;
private Instant timestamp;
private Boolean isWithinGeofence;
private Double distanceFromBranch;
}
PerformanceReview Entity
java@Entity
@Table(name = "performance_reviews")
public class PerformanceReview extends BaseEntity {
@ManyToOne(optional = false)
@JoinColumn(name = "employee_id")
private Employee employee;

    @ManyToOne(optional = false)
    @JoinColumn(name = "reviewer_id")
    private Employee reviewer; // Usually manager
    
    // Review period
    @Column(nullable = false)
    private LocalDate reviewPeriodStart;
    
    @Column(nullable = false)
    private LocalDate reviewPeriodEnd;
    
    @Column(nullable = false)
    private LocalDate reviewDate;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReviewType reviewType = ReviewType.ANNUAL;
    
    // Overall rating
    @Column(nullable = false, precision = 3, scale = 2)
    private BigDecimal overallRating; // 1-5 scale
    
    // Detailed ratings
    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    private Map<String, BigDecimal> categoryRatings = new HashMap<>();
    
    // Comments and feedback
    private String strengths;
    private String areasForImprovement;
    private String goals;
    private String managerComments;
    private String employeeComments;
    
    // Goals and objectives
    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    private List<PerformanceGoal> goals_list = new ArrayList<>();
    
    // Status
    @Enumerated(EnumType.STRING)
    private ReviewStatus status = ReviewStatus.DRAFT;
    
    // Signatures and approvals
    private Instant employeeSignedAt;
    private Instant managerSignedAt;
    private Instant hrApprovedAt;
    
    private UUID hrApproverUserId;
    
    // Next review
    private LocalDate nextReviewDate;
    
    // Compensation changes
    @Column(precision = 10, scale = 2)
    private BigDecimal currentHourlyRate;
    
    @Column(precision = 10, scale = 2)
    private BigDecimal newHourlyRate;
    
    @Column(precision = 10, scale = 2)
    private BigDecimal bonusAmount = BigDecimal.ZERO;
    
    private String compensationNotes;
}

enum ReviewType {
PROBATIONARY, QUARTERLY, SEMI_ANNUAL, ANNUAL, SPECIAL, DISCIPLINARY
}

enum ReviewStatus {
DRAFT, PENDING_EMPLOYEE_REVIEW, PENDING_MANAGER_APPROVAL,
PENDING_HR_APPROVAL, COMPLETED, CANCELLED
}

@Embeddable
class PerformanceGoal {
private String description;
private String category; // SKILL_DEVELOPMENT, PRODUCTIVITY, CUSTOMER_SERVICE, etc.
private LocalDate targetDate;
private String successMetrics;
private String status; // NOT_STARTED, IN_PROGRESS, COMPLETED, DEFERRED
}
APIs Staff Management Module:
Employee Management APIs
yamlGET /api/staff/employees:
filters: branch_id, department, position, status, skills
response: { employees[], headcount_by_department, skills_matrix }

POST /api/staff/employees:
body: { user_id, employee_code, position, department, hourly_rate, certifications }
response: { employee, onboarding_checklist, training_schedule }

PUT /api/staff/employees/{id}/position:
body: { new_position, new_department, effective_date, hourly_rate }
response: { employee, permission_changes, training_requirements }

GET /api/staff/employees/{id}/performance:
params: period_months=12
response: { performance_summary, reviews[], goals[], attendance_stats }
Scheduling APIs
yamlGET /api/staff/schedules:
filters: date_range, branch_id, employee_id, department
response: { schedules[], coverage_analysis, overtime_projections }

POST /api/staff/schedules/generate:
body: { week_start_date, branch_id, coverage_requirements, constraints }
response: { generated_schedules[], coverage_gaps[], optimization_suggestions }

PUT /api/staff/schedules/{id}/modify:
body: { new_shift_times, reason, notify_employee }
response: { updated_schedule, employee_notification_sent, coverage_impact }

POST /api/staff/schedules/swap-request:
body: { schedule_id, requested_employee_id, reason }
response: { swap_request, approval_workflow, affected_employees[] }
Time & Attendance APIs
yamlPOST /api/staff/time-entry/clock-in:
body: { employee_id, location, device_info }
response: { time_entry, location_validated, schedule_match }

POST /api/staff/time-entry/clock-out:
body: { time_entry_id, location }
response: { completed_entry, hours_worked, overtime_calculation, approval_required }

GET /api/staff/time-entry/pending-approval:
filters: manager_id, branch_id, date_range
response: { pending_entries[], discrepancies[], total_hours_pending }

POST /api/staff/time-entry/{id}/approve:
body: { approved_hours, adjustments?, approval_notes }
response: { approved_entry, payroll_impact, employee_notification }
Performance Management APIs
yamlGET /api/staff/performance/reviews:
filters: employee_id, review_type, date_range, status
response: { reviews[], due_reviews[], performance_trends }

POST /api/staff/performance/reviews:
body: { employee_id, review_type, period_start, period_end }
response: { review_draft, auto_populated_metrics, rating_guidelines }

PUT /api/staff/performance/reviews/{id}/submit:
body: { ratings, comments, goals, compensation_changes }
response: { submitted_review, approval_workflow, next_steps }

GET /api/staff/performance/analytics:
params: branch_id?, department?, period_months=12
response: { performance_dashboard, top_performers[], improvement_opportunities[] }
Events Staff Management Module:
yamlEmployee Lifecycle Events:
- EmployeeHired: { employeeId, position, department, start_date, training_required }
- EmployeePromoted: { employeeId, old_position, new_position, compensation_change }
- EmployeeTerminated: { employeeId, termination_date, reason, final_payroll }
- ShiftScheduled: { scheduleId, employeeId, shift_details, approval_required }
- TimeEntryRecorded: { timeEntryId, employeeId, hours_worked, location_validated }
- OvertimeThresholdExceeded: { employeeId, overtime_hours, cost_impact, approval_needed }
- PerformanceReviewDue: { employeeId, review_type, due_date, reviewer_assigned }
- PerformanceGoalCompleted: { employeeId, goal_description, completion_date }
- AttendanceAlert: { employeeId, alert_type, pattern_detected, action_required }
- CertificationExpiring: { employeeId, certification, expiration_date, renewal_required }

📊 PARTE 3B: ADVANCED ENTERPRISE MODULES
9. ADVANCED ANALYTICS MODULE 📊 ACTIVABLE
   Business Value:

Pricing Tier: Pro+ ($25/mes additional), Enterprise included
Value Proposition: ML-powered insights, predictive analytics, 30% better decision making
Configuration Key: modules.analytics-advanced.enabled
Dependencies: core, sales, inventory modules (customer module optional)

Responsabilidades:

Dashboards personalizables con widgets configurables y tiempo real
KPI monitoring específicos gastronómicos con alertas automáticas
ML-powered forecasting para ventas, inventario y comportamiento de clientes
Reportes automáticos con insights de business intelligence
Advanced query engine para análisis ad-hoc complejos

Entidades Principales:
Dashboard Entity
java@Entity
@Table(name = "dashboards")
public class Dashboard extends BaseEntity {
@Column(nullable = false)
private String name;

    @Column(columnDefinition = "TEXT")
    private String description;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DashboardType type; // OPERATIONAL, FINANCIAL, CUSTOMER, KITCHEN
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "owner_id")
    private User owner;
    
    @Column(columnDefinition = "TEXT")
    private String widgetConfiguration; // JSON structure for widgets
    
    @Column(nullable = false)
    private Boolean isShared = false;
    
    @Column(nullable = false)
    private Boolean isDefault = false;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RefreshFrequency refreshFrequency = RefreshFrequency.REAL_TIME;
    
    @OneToMany(mappedBy = "dashboard", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<DashboardWidget> widgets = new HashSet<>();
}

enum DashboardType {
OPERATIONAL, FINANCIAL, CUSTOMER, KITCHEN, CUSTOM
}

enum RefreshFrequency {
REAL_TIME, EVERY_5_MIN, HOURLY, DAILY
}
KPIMetric Entity
java@Entity
@Table(name = "kpi_metrics")
public class KPIMetric extends BaseEntity {
@Column(nullable = false, unique = true)
private String code; // DAILY_REVENUE, FOOD_COST_PERCENTAGE, TABLE_TURNOVER

    @Column(nullable = false)
    private String name;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private KPICategory category; // FINANCIAL, OPERATIONAL, CUSTOMER, KITCHEN
    
    @Column(columnDefinition = "TEXT", nullable = false)
    private String calculationFormula; // SQL-like formula for calculation
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MetricUnit unit; // CURRENCY, PERCENTAGE, COUNT, TIME_MINUTES
    
    @Column(precision = 10, scale = 2)
    private BigDecimal targetValue;
    
    @Column(precision = 10, scale = 2)
    private BigDecimal warningThreshold;
    
    @Column(precision = 10, scale = 2)
    private BigDecimal criticalThreshold;
    
    @Column(nullable = false)
    private Boolean isRealTime = false;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AggregationPeriod aggregationPeriod = AggregationPeriod.DAILY;
    
    @OneToMany(mappedBy = "kpiMetric", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<KPIValue> historicalValues = new HashSet<>();
}

enum KPICategory {
FINANCIAL, OPERATIONAL, CUSTOMER, KITCHEN, STAFF, COMPLIANCE
}

enum MetricUnit {
CURRENCY, PERCENTAGE, COUNT, TIME_MINUTES, TIME_HOURS, WEIGHT_KG, VOLUME_L
}

enum AggregationPeriod {
REAL_TIME, HOURLY, DAILY, WEEKLY, MONTHLY
}
ScheduledReport Entity
java@Entity
@Table(name = "scheduled_reports")
public class ScheduledReport extends BaseEntity {
@Column(nullable = false)
private String name;

    @Column(columnDefinition = "TEXT")
    private String description;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AnalyticsReportType reportType; // SALES_ANALYSIS, INVENTORY_OPTIMIZATION, CUSTOMER_INSIGHTS
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "created_by_user_id")
    private User createdByUser;
    
    @Column(columnDefinition = "TEXT", nullable = false)
    private String reportParameters; // JSON configuration for report generation
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ScheduleFrequency frequency; // DAILY, WEEKLY, MONTHLY, QUARTERLY
    
    @Column(nullable = false)
    private LocalTime scheduledTime;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AnalyticsReportFormat format = AnalyticsReportFormat.PDF; // PDF, EXCEL, JSON
    
    @Column(columnDefinition = "TEXT")
    private String recipientEmails; // JSON array of email addresses
    
    @Column(nullable = false)
    private Boolean includeMLInsights = true;
    
    @Column(nullable = false)
    private Boolean isActive = true;
    
    @Column
    private LocalDateTime lastExecutionTime;
    
    @Column
    private LocalDateTime nextExecutionTime;
    
    @OneToMany(mappedBy = "scheduledReport", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ReportExecution> executions = new HashSet<>();
}

enum AnalyticsReportType {
SALES_ANALYSIS, INVENTORY_OPTIMIZATION, CUSTOMER_INSIGHTS,
PREDICTIVE_FORECAST, KITCHEN_EFFICIENCY, STAFF_PERFORMANCE
}

enum ScheduleFrequency {
DAILY, WEEKLY, MONTHLY, QUARTERLY
}

enum AnalyticsReportFormat {
PDF, EXCEL, JSON, CSV
}
AnalyticsQuery Entity
java@Entity
@Table(name = "analytics_queries")
public class AnalyticsQuery extends BaseEntity {
@Column(nullable = false)
private String queryName;

    @Column(columnDefinition = "TEXT")
    private String description;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private QueryComplexity complexity; // SIMPLE, COMPLEX, ML_POWERED
    
    @Column(columnDefinition = "TEXT", nullable = false)
    private String queryDefinition; // GraphQL/SQL hybrid definition
    
    @Column(columnDefinition = "TEXT")
    private String cacheStrategy; // REAL_TIME, 5_MINUTES, HOURLY, DAILY
    
    @Column(nullable = false)
    private Integer estimatedExecutionTime; // milliseconds
    
    @Column(nullable = false)
    private Boolean requiresMLProcessing = false;
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "created_by_user_id")
    private User createdByUser;
    
    @Column(columnDefinition = "TEXT")
    private String accessPermissions; // JSON roles/users who can execute
    
    @OneToMany(mappedBy = "analyticsQuery", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<QueryExecution> executions = new HashSet<>();
}

enum QueryComplexity {
SIMPLE, COMPLEX, ML_POWERED
}
APIs Advanced Analytics Module:
Dashboard Management APIs
yamlGET /api/analytics/dashboards:
filters: user_id, dashboard_type, is_shared
response: { dashboards[], widget_catalog[], performance_metrics }

POST /api/analytics/dashboards:
body: { name, type, widget_configuration, refresh_frequency }
response: { dashboard, available_widgets[], optimization_suggestions }

PUT /api/analytics/dashboards/{id}/widgets:
body: { widget_updates[], layout_changes }
response: { updated_dashboard, performance_impact, data_freshness }
KPI Monitoring APIs
yamlGET /api/analytics/kpis:
filters: category, real_time_only, threshold_status
response: { kpi_metrics[], current_values[], trend_analysis }

POST /api/analytics/kpis/calculate:
body: { kpi_codes[], force_recalculation, time_range }
response: { calculated_values[], alerts_triggered[], recommendations }

GET /api/analytics/kpis/{code}/trends:
params: period, aggregation, include_forecasts
response: { trend_data[], anomalies[], ml_insights }
ML Forecasting APIs
yamlPOST /api/analytics/forecasting:
body: { forecast_type, time_horizon, confidence_level, factors }
response: { forecast_result[], confidence_intervals[], influencing_factors[] }

GET /api/analytics/forecasting/accuracy:
params: model_type, evaluation_period
response: { accuracy_metrics[], model_performance[], recommendations }

POST /api/analytics/anomaly-detection:
body: { metrics[], sensitivity_level, alert_preferences }
response: { anomalies_detected[], severity_levels[], suggested_actions[] }
Advanced Reporting APIs
yamlPOST /api/analytics/reports/generate:
body: { report_type, parameters, ml_insights_level, format }
response: { report_id, estimated_completion, preview_available }

GET /api/analytics/reports/scheduled:
filters: report_type, frequency, status
response: { scheduled_reports[], next_executions[], storage_usage }

POST /api/analytics/queries/execute:
body: { query_definition, cache_policy, timeout_seconds }
response: { query_results[], execution_time, cache_status }
Events Advanced Analytics Module:
yamlAnalytics Events:
- ReportGenerated: { reportId, report_type, ml_insights_included, file_size }
- KPIThresholdExceeded: { kpiCode, current_value, threshold_type, severity }
- TrendDetected: { metric, trend_direction, confidence, impact_level }
- ForecastUpdated: { forecast_type, accuracy_change, model_version }
- AnomalyDetected: { metric, anomaly_score, detection_algorithm, recommended_action }
- DashboardViewed: { dashboardId, user_id, widgets_loaded, load_time }
- MLModelRetrained: { model_type, accuracy_improvement, training_data_size }
- QueryPerformanceAlert: { queryId, execution_time, optimization_needed }

10. NOTIFICATIONS MODULE 📱 ACTIVABLE
    Business Value:

Pricing Tier: Pro+ ($15/mes additional), Enterprise included
Value Proposition: Automated customer communication, 40% better retention
Configuration Key: modules.notifications.enabled
Dependencies: core, customer modules (staff module optional)

Responsabilidades:

Multi-channel notifications (WhatsApp, SMS, Email, Push)
Template engine con personalización automática
Campaign management con tracking de conversión
WhatsApp Business API integration completa
Automated workflows basados en eventos del sistema

Entidades Principales:
Notification Entity
java@Entity
@Table(name = "notifications")
public class Notification extends BaseEntity {
@Column(nullable = false)
private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationType type; // TRANSACTIONAL, MARKETING, ALERT, REMINDER
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationChannel channel; // WHATSAPP, SMS, EMAIL, PUSH, IN_APP
    
    @ManyToOne
    @JoinColumn(name = "recipient_customer_id")
    private Customer recipientCustomer;
    
    @ManyToOne
    @JoinColumn(name = "recipient_user_id")
    private User recipientUser; // For internal notifications
    
    @Column(nullable = false)
    private String recipientAddress; // Phone, email, etc.
    
    @ManyToOne
    @JoinColumn(name = "template_id")
    private NotificationTemplate template;
    
    @ManyToOne
    @JoinColumn(name = "campaign_id")
    private NotificationCampaign campaign;
    
    // Delivery tracking
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DeliveryStatus deliveryStatus = DeliveryStatus.PENDING;
    
    @Column
    private LocalDateTime sentAt;
    
    @Column
    private LocalDateTime deliveredAt;
    
    @Column
    private LocalDateTime readAt;
    
    @Column
    private LocalDateTime clickedAt;
    
    // Provider tracking
    private String providerMessageId; // WhatsApp, SMS provider ID
    private String providerResponse;
    
    @Column(columnDefinition = "TEXT")
    private String deliveryFailureReason;
    
    // Retry logic
    @Column(nullable = false)
    private Integer retryCount = 0;
    
    @Column
    private LocalDateTime nextRetryAt;
    
    // Personalization data
    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    private Map<String, Object> personalizationData = new HashMap<>();
    
    // Cost tracking
    @Column(precision = 8, scale = 4)
    private BigDecimal deliveryCost = BigDecimal.ZERO;
    
    @Column(nullable = false)
    private String currency = "USD";
    
    private String notes;
}

enum NotificationType {
TRANSACTIONAL, MARKETING, ALERT, REMINDER, PROMOTIONAL
}

enum NotificationChannel {
WHATSAPP, SMS, EMAIL, PUSH, IN_APP
}

enum DeliveryStatus {
PENDING, SENT, DELIVERED, READ, CLICKED, FAILED, BOUNCED, UNSUBSCRIBED
}
NotificationTemplate Entity
java@Entity
@Table(name = "notification_templates")
public class NotificationTemplate extends BaseEntity {
@Column(nullable = false)
private String name;

    @Column(columnDefinition = "TEXT")
    private String description;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationChannel channel;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TemplateCategory category; // ORDER_CONFIRMATION, PROMOTION, REMINDER, ALERT
    
    // Template content
    @Column(nullable = false)
    private String subjectTemplate; // For email/push
    
    @Column(columnDefinition = "TEXT", nullable = false)
    private String bodyTemplate; // Supports variables like {{customerName}}
    
    @Column(columnDefinition = "TEXT")
    private String footerTemplate;
    
    // WhatsApp specific
    @Column(columnDefinition = "TEXT")
    private String whatsappMediaUrl; // Image, document, etc.
    
    @Enumerated(EnumType.STRING)
    private WhatsAppMediaType whatsappMediaType;
    
    @Column(columnDefinition = "TEXT")
    private String whatsappButtonConfig; // JSON for interactive buttons
    
    // Email specific
    @Column(columnDefinition = "TEXT")
    private String htmlTemplate; // Rich HTML version
    
    @Column(columnDefinition = "TEXT")
    private String emailAttachments; // JSON list of attachments
    
    // Scheduling and triggers
    @ElementCollection
    @Enumerated(EnumType.STRING)
    private Set<TriggerEvent> triggerEvents = new HashSet<>();
    
    @Column
    private Integer delayMinutes = 0; // Delay after trigger event
    
    // Personalization
    @ElementCollection
    private Set<String> availableVariables = new HashSet<>(); // {{customerName}}, {{orderTotal}}, etc.
    
    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    private Map<String, String> defaultValues = new HashMap<>();
    
    // Template metadata
    @Column(nullable = false)
    private Boolean isActive = true;
    
    @Column(nullable = false)
    private Boolean requiresApproval = false;
    
    private UUID approvedByUserId;
    private LocalDateTime approvedAt;
    
    // A/B Testing
    @ManyToOne
    @JoinColumn(name = "variant_of_template_id")
    private NotificationTemplate variantOfTemplate;
    
    @Column(precision = 5, scale = 2)
    private BigDecimal variantTrafficPercentage = BigDecimal.ZERO;
    
    // Performance tracking
    @Column(nullable = false)
    private Long totalSent = 0L;
    
    @Column(nullable = false)
    private Long totalDelivered = 0L;
    
    @Column(nullable = false)
    private Long totalClicked = 0L;
    
    @Column(precision = 5, scale = 2)
    private BigDecimal deliveryRate = BigDecimal.ZERO;
    
    @Column(precision = 5, scale = 2)
    private BigDecimal clickThroughRate = BigDecimal.ZERO;
}

enum TemplateCategory {
ORDER_CONFIRMATION, ORDER_STATUS, PAYMENT_REMINDER, PROMOTION,
BIRTHDAY_OFFER, LOYALTY_REWARD, RESERVATION_REMINDER, DELIVERY_UPDATE
}

enum WhatsAppMediaType {
IMAGE, DOCUMENT, VIDEO, AUDIO, STICKER
}

enum TriggerEvent {
ORDER_PLACED, ORDER_CONFIRMED, ORDER_READY, ORDER_DELIVERED,
PAYMENT_RECEIVED, PAYMENT_FAILED, CUSTOMER_BIRTHDAY,
LOYALTY_POINTS_EARNED, RESERVATION_MADE, REVIEW_REQUEST
}
NotificationCampaign Entity
java@Entity
@Table(name = "notification_campaigns")
public class NotificationCampaign extends BaseEntity {
@Column(nullable = false)
private String name;

    @Column(columnDefinition = "TEXT")
    private String description;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CampaignType campaignType; // ONE_TIME, RECURRING, TRIGGERED
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CampaignStatus status = CampaignStatus.DRAFT;
    
    // Campaign scheduling
    @Column
    private LocalDateTime scheduledStartTime;
    
    @Column
    private LocalDateTime scheduledEndTime;
    
    @Column
    private LocalDateTime actualStartTime;
    
    @Column
    private LocalDateTime actualEndTime;
    
    // Targeting
    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    private CustomerSegmentCriteria targetCriteria; // JSON segmentation rules
    
    @Column
    private Long estimatedAudience;
    
    @Column
    private Long actualAudience;
    
    // Template and content
    @ManyToOne(optional = false)
    @JoinColumn(name = "template_id")
    private NotificationTemplate template;
    
    @ElementCollection
    @Enumerated(EnumType.STRING)
    private Set<NotificationChannel> channels = new HashSet<>();
    
    // Budget and limits
    @Column(precision = 10, scale = 2)
    private BigDecimal budget = BigDecimal.ZERO;
    
    @Column(precision = 10, scale = 2)
    private BigDecimal actualCost = BigDecimal.ZERO;
    
    @Column
    private Integer maxRecipientsPerDay;
    
    @Column
    private Integer maxTotalRecipients;
    
    // Performance metrics
    @Column(nullable = false)
    private Long totalSent = 0L;
    
    @Column(nullable = false)
    private Long totalDelivered = 0L;
    
    @Column(nullable = false)
    private Long totalClicked = 0L;
    
    @Column(nullable = false)
    private Long totalConversions = 0L; // Orders, reservations, etc.
    
    @Column(precision = 10, scale = 2)
    private BigDecimal conversionRevenue = BigDecimal.ZERO;
    
    @Column(precision = 5, scale = 2)
    private BigDecimal roi = BigDecimal.ZERO; // Return on Investment
    
    // A/B Testing
    @Column(nullable = false)
    private Boolean isABTest = false;
    
    @Column(precision = 5, scale = 2)
    private BigDecimal variantSplitPercentage = BigDecimal.ZERO;
    
    @ManyToOne
    @JoinColumn(name = "variant_template_id")
    private NotificationTemplate variantTemplate;
    
    // Campaign metadata
    @ManyToOne(optional = false)
    @JoinColumn(name = "created_by_user_id")
    private User createdByUser;
    
    private UUID approvedByUserId;
    private LocalDateTime approvedAt;
    
    private String notes;
}

enum CampaignType {
ONE_TIME, RECURRING, TRIGGERED, DRIP_SEQUENCE
}

enum CampaignStatus {
DRAFT, PENDING_APPROVAL, APPROVED, SCHEDULED, RUNNING, PAUSED, COMPLETED, CANCELLED
}

@Embeddable
class CustomerSegmentCriteria {
private List<String> customerIds; // Specific customers
private List<String> loyaltyTiers; // VIP, REGULAR, NEW
private BigDecimal minOrderValue;
private BigDecimal maxOrderValue;
private Integer daysSinceLastOrder;
private List<String> favoriteCategories;
private String location; // City, region
private LocalDate birthDateRange;
private Boolean hasWhatsApp;
private Boolean optedInMarketing;
}
CampaignMetrics Entity
java@Entity
@Table(name = "campaign_metrics")
public class CampaignMetrics extends BaseEntity {
@ManyToOne(optional = false)
@JoinColumn(name = "campaign_id")
private NotificationCampaign campaign;

    @Column(nullable = false)
    private LocalDate metricsDate;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationChannel channel;
    
    // Daily metrics
    @Column(nullable = false)
    private Long sentCount = 0L;
    
    @Column(nullable = false)
    private Long deliveredCount = 0L;
    
    @Column(nullable = false)
    private Long readCount = 0L;
    
    @Column(nullable = false)
    private Long clickedCount = 0L;
    
    @Column(nullable = false)
    private Long unsubscribedCount = 0L;
    
    @Column(nullable = false)
    private Long bouncedCount = 0L;
    
    // Conversion metrics
    @Column(nullable = false)
    private Long conversions = 0L; // Orders placed, reservations made
    
    @Column(precision = 10, scale = 2)
    private BigDecimal conversionRevenue = BigDecimal.ZERO;
    
    // Cost metrics
    @Column(precision = 10, scale = 2)
    private BigDecimal totalCost = BigDecimal.ZERO;
    
    @Column(precision = 6, scale = 4)
    private BigDecimal costPerDelivery = BigDecimal.ZERO;
    
    @Column(precision = 6, scale = 4)
    private BigDecimal costPerClick = BigDecimal.ZERO;
    
    @Column(precision = 6, scale = 4)
    private BigDecimal costPerConversion = BigDecimal.ZERO;
    
    // Calculated rates
    @Column(precision = 5, scale = 2)
    private BigDecimal deliveryRate = BigDecimal.ZERO;
    
    @Column(precision = 5, scale = 2)
    private BigDecimal openRate = BigDecimal.ZERO;
    
    @Column(precision = 5, scale = 2)
    private BigDecimal clickThroughRate = BigDecimal.ZERO;
    
    @Column(precision = 5, scale = 2)
    private BigDecimal conversionRate = BigDecimal.ZERO;
    
    @Column(precision = 5, scale = 2)
    private BigDecimal unsubscribeRate = BigDecimal.ZERO;
}
APIs Notifications Module:
Multi-Channel Sending APIs
yamlPOST /api/notifications/send:
body: { channel, recipient, template_id, personalization_data, priority }
response: { notification_id, estimated_delivery, provider_response }

POST /api/notifications/whatsapp/send:
body: { phone_number, message, media_url?, buttons?, template_id? }
response: { whatsapp_message_id, delivery_status, cost_estimate }

POST /api/notifications/bulk-send:
body: { campaign_id, recipients[], channels[], personalization }
response: { bulk_job_id, estimated_completion, recipients_processed }

GET /api/notifications/{id}/status:
response: { delivery_status, timestamps, provider_details, cost }
Template Management APIs
yamlGET /api/notifications/templates:
filters: channel, category, is_active
response: { templates[], performance_stats[], approval_status }

POST /api/notifications/templates:
body: { name, channel, category, content_templates, trigger_events }
response: { template, validation_results, approval_required }

POST /api/notifications/templates/{id}/test:
body: { test_recipient, personalization_data }
response: { test_sent, preview_content, delivery_result }

PUT /api/notifications/templates/{id}/approve:
body: { approval_notes }
response: { approved_template, activation_time }
Campaign Management APIs
yamlPOST /api/notifications/campaigns:
body: { name, type, template_id, target_criteria, scheduling }
response: { campaign, audience_estimate, cost_estimate }

POST /api/notifications/campaigns/{id}/launch:
body: { confirm_audience, budget_approval }
response: { campaign_started, actual_audience, first_batch_sent }

GET /api/notifications/campaigns/{id}/metrics:
params: date_range, channel, include_revenue
response: { performance_metrics[], roi_analysis, conversion_funnel }

POST /api/notifications/campaigns/{id}/ab-test:
body: { variant_template_id, split_percentage, test_duration }
response: { ab_test_configured, test_start_time, significance_threshold }
WhatsApp Business Integration APIs
yamlPOST /api/notifications/whatsapp/webhook:
body: { webhook_data }
response: { processed, notification_updates[] }

GET /api/notifications/whatsapp/templates:
response: { approved_templates[], pending_approval[], template_limits }

POST /api/notifications/whatsapp/opt-in:
body: { phone_number, consent_source, consent_timestamp }
response: { opt_in_recorded, marketing_enabled }

GET /api/notifications/whatsapp/delivery-costs:
params: country_code, message_type, volume
response: { cost_per_message, volume_discounts[], billing_details }
Events Notifications Module:
yamlNotification Events:
- NotificationSent: { notificationId, channel, recipient, campaign_id?, cost }
- NotificationDelivered: { notificationId, delivered_at, provider_confirmation }
- NotificationRead: { notificationId, read_at, engagement_score }
- NotificationClicked: { notificationId, clicked_at, click_url, conversion_potential }
- NotificationFailed: { notificationId, failure_reason, retry_scheduled }
- CampaignLaunched: { campaignId, audience_size, estimated_cost, duration }
- CampaignCompleted: { campaignId, final_metrics, roi, recommendations }
- TemplateApproved: { templateId, approved_by, activation_time }
- OptInReceived: { customer_id, channel, consent_source, marketing_enabled }
- OptOutReceived: { customer_id, channel, opt_out_reason, compliance_action }
- WhatsAppWebhookReceived: { webhook_type, message_status, billing_event }
- ABTestResultSignificant: { campaignId, winning_variant, confidence_level, recommendation }

11. COMPLIANCE MODULE 🔒 ACTIVABLE (Enterprise Only)
    Business Value:

Pricing Tier: Enterprise only (+$30/mes)
Value Proposition: Automated regulatory compliance, 90% audit preparation reduction
Configuration Key: modules.compliance.enabled
Dependencies: core, staff modules (financial module recommended)

Responsabilidades:

HACCP compliance automático con control points críticos
Temperature monitoring con IoT integration y alertas
Audit management interno y externo con findings tracking
Regulatory reporting automático para autoridades
Food safety protocols con certificación tracking

Entidades Principales:
HACCPControl Entity
java@Entity
@Table(name = "haccp_controls")
public class HACCPControl extends BaseEntity {
@Column(nullable = false)
private String controlPointName;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private HACCPType haccpType; // CCP, OPRP, PRP
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ControlCategory category; // TEMPERATURE, TIME, PH, CHEMICAL, BIOLOGICAL
    
    // Critical limits
    @Column(precision = 8, scale = 2)
    private BigDecimal criticalLimitMin;
    
    @Column(precision = 8, scale = 2)
    private BigDecimal criticalLimitMax;
    
    @Column(nullable = false)
    private String unit; // °C, minutes, pH, ppm
    
    // Monitoring requirements
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MonitoringFrequency monitoringFrequency;
    
    @Column(nullable = false)
    private Integer monitoringIntervalMinutes;
    
    @Column(columnDefinition = "TEXT", nullable = false)
    private String monitoringMethod;
    
    @ManyToOne
    @JoinColumn(name = "responsible_employee_id")
    private Employee responsibleEmployee;
    
    // Corrective actions
    @Column(columnDefinition = "TEXT", nullable = false)
    private String correctiveActions;
    
    @Column(columnDefinition = "TEXT")
    private String preventiveActions;
    
    // Documentation requirements
    @Column(nullable = false)
    private Boolean requiresDocumentation = true;
    
    @Column(nullable = false)
    private Integer recordRetentionDays = 730; // 2 years default
    
    // Associated equipment/areas
    @ElementCollection
    private Set<String> monitoringEquipment = new HashSet<>();
    
    @ElementCollection
    private Set<String> applicableAreas = new HashSet<>(); // Kitchen, storage, prep area
    
    // Compliance status
    @Column(nullable = false)
    private Boolean isActive = true;
    
    @Column
    private LocalDateTime lastVerificationDate;
    
    private UUID verifiedByUserId;
    
    // Regulation references
    @ElementCollection
    private Set<String> regulatoryReferences = new HashSet<>(); // FDA, HACCP, local regulations
    
    @OneToMany(mappedBy = "haccpControl", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<HACCPMonitoringRecord> monitoringRecords = new HashSet<>();
}

enum HACCPType {
CCP, // Critical Control Point
OPRP, // Operational Prerequisite Program
PRP  // Prerequisite Program
}

enum ControlCategory {
TEMPERATURE, TIME, PH, MOISTURE, CHEMICAL, BIOLOGICAL, FOREIGN_OBJECT, ALLERGEN
}

enum MonitoringFrequency {
CONTINUOUS, EVERY_15_MIN, HOURLY, EVERY_4_HOURS, DAILY, WEEKLY
}
TemperatureLog Entity
java@Entity
@Table(name = "temperature_logs")
public class TemperatureLog extends BaseEntity {
@ManyToOne
@JoinColumn(name = "haccp_control_id")
private HACCPControl haccpControl;

    @Column(nullable = false)
    private String equipmentId; // Freezer-001, Cooler-002, etc.
    
    @Column(nullable = false)
    private String equipmentName;
    
    @Column(nullable = false)
    private String location; // Kitchen, storage, prep area
    
    // Temperature readings
    @Column(nullable = false, precision = 6, scale = 2)
    private BigDecimal temperature;
    
    @Column(nullable = false)
    private String unit = "°C";
    
    @Column(nullable = false)
    private LocalDateTime recordedAt;
    
    // Data source
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TemperatureSource source; // IOT_SENSOR, MANUAL_ENTRY, THERMOMETER
    
    @Column
    private String sensorId; // IoT sensor identifier
    
    @ManyToOne
    @JoinColumn(name = "recorded_by_employee_id")
    private Employee recordedByEmployee; // For manual entries
    
    // Compliance checking
    @Column(nullable = false)
    private Boolean isWithinLimits = true;
    
    @Column(precision = 6, scale = 2)
    private BigDecimal deviationAmount = BigDecimal.ZERO;
    
    @Enumerated(EnumType.STRING)
    private ComplianceStatus complianceStatus = ComplianceStatus.COMPLIANT;
    
    // Alerts and actions
    @Column(nullable = false)
    private Boolean alertTriggered = false;
    
    @Column
    private LocalDateTime alertSentAt;
    
    @ElementCollection
    private Set<String> alertRecipients = new HashSet<>();
    
    @Column(columnDefinition = "TEXT")
    private String correctiveActionTaken;
    
    private UUID correctiveActionByUserId;
    private LocalDateTime correctiveActionAt;
    
    // Verification
    @Column(nullable = false)
    private Boolean isVerified = false;
    
    private UUID verifiedByUserId;
    private LocalDateTime verifiedAt;
    
    private String verificationNotes;
    
    // Equipment health
    @Column(precision = 5, scale = 2)
    private BigDecimal batteryLevel; // For wireless sensors
    
    @Column(precision = 5, scale = 2)
    private BigDecimal signalStrength; // For IoT sensors
    
    @Column
    private LocalDateTime lastMaintenanceDate;
    
    private String notes;
}

enum TemperatureSource {
IOT_SENSOR, MANUAL_ENTRY, DIGITAL_THERMOMETER, PROBE_THERMOMETER
}

enum ComplianceStatus {
COMPLIANT, MINOR_DEVIATION, MAJOR_DEVIATION, CRITICAL_DEVIATION
}
Audit Entity
java@Entity
@Table(name = "audits")
public class Audit extends BaseEntity {
@Column(nullable = false)
private String auditName;

    @Column(columnDefinition = "TEXT")
    private String description;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AuditType auditType; // INTERNAL, EXTERNAL, REGULATORY, CERTIFICATION
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AuditScope scope; // HACCP, FOOD_SAFETY, FINANCIAL, OPERATIONAL
    
    @Column(nullable = false)
    private LocalDate auditDate;
    
    @Column
    private LocalDate followUpDate;
    
    // Auditor information
    @ManyToOne
    @JoinColumn(name = "lead_auditor_employee_id")
    private Employee leadAuditorEmployee; // For internal audits
    
    @Column
    private String externalAuditorName; // For external audits
    
    @Column
    private String auditingOrganization; // FDA, health department, certification body
    
    @Column
    private String auditorCredentials;
    
    // Audit planning
    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    private AuditPlan auditPlan; // JSON structure with areas, checklists, etc.
    
    @ElementCollection
    private Set<String> areasAudited = new HashSet<>(); // Kitchen, storage, dining, etc.
    
    @ElementCollection
    private Set<String> processesAudited = new HashSet<>(); // Food prep, cleaning, receiving
    
    // Audit results
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AuditStatus status = AuditStatus.PLANNED;
    
    @Enumerated(EnumType.STRING)
    private AuditResult overallResult; // PASS, PASS_WITH_OBSERVATIONS, FAIL
    
    @Column(precision = 5, scale = 2)
    private BigDecimal overallScore; // 0-100 scale
    
    @Column(nullable = false)
    private Integer totalFindings = 0;
    
    @Column(nullable = false)
    private Integer criticalFindings = 0;
    
    @Column(nullable = false)
    private Integer majorFindings = 0;
    
    @Column(nullable = false)
    private Integer minorFindings = 0;
    
    // Documentation
    @Column(columnDefinition = "TEXT")
    private String executiveSummary;
    
    @Column(columnDefinition = "TEXT")
    private String recommendations;
    
    @Column
    private String reportFilePath;
    
    @Column
    private String certificateFilePath; // For passed certification audits
    
    @Column
    private LocalDate certificateExpiryDate;
    
    // Follow-up
    @Column(nullable = false)
    private Boolean requiresFollowUp = false;
    
    @Column
    private LocalDate correctionDeadline;
    
    @Column(nullable = false)
    private Boolean isCompleted = false;
    
    @Column
    private LocalDateTime completedAt;
    
    @OneToMany(mappedBy = "audit", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<AuditFinding> findings = new HashSet<>();
}

enum AuditType {
INTERNAL, EXTERNAL, REGULATORY, CERTIFICATION, CUSTOMER, SUPPLIER
}

enum AuditScope {
HACCP, FOOD_SAFETY, FINANCIAL, OPERATIONAL, QUALITY, ENVIRONMENTAL
}

enum AuditStatus {
PLANNED, IN_PROGRESS, COMPLETED, CANCELLED, FOLLOW_UP_REQUIRED
}

enum AuditResult {
PASS, PASS_WITH_OBSERVATIONS, FAIL, PENDING
}

@Embeddable
class AuditPlan {
private List<String> checklistItems;
private List<String> documentsToreview;
private List<String> processesToObserve;
private List<String> employeesToInterview;
private Integer estimatedDurationHours;
private Map<String, String> scopeDetails;
}
ComplianceReport Entity
java@Entity
@Table(name = "compliance_reports")
public class ComplianceReport extends BaseEntity {
@Column(nullable = false)
private String reportName;

    @Column(columnDefinition = "TEXT")
    private String description;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ComplianceReportType reportType; // REGULATORY_SUBMISSION, INTERNAL_REVIEW, CERTIFICATION
    
    @Column(nullable = false)
    private LocalDate reportPeriodStart;
    
    @Column(nullable = false)
    private LocalDate reportPeriodEnd;
    
    @Column(nullable = false)
    private LocalDateTime generatedAt;
    
    // Target authority/organization
    @Column
    private String targetAuthority; // FDA, Health Department, ISO certification body
    
    @Column
    private String regulatoryReference; // Regulation number, standard reference
    
    @Column
    private LocalDate submissionDeadline;
    
    @Column
    private LocalDate submittedAt;
    
    // Report content
    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb", nullable = false)
    private ComplianceReportData reportData; // JSON structure with compliance metrics
    
    // File attachments
    @Column
    private String reportFilePath;
    
    @ElementCollection
    private Set<String> attachmentFilePaths = new HashSet<>(); // Supporting documents
    
    // Status tracking
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ComplianceReportStatus status = ComplianceReportStatus.DRAFT;
    
    @Column(nullable = false)
    private Boolean isAutoGenerated = true;
    
    @ManyToOne
    @JoinColumn(name = "prepared_by_user_id")
    private User preparedByUser;
    
    @ManyToOne
    @JoinColumn(name = "approved_by_user_id")
    private User approvedByUser;
    
    @Column
    private LocalDateTime approvedAt;
    
    // Compliance metrics summary
    @Column(precision = 5, scale = 2)
    private BigDecimal overallComplianceScore = BigDecimal.ZERO;
    
    @Column(nullable = false)
    private Integer totalViolations = 0;
    
    @Column(nullable = false)
    private Integer criticalViolations = 0;
    
    @Column(nullable = false)
    private Integer resolvedViolations = 0;
    
    @Column(nullable = false)
    private Integer pendingViolations = 0;
    
    // Response tracking
    @Column
    private String authorityResponseReference;
    
    @Column
    private LocalDateTime authorityResponseDate;
    
    @Enumerated(EnumType.STRING)
    private AuthorityResponseType authorityResponse;
    
    @Column(columnDefinition = "TEXT")
    private String authorityComments;
    
    @Column(columnDefinition = "TEXT")
    private String requiredActions;
    
    @Column
    private LocalDate actionDeadline;
    
    private String notes;
}

enum ComplianceReportType {
REGULATORY_SUBMISSION, INTERNAL_REVIEW, CERTIFICATION,
INCIDENT_REPORT, CORRECTIVE_ACTION, AUDIT_RESPONSE
}

enum ComplianceReportStatus {
DRAFT, PENDING_APPROVAL, APPROVED, SUBMITTED, ACKNOWLEDGED, UNDER_REVIEW
}

enum AuthorityResponseType {
ACCEPTED, ACCEPTED_WITH_CONDITIONS, REJECTED, PENDING_REVIEW, REQUEST_MORE_INFO
}

@Embeddable
class ComplianceReportData {
// HACCP compliance
private Map<String, BigDecimal> haccpScores;
private List<String> haccpViolations;
private Integer temperatureDeviations;

    // Food safety metrics
    private Integer foodSafetyIncidents;
    private List<String> allergenControls;
    private Integer employeesTrainedPercentage;
    
    // Audit results
    private List<AuditSummary> auditSummaries;
    private Integer openFindings;
    private Integer closedFindings;
    
    // Training compliance
    private Map<String, Integer> trainingCompletionRates;
    private List<String> expiredCertifications;
    
    // Equipment compliance
    private Integer equipmentMaintenanceCompliance;
    private List<String> calibrationStatus;
    
    // Additional metrics by report type
    private Map<String, Object> additionalMetrics;
}
APIs Compliance Module:
HACCP Management APIs
yamlGET /api/compliance/haccp/controls:
filters: category, monitoring_frequency, compliance_status
response: { haccp_controls[], monitoring_summary, deviations_count }

POST /api/compliance/haccp/monitoring-record:
body: { control_id, measurement_value, monitoring_method, employee_id }
response: { monitoring_record, compliance_status, alerts_triggered[] }

GET /api/compliance/haccp/deviations:
params: date_range, severity, control_id
response: { deviations[], corrective_actions[], trend_analysis }

POST /api/compliance/haccp/corrective-action:
body: { deviation_id, action_taken, responsible_employee, verification_method }
response: { corrective_action_recorded, compliance_restored, follow_up_required }
Temperature Monitoring APIs
yamlPOST /api/compliance/temperature/log:
body: { equipment_id, temperature, source, sensor_id?, employee_id? }
response: { temperature_log, compliance_status, alert_triggered }

GET /api/compliance/temperature/equipment/{id}/history:
params: date_range, include_deviations_only
response: { temperature_history[], deviation_analysis, equipment_health }

POST /api/compliance/temperature/iot/webhook:
body: { sensor_data, timestamp, equipment_mapping }
response: { processed_readings[], alerts_generated[], calibration_status }

GET /api/compliance/temperature/alerts:
filters: equipment_id, severity, resolved_status
response: { active_alerts[], alert_history[], response_times[] }
Audit Management APIs
yamlPOST /api/compliance/audits:
body: { audit_type, scope, audit_date, auditor_info, areas_to_audit }
response: { audit_created, checklist_generated, preparation_tasks[] }

POST /api/compliance/audits/{id}/findings:
body: { finding_description, severity, area, evidence, corrective_action }
response: { finding_recorded, compliance_impact, follow_up_required }

GET /api/compliance/audits/{id}/report:
response: { audit_report, executive_summary, recommendations[], certification_status }

PUT /api/compliance/audits/{id}/close:
body: { final_score, overall_result, certification_granted }
response: { audit_closed, certificate_generated?, next_audit_due }
Regulatory Reporting APIs
yamlPOST /api/compliance/reports/generate:
body: { report_type, period, target_authority, include_attachments }
response: { report_id, estimated_completion, compliance_score_preview }

GET /api/compliance/reports/regulatory-calendar:
params: year, authority, report_types
response: { upcoming_deadlines[], submission_status[], preparation_time[] }

POST /api/compliance/reports/{id}/submit:
body: { submission_method, authority_contact, tracking_reference }
response: { submitted_confirmation, tracking_info, acknowledgment_expected }

GET /api/compliance/violations/summary:
params: period, severity, resolution_status
response: { violations_summary[], trends[], corrective_actions_effectiveness }
Events Compliance Module:
yamlCompliance Events:
- TemperatureAlert: { equipmentId, temperature, deviation_amount, severity, location }
- ComplianceViolation: { violation_type, severity, area, haccp_control?, immediate_action_required }
- HACCPDeviationDetected: { controlPointId, deviation_type, corrective_action_triggered }
- AuditScheduled: { auditId, audit_type, auditor, preparation_deadline }
- AuditCompleted: { auditId, overall_result, findings_count, certification_status }
- AuditFindingAdded: { findingId, severity, area, corrective_action_deadline }
- RegulatoryDeadlineApproaching: { report_type, deadline, days_remaining, preparation_status }
- ComplianceReportGenerated: { reportId, report_type, compliance_score, violations_count }
- CertificationExpiring: { certification_type, expiry_date, renewal_required }
- EquipmentCalibrationDue: { equipmentId, last_calibration, due_date, compliance_impact }
- TrainingCertificationExpired: { employeeId, certification_type, expired_date, retraining_required }
- ComplianceScoreChanged: { previous_score, new_score, contributing_factors[], improvement_recommendations[] }vvv