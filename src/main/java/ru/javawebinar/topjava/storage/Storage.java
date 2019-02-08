package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface Storage {

    Meal get(int uuid);

    void update(Meal meal);

    void save(Meal meal);

    void delete(int uuid);

    List<Meal> getMeals();
}
