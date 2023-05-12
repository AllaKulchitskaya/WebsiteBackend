package team.skyprojava.websitebackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team.skyprojava.websitebackend.entity.Comment;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторий для комментариев
 */
@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

    /**
     * Поиск всех объявлений по идентификатору объявления
     *
     * @param adsId
     */
    List<Comment> findAllByAdsId(Integer adsId);
}