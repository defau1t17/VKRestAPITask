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

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Authentication Endpoint")
@RequiredArgsConstructor
public class AuthenticationRestApiController {

    private final UserService userService;
    @Operation(summary = "All DB Users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users existing users in DB", useReturnTypeSchema = true,
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "403", description = "You don't have permission for this Endpoint")})
    @GetMapping("/db")
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(userService.findAll());
    }

    @Operation(summary = "Get authentication token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns user authentication token for further requests", useReturnTypeSchema = true,
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "404", description = "Bad credentials")})
    @GetMapping
    public ResponseEntity<?> getAuthToken(@RequestParam String username, @RequestParam String password) {
        Optional<User> optionalUser = userService.findUserByUsername(username);
        return optionalUser.isPresent() && optionalUser.get().getPassword().equals(password) ?
                ResponseEntity
                        .ok()
                        .body(optionalUser.get().getAuthToken().getToken()) :
                ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .build();
    }
}
