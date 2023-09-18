package com.suyo.muezzin.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@RequiredArgsConstructor
public class SpringShutdownHook implements DisposableBean {

    @Value("${spring.application.name:SpringBoot}")
    private String applicationName;

    @Override
    public void destroy() {
        log.info("🐜 project " + applicationName + " stopped");
    }
}
