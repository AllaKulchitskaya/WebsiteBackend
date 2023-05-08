package team.skyprojava.websitebackend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
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
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Not authorized"
                    )
            },
            tags = "Comments"
    )
    @GetMapping("/{id}/comments")
    public ResponseEntity<ResponseWrapperCommentDto> getComments(@PathVariable int id) {
        logger.info("Request for getting ad comments");

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
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Not authorized"
                    )
            },
            tags = "Comments"
    )
    @PostMapping("/{id}/comments")
    public ResponseEntity<CommentDto> addComment(@PathVariable int id,
                                                 @RequestBody CommentDto commentDto,
                                                 Authentication authentication) {
        logger.info("Request for adding an ad comment");

        CommentDto newCommentDto = commentService.addComment(id, commentDto, authentication);
        return ResponseEntity.ok(newCommentDto);
    }


    @Operation(summary = "Удаление комментариев",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Удаленный комментарий"
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Not authorized"
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "No access"
                    )
            },
            tags = "Comments"
    )
    @DeleteMapping("/{adId}/comments/{commentId}")
    public ResponseEntity<HttpStatus> deleteComment(@PathVariable int adId,
                                                    @PathVariable int commentId,
                                                    Authentication authentication) {
        logger.info("Request for deleting ad comment");

        if (commentService.removeComment(adId, commentId, authentication)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
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
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Not authorized"
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "No access"
                    )
            },
            tags = "Comments"
    )
    @PatchMapping("/{adId}/comments/{commentId}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable int adId, @PathVariable int commentId,
                                                    @RequestBody CommentDto commentDto, Authentication authentication) {
        logger.info("Request for updating ad comment");

        CommentDto updatedCommentDto = commentService.updateComment(adId, commentId, commentDto, authentication);
        return ResponseEntity.ok(updatedCommentDto);
    }
}
