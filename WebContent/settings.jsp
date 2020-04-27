<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>${loginUser.account}の設定</title>
		<link href="css/style.css" rel="stylesheet" type="text/css">
	</head>
	<body>
		<div class="main-contents">
			<h1>ユーザー編集ページ</h1>
			<c:if test="${ not empty errorMessages }">
				<div class="errorMessages">
					<ul>
						<c:forEach items="${errorMessages}" var="message">
							<li><c:out value="${message}" />
						</c:forEach>
					</ul>
				</div>
				<c:remove var="errorMessages" scope="session"/>
			</c:if>

			<form action="settings" method="post"><br>
				<input name="id" value="${editUser.id}" id="id" type="hidden"/>

				<label for="name">ログインID</label><br />
				<input name="login_id" value="${editUser.loginId}" id="login_id"/><br />

				<label for="password">パスワード</label><br />
				<input name="password" type="password" id="password"/> <br />

				<label for="password">パスワード確認用</label><br>
			    <input name="password1" type="password" id="password1"> <br>

				<label for="name">名前</label><br />
				<input name="name" value="${editUser.name}" id="name"/><br />

				<label for="name">支店番号</label><br />
				<input name="branch_id" value="${editUser.branchId}" id="branch_id"/><br />

				<label for="name">部署/役職</label><br />
				<input name="post_id" value="${editUser.postId}" id="post_id"/><br />


				<input type="submit" value="登録" />    <a href="./">戻る</a>
			</form>
		</div>
	</body>
</html>