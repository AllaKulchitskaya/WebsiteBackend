package team.skyprojava.websitebackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.skyprojava.websitebackend.entity.UserImage;

import java.util.Optional;


public interface UserImageRepository extends JpaRepository<UserImage, Integer> {
}
