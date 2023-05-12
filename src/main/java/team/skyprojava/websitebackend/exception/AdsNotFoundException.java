package team.skyprojava.websitebackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Класс для обработки ошибок в объявлениях
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class AdsNotFoundException extends RuntimeException {
    public AdsNotFoundException (Integer id) {
        super("Ads with id: " + id + " not found");
    }

    public AdsNotFoundException(String message) {
        super(message);
    }
}