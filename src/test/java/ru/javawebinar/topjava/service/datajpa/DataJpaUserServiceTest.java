package ru.javawebinar.topjava.service.datajpa;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.repository.datajpa.DataJpaUserRepository;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.service.UserServiceTest;

import static ru.javawebinar.topjava.MealTestData.MEAL_MATCHER;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ActiveProfiles(Profiles.DATAJPA)
public class DataJpaUserServiceTest extends UserServiceTest {

    @Autowired
    private MealService mealService;

    @Autowired
    private DataJpaUserRepository dataJpaUserRepository;

    @Test
    @Transactional
    public void getWithMeal() {
        MEAL_MATCHER.assertMatch(dataJpaUserRepository.getUserWithMealList(USER_ID), mealService.getAll(USER_ID));
    }
}
