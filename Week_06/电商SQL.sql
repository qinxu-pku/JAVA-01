--用户表
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT NULL COMMENT '用户名',
  `password` varchar(100) DEFAULT NULL COMMENT '密码',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `lastsigntime` datetime DEFAULT NULL COMMENT '最后登录时间',
  `is_effective` bit(1) DEFAULT b'0' COMMENT '是否有效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';

--商品类别
CREATE TABLE `good_class` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL COMMENT '商品类别名称',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `updatetime` datetime DEFAULT NULL COMMENT '修改时间',
  `is_effective` bit(1) DEFAULT b'0' COMMENT '是否有效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品类别';

--商品表
CREATE TABLE `goods` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL COMMENT '商品名称',
  `price` decimal(10,2) DEFAULT NULL COMMENT '价格',
  `goodImg` varchar(255) DEFAULT NULL COMMENT '商品图片',
  `classId` bigint(20) DEFAULT NULL COMMENT '商品类别',
  `number` int(11) DEFAULT NULL COMMENT '数量',
  `desc` text COMMENT '描述',
  `is_effective` bit(1) DEFAULT b'0' COMMENT '是否有效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品表';

--订单表
CREATE TABLE `order` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `userId` bigint(20) DEFAULT NULL COMMENT '用户id',
  `order_no` varchar(50) DEFAULT NULL COMMENT '订单号',
  `total_price` decimal(10,2) DEFAULT NULL COMMENT '总价',
  `order_status` int(10) DEFAULT '1' COMMENT '订单状态；0,取消;1,未支付;2,已支付3,已发发货;4,已收货;5退款退货;6,退货完成',
  `delivery_id` varchar(255) DEFAULT NULL COMMENT '配送id',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `paymenttime` datetime DEFAULT NULL COMMENT '支付时间',
  `is_effective` bit(1) DEFAULT b'0' COMMENT '是否有效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单表';

--订单-商品关联表
CREATE TABLE `sub_ouder` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `order_id` bigint(20) DEFAULT NULL COMMENT '订单Id',
  `goods_id` bigint(20) DEFAULT NULL COMMENT '商品Id',
  `goods_price` decimal(10,2) DEFAULT NULL COMMENT '商品价格',
  `goods_num` int(10) DEFAULT NULL COMMENT '商品数量',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `is_effective` bit(1) DEFAULT b'0' COMMENT '是否有效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--配送表 
CREATE TABLE `delivery` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `express_no` varchar(255) DEFAULT NULL COMMENT '快递编号',
  `express_name` varchar(255) DEFAULT NULL COMMENT '快递名称',
  `consignee_address` varchar(255) DEFAULT NULL COMMENT '收货人地址',
  `consignee_mobile` varchar(255) DEFAULT NULL COMMENT '收货人手机号',
  `consignee_name` varchar(255) DEFAULT NULL COMMENT '收货人姓名',
  `consignee_name` varchar(255) DEFAULT NULL COMMENT '收货人姓名',
  'logistics_status' varchar(255) DEFAULT NULL COMMENT '快递状态',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `paymenttime` datetime DEFAULT NULL COMMENT '支付时间',
  `is_effective` bit(1) DEFAULT b'0' COMMENT '是否有效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='配送表';




