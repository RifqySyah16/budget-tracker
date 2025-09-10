package com.devland.finalproject.budget_tracker.common.configuration;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class SwaggerConfiguration {
    @Bean
    OpenAPI app() {
        Info info = new Info();
        info.setTitle("Budget Tracker");
        info.description("Data Station");
        info.setVersion("1.0.0");

        Server server = new Server();
        server.setUrl("https://263c-103-154-109-62.ngrok-free.app");
        server.setDescription("Localhost Server");

        return new OpenAPI().info(info).servers(List.of(server));
    }
}