package servlet;

import java.util.*;

import javax.imageio.plugins.tiff.ExifGPSTagSet;

import org.w3c.dom.UserDataHandler;

import java.sql.*;
import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;

public class check_cred extends HttpServlet {

    public static String hehe;

    public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String uname = req.getParameter("uname");
        String user_pass = req.getParameter("pass");
        PrintWriter out = res.getWriter();
        res.setContentType("text/html");
        hehe = uname;
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


                String query = "select * from usersdata";
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(query);

                boolean checker = false;
                while (rs.next()) {
                    if (rs.getString(1).equals(uname)) {
                        if (rs.getString(2).equals(user_pass)) {
                            //	                			out.println("hehe");
                            checker = true;
                            break;
                        } else {
                            out.print("Inavlid password");
                        }
                    } else {

                        //	                		out.print("Inavlid username");
                    }
                }

                if (checker) {
                    int counter = 0;
                    String query2 = "select * from " + uname + ";";
                    ResultSet rs2 = st.executeQuery(query2);
                    out.println("<h3 style=\"text-align:center\"> Items Ordered </h3>" + "<br> <br>");
                    while (rs2.next()) {
                        counter += 1;
                        out.println(rs2.getString("pname") + "&nbsp;&nbsp;&nbsp" + rs2.getString("quan") + "&nbsp;&nbsp;&nbsp" + rs2.getString("cost") + "&nbsp;&nbsp;&nbsp" + rs2.getString("total") + "<br> <br>");
                    }
                    if (counter == 0) {
                        out.println("<h1 style=\"text-align:center\"> No orders Yet</h1>");
                    }

                    out.println(" <br> <br> <a style=\"text-align:center\" href=\"http://localhost:8080/food_ordering_system/index.html\"> Click here to order </a> ");

                } else {
                    out.println("<h1> User doesnt exit s</h1>");
                    res.sendRedirect("http://localhost:8080/food_ordering_system/");
                }


                // closing connection & statements
                st.close();
                conn.close();

            } catch (SQLException e) {
                System.out.println("Connection failed");
                e.printStackTrace();
            }

        } catch (ClassNotFoundException e) {

            System.out.println("Driver loading failed !!");
            e.printStackTrace();
        }
    }
}