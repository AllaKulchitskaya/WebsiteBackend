package team.skyprojava.websitebackend.service;

import org.springframework.security.core.Authentication;
import team.skyprojava.websitebackend.dto.CommentDto;
import team.skyprojava.websitebackend.dto.ResponseWrapperCommentDto;

/**
 * Предоставляет методы обработки комментариев
 */
public interface CommentService {

    /**
     * Запрос для просмотра комментариев по идентификатору пользователя
     *
     * @param id
     * @return
     */
    ResponseWrapperCommentDto getCommentsByAdsId(int id);

    /**
     * Запрос на добавление комментария пользователя
     *
     * @param id
     * @param commentDto
     * @param authentication
     * @return
     */
    CommentDto addComment(int id, CommentDto commentDto, Authentication authentication);

    /**
     * Запрос на удаление комментария пользователя
     *
     * @param adId
     * @param commentId
     * @param authentication
     * @return
     */
    boolean removeComment(int adId, int commentId, Authentication authentication);

    /**
     * Запрос на обновление комментария пользователя
     *
     * @param adId
     * @param commentId
     * @param commentDto
     * @param authentication
     * @return
     */
    CommentDto updateComment(int adId, int commentId, CommentDto commentDto, Authentication authentication);
}