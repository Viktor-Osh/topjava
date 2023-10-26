package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int USER_ID = START_SEQ;
    public static final int ADMIN_ID = START_SEQ + 1;
    public static final int GUEST_ID = START_SEQ + 2;
    public static final int MEAL_ID = START_SEQ + 3;
    public static final int NOT_FOUND = 10;

    public static final Meal user_meal1 = new Meal(START_SEQ+3, LocalDateTime.of(2023, 10, 22, 10, 0, 0),
            "завтрак usera", 300);

    public static final Meal user_meal2 = new Meal(START_SEQ+4, LocalDateTime.of(2023, 10, 22, 13, 0, 0),
            "обед usera", 1000);

    public static final Meal user_meal3 = new Meal(START_SEQ+5, LocalDateTime.of(2023, 10, 22, 19, 0, 0),
            "ужин usera", 700);
    public static final Meal user_meal4 = new Meal(START_SEQ+6, LocalDateTime.of(2023, 10, 23, 11, 0, 0),
            "завтрак usera", 400);

    public static final Meal user_meal5 = new Meal(START_SEQ+7, LocalDateTime.of(2023, 10, 23, 14, 0, 0),
            "обед usera", 1000);

    public static final Meal user_meal6 = new Meal(START_SEQ+8, LocalDateTime.of(2023, 10, 23, 20, 0, 0),
            "ужин usera", 500);

    public static Meal getNew() {
        return new Meal(null, LocalDateTime.of(2023, 10, 25, 17, 0, 0), "ужин тестовый", 155);
    }

    public static Meal getUpdated() {
        Meal updated = new Meal(user_meal1);
        updated.setId(user_meal1.getId());
        updated.setDateTime(LocalDateTime.of(2023, 10, 22, 10, 0, 0));
        updated.setCalories(300);
        updated.setDescription("завтрак usera");
        return updated;
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

}
