package team.skyprojava.websitebackend.service;

import org.springframework.web.multipart.MultipartFile;
import team.skyprojava.websitebackend.entity.AdsImage;

import java.io.IOException;

/**
 * Интерфейс сервиса для работы с изображениями объявления
 */
public interface AdsImageService {

    /**
     * Метод загрузки изображения
     * @param image
     * @throws IOException
     */
    AdsImage uploadImage(MultipartFile image) throws IOException;

    /**
     * Метод получения изображения к объявлению по идентификатору объявления
     *
     * @param adsId
     */
    AdsImage getImageById(int adsId);

    /**
     * Метод удаления изображения по идентификатору объявления
     *
     * @param adsId
     */
    void removeImage (int adsId);
}