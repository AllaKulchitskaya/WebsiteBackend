package team.skyprojava.websitebackend.service;

import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;
import team.skyprojava.websitebackend.dto.UserDto;


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
     * @param newPassword     новый пароль
     * @param currentPassword старый пароль
     */
    void newPassword(String newPassword, String currentPassword, Authentication authentication);

    /**
     * Обновление аватара пользователя
     *
     * @param image новый аватар
     */
    void updateUserImage(MultipartFile image, Authentication authentication);
}
