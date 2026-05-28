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

public class ViewAthleteServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try {
            long athleteId = Long.parseLong(request.getParameter("id"));

            athleteDAO dao = new athleteDAO();
            AthleteCompleteProfile profile = dao.getAthleteById(athleteId);

            if (profile == null || profile.getAthlete() == null) {
                out.println("<h2>No Athlete Found</h2>");
                return;
            }

            Athlete       athlete  = profile.getAthlete();
            Guardian      guardian = profile.getGuardian();
            Address       address  = profile.getAddress();
            SportsProfile sports   = profile.getSportsProfile();
            Documents     docs     = profile.getDocuments();

            out.println("<!DOCTYPE html><html><head><title>Athlete Profile</title>");
            out.println("<link href='https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap' rel='stylesheet'>");
            out.println("<style>");
            out.println(".tbtn-reject{background:#ef4444;color:white;}");
            out.println("*{margin:0;padding:0;box-sizing:border-box;font-family:'Inter',sans-serif;}");
            // GRADIENT BACKGROUND
            out.println("body{min-height:100vh;background:linear-gradient(to right,#01236d,#3861e7);}");

            // TOPBAR — glass
            out.println(".topbar{height:68px;padding:0 36px;display:flex;align-items:center;justify-content:space-between;" +
                "background:rgba(0,0,0,0.25);backdrop-filter:blur(12px);-webkit-backdrop-filter:blur(12px);" +
                "border-bottom:1px solid rgba(255,255,255,0.12);position:sticky;top:0;z-index:999;}");
            out.println(".logo{font-size:22px;font-weight:700;color:white;display:flex;align-items:center;gap:10px;}");
            out.println(".logo-icon{width:36px;height:36px;background:white;border-radius:8px;display:flex;align-items:center;justify-content:center;font-size:13px;font-weight:bold;color:#01236d;}");
            out.println(".top-actions{display:flex;gap:10px;align-items:center;}");
            out.println(".tbtn{padding:9px 18px;border:none;border-radius:8px;font-size:13px;font-weight:600;cursor:pointer;transition:opacity 0.2s;text-decoration:none;display:inline-flex;align-items:center;gap:6px;}");
            out.println(".tbtn:hover{opacity:0.85;}");
            out.println(".tbtn-approve{background:#22c55e;color:white;}");
            out.println(".tbtn-export{background:rgba(255,255,255,0.15);color:white;border:1px solid rgba(255,255,255,0.3);}");

            // WRAPPER
            out.println(".wrapper{display:grid;grid-template-columns:300px 1fr;gap:24px;padding:28px;}");

            // SIDEBAR — glass (left side stays gradient theme)
            out.println(".sidebar{background:rgba(255,255,255,0.12);backdrop-filter:blur(16px);-webkit-backdrop-filter:blur(16px);" +
                "border:1px solid rgba(255,255,255,0.2);border-radius:24px;padding:28px;height:fit-content;" +
                "box-shadow:0 8px 32px rgba(0,0,0,0.18);}");
            out.println(".profile-pic{width:130px;height:130px;border-radius:50%;object-fit:cover;display:block;margin:auto;border:4px solid rgba(255,255,255,0.5);}");
            out.println(".player-name{text-align:center;margin-top:18px;font-size:22px;font-weight:700;color:white;}");
            out.println(".player-role{text-align:center;margin-top:6px;color:rgba(255,255,255,0.65);font-size:13px;}");
            out.println(".badge-pending{width:fit-content;margin:16px auto 0;background:rgba(251,146,60,0.25);color:#fed7aa;border:1px solid rgba(251,146,60,0.5);padding:8px 18px;border-radius:999px;font-size:12px;font-weight:700;letter-spacing:1px;}");
            out.println(".badge-approved{width:fit-content;margin:16px auto 0;background:rgba(34,197,94,0.25);color:#bbf7d0;border:1px solid rgba(34,197,94,0.5);padding:8px 18px;border-radius:999px;font-size:12px;font-weight:700;letter-spacing:1px;}");
            out.println(".badge-rejected{width:fit-content;margin:16px auto 0;background:rgba(239,68,68,0.25);color:#fecaca;border:1px solid rgba(239,68,68,0.5);padding:8px 18px;border-radius:999px;font-size:12px;font-weight:700;letter-spacing:1px;}");
            out.println(".quick-info{margin-top:24px;display:flex;flex-direction:column;gap:10px;}");
            out.println(".quick-box{padding:14px 16px;border-radius:14px;background:rgba(255,255,255,0.10);border:1px solid rgba(255,255,255,0.15);}");
            out.println(".quick-title{font-size:11px;color:rgba(255,255,255,0.6);margin-bottom:4px;text-transform:uppercase;letter-spacing:0.8px;}");
            out.println(".quick-value{font-size:15px;font-weight:700;color:white;}");

            // MAIN CONTENT — pure white cards (right side)
            out.println(".main-content{display:flex;flex-direction:column;gap:20px;}");
            out.println(".card{background:white;border-radius:22px;border:1px solid #e2e8f0;padding:26px;box-shadow:0 4px 16px rgba(0,0,0,0.08);}");
            out.println(".card-title{font-size:17px;font-weight:700;color:#0f172a;margin-bottom:20px;padding-bottom:12px;border-bottom:1px solid #f1f5f9;}");

            // INFO GRID — white background
            out.println(".info-grid{display:grid;grid-template-columns:repeat(auto-fit,minmax(200px,1fr));gap:14px;}");
            out.println(".info-item{background:#f8fafc;border:1px solid #e2e8f0;padding:14px 16px;border-radius:14px;}");
            out.println(".info-label{font-size:11px;color:#64748b;margin-bottom:6px;text-transform:uppercase;letter-spacing:0.8px;}");
            out.println(".info-value{font-size:15px;font-weight:600;color:#0f172a;}");

            // FULL ADDRESS
            out.println(".full-address{margin-top:14px;background:#f8fafc;border:1px solid #e2e8f0;border-radius:14px;padding:16px;}");

            // DOCS
            out.println(".docs{display:grid;grid-template-columns:repeat(auto-fit,minmax(220px,1fr));gap:14px;}");
            out.println(".doc-card{background:#f8fafc;border:1px solid #e2e8f0;border-radius:14px;padding:16px 20px;display:flex;justify-content:space-between;align-items:center;color:#0f172a;font-size:18px;font-weight:600;}");
            out.println(".download{text-decoration:none;background:#2563eb;color:white;padding:8px 14px;border-radius:8px;font-size:12px;font-weight:600;}");
            out.println(".download:hover{background:#1d4ed8;}");

            // FAB — unchanged
            out.println(".fab-container{position:fixed;bottom:25px;right:25px;z-index:9999;}");
            out.println(".fab-main{width:60px;height:60px;border-radius:50%;background:#2563eb;color:white;display:flex;align-items:center;justify-content:center;font-size:26px;cursor:pointer;box-shadow:0 10px 25px rgba(0,0,0,0.3);transition:0.3s;}");
            out.println(".fab-container.active .fab-main{transform:rotate(45deg) scale(1.05);background:#1d4ed8;}");
            out.println(".fab-actions{position:absolute;bottom:76px;right:0;display:flex;flex-direction:column;gap:10px;opacity:0;pointer-events:none;transition:0.3s;}");
            out.println(".fab-container.active .fab-actions{opacity:1;pointer-events:auto;}");
            out.println(".fab-item{position:relative;background:white;border:1px solid #e2e8f0;padding:10px 14px;border-radius:12px;text-decoration:none;color:#0f172a;font-size:13px;font-weight:600;display:flex;align-items:center;gap:10px;box-shadow:0 6px 15px rgba(0,0,0,0.12);transform:translateY(10px);opacity:0;transition:0.3s;white-space:nowrap;}");
            out.println(".fab-container.active .fab-item{transform:translateY(0);opacity:1;}");
            out.println(".fab-container.active .fab-item:nth-child(1){transition-delay:0.05s;}");
            out.println(".fab-container.active .fab-item:nth-child(2){transition-delay:0.10s;}");
            out.println(".fab-container.active .fab-item:nth-child(3){transition-delay:0.15s;}");
            out.println(".fab-container.active .fab-item:nth-child(4){transition-delay:0.20s;}");
            out.println(".fab-item:hover{background:#f1f5f9;transform:scale(1.04);}");
            out.println(".fab-item::after{content:attr(data-tooltip);position:absolute;right:110%;background:#0f172a;color:white;font-size:11px;padding:6px 10px;border-radius:8px;white-space:nowrap;opacity:0;transform:translateX(5px);transition:0.2s;pointer-events:none;}");
            out.println(".fab-item:hover::after{opacity:1;transform:translateX(0);}");

            out.println("</style></head><body>");

            // JS
            out.println("<script>");
            out.println("function toggleFAB(){document.getElementById('fab').classList.toggle('active');}");
            out.println("function handleExportClick(){document.getElementById('fab').classList.toggle('active');}");
            out.println("</script>");

            // TOPBAR
            out.println("<div class='topbar'>");
            out.println("<div class='logo'><div class='logo-icon'>SE</div>Sports<span style='color:#93c5fd;'>Ecosystem</span></div>");
            out.println("<div class='top-actions'>");
            out.println(
                    "<a href='AthleteApproveServlet?id=" + athleteId + "' class='tbtn tbtn-approve'>" +
                    "✓ Approve Athlete" +
                    "</a>"
                );

                out.println(
                    "<a href='AthleteRejectServlet?id=" + athleteId + "' class='tbtn tbtn-reject'>" +
                    "✕ Reject Athlete" +
                    "</a>"
                );
            out.println("<button class='tbtn tbtn-export' onclick='handleExportClick()'>Export ⬇</button>");
            out.println("</div></div>");

            // WRAPPER
            out.println("<div class='wrapper'>");

            // SIDEBAR — glass left
            out.println("<div class='sidebar'>");
            out.println("<img src='" + docs.getPhotoPath() + "' class='profile-pic'>");
            out.println("<div class='player-name'>" + athlete.getFullName() + "</div>");
            out.println("<div class='player-role'>Registered Athlete</div>");

            String badgeClass;
            switch (athlete.getStatus().toUpperCase()) {
                case "APPROVED": badgeClass = "badge-approved"; break;
                case "REJECTED": badgeClass = "badge-rejected"; break;
                default:         badgeClass = "badge-pending";
            }
            out.println("<div class='" + badgeClass + "'>● " + athlete.getStatus() + "</div>");

            out.println("<div class='quick-info'>");
            String[][] quickItems = {
                {"Athlete ID",     String.valueOf(athlete.getAthleteId())},
                {"Sport Type",     sports.getSportType()},
                {"Training Level", sports.getTrainingLevel()},
                {"Club Name",      sports.getClubName()}
            };
            for (String[] item : quickItems) {
                out.println("<div class='quick-box'>" +
                    "<div class='quick-title'>" + item[0] + "</div>" +
                    "<div class='quick-value'>" + item[1] + "</div>" +
                    "</div>");
            }
            out.println("</div></div>");

            // MAIN CONTENT — pure white right side
            out.println("<div class='main-content'>");

            // ATHLETE INFO
            printCard(out, "Athlete Information", new String[][]{
                {"Full Name",     athlete.getFullName()},
                {"Date of Birth", String.valueOf(athlete.getDob())},
                {"Age",           String.valueOf(athlete.getAge())},
                {"Gender",        athlete.getGender()},
                {"Mobile",        athlete.getMobile()},
                {"Email",         athlete.getEmail()},
                {"Blood Group",   athlete.getBloodGroup()},
                {"Registered On", athlete.getCreatedAt()}
            });

            // GUARDIAN
            printCard(out, "Guardian Details", new String[][]{
                {"Guardian Name",     guardian.getGuardianName()},
                {"Relation",          guardian.getRelation()},
                {"Emergency Contact", guardian.getEmergencyContact()}
            });

            // ADDRESS
            out.println("<div class='card'>");
            out.println("<div class='card-title'>Address Details</div>");
            out.println("<div class='info-grid'>");
            for (String[] f : new String[][]{
                {"State",    address.getState()},
                {"District", address.getDistrict()},
                {"City",     address.getCity()},
                {"PIN Code", address.getPinCode()}
            }) {
                out.println("<div class='info-item'><div class='info-label'>" + f[0] +
                    "</div><div class='info-value'>" + f[1] + "</div></div>");
            }
            out.println("</div>");
            out.println("<div class='full-address'><div class='info-label'>Full Address</div>" +
                "<div class='info-value'>" + address.getFullAddress() + "</div></div>");
            out.println("</div>");

            // SPORTS
            printCard(out, "Sports Profile", new String[][]{
                {"Sport Type",     sports.getSportType()},
                {"Category",       sports.getCategory()},
                {"Training Level", sports.getTrainingLevel()},
                {"Club Name",      sports.getClubName()}
            });

            // DOCUMENTS
            out.println("<div class='card'>");
            out.println("<div class='card-title'>Uploaded Documents</div>");
            out.println("<div class='docs'>");
            out.println("<div class='doc-card'>📷 Athlete Photo <a href='" + docs.getPhotoPath() + "' class='download'>View</a></div>");
            out.println("<div class='doc-card'>📄 Birth Certificate <a href='" + docs.getBirthCertPath() + "' class='download'>Download</a></div>");
            out.println("<div class='doc-card'>🪪 ID Proof <a href='" + docs.getIdProofPath() + "' class='download'>Download</a></div>");
            out.println("</div></div>");

            out.println("</div></div>");

            // FAB
            out.println("<div class='fab-container' id='fab'>");
            out.println("<div class='fab-actions'>");
            out.println("<a class='fab-item' data-tooltip='Download PDF'  href='ViewPdfServlet?id=" + athleteId + "'>📄 <span>Download PDF</span></a>");
            out.println("<a class='fab-item' data-tooltip='Export Excel'  href='ExportExcelServlet'>📊 <span>Excel</span></a>");
            out.println("<a class='fab-item' data-tooltip='Go Back'       href='AdminDashboardServlet'>⬅ <span>Back</span></a>");
            out.println("</div>");
            out.println("<div class='fab-main' onclick='toggleFAB()'>+</div>");
            out.println("</div>");

            out.println("</body></html>");

        } catch (Exception e) {
            e.printStackTrace(out);
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
}