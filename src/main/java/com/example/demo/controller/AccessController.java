package com.example.demo.controller;

import com.example.demo.model.response._MessageSuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "5. Accesses")
@RequestMapping("/api/v1/accesses")
@SecurityRequirement(name = "authorize")
public class AccessController {

    @GetMapping("/admin")
    @Operation(summary = "Admin access only")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<_MessageSuccessResponse> adminAccess() {
        return ResponseEntity.ok(new _MessageSuccessResponse("Access successful"));
    }

    @GetMapping("/user")
    @Operation(summary = "User access only")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<_MessageSuccessResponse> userAccess() {
        return ResponseEntity.ok(new _MessageSuccessResponse("Access successful"));
    }
}
