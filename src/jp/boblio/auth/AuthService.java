package jp.boblio.auth;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import jp.boblio.common.DAOException;
import jp.boblio.common.DbUtil;
import jp.boblio.common.IService;
import jp.villageworks.core.DataUtils;
import jp.villageworks.core.DataValidator;

public class AuthService implements IService {

	/** クラス定数 */
	private static final String ACTION_SIGNIN = "signin";
	private static final String ACTION_SIGNOUT = "signout";
	private static final String ACTION_SIGNUP = "signup";
	private static final String MODE_ENTRY = "entry";
	private static final String MODE_CONFIRM = "confirm";
	private static final String MODE_EXECUTE = "execute";

	// セッションに設定するキー文字列
	private static final String AUTH_KEY = "authenticaded";
	private static final String MESSAGE_KEY = "message";

	// リクエストパラメータのキー定数
	private static final String ACTION_KEY = "action";
	private static final String USERID_KEY = "userId";
	private static final String PASSWORD_KEY = "password";
	private static final String MODE_KEY = "mode";

	// リクエストパラメータの文字列長検査の文字列の下限値
	private static final int ID_LENGTH = 8;
	private static final int PASSWORD_MIN_LENGTH = 8;

	// メッセージ文字列
	private static final String SUCCESS_SIGNIN = "ユーザ認証に成功しました。";
	private static final String FAILURE_SIGNIN = "ユーザIDまたはパスワードが違います。";
	private static final String SUCCESS_SIGNOUT = "サインアウトしました。";

	// 入力値検査のエラーメッセージ
	private static final String ERR_REQUIRED_ID = "ユーザIDを入力してください。";
	private static final String ERR_LENGTH_ID = "ユーザIDは8文字で入力してください。";
	private static final String ERR_CHARTYPE_USREID = "ユーザIDは半角数字で入力してください。";
	private static final String ERR_REQUIRED_PASSWORD = "パスワードを入力してください。";
	private static final String ERR_OVERLENGTH_PASSWORD = "パスワードは8文字以上で入力してください。";
	private static final String ERR_CHARTYPE_PASSWORD = "パスワードは半角英数字で入力してください。";

	// 遷移先URL
	public static final String URL_AUTH = "auth/";
	public static final String URL_SUCCESS = "auth/top.jsp";
	public static final String URL_FAILUER = URL_AUTH;
	public static final String URL_SIGNUP_ENTRY = "auth/signup/entry.jsp";
	public static final String URL_SIGNUP_CONFIRM = "auth/signup/confirm.jsp";
	public static final String URL_SIGNUP_COMPLETE = "auth/signup/complete.jsp";

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
	 * エラーリストを取得する。
	 * @return エラーリスト
	 */
	public List<String> getErrors() {
		return this.errors;
	}

	/**
	 * 入力データの妥当性検査を実行する。
	 * @return すべての入力値が妥当である場合はtrue、それ以外はfalse
	 */
	@Override
	public boolean validate() {
		// リクエストパラメータからactionキーとmodeキーを取得
		String action = this.request.getParameter(ACTION_KEY);
		String mode = this.request.getParameter(MODE_KEY);

		// actionキーが存在しない場合またはactionキーが「signout」である場合はtrueを返して終了
		if (DataUtils.isNull(action) || action.equals(ACTION_SIGNOUT)) return true;
		if (action.equals(ACTION_SIGNUP) && DataUtils.isNull(mode)) return true;

		// リクエストパラメータから送信データを取得
		String userId = this.request.getParameter(USERID_KEY);
		String password = this.request.getParameter(PASSWORD_KEY);
		switch (action) {

		case ACTION_SIGNIN:
			// ユーザIDの検査：必須入力検査
			if (!DataValidator.isRequired(userId)) {
				this.errors.add(ERR_REQUIRED_ID);
			}
			// パスワードの検査：必須入力検査
			if (!DataValidator.isRequired(password)) {
				this.errors.add(ERR_REQUIRED_PASSWORD);
			}
			break;

		case ACTION_SIGNUP:
			// ユーザIDの検査：必須入力検査・文字種検査
			if (!DataValidator.isRequired(userId)) {
				this.errors.add(ERR_REQUIRED_ID);
			} else if (!DataValidator.isInRange(userId, ID_LENGTH, ID_LENGTH)) {
				this.errors.add(ERR_LENGTH_ID);
			} else if (!DataValidator.isNumeric(userId)) {
				this.errors.add(ERR_CHARTYPE_USREID);
			}
			// パスワードの検査：必須入力検査・文字数検査・文字種検査
			if (!DataValidator.isRequired(password)) {
				this.errors.add(ERR_REQUIRED_PASSWORD);
			} else if (!DataValidator.isOverLimit(password, PASSWORD_MIN_LENGTH)) {
				this.errors.add(ERR_OVERLENGTH_PASSWORD);
			} else if (!DataValidator.isAlphaNumericSeq(password)) {
				this.errors.add(ERR_CHARTYPE_PASSWORD);
			}
			break;

		default:
				break;
		}

		// エラーリストの要素による戻り値の切換え
		if (errors.size() == 0) {
			// エラーなし
			return true;
		} else {
			// エラーあり：リクエストスコープに入力値とエラーメッセージを設定
			request.setAttribute(MESSAGE_KEY, FAILURE_SIGNIN);
			if (action.equals(ACTION_SIGNUP)) {
				request.setAttribute(USERID_KEY, userId);
				request.setAttribute(PASSWORD_KEY, password);
			}
			return false;
		}
	}

	/**
	 * 処理を実行する。
	 * @return 処理に成功した場合は遷移先画面のURL、失敗した場合はサインイン画面URL
	 * 				 ユーザ認証において失敗した場合には、セキュリティを考慮して、サインインページに戻り「ユーザIDまたはパスワードが違います。」というメッセージを表示する。
	 * @throws AuthDAOException
	 */
	@Override
	public String execute() throws DAOException {

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

			if (!DataUtils.isNull(session.getAttribute(AUTH_KEY))) {
				// セッションにsignin属性が存在する場合
				session.removeAttribute(AUTH_KEY);
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
				if (DataUtils.isNull(session.getAttribute(AUTH_KEY))) {
					// セッションにsignin属性がある場合：signin属性を削除
					session.removeAttribute(AUTH_KEY);
					session.invalidate();
				}
				// リクエストスコープにメッセージを設定
				request.setAttribute(MESSAGE_KEY, FAILURE_SIGNIN);
				// 遷移先URLに認証ページページに設定
				nextPage = URL_FAILUER;
			} else if (!DataUtils.isNull(auth)) {
				// 認証クラスを取得できた（nullでなかった）場合
				session.setAttribute(AUTH_KEY, auth);
				// リクエストスコープにメッセージを設定
				request.setAttribute(MESSAGE_KEY, SUCCESS_SIGNIN);
				// 遷移先URLにトップページを設定
				nextPage = URL_SUCCESS;
			}
			break;

		case ACTION_SIGNOUT:
			if (!DataUtils.isNull(session.getAttribute(AUTH_KEY))) {
				// セッションにsignin属性がある場合：signin属性を削除
				session.removeAttribute(AUTH_KEY);
				session.invalidate();
			}
			// リクエストスコープにメッセージを設定
			request.setAttribute(MESSAGE_KEY, SUCCESS_SIGNOUT);
			// 遷移先URLに認証ページを設定
			nextPage = URL_AUTH;
			break;

		case ACTION_SIGNUP:
			// リクエストパラメータを取得
			String mode = this.request.getParameter(MODE_KEY);
			if (DataUtils.isEmpty(mode) || mode.equals(MODE_ENTRY)) {
				nextPage = URL_SIGNUP_ENTRY;
			} else if (mode.equals(MODE_CONFIRM)) {
				// リクエストスコープに入力値を設定
				this.request.setAttribute(USERID_KEY, userId);
				this.request.setAttribute(PASSWORD_KEY, password);
				// 遷移先URLに登録内容確認ページを設定
				nextPage = URL_SIGNUP_CONFIRM;
			} else if (mode.equals(MODE_EXECUTE)) {
				// 遷移先URLに灯籠完了ページを設定
				nextPage = URL_SIGNUP_COMPLETE;
			}
			break;

		default:
			// actionキーが存在しない場合：認証ページに遷移
			nextPage = URL_FAILUER;
			break;
		}
		return nextPage;

	}

}
