package com.vesko.userinfo.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserInfoDto implements Serializable {
    private final Long messageCount;
    private final String telegramId;
}