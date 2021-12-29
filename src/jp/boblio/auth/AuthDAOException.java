package jp.boblio.auth;

import jp.boblio.common.DAOException;

/**
 * AuhtDAOで発生した例外クラス
 * @author tutor
 */
public class AuthDAOException extends DAOException {

	public AuthDAOException(String message, Throwable cause) {
		super(message, cause);
	}
}
