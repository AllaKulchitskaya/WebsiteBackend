package team.skyprojava.websitebackend.security;

import lombok.NoArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import team.skyprojava.websitebackend.dto.Role;
import team.skyprojava.websitebackend.entity.Ads;
import team.skyprojava.websitebackend.entity.Comment;
import team.skyprojava.websitebackend.entity.User;

/**
 * Класс для работы доступа с админом или автором объявления
 */
@NoArgsConstructor
public class SecurityAccess {

    /**
     * Разрешение доступа для объявления
     *
     * @param ads
     * @param user
     * @return
     */
    public static boolean adsPermission(Ads ads, User user) {
        SecurityUser securityUser = new SecurityUser(user);

        if (!securityUser.getUsername().equals(ads.getAuthor().getEmail())
                || !securityUser.getAuthorities().contains(Role.ADMIN.name())) {
            throw new AccessDeniedException("Данное действие доступно только автору объявления и администратору");
        }
        return true;
    }

    /**
     * Разрешение доступа к комментарию
     *
     * @param comment
     * @param user
     * @return
     */
    public static boolean commentPermission(Comment comment, User user) {
        SecurityUser securityUser = new SecurityUser(user);

        if (!securityUser.getUsername().equals(comment.getAuthor().getEmail())
                || !securityUser.getAuthorities().contains(Role.ADMIN.name())) {
            throw new AccessDeniedException("Данное действие доступно только автору комментария и администратору");
        }
        return true;
    }
}
