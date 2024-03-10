package org.vktask.vkrestapitask.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.vktask.vkrestapitask.entity.User;
import org.vktask.vkrestapitask.service.UserService;

@RestController
@RequestMapping("/api/v1/reg")
@RequiredArgsConstructor
public class RegistrationRestApiController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<?> saveNewUser(@RequestBody User user) {
        System.out.println(user);
        return userService.validateBeforeSave(user) ?
                ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body(userService.save(user).toString()) :
                ResponseEntity
                        .badRequest()
                        .build();
    }
}
