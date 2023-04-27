package team.skyprojava.websitebackend.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "user_image")
@Data
public class UserImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_image_id")
    private Integer id;
    @Column(name = "image")
    private String image;
}
