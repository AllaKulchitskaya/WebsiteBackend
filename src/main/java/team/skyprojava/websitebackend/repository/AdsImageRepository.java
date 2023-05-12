package team.skyprojava.websitebackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team.skyprojava.websitebackend.entity.AdsImage;

/**
 * Репозиторий для изображений в объявлениях
 */
@Repository
public interface AdsImageRepository extends JpaRepository<AdsImage, Integer> {
}