package team.skyprojava.websitebackend.service;

import team.skyprojava.websitebackend.dto.RegisterReqDto;

/**
 * Интерфейс сервиса для работы с пользователями, проходящими регистрацию или авторизацию
 */
public interface AuthService {

    /**
     * Метод для авторизации пользователя
     *
     * @param userName
     * @param password
     * @return the boolean
     */
    boolean login(String userName, String password);

    /**
     * Метод для регистраций пользователя
     *
     * @param registerReqDto
     * @return the boolean
     */
    boolean register(RegisterReqDto registerReqDto);
}