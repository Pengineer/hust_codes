--------------------------------------------------------
--  File created - 星期四-七月-22-2010   
--------------------------------------------------------
--------------------------------------------------------
--  DDL for Table T_RIGHT
--------------------------------------------------------

  CREATE TABLE "T_RIGHT" 
   (	"C_ID" VARCHAR2(40), 
	"C_NAME" VARCHAR2(40) DEFAULT NULL, 
	"C_DESCRIPTION" VARCHAR2(400) DEFAULT NULL
   ) ;
--------------------------------------------------------
--  DDL for Table T_RIGHT_URL
--------------------------------------------------------

  CREATE TABLE "T_RIGHT_URL" 
   (	"C_ID" VARCHAR2(40), 
	"C_RIGHT_ID" VARCHAR2(40), 
	"C_ACTION_URL" VARCHAR2(100) DEFAULT NULL, 
	"C_DESCRIPTION" VARCHAR2(100) DEFAULT NULL
   ) ;
--------------------------------------------------------
--  DDL for Table T_ROLE
--------------------------------------------------------

  CREATE TABLE "T_ROLE" 
   (	"C_ID" VARCHAR2(40), 
	"C_NAME" VARCHAR2(40) DEFAULT NULL, 
	"C_DESCRIPTION" VARCHAR2(400) DEFAULT NULL
   ) ;
--------------------------------------------------------
--  DDL for Table T_ROLE_RIGHT
--------------------------------------------------------

  CREATE TABLE "T_ROLE_RIGHT" 
   (	"C_ID" VARCHAR2(40), 
	"C_ROLE_ID" VARCHAR2(40), 
	"C_RIGHT_ID" VARCHAR2(40)
   ) ;
--------------------------------------------------------
--  DDL for Table T_SYSTEM_CONFIG
--------------------------------------------------------

  CREATE TABLE "T_SYSTEM_CONFIG" 
   (	"C_ID" VARCHAR2(40), 
	"C_KEY" VARCHAR2(40), 
	"C_DESCRIPTION" VARCHAR2(1000) DEFAULT NULL, 
	"C_IS_HTML" NUMBER(1,0) DEFAULT 0, 
	"C_VALUE" VARCHAR2(4000) DEFAULT NULL
   ) ;
--------------------------------------------------------
--  DDL for Table T_SYSTEM_OPTION
--------------------------------------------------------

  CREATE TABLE "T_SYSTEM_OPTION" 
   (	"C_ID" VARCHAR2(40), 
	"C_NAME" VARCHAR2(100) DEFAULT NULL, 
	"C_DESCRIPTION" VARCHAR2(200) DEFAULT NULL, 
	"C_PARENT_ID" VARCHAR2(40), 
	"C_CODE" VARCHAR2(40), 
	"C_IS_AVAILABLE" NUMBER(1,0), 
	"C_STANDARD" VARCHAR2(100), 
	"C_ABBR" VARCHAR2(100)
   ) ;
--------------------------------------------------------
--  DDL for Table T_USER
--------------------------------------------------------

  CREATE TABLE "T_USER" 
   (	"C_ID" VARCHAR2(40), 
	"C_USERNAME" VARCHAR2(40) DEFAULT NULL, 
	"C_CHINESE_NAME" VARCHAR2(40) DEFAULT NULL, 
	"C_PASSWORD" VARCHAR2(40), 
	"C_BIRTHDAY" DATE DEFAULT NULL, 
	"C_PHOTO_FILE" VARCHAR2(40) DEFAULT NULL, 
	"C_USER_STATUS" NUMBER(1,0) DEFAULT 0, 
	"C_EMAIL" VARCHAR2(40) DEFAULT NULL, 
	"C_MOBILE_PHONE" VARCHAR2(40) DEFAULT NULL, 
	"C_GENDER" VARCHAR2(40) DEFAULT NULL, 
	"C_REGISTER_TIME" DATE DEFAULT NULL, 
	"C_APPROVE_TIME" DATE DEFAULT NULL, 
	"C_EXPIRE_TIME" DATE DEFAULT NULL, 
	"C_ETHNIC" VARCHAR2(40) DEFAULT NULL, 
	"C_PW_RETRIEVE_CODE" VARCHAR2(40) DEFAULT NULL, 
	"C_IS_SUPER_USER" NUMBER(1,0) DEFAULT 0
   ) ;
--------------------------------------------------------
--  DDL for Table T_USER_ROLE
--------------------------------------------------------

  CREATE TABLE "T_USER_ROLE" 
   (	"C_ID" VARCHAR2(40), 
	"C_USER_ID" VARCHAR2(40), 
	"C_ROLE_ID" VARCHAR2(40)
   ) ;

---------------------------------------------------
--   DATA FOR TABLE T_USER_ROLE
--   FILTER = none used
---------------------------------------------------
REM INSERTING into T_USER_ROLE

---------------------------------------------------
--   END DATA FOR TABLE T_USER_ROLE
---------------------------------------------------

---------------------------------------------------
--   DATA FOR TABLE T_ROLE
--   FILTER = none used
---------------------------------------------------
REM INSERTING into T_ROLE
Insert into T_ROLE (C_ID,C_NAME,C_DESCRIPTION) values ('4028d8942953f8000129540e08eb0041','系统管理员','负责系统用户和角色权限管理');

---------------------------------------------------
--   END DATA FOR TABLE T_ROLE
---------------------------------------------------

---------------------------------------------------
--   DATA FOR TABLE T_RIGHT_URL
--   FILTER = none used
---------------------------------------------------
REM INSERTING into T_RIGHT_URL
Insert into T_RIGHT_URL (C_ID,C_RIGHT_ID,C_ACTION_URL,C_DESCRIPTION) values ('4028d8942953f800012954038c2a0031','4028d8a127f5aa3e0127f5c8974b0002','/main/configPageSize.action','配置每页显示数');
Insert into T_RIGHT_URL (C_ID,C_RIGHT_ID,C_ACTION_URL,C_DESCRIPTION) values ('4028d8942953f800012954038c2a0032','4028d8a127f5aa3e0127f5c8974b0002','/main/toConfig.action','进入系统配置页面');
Insert into T_RIGHT_URL (C_ID,C_RIGHT_ID,C_ACTION_URL,C_DESCRIPTION) values ('4028d8942953f800012954038c2a0033','4028d8a127f5aa3e0127f5c8974b0002','/main/toConfigPageSize.action','进入配置每页显示数页面');
Insert into T_RIGHT_URL (C_ID,C_RIGHT_ID,C_ACTION_URL,C_DESCRIPTION) values ('4028d894295e5b2f01295e5ffbe90016','4028d894294377370129439da11d001b','/main/page_bottom.action','加载bottom帧');
Insert into T_RIGHT_URL (C_ID,C_RIGHT_ID,C_ACTION_URL,C_DESCRIPTION) values ('4028d894295e5b2f01295e5ffbe90017','4028d894294377370129439da11d001b','/main/page_right.action','加载right帧');
Insert into T_RIGHT_URL (C_ID,C_RIGHT_ID,C_ACTION_URL,C_DESCRIPTION) values ('4028d894295e5b2f01295e5ffbe90018','4028d894294377370129439da11d001b','/main/page_left.action','加载left帧');
Insert into T_RIGHT_URL (C_ID,C_RIGHT_ID,C_ACTION_URL,C_DESCRIPTION) values ('4028d894295e5b2f01295e5ffbe90019','4028d894294377370129439da11d001b','/main/page_top.action','加载top帧');
Insert into T_RIGHT_URL (C_ID,C_RIGHT_ID,C_ACTION_URL,C_DESCRIPTION) values ('4028d894295e5b2f01295e5ffbe9001a','4028d894294377370129439da11d001b','/selfspace/loadInfo.action','转向加载个人信息');
Insert into T_RIGHT_URL (C_ID,C_RIGHT_ID,C_ACTION_URL,C_DESCRIPTION) values ('4028d894295e5b2f01295e5ffbe9001b','4028d894294377370129439da11d001b','/selfspace/modifyPassword.action','修改密码');
Insert into T_RIGHT_URL (C_ID,C_RIGHT_ID,C_ACTION_URL,C_DESCRIPTION) values ('4028d894295e5b2f01295e5ffbe9001c','4028d894294377370129439da11d001b','/selfspace/toModifyPassword.action','进入修改密码页面');
Insert into T_RIGHT_URL (C_ID,C_RIGHT_ID,C_ACTION_URL,C_DESCRIPTION) values ('4028d894295e5b2f01295e5e49760008','4028d88625da9f590125daa22ae10004','/role/addRole.action','添加角色');
Insert into T_RIGHT_URL (C_ID,C_RIGHT_ID,C_ACTION_URL,C_DESCRIPTION) values ('4028d894295e5b2f01295e5e49760009','4028d88625da9f590125daa22ae10004','/role/deleteRole.action','删除角色');
Insert into T_RIGHT_URL (C_ID,C_RIGHT_ID,C_ACTION_URL,C_DESCRIPTION) values ('4028d894295e5b2f01295e5e4985000a','4028d88625da9f590125daa22ae10004','/role/groupDeleteRoles.action','群删角色');
Insert into T_RIGHT_URL (C_ID,C_RIGHT_ID,C_ACTION_URL,C_DESCRIPTION) values ('4028d894295e5b2f01295e5e4985000b','4028d88625da9f590125daa22ae10004','/role/listRole.action','查看角色列表');
Insert into T_RIGHT_URL (C_ID,C_RIGHT_ID,C_ACTION_URL,C_DESCRIPTION) values ('4028d894295e5b2f01295e5e4985000c','4028d88625da9f590125daa22ae10004','/role/loadRole.action','加载角色信息');
Insert into T_RIGHT_URL (C_ID,C_RIGHT_ID,C_ACTION_URL,C_DESCRIPTION) values ('4028d894295e5b2f01295e5e4985000d','4028d88625da9f590125daa22ae10004','/role/modifyRole.action','修改角色');
Insert into T_RIGHT_URL (C_ID,C_RIGHT_ID,C_ACTION_URL,C_DESCRIPTION) values ('4028d894295e5b2f01295e5e4985000e','4028d88625da9f590125daa22ae10004','/role/nextRole.action','查看角色信息下一条');
Insert into T_RIGHT_URL (C_ID,C_RIGHT_ID,C_ACTION_URL,C_DESCRIPTION) values ('4028d894295e5b2f01295e5e4985000f','4028d88625da9f590125daa22ae10004','/role/prevRole.action','查看角色信息上一条');
Insert into T_RIGHT_URL (C_ID,C_RIGHT_ID,C_ACTION_URL,C_DESCRIPTION) values ('4028d894295e5b2f01295e5e49850010','4028d88625da9f590125daa22ae10004','/role/simpleSearch.action','查询角色');
Insert into T_RIGHT_URL (C_ID,C_RIGHT_ID,C_ACTION_URL,C_DESCRIPTION) values ('4028d894295e5b2f01295e5e49850011','4028d88625da9f590125daa22ae10004','/role/sortRole.action','角色列表排序');
Insert into T_RIGHT_URL (C_ID,C_RIGHT_ID,C_ACTION_URL,C_DESCRIPTION) values ('4028d894295e5b2f01295e5e49850012','4028d88625da9f590125daa22ae10004','/role/toAddRole.action','进入添加角色页面');
Insert into T_RIGHT_URL (C_ID,C_RIGHT_ID,C_ACTION_URL,C_DESCRIPTION) values ('4028d894295e5b2f01295e5e49850013','4028d88625da9f590125daa22ae10004','/role/userRole.action','给用户分配角色');
Insert into T_RIGHT_URL (C_ID,C_RIGHT_ID,C_ACTION_URL,C_DESCRIPTION) values ('4028d894295e5b2f01295e5e49850014','4028d88625da9f590125daa22ae10004','/role/viewRole.action','查看角色');
Insert into T_RIGHT_URL (C_ID,C_RIGHT_ID,C_ACTION_URL,C_DESCRIPTION) values ('4028d894295e5b2f01295e5e49850015','4028d88625da9f590125daa22ae10004','/role/viewUserRole.action','查看用户角色');
Insert into T_RIGHT_URL (C_ID,C_RIGHT_ID,C_ACTION_URL,C_DESCRIPTION) values ('4028d894295e5b2f01295e5ffbf8001d','4028d894294377370129439da11d001b','/selfspace/viewInfo.action','转向查看个人信息');
Insert into T_RIGHT_URL (C_ID,C_RIGHT_ID,C_ACTION_URL,C_DESCRIPTION) values ('4028d894295e5b2f01295e5ffbf8001e','4028d894294377370129439da11d001b','/user/loadUser.action','加载个人信息');
Insert into T_RIGHT_URL (C_ID,C_RIGHT_ID,C_ACTION_URL,C_DESCRIPTION) values ('4028d894295e5b2f01295e5ffbf8001f','4028d894294377370129439da11d001b','/user/modifyUser.action','修改个人信息');
Insert into T_RIGHT_URL (C_ID,C_RIGHT_ID,C_ACTION_URL,C_DESCRIPTION) values ('4028d894295e5b2f01295e5ffbf80020','4028d894294377370129439da11d001b','/user/viewUser.action','查看个人信息');
Insert into T_RIGHT_URL (C_ID,C_RIGHT_ID,C_ACTION_URL,C_DESCRIPTION) values ('4028d894295e5b2f01295e62f68b0030','4028d8a12798fa5d0127991a3040002e','/role/userRole.action','分配角色');
Insert into T_RIGHT_URL (C_ID,C_RIGHT_ID,C_ACTION_URL,C_DESCRIPTION) values ('4028d894295e5b2f01295e62f68b0031','4028d8a12798fa5d0127991a3040002e','/role/viewUserRole.action','进入用户角色分配');
Insert into T_RIGHT_URL (C_ID,C_RIGHT_ID,C_ACTION_URL,C_DESCRIPTION) values ('4028d894295e5b2f01295e62f68b0032','4028d8a12798fa5d0127991a3040002e','/user/checkUserStatus.action','判断用户状态');
Insert into T_RIGHT_URL (C_ID,C_RIGHT_ID,C_ACTION_URL,C_DESCRIPTION) values ('4028d894295e5b2f01295e62f68b0033','4028d8a12798fa5d0127991a3040002e','/user/deleteUser.action','删除用户');
Insert into T_RIGHT_URL (C_ID,C_RIGHT_ID,C_ACTION_URL,C_DESCRIPTION) values ('4028d894295e5b2f01295e62f68b0034','4028d8a12798fa5d0127991a3040002e','/user/groupDeleteUser.action','群删用户');
Insert into T_RIGHT_URL (C_ID,C_RIGHT_ID,C_ACTION_URL,C_DESCRIPTION) values ('4028d894295e5b2f01295e62f69b0035','4028d8a12798fa5d0127991a3040002e','/user/groupDisableAccount.action','账号停用');
Insert into T_RIGHT_URL (C_ID,C_RIGHT_ID,C_ACTION_URL,C_DESCRIPTION) values ('4028d894295e5b2f01295e62f69b0036','4028d8a12798fa5d0127991a3040002e','/user/groupOperation.action','审批、启用用户');
Insert into T_RIGHT_URL (C_ID,C_RIGHT_ID,C_ACTION_URL,C_DESCRIPTION) values ('4028d894295e5b2f01295e62f69b0037','4028d8a12798fa5d0127991a3040002e','/user/listRole.action','获取用户角色');
Insert into T_RIGHT_URL (C_ID,C_RIGHT_ID,C_ACTION_URL,C_DESCRIPTION) values ('4028d894295e5b2f01295e62f69b0038','4028d8a12798fa5d0127991a3040002e','/user/listUser.action','查看用户列表');
Insert into T_RIGHT_URL (C_ID,C_RIGHT_ID,C_ACTION_URL,C_DESCRIPTION) values ('4028d894295e5b2f01295e62f69b0039','4028d8a12798fa5d0127991a3040002e','/user/loadUser.action','加载用户信息');
Insert into T_RIGHT_URL (C_ID,C_RIGHT_ID,C_ACTION_URL,C_DESCRIPTION) values ('4028d894295e5b2f01295e62f69b003a','4028d8a12798fa5d0127991a3040002e','/user/modifyUser.action','修改用户信息');
Insert into T_RIGHT_URL (C_ID,C_RIGHT_ID,C_ACTION_URL,C_DESCRIPTION) values ('4028d894295e5b2f01295e62f69b003b','4028d8a12798fa5d0127991a3040002e','/user/nextUser.action','查看用户信息下一条');
Insert into T_RIGHT_URL (C_ID,C_RIGHT_ID,C_ACTION_URL,C_DESCRIPTION) values ('4028d894295e5b2f01295e62f69b003c','4028d8a12798fa5d0127991a3040002e','/user/prevUser.action','查看用户信息上一条');
Insert into T_RIGHT_URL (C_ID,C_RIGHT_ID,C_ACTION_URL,C_DESCRIPTION) values ('4028d894295e5b2f01295e62f69b003d','4028d8a12798fa5d0127991a3040002e','/user/simpleSearch.action','用户的初级检索');
Insert into T_RIGHT_URL (C_ID,C_RIGHT_ID,C_ACTION_URL,C_DESCRIPTION) values ('4028d894295e5b2f01295e62f69b003e','4028d8a12798fa5d0127991a3040002e','/user/sortUser.action','用户列表排序');
Insert into T_RIGHT_URL (C_ID,C_RIGHT_ID,C_ACTION_URL,C_DESCRIPTION) values ('4028d894295e5b2f01295e62f6aa003f','4028d8a12798fa5d0127991a3040002e','/user/toGroupOperationP.action','进入用户启用页面');
Insert into T_RIGHT_URL (C_ID,C_RIGHT_ID,C_ACTION_URL,C_DESCRIPTION) values ('4028d894295e5b2f01295e62f6aa0040','4028d8a12798fa5d0127991a3040002e','/user/viewUser.action','查看用户信息');

---------------------------------------------------
--   END DATA FOR TABLE T_RIGHT_URL
---------------------------------------------------

---------------------------------------------------
--   DATA FOR TABLE T_SYSTEM_CONFIG
--   FILTER = none used
---------------------------------------------------
REM INSERTING into T_SYSTEM_CONFIG
Insert into T_SYSTEM_CONFIG (C_ID,C_KEY,C_DESCRIPTION,C_IS_HTML,C_VALUE) values ('sysconfig00007','UserPictureUploadPath','用户上传照片保存路径',0,'upload/user/image');
Insert into T_SYSTEM_CONFIG (C_ID,C_KEY,C_DESCRIPTION,C_IS_HTML,C_VALUE) values ('sysconfig00012','DisplayNumberEachPage','每页显示记录的条数',0,'20');

---------------------------------------------------
--   END DATA FOR TABLE T_SYSTEM_CONFIG
---------------------------------------------------

---------------------------------------------------
--   DATA FOR TABLE T_ROLE_RIGHT
--   FILTER = none used
---------------------------------------------------
REM INSERTING into T_ROLE_RIGHT
Insert into T_ROLE_RIGHT (C_ID,C_ROLE_ID,C_RIGHT_ID) values ('4028d9f12954865a012954b3bce70022','4028d8942953f8000129540e08eb0041','4028d894294377370129439da11d001b');
Insert into T_ROLE_RIGHT (C_ID,C_ROLE_ID,C_RIGHT_ID) values ('4028d9f12954865a012954b3bce70023','4028d8942953f8000129540e08eb0041','4028d88625da9f590125daa22ae10004');
Insert into T_ROLE_RIGHT (C_ID,C_ROLE_ID,C_RIGHT_ID) values ('4028d9f12954865a012954b3bcf70024','4028d8942953f8000129540e08eb0041','4028d8a127f5aa3e0127f5c8974b0002');
Insert into T_ROLE_RIGHT (C_ID,C_ROLE_ID,C_RIGHT_ID) values ('4028d9f12954865a012954b3bcf70025','4028d8942953f8000129540e08eb0041','4028d8a12798fa5d0127991a3040002e');

---------------------------------------------------
--   END DATA FOR TABLE T_ROLE_RIGHT
---------------------------------------------------

---------------------------------------------------
--   DATA FOR TABLE T_SYSTEM_OPTION
--   FILTER = none used
---------------------------------------------------
REM INSERTING into T_SYSTEM_OPTION
Insert into T_SYSTEM_OPTION (C_ID,C_NAME,C_DESCRIPTION,C_PARENT_ID,C_CODE,C_IS_AVAILABLE,C_STANDARD,C_ABBR) values ('sysoptionvalue00001','汉族',null,'sysoption00001',null,1,null,null);
Insert into T_SYSTEM_OPTION (C_ID,C_NAME,C_DESCRIPTION,C_PARENT_ID,C_CODE,C_IS_AVAILABLE,C_STANDARD,C_ABBR) values ('sysoptionvalue00002','回族',null,'sysoption00001',null,1,null,null);
Insert into T_SYSTEM_OPTION (C_ID,C_NAME,C_DESCRIPTION,C_PARENT_ID,C_CODE,C_IS_AVAILABLE,C_STANDARD,C_ABBR) values ('sysoptionvalue00003','瑶族',null,'sysoption00001',null,1,null,null);
Insert into T_SYSTEM_OPTION (C_ID,C_NAME,C_DESCRIPTION,C_PARENT_ID,C_CODE,C_IS_AVAILABLE,C_STANDARD,C_ABBR) values ('sysoptionvalue00004','珞巴族',null,'sysoption00001',null,1,null,null);
Insert into T_SYSTEM_OPTION (C_ID,C_NAME,C_DESCRIPTION,C_PARENT_ID,C_CODE,C_IS_AVAILABLE,C_STANDARD,C_ABBR) values ('sysoptionvalue00005','布朗族',null,'sysoption00001',null,1,null,null);
Insert into T_SYSTEM_OPTION (C_ID,C_NAME,C_DESCRIPTION,C_PARENT_ID,C_CODE,C_IS_AVAILABLE,C_STANDARD,C_ABBR) values ('sysoptionvalue00006','俄罗斯',null,'sysoption00001',null,1,null,null);
Insert into T_SYSTEM_OPTION (C_ID,C_NAME,C_DESCRIPTION,C_PARENT_ID,C_CODE,C_IS_AVAILABLE,C_STANDARD,C_ABBR) values ('sysoptionvalue00007','高山族',null,'sysoption00001',null,1,null,null);
Insert into T_SYSTEM_OPTION (C_ID,C_NAME,C_DESCRIPTION,C_PARENT_ID,C_CODE,C_IS_AVAILABLE,C_STANDARD,C_ABBR) values ('sysoptionvalue00008','怒族',null,'sysoption00001',null,1,null,null);
Insert into T_SYSTEM_OPTION (C_ID,C_NAME,C_DESCRIPTION,C_PARENT_ID,C_CODE,C_IS_AVAILABLE,C_STANDARD,C_ABBR) values ('sysoptionvalue00009','京族',null,'sysoption00001',null,1,null,null);
Insert into T_SYSTEM_OPTION (C_ID,C_NAME,C_DESCRIPTION,C_PARENT_ID,C_CODE,C_IS_AVAILABLE,C_STANDARD,C_ABBR) values ('sysoptionvalue00010','苗族',null,'sysoption00001',null,1,null,null);
Insert into T_SYSTEM_OPTION (C_ID,C_NAME,C_DESCRIPTION,C_PARENT_ID,C_CODE,C_IS_AVAILABLE,C_STANDARD,C_ABBR) values ('sysoptionvalue00011','塔吉克族',null,'sysoption00001',null,1,null,null);
Insert into T_SYSTEM_OPTION (C_ID,C_NAME,C_DESCRIPTION,C_PARENT_ID,C_CODE,C_IS_AVAILABLE,C_STANDARD,C_ABBR) values ('sysoptionvalue00012','仫佬族',null,'sysoption00001',null,1,null,null);
Insert into T_SYSTEM_OPTION (C_ID,C_NAME,C_DESCRIPTION,C_PARENT_ID,C_CODE,C_IS_AVAILABLE,C_STANDARD,C_ABBR) values ('sysoptionvalue00013','拉祜族',null,'sysoption00001',null,1,null,null);
Insert into T_SYSTEM_OPTION (C_ID,C_NAME,C_DESCRIPTION,C_PARENT_ID,C_CODE,C_IS_AVAILABLE,C_STANDARD,C_ABBR) values ('sysoptionvalue00014','僳僳族',null,'sysoption00001',null,1,null,null);
Insert into T_SYSTEM_OPTION (C_ID,C_NAME,C_DESCRIPTION,C_PARENT_ID,C_CODE,C_IS_AVAILABLE,C_STANDARD,C_ABBR) values ('sysoptionvalue00015','藏族',null,'sysoption00001',null,1,null,null);
Insert into T_SYSTEM_OPTION (C_ID,C_NAME,C_DESCRIPTION,C_PARENT_ID,C_CODE,C_IS_AVAILABLE,C_STANDARD,C_ABBR) values ('sysoptionvalue00016','水族',null,'sysoption00001',null,1,null,null);
Insert into T_SYSTEM_OPTION (C_ID,C_NAME,C_DESCRIPTION,C_PARENT_ID,C_CODE,C_IS_AVAILABLE,C_STANDARD,C_ABBR) values ('sysoptionvalue00017','彝族',null,'sysoption00001',null,1,null,null);
Insert into T_SYSTEM_OPTION (C_ID,C_NAME,C_DESCRIPTION,C_PARENT_ID,C_CODE,C_IS_AVAILABLE,C_STANDARD,C_ABBR) values ('sysoptionvalue00018','景颇族',null,'sysoption00001',null,1,null,null);
Insert into T_SYSTEM_OPTION (C_ID,C_NAME,C_DESCRIPTION,C_PARENT_ID,C_CODE,C_IS_AVAILABLE,C_STANDARD,C_ABBR) values ('sysoptionvalue00019','土家族',null,'sysoption00001',null,1,null,null);
Insert into T_SYSTEM_OPTION (C_ID,C_NAME,C_DESCRIPTION,C_PARENT_ID,C_CODE,C_IS_AVAILABLE,C_STANDARD,C_ABBR) values ('sysoptionvalue00020','蒙古族',null,'sysoption00001',null,1,null,null);
Insert into T_SYSTEM_OPTION (C_ID,C_NAME,C_DESCRIPTION,C_PARENT_ID,C_CODE,C_IS_AVAILABLE,C_STANDARD,C_ABBR) values ('sysoptionvalue00021','普米族',null,'sysoption00001',null,1,null,null);
Insert into T_SYSTEM_OPTION (C_ID,C_NAME,C_DESCRIPTION,C_PARENT_ID,C_CODE,C_IS_AVAILABLE,C_STANDARD,C_ABBR) values ('sysoptionvalue00022','白族',null,'sysoption00001',null,1,null,null);
Insert into T_SYSTEM_OPTION (C_ID,C_NAME,C_DESCRIPTION,C_PARENT_ID,C_CODE,C_IS_AVAILABLE,C_STANDARD,C_ABBR) values ('sysoptionvalue00023','黎族',null,'sysoption00001',null,1,null,null);
Insert into T_SYSTEM_OPTION (C_ID,C_NAME,C_DESCRIPTION,C_PARENT_ID,C_CODE,C_IS_AVAILABLE,C_STANDARD,C_ABBR) values ('sysoptionvalue00024','仡佬族',null,'sysoption00001',null,1,null,null);
Insert into T_SYSTEM_OPTION (C_ID,C_NAME,C_DESCRIPTION,C_PARENT_ID,C_CODE,C_IS_AVAILABLE,C_STANDARD,C_ABBR) values ('sysoptionvalue00025','保安族',null,'sysoption00001',null,1,null,null);
Insert into T_SYSTEM_OPTION (C_ID,C_NAME,C_DESCRIPTION,C_PARENT_ID,C_CODE,C_IS_AVAILABLE,C_STANDARD,C_ABBR) values ('sysoptionvalue00026','赫哲族',null,'sysoption00001',null,1,null,null);
Insert into T_SYSTEM_OPTION (C_ID,C_NAME,C_DESCRIPTION,C_PARENT_ID,C_CODE,C_IS_AVAILABLE,C_STANDARD,C_ABBR) values ('sysoptionvalue00027','裕固族',null,'sysoption00001',null,1,null,null);
Insert into T_SYSTEM_OPTION (C_ID,C_NAME,C_DESCRIPTION,C_PARENT_ID,C_CODE,C_IS_AVAILABLE,C_STANDARD,C_ABBR) values ('sysoptionvalue00028','哈萨克族',null,'sysoption00001',null,1,null,null);
Insert into T_SYSTEM_OPTION (C_ID,C_NAME,C_DESCRIPTION,C_PARENT_ID,C_CODE,C_IS_AVAILABLE,C_STANDARD,C_ABBR) values ('sysoptionvalue00029','羌族',null,'sysoption00001',null,1,null,null);
Insert into T_SYSTEM_OPTION (C_ID,C_NAME,C_DESCRIPTION,C_PARENT_ID,C_CODE,C_IS_AVAILABLE,C_STANDARD,C_ABBR) values ('sysoptionvalue00031','朝鲜族',null,'sysoption00001',null,1,null,null);
Insert into T_SYSTEM_OPTION (C_ID,C_NAME,C_DESCRIPTION,C_PARENT_ID,C_CODE,C_IS_AVAILABLE,C_STANDARD,C_ABBR) values ('sysoptionvalue00032','毛南族',null,'sysoption00001',null,1,null,null);
Insert into T_SYSTEM_OPTION (C_ID,C_NAME,C_DESCRIPTION,C_PARENT_ID,C_CODE,C_IS_AVAILABLE,C_STANDARD,C_ABBR) values ('sysoptionvalue00033','鄂伦春族',null,'sysoption00001',null,1,null,null);
Insert into T_SYSTEM_OPTION (C_ID,C_NAME,C_DESCRIPTION,C_PARENT_ID,C_CODE,C_IS_AVAILABLE,C_STANDARD,C_ABBR) values ('sysoptionvalue00034','鄂温克族',null,'sysoption00001',null,1,null,null);
Insert into T_SYSTEM_OPTION (C_ID,C_NAME,C_DESCRIPTION,C_PARENT_ID,C_CODE,C_IS_AVAILABLE,C_STANDARD,C_ABBR) values ('sysoptionvalue00035','达斡尔族',null,'sysoption00001',null,1,null,null);
Insert into T_SYSTEM_OPTION (C_ID,C_NAME,C_DESCRIPTION,C_PARENT_ID,C_CODE,C_IS_AVAILABLE,C_STANDARD,C_ABBR) values ('sysoptionvalue00036','壮族',null,'sysoption00001',null,1,null,null);
Insert into T_SYSTEM_OPTION (C_ID,C_NAME,C_DESCRIPTION,C_PARENT_ID,C_CODE,C_IS_AVAILABLE,C_STANDARD,C_ABBR) values ('sysoptionvalue00037','满族',null,'sysoption00001',null,1,null,null);
Insert into T_SYSTEM_OPTION (C_ID,C_NAME,C_DESCRIPTION,C_PARENT_ID,C_CODE,C_IS_AVAILABLE,C_STANDARD,C_ABBR) values ('sysoptionvalue00038','撒拉族',null,'sysoption00001',null,1,null,null);
Insert into T_SYSTEM_OPTION (C_ID,C_NAME,C_DESCRIPTION,C_PARENT_ID,C_CODE,C_IS_AVAILABLE,C_STANDARD,C_ABBR) values ('sysoptionvalue00039','塔塔尔族',null,'sysoption00001',null,1,null,null);
Insert into T_SYSTEM_OPTION (C_ID,C_NAME,C_DESCRIPTION,C_PARENT_ID,C_CODE,C_IS_AVAILABLE,C_STANDARD,C_ABBR) values ('sysoptionvalue00040','基诺族',null,'sysoption00001',null,1,null,null);
Insert into T_SYSTEM_OPTION (C_ID,C_NAME,C_DESCRIPTION,C_PARENT_ID,C_CODE,C_IS_AVAILABLE,C_STANDARD,C_ABBR) values ('sysoptionvalue00041','布依族',null,'sysoption00001',null,1,null,null);
Insert into T_SYSTEM_OPTION (C_ID,C_NAME,C_DESCRIPTION,C_PARENT_ID,C_CODE,C_IS_AVAILABLE,C_STANDARD,C_ABBR) values ('sysoptionvalue00042','柯尔克孜族',null,'sysoption00001',null,1,null,null);
Insert into T_SYSTEM_OPTION (C_ID,C_NAME,C_DESCRIPTION,C_PARENT_ID,C_CODE,C_IS_AVAILABLE,C_STANDARD,C_ABBR) values ('sysoptionvalue00043','侗族',null,'sysoption00001',null,1,null,null);
Insert into T_SYSTEM_OPTION (C_ID,C_NAME,C_DESCRIPTION,C_PARENT_ID,C_CODE,C_IS_AVAILABLE,C_STANDARD,C_ABBR) values ('sysoptionvalue00044','佤族',null,'sysoption00001',null,1,null,null);
Insert into T_SYSTEM_OPTION (C_ID,C_NAME,C_DESCRIPTION,C_PARENT_ID,C_CODE,C_IS_AVAILABLE,C_STANDARD,C_ABBR) values ('sysoptionvalue00045','东乡族',null,'sysoption00001',null,1,null,null);
Insert into T_SYSTEM_OPTION (C_ID,C_NAME,C_DESCRIPTION,C_PARENT_ID,C_CODE,C_IS_AVAILABLE,C_STANDARD,C_ABBR) values ('sysoptionvalue00046','德昂族',null,'sysoption00001',null,1,null,null);
Insert into T_SYSTEM_OPTION (C_ID,C_NAME,C_DESCRIPTION,C_PARENT_ID,C_CODE,C_IS_AVAILABLE,C_STANDARD,C_ABBR) values ('sysoptionvalue00047','门巴族',null,'sysoption00001',null,1,null,null);
Insert into T_SYSTEM_OPTION (C_ID,C_NAME,C_DESCRIPTION,C_PARENT_ID,C_CODE,C_IS_AVAILABLE,C_STANDARD,C_ABBR) values ('sysoptionvalue00048','纳西族',null,'sysoption00001',null,1,null,null);
Insert into T_SYSTEM_OPTION (C_ID,C_NAME,C_DESCRIPTION,C_PARENT_ID,C_CODE,C_IS_AVAILABLE,C_STANDARD,C_ABBR) values ('sysoptionvalue00049','乌孜别克族',null,'sysoption00001',null,1,null,null);
Insert into T_SYSTEM_OPTION (C_ID,C_NAME,C_DESCRIPTION,C_PARENT_ID,C_CODE,C_IS_AVAILABLE,C_STANDARD,C_ABBR) values ('sysoptionvalue00050','畲族',null,'sysoption00001',null,1,null,null);
Insert into T_SYSTEM_OPTION (C_ID,C_NAME,C_DESCRIPTION,C_PARENT_ID,C_CODE,C_IS_AVAILABLE,C_STANDARD,C_ABBR) values ('sysoptionvalue00051','傣族',null,'sysoption00001',null,1,null,null);
Insert into T_SYSTEM_OPTION (C_ID,C_NAME,C_DESCRIPTION,C_PARENT_ID,C_CODE,C_IS_AVAILABLE,C_STANDARD,C_ABBR) values ('sysoptionvalue00052','阿昌族',null,'sysoption00001',null,1,null,null);
Insert into T_SYSTEM_OPTION (C_ID,C_NAME,C_DESCRIPTION,C_PARENT_ID,C_CODE,C_IS_AVAILABLE,C_STANDARD,C_ABBR) values ('sysoptionvalue00053','独龙族',null,'sysoption00001',null,1,null,null);
Insert into T_SYSTEM_OPTION (C_ID,C_NAME,C_DESCRIPTION,C_PARENT_ID,C_CODE,C_IS_AVAILABLE,C_STANDARD,C_ABBR) values ('sysoptionvalue00054','锡伯族',null,'sysoption00001',null,1,null,null);
Insert into T_SYSTEM_OPTION (C_ID,C_NAME,C_DESCRIPTION,C_PARENT_ID,C_CODE,C_IS_AVAILABLE,C_STANDARD,C_ABBR) values ('sysoptionvalue00055','哈尼族',null,'sysoption00001',null,1,null,null);
Insert into T_SYSTEM_OPTION (C_ID,C_NAME,C_DESCRIPTION,C_PARENT_ID,C_CODE,C_IS_AVAILABLE,C_STANDARD,C_ABBR) values ('sysoptionvalue00056','维吾尔族',null,'sysoption00001',null,1,null,null);
Insert into T_SYSTEM_OPTION (C_ID,C_NAME,C_DESCRIPTION,C_PARENT_ID,C_CODE,C_IS_AVAILABLE,C_STANDARD,C_ABBR) values ('sysoptionvalue00087','女',null,'sysoption00007',null,1,null,null);
Insert into T_SYSTEM_OPTION (C_ID,C_NAME,C_DESCRIPTION,C_PARENT_ID,C_CODE,C_IS_AVAILABLE,C_STANDARD,C_ABBR) values ('sysoptionvalue00086','男',null,'sysoption00007',null,1,null,null);
Insert into T_SYSTEM_OPTION (C_ID,C_NAME,C_DESCRIPTION,C_PARENT_ID,C_CODE,C_IS_AVAILABLE,C_STANDARD,C_ABBR) values ('sysoption00001','民族','ethnic',null,null,1,null,null);
Insert into T_SYSTEM_OPTION (C_ID,C_NAME,C_DESCRIPTION,C_PARENT_ID,C_CODE,C_IS_AVAILABLE,C_STANDARD,C_ABBR) values ('sysoption00007','性别','gender',null,null,1,null,null);

---------------------------------------------------
--   END DATA FOR TABLE T_SYSTEM_OPTION
---------------------------------------------------

---------------------------------------------------
--   DATA FOR TABLE T_RIGHT
--   FILTER = none used
---------------------------------------------------
REM INSERTING into T_RIGHT
Insert into T_RIGHT (C_ID,C_NAME,C_DESCRIPTION) values ('4028d894294377370129439da11d001b','基本权限','系统用户默认拥有的基本功能');
Insert into T_RIGHT (C_ID,C_NAME,C_DESCRIPTION) values ('4028d8a12798fa5d0127991a3040002e','用户管理','管理系统用户');
Insert into T_RIGHT (C_ID,C_NAME,C_DESCRIPTION) values ('4028d88625da9f590125daa22ae10004','角色管理','管理系统角色');
Insert into T_RIGHT (C_ID,C_NAME,C_DESCRIPTION) values ('4028d8a127f5aa3e0127f5c8974b0002','系统配置','每页显示数');

---------------------------------------------------
--   END DATA FOR TABLE T_RIGHT
---------------------------------------------------

---------------------------------------------------
--   DATA FOR TABLE T_USER
--   FILTER = none used
---------------------------------------------------
REM INSERTING into T_USER
Insert into T_USER (C_ID,C_USERNAME,C_CHINESE_NAME,C_PASSWORD,C_BIRTHDAY,C_PHOTO_FILE,C_USER_STATUS,C_EMAIL,C_MOBILE_PHONE,C_GENDER,C_REGISTER_TIME,C_APPROVE_TIME,C_EXPIRE_TIME,C_ETHNIC,C_PW_RETRIEVE_CODE,C_IS_SUPER_USER) values ('admin','admin','admin','e10adc3949ba59abbe56e057f20f883e',null,null,1,'kaoyi121@163.com',null,'sysoptionvalue00086',to_timestamp('11-6月 -09 12.00.00.000000000 上午','DD-MON-RR HH.MI.SS.FF AM'),to_timestamp('11-6月 -09 12.00.00.000000000 上午','DD-MON-RR HH.MI.SS.FF AM'),to_timestamp('01-1月 -20 12.00.00.000000000 上午','DD-MON-RR HH.MI.SS.FF AM'),null,null,1);

---------------------------------------------------
--   END DATA FOR TABLE T_USER
---------------------------------------------------
--------------------------------------------------------
--  Constraints for Table T_RIGHT
--------------------------------------------------------

  ALTER TABLE "T_RIGHT" MODIFY ("C_ID" NOT NULL ENABLE);
 
  ALTER TABLE "T_RIGHT" MODIFY ("C_NAME" NOT NULL ENABLE);
 
  ALTER TABLE "T_RIGHT" MODIFY ("C_DESCRIPTION" NOT NULL ENABLE);
 
  ALTER TABLE "T_RIGHT" ADD PRIMARY KEY ("C_ID") ENABLE;
--------------------------------------------------------
--  Constraints for Table T_RIGHT_URL
--------------------------------------------------------

  ALTER TABLE "T_RIGHT_URL" MODIFY ("C_ID" NOT NULL ENABLE);
 
  ALTER TABLE "T_RIGHT_URL" MODIFY ("C_RIGHT_ID" NOT NULL ENABLE);
 
  ALTER TABLE "T_RIGHT_URL" MODIFY ("C_ACTION_URL" NOT NULL ENABLE);
 
  ALTER TABLE "T_RIGHT_URL" MODIFY ("C_DESCRIPTION" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table T_ROLE
--------------------------------------------------------

  ALTER TABLE "T_ROLE" MODIFY ("C_ID" NOT NULL ENABLE);
 
  ALTER TABLE "T_ROLE" MODIFY ("C_NAME" NOT NULL ENABLE);
 
  ALTER TABLE "T_ROLE" MODIFY ("C_DESCRIPTION" NOT NULL ENABLE);
 
  ALTER TABLE "T_ROLE" ADD PRIMARY KEY ("C_ID") ENABLE;
--------------------------------------------------------
--  Constraints for Table T_ROLE_RIGHT
--------------------------------------------------------

  ALTER TABLE "T_ROLE_RIGHT" MODIFY ("C_ID" NOT NULL ENABLE);
 
  ALTER TABLE "T_ROLE_RIGHT" MODIFY ("C_ROLE_ID" NOT NULL ENABLE);
 
  ALTER TABLE "T_ROLE_RIGHT" MODIFY ("C_RIGHT_ID" NOT NULL ENABLE);
 
  ALTER TABLE "T_ROLE_RIGHT" ADD PRIMARY KEY ("C_ID") ENABLE;
--------------------------------------------------------
--  Constraints for Table T_SYSTEM_CONFIG
--------------------------------------------------------

  ALTER TABLE "T_SYSTEM_CONFIG" MODIFY ("C_ID" NOT NULL ENABLE);
 
  ALTER TABLE "T_SYSTEM_CONFIG" MODIFY ("C_KEY" NOT NULL ENABLE);
 
  ALTER TABLE "T_SYSTEM_CONFIG" ADD CONSTRAINT "T_SYSCONFIG_PK" PRIMARY KEY ("C_ID") ENABLE;
--------------------------------------------------------
--  Constraints for Table T_SYSTEM_OPTION
--------------------------------------------------------

  ALTER TABLE "T_SYSTEM_OPTION" MODIFY ("C_ID" NOT NULL ENABLE);
 
  ALTER TABLE "T_SYSTEM_OPTION" MODIFY ("C_NAME" NOT NULL ENABLE);
 
  ALTER TABLE "T_SYSTEM_OPTION" MODIFY ("C_IS_AVAILABLE" NOT NULL ENABLE);
 
  ALTER TABLE "T_SYSTEM_OPTION" ADD CONSTRAINT "T_SYSOPTION_PK" PRIMARY KEY ("C_ID") ENABLE;
--------------------------------------------------------
--  Constraints for Table T_USER
--------------------------------------------------------

  ALTER TABLE "T_USER" MODIFY ("C_ID" NOT NULL ENABLE);
 
  ALTER TABLE "T_USER" MODIFY ("C_USERNAME" NOT NULL ENABLE);
 
  ALTER TABLE "T_USER" MODIFY ("C_CHINESE_NAME" NOT NULL ENABLE);
 
  ALTER TABLE "T_USER" MODIFY ("C_PASSWORD" NOT NULL ENABLE);
 
  ALTER TABLE "T_USER" MODIFY ("C_REGISTER_TIME" NOT NULL ENABLE);
 
  ALTER TABLE "T_USER" MODIFY ("C_IS_SUPER_USER" NOT NULL ENABLE);
 
  ALTER TABLE "T_USER" ADD PRIMARY KEY ("C_ID") ENABLE;
--------------------------------------------------------
--  Constraints for Table T_USER_ROLE
--------------------------------------------------------

  ALTER TABLE "T_USER_ROLE" MODIFY ("C_ID" NOT NULL ENABLE);
 
  ALTER TABLE "T_USER_ROLE" MODIFY ("C_USER_ID" NOT NULL ENABLE);
 
  ALTER TABLE "T_USER_ROLE" MODIFY ("C_ROLE_ID" NOT NULL ENABLE);
 
  ALTER TABLE "T_USER_ROLE" ADD PRIMARY KEY ("C_ID") ENABLE;
--------------------------------------------------------
--  DDL for Index T_SYSCONFIG_PK
--------------------------------------------------------

--  CREATE UNIQUE INDEX "T_SYSCONFIG_PK" ON "T_SYSTEM_CONFIG" ("C_ID") 
--  ;
--------------------------------------------------------
--  DDL for Index T_SYSOPTION_PK
--------------------------------------------------------

--  CREATE UNIQUE INDEX "T_SYSOPTION_PK" ON "T_SYSTEM_OPTION" ("C_ID") 
--  ;
--------------------------------------------------------
--  DDL for Index T_SYSTEM_OPTION_INDEX1
--------------------------------------------------------

  CREATE INDEX "T_SYSTEM_OPTION_INDEX1" ON "T_SYSTEM_OPTION" ("C_CODE") 
  ;

--------------------------------------------------------
--  Ref Constraints for Table T_RIGHT_URL
--------------------------------------------------------

  ALTER TABLE "T_RIGHT_URL" ADD FOREIGN KEY ("C_RIGHT_ID")
	  REFERENCES "T_RIGHT" ("C_ID") ENABLE;

--------------------------------------------------------
--  Ref Constraints for Table T_ROLE_RIGHT
--------------------------------------------------------

  ALTER TABLE "T_ROLE_RIGHT" ADD FOREIGN KEY ("C_ROLE_ID")
	  REFERENCES "T_ROLE" ("C_ID") ENABLE;
 
  ALTER TABLE "T_ROLE_RIGHT" ADD FOREIGN KEY ("C_RIGHT_ID")
	  REFERENCES "T_RIGHT" ("C_ID") ENABLE;

--------------------------------------------------------
--  Ref Constraints for Table T_SYSTEM_OPTION
--------------------------------------------------------

  ALTER TABLE "T_SYSTEM_OPTION" ADD CONSTRAINT "T_SYSTEM_OPTION_T_SYSTEM__FK1" FOREIGN KEY ("C_PARENT_ID")
	  REFERENCES "T_SYSTEM_OPTION" ("C_ID") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table T_USER
--------------------------------------------------------

  ALTER TABLE "T_USER" ADD CONSTRAINT "T_USER_T_SYSTEM_OPTION_FK1" FOREIGN KEY ("C_ETHNIC")
	  REFERENCES "T_SYSTEM_OPTION" ("C_ID") ENABLE;
 
  ALTER TABLE "T_USER" ADD CONSTRAINT "T_USER_T_SYSTEM_OPTION_FK2" FOREIGN KEY ("C_GENDER")
	  REFERENCES "T_SYSTEM_OPTION" ("C_ID") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table T_USER_ROLE
--------------------------------------------------------

  ALTER TABLE "T_USER_ROLE" ADD FOREIGN KEY ("C_ROLE_ID")
	  REFERENCES "T_ROLE" ("C_ID") ENABLE;
 
  ALTER TABLE "T_USER_ROLE" ADD FOREIGN KEY ("C_USER_ID")
	  REFERENCES "T_USER" ("C_ID") ENABLE;
