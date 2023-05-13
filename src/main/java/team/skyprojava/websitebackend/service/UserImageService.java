package team.skyprojava.websitebackend.service;

import org.springframework.web.multipart.MultipartFile;
import team.skyprojava.websitebackend.entity.UserImage;

import java.io.IOException;

/**
 * Интерфейс сервиса для работы с аватаром пользователя
 */
public interface UserImageService {

    /**
     * Метод загрузки изображения пользователя
     *
     * @param image
     * @throws IOException
     */
    UserImage uploadImage(MultipartFile image) throws IOException;

    /**
     * Метод получения изображения по идентификатору пользователя
     *
     * @param userId
     * @return
     */
    UserImage getImageById(int userId);

    /**
     * Метод удаления изображения по идентификатору пользовотеля
     *
     * @param userId
     */
    void removeImage (int userId);
}