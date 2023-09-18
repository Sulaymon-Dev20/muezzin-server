package com.suyo.muezzin.service;

import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class PrayTimeService {
    public static String getTime(Date date, boolean is24) {
        return new SimpleDateFormat(is24 ? "HH:mm" : "hh:mm a").format(date);
    }
}
