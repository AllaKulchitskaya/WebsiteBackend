package team.skyprojava.websitebackend.service;

import org.springframework.security.core.Authentication;
import team.skyprojava.websitebackend.dto.CommentDto;
import team.skyprojava.websitebackend.dto.ResponseWrapperCommentDto;


public interface CommentService {

    ResponseWrapperCommentDto getCommentsByAdsId(int id);

    CommentDto addComment(int id, CommentDto commentDto, Authentication authentication);

    boolean removeComment(int adId, int commentId, Authentication authentication);

    CommentDto updateComment(int adId, int commentId, CommentDto commentDto, Authentication authentication);
}
