package DAO;

import db.DbConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Event;
import model.EventRegistration;

public class EventDAO {

    // ─────────────────────────────────────────────────────────────────────────
    // ADMIN: CREATE EVENT
    // ─────────────────────────────────────────────────────────────────────────

    public boolean createEvent(Event event) {

        String sql =
            "INSERT INTO EVENTS (EVENT_ID, EVENT_NAME, SPORT_TYPE, EVENT_DATE, " +
            "LOCATION, DESCRIPTION, REGISTRATION_FEE, LAST_DATE, EVENT_BANNER, " +
            "CREATED_BY, CREATED_AT) " +
            "VALUES (EVENT_SEQ.NEXTVAL,?,?,?,?,?,?,?,?,?,?)";

        try (Connection con = DbConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, event.getEventName());
            ps.setString(2, event.getSportType());
            ps.setString(3, event.getEventDate());
            ps.setString(4, event.getLocation());
            ps.setString(5, event.getDescription());
            ps.setDouble(6, event.getRegistrationFee());
            ps.setString(7, event.getLastDate());
            ps.setString(8, event.getEventBanner());
            ps.setString(9, event.getCreatedBy());
            ps.setString(10, event.getCreatedAt());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // ─────────────────────────────────────────────────────────────────────────
    // ADMIN: GET ALL EVENTS (for admin dashboard tab)
    // ─────────────────────────────────────────────────────────────────────────

    public List<Event> getAllEvents() {

        List<Event> list = new ArrayList<>();

        String sql = "SELECT * FROM EVENTS ORDER BY CREATED_AT DESC";

        try (Connection con = DbConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapEvent(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // ─────────────────────────────────────────────────────────────────────────
    // ATHLETE: GET UPCOMING EVENTS VISIBLE TO THIS ATHLETE
    // Rule: only events created AFTER the athlete's registration date
    //       AND whose LAST_DATE has not passed (open for registration)
    // ─────────────────────────────────────────────────────────────────────────

    public List<Event> getUpcomingEventsForAthlete(String athleteCreatedAt) {

        List<Event> list = new ArrayList<>();

        // Events created after athlete joined AND last_date >= today
        String sql =
            "SELECT * FROM EVENTS " +
            "WHERE CREATED_AT >= ? " +
            "AND LAST_DATE >= TO_CHAR(SYSDATE,'DD-MM-YYYY') " +
            "ORDER BY EVENT_DATE ASC";

        try (Connection con = DbConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, athleteCreatedAt);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapEvent(rs));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // ─────────────────────────────────────────────────────────────────────────
    // ATHLETE: GET REGISTERED EVENTS (past transactions on athlete dashboard)
    // ─────────────────────────────────────────────────────────────────────────

    public List<EventRegistration> getRegistrationsByAthlete(long athleteId) {

        List<EventRegistration> list = new ArrayList<>();

        String sql =
            "SELECT er.REG_ID, er.EVENT_ID, er.USER_ID, er.USER_TYPE, " +
            "er.PAYMENT_STATUS, er.REGISTRATION_STATUS, er.REGISTERED_AT, " +
            "e.EVENT_NAME, e.SPORT_TYPE, e.EVENT_DATE, e.LOCATION, e.REGISTRATION_FEE " +
            "FROM EVENT_REGISTRATION er " +
            "JOIN EVENTS e ON er.EVENT_ID = e.EVENT_ID " +
            "WHERE er.USER_ID = ? AND er.USER_TYPE = 'ATHLETE' " +
            "ORDER BY er.REGISTERED_AT DESC";

        try (Connection con = DbConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, athleteId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    EventRegistration reg = new EventRegistration();
                    reg.setRegId(rs.getLong("REG_ID"));
                    reg.setEventId(rs.getLong("EVENT_ID"));
                    reg.setUserId(rs.getLong("USER_ID"));
                    reg.setUserType(rs.getString("USER_TYPE"));
                    reg.setPaymentStatus(rs.getString("PAYMENT_STATUS"));
                    reg.setRegistrationStatus(rs.getString("REGISTRATION_STATUS"));
                    reg.setRegisteredAt(rs.getString("REGISTERED_AT"));
                    // joined fields
                    reg.setEventName(rs.getString("EVENT_NAME"));
                    reg.setSportType(rs.getString("SPORT_TYPE"));
                    reg.setEventDate(rs.getString("EVENT_DATE"));
                    reg.setLocation(rs.getString("LOCATION"));
                    reg.setRegistrationFee(rs.getDouble("REGISTRATION_FEE"));
                    list.add(reg);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // ─────────────────────────────────────────────────────────────────────────
    // ATHLETE: CHECK IF ALREADY REGISTERED FOR AN EVENT
    // ─────────────────────────────────────────────────────────────────────────

    public boolean isAlreadyRegistered(long athleteId, long eventId) {

        String sql =
            "SELECT COUNT(*) FROM EVENT_REGISTRATION " +
            "WHERE USER_ID = ? AND EVENT_ID = ? AND USER_TYPE = 'ATHLETE'";

        try (Connection con = DbConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, athleteId);
            ps.setLong(2, eventId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1) > 0;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // ─────────────────────────────────────────────────────────────────────────
    // ATHLETE: REGISTER FOR EVENT (called after payment confirmation)
    // ─────────────────────────────────────────────────────────────────────────

    public boolean registerAthleteForEvent(long athleteId, long eventId,
                                           String paymentStatus, String registeredAt) {

        String sql =
            "INSERT INTO EVENT_REGISTRATION " +
            "(REG_ID, EVENT_ID, USER_ID, USER_TYPE, PAYMENT_STATUS, " +
            "REGISTRATION_STATUS, REGISTERED_AT) " +
            "VALUES (EVENT_REG_SEQ.NEXTVAL,?,?,'ATHLETE',?,?,?)";

        try (Connection con = DbConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, eventId);
            ps.setLong(2, athleteId);
            ps.setString(3, paymentStatus);                          // PAID / PENDING
            ps.setString(4, "REGISTERED");
            ps.setString(5, registeredAt);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // ─────────────────────────────────────────────────────────────────────────
    // ADMIN: GET REGISTRATIONS FOR A SPECIFIC EVENT
    // ─────────────────────────────────────────────────────────────────────────

    public List<EventRegistration> getRegistrationsByEvent(long eventId) {

        List<EventRegistration> list = new ArrayList<>();

        String sql =
            "SELECT er.*, e.EVENT_NAME, e.SPORT_TYPE, e.EVENT_DATE, " +
            "e.LOCATION, e.REGISTRATION_FEE " +
            "FROM EVENT_REGISTRATION er " +
            "JOIN EVENTS e ON er.EVENT_ID = e.EVENT_ID " +
            "WHERE er.EVENT_ID = ? " +
            "ORDER BY er.REGISTERED_AT DESC";

        try (Connection con = DbConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, eventId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    EventRegistration reg = new EventRegistration();
                    reg.setRegId(rs.getLong("REG_ID"));
                    reg.setEventId(rs.getLong("EVENT_ID"));
                    reg.setUserId(rs.getLong("USER_ID"));
                    reg.setUserType(rs.getString("USER_TYPE"));
                    reg.setPaymentStatus(rs.getString("PAYMENT_STATUS"));
                    reg.setRegistrationStatus(rs.getString("REGISTRATION_STATUS"));
                    reg.setRegisteredAt(rs.getString("REGISTERED_AT"));
                    reg.setEventName(rs.getString("EVENT_NAME"));
                    reg.setSportType(rs.getString("SPORT_TYPE"));
                    reg.setEventDate(rs.getString("EVENT_DATE"));
                    reg.setLocation(rs.getString("LOCATION"));
                    reg.setRegistrationFee(rs.getDouble("REGISTRATION_FEE"));
                    list.add(reg);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // ─────────────────────────────────────────────────────────────────────────
    // PAYMENT TABLE: log event payment (extends existing payment flow)
    // PURPOSE = 'EVENT', REF_ID = eventId
    // ─────────────────────────────────────────────────────────────────────────

    public boolean logEventPayment(long athleteId, long eventId,
                                   double amount, String txnId,
                                   String paidAt) {

        // Adjust column names to match YOUR existing PAYMENT table structure
        String sql =
            "INSERT INTO PAYMENT " +
            "(PAYMENT_ID, ATHLETE_ID, AMOUNT, TXN_ID, PAYMENT_STATUS, " +
            "PAYMENT_PURPOSE, REF_ID, PAID_AT) " +
            "VALUES (PAYMENT_SEQ.NEXTVAL,?,?,?,'SUCCESS','EVENT',?,?)";

        try (Connection con = DbConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1,   athleteId);
            ps.setDouble(2, amount);
            ps.setString(3, txnId);
            ps.setLong(4,   eventId);
            ps.setString(5, paidAt);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // ─────────────────────────────────────────────────────────────────────────
    // GET SINGLE EVENT BY ID
    // ─────────────────────────────────────────────────────────────────────────

   public Event getEventById(long eventId)
{
    Event event = null;

    try
    {
        Connection con = DbConnection.getConnection();

        String sql =
        "SELECT * FROM EVENTS WHERE EVENT_ID=?";

        PreparedStatement ps =
        con.prepareStatement(sql);

        ps.setLong(1, eventId);

        ResultSet rs = ps.executeQuery();

        if(rs.next())
        {
            event = new Event();

            event.setEventId(
            rs.getLong("EVENT_ID"));

            event.setEventName(
            rs.getString("EVENT_NAME"));

            event.setDescription(
            rs.getString("DESCRIPTION"));

            event.setSportType(
            rs.getString("SPORT_TYPE"));

            event.setEventDate(
            rs.getString("EVENT_DATE"));

            event.setLocation(
            rs.getString("LOCATION"));

            event.setRegistrationFee(
            rs.getDouble("REGISTRATION_FEE"));

            event.setEventBanner(
            rs.getString("EVENT_BANNER"));
        }
    }

    catch(Exception e)
    {
        e.printStackTrace();
    }

    return event;
}
    public boolean isEventAlreadyRegistered(long athleteId, long eventId)
{
    boolean exists = false;

    try
    {
        Connection con = DbConnection.getConnection();

        String query =
                "SELECT * FROM payment " +
                "WHERE athlete_id=? " +
                "AND event_id=? " +
                "AND payment_type='EVENT_REGISTRATION' " +
                "AND payment_status='SUCCESS'";

        PreparedStatement ps = con.prepareStatement(query);

        ps.setLong(1, athleteId);
        ps.setLong(2, eventId);

        ResultSet rs = ps.executeQuery();

        if(rs.next())
        {
            exists = true;
        }

    }
    catch(Exception e)
    {
        e.printStackTrace();
    }

    return exists;
}

    
    private Event mapEvent(ResultSet rs) throws Exception {
        Event e = new Event();
        e.setEventId(rs.getLong("EVENT_ID"));
        e.setEventName(rs.getString("EVENT_NAME"));
        e.setSportType(rs.getString("SPORT_TYPE"));
        e.setEventDate(rs.getString("EVENT_DATE"));
        e.setLocation(rs.getString("LOCATION"));
        e.setDescription(rs.getString("DESCRIPTION"));
        e.setRegistrationFee(rs.getDouble("REGISTRATION_FEE"));
        e.setLastDate(rs.getString("LAST_DATE"));
        e.setEventBanner(rs.getString("EVENT_BANNER"));
        e.setCreatedBy(rs.getString("CREATED_BY"));
        e.setCreatedAt(rs.getString("CREATED_AT"));
        return e;
    }
}