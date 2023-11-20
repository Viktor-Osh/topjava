package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.javawebinar.topjava.model.Meal;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;

@Controller
public class JspMealController {
    private static final Logger log = LoggerFactory.getLogger(JspMealController.class);

    @Autowired
    private MealRestController controller;

    @GetMapping("/meals")
    public String getAll(Model model) {
        log.info("meals");
        if (model.getAttribute("meals") == null) {
            model.addAttribute("meals", controller.getAll());
        }
        return "meals";
    }

    @GetMapping("/delete")
    public String delete(HttpServletRequest request) {
        int mealId = Integer.parseInt(request.getParameter("id"));
        log.info("delete meal {}", mealId);
        controller.delete(mealId);
        return "redirect:meals";
    }

    @GetMapping({"/create", "/update"})
    public ModelAndView createOrUpdate(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("mealForm");
        if ((request.getParameter("id")) != null) {
            int mealId = Integer.parseInt(request.getParameter("id"));
            log.info("update meal {}", mealId);
            modelAndView.addObject("meal", controller.get(mealId));
        } else {
            log.info("create meal");
            final Meal meal = new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000);
            modelAndView.addObject("meal", meal);
        }
        return modelAndView;
    }

    @PostMapping("/meals")
    public String addNewOrUpdate(HttpServletRequest request) {
        Meal meal = new Meal(LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));
        String paramId = request.getParameter("id");
        if (paramId.isEmpty()) {
            controller.create(meal);
        } else {
            controller.update(meal, Integer.parseInt(paramId));
        }
        return "redirect:meals";
    }

    @GetMapping("filter")
    public String filterList(HttpServletRequest request) {
        LocalDate startDate = parseLocalDate(request.getParameter("startDate"));
        LocalDate endDate = parseLocalDate(request.getParameter("endDate"));
        LocalTime startTime = parseLocalTime(request.getParameter("startTime"));
        LocalTime endTime = parseLocalTime(request.getParameter("endTime"));
        request.setAttribute("meals", controller.getBetween(startDate, startTime, endDate, endTime));
        return "meals";
    }
}
