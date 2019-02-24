package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int USER_ID = START_SEQ;

    public static final Meal MEAL_USER_1 = new Meal(100002, LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500);
    public static final Meal MEAL_USER_2 = new Meal(100003, LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000);
    public static final Meal MEAL_USER_3 = new Meal(100004, LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500);
    public static final Meal MEAL_USER_4 = new Meal(100005, LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000);
    public static final Meal MEAL_USER_5 = new Meal(100006, LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500);
    public static final Meal MEAL_USER_6 = new Meal(100007, LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510);
    public static final Meal MEAL_ADMIN_1 = new Meal(100008, LocalDateTime.of(2019, Month.FEBRUARY, 16, 10, 0), "Бутерброды", 1000);
    public static final Meal MEAL_ADMIN_2 = new Meal(100009, LocalDateTime.of(2019, Month.FEBRUARY, 16, 13, 0), "Котлеты", 510);
    public static final Meal MEAL_ADMIN_3 = new Meal(100010, LocalDateTime.of(2019, Month.FEBRUARY, 16, 20, 0), "Суп", 500);
    public static final Meal MEAL_ADMIN_4 = new Meal(100011, LocalDateTime.of(2019, Month.FEBRUARY, 17, 10, 0), "Яичница", 5000);
    public static final Meal MEAL_ADMIN_5 = new Meal(100012, LocalDateTime.of(2019, Month.FEBRUARY, 17, 13, 0), "Макароны", 500);
    public static final Meal MEAL_ADMIN_6 = new Meal(100013, LocalDateTime.of(2019, Month.FEBRUARY, 17, 20, 0), "Салат", 1000);

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualToComparingFieldByField(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingFieldByFieldElementComparator().isEqualTo(expected);
    }
}
