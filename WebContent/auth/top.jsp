<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ja">
<head>
	<meta charset="UTF-8">
	<title>某市立図書館トップ</title>
</head>
<body>
	<header>
		<h1>某市立図書館 Webシステム</h1>
	</header>
	<main id="top">
		<article>
			<h2>図書館トップページ</h2>
			<p>${requestScope.message}</p>
			<p><a href="/biblio/AuthServlet?action=signout">サインアウトする</a></p>
		</article>
	</main>
	<footer>
		<div id="copyright">&copy; 2021 某市立図書館</div>
	</footer>
</body>
</html>