DROP TABLE IF EXISTS `cms_content_type`;
CREATE TABLE `cms_content_type` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `PARENTID` bigint(20) DEFAULT NULL COMMENT '父类型ID',
  `PATH` varchar(128) DEFAULT NULL COMMENT '搜索路径',
  `CODE` varchar(64) DEFAULT NULL COMMENT '类型编码 (4位业务编码,如：1000)',
  `NAME` varchar(32) NOT NULL COMMENT '类型名称',
  `COMMENTS` varchar(128) DEFAULT NULL COMMENT '备注',
  `create_time` timestamp DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`ID`),
  KEY `FK_CONTENTTYPE_REF_CONTENTTYPE` (`PARENTID`) USING BTREE,
  CONSTRAINT `cms_content_type_ibfk_1` FOREIGN KEY (`PARENTID`) REFERENCES `cms_content_type` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb4 COMMENT='内容类型';

DROP TABLE IF EXISTS `cms_content_body`;
CREATE TABLE `cms_content_body` (
  `ID` bigint(20) NOT NULL COMMENT '主键',
  `BODY` text NOT NULL COMMENT '内容主体',
  `create_time` timestamp DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='内容主体';

DROP TABLE IF EXISTS `cms_content`;
CREATE TABLE `cms_content` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `KEYCODE` varchar(32) DEFAULT NULL,
  `body_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TYPE` bigint(20) NOT NULL COMMENT '类型',
  `TITLE` varchar(128) NOT NULL COMMENT '标题',
  `COVER` varchar(128) DEFAULT NULL COMMENT '封面',
  `LINK` varchar(128) DEFAULT NULL COMMENT '链接',
  `PUB_DATE` datetime NOT NULL COMMENT '发布时间',
  `KEYWORDS` varchar(128) DEFAULT NULL COMMENT '关键字\n            (SEO或本身简单搜索使用)',
  `SUBJECT` varchar(256) DEFAULT NULL COMMENT '主题介绍',
  `AUTHOR` varchar(16) DEFAULT NULL COMMENT '作者',
  `HITS` decimal(10,0) DEFAULT NULL COMMENT '点击数',
  `STATUS` int(11) NOT NULL DEFAULT '1' COMMENT '状态 (1:正常,2:禁用)',
  `create_time` timestamp DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `COMMENTS` varchar(128) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`ID`),
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='内容主表';

DROP TABLE IF EXISTS `cms_attachment`;
CREATE TABLE `cms_attachment` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `CONTENTID` bigint(20) NOT NULL COMMENT '内容ID',
  `FILE_PATH` varchar(128) NOT NULL COMMENT '文件路径',
  `FILE_SIZE` bigint(20) DEFAULT NULL COMMENT '文件大小',
  `COMMENTS` varchar(256) DEFAULT NULL COMMENT '备注',
  `FILE_NAME` varchar(128) DEFAULT NULL,
  `create_time` timestamp DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='内容附件';

CREATE TABLE `cms_code` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `KEYCODE` varchar(32) DEFAULT NULL,
  `STATUS` int(2) NOT NULL DEFAULT '1' COMMENT '状态 (1:正常,2:禁用)',
  `descn` varchar(512) DEFAULT NULL,
  `create_time` timestamp DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='编码';

INSERT INTO `cms_content_type` (`ID`, `PARENTID`, `PATH`, `CODE`, `NAME`, `COMMENTS`) VALUES ('2015102500', NULL, NULL, 'article', '文章', '');
INSERT INTO `cms_content_type` (`ID`, `PARENTID`, `PATH`, `CODE`, `NAME`, `COMMENTS`) VALUES ('2015102501', '2015102500', NULL, 'aboutus', '关于我们', '');
INSERT INTO `cms_content_type` (`ID`, `PARENTID`, `PATH`, `CODE`, `NAME`, `COMMENTS`) VALUES ('2015102502', '2015102500', NULL, 'contactus', '联系我们', '');
INSERT INTO `cms_content_type` (`ID`, `PARENTID`, `PATH`, `CODE`, `NAME`, `COMMENTS`) VALUES ('2015102503', '2015102500', NULL, 'faq', '常见问题', '');
INSERT INTO `cms_content_type` (`ID`, `PARENTID`, `PATH`, `CODE`, `NAME`, `COMMENTS`) VALUES ('2015102504', '2015102500', NULL, 'agreement', '服务协议', '');
INSERT INTO `cms_content_type` (`ID`, `PARENTID`, `PATH`, `CODE`, `NAME`, `COMMENTS`) VALUES ('2015102505', '2015102500', NULL, 'notice', '最新公共', '');
INSERT INTO `cms_content_type` (`ID`, `PARENTID`, `PATH`, `CODE`, `NAME`, `COMMENTS`) VALUES ('2015102506', '2015102500', NULL, 'news', '新闻动态', '');
INSERT INTO `cms_content_type` (`ID`, `PARENTID`, `PATH`, `CODE`, `NAME`, `COMMENTS`) VALUES ('2015102600', NULL, NULL, 'banner', '横幅', '');
INSERT INTO `cms_content_type` (`ID`, `PARENTID`, `PATH`, `CODE`, `NAME`, `COMMENTS`) VALUES ('2015102510', '2015102600', NULL, 'indexBanner', '首页横幅', '');
INSERT INTO `cms_content_type` (`ID`, `PARENTID`, `PATH`, `CODE`, `NAME`, `COMMENTS`) VALUES ('2015102511', '2015102600', NULL, 'partner', '合作伙伴', '');
INSERT INTO `cms_content_type` (`ID`, `PARENTID`, `PATH`, `CODE`, `NAME`, `COMMENTS`) VALUES ('2015102512', '2015102600', NULL, 'friend', '友情链接', '');

