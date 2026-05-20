package controller;

import DAO.athleteDAO;

import model.AthleteCompleteProfile;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;

import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import javax.servlet.ServletException;

import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ViewPdfServlet extends HttpServlet
{
    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response
    )
    throws ServletException, IOException
    {

        response.setContentType("application/pdf");

        response.setHeader(
                "Content-Disposition",
                "inline; filename=athlete.pdf"
        );

        try
        {
            long athleteId =
            Long.parseLong(
            request.getParameter("id"));

            athleteDAO dao =
            new athleteDAO();

            AthleteCompleteProfile profile =
            dao.getAthleteById(athleteId);

            PdfWriter writer =
            new PdfWriter(
            response.getOutputStream());

            PdfDocument pdf =
            new PdfDocument(writer);

            Document document =
            new Document(pdf);

            // Heading
            Paragraph heading =
            new Paragraph("ATHLETE PROFILE").setBold().setFontSize(22);

            document.add(heading);

            document.add(
            new Paragraph(" "));

            if(profile.getAthlete() != null)
            {

                // ATHLETE
                document.add(
                new Paragraph(
                "Athlete ID : " +
                profile.getAthlete()
                .getAthleteId()));

                document.add(
                new Paragraph(
                "Name : " +
                profile.getAthlete()
                .getFullName()));

                document.add(
                new Paragraph(
                "Gender : " +
                profile.getAthlete()
                .getGender()));

                document.add(
                new Paragraph(
                "Email : " +
                profile.getAthlete()
                .getEmail()));

                // GUARDIAN
                document.add(
                new Paragraph(
                "Guardian : " +
                profile.getGuardian()
                .getGuardianName()));

                // ADDRESS
                document.add(
                new Paragraph(
                "City : " +
                profile.getAddress()
                .getCity()));

                // SPORTS
                document.add(
                new Paragraph(
                "Sport : " +
                profile.getSportsProfile()
                .getSportType()));

                document.add(
                new Paragraph(
                "Club : " +
                profile.getSportsProfile()
                .getClubName()));
            }

            else
            {
                document.add(
                new Paragraph(
                "No Athlete Found"));
            }

            document.close();
        }

        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}