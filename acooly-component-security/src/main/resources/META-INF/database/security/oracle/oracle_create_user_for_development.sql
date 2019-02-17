sqlplus sys/123456@ORCL as sysdba 
--创建表空间
create tablespace framework datafile 'D:\oraclexe\app\oracle\oradata\XE\framework.dbf' size 100M autoextend on next 50M maxsize unlimited;

--创建用户
create user framework identified by framework default tablespace users;

--用户基本权限
grant connect,resource to framework;
exit
