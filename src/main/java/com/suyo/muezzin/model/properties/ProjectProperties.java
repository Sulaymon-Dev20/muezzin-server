package com.suyo.muezzin.model.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "spring.application")
@Configuration("project.properties")
@Data
public class ProjectProperties {
    String name;
    String description;
    String url;
    String version;
}
