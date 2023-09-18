package com.suyo.muezzin.service;

import com.batoulapps.adhan.CalculationMethod;
import com.batoulapps.adhan.Madhab;
import com.suyo.muezzin.model.PrayTimesMain;
import com.suyo.muezzin.model.TimeType;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Objects;

@Log4j2
@Service
@RequiredArgsConstructor
public class MuezzinService {
    private final GetLocationService getLocationService;
    private final GenerateImageService generateImageService;

    public byte[] generateImage(HttpServletRequest request, Integer day, Integer month, Integer year, String imageId, CalculationMethod method, Madhab madhab, TimeType timeType) {
        return generateImageService.createImage(getLocationService.getIpLocation(request.getRemoteAddr(), request), getDate(year, month, day), request.getRequestURL().toString(), method, imageId, madhab, timeType, request.getLocale());
    }

    public PrayTimesMain generateData(HttpServletRequest request, Integer day, Integer month, Integer year, CalculationMethod method, Madhab madhab, TimeType timeType) {
        return generateImageService.createDate(getLocationService.getIpLocation(request.getRemoteAddr(), request), getDate(year, month, day), method, madhab, timeType);
    }

    private LocalDate getDate(Integer year, Integer month, Integer day) {
        Calendar currentDate = Calendar.getInstance();
        year = Objects.requireNonNullElse(year, currentDate.get(Calendar.YEAR));
        month = Objects.requireNonNullElse(month, currentDate.get(Calendar.MONTH) + 1);
        day = Objects.requireNonNullElse(day, currentDate.get(Calendar.DAY_OF_MONTH));
        return LocalDate.of(year, month, day);
    }
}
