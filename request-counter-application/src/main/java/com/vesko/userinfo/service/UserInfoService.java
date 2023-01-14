package com.vesko.userinfo.service;

import com.vesko.userinfo.dto.UserInfoDto;
import com.vesko.userinfo.exception.UserAlreadyExistsException;

public interface UserInfoService {
    UserInfoDto findBy(String username);

    UserInfoDto getAndIncrementMessageCounterBy(String username);

    void createUserInfo(String chatId, String username) throws UserAlreadyExistsException;
}
