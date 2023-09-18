package com.suyo.muezzin.config;

import com.maxmind.db.Reader;
import com.maxmind.geoip2.DatabaseReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class GeoLocationConfig {
    @Bean
    public DatabaseReader databaseReader() {
        try {
            log.info("🍭 GeoLocationConfig: Trying to load GeoLite2-Country database...");
            InputStream dbAsStream = new ClassPathResource("data/GeoLite2-City.mmdb").getInputStream();
            log.info("\uD83C\uDF69 GeoLocationConfig: Database was loaded successfully.");
            return new DatabaseReader.Builder(dbAsStream).fileMode(Reader.FileMode.MEMORY).build();
        } catch (IOException | NullPointerException e) {
            log.error("💥 Database reader cound not be initialized. ", e);
            return null;
        }
    }

}
