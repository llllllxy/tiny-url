

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_tenant
-- ----------------------------
DROP TABLE IF EXISTS `t_tenant`;
CREATE TABLE `t_tenant`  (
  `id` bigint(20) NOT NULL COMMENT '业务主键',
  `tenant_account` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '租户账号',
  `tenant_password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '租户密码',
  `tenant_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '租户名称',
  `tenant_phone` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '租户电话',
  `tenant_email` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '租户邮箱',
  `status` tinyint(4) NOT NULL COMMENT '状态标志（0--启用1--禁用）',
  `del_flag` tinyint(4) NOT NULL COMMENT '删除标志（0--未删除1--已删除）',
  `created_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `access_key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'api访问ak',
  `access_key_secret` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'api访问ak密钥',
  `expire_time` datetime(0) NULL DEFAULT NULL COMMENT '到期时间',
  `check_ip_flag` tinyint(4) NULL DEFAULT 0 COMMENT '是否开启`ip`校验，默认0不开启，1开启',
  `ip_whitelist` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'IP访问白名单，以逗号隔开',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_url_access_log
-- ----------------------------
DROP TABLE IF EXISTS `t_url_access_log`;
CREATE TABLE `t_url_access_log`  (
  `id` bigint(20) NOT NULL COMMENT '主键id',
  `tenant_id` bigint(20) NOT NULL COMMENT '租户id',
  `url_id` bigint(20) NOT NULL COMMENT '短链id',
  `access_time` datetime(0) NOT NULL COMMENT '访问时间',
  `access_ip` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '访问者-IP地址',
  `access_address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '访问者-ip物理地址',
  `access_user_agent` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '访问者的user_agent',
  `created_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_url_access_log
-- ----------------------------
INSERT INTO `t_url_access_log` VALUES (1730864438539825153, 1, 1730864349100486658, '2023-12-02 16:20:15', '192.168.0.103', '本地局域网', '{\"operatingSystem\":\"WINDOWS_10\",\"browser\":\"CHROME11\",\"id\":35131152,\"browserVersion\":{\"version\":\"119.0.0.0\",\"majorVersion\":\"119\",\"minorVersion\":\"0\"}}', '2023-12-02 16:20:14');
INSERT INTO `t_url_access_log` VALUES (1730864620811694081, 1, 1730864349100486658, '2023-12-02 16:20:58', '192.168.0.103', '本地局域网', '{\"operatingSystem\":\"WINDOWS_10\",\"browser\":\"CHROME11\",\"id\":35131152,\"browserVersion\":{\"version\":\"119.0.0.0\",\"majorVersion\":\"119\",\"minorVersion\":\"0\"}}', '2023-12-02 16:20:58');
INSERT INTO `t_url_access_log` VALUES (1730879225441120257, 1, 1730879156260270081, '2023-12-02 17:19:00', '192.168.0.103', '本地局域网', '{\"operatingSystem\":\"WINDOWS_10\",\"browser\":\"CHROME11\",\"id\":35131152,\"browserVersion\":{\"version\":\"119.0.0.0\",\"majorVersion\":\"119\",\"minorVersion\":\"0\"}}', '2023-12-02 17:19:00');
INSERT INTO `t_url_access_log` VALUES (1730880035927425026, 1, 1730879971410640898, '2023-12-02 17:22:13', '192.168.0.103', '本地局域网', '{\"operatingSystem\":\"WINDOWS_10\",\"browser\":\"CHROME11\",\"id\":35131152,\"browserVersion\":{\"version\":\"119.0.0.0\",\"majorVersion\":\"119\",\"minorVersion\":\"0\"}}', '2023-12-02 17:22:13');
INSERT INTO `t_url_access_log` VALUES (1730883532060143618, 1, 1730883366452244481, '2023-12-02 17:36:07', '192.168.0.103', '本地局域网', '{\"operatingSystem\":\"WINDOWS_10\",\"browser\":\"CHROME11\",\"id\":35131152,\"browserVersion\":{\"version\":\"119.0.0.0\",\"majorVersion\":\"119\",\"minorVersion\":\"0\"}}', '2023-12-02 17:36:06');
INSERT INTO `t_url_access_log` VALUES (1731284641564155905, 1, 1731284610966708226, '2023-12-03 20:09:59', '192.168.0.104', '本地局域网', '{\"operatingSystem\":\"WINDOWS_10\",\"browser\":\"CHROME11\",\"id\":35131152,\"browserVersion\":{\"version\":\"119.0.0.0\",\"majorVersion\":\"119\",\"minorVersion\":\"0\"}}', '2023-12-03 20:09:58');

-- ----------------------------
-- Table structure for t_url_map
-- ----------------------------
DROP TABLE IF EXISTS `t_url_map`;
CREATE TABLE `t_url_map`  (
  `id` bigint(20) NOT NULL COMMENT '主键id',
  `surl` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '短链接',
  `lurl` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '原始链接',
  `visits` int(11) NOT NULL DEFAULT 0 COMMENT '访问次数',
  `expire_time` datetime(0) NOT NULL COMMENT '到期时间',
  `status` tinyint(4) NOT NULL DEFAULT 0 COMMENT '状态标志（0--启用1--禁用）',
  `del_flag` tinyint(4) NOT NULL DEFAULT 0 COMMENT '删除标志（0--未删除1--已删除）',
  `created_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `tenant_id` bigint(20) NOT NULL COMMENT '租户id',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `t_ur_map_surl_unique`(`surl`) USING BTREE COMMENT '唯一索引，surl不允许重复'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_url_map
-- ----------------------------
INSERT INTO `t_url_map` VALUES (1729067620457848832, '14G65O', 'https://wooadmin.cn/index/index.html', 1, '2023-11-29 14:17:40', 0, 0, '2023-11-28 14:10:01', '2023-11-29 15:02:56', NULL, 1);
INSERT INTO `t_url_map` VALUES (1729746319198965762, '4f9Njg', 'https://juejin.cn/post/7306192146768183311?utm_source=gold_browser_extension#heading-10', 0, '2023-11-29 14:17:40', 0, 0, '2023-11-29 14:17:14', '2023-11-29 15:02:56', NULL, 1);
INSERT INTO `t_url_map` VALUES (1729746426162106370, 'D99Dd', 'https://juejin.cn/post/7306192146768183311?utm_source=gold_browser_extension#heading-10', 0, '2023-11-29 14:17:40', 0, 0, '2023-11-29 14:17:39', '2023-11-29 15:02:56', NULL, 1);
INSERT INTO `t_url_map` VALUES (1729746430146695170, '12sg1b', 'https://juejin.cn/post/7306192146768183311?utm_source=gold_browser_extension#heading-10', 0, '2023-11-29 14:17:40', 0, 0, '2023-11-29 14:17:40', '2023-11-29 15:02:56', NULL, 1);
INSERT INTO `t_url_map` VALUES (1729746433955123201, '1MeCC1', 'https://juejin.cn/post/7306192146768183311?utm_source=gold_browser_extension#heading-10', 0, '2023-11-29 14:17:40', 0, 0, '2023-11-29 14:17:41', '2023-11-29 15:02:56', NULL, 1);
INSERT INTO `t_url_map` VALUES (1729746438795350018, '4fj3xp', 'https://juejin.cn/post/7306192146768183311?utm_source=gold_browser_extension#heading-10', 0, '2023-11-29 14:17:40', 0, 0, '2023-11-29 14:17:42', '2023-11-29 15:02:56', NULL, 1);
INSERT INTO `t_url_map` VALUES (1729747797158445058, '3D56Hj', 'https://juejin.cn/post/7306192146768183311?utm_source=gold_browser_extension#heading-10', 0, '2023-11-29 14:17:40', 0, 0, '2023-11-29 14:23:06', '2023-11-29 15:02:56', NULL, 1);
INSERT INTO `t_url_map` VALUES (1729747800874598401, '2t8O3k', 'https://juejin.cn/post/7306192146768183311?utm_source=gold_browser_extension#heading-10', 0, '2023-11-29 14:17:40', 0, 0, '2023-11-29 14:23:07', '2023-11-29 15:02:56', NULL, 1);
INSERT INTO `t_url_map` VALUES (1729747803378597890, '3AxWMS', 'https://juejin.cn/post/7306192146768183311?utm_source=gold_browser_extension#heading-10', 0, '2023-11-29 14:17:40', 0, 0, '2023-11-29 14:23:08', '2023-11-29 15:02:56', NULL, 1);
INSERT INTO `t_url_map` VALUES (1729747806729846786, '2jsGI7', 'https://juejin.cn/post/7306192146768183311?utm_source=gold_browser_extension#heading-10', 0, '2023-11-29 14:17:40', 0, 0, '2023-11-29 14:23:08', '2023-11-29 15:02:56', NULL, 1);
INSERT INTO `t_url_map` VALUES (1729747810248867841, 'pZcUy', 'https://juejin.cn/post/7306192146768183311?utm_source=gold_browser_extension#heading-10', 0, '2023-11-29 14:17:40', 0, 0, '2023-11-29 14:23:09', '2023-11-29 15:02:56', NULL, 1);
INSERT INTO `t_url_map` VALUES (1730606949789372417, '1IwwxS', 'https://blog.csdn.net/gc_2299/article/details/130956918', 0, '2023-12-08 23:17:05', 0, 0, '2023-12-01 23:17:04', '2023-12-01 23:17:04', '门户平台生成', 1);
INSERT INTO `t_url_map` VALUES (1730862135816572929, '2055Kr', 'https://blog.csdn.net/gc_2299/article/details/130956918', 0, '2023-12-09 16:11:06', 0, 0, '2023-12-02 16:11:05', '2023-12-02 16:11:05', '门户平台生成', 1);
INSERT INTO `t_url_map` VALUES (1730862509432578050, '3xxVT1', 'https://blog.csdn.net/gc_2299/article/details/130956918', 0, '2024-06-02 16:12:35', 0, 0, '2023-12-02 16:12:34', '2023-12-02 16:12:34', '门户平台生成', 1);
INSERT INTO `t_url_map` VALUES (1730863326302593025, 'utW4G', 'https://layui.dev/docs/2/class/#base', 0, '2024-03-02 16:15:49', 0, 0, '2023-12-02 16:15:49', '2023-12-02 16:15:49', '门户平台生成', 1);
INSERT INTO `t_url_map` VALUES (1730863345344733186, '3siOK9', 'https://layui.dev/docs/2/class/#base22222', 0, '2024-03-02 16:15:54', 0, 0, '2023-12-02 16:15:54', '2023-12-02 16:15:54', '门户平台生成', 1);
INSERT INTO `t_url_map` VALUES (1730863401791676417, '2zBRTZ', 'https://cdn.jsdelivr.net/npm/sm-crypto@0.3.13/dist/sm3.min.js', 0, '2024-03-02 16:16:08', 0, 0, '2023-12-02 16:16:07', '2023-12-02 16:16:07', '门户平台生成', 1);
INSERT INTO `t_url_map` VALUES (1730864349100486658, 'YxuxM', 'https://chat18.aichatos.xyz/#/chat/1699796941997', 2, '2024-06-02 16:19:53', 0, 0, '2023-12-02 16:19:53', '2023-12-02 16:20:58', '门户平台生成', 1);
INSERT INTO `t_url_map` VALUES (1730879156260270081, '1OdERI', 'https://www.ixigua.com/7305649422963212852?logTag=30271cf6eaf5deddd94a', 1, '2023-12-09 17:18:44', 0, 0, '2023-12-02 17:18:43', '2023-12-02 17:18:59', '门户平台生成', 1);
INSERT INTO `t_url_map` VALUES (1730879971410640898, '2vmVuH', 'https://juejin.cn/post/7295540969407250441?utm_source=gold_browser_extension', 1, '2024-03-02 17:21:58', 0, 0, '2023-12-02 17:21:58', '2023-12-02 17:22:13', '门户平台生成', 1);
INSERT INTO `t_url_map` VALUES (1730883221165748226, '3JVmVy', 'https://zhuanlan.zhihu.com/p/401012165?utm_id=0&eqid=d72dc7a600013eaf0000000264911368', 0, '2023-12-09 17:34:53', 0, 0, '2023-12-02 17:34:52', '2023-12-02 17:34:52', '门户平台生成', 1);
INSERT INTO `t_url_map` VALUES (1730883366452244481, '1TjdVa', 'https://blog.csdn.net/Y_wq0616/article/details/126247722', 1, '2023-12-09 17:35:27', 0, 0, '2023-12-02 17:35:27', '2023-12-02 17:36:06', '门户平台生成', 1);
INSERT INTO `t_url_map` VALUES (1730884720713314306, '42DFTo', 'https://blog.csdn.net/Y_wq0616/article/details/1262477224444', 0, '2023-12-09 17:40:50', 0, 0, '2023-12-02 17:40:50', '2023-12-02 17:40:50', '门户平台生成', 1);
INSERT INTO `t_url_map` VALUES (1730884750987800578, '3u4YDP', 'https://blog.csdn.net/Y_wq0616/article/details/1262477224444666666', 0, '2023-12-09 17:40:58', 0, 0, '2023-12-02 17:40:57', '2023-12-02 17:40:57', '门户平台生成', 1);
INSERT INTO `t_url_map` VALUES (1731284610966708226, '2hv9h5', 'https://www.ithome.com/', 1, '2024-03-03 20:09:52', 0, 0, '2023-12-03 20:09:51', '2023-12-03 20:09:58', '门户平台生成', 1);

SET FOREIGN_KEY_CHECKS = 1;
