package com.vesko.userinfo.service;

import com.vesko.application.exception.ResourceNotFoundException;
import com.vesko.userinfo.dto.UserInfoDto;
import com.vesko.userinfo.exception.UserAlreadyExistsException;

public interface UserInfoService {
    UserInfoDto findBy(String username) throws ResourceNotFoundException;

    UserInfoDto getAndIncrementMessageCounterBy(String username) throws ResourceNotFoundException;

    void createUserInfo(String chatId, String username) throws UserAlreadyExistsException;
}
