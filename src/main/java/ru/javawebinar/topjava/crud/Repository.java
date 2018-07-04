package ru.javawebinar.topjava.crud;

import ru.javawebinar.topjava.model.Meal;
import java.util.List;

public interface Repository {
    public void saveMeal(Meal meal);

    public void deleteMeal(int id);

    public Meal getMeal(int id);

    public List<Meal> getAllMeals();

}

