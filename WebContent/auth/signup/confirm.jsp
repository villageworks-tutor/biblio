<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ja">
<head>
	<meta charset="UTF-8">
	<title>ユーザ登録 - 某市立図書館 Webシステム</title>
</head>
<body>
	<header>
		<h1>某市立図書館 Webシステム</h1>
	</header>
	<main id="signup">
		<article>
			<h2>ユーザ登録</h2>
			<c:if test="${!empty requestScope.errors}">
			<ul class="error" style="color: tomato;">
				<c:forEach items="${requestScope.errors}" var="error">
					<li>${pageScope.error}</li>
				</c:forEach>
			</ul>
			</c:if>
			<p>ユーザIDはご自分の利用者カードの番号を入力してください。</p>
			<form name="signup">
				<table>
					<tr>
						<th>ユーザID</th>
						<td>
							<c:out value="${sessionScope.userId}" />
						</td>
					</tr>
					<tr>
						<th>パスワード</th>
						<td>
							<c:out value="${sessionScope.password}" />
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<button formaction="/biblio/AuthServlet?action=signup" formmethod="post" name="mode" value="execute">登録</button>
							<button formaction="/biblio/AuthServlet" formmethod="post" name="action" value="signup">
								<input type="hidden" name="mode" value="modify" />
								修正
							</button>
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