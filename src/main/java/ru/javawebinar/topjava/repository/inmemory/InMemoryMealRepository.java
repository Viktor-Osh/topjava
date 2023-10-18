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

@Repository()
public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(meal -> save(1, meal));
        List<Meal> meals = Arrays.asList(
                new Meal(LocalDateTime.of(2023, Month.MAY, 30, 10, 0), "Завтрак user 2", 500),
                new Meal(LocalDateTime.of(2023, Month.MAY, 30, 13, 0), "Обед user'а 2", 1000),
                new Meal(LocalDateTime.of(2023, Month.MAY, 30, 20, 0), "Ужин user'а 2", 500),
                new Meal(LocalDateTime.of(2023, Month.MAY, 31, 0, 0), "Ночной дожор user'а 2", 100),
                new Meal(LocalDateTime.of(2023, Month.MAY, 31, 10, 0), "Завтрак user'а 2", 1000),
                new Meal(LocalDateTime.of(2023, Month.MAY, 31, 13, 0), "Обед user'а 2", 500),
                new Meal(LocalDateTime.of(2023, Month.MAY, 31, 20, 0), "Ужин user'а 2", 410)
        );
        meals.forEach(meal -> save(2, meal));
    }

    @Override
    public Meal save(int userId, Meal meal) {
        Map<Integer, Meal> userMap = repository.compute(userId, (key, existingUserMap) -> {
            if (existingUserMap == null) {
                existingUserMap = new ConcurrentHashMap<>();
            }
            if (!meal.isNew()) {
                existingUserMap.computeIfPresent(meal.getId(), (k, v) -> meal);
            } else {
                meal.setId(counter.incrementAndGet());
                existingUserMap.put(meal.getId(), meal);
            }
            return existingUserMap;
        });
        return userMap.get(meal.getId());
    }

    @Override
    public boolean delete(int userId, int mealId) {
        Map<Integer, Meal> userMap = repository.get(userId);
        if (userMap != null) {
            return userMap.remove(mealId) != null;
        }
        return false;
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
        return getFilteredByDate(userId, LocalDate.MIN, LocalDate.MAX);
    }

    @Override
    public List<Meal> getFilteredByDate(int userId, LocalDate startDate, LocalDate endDate) {
        Map<Integer, Meal> userMap = repository.get(userId);
        return userMap != null ? userMap.values().stream()
                .filter((meal -> meal.getDate().compareTo(startDate) >= 0 && meal.getDate().compareTo(endDate) <= 0))
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList()) : Collections.emptyList();
    }
}

