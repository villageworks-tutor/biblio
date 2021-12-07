/**
 * 【データベース・データベースユーザの作成】
 * 		★ データベース接続情報 ★
 * 			JDBCドライバ：org.postgresql.Driver
 * 			URL：jdbc:postgreql:bliblodb
 * 					 jdbc:postgresql://localhost:5432/liblodb	
 * 			USER：librarian
 * 			PASSSWORD：himitu
 */

DROP DATABASE IF EXISTS liblodb;
DROP USER IF EXISTS librarian;

CREATE USER librarian WITH PASSWORD 'himitu';
CREATE DATABASE liblodb OWNER librarian ENCODING 'utf8';
