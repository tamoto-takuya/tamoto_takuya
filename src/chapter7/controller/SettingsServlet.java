package chapter7.controller;

import java.io.IOException;
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
		//ID取得の為、int型へ変更
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

		List<String> messages = new ArrayList<String>();
		HttpSession session = request.getSession();
		User editUser = getEditUser(request);

		if (isValid(request, messages) == true) {

			try {
				new UserService().update(editUser);
			} catch (NoRowsUpdatedRuntimeException e) {
				messages.add("他の人によって更新されています。最新のデータを表示しました。データを確認してください。");
				session.setAttribute("errorMessages", messages);
				request.setAttribute("editUser", editUser);
				request.getRequestDispatcher("settings.jsp").forward(request, response);
				return;
			}

			session.setAttribute("user", editUser);

			response.sendRedirect("./");
		} else {
			session.setAttribute("errorMessages", messages);

			List<User> branchList = new BranchService().getBranches();
			List<User> postList = new PostService().getPosts();

			request.setAttribute("branchList", branchList);
			request.setAttribute("postList", postList);
			request.setAttribute("editUser", editUser);
			request.getRequestDispatcher("settings.jsp").forward(request, response);
		}
	}

	private User getEditUser(HttpServletRequest request)
			throws IOException, ServletException {

		User editUser = new User();
		editUser.setId(Integer.parseInt(request.getParameter("id")));
		editUser.setLoginId(request.getParameter("login_id"));
		editUser.setPassword(request.getParameter("password"));
		editUser.setName(request.getParameter("name"));
		editUser.setBranchId(Integer.parseInt(request.getParameter("branch_id")));
		editUser.setPostId(Integer.parseInt(request.getParameter("post_id")));
		return editUser;
	}

	private boolean isValid(HttpServletRequest request, List<String> messages) {
		String loginId = request.getParameter("login_id");
		String password = request.getParameter("password");
		String password1 = request.getParameter("password1");
		String name = request.getParameter("name");

		if (StringUtils.isEmpty(loginId) == true) {
			messages.add("ログインIDを入力してください");
		}

		if(!loginId.matches("[0-9a-zA-Z]{6,20}")) {
			messages.add("ログインID半角英数字6文字以上20文字以下で入力してください");
		}

		if (StringUtils.isEmpty(name) == true) {
			messages.add("ユーザー名を入力してください");
		}

		if(!name.matches(".{1,10}")) {
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

		// TODO アカウントが既に利用されていないか、IDが既に登録されていないかなどの確認も必要
		if (messages.size() == 0) {
			return true;
		} else {
			return false;
		}
	}

}
