package com.digiboy.platform.user.web.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class FindUserContext {
    @Autowired
    private List<FindStrategy> findUserStrategies;

    @Bean
    public Map<FindUserMethod, FindStrategy> getStrategyMap(){
        Map<FindUserMethod, FindStrategy> strategyMap = new HashMap<>();
        findUserStrategies.forEach(strategy->{
            strategyMap.put(strategy.getMethod(), strategy);
        });
        return strategyMap;
    }
}
