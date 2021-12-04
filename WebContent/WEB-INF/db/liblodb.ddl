DROP TABLE reservation IF EXISTS;
DROP TABLE activity_logs IF EXISTS;
DROP TABLE book_ledger IF EXISTS;
DROP TABLE book_catalogue IF EXISTS;
DROP TABLE publisher IF EXISTS;
DROP TABLE category IF EXISTS;
DROP TABLE users IF EXISTS;
DROP TABLE privilege IF EXISTS;

/**********************************/
/* テーブル名: 権限マスタ */
/**********************************/
CREATE TABLE privilege(
		code INT NOT NULL,
		name VARCHAR(10) NOT NULL
);

/**********************************/
/* テーブル名: 利用者マスタ */
/**********************************/
CREATE TABLE users(
		id INT DEFAULT 1 NOT NULL IDENTITY,
		uid INT NOT NULL,
		name VARCHAR(10) NOT NULL,
		zipcode CHAR(8),
		address VARCHAR(100),
		phone VARCHAR(14),
		email VARCHAR(100),
		birthday DATE,
		priviledge INT NOT NULL,
		signin_at TIMESTAMP DEFAULT CURRENT_TIESTAMP NOT NULL,
		updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
		erasured_at TIMESTAMP
);

/**********************************/
/* テーブル名: 分類マスタ */
/**********************************/
CREATE TABLE category(
		code INT NOT NULL IDENTITY,
		name VARCHAR(5) NOT NULL
);

/**********************************/
/* テーブル名: 出版社マスタ */
/**********************************/
CREATE TABLE publisher(
		code INT NOT NULL,
		name VARCHAR(50) NOT NULL,
		address VARCHAR(100)
);

/**********************************/
/* テーブル名: 資料目録 */
/**********************************/
CREATE TABLE book_catalogue(
		id INT NOT NULL IDENTITY,
		isbn CHAR(13) NOT NULL,
		category INT NOT NULL,
		title VARCHAR(200) NOT NULL,
		author VARCHAR(100) NOT NULL,
		publisher INT NOT NULL,
		published_at DATE,
		register_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
		updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
		disposal_at TIMESTAMP
);

/**********************************/
/* テーブル名: 資料台帳 */
/**********************************/
CREATE TABLE book_ledger(
		id INT NOT NULL IDENTITY,
		catalogue_id INT NOT NULL,
		arrival_at DATE NOT NULL,
		disposal_at DATE,
		description VARCHAR(255)
);

/**********************************/
/* テーブル名: 貸出台帳 */
/**********************************/
CREATE TABLE activity_logs(
		id INT NOT NULL,
		uid INT NOT NULL,
		book_id INT NOT NULL,
		borrow_at DATE NOT NULL,
		COLUMN_5 INT,
		return _at DATE,
		description VARCHAR(255)
);

/**********************************/
/* テーブル名: 予約台帳 */
/**********************************/
CREATE TABLE reservation(
		id INT,
		uid INT,
		book_id INT,
		reserved_at DATE NOT NULL,
		status TINYINT(10),
		description INT
);


ALTER TABLE privilege ADD CONSTRAINT IDX_privilege_PK PRIMARY KEY (code);

ALTER TABLE users ADD CONSTRAINT IDX_users_PK PRIMARY KEY (id);
ALTER TABLE users ADD CONSTRAINT IDX_users_FK0 FOREIGN KEY (priviledge) REFERENCES privilege (code);

ALTER TABLE category ADD CONSTRAINT IDX_category_PK PRIMARY KEY (code);

ALTER TABLE publisher ADD CONSTRAINT IDX_publisher_PK PRIMARY KEY (code);

ALTER TABLE book_catalogue ADD CONSTRAINT IDX_book_catalogue_PK PRIMARY KEY (id);
ALTER TABLE book_catalogue ADD CONSTRAINT IDX_book_catalogue_FK0 FOREIGN KEY (category) REFERENCES category (code);
ALTER TABLE book_catalogue ADD CONSTRAINT IDX_book_catalogue_FK1 FOREIGN KEY (publisher) REFERENCES publisher (code);

ALTER TABLE book_ledger ADD CONSTRAINT IDX_book_ledger_PK PRIMARY KEY (id);
ALTER TABLE book_ledger ADD CONSTRAINT IDX_book_ledger_FK0 FOREIGN KEY (catalogue_id) REFERENCES book_catalogue (id);

ALTER TABLE activity_logs ADD CONSTRAINT IDX_activity_logs_PK PRIMARY KEY (id);
ALTER TABLE activity_logs ADD CONSTRAINT IDX_activity_logs_FK0 FOREIGN KEY (id) REFERENCES users (id);
ALTER TABLE activity_logs ADD CONSTRAINT IDX_activity_logs_FK1 FOREIGN KEY (book_id) REFERENCES book_ledger (id);

ALTER TABLE reservation ADD CONSTRAINT IDX_reservation_FK0 FOREIGN KEY (uid) REFERENCES users (id);
ALTER TABLE reservation ADD CONSTRAINT IDX_reservation_FK1 FOREIGN KEY (id) REFERENCES book_ledger (id);

