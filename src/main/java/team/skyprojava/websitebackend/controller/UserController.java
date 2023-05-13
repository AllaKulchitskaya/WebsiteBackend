package team.skyprojava.websitebackend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import team.skyprojava.websitebackend.dto.NewPasswordDto;
import team.skyprojava.websitebackend.dto.UserDto;
import team.skyprojava.websitebackend.service.UserImageService;
import team.skyprojava.websitebackend.service.UserService;

/**
 * Класс контроллер для пользователей
 */
@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "Пользователи", description = "Users")
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;
    private final UserImageService userImageService;

    @Operation(summary = "Изменение пароля",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Новый пароль"
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
            tags = "Users"
    )

    @PostMapping("/set_password")
    public ResponseEntity<Void> setPassword(@RequestBody NewPasswordDto newPasswordDto, Authentication authentication) {
        logger.info("Request for creating new password");
        userService.newPassword(newPasswordDto, authentication);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Изменение информации о пользователе",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Измененная информация",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = UserDto.class)
                            )

                    ),
                    @ApiResponse(responseCode = "401",
                            description = "Not authorized"
                    )
            },
            tags = "Users"
    )
    @PatchMapping("/me")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto, Authentication authentication) {
        logger.info("Request for updating my user information");
        return ResponseEntity.ok(userService.updateUser(userDto, authentication));
    }

    @Operation(summary = "Получение информации об авторизованном пользователе",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Авторизованный пользователь",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = UserDto.class)
                            )
                    ),
                    @ApiResponse(responseCode = "401",
                            description = "Not authorized"
                    )
            },
            tags = "Users"
    )
    @GetMapping("/me")
    public ResponseEntity<UserDto> getUser(Authentication authentication) {
        logger.info("Request for getting my profile");

        UserDto userDto = userService.getUserMe(authentication);
        return ResponseEntity.ok(userDto);
    }

    @SneakyThrows
    @Operation(summary = "Обновление аватара пользователя",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Новое изображение"
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Not authorized"
                    )
            },
            tags = "UserImage"
    )
    @PatchMapping(value = "/me/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateUserImage(Authentication authentication,
                                                @Parameter(in = ParameterIn.DEFAULT,
                                                        description = "Загрузите сюда новое изображение",
                                                        schema = @Schema())
                                                @RequestPart(value = "image") MultipartFile image) {
        logger.info("Request for updating user's image");

        userService.updateUserImage(image, authentication);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Просмотр аватара пользователя",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Изображение, найденное по id",
                            content = @Content(
                                    mediaType = MediaType.IMAGE_PNG_VALUE,
                                    schema = @Schema(implementation = Byte[].class)
                            )
                    )
            },
            tags = "UserImage"
    )
    @GetMapping(value = "/{userId}/image", produces = {MediaType.IMAGE_PNG_VALUE})
    public ResponseEntity<byte[]> getUserImage(@PathVariable int userId) {
        logger.info("Request for getting image by user id");

        return ResponseEntity.ok(userImageService.getImageById(userId).getImage());
    }
}