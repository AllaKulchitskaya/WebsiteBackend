package team.skyprojava.websitebackend.service.impl;

import lombok.RequiredArgsConstructor;
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

@Service
@RequiredArgsConstructor
public class UserImageServiceImpl implements UserImageService {

    private final UserImageRepository userImageRepository;
    private final UserRepository userRepository;

    @Override
    public UserImage uploadImage(MultipartFile image) {
        UserImage userImage = new UserImage();
        try {
            userImage.setImage(image.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        userImage.setId(UUID.randomUUID().toString());

        return userImageRepository.save(userImage);
    }

    @Override
    public UserImage getImageById(int userId) {
        User user = getUserById(userId);
        UserImage userImage = user.getUserImage();
        if (userImage == null) {
            throw new NotFoundException("Image is not found");
        }
        return userImage;
    }

    @Override
    public void removeImage(int userId) {
        User user = getUserById(userId);
        UserImage userImage = user.getUserImage();
        if (userImage == null) {
            throw new NotFoundException("Image is not found");
        }
        userImageRepository.delete(userImage);
    }

    public User getUserById(int id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User is not found"));
        return user;
    }
}
