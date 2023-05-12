package team.skyprojava.websitebackend.entity;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;

/**
 * Класс аватарка пользователя
 */
@Entity
@Table(name = "user_image")
@Data
public class UserImage {
    @Id
    @Column(name = "user_image_id")
    private String id;
    @Column(name = "image")
    @Lob
    @Type(type = "org.hibernate.type.BinaryType")
    private byte[] image;
}
