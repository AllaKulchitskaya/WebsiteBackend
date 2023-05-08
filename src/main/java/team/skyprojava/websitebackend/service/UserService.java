package team.skyprojava.websitebackend.service;

import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;
import team.skyprojava.websitebackend.dto.NewPasswordDto;
import team.skyprojava.websitebackend.dto.UserDto;

import java.io.IOException;


public interface UserService {

    /**
     * Получение информации об авторизованном пользователе
     *
     * @return User данный пользователь
     */
    UserDto getUserMe(Authentication authentication);

    /**
     * Изменение пользователя
     *
     * @param userDto Объект пользователя с новыми данными
     * @return User Изменённый пользователь
     */
    UserDto updateUser(UserDto userDto, Authentication authentication);

    /**
     * Изменение пароля пользователя
     *
     * @param newPasswordDto  нынешний и новый пароль
     */
    void newPassword(NewPasswordDto newPasswordDto, Authentication authentication);

    /**
     * Обновление аватара пользователя
     *
     * @param image новый аватар
     */
    void updateUserImage(MultipartFile image, Authentication authentication) throws IOException;
}
