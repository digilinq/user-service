package com.digiboy.platform.user.service;

import com.digiboy.platform.user.api.UserService;
import com.digiboy.platform.user.dto.UserDTO;
import com.digiboy.platform.user.exception.UserNotFoundException;
import com.digiboy.platform.user.exception.UsernameAlreadyExistsException;
import com.digiboy.platform.user.mapper.UserMapper;
import com.digiboy.platform.user.repository.UserRepository;
import com.digiboy.platform.user.specification.SpecificationBuilder;
import com.digiboy.platform.user.to.User;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.digiboy.platform.user.specification.UserSpecification.*;

@Service
public class UserServiceImpl extends ServiceBase implements UserService {

    private final Logger logger;

    @Autowired
    private UserRepository repository;

    @Autowired
    private UserMapper mapper;

    public UserServiceImpl(Logger logger) {
        this.logger = logger;
    }

    public List<UserDTO> findAll() {
        return mapper.map(repository.findAll());
    }

    @Override
    public Page<UserDTO> findAll(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::map);
    }

    @Override
    public UserDTO findByEmail(String email) {
        return Optional.ofNullable(repository.findByEmail(email)).map(mapper::map)
                .orElseThrow(UserNotFoundException::new);
    }

    @Override
    public UserDTO findByUsername(String username) {
        return mapper.map(repository.findByUsername(username));
    }

    @Override
    public List<UserDTO> findByEmailAndUsername(String email, String username) {
        List<Specification<User>> specifications = new ArrayList<>();

        Optional.ofNullable(email).ifPresent(e -> specifications.add(emailEqualsIgnoreCase(e)));
        Optional.ofNullable(username).ifPresent(u -> specifications.add(usernameEqualsIgnoreCase(u)));

        return mapper.map(repository.findAll(SpecificationBuilder.build(specifications)));
    }

    @Override
    public UserDTO save(UserDTO userDTO) {
        User user = mapper.map(userDTO);

        final String username = Optional.ofNullable(user.getUsername())
                .map(String::toLowerCase).orElseThrow(IllegalArgumentException::new);

        if (username.isBlank())
            throw new IllegalArgumentException("Username can not be empty");

        user.setUsername(username);

        if (repository.existsByUsername(username))
            throw new UsernameAlreadyExistsException(username + "already exists");

        return mapper.map(repository.save(user));
    }

    @Override
    public boolean exists(String username) {
        return repository.existsByUsername(username.toLowerCase());
    }
}
