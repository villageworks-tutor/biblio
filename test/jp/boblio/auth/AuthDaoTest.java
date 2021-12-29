package jp.boblio.auth;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;

import org.dbunit.Assertion;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.SortedTable;
import org.dbunit.dataset.filter.DefaultColumnFilter;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import jp.boblio.common.DbUtil;

class AuthDaoTest {

	/** クラス定数：データベース接続情報文字列定数群 */
	private static final String DB_DRIVER = "org.postgresql.Driver";
	private static final String DB_URL = "jdbc:postgresql:testliblodb";
	private static final String DB_USER = "tester";
	private static final String DB_PASSWORD = "himitu";

	// テスト対象テーブル名
	private static final String TARGET_TABLE = "auth";

	/** テスト登録用データセットXMLファイル関連定数群 */
	private static final String DIR_FIXTURES = "test/jp/boblio/auth/_fixtures/";
	private static final String XML_DEFAULT_5 = DIR_FIXTURES + "default_5.xml";									// テスt前初期化用のデフォルトレコード登録用
	private static final String XML_REMOVE_ALL = DIR_FIXTURES + "remove_all.xml";								// テスト後のテーブルの初期化
	private static final String XML_BEFORE_INSERT_1 = DIR_FIXTURES + "before_insert_1.xml";			// 登録処理テストの初期化用
	private static final String XML_EXPECTED_INSERT_1 = DIR_FIXTURES + "expected_insert_1.xml";	// 登録処理テストの処理実行後の期待値
	private static final String XML_BEFORE_UPDATE_1 = DIR_FIXTURES + "before_update_1.xml";			// 更新処理テストの初期化用
	private static final String XML_EXPECTED_UPDATE_1 = DIR_FIXTURES + "expected_update_1.xml";	// 更新処理テストの処理実行後の期待値
	private static final String XML_BEFORE_DELETE_1 = DIR_FIXTURES + "before_delete_1.xml";			// 削除処理テストの初期化用
	private static final String XML_EXPECTED_DELETE_1 = DIR_FIXTURES + "expected_delete_1.xml";	// 削除処理テストの処理実行後の期待値

	// 比較対象外とするカラムの設定
	private static final String[] EXCLUDED_COLUMNS = {"id", "signup_at", "updated_at", "erasured_at"};
	// 比較の際の並べ替えカラムの設定
	private static final String[] SORT_COLUMNS = {"card"};

	/** テスト対象クラス：system under test */
	private AuthDAO sut;

	// テスト補助変数
	static Connection jdbcConnection = null;
	static IDatabaseConnection dbunitConnection = null;
	static IDataSet dataset = null;

	@BeforeEach
	void setUp() throws Exception {
		// テスト対象クラスのインスタンス化
		jdbcConnection = getConnection();
		sut = new AuthDAO(jdbcConnection);
		// DBUnit用データベース接続オブジェクトのインスタンス化
		dbunitConnection = new DatabaseConnection(jdbcConnection);
		// データセットの取得
		dataset = new FlatXmlDataSetBuilder().build(new FileInputStream(XML_DEFAULT_5));
		// テスト用データセットのセットアップ
		DatabaseOperation.CLEAN_INSERT.execute(dbunitConnection, dataset);
	}

	@AfterEach
	void tearDown() throws Exception {
		// テスト用データセットの削除
		DatabaseOperation.DELETE_ALL.execute(dbunitConnection, dataset);
	}

	/**
	 * テスト用データベースに接続する。
	 * @return データベース接続オブジェクト
	 * @throws Exception
	 */
	private Connection getConnection() throws Exception {
		Class.forName(DB_DRIVER);
		return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
	}

	@Nested
	@DisplayName("AuthDAO#deleteメソッドのテストクラス")
	class delete {

		@BeforeEach
		void setUp() throws Exception {
			// データセットの取得
			dataset = new FlatXmlDataSetBuilder().build(new FileInputStream(XML_BEFORE_DELETE_1));
			// テスト用データセットのセットアップ
			DatabaseOperation.INSERT.execute(dbunitConnection, dataset);
		}
		@AfterEach
		void tearDown() throws Exception {
			// データセットの取得
			dataset = new FlatXmlDataSetBuilder().build(new FileInputStream(XML_REMOVE_ALL));
			// テスト用データセットの削除
			DatabaseOperation.DELETE_ALL.execute(dbunitConnection, dataset);
		}
		@Test
		@DisplayName("【Test-11・異常系：利用者登録されていないユーザの削除】利用者カード番号「12050001」のユーザを削除できない。")
		void test_11() {
			// setup
			String card = "12050001";
			// execute & verify
			try {
				sut.delete(card);
			} catch (Exception e) {
				assertThat(e, is(instanceOf(AuthDAOException.class)));
				assertThat(e.getMessage(), is("ユーザ削除ができません"));
			}
		}
		@Test
		@DisplayName("【Test-01・正常系】利用者カード「12055448」の利用者を削除することができる。")
		void test_01() throws Exception {
			// setup
			String card = "12055448";
			ITable expected = createExpected(XML_EXPECTED_DELETE_1);
			// execute
			sut.delete(card);
			ITable actual = getActual();
			// verify
			Assertion.assertEquals(expected, actual);
		}

	}

	@Nested
	@DisplayName("AuthDAO#updateメソッドのテストクラス")
	class update {

		@BeforeEach
		void setUp() throws Exception {
			// データセットの取得
			dataset = new FlatXmlDataSetBuilder().build(new FileInputStream(XML_BEFORE_UPDATE_1));
			// テスト用データセットのセットアップ
			DatabaseOperation.INSERT.execute(dbunitConnection, dataset);
		}
		@AfterEach
		void tearDown() throws Exception {
			// データセットの取得
			dataset = new FlatXmlDataSetBuilder().build(new FileInputStream(XML_REMOVE_ALL));
			// テスト用データセットの削除
			DatabaseOperation.DELETE_ALL.execute(dbunitConnection, dataset);
		}
		@Test
		@DisplayName("【Test-11・異常系：利用者登録されていないユーザの登録】利用者カード番号「12050001」パスワード「user01」のユーザを登録するとDAOExceptionが発生する。")
		void test_11() {
			// setup
			String card = "12050001";
			String password = "user01";
			// execute & verify
			try {
				sut.update(card, password);
			} catch (Exception e) {
				assertThat(e, is(instanceOf(AuthDAOException.class)));
				assertThat(e.getMessage(), is("ユーザ登録ができませんでした。"));
			}
		}

		@Test
		@DisplayName("【Test-01・正常系】利用者カード「12055448」の利用者のパスワードを「password」に変更できる。")
		void test_01() throws Exception {
			// setup
			String card = "12055448";
			String password = "password";
			ITable expected = createExpected(XML_EXPECTED_UPDATE_1);
			// execute
			sut.update(card, password);
			ITable actual = getActual();
			// verify
			Assertion.assertEquals(expected, actual);
		}
	}

	@Nested
	@DisplayName("AuthDAO#insertメソッドのテストクラス")
	class insert {

		@BeforeEach
		void setUp() throws Exception {
			// データセットの取得
			dataset = new FlatXmlDataSetBuilder().build(new FileInputStream(XML_BEFORE_INSERT_1));
			// テスト用データセットのセットアップ
			DatabaseOperation.INSERT.execute(dbunitConnection, dataset);
		}
		@AfterEach
		void tearDown() throws Exception {
			// データセットの取得
			dataset = new FlatXmlDataSetBuilder().build(new FileInputStream(XML_REMOVE_ALL));
			// テスト用データセットの削除
			DatabaseOperation.DELETE_ALL.execute(dbunitConnection, dataset);
		}
		@Test
		@DisplayName("【Test-12・異常系：パスワードの入力検査エラー】利用者カード番号「12055448」パスワード「user01」のユーザを登録するとDAOExceptionが発生する。")
		void test_12() {
			// setup
			String card = "12055448";
			String password = "user01";
			// execute & verify
			try {
				sut.insert(card, password);
			} catch (Exception e) {
				assertThat(e, is(instanceOf(AuthDAOException.class)));
			}
		}
		@Test
		@DisplayName("【Test-11・異常系：利用者登録されていないユーザの登録】利用者カード番号「12050001」パスワード「user01」のユーザを登録するとDAOExceptionが発生する。")
		void test_11() {
			// setup
			String card = "12050001";
			String password = "user01";
			// execute & verify
			try {
				sut.insert(card, password);
			} catch (Exception e) {
				assertThat(e, is(instanceOf(AuthDAOException.class)));
				assertThat(e.getMessage(), is("ユーザ登録ができませんでした。"));
			}
		}
		@Test
		@DisplayName("【Test-01・正常系：利用者登録されているが未サインアップのユーザの登録】利用者カード番号「12055448」パスワード「user0001」のユーザを登録できる。")
		void test_01() throws Exception {
			/* setup */
			// テスト用パラメータの設定
			String card = "12055448";
			String password = "user0001";
			ITable expected = createExpected(XML_EXPECTED_INSERT_1);	// 期待値テーブルの取得
			/* execute */
			sut.insert(card, password);
			ITable actual = getActual();	// 実行値テーブルの取得
			/* verify */
			Assertion.assertEquals(expected, actual);
		}
	}

	/**
	 * 期待値テーブルを生成する。
	 * @return ITableインタフェースの実装クラス
	 * @throws Exception
	 */
	private ITable createExpected(String targetPath) throws Exception {
		IDataSet expectedDataset = new FlatXmlDataSetBuilder().build(new FileInputStream(targetPath));
		return this.getTable(expectedDataset);
	}

	/**
	 * 実行値テーブルを取得する。
	 * @return ITableインタフェースの実装クラス
	 * @throws Exception
	 */
	private ITable getActual() throws Exception {
		IDataSet actualDataset = dbunitConnection.createDataSet();
		return this.getTable(actualDataset);
	}

	/**
	 * データセットからITableインタフェースの実装クラスを取得する。
	 * @param dataset データセット
	 * @return Itableのインタフェースの実装クラス
	 * @throws Exception
	 */
	private ITable getTable(IDataSet dataset) throws Exception {
		ITable table = dataset.getTable(TARGET_TABLE);
		table = DefaultColumnFilter.excludedColumnsTable(table, EXCLUDED_COLUMNS);
		table = new SortedTable(table, SORT_COLUMNS);
		return table;
	}

	@Nested
	@DisplayName("AuthDAO#findMemberメソッドのテストクラス")
	class findMember {
		@Test
		@DisplayName("【Test04】利用者カード番号「12056692」パスワード「xxxxx」で検索するとnullが返される。")
		void test_04() throws Exception {
			// setup
			String userid = "12056692";
			String password = "xxxxx";
			// execute & verify
			assertThat(sut.findMember(userid, password), is(nullValue()));
		}
		@Test
		@DisplayName("【Test03】利用者カード番号「12056692」パスワード「QnLmqZ9b」で検索した結果のインスタンスのパスワードはnullである。")
		void test_03() throws Exception {
			// setup
			String userid = "12056692";
			String password = "QnLmqZ9b";
			String expected = "Auth [card=12056692, password=null]";
			// execute & verify
			assertThat(sut.findMember(userid, password).toString(), is(expected));
		}
		@Test
		@DisplayName("【Test02】利用者カード番号「12056692」パスワード「QnLmqZ9b」で検索した結果のインスタンスの利用者カード番号は「12056692」である。")
		void test_02() throws Exception {
			// setup
			String userid = "12056692";
			String password = "QnLmqZ9b";
			String expected = "12056692";
			// execute & verify
			assertThat(sut.findMember(userid, password).getCard(), is(expected));
		}
		@Test
		@DisplayName("【Test01】利用者カード番号「12056692」パスワード「QnLmqZ9b」で検索するとインスタンスを取得できる。")
		void test_01() throws Exception {
			// setup
			String userid = "12056692";
			String password = "QnLmqZ9b";
			// execute & verify
			assertThat(sut.findMember(userid, password), is(instanceOf(AuthBean.class)));
		}
	}

	@Nested
	@DisplayName("AuthDAOコンストラクタのテストクラス")
	class Constructor {
		@Test
		@DisplayName("【Test01】AuthDAOクラスをインスタンス化できる。")
		void test_01() {
			// execute & verify
			assertThat(new AuthDAO(DbUtil.getConnection()), is(instanceOf(AuthDAO.class)));
		}
	}

}
