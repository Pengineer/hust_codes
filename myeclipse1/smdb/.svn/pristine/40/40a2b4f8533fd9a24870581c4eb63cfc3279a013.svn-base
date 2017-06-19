package csdc.tool.webService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import csdc.bean.SinossProjectEndinspection;
import csdc.dao.IHibernateBaseDao;
import csdc.tool.execution.importer.tool.Tool;

/**
 * 结项结果解析器
 * @author suwb
 *
 */
public class FinishRecordResolver {
	
	public Document doc;//已经读取了xml的document文件
	public SimpleDateFormat sdf;//规律日期格式
	public Tool tool;//徐涵写的转成规律日期格式的方法,只有年月日
	protected IHibernateBaseDao dao;
	private static List<String> finishIds = new ArrayList<String>();//数据判重
	
	public FinishRecordResolver(Document doc, SimpleDateFormat sdf, Tool tool, IHibernateBaseDao dao){
		this.doc = doc;
		this.sdf = sdf;
		this.tool = tool;
		this.dao = dao;
	}
	
	public void initFinishId(){
		finishIds = dao.query("select spe.sinossId from SinossProjectEndinspection spe");
		if(finishIds.isEmpty()){
			finishIds.add("ceshiid");//即便第一次入库，也无需反复进行hql的查询，所以为了initFinishId不被循环执行，增加此测试id
		}
	}
	
	/**
	 * 解析入库代码
	 * @return
	 * @throws ParseException 
	 * @throws DOMException 
	 * @throws XPathExpressionException 
	 */
	public void parse() throws DOMException, ParseException, XPathExpressionException{
		if(finishIds.isEmpty()){
			initFinishId();
		}
		NodeList records =doc.getElementsByTagName("result");
		XPathFactory xFactory = XPathFactory.newInstance();
	    XPath xpath = xFactory.newXPath();
		Element result=(Element) records.item(0);//基本信息
		XPathExpression exprId = xpath.compile("//result[1]/id/text()");
		NodeList nodeId = (NodeList) exprId.evaluate(doc, XPathConstants.NODESET);
		String finishId = nodeId.item(0).getNodeValue();
		if(!finishIds.toString().contains(finishId)){//TODO 注意，现在数据同步完会有对应的标志位，同时同步过的xml文件也会相应的更名，所以这里似乎不需要判重
			SinossProjectEndinspection sinossProjectEndinspection = new SinossProjectEndinspection();
			if(nodeId.item(0) != null){
				sinossProjectEndinspection.setSinossId(finishId);
	//			System.out.println("第" + (i+1) + "条记录的sinossId为：" + nodeId.item(0).getNodeValue());		    	
			}
			if(null != result.getElementsByTagName("projectId").item(0)){
				sinossProjectEndinspection.setSinossProjectId(result.getElementsByTagName("projectId").item(0).getFirstChild().getNodeValue());
	//			System.out.println("第" + (i+1) + "条记录的projectId为：" + result.getElementsByTagName("projectId").item(0).getFirstChild().getNodeValue());
			}
			if(null != result.getElementsByTagName("smdbProjectId").item(0)){
				String grantedId = result.getElementsByTagName("smdbProjectId").item(0).getFirstChild().getNodeValue();
				try {
		    		ProjectGranted pGranted = dao.query(ProjectGranted.class, grantedId);
		    		sinossProjectEndinspection.setProjectGranted(pGranted);				
				} catch (Exception e) {
					sinossProjectEndinspection.setProjectGranted(null);	
				}
	//			System.out.println("第" + (i+1) + "条记录的smdbProjectId为：" + result.getElementsByTagName("smdbProjectId").item(0).getFirstChild().getNodeValue());
			}
			if(null != result.getElementsByTagName("code").item(0)){
				sinossProjectEndinspection.setCode(result.getElementsByTagName("code").item(0).getFirstChild().getNodeValue());
	//			System.out.println("第" + (i+1) + "条记录的code为：" + result.getElementsByTagName("code").item(0).getFirstChild().getNodeValue());
			}
			if(null != result.getElementsByTagName("typeCode").item(0)){
				sinossProjectEndinspection.setTypeCode(result.getElementsByTagName("typeCode").item(0).getFirstChild().getNodeValue());	
	//			System.out.println("第" + (i+1) + "条记录的typeCode为：" + result.getElementsByTagName("typeCode").item(0).getFirstChild().getNodeValue());
			}
			if(null != result.getElementsByTagName("name").item(0)){
				sinossProjectEndinspection.setName(result.getElementsByTagName("name").item(0).getFirstChild().getNodeValue());	
	//			System.out.println("第" + (i+1) + "条记录的name为：" + result.getElementsByTagName("name").item(m-1).getFirstChild().getNodeValue());
			}
			XPathExpression exprCheckstatus = xpath.compile("//result[1]/checkStatus/text()");
			NodeList nodeCheckstatus = (NodeList) exprCheckstatus.evaluate(doc, XPathConstants.NODESET);
			if(nodeCheckstatus.item(0) != null){
				sinossProjectEndinspection.setCheckStatus(nodeCheckstatus.item(0).getNodeValue());
	//			System.out.println("第" + (i+1) + "条记录的checkstatus为：" + nodeCheckstatus.item(0).getNodeValue());		    	
			}
			XPathExpression exprCheckDate = xpath.compile("//result[1]/checkDate/text()");
			NodeList nodeCheckDate = (NodeList) exprCheckDate.evaluate(doc, XPathConstants.NODESET);
			if(nodeCheckDate.item(0) != null){
				sinossProjectEndinspection.setCheckDate(sdf.parse(nodeCheckDate.item(0).getNodeValue()));
	//			System.out.println("第" + (i+1) + "条记录的checkDate为：" + nodeCheckDate.item(0).getNodeValue());		    	
			}
			XPathExpression exprChecker = xpath.compile("//result[1]/checker/text()");
			NodeList nodeChecker = (NodeList) exprChecker.evaluate(doc, XPathConstants.NODESET);
			if(nodeChecker.item(0) != null){
				sinossProjectEndinspection.setChecker(nodeChecker.item(0).getNodeValue());
	//			System.out.println("第" + (i+1) + "条记录的checker为：" + nodeChecker.item(0).getNodeValue());		    	
			}
			if(null != result.getElementsByTagName("finishReportId").item(0)){
				sinossProjectEndinspection.setFinishReportId(result.getElementsByTagName("finishReportId").item(0).getFirstChild().getNodeValue());
	//			System.out.println("第" + (i+1) + "条记录的projectModifyType为：" + result.getElementsByTagName("finishReportId").item(0).getFirstChild().getNodeValue());
			}
			sinossProjectEndinspection.setDumpDate(new Date());
			sinossProjectEndinspection.setDumpPerson("苏文波");
			sinossProjectEndinspection.setFrom("SINOSS");
			dao.add(sinossProjectEndinspection);
			NodeList checkLogs = result.getElementsByTagName("CheckLog");
			if(null !=checkLogs.item(0)){//存在审核记录
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
					sinossChecklogs.setType(4);
					sinossChecklogs.setDumpDate(new Date());
					sinossChecklogs.setIsAdded(1);
					sinossChecklogs.setProjectEndinspection(sinossProjectEndinspection);
					dao.add(sinossChecklogs);
				}
			}
		}
	}
}
