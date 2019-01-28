package com.example.userservice.handlers;

import com.example.userservice.model.User;
import com.example.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyExtractors;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import javax.validation.constraints.NotNull;

@Component
public class UserHandler {

    @Autowired
    private UserRepository userRepository;

    public Mono<ServerResponse> getUsers(@NotNull ServerRequest serverRequest) {
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(userRepository.findAll(), User.class);
    }

    public Mono<ServerResponse> getUserById(@NotNull ServerRequest serverRequest) {
        String id = serverRequest.pathVariable("id");

        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(userRepository.findById(id), User.class);
    }

    public Mono<ServerResponse> saveUser(@NotNull ServerRequest serverRequest) {
        Mono<User> newUser = serverRequest.body(BodyExtractors.toMono(User.class)).flatMap(user -> userRepository.save(user));

        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(newUser, User.class);
    }

    public Mono<ServerResponse> updateUser(@NotNull ServerRequest serverRequest) {
        String id = serverRequest.pathVariable("id");

        Mono<User> userUpdated = userRepository
                .findById(id)
                .flatMap(user -> {
                    User updated = serverRequest.body(BodyExtractors.toMono(User.class)).block();

                    user.setName(updated.getName());

                    return userRepository.save(updated);
                });

        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(userUpdated, User.class);
    }


}
