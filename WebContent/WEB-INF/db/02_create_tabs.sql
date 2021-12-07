-- �e�[�u���̏������i�S�폜�j
DROP TABLE IF EXISTS category       CASCADE;
DROP TABLE IF EXISTS priviledge     CASCADE;
DROP TABLE IF EXISTS publisher      CASCADE;
DROP TABLE IF EXISTS member         	CASCADE;
DROP TABLE IF EXISTS book_catalogue CASCADE;
DROP TABLE IF EXISTS book_ledger    CASCADE;
DROP TABLE IF EXISTS activity_logs  CASCADE;
DROP TABLE IF EXISTS reserve        CASCADE;
DROP TABLE IF EXISTS auth           CASCADE;

/** �R�[�h�}�X�^�̐��� */
/**********************************/
/* �e�[�u����: ���ރ}�X�^ */
/**********************************/
CREATE TABLE category (
  code SMALLINT NOT NULL UNIQUE,
  name VARCHAR(20)
);
ALTER TABLE category ADD CONSTRAINT pk_category PRIMARY KEY (code);

/**********************************/
/* �e�[�u����: �����}�X�^ */
/**********************************/
CREATE TABLE priviledge (
  code SMALLINT NOT NULL UNIQUE,
  name VARCHAR(20)
);
ALTER TABLE priviledge ADD CONSTRAINT pk_priviledge PRIMARY KEY (code);

/**********************************/
/* �e�[�u����: �o�ŎЃ}�X�^ */
/**********************************/
CREATE TABLE publisher (
  code    VARCHAR(8) NOT NULL UNIQUE,
  name    VARCHAR(50) NOT NULL,
  address VARCHAR(100)
);
ALTER TABLE publisher ADD CONSTRAINT pk_publisher PRIMARY KEY (code);

/** �}�X�^�e�[�u���̐��� */
/**********************************/
/* �e�[�u����: ���p�҃}�X�^ */
/**********************************/
CREATE TABLE member (
	id					SERIAL,
	card				CHAR(8) UNIQUE,
	name				VARCHAR(10) NOT NULL,
	zipcode		 	CHAR(8),
	address		 	VARCHAR(100),
	phone			 	VARCHAR(14),
	email				VARCHAR(100),
	birthday		DATE,
	priviledge	SMALLINT,
	signup_at	 	TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	updated_at	TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	erasured_at TIMESTAMP
);
ALTER TABLE member ADD CONSTRAINT pk_member PRIMARY KEY (id);
ALTER TABLE member ADD CONSTRAINT fk_member FOREIGN KEY (priviledge) REFERENCES priviledge (code);

/**********************************/
/* �e�[�u����: �����ژ^ */
/**********************************/
CREATE TABLE book_catalogue (
	id 						SERIAL,
	isbn 					CHAR(13) UNIQUE,
	category 			SMALLINT,
	title 				VARCHAR(200),
	author 				VARCHAR(100),
	publisher_cd 	VARCHAR(8),
	published_at 	DATE,
	signup_at	 		TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	updated_at		TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	erasured_at 	TIMESTAMP
);
ALTER TABLE book_catalogue ADD CONSTRAINT pk_catalogue PRIMARY KEY (id);
ALTER TABLE book_catalogue ADD CONSTRAINT fk_catalogue_category FOREIGN KEY (category) REFERENCES category (code);
ALTER TABLE book_catalogue ADD CONSTRAINT fk_catalogue_publisher FOREIGN KEY (publisher_cd) REFERENCES publisher (code);

/**********************************/
/* �e�[�u����: �����䒠 */
/**********************************/
CREATE TABLE book_ledger (
	id 						SERIAL,
	catalogue_id 	INTEGER,
	arraival_at 	DATE,
	disposal_at 	DATE,
	description 	VARCHAR(255)
);
ALTER TABLE book_ledger ADD CONSTRAINT pk_ledger PRIMARY KEY (id);
ALTER TABLE book_ledger ADD CONSTRAINT fk_ledger FOREIGN KEY (catalogue_id) REFERENCES book_catalogue (id);

/**********************************/
/* �e�[�u����: �ݏo�䒠 */
/**********************************/
CREATE TABLE activity_logs (
	id 					SERIAL,
	uid 				INTEGER NOT NULL,
	book_id 		INTEGER,
	borrow_at 	DATE NOT NULL DEFAULT CURRENT_DATE,
	return_at 	DATE,
	due_date 		DATE,
	description VARCHAR(255)
);
ALTER TABLE activity_logs ADD CONSTRAINT pk_activity_logs PRIMARY KEY (id);
ALTER TABLE activity_logs ADD CONSTRAINT fk_activity_logs FOREIGN KEY (uid) REFERENCES member (id);

/**********************************/
/* �e�[�u����: �\��䒠 */
/**********************************/
CREATE TABLE reserve (
	id 					 SERIAL,
	uid 				 INTEGER,
	book_id 		 INTEGER,
	reserved_at  DATE,
	status 			 SMALLINT,
	descrioption VARCHAR(255) 
);

/**********************************/
/* �e�[�u����: �\��䒠 */
/**********************************/
CREATE TABLE auth (
	id 				INTEGER NOT NULL,
	card 			CHAR(8) UNIQUE NOT NULL,
	signin_id VARCHAR(8),
	password 	CHAR(64) NOT NULL,
	signup_at	 		TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	updated_at		TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	erasured_at 	TIMESTAMP
);
ALTER TABLE auth ADD CONSTRAINT fk_auth_card FOREIGN KEY (card) REFERENCES member (card);
ALTER TABLE auth ADD CONSTRAINT unique_sigin_in UNIQUE (signin_id, password);	-- ���[�UID�ƃp�X���[�h�̑g�ݍ��킹�͈�ӂł���K�v������
