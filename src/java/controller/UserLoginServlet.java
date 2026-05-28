package controller;

import DAO.athleteDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Athlete;

public class UserLoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String mobile = request.getParameter("mobile");

        if (mobile == null || mobile.trim().isEmpty()) {
            response.sendRedirect("UserLogin.html?status=invalid");
            return;
        }

        mobile = mobile.trim();

        athleteDAO dao = new athleteDAO();
        Athlete athlete = dao.getAthleteByMobile(mobile);

        if (athlete == null) {
            // MOBILE NOT FOUND — back to login with error
            response.sendRedirect("UserLogin.html?status=invalid");
            return;
        }

    HttpSession oldSession = request.getSession(false);
if (oldSession != null) {
    oldSession.invalidate();
}

HttpSession session = request.getSession(true);

// 👇 ADD THESE HERE
session.setAttribute("role", "ATHLETE");
session.setAttribute("userId", athlete.getAthleteId());

session.setAttribute("athleteId", athlete.getAthleteId());
session.setAttribute("athleteName", athlete.getFullName());
session.setMaxInactiveInterval(60 * 30);

        // REDIRECT TO DASHBOARD
        response.sendRedirect("AthleteDashboardServlet");
    }
}