package team.skyprojava.websitebackend.service.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.webjars.NotFoundException;
import team.skyprojava.websitebackend.entity.Ads;
import team.skyprojava.websitebackend.entity.AdsImage;
import team.skyprojava.websitebackend.exception.AdsNotFoundException;
import team.skyprojava.websitebackend.repository.AdsImageRepository;
import team.skyprojava.websitebackend.repository.AdsRepository;
import team.skyprojava.websitebackend.service.AdsImageService;

import java.io.IOException;
import java.util.UUID;

/**
 * Класс обрабатывает команды создания рекламы, позволяющие пользователям создавать, обновлять, получать и удалять рекламу.
 */
@Service
@RequiredArgsConstructor
public class AdsImageServiceImpl implements AdsImageService {

    private final Logger logger = LoggerFactory.getLogger(AdsImageServiceImpl.class);
    private final AdsImageRepository adsImageRepository;
    private final AdsRepository adsRepository;

    /**
     * Обновление изображения в объявлении
     *
     * @param image
     * @return
     */
    @Override
    public AdsImage uploadImage(MultipartFile image) {
        logger.info("Was invoked method for uploading image");
        AdsImage adsImage = new AdsImage();
        try {
            adsImage.setImage(image.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        adsImage.setId(UUID.randomUUID().toString());

        return adsImageRepository.save(adsImage);
    }

    /**
     * Получение изображения в объявлении по идентификатору
     *
     * @param adsId
     * @return
     */
    @Override
    public AdsImage getImageById(int adsId) {
        logger.info("Was invoked method for getting image by id");
        Ads ads = getAdsById(adsId);
        AdsImage adsImage = ads.getAdsImage();
        if (adsImage == null) {
            throw new NotFoundException("Image is not found");
        }
        return adsImage;
    }

    /**
     * Удаления изображения объявления по идентификатору
     *
     * @param adsId
     */
    @Override
    public void removeImage(int adsId) {
        logger.info("Was invoked method for removing image by ad id");
        Ads ads = getAdsById(adsId);
        AdsImage adsImage = ads.getAdsImage();
        if (adsImage == null) {
            throw new NotFoundException("Image is not found");
        }
        adsImageRepository.delete(adsImage);
    }

    /**
     * Получение объявления по идентификатору
     *
     * @param id
     * @return
     */
    public Ads getAdsById(int id) {
        logger.info("Was invoked method for getting ads by id");
        Ads ads = adsRepository.findById(id)
                .orElseThrow(() -> new AdsNotFoundException("Ads is not found"));
        return ads;
    }
}