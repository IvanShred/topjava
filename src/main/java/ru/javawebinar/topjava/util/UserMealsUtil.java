package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
        );
        getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
//        .toLocalDate();
//        .toLocalTime();
    }

    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {//        List<LocalDate> listDate = new ArrayList<>();

//        List<LocalDate> listDate = mealList.stream()
//                .map((userMeal -> userMeal.getDateTime().toLocalDate()))
//                .distinct()
//                .collect(Collectors.toList());
//
//        for (UserMeal um : mealList) {
//            LocalDate date = um.getDateTime().toLocalDate();
//            if (!listDate.contains(date))
//                listDate.add(date);
//        }
//
//        int summ;
//        Map<LocalDate, Boolean> excessForDate = new HashMap<>();
//        for (LocalDate date : listDate) {
//            summ = 0;
//            for (UserMeal um : mealList) {
//                if (date.equals(um.getDateTime().toLocalDate())) {
//                    summ += um.getCalories();
//                }
//            }
//            excessForDate.put(date, summ > caloriesPerDay);
//        }
//
//        List<UserMealWithExceed> result = new ArrayList<>();
//        for (UserMeal um : mealList) {
//            if (TimeUtil.isBetween(um.getDateTime().toLocalTime(), startTime, endTime)) {
//                result.add(new UserMealWithExceed(um.getDateTime(), um.getDescription(), um.getCalories(), excessForDate.get(um.getDateTime().toLocalDate())));
//            }
//        }
//
//        return result;

        Map<LocalDate, Integer> caloriesForDate = mealList.stream()
                .collect(Collectors.groupingBy((UserMeal userMeal) -> userMeal.getDateTime().toLocalDate(),
                        Collectors.summingInt(UserMeal::getCalories)));

        return mealList.stream().filter(userMeal -> TimeUtil.isBetween(userMeal.getDateTime().toLocalTime(), startTime, endTime))
                .map(userMeal -> new UserMealWithExceed(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(), caloriesForDate.get(userMeal.getDateTime().toLocalDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }
}
