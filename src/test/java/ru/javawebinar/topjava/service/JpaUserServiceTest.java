package ru.javawebinar.topjava.service;

import org.junit.BeforeClass;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;

@ActiveProfiles(Profiles.JPA)
public class JpaUserServiceTest extends UserServiceTest {
    @BeforeClass
    public static void resetResults() {
        results = new StringBuilder();
    }
}
