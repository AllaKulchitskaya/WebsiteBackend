package team.skyprojava.websitebackend.service.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import team.skyprojava.websitebackend.dto.NewPasswordDto;
import team.skyprojava.websitebackend.dto.UserDto;
import team.skyprojava.websitebackend.entity.User;
import team.skyprojava.websitebackend.exception.UserNotFoundException;
import team.skyprojava.websitebackend.mapper.UserMapper;
import team.skyprojava.websitebackend.repository.UserRepository;
import team.skyprojava.websitebackend.service.UserImageService;
import team.skyprojava.websitebackend.service.UserService;

import java.io.IOException;

/**
 * Реализация сервиса для работы с пользователем
 * @see UserService
 */
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;

    private final UserImageService userImageService;

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    private final UserDetailsService userDetailsService;

    /**
     * Получение информации об авторизованном пользователе
     *
     * @param authentication
     */
    @Override
    public UserDto getUserMe(Authentication authentication) {
        logger.info("Was invoked method for getting user");
        User user = getUserByEmail(authentication.getName());
        logger.info("User has been received");
        return userMapper.toDto(user);
    }

    /**
     * Изменение информации о пользователе
     *
     * @param updatedUserDto Объект пользователя с новыми данными
     * @param authentication аутентификация
     */
    @Override
    public UserDto updateUser(UserDto updatedUserDto, Authentication authentication) {
        logger.info("Was invoked method for updating user's profile ");
        User user = getUserByEmail(authentication.getName());
        user.setFirstName(updatedUserDto.getFirstName());
        user.setLastName(updatedUserDto.getLastName());
        user.setPhone(updatedUserDto.getPhone());
        userRepository.save(user);
        logger.info("User's profile has been updated");
        return userMapper.toDto(user);
    }

    /**
     * Изменение пароля
     *
     * @param newPasswordDto  нынешний и новый пароль
     * @param authentication аутентификация
     */
    @Override
    public void newPassword(NewPasswordDto newPasswordDto, Authentication authentication) {
        logger.info("Was invoked method for creating new password");
        User user = getUserByEmail(authentication.getName());
        if (passwordEncoder.matches(newPasswordDto.getCurrentPassword(), user.getPassword())) {
            user.setPassword(passwordEncoder.encode(newPasswordDto.getNewPassword()));
            userRepository.save(user);
            logger.info("Password has been updated");
            userDetailsService.loadUserByUsername(user.getEmail());
        } else {
            logger.warn("The current password is incorrect");
            throw new BadCredentialsException("The current password is incorrect");
        }
    }

    /**
     * Обновление аватара пользователя
     *
     * @param image новый аватар
     * @param authentication аутентификация
     * @throws IOException
     */
    @Override
    public void updateUserImage(MultipartFile image, Authentication authentication) throws IOException {
        logger.info("Was invoked method for updating user image");
        User user = getUserByEmail(authentication.getName());
        if (user.getUserImage() != null) {
            userImageService.removeImage(user.getId());
        }
        user.setUserImage(userImageService.uploadImage(image));
        logger.info("User's image has been updated");
        userRepository.save(user);
    }

    /**
     * Получение пользователя по емайлу (юзернейму)
     *
     * @param email
     */
    public User getUserByEmail(String email) {
        logger.info("Was invoked method for getting user by email");
        return userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User is not found"));
    }
}