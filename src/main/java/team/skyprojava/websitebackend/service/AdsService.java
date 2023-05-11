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
     * Получить все объявления
     *
     * @return
     */
    ResponseWrapperAdsDto getAllAds();

    /**
     *
     *
     * @param createAdsDto
     * @param image
     * @param authentication
     * @return
     * @throws IOException
     */
    AdsDto createAds(CreateAdsDto createAdsDto, MultipartFile image, Authentication authentication) throws IOException;

    /**
     * Запрос на удаление объявления по идентификатору
     *
     * @param id
     * @param authentication
     * @return
     * @throws IOException
     */
    boolean removeAds(int id, Authentication authentication) throws IOException;

    /**
     * Запрос на обновление объявления по идентификатору
     *
     * @param id
     * @param updateAdsDto
     * @param authentication
     * @return
     */
    AdsDto updateAds(int id, CreateAdsDto updateAdsDto, Authentication authentication);

    /**
     * Запрос на поиск объявления по идентификатору
     *
     * @param id
     * @param authentication
     * @return
     */
    FullAdsDto getFullAdsDto(int id, Authentication authentication);

    /**
     * Запрос на получение всех моих объявлений
     *
     * @param authentication
     * @return
     */
    ResponseWrapperAdsDto getAdsMe(Authentication authentication);

    /**
     * Запрос на обновление изображения объвления по идентификатору
     *
     * @param id
     * @param image
     * @param authentication
     * @return
     * @throws IOException
     */
    byte[] updateAdsImage(int id, MultipartFile image, Authentication authentication) throws IOException;
}





