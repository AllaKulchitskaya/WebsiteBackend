package team.skyprojava.websitebackend.dto;

import lombok.Data;

/**
 * Класс CreateAdsDto для хранения данных о созданных объявлениях
 */
@Data
public class CreateAdsDto {
    private String description;
    private int price;
    private String title;
}