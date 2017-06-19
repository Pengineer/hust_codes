package csdc.tool.webService;

import java.text.SimpleDateFormat;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import csdc.dao.HibernateBaseDao;
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
	protected HibernateBaseDao dao;
	
	public MidCheckRecordsResolver(Document doc, SimpleDateFormat sdf, Tool tool, HibernateBaseDao dao){
		this.doc = doc;
		this.sdf = sdf;
		this.tool = tool;
		this.dao = dao;
	}
	
	/**
	 * 解析入库代码
	 * @return
	 * @throws XPathExpressionException
	 */
	public void prase() throws XPathExpressionException{
		XPathFactory xFactory = XPathFactory.newInstance();
	    XPath xpath = xFactory.newXPath();
	    NodeList records =doc.getElementsByTagName("result");
	    XPathExpression exprProjectId = xpath.compile("//result[1]/projectId/text()");
	    NodeList nodeProjectId = (NodeList) exprProjectId.evaluate(doc, XPathConstants.NODESET);
	    if(nodeProjectId.item(0) != null){
//	    	System.out.println("第" + (i+1) + "条记录的projectId为：" + nodeProjectId.item(0).getNodeValue());		    	
	    }
	    XPathExpression exprCode = xpath.compile("//result[1]/code/text()");
	    NodeList nodeCode = (NodeList) exprCode.evaluate(doc, XPathConstants.NODESET);
	    if(nodeCode.item(0) != null){
//	    	System.out.println("第" + (i+1) + "条记录的code为：" + nodeCode.item(0).getNodeValue());		    	
	    }
	    XPathExpression exprName = xpath.compile("//result[1]/name/text()");
	    NodeList nodeName = (NodeList) exprName.evaluate(doc, XPathConstants.NODESET);
	    if(nodeName.item(0) != null){
//	    	System.out.println("第" + (i+1) + "条记录的name为：" + nodeName.item(0).getNodeValue());		    	
	    }
	    XPathExpression exprMidReportName = xpath.compile("//result[1]/midReportName/text()");
	    NodeList nodeMidReportName = (NodeList) exprMidReportName.evaluate(doc, XPathConstants.NODESET);
	    if(nodeMidReportName.item(0) != null){
//	    	System.out.println("第" + (i+1) + "条记录的midReportName为：" + nodeMidReportName.item(0).getNodeValue());		    	
	    }
	    XPathExpression exprCheckStatus = xpath.compile("//result[1]/checkstatus/text()");
	    NodeList nodeCheckStatus = (NodeList) exprCheckStatus.evaluate(doc, XPathConstants.NODESET);
	    if(nodeCheckStatus.item(0) != null){
//	    	System.out.println("第" + (i+1) + "条记录的checkstatus为：" + nodeCheckStatus.item(0).getNodeValue());		    	
	    }
	    XPathExpression exprCheckInfo = xpath.compile("//result[1]/checkInfo/text()");
	    NodeList nodeCheckInfo = (NodeList) exprCheckInfo.evaluate(doc, XPathConstants.NODESET);
	    if(nodeCheckInfo.item(0) != null){
//	    	System.out.println("第" + (i+1) + "条记录的checkInfo为：" + nodeCheckInfo.item(0).getNodeValue());		    	
	    }
	    XPathExpression exprCheckDate = xpath.compile("//result[1]/checkDate/text()");
	    NodeList nodeCheckDate = (NodeList) exprCheckDate.evaluate(doc, XPathConstants.NODESET);
	    if(nodeCheckDate.item(0) != null){
//	    	System.out.println("第" + (i+1) + "条记录的checkDate为：" + nodeCheckDate.item(0).getNodeValue());		    	
	    }
	    XPathExpression exprChecker = xpath.compile("//result[1]/checker/text()");
	    NodeList nodeChecker = (NodeList) exprChecker.evaluate(doc, XPathConstants.NODESET);
	    if(nodeChecker.item(0) != null){
//	    	System.out.println("第" + (i+1) + "条记录的checker为：" + nodeChecker.item(0).getNodeValue());		    	
	    }
	    XPathExpression exprApplyDate = xpath.compile("//result[1]/applyDate/text()");
	    NodeList nodeApplyDate = (NodeList) exprApplyDate.evaluate(doc, XPathConstants.NODESET);
	    if(nodeApplyDate.item(0) != null){
//	    	System.out.println("第" + (i+1) + "条记录的applyDate为：" + nodeApplyDate.item(0).getNodeValue());		    	
	    }
	    XPathExpression exprDeferReason = xpath.compile("//result[1]/deferReason/text()");
	    NodeList nodeDeferReason = (NodeList) exprDeferReason.evaluate(doc, XPathConstants.NODESET);
	    if(nodeDeferReason.item(0) != null){
//	    	System.out.println("第" + (i+1) + "条记录的deferReason为：" + nodeDeferReason.item(0).getNodeValue());		    	
	    }
	    XPathExpression exprDeferDate = xpath.compile("//result[1]/deferDate/text()");
	    NodeList nodeDeferDate = (NodeList) exprDeferDate.evaluate(doc, XPathConstants.NODESET);
	    if(nodeDeferDate.item(0) != null){
//	    	System.out.println("第" + (i+1) + "条记录的deferDate为：" + nodeDeferDate.item(0).getNodeValue());		    	
	    }		    
		Element result=(Element) records.item(0);
		NodeList checkLogs = result.getElementsByTagName("CheckLog");
		if(null !=checkLogs.item(0)){//存在中检审核记录
			for(int k = 0;k<checkLogs.getLength();k++){
				Element checkLog = (Element) checkLogs.item(k);
				if(null != checkLog.getElementsByTagName("id").item(0)){
					System.out.println("第" + (k+1) + "个审核记录的id为：" + checkLog.getElementsByTagName("id").item(0).getFirstChild().getNodeValue());
				}
				if(null != checkLog.getElementsByTagName("checkStatus").item(0)){
					System.out.println("第" + (k+1) + "个审核记录的checkStatus为：" + checkLog.getElementsByTagName("checkStatus").item(0).getFirstChild().getNodeValue());
				}
				if(null != checkLog.getElementsByTagName("checkDate").item(0)){
					System.out.println("第" + (k+1) + "个审核记录的checkDate为：" + checkLog.getElementsByTagName("checkDate").item(0).getFirstChild().getNodeValue());
				}
				if(null != checkLog.getElementsByTagName("checker").item(0)){	
					System.out.println("第" + (k+1) + "个审核记录的checker为：" + checkLog.getElementsByTagName("checker").item(0).getFirstChild().getNodeValue());
				}
				if(null != checkLog.getElementsByTagName("checkInfo").item(0)){
					System.out.println("第" + (k+1) + "个审核记录的checkInfo为：" + checkLog.getElementsByTagName("checkInfo").item(0).getFirstChild().getNodeValue());
				}					
			}
		}
		NodeList papers = result.getElementsByTagName("Paper");
		if(null != papers.item(0)){//存在论文信息
			for( int j = 0;j< papers.getLength();j++){
				Element Paper = (Element) papers.item(j);
				if(null != Paper.getElementsByTagName("id").item(0)){
					System.out.println("第" + (j+1) + "个论文的id为：" + Paper.getElementsByTagName("id").item(0).getFirstChild().getNodeValue());
				}
				if(null != Paper.getElementsByTagName("name").item(0)){
					System.out.println("第" + (j+1) + "个论文的name为：" + Paper.getElementsByTagName("name").item(0).getFirstChild().getNodeValue());
				}
				if(null != Paper.getElementsByTagName("firstAuthor").item(0)){	
					System.out.println("第" + (j+1) + "个论文的firstAuthor为：" + Paper.getElementsByTagName("firstAuthor").item(0).getFirstChild().getNodeValue());
				}
				if(null != Paper.getElementsByTagName("otherAuthor").item(0)){
					System.out.println("第" + (j+1) + "个论文的otherAuthor为：" + Paper.getElementsByTagName("otherAuthor").item(0).getFirstChild().getNodeValue());
				}
				if(null != Paper.getElementsByTagName("subject").item(0)){
					System.out.println("第" + (j+1) + "个论文的subject为：" + Paper.getElementsByTagName("subject").item(0).getFirstChild().getNodeValue());
				}
				if(null != Paper.getElementsByTagName("isMark").item(0)){
					System.out.println("第" + (j+1) + "个论文的isMark为：" + Paper.getElementsByTagName("isMark").item(0).getFirstChild().getNodeValue());
				}
				if(null != Paper.getElementsByTagName("paperType").item(0)){	
					System.out.println("第" + (j+1) + "个论文的paperType为：" + Paper.getElementsByTagName("paperType").item(0).getFirstChild().getNodeValue());
				}
				if(null != Paper.getElementsByTagName("releaseTime").item(0)){
					System.out.println("第" + (j+1) + "个论文的releaseTime为：" + Paper.getElementsByTagName("releaseTime").item(0).getFirstChild().getNodeValue());
				}
				if(null != Paper.getElementsByTagName("paperBook").item(0)){
					System.out.println("第" + (j+1) + "个论文的paperBook为：" + Paper.getElementsByTagName("paperBook").item(0).getFirstChild().getNodeValue());
				}
				if(null != Paper.getElementsByTagName("paperBookType").item(0)){
					System.out.println("第" + (j+1) + "个论文的paperBookType为：" + Paper.getElementsByTagName("paperBookType").item(0).getFirstChild().getNodeValue());
				}
				if(null != Paper.getElementsByTagName("publishScope").item(0)){
					System.out.println("第" + (j+1) + "个论文的publishScope为：" + Paper.getElementsByTagName("publishScope").item(0).getFirstChild().getNodeValue());
				}
				if(null != Paper.getElementsByTagName("juanhao").item(0)){
					System.out.println("第" + (j+1) + "个论文的juanhao为：" + Paper.getElementsByTagName("juanhao").item(0).getFirstChild().getNodeValue());
				}
				if(null != Paper.getElementsByTagName("qihao").item(0)){
					System.out.println("第" + (j+1) + "个论文的qihao为：" + Paper.getElementsByTagName("qihao").item(0).getFirstChild().getNodeValue());
				}
				if(null != Paper.getElementsByTagName("pageNumScope").item(0)){
					System.out.println("第" + (j+1) + "个论文的pageNumScope为：" + Paper.getElementsByTagName("pageNumScope").item(0).getFirstChild().getNodeValue());
				}
				if(null != Paper.getElementsByTagName("wordNumber").item(0)){
					System.out.println("第" + (j+1) + "个论文的wordNumber为：" + Paper.getElementsByTagName("wordNumber").item(0).getFirstChild().getNodeValue());
				}
				if(null != Paper.getElementsByTagName("isTranslate").item(0)){
					System.out.println("第" + (j+1) + "个论文的isTranslate为：" + Paper.getElementsByTagName("isTranslate").item(0).getFirstChild().getNodeValue());
				}
				if(null != Paper.getElementsByTagName("ISSN").item(0)){
					System.out.println("第" + (j+1) + "个论文的ISSN为：" + Paper.getElementsByTagName("ISSN").item(0).getFirstChild().getNodeValue());
				}
				if(null != Paper.getElementsByTagName("CN").item(0)){
					System.out.println("第" + (j+1) + "个论文的CN为：" + Paper.getElementsByTagName("CN").item(0).getFirstChild().getNodeValue());
				}
				if(null != Paper.getElementsByTagName("note").item(0)){
					System.out.println("第" + (j+1) + "个论文的note为：" + Paper.getElementsByTagName("note").item(0).getFirstChild().getNodeValue());
				}
			}
		}
		NodeList books = result.getElementsByTagName("Book");
		if(null != books.item(0)){//存在著作信息
			for(int t=0;t<books.getLength();t++){
				Element book =(Element)books.item(t);
				if(null != book.getElementsByTagName("id").item(0)){
					System.out.println("第" + (t+1) + "个著作的id为：" + book.getElementsByTagName("id").item(0).getFirstChild().getNodeValue());
				}
				if(null != book.getElementsByTagName("name").item(0)){
					System.out.println("第" + (t+1) + "个著作的name为：" + book.getElementsByTagName("name").item(0).getFirstChild().getNodeValue());
				}
				if(null != book.getElementsByTagName("firstAuthor").item(0)){	
					System.out.println("第" + (t+1) + "个著作的firstAuthor为：" + book.getElementsByTagName("firstAuthor").item(0).getFirstChild().getNodeValue());
				}
				if(null != book.getElementsByTagName("otherAuthor").item(0)){
					System.out.println("第" + (t+1) + "个著作的otherAuthor为：" + book.getElementsByTagName("otherAuthor").item(0).getFirstChild().getNodeValue());
				}
				if(null != book.getElementsByTagName("subject").item(0)){
					System.out.println("第" + (t+1) + "个著作的subject为：" + book.getElementsByTagName("subject").item(0).getFirstChild().getNodeValue());
				}
				if(null != book.getElementsByTagName("isMark").item(0)){
					System.out.println("第" + (t+1) + "个著作的isMark为：" + book.getElementsByTagName("isMark").item(0).getFirstChild().getNodeValue());
				}
				if(null != book.getElementsByTagName("press").item(0)){
					System.out.println("第" + (t+1) + "个著作的press为：" + book.getElementsByTagName("press").item(0).getFirstChild().getNodeValue());
				}
				if(null != book.getElementsByTagName("pressTime").item(0)){
					System.out.println("第" + (t+1) + "个著作的pressTime为：" + book.getElementsByTagName("pressTime").item(0).getFirstChild().getNodeValue());
				}
				if(null != book.getElementsByTagName("pressAddress").item(0)){	
					System.out.println("第" + (t+1) + "个著作的pressAddress为：" + book.getElementsByTagName("pressAddress").item(0).getFirstChild().getNodeValue());
				}
				if(null != book.getElementsByTagName("bookType").item(0)){
					System.out.println("第" + (t+1) + "个著作的bookType为：" + book.getElementsByTagName("bookType").item(0).getFirstChild().getNodeValue());
				}
				if(null != book.getElementsByTagName("wordNumber").item(0)){	
					System.out.println("第" + (t+1) + "个著作的wordNumber为：" + book.getElementsByTagName("wordNumber").item(0).getFirstChild().getNodeValue());
				}
				if(null != book.getElementsByTagName("isEnglish").item(0)){	
					System.out.println("第" + (t+1) + "个著作的isEnglish为：" + book.getElementsByTagName("isEnglish").item(0).getFirstChild().getNodeValue());
				}
				if(null != book.getElementsByTagName("ISBN").item(0)){
					System.out.println("第" + (t+1) + "个著作的ISBN为：" + book.getElementsByTagName("ISBN").item(0).getFirstChild().getNodeValue());
				}
				if(null != book.getElementsByTagName("CIP").item(0)){	
					System.out.println("第" + (t+1) + "个著作的CIP为：" + book.getElementsByTagName("CIP").item(0).getFirstChild().getNodeValue());
				}
			}
		}
		NodeList patents = result.getElementsByTagName("Patent");
		if(null != patents.item(0)){//存在专利信息
			for(int m =0; m<patents.getLength(); m++){
				Element patent = (Element)patents.item(m);		
				if(null != patent.getElementsByTagName("id").item(0)){
					System.out.println("第" + (m+1) + "个专利的id为：" + patent.getElementsByTagName("id").item(0).getFirstChild().getNodeValue());
				}
				if(null != patent.getElementsByTagName("name").item(0)){
					System.out.println("第" + (m+1) + "个专利的name为：" + patent.getElementsByTagName("name").item(0).getFirstChild().getNodeValue());
				}
				if(null != patent.getElementsByTagName("firstAuthor").item(0)){	
					System.out.println("第" + (m+1) + "个专利的firstAuthor为：" + patent.getElementsByTagName("firstAuthor").item(0).getFirstChild().getNodeValue());
				}
				if(null != patent.getElementsByTagName("otherAuthor").item(0)){
					System.out.println("第" + (m+1) + "个专利的otherAuthor为：" + patent.getElementsByTagName("otherAuthor").item(0).getFirstChild().getNodeValue());
				}
				if(null != patent.getElementsByTagName("isMark").item(0)){
					System.out.println("第" + (m+1) + "个专利的isMark为：" + patent.getElementsByTagName("isMark").item(0).getFirstChild().getNodeValue());
				}
				if(null != patent.getElementsByTagName("patentType").item(0)){
					System.out.println("第" + (m+1) + "个专利的patentType为：" + patent.getElementsByTagName("patentType").item(0).getFirstChild().getNodeValue());
				}
				if(null != patent.getElementsByTagName("patentScope").item(0)){
					System.out.println("第" + (m+1) + "个专利的patentScope为：" + patent.getElementsByTagName("patentScope").item(0).getFirstChild().getNodeValue());
				}
				if(null != patent.getElementsByTagName("patentNo").item(0)){
					System.out.println("第" + (m+1) + "个专利的patentNo为：" + patent.getElementsByTagName("patentNo").item(0).getFirstChild().getNodeValue());
				}
				if(null != patent.getElementsByTagName("authorizeDate").item(0)){
					System.out.println("第" + (m+1) + "个专利的authorizeDate为：" + patent.getElementsByTagName("authorizeDate").item(0).getFirstChild().getNodeValue());
				}
				if(null != patent.getElementsByTagName("patentPerson").item(0)){
					System.out.println("第" + (m+1) + "个专利的patentPerson为：" + patent.getElementsByTagName("patentPerson").item(0).getFirstChild().getNodeValue());
				}
				if(null != patent.getElementsByTagName("note").item(0)){
					System.out.println("第" + (m+1) + "个专利的note为：" + patent.getElementsByTagName("note").item(0).getFirstChild().getNodeValue());
				}
			}
		}
		NodeList researches = result.getElementsByTagName("Research");
		if(null != researches.item(0)){//存在研究咨询报告信息
			for(int n=0; n<researches.getLength(); n++){
				Element research = (Element)researches.item(n);
				if(null != research.getElementsByTagName("id").item(0)){
					System.out.println("第" + (n+1) + "个研究咨询报告的id为：" + research.getElementsByTagName("id").item(0).getFirstChild().getNodeValue());
				}
				if(null != research.getElementsByTagName("name").item(0)){
					System.out.println("第" + (n+1) + "个研究咨询报告的name为：" + research.getElementsByTagName("name").item(0).getFirstChild().getNodeValue());
				}
				if(null != research.getElementsByTagName("firstAuthor").item(0)){
					System.out.println("第" + (n+1) + "个研究咨询报告的firstAuthor为：" + research.getElementsByTagName("firstAuthor").item(0).getFirstChild().getNodeValue());
				}
				if(null != research.getElementsByTagName("otherAuthor").item(0)){
					System.out.println("第" + (n+1) + "个研究咨询报告的otherAuthor为：" + research.getElementsByTagName("otherAuthor").item(0).getFirstChild().getNodeValue());
				}
				if(null != research.getElementsByTagName("isMark").item(0)){
					System.out.println("第" + (n+1) + "个研究咨询报告的isMark为：" + research.getElementsByTagName("isMark").item(0).getFirstChild().getNodeValue());
				}
				if(null != research.getElementsByTagName("commitUnit").item(0)){
					System.out.println("第" + (n+1) + "个研究咨询报告的commitUnit为：" + research.getElementsByTagName("commitUnit").item(0).getFirstChild().getNodeValue());
				}
				if(null != research.getElementsByTagName("commitDate").item(0)){
					System.out.println("第" + (n+1) + "个研究咨询报告的commitDate为：" + research.getElementsByTagName("commitDate").item(0).getFirstChild().getNodeValue());
				}
				if(null != research.getElementsByTagName("isAccept").item(0)){
					System.out.println("第" + (n+1) + "个研究咨询报告的isAccept为：" + research.getElementsByTagName("isAccept").item(0).getFirstChild().getNodeValue());
				}
				if(null != research.getElementsByTagName("acceptObj").item(0)){
					System.out.println("第" + (n+1) + "个研究咨询报告的acceptObj为：" + research.getElementsByTagName("acceptObj").item(0).getFirstChild().getNodeValue());
				}
				if(null != research.getElementsByTagName("note").item(0)){
					System.out.println("第" + (n+1) + "个研究咨询报告的note为：" + research.getElementsByTagName("note").item(0).getFirstChild().getNodeValue());
				}
			}
		}
		NodeList videoProducts = result.getElementsByTagName("VideoProduct");
		if(null != videoProducts.item(0)){//存在电子出版物信息
			for(int p=0; p<videoProducts.getLength(); p++){
				Element videoProduct = (Element)videoProducts.item(p);
				if(null != videoProduct.getElementsByTagName("id").item(0)){
					System.out.println("第" + (p+1) + "个电子出版物的id为：" + videoProduct.getElementsByTagName("id").item(0).getFirstChild().getNodeValue());
				}
				if(null != videoProduct.getElementsByTagName("id").item(0)){
					System.out.println("第" + (p+1) + "个电子出版物的name为：" + videoProduct.getElementsByTagName("name").item(0).getFirstChild().getNodeValue());
				}
				if(null != videoProduct.getElementsByTagName("firstAuthor").item(0)){
					System.out.println("第" + (p+1) + "个电子出版物的firstAuthor为：" + videoProduct.getElementsByTagName("firstAuthor").item(0).getFirstChild().getNodeValue());
				}
				if(null != videoProduct.getElementsByTagName("otherAuthor").item(0)){
					System.out.println("第" + (p+1) + "个电子出版物的otherAuthor为：" + videoProduct.getElementsByTagName("otherAuthor").item(0).getFirstChild().getNodeValue());
				}
				if(null != videoProduct.getElementsByTagName("subject").item(0)){
					System.out.println("第" + (p+1) + "个电子出版物的subject为：" + videoProduct.getElementsByTagName("subject").item(0).getFirstChild().getNodeValue());
				}
				if(null != videoProduct.getElementsByTagName("isMark").item(0)){
					System.out.println("第" + (p+1) + "个电子出版物的isMark为：" + videoProduct.getElementsByTagName("isMark").item(0).getFirstChild().getNodeValue());
				}
				if(null != videoProduct.getElementsByTagName("press").item(0)){
					System.out.println("第" + (p+1) + "个电子出版物的press为：" + videoProduct.getElementsByTagName("press").item(0).getFirstChild().getNodeValue());
				}
				if(null != videoProduct.getElementsByTagName("pressTime").item(0)){
					System.out.println("第" + (p+1) + "个电子出版物的pressTime为：" + videoProduct.getElementsByTagName("pressTime").item(0).getFirstChild().getNodeValue());
				}
				if(null != videoProduct.getElementsByTagName("pressAddress").item(0)){
					System.out.println("第" + (p+1) + "个电子出版物的pressAddress为：" + videoProduct.getElementsByTagName("pressAddress").item(0).getFirstChild().getNodeValue());
				}
				if(null != videoProduct.getElementsByTagName("ISBN").item(0)){
					System.out.println("第" + (p+1) + "个电子出版物的ISBN为：" + videoProduct.getElementsByTagName("ISBN").item(0).getFirstChild().getNodeValue());
				}
				if(null != videoProduct.getElementsByTagName("note").item(0)){
					System.out.println("第" + (p+1) + "个电子出版物的note为：" + videoProduct.getElementsByTagName("note").item(0).getFirstChild().getNodeValue());
				}
			}
		}
	}

}
