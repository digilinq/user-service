package com.digiboy.platform.user.web.strategy;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        FindUserContext.class,
        FindUserByEmail.class,
        FindUserByUsername.class,
        FindUserByUserId.class
})
class FindStrategyTest {

    @Autowired
    Map<FindUserMethod, FindStrategy> strategyMap;

    @Test
    void shouldInject() {
        assertNotNull(strategyMap);
    }
}