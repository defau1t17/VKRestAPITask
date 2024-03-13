package org.vktask.vkrestapitask.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.vktask.vkrestapitask.entity.Audit;
import org.vktask.vkrestapitask.service.AuditService;

@Tag(name = "Audit Endpoint")
@RestController
@RequestMapping("/api/v1/audit")
@RequiredArgsConstructor
public class AuditRestApiController {

    private final AuditService auditService;

    @Operation(summary = "get audit")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "all existing logs", useReturnTypeSchema = true
            , content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Audit.class))})})
    @GetMapping
    public ResponseEntity<?> getAuditInformation() {
        return ResponseEntity.ok(auditService.findAll());
    }
}
