package team.skyprojava.websitebackend.service;

import org.springframework.web.multipart.MultipartFile;
import team.skyprojava.websitebackend.entity.UserImage;

import java.io.IOException;

/**
 * Предоставляет методы обработки изображений у пользователя
 */
public interface UserImageService {

    /**
     * Запрос на обновление аватара пользователя
     *
     * @param image
     * @return
     * @throws IOException
     */
    UserImage uploadImage(MultipartFile image) throws IOException;

    /**
     * Запрос на получение изображения по идентификатору пользователя
     *
     * @param userId
     * @return
     */
    UserImage getImageById(int userId);

    /**
     * Удаление изображение по идентификатору пользовотеля
     *
     * @param userId
     */
    void removeImage (int userId);
}