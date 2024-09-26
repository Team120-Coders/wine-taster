package mate.academy.winetaster.exception;

public class WineNotFoundException extends RuntimeException {
    public WineNotFoundException(String message) {
        super(message);
    }
}
