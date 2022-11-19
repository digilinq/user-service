package com.digiboy.platform.user.api;

import com.digiboy.platform.user.dto.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    List<UserDTO> findAll();

    Page<UserDTO> findAll(Pageable pageable);

    UserDTO findByEmail(String email);

    UserDTO findByUsername(String username);

    List<UserDTO> findByEmailAndUsername(String email, String username);

    UserDTO save(UserDTO userDTO);

    boolean exists(String username);
}
