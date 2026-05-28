package controller;

import DAO.EventDAO;
import DAO.paymentDAO;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EventPaymentVerifyServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();

        try {

            String paymentIdStr      = request.getParameter("paymentId");
            String razorpayPaymentId = request.getParameter("razorpay_payment_id");
            String eventIdStr        = request.getParameter("eventId");
            String athleteIdStr      = request.getParameter("athleteId");

            if (paymentIdStr == null || razorpayPaymentId == null
                    || eventIdStr == null || athleteIdStr == null) {
                out.print("fail");
                return;
            }

            long paymentId = Long.parseLong(paymentIdStr);
            long eventId   = Long.parseLong(eventIdStr);
            long athleteId = Long.parseLong(athleteIdStr);

            // STEP 1: UPDATE PAYMENT STATUS TO SUCCESS
            paymentDAO pDao = new paymentDAO();

            boolean updated = pDao.updatePaymentStatus(
                paymentId,
                "SUCCESS",
                razorpayPaymentId
            );

            if (!updated) {
                out.print("fail");
                return;
            }

            // STEP 2: INSERT INTO EVENT_REGISTRATION
            EventDAO eventDAO = new EventDAO();

            String registeredAt = new java.text.SimpleDateFormat("dd-MM-yyyy")
                    .format(new java.util.Date());

            boolean registered = eventDAO.registerAthleteForEvent(
                athleteId,
                eventId,
                "PAID",
                registeredAt
            );

            if (!registered) {
                out.print("fail");
                return;
            }

            // STEP 3: RETURN SUCCESS
            out.print("success");

        } catch (Exception e) {
            e.printStackTrace();
            out.print("fail");
        }
    }
}