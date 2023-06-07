package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.Repository.MealsRepository;
import ru.javawebinar.topjava.model.Meal;

import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;


import static org.slf4j.LoggerFactory.getLogger;
import static ru.javawebinar.topjava.Repository.MealsRepository.CALORIES_PER_DAY;

public class MealServlet extends HttpServlet {
    private static final String MEALS_LIST = "/meals.jsp";
    private static final Logger log = getLogger(MealServlet.class);
    private MealsRepository mealsRepository;

    @Override
    public void init() throws ServletException {
        super.init();
        mealsRepository = new MealsRepository();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String forward;
        String action = req.getParameter("action");

        String INSERT_OR_EDIT = "/mealEdit_Add.jsp";
        if (action.equalsIgnoreCase("delete")) {
            Integer mealId = Integer.parseInt(req.getParameter("mealId"));
            mealsRepository.delete(mealId);
            forward = MEALS_LIST;
            req.setAttribute("meals", MealsUtil.filteredByStreams(mealsRepository.getAll(), LocalTime.MIN, LocalTime.MAX, CALORIES_PER_DAY));
        } else if (action.equalsIgnoreCase("edit")) {
            forward = INSERT_OR_EDIT;
            int mealId = Integer.parseInt(req.getParameter("mealId"));
            String description = req.getParameter("description");
            LocalDateTime dateTime = LocalDateTime.parse(req.getParameter("mealDateTime"));
            int calories = Integer.parseInt(req.getParameter("calories"));
            Meal meal = new Meal(dateTime, description, calories);
            meal.setId(mealId);
            req.setAttribute("meal", meal);
            mealsRepository.update(meal);
            req.setAttribute("meals", MealsUtil.filteredByStreams(mealsRepository.getAll(), LocalTime.MIN, LocalTime.MAX, CALORIES_PER_DAY));
        } else if (action.equalsIgnoreCase("meals")) {
            log.debug("get meals table");
            forward = MEALS_LIST;
            req.setAttribute("meals", MealsUtil.filteredByStreams(mealsRepository.getAll(), LocalTime.MIN, LocalTime.MAX, CALORIES_PER_DAY));
        } else {
            forward = INSERT_OR_EDIT;
        }

        req.getRequestDispatcher(forward).forward(req, resp);

    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Integer mealId = Integer.parseInt(req.getParameter("mealId"));
        LocalDateTime mealDateTime = LocalDateTime.parse(req.getParameter("mealdateTime"));
        String description = (req.getParameter("description"));
        int calories = Integer.parseInt(req.getParameter("calories"));
        Meal meal = new Meal(mealDateTime, description, calories);
        meal.setId(mealId);
        if (!mealsRepository.getMealsHashMap().containsKey(mealId)) {
            mealsRepository.add(meal);
        } else {
            mealsRepository.update(meal);
        }
        req.setAttribute("meals", MealsUtil.filteredByStreams(mealsRepository.getAll(), LocalTime.MIN, LocalTime.MAX, CALORIES_PER_DAY));
        req.getRequestDispatcher(MEALS_LIST).forward(req, resp);
    }
}
