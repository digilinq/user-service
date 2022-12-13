package com.digiboy.platform.user.web.strategy;

import com.digiboy.platform.user.generated.v1.model.User;

public interface FindStrategy {
    User findUser(String userId);

    FindUserMethod getMethod();
}
