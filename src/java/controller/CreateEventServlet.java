package controller;

import DAO.EventDAO;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Event;

public class CreateEventServlet extends HttpServlet
{
    @Override
    protected void doPost
    (
        HttpServletRequest request,
        HttpServletResponse response
    )
    throws ServletException, IOException
    {

        try
        {
            // ─────────────────────────────────────────
            // FETCH FORM DATA
            // ─────────────────────────────────────────

            String eventName =
                    request.getParameter("eventName");

            String sportType =
                    request.getParameter("sportType");

            String eventDate =
                    request.getParameter("eventDate");

            String lastDate =
                    request.getParameter("lastDate");

            String location =
                    request.getParameter("location");

            String description =
                    request.getParameter("description");

            double registrationFee =
                    Double.parseDouble(
                    request.getParameter("registrationFee"));

            String eventBanner =
                    request.getParameter("eventBanner");

            // ─────────────────────────────────────────
            // CURRENT DATE/TIME
            // ─────────────────────────────────────────

            String createdAt =
                    new SimpleDateFormat(
                    "dd-MM-yyyy HH:mm:ss")
                    .format(new Date());

            // ─────────────────────────────────────────
            // MODEL
            // ─────────────────────────────────────────

            Event event = new Event();

            event.setEventName(eventName);
            event.setSportType(sportType);
            event.setEventDate(eventDate);
            event.setLastDate(lastDate);
            event.setLocation(location);
            event.setDescription(description);
            event.setRegistrationFee(registrationFee);
            event.setEventBanner(eventBanner);

            // ADMIN NAME
            event.setCreatedBy("ADMIN");

            event.setCreatedAt(createdAt);

            // ─────────────────────────────────────────
            // DAO
            // ─────────────────────────────────────────

            EventDAO dao = new EventDAO();

            boolean status =
                    dao.createEvent(event);

            // ─────────────────────────────────────────
            // RESPONSE
            // ─────────────────────────────────────────

            if(status)
            {
                response.sendRedirect(
                        "AdminDashboardServlet");
            }
            else
            {
                response.getWriter()
                        .println("Event Creation Failed");
            }

        }
        catch(Exception e)
        {
            e.printStackTrace(
                    response.getWriter());
        }
    }
}