package team.skyprojava.websitebackend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.webjars.NotFoundException;
import team.skyprojava.websitebackend.dto.NewPasswordDto;
import team.skyprojava.websitebackend.dto.Role;
import team.skyprojava.websitebackend.dto.UserDto;
import team.skyprojava.websitebackend.mapper.UserMapper;
import team.skyprojava.websitebackend.service.UserService;

import javax.validation.Valid;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    private final UserMapper mapper;

    @Operation(summary = "Создание пользователя",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Созданный пользователь",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = UserDto.class)
                            )
                    )
            },
            tags = "Users"
    )

    @PostMapping
    public ResponseEntity<UserDto> addUser(@RequestBody UserDto createUserDto) {
        logger.info("Request for add user");
        return ResponseEntity.ok(userService.User(createUserDto));
    }

    @Operation(summary = "Просмотр всех пользователей",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Пользователи",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = UserDto.class)
                            )
                    )
            },
            tags = "Users"
    )
    @GetMapping("/me")
    public ResponseEntity<UserDto> getUserMe(Authentication authentication) {
        logger.info("Request for get users");
        return ResponseEntity.ok(userService.getUserMe(authentication));
    }

    @Operation(summary = "Изменение пароля",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Новый пароль",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = NewPasswordDto.class)
                            )
                    )
            },
            tags = "Users"
    )

    @PostMapping("/set_password")
    public ResponseEntity<NewPasswordDto> setPassword(@Valid @RequestBody NewPasswordDto newPasswordDto) {
        logger.info("Request for create new password");
        userService.newPassword(newPasswordDto.getNewPassword(), newPasswordDto.getCurrentPassword());
        return ResponseEntity.ok(newPasswordDto);
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
                    )
            },
            tags = "Users"
    )
    @PatchMapping("/me")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto) {
        logger.info("Request for update user");
        return ResponseEntity.ok(userService.updateUser(userDto));
    }

    @Operation(summary = "Получение всех пользователей",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Все пользователи",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = UserDto.class)
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            },
            tags = "Users"
    )
    @GetMapping("/me")
    public ResponseEntity<UserDto> getUsers() {
        logger.info("Request for get users");
        return ResponseEntity.ok(new UserDto());
    }

    @Operation(summary = "Поиск пользователя по id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Найденный пользователь",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = UserDto.class)
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            },
            tags = "Users"
    )
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable int id) {
        logger.info("Request for get user by id");
        try {
            return ResponseEntity.ok(userService.getUserById(id));
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/me")
    public ResponseEntity<UserDto> getUser() {
        System.out.println("Проверка get_me");
        return ResponseEntity.ok(new UserDto());
    }



    @PatchMapping("/me/image")
    public ResponseEntity<Void> updateUserImage(@RequestBody MultipartFile image) {
        System.out.println("Проверка_me_image");
        return ResponseEntity.ok().build();
    }

    @PutMapping("{id}/updateRole")
    public ResponseEntity<UserDto> updateRole(@PathVariable("id") int id, Role role) {
        logger.info("Request for update user role");
        return ResponseEntity.ok(userService.updateRole(id, role));
    }


}
