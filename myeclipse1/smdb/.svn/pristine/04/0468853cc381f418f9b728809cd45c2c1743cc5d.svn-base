package csdc.tool.webService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import csdc.bean.SinossBook;
import csdc.bean.SinossChecklogs;
import csdc.bean.SinossConsultation;
import csdc.bean.SinossElectronic;
import csdc.bean.SinossPaper;
import csdc.bean.SinossPatent;
import csdc.bean.SinossProjectMidinspection;
import csdc.dao.IHibernateBaseDao;
import csdc.tool.execution.importer.tool.Tool;

/**
 * 中检数据解析器
 * @author suwb
 *
 */
public class MidCheckRecordsResolver {
	
	public Document doc;//已经读取了xml的document文件
	public SimpleDateFormat sdf;//规律日期格式
	public Tool tool;//徐涵写的转成规律日期格式的方法,只有年月日
	protected IHibernateBaseDao dao;
	private static List<String> midIds = new ArrayList<String>();//数据判重
	
	public MidCheckRecordsResolver(Document doc, SimpleDateFormat sdf, Tool tool, IHibernateBaseDao dao){
		this.doc = doc;
		this.sdf = sdf;
		this.tool = tool;
		this.dao = dao;
	}
	
	public void initMidId(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		String year = sdf.format(new Date());
		Map json = new HashMap();
		json.put("year", year);
		midIds = dao.query("select spm.sinossId from SinossProjectMidinspection spm where spm.batchYear =:year", json);
		if(midIds.isEmpty()){
			midIds.add("ceshiid");//即便第一次入库，也无需反复进行hql的查询，所以为了initMidId不被循环执行，增加此测试id
		}
	}
	
	/**
	 * 解析入库代码
	 * @return
	 * @throws XPathExpressionException
	 * @throws ParseException 
	 * @throws DOMException 
	 */
	public void parse() throws XPathExpressionException, DOMException, ParseException{
		if(midIds.isEmpty()){
			initMidId();
		}
		XPathFactory xFactory = XPathFactory.newInstance();
	    XPath xpath = xFactory.newXPath();
	    NodeList records =doc.getElementsByTagName("result");
	    Element result=(Element) records.item(0);
	    //基本中检信息
	    SinossProjectMidinspection sinossProjectMidinspection = new SinossProjectMidinspection();
		XPathExpression exprId = xpath.compile("//result[1]/id/text()");
		NodeList nodeId = (NodeList) exprId.evaluate(doc, XPathConstants.NODESET);
		String midId = nodeId.item(0).getNodeValue();
		if(!midIds.toString().contains(midId)){//利用社科网id判重
			if(nodeId.item(0) != null){
				sinossProjectMidinspection.setSinossId(nodeId.item(0).getNodeValue());
//				System.out.println("第" + (i+1) + "条记录的sinossId为：" + nodeId.item(0).getNodeValue());		    	
			}
		    if(null != result.getElementsByTagName("projectId").item(0)){
		    	sinossProjectMidinspection.setSinossProjectId(result.getElementsByTagName("projectId").item(0).getFirstChild().getNodeValue());
//		    	System.out.println("第" + (i+1) + "条记录的projectId为：" + nodeMidReportName.item(0).getNodeValue());		    	
		    }
		    if(null != result.getElementsByTagName("smdbProjectId").item(0)){
		    	String grantedId = result.getElementsByTagName("smdbProjectId").item(0).getFirstChild().getNodeValue();
		    	try {
		    		ProjectGranted pGranted = dao.query(ProjectGranted.class, grantedId);
		    		sinossProjectMidinspection.setProjectGranted(pGranted);				
				} catch (Exception e) {
					sinossProjectMidinspection.setProjectGranted(null);	
				}
//		    	System.out.println("第" + (i+1) + "条记录的smdbProjectId为：" + grantedId);		    	
		    }
		    XPathExpression exprCode = xpath.compile("//result[1]/code/text()");
		    NodeList nodeCode = (NodeList) exprCode.evaluate(doc, XPathConstants.NODESET);
		    if(nodeCode.item(0) != null){
		    	sinossProjectMidinspection.setCode(nodeCode.item(0).getNodeValue());
//		    	System.out.println("第" + (i+1) + "条记录的code为：" + nodeCode.item(0).getNodeValue());		    	
		    }
		    XPathExpression exprName = xpath.compile("//result[1]/name/text()");
		    NodeList nodeName = (NodeList) exprName.evaluate(doc, XPathConstants.NODESET);
		    if(nodeName.item(0) != null){
		    	sinossProjectMidinspection.setName(nodeName.item(0).getNodeValue());
//		    	System.out.println("第" + (i+1) + "条记录的name为：" + nodeName.item(0).getNodeValue());		    	
		    }
		    if(null != result.getElementsByTagName("midReportName").item(0)){
		    	sinossProjectMidinspection.setMidReportName(result.getElementsByTagName("midReportName").item(0).getFirstChild().getNodeValue());
//		    	System.out.println("第" + (i+1) + "条记录的midReportName为：" + result.getElementsByTagName("midReportName").item(0).getFirstChild().getNodeValue());		    	
		    }
		    if(null != result.getElementsByTagName("storagefileName").item(0)){
		    	sinossProjectMidinspection.setStorageFileName(result.getElementsByTagName("storagefileName").item(0).getFirstChild().getNodeValue());
//		    	System.out.println("第" + (i+1) + "条记录的storagefileName为：" + result.getElementsByTagName("storagefileName").item(0).getFirstChild().getNodeValue());		    	
		    }
		    XPathExpression exprCheckStatus = xpath.compile("//result[1]/checkStatus/text()");
		    NodeList nodeCheckStatus = (NodeList) exprCheckStatus.evaluate(doc, XPathConstants.NODESET);
		    if(nodeCheckStatus.item(0) != null){
		    	sinossProjectMidinspection.setCheckStatus(nodeCheckStatus.item(0).getNodeValue());
//		    	System.out.println("第" + (i+1) + "条记录的checkstatus为：" + nodeCheckStatus.item(0).getNodeValue());		    	
		    }
		    XPathExpression exprCheckDate = xpath.compile("//result[1]/checkDate/text()");
		    NodeList nodeCheckDate = (NodeList) exprCheckDate.evaluate(doc, XPathConstants.NODESET);
		    if(nodeCheckDate.item(0) != null){
		    	sinossProjectMidinspection.setCheckDate(sdf.parse(nodeCheckDate.item(0).getNodeValue()));
//		    	System.out.println("第" + (i+1) + "条记录的checkDate为：" + nodeCheckDate.item(0).getNodeValue());		    	
		    }
		    XPathExpression exprChecker = xpath.compile("//result[1]/checker/text()");
		    NodeList nodeChecker = (NodeList) exprChecker.evaluate(doc, XPathConstants.NODESET);
		    if(nodeChecker.item(0) != null){
		    	sinossProjectMidinspection.setChecker(nodeChecker.item(0).getNodeValue());
//		    	System.out.println("第" + (i+1) + "条记录的checker为：" + nodeChecker.item(0).getNodeValue());		    	
		    }
		    if(null != result.getElementsByTagName("applyDate").item(0)){
		    	sinossProjectMidinspection.setApplyDate(tool.getDate(result.getElementsByTagName("applyDate").item(0).getFirstChild().getNodeValue()));
//		    	System.out.println("第" + (i+1) + "条记录的applyDate为：" + result.getElementsByTagName("applyDate").item(0).getFirstChild().getNodeValue());		    	
		    }
		    if(null != result.getElementsByTagName("deferReason").item(0)){
		    	String deferReason = result.getElementsByTagName("deferReason").item(0).getFirstChild().getNodeValue();
		    	if(deferReason.length()>1000){
		    		sinossProjectMidinspection.setDeferReason(midId);	
		    	}else {
		    		sinossProjectMidinspection.setDeferReason(deferReason);		    		
		    	}
//		    	System.out.println("第" + (i+1) + "条记录的deferReason为：" + result.getElementsByTagName("deferReason").item(0).getFirstChild().getNodeValue());		    	
		    }
		    if(null != result.getElementsByTagName("deferDate").item(0)){
		    	sinossProjectMidinspection.setDeferDate(tool.getDate(result.getElementsByTagName("deferDate").item(0).getFirstChild().getNodeValue()));
//		    	System.out.println("第" + (i+1) + "条记录的deferDate为：" + result.getElementsByTagName("deferDate").item(0).getFirstChild().getNodeValue());		    	
		    }
		    if(null != result.getElementsByTagName("typeCode").item(0)){
		    	sinossProjectMidinspection.setProjectType(result.getElementsByTagName("typeCode").item(0).getFirstChild().getNodeValue());
//		    	System.out.println("第" + (i+1) + "条记录的typeCode为：" + result.getElementsByTagName("typeCode").item(0).getFirstChild().getNodeValue());		    	
		    }
		    if(null != result.getElementsByTagName("batchYear").item(0)){
		    	sinossProjectMidinspection.setBatchYear(result.getElementsByTagName("batchYear").item(0).getFirstChild().getNodeValue());
//		    	System.out.println("第" + (i+1) + "条记录的batchYear为：" + result.getElementsByTagName("batchYear").item(0).getFirstChild().getNodeValue());		    	
		    }
		    sinossProjectMidinspection.setIsAdded(1);
		    sinossProjectMidinspection.setDumpDate(new Date());
		    sinossProjectMidinspection.setDumpPerson("苏文波");
		    sinossProjectMidinspection.setFrom("SINOSS");
		    dao.add(sinossProjectMidinspection);
			NodeList checkLogs = result.getElementsByTagName("CheckLog");
			if(null !=checkLogs.item(0)){//存在中检审核记录
				for(int k = 0;k<checkLogs.getLength();k++){
					SinossChecklogs sinossChecklogs = new SinossChecklogs();
					Element checkLog = (Element) checkLogs.item(k);
					if(null != checkLog.getElementsByTagName("id").item(0)){
						sinossChecklogs.setSinossId(checkLog.getElementsByTagName("id").item(0).getFirstChild().getNodeValue());
//						System.out.println("第" + (k+1) + "个审核记录的id为：" + checkLog.getElementsByTagName("id").item(0).getFirstChild().getNodeValue());
					}
					if(null != checkLog.getElementsByTagName("checkStatus").item(0)){
						sinossChecklogs.setCheckStatus(Integer.parseInt(checkLog.getElementsByTagName("checkStatus").item(0).getFirstChild().getNodeValue()));
//						System.out.println("第" + (k+1) + "个审核记录的checkStatus为：" + checkLog.getElementsByTagName("checkStatus").item(0).getFirstChild().getNodeValue());
					}
					if(null != checkLog.getElementsByTagName("checkDate").item(0)){
						sinossChecklogs.setCheckDate(sdf.parse(checkLog.getElementsByTagName("checkDate").item(0).getFirstChild().getNodeValue()));
//						System.out.println("第" + (k+1) + "个审核记录的checkDate为：" + checkLog.getElementsByTagName("checkDate").item(0).getFirstChild().getNodeValue());
					}
					if(null != checkLog.getElementsByTagName("checker").item(0)){
						sinossChecklogs.setChecker(checkLog.getElementsByTagName("checker").item(0).getFirstChild().getNodeValue());
//						System.out.println("第" + (k+1) + "个审核记录的checker为：" + checkLog.getElementsByTagName("checker").item(0).getFirstChild().getNodeValue());
					}
					if(null != checkLog.getElementsByTagName("checkInfo").item(0)){
						sinossChecklogs.setCheckInfo(checkLog.getElementsByTagName("checkInfo").item(0).getFirstChild().getNodeValue());
//						System.out.println("第" + (k+1) + "个审核记录的checkInfo为：" + checkLog.getElementsByTagName("checkInfo").item(0).getFirstChild().getNodeValue());
					}
					sinossChecklogs.setType(2);
					sinossChecklogs.setDumpDate(new Date());
					sinossChecklogs.setIsAdded(1);
					sinossChecklogs.setProjectMidinspection(sinossProjectMidinspection);
					dao.add(sinossChecklogs);
				}
			}
			NodeList papers = result.getElementsByTagName("Paper");
			if(null != papers.item(0)){//存在论文信息
				for( int j = 0;j< papers.getLength();j++){
					SinossPaper sinossPaper = new SinossPaper();
					Element Paper = (Element) papers.item(j);
					if(null != Paper.getElementsByTagName("id").item(0)){
						sinossPaper.setSinossId(Paper.getElementsByTagName("id").item(0).getFirstChild().getNodeValue());
//						System.out.println("第" + (j+1) + "个论文的id为：" + Paper.getElementsByTagName("id").item(0).getFirstChild().getNodeValue());
					}
					if(null != Paper.getElementsByTagName("name").item(0)){
						sinossPaper.setName(Paper.getElementsByTagName("name").item(0).getFirstChild().getNodeValue());
//						System.out.println("第" + (j+1) + "个论文的name为：" + Paper.getElementsByTagName("name").item(0).getFirstChild().getNodeValue());
					}
					if(null != Paper.getElementsByTagName("firstAuthor").item(0)){
						sinossPaper.setFirstAuthor(Paper.getElementsByTagName("firstAuthor").item(0).getFirstChild().getNodeValue());
//						System.out.println("第" + (j+1) + "个论文的firstAuthor为：" + Paper.getElementsByTagName("firstAuthor").item(0).getFirstChild().getNodeValue());
					}
					if(null != Paper.getElementsByTagName("otherAuthor").item(0)){
						sinossPaper.setOtherAuthor(Paper.getElementsByTagName("otherAuthor").item(0).getFirstChild().getNodeValue());
//						System.out.println("第" + (j+1) + "个论文的otherAuthor为：" + Paper.getElementsByTagName("otherAuthor").item(0).getFirstChild().getNodeValue());
					}
					if(null != Paper.getElementsByTagName("subject").item(0)){
						sinossPaper.setSubject(Paper.getElementsByTagName("subject").item(0).getFirstChild().getNodeValue());
//						System.out.println("第" + (j+1) + "个论文的subject为：" + Paper.getElementsByTagName("subject").item(0).getFirstChild().getNodeValue());
					}
					if(null != Paper.getElementsByTagName("isMark").item(0)){
						sinossPaper.setIsMark(Paper.getElementsByTagName("isMark").item(0).getFirstChild().getNodeValue());
//						System.out.println("第" + (j+1) + "个论文的isMark为：" + Paper.getElementsByTagName("isMark").item(0).getFirstChild().getNodeValue());
					}
					if(null != Paper.getElementsByTagName("paperType").item(0)){
						sinossPaper.setPaperType(Paper.getElementsByTagName("paperType").item(0).getFirstChild().getNodeValue());
//						System.out.println("第" + (j+1) + "个论文的paperType为：" + Paper.getElementsByTagName("paperType").item(0).getFirstChild().getNodeValue());
					}
					if(null != Paper.getElementsByTagName("releaseTime").item(0)){
						sinossPaper.setPublicDate(tool.getDate(Paper.getElementsByTagName("releaseTime").item(0).getFirstChild().getNodeValue()));
//						System.out.println("第" + (j+1) + "个论文的releaseTime为：" + Paper.getElementsByTagName("releaseTime").item(0).getFirstChild().getNodeValue());
					}
					if(null != Paper.getElementsByTagName("paperBook").item(0)){
						sinossPaper.setPaperBook(Paper.getElementsByTagName("paperBook").item(0).getFirstChild().getNodeValue());
//						System.out.println("第" + (j+1) + "个论文的paperBook为：" + Paper.getElementsByTagName("paperBook").item(0).getFirstChild().getNodeValue());
					}
					if(null != Paper.getElementsByTagName("paperBookType").item(0)){
						sinossPaper.setPaperBookType(Paper.getElementsByTagName("paperBookType").item(0).getFirstChild().getNodeValue());
//						System.out.println("第" + (j+1) + "个论文的paperBookType为：" + Paper.getElementsByTagName("paperBookType").item(0).getFirstChild().getNodeValue());
					}
					if(null != Paper.getElementsByTagName("publishScope").item(0)){
						sinossPaper.setPublishScope(Paper.getElementsByTagName("publishScope").item(0).getFirstChild().getNodeValue());
//						System.out.println("第" + (j+1) + "个论文的publishScope为：" + Paper.getElementsByTagName("publishScope").item(0).getFirstChild().getNodeValue());
					}
					if(null != Paper.getElementsByTagName("juanhao").item(0)){
						sinossPaper.setJuanhao(Paper.getElementsByTagName("juanhao").item(0).getFirstChild().getNodeValue());
//						System.out.println("第" + (j+1) + "个论文的juanhao为：" + Paper.getElementsByTagName("juanhao").item(0).getFirstChild().getNodeValue());
					}
					if(null != Paper.getElementsByTagName("qihao").item(0)){
						sinossPaper.setQihao(Paper.getElementsByTagName("qihao").item(0).getFirstChild().getNodeValue());
//						System.out.println("第" + (j+1) + "个论文的qihao为：" + Paper.getElementsByTagName("qihao").item(0).getFirstChild().getNodeValue());
					}
					if(null != Paper.getElementsByTagName("pageNumScope").item(0)){
						sinossPaper.setPagenumScope(Paper.getElementsByTagName("pageNumScope").item(0).getFirstChild().getNodeValue());
//						System.out.println("第" + (j+1) + "个论文的pageNumScope为：" + Paper.getElementsByTagName("pageNumScope").item(0).getFirstChild().getNodeValue());
					}
					if(null != Paper.getElementsByTagName("wordNumber").item(0)){
						sinossPaper.setWordNumber(Paper.getElementsByTagName("wordNumber").item(0).getFirstChild().getNodeValue());
//						System.out.println("第" + (j+1) + "个论文的wordNumber为：" + Paper.getElementsByTagName("wordNumber").item(0).getFirstChild().getNodeValue());
					}
					if(null != Paper.getElementsByTagName("isTranslate").item(0)){
						sinossPaper.setIsTranslate(Paper.getElementsByTagName("isTranslate").item(0).getFirstChild().getNodeValue());
//						System.out.println("第" + (j+1) + "个论文的isTranslate为：" + Paper.getElementsByTagName("isTranslate").item(0).getFirstChild().getNodeValue());
					}
					if(null != Paper.getElementsByTagName("ISSN").item(0)){
						sinossPaper.setIssn(Paper.getElementsByTagName("ISSN").item(0).getFirstChild().getNodeValue());
//						System.out.println("第" + (j+1) + "个论文的ISSN为：" + Paper.getElementsByTagName("ISSN").item(0).getFirstChild().getNodeValue());
					}
					if(null != Paper.getElementsByTagName("CN").item(0)){
						sinossPaper.setCn(Paper.getElementsByTagName("CN").item(0).getFirstChild().getNodeValue());
//						System.out.println("第" + (j+1) + "个论文的CN为：" + Paper.getElementsByTagName("CN").item(0).getFirstChild().getNodeValue());
					}
					if(null != Paper.getElementsByTagName("note").item(0)){
						sinossPaper.setNote(Paper.getElementsByTagName("note").item(0).getFirstChild().getNodeValue());
//						System.out.println("第" + (j+1) + "个论文的note为：" + Paper.getElementsByTagName("note").item(0).getFirstChild().getNodeValue());
					}
					sinossPaper.setDumpDate(new Date());
					sinossPaper.setProjectMidinspection(sinossProjectMidinspection);
					dao.add(sinossPaper);
				}
			}
			NodeList books = result.getElementsByTagName("Book");
			if(null != books.item(0)){//存在著作信息
				for(int t=0;t<books.getLength();t++){
					SinossBook sinossBook = new SinossBook();
					Element book =(Element)books.item(t);
					if(null != book.getElementsByTagName("id").item(0)){
						sinossBook.setSinossId(book.getElementsByTagName("id").item(0).getFirstChild().getNodeValue());
//						System.out.println("第" + (t+1) + "个著作的id为：" + book.getElementsByTagName("id").item(0).getFirstChild().getNodeValue());
					}
					if(null != book.getElementsByTagName("name").item(0)){
						sinossBook.setName(book.getElementsByTagName("name").item(0).getFirstChild().getNodeValue());
//						System.out.println("第" + (t+1) + "个著作的name为：" + book.getElementsByTagName("name").item(0).getFirstChild().getNodeValue());
					}
					if(null != book.getElementsByTagName("firstAuthor").item(0)){
						sinossBook.setFirstAuthor(book.getElementsByTagName("firstAuthor").item(0).getFirstChild().getNodeValue());
//						System.out.println("第" + (t+1) + "个著作的firstAuthor为：" + book.getElementsByTagName("firstAuthor").item(0).getFirstChild().getNodeValue());
					}
					if(null != book.getElementsByTagName("otherAuthor").item(0)){
						sinossBook.setOtherAuthor(book.getElementsByTagName("otherAuthor").item(0).getFirstChild().getNodeValue());
//						System.out.println("第" + (t+1) + "个著作的otherAuthor为：" + book.getElementsByTagName("otherAuthor").item(0).getFirstChild().getNodeValue());
					}
					if(null != book.getElementsByTagName("subject").item(0)){
						sinossBook.setSubject(book.getElementsByTagName("subject").item(0).getFirstChild().getNodeValue());
//						System.out.println("第" + (t+1) + "个著作的subject为：" + book.getElementsByTagName("subject").item(0).getFirstChild().getNodeValue());
					}
					if(null != book.getElementsByTagName("isMark").item(0)){
						sinossBook.setIsMark(book.getElementsByTagName("isMark").item(0).getFirstChild().getNodeValue());
//						System.out.println("第" + (t+1) + "个著作的isMark为：" + book.getElementsByTagName("isMark").item(0).getFirstChild().getNodeValue());
					}
					if(null != book.getElementsByTagName("press").item(0)){
						sinossBook.setPress(book.getElementsByTagName("press").item(0).getFirstChild().getNodeValue());
//						System.out.println("第" + (t+1) + "个著作的press为：" + book.getElementsByTagName("press").item(0).getFirstChild().getNodeValue());
					}
					if(null != book.getElementsByTagName("pressTime").item(0)){
						sinossBook.setPressTime(tool.getDate(book.getElementsByTagName("pressTime").item(0).getFirstChild().getNodeValue()));
//						System.out.println("第" + (t+1) + "个著作的pressTime为：" + book.getElementsByTagName("pressTime").item(0).getFirstChild().getNodeValue());
					}
					if(null != book.getElementsByTagName("pressAddress").item(0)){
						sinossBook.setPressAddress(book.getElementsByTagName("pressAddress").item(0).getFirstChild().getNodeValue());
//						System.out.println("第" + (t+1) + "个著作的pressAddress为：" + book.getElementsByTagName("pressAddress").item(0).getFirstChild().getNodeValue());
					}
					if(null != book.getElementsByTagName("bookType").item(0)){
						sinossBook.setBookType(book.getElementsByTagName("bookType").item(0).getFirstChild().getNodeValue());
//						System.out.println("第" + (t+1) + "个著作的bookType为：" + book.getElementsByTagName("bookType").item(0).getFirstChild().getNodeValue());
					}
					if(null != book.getElementsByTagName("wordNumber").item(0)){
						sinossBook.setWordNumber(book.getElementsByTagName("wordNumber").item(0).getFirstChild().getNodeValue());
//						System.out.println("第" + (t+1) + "个著作的wordNumber为：" + book.getElementsByTagName("wordNumber").item(0).getFirstChild().getNodeValue());
					}
					if(null != book.getElementsByTagName("isEnglish").item(0)){
						sinossBook.setIsEnglish(book.getElementsByTagName("isEnglish").item(0).getFirstChild().getNodeValue());
//						System.out.println("第" + (t+1) + "个著作的isEnglish为：" + book.getElementsByTagName("isEnglish").item(0).getFirstChild().getNodeValue());
					}
					if(null != book.getElementsByTagName("ISBN").item(0)){
						sinossBook.setIsbn(book.getElementsByTagName("ISBN").item(0).getFirstChild().getNodeValue());
//						System.out.println("第" + (t+1) + "个著作的ISBN为：" + book.getElementsByTagName("ISBN").item(0).getFirstChild().getNodeValue());
					}
					if(null != book.getElementsByTagName("CIP").item(0)){
						sinossBook.setCip(book.getElementsByTagName("CIP").item(0).getFirstChild().getNodeValue());
//						System.out.println("第" + (t+1) + "个著作的CIP为：" + book.getElementsByTagName("CIP").item(0).getFirstChild().getNodeValue());
					}
					if(null != book.getElementsByTagName("note").item(0)){
						sinossBook.setNote(book.getElementsByTagName("note").item(0).getFirstChild().getNodeValue());
//						System.out.println("第" + (t+1) + "个著作的note为：" + book.getElementsByTagName("note").item(0).getFirstChild().getNodeValue());
					}
					sinossBook.setDumpDate(new Date());
					sinossBook.setProjectMidinspection(sinossProjectMidinspection);
					dao.add(sinossBook);
				}
			}
			NodeList patents = result.getElementsByTagName("Patent");
			if(null != patents.item(0)){//存在专利信息
				for(int m =0; m<patents.getLength(); m++){
					SinossPatent sinossPatent = new SinossPatent();
					Element patent = (Element)patents.item(m);		
					if(null != patent.getElementsByTagName("id").item(0)){
						sinossPatent.setSinossId(patent.getElementsByTagName("id").item(0).getFirstChild().getNodeValue());
//						System.out.println("第" + (m+1) + "个专利的id为：" + patent.getElementsByTagName("id").item(0).getFirstChild().getNodeValue());
					}
					if(null != patent.getElementsByTagName("name").item(0)){
						sinossPatent.setName(patent.getElementsByTagName("name").item(0).getFirstChild().getNodeValue());
//						System.out.println("第" + (m+1) + "个专利的name为：" + patent.getElementsByTagName("name").item(0).getFirstChild().getNodeValue());
					}
					if(null != patent.getElementsByTagName("firstAuthor").item(0)){
						sinossPatent.setFirstAuthor(patent.getElementsByTagName("firstAuthor").item(0).getFirstChild().getNodeValue());
//						System.out.println("第" + (m+1) + "个专利的firstAuthor为：" + patent.getElementsByTagName("firstAuthor").item(0).getFirstChild().getNodeValue());
					}
					if(null != patent.getElementsByTagName("otherAuthor").item(0)){
						sinossPatent.setOtherName(patent.getElementsByTagName("otherAuthor").item(0).getFirstChild().getNodeValue());
//						System.out.println("第" + (m+1) + "个专利的otherAuthor为：" + patent.getElementsByTagName("otherAuthor").item(0).getFirstChild().getNodeValue());
					}
					if(null != patent.getElementsByTagName("isMark").item(0)){
						sinossPatent.setIsMark(patent.getElementsByTagName("isMark").item(0).getFirstChild().getNodeValue());
//						System.out.println("第" + (m+1) + "个专利的isMark为：" + patent.getElementsByTagName("isMark").item(0).getFirstChild().getNodeValue());
					}
					if(null != patent.getElementsByTagName("patentType").item(0)){
						sinossPatent.setPatentType(patent.getElementsByTagName("patentType").item(0).getFirstChild().getNodeValue());
//						System.out.println("第" + (m+1) + "个专利的patentType为：" + patent.getElementsByTagName("patentType").item(0).getFirstChild().getNodeValue());
					}
					if(null != patent.getElementsByTagName("patentScope").item(0)){
						sinossPatent.setPatentScope(patent.getElementsByTagName("patentScope").item(0).getFirstChild().getNodeValue());
//						System.out.println("第" + (m+1) + "个专利的patentScope为：" + patent.getElementsByTagName("patentScope").item(0).getFirstChild().getNodeValue());
					}
					if(null != patent.getElementsByTagName("patentNo").item(0)){
						sinossPatent.setPatentNumber(patent.getElementsByTagName("patentNo").item(0).getFirstChild().getNodeValue());
//						System.out.println("第" + (m+1) + "个专利的patentNo为：" + patent.getElementsByTagName("patentNo").item(0).getFirstChild().getNodeValue());
					}
					if(null != patent.getElementsByTagName("authorizeDate").item(0)){
						sinossPatent.setAuthorizeDate(tool.getDate(patent.getElementsByTagName("authorizeDate").item(0).getFirstChild().getNodeValue()));
//						System.out.println("第" + (m+1) + "个专利的authorizeDate为：" + patent.getElementsByTagName("authorizeDate").item(0).getFirstChild().getNodeValue());
					}
					if(null != patent.getElementsByTagName("patentPerson").item(0)){
						sinossPatent.setPatentPerson(patent.getElementsByTagName("patentPerson").item(0).getFirstChild().getNodeValue());
//						System.out.println("第" + (m+1) + "个专利的patentPerson为：" + patent.getElementsByTagName("patentPerson").item(0).getFirstChild().getNodeValue());
					}
					if(null != patent.getElementsByTagName("note").item(0)){
						sinossPatent.setNote(patent.getElementsByTagName("note").item(0).getFirstChild().getNodeValue());
//						System.out.println("第" + (m+1) + "个专利的note为：" + patent.getElementsByTagName("note").item(0).getFirstChild().getNodeValue());
					}
					sinossPatent.setDumpDate(new Date());
					sinossPatent.setProjectMidinspection(sinossProjectMidinspection);
					dao.add(sinossPatent);
				}
			}
			NodeList researches = result.getElementsByTagName("Researche");
			if(null != researches.item(0)){//存在研究咨询报告信息
				for(int n=0; n<researches.getLength(); n++){
					SinossConsultation sinossConsultation = new SinossConsultation();
					Element research = (Element)researches.item(n);
					if(null != research.getElementsByTagName("id").item(0)){
						sinossConsultation.setSinossId(research.getElementsByTagName("id").item(0).getFirstChild().getNodeValue());
//						System.out.println("第" + (n+1) + "个研究咨询报告的id为：" + research.getElementsByTagName("id").item(0).getFirstChild().getNodeValue());
					}
					if(null != research.getElementsByTagName("name").item(0)){
						sinossConsultation.setName(research.getElementsByTagName("name").item(0).getFirstChild().getNodeValue());
//						System.out.println("第" + (n+1) + "个研究咨询报告的name为：" + research.getElementsByTagName("name").item(0).getFirstChild().getNodeValue());
					}
					if(null != research.getElementsByTagName("firstAuthor").item(0)){
						sinossConsultation.setFirstAuthor(research.getElementsByTagName("firstAuthor").item(0).getFirstChild().getNodeValue());
//						System.out.println("第" + (n+1) + "个研究咨询报告的firstAuthor为：" + research.getElementsByTagName("firstAuthor").item(0).getFirstChild().getNodeValue());
					}
					if(null != research.getElementsByTagName("otherAuthor").item(0)){
						sinossConsultation.setOtherAuthor(research.getElementsByTagName("otherAuthor").item(0).getFirstChild().getNodeValue());
//						System.out.println("第" + (n+1) + "个研究咨询报告的otherAuthor为：" + research.getElementsByTagName("otherAuthor").item(0).getFirstChild().getNodeValue());
					}
					if(null != research.getElementsByTagName("isMark").item(0)){
						sinossConsultation.setIsMark(research.getElementsByTagName("isMark").item(0).getFirstChild().getNodeValue());
//						System.out.println("第" + (n+1) + "个研究咨询报告的isMark为：" + research.getElementsByTagName("isMark").item(0).getFirstChild().getNodeValue());
					}
					if(null != research.getElementsByTagName("commitUnit").item(0)){
						sinossConsultation.setCommitUnit(research.getElementsByTagName("commitUnit").item(0).getFirstChild().getNodeValue());
//						System.out.println("第" + (n+1) + "个研究咨询报告的commitUnit为：" + research.getElementsByTagName("commitUnit").item(0).getFirstChild().getNodeValue());
					}
					if(null != research.getElementsByTagName("commitDate").item(0)){
						sinossConsultation.setCommitDate(tool.getDate(research.getElementsByTagName("commitDate").item(0).getFirstChild().getNodeValue()));
//						System.out.println("第" + (n+1) + "个研究咨询报告的commitDate为：" + research.getElementsByTagName("commitDate").item(0).getFirstChild().getNodeValue());
					}
					if(null != research.getElementsByTagName("isAccept").item(0)){
						sinossConsultation.setIsAccept(research.getElementsByTagName("isAccept").item(0).getFirstChild().getNodeValue());
//						System.out.println("第" + (n+1) + "个研究咨询报告的isAccept为：" + research.getElementsByTagName("isAccept").item(0).getFirstChild().getNodeValue());
					}
					if(null != research.getElementsByTagName("acceptObj").item(0)){
						sinossConsultation.setAcceptObj(research.getElementsByTagName("acceptObj").item(0).getFirstChild().getNodeValue());
//						System.out.println("第" + (n+1) + "个研究咨询报告的acceptObj为：" + research.getElementsByTagName("acceptObj").item(0).getFirstChild().getNodeValue());
					}
					if(null != research.getElementsByTagName("note").item(0)){
						sinossConsultation.setNote(research.getElementsByTagName("note").item(0).getFirstChild().getNodeValue());
//						System.out.println("第" + (n+1) + "个研究咨询报告的note为：" + research.getElementsByTagName("note").item(0).getFirstChild().getNodeValue());
					}
					sinossConsultation.setDumpDate(new Date());
					sinossConsultation.setProjectMidinspection(sinossProjectMidinspection);
					dao.add(sinossConsultation);
				}
			}
			NodeList videoProducts = result.getElementsByTagName("VideoProduct");
			if(null != videoProducts.item(0)){//存在电子出版物信息
				for(int p=0; p<videoProducts.getLength(); p++){
					SinossElectronic sinossElectronic = new SinossElectronic();
					Element videoProduct = (Element)videoProducts.item(p);
					if(null != videoProduct.getElementsByTagName("id").item(0)){
						sinossElectronic.setSinossId(videoProduct.getElementsByTagName("id").item(0).getFirstChild().getNodeValue());
//						System.out.println("第" + (p+1) + "个电子出版物的id为：" + videoProduct.getElementsByTagName("id").item(0).getFirstChild().getNodeValue());
					}
					if(null != videoProduct.getElementsByTagName("name").item(0)){
						sinossElectronic.setName(videoProduct.getElementsByTagName("name").item(0).getFirstChild().getNodeValue());
//						System.out.println("第" + (p+1) + "个电子出版物的name为：" + videoProduct.getElementsByTagName("name").item(0).getFirstChild().getNodeValue());
					}
					if(null != videoProduct.getElementsByTagName("firstAuthor").item(0)){
						sinossElectronic.setFirstAuthor(videoProduct.getElementsByTagName("firstAuthor").item(0).getFirstChild().getNodeValue());
//						System.out.println("第" + (p+1) + "个电子出版物的firstAuthor为：" + videoProduct.getElementsByTagName("firstAuthor").item(0).getFirstChild().getNodeValue());
					}
					if(null != videoProduct.getElementsByTagName("otherAuthor").item(0)){
						sinossElectronic.setOtherAuthor(videoProduct.getElementsByTagName("otherAuthor").item(0).getFirstChild().getNodeValue());
//						System.out.println("第" + (p+1) + "个电子出版物的otherAuthor为：" + videoProduct.getElementsByTagName("otherAuthor").item(0).getFirstChild().getNodeValue());
					}
					if(null != videoProduct.getElementsByTagName("subject").item(0)){
						sinossElectronic.setSubject(videoProduct.getElementsByTagName("subject").item(0).getFirstChild().getNodeValue());
//						System.out.println("第" + (p+1) + "个电子出版物的subject为：" + videoProduct.getElementsByTagName("subject").item(0).getFirstChild().getNodeValue());
					}
					if(null != videoProduct.getElementsByTagName("isMark").item(0)){
						sinossElectronic.setIsMark(videoProduct.getElementsByTagName("isMark").item(0).getFirstChild().getNodeValue());
//						System.out.println("第" + (p+1) + "个电子出版物的isMark为：" + videoProduct.getElementsByTagName("isMark").item(0).getFirstChild().getNodeValue());
					}
					if(null != videoProduct.getElementsByTagName("press").item(0)){
						sinossElectronic.setPress(videoProduct.getElementsByTagName("press").item(0).getFirstChild().getNodeValue());
//						System.out.println("第" + (p+1) + "个电子出版物的press为：" + videoProduct.getElementsByTagName("press").item(0).getFirstChild().getNodeValue());
					}
					if(null != videoProduct.getElementsByTagName("pressTime").item(0)){
						sinossElectronic.setPressDate(tool.getDate(videoProduct.getElementsByTagName("pressTime").item(0).getFirstChild().getNodeValue()));
//						System.out.println("第" + (p+1) + "个电子出版物的pressTime为：" + videoProduct.getElementsByTagName("pressTime").item(0).getFirstChild().getNodeValue());
					}
					if(null != videoProduct.getElementsByTagName("pressAddress").item(0)){
						sinossElectronic.setPressAddress(videoProduct.getElementsByTagName("pressAddress").item(0).getFirstChild().getNodeValue());
//						System.out.println("第" + (p+1) + "个电子出版物的pressAddress为：" + videoProduct.getElementsByTagName("pressAddress").item(0).getFirstChild().getNodeValue());
					}
					if(null != videoProduct.getElementsByTagName("ISBN").item(0)){
						sinossElectronic.setIsbn(videoProduct.getElementsByTagName("ISBN").item(0).getFirstChild().getNodeValue());
//						System.out.println("第" + (p+1) + "个电子出版物的ISBN为：" + videoProduct.getElementsByTagName("ISBN").item(0).getFirstChild().getNodeValue());
					}
					if(null != videoProduct.getElementsByTagName("note").item(0)){
						sinossElectronic.setNote(videoProduct.getElementsByTagName("note").item(0).getFirstChild().getNodeValue());
//						System.out.println("第" + (p+1) + "个电子出版物的note为：" + videoProduct.getElementsByTagName("note").item(0).getFirstChild().getNodeValue());
					}
					sinossElectronic.setDumpDate(new Date());
					sinossElectronic.setProjectMidinspection(sinossProjectMidinspection);
					dao.add(sinossElectronic);
				}
			}
		}
	}
}
