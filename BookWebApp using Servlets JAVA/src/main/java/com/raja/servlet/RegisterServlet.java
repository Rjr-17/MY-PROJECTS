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

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private static final String query = "insert into book_data(book_name,book_edition,book_price) values (?,?,?)";
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        //get PrintWriter
        PrintWriter out = res.getWriter();
        //set content type
        res.setContentType("text/html");
        //GET THE book info
        String book_name = req.getParameter("bookName");
        String book_e = req.getParameter("bookEdition");
        String priceStr = req.getParameter("bookPrice");
        
        if ( book_name == null || book_name.trim().isEmpty()) {
        	out.println("<h2>BookName fields is required</h2>");
        	return;
        }else if(book_e == null || book_e.trim().isEmpty()){
        	out.println("<h2>BookEdition fields is required</h2>");
        	return;
        }else if(priceStr == null || priceStr.trim().isEmpty()){
            out.println("<h2>BookPrice fields is required</h2>");
            return;
        }
        int book_edition;
        float book_price;
        try {
        	book_edition=Integer.parseInt(book_e);	
        }catch(NumberFormatException e) {
        	out.println("<h2>Invalid Book Edition</h2>");
        	return;
        }
        
        try {
         book_price = Float.parseFloat(priceStr);
        }catch(NumberFormatException e) {
            out.println("<h2>Invalid Book Price</h2>");
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
            int count = ps.executeUpdate(); //returns integer value
            if (count > 0) {
                out.println("<h2>Record Is Registered Sucessfully</h2>");
            } else {
                out.println("<h2>Record not Registered Sucessfully");
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
