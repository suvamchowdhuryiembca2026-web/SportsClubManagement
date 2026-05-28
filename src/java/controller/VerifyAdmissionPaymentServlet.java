package controller;

import DAO.paymentDAO;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public  class VerifyAdmissionPaymentServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();

        try {

            String paymentIdStr = request.getParameter("paymentId");
            String razorpayPaymentId = request.getParameter("razorpay_payment_id");

            if (paymentIdStr == null || razorpayPaymentId == null) {
                out.print("fail");
                return;
            }

            long paymentId = Long.parseLong(paymentIdStr);

            // ✅ STEP 1: UPDATE PAYMENT ONLY
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

            // ✅ STEP 2: RETURN SUCCESS IMMEDIATELY
            out.print("success");

        } catch (Exception e) {
            e.printStackTrace();
            out.print("fail");
        }
    }
}