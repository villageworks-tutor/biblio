package jp.boblio.auth;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import jp.boblio.common.DbUtil;
import jp.boblio.common.IService;
import jp.villageworks.core.DataUtils;
import jp.villageworks.core.DataValidator;

public class AuthService implements IService {

	/** クラス定数 */
	private static final String ACTION_SIGNIN = "signin";
	private static final String ACTION_SIGNOUT = "signout";

	// セッションに設定するキー文字列
	private static final String KEY_AUTH = "authenticaded";
	private static final String KEY_MESSAGE = "message";

	// リクエストパラメータのキー定数
	private static final String ACTION_KEY = "action";
	private static final String USERID_KEY = "userId";
	private static final String PASSWORD_KEY = "password";

	// パスワード文字列長検査の文字列の下限値
	private static final int PASSWORD_MIN_LENGTH = 8;

	// メッセージ文字列
	private static final String SUCCESS_SIGNIN = "ユーザ認証に成功しました。";
	private static final String FAILURE_SIGNIN = "ユーザIDまたはパスワードが違います。";
	private static final String SUCCESS_SIGNOUT = "サインアウトしました。";


	// 遷移先URL
	public static final String URL_AUTH = "auth/";
	public static final String URL_SUCCESS = "auth/top.jsp";
	public static final String URL_FAILUER = URL_AUTH;

	/** クラスフィールド */
	private HttpServletRequest request;
	private String action;
	private List<String> errors = new ArrayList<String>();

	/**
	 * コンストラクタ
	 * @param request リクエストパラメータ
	 */
	public AuthService(HttpServletRequest request) {
		super();
		this.request = request;
		this.action = request.getParameter(ACTION_KEY);
	}

	/**
	 * アクションを取得する。
	 * @return アクション
	 */
	public String getAction() {
		return this.action;
	}


	/**
	 * 入力データの妥当性検査を実行する。
	 * @return すべての入力値が妥当である場合はtrue、それ以外はfalse
	 */
	@Override
	public boolean validate() {
		// リクエストパラメータからactionキーを取得
		String action = this.request.getParameter(ACTION_KEY);

		// actionキーが存在しない場合またはactionキーが「signout」である場合はtrueを返して終了
		if (DataUtils.isNull(action) || action.equals(ACTION_SIGNOUT)) return true;

		// リクエストパラメータから送信データを取得
		String userId = this.request.getParameter(USERID_KEY);
		String password = this.request.getParameter(PASSWORD_KEY);
		// ユーザIDの検査：必須入力検査
		if (!DataValidator.isRequired(userId)) {
			this.errors.add(FAILURE_SIGNIN);
		}
		// パスワードの検査：必須入力検査・文字数検査
		if (!DataValidator.isRequired(password)) {
			this.errors.add(FAILURE_SIGNIN);
		} else if (!DataValidator.isOverLimit(password, PASSWORD_MIN_LENGTH)) {
			this.errors.add(FAILURE_SIGNIN);
		}

		// エラーリストの要素による戻り値の切換え
		if (errors.size() == 0) {
			// エラーなし
			return true;
		} else {
			// エラーあり：リクエストスコープにメッセージを設定
			request.setAttribute(KEY_MESSAGE, FAILURE_SIGNIN);
			return false;
		}

	}

	/**
	 * 処理を実行する。
	 * @return 処理に成功した場合は遷移先画面のURL、失敗した場合はサインイン画面URL
	 * 				 ユーザ認証において失敗した場合には、セキュリティを考慮して、サインインページに戻り「ユーザIDまたはパスワードが違います。」というメッセージを表示する。
	 */
	@Override
	public String execute() {

		// リクエストパラメータを取得
		String userId = this.request.getParameter(USERID_KEY);
		String password = this.request.getParameter(PASSWORD_KEY);

		// セッションの取得
		HttpSession session = this.request.getSession();

		// 遷移先URLの初期化
		String nextPage = URL_AUTH;

		// actionキーによって処理を分岐
		switch (this.action) {

		case ACTION_SIGNIN:

			if (!DataUtils.isNull(session.getAttribute(KEY_AUTH))) {
				// セッションにsignin属性が存在する場合
				session.removeAttribute(KEY_AUTH);
				session.invalidate();
				// 遷移先URLに認証ページページを設定
				nextPage = URL_AUTH;
				break;
			}

			// リクエストパラメータを利用して認証クラスのオブジェクトを取得
			AuthDAO dao = new AuthDAO(DbUtil.getConnection());
			AuthBean auth = dao.findMember(userId, password);

			if (DataUtils.isNull(auth)) {
				// 認証クラスを取得できなかった（nullだった）場合
				if (DataUtils.isNull(session.getAttribute(KEY_AUTH))) {
					// セッションにsignin属性がある場合：signin属性を削除
					session.removeAttribute(KEY_AUTH);
					session.invalidate();
				}
				// リクエストスコープにメッセージを設定
				request.setAttribute(KEY_MESSAGE, FAILURE_SIGNIN);
				// 遷移先URLに認証ページページに設定
				nextPage = URL_FAILUER;
			} else if (!DataUtils.isNull(auth)) {
				// 認証クラスを取得できた（nullでなかった）場合
				session.setAttribute(KEY_AUTH, auth);
				// リクエストスコープにメッセージを設定
				request.setAttribute(KEY_MESSAGE, SUCCESS_SIGNIN);
				// 遷移先URLにトップページを設定
				nextPage = URL_SUCCESS;
			}
			break;

		case ACTION_SIGNOUT:
			if (!DataUtils.isNull(session.getAttribute(KEY_AUTH))) {
				// セッションにsignin属性がある場合：signin属性を削除
				session.removeAttribute(KEY_AUTH);
				session.invalidate();
			}
			// リクエストスコープにメッセージを設定
			request.setAttribute(KEY_MESSAGE, SUCCESS_SIGNOUT);
			// 遷移先URLに認証ページページを設定
			nextPage = URL_AUTH;
			break;

		default:
			// actionキーが存在しない場合：認証ページに遷移
			nextPage = URL_FAILUER;
			break;
		}
		return nextPage;

	}

}
