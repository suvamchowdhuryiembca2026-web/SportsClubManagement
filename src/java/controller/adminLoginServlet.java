package controller;

import DAO.adminDAO;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
            response.sendRedirect("AdminDashboardServlet");
           
        }

        else
            out.println("<h2 style color:red>Admin Not Found");
//        {
//            response.sendRedirect
//            (
//                "adminLogin.html?status=invalid"
//            );
//        }
    }
}