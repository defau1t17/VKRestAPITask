package org.vktask.vkrestapitask.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.vktask.vkrestapitask.dto.AlbumDTO;
import org.vktask.vkrestapitask.dto.PhotoDTO;

@RestController
@RequestMapping("/api/v1/albums")
public class AlbumsRestApiController {

    private final HttpClient httpClient = HttpClientBuilder.create().build();
    private final RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory(httpClient));

    @GetMapping
    private ResponseEntity<?> getUsers(HttpServletRequest httpServletRequest) {
        return httpServletRequest.getQueryString() == null || httpServletRequest.getQueryString().isEmpty() ?
                restTemplate.getForEntity("https://jsonplaceholder.typicode.com/albums", String.class) :
                restTemplate.getForEntity("https://jsonplaceholder.typicode.com/albums?%s".formatted(
                        httpServletRequest.getQueryString().replaceAll("%20", " ")), String.class);
    }

    @GetMapping("/{id}/photos")
    public ResponseEntity<?> getUserAlbums(@PathVariable(name = "id") String id) {
        return restTemplate.getForEntity("https://jsonplaceholder.typicode.com/albums/%s/photos".formatted(id), String.class);
    }

    @PostMapping
    public ResponseEntity<?> createAlbum(@RequestBody AlbumDTO albumDTO) {
        return restTemplate.postForEntity("https://jsonplaceholder.typicode.com/albums", albumDTO, String.class);
    }

    @PostMapping("/{id}/photos")
    public ResponseEntity<?> createPhotoInAlbum(@PathVariable(name = "id") String id, @RequestBody PhotoDTO photoDTO) {
        return restTemplate.postForEntity("https://jsonplaceholder.typicode.com/users/%s/photos".formatted(id), photoDTO, String.class);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAlbum(@PathVariable(name = "id") String id, @RequestBody AlbumDTO albumDTO) {
        return restTemplate.exchange("https://jsonplaceholder.typicode.com/albums/%s".formatted(id), HttpMethod.PUT, new HttpEntity<>(albumDTO), String.class);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> patchAlbum(@PathVariable(name = "id") String id, @RequestBody() Object param) {
        return restTemplate.exchange("https://jsonplaceholder.typicode.com/albums/%s".formatted(id), HttpMethod.PATCH, new HttpEntity<>(param), String.class);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAlbum(@PathVariable(name = "id") String id) {
        return restTemplate.exchange("https://jsonplaceholder.typicode.com/albums/%s".formatted(id), HttpMethod.DELETE, null, String.class);
    }

}
