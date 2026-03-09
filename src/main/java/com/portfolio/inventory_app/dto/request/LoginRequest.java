package com.portfolio.inventory_app.dto.request;

public record LoginRequest(
        String email,
        String password
) {}