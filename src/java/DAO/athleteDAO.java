package DAO;

import db.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import model.Address;
import model.Athlete;
import model.Documents;
import model.Guardian;
import model.SportsProfile;

public class athleteDAO 
{
    private Connection con = null;
    public boolean registerAthlete(
            Athlete athlete,
            Guardian guardian,
            Address address,
            SportsProfile sports,
            Documents docs
    ) 
    {

        boolean success = false;

        try {
            con =  DbConnection.getConnection();
            con.setAutoCommit(false); 

            
            String sql1 =
                "INSERT INTO ATHLETE " +
                "(ATHLETE_ID, FULL_NAME, DOB, AGE, GENDER, MOBILE, EMAIL, BLOOD_GROUP, STATUS, CREATED_AT) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement ps1 = con.prepareStatement(sql1);
            ps1.setLong(1, athlete.getAthleteId());
            ps1.setString(2, athlete.getFullName());
            ps1.setDate(3, new java.sql.Date(athlete.getDob().getTime()));
            ps1.setInt(4, athlete.getAge());
            ps1.setString(5, athlete.getGender());
            ps1.setString(6, athlete.getMobile());
            ps1.setString(7, athlete.getEmail());
            ps1.setString(8, athlete.getBloodGroup());
            ps1.setString(9, "PENDING");
            ps1.setString(10,athlete.getCreatedAt());
            ps1.executeUpdate();

            long id = athlete.getAthleteId();

            // 2. GUARDIAN
            String sql2 =
                "INSERT INTO GUARDIAN VALUES (?,?,?, ?, ?, ?)";

            PreparedStatement ps2 = con.prepareStatement(sql2);
            ps2.setLong(1, guardian.getGuardianId());
            ps2.setLong(2, id);
            ps2.setString(3, guardian.getGuardianName());
            ps2.setString(4, guardian.getRelation());
            ps2.setString(5, guardian.getMobile());
            ps2.setString(6, guardian.getEmergencyContact());

            ps2.executeUpdate();

            // 3. ADDRESS
            String sql3 =
                "INSERT INTO ADDRESS VALUES (?,?,?,?,?,?,?)";

            PreparedStatement ps3 = con.prepareStatement(sql3);
            ps3.setLong(1, address.getAddressId());
            ps3.setLong(2, id);
            ps3.setString(3, address.getState());
            ps3.setString(4, address.getDistrict());
            ps3.setString(5, address.getCity());
            ps3.setString(6, address.getPinCode());
            ps3.setString(7, address.getFullAddress());

            ps3.executeUpdate();

            // 4. SPORTS
            String sql4 =
                "INSERT INTO SPORTS_PROFILE VALUES (?,?,?,?,?,?)";

            PreparedStatement ps4 = con.prepareStatement(sql4);
            ps4.setLong(1, sports.getProfileId());
            ps4.setLong(2, id);
            ps4.setString(3, sports.getSportType());
            ps4.setString(4, sports.getCategory());
            ps4.setString(5, sports.getTrainingLevel());
            ps4.setString(6, sports.getClubName());

            ps4.executeUpdate();

            // 5. DOCUMENTS
            String sql5 =
                "INSERT INTO DOCUMENTS VALUES (?,?,?,?,?)";

            PreparedStatement ps5 = con.prepareStatement(sql5);
            ps5.setLong(1, docs.getDocId());
            ps5.setLong(2, id);
            ps5.setString(3, docs.getPhotoPath());
            ps5.setString(4, docs.getBirthCertPath());
            ps5.setString(5, docs.getIdProofPath());

            ps5.executeUpdate();

            con.commit();
            success = true;

        } catch (Exception e) {
    e.printStackTrace();
    try {con.rollback(); } catch(Exception ex) { ex.printStackTrace(); }
    return false;
}

        return success;
    }
}