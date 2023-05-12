package team.skyprojava.websitebackend.service.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
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
import java.util.Collections;
import java.util.List;

/**
 * Реализация сервиса для работы с комментариями
 * @see CommentService
 */
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
   private final Logger logger = LoggerFactory.getLogger(CommentServiceImpl.class);
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final UserRepository userRepository;
    private final AdsRepository adsRepository;

    /**
     * Метод получения всех комментариев к объявлению по идентификатору объявления
     *
     * @param id
     */
    @Override
    public ResponseWrapperCommentDto getCommentsByAdsId(int id) {
        logger.info("Was invoked method for getting ad comments by ad id");
        List<Comment> commentList = commentRepository.findAllByAdsId(id);
        if (!commentList.isEmpty()) {
            List<CommentDto> commentDtoList = new ArrayList<>(commentList.size());
            for (Comment c : commentList) {
                commentDtoList.add(commentMapper.toDto(c));
            }
            ResponseWrapperCommentDto result = new ResponseWrapperCommentDto();
            result.setCount(commentList.size());
            result.setResults(commentDtoList);
            logger.info("Comments have been received");
            return result;
        } else {
            ResponseWrapperCommentDto result = new ResponseWrapperCommentDto();
            result.setCount(0);
            result.setResults(Collections.emptyList());
            logger.info("Comments have been received");
            return result;
        }
    }

    /**
     * Метод добавления нового комментария по идентификатору объявления
     *
     * @param id
     * @param commentDto
     * @param authentication
     */
    @Override
    public CommentDto addComment(int id, CommentDto commentDto, Authentication authentication) {
        logger.info("Was invoked method for adding comment by ad id");
        User user = userRepository.findByEmail(authentication.getName()).
                orElseThrow(() -> new UserNotFoundException("User is not found"));
        Comment comment = commentMapper.toEntity(commentDto);
        comment.setAuthor(user);
        comment.setAds(adsRepository.findById(id).orElseThrow(() -> new AdsNotFoundException("Ads is not found")));
        comment.setCreatedAt(LocalDateTime.now());
        commentRepository.save(comment);
        CommentDto newCommentDto = commentMapper.toDto(comment);
        logger.info("Comment has been added");
        return newCommentDto;
    }

    /**
     * Метод удаления комментария по идентификатору
     *
     * @param adId
     * @param commentId
     * @param authentication
     */
    @Override
    public boolean removeComment(int adId, int commentId, Authentication authentication) {
        logger.info("Was invoked method for removing comment by id");
        Comment comment = getCommentById(commentId);
        if (comment.getAds().getId() != adId) {
            logger.warn("Comment by id {} does not belong to ad by id {}", commentId, adId);
            throw new NotFoundException("The comment isn't referred to this ads");
        }
        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new UserNotFoundException("User is not found"));

        if (!comment.getAuthor().getEmail().equals(user.getEmail())
                && !user.getRole().getAuthority().equals("ADMIN")) {
            logger.warn("No access");
            return false;
        }
        commentRepository.delete(comment);
        logger.info("Comment has been deleted");
        return true;
    }

    /**
     * Метод обновления комментария по идентификатору
     *
     * @param adId
     * @param commentId
     * @param commentDto
     * @param authentication
     */
    @Override
    public CommentDto updateComment(int adId, int commentId, CommentDto commentDto, Authentication authentication) {
        logger.info("Was invoked method for updating ad comment by id");
        Comment comment = getCommentById(commentId);

        if (comment.getAds().getId() != adId) {
            logger.warn("Comment by id {} does not belong to ad by id {}", commentId, adId);
            throw new NotFoundException("The comment isn't referred to this ads");
        }

        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new UserNotFoundException("User is not found"));

        if (!comment.getAuthor().getEmail().equals(user.getEmail())
                && !user.getRole().getAuthority().equals("ADMIN")) {
            logger.warn("No access");
            throw new AccessDeniedException("User is not allowed to delete this comment");
        }
        comment.setText(commentDto.getText());
        commentRepository.save(comment);
        logger.info("Comment has been updated");
        CommentDto newCommentDto = commentMapper.toDto(comment);
        return newCommentDto;
    }

    /**
     * Метод получения комментария по идентификатору
     *
     * @param commentId
     */
    public Comment getCommentById(int commentId) {
        logger.info("Was invoked method for get comment by id");
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException("Comment is not found"));
    }


}
