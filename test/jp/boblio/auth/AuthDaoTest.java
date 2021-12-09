package jp.boblio.auth;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import jp.boblio.common.DbUtil;

class AuthDaoTest {

	/** テスト対象クラス：system under test */
	private AuthDAO sut;

	@BeforeEach
	void setUp() throws Exception {
		// テスト対象クラスのインスタンス化
		sut = new AuthDAO(DbUtil.getConnection());
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Nested
	@DisplayName("AuthDAO#findMemberメソッドのテストクラス")
	class findMember {

		@Test
		@DisplayName("【Test04】ユーザID「umeda」パスワード「xxxxx」で検索するとnullが返される")
		void test_04() {
			// setup
			String userid = "umeda";
			String password = "xxxxx";
			// execute & verify
			assertThat(sut.findMember(userid, password), is(nullValue()));
		}

		@Test
		@DisplayName("【Test03】ユーザID「umeda」パスワード「QnLmqZ9b」で検索した結果のインスタンスのパスワードはnullである")
		void test_03() {
			// setup
			String userid = "umeda";
			String password = "QnLmqZ9b";
			String expected = "Auth [card=12056692, userId=umeda, password=null]";
			// execute & verify
			assertThat(sut.findMember(userid, password).toString(), is(expected));
		}

		@Test
		@DisplayName("【Test02】ユーザID「umeda」パスワード「QnLmqZ9b」で検索した結果のインスタンスの利用者カード番号は「12056692」である")
		void test_02() {
			// setup
			String userid = "umeda";
			String password = "QnLmqZ9b";
			String expected = "12056692";
			// execute & verify
			assertThat(sut.findMember(userid, password).getCard(), is(expected));
		}

		@Test
		@DisplayName("【Test01】ユーザID「umeda」パスワード「QnLmqZ9b」で検索するとインスタンスを取得できる")
		void test_01() {
			// setup
			String userid = "umeda";
			String password = "QnLmqZ9b";
			// execute & verify
			assertThat(sut.findMember(userid, password), is(instanceOf(AuthBean.class)));
		}
	}

	@Nested
	@DisplayName("AuthDAOコンストラクタのテストクラス")
	class Constructor {
		@Test
		@DisplayName("【Test01】AuthDAOクラスをインスタンス化できる")
		void test_01() {
			// execute & verify
			assertThat(new AuthDAO(DbUtil.getConnection()), is(instanceOf(AuthDAO.class)));
		}
	}

}
