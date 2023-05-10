package team.skyprojava.websitebackend.service.impl;


import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.webjars.NotFoundException;
import team.skyprojava.websitebackend.dto.*;
import team.skyprojava.websitebackend.entity.Ads;
import team.skyprojava.websitebackend.entity.AdsImage;
import team.skyprojava.websitebackend.entity.Comment;
import team.skyprojava.websitebackend.entity.User;
import team.skyprojava.websitebackend.exception.AdsNotFoundException;
import team.skyprojava.websitebackend.exception.UserNotFoundException;
import team.skyprojava.websitebackend.mapper.AdsMapper;
import team.skyprojava.websitebackend.repository.AdsRepository;
import team.skyprojava.websitebackend.repository.CommentRepository;
import team.skyprojava.websitebackend.repository.UserRepository;
import team.skyprojava.websitebackend.security.SecurityAccess;
import team.skyprojava.websitebackend.service.AdsImageService;
import team.skyprojava.websitebackend.service.AdsService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Реализация сервиса для работы с объявлениями
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
        } else {
            ResponseWrapperAdsDto result = new ResponseWrapperAdsDto();
            result.setCount(0);
            result.setResults(Collections.emptyList());
            return result;
        }
    }

    @Override
    public AdsDto createAds(CreateAdsDto createAdsDto, MultipartFile image, Authentication authentication) throws IOException {
        logger.info("Was invoked method for create ad");
        User user = getUserByEmail(authentication.getName());
        Ads ads = adsMapper.toEntity(createAdsDto);
        ads.setAuthor(user);
        ads.setAdsImage(adsImageService.uploadImage(image));
        logger.info("ad created");
        return adsMapper.toAdsDto(adsRepository.save(ads));
    }

    @Override
    public boolean removeAds(int id, Authentication authentication) {
        logger.info("Was invoked method for delete ad by id");

        User user = getUserByEmail(authentication.getName());
        Ads ads = getAdsById(id);
        if (!ads.getAuthor().getEmail().equals(user.getEmail())) {
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
        logger.info("ad deleted");
        return true;
    }

    @Override
    public AdsDto updateAds(int id, CreateAdsDto updateAdsDto, Authentication authentication) {
        logger.info("Was invoked method for update ad by id");

        User user = getUserByEmail(authentication.getName());
        Ads updatedAds = getAdsById(id);
        if (!updatedAds.getAuthor().getEmail().equals(user.getEmail())) {
            logger.warn("No access");
            throw new AccessDeniedException("User is not allowed to delete this comment");
        }

        updatedAds.setTitle(updateAdsDto.getTitle());
        updatedAds.setDescription(updateAdsDto.getDescription());
        updatedAds.setPrice(updateAdsDto.getPrice());
        adsRepository.save(updatedAds);
        logger.info("ad updated");
        return adsMapper.toAdsDto(updatedAds);
    }

    @Override
    public FullAdsDto getFullAdsDto(int id, Authentication authentication) {
        logger.info("Was invoked method for get full ad dto");

        Ads ads = getAdsById(id);
        return adsMapper.toDto(ads);
    }

    @Override
    public ResponseWrapperAdsDto getAdsMe(Authentication authentication) {
        logger.info("Service for get ads me");

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
            return result;
        } else {
            ResponseWrapperAdsDto result = new ResponseWrapperAdsDto();
            result.setCount(0);
            result.setResults(Collections.emptyList());
            return result;
        }
    }

    @Override
    public byte[] updateAdsImage(int id, MultipartFile image, Authentication authentication) throws IOException {
        if (image != null) {
            User user = getUserByEmail(authentication.getName());
            Ads ads = getAdsById(id);
            if (!ads.getAuthor().getEmail().equals(user.getEmail())
                    || !user.getRole().getAuthority().equals("ADMIN")) {
                logger.warn("No access");
                throw new AccessDeniedException("User is not allowed to delete this comment");
            }
            adsImageService.removeImage(id);
            ads.setAdsImage(adsImageService.uploadImage(image));
            adsRepository.save(ads);
            return ads.getAdsImage().getImage();
        }
        throw new NotFoundException("The image hasn't been downloaded");
    }


    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User is not found"));
    }

    public Ads getAdsById(int id) {
        return adsRepository.findById(id)
                .orElseThrow(() -> new AdsNotFoundException("Объявление с id " + id + " не найдено!"));
    }


}
