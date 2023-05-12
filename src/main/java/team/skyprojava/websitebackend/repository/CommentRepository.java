package team.skyprojava.websitebackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.skyprojava.websitebackend.entity.Comment;

import java.util.List;
import java.util.Optional;

/**
 * Интерфейс для работы с комментариями
 */
public interface CommentRepository extends JpaRepository<Comment, Integer> {

    /**
     * Поиск всех объявлений по идентификатору
     *
     * @param adsId
     * @return
     */
    List<Comment> findAllByAdsId(Integer adsId);

    /*
     * ???????????????????????????? не похоже,что бы этот метод использовался
     * @param id
     * @param adsId
     * @return
     */
    Optional<Comment> findByIdAndAdsId(Integer id, Integer adsId);
}