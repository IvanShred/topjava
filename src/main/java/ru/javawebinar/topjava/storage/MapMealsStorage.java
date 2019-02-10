package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MapMealsStorage implements MealsStorage {
    private AtomicInteger counter = new AtomicInteger(1);
    private Map<Integer, Meal> meals = new ConcurrentHashMap<>();

    {
        save(new Meal(1, LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));
        save(new Meal(2, LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000));
        save(new Meal(3, LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500));
        save(new Meal(4, LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000));
        save(new Meal(5, LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500));
        save(new Meal(6, LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510));
    }

    public List<Meal> getAll() {
        return new ArrayList<>(meals.values());
    }

    @Override
    public Meal get(int id) {
        return meals.get(id);
    }

    @Override
    public Meal update(Meal meal) {
        if (get(meal.getId()) == null) {
            save(meal);
        } else {
            meals.put(meal.getId(), meal);
        }
        return meal;
    }

    @Override
    public Meal save(Meal meal) {
        meal.setId(counter.getAndIncrement());
        meals.put(meal.getId(), meal);
        return meal;
    }

    @Override
    public void delete(int id) {
        meals.remove(id);
    }
}
