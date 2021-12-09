<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
<head>
	<meta charset="UTF-8">
	<title>エラー</title>
</head>
<body>
	<header>
		<h1>某市立図書館 Webシステム</h1>
	</header>
	<main id="error">
		<article>
			<h2>エラー</h2>
			<p>エラーが発生しました。</p>
			<p>ユーザ認証ページから入り直してください。</p>
			<p><a href="<%= request.getContextPath() %>/auth">ユーザ認証ページに戻る</a></p>
		</article>
	</main>
	<footer>
		<div id="copyright">&copy; 2021 某市立図書館</div>
	</footer>
</body>
</html>