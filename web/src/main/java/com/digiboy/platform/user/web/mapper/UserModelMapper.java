package com.digiboy.platform.user.web.mapper;

import com.digiboy.platform.user.dto.UserDTO;
import com.digiboy.platform.user.generated.v1.model.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface UserModelMapper {
    User map(UserDTO dto);

    UserDTO map(User user);

    List<User> map(List<UserDTO> users);
}
