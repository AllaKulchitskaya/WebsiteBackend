package team.skyprojava.websitebackend.controller;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.skyprojava.websitebackend.dto.CommentDto;
import team.skyprojava.websitebackend.dto.ResponseWrapperCommentDto;
import team.skyprojava.websitebackend.service.CommentService;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/ads")
public class CommentController {
    private final Logger logger = LoggerFactory.getLogger(CommentController.class);
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/{id}/comments")
    public ResponseEntity<ResponseWrapperCommentDto> getComments(@PathVariable int id) {
        ResponseWrapperCommentDto responseWrapperCommentDto = commentService.getCommentsByAdsId(id);
        return ResponseEntity.ok(responseWrapperCommentDto);
    }

    @PostMapping("/{id}/comments")
    public ResponseEntity<CommentDto> addComment(@PathVariable int id, @RequestBody CommentDto commentDto) {
        CommentDto newCommentDto = commentService.addComment(id, commentDto);
        return ResponseEntity.ok(newCommentDto);
    }


    @DeleteMapping("/{adId}/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable int adId, @PathVariable int commentId) {
        commentService.removeComment(adId, commentId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{adId}/comments/{commentId}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable int adId, @PathVariable int commentId, @RequestBody CommentDto commentDto) {
        CommentDto updatedCommentDto = commentService.updateComment(adId, commentId, commentDto);
        return ResponseEntity.ok(updatedCommentDto);
    }
}
