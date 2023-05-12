package team.skyprojava.websitebackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team.skyprojava.websitebackend.entity.UserImage;

import java.util.Optional;

/**
 * Репозиторий для аватара пользователя
 */
@Repository
public interface UserImageRepository extends JpaRepository<UserImage, Integer> {
}