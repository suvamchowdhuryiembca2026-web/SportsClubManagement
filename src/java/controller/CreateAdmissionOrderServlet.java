//package controller;
//
//import DAO.paymentDAO;
//
//import com.razorpay.Order;
//import com.razorpay.RazorpayClient;
//
//import java.io.IOException;
//import java.io.PrintWriter;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import model.Payment;
//
//import org.json.JSONObject;
//
//import util.IdGenerator;
//
//public class CreateAdmissionOrderServlet
//extends HttpServlet
//{
//    @Override
//    protected void doPost
//    (
//        HttpServletRequest request,
//        HttpServletResponse response
//    )
//    throws ServletException, IOException
//    {
//        try
//        {
//            long athleteId =
//            Long.parseLong
//            (
//                request.getParameter("athleteId")
//            );
//
//            double amount = 5000;
//
//            RazorpayClient client =
//            new RazorpayClient
//            (
//                "rzp_test_St1xicWA9FfwBv",
//                "gBpY6uXis3LYp8txIclKrvAZ"
//            );
//
//            JSONObject orderRequest =
//            new JSONObject();
//
//            orderRequest.put
//            (
//                "amount",
//                amount * 100
//            );
//
//            orderRequest.put
//            (
//                "currency",
//                "INR"
//            );
//
//            orderRequest.put
//            (
//                "receipt",
//                "receipt_" + athleteId
//            );
//
//            Order order =
//            client.orders.create(orderRequest);
//
//            String razorpayOrderId =
//            order.get("id");
//
//            Payment payment =
//            new Payment();
//
//            long dbPaymentId =
//            IdGenerator.generateId();
//
//            payment.setPaymentId
//            (
//                dbPaymentId
//            );
//
//            payment.setAthleteId
//            (
//                athleteId
//            );
//
//            payment.setPaymentType
//            (
//                "ADMISSION"
//            );
//
//            payment.setAmount
//            (
//                amount
//            );
//
//            payment.setPaymentStatus
//            (
//                "PENDING"
//            );
//
//            payment.setRazorpayOrderId
//            (
//                razorpayOrderId
//            );
//
//            payment.setRazorpayPaymentId
//            (
//                null
//            );
//
//            payment.setPaymentDate
//            (
//                String.valueOf
//                (
//                    System.currentTimeMillis()
//                )
//            );
//
//            paymentDAO dao =
//            new paymentDAO();
//
//            dao.createPayment(payment);
//
//            // IMPORTANT
//            // SEND DB PAYMENT ID TO FRONTEND
//
//            response.setContentType
//(
//    "application/json"
//);
//
//JSONObject responseObj =
//new JSONObject();
//
//responseObj.put
//(
//    "id",
//    order.get("id")
//);
//
//responseObj.put
//(
//    "amount",
//    order.get("amount")
//);
//
//responseObj.put
//(
//    "currency",
//    order.get("currency")
//);
//
//responseObj.put
//(
//    "db_payment_id",
//    dbPaymentId
//);
//
//PrintWriter out =
//response.getWriter();
//
//out.print
//(
//    responseObj.toString()
//);
//
//out.flush();
//        }
//
//        catch(Exception e)
//        {
//            e.printStackTrace();
//        }
//    }
//}