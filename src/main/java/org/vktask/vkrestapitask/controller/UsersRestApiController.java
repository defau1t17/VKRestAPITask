package org.vktask.vkrestapitask.controller;

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
import org.vktask.vkrestapitask.service.AuditService;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UsersRestApiController {
    private final HttpClient httpClient = HttpClientBuilder.create().build();
    private final RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory(httpClient));

    private final AuditService auditService;

    @GetMapping
    @Cacheable("users")
    public ResponseEntity<?> getUsers(HttpServletRequest httpServletRequest) {
        return httpServletRequest.getQueryString() == null || httpServletRequest.getQueryString().isEmpty() ?
                restTemplate.getForEntity("https://jsonplaceholder.typicode.com/users", String.class) :
                restTemplate.getForEntity("https://jsonplaceholder.typicode.com/users?%s".formatted(
                        httpServletRequest.getQueryString().replaceAll("%20", " ")), String.class);
    }

    @GetMapping("/{id}/albums")
    @Cacheable("userAlbums")
    public ResponseEntity<?> getUserAlbums(@PathVariable(name = "id") String id) {
        return restTemplate.getForEntity("https://jsonplaceholder.typicode.com/users/%s/albums".formatted(id), String.class);
    }

    @GetMapping("/{id}/posts")
    @Cacheable("userPosts")
    public ResponseEntity<?> getUserPosts(@PathVariable(name = "id") String id) {
        return restTemplate.getForEntity("https://jsonplaceholder.typicode.com/users/%s/posts".formatted(id), String.class);
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody UsersDTO usersDTO) {
        return restTemplate.postForEntity("https://jsonplaceholder.typicode.com/users", usersDTO, String.class);
    }

    @PostMapping("/{id}/albums")
    public ResponseEntity<?> createUserAlbum(@PathVariable(name = "id") String id, @RequestBody AlbumDTO albumDTO) {
        return restTemplate.postForEntity("https://jsonplaceholder.typicode.com/users/%s/albums".formatted(id), albumDTO, String.class);
    }

    @PostMapping("/{id}/posts")
    public ResponseEntity<?> createUserAlbum(@PathVariable(name = "id") String id, @RequestBody PostsDTO postsDTO) {
        return restTemplate.postForEntity("https://jsonplaceholder.typicode.com/users/%s/posts".formatted(id), postsDTO, String.class);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable(name = "id") String id, @RequestBody UsersDTO usersDTO) {
        return restTemplate.exchange("https://jsonplaceholder.typicode.com/users/%s".formatted(id), HttpMethod.PUT, new HttpEntity<>(usersDTO), String.class);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> patchUser(@PathVariable(name = "id") String id, @RequestBody() Object param) {
        return restTemplate.exchange("https://jsonplaceholder.typicode.com/users/%s".formatted(id), HttpMethod.PATCH, new HttpEntity<>(param), String.class);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@PathVariable(name = "id") String id) {
        return restTemplate.exchange("https://jsonplaceholder.typicode.com/users/%s".formatted(id), HttpMethod.DELETE, null, String.class);
    }


}
