package com.digiboy.platform.user.web.mapper;

import com.digiboy.platform.user.dto.UserDTO;
import com.digiboy.platform.user.generated.v1.model.UserInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface UserInfoMapper {
    @Mapping(target = "password", source = "encryptedPassword")
    UserInfo map(UserDTO dto);
}
