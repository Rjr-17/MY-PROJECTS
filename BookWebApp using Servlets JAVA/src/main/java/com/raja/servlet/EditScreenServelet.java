package com.raja.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/editScreen")
public class EditScreenServelet extends HttpServlet {
	private static final String query = "select book_name,book_edition,book_price from book_data where id=?";
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        //get PrintWriter
        PrintWriter out = res.getWriter();
        //set content type
        res.setContentType("text/html");
        //get the id of record
        int id = Integer.parseInt(req.getParameter("id"));        
        //LOAD jdbc driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException cnf) {
            cnf.printStackTrace();
        }
        //generate the connection
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3307/book", "root", "root"); 
        		PreparedStatement ps = con.prepareStatement(query);) {
          ps.setInt(1, id);
          ResultSet rs = ps.executeQuery();
          rs.next();
          out.println("<form action='editurl?id="+id+"' method='post'>");
          out.println("<table align='center'>");
          out.println("<tr>");
          out.println("<td>Book Name</td>");
          out.println("<td><input type='text' name='book_name' value='"+rs.getString(1)+"'</td>");
          out.println("</tr>");
          out.println("<tr>");
          out.println("<td>Book Edition</td>");
          out.println("<td><input type='text' name='book_edition' value='"+rs.getInt(2)+"'</td>");
          out.println("</tr>");
          out.println("<tr>");
          out.println("<td>Book Price</td>");
          out.println("<td><input type='text' name='book_price' value='"+rs.getFloat(3)+"'</td>");
          out.println("</tr>");
          out.println("<tr>");
          out.println("<td> <input type='submit' value='edit'></td>");
          out.println("<td> <input type='reset' value='cancel'></td>");
          out.println("</tr>");
          out.println("</table>");
          out.println("</form>");
        } catch (SQLException se) {
            se.printStackTrace();
            out.println("<h2>Database connection failed</h2>");
        } catch (Exception e) {
            e.printStackTrace();
            out.println("<h2>Something went wrong</h2>");
        }
        out.println("<a href='home.html'>Home Page</a>");
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doGet(req, res);
    }
}




