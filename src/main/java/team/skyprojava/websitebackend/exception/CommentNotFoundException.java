package team.skyprojava.websitebackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Класс для обработки ошибок в комментариях
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class CommentNotFoundException extends RuntimeException {
    public CommentNotFoundException(Integer id) {
        super("Comment with id: " + id + " not found");
    }
    public CommentNotFoundException(String message) {
        super(message);
    }

}