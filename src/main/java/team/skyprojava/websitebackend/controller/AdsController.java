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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import team.skyprojava.websitebackend.dto.*;
import team.skyprojava.websitebackend.entity.Ads;
import team.skyprojava.websitebackend.service.AdsService;


@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RequiredArgsConstructor
@RestController
@RequestMapping("/ads")
@Tag(name = "Объявления", description = "AdsController")
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
    @Operation(summary = "Создание объявления",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Созданное объявление",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = AdsDto.class)
                            )
                    )
            },
            tags = "Ads"
    )
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AdsDto> addAds(@Parameter(in = ParameterIn.DEFAULT, description = "Данные нового объявления",
            required = true, schema = @Schema())
                                             @RequestPart("properties") CreateAdsDto createAdsDto,
                                         @RequestPart("image") MultipartFile image) {
        logger.info("Request for add new ad");
        return ResponseEntity.ok(adsService.createAds(createAdsDto, image));
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
    public ResponseEntity<FullAdsDto> getAds(@PathVariable int id) {
        logger.info("Request for get ad by id");
        return ResponseEntity.ok(adsService.getFullAdsDto(id));
    }

    @SneakyThrows
    @Operation(summary = "Удаление объявления",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Удаленное объявление",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Ads.class)
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            },
            tags = "Ads"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeAds(@PathVariable int id) {
        logger.info("Request for delete ad by id");
        adsService.removeAds(id);
        return ResponseEntity.ok().build();
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
                    @ApiResponse(responseCode = "404", description = "Not Found")
            },
            tags = "Ads"
    )
    @PatchMapping("/{id}")
    public ResponseEntity<AdsDto> updateAds(@PathVariable int id,
                                            @RequestBody CreateAdsDto updatedAdsDto) {
        logger.info("Request for update ad by id");
        AdsDto updateAdsDto = adsService.updateAds(id, updatedAdsDto);
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
                    )
            },
            tags = "Ads"
    )
    @GetMapping("/me")
    public ResponseEntity<ResponseWrapperAdsDto> getAdsMe() {
        logger.info("Request for found ads me");
        ResponseWrapperAdsDto responseWrapperAdsDto = adsService.getAdsMe();
        return ResponseEntity.ok(responseWrapperAdsDto);
    }

    @PatchMapping(value = "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<byte[]> updateAdsImage(@PathVariable int id, @Parameter(in = ParameterIn.DEFAULT, description = "Загрузите сюда новое изображение",
            schema = @Schema())
    @RequestPart(value = "image") MultipartFile image) {
        logger.info("Request for update ad image by id");
        return ResponseEntity.ok(adsService.updateAdsImage(id, image));
    }
}
