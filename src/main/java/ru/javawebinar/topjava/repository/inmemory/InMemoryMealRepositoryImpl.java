package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(meal -> save(meal, 1));
        save(new Meal(LocalDateTime.of(2019, Month.FEBRUARY, 16, 10, 0), "Бутерброды", 1000), 2);
        save(new Meal(LocalDateTime.of(2019, Month.FEBRUARY, 16, 13, 0), "Котлеты", 510), 2);
        save(new Meal(LocalDateTime.of(2019, Month.FEBRUARY, 16, 20, 0), "Суп", 500), 2);
        save(new Meal(LocalDateTime.of(2019, Month.FEBRUARY, 17, 10, 0), "Яичница", 500), 2);
        save(new Meal(LocalDateTime.of(2019, Month.FEBRUARY, 17, 13, 0), "Макароны", 500), 2);
        save(new Meal(LocalDateTime.of(2019, Month.FEBRUARY, 17, 20, 0), "Салат", 1000), 2);

    }

    @Override
    public Meal save(Meal meal, int userId) {
        repository.putIfAbsent(userId, new ConcurrentHashMap<>());
        Map<Integer, Meal> meals = repository.get(userId);
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            meals.put(meal.getId(), meal);
            return meal;
        } else {
            return meals.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
        }
    }

    @Override
    public boolean delete(int id, int userId) {
        Map<Integer, Meal> meals = repository.get(userId);
        boolean result = meals != null && meals.get(id) != null;
        if (result) {
            meals.remove(id);
        }
        return result;
    }

    @Override
    public Meal get(int id, int userId) {
        Map<Integer, Meal> meals = repository.get(userId);
        return meals != null ? meals.get(id) : null;
    }

    @Override
    public List<Meal> getAll(int userId) {
        Map<Integer, Meal> mealsMap = repository.get(userId);
        if (mealsMap != null) {
            List<Meal> meals = new ArrayList<>(mealsMap.values());
            meals.sort((o1, o2) -> -o1.getDate().compareTo(o2.getDate()));
            return meals;
        }
        return new ArrayList<>();
    }

    @Override
    public List<Meal> getAllWithFilter(LocalDate startDate, LocalDate endDate, int userId) {
        return getAll(userId).stream()
                .filter(meal -> DateTimeUtil.isBetween(meal.getDateTime().toLocalDate(), startDate, endDate))
                .collect(Collectors.toList());
    }
}

