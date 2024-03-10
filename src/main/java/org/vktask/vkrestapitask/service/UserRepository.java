package org.vktask.vkrestapitask.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.vktask.vkrestapitask.entity.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findUserByUsername(String username);

    @Query(value = "SELECT user FROM User user WHERE user.authToken.token = ?1 ")
    Optional<User> findUserByToken(String token);


}
