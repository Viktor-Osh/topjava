package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalTime;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.javawebinar.topjava.Repository.MealsRepository.CaloriesPerDay;
import static ru.javawebinar.topjava.Repository.MealsRepository.meals;

public class MealServlet extends HttpServlet {

    private static final Logger log = getLogger(MealServlet.class);

    List<MealTo> allMealsWithExceedList = MealsUtil.filteredByStreams(meals,
            LocalTime.MIN, LocalTime.MAX, CaloriesPerDay);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("get meals table");
        req.setAttribute("meals", allMealsWithExceedList);
        req.getRequestDispatcher("meals.jsp").forward(req, resp);
    }
}
