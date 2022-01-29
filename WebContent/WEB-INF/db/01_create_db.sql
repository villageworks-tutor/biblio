/**
 * 【データベース・データベースユーザの作成】
 * 		★ データベース接続情報 ★
 * 			JDBCドライバ：org.postgresql.Driver
 * 			URL：jdbc:postgreql:bliblodb
 * 					 jdbc:postgresql://localhost:5432/liblodb	
 * 			URL for unit tests
 *				 ：jdbc:postgreql:bliblodb
 * 					 jdbc:postgresql://localhost:5432/liblodb	
 * 			USER：librarian
 * 			PASSSWORD：himitu
 */

DROP DATABASE IF EXISTS liblodb;
DROP DATABASE IF EXISTS testliblodb;
DROP USER IF EXISTS librarian;
DROP USER IF EXISTS tester;

CREATE USER librarian WITH PASSWORD 'himitu';
CREATE USER tester WITH PASSWORD 'himitu';
CREATE DATABASE liblodb OWNER librarian ENCODING 'utf8';
CREATE DATABASE testliblodb OWNER tester ENCODING 'utf8';
