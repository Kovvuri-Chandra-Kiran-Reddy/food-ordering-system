package servlet;

import java.util.*;
import java.sql.*;
import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;


public class create_account extends HttpServlet {
    public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String uname = req.getParameter("uname");
        String user_pass = req.getParameter("pass");
        PrintWriter out = res.getWriter();
        //		 out.println(table+password);

        try {

            // Class.forName("com.mysql.jdbc.Driver");    // depreciated
            // loading JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver loading succesfull");

            try {
                // connecting database
                String url = "jdbc:mysql://localhost:3306/food_ordering_system";
                String username = "root";
                String password = "kiran";
                Connection conn = DriverManager.getConnection(url, username, password);
                System.out.println("Connected Succesfully");


                String query = "insert into usersdata values(" + "\"" + uname + "\"" + "," + "\"" + user_pass + "\"" + ")";
                Statement st = conn.createStatement();
                st.executeUpdate(query);

                String query2 = "create table " + uname + "(" + "uname varchar(100),pname varchar(100),quan int,cost float,total float);";
                st.executeUpdate(query2);

                res.sendRedirect("http://localhost:8080/food_ordering_system/");

                // closing connection & statements
                st.close();
                conn.close();

            } catch (SQLException e) {
                System.out.println("Connection failed");
                System.out.println(e.getMessage());
            }

        } catch (ClassNotFoundException e) {

            System.out.println("Driver loading failed !!");
            System.out.println(e.getMessage());
        }
    }
}