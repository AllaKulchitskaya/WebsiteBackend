package team.skyprojava.websitebackend.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team.skyprojava.websitebackend.entity.User;

import java.util.Optional;

/**
 * Репозиторий для пользователей
 *
 */

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    /**
     * Поиск пользователя по почтовому адресу (юзернейму)
     *
     * @param email
     */
    Optional<User> findByEmail(String email);

    /**
     * Проверка наличия пользователя по искомому почтовому адресу (юзернейму) в базе данных
     *
     * @param email
     */
    boolean existsByEmail(String email);
}