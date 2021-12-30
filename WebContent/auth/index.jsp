<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ja">
<head>
	<meta charset="UTF-8">
	<title>ユーザ認証 - 某市立図書館 Webシステム</title>
</head>
<body>
	<header>
		<h1>某市立図書館 Webシステム</h1>
	</header>
	<main id="signin">
		<article>
			<h2>ユーザ認証</h2>
			<p><a href="/biblio/AuthServlet?action=signup">パスワードを変更する</a></p>
			<p><a href=#"">ユーザ登録する</a></p>
			<c:if test="${not empty requestScope.message}">
				<p>${requestScope.message}</p>
			</c:if>
			<form name="signin">
				<table>
					<tr>
						<th>ユーザID</th>
						<td>
							<input type="text" name="userId" value="12056692" />
						</td>
					</tr>
					<tr>
						<th>パスワード</th>
						<td>
							<input type="text" name="password" value="QnLmqZ9b" />
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<button formaction="/biblio/AuthServlet" formmethod="post" name="action" value="signin">サインイン</button>
						</td>
					</tr>
				</table>
			</form>
		</article>
	</main>
	<footer>
		<div id="copyright">&copy;2021 The Certain City Public Library Web System.</div>
	</footer>
</body>
</html>