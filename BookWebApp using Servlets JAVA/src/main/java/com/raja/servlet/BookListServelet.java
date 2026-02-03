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
@WebServlet("/BookList")
public class BookListServelet extends HttpServlet {
	private static final String query = "select * from book_data";
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        //get PrintWriter
        PrintWriter out = res.getWriter();
        //set content type
        res.setContentType("text/html");
        //LOAD jdbc driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException cnf) {
            cnf.printStackTrace();
        }
        //generate the connection
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3307/book", "root", "root"); 
        		PreparedStatement ps = con.prepareStatement(query);) {
          ResultSet rs = ps.executeQuery();
          out.println("<table border='1' align='center'>");
          out.println("<tr>");
          out.println("<th>book_id</th>");
          out.println("<th>book_name</th>");
          out.println("<th>book_edition</th>");
          out.println("<th>book_price</th>");
          out.println("<th>Edit</th>");
          out.println("<th>Delete</th>");
          out.println("</tr>");
          while(rs.next()) {
              out.println("<tr>");
              out.println("<td>"+rs.getInt(1)+"</td>");
              out.println("<td>"+rs.getString(2)+"</td>");
              out.println("<td>"+rs.getInt(3)+"</td>");
              out.println("<td>"+rs.getFloat(4)+"</td>");
              out.println("<td><a href='editScreen?id="+rs.getInt(1)+"'>Edit</a></td>");
              out.println("<td><a href='deleteurl?id="+rs.getInt(1)+"'>Delete</a></td>");
              out.println("</tr>");
          }
          out.println("</table>");
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


