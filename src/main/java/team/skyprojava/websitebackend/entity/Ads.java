package team.skyprojava.websitebackend.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "ads")
@Data
public class Ads {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ads_id")
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User author;
    @Column(name = "price")
    private int price;
    @Column(name = "title")
    private String title;
    @Column(name = "description")
    private String description;
    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "ads_image_id")
    private AdsImage adsImage;
}
