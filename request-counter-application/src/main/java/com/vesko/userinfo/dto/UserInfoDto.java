package com.vesko.userinfo.dto;

import java.io.Serializable;

public record UserInfoDto(Long messageCount, String chatId, String username) implements Serializable {
}