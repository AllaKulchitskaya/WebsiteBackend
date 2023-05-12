package team.skyprojava.websitebackend.service;

import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;
import team.skyprojava.websitebackend.dto.NewPasswordDto;
import team.skyprojava.websitebackend.dto.UserDto;

import java.io.IOException;

/**
 * Интерфейс сервиса для работы с пользователем
 */
public interface UserService {

    /**
     * Получение информации об авторизованном пользователе
     *
     * @return UserDto данный пользователь
     */
    UserDto getUserMe(Authentication authentication);

    /**
     * Изменение пользователя
     *
     * @param userDto Объект пользователя с новыми данными
     * @return UserDto Изменённый пользователь
     */
    UserDto updateUser(UserDto userDto, Authentication authentication);

    /**
     * Изменение пароля пользователя
     *
     * @param newPasswordDto  нынешний и новый пароль
     * @param authentication аутентификация
     */
    void newPassword(NewPasswordDto newPasswordDto, Authentication authentication);

    /**
     * Обновление аватара пользователя
     *
     * @param image новый аватар
     * @param authentication аутентификация
     * @throws IOException
     */
    void updateUserImage(MultipartFile image, Authentication authentication) throws IOException;
}