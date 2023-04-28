package team.skyprojava.websitebackend.service.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import team.skyprojava.websitebackend.dto.CommentDto;
import team.skyprojava.websitebackend.dto.ResponseWrapperCommentDto;
import team.skyprojava.websitebackend.entity.Comment;
import team.skyprojava.websitebackend.entity.User;
import team.skyprojava.websitebackend.exception.AdsNotFoundException;
import team.skyprojava.websitebackend.exception.CommentNotFoundException;
import team.skyprojava.websitebackend.exception.UserNotFoundException;
import team.skyprojava.websitebackend.mapper.CommentMapper;
import team.skyprojava.websitebackend.repository.AdsRepository;
import team.skyprojava.websitebackend.repository.CommentRepository;
import team.skyprojava.websitebackend.repository.UserRepository;
import team.skyprojava.websitebackend.service.CommentService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
   private final Logger logger = LoggerFactory.getLogger(CommentServiceImpl.class);
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final UserRepository userRepository;
    private final AdsRepository adsRepository;

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
        User user = userRepository.findByEmail("user@mail.ru").
                orElseThrow(() -> new UserNotFoundException("User is not found"));
        Comment comment = commentMapper.toEntity(commentDto);
        comment.setAuthor(user);
        comment.setAds(adsRepository.findById(id).orElseThrow(() -> new AdsNotFoundException("Ads is not found")));
        comment.setCreatedAt(LocalDateTime.now());
        commentRepository.save(comment);
        CommentDto newCommentDto = commentMapper.toDto(comment);
        return newCommentDto;
    }

    @Override
    public void removeComment(int adId, int commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException("Comment is not found"));
        if (comment.getAds().getId() != adId) {
            throw new NotFoundException("The comment isn't referred to this ads");
        }
        commentRepository.delete(comment);
    }

    @Override
    public CommentDto updateComment(int adId, int commentId, CommentDto commentDto) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException("Comment is not found"));
        if (comment.getAds().getId() != adId) {
            throw new NotFoundException("The comment isn't referred to this ads");
        }
        comment.setText(commentDto.getText());
        CommentDto newCommentDto = commentMapper.toDto(comment);
        return newCommentDto;
    }


}
