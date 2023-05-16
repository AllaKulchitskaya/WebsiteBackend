package team.skyprojava.websitebackend.dto;

import lombok.Data;

/**
 * Класс NewPasswordDto для хранения паролей пользователя
 */
@Data
public class NewPasswordDto {
    private String currentPassword;
    private String newPassword;
}