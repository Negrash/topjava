package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.crud.MockRepository;
import ru.javawebinar.topjava.crud.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class MealServlet extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(MealServlet.class);

    private static Repository repository = new MockRepository();
    static {
        MealsUtil.mealList.forEach(repository::saveMeal);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        List<MealWithExceed> mealList;
        String forward = "/meals.jsp";
        String action = req.getParameter("action");
        String strId = req.getParameter("id");
        Integer id = strId != null ? Integer.parseInt(strId) : 0;
        if(action.equalsIgnoreCase("edit") | action.equalsIgnoreCase("insert")){
            Meal meal = repository.getMeal(id);
            req.setAttribute("meal", meal);
            req.setAttribute("action", action);
            forward = "/mealEdit.jsp";
            LOG.debug("redirect to mealEdit");
        } else {
            if(action.equalsIgnoreCase("delete")){
                repository.deleteMeal(id);
            }
            mealList = MealsUtil.getFilteredWithExceeded(repository.getAllMeals(), LocalTime.MIN, LocalTime.MAX, 2000);
            req.setAttribute("mealList", mealList);
            LOG.debug("redirect to meals");
        }
        req.getRequestDispatcher(forward).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        Meal meal = new Meal(
                Integer.parseInt(req.getParameter("id")),
                LocalDateTime.parse(req.getParameter("dateTime")),
                req.getParameter("description"),
                Integer.parseInt(req.getParameter("calories")));
        LOG.debug("redirect to meals");
        repository.saveMeal(meal);
        List<MealWithExceed> mealList = MealsUtil.getFilteredWithExceeded(repository.getAllMeals(), LocalTime.MIN, LocalTime.MAX, 2000);
        req.setAttribute("mealList", mealList);
        req.getRequestDispatcher("/meals.jsp").forward(req, resp);
    }
}

