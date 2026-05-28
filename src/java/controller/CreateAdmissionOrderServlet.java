package controller;

import DAO.paymentDAO;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Payment;
import org.json.JSONObject;
import util.IdGenerator;

public class CreateAdmissionOrderServlet extends HttpServlet {

    private static final String RAZORPAY_KEY_ID     = "rzp_test_StBIfYA8nax4IQ";
    private static final String RAZORPAY_KEY_SECRET = "Gwb40zkYRkVi24AMBM3s0zw3";
    private static final double ADMISSION_FEE       = 5000.0;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        try {

           HttpSession session = request.getSession(false);

            if (session == null) {
                out.print("{\"error\":\"session expired\"}");
                return;
            }

            Object idObj = session.getAttribute("athleteId");

            if (idObj == null) {
                out.print("{\"error\":\"athleteId missing in session\"}");
                return;
            }

            long athleteId = (Long) idObj;
            // ✅ CREATE RAZORPAY ORDER
            RazorpayClient client =
                    new RazorpayClient(RAZORPAY_KEY_ID, RAZORPAY_KEY_SECRET);

            JSONObject orderRequest = new JSONObject();
            orderRequest.put("amount", (int)(ADMISSION_FEE * 100));
            orderRequest.put("currency", "INR");
            orderRequest.put("receipt", "admission_" + athleteId);

            Order order = client.orders.create(orderRequest);
            String razorpayOrderId = order.get("id");

            // ✅ SAVE PAYMENT IN DB
            long dbPaymentId = IdGenerator.generateId();

            Payment payment = new Payment();
            payment.setPaymentId(dbPaymentId);
            payment.setAthleteId(athleteId);
            payment.setPaymentType("ADMISSION");
            payment.setAmount(ADMISSION_FEE);
            payment.setPaymentStatus("PENDING");
            payment.setRazorpayOrderId(razorpayOrderId);
            payment.setRazorpayPaymentId(null);
            Date dt = new Date(); 
            String createdAt = LocalDate.now().toString();
            payment.setPaymentDate(createdAt);

            paymentDAO dao = new paymentDAO();
            dao.createPayment(payment);

            // ✅ RESPONSE TO FRONTEND
            JSONObject resp = new JSONObject();
            resp.put("id", (String)order.get("id"));
            resp.put("amount", ((Number) order.get("amount")).intValue());
            resp.put("currency", (String)order.get("currency"));
            resp.put("db_payment_id", dbPaymentId);

            out.print(resp.toString());
            out.flush();

        } catch (Exception e) {
            e.printStackTrace();
            out.print("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }
}