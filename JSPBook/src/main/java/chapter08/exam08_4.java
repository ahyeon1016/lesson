package chapter08;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/8_4")
public class exam08_4 extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("get 도착");
		
		RequestDispatcher ds = req.getRequestDispatcher("chapter8/exam8_4.jsp");
		ds.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("post 도착");
		
		String id = req.getParameter("id"); 
		String pwd = req.getParameter("pwd");
		
		System.out.println("아이디 : "+id+" /비밀번호 : "+pwd);
		
		req.setAttribute("id", id);
		req.setAttribute("pwd", pwd);
		
		RequestDispatcher ds = req.getRequestDispatcher("chapter8/exam8_4_page.jsp");
		ds.forward(req, resp);
	}
	
}
