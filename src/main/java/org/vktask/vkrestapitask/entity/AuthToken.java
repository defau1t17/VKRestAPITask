package org.vktask.vkrestapitask.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.vktask.vkrestapitask.service.TokenRepository;

import java.util.UUID;

@Entity
@Data
public final class AuthToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String token;

    public static AuthToken tokenFabric(TokenRepository tokenRepository) {
        return tokenRepository.save(new AuthToken());
    }

    protected AuthToken() {
        this.id = 0;
        this.token = "" + UUID.randomUUID();
    }

}
