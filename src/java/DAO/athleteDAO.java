package DAO;

import db.DbConnection;
import model.AthleteCompleteProfile;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Address;
import model.Athlete;
import model.DashBoardAthleteRowMdl;
import model.DashboardStats;
import model.Documents;
import model.Guardian;
import model.SportsProfile;

public class athleteDAO {

    private Connection con = null;

    // -------------------------------------------------------
    // 1. REGISTER ATHLETE
    // Called by: AthleteRegisterservlet
    // -------------------------------------------------------
    public boolean registerAthlete(
            Athlete athlete,
            Guardian guardian,
            Address address,
            SportsProfile sports,
            Documents docs) {

        boolean success = false;

        try {
            con = DbConnection.getConnection();
            con.setAutoCommit(false);

            String sql1 =
                "INSERT INTO ATHLETE " +
                "(ATHLETE_ID, FULL_NAME, DOB, AGE, GENDER, MOBILE, EMAIL, BLOOD_GROUP, STATUS, CREATED_AT) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement ps1 = con.prepareStatement(sql1);
            ps1.setLong(1,    athlete.getAthleteId());
            ps1.setString(2,  athlete.getFullName());
            ps1.setDate(3,    new java.sql.Date(athlete.getDob().getTime()));
            ps1.setInt(4,     athlete.getAge());
            ps1.setString(5,  athlete.getGender());
            ps1.setString(6,  athlete.getMobile());
            ps1.setString(7,  athlete.getEmail());
            ps1.setString(8,  athlete.getBloodGroup());
            ps1.setString(9,  "PENDING");
            ps1.setString(10, athlete.getCreatedAt());
            ps1.executeUpdate();

            long id = athlete.getAthleteId();

            String sql2 = "INSERT INTO GUARDIAN VALUES (?,?,?,?,?,?)";
            PreparedStatement ps2 = con.prepareStatement(sql2);
            ps2.setLong(1,   guardian.getGuardianId());
            ps2.setLong(2,   id);
            ps2.setString(3, guardian.getGuardianName());
            ps2.setString(4, guardian.getRelation());
            ps2.setString(5, guardian.getMobile());
            ps2.setString(6, guardian.getEmergencyContact());
            ps2.executeUpdate();

            String sql3 = "INSERT INTO ADDRESS VALUES (?,?,?,?,?,?,?)";
            PreparedStatement ps3 = con.prepareStatement(sql3);
            ps3.setLong(1,   address.getAddressId());
            ps3.setLong(2,   id);
            ps3.setString(3, address.getState());
            ps3.setString(4, address.getDistrict());
            ps3.setString(5, address.getCity());
            ps3.setString(6, address.getPinCode());
            ps3.setString(7, address.getFullAddress());
            ps3.executeUpdate();

            String sql4 = "INSERT INTO SPORTS_PROFILE VALUES (?,?,?,?,?,?)";
            PreparedStatement ps4 = con.prepareStatement(sql4);
            ps4.setLong(1,   sports.getProfileId());
            ps4.setLong(2,   id);
            ps4.setString(3, sports.getSportType());
            ps4.setString(4, sports.getCategory());
            ps4.setString(5, sports.getTrainingLevel());
            ps4.setString(6, sports.getClubName());
            ps4.executeUpdate();

            String sql5 = "INSERT INTO DOCUMENTS VALUES (?,?,?,?,?)";
            PreparedStatement ps5 = con.prepareStatement(sql5);
            ps5.setLong(1,   docs.getDocId());
            ps5.setLong(2,   id);
            ps5.setString(3, docs.getPhotoPath());
            ps5.setString(4, docs.getBirthCertPath());
            ps5.setString(5, docs.getIdProofPath());
            ps5.executeUpdate();

            con.commit();
            success = true;

        } catch (Exception e) {
            e.printStackTrace();
            try { con.rollback(); } catch (Exception ex) { ex.printStackTrace(); }
            return false;
        }

        return success;
    }

    // -------------------------------------------------------
    // 2. GET FULL ATHLETE PROFILE BY ID (5-table JOIN)
    // Called by: ViewAthleteServlet, AthleteDashboardServlet,
    //            ViewPdfServlet, VerifyAdmissionPaymentServlet
    // Returns:   AthleteCompleteProfile (athlete + guardian +
    //            address + sports + documents)
    // -------------------------------------------------------
    public AthleteCompleteProfile getAthleteById(long athleteId) {

        AthleteCompleteProfile profile = new AthleteCompleteProfile();

        try {
            con = DbConnection.getConnection();

           String sql =
    "SELECT " +

    "A.ATHLETE_ID, " +
    "A.FULL_NAME, " +
    "A.DOB, " +
    "A.AGE, " +
    "A.GENDER, " +
    "A.MOBILE AS ATHLETE_MOBILE, " +
    "A.EMAIL, " +
    "A.BLOOD_GROUP, " +
    "A.STATUS, " +
    "A.CREATED_AT, " +

    "G.GUARDIAN_NAME, " +
    "G.RELATION, " +
    "G.MOBILE AS GUARDIAN_MOBILE, " +
    "G.EMERGENCY_CONTACT, " +

    "AD.STATE, " +
    "AD.DISTRICT, " +
    "AD.CITY, " +
    "AD.PIN_CODE, " +
    "AD.FULL_ADDRESS, " +

    "S.SPORT_TYPE, " +
    "S.CATEGORY, " +
    "S.TRAINING_LEVEL, " +
    "S.CLUB_NAME, " +

    "D.PHOTO_PATH, " +
    "D.BIRTH_CERT_PATH, " +
    "D.ID_PROOF_PATH " +

    "FROM ATHLETE A " +

    "JOIN GUARDIAN G " +
    "ON A.ATHLETE_ID = G.ATHLETE_ID " +

    "JOIN ADDRESS AD " +
    "ON A.ATHLETE_ID = AD.ATHLETE_ID " +

    "JOIN SPORTS_PROFILE S " +
    "ON A.ATHLETE_ID = S.ATHLETE_ID " +

    "JOIN DOCUMENTS D " +
    "ON A.ATHLETE_ID = D.ATHLETE_ID " +

    "WHERE A.ATHLETE_ID = ?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, athleteId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                Athlete athlete = new Athlete();
                athlete.setAthleteId(rs.getLong("ATHLETE_ID"));
                athlete.setFullName(rs.getString("FULL_NAME"));
                athlete.setDob(rs.getDate("DOB"));
                athlete.setAge(rs.getInt("AGE"));
                athlete.setGender(rs.getString("GENDER"));
                athlete.setMobile(rs.getString("ATHLETE_MOBILE"));
                athlete.setEmail(rs.getString("EMAIL"));
                athlete.setBloodGroup(rs.getString("BLOOD_GROUP"));
                athlete.setStatus(rs.getString("STATUS"));
                athlete.setCreatedAt(rs.getString("CREATED_AT"));

                Guardian guardian = new Guardian();
                guardian.setGuardianName(rs.getString("GUARDIAN_NAME"));
                guardian.setRelation(rs.getString("RELATION"));
                guardian.setMobile(rs.getString("GUARDIAN_MOBILE"));
                guardian.setEmergencyContact(rs.getString("EMERGENCY_CONTACT"));

                Address address = new Address();
                address.setState(rs.getString("STATE"));
                address.setDistrict(rs.getString("DISTRICT"));
                address.setCity(rs.getString("CITY"));
                address.setPinCode(rs.getString("PIN_CODE"));
                address.setFullAddress(rs.getString("FULL_ADDRESS"));

                SportsProfile sports = new SportsProfile();
                sports.setSportType(rs.getString("SPORT_TYPE"));
                sports.setCategory(rs.getString("CATEGORY"));
                sports.setTrainingLevel(rs.getString("TRAINING_LEVEL"));
                sports.setClubName(rs.getString("CLUB_NAME"));

                Documents docs = new Documents();
                docs.setPhotoPath(rs.getString("PHOTO_PATH"));
                docs.setBirthCertPath(rs.getString("BIRTH_CERT_PATH"));
                docs.setIdProofPath(rs.getString("ID_PROOF_PATH"));

                profile.setAthlete(athlete);
                profile.setGuardian(guardian);
                profile.setAddress(address);
                profile.setSportsProfile(sports);
                profile.setDocuments(docs);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return profile;
    }

    // -------------------------------------------------------
    // 3. GET DASHBOARD STATS (4 counts)
    // Called by: AdminDashboardServlet
    // Returns:   DashboardStats (totalAthletes, pending,
    //            approved, totalSports)
    // -------------------------------------------------------
  public DashboardStats getDashboardStats()
{
    DashboardStats stats = new DashboardStats();

    try
    {
        con = DbConnection.getConnection();

        // TOTAL ATHLETES
        PreparedStatement ps1 = con.prepareStatement(
            "SELECT COUNT(*) FROM ATHLETE");

        ResultSet rs1 = ps1.executeQuery();

        if(rs1.next())
        {
            stats.setTotalAthletes(rs1.getInt(1));
        }

        // PENDING ATHLETES
        PreparedStatement ps2 = con.prepareStatement(
            "SELECT COUNT(*) FROM ATHLETE WHERE STATUS='PENDING'");

        ResultSet rs2 = ps2.executeQuery();

        if(rs2.next())
        {
            stats.setPendingAthletes(rs2.getInt(1));
        }

        // APPROVED ATHLETES
        PreparedStatement ps3 = con.prepareStatement(
            "SELECT COUNT(*) FROM ATHLETE WHERE STATUS='APPROVED'");

        ResultSet rs3 = ps3.executeQuery();

        if(rs3.next())
        {
            stats.setApprovedAthletes(rs3.getInt(1));
        }

        // REJECTED ATHLETES
        PreparedStatement ps4 = con.prepareStatement(
            "SELECT COUNT(*) FROM ATHLETE WHERE STATUS='REJECTED'");

        ResultSet rs4 = ps4.executeQuery();

        if(rs4.next())
        {
            stats.setRejectedAthletes(rs4.getInt(1));
        }
    }

    catch(Exception e)
    {
        e.printStackTrace();
    }

    return stats;
}

    // -------------------------------------------------------
    // 4. GET ATHLETE LIST FOR ADMIN TABLE
    // Called by: AdminDashboardServlet, ExportExcelServlet
    // Returns:   List<DashBoardAthleteRowMdl>
    //            (id, name, mobile, age, sport, category, status)
    // -------------------------------------------------------
    public List<DashBoardAthleteRowMdl> getDashboardAthleteList() {

        List<DashBoardAthleteRowMdl> list = new ArrayList<>();

        try {
            con = DbConnection.getConnection();

            String sql =
                "SELECT A.ATHLETE_ID, A.FULL_NAME, A.MOBILE, " +
                "A.AGE, A.STATUS, S.SPORT_TYPE, S.CATEGORY " +
                "FROM ATHLETE A " +
                "JOIN SPORTS_PROFILE S ON A.ATHLETE_ID = S.ATHLETE_ID";

            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                DashBoardAthleteRowMdl row = new DashBoardAthleteRowMdl();
                row.setAthleteId(rs.getLong("ATHLETE_ID"));
                row.setFullName(rs.getString("FULL_NAME"));
                row.setMobile(rs.getString("MOBILE"));
                row.setAge(rs.getInt("AGE"));
                row.setSportType(rs.getString("SPORT_TYPE"));
                row.setCategory(rs.getString("CATEGORY"));
                row.setStatus(rs.getString("STATUS"));
                list.add(row);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // -------------------------------------------------------
    // 5. UPDATE ATHLETE STATUS
    // Called by: ApproveAthleteServlet → sets "APPROVED"
    //            RejectAthleteServlet  → sets "REJECTED"
    //            VerifyAdmissionPaymentServlet (optional update)
    // -------------------------------------------------------
    public boolean updateAthleteStatus
(
    long athleteId,
    String status
)
{

    boolean updated = false;

    try
    {

        Connection conn =
        DbConnection.getConnection();

        String query =
        "UPDATE athlete SET status=? WHERE athlete_id=?";

        PreparedStatement ps =
        conn.prepareStatement(query);

        ps.setString(1, status);

        ps.setLong(2, athleteId);

        int rows =
        ps.executeUpdate();

        System.out.println("Rows Updated = " + rows);

        if(rows > 0)
        {
            updated = true;
        }

    }

    catch(Exception e)
    {
        e.printStackTrace();
    }

    return updated;
}

    // -------------------------------------------------------
    // 6. GET ATHLETE EMAIL BY ID
    // Called by: EmailService (sendApprovalMail / sendRejectionMail)
    // Returns:   email string or null
    // -------------------------------------------------------
    public String getAthleteEmail(long athleteId) {

        String email = null;

        try {
            con = DbConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(
                "SELECT EMAIL FROM ATHLETE WHERE ATHLETE_ID = ?");
            ps.setLong(1, athleteId);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) email = rs.getString("EMAIL");

        } catch (Exception e) {
            e.printStackTrace();
        }

        return email;
    }

    // -------------------------------------------------------
    // 7. GET ATHLETE BY MOBILE
    // Called by: UserLoginServlet (mobile-only login)
    // Returns:   Athlete object (basic fields only, no joins)
    //            null if mobile not found
    // -------------------------------------------------------
    public Athlete getAthleteByMobile(String mobile) {

        Athlete athlete = null;

        try {
            con = DbConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(
                "SELECT * FROM ATHLETE WHERE MOBILE = ?");
            ps.setString(1, mobile);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                athlete = new Athlete();
                athlete.setAthleteId(rs.getLong("ATHLETE_ID"));
                athlete.setFullName(rs.getString("FULL_NAME"));
                athlete.setDob(rs.getDate("DOB"));
                athlete.setAge(rs.getInt("AGE"));
                athlete.setGender(rs.getString("GENDER"));
                athlete.setMobile(rs.getString("MOBILE"));
                athlete.setEmail(rs.getString("EMAIL"));
                athlete.setBloodGroup(rs.getString("BLOOD_GROUP"));
                athlete.setStatus(rs.getString("STATUS"));
                athlete.setCreatedAt(rs.getString("CREATED_AT"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return athlete;
    }
}