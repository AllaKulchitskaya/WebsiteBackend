package team.skyprojava.websitebackend.service.impl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
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
import java.util.stream.Collectors;

/**
 * Реализация сервиса для работы с объявлениями
 */


@RequiredArgsConstructor
@Service
public class AdsServiceImpl implements AdsService {

    Logger logger = LoggerFactory.getLogger(AdsController.class);
    private final AdsService adsService;
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
    public AdsDto createAds(CreateAdsDto createAdsDto, Authentication authentication) throws IOException {
        logger.info("Was invoked method for create ad");
        User user = userRepository.findByEmail(authentication.getName()).
                orElseThrow(() -> new UserNotFoundException("User is not found"));
        Ads ads = adsMapper.toEntity(createAdsDto);
        ads.setAuthor(user);
        logger.info("ad created");
        return adsMapper.toAdsDto(adsRepository.save(ads));
    }

    @Override
    public void removeAds(int id) throws IOException {
        logger.info("Was invoked method for delete ad by id");
        Ads ads = adsRepository.findById(id)
                .orElseThrow(() -> new AdsNotFoundException("Объявление с id " + id + " не найдено!"));
        logger.warn("Ad by id {} not found", id);
        adsRepository.delete(ads);
    }

    @Override
    public AdsDto updateAds(int id, CreateAdsDto updateAdsDto) {
        logger.info("Was invoked method for update ad by id");
        Ads updatedAds = adsRepository.findById(id).orElseThrow(() -> new AdsNotFoundException("Объявление с id " + id + " не найдено!"));
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
        return adsMapper.toDto(adsRepository.findById(id).orElseThrow(() -> new NotFoundException("Объявление с id " + id + " не найдено!")));
    }

    @Override
    public ResponseWrapperAdsDto getAdsMe(Authentication authentication) {
        logger.info("Service for get ads me");
        User user = userRepository. findByEmail(authentication.getName()).
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


}
