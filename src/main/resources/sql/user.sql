CREATE TABLE User (
  id       INT(11) NOT NULL AUTO_INCREMENT,
  username VARCHAR(255),
  password VARCHAR(255)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COLLATE utf8_bin;

INSERT INTO db_activiti.User (username, password) VALUES ('zhangsan', '123');
INSERT INTO db_activiti.User (username, password) VALUES ('lishi', '321');

CREATE TABLE LeaveTask (
  id        INT(11) NOT NULL AUTO_INCREMENT,
  instaceId VARCHAR(255),
  username  VARCHAR(255),
  days      VARCHAR(255),
  content   VARCHAR(255)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COLLATE utf8_bin;