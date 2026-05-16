package controller;

import db.DbConnection;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ViewAthleteServlet extends HttpServlet
{

    @Override
    protected void doGet
    (
        HttpServletRequest request,
        HttpServletResponse response
    )

    throws ServletException, IOException
    {

        response.setContentType("text/html");

        PrintWriter out = response.getWriter();

        Connection con = null;

        try
        {

            long athleteId =
            Long.parseLong(request.getParameter("id"));

            con = DbConnection.getConnection();

            String sql =
            "SELECT * FROM ATHLETE A " +
            "JOIN GUARDIAN G " +
            "ON A.ATHLETE_ID = G.ATHLETE_ID " +

            "JOIN ADDRESS AD " +
            "ON A.ATHLETE_ID = AD.ATHLETE_ID " +

            "JOIN SPORTS_PROFILE S " +
            "ON A.ATHLETE_ID = S.ATHLETE_ID " +

            "JOIN DOCUMENTS D " +
            "ON A.ATHLETE_ID = D.ATHLETE_ID " +

            "WHERE A.ATHLETE_ID=?";

            PreparedStatement ps =
            con.prepareStatement(sql);

            ps.setLong(1, athleteId);

            ResultSet rs = ps.executeQuery();

            if(rs.next())
            {

                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Athlete Profile</title>");

                out.println("<link href='https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap' rel='stylesheet'>");

                out.println("<style>");

                out.println("*{margin:0;padding:0;box-sizing:border-box;font-family:'Inter',sans-serif;}");

                out.println("body{background:#f1f5f9;color:#0f172a;}");

                out.println(".topbar{height:75px;background:white;display:flex;align-items:center;justify-content:space-between;padding:0 40px;border-bottom:1px solid #e2e8f0;}");

                out.println(".logo{font-size:24px;font-weight:700;}");

                out.println(".logo span{color:#2563eb;}");

                out.println(".wrapper{display:grid;grid-template-columns:330px 1fr;gap:28px;padding:35px;}");

                out.println(".sidebar{background:white;border-radius:28px;padding:30px;border:1px solid #e2e8f0;height:fit-content;}");

                out.println(".profile-pic{width:140px;height:140px;border-radius:26px;object-fit:cover;display:block;margin:auto;border:5px solid #eff6ff;}");

                out.println(".player-name{text-align:center;margin-top:22px;font-size:26px;font-weight:700;}");

                out.println(".player-role{text-align:center;margin-top:8px;color:#64748b;font-size:14px;}");

                out.println(".badge{width:fit-content;margin:22px auto 0;background:#eff6ff;color:#2563eb;padding:10px 18px;border-radius:999px;font-size:13px;font-weight:600;}");

                out.println(".quick-info{margin-top:35px;}");

                out.println(".quick-box{padding:18px;border-radius:18px;margin-bottom:15px;background:#f8fafc;border:1px solid #e2e8f0;}");

                out.println(".quick-title{font-size:13px;color:#64748b;margin-bottom:6px;}");

                out.println(".quick-value{font-size:18px;font-weight:700;}");

                out.println(".main-content{display:flex;flex-direction:column;gap:25px;}");

                out.println(".card{background:white;border-radius:28px;border:1px solid #e2e8f0;padding:30px;}");

                out.println(".card-title{font-size:21px;font-weight:700;margin-bottom:24px;}");

                out.println(".info-grid{display:grid;grid-template-columns:repeat(auto-fit,minmax(220px,1fr));gap:20px;}");

                out.println(".info-item{background:#f8fafc;padding:18px;border-radius:18px;}");

                out.println(".info-label{font-size:13px;color:#64748b;margin-bottom:8px;}");

                out.println(".info-value{font-size:16px;font-weight:600;}");

                out.println(".full-address{margin-top:20px;background:#f8fafc;border-radius:18px;padding:20px;}");

                out.println(".docs{display:grid;grid-template-columns:repeat(auto-fit,minmax(240px,1fr));gap:20px;}");

                out.println(".doc-card{border:1px solid #e2e8f0;border-radius:18px;padding:20px;display:flex;justify-content:space-between;align-items:center;}");

                out.println(".download{text-decoration:none;background:#2563eb;color:white;padding:10px 16px;border-radius:10px;font-size:13px;font-weight:600;}");

                out.println(".status{background:#ecfdf5;border:1px solid #bbf7d0;padding:25px;border-radius:24px;display:flex;justify-content:space-between;align-items:center;}");

                out.println(".btn{border:none;padding:12px 18px;border-radius:14px;font-weight:600;cursor:pointer;background:#2563eb;color:white;}");

                out.println("</style>");

                out.println("</head>");
                out.println("<body>");

                // TOPBAR

                out.println("<div class='topbar'>");

                out.println("<div class='logo'><span>Sports</span>Ecosystem</div>");

                out.println("<button class='btn'>Approve Athlete</button>");

                out.println("</div>");

                // WRAPPER

                out.println("<div class='wrapper'>");

                // SIDEBAR

                out.println("<div class='sidebar'>");

                out.println
                (
                    "<img src='" +
                    rs.getString("PHOTO_PATH") +
                    "' class='profile-pic'>"
                );

                out.println
                (
                    "<div class='player-name'>" +
                    rs.getString("FULL_NAME") +
                    "</div>"
                );

                out.println("<div class='player-role'>Registered Athlete</div>");

                out.println
                (
                    "<div class='badge'>STATUS : " +
                    rs.getString("STATUS") +
                    "</div>"
                );

                out.println("<div class='quick-info'>");

                out.println("<div class='quick-box'>");
                out.println("<div class='quick-title'>Athlete ID</div>");
                out.println("<div class='quick-value'>" + rs.getLong("ATHLETE_ID") + "</div>");
                out.println("</div>");

                out.println("<div class='quick-box'>");
                out.println("<div class='quick-title'>Sport Type</div>");
                out.println("<div class='quick-value'>" + rs.getString("SPORT_TYPE") + "</div>");
                out.println("</div>");

                out.println("<div class='quick-box'>");
                out.println("<div class='quick-title'>Training Level</div>");
                out.println("<div class='quick-value'>" + rs.getString("TRAINING_LEVEL") + "</div>");
                out.println("</div>");

                out.println("<div class='quick-box'>");
                out.println("<div class='quick-title'>Club Name</div>");
                out.println("<div class='quick-value'>" + rs.getString("CLUB_NAME") + "</div>");
                out.println("</div>");

                out.println("</div>");
                out.println("</div>");

                // MAIN CONTENT

                out.println("<div class='main-content'>");

                // ATHLETE INFO

                out.println("<div class='card'>");
                out.println("<div class='card-title'>Athlete Information</div>");

                out.println("<div class='info-grid'>");

                out.println("<div class='info-item'><div class='info-label'>Full Name</div><div class='info-value'>" + rs.getString("FULL_NAME") + "</div></div>");

                out.println("<div class='info-item'><div class='info-label'>DOB</div><div class='info-value'>" + rs.getDate("DOB") + "</div></div>");

                out.println("<div class='info-item'><div class='info-label'>Age</div><div class='info-value'>" + rs.getInt("AGE") + "</div></div>");

                out.println("<div class='info-item'><div class='info-label'>Gender</div><div class='info-value'>" + rs.getString("GENDER") + "</div></div>");

                out.println("<div class='info-item'><div class='info-label'>Mobile</div><div class='info-value'>" + rs.getString("MOBILE") + "</div></div>");

                out.println("<div class='info-item'><div class='info-label'>Email</div><div class='info-value'>" + rs.getString("EMAIL") + "</div></div>");

                out.println("<div class='info-item'><div class='info-label'>Blood Group</div><div class='info-value'>" + rs.getString("BLOOD_GROUP") + "</div></div>");

                out.println("<div class='info-item'><div class='info-label'>Created At</div><div class='info-value'>" + rs.getString("CREATED_AT") + "</div></div>");

                out.println("</div>");
                out.println("</div>");

                // GUARDIAN

                out.println("<div class='card'>");
                out.println("<div class='card-title'>Guardian Details</div>");

                out.println("<div class='info-grid'>");

                out.println("<div class='info-item'><div class='info-label'>Guardian Name</div><div class='info-value'>" + rs.getString("GUARDIAN_NAME") + "</div></div>");

                out.println("<div class='info-item'><div class='info-label'>Relation</div><div class='info-value'>" + rs.getString("RELATION") + "</div></div>");

                out.println("<div class='info-item'><div class='info-label'>Guardian Mobile</div><div class='info-value'>" + rs.getString("EMERGENCY_CONTACT") + "</div></div>");

                out.println("</div>");
                out.println("</div>");

                // ADDRESS

                out.println("<div class='card'>");
                out.println("<div class='card-title'>Address Details</div>");

                out.println("<div class='info-grid'>");

                out.println("<div class='info-item'><div class='info-label'>State</div><div class='info-value'>" + rs.getString("STATE") + "</div></div>");

                out.println("<div class='info-item'><div class='info-label'>District</div><div class='info-value'>" + rs.getString("DISTRICT") + "</div></div>");

                out.println("<div class='info-item'><div class='info-label'>City</div><div class='info-value'>" + rs.getString("CITY") + "</div></div>");

                out.println("<div class='info-item'><div class='info-label'>PIN</div><div class='info-value'>" + rs.getString("PIN_CODE") + "</div></div>");

                out.println("</div>");

                out.println("<div class='full-address'>");
                out.println("<div class='info-label'>Full Address</div>");
                out.println("<div class='info-value'>" + rs.getString("FULL_ADDRESS") + "</div>");
                out.println("</div>");

                out.println("</div>");

                // SPORTS

                out.println("<div class='card'>");
                out.println("<div class='card-title'>Sports Profile</div>");

                out.println("<div class='info-grid'>");

                out.println("<div class='info-item'><div class='info-label'>Sport Type</div><div class='info-value'>" + rs.getString("SPORT_TYPE") + "</div></div>");

                out.println("<div class='info-item'><div class='info-label'>Category</div><div class='info-value'>" + rs.getString("CATEGORY") + "</div></div>");

                out.println("<div class='info-item'><div class='info-label'>Training Level</div><div class='info-value'>" + rs.getString("TRAINING_LEVEL") + "</div></div>");

                out.println("<div class='info-item'><div class='info-label'>Club Name</div><div class='info-value'>" + rs.getString("CLUB_NAME") + "</div></div>");

                out.println("</div>");
                out.println("</div>");


                out.println("<div class='card'>");
                out.println("<div class='card-title'>Uploaded Documents</div>");

                out.println("<div class='docs'>");

                out.println("<div class='doc-card'>Athlete Photo <a href='" + rs.getString("PHOTO_PATH") + "' class='download'>View</a></div>");

                out.println("<div class='doc-card'>Birth Certificate <a href='" + rs.getString("BIRTH_CERT_PATH") + "' class='download'>Download</a></div>");

                out.println("<div class='doc-card'>ID Proof <a href='" + rs.getString("ID_PROOF_PATH") + "' class='download'>Download</a></div>");

                out.println("</div>");
                out.println("</div>");

                out.println("</div>");
                out.println("</div>");

                out.println("</body>");
                out.println("</html>");

            }

            else
            {
                out.println("<h2>No Athlete Found</h2>");
            }

        }

        catch(Exception e)
        {
            e.printStackTrace(out);
        }
    }
}