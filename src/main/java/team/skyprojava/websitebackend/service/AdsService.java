package team.skyprojava.websitebackend.service;

import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;
import team.skyprojava.websitebackend.dto.AdsDto;
import team.skyprojava.websitebackend.dto.CreateAdsDto;
import team.skyprojava.websitebackend.dto.FullAdsDto;
import team.skyprojava.websitebackend.dto.ResponseWrapperAdsDto;

import java.io.IOException;

/**
 * Интерфейс сервиса для работы с объявлениями
 */

public interface AdsService {


    ResponseWrapperAdsDto getAllAds();

    AdsDto createAds(CreateAdsDto createAdsDto, MultipartFile image, Authentication authentication) throws IOException;

    boolean removeAds(int id, Authentication authentication) throws IOException;

    AdsDto updateAds(int id, CreateAdsDto updateAdsDto, Authentication authentication);

    FullAdsDto getFullAdsDto(int id, Authentication authentication);

    ResponseWrapperAdsDto getAdsMe(Authentication authentication);
    byte[] updateAdsImage(int id, MultipartFile image);



}
