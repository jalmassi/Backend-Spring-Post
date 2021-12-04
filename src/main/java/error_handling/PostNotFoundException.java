package error_handling;

public class PostNotFoundException extends RuntimeException {
    public PostNotFoundException(String exMessage) {
        super(exMessage);
    }
}
