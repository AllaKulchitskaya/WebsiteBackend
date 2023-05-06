package team.skyprojava.websitebackend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import team.skyprojava.websitebackend.dto.*;
import team.skyprojava.websitebackend.entity.Ads;
import team.skyprojava.websitebackend.service.AdsService;

import javax.servlet.http.HttpServletResponse;


@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RequiredArgsConstructor
@RestController
@RequestMapping("/ads")
@Tag(name = "Объявления", description = "Ads")
public class AdsController {

    private final Logger logger = LoggerFactory.getLogger(AdsController.class);
    private final AdsService adsService;

    @Operation(summary = "Просмотр всех объявлений",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Все найденные объявления",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = AdsDto[].class)
                            )
                    )
            },
            tags = "Ads"
    )
    @GetMapping
    public ResponseEntity<ResponseWrapperAdsDto> getAllAds() {
        logger.info("Request for found all ads");
        ResponseWrapperAdsDto responseWrapperAdsDto = adsService.getAllAds();
        return ResponseEntity.ok(responseWrapperAdsDto);
    }
    @SneakyThrows
    @PreAuthorize("hasAnyAuthority('USER')")
    @Operation(summary = "Создание объявления",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Созданное объявление",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = AdsDto.class)
                            )
                    ),
                    @ApiResponse(responseCode = "401", description = "Not authorized")
            },
            tags = "Ads"
    )
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AdsDto> addAds(Authentication authentication,
                                         @Parameter(in = ParameterIn.DEFAULT,
                                                 description = "Данные нового объявления", required = true,
                                                 schema = @Schema())
                                             @RequestPart("properties") CreateAdsDto createAdsDto,
                                         @RequestPart("image") MultipartFile image) {
        logger.info("Request for add new ad");
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(adsService.createAds(createAdsDto, image, authentication));
    }

    @Operation(summary = "Поиск объявления по id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Объявление, найденное по id",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Ads.class)
                            )
                    )
            },
            tags = "Ads"
    )
    @GetMapping("/{id}")
    public ResponseEntity<FullAdsDto> getFullAds(@PathVariable int id, Authentication authentication) {
        logger.info("Request for get ad by id");
        if (!SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(adsService.getFullAdsDto(id, authentication));
    }

    @SneakyThrows
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @Operation(summary = "Удаление объявления",
            responses = {
                    @ApiResponse(
                            responseCode = "204"
                    ),
                    @ApiResponse(responseCode = "401", description = "Not authorized"),
                    @ApiResponse(responseCode = "403", description = "Not access")
            },
            tags = "Ads"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> removeAds(@PathVariable int id, Authentication authentication) {
        logger.info("Request for delete ad by id");
        if (adsService.removeAds(id, authentication)){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED).build();
    }

    @SneakyThrows
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @Operation(summary = "Изменение объявления",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Измененное объявление",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = AdsDto.class)
                            )
                    ),
                    @ApiResponse(responseCode = "401", description = "Not authorized"),
                    @ApiResponse(responseCode = "403", description = "Not access")
            },
            tags = "Ads"
    )
    @PatchMapping("/{id}")
    public ResponseEntity<AdsDto> updateAds(@PathVariable int id,
                                            @RequestBody CreateAdsDto updatedAdsDto, Authentication authentication) {
        logger.info("Request for update ad by id");
        AdsDto updateAdsDto = adsService.updateAds(id, updatedAdsDto, authentication);
        if (!SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(updateAdsDto);
    }

    @Operation(summary = "Просмотр всех моих объявлений",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Все мои объявления",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = AdsDto[].class)
                            )
                    ),
                    @ApiResponse(responseCode = "401", description = "Not authorized")
            },
            tags = "Ads"
    )
    @GetMapping("/me")
    public ResponseEntity<ResponseWrapperAdsDto> getAdsMe(Authentication authentication) {
        logger.info("Request for found ads me");
        if (!SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        ResponseWrapperAdsDto responseWrapperAdsDto = adsService.getAdsMe(authentication);
        return ResponseEntity.ok(responseWrapperAdsDto);
    }

    @SneakyThrows
    @Operation(summary = "Обновление изображения объявления",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Новое изображение",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_OCTET_STREAM_VALUE,
                                    schema = @Schema(implementation = Byte[].class)
                            )
                    )
            },
            tags = "AdsImage"
    )
    @PatchMapping(value = "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<byte[]> updateAdsImage(@PathVariable int id,
                                                 @Parameter(in = ParameterIn.DEFAULT,
                                                         description = "Загрузите сюда новое изображение",
                                                         schema = @Schema())
                                                 @RequestPart(value = "image") MultipartFile image) {
        logger.info("Request for update ad image by id");
        return ResponseEntity.ok(adsService.updateAdsImage(id, image));
    }
}
