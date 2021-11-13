/** コードマスタのデータ登録 */
-- 権限マスタのコードの登録
INSERT INTO priviledge (code, name) VALUES (0, 'システム管理者');
INSERT INTO priviledge (code, name) VALUES (1, '一般利用者');

-- 分類マスタのコードの登録
INSERT INTO category (code, name) VALUES (0, '総記');
INSERT INTO category (code, name) VALUES (1, '哲学');
INSERT INTO category (code, name) VALUES (2, '歴史');
INSERT INTO category (code, name) VALUES (3, '社会科学');
INSERT INTO category (code, name) VALUES (4, '自然科学');
INSERT INTO category (code, name) VALUES (5, '技術');
INSERT INTO category (code, name) VALUES (6, '産業');
INSERT INTO category (code, name) VALUES (7, '芸術');
INSERT INTO category (code, name) VALUES (8, '言語');
INSERT INTO category (code, name) VALUES (9, '文学');

-- 出版社マスタのコードの登録
INSERT INTO publisher (code, name) VALUES ('01', '岩波書店');

/** マスタテーブルのデータ登録 */
-- 利用者マスタの登録
INSERT INTO member (id, name, zipcode, address, phone, email, birthday, priviledge, signup_at, updated_at) 
						VALUES (1, '坂野 志穂', '220-0002', '神奈川県横浜市西区南軽井沢3-5-4南軽井沢タウン106', '046-456-7153', 'shiho983@czvme.zxp', '1998-03-22', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO member (id, name, zipcode, address, phone, email, birthday, priviledge, signup_at, updated_at) 
						VALUES (2, '稲田 邦男', '162-0855', '東京都新宿区二十騎町2-2-14', '03-4387-8302', 'kunio8745@muqv.raz', '1971-02-11', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO member (id, name, zipcode, address, phone, email, birthday, priviledge, signup_at, updated_at) 
						VALUES (3, '黒田 由子', '237-0074', '神奈川県横須賀市田浦大作町4-3-4田浦大作町ヒル413', '0466-33-5410', 'xdued-vbjglcyuuko40455@rhpibewkze.oq', '1985-05-11', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO member (id, name, zipcode, address, phone, email, birthday, priviledge, signup_at, updated_at) 
						VALUES (4, '坪田 容子', '101-0044', '東京都千代田区鍛冶町3-8パレス鍛冶町300', '03-1761-5114', 'vbgoksqayouko49720@qclaxjl.yphd.vgl', '1968-03-29', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO member (id, name, zipcode, address, phone, email, birthday, priviledge, signup_at, updated_at) 
						VALUES (5, '青木 昌嗣', '356-0024', '埼玉県ふじみ野市谷田4-2グリーン谷田208', '04-7439-0729', 'masashiaoki@jlwozb.oa.we', '1988-12-27', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO member (id, name, zipcode, address, phone, email, birthday, priviledge, signup_at, updated_at) 
						VALUES (6, '福原 明菜', '277-0062', '千葉県柏市光ケ丘団地4-20-9光ケ丘団地ハイツ109', '0438-09-9183', 'Akina_Fukuhara@wxgunhcic.nfg', '1982-10-19', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO member (id, name, zipcode, address, phone, email, birthday, priviledge, signup_at, updated_at) 
						VALUES (7, '河内 修司', '101-0029', '東京都千代田区神田相生町4-13-9ドリーム神田相生町401', '03-5183-3635', 'shuuji6062@gopnez.bj.gkg', '1977-01-16', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO member (id, name, zipcode, address, phone, email, birthday, priviledge, signup_at, updated_at) 
						VALUES (8, '岩本 結芽花', '259-1135', '神奈川県伊勢原市岡崎2-20-19岡崎の杜102', '0466-06-1644', 'yumekaiwamoto@qybcf.wy', '2001-05-10', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO member (id, name, zipcode, address, phone, email, birthday, priviledge, signup_at, updated_at) 
						VALUES (9, '若林 敏雄', '230-0042', '神奈川県横浜市鶴見区仲通2-2-16', '046-282-3240', 'wwkpbbeklwztoshio22832@loewhau.nqb.pct', '1968-06-03', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO member (id, name, zipcode, address, phone, email, birthday, priviledge, signup_at, updated_at) 
						VALUES (10, '安川 金次', '250-0313', '神奈川県足柄下郡箱根町須雲川2-5須雲川庵411', '046-295-6180', 'kinji179@klrht.qei', '1992-07-06', 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


