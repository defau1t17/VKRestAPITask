package org.vktask.vkrestapitask.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.vktask.vkrestapitask.dto.AlbumDTO;
import org.vktask.vkrestapitask.dto.PostsDTO;
import org.vktask.vkrestapitask.dto.UsersDTO;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "Users Proxy Endpoint")
@RequiredArgsConstructor
public class UsersRestApiController {
    private final HttpClient httpClient = HttpClientBuilder.create().build();
    private final RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory(httpClient));

    private final ObjectMapper objectMapper;

    @Operation(summary = "All Users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get all Users ", useReturnTypeSchema = true,
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UsersDTO.class))}),
            @ApiResponse(responseCode = "403", description = "You don't have permission for this Endpoint")})
    @GetMapping
    @Cacheable("users")
    public ResponseEntity<?> getUsers(HttpServletRequest httpServletRequest) {
        return httpServletRequest.getQueryString() == null || httpServletRequest.getQueryString().isEmpty() ?
                restTemplate.getForEntity("https://jsonplaceholder.typicode.com/users", UsersDTO[].class) :
                restTemplate.getForEntity("https://jsonplaceholder.typicode.com/users?%s".formatted(
                        httpServletRequest.getQueryString().replaceAll("%20", " ")), UsersDTO[].class);
    }

    @Operation(summary = "User by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get User by ID", useReturnTypeSchema = true,
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UsersDTO.class))}),
            @ApiResponse(responseCode = "403", description = "You don't have permission for this Endpoint")})
    @GetMapping("/{id}")
    @Cacheable("users")
    public ResponseEntity<?> getUserByID(@PathVariable(name = "id") String id) {
        return restTemplate.getForEntity("https://jsonplaceholder.typicode.com/users/%s".formatted(id), UsersDTO.class);
    }


    @Operation(summary = "All User albums by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User albums by ID", useReturnTypeSchema = true,
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = AlbumDTO.class))}),
            @ApiResponse(responseCode = "403", description = "You don't have permission for this Endpoint")})
    @GetMapping("/{id}/albums")
    @Cacheable("userAlbums")
    public ResponseEntity<?> getUserAlbums(@PathVariable(name = "id") String id) {
        return restTemplate.getForEntity("https://jsonplaceholder.typicode.com/users/%s/albums".formatted(id), AlbumDTO[].class);
    }

    @Operation(summary = "All User posts by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User posts by ID", useReturnTypeSchema = true,
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = PostsDTO.class))}),
            @ApiResponse(responseCode = "403", description = "You don't have permission for this Endpoint")})
    @GetMapping("/{id}/posts")
    @Cacheable("userPosts")
    public ResponseEntity<?> getUserPosts(@PathVariable(name = "id") String id) {
        return restTemplate.getForEntity("https://jsonplaceholder.typicode.com/users/%s/posts".formatted(id), PostsDTO[].class);
    }

    @Operation(summary = "Create new User")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "new user", useReturnTypeSchema = true,
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UsersDTO.class))}),
            @ApiResponse(responseCode = "403", description = "You don't have permission for this Endpoint")})
    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody UsersDTO usersDTO) {
        UsersDTO response = restTemplate.postForEntity("https://jsonplaceholder.typicode.com/users", usersDTO, UsersDTO.class).getBody();
        return response != null ?
                ResponseEntity
                        .ok(response) :
                ResponseEntity
                        .badRequest()
                        .build();
    }

    @Operation(summary = "Create new user album by ID ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "new user album", useReturnTypeSchema = true,
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = AlbumDTO.class))}),
            @ApiResponse(responseCode = "403", description = "You don't have permission for this Endpoint")})
    @PostMapping("/{id}/albums")
    public ResponseEntity<?> createUserAlbum(@PathVariable(name = "id") String id, @RequestBody AlbumDTO albumDTO) {
        ResponseEntity<AlbumDTO> response = restTemplate.postForEntity("https://jsonplaceholder.typicode.com/users/%s/albums".formatted(id), albumDTO, AlbumDTO.class);
        return response != null ?
                ResponseEntity
                        .ok(response.getBody()) :
                ResponseEntity
                        .badRequest()
                        .build();
    }

    @Operation(summary = "Create new user posts by ID ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "new user post", useReturnTypeSchema = true,
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = PostsDTO.class))}),
            @ApiResponse(responseCode = "403", description = "You don't have permission for this Endpoint")})
    @PostMapping("/{id}/posts")
    public ResponseEntity<?> createUserPost(@PathVariable(name = "id") String id, @RequestBody PostsDTO postsDTO) {
        ResponseEntity<PostsDTO> response = restTemplate.postForEntity("https://jsonplaceholder.typicode.com/users/%s/posts".formatted(id), postsDTO, PostsDTO.class);
        return response != null ?
                ResponseEntity
                        .ok(response.getBody()) :
                ResponseEntity
                        .badRequest()
                        .build();
    }

    @Operation(summary = "Update user by ID ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "updated user", useReturnTypeSchema = true,
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = PostsDTO.class))}),
            @ApiResponse(responseCode = "403", description = "You don't have permission for this Endpoint")})
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable(name = "id") String id, @RequestBody UsersDTO usersDTO) {
        return restTemplate.exchange("https://jsonplaceholder.typicode.com/users/%s".formatted(id), HttpMethod.PUT, new HttpEntity<>(usersDTO), UsersDTO.class);
    }

    @Operation(summary = "Patched user by ID ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "updated user", useReturnTypeSchema = true,
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UsersDTO.class))}),
            @ApiResponse(responseCode = "403", description = "You don't have permission for this Endpoint")})
    @PatchMapping("/{id}")
    public ResponseEntity<?> patchUser(@PathVariable(name = "id") String id, @RequestBody() Object param) {
        return restTemplate.exchange("https://jsonplaceholder.typicode.com/users/%s".formatted(id), HttpMethod.PATCH, new HttpEntity<>(param), UsersDTO.class);
    }

    @Operation(summary = "Delete user by ID ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "", useReturnTypeSchema = true,
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "403", description = "You don't have permission for this Endpoint")})
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable(name = "id") String id) {
        return restTemplate.exchange("https://jsonplaceholder.typicode.com/users/%s".formatted(id), HttpMethod.DELETE, null, String.class);
    }


}
