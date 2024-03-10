package org.vktask.vkrestapitask.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.vktask.vkrestapitask.entity.User;
import org.vktask.vkrestapitask.service.UserService;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationRestApiController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<?> getAuthToken(@RequestParam String username, @RequestParam String password) {
        Optional<User> optionalUser = userService.findUserByUsername(username);
        return optionalUser.isPresent() && optionalUser.get().getPassword().equals(password) ?
                ResponseEntity
                        .status(HttpStatus.FOUND)
                        .body(optionalUser.get().getAuthToken().getToken()) :
                ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .build();
    }
}
