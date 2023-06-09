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
    public final int CALORIES_PER_DAY = 2000;
    private static final String MEALS_LIST = "/meals.jsp";
    private static final Logger log = getLogger(MealServlet.class);
    private Repository<Meal> mealsRepository;

    @Override
    public void init() throws ServletException {
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
                forward = MEALS_LIST;
                req.setAttribute("meals", MealsUtil.filteredByStreams(mealsRepository.getAll(), LocalTime.MIN, LocalTime.MAX, CALORIES_PER_DAY));
//                resp.sendRedirect("meals.jsp");
//                break;
                req.getRequestDispatcher(forward).forward(req, resp);
                return;
            }
            case "edit": {
                forward = "/mealEdit_Add.jsp";
                int mealId = Integer.parseInt(req.getParameter("mealId"));
                String description = req.getParameter("description");
                LocalDateTime dateTime = LocalDateTime.parse(req.getParameter("dateTime"));
                int calories = Integer.parseInt(req.getParameter("calories"));
                Meal meal = new Meal(dateTime, description, calories);
                meal.setId(mealId);
                req.setAttribute("meal", meal);
                mealsRepository.update(meal);
                req.setAttribute("meals", MealsUtil.filteredByStreams(mealsRepository.getAll(), LocalTime.MIN, LocalTime.MAX, CALORIES_PER_DAY));
                break;
            }
            case "meals": {
                log.debug("get meals table");
                forward = MEALS_LIST;
                req.setAttribute("meals", MealsUtil.filteredByStreams(mealsRepository.getAll(), LocalTime.MIN, LocalTime.MAX, CALORIES_PER_DAY));
                break;
            }
            default: {
                forward = MEALS_LIST;
                req.setAttribute("meals", MealsUtil.filteredByStreams(mealsRepository.getAll(), LocalTime.MIN, LocalTime.MAX, CALORIES_PER_DAY));
            }
        }

        req.getRequestDispatcher(forward).forward(req, resp);

    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Integer mealId = Integer.parseInt(req.getParameter("mealId"));
        LocalDateTime mealDateTime = LocalDateTime.parse(req.getParameter("dateTime"));
        String description = (req.getParameter("description"));
        int calories = Integer.parseInt(req.getParameter("calories"));
        Meal meal = new Meal(mealDateTime, description, calories);
        meal.setId(mealId);
//        if (!mealsRepository.getMealsHashMap().containsKey(mealId)) {
//            mealsRepository.add(meal);
//        } else {
//            mealsRepository.update(meal);
//        }
        if (mealsRepository.get(mealId) == null) {
            mealsRepository.add(meal);
        } else mealsRepository.update(meal);

        req.setAttribute("meals", MealsUtil.filteredByStreams(mealsRepository.getAll(), LocalTime.MIN, LocalTime.MAX, CALORIES_PER_DAY));
        resp.sendRedirect("meals.jsp");
    }
}
