package csdc.tool.webService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import csdc.bean.ProjectApplication;
import csdc.bean.ProjectGranted;
import csdc.bean.SinossChecklogs;
import csdc.bean.SinossMembers;
import csdc.bean.SinossModifyContent;
import csdc.bean.SinossProjectVariation;
import csdc.dao.HibernateBaseDao;
import csdc.tool.execution.importer.tool.Tool;

/**
 * 变更结果解析器
 * @author suwb
 *
 */
public class ModifyRecordResolver {
	
	public Document doc;//已经读取了xml的document文件
	public SimpleDateFormat sdf;//规律日期格式
	public Tool tool;//徐涵写的转成规律日期格式的方法,只有年月日
	protected HibernateBaseDao dao;
	
	public ModifyRecordResolver(Document doc, SimpleDateFormat sdf, Tool tool, HibernateBaseDao dao){
		this.doc = doc;
		this.sdf = sdf;
		this.tool = tool;
		this.dao = dao;
	}
	
	/**
	 * 解析入库代码
	 * @return
	 * @throws ParseException 
	 * @throws DOMException 
	 */
	public void parse() throws DOMException, ParseException{
		NodeList records =doc.getElementsByTagName("result");
		List<String> variationIds = dao.query("select spv.sinossId from SinossProjectVariation spv");
//		System.out.println(records.getLength());
		Element result=(Element) records.item(0);//基本变更信息
		//如果新增数据没有,新建,否则继续往下执行
		String variationId = result.getElementsByTagName("id").item(0).getFirstChild().getNodeValue();
		if(variationIds.toString().contains(variationId)){}
		else {
			SinossProjectVariation sinossProjectVariation = new SinossProjectVariation();
			sinossProjectVariation.setDumpDate(new Date());
			if(null != result.getElementsByTagName("id").item(0)){
				sinossProjectVariation.setSinossId(result.getElementsByTagName("id").item(0).getFirstChild().getNodeValue());
//			System.out.println("第" + (i+1) + "条记录的id为：" + result.getElementsByTagName("id").item(0).getFirstChild().getNodeValue());
			}
			if(null != result.getElementsByTagName("projectId").item(0)){
				sinossProjectVariation.setSinossProjectId(result.getElementsByTagName("projectId").item(0).getFirstChild().getNodeValue());
//			System.out.println("第" + (i+1) + "条记录的projectId为：" + result.getElementsByTagName("projectId").item(0).getFirstChild().getNodeValue());
			}
			if(null != result.getElementsByTagName("smdbProjectId").item(0)){
				String grantedId = result.getElementsByTagName("smdbProjectId").item(0).getFirstChild().getNodeValue();
				ProjectGranted pv = dao.query(ProjectGranted.class, grantedId);
				sinossProjectVariation.setProjectGranted(pv);
				String applicationId = pv.getApplicationId();
				ProjectApplication pa = dao.query(ProjectApplication.class, applicationId);
				sinossProjectVariation.setProjectApplication(pa);
//			System.out.println("第" + (i+1) + "条记录的smdbProjectId为：" + result.getElementsByTagName("smdbProjectId").item(0).getFirstChild().getNodeValue());
			}
			if(null != result.getElementsByTagName("code").item(0)){
				sinossProjectVariation.setCode(result.getElementsByTagName("code").item(0).getFirstChild().getNodeValue());
//			System.out.println("第" + (i+1) + "条记录的code为：" + result.getElementsByTagName("code").item(0).getFirstChild().getNodeValue());
			}
			if(null != result.getElementsByTagName("typeCode").item(0)){
				sinossProjectVariation.setTypeCode(result.getElementsByTagName("typeCode").item(0).getFirstChild().getNodeValue());	
//			System.out.println("第" + (i+1) + "条记录的typeCode为：" + result.getElementsByTagName("typeCode").item(0).getFirstChild().getNodeValue());
			}
			if(null != result.getElementsByTagName("name").item(0)){
				int m = result.getElementsByTagName("name").getLength();
				sinossProjectVariation.setName(result.getElementsByTagName("name").item(m-1).getFirstChild().getNodeValue());	
//			System.out.println("第" + (i+1) + "条记录的name为：" + result.getElementsByTagName("name").item(m-1).getFirstChild().getNodeValue());
			}
			if(null != result.getElementsByTagName("checkStatus").item(0)){
				sinossProjectVariation.setCheckStatus(Integer.parseInt(result.getElementsByTagName("checkStatus").item(0).getFirstChild().getNodeValue()));
//			System.out.println("第" + (i+1) + "条记录的checkStatus为：" + result.getElementsByTagName("checkStatus").item(0).getFirstChild().getNodeValue());
			}
			if(null != result.getElementsByTagName("checkDate").item(0)){
				sinossProjectVariation.setCheckDate(sdf.parse(result.getElementsByTagName("checkDate").item(0).getFirstChild().getNodeValue()));
//			System.out.println("第" + (i+1) + "条记录的checkDate为：" + result.getElementsByTagName("checkDate").item(0).getFirstChild().getNodeValue());
			}
			if(null != result.getElementsByTagName("checker").item(0)){
				sinossProjectVariation.setChecker(result.getElementsByTagName("checker").item(0).getFirstChild().getNodeValue());
//			System.out.println("第" + (i+1) + "条记录的checker为：" + result.getElementsByTagName("checker").item(0).getFirstChild().getNodeValue());
			}
			int n = result.getElementsByTagName("checkInfo").getLength();
			if(n > 0){
				sinossProjectVariation.setCheckInfo(result.getElementsByTagName("checkInfo").item(n-1).getFirstChild().getNodeValue());
//			System.out.println("第" + (i+1) + "条记录的checkInfo为：" + result.getElementsByTagName("checkInfo").item(n-1).getFirstChild().getNodeValue());				
			}
			if(null != result.getElementsByTagName("projectModifyType").item(0)){
				sinossProjectVariation.setProjectModifyType(result.getElementsByTagName("projectModifyType").item(0).getFirstChild().getNodeValue());
//			System.out.println("第" + (i+1) + "条记录的projectModifyType为：" + result.getElementsByTagName("projectModifyType").item(0).getFirstChild().getNodeValue());
			}
			if(null != result.getElementsByTagName("modifyReason").item(0)){
				sinossProjectVariation.setModifyReason(result.getElementsByTagName("modifyReason").item(0).getFirstChild().getNodeValue());
//			System.out.println("第" + (i+1) + "条记录的modifyReason为：" + result.getElementsByTagName("modifyReason").item(0).getFirstChild().getNodeValue());					
			}
			if(null != result.getElementsByTagName("applyDate").item(0)){
				sinossProjectVariation.setApplyDate(tool.getDate(result.getElementsByTagName("applyDate").item(0).getFirstChild().getNodeValue()));		
//			System.out.println("第" + (i+1) + "条记录的applyDate为：" + result.getElementsByTagName("applyDate").item(0).getFirstChild().getNodeValue());
			}
			sinossProjectVariation.setIsAdded(1);
			sinossProjectVariation.setDumpPerson("苏文波");
			sinossProjectVariation.setFrom("SINOSS");
			dao.add(sinossProjectVariation);
			NodeList checkLogs = result.getElementsByTagName("CheckLog");
			if(null !=checkLogs.item(0)){//存在变更申请审核记录
				for(int k = 0;k<checkLogs.getLength();k++){
					SinossChecklogs sinossChecklogs = new SinossChecklogs();
					Element checkLog = (Element) checkLogs.item(k);
					sinossChecklogs.setType(3);
					if(null != checkLog.getElementsByTagName("id").item(0)){
						sinossChecklogs.setSinossId(checkLog.getElementsByTagName("id").item(0).getFirstChild().getNodeValue());	
//					System.out.println("第" + (k+1) + "个审核记录的id为：" + checkLog.getElementsByTagName("id").item(0).getFirstChild().getNodeValue());
					}
					if(null != checkLog.getElementsByTagName("checkStatus").item(0)){
						sinossChecklogs.setCheckStatus(Integer.parseInt(checkLog.getElementsByTagName("checkStatus").item(0).getFirstChild().getNodeValue()));
//					System.out.println("第" + (k+1) + "个审核记录的checkStatus为：" + checkLog.getElementsByTagName("checkStatus").item(0).getFirstChild().getNodeValue());
					}
					if(null != checkLog.getElementsByTagName("checkDate").item(0)){
						sinossChecklogs.setCheckDate(sdf.parse(checkLog.getElementsByTagName("checkDate").item(0).getFirstChild().getNodeValue()));	
//					System.out.println("第" + (k+1) + "个审核记录的checkDate为：" + checkLog.getElementsByTagName("checkDate").item(0).getFirstChild().getNodeValue());
					}
					if(null != checkLog.getElementsByTagName("checker").item(0)){
						sinossChecklogs.setChecker(checkLog.getElementsByTagName("checker").item(0).getFirstChild().getNodeValue());	
//					System.out.println("第" + (k+1) + "个审核记录的checker为：" + checkLog.getElementsByTagName("checker").item(0).getFirstChild().getNodeValue());
					}
					if(null != checkLog.getElementsByTagName("checkInfo").item(0)){
						sinossChecklogs.setCheckInfo(checkLog.getElementsByTagName("checkInfo").item(0).getFirstChild().getNodeValue());
//					System.out.println("第" + (k+1) + "个审核记录的checkInfo为：" + checkLog.getElementsByTagName("checkInfo").item(0).getFirstChild().getNodeValue());
					}
					sinossChecklogs.setProjectVariation(sinossProjectVariation);
					dao.add(sinossChecklogs);	
				}
			}
			NodeList members = result.getElementsByTagName("ProjectModifyMember");
			if(null != members.item(0)){//存在成员变更信息
				for( int j = 0;j< members.getLength();j++){
					SinossMembers sinossMembers = new SinossMembers();
					Element MembersObj = (Element) members.item(j);
					if(null != MembersObj.getElementsByTagName("id").item(0)){
						sinossMembers.setCode(MembersObj.getElementsByTagName("id").item(0).getFirstChild().getNodeValue());	
//					System.out.println("第" + (j+1) + "个成员变更的id为：" + MembersObj.getElementsByTagName("id").item(0).getFirstChild().getNodeValue());
					}
					if(null != MembersObj.getElementsByTagName("name").item(0)){
						sinossMembers.setName(MembersObj.getElementsByTagName("name").item(0).getFirstChild().getNodeValue());		
//					System.out.println("第" + (j+1) + "个成员变更的name为：" + MembersObj.getElementsByTagName("name").item(0).getFirstChild().getNodeValue());
					}
					if(null != MembersObj.getElementsByTagName("unitName").item(0)){
						sinossMembers.setUnitName(MembersObj.getElementsByTagName("unitName").item(0).getFirstChild().getNodeValue());		
//					System.out.println("第" + (j+1) + "个成员变更的unitName为：" + MembersObj.getElementsByTagName("unitName").item(0).getFirstChild().getNodeValue());
					}
					if(null != MembersObj.getElementsByTagName("title").item(0)){
						sinossMembers.setTitle(MembersObj.getElementsByTagName("title").item(0).getFirstChild().getNodeValue());	
//					System.out.println("第" + (j+1) + "个成员变更的title为：" + MembersObj.getElementsByTagName("title").item(0).getFirstChild().getNodeValue());
					}
					if(null != MembersObj.getElementsByTagName("divide").item(0)){
						sinossMembers.setDivide(MembersObj.getElementsByTagName("divide").item(0).getFirstChild().getNodeValue());	
//					System.out.println("第" + (j+1) + "个成员变更的divide为：" + MembersObj.getElementsByTagName("divide").item(0).getFirstChild().getNodeValue());
					}
					if(null != MembersObj.getElementsByTagName("orders").item(0)){
						sinossMembers.setOrders(MembersObj.getElementsByTagName("orders").item(0).getFirstChild().getNodeValue());	
//					System.out.println("第" + (j+1) + "个成员变更的orders为：" + MembersObj.getElementsByTagName("orders").item(0).getFirstChild().getNodeValue());
					}
					if(null != MembersObj.getElementsByTagName("resAdvantage").item(0)){
						sinossMembers.setResAdvantage(MembersObj.getElementsByTagName("resAdvantage").item(0).getFirstChild().getNodeValue());	
//					System.out.println("第" + (j+1) + "个成员变更的resAdvantage为：" + MembersObj.getElementsByTagName("resAdvantage").item(0).getFirstChild().getNodeValue());
					}
					if(null != MembersObj.getElementsByTagName("birthDay").item(0)){
						sinossMembers.setBirthday(tool.getDate(MembersObj.getElementsByTagName("birthDay").item(0).getFirstChild().getNodeValue()));
//					System.out.println("第" + (j+1) + "个成员变更的birthDay为：" + MembersObj.getElementsByTagName("birthDay").item(0).getFirstChild().getNodeValue());
					}
					if(null != MembersObj.getElementsByTagName("specialty").item(0)){
						sinossMembers.setSpecialty(MembersObj.getElementsByTagName("specialty").item(0).getFirstChild().getNodeValue());
//					System.out.println("第" + (j+1) + "个成员变更的specialty为：" + MembersObj.getElementsByTagName("specialty").item(0).getFirstChild().getNodeValue());
					}
					if(null != MembersObj.getElementsByTagName("eduDegree").item(0)){
						sinossMembers.setEduDegree(MembersObj.getElementsByTagName("eduDegree").item(0).getFirstChild().getNodeValue());
//					System.out.println("第" + (j+1) + "个成员变更的eduDegree为：" + MembersObj.getElementsByTagName("eduDegree").item(0).getFirstChild().getNodeValue());
					}
					if(null != MembersObj.getElementsByTagName("subjectFlag").item(0)){
						sinossMembers.setSubjectFlag(MembersObj.getElementsByTagName("subjectFlag").item(0).getFirstChild().getNodeValue());
//					System.out.println("第" + (j+1) + "个成员变更的subjectFlag为：" + MembersObj.getElementsByTagName("subjectFlag").item(0).getFirstChild().getNodeValue());
					}
					sinossMembers.setProjectVariation(sinossProjectVariation);
					dao.add(sinossMembers);
				}
			}
			NodeList modifyContents = result.getElementsByTagName("ModifyObj");
			if(null != modifyContents.item(0)){//存在变更内容
				for(int t=0;t<modifyContents.getLength();t++){
					SinossModifyContent sinossModifyContent = new SinossModifyContent();
					Element modifyContent =(Element)modifyContents.item(t);
					if(null != modifyContent.getElementsByTagName("idNumber").item(0)){
						sinossModifyContent.setIdNumber(modifyContent.getElementsByTagName("idNumber").item(0).getFirstChild().getNodeValue());
//					System.out.println("第" + (t+1) + "个变更内容的idNumber为：" + modifyContent.getElementsByTagName("idNumber").item(0).getFirstChild().getNodeValue());
					}
					if(null != modifyContent.getElementsByTagName("beforeValue").item(0)){
						sinossModifyContent.setBeforeValue(modifyContent.getElementsByTagName("beforeValue").item(0).getFirstChild().getNodeValue());
//					System.out.println("第" + (t+1) + "个变更内容的beforeValue为：" + modifyContent.getElementsByTagName("beforeValue").item(0).getFirstChild().getNodeValue());
					}
					if(null != modifyContent.getElementsByTagName("id").item(0)){
						sinossModifyContent.setSinossId(modifyContent.getElementsByTagName("id").item(0).getFirstChild().getNodeValue());		
//					System.out.println("第" + (t+1) + "个变更内容的id为：" + modifyContent.getElementsByTagName("id").item(0).getFirstChild().getNodeValue());
					}
					if(null != modifyContent.getElementsByTagName("modifyFieIdMean").item(0)){
						sinossModifyContent.setModifyFieldMean(modifyContent.getElementsByTagName("modifyFieIdMean").item(0).getFirstChild().getNodeValue());	
//					System.out.println("第" + (t+1) + "个变更内容的modifyFieIdMean为：" + modifyContent.getElementsByTagName("modifyFieIdMean").item(0).getFirstChild().getNodeValue());
					}
					if(null != modifyContent.getElementsByTagName("afterValue").item(0)){
						sinossModifyContent.setAfterValue(modifyContent.getElementsByTagName("afterValue").item(0).getFirstChild().getNodeValue());		
//					System.out.println("第" + (t+1) + "个变更内容的afterValue为：" + modifyContent.getElementsByTagName("afterValue").item(0).getFirstChild().getNodeValue());
					}
					sinossModifyContent.setProjectVariation(sinossProjectVariation);
					dao.add(sinossModifyContent);
				}
			}				
		}
	}
}
