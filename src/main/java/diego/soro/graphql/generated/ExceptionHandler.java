package diego.soro.graphql.generated;


import com.netflix.graphql.types.errors.ErrorType;
import diego.soro.exception.InvalidArgument;
import diego.soro.exception.NotFoundException;
import graphql.GraphQLError;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.graphql.data.method.annotation.GraphQlExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.util.Optional;

@ControllerAdvice
public class ExceptionHandler {
    @GraphQlExceptionHandler
    public GraphQLError handle(ConstraintViolationException e) {
        return GraphQLError.newError().errorType(ErrorType.BAD_REQUEST).message(e.getMessage()).build();
    }
    @GraphQlExceptionHandler
    public GraphQLError handle(NotFoundException e) {
        return GraphQLError.newError().errorType(ErrorType.NOT_FOUND).message(e.getMessage()).build();
    }
    @GraphQlExceptionHandler
    public GraphQLError handle(InvalidArgument e) {
        return GraphQLError.newError().errorType(ErrorType.BAD_REQUEST).message(e.getMessage()).build();
    }
    public GraphQLError handle(DataIntegrityViolationException e) {
        String message = Optional.ofNullable(e.getRootCause())
                .map(Throwable::getMessage)
                .orElse("Violación de integridad de datos");
        if (message.contains("Duplicate entry")) {
            if (e.getMessage().contains("insert into raw_material")) {
                return GraphQLError.newError()
                        .errorType(ErrorType.BAD_REQUEST)
                        .message("El nombre de la materia prima ya existe")
                        .build();
            } else if (message.contains("insert into category")) {
                return GraphQLError.newError()
                        .errorType(ErrorType.BAD_REQUEST)
                        .message("El nombre de la categoría ya existe")
                        .build();
            } else {
                return GraphQLError.newError()
                        .errorType(ErrorType.BAD_REQUEST)
                        .message("Violación de clave única: " + message)
                        .build();
            }
        }
        return GraphQLError.newError().errorType(ErrorType.BAD_REQUEST).message(e.toString()).build();
    }
}
