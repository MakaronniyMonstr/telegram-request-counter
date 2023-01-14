package com.vesko.userinfo.service;

import com.vesko.userinfo.dto.UserInfoDto;

public interface UserInfoService {
    UserInfoDto findBy(String telegramId);

    UserInfoDto getAndIncreaseMessageCounter(String telegramId);
}
