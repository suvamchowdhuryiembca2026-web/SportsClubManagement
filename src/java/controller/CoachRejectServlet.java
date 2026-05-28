package controller;

import DAO.coachDAO;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Coach;
import util.EmailService;

// =====================================================
//  CoachRejectServlet
//  URL: CoachRejectServlet?id=COACH_ID&reason=...
//  Called by: Admin reject button (pass reason in param)
// =====================================================
public class CoachRejectServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        try {
            long coachId = Long.parseLong(request.getParameter("id"));

            String reason = request.getParameter("reason");
            if (reason == null || reason.trim().isEmpty()) {
                reason = "Your profile did not meet the eligibility criteria.";
            }

            coachDAO dao = new coachDAO();

            // STEP 1: UPDATE STATUS TO REJECTED + SAVE REASON
            boolean updated = dao.rejectCoach(coachId, reason);

            if (updated) {

                // STEP 2: FETCH COACH PROFILE
                Coach coach = dao.getCoachById(coachId);

                // STEP 3: SEND REJECTION EMAIL
                EmailService.sendCoachRejectionMail(
                    coach.getEmail(),
                    coach.getFullName(),
                    reason
                );

                // STEP 4: REDIRECT ADMIN
                response.sendRedirect(
                    "ViewCoachServlet?id=" + coachId + "&status=rejected"
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