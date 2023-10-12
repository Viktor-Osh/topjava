package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;

import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;

@Controller
public class MealRestController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    @Qualifier("mealService")
    private MealService service;

    public Meal get(int mealId) {
        log.info("get meal with id: {} for user with id: {}", mealId, authUserId());
        return service.get(authUserId(), mealId);
    }

    public void delete(int mealId) {
        log.info("delete meal with id: {} for user with id: {}", mealId, authUserId());
        service.delete(authUserId(), mealId);
    }

    public void create(Meal meal) {
        log.info("create meal {} for user with id: {}", meal, authUserId());
        checkNew(meal);
        service.create(authUserId(), meal);
    }

    public void update(Meal meal) {
        log.info("update {} for user with id={}", meal, authUserId());
        service.update(authUserId(), meal);
    }

    public List<Meal> getAll() {
        log.info("getAll Meals for userId {}", authUserId());
        return service.getAll(authUserId());
    }
}