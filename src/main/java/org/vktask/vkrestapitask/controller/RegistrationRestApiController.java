package org.vktask.vkrestapitask.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.vktask.vkrestapitask.entity.User;
import org.vktask.vkrestapitask.service.UserService;

@RestController
@RequestMapping("/api/v1/reg")
@Tag(name = "User Registration Endpoint")
@RequiredArgsConstructor
public class RegistrationRestApiController {

    private final UserService userService;

    @Operation(summary = "Create new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Returns new user information", useReturnTypeSchema = true, content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "400", description = "Validation failed")})
    @PostMapping
    public ResponseEntity<?> saveNewUser(@RequestBody User user) {
        System.out.println(userService.validateBeforeSave(user));
        return userService.validateBeforeSave(user) ?
                ResponseEntity
                        .ok()
                        .body(userService.save(user).toString()) :
                ResponseEntity
                        .badRequest()
                        .build();
    }
}
