/*
 Navicat Premium Data Transfer

 Source Server         : MySQl
 Source Server Type    : MySQL
 Source Server Version : 50726
 Source Host           : localhost:3306
 Source Schema         : mall_db

 Target Server Type    : MySQL
 Target Server Version : 50726
 File Encoding         : 65001

 Date: 07/04/2020 09:53:00
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admins
-- ----------------------------
DROP TABLE IF EXISTS `admins`;
CREATE TABLE `admins`  (
  `id` int(4) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `nickname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `pwd` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `level` int(11) NOT NULL DEFAULT 2,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 115 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of admins
-- ----------------------------
INSERT INTO `admins` VALUES (100, 'root@qq.com', 'root', 'Admin@100', 1);
INSERT INTO `admins` VALUES (112, '22222@qq.com', 'fdsafds', 'Admin@11111', 2);
INSERT INTO `admins` VALUES (114, '44444@qq.com', 'fdsfa', 'fdsafd@H1111', 2);

-- ----------------------------
-- Table structure for comments
-- ----------------------------
DROP TABLE IF EXISTS `comments`;
CREATE TABLE `comments`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `score` double(5, 1) NOT NULL,
  `specName` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `comment` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `time` datetime(0) NOT NULL,
  `userId` int(11) NOT NULL,
  `goodId` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of comments
-- ----------------------------
INSERT INTO `comments` VALUES (9, 80.0, 'S', '穿着很好看，出街很帅', '2020-04-05 22:27:22', 1000006, 18);
INSERT INTO `comments` VALUES (10, 100.0, 'S', '店家推荐的尺码很合身，我很喜欢', '2020-04-06 19:13:43', 1000008, 18);
INSERT INTO `comments` VALUES (11, 80.0, 'M', '很合身', '2020-04-06 22:57:05', 1000006, 21);

-- ----------------------------
-- Table structure for goods
-- ----------------------------
DROP TABLE IF EXISTS `goods`;
CREATE TABLE `goods`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `img` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `desc` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `typeId` int(11) NOT NULL,
  `price` double(11, 1) NOT NULL,
  `stockNum` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `typeid`(`typeId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 48 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of goods
-- ----------------------------
INSERT INTO `goods` VALUES (18, 'http://localhost:8084/upload/6/4/1/3/3/5/5/d/fb74ab54-1518-419f-98f8-4e29d3e1a892-TB2ppWAoiFTMKJjSZFAXXckJpXa_!!579299945.jpg', '毛呢九分裤春季新款宽松九分阔腿裤', '浮笙文艺小清新复古百搭显瘦毛呢九分裤', 101, 199.0, 2096);
INSERT INTO `goods` VALUES (19, 'http://localhost:8084/upload/d/1/6/9/7/0/c/a/6f25418f-1936-4bd4-bd72-dd9b30b8e41d-TB2D6AWoTnI8KJjSszbXXb4KFXa_!!101114732.jpg', '2019春秋牛仔外套女', '港风宽松破洞牛仔衣潮学生韩版bf原宿长袖夹克', 101, 198.0, 1500);
INSERT INTO `goods` VALUES (20, 'http://localhost:8084/upload/8/d/0/1/c/9/3/1/af662282-944e-4d01-a2b3-1b4f71db5aae-TB2XfH9arwrBKNjSZPcXXXpapXa_!!46216288.jpg', '高腰百褶雪纺短裙女', '夏款小清新柠檬黄碎花显瘦', 101, 299.0, 1500);
INSERT INTO `goods` VALUES (21, 'http://localhost:8084/upload/e/6/5/d/d/6/e/8/f828abab-0849-45e9-aca6-bc5bb4bc547f-TB2MMAGwB8lpuFjy0FnXXcZyXXa_!!719529510.jpg', '女装针织衫2019新款', '收藏宝贝 (450人气) 秋冬季清新小外套短款百搭日系毛衣开衫', 101, 298.0, 1500);
INSERT INTO `goods` VALUES (22, 'http://localhost:8084/upload/3/f/e/f/3/6/1/e/283d29d7-400f-455f-bfad-03b43e8b5a2d-TB258yYdLxNTKJjy0FjXXX6yVXa_!!2098699084.jpg', '桔熊秋季搭大衣的裙子格子厚款立领连衣裙女', '人气宝贝', 101, 199.0, 1500);
INSERT INTO `goods` VALUES (23, 'http://localhost:8084/upload/d/9/4/5/c/4/8/9/658def93-6aa4-4c57-8a9d-99c97dc45107-O1CN011bPo4TROfVj3qpX_!!2037163458.jpg', '春季男士正反两面穿运动外套夹克', '纯色碎花潮牌双面拉链衫', 102, 288.0, 1600);
INSERT INTO `goods` VALUES (24, 'http://localhost:8084/upload/6/4/7/5/4/7/4/6/c16f61b7-589c-4e50-b2eb-e4a359456ef2-O1CN01nlw8nk1JNpMSeJTv9_!!2711381017.jpg', '自制潮牌拼色外套男', '秋冬季学生帅气翻领夹克宽松百搭', 102, 198.0, 1600);
INSERT INTO `goods` VALUES (25, 'http://localhost:8084/upload/5/9/c/c/a/8/e/3639e2cb-0b21-4dfa-9f0e-a59c69dbeb55-O1CN01NKoOsK1JNpM2vAAfj_!!2711381017.jpg', '蓝色拼色工装外套男', '青年韩版帅气机能小伙休闲夹克潮', 102, 199.0, 2000);
INSERT INTO `goods` VALUES (26, 'http://localhost:8084/upload/1/f/4/8/4/a/c/b/38b861bc-3dc4-4eb6-9cfb-aadd3465f029-O1CN010qLZiu1JNpOEul1zd_!!0-item_pic.jpg', '绿白连帽外套男', '帅气2020新款学生青年春秋潮流夹克', 102, 155.0, 2000);
INSERT INTO `goods` VALUES (27, 'http://localhost:8084/upload/d/6/7/7/0/a/e/9/cb89fb8a-afd6-497b-a6d1-802ad14d6e80-TB29CaNfgRkpuFjy1zeXXc.6FXa_!!54816048.jpg', '享声数字DSD母带播放器网络耳放解码器转盘机', '不要往耳朵里倒垃圾，嘻嘻嘻', 103, 3998.0, 778);
INSERT INTO `goods` VALUES (28, 'http://localhost:8084/upload/b/6/6/7/d/8/f/2/06b72363-71c5-4aea-88dd-91a98ca5e94b-O1CN01FEoZ4J1UEd9HDcAQM_!!10922486.jpg', '索尼 WH-1000XM3 头戴式无线降噪蓝牙耳机1000XM3', '不要往耳朵里倒垃圾，嘻嘻嘻', 103, 1799.0, 444);
INSERT INTO `goods` VALUES (29, 'http://localhost:8084/upload/d/3/9/8/3/e/0/c/0e944273-93ca-4efc-a35b-579e5def7e62-O1CN014zRHkA1VdC4LpVRWD_!!127182675.jpg', '华为freebuds3无线蓝牙耳机双耳骨声纹主动降噪运动跑步半入耳式', '不要往耳朵里倒垃圾，嘻嘻嘻', 103, 798.0, 4671);
INSERT INTO `goods` VALUES (30, 'http://localhost:8084/upload/6/1/e/f/7/e/2/c/03f38813-03d8-4f1e-9bde-bde1b2eadf5d-O1CN01h1thZr1YKMaTBVL9N_!!3622223040.jpg', 'Bose 700蓝牙耳机头戴式无线降噪耳机电脑手机消噪耳麦耳机', '不要往耳朵里倒垃圾，嘻嘻嘻', 103, 2088.0, 761);
INSERT INTO `goods` VALUES (31, 'http://localhost:8084/upload/8/b/a/b/b/a/1/d/6f307cc0-8aa9-426f-b988-707c25acc812-TB2VkjnlbsTMeJjSszhXXcGCFXa_!!3446633117.jpg', '百味烧 辣多多辣条', '大刀肉麻辣辣片休闲零食小吃素食素肉特产面筋', 104, 45.0, 4244);
INSERT INTO `goods` VALUES (32, 'http://localhost:8084/upload/d/3/8/1/7/8/2/1/59b28dc2-1848-4bbd-89ac-1df570293812-7ecc4571fcd8bbdd.jpg', '三只松鼠每日坚果开心果', '进口加州坚果炒货每日坚果零食地方特产100g/袋', 104, 45.0, 7489);
INSERT INTO `goods` VALUES (33, 'http://localhost:8084/upload/9/3/d/f/a/7/3/6/30482e7f-dbb9-4408-a1f3-dbf98c2aec4d-105904440ad1aa25.jpg', '三只松鼠每日坚果炭烧腰果', '坚果炒货零食特产 90g/袋', 104, 30.0, 7489);
INSERT INTO `goods` VALUES (34, 'http://localhost:8084/upload/7/d/a/4/f/9/b/c/06a5c09f-0f20-4f22-81bf-6123dd786afa-15f5a852eec13585.jpg', '禧漫屋 床 简约现代全实木床', '大人成人床松木北欧双人床公主床主卧家具小孩单人床 深蓝色+送床垫【颜色拍下备注】 1200mm*2000mm', 105, 880.0, 1148);
INSERT INTO `goods` VALUES (35, 'http://localhost:8084/upload/e/0/b/6/7/2/4/0/9049c8a0-6ce4-4a3c-a0da-5c4f2fe9de05-a06e82068b5ed8a8.jpg', '全实木沙发组合中式仿古', '品木客 红木家具 非洲花梨（学名:刺猬紫檀）', 105, 12800.0, 114);
INSERT INTO `goods` VALUES (36, 'http://localhost:8084/upload/3/f/b/9/c/b/1/c/419195a6-11b7-43a1-8a14-e96c04887ba4-8d5a0f5ea748f4e2.jpg', '尚仁现代板式衣柜', '整体两门推拉门实木质移门衣橱定制卧室组合家具简约 暖白色', 105, 1095.0, 887);
INSERT INTO `goods` VALUES (37, 'http://localhost:8084/upload/8/e/4/9/2/2/3/d/551993f9-fbd4-465c-b586-775d9edf329b-4f686399fa9027df.jpg', '亿家达电脑桌台式桌', '家用简约经济型书桌学生卧室学习写字桌简易桌子', 105, 180.0, 558);
INSERT INTO `goods` VALUES (38, 'http://localhost:8084/upload/c/1/6/6/1/a/4/a/a5c50744-c69d-42da-8f2a-4e16214e2fa6-596e136d4a144cae.jpg', 'Java核心技术 卷I', '基础知识（原书第11版） [Core Java Volume Ⅰ-Fundamentals(Eleventh Edition)]', 124, 98.3, 434);
INSERT INTO `goods` VALUES (39, 'http://localhost:8084/upload/4/b/4/8/1/7/c/5/97d8232c-1b39-4bf8-bb5b-34a1d12483ef-e6b493934a41a0e6.jpg', 'Java异步编程实战', '淘宝资深Java工程师撰写，从语言、框架等角度深入讲解异步编程原理和方法，周志明、李运华等推荐。', 124, 65.2, 432);
INSERT INTO `goods` VALUES (40, 'http://localhost:8084/upload/8/c/3/5/e/3/f/1b8502c3-afeb-4901-bc69-086c74ac3d8b-56f25c46Nc3b3c506.jpg', '数据结构与算法分析：Java语言描述（原书第3版）', '国际著名计算机教育专家Weiss数据结构与算法Java描述经典教材新版，把算法分析与高效率的Java程序的开发有机地结合起来，深入分析每种算法。', 124, 50.6, 853);
INSERT INTO `goods` VALUES (43, 'http://localhost:8084/upload/8/1/d/5/8/2/9/9/cbadcbe2-2bd1-408f-a80b-55169c832a1c-2cddbf8b81dbcf85.jpg', 'Java设计模式及实践', '精选Java实用设计模式，帮你有效解决开发应用程序过程中的常见问题，轻松应对各种规模项目的扩展和维护', 124, 65.5, 444);
INSERT INTO `goods` VALUES (44, 'http://localhost:8084/upload/e/6/c/e/d/3/8/0/f6783230-895a-4545-a955-bd1ebd381559-a5b85d0b9944f6c7.jpg', '深入理解Spring MVC源代码：从原理分析到实战应用', 'Java架构师必备，Web开发人员案头手册！精通SpringMVC源代码，深入理解框架开发编程思想和设计模式，从框架使用者到开发者，带你实现认知升级和华丽转身', 124, 106.9, 433);
INSERT INTO `goods` VALUES (45, 'http://localhost:8084/upload/1/7/f/c/a/0/0/f/fc69b6cc-91ef-4e7b-abc9-2b2be06570ce-389aafdea5a4480f.jpg', '三珍斋 熟食卤味 酱鸭400g 中华老字号', '【爆款低至满199减100】春意盎然，食力守护，宅家正能量，美味零食速食享不停', 104, 22.9, 2002);
INSERT INTO `goods` VALUES (47, 'http://localhost:8084/upload/6/6/a/9/4/e/b/0/e0092c80-b4a6-49f8-b969-912c438341a2-389aafdea5a4480f.jpg', '测试', '幅度萨芬', 126, 1.0, 2333);

-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) NOT NULL,
  `goodId` int(11) NOT NULL,
  `goodNum` int(11) NOT NULL,
  `spec` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `amount` double(10, 1) NOT NULL,
  `stateId` int(11) NOT NULL,
  `state` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `deleteFlag` int(11) NOT NULL DEFAULT 0,
  `createTime` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `hasComment` tinyint(1) NOT NULL DEFAULT 0,
  `updateTime` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 100000044 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of orders
-- ----------------------------
INSERT INTO `orders` VALUES (100000033, 1000006, 18, 500, 'M', 99500.0, 1, '未发货', 1, '2020-04-05 21:52:27', 0, '2020-04-05 21:52:57');
INSERT INTO `orders` VALUES (100000035, 1000006, 18, 1, 'S', 199.0, 0, '未付款', 1, '2020-04-05 21:54:09', 0, '2020-04-05 21:54:25');
INSERT INTO `orders` VALUES (100000036, 1000006, 18, 5, 'S', 995.0, 3, '已到货', 0, '2020-04-05 21:54:49', 1, '2020-04-05 22:27:22');
INSERT INTO `orders` VALUES (100000039, 1000008, 18, 4, 'S', 796.0, 3, '已到货', 0, '2020-04-06 19:09:43', 1, '2020-04-06 19:13:43');
INSERT INTO `orders` VALUES (100000040, 1000008, 18, 1, 'L', 199.0, 0, '未付款', 1, '2020-04-06 19:12:40', 0, '2020-04-06 19:12:49');
INSERT INTO `orders` VALUES (100000041, 1000006, 19, 1, 'L', 198.0, 1, '未发货', 0, '2020-04-06 19:32:34', 0, '2020-04-06 22:51:53');
INSERT INTO `orders` VALUES (100000042, 1000006, 18, 4, 'S', 796.0, 1, '未发货', 0, '2020-04-06 22:55:02', 0, '2020-04-06 22:55:49');

-- ----------------------------
-- Table structure for questions
-- ----------------------------
DROP TABLE IF EXISTS `questions`;
CREATE TABLE `questions`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) NOT NULL,
  `goodId` int(11) NOT NULL,
  `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `state` int(11) NOT NULL DEFAULT 0,
  `createTime` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `replyContent` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `replyTime` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of questions
-- ----------------------------
INSERT INTO `questions` VALUES (11, 1000006, 18, '现在穿会冷么', 1, '2020-04-05 21:52:23', '这款毛衣穿着冬暖夏凉的呢，可以放心买的呢，亲', '2020-04-05 22:25:03');
INSERT INTO `questions` VALUES (12, 1000008, 18, '170，100，穿多大码的好啊', 1, '2020-04-06 19:10:44', '建议您选购L好的呢，亲', '2020-04-06 19:14:14');
INSERT INTO `questions` VALUES (13, 1000006, 19, '好看么', 1, '2020-04-06 22:52:46', '很好看', '2020-04-06 22:52:55');

-- ----------------------------
-- Table structure for specs
-- ----------------------------
DROP TABLE IF EXISTS `specs`;
CREATE TABLE `specs`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `specName` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `stockNum` int(4) NOT NULL,
  `unitPrice` double(10, 1) NOT NULL,
  `goodId` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `goodId`(`goodId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 128 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of specs
-- ----------------------------
INSERT INTO `specs` VALUES (62, '陶瓷白', 3214, 798.0, 29);
INSERT INTO `specs` VALUES (63, '碳晶黑', 1123, 798.0, 29);
INSERT INTO `specs` VALUES (64, '密语红', 334, 798.0, 29);
INSERT INTO `specs` VALUES (65, '黑色', 314, 2088.0, 30);
INSERT INTO `specs` VALUES (66, '白色', 113, 2098.0, 30);
INSERT INTO `specs` VALUES (67, '白金色', 334, 2208.0, 30);
INSERT INTO `specs` VALUES (69, '香辣', 4244, 45.0, 31);
INSERT INTO `specs` VALUES (70, '100g/每袋', 4244, 45.0, 32);
INSERT INTO `specs` VALUES (71, '200g/每袋', 3245, 86.0, 32);
INSERT INTO `specs` VALUES (72, '90g/每袋', 4244, 30.0, 33);
INSERT INTO `specs` VALUES (73, '180g/每袋', 3245, 58.0, 33);
INSERT INTO `specs` VALUES (74, '深蓝色', 444, 880.0, 34);
INSERT INTO `specs` VALUES (75, '纯白色', 345, 880.0, 34);
INSERT INTO `specs` VALUES (76, '粉白色', 325, 880.0, 34);
INSERT INTO `specs` VALUES (77, '白金色', 34, 1580.0, 34);
INSERT INTO `specs` VALUES (78, '财源滚滚六件套', 43, 25000.0, 35);
INSERT INTO `specs` VALUES (79, '财源滚滚十件套', 45, 35000.0, 35);
INSERT INTO `specs` VALUES (80, '单个双人位', 25, 12800.0, 35);
INSERT INTO `specs` VALUES (81, '1.5*0.55*2', 432, 1095.0, 36);
INSERT INTO `specs` VALUES (82, '1.6*0.55*2', 455, 1420.0, 36);
INSERT INTO `specs` VALUES (83, '一门四抽带键盘板', 324, 180.0, 37);
INSERT INTO `specs` VALUES (84, '一门五抽', 234, 220.0, 37);
INSERT INTO `specs` VALUES (85, '卷一', 429, 98.3, 38);
INSERT INTO `specs` VALUES (86, '一件', 432, 65.2, 39);
INSERT INTO `specs` VALUES (87, '中文版', 432, 50.6, 40);
INSERT INTO `specs` VALUES (88, '英文版', 421, 63.4, 40);
INSERT INTO `specs` VALUES (89, 'java设计模式及实践', 444, 65.5, 43);
INSERT INTO `specs` VALUES (90, '深入理解spring mvc源代码', 433, 106.9, 44);
INSERT INTO `specs` VALUES (91, 'S', 587, 199.0, 18);
INSERT INTO `specs` VALUES (92, 'M', 0, 199.0, 18);
INSERT INTO `specs` VALUES (93, 'L', 500, 199.0, 18);
INSERT INTO `specs` VALUES (94, 'S', 501, 198.0, 19);
INSERT INTO `specs` VALUES (95, 'M', 499, 198.0, 19);
INSERT INTO `specs` VALUES (96, 'L', 499, 198.0, 19);
INSERT INTO `specs` VALUES (97, 'S', 500, 299.0, 20);
INSERT INTO `specs` VALUES (98, 'M', 500, 299.0, 20);
INSERT INTO `specs` VALUES (99, 'L', 500, 299.0, 20);
INSERT INTO `specs` VALUES (100, 'S', 499, 298.0, 21);
INSERT INTO `specs` VALUES (101, 'M', 500, 298.0, 21);
INSERT INTO `specs` VALUES (102, 'L', 500, 298.0, 21);
INSERT INTO `specs` VALUES (103, 'S', 500, 199.0, 22);
INSERT INTO `specs` VALUES (104, 'L', 500, 199.0, 22);
INSERT INTO `specs` VALUES (105, 'M', 500, 199.0, 22);
INSERT INTO `specs` VALUES (106, 'M', 400, 288.0, 23);
INSERT INTO `specs` VALUES (107, 'L', 400, 288.0, 23);
INSERT INTO `specs` VALUES (108, 'XL', 400, 288.0, 23);
INSERT INTO `specs` VALUES (109, 'XXL', 400, 288.0, 23);
INSERT INTO `specs` VALUES (110, 'M', 400, 198.0, 24);
INSERT INTO `specs` VALUES (111, 'L', 400, 198.0, 24);
INSERT INTO `specs` VALUES (112, 'XL', 400, 198.0, 24);
INSERT INTO `specs` VALUES (113, 'XXL', 400, 198.0, 24);
INSERT INTO `specs` VALUES (114, 'M', 500, 199.0, 25);
INSERT INTO `specs` VALUES (115, 'L', 500, 199.0, 25);
INSERT INTO `specs` VALUES (116, 'XL', 500, 199.0, 25);
INSERT INTO `specs` VALUES (117, 'XXL', 500, 199.0, 25);
INSERT INTO `specs` VALUES (118, 'M', 500, 155.0, 26);
INSERT INTO `specs` VALUES (119, 'L', 500, 155.0, 26);
INSERT INTO `specs` VALUES (120, 'XL', 500, 155.0, 26);
INSERT INTO `specs` VALUES (121, 'XXL', 500, 155.0, 26);
INSERT INTO `specs` VALUES (122, '酱鸭400g', 996, 22.9, 45);
INSERT INTO `specs` VALUES (123, '酱鸭800g', 1000, 39.0, 45);
INSERT INTO `specs` VALUES (124, '测试', 100, 1.0, 46);
INSERT INTO `specs` VALUES (125, '测试2', 100, 100.0, 46);
INSERT INTO `specs` VALUES (126, '幅度萨芬', 2222, 1.0, 47);
INSERT INTO `specs` VALUES (127, '幅度萨芬2', 111, 222.0, 47);

-- ----------------------------
-- Table structure for states
-- ----------------------------
DROP TABLE IF EXISTS `states`;
CREATE TABLE `states`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of states
-- ----------------------------
INSERT INTO `states` VALUES (0, '未付款');
INSERT INTO `states` VALUES (1, '未发货');
INSERT INTO `states` VALUES (2, '已发货');
INSERT INTO `states` VALUES (3, '已完成');

-- ----------------------------
-- Table structure for test
-- ----------------------------
DROP TABLE IF EXISTS `test`;
CREATE TABLE `test`  (
  `id` int(11) NOT NULL,
  `price` double(10, 2) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of test
-- ----------------------------

-- ----------------------------
-- Table structure for types
-- ----------------------------
DROP TABLE IF EXISTS `types`;
CREATE TABLE `types`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 127 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of types
-- ----------------------------
INSERT INTO `types` VALUES (101, '女装');
INSERT INTO `types` VALUES (102, '男装');
INSERT INTO `types` VALUES (103, '电子产品');
INSERT INTO `types` VALUES (104, '食品');
INSERT INTO `types` VALUES (105, '家具家居');
INSERT INTO `types` VALUES (107, '童装');
INSERT INTO `types` VALUES (124, '书籍');
INSERT INTO `types` VALUES (126, '测试');

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`  (
  `id` int(4) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `nickname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `pwd` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `recipient` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `img` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1000011 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES (1000006, '111111@qq.com', 'user111111', 'User@111111', '王道一号男神', '王道训练营ww', '18811111111', 'http://localhost:8084/upload/5/2/6/a/1/3/4/4/574b001f-9d30-49fe-8da8-9865a6b8dc7d-TB258yYdLxNTKJjy0FjXXX6yVXa_!!2098699084.jpg');
INSERT INTO `users` VALUES (1000007, '222222@qq.com', 'user222222', 'User@222222', '王道二号男神', '王道训练因', '18822222222', 'http://localhost:8084/upload/4/d/a/e/2/3/2/f/27eaf6d9-8f34-42d8-8caf-909e6b13ead4-O1CN011bPo4TROfVj3qpX_!!2037163458.jpg');
INSERT INTO `users` VALUES (1000008, '333333@qq.com', 'user333333', 'User@333333', '王道三号男神', '王道训练营', '18833333333', 'http://localhost:8084/upload/7/b/5/2/1/7/7/6/482e717f-e569-4f4b-907f-90402d009dc4-TB2MMAGwB8lpuFjy0FnXXcZyXXa_!!719529510.jpg');
INSERT INTO `users` VALUES (1000009, '444444@qq.com', 'user444444', 'User@444444', '王道四号男神', '王道训练应', '18844444444', 'http://localhost:8084/upload/9/0/f/7/0/9/b/9/62c1ca14-270c-430f-afa3-de3447efdd04-389aafdea5a4480f.jpg');

SET FOREIGN_KEY_CHECKS = 1;
