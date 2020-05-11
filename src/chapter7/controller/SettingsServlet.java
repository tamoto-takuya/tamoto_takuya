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
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import chapter7.beans.User;
import chapter7.exception.NoRowsUpdatedRuntimeException;
import chapter7.service.BranchService;
import chapter7.service.PostService;
import chapter7.service.UserService;

@WebServlet(urlPatterns = { "/settings" })
public class SettingsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		int setId = Integer.parseInt(request.getParameter("id"));
		User editUser = new UserService().getUser(setId);

		List<User> branchList = new BranchService().getBranches();
		List<User> postList = new PostService().getPosts();

		request.setAttribute("branchList", branchList);
		request.setAttribute("postList", postList);
		request.setAttribute("editUser", editUser);

		request.getRequestDispatcher("settings.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		int setId = Integer.parseInt(request.getParameter("id"));
		User editUser = new UserService().getUser(setId);

		List<String> messages = new ArrayList<String>();
		List<User> branchList = new BranchService().getBranches();
		List<User> postList = new PostService().getPosts();
		HttpSession session = request.getSession();
		User inputUser = getInputUser(request);

		if (isValid(request, messages) == true) {

			try {
				if (inputUser.getLoginId().equals(editUser.getLoginId())) {
					new UserService().update(inputUser);
				} else {

					int id =new UserService().checkUpdate(inputUser);
					if (id ==0) {
						messages.add("ログインIDが既に存在します");
						session.setAttribute("errorMessages", messages);
						request.setAttribute("branchList", branchList);
						request.setAttribute("postList", postList);
						request.setAttribute("editUser", inputUser);

						request.getRequestDispatcher("settings.jsp").forward(request, response);
					}
				}
			} catch (NoRowsUpdatedRuntimeException e) {
				messages.add("他の人によって更新されています。最新のデータを表示しました。データを確認してください。");
				session.setAttribute("errorMessages", messages);
				request.setAttribute("editUser", inputUser);
				request.getRequestDispatcher("settings.jsp").forward(request, response);
				return;
			} catch (SQLException e) {
				e.printStackTrace();
			}

			response.sendRedirect("./");
		} else {
			session.setAttribute("errorMessages", messages);
			request.setAttribute("branchList", branchList);
			request.setAttribute("postList", postList);
			request.setAttribute("editUser", inputUser);
			request.getRequestDispatcher("settings.jsp").forward(request, response);
		}
	}

	private User getInputUser(HttpServletRequest request)
			throws IOException, ServletException {

		User inputUser = new User();
		inputUser.setId(Integer.parseInt(request.getParameter("id")));
		inputUser.setLoginId(request.getParameter("login_id"));
		inputUser.setPassword(request.getParameter("password"));
		inputUser.setName(request.getParameter("name"));
		inputUser.setBranchId(Integer.parseInt(request.getParameter("branch_id")));
		inputUser.setPostId(Integer.parseInt(request.getParameter("post_id")));
		return inputUser;
	}

	private boolean isValid(HttpServletRequest request, List<String> messages) {
		String loginId = request.getParameter("login_id");
		String password = request.getParameter("password");
		String password1 = request.getParameter("password1");
		String name = request.getParameter("name");
		int branchId = Integer.parseInt(request.getParameter("branch_id"));
		int postId = Integer.parseInt(request.getParameter("post_id"));

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

		if (StringUtils.isEmpty(password) == false) {

			if(!password.matches("[a-zA-Z0-9!-/:-@\\[-`{-~]{6,20}")) {
				messages.add("パスワード半角英数字記号6文字以上20文字以下で入力してください");
			}
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
