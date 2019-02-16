package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

@Controller
public class MealRestController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    private MealService service;

    @Autowired
    public MealRestController(MealService service) {
        this.service = service;
    }

    public List<MealTo> getAll() {
        log.info("getAll");
        return MealsUtil.getWithExcess(service.getAll(SecurityUtil.authUserId()), SecurityUtil.authUserCaloriesPerDay());
    }

    public List<MealTo> getAllWithFilter(String startDate, String endDate, String startTime, String endTime) {
        log.info("getAllWithFilter");
        List<MealTo> mealsTo = getAll();
        LocalDate dateStart = startDate.length() != 0 ? LocalDate.parse(startDate) : LocalDate.MIN;
        LocalDate dateEnd = endDate.length() != 0 ? LocalDate.parse(endDate) : LocalDate.MAX;
        LocalTime timeStart = startTime.length() != 0 ? LocalTime.parse(startTime) : LocalTime.MIN;
        LocalTime timeEnd = endTime.length() != 0 ? LocalTime.parse(endTime) : LocalTime.MAX;

        List<Meal> meals = service.getAllWithFilter(dateStart, dateEnd, timeStart, timeEnd, SecurityUtil.authUserId());
        List<MealTo> result = new ArrayList<>();
        //meals.stream().map(x -> new MealTo(x.getId(), x.getDateTime(), x.getDescription(), x.getCalories(), mealsTo.get(x.getId()).isExcess()));
        for (int i = 0; i < meals.size(); i++) {
            for (int j = 0; j < mealsTo.size(); j++) {
                if (meals.get(i).getId() == mealsTo.get(j).getId()) {
                    result.add(new MealTo(meals.get(i).getId(), meals.get(i).getDateTime(),
                            meals.get(i).getDescription(), meals.get(i).getCalories(), mealsTo.get(j).isExcess()));
                    break;
                }
            }
        }
        //return MealsUtil.getWithExcess(service.getAllWithFilter(startDate, endDate, startTime, endTime, SecurityUtil.authUserId()), SecurityUtil.authUserCaloriesPerDay());
        return result;
    }

    public Meal get(int id) {
        log.info("get {}", id);
        return service.get(id, SecurityUtil.authUserId());
    }

    public Meal create(Meal meal) {
        log.info("create {}", meal);
        meal.setUserId(SecurityUtil.authUserId());
        checkNew(meal);
        return service.create(meal, SecurityUtil.authUserId());
    }

    public void delete(int id) {
        log.info("delete {}", id);
        service.delete(id, SecurityUtil.authUserId());
    }

    public void update(Meal meal, int id) {
        log.info("update {} with id={}", meal, id);
        meal.setUserId(SecurityUtil.authUserId());
        assureIdConsistent(meal, id);
        service.update(meal, SecurityUtil.authUserId());
    }
}