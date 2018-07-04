package ru.javawebinar.topjava.crud;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.Meal;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class MockRepository implements Repository {
    private static final Logger LOG = LoggerFactory.getLogger(MockRepository.class);
    static public ConcurrentHashMap<Integer, Meal> map = new ConcurrentHashMap<>();
    static public AtomicInteger id = new AtomicInteger(0);

    @Override
    public void saveMeal(Meal meal) {
        LOG.debug("save meal");
        if(isNewMeal(meal)){
            meal.setId(id.incrementAndGet());
        }
        map.put(meal.getId(), meal);
    }

    @Override
    public void deleteMeal(int id) {
        LOG.debug("delete meal");
        map.remove(id);
    }

    @Override
    public Meal getMeal(int id) {
        LOG.debug("get meal");
        Meal meal;
        if(id == 0){
            meal = new Meal(id, LocalDateTime.now(), "eat", 0);
        } else {
            meal = map.get(id);
        }
        return meal;
    }

    @Override
    public List<Meal> getAllMeals() {
        LOG.debug("get all meals");
        return map.values()
                .stream()
                .sorted(new Comparator<Meal>() {
                    @Override
                    public int compare(Meal o1, Meal o2) {
                        return o2.getDateTime().compareTo(o1.getDateTime());
                    }
                })
                .collect(Collectors.toList());
    }

    private boolean isNewMeal(Meal meal){
        return meal.getId() < 1;
    }
}

