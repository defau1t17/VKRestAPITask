package org.vktask.vkrestapitask.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.vktask.vkrestapitask.entity.AuthToken;
import org.vktask.vkrestapitask.entity.Role;
import org.vktask.vkrestapitask.entity.User;

import java.util.Arrays;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService extends EntityDAO<User, Integer> {

    private final UserRepository userRepository;

    private final TokenRepository tokenRepository;

    @Override
    public User save(User entity) {
        entity.setAuthToken(AuthToken.tokenFabric(tokenRepository));
        entity.setRole(Arrays
                .stream(Role.values())
                .anyMatch(match -> match.equals(entity.getRole())) ?
                entity.getRole() :
                Role.ROLE_USERS);
        return userRepository.save(entity);
    }

    @Override
    public User update(User entity) {
        return userRepository.save(entity);
    }

    @Override
    public void delete(User entity) {
        userRepository.delete(entity);
    }

    @Override
    public Optional<User> findByID(Integer id) {
        return userRepository.findById(id);
    }

    @Override
    public boolean validateBeforeSave(User entity) {
        return findUserByUsername(entity.getUsername()).isEmpty();
    }

    public Optional<User> findUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    public Optional<User> findUserByToken(String token) {
        return userRepository.findUserByToken(token);
    }

}
