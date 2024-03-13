package org.vktask.vkrestapitask.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.vktask.vkrestapitask.dto.CommentDTO;
import org.vktask.vkrestapitask.dto.PostsDTO;


@Tag(name = "Posts Proxy Endpoint")
@RestController
@RequestMapping("/api/v1/posts")
public class PostsRestApiController {

    private final HttpClient httpClient = HttpClientBuilder.create().build();
    private final RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory(httpClient));

    @Operation(summary = "All Posts")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "all existing posts", useReturnTypeSchema = true,
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = PostsDTO.class))}),
            @ApiResponse(responseCode = "403", description = "You don't have permission for this Endpoint")})
    @GetMapping
    @Cacheable("posts")
    public ResponseEntity<?> getPosts(HttpServletRequest httpServletRequest) {
        return httpServletRequest.getQueryString() == null || httpServletRequest.getQueryString().isEmpty() ?
                restTemplate.getForEntity("https://jsonplaceholder.typicode.com/posts", PostsDTO[].class) :
                restTemplate.getForEntity("https://jsonplaceholder.typicode.com/posts?%s".formatted(
                        httpServletRequest.getQueryString().replaceAll("%20", " ")), PostsDTO[].class);
    }

    @Operation(summary = "Post by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "get post by id", useReturnTypeSchema = true,
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = PostsDTO.class))}),
            @ApiResponse(responseCode = "403", description = "You don't have permission for this Endpoint")})
    @GetMapping("/{id}")
    @Cacheable("posts")
    public ResponseEntity<?> getPostByID(@PathVariable(name = "id") String id) {
        return restTemplate.getForEntity("https://jsonplaceholder.typicode.com/posts/%s".formatted(id), PostsDTO.class);
    }

    @Operation(summary = "Post comment by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "comments under the post", useReturnTypeSchema = true,
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CommentDTO.class))}),
            @ApiResponse(responseCode = "403", description = "You don't have permission for this Endpoint")})
    @GetMapping("/{id}/comments")
    @Cacheable("postComments")
    public ResponseEntity<?> getCommentsByPostID(@PathVariable(name = "id") String id) {
        return restTemplate.getForEntity("https://jsonplaceholder.typicode.com/posts/%s/comments".formatted(id), CommentDTO[].class);
    }

    @Operation(summary = "New Post")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "new post", useReturnTypeSchema = true,
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = PostsDTO.class))}),
            @ApiResponse(responseCode = "403", description = "You don't have permission for this Endpoint")})
    @PostMapping
    public ResponseEntity<?> createPost(@RequestBody PostsDTO postsDTO) {
        ResponseEntity<PostsDTO> response = restTemplate.postForEntity("https://jsonplaceholder.typicode.com/posts", postsDTO, PostsDTO.class);
        return response != null ?
                ResponseEntity
                        .ok(response.getBody()) :
                ResponseEntity
                        .badRequest()
                        .build();
    }

    @Operation(summary = "New comment under the Post")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "new comment", useReturnTypeSchema = true,
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CommentDTO.class))}),
            @ApiResponse(responseCode = "403", description = "You don't have permission for this Endpoint")})
    @PostMapping("/{id}/comments")
    public ResponseEntity<?> createComment(@RequestBody CommentDTO commentDTO) {
        ResponseEntity<CommentDTO> response = restTemplate.postForEntity("https://jsonplaceholder.typicode.com/comments", commentDTO, CommentDTO.class);
        return response != null ?
                ResponseEntity
                        .ok(response.getBody()) :
                ResponseEntity
                        .badRequest()
                        .build();
    }

    @Operation(summary = "Update Post by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "updated post", useReturnTypeSchema = true,
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = PostsDTO.class))}),
            @ApiResponse(responseCode = "403", description = "You don't have permission for this Endpoint")})
    @PutMapping("/{id}")
    public ResponseEntity<?> updatePost(@PathVariable(name = "id") String id, @RequestBody PostsDTO postsDTO) {
        return restTemplate.exchange("https://jsonplaceholder.typicode.com/posts/%s".formatted(id), HttpMethod.PUT, new HttpEntity<>(postsDTO), PostsDTO.class);
    }

    @Operation(summary = "Patch Post by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "patched post", useReturnTypeSchema = true,
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = PostsDTO.class))}),
            @ApiResponse(responseCode = "403", description = "You don't have permission for this Endpoint")})
    @PatchMapping("/{id}")
    public ResponseEntity<?> patchPost(@PathVariable(name = "id") String id, @RequestBody() Object param) {
        return restTemplate.exchange("https://jsonplaceholder.typicode.com/posts/%s".formatted(id), HttpMethod.PATCH, new HttpEntity<>(param), PostsDTO.class);
    }

    @Operation(summary = "Delete Post by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "", useReturnTypeSchema = true,
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "403", description = "You don't have permission for this Endpoint")})
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@PathVariable(name = "id") String id) {
        return restTemplate.exchange("https://jsonplaceholder.typicode.com/posts/%s".formatted(id), HttpMethod.DELETE, null, String.class);
    }
}
