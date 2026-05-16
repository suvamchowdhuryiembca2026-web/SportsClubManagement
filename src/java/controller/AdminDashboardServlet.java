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

public class AdminDashboardServlet extends HttpServlet
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

            con = DbConnection.getConnection();

            int totalAthletes = 0;
            int pending = 0;
            int approved = 0;
            int totalSports = 0;

            PreparedStatement ps1 =
            con.prepareStatement
            (
                "SELECT COUNT(*) FROM ATHLETE"
            );

            ResultSet rs1 = ps1.executeQuery();

            if(rs1.next())
            {
                totalAthletes = rs1.getInt(1);
            }


            PreparedStatement ps2 =
            con.prepareStatement
            (
                "SELECT COUNT(*) FROM ATHLETE WHERE STATUS='PENDING'"
            );

            ResultSet rs2 = ps2.executeQuery();

            if(rs2.next())
            {
                pending = rs2.getInt(1);
            }

            PreparedStatement ps3 =
            con.prepareStatement
            (
                "SELECT COUNT(*) FROM ATHLETE WHERE STATUS='APPROVED'"
            );

            ResultSet rs3 = ps3.executeQuery();

            if(rs3.next())
            {
                approved = rs3.getInt(1);
            }

            // TOTAL SPORTS
            PreparedStatement ps4 =
            con.prepareStatement
            (
                "SELECT COUNT(DISTINCT SPORT_TYPE) FROM SPORTS_PROFILE"
            );

            ResultSet rs4 = ps4.executeQuery();

            if(rs4.next())
            {
                totalSports = rs4.getInt(1);
            }


            String sql =
            "SELECT " +
            "A.ATHLETE_ID, " +
            "A.FULL_NAME, " +
            "A.MOBILE, " +
            "A.AGE, " +
            "A.STATUS, " +
            "S.SPORT_TYPE, " +
            "S.CATEGORY " +
            "FROM ATHLETE A " +
            "JOIN SPORTS_PROFILE S " +
            "ON A.ATHLETE_ID = S.ATHLETE_ID";

            PreparedStatement ps =
            con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();


            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Admin Dashboard</title>");

            out.println("<style>");

            out.println("body{margin:0;font-family:Arial;background:#f4f7fb;}");

            out.println(".header{background:#1f3b57;color:white;padding:15px 20px;display:flex;justify-content:space-between;align-items:center;}");

            out.println(".logout{background:#ff4d4d;border:none;padding:8px 15px;color:white;border-radius:5px;cursor:pointer;}");

            out.println(".container{padding:20px;}");

            out.println(".cards{display:grid;grid-template-columns:repeat(4,1fr);gap:15px;margin-bottom:20px;}");

            out.println(".card{background:white;padding:15px;border-radius:10px;box-shadow:0 2px 6px rgba(0,0,0,0.1);text-align:center;}");

            out.println("table{width:100%;border-collapse:collapse;background:white;border-radius:10px;overflow:hidden;box-shadow:0 2px 6px rgba(0,0,0,0.1);}");

            out.println("th,td{padding:12px;text-align:left;border-bottom:1px solid #ddd;}");

            out.println("th{background:#2c4c6b;color:white;}");

            out.println(".status{padding:5px 10px;border-radius:5px;font-size:12px;color:white;}");

            out.println(".pending{background:orange;}");

            out.println(".approved{background:green;}");

            out.println(".view-btn{padding:6px 12px;border:none;background:#1f3b57;color:white;border-radius:5px;cursor:pointer;}");

            out.println("a{text-decoration:none;}");

            out.println("</style>");

            out.println("</head>");
            out.println("<body>");


            out.println("<div class='header'>");

            out.println("<h2>Sports Ecosystem Admin Dashboard</h2>");

            out.println("<a href='adminLogin.html'>");
            out.println("<button class='logout'>Logout</button>");
            out.println("</a>");

            out.println("</div>");

            out.println("<div class='container'>");

            out.println("<div class='cards'>");

            out.println("<div class='card'>");
            out.println("<h3>Total Athletes</h3>");
            out.println("<h2>" + totalAthletes + "</h2>");
            out.println("</div>");

            out.println("<div class='card'>");
            out.println("<h3>Pending</h3>");
            out.println("<h2>" + pending + "</h2>");
            out.println("</div>");

            out.println("<div class='card'>");
            out.println("<h3>Approved</h3>");
            out.println("<h2>" + approved + "</h2>");
            out.println("</div>");

            out.println("<div class='card'>");
            out.println("<h3>Sports</h3>");
            out.println("<h2>" + totalSports + "</h2>");
            out.println("</div>");

            out.println("</div>");


            out.println("<table>");

            out.println("<tr>");
            out.println("<th>Name</th>");
            out.println("<th>Mobile</th>");
            out.println("<th>Age</th>");
            out.println("<th>Sport</th>");
            out.println("<th>Category</th>");
            out.println("<th>Status</th>");
            out.println("<th>Action</th>");
            out.println("</tr>");

            while(rs.next())
            {

                String status = rs.getString("STATUS");

                String statusClass = "";

                if(status.equalsIgnoreCase("PENDING"))
                {
                    statusClass = "pending";
                }
                else
                {
                    statusClass = "approved";
                }

                out.println("<tr>");

                out.println("<td>" + rs.getString("FULL_NAME") + "</td>");

                out.println("<td>" + rs.getString("MOBILE") + "</td>");

                out.println("<td>" + rs.getInt("AGE") + "</td>");

                out.println("<td>" + rs.getString("SPORT_TYPE") + "</td>");

                out.println("<td>" + rs.getString("CATEGORY") + "</td>");

                out.println
                (
                    "<td><span class='status "
                    + statusClass +
                    "'>" + status + "</span></td>"
                );

                out.println("<td>");

                out.println
                (
                    "<a href='ViewAthleteServlet?id="
                    + rs.getLong("ATHLETE_ID") +
                    "'>"
                );

                out.println
                (
                    "<button class='view-btn'>View</button>"
                );

                out.println("</a>");

                out.println("</td>");

                out.println("</tr>");
            }

            out.println("</table>");

            out.println("</div>");

            out.println("</body>");
            out.println("</html>");

        }

        catch(Exception e)
        {
            out.println("<h2>ERROR : " + e.getMessage() + "</h2>");
            e.printStackTrace(out);
        }
    }
}