package com.suyo.muezzin.config;

import com.suyo.muezzin.model.properties.ProjectProperties;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.servers.ServerVariable;
import io.swagger.v3.oas.models.servers.ServerVariables;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class SwaggerConfig {

    private final ProjectProperties projectProperties;

    @Value("${server.port:6236}")
    private String localPort;

    @Bean
    public GroupedOpenApi allApis() {
        return GroupedOpenApi.builder()
            .group("all")
            .displayName("all apis")
            .pathsToMatch("/api/**")
            .build();
    }

    @Bean
    public OpenAPI usersMicroserviceOpenAPI() {
        final Server localServer = new Server()
            .url("http://localhost:" + localPort)
            .description("Server URL in Local environment");

        final Server devServer = new Server()
            .url("{protocol}://{address}")
            .description("Server URL in Development environment")
            .variables(new ServerVariables()
                .addServerVariable("protocol", new ServerVariable()._default("http")._enum(List.of("http", "https")))
                .addServerVariable("address", new ServerVariable()._default("16.16.90.73:" + localPort)));

        final Server prodServer = new Server()
            .url("{protocol}://{subdomain}.sulaymonyahyo.com")
            .description("Server URL in Production environment")
            .variables(new ServerVariables()
                .addServerVariable("protocol", new ServerVariable()._default("https")._enum(List.of("http", "https")))
                .addServerVariable("subdomain", new ServerVariable().description("nimadir nimadir nimadir 222")._default("muezzin")));

        return new OpenAPI()
            .info(
                new Info().title(projectProperties.getName())
                    .contact(new Contact().name("𝕊𝕦𝕝𝕒𝕪𝕞𝕠𝕟 𝕐𝕒𝕙𝕪𝕠").url("https://sulaymonyahyo.com").email("sulaymon1w@gmail.com"))
                    .license(new License().name("Apache License Version 2.0").url("https://www.apache.org/licenses/LICENSE-2.0"))
                    .description("<h1>بِسۡمِ ٱللَّهِ ٱلرَّحۡمَٰنِ ٱلرَّحِيمِ</h1>" + projectProperties.getDescription())
                    .version(projectProperties.getVersion()))
            .externalDocs(new ExternalDocumentation()
                .description("Source code Github")
                .url(projectProperties.getUrl()))
            .servers(List.of(localServer, devServer, prodServer));
    }
}
