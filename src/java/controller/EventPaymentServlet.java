package controller;

import DAO.EventDAO;
import DAO.paymentDAO;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Event;
import model.Payment;

import org.json.JSONObject;

import util.IdGenerator;

public class EventPaymentServlet extends HttpServlet
{

    private static final String RAZORPAY_KEY_ID =
            "rzp_test_StBIfYA8nax4IQ";

    private static final String RAZORPAY_KEY_SECRET =
            "Gwb40zkYRkVi24AMBM3s0zw3";

    @Override
    protected void doPost
    (
            HttpServletRequest request,
            HttpServletResponse response
    )
            throws ServletException, IOException
    {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();

        try
        {

            // ================= SESSION CHECK =================

            HttpSession session = request.getSession(false);

            if(session == null)
            {
                out.print("{\"error\":\"Session expired\"}");
                return;
            }

            Object athleteObj =
                    session.getAttribute("athleteId");

            if(athleteObj == null)
            {
                out.print("{\"error\":\"Athlete not logged in\"}");
                return;
            }

            long athleteId = (Long) athleteObj;

            // ================= GET EVENT =================

            long eventId =
                    Long.parseLong(request.getParameter("eventId"));
            System.out.println("EVENT ID RECEIVED = " + eventId);

            EventDAO eventDAO = new EventDAO();

            Event event =
                    eventDAO.getEventById(eventId);
            System.out.println(event);

            if(event == null)
            {
                out.print("{\"error\":\"Event not found\"}");
                return;
            }

            // ================= CHECK ADMISSION PAYMENT =================

            paymentDAO paymentDao = new paymentDAO();

            boolean admissionPaid = false;

            for(Payment p :
                    paymentDao.getPaymentsByAthlete(athleteId))
            {

                if(
                        "ADMISSION".equalsIgnoreCase(
                                p.getPaymentType()
                        )
                                &&
                                "SUCCESS".equalsIgnoreCase(
                                        p.getPaymentStatus()
                                )
                )
                {
                    admissionPaid = true;
                    break;
                }
            }

            if(!admissionPaid)
            {
                out.print("{\"error\":\"Complete admission payment first\"}");
                return;
            }

            // ================= CREATE RAZORPAY ORDER =================

            RazorpayClient client =
                    new RazorpayClient(
                            RAZORPAY_KEY_ID,
                            RAZORPAY_KEY_SECRET
                    );

            JSONObject orderRequest =
                    new JSONObject();

            orderRequest.put(
                    "amount",
                    (int)(event.getRegistrationFee() * 100)
            );

            orderRequest.put("currency", "INR");

            orderRequest.put(
                    "receipt",
                    "event_" + athleteId + "_" + eventId
            );

            Order order =
                    client.orders.create(orderRequest);

            String razorpayOrderId =
                    order.get("id");

            // ================= SAVE PAYMENT =================

            long paymentId =
                    IdGenerator.generateId();

            Payment payment =
                    new Payment();

            payment.setPaymentId(paymentId);

            payment.setAthleteId(athleteId);

            payment.setPaymentType(
                    "EVENT_" + event.getEventId()
            );

            payment.setAmount(
                    event.getRegistrationFee()
            );

            payment.setPaymentStatus("PENDING");

            payment.setRazorpayOrderId(
                    razorpayOrderId
            );

            payment.setRazorpayPaymentId(null);

            payment.setPaymentDate(
                    LocalDate.now().toString()
            );

            paymentDao.createPayment(payment);

            // ================= RESPONSE =================

            JSONObject resp =
                    new JSONObject();

            resp.put(
                    "id",
                    (String)order.get("id")
            );

            resp.put(
                    "amount",
                    ((Number)order.get("amount")).intValue()
            );

            resp.put(
                    "currency",
                    (String)order.get("currency")
            );

            resp.put(
                    "db_payment_id",
                    paymentId
            );

            resp.put(
                    "event_name",
                    event.getEventName()
            );

            out.print(resp.toString());

            out.flush();

        }

        catch(Exception e)
        {
            e.printStackTrace();

            out.print(
                    "{\"error\":\"" +
                            e.getMessage() +
                            "\"}"
            );
        }
    }
}