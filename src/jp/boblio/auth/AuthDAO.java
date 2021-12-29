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
	private static final String SQL_FIND_BY_ID_AND_PASSWORD = "SELECT * FROM auth WHERE card = ? AND password = ?";
	private static final String SQL_INSERT_AUTH = "INSERT INTO auth (card, password) VALUES (?, ?)";
	private static final String SQL_UPDATE_AUTH = "UPDATE auth SET password = ? WHERE card = ?";
	private static final String SQL_DELETE_BY_CARD = "DELETE FROM auth WHERE card = ?";

	/**
	 * コンストラクタ
	 * @param conn データベース接続オブジェクト
	 */
	public AuthDAO(Connection conn) {
		super(conn);
	}

	/**
	 * 指定されたユーザIDとパスワードに対応する認証クラスを取得する。
	 * @param card 	 	 ログインID：利用者カード番号
	 * @param password ログインパスワード
	 * @return 対応する認証クラスがある場合はそのインスタンス、それ以外はnull
	 * 				 返されるインスタンスのパスワードはnullである。
	 * @throws AuthDAOException
	 */
	public AuthBean findMember(String card, String password) throws AuthDAOException {
		String hashedPassword = PasswordUtil.getHashdPassword(password, card);
		try (PreparedStatement pstmt = this.conn.prepareStatement(SQL_FIND_BY_ID_AND_PASSWORD)) {
			pstmt.setString(1, card);
			pstmt.setString(2, hashedPassword);
			AuthBean bean = null;
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					bean = new AuthBean(rs.getString("card"), null);
				}
			}
			return bean;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new AuthDAOException("登録ユーザを見つけることができませんでした。", e);
		}
	}

	/**
	 * ユーザID（利用者カード番号）とパスワードを登録する。
	 * @param card			// ユーザID（利用者カード番号）
	 * @param password	// パスワード
	 * @return 登録に成功した場合には1、それ以外はAuthDAOException
	 * @throws AuthDAOException
	 */
	public int insert(String card, String password) throws AuthDAOException {
		String hashedPassword = PasswordUtil.getHashdPassword(password, card);
		try (PreparedStatement pstmt = this.conn.prepareStatement(SQL_INSERT_AUTH)) {
			pstmt.setString(1, card);
			pstmt.setString(2, hashedPassword);
			return pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new AuthDAOException("ユーザ登録ができませんでした。", e);
		}
	}

	/**
	 * パスワードを更新する。
	 * @param card			// ユーザID（利用者カード番号）
	 * @param password	// パスワード
	 * @return 更新に成功した場合には1、それ以外はAuthDAOException
	 * @throws AuthDAOException
	 */
	public int update(String card, String password) throws AuthDAOException {
		String hashedPassword = PasswordUtil.getHashdPassword(password, card);
		try (PreparedStatement pstmt = this.conn.prepareStatement(SQL_UPDATE_AUTH)) {
			pstmt.setString(1, hashedPassword);
			pstmt.setString(2, card);
			return pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new AuthDAOException("ユーザ登録ができませんでした。", e);
		}
	}

	/**
	 * ユーザを削除する。
	 * @param card			// ユーザID（利用者カード番号）
	 * @return 削除に成功した場合には1、それ以外はAuthDAOException
	 * @throws AuthDAOException
	 */
	public int delete(String card) throws AuthDAOException {
		try (PreparedStatement pstmt = this.conn.prepareStatement(SQL_DELETE_BY_CARD)) {
			pstmt.setString(1, card);
			return pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new AuthDAOException("ユーザ削除ができませんでした。", e);
		}
	}
}
