package team.skyprojava.websitebackend.service.impl;


import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.webjars.NotFoundException;
import team.skyprojava.websitebackend.controller.AdsController;
import team.skyprojava.websitebackend.dto.AdsDto;
import team.skyprojava.websitebackend.dto.CreateAdsDto;
import team.skyprojava.websitebackend.dto.FullAdsDto;
import team.skyprojava.websitebackend.dto.ResponseWrapperAdsDto;
import team.skyprojava.websitebackend.entity.Ads;
import team.skyprojava.websitebackend.entity.User;
import team.skyprojava.websitebackend.exception.AdsNotFoundException;
import team.skyprojava.websitebackend.exception.UserNotFoundException;
import team.skyprojava.websitebackend.mapper.AdsMapper;
import team.skyprojava.websitebackend.repository.AdsRepository;
import team.skyprojava.websitebackend.repository.UserRepository;
import team.skyprojava.websitebackend.service.AdsService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Реализация сервиса для работы с объявлениями
 */


@RequiredArgsConstructor
@Service
public class AdsServiceImpl implements AdsService {

    Logger logger = LoggerFactory.getLogger(AdsController.class);
    private final AdsRepository adsRepository;
    private final AdsMapper adsMapper;
    private final UserRepository userRepository;


    @Override
    public ResponseWrapperAdsDto getAllAds() {
        logger.info("Service for get all ads");
        List<Ads> adsList = adsRepository.findAll();
        if (!adsList.isEmpty()) {
            List<AdsDto> adsDtoList = new ArrayList<>(adsList.size());
            for (Ads a : adsList) {
                adsDtoList.add(adsMapper.toAdsDto(a));
            }
            ResponseWrapperAdsDto result = new ResponseWrapperAdsDto();
            result.setCount(adsList.size());
            result.setResults(adsDtoList);
            return result;
        }
        throw new AdsNotFoundException("Ads are not found");
    }

    @Override
    public AdsDto createAds(CreateAdsDto createAdsDto, MultipartFile image) throws IOException {
        logger.info("Was invoked method for create ad");
        User user = userRepository.findByEmail("user@mail.ru").
                orElseThrow(() -> new UserNotFoundException("User is not found"));
        Ads ads = adsMapper.toEntity(createAdsDto);
        ads.setAuthor(user);
        //ads.setAdsImage(image);
        logger.info("ad created");
        return adsMapper.toAdsDto(adsRepository.save(ads));
    }

    @Override
    public void removeAds(int id) {
        logger.info("Was invoked method for delete ad by id");
        Ads ads = adsRepository.findById(id)
                .orElseThrow(() -> new AdsNotFoundException("Объявление с id " + id + " не найдено!"));
        logger.warn("Ad by id {} not found", id);
        adsRepository.delete(ads);
    }

    @Override
    public AdsDto updateAds(int id, CreateAdsDto updateAdsDto) {
        logger.info("Was invoked method for update ad by id");
        Ads updatedAds = adsRepository.findById(id)
                .orElseThrow(() -> new AdsNotFoundException("Объявление с id " + id + " не найдено!"));
        logger.warn("Ad by id {} not found", id);
        updatedAds.setTitle(updateAdsDto.getTitle());
        updatedAds.setDescription(updateAdsDto.getDescription());
        updatedAds.setPrice(updateAdsDto.getPrice());
        adsRepository.save(updatedAds);
        logger.info("ad updated");
        return adsMapper.toAdsDto(updatedAds);
    }

    @Override
    public FullAdsDto getFullAdsDto(int id) {
        logger.info("Was invoked method for get full ad dto");
        Ads ads = adsRepository.findById(id)
                .orElseThrow(() -> new AdsNotFoundException("Объявление с id " + id + " не найдено!"));
        return adsMapper.toDto(ads);
    }

    @Override
    public ResponseWrapperAdsDto getAdsMe() {
        logger.info("Service for get ads me");
        User user = userRepository. findByEmail("user@mail.ru").
                orElseThrow(() -> new UserNotFoundException("User is not found"));
        List<Ads> adsList = adsRepository.findAllByAuthorId(user.getId());
        if (!adsList.isEmpty()) {
            List<AdsDto> adsDtoList = new ArrayList<>(adsList.size());
            for (Ads a : adsList) {
                adsDtoList.add(adsMapper.toAdsDto(a));
            }
            ResponseWrapperAdsDto result = new ResponseWrapperAdsDto();
            result.setCount(adsList.size());
            result.setResults(adsDtoList);
            return result;
        }
        throw new AdsNotFoundException("Ads are not found");
    }

    @Override
    public byte[] updateAdsImage(int id, MultipartFile image) {
        //тело метода
        return null;
    }


}
