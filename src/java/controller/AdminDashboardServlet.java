package controller;

import DAO.athleteDAO;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.DashboardStats;
import model.DashBoardAthleteRowMdl;

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

        PrintWriter out =
        response.getWriter();

        try
        {

            athleteDAO dao =
            new athleteDAO();

            DashboardStats stats =
            dao.getDashboardStats();

            List<DashBoardAthleteRowMdl> athleteList =
            dao.getDashboardAthleteList();

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

            // HEADER

            out.println("<div class='header'>");

            out.println("<h2>Sports Ecosystem Admin Dashboard</h2>");

            out.println("<a href='adminLogin.html'>");

            out.println("<button class='logout'>Logout</button>");

            out.println("</a>");

            out.println("</div>");

            // CONTAINER

            out.println("<div class='container'>");

            // CARDS

            out.println("<div class='cards'>");

            out.println("<div class='card'>");
            out.println("<h3>Total Athletes</h3>");
            out.println("<h2>" +
            stats.getTotalAthletes() +
            "</h2>");
            out.println("</div>");

            out.println("<div class='card'>");
            out.println("<h3>Pending</h3>");
            out.println("<h2>" +
            stats.getPending() +
            "</h2>");
            out.println("</div>");

            out.println("<div class='card'>");
            out.println("<h3>Approved</h3>");
            out.println("<h2>" +
            stats.getApproved() +
            "</h2>");
            out.println("</div>");

            out.println("<div class='card'>");
            out.println("<h3>Sports</h3>");
            out.println("<h2>" +
            stats.getTotalSports() +
            "</h2>");
            out.println("</div>");

            out.println("</div>");

            // TABLE

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

            for(DashBoardAthleteRowMdl athlete
            : athleteList)
            {

                String statusClass = "";

                if
                (
                    athlete.getStatus()
                    .equalsIgnoreCase("PENDING")
                )
                {
                    statusClass = "pending";
                }

                else
                {
                    statusClass = "approved";
                }

                out.println("<tr>");

                out.println("<td>" +
                athlete.getFullName() +
                "</td>");

                out.println("<td>" +
                athlete.getMobile() +
                "</td>");

                out.println("<td>" +
                athlete.getAge() +
                "</td>");

                out.println("<td>" +
                athlete.getSportType() +
                "</td>");

                out.println("<td>" +
                athlete.getCategory() +
                "</td>");

                out.println
                (
                    "<td><span class='status "
                    + statusClass +
                    "'>"
                    + athlete.getStatus() +
                    "</span></td>"
                );

                out.println("<td>");

                out.println
                (
                    "<a href='ViewAthleteServlet?id="
                    + athlete.getAthleteId()
                    + "'>"
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
            out.println("<h2>ERROR : "
            + e.getMessage() +
            "</h2>");

            e.printStackTrace(out);
        }
    }
}