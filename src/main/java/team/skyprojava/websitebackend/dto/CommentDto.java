package team.skyprojava.websitebackend.dto;

import lombok.Data;

/**
 * Класс CommentDto  для хранения комментариев пользователя
 */
@Data
public class CommentDto {
    private int author;
    private String authorImage;
    private String authorFirstName;
    private long createdAt;
    private int pk;
    private String text;
}