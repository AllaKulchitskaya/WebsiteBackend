package team.skyprojava.websitebackend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import team.skyprojava.websitebackend.dto.CommentDto;
import team.skyprojava.websitebackend.dto.ResponseWrapperCommentDto;
import team.skyprojava.websitebackend.service.CommentService;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/ads")
@Tag(name = "Комментарии", description = "Comments")
public class CommentController {
    private final Logger logger = LoggerFactory.getLogger(CommentController.class);
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @Operation(summary = "Просмотр комментариев к объявлению",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Комментарии",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = CommentDto[].class)
                            )
                    )
            },
            tags = "Comments"
    )
    @GetMapping("/{id}/comments")
    public ResponseEntity<ResponseWrapperCommentDto> getComments(@PathVariable int id) {
        ResponseWrapperCommentDto responseWrapperCommentDto = commentService.getCommentsByAdsId(id);
        return ResponseEntity.ok(responseWrapperCommentDto);
    }

    @Operation(summary = "Добавление комментария к объявлению",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Комментарий",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = CommentDto.class)
                            )
                    )
            },
            tags = "Comments"
    )
    @PostMapping("/{id}/comments")
    public ResponseEntity<CommentDto> addComment(@PathVariable int id, @RequestBody CommentDto commentDto, Authentication authentication) {
        CommentDto newCommentDto = commentService.addComment(id, commentDto, authentication);
        return ResponseEntity.ok(newCommentDto);
    }


    @Operation(summary = "Удаление комментариея",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Удаленный комментарий",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = CommentDto.class)
                            )
                    )
            },
            tags = "Comments"
    )
    @DeleteMapping("/{adId}/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable int adId, @PathVariable int commentId) {
        commentService.removeComment(adId, commentId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Изменение комментария",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Измененный комментарий",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = CommentDto.class)
                            )
                    )
            },
            tags = "Comments"
    )
    @PatchMapping("/{adId}/comments/{commentId}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable int adId, @PathVariable int commentId, @RequestBody CommentDto commentDto) {
        CommentDto updatedCommentDto = commentService.updateComment(adId, commentId, commentDto);
        return ResponseEntity.ok(updatedCommentDto);
    }
}
