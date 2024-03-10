package org.vktask.vkrestapitask.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.vktask.vkrestapitask.entity.AuthToken;

public interface TokenRepository extends JpaRepository<AuthToken, Integer> {
}
