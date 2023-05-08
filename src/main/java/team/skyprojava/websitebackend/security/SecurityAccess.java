package team.skyprojava.websitebackend.security;

import lombok.NoArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import team.skyprojava.websitebackend.dto.Role;
import team.skyprojava.websitebackend.entity.Ads;
import team.skyprojava.websitebackend.entity.Comment;
import team.skyprojava.websitebackend.entity.User;

@NoArgsConstructor
public class SecurityAccess {

    public static boolean adsPermission(Ads ads, User user) {
        SecurityUser securityUser = new SecurityUser(user);

        if (!securityUser.getAuthorities().contains(Role.ADMIN) &&
                securityUser.getUsername().equals(ads.getAuthor().getEmail())) {
            throw new AccessDeniedException("Данное действие доступно только автору объявления и администратору");
        }
        return true;
    }

    public static boolean commentPermission(Comment comment, User user) {
        SecurityUser securityUser = new SecurityUser(user);

        if (!securityUser.getAuthorities().contains(Role.ADMIN) &&
                securityUser.getUsername().equals(comment.getAuthor().getEmail())) {
            throw new AccessDeniedException("Данное действие доступно только автору комментария и администратору");
        }
        return true;
    }
}
