package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510)
        );
        getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000).forEach(System.out::println);
        getFilteredWithExceededOptional(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000).forEach(System.out::println);
    }

    public static List<UserMealWithExceed>  getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        List<UserMealWithExceed> result = new ArrayList<>();
        Map<LocalDate, Integer> map = new HashMap<>();
        mealList.forEach(m -> map.merge(m.getDateTime().toLocalDate(), m.getCalories(), (sum, val) -> sum + val));
        mealList.forEach(m -> {
            if(TimeUtil.isBetween(m.getTime(), startTime, endTime)){
                result.add(CreateUserMealWithExceed(m, map, caloriesPerDay));
            }
        });
        return result;
    }
    public static List<UserMealWithExceed>  getFilteredWithExceededOptional(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        Map<LocalDate, Integer> map = mealList.stream()
                .collect(Collectors.groupingBy(UserMeal::getDate, Collectors.summingInt(UserMeal::getCalories)));
        return mealList.stream()
                .filter(m -> TimeUtil.isBetween(m.getTime(), startTime, endTime))
                .map(m -> CreateUserMealWithExceed(m, map, caloriesPerDay))
                .collect(Collectors.toList());
    }
    private static UserMealWithExceed CreateUserMealWithExceed(UserMeal m, Map<LocalDate, Integer> map, int caloriesPerDay){
        return new UserMealWithExceed(m.getDateTime(), m.getDescription(), m.getCalories(), map.get(m.getDateTime().toLocalDate()) > caloriesPerDay);
    }
}
