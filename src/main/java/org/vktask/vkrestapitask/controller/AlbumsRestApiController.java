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
import org.vktask.vkrestapitask.dto.AlbumDTO;
import org.vktask.vkrestapitask.dto.PhotoDTO;

@Tag(name = "Albums Proxy Endpoint")
@RestController
@RequestMapping("/api/v1/albums")
public class AlbumsRestApiController {

    private final HttpClient httpClient = HttpClientBuilder.create().build();
    private final RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory(httpClient));

    @Operation(summary = "All Albums")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "all existing albums", useReturnTypeSchema = true,
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = AlbumDTO.class))}),
            @ApiResponse(responseCode = "403", description = "You don't have permission for this Endpoint")})
    @GetMapping
    @Cacheable("albums")
    public ResponseEntity<?> getAlbums(HttpServletRequest httpServletRequest) {
        return httpServletRequest.getQueryString() == null || httpServletRequest.getQueryString().isEmpty() ?
                restTemplate.getForEntity("https://jsonplaceholder.typicode.com/albums", AlbumDTO[].class) :
                restTemplate.getForEntity("https://jsonplaceholder.typicode.com/albums?%s".formatted(
                        httpServletRequest.getQueryString().replaceAll("%20", " ")), AlbumDTO[].class);
    }

    @Operation(summary = "Album by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "get album by ID", useReturnTypeSchema = true,
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = AlbumDTO.class))}),
            @ApiResponse(responseCode = "403", description = "You don't have permission for this Endpoint")})
    @GetMapping("/{id}")
    @Cacheable("albums")
    public ResponseEntity<?> getAlbumByID(@PathVariable(name = "id") String id, HttpServletRequest httpServletRequest) {
        return restTemplate.getForEntity("https://jsonplaceholder.typicode.com/albums/%s".formatted(id), AlbumDTO.class);
    }

    @Operation(summary = "All Photos in album")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "photos", useReturnTypeSchema = true,
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = PhotoDTO.class))}),
            @ApiResponse(responseCode = "403", description = "You don't have permission for this Endpoint")})
    @GetMapping("/{id}/photos")
    @Cacheable("albumPhotos")
    public ResponseEntity<?> getAlbumPhotos(@PathVariable(name = "id") String id) {
        return restTemplate.getForEntity("https://jsonplaceholder.typicode.com/albums/%s/photos".formatted(id), PhotoDTO[].class);
    }

    @Operation(summary = "Create Album")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "new album", useReturnTypeSchema = true,
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = AlbumDTO.class))}),
            @ApiResponse(responseCode = "403", description = "You don't have permission for this Endpoint")})
    @PostMapping
    public ResponseEntity<?> createAlbum(@RequestBody AlbumDTO albumDTO) {
        ResponseEntity<AlbumDTO> response = restTemplate.postForEntity("https://jsonplaceholder.typicode.com/albums", albumDTO, AlbumDTO.class);
        return response != null ?
                ResponseEntity
                        .ok(response.getBody()) :
                ResponseEntity
                        .badRequest()
                        .build();
    }

    @Operation(summary = "Create photo in Album")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "new photo", useReturnTypeSchema = true,
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = PhotoDTO.class))}),
            @ApiResponse(responseCode = "403", description = "You don't have permission for this Endpoint")})
    @PostMapping("/{id}/photos")
    public ResponseEntity<?> createPhotoInAlbum(@PathVariable(name = "id") String id, @RequestBody PhotoDTO photoDTO) {
        ResponseEntity<PhotoDTO> response = restTemplate.postForEntity("https://jsonplaceholder.typicode.com/users/%s/photos".formatted(id), photoDTO, PhotoDTO.class);
        return response != null ?
                ResponseEntity
                        .ok(response.getBody()) :
                ResponseEntity
                        .badRequest()
                        .build();
    }

    @Operation(summary = "Update Album")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "updated album", useReturnTypeSchema = true,
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = AlbumDTO.class))}),
            @ApiResponse(responseCode = "403", description = "You don't have permission for this Endpoint")})
    @PutMapping("/{id}")
    public ResponseEntity<?> updateAlbum(@PathVariable(name = "id") String id, @RequestBody AlbumDTO albumDTO) {
        return restTemplate.exchange("https://jsonplaceholder.typicode.com/albums/%s".formatted(id), HttpMethod.PUT, new HttpEntity<>(albumDTO), AlbumDTO.class);
    }

    @Operation(summary = "Patch Album")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "patched album", useReturnTypeSchema = true,
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = AlbumDTO.class))}),
            @ApiResponse(responseCode = "403", description = "You don't have permission for this Endpoint")})
    @PatchMapping("/{id}")
    public ResponseEntity<?> patchAlbum(@PathVariable(name = "id") String id, @RequestBody() Object param) {
        return restTemplate.exchange("https://jsonplaceholder.typicode.com/albums/%s".formatted(id), HttpMethod.PATCH, new HttpEntity<>(param), AlbumDTO.class);
    }

    @Operation(summary = "Delete Album")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "", useReturnTypeSchema = true,
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "403", description = "You don't have permission for this Endpoint")})
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAlbum(@PathVariable(name = "id") String id) {
        return restTemplate.exchange("https://jsonplaceholder.typicode.com/albums/%s".formatted(id), HttpMethod.DELETE, null, String.class);
    }

}
