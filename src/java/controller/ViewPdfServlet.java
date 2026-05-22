package controller;

import DAO.athleteDAO;

import model.Address;
import model.Athlete;
import model.AthleteCompleteProfile;
import model.Documents;
import model.Guardian;
import model.SportsProfile;

import java.io.IOException;

import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ViewPdfServlet extends HttpServlet
{

    @Override
    protected void doGet
    (
        HttpServletRequest request,
        HttpServletResponse response
    )

    throws ServletException, IOException
    {

        try
        {

            long athleteId =
            Long.parseLong(
            request.getParameter("id")
            );

            athleteDAO dao =
            new athleteDAO();

            AthleteCompleteProfile profile =
            dao.getAthleteById(athleteId);

            if(profile == null ||
               profile.getAthlete() == null)
            {
                response.getWriter()
                .println("<h2>No Athlete Found</h2>");

                return;
            }

            Athlete athlete =
            profile.getAthlete();

            Guardian guardian =
            profile.getGuardian();

            Address address =
            profile.getAddress();

            SportsProfile sports =
            profile.getSportsProfile();

            Documents docs =
            profile.getDocuments();

            // SEND DATA TO JSP

            request.setAttribute(
            "athlete",
            athlete
            );

            request.setAttribute(
            "guardian",
            guardian
            );

            request.setAttribute(
            "address",
            address
            );

            request.setAttribute(
            "sports",
            sports
            );

            request.setAttribute(
            "docs",
            docs
            );

            // FORWARD TO RESUME JSP

            request.getRequestDispatcher
            (
                "ResumeTemplate.jsp"
            )
            .forward(request, response);

        }

        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}