package diego.soro.model2.core.configuration;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CompanyConfigurationTest {
    
    @Test
    void testCompanyConfigurationCreation() {
        CompanyConfiguration config = new CompanyConfiguration();
        config.setCompanyId(1L);
        config.setSubscriptionTier(SubscriptionTier.BASIC);
        config.setMaxUsers(10);
        
        assertEquals(1L, config.getCompanyId());
        assertEquals(SubscriptionTier.BASIC, config.getSubscriptionTier());
        assertEquals(10, config.getMaxUsers());
    }
}