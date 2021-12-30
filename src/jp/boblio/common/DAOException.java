package jp.boblio.common;

/**
 * すべてのDAOExceptionが実装する基底クラス
 * @author tutor
 */
public class DAOException extends Exception {
	public DAOException(String message, Throwable cause) {
		super(message, cause);
	}
}
