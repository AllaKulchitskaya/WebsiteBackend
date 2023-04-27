package team.skyprojava.websitebackend.service.impl;

import io.swagger.v3.oas.models.info.Contact;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;
import team.skyprojava.websitebackend.dto.Role;
import team.skyprojava.websitebackend.dto.UserDto;
import team.skyprojava.websitebackend.entity.User;
import team.skyprojava.websitebackend.exception.UserNotFoundException;
import team.skyprojava.websitebackend.mapper.UserMapper;
import team.skyprojava.websitebackend.repository.UserRepository;
import team.skyprojava.websitebackend.service.UserService;

import javax.validation.ValidationException;
import java.util.List;

import static team.skyprojava.websitebackend.dto.Role.USER;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;


    @Override
    public UserDto User(UserDto userDto) {
        logger.info("Was invoked method for create user");
        Contact user = null;
        if (userRepository.existsByEmail(user.getEmail())) {
            logger.warn("user already exists");
            throw new ValidationException(String.format("Пользователь \"%s\" уже существует!", user.getEmail()));
        }
        User createdUser = userMapper.userDtoToEntity(user);
        if (createdUser.getRole() == null) {
            createdUser.setRole(USER);
        }
        logger.info("user created");
        return userMapper.toDto(userRepository.save(createdUser));
    }

    @Override
    public List<UserDto> getUsers() {
        logger.info("Получение всех пользователей");
        return (List<UserDto>) userMapper.toDto((User) userRepository.findAll());
    }

    @Override
    public UserDto getUserMe(Authentication authentication) {
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow();
        return userMapper.toDto(user);
    }

    @Override
    public UserDto updateUser(UserDto updatedUserDto) {
        logger.info("Was invoked method for update user");
        User user = userRepository.findByEmail("user@mail.ru").orElseThrow();
        user.setFirstName(updatedUserDto.getFirstName());
        user.setLastName(updatedUserDto.getLastName());
        user.setPhone(updatedUserDto.getPhone());
        userRepository.save(user);
        return userMapper.toDto(user);
    }

    @Override
    public UserDto getUserById(int id) {
        logger.info("Получение пользователей по id");
        return userMapper.toDto(userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Пользователь с id " + id + " не найден!")));
    }

    @Override
    public void newPassword(String newPassword, String currentPassword) {
        logger.info("Was invoked method for create new password");
        User user = userRepository.findByEmail("user@mail.ru").orElseThrow();
        if (passwordEncoder.matches(currentPassword, user.getPassword())) {
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
            logger.info("password updated");
        }
    }

    @Override
    public UserDto updateRole(int id, Role role) {
        logger.info("Was invoked method for update user role");
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("Пользователь с id " + id + " не найден!"));
        user.setRole(role);
        userRepository.save(user);
        return userMapper.toDto(user);
    }

}
