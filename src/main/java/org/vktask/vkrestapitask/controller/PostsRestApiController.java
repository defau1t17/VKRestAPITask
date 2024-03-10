package org.vktask.vkrestapitask.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.vktask.vkrestapitask.dto.CommentDTO;
import org.vktask.vkrestapitask.dto.PostsDTO;

import static org.springframework.http.HttpMethod.DELETE;


@RestController
@RequestMapping("/api/v1/posts")
public class PostsRestApiController {

    private final HttpClient httpClient = HttpClientBuilder.create().build();
    private final RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory(httpClient));



    @GetMapping
    public ResponseEntity<?> getPosts(HttpServletRequest httpServletRequest) {
        return httpServletRequest.getQueryString() == null || httpServletRequest.getQueryString().isEmpty() ?
                restTemplate.getForEntity("https://jsonplaceholder.typicode.com/posts", String.class) :
                restTemplate.getForEntity("https://jsonplaceholder.typicode.com/posts?%s".formatted(
                        httpServletRequest.getQueryString().replaceAll("%20", " ")), String.class);
    }

    @GetMapping("/{id}/comments")
    public ResponseEntity<?> getCommendByPostID(@PathVariable(name = "id") String id) {
        return restTemplate.getForEntity("https://jsonplaceholder.typicode.com/posts/%s/comments".formatted(id), String.class);
    }

    @PostMapping
    public ResponseEntity<?> createPost(@RequestBody PostsDTO postsDTO) {
        return restTemplate.postForEntity("https://jsonplaceholder.typicode.com/posts", postsDTO, String.class);
    }

    @PostMapping("/{id}/comments")
    public ResponseEntity<?> createComment(@RequestBody CommentDTO commentDTO) {
        return restTemplate.postForEntity("https://jsonplaceholder.typicode.com/comments", commentDTO, String.class);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePost(@PathVariable(name = "id") String id, @RequestBody PostsDTO postsDTO) {
        return restTemplate.exchange("https://jsonplaceholder.typicode.com/posts/%s".formatted(id), HttpMethod.PUT, new HttpEntity<>(postsDTO), String.class);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> patchPost(@PathVariable(name = "id") String id, @RequestBody() Object param) {
        return restTemplate.exchange("https://jsonplaceholder.typicode.com/posts/%s".formatted(id), HttpMethod.PATCH, new HttpEntity<>(param), String.class);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@PathVariable(name = "id") String id) {
        return restTemplate.exchange("https://jsonplaceholder.typicode.com/posts/%s".formatted(id), HttpMethod.DELETE, null, String.class);
    }
}
