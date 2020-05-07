package chapter7.controller;


import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import chapter7.beans.User;
import chapter7.exception.NoRowsUpdatedRuntimeException;
import chapter7.service.UserService;



@WebServlet(urlPatterns = { "/index.jsp" })
public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		List<User> userList = new UserService().getUsers();
		request.setAttribute("userList", userList);
		request.getRequestDispatcher("/home.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		User userStatus = getUserStatus(request);

		try {
			new UserService().updateStatus(userStatus);
		} catch(NoRowsUpdatedRuntimeException e) {
			request.getRequestDispatcher("./").forward(request, response);
			return;
		}

		List<User> userList = new UserService().getUsers();

		request.setAttribute("userList", userList);

		request.getRequestDispatcher("/home.jsp").forward(request, response);

	}

	private User getUserStatus(HttpServletRequest request)
			throws IOException, ServletException {

		User userStatus = new User();
		userStatus.setId(Integer.parseInt(request.getParameter("id")));
		userStatus.setStatus(Integer.parseInt(request.getParameter("status")));
		return userStatus;
	}
}