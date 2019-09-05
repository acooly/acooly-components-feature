/*==============================================================*/
/* DBMS name:      ORACLE Version 11g                           */
/* Created on:     2014/1/10 1:14:20                            */
/*==============================================================*/


CREATE SEQUENCE "HIBERNATE_SEQUENCE"
INCREMENT BY 1
START WITH 1
 MAXVALUE 9999999999
 MINVALUE 1
CYCLE
 CACHE 20;

/*==============================================================*/
/* Table: SYS_OLOG                                              */
/*==============================================================*/
CREATE TABLE SYS_OLOG 
(
   ID                   NUMBER(10)           NOT NULL,
   MODULE               VARCHAR2(256),
   MODULE_NAME          VARCHAR2(256),
   ACTION               VARCHAR2(32),
   ACTION_NAME          VARCHAR2(32),
   EXECUTE_MILLISECONDS NUMBER(10)           NOT NULL,
   OPERATE_TIME         DATE                 DEFAULT SYSDATE,
   OPERATE_USER         VARCHAR2(64),
   OPERATE_USER_ID      NUMBER(10),
   REQUEST_PARAMETERS   VARCHAR2(512),
   OPERATE_RESULT       NUMBER(1)            DEFAULT 1 NOT NULL,
   OPERATE_MESSAGE      VARCHAR2(512),
   CLIENT_INFORMATIONS  VARCHAR2(256),
   DESCN                VARCHAR2(256),
   CONSTRAINT PK_SYS_OLOG_ID PRIMARY KEY (ID)
);

COMMENT ON TABLE SYS_OLOG IS
'操作日志';

COMMENT ON COLUMN SYS_OLOG.ID IS
'主键';

COMMENT ON COLUMN SYS_OLOG.MODULE IS
'模块';

COMMENT ON COLUMN SYS_OLOG.MODULE_NAME IS
'模块名称';

COMMENT ON COLUMN SYS_OLOG.ACTION IS
'操作';

COMMENT ON COLUMN SYS_OLOG.ACTION_NAME IS
'操作名称';

COMMENT ON COLUMN SYS_OLOG.EXECUTE_MILLISECONDS IS
'执行时间';

COMMENT ON COLUMN SYS_OLOG.OPERATE_TIME IS
'操作时间';

COMMENT ON COLUMN SYS_OLOG.OPERATE_USER IS
'操作员';

COMMENT ON COLUMN SYS_OLOG.OPERATE_USER_ID IS
'操作员ID';

COMMENT ON COLUMN SYS_OLOG.REQUEST_PARAMETERS IS
'请求参数';

COMMENT ON COLUMN SYS_OLOG.OPERATE_RESULT IS
'操作结果{1:成功,2:失败}';

COMMENT ON COLUMN SYS_OLOG.OPERATE_MESSAGE IS
'消息';

COMMENT ON COLUMN SYS_OLOG.CLIENT_INFORMATIONS IS
'客户端信息';

COMMENT ON COLUMN SYS_OLOG.DESCN IS
'备注';

/*==============================================================*/
/* Table: SYS_PORTALLET                                         */
/*==============================================================*/
CREATE TABLE SYS_PORTALLET 
(
   ID                   NUMBER(10)           NOT NULL,
   TITLE                VARCHAR2(64)         NOT NULL,
   WIDTH                INTEGER              NOT NULL,
   HEIGHT               INTEGER              NOT NULL,
   COLLAPSIBLE          INTEGER              NOT NULL,
   HREF                 VARCHAR2(128),
   CONTENT              VARCHAR2(4000),
   CONSTRAINT PK_SYS_PORTALLET PRIMARY KEY (ID)
);

COMMENT ON TABLE SYS_PORTALLET IS
'桌面管理';

COMMENT ON COLUMN SYS_PORTALLET.ID IS
'ID';

COMMENT ON COLUMN SYS_PORTALLET.TITLE IS
'标题';

COMMENT ON COLUMN SYS_PORTALLET.WIDTH IS
'高度';

COMMENT ON COLUMN SYS_PORTALLET.HEIGHT IS
'宽度';

COMMENT ON COLUMN SYS_PORTALLET.COLLAPSIBLE IS
'可否收缩{1:true,0:false}';

COMMENT ON COLUMN SYS_PORTALLET.HREF IS
'连接地址';

COMMENT ON COLUMN SYS_PORTALLET.CONTENT IS
'内容';

/*==============================================================*/
/* Table: SYS_RESOURCE                                          */
/*==============================================================*/
CREATE TABLE SYS_RESOURCE 
(
   ID                   NUMBER(10)           NOT NULL,
   PARENTID             NUMBER(10),
   NAME                 VARCHAR2(64)         NOT NULL,
   TYPE                 VARCHAR2(64)         NOT NULL,
   SHOW_STATE           NUMBER(1)            NOT NULL,
   ORDER_TIME           DATE                 NOT NULL,
   VALUE                VARCHAR2(256),
   SHOW_MODE            NUMBER(1),
   ICON                 VARCHAR2(64),
   DESCN                VARCHAR2(256),
   CONSTRAINT PK_SYS_RESOURCE PRIMARY KEY (ID)
);

COMMENT ON TABLE SYS_RESOURCE IS
'系统资源表';

COMMENT ON COLUMN SYS_RESOURCE.ID IS
'主键';

COMMENT ON COLUMN SYS_RESOURCE.PARENTID IS
'父主键';

COMMENT ON COLUMN SYS_RESOURCE.NAME IS
'资源名称';

COMMENT ON COLUMN SYS_RESOURCE.TYPE IS
'资源类型
MENU
FUNC
URL';

COMMENT ON COLUMN SYS_RESOURCE.SHOW_STATE IS
'是否显示{0:显示,1:隐藏}';

COMMENT ON COLUMN SYS_RESOURCE.ORDER_TIME IS
'排序时间';

COMMENT ON COLUMN SYS_RESOURCE.VALUE IS
'资源值';

COMMENT ON COLUMN SYS_RESOURCE.SHOW_MODE IS
'加载方式 (1:AJAXLOAD,2:IFRAME)';

COMMENT ON COLUMN SYS_RESOURCE.ICON IS
'图标';

COMMENT ON COLUMN SYS_RESOURCE.DESCN IS
'描述';

/*==============================================================*/
/* Table: SYS_ROLE                                              */
/*==============================================================*/
CREATE TABLE SYS_ROLE 
(
   ID                   NUMBER(10)           NOT NULL,
   NAME                 VARCHAR2(64)         NOT NULL,
   DESCN                VARCHAR2(256),
   CONSTRAINT PK_SYS_ROLE PRIMARY KEY (ID)
);

/*==============================================================*/
/* Table: SYS_ROLE_RESC                                         */
/*==============================================================*/
CREATE TABLE SYS_ROLE_RESC 
(
   ROLE_ID              NUMBER(10)           NOT NULL,
   RESC_ID              NUMBER(10)           NOT NULL,
   CONSTRAINT PK_SYS_ROLE_RESC PRIMARY KEY (ROLE_ID, RESC_ID)
);

/*==============================================================*/
/* Table: SYS_USER                                              */
/*==============================================================*/
CREATE TABLE SYS_USER 
(
   ID                   NUMBER(10)           NOT NULL,
   USERNAME             VARCHAR2(16)         NOT NULL,
   REAL_NAME            VARCHAR2(32),
   PASSWORD             VARCHAR2(128)        NOT NULL,
   SALT                 VARCHAR2(32),
   USER_TYPE            INTEGER              NOT NULL,
   EMAIL                VARCHAR2(64),
   MOBILE_NO            VARCHAR2(32),
   CREATE_TIME          DATE,
   LAST_MODIFY_TIME     DATE,
   EXPIRATION_TIME      DATE,
   UNLOCK_TIME          DATE,
   STATUS               INTEGER              NOT NULL,
   DESCN                VARCHAR2(256),
   CONSTRAINT PK_SYS_USER PRIMARY KEY (ID)
);

COMMENT ON TABLE SYS_USER IS
'操作员表';

COMMENT ON COLUMN SYS_USER.USERNAME IS
'登录用户名';

COMMENT ON COLUMN SYS_USER.REAL_NAME IS
'操作员姓名';

COMMENT ON COLUMN SYS_USER.PASSWORD IS
'登录密码';

COMMENT ON COLUMN SYS_USER.SALT IS
'密码加密填充值';

COMMENT ON COLUMN SYS_USER.USER_TYPE IS
'用户类型
1:管理员
2:操作员
';

COMMENT ON COLUMN SYS_USER.EMAIL IS
'电子邮件';

COMMENT ON COLUMN SYS_USER.MOBILE_NO IS
'手机号码';

COMMENT ON COLUMN SYS_USER.CREATE_TIME IS
'创建时间';

COMMENT ON COLUMN SYS_USER.LAST_MODIFY_TIME IS
'最后修改时间';

COMMENT ON COLUMN SYS_USER.EXPIRATION_TIME IS
'账户过期时间';

COMMENT ON COLUMN SYS_USER.UNLOCK_TIME IS
'解锁时间';

COMMENT ON COLUMN SYS_USER.STATUS IS
'状态 {1:有效,2:过期,3:禁用}';

COMMENT ON COLUMN SYS_USER.DESCN IS
'描述';

/*==============================================================*/
/* Table: SYS_USER_ROLE                                         */
/*==============================================================*/
CREATE TABLE SYS_USER_ROLE 
(
   ROLE_ID              NUMBER(10)           NOT NULL,
   USER_ID              NUMBER(10)           NOT NULL,
   CONSTRAINT PK_SYS_USER_ROLE PRIMARY KEY (ROLE_ID, USER_ID)
);

ALTER TABLE SYS_ROLE_RESC
   ADD CONSTRAINT FK_SYS_ROLE_RESC_SYS_RESOURCE FOREIGN KEY (RESC_ID)
      REFERENCES SYS_RESOURCE (ID);

ALTER TABLE SYS_ROLE_RESC
   ADD CONSTRAINT FK_SYS_ROLE_RESC_SYS_ROLE FOREIGN KEY (ROLE_ID)
      REFERENCES SYS_ROLE (ID);

ALTER TABLE SYS_USER_ROLE
   ADD CONSTRAINT FK_SYS_USER_ROLE_SYS_ROLE FOREIGN KEY (ROLE_ID)
      REFERENCES SYS_ROLE (ID);

ALTER TABLE SYS_USER_ROLE
   ADD CONSTRAINT FK_SYS_USER_ROLE_SYS_USER FOREIGN KEY (USER_ID)
      REFERENCES SYS_USER (ID);

insert into SYS_RESOURCE (ID, PARENTID, NAME, TYPE, VALUE, SHOW_STATE, SHOW_MODE, ORDER_TIME, ICON, DESCN)
values (1, null, '系统管理', 'MENU', null, 0, 1, to_date('07-01-2014', 'dd-mm-yyyy'), 'icons-resource-system', null);
insert into SYS_RESOURCE (ID, PARENTID, NAME, TYPE, VALUE, SHOW_STATE, SHOW_MODE, ORDER_TIME, ICON, DESCN)
values (2, 1, '账户管理', 'URL', '/manage/system/user/index.html', 0, 1, to_date('10-01-2014 00:00:01', 'dd-mm-yyyy hh24:mi:ss'), 'icons-resource-role', null);
insert into SYS_RESOURCE (ID, PARENTID, NAME, TYPE, VALUE, SHOW_STATE, SHOW_MODE, ORDER_TIME, ICON, DESCN)
values (3, 1, '角色管理', 'URL', '/manage/system/role/index.html', 0, 1, to_date('10-01-2014 00:00:01', 'dd-mm-yyyy hh24:mi:ss'), 'icons-resource-agent', null);
insert into SYS_RESOURCE (ID, PARENTID, NAME, TYPE, VALUE, SHOW_STATE, SHOW_MODE, ORDER_TIME, ICON, DESCN)
values (4, 1, '资源菜单', 'URL', '/manage/system/resource/index.html', 0, 1, to_date('10-01-2014', 'dd-mm-yyyy'), 'icons-resource-platform', null);
insert into SYS_RESOURCE (ID, PARENTID, NAME, TYPE, VALUE, SHOW_STATE, SHOW_MODE, ORDER_TIME, ICON, DESCN)
values (6, 1, '系统监控', 'URL', '/druid/index.html', 0, 2, to_date('08-01-2014 02:37:26', 'dd-mm-yyyy hh24:mi:ss'), 'icons-resource-config', null);
insert into SYS_RESOURCE (ID, PARENTID, NAME, TYPE, VALUE, SHOW_STATE, SHOW_MODE, ORDER_TIME, ICON, DESCN)
values (7, 1, '桌面管理', 'URL', '/manage/system/portallet/index.html', 0, 1, to_date('08-01-2014 02:39:40', 'dd-mm-yyyy hh24:mi:ss'), 'icons-resource-menu', null);
commit;

insert into SYS_ROLE (ID, NAME, DESCN)
values (1, 'ROLE_SYSTEM', '系统管理角色');
commit;

insert into SYS_ROLE_RESC (ROLE_ID, RESC_ID)
values (1, 1);
insert into SYS_ROLE_RESC (ROLE_ID, RESC_ID)
values (1, 2);
insert into SYS_ROLE_RESC (ROLE_ID, RESC_ID)
values (1, 3);
insert into SYS_ROLE_RESC (ROLE_ID, RESC_ID)
values (1, 4);
insert into SYS_ROLE_RESC (ROLE_ID, RESC_ID)
values (1, 6);
insert into SYS_ROLE_RESC (ROLE_ID, RESC_ID)
values (1, 7);
commit;

insert into SYS_USER (ID, USERNAME, REAL_NAME, PASSWORD, SALT, USER_TYPE, EMAIL, MOBILE_NO, CREATE_TIME, LAST_MODIFY_TIME, EXPIRATION_TIME, UNLOCK_TIME, STATUS, DESCN)
values (1, 'admin', '超级管理员', 'ab7cc1898fe24ef738595e56759b17893f2dbcc6', 'f10a5bda42097431', 1, 'zp820705@163.com', '13896177630', to_date('07-01-2014', 'dd-mm-yyyy'), to_date('07-01-2014', 'dd-mm-yyyy'), null, null, 1, null);
commit;

insert into SYS_USER_ROLE (ROLE_ID, USER_ID)
values (1, 1);
commit;

