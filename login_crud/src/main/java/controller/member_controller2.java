package controller;

import java.io.IOException;
import java.util.ArrayList;

import dao.member_repository;
import dto.member_dto;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/readall")
public class member_controller2 extends HttpServlet {
// ��ǥ : READ �������� dto�� �о�ͼ� �信 ����ϴ� ���� ��ǥ
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("readall get����");
		//��ó�� : �Ķ���� ���� ����
		//���̵�
		member_repository mr = member_repository.getInstance();
		ArrayList<member_dto> arr = mr.getAllmember();
		//���̵�
		req.setAttribute("list", arr);
		RequestDispatcher ds = req.getRequestDispatcher("all.jsp");
		ds.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	}
	
}