package chapter7.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import chapter7.beans.User;
import chapter7.service.BranchService;
import chapter7.service.PostService;
import chapter7.service.UserService;

@WebServlet(urlPatterns = { "/signup" })
public class SignUpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		List<User> branchList = new BranchService().getBranches();
		List<User> postList = new PostService().getPosts();

		request.setAttribute("branchList", branchList);
		request.setAttribute("postList", postList);

		request.getRequestDispatcher("signup.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		List<String> messages = new ArrayList<String>();
		List<User> branchList = new BranchService().getBranches();
		List<User> postList = new PostService().getPosts();

		User user = new User();
		user.setLoginId(request.getParameter("login_id"));
		user.setPassword(request.getParameter("password"));
		user.setName(request.getParameter("name"));
		user.setBranchId(Integer.parseInt(request.getParameter("branch_id")));
		user.setPostId(Integer.parseInt(request.getParameter("post_id")));

		if (isValid(request, messages) == true) {

			try {
				int checkUser = new UserService().register(user);
				if (checkUser ==0) {
					messages.add("ログインIDが既に存在します");
					request.setAttribute("errorMessages", messages);
					request.setAttribute("branchList", branchList);
					request.setAttribute("postList", postList);
					request.setAttribute("inputUser", user);
					request.getRequestDispatcher("signup.jsp").forward(request, response);
				}
			} catch (SQLException e) {
				messages.add("登録に失敗しました");
				request.setAttribute("errorMessages", messages);
				request.setAttribute("branchList", branchList);
				request.setAttribute("postList", postList);
				request.setAttribute("inputUser", user);
				request.getRequestDispatcher("signup.jsp").forward(request, response);
			}
			response.sendRedirect("./");
		} else {
			request.setAttribute("errorMessages", messages);
			request.setAttribute("branchList", branchList);
			request.setAttribute("postList", postList);
			request.setAttribute("inputUser", user);
			request.getRequestDispatcher("signup.jsp").forward(request, response);
		}
	}

	private boolean isValid(HttpServletRequest request, List<String> messages) {
		String loginId = request.getParameter("login_id");
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		String password1 = request.getParameter("password1");
		int branchId = Integer.parseInt(request.getParameter("branch_id"));
		int postId = Integer.parseInt(request.getParameter("post_id"));

		request.setAttribute("name", name);
		request.setAttribute("login_id", loginId);

		if (branchId ==1) {
			messages.add("支店名を入力してください");
		}

		if (postId ==1) {
			messages.add("部署/役職名を入力してください");
		}

		if (StringUtils.isEmpty(loginId) == true) {
			messages.add("ログインIDを入力してください");
		}

		if(!loginId.matches("[0-9a-zA-Z]{6,20}")) {
			messages.add("ログインID半角英数字6文字以上20文字以下で入力してください");
		}

		if (StringUtils.isEmpty(name) == true) {
			messages.add("ユーザー名を入力してください");
		}

		if(name.length()>10) {
			messages.add("ユーザー名10文字以内にしてください");
		}

		if (StringUtils.isEmpty(password) == true) {
			messages.add("パスワードを入力してください");
		}

		if(!password.matches("[a-zA-Z0-9!-/:-@\\[-`{-~]{6,20}")) {
			messages.add("パスワード半角英数字6文字以上20文字以下で入力してください");
		}

		if (!password.equals(password1)) {
			messages.add("パスワードが一致しません");
		}

		if (messages.size() == 0) {
			return true;
		} else {
			return false;
		}
	}

}