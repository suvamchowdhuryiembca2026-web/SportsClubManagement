package controller;

import DAO.athleteDAO;
import model.Athlete;
import util.EmailService;

import java.io.IOException;
import javax.servlet.http.*;

public class AthleteRejectServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        try {

            long athleteId = Long.parseLong(request.getParameter("id"));

            athleteDAO dao = new athleteDAO();

            // STEP 1: UPDATE STATUS TO REJECTED
            boolean updated = dao.updateAthleteStatus(athleteId, "REJECTED");

            if (updated) {

                // STEP 2: FETCH COMPLETE PROFILE
                Athlete athlete = dao
                        .getAthleteById(athleteId)
                        .getAthlete();

                // STEP 3: SEND REJECTION EMAIL
                EmailService.sendRejectionMail(
                        athlete.getEmail(),
                        athlete.getFullName(),
                        "Your profile did not meet the eligibility criteria for approval."
                );

                // STEP 4: REDIRECT ADMIN
                response.sendRedirect(
                        "ViewAthleteServlet?id=" + athleteId + "&status=rejected"
                );

            } else {

                response.sendRedirect(
                        "ViewAthleteServlet?id=" + athleteId + "&status=failed"
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}