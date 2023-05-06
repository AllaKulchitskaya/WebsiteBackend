package team.skyprojava.websitebackend.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import team.skyprojava.websitebackend.entity.User;
import team.skyprojava.websitebackend.exception.UserNotFoundException;
import team.skyprojava.websitebackend.repository.UserRepository;


@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService, UserDetailsPasswordService {
    @Autowired
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User is not found"));
        return new SecurityUser(user);
    }

    @Override
    public UserDetails updatePassword(UserDetails userDetails, String newPassword) {
        User user = getUserByUsername(userDetails.getUsername());

        user.setPassword(newPassword);

        SecurityUser updatedUserDetails = new SecurityUser(userRepository.save(user));

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken
                        (updatedUserDetails, null, updatedUserDetails.getAuthorities()));

        return updatedUserDetails;
    }

    private User getUserByUsername(String username) {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new UserNotFoundException("User is not found"));
    }
}
