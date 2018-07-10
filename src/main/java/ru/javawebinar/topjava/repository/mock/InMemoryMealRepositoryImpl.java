package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        for (Meal m: MealsUtil.MEALS) {
            this.save(m.getUserId(), m);
        }
    }

    @Override
    public Meal save(Integer userId, Meal meal) {
        if(userId == meal.getUserId()){
            if (meal.isNew()) {
                meal.setId(counter.incrementAndGet());
                repository.put(meal.getId(), meal);
                return meal;
            }
            // treat case: update, but absent in storage
            return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
        }
        return null;
    }

    @Override
    public boolean delete(Integer userId, int id) {
        Meal meal = repository.get(id);
        if(meal != null && userId == meal.getUserId()){
            repository.remove(id);
            return true;
        }
        return false;
    }

    @Override
    public Meal get(Integer userId, int id) {
        Meal meal = repository.get(id);
        if(meal != null && userId == meal.getUserId()){
            return meal;
        }
        return null;
    }

    @Override
    public Collection<Meal> getAll(Integer userId) {
        return repository.values().stream()
                .filter(m -> m.getUserId() == userId)
                .sorted((m1, m2) -> m2.getDateTime().compareTo(m1.getDateTime()))
                .collect(Collectors.toList());
    }
}

