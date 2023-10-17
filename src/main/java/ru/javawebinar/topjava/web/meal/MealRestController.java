package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserCaloriesPerDay;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;

@Controller
public class MealRestController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private MealService service;

    public Meal get(int mealId) {
        log.debug("get meal with id: {} for user with id: {}", mealId, authUserId());
        return service.get(authUserId(), mealId);
    }

    public void delete(int mealId) {
        log.debug("delete meal with id: {} for user with id: {}", mealId, authUserId());
        service.delete(authUserId(), mealId);
    }

    public void create(Meal meal) {
        log.debug("create meal {} for user with id: {}", meal, authUserId());
        checkNew(meal);
        service.create(authUserId(), meal);
    }

    public void update(Meal meal, int mealId) {
        log.debug("update {} for user with id={}", meal, authUserId());
        assureIdConsistent(meal, mealId);
        service.update(authUserId(), meal);
    }

    public List<MealTo> getAll() {
        log.debug("getAll Meals for userId {}", authUserId());
        return MealsUtil.getTos(service.getAll(authUserId()), authUserCaloriesPerDay());
    }

    public List<MealTo> getFiltered(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
        if (startDate == null) {
            startDate = LocalDate.MIN;
        }
        if (endDate == null) {
            endDate = LocalDate.MAX;
        }
        if (startTime == null) {
            startTime = LocalTime.MIN;
        }
        if (endTime == null) {
            endTime = LocalTime.MAX;
        }
        List<Meal> mealList = service.getFilteredByDate(authUserId(), startDate, endDate);
        return MealsUtil.getFilteredByTimeTos(mealList, authUserCaloriesPerDay(), startTime, endTime);
    }
}