package controller;

import model.Athlete;
import util.EmailService;
import DAO.athleteDAO;
import java.io.IOException;
import javax.servlet.http.*;
import model.AthleteCompleteProfile;

public class AthleteApproveServlet extends HttpServlet 
{

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        try {
            long athleteId = Long.parseLong(request.getParameter("id"));

            athleteDAO dao = new athleteDAO();

            boolean updated = dao.updateAthleteStatus(athleteId, "APPROVED");

            if (updated) {

    AthleteCompleteProfile profile = dao.getAthleteById(athleteId);

Athlete athlete = profile.getAthlete();
String email = athlete.getEmail();
String name = athlete.getFullName();
    EmailService.sendApprovalMail(email,name);

    response.sendRedirect(
        "ViewAthleteServlet?id=" + athleteId + "&status=approved"
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