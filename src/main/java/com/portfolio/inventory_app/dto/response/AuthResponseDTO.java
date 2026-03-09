package com.portfolio.inventory_app.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AuthResponseDTO(
        @JsonProperty("access_token")
        String accessToken,
        @JsonProperty("refresh_token")
        String refreshToken
) {}