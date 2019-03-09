package ru.javawebinar.topjava.service;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;

import java.util.List;

import static ru.javawebinar.topjava.MealTestData.MEALS;
import static ru.javawebinar.topjava.UserTestData.USER;
import static ru.javawebinar.topjava.UserTestData.USER_ID;
import static ru.javawebinar.topjava.UserTestData.assertMatch;

@ActiveProfiles(Profiles.DATAJPA)
public class DataJpaUserServiceTest extends UserServiceTest {
    @BeforeClass
    public static void resetResults() {
        results = new StringBuilder();
    }

    @Test
    public void getUserByIdWithMeals() {
        User user = service.getUserByIdWithMeals(USER_ID);
        User expected = USER;
        assertMatch(user, expected);
        List<Meal> meals = user.getMeals();
        MealTestData.assertMatch(meals, MEALS);
    }
}
