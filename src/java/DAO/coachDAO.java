package DAO;

import db.DbConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Coach;
import model.DashboardCoachRowMdl;

public class coachDAO {

    public boolean registerCoach(Coach coach) {

        boolean success = false;

        try (Connection con = DbConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(
                "INSERT INTO COACH " +
                "(COACH_ID, FULL_NAME, EMAIL, MOBILE, GENDER, DOB, BLOOD_GROUP, " +
                "EXPERIENCE, SPECIALIZATION, COACHING_LEVEL, QUALIFICATION, " +
                "CLUB_NAME, AVAILABILITY, ACHIEVEMENTS, " +
                "STATE, DISTRICT, CITY, PIN_CODE, FULL_ADDRESS, " +
                "PHOTO_PATH, CERTIFICATE_PATH, ID_PROOF_PATH, QUAL_DOC_PATH, " +
                "STATUS, CREATED_AT) " +
                "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,'PENDING',?)")) {

            ps.setLong(1,    coach.getCoachId());
            ps.setString(2,  coach.getFullName());
            ps.setString(3,  coach.getEmail());
            ps.setString(4,  coach.getMobile());
            ps.setString(5,  coach.getGender());
            ps.setString(6,  coach.getDob());
            ps.setString(7,  coach.getBloodGroup());
            ps.setInt(8,     coach.getExperience());
            ps.setString(9,  coach.getSpecialization());
            ps.setString(10, coach.getCoachingLevel());
            ps.setString(11, coach.getQualification());
            ps.setString(12, coach.getClubName());
            ps.setString(13, coach.getAvailability());
            ps.setString(14, coach.getAchievements());
            ps.setString(15, coach.getState());
            ps.setString(16, coach.getDistrict());
            ps.setString(17, coach.getCity());
            ps.setString(18, coach.getPinCode());
            ps.setString(19, coach.getFullAddress());
            ps.setString(20, coach.getPhotoPath());
            ps.setString(21, coach.getCertificatePath());
            ps.setString(22, coach.getIdProofPath());
            ps.setString(23, coach.getQualDocPath());
            ps.setString(24, coach.getCreatedAt());

            success = ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return success;
    }

    // FIX: use try-with-resources for PreparedStatement and ResultSet
    public Coach getCoachById(long coachId) {

        Coach coach = null;

        try (Connection con = DbConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(
                "SELECT * FROM COACH WHERE COACH_ID = ?")) {

            ps.setLong(1, coachId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    coach = mapRow(rs);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return coach;
    }

    public boolean updateCoachStatus(long coachId, String status) {

        boolean updated = false;

        try (Connection con = DbConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(
                "UPDATE COACH SET STATUS = ? WHERE COACH_ID = ?")) {

            ps.setString(1, status);
            ps.setLong(2, coachId);

            int rows = ps.executeUpdate();
            System.out.println("Coach status updated rows = " + rows);
            updated = rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return updated;
    }

    public boolean rejectCoach(long coachId, String reason) {

        boolean updated = false;

        try (Connection con = DbConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(
                "UPDATE COACH SET STATUS = 'REJECTED', REJECTION_REASON = ? WHERE COACH_ID = ?")) {

            ps.setString(1, reason);
            ps.setLong(2, coachId);
            updated = ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return updated;
    }

    public List<Coach> getAllCoaches() {

        List<Coach> list = new ArrayList<>();

        try (Connection con = DbConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(
                "SELECT * FROM COACH ORDER BY CREATED_AT DESC");
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapRow(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public List<Coach> getCoachesByStatus(String status) {

        List<Coach> list = new ArrayList<>();

        try (Connection con = DbConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(
                "SELECT * FROM COACH WHERE STATUS = ? ORDER BY CREATED_AT DESC")) {

            ps.setString(1, status);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // FIX: was leaking Connection — now uses try-with-resources properly
    public List<DashboardCoachRowMdl> getDashboardCoachList() {

        List<DashboardCoachRowMdl> list = new ArrayList<>();

        String sql =
            "SELECT coach_id, full_name, mobile, " +
            "specialization, coaching_level, experience, status " +
            "FROM coach " +
            "ORDER BY created_at DESC";

        try (Connection con = DbConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                DashboardCoachRowMdl row = new DashboardCoachRowMdl();
                row.setCoachId(rs.getLong("coach_id"));
                row.setFullName(rs.getString("full_name"));
                row.setMobile(rs.getString("mobile"));
                row.setCoachingLevel(rs.getString("coaching_level"));
                row.setSpecialization(rs.getString("specialization"));
                row.setExperience(rs.getString("experience"));
                row.setStatus(rs.getString("status"));
                list.add(row);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    private Coach mapRow(ResultSet rs) throws Exception {

        Coach c = new Coach();
        c.setCoachId(rs.getLong("COACH_ID"));
        c.setFullName(rs.getString("FULL_NAME"));
        c.setEmail(rs.getString("EMAIL"));
        c.setMobile(rs.getString("MOBILE"));
        c.setGender(rs.getString("GENDER"));
        c.setDob(rs.getString("DOB"));
        c.setBloodGroup(rs.getString("BLOOD_GROUP"));
        c.setExperience(rs.getInt("EXPERIENCE"));
        c.setSpecialization(rs.getString("SPECIALIZATION"));
        c.setCoachingLevel(rs.getString("COACHING_LEVEL"));
        c.setQualification(rs.getString("QUALIFICATION"));
        c.setClubName(rs.getString("CLUB_NAME"));
        c.setAvailability(rs.getString("AVAILABILITY"));
        c.setAchievements(rs.getString("ACHIEVEMENTS"));
        c.setState(rs.getString("STATE"));
        c.setDistrict(rs.getString("DISTRICT"));
        c.setCity(rs.getString("CITY"));
        c.setPinCode(rs.getString("PIN_CODE"));
        c.setFullAddress(rs.getString("FULL_ADDRESS"));
        c.setPhotoPath(rs.getString("PHOTO_PATH"));
        c.setCertificatePath(rs.getString("CERTIFICATE_PATH"));
        c.setIdProofPath(rs.getString("ID_PROOF_PATH"));
        c.setQualDocPath(rs.getString("QUAL_DOC_PATH"));
        c.setStatus(rs.getString("STATUS"));
        c.setCreatedAt(rs.getString("CREATED_AT"));
        c.setRejectionReason(rs.getString("REJECTION_REASON"));
        return c;
    }
}