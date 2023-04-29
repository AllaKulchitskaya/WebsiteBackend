package team.skyprojava.websitebackend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;
import team.skyprojava.websitebackend.dto.AdsDto;
import team.skyprojava.websitebackend.dto.CreateAdsDto;
import team.skyprojava.websitebackend.dto.FullAdsDto;
import team.skyprojava.websitebackend.entity.Ads;

@Component
@Mapper
public interface AdsMapper{
    AdsMapper INSTANCE = Mappers.getMapper(AdsMapper.class);

    @Mapping(target = "id", source = "pk")
    @Mapping(target = "author.id", source = "author")
    @Mapping(target = "adsImage", ignore = true)
    @Mapping(target = "description", ignore = true)
    Ads toEntity(AdsDto adsDto);

    @Mapping(target = "author", ignore = true)
    @Mapping(target = "adsImage", ignore = true)
    @Mapping(target = "id", ignore = true)
    Ads toEntity(CreateAdsDto createAdsDto);

    @Mapping(target = "author", source = "author.id")
    @Mapping(target = "pk", source = "id")
    @Mapping(target = "image", expression = "java(\"/ads/\" + ads.getId() + \"/image/\")")
    AdsDto toAdsDto(Ads ads);

    @Mapping(target = "pk", source = "id")
    @Mapping(target = "authorFirstName", source = "author.firstName")
    @Mapping(target = "authorLastName", source = "author.lastName")
    @Mapping(target = "email", source = "author.email")
    @Mapping(target = "phone", source = "author.phone")
    @Mapping(target = "image", expression = "java(\"/ads/\" + ads.getId() + \"/image/\")")
    FullAdsDto toDto(Ads ads);
}