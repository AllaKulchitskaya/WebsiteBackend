package team.skyprojava.websitebackend.security;

import lombok.NoArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import team.skyprojava.websitebackend.dto.Role;
import team.skyprojava.websitebackend.entity.Ads;
import team.skyprojava.websitebackend.entity.Comment;
import team.skyprojava.websitebackend.entity.User;

@NoArgsConstructor
public class SecurityAccess {

    public static void adsPermission(Ads ads, User user) {
        SecurityUser securityUser = new SecurityUser(user);

        if (!securityUser.getAuthorities().contains(Role.ADMIN) &&
                securityUser.getUsername() != ads.getAuthor().getEmail()) {
            throw new AccessDeniedException("Данное действие доступно только автору объявления и администратору");
        }
    }

    public static void commentPermission(Comment comment, User user) {
        SecurityUser securityUser = new SecurityUser(user);

        if (!securityUser.getAuthorities().contains(Role.ADMIN) &&
                securityUser.getUsername() != comment.getAuthor().getEmail()) {
            throw new AccessDeniedException("Данное действие доступно только автору комментария и администратору");
        }
    }
}
