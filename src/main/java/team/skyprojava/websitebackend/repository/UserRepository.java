package team.skyprojava.websitebackend.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import team.skyprojava.websitebackend.entity.User;

import java.util.Optional;

/**
 * Интерфейс для работы с пользователем (users/пользователь)
 *
 */

public interface UserRepository extends JpaRepository<User, Integer> {

    /**
     * Поиск почты для пользователя
     *
     * @param email
     * @return
     */
    Optional<User> findByEmail(String email);

    /**
     * Хранение существущих пользователей по почте???????????
     *
     * @param email
     * @return
     */
    boolean existsByEmail(String email);
}