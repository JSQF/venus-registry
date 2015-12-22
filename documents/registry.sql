DROP TABLE IF EXISTS t_venus_server;
CREATE TABLE IF NOT EXISTS t_venus_server (
  id          INT PRIMARY KEY AUTO_INCREMENT,
  hostname    VARCHAR(36) NOT NULL COMMENT 'Venus服务主机ip或主机名',
  port        INT         NOT NULL COMMENT 'Venus服务端口',
  create_time DATETIME,
  update_time TIMESTAMP       DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) COMMENT  = 'Venus服务器表';
CREATE UNIQUE INDEX idx_hostname_port ON t_venus_server(hostname, port);

DROP TABLE IF EXISTS t_venus_service;
CREATE TABLE IF NOT EXISTS t_venus_service (
  id          INT PRIMARY KEY AUTO_INCREMENT,
  name        VARCHAR(64)  NOT NULL COMMENT 'Venus服务名称',
  description VARCHAR(256) NOT NULL COMMENT 'Venus服务描述',
  create_time DATETIME,
  update_time TIMESTAMP       DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) COMMENT 'Venus服务表';
CREATE UNIQUE INDEX idx_name ON t_venus_service(name);

DROP TABLE IF EXISTS t_venus_service_mapping;
CREATE TABLE IF NOT EXISTS t_venus_service_mapping (
  id          INT PRIMARY KEY AUTO_INCREMENT,
  server_id   INT        NOT NULL COMMENT 'Venus服务器id',
  service_id  INT        NOT NULL COMMENT 'Venus服务id',
  version     VARCHAR(64) COMMENT 'Venus服务版本',
  active      TINYINT(1) NOT NULL COMMENT '是否激活服务',
  sync        TINYINT(1) NOT NULL COMMENT '是否注册中心与服务端同步',
  create_time DATETIME,
  update_time TIMESTAMP       DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) COMMENT 'Venus服务映射表';
