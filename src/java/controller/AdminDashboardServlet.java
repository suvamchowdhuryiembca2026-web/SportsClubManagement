package controller;

import DAO.athleteDAO;
import DAO.coachDAO;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.DashboardStats;
import model.DashBoardAthleteRowMdl;
import model.DashboardCoachRowMdl;

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
        response.setContentType("text/html;charset=UTF-8");

        PrintWriter out = response.getWriter();

        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);

        try
        {
            // ─────────────────────────────────────────────
            // DAO OBJECTS
            // ─────────────────────────────────────────────
            athleteDAO athleteDao = new athleteDAO();
            coachDAO coachDao     = new coachDAO();

            // ─────────────────────────────────────────────
            // FETCH DATA
            // ─────────────────────────────────────────────
            DashboardStats stats =
                    athleteDao.getDashboardStats();

            List<DashBoardAthleteRowMdl> athleteList =
                    athleteDao.getDashboardAthleteList();

            List<DashboardCoachRowMdl> coachList =
                    coachDao.getDashboardCoachList();

            // ─────────────────────────────────────────────
            // COACH COUNTS
            // ─────────────────────────────────────────────
            int totalCoaches = coachList.size();

            long pendingCoaches =
                    coachList.stream()
                    .filter(c -> "PENDING"
                    .equalsIgnoreCase(c.getStatus()))
                    .count();

            long approvedCoaches =
                    coachList.stream()
                    .filter(c -> "APPROVED"
                    .equalsIgnoreCase(c.getStatus()))
                    .count();
            long rejectedCoaches =
        coachList.stream()
        .filter(c -> "REJECTED"
        .equalsIgnoreCase(c.getStatus()))
        .count();

            // ─────────────────────────────────────────────
            // HTML START
            // ─────────────────────────────────────────────
            out.println("<!DOCTYPE html>");

            out.println("<html>");

            out.println("<head>");

            out.println("<title>Admin Dashboard</title>");

            // ─────────────────────────────────────────────
            // CSS
            // ─────────────────────────────────────────────
            out.println("<style>");

            out.println("*{");
            out.println("margin:0;");
            out.println("padding:0;");
            out.println("box-sizing:border-box;");
            out.println("font-family:Arial;");
            out.println("}");

            out.println("body{");
            out.println("background:linear-gradient(to right,#01236d,#3861e7);");
            out.println("min-height:100vh;");
            out.println("}");

            // HEADER
            out.println(".header{");
            out.println("height:70px;");
            out.println("padding:0 30px;");
            out.println("display:flex;");
            out.println("justify-content:space-between;");
            out.println("align-items:center;");
            out.println("background:rgba(0,0,0,0.2);");
            out.println("backdrop-filter:blur(12px);");
            out.println("position:sticky;");
            out.println("top:0;");
            out.println("z-index:100;");
            out.println("}");

            out.println(".logo{");
            out.println("display:flex;");
            out.println("align-items:center;");
            out.println("gap:12px;");
            out.println("font-size:22px;");
            out.println("font-weight:bold;");
            out.println("color:white;");
            out.println("}");
            
            out.println(".event-btn{");
            out.println("background:#22c55e;");
            out.println("color:white;");
            out.println("}");

            out.println(".event-btn:hover{");
            out.println("background:#16a34a;");
            out.println("}");
            out.println(".logo-box{");
            out.println("width:40px;");
            out.println("height:40px;");
            out.println("border-radius:10px;");
            out.println("background:white;");
            out.println("display:flex;");
            out.println("justify-content:center;");
            out.println("align-items:center;");
            out.println("color:#01236d;");
            out.println("font-weight:bold;");
            out.println("}");

            out.println(".header-buttons{");
            out.println("display:flex;");
            out.println("gap:10px;");
            out.println("}");

            out.println(".btn{");
            out.println("padding:10px 18px;");
            out.println("border:none;");
            out.println("border-radius:8px;");
            out.println("cursor:pointer;");
            out.println("font-weight:bold;");
            out.println("text-decoration:none;");
            out.println("font-size:13px;");
            out.println("}");

            out.println(".export-btn{");
            out.println("background:white;");
            out.println("color:#01236d;");
            out.println("}");

            out.println(".logout-btn{");
            out.println("background:#ef4444;");
            out.println("color:white;");
            out.println("}");

            // CONTAINER
            out.println(".container{");
            out.println("padding:30px;");
            out.println("}");

            // CARDS
            out.println(".cards{");
            out.println("display:grid;");
            out.println("grid-template-columns:repeat(auto-fit,minmax(220px,1fr));");
            out.println("gap:20px;");
            out.println("margin-bottom:30px;");
            out.println("}");

            out.println(".card{");
            out.println("background:rgba(255,255,255,0.15);");
            out.println("backdrop-filter:blur(16px);");
            out.println("border:1px solid rgba(255,255,255,0.2);");
            out.println("border-radius:18px;");
            out.println("padding:25px;");
            out.println("color:white;");
            out.println("box-shadow:0 8px 20px rgba(0,0,0,0.2);");
            out.println("}");

            out.println(".card h3{");
            out.println("font-size:14px;");
            out.println("margin-bottom:12px;");
            out.println("color:#dbeafe;");
            out.println("}");

            out.println(".card h1{");
            out.println("font-size:42px;");
            out.println("}");

            // TABS
            out.println(".tab-buttons{");
            out.println("display:flex;");
            out.println("gap:12px;");
            out.println("margin-bottom:20px;");
            out.println("}");

            out.println(".tab-btn{");
            out.println("padding:12px 24px;");
            out.println("border:none;");
            out.println("border-radius:10px;");
            out.println("cursor:pointer;");
            out.println("font-weight:bold;");
            out.println("background:rgba(255,255,255,0.15);");
            out.println("color:white;");
            out.println("}");

            out.println(".tab-btn.active{");
            out.println("background:white;");
            out.println("color:#01236d;");
            out.println("}");

            // TABLE
            out.println(".table-wrapper{");
            out.println("background:white;");
            out.println("border-radius:18px;");
            out.println("overflow:auto;");
            out.println("box-shadow:0 8px 20px rgba(0,0,0,0.2);");
            out.println("}");

            out.println("table{");
            out.println("width:100%;");
            out.println("border-collapse:collapse;");
            out.println("}");

            out.println("th{");
            out.println("background:#01236d;");
            out.println("color:white;");
            out.println("padding:16px;");
            out.println("font-size:14px;");
            out.println("text-align:left;");
            out.println("}");

            out.println("td{");
            out.println("padding:14px 16px;");
            out.println("border-bottom:1px solid #e5e7eb;");
            out.println("font-size:14px;");
            out.println("}");

            out.println("tr:hover{");
            out.println("background:#f8fafc;");
            out.println("}");

            // STATUS
            out.println(".status{");
            out.println("padding:6px 12px;");
            out.println("border-radius:30px;");
            out.println("font-size:12px;");
            out.println("font-weight:bold;");
            out.println("color:white;");
            out.println("}");

            out.println(".approved{background:#16a34a;}");
            out.println(".pending{background:#f59e0b;}");
            out.println(".rejected{background:#ef4444;}");

            // VIEW BUTTON
            out.println(".view-btn{");
            out.println("padding:8px 14px;");
            out.println("border:none;");
            out.println("border-radius:8px;");
            out.println("background:#2563eb;");
            out.println("color:white;");
            out.println("cursor:pointer;");
            out.println("font-size:12px;");
            out.println("font-weight:bold;");
            out.println("}");

            out.println(".view-btn:hover{");
            out.println("background:#1d4ed8;");
            out.println("}");

            // PANELS
            out.println(".tab-panel{display:none;}");
            out.println(".tab-panel.active{display:block;}");

            // MOBILE
            out.println("@media(max-width:900px){");
            out.println(".cards{grid-template-columns:1fr 1fr;}");
            out.println("}");

            out.println("@media(max-width:600px){");
            out.println(".cards{grid-template-columns:1fr;}");
            out.println(".header{padding:0 15px;}");
            out.println(".container{padding:15px;}");
            out.println("}");

            out.println("</style>");

            out.println("</head>");

            // ─────────────────────────────────────────────
            // BODY
            // ─────────────────────────────────────────────
            out.println("<body>");

            // HEADER
            out.println("<div class='header'>");

            out.println("<div class='logo'>");
            out.println("<div class='logo-box'>SE</div>");
            out.println("Sports Ecosystem");
            out.println("</div>");

            out.println("<div class='header-buttons'>");

            out.println("<a href='ExportExcelServlet' class='btn export-btn'>");
            out.println("Export Excel");
            out.println("</a>");
            out.println("<a href='Event.html' class='btn event-btn'>");
            out.println("Create Event");
            out.println("</a>");

            out.println("<a href='adminLogin.html' class='btn logout-btn'>");
            out.println("Logout");
            out.println("</a>");

            out.println("</div>");

            out.println("</div>");

            // CONTAINER
            out.println("<div class='container'>");

            // ─────────────────────────────────────────────
            // STATS
            // ─────────────────────────────────────────────
            out.println("<div class='cards'>");

printCard(out, "Total Athletes",
        String.valueOf(stats.getTotalAthletes()),
        "stat1");

printCard(out, "Pending Athletes",
        String.valueOf(stats.getPendingAthletes()),
        "stat2");

printCard(out, "Approved Athletes",
        String.valueOf(stats.getApprovedAthletes()),
        "stat3");

printCard(out, "Rejected Athletes",
        String.valueOf(stats.getRejectedAthletes()),
        "stat4");

out.println("</div>");
            // ─────────────────────────────────────────────
            // TAB BUTTONS
            // ─────────────────────────────────────────────
            out.println("<div class='tab-buttons'>");

            out.println("<button class='tab-btn active' ");
            out.println("onclick=\"switchTab('athletes',this)\">");
            out.println("Athletes");
            out.println("</button>");

            out.println("<button class='tab-btn' ");
            out.println("onclick=\"switchTab('coaches',this)\">");
            out.println("Coaches");
            out.println("</button>");

            out.println("</div>");

            // ─────────────────────────────────────────────
            // ATHLETE PANEL
            // ─────────────────────────────────────────────
            out.println("<div id='athletes' class='tab-panel active'>");

            out.println("<div class='table-wrapper'>");

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

            for (DashBoardAthleteRowMdl athlete : athleteList)
            {
                String statusClass =
                        athlete.getStatus().equalsIgnoreCase("APPROVED")
                        ? "approved"
                        : athlete.getStatus().equalsIgnoreCase("REJECTED")
                        ? "rejected"
                        : "pending";

                out.println("<tr>");

                out.println("<td>" + athlete.getFullName() + "</td>");
                out.println("<td>" + athlete.getMobile() + "</td>");
                out.println("<td>" + athlete.getAge() + "</td>");
                out.println("<td>" + athlete.getSportType() + "</td>");
                out.println("<td>" + athlete.getCategory() + "</td>");

                out.println("<td>");
                out.println("<span class='status " + statusClass + "'>");
                out.println(athlete.getStatus());
                out.println("</span>");
                out.println("</td>");

                out.println("<td>");
                out.println("<a href='ViewAthleteServlet?id="
                        + athlete.getAthleteId() + "'>");
                out.println("<button class='view-btn'>View</button>");
                out.println("</a>");
                out.println("</td>");

                out.println("</tr>");
            }

            out.println("</table>");

            out.println("</div>");

            out.println("</div>");

            // ─────────────────────────────────────────────
            // COACH PANEL
            // ─────────────────────────────────────────────
            out.println("<div id='coaches' class='tab-panel'>");

            out.println("<div class='table-wrapper'>");

            out.println("<table>");

            out.println("<tr>");
            out.println("<th>Name</th>");
            out.println("<th>Mobile</th>");
            out.println("<th>Specialization</th>");
            out.println("<th>Level</th>");
            out.println("<th>Experience</th>");
            out.println("<th>Status</th>");
            out.println("<th>Action</th>");
            out.println("</tr>");

            for (DashboardCoachRowMdl coach : coachList)
            {
                String statusClass =
                        coach.getStatus().equalsIgnoreCase("APPROVED")
                        ? "approved"
                        : coach.getStatus().equalsIgnoreCase("REJECTED")
                        ? "rejected"
                        : "pending";

                out.println("<tr>");

                out.println("<td>" + coach.getFullName() + "</td>");
                out.println("<td>" + coach.getMobile() + "</td>");
                out.println("<td>" + coach.getSpecialization() + "</td>");
                out.println("<td>" + coach.getCoachingLevel() + "</td>");
                out.println("<td>" + coach.getExperience() + " yrs</td>");

                out.println("<td>");
                out.println("<span class='status " + statusClass + "'>");
                out.println(coach.getStatus());
                out.println("</span>");
                out.println("</td>");

                out.println("<td>");
                out.println("<a href='ViewCoachServlet?id="
                        + coach.getCoachId() + "'>");
                out.println("<button class='view-btn'>View</button>");
                out.println("</a>");
                out.println("</td>");

                out.println("</tr>");
            }

            out.println("</table>");

            out.println("</div>");

            out.println("</div>");

            out.println("</div>");

            // ─────────────────────────────────────────────
            // JAVASCRIPT
            // ─────────────────────────────────────────────
           out.println("<script>");

out.println("function switchTab(id,btn){");

// PANEL SWITCH
out.println("document.querySelectorAll('.tab-panel')");
out.println(".forEach(panel=>panel.classList.remove('active'));");

out.println("document.querySelectorAll('.tab-btn')");
out.println(".forEach(button=>button.classList.remove('active'));");

out.println("document.getElementById(id)");
out.println(".classList.add('active');");

out.println("btn.classList.add('active');");

// DYNAMIC CARD CHANGE
out.println("if(id === 'athletes'){");

out.println("document.getElementById('stat1-title').innerText='Total Athletes';");
out.println("document.getElementById('stat2-title').innerText='Pending Athletes';");
out.println("document.getElementById('stat3-title').innerText='Approved Athletes';");
out.println("document.getElementById('stat4-title').innerText='Rejected Athletes';");

out.println("document.getElementById('stat1-value').innerText='" + stats.getTotalAthletes() + "';");
out.println("document.getElementById('stat2-value').innerText='" + stats.getPendingAthletes() + "';");
out.println("document.getElementById('stat3-value').innerText='" + stats.getApprovedAthletes() + "';");
out.println("document.getElementById('stat4-value').innerText='" + stats.getRejectedAthletes() + "';");

out.println("}");

out.println("else{");

out.println("document.getElementById('stat1-title').innerText='Total Coaches';");
out.println("document.getElementById('stat2-title').innerText='Pending Coaches';");
out.println("document.getElementById('stat3-title').innerText='Approved Coaches';");
out.println("document.getElementById('stat4-title').innerText='Rejected Coaches';");

out.println("document.getElementById('stat1-value').innerText='" + totalCoaches + "';");
out.println("document.getElementById('stat2-value').innerText='" + pendingCoaches + "';");
out.println("document.getElementById('stat3-value').innerText='" + approvedCoaches + "';");
out.println("document.getElementById('stat4-value').innerText='" + rejectedCoaches + "';");

out.println("}");

out.println("}");

out.println("</script>");

            out.println("</body>");

            out.println("</html>");
        }
        catch (Exception e)
        {
            out.println("<h2>ERROR : " + e.getMessage() + "</h2>");
            e.printStackTrace(out);
        }
    }

    // ─────────────────────────────────────────────
    // REUSABLE CARD METHOD
    // ─────────────────────────────────────────────
    private void printCard
(
    PrintWriter out,
    String title,
    String value,
    String id
)
{
    out.println("<div class='card'>");

    out.println("<h3 id='" + id + "-title'>" +
            title + "</h3>");

    out.println("<h1 id='" + id + "-value'>" +
            value + "</h1>");

    out.println("</div>");
}
}