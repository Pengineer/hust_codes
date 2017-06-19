package csdc.tool.execution.importer.tool;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import csdc.bean.SinossChecklogs;
import csdc.bean.SinossMembers;
import csdc.bean.SinossProjectApplication;
import csdc.dao.IHibernateBaseDao;
/**
* Sinoss中间表的工具类：将中间表指定数据初始化到List集合(内存)以提高数据导入速度
* @author pengliang 2014-9-12
*
*/
@Component
public class SinossTableTool {
	@Autowired
	private IHibernateBaseDao dao;
	
	/**
	 * SinossApplication 申请项目记录
	 */
	public List<SinossProjectApplication> sinossApplicationList;

	/**
	 * SinossMembers 成员记录
	 */
	public List<SinossMembers> sinossMemberList;
	
	/**
	 * SinossChecklogs 审核记录
	 */
	public List<SinossChecklogs> sinossChecklogsList;
	
	/**
	 * 初始化指定年份和指定类型的申请项目数据到内存
	 * @param appType 项目类型
	 * @param applicationFrom和applicationTo都是学校代码，用于确定导入数据范围
	 * @return
	 */
	public void initSinossApplicationList(String appType,int applicationFrom, int applicationTo, String applyYear) {
		long beginTime = new Date().getTime();
		Map paraMap = new HashMap();
		paraMap.put("typeCode", appType);
		paraMap.put("from", applicationFrom + "");
		paraMap.put("to", applicationTo + "");
		paraMap.put("applyYear", applyYear);
		initSinossApplicationList("select spa from SinossProjectApplication spa where spa.typeCode = :typeCode AND spa.unitCode between :from and :to AND spa.year = :applyYear",paraMap);

		System.out.println("initSinossApplicationList complete! Used time: " + (new Date().getTime() - beginTime) + "ms");
	}
	
	/**
	 * 	直接通过SQL获取特定申请项目数据
	 */
	public void initSinossApplicationList(String queryString){
		long beginTime = new Date().getTime();
		initSinossApplicationList(queryString,new HashMap());
		System.out.println("initSinossApplicationList complete! Used time: " + (new Date().getTime() - beginTime) + "ms");
	}
	
	/**
	 * 通过SQL和相关约束条件获取指定申请项目数据
	 */
	public void initSinossApplicationList(String queryString,Map paraMap) {		
		sinossApplicationList =(List<SinossProjectApplication>)dao.query(queryString,paraMap);
	}
	
	/**
	 * 初始化指定年份和指定类型的申请项目成员数据到内存
	 */
	public void initSinossMemberList(String appType, String applyYear) {
		long beginTime = new Date().getTime();
		Map paraMap = new HashMap();
		paraMap.put("typeCode", appType);
		paraMap.put("applyYear", applyYear);
		initSinossMemberList("select sm from SinossMembers sm where sm.projectApplication.typeCode = :typeCode and sm.projectApplication.year = :applyYear", paraMap);
		
		System.out.println("initSinossMemberList complete! Used time: " + (new Date().getTime() - beginTime) + "ms");
	}
	
	/**
	 * 初始化指定成员数据到内存（sql型）
	 */
	public void initSinossMemberList(String queryString) {
		long beginTime = new Date().getTime();
		initSinossMemberList(queryString,new HashMap());
		System.out.println("initSinossMemberList complete! Used time: " + (new Date().getTime() - beginTime) + "ms");
	}
	
	/**
	 * 初始化指定成员数据到内存（sql+map参数型）
	 */
	public void initSinossMemberList(String queryString,Map paraMap) {
		sinossMemberList =(List<SinossMembers>)dao.query(queryString, paraMap);//SinossMembers表里面无负责人信息
	}
	
	/**
	 * 初始化指定申请项目审核信息数据到内存(特定年份特定类型)
	 */
	public void initSinossChecklogsList(String appType,String applyYear){
		long beginTime = new Date().getTime();
		Map paraMap = new HashMap();
		paraMap.put("typeCode", appType);
		paraMap.put("applyYear", applyYear);
		initSinossChecklogsList("select sic from SinossChecklogs sic where sic.projectApplication.typeCode = :typeCode and sic.projectApplication.year = :applyYear", paraMap);
		
		System.out.println("initSinossChecklogsList complete! Used time: " + (new Date().getTime() - beginTime) + "ms");
	}
	
	/**
	 * 初始化指定项目审核信息数据到内存(sql型)
	 */
	public void initSinossChecklogsList(String queryString) {
		long beginTime = new Date().getTime();
		initSinossChecklogsList(queryString,new HashMap());
		System.out.println("initSinossChecklogsList complete! Used time: " + (new Date().getTime() - beginTime) + "ms");
	}
	
	/**
	 * 初始化指定项目审核信息数据到内存(sql+map参数型)
	 */
	public void initSinossChecklogsList(String queryString,Map paraMap) {
		sinossChecklogsList =(List<SinossChecklogs>)dao.query(queryString, paraMap);
	}
}
