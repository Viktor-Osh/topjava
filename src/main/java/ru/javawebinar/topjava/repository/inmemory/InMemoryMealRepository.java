package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository("inMemoryMealRepo")
public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach((meal) -> save(SecurityUtil.authUserId(), meal));
    }

    @Override
    public Meal save(int userId, Meal meal) {
        if (!meal.isNew() && repository.containsKey(userId) && !(repository.get(userId).containsKey(meal.getId()))) {
            return null;
        }
        repository.compute(userId, (key, map) -> {
            if (map == null) {
                map = new ConcurrentHashMap<>();
            }
            if (meal.isNew()) {
                meal.setId(counter.incrementAndGet());
            }
            map.put(meal.getId(), meal);
            return map;
        });
        return meal;
    }

    @Override
    public boolean delete(int userId, int mealId) {
        AtomicBoolean isDeleted = new AtomicBoolean(false);
        repository.computeIfPresent(userId, (id, mealMap) -> {
            isDeleted.set(mealMap.remove(mealId) != null);
            return mealMap;
        });
        return isDeleted.get();
    }

    @Override
    public Meal get(int userId, int mealId) {
        if (repository.containsKey(userId) && repository.get(userId).containsKey(mealId)) {
            return repository.get(userId).get(mealId);
        }
        return null;
    }

    @Override
    public List<Meal> getAll(int userId) {
        return repository.containsKey(userId) ? repository.get(userId).values().stream()
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList()) : new ArrayList<>();
    }

}

