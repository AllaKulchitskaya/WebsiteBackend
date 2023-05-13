package team.skyprojava.websitebackend.service;

import org.springframework.security.core.Authentication;
import team.skyprojava.websitebackend.dto.CommentDto;
import team.skyprojava.websitebackend.dto.ResponseWrapperCommentDto;

/**
 * Интерфейс сервиса для работы с комментариями
 */
public interface CommentService {

    /**
     * Метод для просмотра всех комментариев по идентификатору объявления
     *
     * @param id
     */
    ResponseWrapperCommentDto getCommentsByAdsId(int id);

    /**
     * Метод добавления нового комментария пользователем
     *
     * @param id
     * @param commentDto
     * @param authentication
     */
    CommentDto addComment(int id, CommentDto commentDto, Authentication authentication);

    /**
     * Метод удаления комментария пользователя
     *
     * @param adId
     * @param commentId
     * @param authentication
     */
    boolean removeComment(int adId, int commentId, Authentication authentication);

    /**
     * Метод обновления комментария пользователя
     *
     * @param adId
     * @param commentId
     * @param commentDto
     * @param authentication
     */
    CommentDto updateComment(int adId, int commentId, CommentDto commentDto, Authentication authentication);
}