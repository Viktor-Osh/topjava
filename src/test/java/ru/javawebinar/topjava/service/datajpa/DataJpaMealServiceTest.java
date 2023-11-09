package ru.javawebinar.topjava.service.datajpa;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.datajpa.DataJpaMealRepository;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.service.MealServiceTest;
import ru.javawebinar.topjava.service.UserService;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.*;

@ActiveProfiles(Profiles.DATAJPA)
    public class DataJpaMealServiceTest extends MealServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private MealService mealService;

    @Test
    @Transactional
    public void getWithMeal() {
        USER_MATCHER.assertMatch(mealService.getMealWithUser(meal1, USER_ID).getUser(), userService.get(USER_ID));
    }

    @Test
    public void getUserWithNotFoundMeal() {
        assertThrows(NotFoundException.class, () -> mealService.getMealWithUser(meal1, ADMIN_ID));
    }
}
