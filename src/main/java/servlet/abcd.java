package servlet;

import java.util.*;
import java.sql.*;
import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;

public class abcd extends HttpServlet {


//	  public boolean userexistancecheck(Connection conn, String uname, PrintWriter out) throws SQLException {
//	        String query = "select * from usersdata";
//	        Statement st = conn.createStatement();
//	        ResultSet rs = st.executeQuery(query);
//
//	        while (rs.next()) {
//	            if (rs.getString(1).equals(uname)) {
//	                return true;
//	            }
//	        }
//	        return false;
//	    }

    public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        String uname = req.getParameter("uname");
        int pid = Integer.parseInt(req.getParameter("pid"));
        String pname = req.getParameter("pname");
        int quan = Integer.parseInt(req.getParameter("quan"));
        Double cost = Double.parseDouble(req.getParameter("cost"));
        Double total_cost = quan * cost;

        PrintWriter out = res.getWriter();
        //        out.println("Product id : " + pid);
        //        out.println("Product name : " + pname);
        //        out.println("Quantity : " + quan);
        //        out.println("Cost : " + cost);
        //        out.println("Total Cost : " + total_cost);

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

                
//                System.out.println(servlet.check_cred.hehe);	
                
                if (uname.equals(servlet.check_cred.hehe)) {
                    Boolean c = false;
                    String query2 = "insert into " + uname + " values(" + "\"" + uname + "\"" + ",\"" + pname + "\"," + quan + "," + cost + "," + total_cost + ");";

                    PreparedStatement st2 = conn.prepareStatement(query2);
                    //    out.println(query2);
                    try {
                        st2.executeUpdate(query2);
                        out.println("Inserted into ur database Successfully");
                        c = true;
                        st2.close();
                    } catch (Exception e) {
                        out.println(e);
                    } finally {
                        st2.close();
                    }

                    if (c) {

                        String query = "insert into items_ordered values(" + "\"" + uname + "\"" + ",\"" + pname + "\"," + quan + "," + cost + "," + total_cost + ");";
                        //                 	out.println(query);
                        PreparedStatement st = conn.prepareStatement(query);

                        try {
                            st.executeUpdate(query);
                            out.println("Inserted into items ordered Successfully");
                        } catch (Exception e) {
                            out.println(e.getMessage());
                        } finally {
                            st.close();
                        }
                    }

                } else {
                    out.println("Invalid username");
                }

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