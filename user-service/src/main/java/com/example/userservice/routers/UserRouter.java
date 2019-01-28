package com.example.userservice.routers;

import com.example.userservice.handlers.UserHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class UserRouter {

    @Bean
    public RouterFunction<ServerResponse> route(UserHandler userHandler) {
        return RouterFunctions
                .route(RequestPredicates.GET("/users"), userHandler::getUsers)
                .andRoute(RequestPredicates.GET("/users/{id}"), userHandler::getUserById)
                .andRoute(RequestPredicates.POST("/users"), userHandler::saveUser)
                .andRoute(RequestPredicates.PUT("/users/{id}"), userHandler::updateUser);
    }

}
