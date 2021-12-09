package jp.boblio.auth;

/**
 * authテーブルの1レコードを取得するクラス
 * @author tutor
 */
public class AuthBean {

	/**
	 * クラスフィールド
	 */
	private String card;			// 利用者カード番号
	private String userId;		// ログインID
	private String password;	// 入力パスワード

	public AuthBean(String userId, String password) {
		super();
		this.userId = userId;
		this.password = password;
	}

	public AuthBean(String card, String userId, String password) {
		this(userId, password);
		this.card = card;
	}

	public String getCard() {
		return this.card;
	}

	public String getUserId() {
		return this.userId;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Auth [");
		builder.append("card=" + this.card + ", ");
		builder.append("userId=" + this.userId + ", ");
		builder.append("password=" + this.password);
		builder.append("]");
		return builder.toString();
	}

}
