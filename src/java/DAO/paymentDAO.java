package DAO;

import db.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.List;

import model.Payment;

public class paymentDAO
{
    private Connection con = null;


    public boolean createPayment(Payment payment)
    {
        boolean success = false;

        try
        {
            con = DbConnection.getConnection();

            String sql =
            "INSERT INTO PAYMENT " +
            "(" +
            "PAYMENT_ID, " +
            "ATHLETE_ID, " +
            "PAYMENT_TYPE, " +
            "AMOUNT, " +
            "PAYMENT_STATUS, " +
            "RAZORPAY_ORDER_ID, " +
            "RAZORPAY_PAYMENT_ID, " +
            "PAYMENT_DATE" +
            ") " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement ps =
            con.prepareStatement(sql);

            ps.setLong(1, payment.getPaymentId());

            ps.setLong(2, payment.getAthleteId());

            ps.setString(3, payment.getPaymentType());

            ps.setDouble(4, payment.getAmount());

            ps.setString(5, payment.getPaymentStatus());

            ps.setString(6, payment.getRazorpayOrderId());

            ps.setString(7, payment.getRazorpayPaymentId());

            ps.setString(8, payment.getPaymentDate());

            int rows = ps.executeUpdate();

            if(rows > 0)
            {
                success = true;
            }
        }

        catch(Exception e)
        {
            e.printStackTrace();
        }

        return success;
    }

    // UPDATE PAYMENT STATUS

    public boolean updatePaymentStatus
    (
        long paymentId,
        String paymentStatus,
        String razorpayPaymentId
    )
    {
        boolean success = false;

        try
        {
            con = DbConnection.getConnection();

            String sql =
            "UPDATE PAYMENT " +
            "SET PAYMENT_STATUS=?, " +
            "RAZORPAY_PAYMENT_ID=? " +
            "WHERE PAYMENT_ID=?";

            PreparedStatement ps =
            con.prepareStatement(sql);

            ps.setString(1, paymentStatus);

            ps.setString(2, razorpayPaymentId);

            ps.setLong(3, paymentId);

            int rows = ps.executeUpdate();

            if(rows > 0)
            {
                success = true;
            }
        }

        catch(Exception e)
        {
            e.printStackTrace();
        }

        return success;
    }

    // GET PAYMENT BY ID

    public Payment getPaymentById(long paymentId)
    {
        Payment payment = null;

        try
        {
            con = DbConnection.getConnection();

            String sql =
            "SELECT * FROM PAYMENT " +
            "WHERE PAYMENT_ID=?";

            PreparedStatement ps =
            con.prepareStatement(sql);

            ps.setLong(1, paymentId);

            ResultSet rs = ps.executeQuery();

            if(rs.next())
            {
                payment = new Payment();

                payment.setPaymentId(
                rs.getLong("PAYMENT_ID"));

                payment.setAthleteId(
                rs.getLong("ATHLETE_ID"));

                payment.setPaymentType(
                rs.getString("PAYMENT_TYPE"));

                payment.setAmount(
                rs.getDouble("AMOUNT"));

                payment.setPaymentStatus(
                rs.getString("PAYMENT_STATUS"));

                payment.setRazorpayOrderId(
                rs.getString("RAZORPAY_ORDER_ID"));

                payment.setRazorpayPaymentId(
                rs.getString("RAZORPAY_PAYMENT_ID"));

                payment.setPaymentDate(
                rs.getString("PAYMENT_DATE"));
            }
        }

        catch(Exception e)
        {
            e.printStackTrace();
        }

        return payment;
    }

    // GET PAYMENTS OF ATHLETE

    public List<Payment> getPaymentsByAthlete(long athleteId)
    {
        List<Payment> list =
        new ArrayList<Payment>();

        try
        {
            con = DbConnection.getConnection();

            String sql =
            "SELECT * FROM PAYMENT " +
            "WHERE ATHLETE_ID=? " +
            "ORDER BY PAYMENT_DATE DESC";

            PreparedStatement ps =
            con.prepareStatement(sql);

            ps.setLong(1, athleteId);

            ResultSet rs = ps.executeQuery();

            while(rs.next())
            {
                Payment payment =
                new Payment();

                payment.setPaymentId(
                rs.getLong("PAYMENT_ID"));

                payment.setAthleteId(
                rs.getLong("ATHLETE_ID"));

                payment.setPaymentType(
                rs.getString("PAYMENT_TYPE"));

                payment.setAmount(
                rs.getDouble("AMOUNT"));

                payment.setPaymentStatus(
                rs.getString("PAYMENT_STATUS"));

                payment.setRazorpayOrderId(
                rs.getString("RAZORPAY_ORDER_ID"));

                payment.setRazorpayPaymentId(
                rs.getString("RAZORPAY_PAYMENT_ID"));

                payment.setPaymentDate(
                rs.getString("PAYMENT_DATE"));

                list.add(payment);
            }
        }

        catch(Exception e)
        {
            e.printStackTrace();
        }

        return list;
    }
    // CHECK EVENT PAYMENT

public boolean hasPaidForEvent(long athleteId, long eventId)
{
    boolean paid = false;

    try
    {
        con = DbConnection.getConnection();

        String sql =
        "SELECT * FROM PAYMENT " +
        "WHERE ATHLETE_ID=? " +
        "AND PAYMENT_TYPE=? " +
        "AND PAYMENT_STATUS='SUCCESS'";

        PreparedStatement ps =
        con.prepareStatement(sql);

        ps.setLong(1, athleteId);

        ps.setString(2, "EVENT_" + eventId);

        ResultSet rs = ps.executeQuery();

        if(rs.next())
        {
            paid = true;
        }
    }

    catch(Exception e)
    {
        e.printStackTrace();
    }

    return paid;
}
}