package controller;

import DAO.athleteDAO;

import java.io.IOException;
import java.io.PrintWriter;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Address;
import model.Athlete;
import model.AthleteCompleteProfile;
import model.Documents;
import model.Guardian;
import model.SportsProfile;

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

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();


        try
        {

            long athleteId =
            Long.parseLong(
            request.getParameter("id")
            );

            athleteDAO dao =
            new athleteDAO();

            AthleteCompleteProfile profile =
            dao.getAthleteById(athleteId);

            if(profile == null ||
               profile.getAthlete() == null)
            {
                out.println("<h2>No Athlete Found</h2>");
                return;
            }

            Athlete athlete =
            profile.getAthlete();

            Guardian guardian =
            profile.getGuardian();

            Address address =
            profile.getAddress();

            SportsProfile sports =
            profile.getSportsProfile();

            Documents docs =
            profile.getDocuments();

            out.println("<!DOCTYPE html>");

            out.println("<html>");

            out.println("<head>");

            out.println("<title>Athlete Profile</title>");

            out.println
            (
            "<link href='https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap' rel='stylesheet'>"
            );

            out.println("<style>");

            out.println("*{margin:0;padding:0;box-sizing:border-box;font-family:'Inter',sans-serif;}");

            out.println("body{background:#f1f5f9;color:#0f172a;}");

            out.println(".topbar{height:75px;background:white;display:flex;align-items:center;justify-content:space-between;padding:0 40px;border-bottom:1px solid #e2e8f0;}");

            out.println(".logo{font-size:24px;font-weight:700;}");

            out.println(".logo span{color:#2563eb;}");

            out.println(".btn:active {\n" +
"    transform: scale(0.95);\n" +
"}");
            
            out.println(".top-actions{display:flex;gap:12px;align-items:center;}");

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
out.println(".fab-container{position:fixed;bottom:25px;right:25px;z-index:9999;}");

out.println(".fab-main{width:62px;height:62px;border-radius:50%;background:#2563eb;color:white;display:flex;align-items:center;justify-content:center;font-size:26px;cursor:pointer;box-shadow:0 10px 25px rgba(0,0,0,0.25);transition:0.3s;}");

out.println(".fab-container.active .fab-main{transform:rotate(45deg) scale(1.05);background:#1d4ed8;}");

/* STACK */
out.println(".fab-actions{position:absolute;bottom:80px;right:0;display:flex;flex-direction:column;gap:12px;opacity:0;pointer-events:none;transition:0.3s;}");

out.println(".fab-container.active .fab-actions{opacity:1;pointer-events:auto;}");

/* ITEM */
out.println(".fab-item{position:relative;background:white;border:1px solid #e2e8f0;padding:10px 14px;border-radius:12px;text-decoration:none;color:#0f172a;font-size:13px;font-weight:600;display:flex;align-items:center;gap:10px;box-shadow:0 6px 15px rgba(0,0,0,0.08);transform:translateY(10px);opacity:0;transition:0.3s;}");

/* STAGGER ANIMATION */
out.println(".fab-container.active .fab-item{transform:translateY(0);opacity:1;}");

out.println(".fab-container.active .fab-item:nth-child(1){transition-delay:0.05s;}");
out.println(".fab-container.active .fab-item:nth-child(2){transition-delay:0.10s;}");
out.println(".fab-container.active .fab-item:nth-child(3){transition-delay:0.15s;}");
out.println(".fab-container.active .fab-item:nth-child(4){transition-delay:0.20s;}");
out.println(".fab-container.active .fab-item:nth-child(5){transition-delay:0.25s;}");

/* HOVER */
out.println(".fab-item:hover{background:#f1f5f9;transform:scale(1.05);}");

/* TOOLTIP */
out.println(".fab-item::after{content:attr(data-tooltip);position:absolute;right:110%;background:#0f172a;color:white;font-size:11px;padding:6px 10px;border-radius:8px;white-space:nowrap;opacity:0;transform:translateX(5px);transition:0.2s;pointer-events:none;}");

out.println(".fab-item:hover::after{opacity:1;transform:translateX(0);} ");
            
            out.println(".card-title{font-size:21px;font-weight:700;margin-bottom:24px;}");

            out.println(".info-grid{display:grid;grid-template-columns:repeat(auto-fit,minmax(220px,1fr));gap:20px;}");

            out.println(".info-item{background:#f8fafc;padding:18px;border-radius:18px;}");

            out.println(".info-label{font-size:13px;color:#64748b;margin-bottom:8px;}");

            out.println(".info-value{font-size:16px;font-weight:600;}");

            out.println(".full-address{margin-top:20px;background:#f8fafc;border-radius:18px;padding:20px;}");

            out.println(".docs{display:grid;grid-template-columns:repeat(auto-fit,minmax(240px,1fr));gap:20px;}");

            out.println(".doc-card{border:1px solid #e2e8f0;border-radius:18px;padding:20px;display:flex;justify-content:space-between;align-items:center;}");

            out.println(".download{text-decoration:none;background:#2563eb;color:white;padding:10px 16px;border-radius:10px;font-size:13px;font-weight:600;}");

            out.println(".btn{border:none;padding:12px 18px;border-radius:14px;font-weight:600;cursor:pointer;background:#2563eb;color:white;text-decoration:none;}");

            out.println(".btn:hover{opacity:0.9;}");

            out.println("</style>");

            out.println("</head>");

            out.println("<body>");

            out.println("<script>");

            out.println
            (
            "function openExportModal(){"
            +
            "document.getElementById('exportModal').style.display='flex';"
            +
            "}"
            );

            out.println
            (
            "function closeExportModal(){"
            +
            "document.getElementById('exportModal').style.display='none';"
            +
            "}"
            );
           
            out.println(
                "function toggleFAB(){" +
                " const fab=document.getElementById('fab');" +
                " fab.classList.toggle('active');" +
                "}"
                );
  out.println(
"function handleExportClick() {" +
    "const fab = document.getElementById('fab');" +
    "fab.classList.toggle('active');" +
"}"
);

            out.println("</script>");

            // TOPBAR

            out.println("<div class='topbar'>");

            out.println("<div class='logo'><span>Sports</span>Ecosystem</div>");

            out.println("<div class='top-actions'>");

            out.println("<button class='btn'>Approve Athlete</button>");

            out.println("<button class='btn' onclick=\"handleExportClick()\">Export ⬇</button>");

            out.println("</div>");

            out.println("</div>");

            // WRAPPER

            out.println("<div class='wrapper'>");

            // SIDEBAR

            out.println("<div class='sidebar'>");

            out.println
            (
            "<img src='" +
            docs.getPhotoPath() +
            "' class='profile-pic'>"
            );

            out.println
            (
            "<div class='player-name'>" +
            athlete.getFullName() +
            "</div>"
            );

            out.println
            (
            "<div class='player-role'>Registered Athlete</div>"
            );

            out.println
            (
            "<div class='badge'>STATUS : " +
            athlete.getStatus() +
            "</div>"
            );

            out.println("<div class='quick-info'>");

            out.println("<div class='quick-box'>");
            out.println("<div class='quick-title'>Athlete ID</div>");
            out.println("<div class='quick-value'>" + athlete.getAthleteId() + "</div>");
            out.println("</div>");

            out.println("<div class='quick-box'>");
            out.println("<div class='quick-title'>Sport Type</div>");
            out.println("<div class='quick-value'>" + sports.getSportType() + "</div>");
            out.println("</div>");

            out.println("<div class='quick-box'>");
            out.println("<div class='quick-title'>Training Level</div>");
            out.println("<div class='quick-value'>" + sports.getTrainingLevel() + "</div>");
            out.println("</div>");

            out.println("<div class='quick-box'>");
            out.println("<div class='quick-title'>Club Name</div>");
            out.println("<div class='quick-value'>" + sports.getClubName() + "</div>");
            out.println("</div>");

            out.println("</div>");

            out.println("</div>");

            // MAIN CONTENT

            out.println("<div class='main-content'>");

            // ATHLETE INFO

            out.println("<div class='card'>");

            out.println("<div class='card-title'>Athlete Information</div>");

            out.println("<div class='info-grid'>");

            out.println("<div class='info-item'><div class='info-label'>Full Name</div><div class='info-value'>" + athlete.getFullName() + "</div></div>");

            out.println("<div class='info-item'><div class='info-label'>DOB</div><div class='info-value'>" + athlete.getDob() + "</div></div>");

            out.println("<div class='info-item'><div class='info-label'>Age</div><div class='info-value'>" + athlete.getAge() + "</div></div>");

            out.println("<div class='info-item'><div class='info-label'>Gender</div><div class='info-value'>" + athlete.getGender() + "</div></div>");

            out.println("<div class='info-item'><div class='info-label'>Mobile</div><div class='info-value'>" + athlete.getMobile() + "</div></div>");

            out.println("<div class='info-item'><div class='info-label'>Email</div><div class='info-value'>" + athlete.getEmail() + "</div></div>");

            out.println("<div class='info-item'><div class='info-label'>Blood Group</div><div class='info-value'>" + athlete.getBloodGroup() + "</div></div>");

            out.println("<div class='info-item'><div class='info-label'>Created At</div><div class='info-value'>" + athlete.getCreatedAt() + "</div></div>");

            out.println("</div>");

            out.println("</div>");

            // GUARDIAN

            out.println("<div class='card'>");

            out.println("<div class='card-title'>Guardian Details</div>");

            out.println("<div class='info-grid'>");

            out.println("<div class='info-item'><div class='info-label'>Guardian Name</div><div class='info-value'>" + guardian.getGuardianName() + "</div></div>");

            out.println("<div class='info-item'><div class='info-label'>Relation</div><div class='info-value'>" + guardian.getRelation() + "</div></div>");

            out.println("<div class='info-item'><div class='info-label'>Emergency Contact</div><div class='info-value'>" + guardian.getEmergencyContact() + "</div></div>");

            out.println("</div>");

            out.println("</div>");

            // ADDRESS

            out.println("<div class='card'>");

            out.println("<div class='card-title'>Address Details</div>");

            out.println("<div class='info-grid'>");

            out.println("<div class='info-item'><div class='info-label'>State</div><div class='info-value'>" + address.getState() + "</div></div>");

            out.println("<div class='info-item'><div class='info-label'>District</div><div class='info-value'>" + address.getDistrict() + "</div></div>");

            out.println("<div class='info-item'><div class='info-label'>City</div><div class='info-value'>" + address.getCity() + "</div></div>");

            out.println("<div class='info-item'><div class='info-label'>PIN</div><div class='info-value'>" + address.getPinCode() + "</div></div>");

            out.println("</div>");

            out.println("<div class='full-address'>");

            out.println("<div class='info-label'>Full Address</div>");

            out.println("<div class='info-value'>" + address.getFullAddress() + "</div>");

            out.println("</div>");

            out.println("</div>");

            // SPORTS

            out.println("<div class='card'>");

            out.println("<div class='card-title'>Sports Profile</div>");

            out.println("<div class='info-grid'>");

            out.println("<div class='info-item'><div class='info-label'>Sport Type</div><div class='info-value'>" + sports.getSportType() + "</div></div>");

            out.println("<div class='info-item'><div class='info-label'>Category</div><div class='info-value'>" + sports.getCategory() + "</div></div>");

            out.println("<div class='info-item'><div class='info-label'>Training Level</div><div class='info-value'>" + sports.getTrainingLevel() + "</div></div>");

            out.println("<div class='info-item'><div class='info-label'>Club Name</div><div class='info-value'>" + sports.getClubName() + "</div></div>");

            out.println("</div>");

            out.println("</div>");

            // DOCUMENTS

            out.println("<div class='card'>");

            out.println("<div class='card-title'>Uploaded Documents</div>");

            out.println("<div class='docs'>");

            out.println("<div class='doc-card'>Athlete Photo <a href='" + docs.getPhotoPath() + "' class='download'>View</a></div>");

            out.println("<div class='doc-card'>Birth Certificate <a href='" + docs.getBirthCertPath() + "' class='download'>Download</a></div>");

            out.println("<div class='doc-card'>ID Proof <a href='" + docs.getIdProofPath() + "' class='download'>Download</a></div>");

            out.println("</div>");

            out.println("</div>");

            out.println("</div>");

            out.println("</div>");

           out.println(
"<div class='fab-container' id='fab'>" +

    "<div class='fab-actions'>" +


        "<a class='fab-item' data-tooltip='Download as PDF file' href='ViewPdfServlet?id=" + athleteId + "'>📄 <span>Download PDF</span></a>" +

        "<a class='fab-item' data-tooltip='Export Excel Sheet' href='ExportExcelServlet?id=" + athleteId + "'>📊 <span>Excel</span></a>" +

        "<a class='fab-item' data-tooltip='Export CSV File' href='ExportCSVServlet?id=" + athleteId + "'>🧾 <span>CSV</span></a>" +

        "<a class='fab-item' data-tooltip='Go Back to Dashboard' href='AdminDashboardServlet'>⬅ <span>Back</span></a>" +

    "</div>" +

    "<div class='fab-main' onclick='toggleFAB()'>+</div>" +

"</div>"
);
            
            out.println("</body>");

            out.println("</html>");
        }

        catch(Exception e)
        {
            e.printStackTrace(out);
        }
    }
}