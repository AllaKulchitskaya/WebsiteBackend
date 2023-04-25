package team.skyprojava.websitebackend.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import team.skyprojava.websitebackend.dto.ResponseWrapperCommentDto;
import team.skyprojava.websitebackend.mapper.CommentMapper;
import team.skyprojava.websitebackend.repository.CommentRepository;
import team.skyprojava.websitebackend.service.CommentService;

public class CommentServiceImpl implements CommentService {
    Logger logger = LoggerFactory.getLogger(CommentService.class);
    CommentRepository commentRepository;
    CommentMapper commentMapper;
    @Override
    public ResponseWrapperCommentDto getAdsComments(int id) {
        logger.info("Was invoked method for get ads comment by id");
        return commentMapper.toDto(commentRepository.findAllByAdsId(id));
    }


}
