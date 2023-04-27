package team.skyprojava.websitebackend.service;

import org.springframework.web.multipart.MultipartFile;
import team.skyprojava.websitebackend.dto.UserDto;


public interface UserService {

    /**
     * Получение информации об авторизованном пользователе
     *
     * @return User данный пользователь
     */
    UserDto getUserMe();

    /**
     * Изменение пользователя
     *
     * @param userDto Объект пользователя с новыми данными
     * @return User Изменённый пользователь
     */
    UserDto updateUser(UserDto userDto);

    /**
     * Изменение пароля пользователя
     *
     * @param newPassword     новый пароль
     * @param currentPassword старый пароль
     */
    void newPassword(String newPassword, String currentPassword);

    /**
     * Обновление аватара пользователя
     *
     * @param image новый аватар
     */
    void updateUserImage(MultipartFile image);
}
