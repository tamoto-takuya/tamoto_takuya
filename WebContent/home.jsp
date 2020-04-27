<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link href="./css/style.css" rel="stylesheet" type="text/css">
		<title>ユーザ管理システム</title>
	</head>
	<body>
		<div class="main-contents">
			<h1>ユーザー管理システム</h1>
			<div class="header">
				<table border="1">
					<tr>
						<th>ID</th>
						<th>名前</th>
						<th>支店番号</th>
						<th>部署/役職</th>
						<th>ユーザー状態</th>
					</tr>
					<c:forEach items="${userList}" var ="user">
						<tr>
							<td><a href="settings?id=${user.id}"><c:out value="${user.loginId}" /></a></td>
							<td><c:out value="${user.name}" /></td>
							<td><c:out value="${user.branchId}" /></td>
							<td><c:out value="${user.postId}" /></td>

						</tr>
					</c:forEach>
				</table>
				<a href="signup">新規登録</a>
			</div>
		</div>
	</body>
</html>
