package diego.soro;

import graphql.ErrorClassification;
import graphql.GraphQLError;
import graphql.language.SourceLocation;

import java.util.List;

public class GAdminException extends RuntimeException implements GraphQLError {

    private final GAdminErrorType errorType;

    public GAdminException(String message, GAdminErrorType errorType) {
        super(message);
        this.errorType = errorType;
    }

    @Override
    public ErrorClassification getErrorType() {
        return this.errorType;
    }

    @Override
    public List<SourceLocation> getLocations() {
        return null; // Puedes personalizar esto según tus necesidades
    }



    // Otros métodos de GraphQLError que puedes implementar según sea necesario
}
