/**
 * �y�f�[�^�x�[�X�E�f�[�^�x�[�X���[�U�̍쐬�z
 * 		�� �f�[�^�x�[�X�ڑ���� ��
 * 			JDBC�h���C�o�Forg.postgresql.Driver
 * 			URL�Fjdbc:postgreql:bibliodb
 * 					 jdbc:postgresql://localhost:5432/biblio	
 * 			USER�Flibrarian
 * 			PASSSWORD�Fhimitu
 */

DROP DATABASE IF EXISTS liblodb;
DROP USER IF EXISTS librarian;

CREATE USER librarian WITH PASSWORD 'himitu';
CREATE DATABASE liblodb OWNER librarian ENCODING 'utf8';
