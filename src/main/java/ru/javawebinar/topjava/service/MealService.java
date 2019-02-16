package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface MealService {
    Meal create(Meal meal, int authUserId);

    void delete(int id, int authUserId) throws NotFoundException;

    Meal get(int id, int authUserId) throws NotFoundException;

    void update(Meal meal, int authUserId);

    List<Meal> getAll(int authUserId);

    List<Meal> getAllWithFilter(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime, int authUserId);
}