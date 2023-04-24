package team.skyprojava.websitebackend.service;

import org.springframework.security.core.Authentication;
import team.skyprojava.websitebackend.dto.CommentDto;
import team.skyprojava.websitebackend.dto.ResponseWrapperCommentDto;

import java.util.List;

public interface CommentService {

    ResponseWrapperCommentDto getAdsComments(int id);

    ResponseWrapperCommentDto getCommentsByAdsId(int id);

    CommentDto addComment(int id, CommentDto commentDto, Authentication authentication);

    void removeComment(int adId, int commentId);

    CommentDto updateComment(int adId, int commentId, CommentDto commentDto);
}
