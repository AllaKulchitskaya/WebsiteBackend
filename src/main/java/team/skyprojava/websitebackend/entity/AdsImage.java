package team.skyprojava.websitebackend.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "ads_image")
@Data
public class AdsImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ads_image_id")
    private Integer id;
    @Column(name = "image")
    private String image;
}
