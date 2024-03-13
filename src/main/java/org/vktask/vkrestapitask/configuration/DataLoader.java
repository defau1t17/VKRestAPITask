package org.vktask.vkrestapitask.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.vktask.vkrestapitask.entity.AuthToken;
import org.vktask.vkrestapitask.entity.Role;
import org.vktask.vkrestapitask.entity.User;
import org.vktask.vkrestapitask.service.TokenRepository;
import org.vktask.vkrestapitask.service.UserRepository;
import org.vktask.vkrestapitask.service.UserService;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DataLoader implements ApplicationRunner {

    private final UserRepository userRepository;

    private final TokenRepository tokenRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<User> users = List.of(new User(0, "admin", "admin", Role.ROLE_ADMIN, AuthToken.tokenFabric(tokenRepository)),
                new User(0, "users", "users", Role.ROLE_USERS, AuthToken.tokenFabric(tokenRepository)),
                new User(0, "posts", "posts", Role.ROLE_POSTS, AuthToken.tokenFabric(tokenRepository)),
                new User(0, "albums", "albums", Role.ROLE_ALBUMS, AuthToken.tokenFabric(tokenRepository)));
        userRepository.saveAll(users);

    }
}
