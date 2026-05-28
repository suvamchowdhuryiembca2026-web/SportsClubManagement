package controller;

import DAO.coachDAO;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Coach;

public class ViewCoachServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        response.setHeader("Connection", "close"); 
        PrintWriter out = response.getWriter();

        try 
        {
          
            String idParam = request.getParameter("id");
            if (idParam == null || idParam.trim().isEmpty()) {
                out.println("<!DOCTYPE html><html><body><h2>Missing coach ID.</h2></body></html>");
                out.flush();
                out.close();
                return;
            }

            long coachId = Long.parseLong(idParam.trim());

            coachDAO dao = new coachDAO();
            Coach coach = dao.getCoachById(coachId);

           
            if (coach == null) {
                out.println("<!DOCTYPE html><html><body><h2>No Coach Found</h2></body></html>");
                out.flush();
                out.close();
                return;
            }

            out.println("<!DOCTYPE html><html><head><title>Coach Profile</title>");
            out.println("<link href='https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap' rel='stylesheet'>");
            out.println("<style>");

            out.println(".tbtn-reject{background:#ef4444;color:white;}");
            out.println("*{margin:0;padding:0;box-sizing:border-box;font-family:'Inter',sans-serif;}");
            out.println("body{min-height:100vh;background:linear-gradient(to right,#01236d,#3861e7);}");

            out.println(".topbar{height:68px;padding:0 36px;display:flex;align-items:center;justify-content:space-between;" +
                "background:rgba(0,0,0,0.25);backdrop-filter:blur(12px);-webkit-backdrop-filter:blur(12px);" +
                "border-bottom:1px solid rgba(255,255,255,0.12);position:sticky;top:0;z-index:999;}");
            out.println(".logo{font-size:22px;font-weight:700;color:white;display:flex;align-items:center;gap:10px;}");
            out.println(".logo-icon{width:36px;height:36px;background:white;border-radius:8px;display:flex;align-items:center;" +
                "justify-content:center;font-size:13px;font-weight:bold;color:#01236d;}");
            out.println(".top-actions{display:flex;gap:10px;align-items:center;}");
            out.println(".tbtn{padding:9px 18px;border:none;border-radius:8px;font-size:13px;font-weight:600;cursor:pointer;" +
                "transition:opacity 0.2s;text-decoration:none;display:inline-flex;align-items:center;gap:6px;}");
            out.println(".tbtn:hover{opacity:0.85;}");
            out.println(".tbtn-approve{background:#22c55e;color:white;}");
            out.println(".tbtn-export{background:rgba(255,255,255,0.15);color:white;border:1px solid rgba(255,255,255,0.3);}");

            out.println(".wrapper{display:grid;grid-template-columns:300px 1fr;gap:24px;padding:28px;}");

            out.println(".sidebar{background:rgba(255,255,255,0.12);backdrop-filter:blur(16px);-webkit-backdrop-filter:blur(16px);" +
                "border:1px solid rgba(255,255,255,0.2);border-radius:24px;padding:28px;height:fit-content;" +
                "box-shadow:0 8px 32px rgba(0,0,0,0.18);}");
            out.println(".profile-pic{width:130px;height:130px;border-radius:50%;object-fit:cover;display:block;margin:auto;" +
                "border:4px solid rgba(255,255,255,0.5);}");
            out.println(".player-name{text-align:center;margin-top:18px;font-size:22px;font-weight:700;color:white;}");
            out.println(".player-role{text-align:center;margin-top:6px;color:rgba(255,255,255,0.65);font-size:13px;}");

            out.println(".badge-pending{width:fit-content;margin:16px auto 0;background:rgba(251,146,60,0.25);color:#fed7aa;" +
                "border:1px solid rgba(251,146,60,0.5);padding:8px 18px;border-radius:999px;font-size:12px;font-weight:700;letter-spacing:1px;}");
            out.println(".badge-approved{width:fit-content;margin:16px auto 0;background:rgba(34,197,94,0.25);color:#bbf7d0;" +
                "border:1px solid rgba(34,197,94,0.5);padding:8px 18px;border-radius:999px;font-size:12px;font-weight:700;letter-spacing:1px;}");
            out.println(".badge-rejected{width:fit-content;margin:16px auto 0;background:rgba(239,68,68,0.25);color:#fecaca;" +
                "border:1px solid rgba(239,68,68,0.5);padding:8px 18px;border-radius:999px;font-size:12px;font-weight:700;letter-spacing:1px;}");

            out.println(".quick-info{margin-top:24px;display:flex;flex-direction:column;gap:10px;}");
            out.println(".quick-box{padding:14px 16px;border-radius:14px;background:rgba(255,255,255,0.10);" +
                "border:1px solid rgba(255,255,255,0.15);}");
            out.println(".quick-title{font-size:11px;color:rgba(255,255,255,0.6);margin-bottom:4px;" +
                "text-transform:uppercase;letter-spacing:0.8px;}");
            out.println(".quick-value{font-size:15px;font-weight:700;color:white;word-break:break-all;overflow-wrap:anywhere;}");

            out.println(".main-content{display:flex;flex-direction:column;gap:20px;}");
            out.println(".card{background:white;border-radius:22px;border:1px solid #e2e8f0;padding:26px;" +
                "box-shadow:0 4px 16px rgba(0,0,0,0.08);}");
            out.println(".card-title{font-size:17px;font-weight:700;color:#0f172a;margin-bottom:20px;" +
                "padding-bottom:12px;border-bottom:1px solid #f1f5f9;}");

            out.println(".info-grid{display:grid;grid-template-columns:repeat(auto-fit,minmax(220px,1fr));gap:14px;}");
            out.println(".info-item{background:#f8fafc;border:1px solid #e2e8f0;padding:14px 16px;border-radius:14px;min-width:0;}");
            out.println(".info-label{font-size:11px;color:#64748b;margin-bottom:6px;text-transform:uppercase;letter-spacing:0.8px;}");
            out.println(".info-value{font-size:15px;font-weight:600;color:#0f172a;word-break:break-all;overflow-wrap:anywhere;}");

            out.println(".full-block{margin-top:14px;background:#f8fafc;border:1px solid #e2e8f0;" +
                "border-radius:14px;padding:16px;}");

            out.println(".rejection-box{margin-top:14px;background:#fff1f2;border:1px solid #fecaca;" +
                "border-radius:14px;padding:16px;}");
            out.println(".rejection-label{font-size:11px;color:#ef4444;margin-bottom:6px;" +
                "text-transform:uppercase;letter-spacing:0.8px;}");
            out.println(".rejection-value{font-size:14px;font-weight:500;color:#7f1d1d;}");

            out.println(".docs{display:grid;grid-template-columns:repeat(auto-fit,minmax(220px,1fr));gap:14px;}");
            out.println(".doc-card{background:#f8fafc;border:1px solid #e2e8f0;border-radius:14px;padding:16px 20px;" +
                "display:flex;justify-content:space-between;align-items:center;color:#0f172a;font-size:15px;font-weight:600;}");
            out.println(".download{text-decoration:none;background:#2563eb;color:white;padding:8px 14px;" +
                "border-radius:8px;font-size:12px;font-weight:600;}");
            out.println(".download:hover{background:#1d4ed8;}");

            out.println(".fab-container{position:fixed;bottom:25px;right:25px;z-index:9999;}");
            out.println(".fab-main{width:60px;height:60px;border-radius:50%;background:#2563eb;color:white;" +
                "display:flex;align-items:center;justify-content:center;font-size:26px;cursor:pointer;" +
                "box-shadow:0 10px 25px rgba(0,0,0,0.3);transition:0.3s;}");
            out.println(".fab-container.active .fab-main{transform:rotate(45deg) scale(1.05);background:#1d4ed8;}");
            out.println(".fab-actions{position:absolute;bottom:76px;right:0;display:flex;flex-direction:column;" +
                "gap:10px;opacity:0;pointer-events:none;transition:0.3s;}");
            out.println(".fab-container.active .fab-actions{opacity:1;pointer-events:auto;}");
            out.println(".fab-item{position:relative;background:white;border:1px solid #e2e8f0;padding:10px 14px;" +
                "border-radius:12px;text-decoration:none;color:#0f172a;font-size:13px;font-weight:600;" +
                "display:flex;align-items:center;gap:10px;box-shadow:0 6px 15px rgba(0,0,0,0.12);" +
                "transform:translateY(10px);opacity:0;transition:0.3s;white-space:nowrap;}");
            out.println(".fab-container.active .fab-item{transform:translateY(0);opacity:1;}");
            out.println(".fab-container.active .fab-item:nth-child(1){transition-delay:0.05s;}");
            out.println(".fab-container.active .fab-item:nth-child(2){transition-delay:0.10s;}");
            out.println(".fab-container.active .fab-item:nth-child(3){transition-delay:0.15s;}");
            out.println(".fab-item:hover{background:#f1f5f9;transform:scale(1.04);}");
            out.println(".fab-item::after{content:attr(data-tooltip);position:absolute;right:110%;background:#0f172a;" +
                "color:white;font-size:11px;padding:6px 10px;border-radius:8px;white-space:nowrap;" +
                "opacity:0;transform:translateX(5px);transition:0.2s;pointer-events:none;}");
            out.println(".fab-item:hover::after{opacity:1;transform:translateX(0);}");

            out.println("</style></head><body>");

            out.println("<script>");
            out.println("function toggleFAB(){document.getElementById('fab').classList.toggle('active');}");
            out.println("function handleExportClick(){document.getElementById('fab').classList.toggle('active');}");
            out.println("</script>");

           
            out.println("<div class='topbar'>");
            out.println("<div class='logo'><div class='logo-icon'>SE</div>Sports<span style='color:#93c5fd;'>Ecosystem</span></div>");
            out.println("<div class='top-actions'>");
            out.println("<a href='CoachApproveServlet?id=" + coachId + "' class='tbtn tbtn-approve'>&#10003; Approve Coach</a>");
            out.println("<a href='CoachRejectServlet?id="  + coachId + "' class='tbtn tbtn-reject'>&#10005; Reject Coach</a>");
            out.println("<button class='tbtn tbtn-export' onclick='handleExportClick()'>Export &#11015;</button>");
            out.println("</div></div>");

           
            out.println("<div class='wrapper'>");

            
            out.println("<div class='sidebar'>");
           out.println("<img src='" + safe(coach.getPhotoPath()) + "' class='profile-pic' " +
    "onerror=\"this.onerror=null;this.src='assets/default-avatar.png'\">");
            out.println("<div class='player-name'>" + safe(coach.getFullName()) + "</div>");
            out.println("<div class='player-role'>Registered Coach</div>");

            String coachStatus = coach.getStatus() != null ? coach.getStatus().toUpperCase() : "PENDING";
            String badgeClass;
            switch (coachStatus) {
                case "APPROVED": badgeClass = "badge-approved"; break;
                case "REJECTED": badgeClass = "badge-rejected"; break;
                default:         badgeClass = "badge-pending";  break;
            }
            out.println("<div class='" + badgeClass + "'>&#9679; " + safe(coach.getStatus()) + "</div>");

            out.println("<div class='quick-info'>");
            String[][] quickItems = {
                {"Coach ID",       String.valueOf(coach.getCoachId())},
                {"Specialization", safe(coach.getSpecialization())},
                {"Coaching Level", safe(coach.getCoachingLevel())},
                {"Experience",     coach.getExperience() + " Years"},
                {"Availability",   safe(coach.getAvailability())}
            };
            for (String[] item : quickItems) {
                out.println("<div class='quick-box'>" +
                    "<div class='quick-title'>" + item[0] + "</div>" +
                    "<div class='quick-value'>" + item[1] + "</div>" +
                    "</div>");
            }
            out.println("</div></div>"); // end sidebar

           
            out.println("<div class='main-content'>");

            
            printCard(out, "Personal Information", new String[][]{
                {"Full Name",     safe(coach.getFullName())},
                {"Date of Birth", safe(coach.getDob())},
                {"Gender",        safe(coach.getGender())},
                {"Blood Group",   safe(coach.getBloodGroup())},
                {"Mobile",        safe(coach.getMobile())},
                {"Email",         safe(coach.getEmail())},
                {"Registered On", safe(coach.getCreatedAt())}
            });

            
            printCard(out, "Professional Profile", new String[][]{
                {"Specialization", safe(coach.getSpecialization())},
                {"Coaching Level", safe(coach.getCoachingLevel())},
                {"Experience",     coach.getExperience() + " Years"},
                {"Qualification",  safe(coach.getQualification())},
                {"Club / Academy", safe(coach.getClubName())},
                {"Availability",   safe(coach.getAvailability())}
            });

            
            out.println("<div class='card'>");
            out.println("<div class='card-title'>Achievements &amp; Highlights</div>");
            out.println("<div class='full-block'>" +
                "<div class='info-label'>Achievements</div>" +
                "<div class='info-value' style='line-height:1.6;word-break:break-word;'>" +
                safe(coach.getAchievements()) + "</div></div>");
            out.println("</div>");

           
            out.println("<div class='card'>");
            out.println("<div class='card-title'>Address Details</div>");
            out.println("<div class='info-grid'>");
            for (String[] f : new String[][]{
                {"State",    safe(coach.getState())},
                {"District", safe(coach.getDistrict())},
                {"City",     safe(coach.getCity())},
                {"PIN Code", safe(coach.getPinCode())}
            }) {
                out.println("<div class='info-item'>" +
                    "<div class='info-label'>" + f[0] + "</div>" +
                    "<div class='info-value'>" + f[1] + "</div></div>");
            }
            out.println("</div>");
            out.println("<div class='full-block'>" +
                "<div class='info-label'>Full Address</div>" +
                "<div class='info-value'>" + safe(coach.getFullAddress()) + "</div></div>");
            out.println("</div>");

            
            

           
            out.println("<div class='card'>");
            out.println("<div class='card-title'>Uploaded Documents</div>");
            out.println("<div class='docs'>");
            out.println("<div class='doc-card'>&#128247; Coach Photo " +
                "<a href='" + safe(coach.getPhotoPath()) + "' class='download' target='_blank'>View</a></div>");
            out.println("<div class='doc-card'>&#128220; Certificate " +
                "<a href='" + safe(coach.getCertificatePath()) + "' class='download' target='_blank'>Download</a></div>");
            out.println("<div class='doc-card'>&#128100; ID Proof " +
                "<a href='" + safe(coach.getIdProofPath()) + "' class='download' target='_blank'>Download</a></div>");
            out.println("<div class='doc-card'>&#127891; Qualification Doc " +
                "<a href='" + safe(coach.getQualDocPath()) + "' class='download' target='_blank'>Download</a></div>");
            out.println("</div></div>");

            out.println("</div></div>"); // end main-content + wrapper

            
            out.println("<div class='fab-container' id='fab'>");
            out.println("<div class='fab-actions'>");
            out.println("<a class='fab-item' data-tooltip='Download PDF' href='CoachPdfServlet?id=" + coachId + "'>&#128196; <span>Download PDF</span></a>");
            out.println("<a class='fab-item' data-tooltip='Export Excel' href='ExportCoachExcelServlet'>&#128202; <span>Excel</span></a>");
            out.println("<a class='fab-item' data-tooltip='Go Back'      href='AdminDashboardServlet'>&#8592; <span>Back</span></a>");
            out.println("</div>");
            out.println("<div class='fab-main' onclick='toggleFAB()'>+</div>");
            out.println("</div>");

            out.println("</body></html>");

            
            out.flush();
            out.close();

        } catch (NumberFormatException e) {
            
            out.println("<!DOCTYPE html><html><body><h2>Invalid coach ID.</h2></body></html>");
            out.flush();
            out.close();
        } catch (Exception e) {
           
            out.println("<br><b>Server Error:</b> " + e.getMessage());
            e.printStackTrace(out);
            out.flush();
            out.close();
        }
    }

    private void printCard(PrintWriter out, String title, String[][] fields) {
        out.println("<div class='card'>");
        out.println("<div class='card-title'>" + title + "</div>");
        out.println("<div class='info-grid'>");
        for (String[] f : fields) {
            out.println("<div class='info-item'>" +
                "<div class='info-label'>" + f[0] + "</div>" +
                "<div class='info-value'>" + f[1] + "</div>" +
                "</div>");
        }
        out.println("</div></div>");
    }

    private String safe(String val) {
        return (val != null && !val.trim().isEmpty()) ? val : "&#8212;";
    }
}