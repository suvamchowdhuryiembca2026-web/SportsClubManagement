package controller;

import DAO.athleteDAO;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import model.Address;
import model.Athlete;
import model.Documents;
import model.Guardian;
import model.SportsProfile;
import util.IdGenerator;

@MultipartConfig
public class AthleteRegisterservlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try {

            Date dt = new Date();
           
            long athleteId = IdGenerator.generateAthleteId();
            long guardianId = IdGenerator.generateGuardianId();
            long addressId = IdGenerator.generateAddressId();
            long sportsId = IdGenerator.generateSportsProfileId();
            long docId = IdGenerator.generateDocumentId();
           String dobStr = request.getParameter("dob");

LocalDate dob = LocalDate.parse(dobStr);


int age = Period.between(dob, LocalDate.now()).getYears();
            
LocalDate now = LocalDate.now();
String createdAt = now.toString();
            String uploadDir = getServletContext().getRealPath("") + "athlete_docs";


            Part photoPart = request.getPart("photo");
            String photoName = photoPart.getSubmittedFileName();
            String photoPath = uploadFile(photoPart, uploadDir, photoName);

            Part birthPart = request.getPart("birthCert");
            String birthName = athleteId + "_birth.pdf";
            String birthPath = uploadFile(birthPart, uploadDir, birthName);

   
            Part idPart = request.getPart("idProof");
            String idName = athleteId + "_id.pdf";
            String idPath = uploadFile(idPart, uploadDir, idName);

            Athlete athlete = new Athlete();

            athlete.setAthleteId(athleteId);
            athlete.setFullName(request.getParameter("fullName"));
            athlete.setDob(java.sql.Date.valueOf(dob)); 
            
            athlete.setAge(age);
            athlete.setGender(request.getParameter("gender"));
            athlete.setMobile(request.getParameter("mobile"));
            athlete.setEmail(request.getParameter("email"));
            athlete.setBloodGroup(request.getParameter("blood"));
            athlete.setCreatedAt(createdAt);
            Guardian g = new Guardian();

            g.setAthleteId(athleteId);
            g.setGuardianId(guardianId);
            g.setGuardianName(request.getParameter("gname"));
            g.setRelation(request.getParameter("relation"));
            g.setMobile(request.getParameter("gmobile"));
            g.setEmergencyContact(request.getParameter("emergency"));

            Address a = new Address();

            a.setAthleteId(athleteId);
            a.setAddressId(addressId);
            a.setState(request.getParameter("state"));
            a.setCity(request.getParameter("city"));
            a.setDistrict(request.getParameter("district"));
            a.setPinCode(request.getParameter("pincode"));
            a.setFullAddress(request.getParameter("fullAddress"));

            
            SportsProfile s = new SportsProfile();

            s.setAthleteId(athleteId);
            s.setProfileId(sportsId);
            s.setSportType(request.getParameter("sport"));
            s.setCategory(request.getParameter("category"));
            s.setClubName(request.getParameter("club"));
            s.setTrainingLevel(request.getParameter("trainingLevel"));
            a.setDistrict(request.getParameter("district"));  
            Documents d = new Documents();

            d.setAthleteId(athleteId);
            d.setDocId(docId);
            d.setPhotoPath(photoPath);
            d.setBirthCertPath(birthPath);
            d.setIdProofPath(idPath);

            athleteDAO dao = new athleteDAO();

            boolean ok = dao.registerAthlete(athlete, g, a, s, d);

           
            if (ok) {
                response.sendRedirect("athleteRegistration.html?status=success");
            } else {
                response.sendRedirect("athleteRegistration.html?status=fail");
            }

            System.out.println("ATHLETE ID = " + athleteId);
            System.out.println("INSERT STATUS = " + ok);

        } catch (Exception ex) {

            out.println("<h2 style='color:red;'>Registration Failed</h2>");
            out.println("<pre>");
            ex.printStackTrace(out);
            out.println("</pre>");
        }
    }

    
    private String uploadFile(Part part, String uploadDir, String fileName) throws IOException {

        
        File dir = new File(uploadDir);
        if (!dir.exists()) {
            dir.mkdir();
        }

     
        File file = new File(uploadDir + "/" + fileName);

        
        FileOutputStream fos = new FileOutputStream(file);
        InputStream is = part.getInputStream();

        byte[] buffer = new byte[1024];
        int bytesRead;

        while ((bytesRead = is.read(buffer)) != -1) {
            fos.write(buffer, 0, bytesRead);
        }

        fos.close();
        is.close();

        
        return "athlete_docs/" + fileName;
    }
}