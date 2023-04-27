package team.skyprojava.websitebackend.service;

import org.springframework.security.core.Authentication;
import team.skyprojava.websitebackend.dto.Role;
import team.skyprojava.websitebackend.dto.UserDto;

import java.util.List;

public interface UserService {

    /**
     * Создание пользователя
     *
     * @param userDto Объект пользователя для передачи данных
     * @return User Созданный пользователь
     */
    UserDto User(UserDto userDto);

    /**
     * Получение всех существующих пользователей
     *
     * @return Collection<User>
     */

    List<UserDto> getUsers();

    UserDto getUserMe(Authentication authentication);

    /**
     * Получение пользователя по ID
     *
     * @param id ID пользователя
     * @return User с данным ID
     */

    UserDto getUserById(int id);

    /**
     * Изменение пользователя
     *
     * @param user Объект пользователя с новыми данными
     * @return User Изменённый пользователь
     */
    UserDto updateUser(UserDto user);

    /**
     * Изменение пароля пользователя
     *
     * @param newPassword     новый пароль
     * @param currentPassword старый пароль
     * @return Возвращает true если пароль успешно изменен, иначе false
     */
    void newPassword(String newPassword, String currentPassword);


    UserDto updateRole(int id, Role role);
}
