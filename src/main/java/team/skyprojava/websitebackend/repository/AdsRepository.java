package team.skyprojava.websitebackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.skyprojava.websitebackend.entity.Ads;

import java.util.List;

/**
 * Интерфейс для работы с объявлениями
 */
public interface AdsRepository extends JpaRepository<Ads, Integer> {

    /**
     * Поиск всех авторов по идентификатору
     *
     * @param id
     * @return
     */
    List<Ads> findAllByAuthorId(Integer id);
}
