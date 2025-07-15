package diego.soro;



import graphql.ErrorClassification;

public enum GAdminErrorType implements ErrorClassification {
    NOT_FOUND,
    UNAUTHORIZED,
    VALIDATION_FAILED,
    TENANT_ACCESS_DENIED,
    FORBIDDEN,
    INTERNAL_ERROR

}
