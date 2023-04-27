package team.skyprojava.websitebackend.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import team.skyprojava.websitebackend.dto.CommentDto;
import team.skyprojava.websitebackend.dto.ResponseWrapperCommentDto;
import team.skyprojava.websitebackend.entity.Comment;
import team.skyprojava.websitebackend.exception.CommentNotFoundException;
import team.skyprojava.websitebackend.mapper.CommentMapper;
import team.skyprojava.websitebackend.repository.CommentRepository;
import team.skyprojava.websitebackend.service.CommentService;

import java.util.ArrayList;
import java.util.List;

public class CommentServiceImpl implements CommentService {
    Logger logger = LoggerFactory.getLogger(CommentService.class);
    CommentRepository commentRepository;
    CommentMapper commentMapper;

    @Override
    public ResponseWrapperCommentDto getCommentsByAdsId(int id) {
        logger.info("Was invoked method for get ads comment by id");
        List<Comment> commentList = commentRepository.findAllByAdsId(id);
        if (!commentList.isEmpty()) {
            List<CommentDto> commentDtoList = new ArrayList<>(commentList.size());
            for (Comment c : commentList) {
                commentDtoList.add(commentMapper.toDto(c));
            }
            ResponseWrapperCommentDto result = new ResponseWrapperCommentDto();
            result.setCount(commentList.size());
            result.setResults(commentDtoList);
            return result;
        }
        throw new CommentNotFoundException("Comments are not found");
    }

    @Override
    public CommentDto addComment(int id, CommentDto commentDto) {
        return null;
    }

    @Override
    public void removeComment(int adId, int commentId) {

    }

    @Override
    public CommentDto updateComment(int adId, int commentId, CommentDto commentDto) {
        return null;
    }


}
