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
@WebServlet("/editurl")
public class EditServlet extends HttpServlet {
	private static final String query = "update book_data set book_name=?,book_edition=?,book_price=? where id=?";
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        //get PrintWriter
        PrintWriter out = res.getWriter();
        //set content type
        res.setContentType("text/html");
        //get the id of record
        int id = Integer.parseInt(req.getParameter("id"));  
        //get edit data that we want to edit
        String book_name=req.getParameter("book_name");
        String book_e=req.getParameter("book_edition");
        String priceStr=req.getParameter("book_price");
        
        int book_edition;
        float book_price;
        try {
        	book_edition=Integer.parseInt(book_e);	
        }catch(NumberFormatException e) {
        	out.println("<h2>Invalid BookEdition</h2>");
        	return;
        }
        
        try {
         book_price = Float.parseFloat(priceStr);
        }catch(NumberFormatException e) {
            out.println("<h2>Invalid BookPrice</h2>");
            return;
        }
        //LOAD jdbc driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException cnf) {
            cnf.printStackTrace();
        }
        //generate the connection
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3307/book", "root", "root"); 
        		PreparedStatement ps = con.prepareStatement(query);) {
         ps.setString(1, book_name);
         ps.setInt(2, book_edition);
         ps.setFloat(3, book_price);
         ps.setInt(4, id);
         int count=ps.executeUpdate();
         if(count>0) {
        	 out.println("<h2>Record Edited Succesfully</h2>");
         }else {
        	 out.println("<h2>Record Failed to Edit</h2>");
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



