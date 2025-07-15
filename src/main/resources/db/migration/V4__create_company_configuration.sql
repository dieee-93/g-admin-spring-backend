CREATE TABLE company_configuration (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    company_id BIGINT NOT NULL,
    subscription_tier VARCHAR(50) NOT NULL,
    max_users INT,
    max_locations INT,
    features_enabled TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE KEY uk_company_configuration_company_id (company_id)
);