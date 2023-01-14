package com.vesko.userinfo.service.impl;

import com.vesko.application.exception.ResourceNotFoundException;
import com.vesko.userinfo.dto.UserInfoDto;
import com.vesko.userinfo.entity.UserInfo;
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
    public UserInfoDto findBy(String telegramId) {
        return null;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Override
    public UserInfoDto getAndIncreaseMessageCounter(String telegramId) {
        notNull(telegramId, "TelegramId must not be null");

        UserInfo userInfo = userInfoRepository.findByChatIdWithWriteLock(telegramId)
                .orElseThrow(() -> new ResourceNotFoundException("telegramId", telegramId));
        Long messageCount = userInfo.getMessageCount();
        userInfo.setMessageCount(messageCount + 1);

        return userInfoMapper.toDto(userInfo);
    }
}
