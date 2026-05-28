package controller;

import DAO.adminDAO;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class adminLoginServlet extends HttpServlet
{
    @Override

    protected void doPost
    (
        HttpServletRequest request,
        HttpServletResponse response
    )

    throws ServletException, IOException
    {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String username =
                request.getParameter("username");

        String password =
                request.getParameter("password");

        adminDAO dao = new adminDAO();

        boolean valid =
                dao.checkLogin(username, password);

        if(valid)
{
    HttpSession oldSession = request.getSession(false);
if (oldSession != null) {
    oldSession.invalidate();
}
HttpSession session = request.getSession(true);

session.setAttribute("role", "ADMIN");

    response.sendRedirect("AdminDashboardServlet");
}

        else
            
            response.sendRedirect
            (
                "adminLogin.html?status=invalid"
            );
        }
    
}