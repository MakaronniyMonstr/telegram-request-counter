package com.vesko.userinfo.entity;

import com.vesko.application.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import java.util.Objects;

@Entity
@Table(name = "user_info")
@Getter
@Setter
@ToString
public class UserInfo extends BaseEntity {
    @Column(name = "message_count")
    private Long messageCount = 0L;

    @Column(name = "chat_id", unique = true, nullable = false)
    private String chatId;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        UserInfo userInfo = (UserInfo) o;
        return getId() != null && Objects.equals(getChatId(), userInfo.getChatId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getChatId());
    }
}
