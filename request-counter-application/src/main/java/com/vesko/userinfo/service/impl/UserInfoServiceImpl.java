package com.vesko.userinfo.service.impl;

import com.vesko.application.exception.ResourceNotFoundException;
import com.vesko.userinfo.dto.UserInfoDto;
import com.vesko.userinfo.entity.UserInfo;
import com.vesko.userinfo.exception.UserAlreadyExistsException;
import com.vesko.userinfo.mapper.UserInfoMapper;
import com.vesko.userinfo.repositoty.UserInfoRepository;
import com.vesko.userinfo.service.UserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.util.Assert.notNull;

@Service
@RequiredArgsConstructor
public class UserInfoServiceImpl implements UserInfoService {
    private final UserInfoRepository userInfoRepository;
    private final UserInfoMapper userInfoMapper;

    @Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true)
    @Override
    public UserInfoDto findBy(String username) throws ResourceNotFoundException {
        var userInfo = userInfoRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(username));
        return userInfoMapper.toDto(userInfo);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Override
    public UserInfoDto getAndIncrementMessageCounterBy(String username) throws ResourceNotFoundException {
        notNull(username, "Username must not be null");

        UserInfo userInfo = userInfoRepository.findByUsernameWithWriteLock(username)
                .orElseThrow(() -> new ResourceNotFoundException("telegramId", username));
        Long messageCount = userInfo.getMessageCount();
        userInfo.setMessageCount(messageCount + 1);

        return userInfoMapper.toDto(userInfo);
    }

    @Transactional
    @Override
    public void createUserInfo(String chatId, String username) throws UserAlreadyExistsException {
        notNull(chatId, "ChatID must not be null");
        notNull(username, "Username must not be null");

        if (userInfoRepository.existsByChatId(chatId)) {
            throw new UserAlreadyExistsException();
        }
        var userInfo = new UserInfo();
        userInfo.setChatId(chatId);
        userInfo.setUsername(username);

        userInfoRepository.save(userInfo);
    }
}
