INSERT INTO `sys_resource` (`ID`, `PARENTID`, `NAME`, `TYPE`, `SHOW_STATE`, `ORDER_TIME`, `VALUE`, `SHOW_MODE`, `ICON`, `DESCN`)
VALUES ('17042001', '2019022201', '身份证实名记录', 'URL', '0', '2015-10-01 03:10:00',
        '/manage/module/certification/certificationRecord/index.html', '1', 'fa fa-id-card-o', NULL);
INSERT INTO `sys_resource` (`ID`, `PARENTID`, `NAME`, `TYPE`, `SHOW_STATE`, `ORDER_TIME`, `VALUE`, `SHOW_MODE`, `ICON`, `DESCN`)
VALUES ('2016093013', '2019022201', '银行卡实名记录', 'URL', '0', '2016-10-01 03:10:00',
        '/manage/module/certification/bankCertificationRecord/index.html', '1', 'fa fa-credit-card',
        NULL);


INSERT INTO `sys_role_resc` (`ROLE_ID`, `RESC_ID`) VALUES ('1', '17042001');
INSERT INTO `sys_role_resc` (`ROLE_ID`, `RESC_ID`) VALUES ('1', '2016093013');

