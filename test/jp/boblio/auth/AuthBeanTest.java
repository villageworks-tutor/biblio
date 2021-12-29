package jp.boblio.auth;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AuthBeanTest {

	/** テスト対象クラス：system under test */
	AuthBean sut = null;

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	@DisplayName("利用カード番号「12056692」ユーザID「umeda」パスワード「QnLmqZ9b」でインスタンス化されたAuthクラスをシリアライズできる")
	void test_03() {
		// setup
		sut = new AuthBean("12056692", "QnLmqZ9b");
		String expected = "Auth [card=12056692, password=QnLmqZ9b]";
		// execute
		String actual = sut.toString();
		// verify
		assertThat(actual, is(expected));
	}
	@Test
	@DisplayName("ユーザID「12056692」パスワード「QnLmqZ9b」でインスタンス化されたAuthクラスをシリアライズする")
	void test_02() {
		// setup
		sut = new AuthBean("12056692", "QnLmqZ9b");
		String expected = "Auth [card=12056692, password=QnLmqZ9b]";
		// execute
		String actual = sut.toString();
		// verify
		assertThat(actual, is(expected));
	}

	@Test
	@DisplayName("ユーザID「12056692」パスワード「QnLmqZ9b」でAuthクラスをインスタンス化できる")
	void test_01() {
		// setup
		// sut = new Auth("umeda", "QnLmqZ9b");
		// execute & verify
		assertThat(new AuthBean("12056692", "QnLmqZ9b"), is(instanceOf(AuthBean.class)));
	}
}


