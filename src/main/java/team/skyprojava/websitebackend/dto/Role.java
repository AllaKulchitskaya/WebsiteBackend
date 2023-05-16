package team.skyprojava.websitebackend.dto;

import org.springframework.security.core.GrantedAuthority;

/**
 * Перечисление Role (тип пользователя)
 */
public enum Role implements GrantedAuthority {
    USER, ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}