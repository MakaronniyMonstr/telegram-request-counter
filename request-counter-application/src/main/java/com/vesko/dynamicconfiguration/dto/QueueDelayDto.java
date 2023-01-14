package com.vesko.dynamicconfiguration.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class QueueDelayDto {
    @NotNull
    @JsonProperty("new_delay")
    private Long delay;
}
