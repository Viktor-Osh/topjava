package ru.javawebinar.topjava.Repository;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MealsRepository implements Repository<Meal> {
    private static final AtomicInteger idCounter = new AtomicInteger(1);
    public static final int CALORIES_PER_DAY = 2000;

    public ConcurrentHashMap<Integer, Meal> getMealsHashMap() {
        return mealsHashMap;
    }

    private final ConcurrentHashMap<Integer, Meal> mealsHashMap = new ConcurrentHashMap<>();

    public MealsRepository() {
        List<Meal> meals = Arrays.asList(
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410));

            addAll(meals);
    }

    @Override
    public void addAll(List<Meal> meals) {
        for (Meal meal : meals) {
            add(meal);
        }
    }

    @Override
    public void add(Meal meal) {
        if (meal.getId() == null) {
            meal.setId(idCounter.getAndIncrement());
        }
        mealsHashMap.putIfAbsent(meal.getId(), meal);
    }
    
    @Override
    public void update(Meal meal) {
        if (mealsHashMap.containsKey(meal.getId())) {
            mealsHashMap.put(meal.getId(), meal);
        }
    }

    @Override
    public void delete(Integer id) {
        mealsHashMap.remove(id);
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(mealsHashMap.values());
    }
}
