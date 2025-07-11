package diego.soro.exception;


import diego.soro.GAdminErrorType;
import diego.soro.GAdminException;

public class NotFoundException extends GAdminException {
    public NotFoundException(String message) {
        super(message, GAdminErrorType.NOT_FOUND);
    }
}
