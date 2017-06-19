------------------先删除之前的数据------------------
delete from s_s_award;--[end]
delete from s_s_project;--[end]
delete from s_s_person;--[end]
delete from s_s_unit;--[end]
delete from s_s_product;--[end]
delete from s_d_award;--[end]
delete from s_d_project;--[end]
delete from s_d_person;--[end]
delete from s_d_unit;--[end]
delete from s_d_product;--[end]

--机构维表--
insert into S_D_UNIT (c_id, c_name, c_style, c_category, c_province, c_type, C_ORGANIZER)
select 
DISTINCT t_agency.C_ID as a_id, 
t_agency.C_NAME as a_name,
t_agency.c_style as a_style,
t_agency.c_category as a_category,
t_system_option.c_name as a_province,
(case when (t_agency.C_TYPE = 1) then '部级' 
when (t_agency.C_TYPE = 2) then '省级'
when (t_agency.C_TYPE = 3) then '部属高校'
when (t_agency.C_TYPE = 4) then '地方高校'
end) as a_type ,
t_agency.C_ORGANIZER as C_ORGANIZER 
from t_agency, t_system_option 
where t_agency.c_province_id = t_system_option.c_id;--[end]

--人员事实表--
insert into s_s_person (c_id, c_d_person_id, c_d_unit_id)
select t_project_member.c_member_id, t_project_member.c_member_id, max(t_project_member.c_university_id) from t_project_member  where t_project_member.c_university_id is not null group by(t_project_member.c_member_id) having (t_project_member.c_member_id is not null);--[end]

--人员维表--
insert into S_D_PERSON (c_id, c_name, c_gender, c_age_group, c_title, C_LAST_DEGREE, C_LAST_EDUCATION, C_TUTOR_TYPE, C_DISCIPLINE_TYPE)
select 
t_person.C_ID as p_id, 
t_person.C_NAME as p_name, 
t_person.C_GENDER as p_gender, (case 
when((to_number(to_char(sysdate, 'YYYY')) - to_number(to_char(t_person.c_birthday, 'YYYY'))) <= 30) then '30岁及以下' 
when((to_number(to_char(sysdate, 'YYYY')) - to_number(to_char(t_person.c_birthday, 'YYYY'))) > 30) and ((to_number(to_char(sysdate, 'YYYY')) - to_number(to_char(t_person.c_birthday, 'YYYY'))) <= 35) then '31-35岁' 
when((to_number(to_char(sysdate, 'YYYY')) - to_number(to_char(t_person.c_birthday, 'YYYY'))) > 35) and ((to_number(to_char(sysdate, 'YYYY')) - to_number(to_char(t_person.c_birthday, 'YYYY'))) <= 40) then '36-40岁'
when((to_number(to_char(sysdate, 'YYYY')) - to_number(to_char(t_person.c_birthday, 'YYYY'))) > 40) and ((to_number(to_char(sysdate, 'YYYY')) - to_number(to_char(t_person.c_birthday, 'YYYY'))) <= 45) then '41-45岁'
when((to_number(to_char(sysdate, 'YYYY')) - to_number(to_char(t_person.c_birthday, 'YYYY'))) > 45) and ((to_number(to_char(sysdate, 'YYYY')) - to_number(to_char(t_person.c_birthday, 'YYYY'))) <= 50) then '46-50岁'
when((to_number(to_char(sysdate, 'YYYY')) - to_number(to_char(t_person.c_birthday, 'YYYY'))) > 50) and ((to_number(to_char(sysdate, 'YYYY')) - to_number(to_char(t_person.c_birthday, 'YYYY'))) <= 55) then '51-55岁'
when((to_number(to_char(sysdate, 'YYYY')) - to_number(to_char(t_person.c_birthday, 'YYYY'))) > 55) and ((to_number(to_char(sysdate, 'YYYY')) - to_number(to_char(t_person.c_birthday, 'YYYY'))) <= 60) then '56-60岁'
when((to_number(to_char(sysdate, 'YYYY')) - to_number(to_char(t_person.c_birthday, 'YYYY'))) > 60) then '60岁以上' 
end ) as p_age, 
t_academic.C_SPECIALITY_TITLE as p_title,
t_academic.C_LAST_DEGREE as p_degree,
t_academic.c_last_education as c_last_education,
t_academic.C_TUTOR_TYPE as C_TUTOR_TYPE,
t_academic.C_DISCIPLINE_TYPE as p_dt
from s_s_person left outer join t_person  on (s_s_person.c_id = t_person.c_id) left outer join  t_academic on t_person.C_ID = t_academic.C_PERSON_ID where t_person.C_ID is not null;--[end]
--更新部分--
--是否项目负责人--
update S_D_PERSON set S_D_PERSON.C_IS_DIRECTOR = '项目负责人' where S_D_PERSON.c_id in (SELECT distinct pm.c_member_id from t_project_member pm where pm.c_is_director = 1);--[end]
update S_D_PERSON set S_D_PERSON.C_IS_DIRECTOR = '非项目负责人' where S_D_PERSON.C_IS_DIRECTOR is null;--[end]
--是否项目成员--
update S_D_PERSON set S_D_PERSON.c_is_member = '项目成员' where S_D_PERSON.c_id in (SELECT distinct pm.c_member_id from t_project_member pm);--[end]
update S_D_PERSON set S_D_PERSON.c_is_member = '非项目成员' where S_D_PERSON.c_is_member is null;--[end]
--参与项目类型--
update S_D_PERSON set S_D_PERSON.C_PROJECT_TYPE = concat(S_D_PERSON.C_PROJECT_TYPE, '; 一般项目') where S_D_PERSON.c_id in (SELECT distinct pm.c_member_id from t_project_member pm where pm.C_PROJECT_TYPE = 'general');--[end]
update S_D_PERSON set S_D_PERSON.C_PROJECT_TYPE = concat(S_D_PERSON.C_PROJECT_TYPE, '; 基地项目') where S_D_PERSON.c_id in (SELECT distinct pm.c_member_id from t_project_member pm where pm.C_PROJECT_TYPE = 'instp');--[end]
update S_D_PERSON set S_D_PERSON.C_PROJECT_TYPE = concat(S_D_PERSON.C_PROJECT_TYPE, '; 重大攻关项目') where S_D_PERSON.c_id in (SELECT distinct pm.c_member_id from t_project_member pm where pm.C_PROJECT_TYPE = 'key');--[end]
update S_D_PERSON set S_D_PERSON.C_PROJECT_TYPE = concat(S_D_PERSON.C_PROJECT_TYPE, '; 后期资助项目') where S_D_PERSON.c_id in (SELECT distinct pm.c_member_id from t_project_member pm where pm.C_PROJECT_TYPE = 'post');--[end]
update S_D_PERSON set S_D_PERSON.C_PROJECT_TYPE = concat(S_D_PERSON.C_PROJECT_TYPE, '; 应急委托课题') where S_D_PERSON.c_id in (SELECT distinct pm.c_member_id from t_project_member pm where pm.C_PROJECT_TYPE = 'entrust');--[end]
update S_D_PERSON set S_D_PERSON.C_PROJECT_TYPE = substr(S_D_PERSON.C_PROJECT_TYPE, 3) where S_D_PERSON.C_PROJECT_TYPE is not null and S_D_PERSON.C_PROJECT_TYPE like '; %';--[end]

--项目维表--
INSERT INTO S_D_PROJECT ( c_id, c_name, c_product_type, c_subtype, c_discipline_type, c_research_type, c_year, c_fee, c_is_granted, c_director_agegroup, c_director_gender, c_director_degree, c_area_type, c_project_type, c_end_year, c_evaluation_type, c_status, c_apply_excelle, c_mid_year, c_end_years)
select 
distinct
tpa.c_id as pid, 
tpa.c_name as pname, 
rtrim((case when (tpa.c_product_type like '%论文%') then '论文; ' else '' end)
|| (case when (tpa.c_product_type like '%著%') then '著作; ' else '' end)
|| (case when (tpa.c_product_type like '%报告%') then '研究咨询报告; ' else '' end)
|| (case when (tpa.c_product_type like '%专利%') then '专利; ' else '' end)
|| (case when (tpa.c_product_type like '%电子%') then '电子出版物; ' else '' end), '; ') as productType,
sysa.c_name as subt,
tpa.c_discipline_type as pdt,
sys.c_name as researchType,
tpa.c_year as pyear,
(case when ( tgg.c_approve_fee is null ) then 0 when (tgg.c_approve_fee is not null ) then tgg.c_approve_fee  end ) as pFee,
(case when (tgg.c_id is null ) then '未立项' when (tgg.c_id is not null ) then '已立项'  end ) as g_is_granted,
(case 
when((to_number(tpa.c_year) - to_number(to_char(tp.c_birthday, 'YYYY'))) <= 30) then '30岁及以下' 
when((to_number(tpa.c_year) - to_number(to_char(tp.c_birthday, 'YYYY'))) > 30 and ((to_number(tpa.c_year)) - to_number(to_char(tp.c_birthday, 'YYYY'))) <= 35) then '31-35岁' 
when((to_number(tpa.c_year) - to_number(to_char(tp.c_birthday, 'YYYY'))) > 35 and ((to_number(tpa.c_year)) - to_number(to_char(tp.c_birthday, 'YYYY'))) <= 40) then '36-40岁'
when((to_number(tpa.c_year) - to_number(to_char(tp.c_birthday, 'YYYY'))) > 40 and ((to_number(tpa.c_year)) - to_number(to_char(tp.c_birthday, 'YYYY'))) <= 45) then '41-45岁'
when((to_number(tpa.c_year) - to_number(to_char(tp.c_birthday, 'YYYY'))) > 45 and ((to_number(tpa.c_year)) - to_number(to_char(tp.c_birthday, 'YYYY'))) <= 50) then '46-50岁'
when((to_number(tpa.c_year) - to_number(to_char(tp.c_birthday, 'YYYY'))) > 50 and ((to_number(tpa.c_year)) - to_number(to_char(tp.c_birthday, 'YYYY'))) <= 55) then '51-55岁'
when((to_number(tpa.c_year) - to_number(to_char(tp.c_birthday, 'YYYY'))) > 55 and ((to_number(tpa.c_year)) - to_number(to_char(tp.c_birthday, 'YYYY'))) <= 60) then '56-60岁'
when((to_number(tpa.c_year) - to_number(to_char(tp.c_birthday, 'YYYY'))) > 60) then '60岁以上' 
end ) as age,
tp.c_gender as dgender,
( case when (taca.c_last_degree like '%博士%') then '博士'
when (taca.c_last_degree like '%硕士%') then '硕士'
when (taca.c_last_degree like '%学士%') then '学士' 
when (taca.c_last_degree = '其他' ) then '其他' end) as ddegree,
(case 
when (sysc.c_name in ('重庆市','四川省','贵州省','云南省','广西壮族自治区','陕西省','甘肃省','青海省','宁夏回族自治区','内蒙古自治区')) then '西部项目'
when (sysc.c_name = '西藏自治区') then '西藏项目'
when (sysc.c_name = '新疆维吾尔自治区') then '新疆项目'
else '一般项目' end ) as areaname,
( case when (tpa.c_type = 'general' ) then '一般项目'
when (tpa.c_type = 'post' ) then '后期资助项目'
when (tpa.c_type = 'instp' ) then '基地项目' 
when (tpa.c_type = 'key' ) then '重大攻关项目' 
when (tpa.c_type = 'entrust' ) then '应急委托课题' end) as pt,
to_char(tpe.c_final_audit_date, 'yyyy') as pendyear,
( case when (tpe.C_IS_APPLY_NOEVALU  = 1) then '免鉴定'
when (tpe.C_REVIEW_WAY  = 1) then '通讯评审'
when (tpe.C_REVIEW_WAY  = 2) then '会议评审' end) as pslx,
( case when (tgg.c_status is null) then 0 else tgg.c_status end) as pstatus,
( case when (tpe.C_IS_APPLY_EXCELLE  = 1) then '申请优秀成果' end ) as pexcelle,
to_char(tpmid.c_final_audit_date, 'yyyy') as pmidyear,
( case 
when (tgg.C_STATUS = 2) then (to_number(to_char(tpe.c_final_audit_date, 'YYYY') - to_number(tpa.c_year))) || '年'
when (tgg.C_STATUS <> 2 AND tgg.C_PLAN_END_DATE is not null) then (to_number(to_char(tgg.C_PLAN_END_DATE, 'YYYY')) - to_number(tpa.c_year)) || '年'
else '未知' end) as pendyears
from t_project_application tpa 
LEFT outer join t_system_option sysa on tpa.c_subtype_id = sysa.c_id
LEFT outer join t_system_option sys on tpa.c_research_type_id = sys.c_id
LEFT outer join t_project_granted tgg on tpa.c_id = tgg.c_application_id
left outer join t_project_midinspection tpmid on (tpmid.c_granted_id = tgg.c_id and tpmid.C_FINAL_AUDIT_RESULT = 2 and tpmid.C_FINAL_AUDIT_STATUS = 3)
left outer join t_project_endinspection tpe on (tpe.c_granted_id = tgg.c_id and tpe.C_FINAL_AUDIT_RESULT_END = 2 and tpe.C_FINAL_AUDIT_STATUS = 3)
right outer join t_project_member tpm on (tpm.c_application_id = tpa.c_id and tpm.c_is_director = 1  and tpm.C_MEMBER_SN = 1 and tpm.C_GROUP_NUMBER = 1)
LEFT outer join t_person tp on tp.c_id = tpm.c_member_id
LEFT outer join t_academic taca on taca.c_person_id = tp.c_id
LEFT outer join t_agency ta on ta.c_id = tpa.C_UNIVERSITY_ID
LEFT outer join t_system_option sysc on ta.c_province_id = sysc.c_id
where tp.c_id is not null and tpa.c_type is not null and tpa.c_id <> '4028d88e3615073c013615363ca5000b' and tpa.c_id <> '4028d88a2d354302012d3546cbf97d13' and tpa.c_id<>'4028d898362f06e901362f0cd481002c';--[end]
--更新部分--
--结项状态--
update S_D_PROJECT set S_D_PROJECT.c_is_finished = '已结项' where S_D_PROJECT.c_id in
(select tpg.c_application_id from t_project_endinspection tpm join t_project_granted tpg on  tpg.c_id = tpm.c_granted_id where tpm.C_FINAL_AUDIT_RESULT_END = 2 and tpm.C_FINAL_AUDIT_STATUS = 3);--[end]
update S_D_PROJECT set S_D_PROJECT.c_is_finished = '未结项' where S_D_PROJECT.c_is_finished is null;--[end]
--中检状态--
update S_D_PROJECT set S_D_PROJECT.c_is_passmid = '通过中检' where S_D_PROJECT.c_id in
(select tpg.c_application_id from t_project_midinspection tpm join t_project_granted tpg on tpg.c_id = tpm.c_granted_id where tpm.C_FINAL_AUDIT_RESULT = 2 and tpm.C_FINAL_AUDIT_STATUS = 3);--[end]
update S_D_PROJECT set S_D_PROJECT.c_is_passmid = '未通过中检' where S_D_PROJECT.c_id in
(select tpg.c_application_id from t_project_midinspection tpm join t_project_granted tpg on tpg.c_id = tpm.c_granted_id where tpm.C_FINAL_AUDIT_RESULT = 1 and tpm.C_FINAL_AUDIT_STATUS = 3);--[end]
update S_D_PROJECT set S_D_PROJECT.c_is_passmid = '默认' where S_D_PROJECT.c_is_passmid is null;--[end]
--中检次数--
update S_D_PROJECT set S_D_PROJECT.c_mid_times = '一次中检' where S_D_PROJECT.c_id in
(select tpg.c_application_id  from t_project_midinspection tpm join t_project_granted tpg on tpg.c_id = tpm.c_granted_id group by tpg.c_application_id having count(tpg.c_application_id) = 1);--[end]
update S_D_PROJECT set S_D_PROJECT.c_mid_times = '二次中检' where S_D_PROJECT.c_id in
(select tpg.c_application_id  from t_project_midinspection tpm join t_project_granted tpg on tpg.c_id = tpm.c_granted_id group by tpg.c_application_id having count(tpg.c_application_id) = 2);--[end]
update S_D_PROJECT set S_D_PROJECT.c_mid_times = '零次中检' where S_D_PROJECT.c_mid_times is null;--[end]
--评审类型--
update S_D_PROJECT set C_EVALUATION_TYPE ='默认' where c_is_finished ='已结项' and  C_EVALUATION_TYPE is null;--[end]
--申请优秀成果--
update S_D_PROJECT set c_apply_excelle ='未申请优秀成果' where c_is_finished ='已结项' and c_apply_excelle is null;--[end]
update S_D_PROJECT set c_apply_excelle ='默认' where c_is_finished ='未结项';--[end]

--项目事实表--
insert into s_s_project ( c_id, c_d_project_id, c_d_unit_id, c_d_person_id, c_is_granted,  c_fee)
select 
distinct
tpa.c_id as pid, 
tpa.c_id as pid1, 
ta.c_id as aid,
tp.c_id as peid,
(case when (tgg.c_id is null ) then 0 when (tgg.c_id is not null ) then 1  end ) as g_is_granted,
(case when ( tgg.c_approve_fee is null ) then 0 when (tgg.c_approve_fee is not null ) then tgg.c_approve_fee  end ) as pFee
from t_project_application tpa 
LEFT outer join t_system_option sysa on tpa.c_subtype_id = sysa.c_id
LEFT outer join t_project_granted tgg on tpa.c_id = tgg.c_application_id
left outer join t_project_endinspection tpe on (tpe.c_granted_id = tgg.c_id and tpe.C_FINAL_AUDIT_RESULT_END = 2 and tpe.C_FINAL_AUDIT_STATUS = 3)
right outer join t_project_member tpm on (tpm.c_application_id = tpa.c_id and tpm.c_is_director = 1  and tpm.C_MEMBER_SN = 1 and tpm.C_GROUP_NUMBER = 1)
LEFT outer join t_person tp on tp.c_id = tpm.c_member_id
LEFT outer join t_agency ta on ta.c_id = tpa.C_UNIVERSITY_ID
where tp.c_id is not null and tpa.c_type is not null and tpa.c_id <> '4028d88e3615073c013615363ca5000b' and tpa.c_id <> '4028d88a2d354302012d3546cbf97d13' and tpa.c_id<>'4028d898362f06e901362f0cd481002c';--[end]-- and tpa.c_id <> '4028d88e3615073c013615363ca5000b' and tpa.c_id <> '4028d88a2d354302012d3546cbf97d13' and tpa.c_id<>'4028d898362f06e901362f0cd481002c';--[end]
--更新部分--
update S_S_PROJECT set S_S_PROJECT.C_IS_FINISHED = 1 where S_S_PROJECT.c_id in
(select tpg.c_application_id from t_project_endinspection tpm join t_project_granted tpg on tpg.c_id = tpm.c_granted_id where tpm.C_FINAL_AUDIT_RESULT_END = 2 and tpm.C_FINAL_AUDIT_STATUS = 3);--[end]
update S_S_PROJECT set S_S_PROJECT.C_IS_PASSMID = 1 where S_S_PROJECT.c_id in
(select tpg.c_application_id from t_project_midinspection tpm join t_project_granted tpg on tpg.c_id = tpm.c_granted_id where tpm.C_FINAL_AUDIT_RESULT = 2 and tpm.C_FINAL_AUDIT_STATUS = 3);--[end]
update S_S_PROJECT set S_S_PROJECT.C_IS_FINISHED_NOEVALU = 1 where S_S_PROJECT.c_id in
(select tpg.c_application_id from t_project_endinspection tpm join t_project_granted tpg on tpg.c_id = tpm.c_granted_id where tpm.C_FINAL_AUDIT_RESULT_END = 2 and tpm.C_FINAL_AUDIT_STATUS = 3 and tpm.C_IS_APPLY_NOEVALU = 1);--[end]
update S_S_PROJECT set S_S_PROJECT.C_IS_FINISHED_EVALU = 1 where S_S_PROJECT.c_id in
(select tpg.c_application_id from t_project_endinspection tpm join t_project_granted tpg on tpg.c_id = tpm.c_granted_id where tpm.C_FINAL_AUDIT_RESULT_END = 2 and tpm.C_FINAL_AUDIT_STATUS = 3 and tpm.C_IS_APPLY_NOEVALU <> 1);--[end]
update S_S_PROJECT set S_S_PROJECT.C_IS_FINISHED_EXCELLE = 1 where S_S_PROJECT.c_id in
(select tpg.c_application_id from t_project_endinspection tpm join t_project_granted tpg on tpg.c_id = tpm.c_granted_id where tpm.C_FINAL_AUDIT_RESULT_END = 2 and tpm.C_FINAL_AUDIT_STATUS = 3 and tpm.C_IS_APPLY_EXCELLE = 1);--[end]
update S_S_PROJECT set S_S_PROJECT.C_IS_FINISHED_PAPER = 1 where S_S_PROJECT.c_d_project_id in
(select sdp.c_id from s_d_project sdp where sdp.c_product_type like '%论文%' and sdp.c_product_type not like '%著作%');--[end]
update S_S_PROJECT set S_S_PROJECT.C_IS_FINISHED_CONSULTATION = 1 where S_S_PROJECT.c_d_project_id in
(select sdp.c_id from s_d_project sdp where sdp.c_product_type like '%研究咨询报告%' and sdp.c_product_type not like '%著作%');--[end]
update S_S_PROJECT set S_S_PROJECT.C_IS_FINISHED_BOOK = 1 where S_S_PROJECT.c_d_project_id in
(select sdp.c_id from s_d_project sdp where sdp.c_product_type like '%著作%');--[end]

--奖励维表--
insert into s_d_award (c_id, c_name, c_type, c_grade, c_discipline, c_year, c_session, c_score)
select 
tag.c_id as a_id, 
taa.c_product_name as a_name,
(case 
when(tp.c_type = 'paper') then '论文' 
when(tp.c_type = 'book') then '著作' 
when(tp.c_type = 'consultation') then '研究咨询报告' 
when(tp.c_type = 'electronic') then '电子出版物'  
else '其他' end) as a_type,
sysa.c_name as a_grade,
taa.c_discipline_type as a_discipline_type,
tag.c_year as a_year,
'第' ||  to_char(tag.c_session) || '届' as a_session,
(case 
when(sysa.c_name = '特等奖') then 4 
when(sysa.c_name = '一等奖') then 3 
when(sysa.c_name = '二等奖') then 2 
when(sysa.c_name = '三等奖') then 1 
else 0 end) as a_score 
from  t_award_granted tag
left outer join t_award_application taa on tag.c_application_id = taa.c_id
left outer join t_product tp on  tp.c_id = taa.C_product_ID
left outer join t_system_option sysa on sysa.c_id = tag.c_grade_id;--[end]

--奖励事实表--
insert into s_s_award(c_id, c_d_award_id, c_d_unit_id, c_score, c_grade, c_session)
select 
tag.c_id as c_id, 
tag.c_id as c_d_award_id, 
taa.c_university_id as c_d_unit_id, 
sda.c_score as c_scroe,
sda.c_grade as c_grade,
'第' ||  to_char(tag.c_session) || '届' as a_session
 from t_award_granted tag
 left outer join t_award_application taa on taa.c_id = tag.c_application_id
 left outer join s_d_award sda on sda.c_id = tag.c_id;--[end]

--机构事实表--
insert into s_s_unit(c_id, c_d_unit_id, c_type)
select
s_d_unit.c_id as c1,
s_d_unit.c_id as c2,
s_d_unit.c_type as c3
from s_d_unit;--[end]

--成果维表--
insert into s_d_product(c_id, c_name, c_type, c_discipline)
select 
tp.c_id as cid,
tp.c_chinese_name as cname,
(case 
when(tp.c_type = 'paper') then '论文' 
when(tp.c_type = 'book') then '著作' 
when(tp.c_type = 'consultation') then '研究咨询报告' 
when(tp.c_type = 'electronic') then '电子出版物'  
when(tp.c_type = 'patent') then '专利'  
else '其他成果' end) as a_type,
tp.c_discipline_type as a_discipline_type
from t_product tp where tp.c_university_id is not null;--[end]

--成果事实表--
insert into s_s_product(c_id, C_D_PRODUCT_ID, C_D_UNIT_ID, c_type)
select 
tp.c_id as cid,
tp.c_id as cid1,
tp.c_university_id as u_id,
(case 
when(tp.c_type = 'paper') then '论文' 
when(tp.c_type = 'book') then '著作' 
when(tp.c_type = 'consultation') then '研究咨询报告' 
when(tp.c_type = 'electronic') then '电子出版物'  
when(tp.c_type = 'patent') then '专利'  
else '其他成果' end) as a_type
from t_product tp where tp.c_university_id is not null;--[end]

--最后更新--
update S_D_PROJECT set c_discipline_type = replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(c_discipline_type,' ',''), '　', ''), '.', ''),'1',''),'2',''),'3',''),'4',''),'5',''),'6',''),'7',''),'8',''),'9',''),'0','');--[end]
update S_D_PROJECT set c_discipline_type = '综合研究/交叉学科' where c_discipline_type like '%交叉%' or c_discipline_type like '%综合%';--[end]
update S_D_PROJECT set c_discipline_type = '马克思主义理论/思想政治教育' where c_discipline_type like '%马克思%' or c_discipline_type like '%思想%';--[end]
update s_d_award set c_discipline = '马克思主义理论/思想政治教育' where c_discipline = '马克思主义';--[end]

update s_d_person set c_last_degree = '博士' where c_last_degree like '%博%';--[end]
update s_d_person set c_last_degree = '硕士' where c_last_degree like '%研究生%' or c_last_degree like '%硕%';--[end]
update s_d_person set c_last_degree = '学士' where c_last_degree like '%本科%' or c_last_degree like '%学士%' or c_last_degree like '%大学%';--[end]
update s_d_person set c_last_degree = null where c_last_degree <> '博士' and c_last_degree <> '硕士' and c_last_degree <> '学士' and c_last_degree <> '其他';--[end]
update s_d_person set C_LAST_EDUCATION = '博士研究生' where C_LAST_EDUCATION like '%博%';--[end]
update s_d_person set C_LAST_EDUCATION = '硕士研究生' where C_LAST_EDUCATION <> '博士研究生' and (C_LAST_EDUCATION like '%研究生%' or C_LAST_EDUCATION like '%硕%');--[end]
update s_d_person set C_LAST_EDUCATION = '本科' where C_LAST_EDUCATION like '%本科%' or C_LAST_EDUCATION like '%学士%' or C_LAST_EDUCATION like '%大学%';--[end]
update s_d_person set C_LAST_EDUCATION = '大专' where C_LAST_EDUCATION like '%大专%';--[end]
update s_d_person set C_LAST_EDUCATION = '中专' where C_LAST_EDUCATION like '%中专%';--[end]