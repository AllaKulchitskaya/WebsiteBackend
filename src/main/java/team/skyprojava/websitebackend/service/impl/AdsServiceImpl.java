package team.skyprojava.websitebackend.service.impl;


import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.webjars.NotFoundException;
import team.skyprojava.websitebackend.dto.*;
import team.skyprojava.websitebackend.entity.Ads;
import team.skyprojava.websitebackend.entity.Comment;
import team.skyprojava.websitebackend.entity.User;
import team.skyprojava.websitebackend.exception.AdsNotFoundException;
import team.skyprojava.websitebackend.exception.UserNotFoundException;
import team.skyprojava.websitebackend.mapper.AdsMapper;
import team.skyprojava.websitebackend.repository.AdsRepository;
import team.skyprojava.websitebackend.repository.CommentRepository;
import team.skyprojava.websitebackend.repository.UserRepository;
import team.skyprojava.websitebackend.service.AdsImageService;
import team.skyprojava.websitebackend.service.AdsService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Реализация сервиса для работы с объявлениями
 * @see AdsService
 */
@RequiredArgsConstructor
@Service
public class AdsServiceImpl implements AdsService {

    private final Logger logger = LoggerFactory.getLogger(AdsServiceImpl.class);
    private final AdsRepository adsRepository;
    private final CommentRepository commentRepository;
    private final AdsMapper adsMapper;
    private final UserRepository userRepository;
    private final AdsImageService adsImageService;


    /**
     * Метод для получения всех объявлений
     */
    @Override
    public ResponseWrapperAdsDto getAllAds() {
        logger.info("Was invoked method for getting all ads");
        List<Ads> adsList = adsRepository.findAll();
        if (!adsList.isEmpty()) {
            List<AdsDto> adsDtoList = new ArrayList<>(adsList.size());
            for (Ads a : adsList) {
                adsDtoList.add(adsMapper.toAdsDto(a));
            }
            ResponseWrapperAdsDto result = new ResponseWrapperAdsDto();
            result.setCount(adsList.size());
            result.setResults(adsDtoList);
            logger.info("Ads have been received");
            return result;
        } else {
            ResponseWrapperAdsDto result = new ResponseWrapperAdsDto();
            result.setCount(0);
            result.setResults(Collections.emptyList());
            logger.info("Ads have been received");
            return result;
        }
    }

    /**
     * Метод для создания нового объявления
     *
     * @param createAdsDto
     * @param image
     * @param authentication
     * @throws IOException
     */
    @Override
    public AdsDto createAds(CreateAdsDto createAdsDto, MultipartFile image, Authentication authentication) throws IOException {
        logger.info("Was invoked method for creating an ad");
        User user = getUserByEmail(authentication.getName());
        Ads ads = adsMapper.toEntity(createAdsDto);
        ads.setAuthor(user);
        ads.setAdsImage(adsImageService.uploadImage(image));
        logger.info("Ad has been created");
        return adsMapper.toAdsDto(adsRepository.save(ads));
    }

    /**
     * Метод для удаления объявления по идентификатору
     *
     * @param id
     * @param authentication
     */
    @Override
    public boolean removeAds(int id, Authentication authentication) {
        logger.info("Was invoked method for deleting ad by id");

        User user = getUserByEmail(authentication.getName());
        Ads ads = getAdsById(id);

        if (!adsPermission(user, ads)) {
            logger.warn("No access");
            return false;
        }

        List<Integer> adsComments = commentRepository.findAll().stream()
                    .filter(adsComment -> adsComment.getAds().getId() == ads.getId())
                    .map(Comment::getId)
                    .collect(Collectors.toList());
        commentRepository.deleteAllById(adsComments);
        adsImageService.removeImage(id);
        adsRepository.delete(ads);
        logger.info("Ad has been deleted");
        return true;
    }

    /**
     * Метод для обновления объявления по идентификатору
     *
     * @param id
     * @param updateAdsDto
     * @param authentication
     */
    @Override
    public AdsDto updateAds(int id, CreateAdsDto updateAdsDto, Authentication authentication) {
        logger.info("Was invoked method for updating ad by id");

        User user = getUserByEmail(authentication.getName());
        Ads updatedAds = getAdsById(id);

        if (!adsPermission(user, updatedAds)) {
            logger.warn("No access");
            throw new AccessDeniedException("User is not allowed to update this ad");
        }

        updatedAds.setTitle(updateAdsDto.getTitle());
        updatedAds.setDescription(updateAdsDto.getDescription());
        updatedAds.setPrice(updateAdsDto.getPrice());
        adsRepository.save(updatedAds);
        logger.info("Ad has been updated");
        return adsMapper.toAdsDto(updatedAds);
    }

    /**
     * Метод для поиска объявлений по идентификатору
     *
     * @param id
     * @param authentication
     */
    @Override
    public FullAdsDto getFullAdsDto(int id, Authentication authentication) {
        logger.info("Was invoked method for getting full ad");

        Ads ads = getAdsById(id);
        logger.info("Ad has been received");
        return adsMapper.toDto(ads);
    }

    /**
     * Метод для просмотра всех моих объявлений
     *
     * @param authentication
     */
    @Override
    public ResponseWrapperAdsDto getAdsMe(Authentication authentication) {
        logger.info("Was invoked method for getting my ads");

        User user = getUserByEmail(authentication.getName());
        List<Ads> adsList = adsRepository.findAllByAuthorId(user.getId());
        if (!adsList.isEmpty()) {
            List<AdsDto> adsDtoList = new ArrayList<>(adsList.size());
            for (Ads a : adsList) {
                adsDtoList.add(adsMapper.toAdsDto(a));
            }
            ResponseWrapperAdsDto result = new ResponseWrapperAdsDto();
            result.setCount(adsList.size());
            result.setResults(adsDtoList);
            logger.info("Ads have been received");
            return result;
        } else {
            ResponseWrapperAdsDto result = new ResponseWrapperAdsDto();
            result.setCount(0);
            result.setResults(Collections.emptyList());
            logger.info("Ads have been received");
            return result;
        }
    }

    /**
     * Метод для обновления изображения в объявлении по идентификатору
     *
     * @param id
     * @param image
     * @param authentication
     * @throws IOException
     */
    @Override
    public byte[] updateAdsImage(int id, MultipartFile image, Authentication authentication) throws IOException {
        logger.info("Was invoked method for updating ad's image");
        if (image != null) {
            User user = getUserByEmail(authentication.getName());
            Ads ads = getAdsById(id);

            if (!adsPermission(user, ads)) {
                logger.warn("No access");
                throw new AccessDeniedException("User is not allowed to update this ad image");
            }

            adsImageService.removeImage(id);
            ads.setAdsImage(adsImageService.uploadImage(image));
            adsRepository.save(ads);
            logger.info("Ad image has been updated");
            return ads.getAdsImage().getImage();
        }
        throw new NotFoundException("The image hasn't been downloaded");
    }

    /**
     * Метод для получения пользователя по почтовому адресу (юзернейму)
     *
     * @param email
     */
    public User getUserByEmail(String email) {
        logger.info("Was invoked method for getting user by email");
        return userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User is not found"));
    }

    /**
     * Метод для получения объявления по идентификатору
     *
     * @param id
     */
    public Ads getAdsById(int id) {
        logger.info("Was invoked method for getting ads by id");
        return adsRepository.findById(id)
                .orElseThrow(() -> new AdsNotFoundException("Объявление с id " + id + " не найдено!"));
    }

    /**
     * Метод проверяет, доступно ли пользователю изменение и удаление объявлений
     *
     * @param user
     * @param ads
     */
    public boolean adsPermission(User user, Ads ads) {
        if (!ads.getAuthor().getEmail().equals(user.getEmail())
                && !user.getRole().name().equals("ADMIN")) {
            return false;
        }
        return true;
    }
}