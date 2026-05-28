package com.sebas.finance.module.user.infrastructure.controller;

import com.sebas.finance.module.user.application.service.UserService;
import com.sebas.finance.module.user.domain.model.*;
import com.sebas.finance.module.user.infrastructure.controller.dto.UserRequest;
import com.sebas.finance.module.user.infrastructure.controller.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody UserRequest request) {
        User user = new User(
                new UserId(java.util.UUID.randomUUID().toString()),
                new UserFullName(request.getUserFullName()),
                new UserEmail(request.getUserEmail()),
                new UserPassword(request.getUserPassword())
        );
        userService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable String id, @RequestBody UserRequest request) {
        User user = new User(
                new UserId(id),
                new UserFullName(request.getUserFullName()),
                new UserEmail(request.getUserEmail()),
                new UserPassword(request.getUserPassword())
        );
        userService.update(user);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        userService.delete(new UserId(id));
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> findAll() {
        List<UserResponse> response = userService.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> findById(@PathVariable String id) {
        return userService.findById(new UserId(id))
                .map(user -> ResponseEntity.ok(toResponse(user)))
                .orElse(ResponseEntity.notFound().build());
    }

    private UserResponse toResponse(User user) {
        UserResponse response = new UserResponse();
        response.setUserId(user.getUserId().getValue());
        response.setUserFullName(user.getUserFullName().getValue());
        response.setUserEmail(user.getUserEmail().getValue());
        return response;
    }
}
