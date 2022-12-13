package com.digiboy.platform.user.web.resources;

import com.digiboy.platform.user.api.UserService;
import com.digiboy.platform.user.dto.UserDTO;
import com.digiboy.platform.user.generated.v1.api.UsersApi;
import com.digiboy.platform.user.generated.v1.model.CreateUserRequest;
import com.digiboy.platform.user.generated.v1.model.CreateUserResponse;
import com.digiboy.platform.user.generated.v1.model.Credential;
import com.digiboy.platform.user.generated.v1.model.User;
import com.digiboy.platform.user.web.mapper.CreateUserMapper;
import com.digiboy.platform.user.web.mapper.UserModelMapper;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class UsersResource implements UsersApi {

    private final Logger logger;
    private final UserService service;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private UserModelMapper userModelMapper;

    @Autowired
    private CreateUserMapper createUserMapper;

    public UsersResource(Logger logger, UserService service, PasswordEncoder passwordEncoder) {
        this.logger = logger;
        this.service = service;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public ResponseEntity<User> findUser(String userId) {
        return UsersApi.super.findUser(userId);
    }

    @Override
    public ResponseEntity<List<Credential>> getUserCredentioals(UUID userId) {
        return UsersApi.super.getUserCredentioals(userId);
    }

    @Override
    public ResponseEntity<List<User>> findUsers(String username, String email) {
        return ResponseEntity.ok(userModelMapper.map(
                service.findAll()));
    }

    @Override
    public ResponseEntity<CreateUserResponse> saveUser(CreateUserRequest createUserRequest) {
        UserDTO user = createUserMapper.map(createUserRequest);
        CreateUserResponse response = createUserMapper.map(service.save(user));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
