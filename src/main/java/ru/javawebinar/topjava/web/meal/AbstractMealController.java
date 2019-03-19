package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class AbstractMealController {
    @Autowired
    private MealService service;

    private static final Logger log = LoggerFactory.getLogger(AbstractMealController.class);

    protected List<MealTo> getAllForController(int userId) {
        log.info("getAll for user {}", userId);
        return MealsUtil.getWithExcess(service.getAll(userId), SecurityUtil.authUserCaloriesPerDay());
    }

    protected void deleteForController(int id, int userId) {
        log.info("delete meal {} for user {}", id, userId);
        service.delete(id, userId);
    }

    protected Meal createForController(Meal meal, int userId) {
        log.info("create {} for user {}", meal, userId);
        return service.create(meal, userId);
    }

    protected void updateForController(Meal meal, int userId) {
        log.info("update {} for user {}", meal, userId);
        service.update(meal, userId);
    }

    protected List<MealTo> getBetweenForController(LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime, int userId) {
        log.info("getBetween dates({} - {}) time({} - {}) for user {}", startDate, endDate, startTime, endTime, userId);
        List<Meal> mealsDateFiltered = service.getBetweenDates(startDate, endDate, userId);
        return MealsUtil.getFilteredWithExcess(mealsDateFiltered, SecurityUtil.authUserCaloriesPerDay(), startTime, endTime);
    }
}
