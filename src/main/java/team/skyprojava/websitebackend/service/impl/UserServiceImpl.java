package team.skyprojava.websitebackend.service.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import team.skyprojava.websitebackend.dto.LoginReqDto;
import team.skyprojava.websitebackend.dto.NewPasswordDto;
import team.skyprojava.websitebackend.dto.UserDto;
import team.skyprojava.websitebackend.entity.User;
import team.skyprojava.websitebackend.exception.UserNotFoundException;
import team.skyprojava.websitebackend.mapper.UserMapper;
import team.skyprojava.websitebackend.repository.UserImageRepository;
import team.skyprojava.websitebackend.repository.UserRepository;
import team.skyprojava.websitebackend.security.UserDetailsServiceImpl;
import team.skyprojava.websitebackend.service.UserService;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final UserImageRepository userImageRepository;

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;
    private final UserDetailsServiceImpl userDetailsServiceImpl;



    @Override
    public UserDto getUserMe(Authentication authentication) {
        User user = getUserByEmail(authentication.getName());
        return userMapper.toDto(user);
    }

    @Override
    public UserDto updateUser(UserDto updatedUserDto, Authentication authentication) {
        logger.info("Was invoked method for update user");
        User user = getUserByEmail(authentication.getName());
        user.setFirstName(updatedUserDto.getFirstName());
        user.setLastName(updatedUserDto.getLastName());
        user.setPhone(updatedUserDto.getPhone());
        userRepository.save(user);
        return userMapper.toDto(user);
    }

    @Override
    public void newPassword(NewPasswordDto newPasswordDto, Authentication authentication) {
        logger.info("Was invoked method for create new password");
        User user = getUserByEmail(authentication.getName());
        if (passwordEncoder.matches(newPasswordDto.getCurrentPassword(), user.getPassword())) {
            user.setPassword(passwordEncoder.encode(newPasswordDto.getNewPassword()));
            userRepository.save(user);
            logger.info("Password updated");
            userDetailsServiceImpl.loadUserByUsername(user.getEmail());
        } else {
            logger.info("The current password is incorrect");
            throw new BadCredentialsException("The current password is incorrect!");
        }
    }

    @Override
    public void updateUserImage(MultipartFile image, Authentication authentication) {
        User user = getUserByEmail(authentication.getName());
        if (user.getUserImage() != null) {
            userImageRepository.delete(user.getUserImage());
        }
        //user.setUserImage();
        userRepository.save(user);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User is not found"));
    }

}
