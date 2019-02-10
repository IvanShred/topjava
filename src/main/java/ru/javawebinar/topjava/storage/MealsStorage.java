package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealsStorage {

    Meal get(int uuid);

    Meal update(Meal meal);

    Meal save(Meal meal);

    void delete(int uuid);

    List<Meal> getAll();
}
