/**
 * �y�f�[�^�x�[�X�E�f�[�^�x�[�X���[�U�̍쐬�z
 * 		�� �f�[�^�x�[�X�ڑ���� ��
 * 			JDBC�h���C�o�Forg.postgresql.Driver
 * 			URL�Fjdbc:postgreql:bliblodb
 * 					 jdbc:postgresql://localhost:5432/liblodb	
 * 			URL for unit tests
 *				 �Fjdbc:postgreql:bliblodb
 * 					 jdbc:postgresql://localhost:5432/liblodb	
 * 			USER�Flibrarian
 * 			PASSSWORD�Fhimitu
 */

DROP DATABASE IF EXISTS liblodb;
DROP DATABASE IF EXISTS testliblodb;
DROP USER IF EXISTS librarian;
DROP USER IF EXISTS tester;

CREATE USER librarian WITH PASSWORD 'himitu';
CREATE USER tester WITH PASSWORD 'himitu';
CREATE DATABASE liblodb OWNER librarian ENCODING 'utf8';
CREATE DATABASE testliblodb OWNER tester ENCODING 'utf8';
