package jp.boblio.auth;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jp.boblio.common.BaseDAO;
import util.PasswordUtil;

/**
 * authテーブルにアクセスするDAOクラス
 * @author tutor
 */
public class AuthDAO extends BaseDAO {

	/** クラス定数：SQL文字列群 */
	private static final String SQL_FIND_BY_ID_AND_PASSWORD = "select * from auth where signin_id = ? and password = ?";

	/**
	 * コンストラクタ
	 * @param conn データベース接続オブジェクト
	 */
	public AuthDAO(Connection conn) {
		super(conn);
	}

	/**
	 * 指定されたユーザIDとパスワードに対応する認証クラスを取得する。
	 * @param userId 	 ログインID
	 * @param password ログインパスワード
	 * @return 対応する認証クラスがある場合はそのインスタンス、それ以外はnull
	 * 				 返されるインスタンスのパスワードはnullである。
	 */
	public AuthBean findMember(String userId, String password) {
		String hashedPassword = PasswordUtil.getHashdPassword(password, userId);
		try (PreparedStatement pstmt = this.conn.prepareStatement(SQL_FIND_BY_ID_AND_PASSWORD)) {
			pstmt.setString(1, userId);
			pstmt.setString(2, hashedPassword);
			try (ResultSet rs = pstmt.executeQuery()) {
				AuthBean bean = null;
				if (rs.next()) {
					bean = new AuthBean(rs.getString("card"), userId, null);
				}
				return bean;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
