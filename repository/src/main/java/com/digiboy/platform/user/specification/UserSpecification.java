package com.digiboy.platform.user.specification;

import com.digiboy.platform.user.to.User;
import com.digiboy.platform.user.to.User_;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecification {
    public static Specification<User> emailEqualsIgnoreCase(String email) {
        return (root, query, builder) -> builder.equal(builder.lower(root.get(User_.email)), email.toLowerCase());
    }

    public static Specification<User> usernameEqualsIgnoreCase(String username) {
        return (root, query, builder) -> builder.equal(builder.lower(root.get(User_.username)), username.toLowerCase());
    }
}
