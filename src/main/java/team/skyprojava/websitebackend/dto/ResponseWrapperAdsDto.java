package team.skyprojava.websitebackend.dto;

import lombok.Data;

import java.util.List;

/**
 * Класс ResponseWrapperAdsDto для хранения списка объявлений
 */
@Data
public class ResponseWrapperAdsDto {
    private int count;
    private List<AdsDto> results;
}