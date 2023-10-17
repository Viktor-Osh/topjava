package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository("inMemoryMealRepository")
public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(meal -> save(1, meal));
        List<Meal> meals = Arrays.asList(
                new Meal(LocalDateTime.of(2023, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new Meal(LocalDateTime.of(2023, Month.MAY, 30, 13, 0), "Обед", 1000),
                new Meal(LocalDateTime.of(2023, Month.MAY, 30, 20, 0), "Ужин", 500),
                new Meal(LocalDateTime.of(2023, Month.MAY, 31, 0, 0), "Еда на граничное значение", 100),
                new Meal(LocalDateTime.of(2023, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new Meal(LocalDateTime.of(2023, Month.MAY, 31, 13, 0), "Обед", 500),
                new Meal(LocalDateTime.of(2023, Month.MAY, 31, 20, 0), "Ужин", 410)
        );
        meals.forEach(meal -> save(2, meal));
    }


    @Override
    public Meal save(int userId, Meal meal) {
        Map<Integer, Meal> userMap = repository.get(userId);
        if (!meal.isNew() && userMap != null && userMap.get(meal.getId()) == null) {
            return null;
        }
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
        }
        repository.compute(userId, (key, map) -> {
            if (map == null) {
                map = new ConcurrentHashMap<>();
            }
            map.put(meal.getId(), meal);
            return map;
        });
        return meal;
    }

    @Override
    public boolean delete(int userId, int mealId) {
        return repository.computeIfPresent(userId, (id, mealMap) -> {
            if (mealMap.remove(mealId) != null) {
                return mealMap;
            } else {
                return null;
            }
        }) != null;
    }

    @Override
    public Meal get(int userId, int mealId) {
        Map<Integer, Meal> userMap = repository.get(userId);
        if (userMap != null) {
            return userMap.get(mealId);
        }
        return null;
    }

    @Override
    public List<Meal> getAll(int userId) {
        return repository.containsKey(userId) ? repository.get(userId).values().stream()
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList()) : Collections.emptyList();
    }

    @Override
    public List<Meal> getFilteredByDate(int userId, LocalDate startDate, LocalDate endDate) {
        return getAll(userId).stream()
                .filter((meal -> meal.getDate().compareTo(startDate) >= 0 && meal.getDate().compareTo(endDate) <= 0))
                .collect(Collectors.toList());
    }
}

