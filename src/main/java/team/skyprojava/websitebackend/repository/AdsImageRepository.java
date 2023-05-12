package team.skyprojava.websitebackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.skyprojava.websitebackend.entity.AdsImage;

/**
 * Интерфейс для работы с изображениями в объявлениях
 */
public interface AdsImageRepository extends JpaRepository<AdsImage, Integer> {
}