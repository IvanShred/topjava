package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MealServiceImpl implements MealService {

    private MealRepository repository;

    @Autowired
    public MealServiceImpl(MealRepository repository) {
        this.repository = repository;
    }

    @Override
    public Meal create(Meal meal, int authUserId) {
        return repository.save(meal, authUserId);
    }

    @Override
    public void delete(int id, int authUserId) throws NotFoundException {
        checkNotFoundWithId(repository.delete(id, authUserId), id);
    }

    @Override
    public Meal get(int id, int authUserId) throws NotFoundException {
        return checkNotFoundWithId(repository.get(id, authUserId), id);
    }

    @Override
    public Meal update(Meal meal, int authUserId) {
        return checkNotFoundWithId(repository.save(meal, authUserId), meal.getId());
    }

    @Override
    public List<Meal> getAll(int authUserId) {
        return repository.getAll(authUserId);
    }

    @Override
    public List<Meal> getAllWithFilter(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime, int authUserId) {
        return repository.getAllWithFilter(startDate, endDate, startTime, endTime, authUserId);
    }
}