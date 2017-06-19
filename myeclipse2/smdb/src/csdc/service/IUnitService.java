package csdc.service;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;

import com.opensymphony.xwork2.ActionSupport;

import csdc.bean.Account;
import csdc.bean.Agency;
import csdc.bean.Department;
import csdc.bean.Institute;
import csdc.bean.Person;
import csdc.tool.InputValidate;

public interface IUnitService extends IBaseService {
	
	/**
	 * 获取教育部的id(仅根据机构代码为360)
	 * @return
	 */
	public String getMOEId();
	
	/**
	 * 获取教育部对象(仅根据机构代码为360)
	 * @return
	 */
	public Agency getMOE();
	
	/**
	 * 管理机构的查看（用于部级、省级、校级）
	 * @param entityId	机构ID
	 * @param jsonMap	返回的结果
	 */
	public void getViewOfAgency(String entityId, Map jsonMap);
	
	/**
	 * 院系管理机构的查看
	 * @param entityId	院系ID
	 * @param jsonMap	返回的结果
	 */
	public void getViewOfDepartment(String entityId, Map jsonMap);
	
	/**
	 * 基地机构的查看
	 * @param entityId	基地ID
	 * @param jsonMap	返回的结果
	 */
	public void getViewOfInstitute(String entityId, Map jsonMap);
	
	/**
	 * 部、省、校级管理机构的修改
	 * @param session
	 * @param agency
	 * @param directorId
	 * @param slinkmanId
	 * @param sdirectorId
	 * @param univOrganizerCode
	 * @param univOrganizer
	 * @param entityId
	 */
	public void getModifyOfAgency(Map session, Agency agency, String directorId, String slinkmanId, String sdirectorId, String univOrganizerCode, String univOrganizer, String entityId);
	
	/**
	 * 部、省、校级管理机构的修改校验
	 * @param agency
	 * @param inputValidate
	 * @param thisAction
	 */
	public void getValidateModifyOfAgency(Agency agency, InputValidate inputValidate, ActionSupport thisAction);
	
	/**
	 * 院系机构的修改
	 * @param session
	 * @param department
	 * @param directorId
	 * @param linkmanId
	 * @param entityId
	 */
	public void getModifyOfDepartment(Map session, Department department, String directorId, String linkmanId, String entityId);
	
	/**
	 * 院系修改校验
	 * @param department
	 * @param inputValidate
	 * @param thisAction
	 */
	public void getValidateModifyOfDepartment(Department department, InputValidate inputValidate, ActionSupport thisAction);
	
	/**
	 * 基地机构的修改
	 * @param session
	 * @param institute
	 * @param directorId
	 * @param linkmanId
	 * @retrun entityId
	 */
	public void getModifyOfInstitute(Map session, Institute institute, String directorId, String linkmanId, String entityId);
	
	/**
	 * 基地修改校验
	 * @param institute
	 * @param inputValidate
	 * @param thisAction
	 */
	public void getValidateModifyOfInstitute(Institute institute, InputValidate inputValidate, ActionSupport thisAction);
	/**
	 * 判断某账号是否能添加某类管理机构
	 * @param current 当前账号
	 * @param agencyType 管理机构类型1:部级，2:省级，3:校级
	 * @param agencyId 管理机构id,添加操作时为null
	 * @param subjectionId 上级机构id,主要针对校级，其它可置为null
	 * @param currentBelongUnitId 账号所在单位
	 * @return
	 */
	public boolean checkAgencyLeadByAccount(Account current,int agencyType,String agencyId,String subjectionId,String currentBelongUnitId);

	/**
	 * 由officerid获取对应人员对象
	 * @param offid
	 * @return
	 */
	public Person getPersonByOfficerId(String offid);
	/**
	 * 由personId获取对应officerId
	 * @param id
	 * @return
	 */
	public String getOfficerIdByPersonId(String id);

	/**
	 * 高级检索
	 * @param str
	 * @param director
	 * @param sDirector
	 * @param agency
	 * @return
	 */
	public Object[] advSearchAgency(String str,String aName,String aCode,String directorName,String type, String provinceId,String aSname,String sDirectorName,String pageName);
	
	/**
	 * 判断单位(各类管理机构或研究基地)名称是否已经存在
	 * @param objectName 对象名（如Agency)
	 * @param id 对象编号：添加时以null代替，编辑时即为该对象的编号
	 * @param name 单位名称
	 * @return true:不存在，false:存在
	 */
	public boolean checkUnitNameUnique(String objectName,String id,String name);
	
	/**
	 * 判断研究基地名称是否已经存在
	 * @param objectName 对象名（如Institute)
	 * @param id 对象编号：添加时以null代替，编辑时即为该对象的编号
	 * @param name 单位名称
	 * @param subjectionId 上级单位id
	 * @return true:不存在，false:存在
	 */
	public boolean checkInsNameUnique(String objectName,String id,String name,String subjectionId);
	
	/**
	 * 由学校id获取院系列表
	 * @param univId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getDeptsByUnivId(String univId);

	/**
	 * 获取院系数目
	 * @param univId
	 * @return
	 */
	public String getDeptNumByUnivId(String univId);

	/**
	 * 根据编号组成的数组删除管理机构
	 * @param ids
	 * @return true：删除成功	false：不能删除
	 */
	public int deleteAgencyByIds(int type,List<String> entityIds) ;

	/**
	 * 根据机构编号获取机构主账号对象
	 * @param str 机构编号
	 * @type 1:agency; 2:department; 3:institute
	 * @return 主账号对象
	 */
	public Account getAccountByUnitId(String str, int type);

	/**
	 * 生成学科树
	 * @param id 根学科编号
	 * @return
	 */
	public Document createDisciplineXML(String id);

	//--------------------------------------------- 院系部分代码 -----------------------------------------------

	/**
	 * 判断某院系是否在指定账号的管理范围
	 * @param department
	 * @param current 当前用户账号
	 * @param currentBelongUnitId 账号所在单位
	 * @return
	 */
	public boolean checkDepartmentLeadByAccount(Department department,Account current,String currentBelongUnitId);
	
	/**
	 * 根据编号组成的数组删除院系
	 * @param entityClass
	 * @param ids
	 * @return true：删除成功	false：不能删除
	 */
	public int deleteDeptByIds(List<String> ids);
	
	/**
	 * 将income院系的信息合并至target，将income的子表指向target(，并删除income)
	 * @param targetDept 合并后保留的院系(PO)
	 * @param incomeDept 合并后删除的院系(PO)
	 * @throws IntrospectionException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public void mergeDepartment(Department targetDept, List<Department> incomeDept) throws IntrospectionException, IllegalArgumentException, IllegalAccessException, InvocationTargetException;	
	public void mergeDepartment(Department targetDept, Department incomeDept) throws IntrospectionException, IllegalArgumentException, IllegalAccessException, InvocationTargetException;	
	
	/**
	 * 根据院系id列表合并院系
	 * @param ids
	 */
	@Deprecated
	public void mergeDepts(List<String> ids);

	/**
	 * 将income基地的信息合并至target，将income的子表指向target(，并删除income)
	 * @param targetInst 合并后保留的基地(PO)
	 * @param incomeInst合并后删除的基地(PO)
	 * @throws IntrospectionException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public void mergeInstitute(Institute targetInst, List<Institute> incomeInst) throws IntrospectionException, IllegalArgumentException, IllegalAccessException, InvocationTargetException;	
	public void mergeInstitute(Institute targetInst, Institute incomeInst) throws IntrospectionException, IllegalArgumentException, IllegalAccessException, InvocationTargetException;	

	/**
	 * 判断多个院系是否同属于一个学校
	 * @param ids
	 * @return
	 */
	public boolean checkDeptsInSameUniv(List<String> ids);

	/**
	 * 判断指定学校是否由指定省指定省管辖
	 * @param unividid 学校编号
	 * @param Province 省编号
	 * @return true:指定学校有指定省管辖;false:指定学校不由指定省管辖
	 */
	public boolean checkUniversityLeadByProvince(String univid,String Province);

	// ---------------------------------研究机构部分-------------------------------------
	/**
	 * 由id字符串获取选项表中的名称字符串
	 * @param ids
	 * @return
	 */
	public String getNamesByIds(String ids);

	/**
	 * 根据编号组成的数组删除研究机构
	 * @param ids
	 * @return true：删除成功	false：不能删除
	 */
	public int deleteInstituteByIds(List<String> ids);

	/**
	 * 根据"id+';'"组成的字符串获取对应的学科代码
	 * @param ids
	 * @return
	 */
	public String getDispCodesByIds(String ids);

	/**
	 * 判断某个研究机构是否在指定账号的管辖范围
	 * @param institute 研究机构对象
	 * @param account 指定账号对象
	 * @param currentBelongUnitId 账号所在单位
	 * @return false:不在,true:在
	 */
	public boolean checkInstituteLeadByAccount(Institute institute,Account account,String currentBelongUnitId);

	/**
	 * 选择研究机构类型时由系统选项表id获取对应的code.
	 * @param id 机构类别id
	 * @return 机构类型编码
	 */
	public String getCodeById(String id);

	/**
	 * 在系统选项表中根据父节点id获取子节点的（id,name)组成的list
	 * @param id
	 * @return
	 */
	public Map<String,String> getSystemOptionById(String id);

	/**
	 * 获取省份列表
	 * @return
	 */
	public Map<String,String> getProvinceList();
	
	/**
	 * 获取研究基地类型列表
	 * @return
	 */
	public Map<String,String> getInstituteType();
	
	/**
	 * 获取一级学科
	 * @return
	 */
	public Map<String,String> getDisciplineOne();

	/**
	 * 由以‘;’相隔的学科id组成的字符串获取对应的学科代码
	 */
	public String getDisciplineCodesByIds(String ids);

	/**
	 * 根据学科"代码/名称; "序列获取”编号%名称%代码；“组成的序列，”%“，”；“分隔符
	 * @param ids
	 * @return
	 */
	public String getDispIdCodeNameByCodeName(String nameCode);

	/**
	 * 在系统选项表中根据父节点id获取子节点的（id,name)组成的list
	 * @param id
	 * @return
	 */
	public Map<String,String> getSystemOptionCodeName(String parentId);

	/**
	 * 根据机构类型和id找到机构下博士点list
	 * @param type 机构类型(3:部属高校 4:地方高校 5:院系 6:研究基地)
	 * @param unitId 机构id
	 * @return doctorialList
	 * @author zhouzj
	 */
	@SuppressWarnings("unchecked")
	public List getDoctorialList(int type, String unitId);
	
	/**
	 * 根据机构类型和id找到机构下重点学科list
	 * @param type 机构类型(3:部属高校 4:地方高校 5:院系 6:研究基地)
	 * @param unitId 机构id
	 * @return doctorialList
	 * @author zhouzj
	 */
	@SuppressWarnings("unchecked")
	public List getDisciplineList(int type, String unitId);
	
	/**
	 * 根据机构类型和id找到机构下重点学科list
	 * @param unitType 机构类型 (1:部属高校 2:地方高校 3:院系 4:研究基地)
	 * @param personType 返回的人员类型 (1:社科管理人员 2:专家 3:教师 4:学生)
	 * @param unitId 机构id
	 * @return 对应类型的人员列表
	 * @author zhouzj
	 */
	@SuppressWarnings("unchecked")
	public List getPersonList(int unitType, int personType, String unitId);
	
	/**
	 * 根据研究基地id找到此研究基地的拨款信息
	 * @param instituteId
	 * @return funding list
	 */
	@SuppressWarnings("unchecked")
	public List getInstituteFunding(String instituteId);
	
	/**
	 * 根据ids字符串获得这些机构的list
	 * @param checkedIds
	 * @param type 1高校 2院系 3基地
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getCheckedList(String checkedIds, Integer type);
	
	/**
	 * 根据研究基地编码获取研究基地名称
	 * @param code 研究基地编码 (1,2,3,4,5,6,7,8)
	 * @return
	 */
	public String getInstituteTypeByCode(Integer code);
}
