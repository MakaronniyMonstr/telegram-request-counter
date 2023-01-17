package com.vesko.dynamicconfiguration.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class QueueDelayDto {
    @NotNull
    @PositiveOrZero
    @JsonProperty("new_delay")
    private Long delay;
}
