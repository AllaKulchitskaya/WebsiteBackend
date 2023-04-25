package team.skyprojava.websitebackend.service;

import org.springframework.security.core.Authentication;
import team.skyprojava.websitebackend.dto.AdsDto;
import team.skyprojava.websitebackend.dto.CreateAdsDto;
import team.skyprojava.websitebackend.dto.FullAdsDto;
import team.skyprojava.websitebackend.dto.ResponseWrapperAdsDto;
import team.skyprojava.websitebackend.entity.Ads;

import java.io.IOException;
import java.util.List;

/**
 * Интерфейс сервиса для работы с объявлениями
 */

public interface AdsService {


    ResponseWrapperAdsDto getAllAds();

    AdsDto createAds(CreateAdsDto createAdsDto, Authentication authentication) throws IOException;

    void removeAds(int id) throws IOException;

    AdsDto updateAds(int id, CreateAdsDto updateAdsDto);

    FullAdsDto getFullAdsDto(int id);

    ResponseWrapperAdsDto getAdsMe(Authentication authentication);



}
