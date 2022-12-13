package com.digiboy.platform.user.web.strategy;

import com.digiboy.platform.user.generated.v1.model.User;
import org.springframework.stereotype.Component;

@Component
public class FindUserByEmail implements FindStrategy {
    @Override
    public User findUser(String userId) {
        return new User();
    }

    @Override
    public FindUserMethod getMethod() {
        return FindUserMethod.EMAIL;
    }
}
