package controller;
import DAO.coachDAO;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.Period;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import model.Coach;
import util.EmailService;
import util.IdGenerator;

@MultipartConfig
public class CoachRegisterServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try {
            // GENERATE ID
            long coachId = IdGenerator.generateId();

            // UPLOAD DIRECTORY
            String uploadDir = getServletContext().getRealPath("") + "coach_docs";

            // PHOTO — same compression handled on frontend
            Part photoPart = request.getPart("photo");
            String photoName = coachId + "_photo.jpg";
            String photoPath = uploadFile(photoPart, uploadDir, photoName);

            // COACHING CERTIFICATE
            Part certPart = request.getPart("certificate");
            String certName = coachId + "_certificate.pdf";
            String certPath = uploadFile(certPart, uploadDir, certName);

            // ID PROOF — keep original extension
            Part idPart = request.getPart("idProof");
            String originalIdName = idPart.getSubmittedFileName();
            String idExt = originalIdName.substring(originalIdName.lastIndexOf("."));
            String idName = coachId + "_id" + idExt;
            String idPath = uploadFile(idPart, uploadDir, idName);

            // QUALIFICATION DOCUMENT
            Part qualPart = request.getPart("qualDoc");
            String qualName = coachId + "_qual.pdf";
            String qualPath = uploadFile(qualPart, uploadDir, qualName);

            // CREATED AT
            String createdAt = LocalDate.now().toString();

            // BUILD COACH OBJECT
            Coach coach = new Coach();
            coach.setCoachId(coachId);
            coach.setFullName(request.getParameter("fullName"));
            coach.setEmail(request.getParameter("email"));
            coach.setMobile(request.getParameter("mobile"));
            coach.setGender(request.getParameter("gender"));
            coach.setDob(request.getParameter("dob"));
            coach.setBloodGroup(request.getParameter("bloodGroup"));
            coach.setExperience(Integer.parseInt(request.getParameter("experience")));
            coach.setSpecialization(request.getParameter("specialization"));
            coach.setCoachingLevel(request.getParameter("coachingLevel"));
            coach.setQualification(request.getParameter("qualification"));
            coach.setClubName(request.getParameter("clubName"));
            coach.setAvailability(request.getParameter("availability"));
            coach.setAchievements(request.getParameter("achievements"));
            coach.setState(request.getParameter("state"));
            coach.setDistrict(request.getParameter("district"));
            coach.setCity(request.getParameter("city"));
            coach.setPinCode(request.getParameter("pincode"));
            coach.setFullAddress(request.getParameter("fullAddress"));
            coach.setPhotoPath(photoPath);
            coach.setCertificatePath(certPath);
            coach.setIdProofPath(idPath);
            coach.setQualDocPath(qualPath);
            coach.setCreatedAt(createdAt);
            // STATUS set to PENDING inside DAO

            // PERSIST
            coachDAO dao = new coachDAO();
            boolean ok = dao.registerCoach(coach);

            System.out.println("COACH ID      = " + coachId);
            System.out.println("INSERT STATUS = " + ok);

            if (ok) {
                EmailService.sendCoachPendingMail(
                    coach.getEmail(),
                    coach.getFullName(),
                    coach.getSpecialization()
                );
                response.sendRedirect("coachRegistration.html?status=success");
            } else {
                response.sendRedirect("coachRegistration.html?status=fail");
            }

        } catch (Exception ex) {
            out.println("<h2 style='color:red;'>Coach Registration Failed</h2>");
            out.println("<pre>");
            ex.printStackTrace(out);
            out.println("</pre>");
        }
    }

    private String uploadFile(Part part, String uploadDir, String fileName)
            throws IOException {

        File dir = new File(uploadDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        File file = new File(uploadDir + File.separator + fileName);

        try (
            InputStream is  = part.getInputStream();
            FileOutputStream fos = new FileOutputStream(file)
        ) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = is.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }
        }

        return "coach_docs/" + fileName;
    }
}