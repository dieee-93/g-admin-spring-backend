package diego.soro.model2.core.configuration;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "company_configuration")
@Data
public class CompanyConfiguration {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "company_id", nullable = false)
    private Long companyId;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "subscription_tier", nullable = false)
    private SubscriptionTier subscriptionTier;
    
    @Column(name = "max_users")
    private Integer maxUsers;
    
    @Column(name = "max_locations")
    private Integer maxLocations;
    
    @Column(name = "features_enabled")
    private String featuresEnabled;
}