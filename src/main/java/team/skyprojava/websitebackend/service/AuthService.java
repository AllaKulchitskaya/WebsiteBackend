package team.skyprojava.websitebackend.service;

import team.skyprojava.websitebackend.dto.RegisterReqDto;

/**
 * Способы входа пользователя в систему и регистрации
 */
public interface AuthService {

    /**
     * Логин
     *
     * @param userName
     * @param password
     * @return the boolean
     */
    boolean login(String userName, String password);

    /**
     * Регистрация
     *
     * @param registerReqDto
     * @return the boolean
     */
    boolean register(RegisterReqDto registerReqDto);
}