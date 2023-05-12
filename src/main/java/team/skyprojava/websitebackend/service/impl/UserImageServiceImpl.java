package team.skyprojava.websitebackend.service.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.webjars.NotFoundException;
import team.skyprojava.websitebackend.entity.User;
import team.skyprojava.websitebackend.entity.UserImage;
import team.skyprojava.websitebackend.exception.UserNotFoundException;
import team.skyprojava.websitebackend.repository.UserImageRepository;
import team.skyprojava.websitebackend.repository.UserRepository;
import team.skyprojava.websitebackend.service.UserImageService;

import java.io.IOException;
import java.util.UUID;

/**
 * Реализация сервиса для работы с аватаром пользователя
 * @see UserImageService
 */
@Service
@RequiredArgsConstructor
public class UserImageServiceImpl implements UserImageService {

    private final Logger logger = LoggerFactory.getLogger(UserImageServiceImpl.class);
    private final UserImageRepository userImageRepository;
    private final UserRepository userRepository;

    /**
     * Загрузка аватара пользователя
     *
     * @param image
     */
    @Override
    public UserImage uploadImage(MultipartFile image) {
        logger.info("Was invoked method for uploading image");
        UserImage userImage = new UserImage();
        try {
            userImage.setImage(image.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        userImage.setId(UUID.randomUUID().toString());

        logger.info("Image has been successfully uploaded");
        return userImageRepository.save(userImage);
    }

    /**
     * Получение аватара пользователя по идентификатору пользователя
     *
     * @param userId
     */
    @Override
    public UserImage getImageById(int userId) {
        logger.info("Was invoked method for getting image by user id");
        User user = getUserById(userId);
        UserImage userImage = user.getUserImage();
        if (userImage == null) {
            logger.warn("Image is not found");
            throw new NotFoundException("Image is not found");
        }
        logger.info("Image has been received");
        return userImage;
    }

    /**
     * Удаление аватара пользователя по идентификатору пользователя
     *
     * @param userId
     */
    @Override
    public void removeImage(int userId) {
        logger.info("Was invoked method for removing image by user id");
        User user = getUserById(userId);
        UserImage userImage = user.getUserImage();
        if (userImage == null) {
            logger.warn("Image is not found");
            throw new NotFoundException("Image is not found");
        }
        logger.info("Image has been deleted");
        userImageRepository.delete(userImage);
    }

    /**
     * Получение пользователя по идентификатору
     *
     * @param id
     */
    public User getUserById(int id) {
        logger.info("Was invoked method for getting user by id");
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User is not found"));
        return user;
    }
}