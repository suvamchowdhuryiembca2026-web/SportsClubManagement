package DAO;

import db.DbConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class EmailLogDAO {

    public static void insertLog(String email, String subject, String status) {

        String sql = "INSERT INTO EMAIL_LOGS " +
                     "(MAIL_ID, USER_EMAIL, SUBJECT, STATUS, SENT_AT) " +
                     "VALUES " +
                     "(EMAIL_LOG_SEQ.NEXTVAL, ?, ?, ?, " +
                     "TO_CHAR(SYSDATE, 'DD-MON-YYYY HH24:MI:SS'))";

        try (Connection con = DbConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.setString(2, subject);
            ps.setString(3, status);

            int rows = ps.executeUpdate();

            System.out.println("EmailLogDAO inserted = " + rows + 
                               " | to=" + email + " | status=" + status);

        } catch (Exception e) {
            System.err.println("EmailLogDAO FAILED: " + e.getMessage());
            e.printStackTrace();
        }
    }
}