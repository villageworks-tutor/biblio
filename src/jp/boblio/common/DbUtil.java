package jp.boblio.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbUtil {

	/** クラス定数：データベース接続情報文字列定数群 */
	private static final String DB_DRIVER = "org.postgresql.Driver";
	private static final String DB_URL = "jdbc:postgresql:liblodb";
	private static final String DB_USER = "librarian";
	private static final String DB_PASSWORD = "himitu";

	/**
	 * データベースに接続する（データベース接続オブジェクトを取得する）。
	 * @return Connection データベースへの接続に成功した場合はデータベース接続オブジェクト、それ以外はnull
	 */
	public static Connection getConnection() {
		Connection conn = null;
		try {
			Class.forName(DB_DRIVER);
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			return conn;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
