package com.suyo.muezzin.service;

import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Date;
import java.util.TimeZone;

public record PrayTimeService(Date date, boolean is24, String zoneId) {
    public String getTime() {
        final SimpleDateFormat dateFormat = new SimpleDateFormat(is24 ? "HH:mm" : "hh:mm a");
        dateFormat.setTimeZone(TimeZone.getTimeZone(ZoneId.of(zoneId)));
        return dateFormat.format(date);
    }
}
