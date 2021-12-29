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
	private String password;	// 入力パスワード

	/**
	 * コンストラクタ
	 * @param card 利用者カード番号
	 * @param password パスワード（平文）
	 */
	public AuthBean(String card, String password) {
		super();
		this.card = card;
		this.password = password;
	}

	/**
	 * 利用者カード番号を取得する。
	 * @return 利用者カード番号
	 */
	public String getCard() {
		return this.card;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Auth [");
		builder.append("card=" + this.card + ", ");
		builder.append("password=" + this.password);
		builder.append("]");
		return builder.toString();
	}

}
