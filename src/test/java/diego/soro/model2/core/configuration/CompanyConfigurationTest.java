package diego.soro.model2.core.configuration;

import diego.soro.model2.core.Company.Company;
import diego.soro.model2.core.configuration.repository.CompanyConfigurationRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

/**
 * Unit tests for CompanyConfiguration entity and repository
 *
 * Tests cover:
 * - Entity creation and validation
 * - Repository CRUD operations
 * - Custom query methods
 * - Business logic helper methods
 * - Module management functionality
 */
@DataJpaTest
@ActiveProfiles("test")
@DisplayName("CompanyConfiguration Tests")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class CompanyConfigurationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CompanyConfigurationRepository repository;

    private Company testCompany;
    private CompanyConfiguration testConfiguration;

    @BeforeEach
    void setUp() {
        // Create a test company
        testCompany = Company.builder()
                .name("Test Restaurant")
                .taxId("20-12345678-9")
                .email("test@restaurant.com")
                .phone("+54 11 1234-5678")
                .address("Test Address 123")
                .country("Argentina")
                .timezone("America/Argentina/Buenos_Aires")
                .active(true)
                .build();

        testCompany = entityManager.persistAndFlush(testCompany);

        // Create test configuration
        testConfiguration = CompanyConfiguration.builder()
                .company(testCompany)
                .tier(SubscriptionTier.LITE)
                .lastModifiedBy("test-user")
                .configurationNotes("Initial configuration for testing")
                .active(true)
                .build();
    }

    // ===== ENTITY CREATION TESTS =====

    @Test
    @Order(1)
    @DisplayName("Should create configuration with default values")
    void shouldCreateConfigurationWithDefaults() {
        // When
        CompanyConfiguration saved = repository.save(testConfiguration);

        // Then
        assertThat(saved).isNotNull();
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getCompany()).isEqualTo(testCompany);
        assertThat(saved.getTier()).isEqualTo(SubscriptionTier.LITE);

        // Core modules should be enabled by default
        assertThat(saved.getCoreEnabled()).isTrue();
        assertThat(saved.getInventoryEnabled()).isTrue();
        assertThat(saved.getSalesEnabled()).isTrue();
        assertThat(saved.getMenuEnabled()).isTrue();

        // Activable modules should be disabled by default
        assertThat(saved.getMenuAdvancedEnabled()).isFalse();
        assertThat(saved.getKitchenEnabled()).isFalse();
        assertThat(saved.getCustomerEnabled()).isFalse();
        assertThat(saved.getTableEnabled()).isFalse();
        assertThat(saved.getFinancialEnabled()).isFalse();
        assertThat(saved.getStaffEnabled()).isFalse();
        assertThat(saved.getAnalyticsEnabled()).isFalse();
        assertThat(saved.getNotificationsEnabled()).isFalse();
        assertThat(saved.getComplianceEnabled()).isFalse();

        // Metadata should be set
        assertThat(saved.getLastConfigurationChange()).isNotNull();
        assertThat(saved.getLastModifiedBy()).isEqualTo("test-user");
    }

    @Test
    @DisplayName("Should enforce unique constraint on company_id")
    void shouldEnforceUniqueCompanyConstraint() {
        // Given
        repository.save(testConfiguration);

        // When - Try to create another configuration for same company
        CompanyConfiguration duplicate = CompanyConfiguration.builder()
                .company(testCompany)
                .tier(SubscriptionTier.PRO)
                .active(true)
                .build();

        // Then - Should throw constraint violation
        assertThatThrownBy(() -> {
            repository.save(duplicate);
            entityManager.flush();
        }).isInstanceOf(Exception.class);
    }

    // ===== REPOSITORY QUERY TESTS =====

    @Test
    @DisplayName("Should find configuration by company ID")
    void shouldFindByCompanyId() {
        // Given
        CompanyConfiguration saved = repository.save(testConfiguration);

        // When
        Optional<CompanyConfiguration> found = repository.findByCompanyId(testCompany.getId());

        // Then
        assertThat(found).isPresent();
        assertThat(found.get().getId()).isEqualTo(saved.getId());
        assertThat(found.get().getCompany().getId()).isEqualTo(testCompany.getId());
    }

    @Test
    @DisplayName("Should check existence by company ID")
    void shouldCheckExistenceByCompanyId() {
        // Given
        repository.save(testConfiguration);

        // When & Then
        assertThat(repository.existsByCompanyId(testCompany.getId())).isTrue();
        assertThat(repository.existsByCompanyId(UUID.randomUUID())).isFalse();
    }

    @Test
    @DisplayName("Should find configurations by tier")
    void shouldFindByTier() {
        // Given
        repository.save(testConfiguration);

        CompanyConfiguration proConfig = createConfigurationForTier(SubscriptionTier.PRO);
        repository.save(proConfig);

        // When
        List<CompanyConfiguration> liteConfigs = repository.findByTier(SubscriptionTier.LITE);
        List<CompanyConfiguration> proConfigs = repository.findByTier(SubscriptionTier.PRO);

        // Then
        assertThat(liteConfigs).hasSize(1);
        assertThat(liteConfigs.get(0).getTier()).isEqualTo(SubscriptionTier.LITE);

        assertThat(proConfigs).hasSize(1);
        assertThat(proConfigs.get(0).getTier()).isEqualTo(SubscriptionTier.PRO);
    }

    @Test
    @DisplayName("Should count configurations by tier")
    void shouldCountByTier() {
        // Given
        repository.save(testConfiguration);
        repository.save(createConfigurationForTier(SubscriptionTier.PRO));
        repository.save(createConfigurationForTier(SubscriptionTier.PRO));

        // When & Then
        assertThat(repository.countByTier(SubscriptionTier.LITE)).isEqualTo(1);
        assertThat(repository.countByTier(SubscriptionTier.PRO)).isEqualTo(2);
        assertThat(repository.countByTier(SubscriptionTier.ENTERPRISE)).isEqualTo(0);
    }

    // ===== MODULE-SPECIFIC QUERY TESTS =====

    @Test
    @DisplayName("Should find configurations with specific modules enabled")
    void shouldFindByModuleEnabled() {
        // Given
        testConfiguration.setKitchenEnabled(true);
        testConfiguration.setCustomerEnabled(true);
        repository.save(testConfiguration);

        CompanyConfiguration withoutModules = createConfigurationForTier(SubscriptionTier.PRO);
        repository.save(withoutModules);

        // When
        List<CompanyConfiguration> withKitchen = repository.findByKitchenEnabledTrue();
        List<CompanyConfiguration> withCustomer = repository.findByCustomerEnabledTrue();
        List<CompanyConfiguration> withAdvancedMenu = repository.findByMenuAdvancedEnabledTrue();

        // Then
        assertThat(withKitchen).hasSize(1);
        assertThat(withKitchen.get(0).getKitchenEnabled()).isTrue();

        assertThat(withCustomer).hasSize(1);
        assertThat(withCustomer.get(0).getCustomerEnabled()).isTrue();

        assertThat(withAdvancedMenu).isEmpty();
    }

    @Test
    @DisplayName("Should count modules correctly")
    void shouldCountModules() {
        // Given
        testConfiguration.setKitchenEnabled(true);
        testConfiguration.setCustomerEnabled(true);
        repository.save(testConfiguration);

        // When & Then
        assertThat(repository.countByKitchenEnabledTrue()).isEqualTo(1);
        assertThat(repository.countByCustomerEnabledTrue()).isEqualTo(1);
        assertThat(repository.countByMenuAdvancedEnabledTrue()).isEqualTo(0);
    }

    @Test
    @DisplayName("Should find configurations by active module count")
    void shouldFindByActiveModuleCount() {
        // Given - Configuration with 3 modules
        testConfiguration.setKitchenEnabled(true);
        testConfiguration.setCustomerEnabled(true);
        testConfiguration.setAnalyticsEnabled(true);
        repository.save(testConfiguration);

        // Configuration with 1 module
        CompanyConfiguration singleModule = createConfigurationForTier(SubscriptionTier.PRO);
        singleModule.setStaffEnabled(true);
        repository.save(singleModule);

        // When
        List<CompanyConfiguration> withAtLeast2Modules =
                repository.findByActiveModuleCountGreaterThanEqual(2);
        List<CompanyConfiguration> withAtLeast1Module =
                repository.findByActiveModuleCountGreaterThanEqual(1);
        List<CompanyConfiguration> withAtLeast5Modules =
                repository.findByActiveModuleCountGreaterThanEqual(5);

        // Then
        assertThat(withAtLeast2Modules).hasSize(1);
        assertThat(withAtLeast1Module).hasSize(2);
        assertThat(withAtLeast5Modules).isEmpty();
    }

    @Test
    @DisplayName("Should find lite tier with paid modules")
    void shouldFindLiteTierWithPaidModules() {
        // Given - Lite tier with paid modules
        testConfiguration.setKitchenEnabled(true);
        testConfiguration.setCustomerEnabled(true);
        repository.save(testConfiguration);

        // Lite tier without paid modules
        CompanyConfiguration liteBasic = createConfigurationForTier(SubscriptionTier.LITE);
        repository.save(liteBasic);

        // Pro tier with paid modules (should not be included)
        CompanyConfiguration proWithModules = createConfigurationForTier(SubscriptionTier.PRO);
        proWithModules.setAnalyticsEnabled(true);
        repository.save(proWithModules);

        // When
        List<CompanyConfiguration> upgradeCandidates = repository.findLiteTierWithPaidModules();

        // Then
        assertThat(upgradeCandidates).hasSize(1);
        assertThat(upgradeCandidates.get(0).getTier()).isEqualTo(SubscriptionTier.LITE);
        assertThat(upgradeCandidates.get(0).getKitchenEnabled()).isTrue();
    }

    // ===== HELPER METHOD TESTS =====

    @Test
    @DisplayName("Should check module enabled by name")
    void shouldCheckModuleEnabledByName() {
        // Given
        testConfiguration.setKitchenEnabled(true);
        testConfiguration.setCustomerEnabled(false);

        // When & Then
        assertThat(testConfiguration.isModuleEnabled("core")).isTrue();
        assertThat(testConfiguration.isModuleEnabled("inventory")).isTrue();
        assertThat(testConfiguration.isModuleEnabled("sales")).isTrue();
        assertThat(testConfiguration.isModuleEnabled("menu")).isTrue();

        assertThat(testConfiguration.isModuleEnabled("kitchen")).isTrue();
        assertThat(testConfiguration.isModuleEnabled("customer")).isFalse();
        assertThat(testConfiguration.isModuleEnabled("menu-advanced")).isFalse();
        assertThat(testConfiguration.isModuleEnabled("nonexistent")).isFalse();

        // Case insensitive
        assertThat(testConfiguration.isModuleEnabled("KITCHEN")).isTrue();
        assertThat(testConfiguration.isModuleEnabled("Kitchen")).isTrue();
    }

    @Test
    @DisplayName("Should count active modules correctly")
    void shouldCountActiveModules() {
        // Given - No activable modules enabled
        assertThat(testConfiguration.getActiveModuleCount()).isEqualTo(0);

        // When - Enable some modules
        testConfiguration.setKitchenEnabled(true);
        testConfiguration.setCustomerEnabled(true);
        testConfiguration.setAnalyticsEnabled(true);

        // Then
        assertThat(testConfiguration.getActiveModuleCount()).isEqualTo(3);

        // Core modules should not count
        testConfiguration.setCoreEnabled(false); // This shouldn't happen in practice
        assertThat(testConfiguration.getActiveModuleCount()).isEqualTo(3);
    }

    @Test
    @DisplayName("Should update metadata on persist/update")
    void shouldUpdateMetadataOnPersistUpdate() {
        // Given
        LocalDateTime beforeSave = LocalDateTime.now().minusSeconds(1);

        // When
        CompanyConfiguration saved = repository.save(testConfiguration);

        // Then
        assertThat(saved.getLastConfigurationChange()).isNotNull();
        assertThat(saved.getLastConfigurationChange()).isAfter(beforeSave);

        // When - Update
        LocalDateTime beforeUpdate = LocalDateTime.now().minusSeconds(1);
        saved.setKitchenEnabled(true);
        CompanyConfiguration updated = repository.save(saved);

        // Then
        assertThat(updated.getLastConfigurationChange()).isAfter(beforeUpdate);
    }

    // ===== HELPER METHODS =====

    private CompanyConfiguration createConfigurationForTier(SubscriptionTier tier) {
        Company anotherCompany = Company.builder()
                .name("Another Restaurant")
                .taxId("20-87654321-9")
                .email("another@restaurant.com")
                .active(true)
                .build();

        anotherCompany = entityManager.persistAndFlush(anotherCompany);

        return CompanyConfiguration.builder()
                .company(anotherCompany)
                .tier(tier)
                .active(true)
                .build();
    }
}