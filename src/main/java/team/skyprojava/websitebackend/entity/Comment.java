package team.skyprojava.websitebackend.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Класс сущности комментария к объявлению
 */
@Entity
@Table(name = "comment")
@Data
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User author;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "text")
    private String text;
    @ManyToOne
    @JoinColumn(name = "ads_id")
    private Ads ads;
}