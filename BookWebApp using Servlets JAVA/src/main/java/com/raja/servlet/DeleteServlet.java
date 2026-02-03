package com.raja.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/deleteurl")
public class DeleteServlet extends HttpServlet {
	private static final String query = "delete from book_data where id=?";
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
         int count=ps.executeUpdate();
         if(count>0) {
        	 out.println("<h2>Record Deleted Succesfully</h2>");
         }else {
        	 out.println("<h2>Record Failed to Delete</h2>");
         }
        } catch (SQLException se) {
            se.printStackTrace();
            out.println("<h2>Database connection failed</h2>");
        } catch (Exception e) {
            e.printStackTrace();
            out.println("<h2>Something went wrong</h2>");
        }
        out.println("<a href='home.html'>Home Page</a>");
        out.println("<br>");
        out.println("<a href='BookList'>Book List</a>");
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doGet(req, res);
    }
}




