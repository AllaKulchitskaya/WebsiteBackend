package team.skyprojava.websitebackend.dto;

import lombok.Data;

/**
 * Класс AdsDto для хранения объявлений
 */
@Data
public class AdsDto {
    private int author;
    private String image;
    private int pk;
    private int price;
    private String title;
}