package team.skyprojava.websitebackend.dto;

import lombok.Data;

/**
 * Класс RegisterReqDto для хранения данных о регистрации пользователя
 */
@Data
public class RegisterReqDto {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String phone;
    private Role role;
}