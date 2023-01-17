package com.vesko.telegram.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EchoMessageRequest {
    @NotNull
    private String message;
    @JsonProperty("user_sender")
    @NotNull
    private String targetUsername;
}
