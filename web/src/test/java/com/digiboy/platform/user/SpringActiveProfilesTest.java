package com.digiboy.platform.user;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ActiveProfiles(profiles = {"test"})
class SpringActiveProfilesTest {

    @Autowired
    Environment env;

    @Test
    void activeProfilesInTestsNotRelatedToSpringActiveProfilesEnvironment() {
        assertThat(env.getActiveProfiles()).hasSize(1);
        assertThat(env.getActiveProfiles()).containsExactly("test");
    }
}
