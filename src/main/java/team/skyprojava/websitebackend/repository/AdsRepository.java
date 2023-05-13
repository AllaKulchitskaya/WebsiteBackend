package team.skyprojava.websitebackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team.skyprojava.websitebackend.entity.Ads;

import java.util.List;

/**
 * Репозиторий для объявлений
 */
@Repository
public interface AdsRepository extends JpaRepository<Ads, Integer> {

    /**
     * Поиск всех авторов по идентификатору
     *
     * @param id
     */
    List<Ads> findAllByAuthorId(Integer id);
}