package team.skyprojava.websitebackend.service;

import org.springframework.web.multipart.MultipartFile;
import team.skyprojava.websitebackend.entity.AdsImage;

import java.io.IOException;

public interface AdsImageService {
    AdsImage uploadImage(MultipartFile image) throws IOException;
    AdsImage getImageById(int adsId);
    void removeImage (int adsId);

}
