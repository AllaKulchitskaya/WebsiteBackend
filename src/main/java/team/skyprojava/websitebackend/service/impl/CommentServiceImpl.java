package team.skyprojava.websitebackend.service.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import team.skyprojava.websitebackend.dto.CommentDto;
import team.skyprojava.websitebackend.dto.ResponseWrapperCommentDto;
import team.skyprojava.websitebackend.entity.Ads;
import team.skyprojava.websitebackend.entity.Comment;
import team.skyprojava.websitebackend.entity.User;
import team.skyprojava.websitebackend.exception.AdsNotFoundException;
import team.skyprojava.websitebackend.exception.CommentNotFoundException;
import team.skyprojava.websitebackend.exception.UserNotFoundException;
import team.skyprojava.websitebackend.mapper.CommentMapper;
import team.skyprojava.websitebackend.repository.AdsRepository;
import team.skyprojava.websitebackend.repository.CommentRepository;
import team.skyprojava.websitebackend.repository.UserRepository;
import team.skyprojava.websitebackend.security.SecurityAccess;
import team.skyprojava.websitebackend.service.CommentService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
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
        } else {
            ResponseWrapperCommentDto result = new ResponseWrapperCommentDto();
            result.setCount(0);
            result.setResults(Collections.emptyList());
            return result;
        }
    }

    @Override
    public CommentDto addComment(int id, CommentDto commentDto, Authentication authentication) {
        User user = userRepository.findByEmail(authentication.getName()).
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
    public boolean removeComment(int adId, int commentId, Authentication authentication) {
        logger.info("Was invoked method for delete ads comment by adKey and id");
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("Комментарий с id " + commentId + " не найден!"));
        if (comment.getAds().getId() != adId) {
            logger.warn("Comment by id {} does not belong to ad by id {} ", commentId, adId);
            throw new NotFoundException("Комментарий с id " + commentId + "   " + adId);
        }

        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new UserNotFoundException("User is not found"));

        if (!comment.getAuthor().getEmail().equals(user.getEmail())
                && !user.getRole().getAuthority().equals("ADMIN")) {
            logger.warn("No access");
            return false;
        }
        commentRepository.delete(comment);
        logger.info("Comment deleted");
        return true;
    }

    @Override
    public CommentDto updateComment(int adId, int commentId, CommentDto commentDto, Authentication authentication) {
        logger.info("Was invoked method for update ads comment by adKey and id");
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("Комментарий с id " + commentId + " не найден!"));
        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new UserNotFoundException("User is not found"));

        if (!comment.getAuthor().getEmail().equals(user.getEmail()) &&
                !user.getRole().getAuthority().equals("ADMIN")) {
            if (comment.getAds().getId() != adId) {
                logger.warn("Comment by id {} does not belong to ad by id {} ", commentId, adId);
            }
            logger.warn("No access");
        }
        comment.setText(commentDto.getText());
        commentRepository.save(comment);
        logger.info("Comment updated");
        CommentDto newCommentDto = commentMapper.toDto(comment);
        return newCommentDto;
    }

    public Comment getCommentById(int commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException("Comment is not found"));
    }


}
