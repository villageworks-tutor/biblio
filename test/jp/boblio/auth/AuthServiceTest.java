package jp.boblio.auth;


import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;

class AuthServiceTest {

	/** テスト対象クラス */
	private AuthService sut;

	/** テスト補助クラス */
	private MockHttpServletRequest request;

	@BeforeEach
	void setUp() throws Exception {
		request = new MockHttpServletRequest();
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Nested
	@DisplayName("AuthService#executeメソッドのテストクラス")
	class execute {
		@Nested
		@DisplayName("signupの場合")
		class signup {
			@BeforeEach
			void setUp() {
				request.setParameter("action", "signup");
			}
			@Test
			@DisplayName("【Test-03・ユーザ登録（完了）】ユーザ登録の場合は遷移先は「auth/」である")
			void test_03() throws Exception {
				// setup
				request.setParameter("mode", "complete");
				sut = new AuthService(request);
				String expected = "auth/";
				// execute
				String actual = sut.execute();
				// verify
				assertThat(actual, is(expected));
			}
			@Test
			@DisplayName("【Test-02・ユーザ登録（確認）】ユーザ登録の場合は遷移先は「auth/signup/confirm.jsp」である")
			void test_02() throws Exception {
				// setup
				request.setParameter("mode", "confirm");
				sut = new AuthService(request);
				String expected = "auth/signup/confirm.jsp";
				// execute
				String actual = sut.execute();
				// verify
				assertThat(actual, is(expected));
			}
			@Test
			@DisplayName("【Test-01・ユーザ登録（入力）】ユーザ登録の場合は遷移先は「auth/signup/entry.jsp」である")
			void test_01() throws Exception {
				// setup
				request.setParameter("mode", "entry");
				sut = new AuthService(request);
				String expected = "auth/signup/entry.jsp";
				// execute
				String actual = sut.execute();
				// verify
				assertThat(actual, is(expected));
			}
		}

		@Nested
		@DisplayName("signoutの場合")
		class signout {
			@BeforeEach
			void setUp() {
				request.setParameter("action", "signout");
			}
			@Test
			@DisplayName("【Test】サインアウトすると遷移先は「auth/」である")
			void test() throws Exception {
				// setup
				String userId = "12056692";
				String password = "QnLmqZ9b";
				request.setParameter("userId", userId);
				request.setParameter("password", password);
				String expected = "auth/";
				sut = new AuthService(request);
				// execute
				String actual = sut.execute();
				// verify
				assertThat(actual, is(expected));
			}
		}

		@Nested
		@DisplayName("signinの場合")
		class signin {
			@BeforeEach
			void setUp() {
				request.setParameter("action", "signin");
			}
			@Test
			@DisplayName("【Test-02・ユーザ認証（失敗）】ID「12056692」パスワード「xxxxxxxx」を入力すると遷移先は「auth/」である")
			void test_02() throws Exception {
				// setup
				String userId = "12056692";
				String password = "xxxxxxxx";
				request.setParameter("userId", userId);
				request.setParameter("password", password);
				String expected = "auth/";
				sut = new AuthService(request);
				// execute
				String actual = sut.execute();
				// verify
				assertThat(actual, is(expected));
			}
			@Test
			@DisplayName("【Test-01・ユーザ認証（成功）】ID「12056692」パスワード「QnLmqZ9b」を入力すると遷移先は「auth/top.jsp」である")
			void test_01() throws Exception {
				// setup
				String userId = "12056692";
				String password = "QnLmqZ9b";
				request.setParameter("userId", userId);
				request.setParameter("password", password);
				String expected = "auth/top.jsp";
				sut = new AuthService(request);
				// execute
				String actual = sut.execute();
				// verify
				assertThat(actual, is(expected));
			}
		}
	}

	@Nested
	@DisplayName("AuthService#validateメソッドのテストクラス")
	class validate {
		@Nested
		@DisplayName("サインアップの場合")
		class signup {
			@BeforeEach
			void setUp() {
				request.setParameter("action", "signup");
				request.setParameter("mode", "entry");
			}
			@Test
			@DisplayName("【Test-32・複合エラー（文字種検査と文字数検査）】ID「12+/??92」パスワード「QnLmqZ9」を入力するとエラーメッセージは「ユーザIDは半角数字で入力してください。」「パスワードは8文字以上で入力してください。」である")
			void test_32() {
				// setup
				String userId = "12+/??92";
				String password = "Qn&&qZ9";
				request.setParameter("userId", userId);
				request.setParameter("password", password);
				sut = new AuthService(request);
				List<String> expected = new ArrayList<String>();
				expected.add("ユーザIDは半角数字で入力してください。");
				expected.add("パスワードは8文字以上で入力してください。");
				// execute
				sut.validate();
				List<String> actual = sut.getErrors();
				// verify
				for (int i = 0; i < actual.size(); i++) {
					assertThat(actual.get(i), is(expected.get(i)));
				}
			}
			@Test
			@DisplayName("【Test-31・複合エラー（必須入力検査と文字種検査）】ID「」パスワード「Qn&&qZ9b」を入力するとエラーメッセージは「ユーザIDを入力してください。」「パスワードは半角英数字で入力してください。」である")
			void test_31() {
				// setup
				String userId = "";
				String password = "Qn&&qZ9*";
				request.setParameter("userId", userId);
				request.setParameter("password", password);
				sut = new AuthService(request);
				List<String> expected = new ArrayList<String>();
				expected.add("ユーザIDを入力してください。");
				expected.add("パスワードは半角英数字で入力してください。");
				// execute
				sut.validate();
				List<String> actual = sut.getErrors();
				// verify
				for (int i = 0; i < actual.size(); i++) {
					assertThat(actual.get(i), is(expected.get(i)));
				}
			}
			@Test
			@DisplayName("【Test-24・文字種】ID「1205669」パスワード「QnLmqZ9*」を入力するとエラーメッセージは「パスワードは半角英数字で入力してください。」である")
			void test_24() {
				// setup
				String userId = "12056692";
				String password = "QnLmqZ9*";
				request.setParameter("userId", userId);
				request.setParameter("password", password);
				sut = new AuthService(request);
				List<String> expected = new ArrayList<String>();
				expected.add("パスワードは半角英数字で入力してください。");
				// execute
				sut.validate();
				List<String> actual = sut.getErrors();
				// verify
				for (int i = 0; i < actual.size(); i++) {
					assertThat(actual.get(i), is(expected.get(i)));
				}
			}
			@Test
			@DisplayName("【Test-23・文字数検査（文字数超過）】ID「1205669」パスワード「QnLmqZ9b123」を入力すると妥当性検査をパスできる")
			void test_23() {
				// setup
				String userId = "12056692";
				String password = "QnLmqZ9b123";
				request.setParameter("userId", userId);
				request.setParameter("password", password);
				sut = new AuthService(request);
				// execute ＆ verify
				assertThat(sut.validate(), is(true));
			}
			@Test
			@DisplayName("【Test-22・文字数検査（文字数不足）】ID「1205669」パスワード「QnLmqZ9」を入力するとエラーメッセージは「パスワードは8文字以上で入力してください。」である")
			void test_22() {
				// setup
				String userId = "12056692";
				String password = "QnLmqZ9";
				request.setParameter("userId", userId);
				request.setParameter("password", password);
				sut = new AuthService(request);
				List<String> expected = new ArrayList<String>();
				expected.add("パスワードは8文字以上で入力してください。");
				// execute
				sut.validate();
				List<String> actual = sut.getErrors();
				// verify
				for (int i = 0; i < actual.size(); i++) {
					assertThat(actual.get(i), is(expected.get(i)));
				}
			}
			@Test
			@DisplayName("【Test-21・必須入力検査】ID「12056692」パスワード「」を入力するとエラーメッセージは「パスワードを入力してください。」である")
			void test_21() {
				// setup
				String userId = "12056692";
				String password = "";
				request.setParameter("userId", userId);
				request.setParameter("password", password);
				sut = new AuthService(request);
				List<String> expected = new ArrayList<String>();
				expected.add("パスワードを入力してください。");
				// execute
				sut.validate();
				List<String> actual = sut.getErrors();
				// verify
				for (int i = 0; i < actual.size(); i++) {
					assertThat(actual.get(i), is(expected.get(i)));
				}
			}
			@Test
			@DisplayName("【Test-14・文字種数検査】ID「12056A%&」パスワード「QnLmqZ9b」を入力するとエラーメッセージは「ユーザIDは半角数字で入力してください。」である")
			void test_14() {
				// setup
				String userId = "12056A%&";
				String password = "QnLmqZ9b";
				request.setParameter("userId", userId);
				request.setParameter("password", password);
				sut = new AuthService(request);
				List<String> expected = new ArrayList<String>();
				expected.add("ユーザIDは半角数字で入力してください。");
				// execute
				sut.validate();
				List<String> actual = sut.getErrors();
				// verify
				for (int i = 0; i < actual.size(); i++) {
					assertThat(actual.get(i), is(expected.get(i)));
				}
			}
			@Test
			@DisplayName("【Test-13・文字数検査（文字数超過）】ID「1205669200」パスワード「QnLmqZ9b」を入力するとエラーメッセージは「ユーザIDは8文字で入力してください。」である")
			void test_13() {
				// setup
				String userId = "120566900";
				String password = "QnLmqZ9b";
				request.setParameter("userId", userId);
				request.setParameter("password", password);
				sut = new AuthService(request);
				List<String> expected = new ArrayList<String>();
				expected.add("ユーザIDは8文字で入力してください。");
				// execute
				sut.validate();
				List<String> actual = sut.getErrors();
				// verify
				for (int i = 0; i < actual.size(); i++) {
					assertThat(actual.get(i), is(expected.get(i)));
				}
			}
			@Test
			@DisplayName("【Test-12・文字数検査（文字数不足）】ID「1205669」パスワード「QnLmqZ9b」を入力するとエラーメッセージは「ユーザIDは8文字で入力してください。」である")
			void test_12() {
				// setup
				String userId = "1205669";
				String password = "QnLmqZ9b";
				request.setParameter("userId", userId);
				request.setParameter("password", password);
				sut = new AuthService(request);
				List<String> expected = new ArrayList<String>();
				expected.add("ユーザIDは8文字で入力してください。");
				// execute
				sut.validate();
				List<String> actual = sut.getErrors();
				// verify
				for (int i = 0; i < actual.size(); i++) {
					assertThat(actual.get(i), is(expected.get(i)));
				}
			}
			@Test
			@DisplayName("【Test-11・必須入力検査】ID「」パスワード「QnLmqZ9b」を入力するとエラーメッセージは「ユーザIDを入力してください。」である")
			void test_11() {
				// setup
				String userId = "";
				String password = "QnLmqZ9b";
				request.setParameter("userId", userId);
				request.setParameter("password", password);
				sut = new AuthService(request);
				List<String> expected = new ArrayList<String>();
				expected.add("ユーザIDを入力してください。");
				// execute
				sut.validate();
				List<String> actual = sut.getErrors();
				// verify
				for (int i = 0; i < actual.size(); i++) {
					assertThat(actual.get(i), is(expected.get(i)));
				}
			}
			@Test
			@DisplayName("【Test-01】ID「12056692」パスワード「QnLmqZ9b」を入力すると妥当性検査をパスできる")
			void test_01() {
				// setup
				String userId = "12056692";
				String password = "QnLmqZ9b";
				request.setParameter("userId", userId);
				request.setParameter("password", password);
				sut = new AuthService(request);
				// execute & verify
				assertThat(sut.validate(), is(true));
			}
		}

		@Nested
		@DisplayName("サインアウトの場合")
		class signout {
			@BeforeEach
			void setUp() {
				request.setParameter("action", "signout");
			}
			@Test
			@DisplayName("妥当性検査はパスする")
			void test() {
				// setup
				sut = new AuthService(request);
				// execute & verify
				assertThat(sut.validate(), is(true));
			}
		}

		@Nested
		@DisplayName("サインインの場合")
		class signin {
			@BeforeEach
			void setUp() {
				request.setParameter("action", "signin");
			}
			@Test
			@DisplayName("【Test-05】ID「12056692」パスワード「」を入力すると妥当性検査をパスできない")
			void test_05() {
				String userId = "12056692";
				String password = "";
				request.setParameter("userId", userId);
				request.setParameter("password", password);
				sut = new AuthService(request);
				// execute & verify
				assertThat(sut.validate(), is(false));
			}
			@Test
			@DisplayName("【Test-04】ID「」パスワード「QnLmqZ9b」を入力すると妥当性検査をパスできない")
			void test_04() {
				// setup
				String userId = "";
				String password = "QnLmqZ9b";
				request.setParameter("userId", userId);
				request.setParameter("password", password);
				sut = new AuthService(request);
				// execute & verify
				assertThat(sut.validate(), is(false));
			}
			@Test
			@DisplayName("【Test-03】ID「12050000」パスワード「QnLmqZ9b」を入力すると妥当性検査をパスできる")
			void test_03() {
				// setup
				String userId = "12050000";
				String password = "QnLmqZ9b";
				request.setParameter("userId", userId);
				request.setParameter("password", password);
				sut = new AuthService(request);
				// execute & verify
				assertThat(sut.validate(), is(true));
			}
			@Test
			@DisplayName("【Test-02】ID「12056692」パスワード「QnLmqZ9b222」を入力すると妥当性検査をパスできる")
			void test_02() {
				// setup
				String userId = "12056692";
				String password = "QnLmqZ9b222";
				request.setParameter("userId", userId);
				request.setParameter("password", password);
				sut = new AuthService(request);
				// execute & verify
				assertThat(sut.validate(), is(true));
			}
			@Test
			@DisplayName("【Test-01】ID「12056692」パスワード「QnLmqZ9b」を入力すると妥当性検査をパスできる")
			void test_01() {
				// setup
				String userId = "12056692";
				String password = "QnLmqZ9b";
				request.setParameter("userId", userId);
				request.setParameter("password", password);
				sut = new AuthService(request);
				// execute & verify
				assertThat(sut.validate(), is(true));
			}
		}
	}
}
