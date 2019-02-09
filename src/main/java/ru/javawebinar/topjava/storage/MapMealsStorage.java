package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MapMealsStorage implements MealsStorage {
    private static AtomicInteger counter;
    private ConcurrentHashMap<Integer, Meal> meals = new ConcurrentHashMap<>();

    public List<Meal> getMeals() {
        return new ArrayList<>(meals.values());
    }

    @Override
    public void fillStorage() {
        meals.put(1, new Meal(1, LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));
        meals.put(2, new Meal(2, LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000));
        meals.put(3, new Meal(3, LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500));
        meals.put(4, new Meal(4, LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000));
        meals.put(5, new Meal(5, LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500));
        meals.put(6, new Meal(6, LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510));
        counter = new AtomicInteger(6);
    }

    @Override
    public Meal get(int id) {
        return meals.get(id);
    }

    @Override
    public Meal update(Meal meal) {
        meals.put(meal.getId(), meal);
        return meal;
    }

    @Override
    public Meal save(Meal meal) {
        counter.incrementAndGet();
        meal.setId(counter.intValue());
        meals.put(meal.getId(), meal);
        return meal;
    }

    @Override
    public void delete(int id) {
        meals.remove(id);
    }
}
