<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link href="./css/style.css" rel="stylesheet" type="text/css">
	<title>ユーザー登録</title>
	</head>
	<body>
		<div class="main-contents">
			<h1>ユーザー新規登録ページ</h1>
			<c:if test="${ not empty errorMessages }">
				<div class="errorMessages">

					<ul>
						<c:forEach items="${errorMessages}" var="message">
							<li><c:out value="${message}" /></li>
						</c:forEach>
					</ul>
				</div>
				<c:remove var="errorMessages" scope="session" />
			</c:if>
			<form action="signup" method="post" onSubmit="return check()">

				<label for="login_id">ログインID ※必須</label><br>
				<input name="login_id" value="${login_id}" id="login_id" required><br>
				<p class="error" id="loginIdError" style="display: none;"></p>

				<label for="password">パスワード ※必須</label><br>
				<input name="password" type="password" id="password" required> <br>
				<p class="error" id="passwordError" style="display: none;"></p>

				<label for="password">パスワード確認 ※必須</label><br>
				<input name="confirmPass" type="password" id="confirmPass" required> <br>
				<p class="error" id="confirmPassError" style="display: none;"></p>

				<label for="name">名前 ※必須</label><br>
				<input name="name" value="${name}" id="name" required><br>
				<p class="error" id="nameError" style="display: none;"></p>


				<label for="branch_id">支店名</label><br>
				<select style="width: 175px" name="branch_id" id="branch_id">
					<c:forEach items="${branchList}" var="branch">
						<c:choose>
							<c:when  test="${branch.branchId==inputUser.branchId}">
								<option value="${branch.branchId}" selected>${branch.branchName}</option>
							</c:when>
							<c:otherwise>
								<option value="${branch.branchId}">${branch.branchName}</option>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</select><br>

				<label for="post_id">部署・役職</label><br>
				<select style="width: 175px" name="post_id" id="post_id">
					<c:forEach items="${postList}" var="post">
						<c:choose>
							<c:when  test="${post.postId==inputUser.postId}">
								<option value="${post.postId}" selected>${post.postName}</option>
							</c:when>
							<c:otherwise>
								<option value="${post.postId}">${post.postName}</option>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</select><br>
				<input type="submit" value="登録">  <a href="./">戻る</a>
			</form>
		</div>
		<script type="text/javascript">

			function check() {

				var isValid = true;

				var name = document.getElementById("name").value;
				if (name.length > 10) {
					nameError.style.display = "block";
					nameError.innerHTML = "※ユーザー名10文字以下で入力してください";
					isValid = false;
				} else {
					nameError.style.display = "none";
				}

				var loginIdValue = login_id.value;
				var pattern = /^[0-9a-zA-Z]{6,20}$/g;
				if (!loginIdValue.match(pattern)) {
					loginIdError.style.display = "block";
					loginIdError.innerHTML = "※ログインID半角英数字6文字以上20文字以下で入力してください";
					isValid = false;
				} else {
					loginIdError.style.display = "none";
				}

				var passwordValue = password.value;
				var passPattern = /^[0-9a-zA-Z]{6,20}$/g;
				if (!passwordValue.match(passPattern)) {
					passwordError.style.display = "block";
					passwordError.innerHTML = "※パスワード半角英数字6文字以上20文字以下で入力してください";
					isValid = false;
				} else {
					passwordError.style.display = "none";
				}

				var confirmPassValue = confirmPass.value;
				if (confirmPassValue != passwordValue) {
					confirmPassError.style.display = "block";
					confirmPassError.innerHTML = "※パスワードが一致しません";
					isValid = false;
				} else {
					confirmPassError.style.display = "none";
				}

				return isValid;
			}

		</script>
	</body>
</html>