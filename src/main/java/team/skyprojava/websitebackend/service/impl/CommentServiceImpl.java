package team.skyprojava.websitebackend.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import team.skyprojava.websitebackend.dto.CommentDto;
import team.skyprojava.websitebackend.dto.ResponseWrapperCommentDto;
import team.skyprojava.websitebackend.entity.Comment;
import team.skyprojava.websitebackend.exception.AdsNotFoundException;
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
    public ResponseWrapperCommentDto getAdsComments(int id) {
        logger.info("Was invoked method for get ads comment by id");
        List<Comment> adsList = commentRepository.findAll();
        if (!adsList.isEmpty()) {
            List<CommentDto> adsDtoList = new ArrayList<>(adsList.size());
            for (Comment a : adsList) {
                adsDtoList.add(commentMapper.toDto(a));
            }
            ResponseWrapperCommentDto result = new ResponseWrapperCommentDto();
            result.setCount(adsList.size());
            result.setResults(adsDtoList);
            return result;
        }
        throw new AdsNotFoundException("Ads are not found");
    }

    @Override
    public ResponseWrapperCommentDto getCommentsByAdsId(int id) {
        return null;
    }

    @Override
    public CommentDto addComment(int id, CommentDto commentDto, Authentication authentication) {
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
