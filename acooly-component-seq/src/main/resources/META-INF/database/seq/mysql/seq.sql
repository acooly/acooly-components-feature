-- ----------------------------
--  Table structure for `id`
-- ----------------------------
CREATE TABLE sys_seq (
`keyName` varchar(30),
`nextId` int(11) unsigned,
PRIMARY KEY (`keyName`),
UNIQUE KEY `keyName_UNIQUE` (`keyName`))ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;


