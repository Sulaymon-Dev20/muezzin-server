package com.suyo.muezzin.service;

import com.batoulapps.adhan.Coordinates;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.model.CityResponse;
import com.suyo.muezzin.model.GeoIP;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import ua_parser.Client;
import ua_parser.Parser;

import java.net.InetAddress;
import java.util.Objects;

import static java.util.Objects.nonNull;

@Log4j2
@Service
@RequiredArgsConstructor
public class GetLocationService {
    private final DatabaseReader databaseReader;
    private static final String UNKNOWN = "UNKNOWN";

    private String getDeviceDetails(String userAgent) {
        String deviceDetails = UNKNOWN;
        Parser parser = new Parser();
        Client client = parser.parse(userAgent);
        if (nonNull(client)) {
            deviceDetails = client.userAgent.family + " " + client.userAgent.major + "." + client.userAgent.minor + " - " + client.os.family + " " + client.os.major + "." + client.os.minor;
        }
        return deviceDetails;
    }

    public GeoIP getIpLocation(String ip, HttpServletRequest request) {
        GeoIP position = new GeoIP();
        try {
            String location;
            InetAddress ipAddress = InetAddress.getByName(ip);
            CityResponse cityResponse = databaseReader.city(ipAddress);
            if (nonNull(cityResponse) && nonNull(cityResponse.getCity())) {
                String continent = Objects.requireNonNullElse(cityResponse.getContinent().getName(), "");
                String country = Objects.requireNonNullElse(cityResponse.getCountry().getName(), "");
                location = String.format("%s, %s, %s", continent, country, cityResponse.getCity().getName());
                position.setCity(cityResponse.getCity().getName());
                position.setFullLocation(location);
                position.setTimeZone(cityResponse.getLocation().getTimeZone());
                position.setLocation(new Coordinates(Objects.requireNonNullElse(cityResponse.getLocation().getLatitude(), 0.0), Objects.requireNonNullElse(cityResponse.getLocation().getLongitude(), 0.0)));
                position.setDevice(getDeviceDetails(request.getHeader("user-agent")));
                position.setIpAddress(ip);
            }
        } catch (Exception e) {
            position.setTimeZone("Asia/Tashkent");
            position.setLocation(new Coordinates(41.289960, 69.358807));
            position.setFullLocation("Tashkent");
        }
        return position;
    }

}
