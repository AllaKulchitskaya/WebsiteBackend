package team.skyprojava.websitebackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Класс для обработки ошибок в пользователе
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException (Integer id) {
        super("User with id: " + id + " not found");
    }
    public UserNotFoundException (String message) {
        super(message);
    }
}