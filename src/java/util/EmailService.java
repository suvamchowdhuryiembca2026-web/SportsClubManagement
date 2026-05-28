package util;

import DAO.EmailLogDAO;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class EmailService {

    private static final String FROM_EMAIL = "suvamchowdhury490@gmail.com";
   private static final String PASSWORD = System.getenv("EMAIL_PASSWORD");   
    private static final String ORG_NAME = "Sports Ecosystem";
    // ================= CORE EMAIL =================
    private static void sendEmail(String toEmail, String subject, String htmlBody) 
    {

        try {
            Properties props = new Properties();

            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
            props.put("mail.debug", "true");

            Session session = Session.getInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(FROM_EMAIL, PASSWORD);
                }
            });

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(FROM_EMAIL, ORG_NAME + " Notifications"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject(subject);
            message.setContent(htmlBody, "text/html; charset=utf-8");

            Transport.send(message);
            // After Transport.send(message); — add this:
            EmailLogDAO.insertLog(toEmail, subject, "SENT");
            System.out.println("FROM EMAIL = " + FROM_EMAIL);
            System.out.println("PASSWORD = " + PASSWORD);
            System.out.println("TO EMAIL = " + toEmail);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ================= PENDING MAIL =================
    public static void sendPendingMail(String toEmail, String name) {

        String html =
            "<div style='font-family:Arial;background:#f3f6ff;padding:20px;'>" +
            "<div style='max-width:600px;margin:auto;background:white;padding:30px;border-radius:10px;'>" +

            "<h2 style='color:#2563eb;'>Hello " + name + ",</h2>" +

            "<p>Your athlete registration has been successfully received in <b>Sports Ecosystem</b>.</p>" +

            "<div style='background:#fff7d6;padding:15px;border-radius:8px;margin:20px 0;'>" +
            "<b>Status:</b> Pending Review<br>" +
            "Our admin team is currently verifying your profile and documents." +
            "</div>" +

            "<p>You will receive an update once your profile is approved or rejected.</p>" +

            "</div></div>";

        sendEmail(toEmail, "Registration Received - Sports Ecosystem", html);
    }

    // ================= APPROVAL MAIL =================
    public static void sendApprovalMail(String toEmail, String name) {

        String html =
            "<div style='font-family:Arial;background:#f0fff4;padding:20px;'>" +
            "<div style='max-width:600px;margin:auto;background:white;padding:30px;border-radius:10px;border-left:6px solid #22c55e;'>" +

            "<h2 style='color:#16a34a;'>Congratulations " + name + " 🎉</h2>" +

            "<p>Your athlete profile has been <b>APPROVED</b> by the Sports Ecosystem admin team.</p>" +

            "<div style='background:#dcfce7;padding:15px;border-radius:8px;margin:20px 0;'>" +
            "You can now access your dashboard, make payments, and register for events." +
            "</div>" +

            "<a href='http://localhost:8080/AthleteDashboardServlet' " +
            "style='display:inline-block;padding:12px 18px;background:#16a34a;color:white;text-decoration:none;border-radius:6px;'>" +
            "Go to Dashboard</a>" +

            "</div></div>";

        sendEmail(toEmail, "Profile Approved - Sports Ecosystem", html);
    }

    // ================= REJECTION MAIL =================
    public static void sendRejectionMail(String toEmail, String name, String reason) {

        String html =
            "<div style='font-family:Arial;background:#fff1f1;padding:20px;'>" +
            "<div style='max-width:600px;margin:auto;background:white;padding:30px;border-radius:10px;border-left:6px solid #ef4444;'>" +

            "<h2 style='color:#dc2626;'>Hello " + name + "</h2>" +

            "<p>We regret to inform you that your athlete registration was not approved.</p>" +

            "<div style='background:#fee2e2;padding:12px;border-radius:6px;margin:15px 0;'>" +
            "<b>Reason:</b> " + reason +
            "</div>" +

            "<p>You may update your details and reapply later.</p>" +

            "</div></div>";

        sendEmail(toEmail, "Registration Update - Sports Ecosystem", html);
    }
    // ================================================================
//  ADD THESE 3 METHODS TO YOUR EXISTING EmailService.java
//  Place them after sendRejectionMail()
// ================================================================


// ================= COACH PENDING =================
public static void sendCoachPendingMail(String toEmail, String name, String sport) {

    String html =
        "<div style='font-family:Arial;background:#f3f6ff;padding:20px;'>" +
        "<div style='max-width:600px;margin:auto;background:white;padding:30px;" +
        "border-radius:10px;border-left:6px solid #2563eb;'>" +

        "<h2 style='color:#2563eb;'>Hello Coach " + name + ",</h2>" +

        "<p>Your coach registration for <b>Sports Ecosystem</b> has been " +
        "successfully received.</p>" +

        "<div style='background:#eff6ff;padding:15px;border-radius:8px;margin:20px 0;'>" +
        "<b>Sport:</b> " + sport + "<br>" +
        "<b>Status:</b> Pending Review<br><br>" +
        "Our admin team is currently verifying your profile, certifications, " +
        "and documents." +
        "</div>" +

        "<p>You will be notified via email once your profile is reviewed. " +
        "This typically takes 3-5 business days.</p>" +

        "<p style='color:#64748b;font-size:13px;margin-top:20px;'>" +
        "If you have any queries, contact: support@sportsecosystem.com</p>" +

        "</div></div>";

    sendEmail(toEmail, "Coach Registration Received - Sports Ecosystem", html);
}


// ================= COACH APPROVAL =================
public static void sendCoachApprovalMail(String toEmail, String name, String sport) {

    String html =
        "<div style='font-family:Arial;background:#f0fff4;padding:20px;'>" +
        "<div style='max-width:600px;margin:auto;background:white;padding:30px;" +
        "border-radius:10px;border-left:6px solid #22c55e;'>" +

        "<h2 style='color:#16a34a;'>Congratulations Coach " + name + " &#127881;</h2>" +

        "<p>Your coach profile has been <b>APPROVED</b> by the " +
        "Sports Ecosystem admin team.</p>" +

        "<div style='background:#dcfce7;padding:15px;border-radius:8px;margin:20px 0;'>" +
        "<b>Specialization:</b> " + sport + "<br>" +
        "<b>Status:</b> Approved &#10003;<br><br>" +
        "You can now log in to your coach dashboard, view assigned athletes, " +
        "and manage training sessions." +
        "</div>" +

        "<a href='http://localhost:8085/Sportclub_Management_System/UserLogin.html' " +
        "style='display:inline-block;padding:12px 18px;background:#16a34a;" +
        "color:white;text-decoration:none;border-radius:6px;font-weight:bold;'>" +
        "Go to Dashboard</a>" +

        "<p style='color:#64748b;font-size:13px;margin-top:20px;'>" +
        "Sports Ecosystem Team</p>" +

        "</div></div>";

    sendEmail(toEmail, "Coach Profile Approved - Sports Ecosystem", html);
}


// ================= COACH REJECTION =================
public static void sendCoachRejectionMail(String toEmail, String name, String reason) {

    String html =
        "<div style='font-family:Arial;background:#fff1f1;padding:20px;'>" +
        "<div style='max-width:600px;margin:auto;background:white;padding:30px;" +
        "border-radius:10px;border-left:6px solid #ef4444;'>" +

        "<h2 style='color:#dc2626;'>Hello Coach " + name + ",</h2>" +

        "<p>We regret to inform you that your coach registration application " +
        "has not been approved at this time.</p>" +

        "<div style='background:#fee2e2;padding:12px;border-radius:6px;margin:15px 0;'>" +
        "<b>Reason:</b> " + reason +
        "</div>" +

        "<p>You may review the reason above, update your certifications or " +
        "documents, and reapply after 30 days.</p>" +

        "<p style='color:#64748b;font-size:13px;margin-top:20px;'>" +
        "For further assistance, contact: support@sportsecosystem.com</p>" +

        "</div></div>";

    sendEmail(toEmail, "Coach Application Update - Sports Ecosystem", html);
}
}