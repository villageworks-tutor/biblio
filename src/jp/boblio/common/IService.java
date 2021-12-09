package jp.boblio.common;

public interface IService {
	/**
	 * リクエストパラメータの妥当性検査を行う。
	 * @return すべての検査にパスした場合にはtrue、それ以外はfalse
	 */
	public boolean validate();

	/**
	 * 処理を字高する。
	 * @return 次画面遷移先URL
	 */
	public String execute();
}
