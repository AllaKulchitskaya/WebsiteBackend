package team.skyprojava.websitebackend.dto;

import lombok.*;

/**
 * Класс LoginReqDto для хранения логина данных
 */
@Data
public class LoginReqDto {
    private String password;
    private String username;
}