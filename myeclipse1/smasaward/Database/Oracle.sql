--------------------------------------------------------
--  File created - 星期二-六月-22-2010   
--------------------------------------------------------
--------------------------------------------------------
--  DDL for Table T_EXPERT
--------------------------------------------------------

  CREATE TABLE "T_EXPERT" 
   (	"C_ID" VARCHAR2(40), 
	"C_NAME" VARCHAR2(100), 
	"C_UNIVERSITY_CODE" VARCHAR2(100), 
	"C_GENDER" VARCHAR2(100), 
	"C_BIRTHDAY" DATE, 
	"C_SPECIALTY_TITLE" VARCHAR2(500), 
	"C_DISCIPLINE" VARCHAR2(500), 
	"C_HOME_PHONE" VARCHAR2(100), 
	"C_MOBILE_PHONE" VARCHAR2(200), 
	"C_OFFICE_PHONE" VARCHAR2(100), 
	"C_EMAIL" VARCHAR2(200), 
	"C_MOE_PROJECT" VARCHAR2(4000), 
	"C_NATIONAL_PROJECT" VARCHAR2(4000), 
	"C_JOB" VARCHAR2(500), 
	"C_IS_DOCTOR_TUTOR" VARCHAR2(40), 
	"C_DEPARTMENT" VARCHAR2(100), 
	"C_DEGREE" VARCHAR2(100), 
	"C_LANGUAGE_1" VARCHAR2(100), 
	"C_POSITION_LEVEL" VARCHAR2(100), 
	"C_AWARD" VARCHAR2(4000), 
	"C_RESEARCH_FIELD" VARCHAR2(4000), 
	"C_PARTTIME" VARCHAR2(4000), 
	"C_OFFICE_ADDRESS" VARCHAR2(500), 
	"C_OFFICE_POSTCODE" VARCHAR2(100), 
	"C_REMARK" VARCHAR2(500), 
	"C_ID_CARD" VARCHAR2(100), 
	"C_WARNING" VARCHAR2(500)
   ) ;
--------------------------------------------------------
--  DDL for Table T_PROJECT
--------------------------------------------------------

  CREATE TABLE "T_PROJECT" 
   (	"C_MEMBERS" VARCHAR2(500), 
	"C_IDCARD" VARCHAR2(40), 
	"C_EMAIL" VARCHAR2(100), 
	"C_MOBILE" VARCHAR2(20), 
	"C_PHONE" VARCHAR2(20), 
	"C_POSTCODE" VARCHAR2(20), 
	"C_ADDRESS" VARCHAR2(100), 
	"C_FOREIGN" VARCHAR2(20), 
	"C_DEGREE" VARCHAR2(20), 
	"C_EDUCATION" VARCHAR2(20), 
	"C_JOB" VARCHAR2(20), 
	"C_DEPARTMENT" VARCHAR2(40), 
	"C_TITLE" VARCHAR2(20), 
	"C_BIRTHDAY" DATE, 
	"C_GENDER" VARCHAR2(10), 
	"C_DIRECTOR" VARCHAR2(40), 
	"C_FINAL_RESULT_TYPE" VARCHAR2(100), 
	"C_OTHER_FEE" VARCHAR2(20), 
	"C_APPLY_FEE" VARCHAR2(20), 
	"C_PLAN_END_DATE" DATE, 
	"C_RESEARCH_TYPE" VARCHAR2(20), 
	"C_DISCIPLINE" VARCHAR2(150), 
	"C_APPLY_DATE" DATE, 
	"C_DISCIPLINE_TYPE" VARCHAR2(40), 
	"C_PROJECT_TYPE" VARCHAR2(20), 
	"C_PROJECT_NAME" VARCHAR2(100), 
	"C_UNIVERSITY_NAME" VARCHAR2(40), 
	"C_UNIVERSITY_CODE" VARCHAR2(10), 
	"C_FILE" VARCHAR2(80), 
	"C_AUDIT_STATUS" VARCHAR2(20), 
	"C_ADD_DATE" DATE, 
	"C_ID" VARCHAR2(10), 
	"C_WARNING" VARCHAR2(200), 
	"C_WARNING_REVIEWER" VARCHAR2(200), 
	"C_IS_REVIEWABLE" NUMBER(1,0)
   ) ;
--------------------------------------------------------
--  DDL for Table T_PROJECT_REVIEWER
--------------------------------------------------------

  CREATE TABLE "T_PROJECT_REVIEWER" 
   (	"C_ID" VARCHAR2(40), 
	"C_PROJECT_ID" VARCHAR2(40), 
	"C_REVIEWER_ID" VARCHAR2(40)
   ) ;
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
	"C_STANDARD" VARCHAR2(100)
   ) ;
--------------------------------------------------------
--  DDL for Table T_UNIVERSITY
--------------------------------------------------------

  CREATE TABLE "T_UNIVERSITY" 
   (	"C_FOUNDER_CODE" VARCHAR2(100), 
	"C_NAME" VARCHAR2(100), 
	"C_CODE" VARCHAR2(100), 
	"C_TYPE" NUMBER(1,0), 
	"C_REMARK" VARCHAR2(100)
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
--------------------------------------------------------
--  Constraints for Table T_EXPERT
--------------------------------------------------------

  ALTER TABLE "T_EXPERT" ADD CONSTRAINT "C_EXPERT_PK" PRIMARY KEY ("C_ID") ENABLE;
 
  ALTER TABLE "T_EXPERT" MODIFY ("C_ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table T_PROJECT
--------------------------------------------------------

  ALTER TABLE "T_PROJECT" MODIFY ("C_ID" NOT NULL ENABLE);
 
  ALTER TABLE "T_PROJECT" ADD CONSTRAINT "T_PROJECT_PK" PRIMARY KEY ("C_ID") ENABLE;
--------------------------------------------------------
--  Constraints for Table T_PROJECT_REVIEWER
--------------------------------------------------------

  ALTER TABLE "T_PROJECT_REVIEWER" MODIFY ("C_ID" NOT NULL ENABLE);
 
  ALTER TABLE "T_PROJECT_REVIEWER" MODIFY ("C_PROJECT_ID" NOT NULL ENABLE);
 
  ALTER TABLE "T_PROJECT_REVIEWER" MODIFY ("C_REVIEWER_ID" NOT NULL ENABLE);
 
  ALTER TABLE "T_PROJECT_REVIEWER" ADD CONSTRAINT "T_PROJECT_EXPERT_PK" PRIMARY KEY ("C_ID") ENABLE;
--------------------------------------------------------
--  Constraints for Table T_RIGHT
--------------------------------------------------------

  ALTER TABLE "T_RIGHT" MODIFY ("C_ID" NOT NULL ENABLE);
 
  ALTER TABLE "T_RIGHT" ADD PRIMARY KEY ("C_ID") ENABLE;
 
  ALTER TABLE "T_RIGHT" MODIFY ("C_NAME" NOT NULL ENABLE);
 
  ALTER TABLE "T_RIGHT" MODIFY ("C_DESCRIPTION" NOT NULL ENABLE);
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
 
  ALTER TABLE "T_ROLE" ADD PRIMARY KEY ("C_ID") ENABLE;
 
  ALTER TABLE "T_ROLE" MODIFY ("C_NAME" NOT NULL ENABLE);
 
  ALTER TABLE "T_ROLE" MODIFY ("C_DESCRIPTION" NOT NULL ENABLE);
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
--  Constraints for Table T_UNIVERSITY
--------------------------------------------------------

  ALTER TABLE "T_UNIVERSITY" MODIFY ("C_CODE" NOT NULL ENABLE);
 
  ALTER TABLE "T_UNIVERSITY" ADD CONSTRAINT "T_UNIVERSITY_PK" PRIMARY KEY ("C_CODE") ENABLE;
--------------------------------------------------------
--  Constraints for Table T_USER
--------------------------------------------------------

  ALTER TABLE "T_USER" MODIFY ("C_ID" NOT NULL ENABLE);
 
  ALTER TABLE "T_USER" ADD PRIMARY KEY ("C_ID") ENABLE;
 
  ALTER TABLE "T_USER" MODIFY ("C_USERNAME" NOT NULL ENABLE);
 
  ALTER TABLE "T_USER" MODIFY ("C_CHINESE_NAME" NOT NULL ENABLE);
 
  ALTER TABLE "T_USER" MODIFY ("C_PASSWORD" NOT NULL ENABLE);
 
  ALTER TABLE "T_USER" MODIFY ("C_REGISTER_TIME" NOT NULL ENABLE);
 
  ALTER TABLE "T_USER" MODIFY ("C_IS_SUPER_USER" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table T_USER_ROLE
--------------------------------------------------------

  ALTER TABLE "T_USER_ROLE" MODIFY ("C_ID" NOT NULL ENABLE);
 
  ALTER TABLE "T_USER_ROLE" MODIFY ("C_USER_ID" NOT NULL ENABLE);
 
  ALTER TABLE "T_USER_ROLE" MODIFY ("C_ROLE_ID" NOT NULL ENABLE);
 
  ALTER TABLE "T_USER_ROLE" ADD PRIMARY KEY ("C_ID") ENABLE;
--------------------------------------------------------
--  DDL for Index C_EXPERT_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "C_EXPERT_PK" ON "T_EXPERT" ("C_ID") 
  ;
--------------------------------------------------------
--  DDL for Index T_PROJECT_EXPERT_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "T_PROJECT_EXPERT_PK" ON "T_PROJECT_REVIEWER" ("C_ID") 
  ;
--------------------------------------------------------
--  DDL for Index T_PROJECT_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "T_PROJECT_PK" ON "T_PROJECT" ("C_ID") 
  ;
--------------------------------------------------------
--  DDL for Index T_SYSCONFIG_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "T_SYSCONFIG_PK" ON "T_SYSTEM_CONFIG" ("C_ID") 
  ;
--------------------------------------------------------
--  DDL for Index T_SYSOPTION_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "T_SYSOPTION_PK" ON "T_SYSTEM_OPTION" ("C_ID") 
  ;
--------------------------------------------------------
--  DDL for Index T_SYSTEM_OPTION_INDEX1
--------------------------------------------------------

  CREATE INDEX "T_SYSTEM_OPTION_INDEX1" ON "T_SYSTEM_OPTION" ("C_CODE") 
  ;
--------------------------------------------------------
--  DDL for Index T_UNIVERSITY_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "T_UNIVERSITY_PK" ON "T_UNIVERSITY" ("C_CODE") 
  ;


--------------------------------------------------------
--  Ref Constraints for Table T_PROJECT_REVIEWER
--------------------------------------------------------

  ALTER TABLE "T_PROJECT_REVIEWER" ADD CONSTRAINT "T_PROJECT_EXPERT_T_EXPERT_FK1" FOREIGN KEY ("C_REVIEWER_ID")
	  REFERENCES "T_EXPERT" ("C_ID") ENABLE;
 
  ALTER TABLE "T_PROJECT_REVIEWER" ADD CONSTRAINT "T_PROJECT_EXPERT_T_PROJEC_FK1" FOREIGN KEY ("C_PROJECT_ID")
	  REFERENCES "T_PROJECT" ("C_ID") ENABLE;

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
