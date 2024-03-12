package org.vktask.vkrestapitask.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.vktask.vkrestapitask.entity.Audit;

public interface AuditRepository extends JpaRepository<Audit, String> {
}
