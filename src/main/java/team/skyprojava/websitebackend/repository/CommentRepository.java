package team.skyprojava.websitebackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.skyprojava.websitebackend.entity.Comment;

import java.util.List;
import java.util.Optional;


public interface CommentRepository extends JpaRepository<Comment, Integer> {

    List<Comment> findAllByAdsId(Integer adsId);

    Optional<Comment> findByIdAndAdsId(Integer id, Integer adsId);
}
