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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import team.skyprojava.websitebackend.dto.*;
import team.skyprojava.websitebackend.service.AdsImageService;
import team.skyprojava.websitebackend.service.AdsService;

/**
 * Класс контроллер для объявлений
 */
@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RequiredArgsConstructor
@RestController
@RequestMapping("/ads")
@Tag(name = "Объявления", description = "Ads")
public class AdsController {

    private final Logger logger = LoggerFactory.getLogger(AdsController.class);
    private final AdsService adsService;
    private final AdsImageService adsImageService;

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
        logger.info("Request for getting all ads");
        ResponseWrapperAdsDto responseWrapperAdsDto = adsService.getAllAds();
        return ResponseEntity.ok(responseWrapperAdsDto);
    }

    @SneakyThrows
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
                                                 schema = @Schema(implementation = AdsDto.class))
                                             @RequestPart("properties") CreateAdsDto createAdsDto,
                                         @RequestPart("image") MultipartFile image) {
        logger.info("Request for adding a new ad");
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
                                    schema = @Schema(implementation = FullAdsDto.class)
                            )
                    ),
                    @ApiResponse(responseCode = "401", description = "Not authorized")
            },
            tags = "Ads"
    )
    @GetMapping("/{id}")
    public ResponseEntity<FullAdsDto> getFullAds(@PathVariable int id, Authentication authentication) {
        logger.info("Request for getting ad by id");
        return ResponseEntity.ok(adsService.getFullAdsDto(id, authentication));
    }

    @SneakyThrows
    @Operation(summary = "Удаление объявления",
            responses = {
                    @ApiResponse(
                            responseCode = "204"
                    ),
                    @ApiResponse(responseCode = "401", description = "Not authorized"),
                    @ApiResponse(responseCode = "403", description = "No access")
            },
            tags = "Ads"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> removeAds(@PathVariable int id, Authentication authentication) {
        logger.info("Request for deleting ad by id");
        if (adsService.removeAds(id, authentication)){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @SneakyThrows
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
                    @ApiResponse(responseCode = "403", description = "No access")
            },
            tags = "Ads"
    )
    @PatchMapping("/{id}")
    public ResponseEntity<AdsDto> updateAds(@PathVariable int id,
                                            @RequestBody CreateAdsDto updatedAdsDto, Authentication authentication) {
        logger.info("Request for updating ad by id");
        AdsDto updateAdsDto = adsService.updateAds(id, updatedAdsDto, authentication);
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
        logger.info("Request for getting my ads");
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
                    ),
                    @ApiResponse(responseCode = "401", description = "Not authorized"),
                    @ApiResponse(responseCode = "403", description = "No access")
            },
            tags = "AdsImage"
    )
    @PatchMapping(value = "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<byte[]> updateAdsImage(Authentication authentication,
                                                 @PathVariable int id,
                                                 @Parameter(in = ParameterIn.DEFAULT,
                                                         description = "Загрузите сюда новое изображение",
                                                         schema = @Schema())
                                                 @RequestPart(value = "image") MultipartFile image) {
        logger.info("Request for updating ad image by id");
        return ResponseEntity.ok(adsService.updateAdsImage(id, image, authentication));
    }

    @Operation(summary = "Просмотр изображения к объявлению",
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
            tags = "AdsImage"
    )
    @GetMapping(value = "/{adsId}/image", produces = {MediaType.IMAGE_PNG_VALUE})
    public ResponseEntity<byte[]> getAdsImage(@PathVariable int adsId) {
        logger.info("Request for getting image by ad id");
        return ResponseEntity.ok(adsImageService.getImageById(adsId).getImage());
    }
}