package com.vesko.userinfo.mapper;

import com.vesko.userinfo.dto.UserInfoDto;
import com.vesko.userinfo.entity.UserInfo;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface UserInfoMapper {
    UserInfo toEntity(UserInfoDto userInfoDto);

    UserInfoDto toDto(UserInfo userInfo);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    UserInfo partialUpdate(UserInfoDto userInfoDto, @MappingTarget UserInfo userInfo);
}