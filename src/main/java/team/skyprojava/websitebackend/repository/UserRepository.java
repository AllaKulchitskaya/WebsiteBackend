package team.skyprojava.websitebackend.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import team.skyprojava.websitebackend.entity.User;

import java.util.Optional;

/*
 * Repository UserRepository (users/пользователь). Везде на русском поэтому
 * Интерфейс для работы с пользователем
 */

public interface UserRepository extends JpaRepository<User, Integer> {

    /**
     * Поиск почты для пользователя
     *
     * @param email
     * @return
     */
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

}
