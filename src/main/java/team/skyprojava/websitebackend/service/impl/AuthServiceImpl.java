package team.skyprojava.websitebackend.service.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.skyprojava.websitebackend.dto.RegisterReqDto;
import team.skyprojava.websitebackend.mapper.UserMapper;
import team.skyprojava.websitebackend.repository.UserRepository;
import team.skyprojava.websitebackend.service.AuthService;

/**
 * Предоставляет реализации методов AuthService
 */
@Transactional
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);
    private final UserDetailsService userDetailsService;
    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    /**
     * Логин для авторизации пользователя
     *
     * @param username
     * @param password
     * @return
     */
    @Override
    public boolean login(String username, String password) {
        logger.info("Was invoked method for user authorization");

            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            if (!passwordEncoder.matches(password, userDetails.getPassword())) {
                logger.warn("The password is incorrect");
                return false;
            }
            logger.info("User has been successfully logged in");
            return true;
    }

    /**
     * Регистрация пользователя
     *
     * @param registerReqDto
     * @return
     */
    @Override
    public boolean register(RegisterReqDto registerReqDto) {
        logger.info("Was invoked method for user registration");
        team.skyprojava.websitebackend.entity.User user = userMapper.toEntity(registerReqDto);
        if (userRepository.existsByEmail(user.getEmail())) {
            logger.warn("User already exists");
            return false;
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        logger.info("User registered");
        userRepository.save(user);
        return true;
    }
}