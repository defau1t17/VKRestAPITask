package org.vktask.vkrestapitask.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.vktask.vkrestapitask.entity.Audit;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuditService {

    private final AuditRepository auditRepository;

    public Audit save() {
        return auditRepository.save(new Audit());
    }

    public List<Audit> findAll() {
        return auditRepository.findAll();
    }
}
