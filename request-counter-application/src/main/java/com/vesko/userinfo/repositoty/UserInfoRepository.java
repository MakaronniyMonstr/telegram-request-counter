package com.vesko.userinfo.repositoty;

import com.vesko.userinfo.entity.UserInfo;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select u from UserInfo u where u.username = :username")
    Optional<UserInfo> findByUsernameWithWriteLock(String username);

    boolean existsByChatId(String chatId);

    Optional<UserInfo> findByUsername(String username);
}