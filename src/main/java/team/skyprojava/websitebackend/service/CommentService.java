package team.skyprojava.websitebackend.service;

import team.skyprojava.websitebackend.dto.CommentDto;
import team.skyprojava.websitebackend.dto.ResponseWrapperCommentDto;


public interface CommentService {

    ResponseWrapperCommentDto getCommentsByAdsId(int id);

    CommentDto addComment(int id, CommentDto commentDto);

    void removeComment(int adId, int commentId);

    CommentDto updateComment(int adId, int commentId, CommentDto commentDto);
}
