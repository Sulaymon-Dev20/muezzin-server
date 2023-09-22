package com.suyo.muezzin.model;

import com.batoulapps.adhan.PrayerTimes;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import static com.suyo.muezzin.service.PrayTimeService.getTime;

@Data
public class PrayTimes {
    @Schema(title = "Fajr", example = "05:30 AM", description = "The time of the Fajr prayer")
    private String fajr;
    @Schema(title = "Sunrise", example = "06:45 AM", description = "The time of sunrise")
    private String sunrise;
    @Schema(title = "Dhuhr", example = "12:30 PM", description = "The time of the Dhuhr prayer")
    private String dhuhr;
    @Schema(title = "Asr", example = "03:15 PM", description = "The time of the Asr prayer")
    private String asr;
    @Schema(title = "Maghrib", example = "06:00 PM", description = "The time of the Maghrib prayer")
    private String maghrib;
    @Schema(title = "Isha", example = "08:30 PM", description = "The time of the Isha prayer")
    private String isha;

    public PrayTimes(PrayerTimes prayerTimes, boolean is24, String zoneId) {
        this.fajr = getTime(prayerTimes.fajr, is24, zoneId);
        this.sunrise = getTime(prayerTimes.sunrise, is24, zoneId);
        this.dhuhr = getTime(prayerTimes.dhuhr, is24, zoneId);
        this.asr = getTime(prayerTimes.asr, is24, zoneId);
        this.maghrib = getTime(prayerTimes.maghrib, is24, zoneId);
        this.isha = getTime(prayerTimes.isha, is24, zoneId);
    }

}
