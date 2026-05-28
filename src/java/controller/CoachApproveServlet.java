package controller;

import DAO.coachDAO;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Coach;
import util.EmailService;

// =====================================================
//  CoachApproveServlet
//  URL: CoachApproveServlet?id=COACH_ID
//  Called by: Admin approve button
// =====================================================
public class CoachApproveServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        try {
            long coachId = Long.parseLong(request.getParameter("id"));

            coachDAO dao = new coachDAO();

            // STEP 1: UPDATE STATUS TO APPROVED
            boolean updated = dao.updateCoachStatus(coachId, "APPROVED");

            if (updated) {

                // STEP 2: FETCH COACH PROFILE
                Coach coach = dao.getCoachById(coachId);

                // STEP 3: SEND APPROVAL EMAIL
                EmailService.sendCoachApprovalMail(
                    coach.getEmail(),
                    coach.getFullName(),
                    coach.getSpecialization()
                );

                // STEP 4: REDIRECT ADMIN
                response.sendRedirect(
                    "ViewCoachServlet?id=" + coachId + "&status=approved"
                );

            } else {
                response.sendRedirect(
                    "ViewCoachServlet?id=" + coachId + "&status=failed"
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}