package team.skyprojava.websitebackend.service;

import org.springframework.web.multipart.MultipartFile;
import team.skyprojava.websitebackend.entity.UserImage;

import java.io.IOException;

public interface UserImageService {
    UserImage uploadImage(MultipartFile image) throws IOException;
    UserImage getImageById(int userId);
    void removeImage (int userId);
}
