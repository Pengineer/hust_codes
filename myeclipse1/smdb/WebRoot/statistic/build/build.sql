------------------先确定数据是否有问题，是否违反一致性/唯一性约束------------------
--确定是否有一条立项项目对应多条申请项目
--select c_application_id from t_project_granted group by c_application_id having count(c_application_id)>1;

--确定是否有项目存在同一组中编号都为一的多个负责人
--select c_application_id from t_project_member where c_member_sn = 1 and c_group_number = 1 group by c_application_id having count(c_application_id)>1;

------------------先删除之前的数据------------------
truncate table s_s_award;--[end]
truncate table s_s_project;--[end]
truncate table s_s_person;--[end]
truncate table s_s_unit;--[end]
truncate table s_s_product;--[end]
truncate table s_d_award;--[end]
truncate table s_d_project;--[end]
truncate table s_d_person;--[end]
truncate table s_d_unit;--[end]
truncate table s_d_product;--[end]

--机构维表--
insert into s_d_unit (c_id, c_name, c_style, c_category, c_province, c_type, c_organizer)
select 
distinct t_agency.c_id as a_id, 
t_agency.c_name as a_name,
t_agency.c_style as a_style,
t_agency.c_category as a_category,
t_system_option.c_name as a_province,
(case when (t_agency.c_type = 1) then '部级' 
when (t_agency.c_type = 2) then '省级'
when (t_agency.c_type = 3) then '部属高校'
when (t_agency.c_type = 4) then '地方高校'
end) as a_type ,
t_agency.c_organizer as c_organizer 
from t_agency, t_system_option 
where t_agency.c_province_id = t_system_option.c_id;--[end]

--人员事实表--
insert into s_s_person (c_id, c_d_person_id, c_d_unit_id)
select t_project_member.c_member_id, t_project_member.c_member_id, max(t_project_member.c_university_id) from t_project_member  where t_project_member.c_university_id is not null group by(t_project_member.c_member_id) having (t_project_member.c_member_id is not null);--[end]

--人员维表--
insert into s_d_person (c_id, c_name, c_gender, c_age_group, c_title, c_last_degree, c_last_education, c_tutor_type, c_discipline_type)
select 
t_person.c_id as p_id, 
t_person.c_name as p_name, 
t_person.c_gender as p_gender, (case 
when((to_number(to_char(sysdate, 'yyyy')) - to_number(to_char(t_person.c_birthday, 'yyyy'))) <= 30) then '30岁及以下' 
when((to_number(to_char(sysdate, 'yyyy')) - to_number(to_char(t_person.c_birthday, 'yyyy'))) > 30) and ((to_number(to_char(sysdate, 'yyyy')) - to_number(to_char(t_person.c_birthday, 'yyyy'))) <= 35) then '31-35岁' 
when((to_number(to_char(sysdate, 'yyyy')) - to_number(to_char(t_person.c_birthday, 'yyyy'))) > 35) and ((to_number(to_char(sysdate, 'yyyy')) - to_number(to_char(t_person.c_birthday, 'yyyy'))) <= 40) then '36-40岁'
when((to_number(to_char(sysdate, 'yyyy')) - to_number(to_char(t_person.c_birthday, 'yyyy'))) > 40) and ((to_number(to_char(sysdate, 'yyyy')) - to_number(to_char(t_person.c_birthday, 'yyyy'))) <= 45) then '41-45岁'
when((to_number(to_char(sysdate, 'yyyy')) - to_number(to_char(t_person.c_birthday, 'yyyy'))) > 45) and ((to_number(to_char(sysdate, 'yyyy')) - to_number(to_char(t_person.c_birthday, 'yyyy'))) <= 50) then '46-50岁'
when((to_number(to_char(sysdate, 'yyyy')) - to_number(to_char(t_person.c_birthday, 'yyyy'))) > 50) and ((to_number(to_char(sysdate, 'yyyy')) - to_number(to_char(t_person.c_birthday, 'yyyy'))) <= 55) then '51-55岁'
when((to_number(to_char(sysdate, 'yyyy')) - to_number(to_char(t_person.c_birthday, 'yyyy'))) > 55) and ((to_number(to_char(sysdate, 'yyyy')) - to_number(to_char(t_person.c_birthday, 'yyyy'))) <= 60) then '56-60岁'
when((to_number(to_char(sysdate, 'yyyy')) - to_number(to_char(t_person.c_birthday, 'yyyy'))) > 60) then '60岁以上' 
end ) as p_age, 
t_academic.c_speciality_title as p_title,
( case when (t_academic.c_last_degree like '%博士%') then '博士'
when (t_academic.c_last_degree like '%硕士%') then '硕士'
when (t_academic.c_last_degree like '%学士%') then '学士' 
when (t_academic.c_last_degree is null ) then '' 
else '其他' end) as p_degree,
t_academic.c_last_education as c_last_education,
t_academic.c_tutor_type as c_tutor_type,
t_academic.c_discipline_type as p_dt
from s_s_person left outer join t_person  on (s_s_person.c_id = t_person.c_id) left outer join  t_academic on t_person.c_id = t_academic.c_person_id where t_person.c_id is not null;--[end]
--更新部分--
--是否项目负责人--
update s_d_person set s_d_person.c_is_director = '项目负责人' where s_d_person.c_id in (select distinct pm.c_member_id from t_project_member pm where pm.c_is_director = 1);--[end]
update s_d_person set s_d_person.c_is_director = '非项目负责人' where s_d_person.c_is_director is null;--[end]
--是否项目成员--
update s_d_person set s_d_person.c_is_member = '项目成员' where s_d_person.c_id in (select distinct pm.c_member_id from t_project_member pm);--[end]
update s_d_person set s_d_person.c_is_member = '非项目成员' where s_d_person.c_is_member is null;--[end]
--参与项目类型--
update s_d_person set s_d_person.c_project_type = concat(s_d_person.c_project_type, '; 一般项目') where s_d_person.c_id in (select distinct pm.c_member_id from t_project_member pm where pm.c_project_type = 'general');--[end]
update s_d_person set s_d_person.c_project_type = concat(s_d_person.c_project_type, '; 基地项目') where s_d_person.c_id in (select distinct pm.c_member_id from t_project_member pm where pm.c_project_type = 'instp');--[end]
update s_d_person set s_d_person.c_project_type = concat(s_d_person.c_project_type, '; 重大攻关项目') where s_d_person.c_id in (select distinct pm.c_member_id from t_project_member pm where pm.c_project_type = 'key');--[end]
update s_d_person set s_d_person.c_project_type = concat(s_d_person.c_project_type, '; 后期资助项目') where s_d_person.c_id in (select distinct pm.c_member_id from t_project_member pm where pm.c_project_type = 'post');--[end]
update s_d_person set s_d_person.c_project_type = concat(s_d_person.c_project_type, '; 委托应急课题') where s_d_person.c_id in (select distinct pm.c_member_id from t_project_member pm where pm.c_project_type = 'entrust');--[end]
update s_d_person set s_d_person.c_project_type = concat(s_d_person.c_project_type, '; 专项任务项目') where s_d_person.c_id in (select distinct pm.c_member_id from t_project_member pm where pm.c_project_type = 'special');--[end]
update s_d_person set s_d_person.c_project_type = concat(s_d_person.c_project_type, '; 发展报告项目') where s_d_person.c_id in (select distinct pm.c_member_id from t_project_member pm where pm.c_project_type = 'devrpt');--[end]
update s_d_person set s_d_person.c_project_type = substr(s_d_person.c_project_type, 3) where s_d_person.c_project_type is not null and s_d_person.c_project_type like '; %';--[end]
--最后学历--
update s_d_person set c_last_education = '博士研究生' where c_last_education like '%博%';--[end]
update s_d_person set c_last_education = '硕士研究生' where c_last_education <> '博士研究生' and (c_last_education like '%研究生%' or c_last_education like '%硕%');--[end]
update s_d_person set c_last_education = '本科' where c_last_education like '%本科%' or c_last_education like '%学士%' or c_last_education like '%大学%';--[end]
update s_d_person set c_last_education = '大专' where c_last_education like '%大专%';--[end]
update s_d_person set c_last_education = '中专' where c_last_education like '%中专%';--[end]

--项目维表--
insert into s_d_project ( c_id, c_name, c_product_type, c_subtype, c_discipline_type, c_research_type, c_year, c_fee, c_is_granted, c_director_agegroup, c_director_gender, c_director_degree, c_area_type, c_project_type, c_end_year, c_evaluation_type, c_status, c_apply_excelle, c_mid_year, c_end_years)
select 
distinct
tpa.c_id as pid, 
tpa.c_name as pname, 
rtrim((case when (tpa.c_product_type like '%论文%') then '论文; ' else '' end)
|| (case when (tpa.c_product_type like '%著%') then '著作; ' else '' end)
|| (case when (tpa.c_product_type like '%报告%') then '研究咨询报告; ' else '' end)
|| (case when (tpa.c_product_type like '%专利%') then '专利; ' else '' end)
|| (case when (tpa.c_product_type like '%电子%') then '电子出版物; ' else '' end), '; ') as producttype,
sysa.c_name as subt,
tpa.c_discipline_type as pdt,
sys.c_name as researchtype,
tpa.c_year as pyear,
(case when ( tgg.c_approve_fee is null ) then 0 when (tgg.c_approve_fee is not null ) then tgg.c_approve_fee  end ) as pfee,
(case when (tgg.c_id is null ) then '未立项' when (tgg.c_id is not null ) then '已立项'  end ) as g_is_granted,
(case 
when((to_number(tpa.c_year) - to_number(to_char(tp.c_birthday, 'yyyy'))) <= 30) then '30岁及以下' 
when((to_number(tpa.c_year) - to_number(to_char(tp.c_birthday, 'yyyy'))) > 30 and ((to_number(tpa.c_year)) - to_number(to_char(tp.c_birthday, 'yyyy'))) <= 35) then '31-35岁' 
when((to_number(tpa.c_year) - to_number(to_char(tp.c_birthday, 'yyyy'))) > 35 and ((to_number(tpa.c_year)) - to_number(to_char(tp.c_birthday, 'yyyy'))) <= 40) then '36-40岁'
when((to_number(tpa.c_year) - to_number(to_char(tp.c_birthday, 'yyyy'))) > 40 and ((to_number(tpa.c_year)) - to_number(to_char(tp.c_birthday, 'yyyy'))) <= 45) then '41-45岁'
when((to_number(tpa.c_year) - to_number(to_char(tp.c_birthday, 'yyyy'))) > 45 and ((to_number(tpa.c_year)) - to_number(to_char(tp.c_birthday, 'yyyy'))) <= 50) then '46-50岁'
when((to_number(tpa.c_year) - to_number(to_char(tp.c_birthday, 'yyyy'))) > 50 and ((to_number(tpa.c_year)) - to_number(to_char(tp.c_birthday, 'yyyy'))) <= 55) then '51-55岁'
when((to_number(tpa.c_year) - to_number(to_char(tp.c_birthday, 'yyyy'))) > 55 and ((to_number(tpa.c_year)) - to_number(to_char(tp.c_birthday, 'yyyy'))) <= 60) then '56-60岁'
when((to_number(tpa.c_year) - to_number(to_char(tp.c_birthday, 'yyyy'))) > 60) then '60岁以上' 
end ) as age,
tp.c_gender as dgender,
( case when (taca.c_last_degree like '%博士%') then '博士'
when (taca.c_last_degree like '%硕士%') then '硕士'
when (taca.c_last_degree like '%学士%') then '学士' 
when (taca.c_last_degree is null ) then '' 
else '其他' end) as ddegree,
(case 
when sysc.c_name in ('重庆市','四川省','贵州省','云南省','广西壮族自治区','陕西省','甘肃省','青海省','宁夏回族自治区','内蒙古自治区') then '西部项目'
when (sysc.c_name = '西藏自治区') then '西藏项目'
when (sysc.c_name = '新疆维吾尔自治区') then '新疆项目'
else '一般项目' end ) as areaname,
( case when (tpa.c_type = 'general' ) then '一般项目'
when (tpa.c_type = 'special' ) then '专项任务项目'
when (tpa.c_type = 'devrpt' ) then '发展报告项目'
when (tpa.c_type = 'post' ) then '后期资助项目'
when (tpa.c_type = 'instp' ) then '基地项目' 
when (tpa.c_type = 'key' ) then '重大攻关项目' 
when (tpa.c_type = 'entrust' ) then '委托应急课题' end) as pt,
to_char(tpe.c_final_audit_date, 'yyyy') as pendyear,
( case when (tpe.c_is_apply_noevalu  = 1) then '免鉴定'
when (tpe.c_review_way  = 1) then '通讯评审'
when (tpe.c_review_way  = 2) then '会议评审' end) as pslx,
( case when (tgg.c_status is null) then 0 else tgg.c_status end) as pstatus,
( case when (tpe.c_is_apply_excelle  = 1) then '申请优秀成果' end ) as pexcelle,
to_char(tpmid.c_final_audit_date, 'yyyy') as pmidyear,
( case 
when (tgg.c_status = 2) then (to_number(to_char(tpe.c_final_audit_date, 'yyyy') - to_number(tpa.c_year))) || '年'
when (tgg.c_status <> 2 and tgg.c_plan_end_date is not null) then (to_number(to_char(tgg.c_plan_end_date, 'yyyy')) - to_number(tpa.c_year)) || '年'
else '未知' end) as pendyears
from t_project_application tpa 
left outer join t_system_option sysa on tpa.c_subtype_id = sysa.c_id
left outer join t_system_option sys on tpa.c_research_type_id = sys.c_id
left outer join t_project_granted tgg on tpa.c_id = tgg.c_application_id
left outer join t_project_midinspection tpmid on (tpmid.c_granted_id = tgg.c_id and tpmid.c_final_audit_result = 2 and tpmid.c_final_audit_status = 3)
left outer join t_project_endinspection tpe on (tpe.c_granted_id = tgg.c_id and tpe.c_final_audit_result_end = 2 and tpe.c_final_audit_status = 3)
left outer join t_project_member tpm on (tpm.c_application_id = tpa.c_id and tpm.c_is_director = 1  and tpm.c_member_sn = 1 and tpm.c_group_number = 1)
left outer join t_person tp on tp.c_id = tpm.c_member_id
left outer join t_academic taca on taca.c_person_id = tp.c_id
left outer join t_agency ta on ta.c_id = tpa.c_university_id
left outer join t_system_option sysc on ta.c_province_id = sysc.c_id
where tp.c_id is not null and tpa.c_type is not null;--[end]
--更新部分--
--结项状态--
update s_d_project set s_d_project.c_is_finished = '已结项' where s_d_project.c_id in
(select tpg.c_application_id from t_project_endinspection tpm join t_project_granted tpg on  tpg.c_id = tpm.c_granted_id where tpm.c_final_audit_result_end = 2 and tpm.c_final_audit_status = 3);--[end]
update s_d_project set s_d_project.c_is_finished = '未结项' where s_d_project.c_is_finished is null;--[end]
--中检状态--
update s_d_project set s_d_project.c_is_passmid = '通过中检' where s_d_project.c_id in
(select tpg.c_application_id from t_project_midinspection tpm join t_project_granted tpg on tpg.c_id = tpm.c_granted_id where tpm.c_final_audit_result = 2 and tpm.c_final_audit_status = 3);--[end]
update s_d_project set s_d_project.c_is_passmid = '未通过中检' where s_d_project.c_id in
(select tpg.c_application_id from t_project_midinspection tpm join t_project_granted tpg on tpg.c_id = tpm.c_granted_id where tpm.c_final_audit_result = 1 and tpm.c_final_audit_status = 3);--[end]
update s_d_project set s_d_project.c_is_passmid = '默认' where s_d_project.c_is_passmid is null;--[end]
--中检次数--
update s_d_project set s_d_project.c_mid_times = '一次中检' where s_d_project.c_id in
(select tpg.c_application_id  from t_project_midinspection tpm join t_project_granted tpg on tpg.c_id = tpm.c_granted_id group by tpg.c_application_id having count(tpg.c_application_id) = 1);--[end]
update s_d_project set s_d_project.c_mid_times = '二次中检' where s_d_project.c_id in
(select tpg.c_application_id  from t_project_midinspection tpm join t_project_granted tpg on tpg.c_id = tpm.c_granted_id group by tpg.c_application_id having count(tpg.c_application_id) = 2);--[end]
update s_d_project set s_d_project.c_mid_times = '零次中检' where s_d_project.c_mid_times is null;--[end]
--评审类型--
update s_d_project set c_evaluation_type ='默认' where c_is_finished ='已结项' and  c_evaluation_type is null;--[end]
--申请优秀成果--
update s_d_project set c_apply_excelle ='未申请优秀成果' where c_is_finished ='已结项' and c_apply_excelle is null;--[end]
update s_d_project set c_apply_excelle ='默认' where c_is_finished ='未结项';--[end]
--学科门类--
--update s_d_project set c_discipline_type = replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(c_discipline_type,' ',''), '　', ''), '.', ''),'1',''),'2',''),'3',''),'4',''),'5',''),'6',''),'7',''),'8',''),'9',''),'0','');--[end]
--update s_d_project set c_discipline_type = '综合研究/交叉学科' where c_discipline_type like '%交叉%' or c_discipline_type like '%综合%';--[end]
--update s_d_project set c_discipline_type = '马克思主义理论/思想政治教育' where c_discipline_type like '%马克思%' or c_discipline_type like '%思想%';--[end]

--项目事实表--
insert into s_s_project ( c_id, c_d_project_id, c_d_unit_id, c_d_person_id, c_is_granted,  c_fee)
select 
distinct
tpa.c_id as pid, 
tpa.c_id as pid1, 
ta.c_id as aid,
tp.c_id as peid,
(case when (tgg.c_id is null ) then 0 when (tgg.c_id is not null ) then 1  end ) as g_is_granted,
(case when ( tgg.c_approve_fee is null ) then 0 when (tgg.c_approve_fee is not null ) then tgg.c_approve_fee  end ) as pfee
from t_project_application tpa 
left outer join t_system_option sysa on tpa.c_subtype_id = sysa.c_id
left outer join t_project_granted tgg on tpa.c_id = tgg.c_application_id
left outer join t_project_endinspection tpe on (tpe.c_granted_id = tgg.c_id and tpe.c_final_audit_result_end = 2 and tpe.c_final_audit_status = 3)
right outer join t_project_member tpm on (tpm.c_application_id = tpa.c_id and tpm.c_is_director = 1  and tpm.c_member_sn = 1 and tpm.c_group_number = 1)
left outer join t_person tp on tp.c_id = tpm.c_member_id
left outer join t_agency ta on ta.c_id = tpa.c_university_id
where tp.c_id is not null and tpa.c_type is not null;--[end]
--更新部分--
update s_s_project set s_s_project.c_is_finished = 1 where s_s_project.c_id in
(select tpg.c_application_id from t_project_endinspection tpm join t_project_granted tpg on tpg.c_id = tpm.c_granted_id where tpm.c_final_audit_result_end = 2 and tpm.c_final_audit_status = 3);--[end]
update s_s_project set s_s_project.c_is_passmid = 1 where s_s_project.c_id in
(select tpg.c_application_id from t_project_midinspection tpm join t_project_granted tpg on tpg.c_id = tpm.c_granted_id where tpm.c_final_audit_result = 2 and tpm.c_final_audit_status = 3);--[end]
update s_s_project set s_s_project.c_is_finished_noevalu = 1 where s_s_project.c_id in
(select tpg.c_application_id from t_project_endinspection tpm join t_project_granted tpg on tpg.c_id = tpm.c_granted_id where tpm.c_final_audit_result_end = 2 and tpm.c_final_audit_status = 3 and tpm.c_is_apply_noevalu = 1);--[end]
update s_s_project set s_s_project.c_is_finished_evalu = 1 where s_s_project.c_id in
(select tpg.c_application_id from t_project_endinspection tpm join t_project_granted tpg on tpg.c_id = tpm.c_granted_id where tpm.c_final_audit_result_end = 2 and tpm.c_final_audit_status = 3 and tpm.c_is_apply_noevalu <> 1);--[end]
update s_s_project set s_s_project.c_is_finished_excelle = 1 where s_s_project.c_id in
(select tpg.c_application_id from t_project_endinspection tpm join t_project_granted tpg on tpg.c_id = tpm.c_granted_id where tpm.c_final_audit_result_end = 2 and tpm.c_final_audit_status = 3 and tpm.c_is_apply_excelle = 1);--[end]
update s_s_project set s_s_project.c_is_finished_paper = 1 where s_s_project.c_d_project_id in
(select sdp.c_id from s_d_project sdp where sdp.c_product_type like '%论文%' and sdp.c_product_type not like '%著作%');--[end]
update s_s_project set s_s_project.c_is_finished_consultation = 1 where s_s_project.c_d_project_id in
(select sdp.c_id from s_d_project sdp where sdp.c_product_type like '%研究咨询报告%' and sdp.c_product_type not like '%著作%');--[end]
update s_s_project set s_s_project.c_is_finished_book = 1 where s_s_project.c_d_project_id in
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
when(tp.c_type = 'patent') then '专利' 
when(tp.c_type = 'otherproduct') then '其他' end) as a_type,
sysa.c_name as a_grade,
taa.c_discipline_type as a_discipline_type,
tag.c_year as a_year,
'第' ||  to_char(tag.c_session) || '届' as a_session,
(case 
when(sysa.c_name = '特等奖') then 4 
when(sysa.c_name = '一等奖') then 3 
when(sysa.c_name = '二等奖') then 2 
when(sysa.c_name = '三等奖') then 1
when(sysa.c_name = '普及奖') then 0 end) as a_score 
from  t_award_granted tag
left outer join t_award_application taa on tag.c_application_id = taa.c_id
left outer join t_product tp on  tp.c_id = taa.c_product_id
left outer join t_system_option sysa on sysa.c_id = tag.c_grade_id;--[end]
--更新部分--
update s_d_award set c_discipline = '马克思主义/思想政治教育' where c_discipline = '马克思主义';--[end]

--奖励事实表--
insert into s_s_award(c_id, c_d_award_id, c_d_unit_id, c_score, c_grade, c_session)
select 
tag.c_id as c_id, 
tag.c_id as c_d_award_id, 
tag.c_university_id as c_d_unit_id, 
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
when(tp.c_type = 'otherproduct') then '其他成果' end) as a_type,
tp.c_discipline_type as a_discipline_type
from t_product tp where tp.c_university_id is not null;--[end]

--成果事实表--
insert into s_s_product(c_id, c_d_product_id, c_d_unit_id, c_type)
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
when(tp.c_type = 'otherproduct') then '其他成果' end) as a_type
from t_product tp where tp.c_university_id is not null;--[end]