package team.skyprojava.websitebackend.service.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import team.skyprojava.websitebackend.dto.RegisterReqDto;
import team.skyprojava.websitebackend.dto.Role;
import team.skyprojava.websitebackend.mapper.UserMapper;
import team.skyprojava.websitebackend.repository.UserRepository;
import team.skyprojava.websitebackend.service.AuthService;

import javax.validation.ValidationException;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);
    private final UserDetailsService userDetailsService;
    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;


    @Override
    public void login(String username, String password) {
        logger.info("Was invoked method for user authorization");

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            logger.warn("the password is incorrect");
            throw new BadCredentialsException("The password is incorrect");
        }
    }

    @Override
    public void register(RegisterReqDto registerReqDto) {
        logger.info("Was invoked method for user registration");
        team.skyprojava.websitebackend.entity.User user = userMapper.toEntity(registerReqDto);
        if (userRepository.existsByEmail(user.getEmail())) {
            logger.warn("User already exists");
            throw new ValidationException(String.format("User \"%s\" has already been registered!", user.getEmail()));
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        logger.info("User registered");
        userRepository.save(user);
    }
}
