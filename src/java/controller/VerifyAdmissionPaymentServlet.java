package controller;

import DAO.paymentDAO;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class VerifyAdmissionPaymentServlet
extends HttpServlet
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
            long paymentId =
            Long.parseLong(
            request.getParameter("paymentId")
            );

            String razorpayPaymentId =
            request.getParameter("razorpay_payment_id");

            String razorpayOrderId =
            request.getParameter("razorpay_order_id");

            String razorpaySignature =
            request.getParameter("razorpay_signature");

            /*
             * IMPORTANT
             *
             * In production:
             * Verify signature using Razorpay utility
             *
             * For now:
             * We assume payment is successful
             */

            paymentDAO dao =
            new paymentDAO();

            boolean updated =
            dao.updatePaymentStatus
            (
                paymentId,
                "SUCCESS",
                razorpayPaymentId
            );

            if(updated)
            {
                response.sendRedirect
                (
                    "paymentSuccess.html"
                );
            }

            else
            {
                response.sendRedirect
                (
                    "paymentFailed.html"
                );
            }
        }

        catch(Exception e)
        {
            e.printStackTrace();

            response.sendRedirect
            (
                "paymentFailed.html"
            );
        }
    }
}