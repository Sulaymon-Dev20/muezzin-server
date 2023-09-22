package com.suyo.muezzin.service;

import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Date;
import java.util.TimeZone;

@Service
public class PrayTimeService {
    public static String getTime(Date date, boolean is24, String zoneId) {
        final SimpleDateFormat dateFormat = new SimpleDateFormat(is24 ? "HH:mm" : "hh:mm a");
        dateFormat.setTimeZone(TimeZone.getTimeZone(ZoneId.of(zoneId)));
        return dateFormat.format(date);
    }
}
