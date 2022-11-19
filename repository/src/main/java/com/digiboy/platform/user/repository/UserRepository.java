package com.digiboy.platform.user.repository;

import com.digiboy.platform.user.to.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID>, JpaSpecificationExecutor<User> {
    boolean existsByUsername(String username);

    User findByEmail(String email);

    User findByUsername(String username);
}
