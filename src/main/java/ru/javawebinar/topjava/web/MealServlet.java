package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.storage.MapMealsStorage;
import ru.javawebinar.topjava.storage.MealsStorage;
import ru.javawebinar.topjava.util.DateUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalTime;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private MealsStorage storage;

    @Override
    public void init() throws ServletException {
        storage = new MapMealsStorage();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        int uuid = Integer.parseInt(request.getParameter("uuid"));
        final boolean isCreate = (uuid == 0);
        Meal meal = new Meal();
        meal.setId(uuid);
        String data = request.getParameter("data");
        meal.setDateTime(DateUtil.dateTimeFormatFromString(data));
        String description = request.getParameter("description");
        meal.setDescription(description);
        String calories = request.getParameter("calories");
        meal.setCalories(Integer.parseInt(calories));

        if (isCreate) {
            storage.save(meal);
        } else {
            storage.update(meal);
        }

        response.sendRedirect("meals");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("forward to meals");
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("meals", MealsUtil.getFilteredWithExcess(storage.getAll(), LocalTime.MIN, LocalTime.MAX, 2000));
            request.getRequestDispatcher("/meals.jsp").forward(request, response);
            return;
        }
        Meal meal;
        switch (action) {
            case "delete":
                storage.delete(Integer.parseInt(uuid));
                response.sendRedirect("meals");
                return;
            case "add":
                meal = new Meal();
                break;
            case "edit":
                meal = storage.get(Integer.parseInt(uuid));
                break;
            default:
                response.sendRedirect("meals");
                return;
        }
        request.setAttribute("meal", meal);
        request.getRequestDispatcher("/editMeals.jsp").forward(request, response);
    }
}
