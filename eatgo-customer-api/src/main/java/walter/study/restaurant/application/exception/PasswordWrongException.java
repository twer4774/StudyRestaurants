package walter.study.restaurant.application.exception;

public class PasswordWrongException extends RuntimeException{

    public PasswordWrongException() {
        super("Password is wrong");
    }
}
