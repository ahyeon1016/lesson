package Controller;

import java.io.IOException;
import java.util.ArrayList;

import dao.BookRepository;
import dto.Book;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/removeCart")
public class removeCart extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		
		HttpSession session = req.getSession(false);
		
		String id = req.getParameter("id");
		if(id==null||id.trim().equals("")) {
			resp.sendRedirect("products");
			return;
		}
		
		BookRepository dao = BookRepository.getInstance();
		
		Book book= dao.getBookById(id);
		if(book==null) {
			resp.sendRedirect("exceptionNoBookId.jsp");
		}
		
		ArrayList<Book> cartList = (ArrayList<Book>)session.getAttribute("cartlist");
		Book goodsQnt = new Book();
		for(int i=0; i<cartList.size(); i++) {
			goodsQnt=cartList.get(i);
			if(goodsQnt.getBookId().equals(id)) {
				cartList.remove(goodsQnt);
			}
		}
		
		resp.sendRedirect("cart.jsp");
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	}
	
}
