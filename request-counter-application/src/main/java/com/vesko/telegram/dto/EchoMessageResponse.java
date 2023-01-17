package com.vesko.telegram.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EchoMessageResponse {
    @JsonProperty("is_ok")
    private Boolean isOk;
    @JsonProperty("message_number")
    private Long messageNumber;
}
