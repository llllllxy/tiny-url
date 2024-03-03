/*
 Navicat Premium Data Transfer

 Source Server         : 127.0.0.1
 Source Server Type    : MySQL
 Source Server Version : 50733
 Source Host           : localhost:3306
 Source Schema         : tiny-url

 Target Server Type    : MySQL
 Target Server Version : 50733
 File Encoding         : 65001

 Date: 03/03/2024 16:44:12
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_mail_config
-- ----------------------------
DROP TABLE IF EXISTS `t_mail_config`;
CREATE TABLE `t_mail_config`  (
  `id` bigint(20) NOT NULL COMMENT '自增主键',
  `smtp_address` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'smtp服务器地址',
  `smtp_port` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'smtp端口',
  `email_account` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '邮箱账号',
  `email_password` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '邮箱密码',
  `receive_email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '收件邮箱地址(多个用逗号隔开)',
  `del_flag` tinyint(4) NOT NULL DEFAULT 0 COMMENT '删除标志（0--未删除1--已删除）',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注描述信息',
  `created_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `created_by` bigint(20) NOT NULL COMMENT '创建人-对应t_user.id',
  `updated_by` bigint(20) NULL DEFAULT NULL COMMENT '更新人-对应t_user.id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统邮箱配置表' ROW_FORMAT = Dynamic;

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
  `ip_whitelist` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'IP访问白名单',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_tenant
-- ----------------------------
INSERT INTO `t_tenant` VALUES (2212121, 'zhangsan', 'e960be68749241c3e3562094239e28e2b4482e9d28c65413bbe2f9bd0419cdb8', '张三', '17862718963', '17862718963@email.com', 0, 0, '2023-12-05 16:33:46', 'fc9793001f8845aa940eb3223d72730b', 'N2JlNDU1MTI1Mzk2NDkxZjkyM2RiODkwNmRmNmViNGNlOTYwMjdmNTgxNGQ0YjBiOGYxYjkyZGE2NjE2ZDhjOA', '2099-12-31 23:59:59', 0, '172.20.88.154,172.20.88.155');

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
INSERT INTO `t_url_access_log` VALUES (1763100440500936705, 2212121, 1763100383005417473, '2024-02-29 15:14:36', '192.168.5.81', '本地局域网', '{\"operatingSystem\":\"WINDOWS_10\",\"browser\":\"FIREFOX11\",\"id\":35130716,\"browserVersion\":{\"version\":\"119.0\",\"majorVersion\":\"119\",\"minorVersion\":\"0\"}}', '2024-02-29 15:14:36');
INSERT INTO `t_url_access_log` VALUES (1763100524001140738, 2212121, 1763100383005417473, '2024-03-01 15:14:56', '192.168.5.82', '本地局域网', '{\"operatingSystem\":\"WINDOWS_10\",\"browser\":\"FIREFOX11\",\"id\":35130716,\"browserVersion\":{\"version\":\"119.0\",\"majorVersion\":\"119\",\"minorVersion\":\"0\"}}', '2024-02-29 15:14:56');
INSERT INTO `t_url_access_log` VALUES (1763453297234317313, 2212121, 1763453231446659074, '2024-03-01 14:36:44', '192.168.5.81', '本地局域网', '{\"operatingSystem\":\"WINDOWS_10\",\"browser\":\"FIREFOX11\",\"id\":35130716,\"browserVersion\":{\"version\":\"119.0\",\"majorVersion\":\"119\",\"minorVersion\":\"0\"}}', '2024-03-01 14:36:44');
INSERT INTO `t_url_access_log` VALUES (1763453329895362562, 2212121, 1763453231446659074, '2024-03-01 14:36:52', '192.168.5.83', '本地局域网', '{\"operatingSystem\":\"WINDOWS_10\",\"browser\":\"FIREFOX11\",\"id\":35130716,\"browserVersion\":{\"version\":\"119.0\",\"majorVersion\":\"119\",\"minorVersion\":\"0\"}}', '2024-03-01 14:36:51');
INSERT INTO `t_url_access_log` VALUES (1763453349214326786, 2212121, 1763453231446659074, '2024-03-01 14:36:56', '192.168.5.80', '本地局域网', '{\"operatingSystem\":\"WINDOWS_10\",\"browser\":\"FIREFOX11\",\"id\":35130716,\"browserVersion\":{\"version\":\"119.0\",\"majorVersion\":\"119\",\"minorVersion\":\"0\"}}', '2024-03-01 14:36:56');
INSERT INTO `t_url_access_log` VALUES (1763475915912060929, 2212121, 1763475902263795713, '2024-03-01 16:06:37', '192.168.5.81', '本地局域网', '{\"operatingSystem\":\"WINDOWS_10\",\"browser\":\"CHROME12\",\"id\":35131153,\"browserVersion\":{\"version\":\"122.0.0.0\",\"majorVersion\":\"122\",\"minorVersion\":\"0\"}}', '2024-03-01 16:06:36');
INSERT INTO `t_url_access_log` VALUES (1763475944441716738, 2212121, 1763475902263795713, '2024-03-01 16:06:44', '192.168.5.81', '本地局域网', '{\"operatingSystem\":\"WINDOWS_10\",\"browser\":\"CHROME12\",\"id\":35131153,\"browserVersion\":{\"version\":\"122.0.0.0\",\"majorVersion\":\"122\",\"minorVersion\":\"0\"}}', '2024-03-01 16:06:43');
INSERT INTO `t_url_access_log` VALUES (1763475951307792385, 2212121, 1763475902263795713, '2024-03-01 16:06:45', '192.168.5.81', '本地局域网', '{\"operatingSystem\":\"WINDOWS_10\",\"browser\":\"CHROME12\",\"id\":35131153,\"browserVersion\":{\"version\":\"122.0.0.0\",\"majorVersion\":\"122\",\"minorVersion\":\"0\"}}', '2024-03-01 16:06:45');
INSERT INTO `t_url_access_log` VALUES (1763475972732297218, 2212121, 1763475902263795713, '2024-03-01 16:06:50', '192.168.5.81', '本地局域网', '{\"operatingSystem\":\"WINDOWS_10\",\"browser\":\"CHROME12\",\"id\":35131153,\"browserVersion\":{\"version\":\"122.0.0.0\",\"majorVersion\":\"122\",\"minorVersion\":\"0\"}}', '2024-03-01 16:06:50');
INSERT INTO `t_url_access_log` VALUES (1763475994580426753, 2212121, 1763475902263795713, '2024-03-01 16:06:56', '192.168.5.81', '本地局域网', '{\"operatingSystem\":\"WINDOWS_10\",\"browser\":\"FIREFOX11\",\"id\":35130716,\"browserVersion\":{\"version\":\"119.0\",\"majorVersion\":\"119\",\"minorVersion\":\"0\"}}', '2024-03-01 16:06:55');
INSERT INTO `t_url_access_log` VALUES (1763476002243420162, 2212121, 1763475902263795713, '2024-03-01 16:06:57', '192.168.5.81', '本地局域网', '{\"operatingSystem\":\"WINDOWS_10\",\"browser\":\"FIREFOX11\",\"id\":35130716,\"browserVersion\":{\"version\":\"119.0\",\"majorVersion\":\"119\",\"minorVersion\":\"0\"}}', '2024-03-01 16:06:57');
INSERT INTO `t_url_access_log` VALUES (1763476008954306562, 2212121, 1763475902263795713, '2024-03-01 16:06:59', '192.168.5.81', '本地局域网', '{\"operatingSystem\":\"WINDOWS_10\",\"browser\":\"FIREFOX11\",\"id\":35130716,\"browserVersion\":{\"version\":\"119.0\",\"majorVersion\":\"119\",\"minorVersion\":\"0\"}}', '2024-03-01 16:06:59');
INSERT INTO `t_url_access_log` VALUES (1763476014796972033, 2212121, 1763475902263795713, '2024-03-01 16:07:00', '192.168.5.81', '本地局域网', '{\"operatingSystem\":\"WINDOWS_10\",\"browser\":\"FIREFOX11\",\"id\":35130716,\"browserVersion\":{\"version\":\"119.0\",\"majorVersion\":\"119\",\"minorVersion\":\"0\"}}', '2024-03-01 16:07:00');
INSERT INTO `t_url_access_log` VALUES (1763476021017124866, 2212121, 1763475902263795713, '2024-03-01 16:07:02', '192.168.5.81', '本地局域网', '{\"operatingSystem\":\"WINDOWS_10\",\"browser\":\"FIREFOX11\",\"id\":35130716,\"browserVersion\":{\"version\":\"119.0\",\"majorVersion\":\"119\",\"minorVersion\":\"0\"}}', '2024-03-01 16:07:01');

-- ----------------------------
-- Table structure for t_url_map
-- ----------------------------
DROP TABLE IF EXISTS `t_url_map`;
CREATE TABLE `t_url_map`  (
  `id` bigint(20) NOT NULL COMMENT '主键id',
  `tenant_id` bigint(20) NOT NULL COMMENT '租户id',
  `surl` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '短链接',
  `lurl` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '原始链接',
  `visits` int(11) NOT NULL DEFAULT 0 COMMENT '访问次数',
  `expire_time` datetime(0) NOT NULL COMMENT '到期时间',
  `status` tinyint(4) NOT NULL COMMENT '状态标志（0--启用1--禁用）',
  `del_flag` tinyint(4) NOT NULL COMMENT '删除标志（0--未删除1--已删除）',
  `created_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `t_ur_map_surl_unique`(`surl`) USING BTREE COMMENT '唯一索引，surl不允许重复'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_url_map
-- ----------------------------
INSERT INTO `t_url_map` VALUES (1729067620457848832, 1, '14G65O', 'https://wooadmin.cn/index/index.html', 1, '2023-11-29 14:17:40', 0, 0, '2023-11-28 14:10:01', '2023-11-29 15:02:56', NULL);
INSERT INTO `t_url_map` VALUES (1729746319198965762, 1, '4f9Njg', 'https://juejin.cn/post/7306192146768183311?utm_source=gold_browser_extension#heading-10', 0, '2023-11-29 14:17:40', 0, 0, '2023-11-29 14:17:14', '2023-11-29 15:02:56', NULL);
INSERT INTO `t_url_map` VALUES (1729746426162106370, 1, 'D99Dd', 'https://juejin.cn/post/7306192146768183311?utm_source=gold_browser_extension#heading-10', 0, '2023-11-29 14:17:40', 0, 0, '2023-11-29 14:17:39', '2023-11-29 15:02:56', NULL);
INSERT INTO `t_url_map` VALUES (1729746430146695170, 1, '12sg1b', 'https://juejin.cn/post/7306192146768183311?utm_source=gold_browser_extension#heading-10', 0, '2023-11-29 14:17:40', 0, 0, '2023-11-29 14:17:40', '2023-11-29 15:02:56', NULL);
INSERT INTO `t_url_map` VALUES (1729746433955123201, 1, '1MeCC1', 'https://juejin.cn/post/7306192146768183311?utm_source=gold_browser_extension#heading-10', 0, '2023-11-29 14:17:40', 0, 0, '2023-11-29 14:17:41', '2023-11-29 15:02:56', NULL);
INSERT INTO `t_url_map` VALUES (1729746438795350018, 1, '4fj3xp', 'https://juejin.cn/post/7306192146768183311?utm_source=gold_browser_extension#heading-10', 0, '2023-11-29 14:17:40', 0, 0, '2023-11-29 14:17:42', '2023-11-29 15:02:56', NULL);
INSERT INTO `t_url_map` VALUES (1729747797158445058, 1, '3D56Hj', 'https://juejin.cn/post/7306192146768183311?utm_source=gold_browser_extension#heading-10', 0, '2023-11-29 14:17:40', 0, 0, '2023-11-29 14:23:06', '2023-11-29 15:02:56', NULL);
INSERT INTO `t_url_map` VALUES (1729747800874598401, 1, '2t8O3k', 'https://juejin.cn/post/7306192146768183311?utm_source=gold_browser_extension#heading-10', 0, '2023-11-29 14:17:40', 0, 0, '2023-11-29 14:23:07', '2023-11-29 15:02:56', NULL);
INSERT INTO `t_url_map` VALUES (1729747803378597890, 1, '3AxWMS', 'https://juejin.cn/post/7306192146768183311?utm_source=gold_browser_extension#heading-10', 0, '2023-11-29 14:17:40', 0, 0, '2023-11-29 14:23:08', '2023-11-29 15:02:56', NULL);
INSERT INTO `t_url_map` VALUES (1729747806729846786, 1, '2jsGI7', 'https://juejin.cn/post/7306192146768183311?utm_source=gold_browser_extension#heading-10', 0, '2023-11-29 14:17:40', 0, 0, '2023-11-29 14:23:08', '2023-11-29 15:02:56', NULL);
INSERT INTO `t_url_map` VALUES (1729747810248867841, 1, 'pZcUy', 'https://juejin.cn/post/7306192146768183311?utm_source=gold_browser_extension#heading-10', 0, '2023-11-29 14:17:40', 0, 0, '2023-11-29 14:23:09', '2023-11-29 15:02:56', NULL);
INSERT INTO `t_url_map` VALUES (1731533845312081922, 1, '3hIzMP', 'https://github.com/zjcscut/octopus/issues', 0, '2023-12-11 12:40:14', 0, 0, '2023-12-04 12:40:13', '2023-12-04 12:40:13', '门户平台生成');
INSERT INTO `t_url_map` VALUES (1732689039072706561, 1, '1FrBfF', 'https://blog.csdn.net/shenju2011/article/details/90748409', 0, '2023-12-14 17:10:33', 0, 0, '2023-12-07 17:10:33', '2023-12-07 17:10:33', '门户平台生成');
INSERT INTO `t_url_map` VALUES (1763100383005417473, 2212121, 'D8yAY', 'https://blog.csdn.net/sinat_32501475/article/details/106994575#commons-pool2%20%E6%A0%B8%E5%BF%83%E6%8E%A5%E5%8F%A3', 2, '2123-02-28 15:14:23', 0, 0, '2024-02-29 15:14:22', '2024-03-01 14:49:13', '门户平台生成');
INSERT INTO `t_url_map` VALUES (1763453231446659074, 2212121, '25UQUy', 'https://tongyi.aliyun.com/qianwen/', 3, '2024-03-08 14:36:28', 0, 0, '2024-03-01 14:36:28', '2024-03-01 14:49:10', '门户平台生成');
INSERT INTO `t_url_map` VALUES (1763475902263795713, 2212121, 'Z3zpo', 'https://www.qianxin.com/ctp/index.html', 9, '2123-03-01 16:06:34', 0, 0, '2024-03-01 16:06:33', '2024-03-03 16:43:16', '门户平台生成');
INSERT INTO `t_url_map` VALUES (1764197675944923138, 2212121, 'A2q4z', 'https://top.baidu.com/board?platform=pc&sa=pcindex_entry', 0, '2024-03-31 16:34:11', 0, 1, '2024-03-03 15:54:37', '2024-03-03 16:42:43', '租户生成');

SET FOREIGN_KEY_CHECKS = 1;
