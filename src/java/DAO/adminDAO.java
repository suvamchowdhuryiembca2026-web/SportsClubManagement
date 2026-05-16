package DAO;

import db.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class adminDAO
{

    public boolean checkLogin
    (
        String username,
        String password
    )
    {

        boolean valid = false;

        try
        {
            Connection con =
                    DbConnection.getConnection();

            String sql =
                    "SELECT * FROM ADMIN1 " +
                    "WHERE USERMAIL = ? " +
                    "AND PASSWORD =?";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs =
                    ps.executeQuery();

            if(rs.next())
            {
                valid = true;
            }

        }

        catch(Exception e)
        {
            e.printStackTrace();
        }

        return valid;
    }
}