package team.skyprojava.websitebackend.service;

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

    AdsDto createAds(CreateAdsDto createAdsDto, MultipartFile image) throws IOException;

    void removeAds(int id) throws IOException;

    AdsDto updateAds(int id, CreateAdsDto updateAdsDto);

    FullAdsDto getFullAdsDto(int id);

    ResponseWrapperAdsDto getAdsMe();
    byte[] updateAdsImage(int id, MultipartFile image);



}
