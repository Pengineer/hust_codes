package csdc.tool.webService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import csdc.bean.ProjectGranted;
import csdc.bean.SinossChecklogs;
import csdc.bean.SinossMembers;
import csdc.bean.SinossModifyContent;
import csdc.bean.SinossProjectVariation;
import csdc.dao.IHibernateBaseDao;
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
	protected IHibernateBaseDao dao;
	
	public ModifyRecordResolver(Document doc, SimpleDateFormat sdf, Tool tool, IHibernateBaseDao dao){
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
	 * @throws XPathExpressionException 
	 */
	public void parse() throws DOMException, ParseException, XPathExpressionException{
		NodeList records =doc.getElementsByTagName("result");
		XPathFactory xFactory = XPathFactory.newInstance();
	    XPath xpath = xFactory.newXPath();		
//		System.out.println(records.getLength());
		Element result=(Element) records.item(0);//基本变更信息
		XPathExpression exprId = xpath.compile("//result[1]/id/text()");
		NodeList nodeId = (NodeList) exprId.evaluate(doc, XPathConstants.NODESET);		
		SinossProjectVariation sinossProjectVariation = new SinossProjectVariation();
		if(nodeId.item(0) != null){
			sinossProjectVariation.setSinossId(nodeId.item(0).getNodeValue());
//				System.out.println("第" + (i+1) + "条记录的sinossId为：" + nodeId.item(0).getNodeValue());		    	
		}	    	
		if(null != result.getElementsByTagName("projectId").item(0)){
			sinossProjectVariation.setSinossProjectId(result.getElementsByTagName("projectId").item(0).getFirstChild().getNodeValue());
//				System.out.println("第" + (i+1) + "条记录的projectId为：" + result.getElementsByTagName("projectId").item(0).getFirstChild().getNodeValue());
		}
		if(null != result.getElementsByTagName("smdbProjectId").item(0)){
			String grantedId = result.getElementsByTagName("smdbProjectId").item(0).getFirstChild().getNodeValue();
			try {
	    		ProjectGranted pGranted = dao.query(ProjectGranted.class, grantedId);
	    		sinossProjectVariation.setProjectGranted(pGranted);				
			} catch (Exception e) {
				sinossProjectVariation.setProjectGranted(null);	
			}
//				String applicationId = pv.getApplicationId();
//				ProjectApplication pa = dao.query(ProjectApplication.class, applicationId);
//				sinossProjectVariation.setProjectApplication(pa);
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
		XPathExpression exprName = xpath.compile("//result[1]/name/text()");
		NodeList nodeName = (NodeList) exprName.evaluate(doc, XPathConstants.NODESET);
		if(nodeName.item(0) != null){
			sinossProjectVariation.setName(nodeName.item(0).getNodeValue());
		}
		XPathExpression exprCheckstatus = xpath.compile("//result[1]/checkStatus/text()");
		NodeList nodeCheckstatus = (NodeList) exprCheckstatus.evaluate(doc, XPathConstants.NODESET);
		if(nodeCheckstatus.item(0) != null){
			sinossProjectVariation.setCheckStatus(Integer.parseInt(nodeCheckstatus.item(0).getNodeValue()));
//				System.out.println("第" + (i+1) + "条记录的checkstatus为：" + nodeCheckstatus.item(0).getNodeValue());		    	
		}
		XPathExpression exprCheckDate = xpath.compile("//result[1]/checkDate/text()");
		NodeList nodeCheckDate = (NodeList) exprCheckDate.evaluate(doc, XPathConstants.NODESET);
		if(nodeCheckDate.item(0) != null){
			sinossProjectVariation.setCheckDate(sdf.parse(nodeCheckDate.item(0).getNodeValue()));
//				System.out.println("第" + (i+1) + "条记录的checkDate为：" + nodeCheckDate.item(0).getNodeValue());		    	
		}
		XPathExpression exprChecker = xpath.compile("//result[1]/checker/text()");
		NodeList nodeChecker = (NodeList) exprChecker.evaluate(doc, XPathConstants.NODESET);
		if(nodeChecker.item(0) != null){
			sinossProjectVariation.setChecker(nodeChecker.item(0).getNodeValue());
//				System.out.println("第" + (i+1) + "条记录的checker为：" + nodeChecker.item(0).getNodeValue());		    	
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
		sinossProjectVariation.setDumpDate(new Date());
		sinossProjectVariation.setDumpPerson("苏文波");
		sinossProjectVariation.setFrom("SINOSS");
		dao.add(sinossProjectVariation);
		NodeList checkLogs = result.getElementsByTagName("CheckLog");
		if(null !=checkLogs.item(0)){//存在变更申请审核记录
			for(int k = 0;k<checkLogs.getLength();k++){
				SinossChecklogs sinossChecklogs = new SinossChecklogs();
				Element checkLog = (Element) checkLogs.item(k);
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
				sinossChecklogs.setType(3);
				sinossChecklogs.setDumpDate(new Date());
				sinossChecklogs.setIsAdded(1);
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
				sinossMembers.setDumpDate(new Date());
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
				sinossModifyContent.setDumpDate(new Date());
				sinossModifyContent.setIsAdded(1);
				sinossModifyContent.setProjectVariation(sinossProjectVariation);
				dao.add(sinossModifyContent);
			}
		}				
	}
}
