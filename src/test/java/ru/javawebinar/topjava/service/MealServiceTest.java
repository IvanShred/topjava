package ru.javawebinar.topjava.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

import static ru.javawebinar.topjava.MealTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-general.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void get() {
        Meal meal = service.get(MEAL_USER_1.getId(), USER_ID);
        assertMatch(MEAL_USER_1, meal);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() throws Exception {
        service.get(MEAL_ADMIN_1.getId(), USER_ID);
    }

    @Test
    public void delete() {
        service.delete(MEAL_USER_1.getId(), USER_ID);
        assertMatch(service.getAll(USER_ID), MEAL_USER_6, MEAL_USER_5, MEAL_USER_4, MEAL_USER_3, MEAL_USER_2);

    }

    @Test(expected = NotFoundException.class)
    public void deletedNotFound() throws Exception {
        service.delete(MEAL_ADMIN_1.getId(), USER_ID);
    }

    @Test
    public void getBetweenDates() {
        List<Meal> meals = service.getBetweenDates(LocalDate.of(2015, Month.MAY, 31),
                LocalDate.of(2015, Month.JUNE, 1), USER_ID);
        assertEquals(3, meals.size());
    }

    @Test
    public void getBetweenDateTimes() {
        List<Meal> meals = service.getBetweenDateTimes(LocalDateTime.of(2015, Month.MAY, 31, 12, 0),
                LocalDateTime.MAX, USER_ID);
        Assert.assertEquals(2, meals.size());
        List<Meal> sortedMeals = Arrays.asList(MEAL_USER_5, MEAL_USER_6);
        sortedMeals.sort((o1, o2) -> -o1.getDateTime().compareTo(o2.getDateTime()));
        assertMatch(sortedMeals, meals);
    }

    @Test
    public void getAll() {
        List<Meal> meals = service.getAll(USER_ID);
        Assert.assertEquals(6, meals.size());
        List<Meal> sortedMeals = Arrays.asList(MEAL_USER_1, MEAL_USER_2, MEAL_USER_3, MEAL_USER_4, MEAL_USER_5, MEAL_USER_6);
        sortedMeals.sort((o1, o2) -> -o1.getDateTime().compareTo(o2.getDateTime()));
        assertMatch(sortedMeals, meals);
    }

    @Test
    public void update() {
        Meal updated = new Meal(MEAL_USER_1);
        updated.setDescription("Полдник");
        updated.setCalories(330);
        service.update(updated, USER_ID);
        assertMatch(service.get(MEAL_USER_1.getId(), USER_ID), updated);
    }

    @Test(expected = NotFoundException.class)
    public void updatedNotFound() {
        service.update(MEAL_ADMIN_1, USER_ID);
    }

    @Test
    public void create() {
        Meal newMeal = new Meal(LocalDateTime.of(2019, Month.FEBRUARY, 18, 20, 0), "Гречка", 600);
        Meal created = service.create(newMeal, USER_ID);
        service.create(newMeal, USER_ID);
        newMeal.setId(created.getId());
        assertMatch(service.getAll(USER_ID), newMeal, MEAL_USER_6, MEAL_USER_5, MEAL_USER_4, MEAL_USER_3, MEAL_USER_2, MEAL_USER_1);
    }

    @Test(expected = DataAccessException.class)
    public void duplicateDateTime() throws Exception {
        service.create(new Meal(null, LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Ланч", 400), USER_ID);
    }
}