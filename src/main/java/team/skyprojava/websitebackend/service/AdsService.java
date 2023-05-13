package team.skyprojava.websitebackend.service;

import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;
import team.skyprojava.websitebackend.dto.*;

import java.io.IOException;

/**
 * Интерфейс сервиса для работы с объявлениями
 */

public interface AdsService {

    /**
     * Метод получения всех объявлений
     *
     */
    ResponseWrapperAdsDto getAllAds();

    /**
     * Метод создания нового объявления
     * @param createAdsDto
     * @param image
     * @param authentication
     * @throws IOException
     */
    AdsDto createAds(CreateAdsDto createAdsDto, MultipartFile image, Authentication authentication) throws IOException;

    /**
     * Метод удаления объявления по идентификатору
     *
     * @param id
     * @param authentication
     * @throws IOException
     */
    boolean removeAds(int id, Authentication authentication) throws IOException;

    /**
     * Метод обновления объявления по идентификатору
     *
     * @param id
     * @param updateAdsDto
     * @param authentication
     */
    AdsDto updateAds(int id, CreateAdsDto updateAdsDto, Authentication authentication);

    /**
     * Метод получения всего объявления по идентификатору
     *
     * @param id
     * @param authentication
     */
    FullAdsDto getFullAdsDto(int id, Authentication authentication);

    /**
     * Метод получения всех моих объявлений
     *
     * @param authentication
     */
    ResponseWrapperAdsDto getAdsMe(Authentication authentication);

    /**
     * Метод обновления изображения объвления по идентификатору
     *
     * @param id
     * @param image
     * @param authentication
     * @throws IOException
     */
    byte[] updateAdsImage(int id, MultipartFile image, Authentication authentication) throws IOException;
}