package chapter7.controller;


import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import chapter7.beans.User;
import chapter7.service.UserService;



@WebServlet(urlPatterns = { "/index.jsp" })
public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		List<User> userList = new UserService().getUser();
//		System.out.println(userList.size()); ※リスト確認
		request.setAttribute("userList", userList);

		request.getRequestDispatcher("/home.jsp").forward(request, response);
	}
}