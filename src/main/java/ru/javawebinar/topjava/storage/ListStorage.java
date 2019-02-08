package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class ListStorage implements Storage {
    private static AtomicInteger counter;
    private static List<Meal> meals = new CopyOnWriteArrayList();

    static {
        meals.add(new Meal(1, LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));
        meals.add(new Meal(2, LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000));
        meals.add(new Meal(3, LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500));
        meals.add(new Meal(4, LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000));
        meals.add(new Meal(5, LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500));
        meals.add(new Meal(6, LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510));
        counter = new AtomicInteger(7);
    }

    public List<Meal> getMeals() {
        return meals;
    }

    public int getIndex(int uuid) {
        for (int i = 0; i < meals.size(); i++) {
            if (meals.get(i).getId() == uuid) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public Meal get(int uuid) {
        return meals.get(getIndex(uuid));
    }

    @Override
    public void update(Meal meal) {

    }

    @Override
    public void save(Meal meal) {
        meal.setId(counter.intValue());
        counter.getAndDecrement();
        meals.add(meal);
    }

    @Override
    public void delete(int uuid) {
        meals.remove(getIndex(uuid));
    }
}
