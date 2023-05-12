package team.skyprojava.websitebackend.entity;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;

/**
 * Класс изображение в объявлении
 */
@Entity
@Table(name = "ads_image")
@Data
public class AdsImage {
    @Id
    @Column(name = "ads_image_id")
    private String id;
    @Column(name = "image")
    @Lob
    @Type(type = "org.hibernate.type.BinaryType")
    private byte[] image;
}