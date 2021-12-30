package jp.boblio.common;

import java.sql.Connection;

/**
 * すべてのDAOクラスの基底クラス
 * @author tutor
 */
public class BaseDAO {

	/**
	 * クラスフィールド：データベース接続オブジェクト
	 */
	protected Connection conn;

	/**
	 * コンストラクタ
	 * @param conn データベース接続オブジェクト
	 */
	public BaseDAO(Connection conn) {
		this.conn = conn;
	}

}
