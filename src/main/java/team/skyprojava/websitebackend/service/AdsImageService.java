package team.skyprojava.websitebackend.service;

import org.springframework.web.multipart.MultipartFile;
import team.skyprojava.websitebackend.entity.AdsImage;

import java.io.IOException;

/**
 * Предоставляет методы обработки изображений у объявления
 */
public interface AdsImageService {

    /**
     *
     * @param image
     * @return
     * @throws IOException
     */
    AdsImage uploadImage(MultipartFile image) throws IOException;

    /**
     * Запрос на изображение к объявлению по идентификатору
     *
     * @param adsId
     * @return
     */
    AdsImage getImageById(int adsId);

    /**
     * Запрос на удаление объявления по идентификатору
     *
     * @param adsId
     */
    void removeImage (int adsId);

}
