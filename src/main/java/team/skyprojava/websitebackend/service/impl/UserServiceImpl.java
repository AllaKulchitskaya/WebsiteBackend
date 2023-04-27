package team.skyprojava.websitebackend.service.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import team.skyprojava.websitebackend.dto.UserDto;
import team.skyprojava.websitebackend.entity.User;
import team.skyprojava.websitebackend.exception.UserNotFoundException;
import team.skyprojava.websitebackend.mapper.UserMapper;
import team.skyprojava.websitebackend.repository.UserRepository;
import team.skyprojava.websitebackend.service.UserService;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDto getUserMe() {
        User user = userRepository.findByEmail("user@mail.ru")
                .orElseThrow(() -> new UserNotFoundException("User is not found"));
        return userMapper.toDto(user);
    }

    @Override
    public UserDto updateUser(UserDto updatedUserDto) {
        logger.info("Was invoked method for update user");
        User user = userRepository.findByEmail("user@mail.ru")
                .orElseThrow(() -> new UserNotFoundException("User is not found"));
        user.setFirstName(updatedUserDto.getFirstName());
        user.setLastName(updatedUserDto.getLastName());
        user.setPhone(updatedUserDto.getPhone());
        userRepository.save(user);
        return userMapper.toDto(user);
    }

    @Override
    public void newPassword(String newPassword, String currentPassword) {
        logger.info("Was invoked method for create new password");
        User user = userRepository.findByEmail("user@mail.ru")
                .orElseThrow(() -> new UserNotFoundException("User is not found"));
        if (passwordEncoder.matches(currentPassword, user.getPassword())) {
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
            logger.info("password updated");
        }
    }

    @Override
    public void updateUserImage(MultipartFile image) {
        //тело метода
    }

}
