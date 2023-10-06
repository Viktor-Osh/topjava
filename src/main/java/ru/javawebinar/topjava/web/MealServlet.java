package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.Repository.MealsRepository;
import ru.javawebinar.topjava.Repository.Repository;
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

public class MealServlet extends HttpServlet {
    private static final int CALORIES_PER_DAY = 2000;
    private static final String MEALS_LIST = "/meals.jsp";
    private static final Logger log = getLogger(MealServlet.class);
    private static final String MEALS = "meals";

    private Repository<Meal> mealsRepository;

    @Override
    public void init() {
        mealsRepository = new MealsRepository();
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String forward;
        String action = req.getParameter("action");
        switch (action == null ? "default" : action.toLowerCase()) {
            case "delete": {
                int mealId = Integer.parseInt(req.getParameter("mealId"));
                mealsRepository.delete(mealId);
                resp.sendRedirect(MEALS);
                break;
            }
            case "edit": {
                forward = "/mealEdit.jsp";
                int mealId = Integer.parseInt(req.getParameter("mealId"));
                Meal meal = mealsRepository.get(mealId);
                req.setAttribute("meal", meal);
                req.getRequestDispatcher(forward).forward(req, resp);
                break;
            }
            case "add": {
                forward = "/mealEdit.jsp";
                req.getRequestDispatcher(forward).forward(req, resp);
                break;
            }
            case "meals": {
                log.debug("get meals table");
                forward = MEALS_LIST;
                req.setAttribute(MEALS, MealsUtil.filteredByStreams(mealsRepository.getAll(), LocalTime.MIN, LocalTime.MAX, CALORIES_PER_DAY));
                req.getRequestDispatcher(forward).forward(req, resp);
                break;
            }
            default: {
                forward = MEALS_LIST;
                req.setAttribute(MEALS, MealsUtil.filteredByStreams(mealsRepository.getAll(), LocalTime.MIN, LocalTime.MAX, CALORIES_PER_DAY));
                req.getRequestDispatcher(forward).forward(req, resp);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String id = req.getParameter("mealId");
        LocalDateTime mealDateTime = LocalDateTime.parse(req.getParameter("dateTime"));
        String description = (req.getParameter("description"));
        int calories = Integer.parseInt(req.getParameter("calories"));
        Meal meal = new Meal(mealDateTime, description, calories);
        if (id == null || id.isEmpty()) {
            mealsRepository.add(meal);
        } else {
            Integer mealId = Integer.parseInt(id);
            meal.setId(mealId);
            meal.setCalories(calories);
            mealsRepository.update(meal);
        }
        req.setAttribute(MEALS, MealsUtil.filteredByStreams(mealsRepository.getAll(), LocalTime.MIN, LocalTime.MAX, CALORIES_PER_DAY));
        resp.sendRedirect(MEALS);
    }
}
