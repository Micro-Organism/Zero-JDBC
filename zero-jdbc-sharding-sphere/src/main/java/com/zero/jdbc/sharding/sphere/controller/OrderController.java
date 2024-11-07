package com.zero.jdbc.sharding.sphere.controller;

import com.zero.jdbc.sharding.sphere.domain.entity.SystemUserEntity;
import com.zero.jdbc.sharding.sphere.service.SystemUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final SystemUserService systemUserService;

    public OrderController(SystemUserService systemUserService) {
        this.systemUserService = systemUserService;
    }

    @PostMapping
    public ResponseEntity<SystemUserEntity> createOrder(@RequestBody SystemUserEntity systemUserEntity) {
        return ResponseEntity.ok(systemUserService.saveSystemUser(systemUserEntity));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SystemUserEntity> getOrder(@PathVariable String id) {
        return ResponseEntity.ok(systemUserService.getSystemUser(id));
    }
}
