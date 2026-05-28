package controller;

import DAO.athleteDAO;
import DAO.paymentDAO;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import DAO.EventDAO;
import model.Event;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Address;
import model.Athlete;
import model.AthleteCompleteProfile;
import model.Documents;
import model.Guardian;
import model.Payment;
import model.SportsProfile;

public class AthleteDashboardServlet extends HttpServlet {

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);

        try {

            // ================= SESSION CHECK =================

          HttpSession session = request.getSession(false);

if(session == null || session.getAttribute("athleteId") == null)
{
    response.sendRedirect("UserLogin.html?status=sessionExpired");
    return;
}
            long athleteId = (long) session.getAttribute("athleteId");

            // ================= FETCH PROFILE =================

            athleteDAO athleteDao = new athleteDAO();

            AthleteCompleteProfile profile =
                    athleteDao.getAthleteById(athleteId);

            if (profile == null || profile.getAthlete() == null) {
                response.sendRedirect("UserLogin.html");
                return;
            }

            Athlete athlete = profile.getAthlete();
            Guardian guardian = profile.getGuardian();
            Address address = profile.getAddress();
            SportsProfile sports = profile.getSportsProfile();
            Documents docs = profile.getDocuments();

            // ================= PAYMENT =================

            paymentDAO paymentDao = new paymentDAO();

            List<Payment> payments =
                    paymentDao.getPaymentsByAthlete(athleteId);
            // ================= EVENTS =================

EventDAO eventDAO = new EventDAO();

List<Event> events = eventDAO.getAllEvents();

            boolean admissionPaid = false;

            for (Payment p : payments) {

                if (
                        "ADMISSION".equalsIgnoreCase(p.getPaymentType())
                                &&
                                "SUCCESS".equalsIgnoreCase(p.getPaymentStatus())
                ) {
                    admissionPaid = true;
                    break;
                }
            }

            String status = athlete.getStatus();
            boolean isApproved = "APPROVED".equalsIgnoreCase(status);

            boolean canRegisterEvent = isApproved && admissionPaid;
            

            // ================= HTML START =================

            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");

            out.println("<meta charset='UTF-8'>");
            out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");

            out.println("<title>Athlete Dashboard</title>");

            // ================= CSS =================

            out.println("<style>");

            out.println("*{");
            out.println("margin:0;");
            out.println("padding:0;");
            out.println("box-sizing:border-box;");
            out.println("font-family:Arial,sans-serif;");
            out.println("}");

            out.println("body{");
            out.println("background:#f1f5f9;");
            out.println("display:flex;");
            out.println("color:#0f172a;");
            out.println("}");

            // ================= SIDEBAR =================

            out.println(".sidebar{");
            out.println("width:280px;");
            out.println("height:100vh;");
            out.println("background:#0f172a;");
            out.println("color:white;");
            out.println("position:fixed;");
            out.println("left:0;");
            out.println("top:0;");
            out.println("padding:30px 20px;");
            out.println("overflow-y:auto;");
            out.println("}");

            out.println(".logo{");
            out.println("font-size:30px;");
            out.println("font-weight:bold;");
            out.println("margin-bottom:35px;");
            out.println("}");

            out.println(".logo span{");
            out.println("color:#3b82f6;");
            out.println("}");

            out.println(".athlete-profile{");
            out.println("text-align:center;");
            out.println("margin-bottom:35px;");
            out.println("}");

            out.println(".athlete-profile img{");
            out.println("width:110px;");
            out.println("height:110px;");
            out.println("border-radius:50%;");
            out.println("object-fit:cover;");
            out.println("border:4px solid #3b82f6;");
            out.println("}");

            out.println(".athlete-profile h3{");
            out.println("margin-top:15px;");
            out.println("font-size:20px;");
            out.println("}");

            out.println(".athlete-profile p{");
            out.println("margin-top:5px;");
            out.println("color:#94a3b8;");
            out.println("font-size:14px;");
            out.println("}");

            out.println(".status-approved,.status-pending,.status-rejected{");
            out.println("margin-top:15px;");
            out.println("padding:10px;");
            out.println("border-radius:30px;");
            out.println("font-size:13px;");
            out.println("font-weight:bold;");
            out.println("text-align:center;");
            out.println("}");

            out.println(".status-approved{background:#16a34a;}");
            out.println(".status-pending{background:#f59e0b;}");
            out.println(".status-rejected{background:#ef4444;}");

            out.println(".sidebar ul{");
            out.println("list-style:none;");
            out.println("margin-top:25px;");
            out.println("}");

            out.println(".sidebar ul li{");
            out.println("padding:15px;");
            out.println("border-radius:12px;");
            out.println("margin-bottom:10px;");
            out.println("cursor:pointer;");
            out.println("transition:0.3s;");
            out.println("font-weight:bold;");
            out.println("}");

            out.println(".sidebar ul li:hover,.sidebar ul li.active{");
            out.println("background:#1e293b;");
            out.println("}");

            // ================= MAIN =================

            out.println(".main-content{");
            out.println("margin-left:280px;");
            out.println("width:calc(100% - 280px);");
            out.println("padding:35px;");
            out.println("min-height:100vh;");
            out.println("}");

            out.println(".page{display:none;}");
            out.println(".active-page{display:block;}");

            out.println(".topbar{");
            out.println("margin-bottom:30px;");
            out.println("}");

            out.println(".topbar h1{");
            out.println("font-size:34px;");
            out.println("}");

            out.println(".topbar p{");
            out.println("margin-top:6px;");
            out.println("color:#64748b;");
            out.println("}");

            // ================= STATS =================

            out.println(".stats-grid{");
            out.println("display:grid;");
            out.println("grid-template-columns:repeat(auto-fit,minmax(240px,1fr));");
            out.println("gap:20px;");
            out.println("margin-bottom:30px;");
            out.println("}");

            out.println(".stat-card{");
            out.println("background:white;");
            out.println("border-radius:20px;");
            out.println("padding:25px;");
            out.println("display:flex;");
            out.println("align-items:center;");
            out.println("gap:18px;");
            out.println("box-shadow:0 4px 12px rgba(0,0,0,0.08);");
            out.println("}");

            out.println(".stat-icon{");
            out.println("width:65px;");
            out.println("height:65px;");
            out.println("background:#dbeafe;");
            out.println("border-radius:16px;");
            out.println("display:flex;");
            out.println("justify-content:center;");
            out.println("align-items:center;");
            out.println("font-size:28px;");
            out.println("}");

            // ================= GRID FIX =================

            out.println(".dashboard-grid{");
            out.println("display:grid;");
            out.println("grid-template-columns:1fr;");
            out.println("gap:25px;");
            out.println("}");

            // ================= CARD =================

            out.println(".card{");
            out.println("background:white;");
            out.println("border-radius:20px;");
            out.println("padding:25px;");
            out.println("box-shadow:0 4px 12px rgba(0,0,0,0.08);");
            out.println("width:100%;");
            out.println("}");

            out.println(".card-header{");
            out.println("margin-bottom:20px;");
            out.println("}");

            out.println(".card-header h2{");
            out.println("font-size:24px;");
            out.println("}");

            // ================= ACTIVITY =================

            out.println(".activity-item{");
            out.println("display:flex;");
            out.println("gap:15px;");
            out.println("margin-bottom:25px;");
            out.println("}");

            out.println(".activity-dot{");
            out.println("width:14px;");
            out.println("height:14px;");
            out.println("border-radius:50%;");
            out.println("margin-top:6px;");
            out.println("flex-shrink:0;");
            out.println("}");

            out.println(".green{background:#16a34a;}");
            out.println(".blue{background:#2563eb;}");
            out.println(".orange{background:#f59e0b;}");
            out.println(".red{background:#ef4444;}");
            out.println(".grey{background:#94a3b8;}");

            out.println(".activity-item h4{");
            out.println("font-size:16px;");
            out.println("margin-bottom:5px;");
            out.println("}");

            out.println(".activity-item p{");
            out.println("color:#64748b;");
            out.println("line-height:1.6;");
            out.println("}");

            // ================= PROFILE =================

            out.println(".profile-info{");
            out.println("display:flex;");
            out.println("flex-direction:column;");
            out.println("gap:15px;");
            out.println("}");

            out.println(".info-row{");
            out.println("background:#f8fafc;");
            out.println("padding:16px;");
            out.println("border-radius:12px;");
            out.println("display:flex;");
            out.println("justify-content:space-between;");
            out.println("align-items:center;");
            out.println("gap:15px;");
            out.println("}");

            out.println(".info-row span{");
            out.println("color:#64748b;");
            out.println("font-weight:bold;");
            out.println("}");

            out.println(".info-row strong{");
            out.println("text-align:right;");
            out.println("}");

            // ================= PAYMENT =================

            out.println(".payment-box{");
            out.println("text-align:center;");
            out.println("padding:20px 0;");
            out.println("}");

            out.println(".amount-display{");
            out.println("font-size:52px;");
            out.println("font-weight:bold;");
            out.println("margin:20px 0;");
            out.println("}");

            out.println(".pay-btn{");
            out.println("width:100%;");
            out.println("padding:16px;");
            out.println("background:#2563eb;");
            out.println("color:white;");
            out.println("border:none;");
            out.println("border-radius:12px;");
            out.println("font-size:16px;");
            out.println("font-weight:bold;");
            out.println("cursor:pointer;");
            out.println("}");

            out.println(".pay-btn:hover{");
            out.println("background:#1d4ed8;");
            out.println("}");

            out.println(".paid-badge{");
            out.println("background:#dcfce7;");
            out.println("color:#16a34a;");
            out.println("padding:16px;");
            out.println("border-radius:12px;");
            out.println("font-weight:bold;");
            out.println("}");

            out.println(".locked-notice{");
            out.println("background:#fef9c3;");
            out.println("padding:16px;");
            out.println("border-radius:12px;");
            out.println("color:#92400e;");
            out.println("line-height:1.7;");
            out.println("}");

            // ================= REGISTRATION =================

            out.println(".registration-item{");
            out.println("display:flex;");
            out.println("justify-content:space-between;");
            out.println("align-items:center;");
            out.println("padding:18px;");
            out.println("background:#f8fafc;");
            out.println("border-radius:12px;");
            out.println("margin-bottom:15px;");
            out.println("}");

            out.println(".paid{");
            out.println("color:#16a34a;");
            out.println("font-weight:bold;");
            out.println("}");

            out.println(".pending{");
            out.println("color:#f59e0b;");
            out.println("font-weight:bold;");
            out.println("}");

            // ================= BUTTON =================

            out.println(".back-btn{");
            out.println("background:#0f172a;");
            out.println("color:white;");
            out.println("border:none;");
            out.println("padding:12px 20px;");
            out.println("border-radius:10px;");
            out.println("margin-bottom:20px;");
            out.println("cursor:pointer;");
            out.println("font-weight:bold;");
            out.println("}");

            // ================= RESPONSIVE =================

            out.println("@media(max-width:900px){");

            out.println(".sidebar{");
            out.println("display:none;");
            out.println("}");

            out.println(".main-content{");
            out.println("margin-left:0;");
            out.println("width:100%;");
            out.println("padding:20px;");
            out.println("}");

            out.println(".info-row{");
            out.println("flex-direction:column;");
            out.println("align-items:flex-start;");
            out.println("}");

            out.println("}");

            out.println("</style>");
            out.println("</head>");
            out.println("<body>");

            // ================= SIDEBAR =================

            out.println("<div class='sidebar'>");

            out.println("<div class='logo'>Sports<span>Ecosystem</span></div>");

            out.println("<div class='athlete-profile'>");

            String photoPath = "https://i.pravatar.cc/200";

            if(docs != null && docs.getPhotoPath() != null)
            {
                photoPath = docs.getPhotoPath();
            }

            out.println("<img src='" + photoPath + "' onerror=\"this.src='https://i.pravatar.cc/200'\">");
            out.println("<h3>" + athlete.getFullName() + "</h3>");

            out.println("<p>Registered Athlete</p>");

            String badgeClass;

            switch (status.toUpperCase()) {

                case "APPROVED":
                    badgeClass = "status-approved";
                    break;

                case "REJECTED":
                    badgeClass = "status-rejected";
                    break;

                default:
                    badgeClass = "status-pending";
            }

            out.println("<div class='" + badgeClass + "'>" + status + "</div>");

            out.println("</div>");

            out.println("<ul>");
            out.println("<li class='menu active' data-page='dashboardPage'>🏠 Dashboard</li>");
            out.println("<li class='menu' data-page='profilePage'>👤 My Profile</li>");
            out.println("<li class='menu' data-page='paymentPage'>💳 Payment</li>");
            out.println("<li class='menu' data-page='registrationPage'>📋 Registrations</li>");
            out.println("<li class='menu' data-page='eventsPage'>🏆 Events</li>");
            out.println("<li onclick='logoutUser()'>🚪 Logout</li>");
            out.println("</ul>");

            out.println("</div>");

            // ================= MAIN =================

            out.println("<div class='main-content'>");

            // ================= DASHBOARD =================

            out.println("<div id='dashboardPage' class='page active-page'>");

            out.println("<div class='topbar'>");
            out.println("<h1>Athlete Dashboard</h1>");
            out.println("<p>Welcome back, " + athlete.getFullName() + "</p>");
            out.println("</div>");

            // ================= STATS =================

            out.println("<div class='stats-grid'>");

            out.println("<div class='stat-card'>");
            out.println("<div class='stat-icon'>🏆</div>");
            out.println("<div><h2>00</h2><p>Events Participated</p></div>");
            out.println("</div>");

            out.println("<div class='stat-card'>");
            out.println("<div class='stat-icon'>📅</div>");
            out.println("<div><h2>00</h2><p>Upcoming Events</p></div>");
            out.println("</div>");

            out.println("<div class='stat-card'>");
            out.println("<div class='stat-icon'>📄</div>");
            out.println("<div><h2>100%</h2><p>Profile Completion</p></div>");
            out.println("</div>");

            out.println("<div class='stat-card'>");
            out.println("<div class='stat-icon'>⭐</div>");
            out.println("<div><h2>" + sports.getCategory() + "</h2><p>Category</p></div>");
            out.println("</div>");

            out.println("</div>");

            // ================= ACTIVITY =================

            out.println("<div class='dashboard-grid'>");

            out.println("<div class='card'>");

            out.println("<div class='card-header'>");
            out.println("<h2>Recent Activity</h2>");
            out.println("</div>");

            // ALWAYS

            out.println("<div class='activity-item'>");
            out.println("<div class='activity-dot orange'></div>");
            out.println("<div>");
            out.println("<h4>Registration Submitted</h4>");
            out.println("<p>Your athlete registration was submitted successfully.</p>");
            out.println("</div>");
            out.println("</div>");

            out.println("<div class='activity-item'>");
            out.println("<div class='activity-dot blue'></div>");
            out.println("<div>");
            out.println("<h4>Documents Under Verification</h4>");
            out.println("<p>Admin is reviewing your uploaded documents.</p>");
            out.println("</div>");
            out.println("</div>");

            // APPROVED

            if ("APPROVED".equalsIgnoreCase(status)) {

                out.println("<div class='activity-item'>");
                out.println("<div class='activity-dot green'></div>");
                out.println("<div>");
                out.println("<h4>Registration Approved ✓</h4>");
                out.println("<p>Your registration has been approved by admin.</p>");
                out.println("</div>");
                out.println("</div>");

                out.println("<div class='activity-item'>");
                out.println("<div class='activity-dot blue'></div>");
                out.println("<div>");
                out.println("<h4>Payment Access Enabled</h4>");
                out.println("<p>You can now pay the admission fee.</p>");
                out.println("</div>");
                out.println("</div>");

                if (admissionPaid) {

                    out.println("<div class='activity-item'>");
                    out.println("<div class='activity-dot green'></div>");
                    out.println("<div>");
                    out.println("<h4>Admission Payment Successful ✓</h4>");
                    out.println("<p>Your athlete account is fully activated.</p>");
                    out.println("</div>");
                    out.println("</div>");
                }

            }

            // REJECTED

            else if ("REJECTED".equalsIgnoreCase(status)) {

                out.println("<div class='activity-item'>");
                out.println("<div class='activity-dot red'></div>");
                out.println("<div>");
                out.println("<h4>Registration Rejected</h4>");
                out.println("<p>Your registration was rejected by admin.</p>");
                out.println("</div>");
                out.println("</div>");

            }

            // PENDING

            else {

                out.println("<div class='activity-item'>");
                out.println("<div class='activity-dot grey'></div>");
                out.println("<div>");
                out.println("<h4 style='color:#94a3b8;'>Awaiting Approval</h4>");
                out.println("<p>Please wait while admin reviews your profile.</p>");
                out.println("</div>");
                out.println("</div>");
            }

            out.println("</div>");

            // ================= PROFILE SUMMARY =================

            out.println("<div class='card'>");

            out.println("<div class='card-header'>");
            out.println("<h2>Profile Summary</h2>");
            out.println("</div>");

            out.println("<div class='profile-info'>");

            out.println("<div class='info-row'><span>Athlete ID</span><strong>" + athlete.getAthleteId() + "</strong></div>");
            out.println("<div class='info-row'><span>Sport</span><strong>" + sports.getSportType() + "</strong></div>");
            out.println("<div class='info-row'><span>Category</span><strong>" + sports.getCategory() + "</strong></div>");
            out.println("<div class='info-row'><span>Club</span><strong>" + sports.getClubName() + "</strong></div>");
            out.println("<div class='info-row'><span>State</span><strong>" + address.getState() + "</strong></div>");

            out.println("</div>");
            out.println("</div>");

            out.println("</div>");
            out.println("</div>");

            // ================= PROFILE PAGE =================

            out.println("<div id='profilePage' class='page'>");

            out.println("<button class='back-btn' onclick='goDashboard()'>← Back</button>");

            out.println("<div class='card'>");

            out.println("<div class='card-header'>");
            out.println("<h2>My Profile</h2>");
            out.println("</div>");

            out.println("<div class='profile-info'>");

            out.println("<div class='info-row'><span>Full Name</span><strong>" + athlete.getFullName() + "</strong></div>");
            out.println("<div class='info-row'><span>Age</span><strong>" + athlete.getAge() + "</strong></div>");
            out.println("<div class='info-row'><span>Gender</span><strong>" + athlete.getGender() + "</strong></div>");
            out.println("<div class='info-row'><span>Mobile</span><strong>" + athlete.getMobile() + "</strong></div>");
            out.println("<div class='info-row'><span>Email</span><strong>" + athlete.getEmail() + "</strong></div>");
            out.println("<div class='info-row'><span>Sport</span><strong>" + sports.getSportType() + "</strong></div>");
            out.println("<div class='info-row'><span>Category</span><strong>" + sports.getCategory() + "</strong></div>");
            out.println("<div class='info-row'><span>Guardian</span><strong>" + guardian.getGuardianName() + "</strong></div>");

            out.println("</div>");
            out.println("</div>");
            out.println("</div>");

            // ================= PAYMENT PAGE =================

            out.println("<div id='paymentPage' class='page'>");

            out.println("<button class='back-btn' onclick='goDashboard()'>← Back</button>");

            out.println("<div class='card'>");

            out.println("<div class='card-header'>");
            out.println("<h2>Admission Payment</h2>");
            out.println("</div>");

            out.println("<div class='payment-box'>");

            out.println("<div class='amount-display'>₹5000</div>");

if (admissionPaid) {

    out.println("<div class='paid-badge'>");
    out.println("✓ Admission Fee Already Paid");
    out.println("</div>");

}
else if ("APPROVED".equalsIgnoreCase(status)) {

    out.println("<form action='admissionPayment.html' method='get'>");

    out.println("<input type='hidden' name='athleteId' value='" + athleteId + "'>");

    out.println("<button type='submit' class='pay-btn'>");
    out.println("Pay Now");
    out.println("</button>");

    out.println("</form>");

}
else {

    out.println("<div class='locked-notice'>");
    out.println("Payment will be enabled after admin approval.");
    out.println("</div>");
}


            out.println("</div>");
            out.println("</div>");
            out.println("</div>");

            // ================= REGISTRATION PAGE =================

            out.println("<div id='registrationPage' class='page'>");

            out.println("<button class='back-btn' onclick='goDashboard()'>← Back</button>");

            out.println("<div class='card'>");

            out.println("<div class='card-header'>");
            out.println("<h2>My Registrations</h2>");
            out.println("</div>");

            if (payments.isEmpty()) {

                out.println("<p>No payment records found.</p>");

            } else {

                for (Payment p : payments) {

                    String cls =
                            "SUCCESS".equalsIgnoreCase(p.getPaymentStatus())
                                    ? "paid"
                                    : "pending";

                    out.println("<div class='registration-item'>");

                    out.println("<span>" +
                            p.getPaymentType() +
                            " - ₹" +
                            (int) p.getAmount() +
                            "</span>");

                    out.println("<span class='" + cls + "'>" +
                            p.getPaymentStatus() +
                            "</span>");

                    out.println("</div>");
                }
            }

            out.println("</div>");
            out.println("</div>");

            out.println("</div>");
            // ================= EVENTS PAGE =================

out.println("<div id='eventsPage' class='page'>");

out.println("<button class='back-btn' onclick='goDashboard()'>← Back</button>");

out.println("<div class='card'>");

out.println("<div class='card-header'>");
out.println("<h2>Available Events</h2>");
out.println("</div>");

if(!isApproved)
{
    out.println("<div class='locked-notice'>");
    out.println("Events will be available after admin approval.");
    out.println("</div>");
}
else if(events.isEmpty())
{
    out.println("<p>No events available right now.</p>");
}
else
{
    for(Event e : events)
    {
        out.println("<div style='background:#f8fafc;padding:20px;border-radius:18px;margin-bottom:20px;'>");

        out.println("<img src='" + e.getEventBanner() + "' " +
                "style='width:100%;height:220px;object-fit:cover;border-radius:14px;'>");

        out.println("<h2 style='margin-top:15px;color:#0f172a;'>"
                + e.getEventName() +
                "</h2>");

        out.println("<p style='margin-top:10px;color:#475569;'>"
                + e.getDescription() +
                "</p>");

        out.println("<div style='margin-top:15px;'>");

        out.println("<p><strong>Sport:</strong> "
                + e.getSportType() +
                "</p>");

        out.println("<p><strong>Date:</strong> "
                + e.getEventDate() +
                "</p>");

        out.println("<p><strong>Location:</strong> "
                + e.getLocation() +
                "</p>");

        out.println("<p><strong>Registration Fee:</strong> ₹"
                + e.getRegistrationFee() +
                "</p>");

        out.println("</div>");

        // REGISTER BUTTON

        if(canRegisterEvent)
{
    // REPLACE WITH - redirect to eventPayment.html just like admissionPayment.html
out.println("<form action='eventPayment.html' method='get' style='margin-top:18px;'>");

out.println("<input type='hidden' name='eventId' value='"
        + e.getEventId() +
        "'>");

out.println("<input type='hidden' name='athleteId' value='"
        + athleteId +
        "'>");

out.println("<input type='hidden' name='amount' value='"
        + e.getRegistrationFee() +
        "'>");

out.println("<input type='hidden' name='eventName' value='"
        + e.getEventName().replace("'", "\\'") +
        "'>");

out.println("<button type='submit' class='pay-btn'>");
out.println("Register Now");
out.println("</button>");

out.println("</form>");
}
else
{
    out.println("<div class='locked-notice' style='margin-top:18px;'>");

    out.println("Complete admission payment first to register for events.");

    out.println("</div>");
}
        out.println("</div>");
    }
}

out.println("</div>");
out.println("</div>");

            // ================= JS =================

            out.println("<script>");

            out.println("const menu=document.querySelectorAll('.menu');");
            out.println("const pages=document.querySelectorAll('.page');");

            out.println("menu.forEach(item=>{");

            out.println("item.addEventListener('click',()=>{");

            out.println("menu.forEach(i=>i.classList.remove('active'));");
            out.println("item.classList.add('active');");

            out.println("pages.forEach(p=>p.classList.remove('active-page'));");

            out.println("document.getElementById(item.dataset.page).classList.add('active-page');");

            out.println("});");

            out.println("});");

            out.println("function goDashboard(){");

            out.println("pages.forEach(p=>p.classList.remove('active-page'));");

            out.println("document.getElementById('dashboardPage').classList.add('active-page');");

            out.println("menu.forEach(i=>i.classList.remove('active'));");

            out.println("document.querySelector('[data-page=\"dashboardPage\"]').classList.add('active');");

            out.println("}");

            out.println("function logoutUser(){");
            out.println("window.location.href='UserLoginServlet?action=logout';");
            out.println("}");

            out.println("</script>");

            out.println("</body>");
            out.println("</html>");

        }

        catch (Exception e) {

            out.println("<h2>Error : " + e.getMessage() + "</h2>");
            e.printStackTrace(out);
        }
    }
}