package team.skyprojava.websitebackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.skyprojava.websitebackend.entity.UserImage;


public interface UserImageRepository extends JpaRepository<UserImage, Integer> {
}
