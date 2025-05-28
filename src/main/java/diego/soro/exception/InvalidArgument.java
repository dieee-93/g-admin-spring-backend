package diego.soro.exception;

public class InvalidArgument extends AppException {
    public InvalidArgument(String message) {
        super("INVALID_ARGUMENT: " + message);
    }
}
