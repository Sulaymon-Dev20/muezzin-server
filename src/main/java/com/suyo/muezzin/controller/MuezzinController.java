package com.suyo.muezzin.controller;

import com.batoulapps.adhan.CalculationMethod;
import com.batoulapps.adhan.Madhab;
import com.suyo.muezzin.model.PrayTimesMain;
import com.suyo.muezzin.model.TimeType;
import com.suyo.muezzin.service.MuezzinService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "MuezzinController", description = "Controller for Muezzin operations")
public class MuezzinController {
    private final MuezzinService service;

    @GetMapping(value = "muezzin.jpeg", produces = MediaType.IMAGE_JPEG_VALUE)
    @Operation(summary = "Retrieve Image", description = "Retrieve an image based on the provided parameters")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "OK - The request was successful")
    })
    public ResponseEntity<byte[]> getImage(HttpServletRequest request,
                                           @Parameter(description = "Default current day") @RequestParam(required = false) Integer day,
                                           @Parameter(description = "Default current month") @RequestParam(required = false) Integer month,
                                           @Parameter(description = "Default current year") @RequestParam(required = false) Integer year,
                                           @Parameter(description = "Default weekdays image") @RequestParam(required = false) String imageURL,
                                           @Parameter(description = "Default 24 hours") @RequestParam(defaultValue = "T24", required = false) TimeType timeType,
                                           @Parameter(description = "Default calculation MUSLIM_WORLD_LEAGUE") @RequestParam(defaultValue = "MUSLIM_WORLD_LEAGUE", required = false) CalculationMethod method,
                                           @Parameter(description = "Default madhab HANAFI") @RequestParam(defaultValue = "HANAFI", required = false) Madhab madhab) {
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(service.generateImage(request, day, month, year, imageURL, method, madhab, timeType));
    }

    @GetMapping(value = "muezzin.json", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Retrieve Data json", description = "Retrieve data json based on the provided parameters")
    public ResponseEntity<PrayTimesMain> getData(HttpServletRequest request,
                                                 @Parameter(description = "Default current day") @RequestParam(required = false) Integer day,
                                                 @Parameter(description = "Default current month") @RequestParam(required = false) Integer month,
                                                 @Parameter(description = "Default current year") @RequestParam(required = false) Integer year,
                                                 @Parameter(description = "Default 24 hours") @RequestParam(defaultValue = "T24", required = false) TimeType timeType,
                                                 @Parameter(description = "Default calculation MUSLIM_WORLD_LEAGUE") @RequestParam(defaultValue = "MUSLIM_WORLD_LEAGUE", required = false) CalculationMethod method,
                                                 @Parameter(description = "Default madhab HANAFI") @RequestParam(defaultValue = "HANAFI", required = false) Madhab madhab) {
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(service.generateData(request, day, month, year, method, madhab, timeType));
    }
}
