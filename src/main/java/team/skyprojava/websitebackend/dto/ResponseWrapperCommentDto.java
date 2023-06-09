package team.skyprojava.websitebackend.dto;

import lombok.Data;

import java.util.List;

/**
 * Класс ResponseWrapperCommentDto для хранения списка коментариев
 */
@Data
public class ResponseWrapperCommentDto {
    private int count;
    private List<CommentDto> results;
}