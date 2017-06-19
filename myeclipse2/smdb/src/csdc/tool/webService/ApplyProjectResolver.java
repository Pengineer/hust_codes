package csdc.tool.webService;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import csdc.bean.SinossChecklogs;
import csdc.bean.SinossMembers;
import csdc.bean.SinossProjectApplication;
import csdc.dao.HibernateBaseDao;
import csdc.tool.execution.importer.tool.Tool;

/**
 * 项目申报数据解析器
 * @author suwb
 *
 */
public class ApplyProjectResolver {
	
	public Document doc;//已经读取了xml的document文件
	public SimpleDateFormat sdf;//规律日期格式
	public Tool tool;//徐涵写的转成规律日期格式的方法,只有年月日
	protected HibernateBaseDao dao;
	
	public ApplyProjectResolver(Document doc, SimpleDateFormat sdf, Tool tool, HibernateBaseDao dao){
		this.doc = doc;
		this.sdf = sdf;
		this.tool = tool;
		this.dao = dao;
	}
	
	/**
	 * 解析入库代码
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * @throws XPathExpressionException
	 * @throws DOMException
	 * @throws ParseException
	 */
	public void prase() throws ParserConfigurationException, SAXException, IOException, XPathExpressionException, DOMException, ParseException{
		NodeList records =doc.getElementsByTagName("result");
		Element result=(Element) records.item(0);
		XPathFactory xFactory = XPathFactory.newInstance();
	    XPath xpath = xFactory.newXPath();
		//基本申报信息
		SinossProjectApplication sinossProjectApplication = new SinossProjectApplication();
		XPathExpression exprId = xpath.compile("//result[1]/id/text()");
		NodeList nodeId = (NodeList) exprId.evaluate(doc, XPathConstants.NODESET);
		if(nodeId.item(0) != null){
			sinossProjectApplication.setSinossId(nodeId.item(0).getNodeValue());
//			System.out.println("第" + (i+1) + "条记录的sinossId为：" + nodeId.item(0).getNodeValue());		    	
		}	    	
		if(null != result.getElementsByTagName("projectName").item(0)){
			sinossProjectApplication.setProjectName(result.getElementsByTagName("projectName").item(0).getFirstChild().getNodeValue());
//			System.out.println("第" + (i+1) + "条记录的projectName为：" + result.getElementsByTagName("projectName").item(0).getFirstChild().getNodeValue());
		}
		if(null != result.getElementsByTagName("projectSubType").item(0)){
			sinossProjectApplication.setSubType(result.getElementsByTagName("projectSubType").item(0).getFirstChild().getNodeValue());
//			System.out.println("第" + (i+1) + "条记录的projectName为：" + result.getElementsByTagName("projectName").item(0).getFirstChild().getNodeValue());
		}
		if(null != result.getElementsByTagName("projectJdId").item(0)){
			sinossProjectApplication.setProjectJDId(result.getElementsByTagName("projectJdId").item(0).getFirstChild().getNodeValue());
//			System.out.println("第" + (i+1) + "条记录的projectJdId为：" + result.getElementsByTagName("projectJdId").item(0).getFirstChild().getNodeValue());
		}
		if(null != result.getElementsByTagName("projectJdName").item(0)){
			sinossProjectApplication.setProjectJDName(result.getElementsByTagName("projectJdName").item(0).getFirstChild().getNodeValue());
//			System.out.println("第" + (i+1) + "条记录的projectJdName为：" + result.getElementsByTagName("projectJdName").item(0).getFirstChild().getNodeValue());
		}
		if(null != result.getElementsByTagName("unitCode").item(0)){
			sinossProjectApplication.setUnitCode(result.getElementsByTagName("unitCode").item(0).getFirstChild().getNodeValue());
//			System.out.println("第" + (i+1) + "条记录的unitCode为：" + result.getElementsByTagName("unitCode").item(0).getFirstChild().getNodeValue());
		}
		if(null != result.getElementsByTagName("unitName").item(0)){
			sinossProjectApplication.setUnitName(result.getElementsByTagName("unitName").item(0).getFirstChild().getNodeValue());
//			System.out.println("第" + (i+1) + "条记录的unitName为：" + result.getElementsByTagName("unitName").item(0).getFirstChild().getNodeValue());
		}
		XPathExpression exprTypeCode = xpath.compile("//result[1]/typeCode/text()");
		NodeList nodeTypeCode = (NodeList) exprTypeCode.evaluate(doc, XPathConstants.NODESET);
		if(nodeTypeCode.item(0) != null){
			sinossProjectApplication.setTypeCode(nodeTypeCode.item(0).getNodeValue());
//			System.out.println("第" + (i+1) + "条记录的typeCode为：" + nodeTypeCode.item(0).getNodeValue());		    	
		}
		if(null != result.getElementsByTagName("projectType").item(0)){
			sinossProjectApplication.setProjectType(result.getElementsByTagName("projectType").item(0).getFirstChild().getNodeValue());
//			System.out.println("第" + (i+1) + "条记录的projectType为：" + result.getElementsByTagName("projectType").item(0).getFirstChild().getNodeValue());
		}
		if(null != result.getElementsByTagName("batchId").item(0)){
			sinossProjectApplication.setBatchId(result.getElementsByTagName("batchId").item(0).getFirstChild().getNodeValue());
//			System.out.println("第" + (i+1) + "条记录的batchId为：" + result.getElementsByTagName("batchId").item(0).getFirstChild().getNodeValue());
		}
		if(null != result.getElementsByTagName("year").item(0)){
			sinossProjectApplication.setYear(result.getElementsByTagName("year").item(0).getFirstChild().getNodeValue());
//			System.out.println("第" + (i+1) + "条记录的year为：" + result.getElementsByTagName("year").item(0).getFirstChild().getNodeValue());
		}
		if(null != result.getElementsByTagName("batchName").item(0)){
			sinossProjectApplication.setBatchName(result.getElementsByTagName("batchName").item(0).getFirstChild().getNodeValue());
//			System.out.println("第" + (i+1) + "条记录的batchName为：" + result.getElementsByTagName("batchName").item(0).getFirstChild().getNodeValue());
		}
		if(null != result.getElementsByTagName("beginDate").item(0)){
			sinossProjectApplication.setBeginDate(sdf.parse(result.getElementsByTagName("beginDate").item(0).getFirstChild().getNodeValue()));
//			System.out.println("第" + (i+1) + "条记录的beginDate为：" + result.getElementsByTagName("beginDate").item(0).getFirstChild().getNodeValue());
		}
		if(null != result.getElementsByTagName("endDate").item(0)){
			sinossProjectApplication.setEndDate(sdf.parse(result.getElementsByTagName("endDate").item(0).getFirstChild().getNodeValue()));
//			System.out.println("第" + (i+1) + "条记录的endDate为：" + result.getElementsByTagName("endDate").item(0).getFirstChild().getNodeValue());
		}
		if(null != result.getElementsByTagName("applyDate").item(0)){
			sinossProjectApplication.setApplyDate(tool.getDate(result.getElementsByTagName("applyDate").item(0).getFirstChild().getNodeValue()));
//			System.out.println("第" + (i+1) + "条记录的applyDate为：" + result.getElementsByTagName("applyDate").item(0).getFirstChild().getNodeValue());
		}
		if(null != result.getElementsByTagName("planFinishDate").item(0)){
			sinossProjectApplication.setPlanFinishDate(tool.getDate(result.getElementsByTagName("planFinishDate").item(0).getFirstChild().getNodeValue()));
//			System.out.println("第" + (i+1) + "条记录的planFinishDate为：" + result.getElementsByTagName("planFinishDate").item(0).getFirstChild().getNodeValue());
		}
		if(null != result.getElementsByTagName("subject").item(0)){
			sinossProjectApplication.setSubject(result.getElementsByTagName("subject").item(0).getFirstChild().getNodeValue());
//			System.out.println("第" + (i+1) + "条记录的subject为：" + result.getElementsByTagName("subject").item(0).getFirstChild().getNodeValue());
		}
		if(null != result.getElementsByTagName("subject1_1").item(0)){
			sinossProjectApplication.setSubject1_1(result.getElementsByTagName("subject1_1").item(0).getFirstChild().getNodeValue());
//			System.out.println("第" + (i+1) + "条记录的subject1_1为：" + result.getElementsByTagName("subject1_1").item(0).getFirstChild().getNodeValue());
		}
		if(null != result.getElementsByTagName("subject1_2").item(0)){
			sinossProjectApplication.setSubject1_2(result.getElementsByTagName("subject1_2").item(0).getFirstChild().getNodeValue());
//			System.out.println("第" + (i+1) + "条记录的subject1_2为：" + result.getElementsByTagName("subject1_2").item(0).getFirstChild().getNodeValue());
		}
		if(null != result.getElementsByTagName("researchDirection").item(0)){
			sinossProjectApplication.setResearchDirection(result.getElementsByTagName("researchDirection").item(0).getFirstChild().getNodeValue());
//			System.out.println("第" + (i+1) + "条记录的researchDirection为：" + result.getElementsByTagName("researchDirection").item(0).getFirstChild().getNodeValue());
		}
		if(null != result.getElementsByTagName("researchType").item(0)){
			sinossProjectApplication.setResearchType(result.getElementsByTagName("researchType").item(0).getFirstChild().getNodeValue());
//			System.out.println("第" + (i+1) + "条记录的researchType为：" + result.getElementsByTagName("researchType").item(0).getFirstChild().getNodeValue());
		}
		if(null != result.getElementsByTagName("lastProductMode").item(0)){
			sinossProjectApplication.setLastProductMode(result.getElementsByTagName("lastProductMode").item(0).getFirstChild().getNodeValue());
//			System.out.println("第" + (i+1) + "条记录的lastProductMode为：" + result.getElementsByTagName("lastProductMode").item(0).getFirstChild().getNodeValue());
		}
		if(null != result.getElementsByTagName("words").item(0)){
			sinossProjectApplication.setWords(result.getElementsByTagName("words").item(0).getFirstChild().getNodeValue());
//			System.out.println("第" + (i+1) + "条记录的words为：" + result.getElementsByTagName("words").item(0).getFirstChild().getNodeValue());
		}
		if(null != result.getElementsByTagName("applyTotalFee").item(0)){
			sinossProjectApplication.setApplyTotleFee(result.getElementsByTagName("applyTotalFee").item(0).getFirstChild().getNodeValue());
//			System.out.println("第" + (i+1) + "条记录的applyTotalFee为：" + result.getElementsByTagName("applyTotalFee").item(0).getFirstChild().getNodeValue());
		}
		if(null != result.getElementsByTagName("otherFeeSource").item(0)){
			sinossProjectApplication.setOtherFeeSource(result.getElementsByTagName("otherFeeSource").item(0).getFirstChild().getNodeValue());
//			System.out.println("第" + (i+1) + "条记录的otherFeeSource为：" + result.getElementsByTagName("otherFeeSource").item(0).getFirstChild().getNodeValue());
		}
		if(null != result.getElementsByTagName("applyDocName").item(0)){
			sinossProjectApplication.setApplyDocName(result.getElementsByTagName("applyDocName").item(0).getFirstChild().getNodeValue());
//			System.out.println("第" + (i+1) + "条记录的applyDocName为：" + result.getElementsByTagName("applyDocName").item(0).getFirstChild().getNodeValue());
		}
		if(null != result.getElementsByTagName("attachmentName").item(0)){
			sinossProjectApplication.setAttachmentName(result.getElementsByTagName("attachmentName").item(0).getFirstChild().getNodeValue());
//			System.out.println("第" + (i+1) + "条记录的attachmentName为：" + result.getElementsByTagName("attachmentName").item(0).getFirstChild().getNodeValue());
		}
		if(null != result.getElementsByTagName("applyer").item(0)){
			sinossProjectApplication.setApplyer(result.getElementsByTagName("applyer").item(0).getFirstChild().getNodeValue());
//			System.out.println("第" + (i+1) + "条记录的applyer为：" + result.getElementsByTagName("applyer").item(0).getFirstChild().getNodeValue());
		}
		if(null != result.getElementsByTagName("gender").item(0)){
			sinossProjectApplication.setGender(result.getElementsByTagName("gender").item(0).getFirstChild().getNodeValue());
//			System.out.println("第" + (i+1) + "条记录的gender为：" + result.getElementsByTagName("gender").item(0).getFirstChild().getNodeValue());
		}
		if(null != result.getElementsByTagName("birthday").item(0)){
			sinossProjectApplication.setBirthday(tool.getDate(result.getElementsByTagName("birthday").item(0).getFirstChild().getNodeValue()));
//			System.out.println("第" + (i+1) + "条记录的birthday为：" + result.getElementsByTagName("birthday").item(0).getFirstChild().getNodeValue());
		}
		if(null != result.getElementsByTagName("IDCardNo").item(0)){
			sinossProjectApplication.setIdCardNo(result.getElementsByTagName("IDCardNo").item(0).getFirstChild().getNodeValue());
//			System.out.println("第" + (i+1) + "条记录的IDCardNo为：" + result.getElementsByTagName("IDCardNo").item(0).getFirstChild().getNodeValue());
		}
		if(null != result.getElementsByTagName("title").item(0)){
			sinossProjectApplication.setTitle(result.getElementsByTagName("title").item(0).getFirstChild().getNodeValue());
//			System.out.println("第" + (i+1) + "条记录的title为：" + result.getElementsByTagName("title").item(0).getFirstChild().getNodeValue());
		}
		if(null != result.getElementsByTagName("division").item(0)){
			sinossProjectApplication.setDivision(result.getElementsByTagName("division").item(0).getFirstChild().getNodeValue());
//			System.out.println("第" + (i+1) + "条记录的division为：" + result.getElementsByTagName("division").item(0).getFirstChild().getNodeValue());
		}
		if(null != result.getElementsByTagName("duty").item(0)){
			sinossProjectApplication.setDuty(result.getElementsByTagName("duty").item(0).getFirstChild().getNodeValue());
//			System.out.println("第" + (i+1) + "条记录的duty为：" + result.getElementsByTagName("duty").item(0).getFirstChild().getNodeValue());
		}
		if(null != result.getElementsByTagName("eduLevel").item(0)){
			sinossProjectApplication.setEduLevel(result.getElementsByTagName("eduLevel").item(0).getFirstChild().getNodeValue());
//			System.out.println("第" + (i+1) + "条记录的eduLevel为：" + result.getElementsByTagName("eduLevel").item(0).getFirstChild().getNodeValue());
		}
		if(null != result.getElementsByTagName("eduDegree").item(0)){
			sinossProjectApplication.setEduDegree(result.getElementsByTagName("eduDegree").item(0).getFirstChild().getNodeValue());
//			System.out.println("第" + (i+1) + "条记录的eduDegree为：" + result.getElementsByTagName("eduDegree").item(0).getFirstChild().getNodeValue());
		}
		if(null != result.getElementsByTagName("language").item(0)){
			sinossProjectApplication.setLanguage(result.getElementsByTagName("language").item(0).getFirstChild().getNodeValue());
//			System.out.println("第" + (i+1) + "条记录的language为：" + result.getElementsByTagName("language").item(0).getFirstChild().getNodeValue());
		}
		if(null != result.getElementsByTagName("email").item(0)){
			sinossProjectApplication.setEmail(result.getElementsByTagName("email").item(0).getFirstChild().getNodeValue());
//			System.out.println("第" + (i+1) + "条记录的email为：" + result.getElementsByTagName("email").item(0).getFirstChild().getNodeValue());
		}
		if(null != result.getElementsByTagName("address").item(0)){
			sinossProjectApplication.setAddress(result.getElementsByTagName("address").item(0).getFirstChild().getNodeValue());
//			System.out.println("第" + (i+1) + "条记录的address为：" + result.getElementsByTagName("address").item(0).getFirstChild().getNodeValue());
		}
		if(null != result.getElementsByTagName("postalCode").item(0)){
			sinossProjectApplication.setPostalCode(result.getElementsByTagName("postalCode").item(0).getFirstChild().getNodeValue());
//			System.out.println("第" + (i+1) + "条记录的postalCode为：" + result.getElementsByTagName("postalCode").item(0).getFirstChild().getNodeValue());
		}
		if(null != result.getElementsByTagName("telOffice").item(0)){
			sinossProjectApplication.setTelOffice(result.getElementsByTagName("telOffice").item(0).getFirstChild().getNodeValue());
//			System.out.println("第" + (i+1) + "条记录的telOffice为：" + result.getElementsByTagName("telOffice").item(0).getFirstChild().getNodeValue());
		}
		if(null != result.getElementsByTagName("tel").item(0)){
			sinossProjectApplication.setTel(result.getElementsByTagName("tel").item(0).getFirstChild().getNodeValue());
//			System.out.println("第" + (i+1) + "条记录的tel为：" + result.getElementsByTagName("tel").item(0).getFirstChild().getNodeValue());
		}
		XPathExpression exprCheckstatus = xpath.compile("//result[1]/checkStatus/text()");
		NodeList nodeCheckstatus = (NodeList) exprCheckstatus.evaluate(doc, XPathConstants.NODESET);
		if(nodeCheckstatus.item(0) != null){
			sinossProjectApplication.setCheckStatus(nodeCheckstatus.item(0).getNodeValue());
//			System.out.println("第" + (i+1) + "条记录的checkstatus为：" + nodeCheckstatus.item(0).getNodeValue());		    	
		}
		XPathExpression exprCheckDate = xpath.compile("//result[1]/checkDate/text()");
		NodeList nodeCheckDate = (NodeList) exprCheckDate.evaluate(doc, XPathConstants.NODESET);
		if(nodeCheckDate.item(0) != null){
			sinossProjectApplication.setCheckDate(sdf.parse(nodeCheckDate.item(0).getNodeValue()));
//			System.out.println("第" + (i+1) + "条记录的checkDate为：" + nodeCheckDate.item(0).getNodeValue());		    	
		}
		XPathExpression exprChecker = xpath.compile("//result[1]/checker/text()");
		NodeList nodeChecker = (NodeList) exprChecker.evaluate(doc, XPathConstants.NODESET);
		if(nodeChecker.item(0) != null){
			sinossProjectApplication.setChecker(nodeChecker.item(0).getNodeValue());
//			System.out.println("第" + (i+1) + "条记录的checker为：" + nodeChecker.item(0).getNodeValue());		    	
		}
		sinossProjectApplication.setDumpDate(new Date());
		sinossProjectApplication.setDumpPerson("苏文波");
		sinossProjectApplication.setFrom("SINOSS");
		dao.add(sinossProjectApplication);
		//存在申报成员信息
		NodeList members = result.getElementsByTagName("ProjectApplyMember");
		if(null !=members.item(0)){
			for(int k = 0;k<members.getLength();k++){
				SinossMembers sinossMembers = new SinossMembers();
				Element member = (Element) members.item(k);
				if(null != member.getElementsByTagName("id").item(0)){
					sinossMembers.setSinossId(member.getElementsByTagName("id").item(0).getFirstChild().getNodeValue());
//					System.out.println("第" + (k+1) + "个申报成员的Id为：" + member.getElementsByTagName("Id").item(0).getFirstChild().getNodeValue());
				}
				if(null != member.getElementsByTagName("projectID").item(0)){
					sinossMembers.setSinossProjectId(member.getElementsByTagName("projectID").item(0).getFirstChild().getNodeValue());
//					System.out.println("第" + (k+1) + "个申报成员的projectID为：" + member.getElementsByTagName("projectID").item(0).getFirstChild().getNodeValue());
				}
				if(null != member.getElementsByTagName("memberName").item(0)){
					sinossMembers.setName(member.getElementsByTagName("memberName").item(0).getFirstChild().getNodeValue());
//					System.out.println("第" + (k+1) + "个申报成员的memberName为：" + member.getElementsByTagName("memberName").item(0).getFirstChild().getNodeValue());
				}
				if(null != member.getElementsByTagName("memberCode").item(0)){
					sinossMembers.setCode(member.getElementsByTagName("memberCode").item(0).getFirstChild().getNodeValue());
//					System.out.println("第" + (k+1) + "个申报成员的memberCode为：" + member.getElementsByTagName("memberCode").item(0).getFirstChild().getNodeValue());
				}
				if(null != member.getElementsByTagName("memberBirthday").item(0)){
					sinossMembers.setBirthday(tool.getDate(member.getElementsByTagName("memberBirthday").item(0).getFirstChild().getNodeValue()));
//					System.out.println("第" + (k+1) + "个申报成员的memberBirthday为：" + member.getElementsByTagName("memberBirthday").item(0).getFirstChild().getNodeValue());
				}
				if(null != member.getElementsByTagName("memberTitle").item(0)){
					sinossMembers.setTitle(member.getElementsByTagName("memberTitle").item(0).getFirstChild().getNodeValue());
//					System.out.println("第" + (k+1) + "个申报成员的memberTitle为：" + member.getElementsByTagName("memberTitle").item(0).getFirstChild().getNodeValue());
				}
				if(null != member.getElementsByTagName("memberUnit").item(0)){
					sinossMembers.setUnitName(member.getElementsByTagName("memberUnit").item(0).getFirstChild().getNodeValue());
//					System.out.println("第" + (k+1) + "个申报成员的memberUnit为：" + member.getElementsByTagName("memberUnit").item(0).getFirstChild().getNodeValue());
				}
				if(null != member.getElementsByTagName("memberSpecialty").item(0)){
					sinossMembers.setSpecialty(member.getElementsByTagName("memberSpecialty").item(0).getFirstChild().getNodeValue());
//					System.out.println("第" + (k+1) + "个申报成员的memberSpecialty为：" + member.getElementsByTagName("memberSpecialty").item(0).getFirstChild().getNodeValue());
				}
				if(null != member.getElementsByTagName("memberDivide").item(0)){
					sinossMembers.setDivide(member.getElementsByTagName("memberDivide").item(0).getFirstChild().getNodeValue());
//					System.out.println("第" + (k+1) + "个申报成员的memberDivide为：" + member.getElementsByTagName("memberDivide").item(0).getFirstChild().getNodeValue());
				}
				if(null != member.getElementsByTagName("orders").item(0)){
					sinossMembers.setOrders(member.getElementsByTagName("orders").item(0).getFirstChild().getNodeValue());
//					System.out.println("第" + (k+1) + "个申报成员的orders为：" + member.getElementsByTagName("orders").item(0).getFirstChild().getNodeValue());
				}
				if(null != member.getElementsByTagName("memberEduDegree").item(0)){
					sinossMembers.setEduDegree(member.getElementsByTagName("memberEduDegree").item(0).getFirstChild().getNodeValue());
//					System.out.println("第" + (k+1) + "个申报成员的memberEduDegree为：" + member.getElementsByTagName("memberEduDegree").item(0).getFirstChild().getNodeValue());
				}
				sinossMembers.setProjectApplication(sinossProjectApplication);
				dao.add(sinossMembers);
			}
		}
		//存在审核记录
		NodeList checkLogs = result.getElementsByTagName("CheckLog");
		if(null !=checkLogs.item(0)){
			for(int k = 0;k<checkLogs.getLength();k++){
				SinossChecklogs sinossChecklogs = new SinossChecklogs();
				Element checkLog = (Element) checkLogs.item(k);
				if(null != checkLog.getElementsByTagName("id").item(0)){
					sinossChecklogs.setSinossId(checkLog.getElementsByTagName("id").item(0).getFirstChild().getNodeValue());
//					System.out.println("第" + (k+1) + "个审核记录的Id为：" + checkLog.getElementsByTagName("Id").item(0).getFirstChild().getNodeValue());
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
				sinossChecklogs.setProjectApplication(sinossProjectApplication);
				dao.add(sinossChecklogs);
			}
		}
	}

}
