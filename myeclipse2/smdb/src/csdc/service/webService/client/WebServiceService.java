package csdc.service.webService.client;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.ws.handler.Handler;
import javax.xml.ws.handler.HandlerResolver;
import javax.xml.ws.handler.PortInfo;
import javax.xml.xpath.XPathExpressionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import csdc.bean.CrawlTask;
import csdc.dao.HibernateBaseDao;
import csdc.service.imp.BaseService;
import csdc.tool.ApplicationContainer;
import csdc.tool.SpringBean;
import csdc.tool.crawler.AttachmentUpdater;
import csdc.tool.crawler.Crawler;
import csdc.tool.crawler.CrawlerExecutor;
import csdc.tool.execution.importer.tool.Tool;
import csdc.tool.webService.ApplyProjectResolver;
import csdc.tool.webService.ClientHandler;
import csdc.tool.webService.MidCheckRecordsResolver;
import csdc.tool.webService.ModifyRecordResolver;
import csdc.tool.webService.sinoss.SinossServiceService;

public class WebServiceService extends BaseService implements IWebServiceService {

	@Autowired
	private CrawlerExecutor crawlerExecutor;
	
	@Autowired
	protected HibernateBaseDao dao;
	
	private Integer obtainCount;//正在同步的文件数
	private Integer fileCount;//正在入库的文件数
	private Integer recordCount;//当前文件正在入库的记录数
	private String attachmentType;//下载项目类型
	
	public void download(String projectType, String methodName) {
		AttachmentUpdater updater = (AttachmentUpdater) SpringBean.getBean("attachmentUpdater");
		updater.setAttachmentType(projectType);
		setAttachmentType(projectType);
		updater.setMethodType(methodName);
		crawlerExecutor.execute(updater);
	}

	public void importer(String methodName, String projectType, int i) 
			throws SAXException, IOException, DOMException, ParseException, 
			XPathExpressionException, ParserConfigurationException {
		if(methodName.equals("getModifyRecord") || methodName.equals("getMidCheckRecords")){//变更和中检数据一次解析入库完全
			try {
				setFileCount(i);//记录当前正在入库的文件数
				String filename = ApplicationContainer.sc.getRealPath("/system/interfaces/sinossResource/" + methodName + "_" + projectType + ".xml");
				FileInputStream fInputStream = new FileInputStream(filename);
				InputStreamReader inputStreamReader = new InputStreamReader(fInputStream, "utf-8");
				BufferedReader in = new BufferedReader(inputStreamReader);
				StringBuffer sBuffer = new StringBuffer();
				String line = in.readLine();
				while (line != null) {
					sBuffer.append(line);
					line = in.readLine();
				}
				String[] a = sBuffer.toString().split("</result>");
				Tool tool = new Tool();//徐涵写的转成规律日期格式的方法,只有年月日
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//转成规律日期格式的方法
				for(int k=0; k<a.length-1; k++){
					setRecordCount(k+1);//记录当前正在入库的条数
					String result;
					if(k==0){
						String[] b = a[0].split("<result>");
						result = "<result>" + b[1] + "</result>";
					}
					else result = a[k]+"</result>";
					InputStream input = new ByteArrayInputStream(result.getBytes("UTF-8"));
					DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
					DocumentBuilder builder = factory.newDocumentBuilder();
					Document doc=builder.parse(input);//读取输入流
					if("getModifyRecord".equals(methodName)){//项目变更申请信息接口
						ModifyRecordResolver modify = new ModifyRecordResolver(doc, sdf, tool, dao);
						modify.parse();
					}else if("getMidCheckRecords".equals(methodName)){//中检数据及相关中期成果信息接口
						MidCheckRecordsResolver midCheck = new MidCheckRecordsResolver(doc, sdf, tool, dao);
						midCheck.prase();
					}
					System.out.println("第" + (k+1) + "条数据解析入库完毕");
				}
			}catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		else{
			try {
				setFileCount(i);//记录当前正在入库的文件数
				String file = ApplicationContainer.sc.getRealPath("/system/interfaces/sinossResource/" + methodName + "_" + projectType + "_" + i + ".xml");
				FileInputStream fInputStream = new FileInputStream(file);
				InputStreamReader inputStreamReader = new InputStreamReader(fInputStream, "utf-8");
				BufferedReader in = new BufferedReader(inputStreamReader);
				StringBuffer sBuffer = new StringBuffer();
				String line = in.readLine();
				while (line != null) {
					sBuffer.append(line);
					line = in.readLine();
				}
				String[] a = sBuffer.toString().split("</result>");
				Tool tool = new Tool();//徐涵写的转成规律日期格式的方法,只有年月日
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//转成规律日期格式的方法
				for(int k=0; k<a.length-1; k++){
					setRecordCount(k+1);//记录当前正在入库的条数
					String result;
					if(k==0){
						String[] b = a[0].split("<result>");
						result = "<result>" + b[1] + "</result>";
					}
					else result = a[k]+"</result>";
					InputStream input = new ByteArrayInputStream(result.getBytes("UTF-8"));
					DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
					DocumentBuilder builder = factory.newDocumentBuilder();
					Document doc=builder.parse(input);//读取输入流
					ApplyProjectResolver apply = new ApplyProjectResolver(doc, sdf, tool, dao);
					apply.prase();
					System.out.println("当前文档的第" + (k+1) + "条数据解析入库完毕");
				}
				System.out.println("当前已经解析到第" + i + "个文档");
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		setFileCount(1000);//循环结束的标志
	}

	public Integer obtain(String projectType, Integer year, Integer count, String methodName, String projectCode) 
			throws IOException {
		int j = 0;
		String result = null;
    	SinossServiceService service=new SinossServiceService();

        //向SOAP添加表头
    	service.setHandlerResolver(new HandlerResolver(){
    		public List<Handler> getHandlerChain(PortInfo portInfo) {
    			List<Handler> handlerList = new ArrayList<Handler>();
    			//添加认证信息
    			handlerList.add(new ClientHandler());
    			return handlerList;
            }
        });
    	
    	//变更和中检数据一次取完；申报数据分批取
    	if("getModifyRecord".equals(methodName)){
    		j++;
    		setObtainCount(j);//记录当前正在同步的文件数
    		result = service.getSinossServicePort().getModifyRecord(projectType);
    		fileOutput(result, methodName, projectType, 0);
    	}else if("getMidCheckRecords".equals(methodName)){
    		j++;
    		setObtainCount(j);//记录当前正在同步的文件数
    		result = service.getSinossServicePort().getMidCheckRecords(projectType, year, projectCode);
    		fileOutput(result, methodName, projectType, 0);
    	}else if("getApplyProject".equals(methodName)){
    		NodeList records = null;
    		try {
    			do {
    				j++;
    				setObtainCount(j);//记录当前正在同步的文件数
    				String feedBack = "";
    				StringBuffer sinossIds = new StringBuffer();
    				//得到结果
    				result =service.getSinossServicePort().getApplyProject(projectType, year, count);
    				
    				if(result.startsWith("<")){
    					InputStream input = new ByteArrayInputStream(result.getBytes("UTF-8"));
    					DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    					DocumentBuilder builder = factory.newDocumentBuilder();
    					Document doc=builder.parse(input);//读取输入流
    					records =doc.getElementsByTagName("result");
    					for (int i=0;i<records.getLength();i++){
    						Element element =(Element) records.item(i);
    						if(null != element.getElementsByTagName("id").item(0)){
    							sinossIds.append(element.getElementsByTagName("id").item(0).getFirstChild().getNodeValue() + ",");					
    						}
    					}
    					if (sinossIds.length()>0) {
    						String sinossId = sinossIds.toString().substring(0, sinossIds.toString().length()-1);				
    						feedBack = service.getSinossServicePort().updateApplyProject(sinossId);
    					}
    					fileOutput(result, methodName, projectType, j);
    					System.out.println(feedBack + "\n当前已同步的总条数为" + j*count);
    				}
    			} while (result.startsWith("<"));
    		} catch (Exception e) {
    			e.printStackTrace();
    			System.out.println("异常了：" + e.getMessage());
    		} 		
    	}else result = "";
    	setObtainCount(1000);//循环停止的标志
    	return j;
	}

	public Map getCurrentTask(){
		Map jsonMap = new HashMap();
		jsonMap.put("obtainCount", getObtainCount());
		jsonMap.put("fileCount", getFileCount());
		jsonMap.put("recordCount", getRecordCount());
		attachmentType = getAttachmentType();
		List<CrawlTask> tasks = new ArrayList<CrawlTask>();
		for (Crawler crawler : crawlerExecutor.getRunningCrawlers()) {
			if (crawler.getCrawlTask().getTaskType().equals(attachmentType)) {
				tasks.add(crawler.getCrawlTask());
			}
		}
		Map map = new HashMap();
		map.put("attachmentType", attachmentType);
		long finishedAttachment = dao.count("select task from CrawlTask task where task.taskType =:attachmentType and task.finishTime is not null", map);
		long totalAttachment = dao.count("select task from CrawlTask task where task.taskType =:attachmentType", map);
		jsonMap.put("finishedAttachment", Integer.parseInt(finishedAttachment + ""));
		jsonMap.put("totalAttachment", Integer.parseInt(totalAttachment + ""));
		jsonMap.put("currentAttachment", tasks.size());
		return jsonMap;
	}
	
	public Map xmlValidate(String methodName, String projectType, Integer n) 
			throws ParserConfigurationException, SAXException, IOException{
		Map jsonMap = new HashMap();
		List<String> resultList = new ArrayList<String>();//用于存放主表数据出现的所有节点
		List<String> checkLogList = new ArrayList<String>();//用于存放子表数据出现的所有节点
		List<String> membersList = new ArrayList<String>();//用于存放子表数据出现的所有节点
		List<String> modifyContentList = new ArrayList<String>();//用于存放子表数据出现的所有节点
		if(methodName.equals("getModifyRecord") || methodName.equals("getMidCheckRecords")){
			String filename = ApplicationContainer.sc.getRealPath("/system/interfaces/sinossResource/" + methodName + "_" + projectType + ".xml");
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc=builder.parse(filename);//读取xml文件
			NodeList records =doc.getElementsByTagName("result");
			for(int k=0; k<records.getLength(); k++){
				Element result=(Element) records.item(k);
				NodeList resultNode = result.getChildNodes();
				for(int i=0; i<resultNode.getLength(); i++){
					String childNode = resultNode.item(i).getNodeName();
					if(!resultList.contains(childNode)){
						resultList.add(childNode);
					}
				}
				NodeList CheckLogNodes = result.getElementsByTagName("CheckLog");
				for(int i=0; i<CheckLogNodes.getLength(); i++){
					Element checkLog = (Element)CheckLogNodes.item(i);
					NodeList checkLogNode = checkLog.getChildNodes();
					for(int j=0; j<checkLogNode.getLength(); j++){
						String childNode = checkLogNode.item(j).getNodeName();
						if(!checkLogList.contains(childNode)){
							checkLogList.add(childNode);
						}
					}
				}
				NodeList memberNodes = result.getElementsByTagName("ProjectModifyMember");
				for(int i=0; i<memberNodes.getLength(); i++){
					Element members = (Element)memberNodes.item(i);
					NodeList memberNode = members.getChildNodes();
					for(int j=0; j<memberNode.getLength(); j++){
						String childNode = memberNode.item(j).getNodeName();
						if(!membersList.contains(childNode)){
							membersList.add(childNode);
						}
					}
				}
				NodeList contentNodes = result.getElementsByTagName("ModifyObj");
				for(int i=0; i<contentNodes.getLength(); i++){
					Element content = (Element)contentNodes.item(i);
					NodeList contentNode = content.getChildNodes();
					for(int j=0; j<contentNode.getLength(); j++){
						String childNode = contentNode.item(j).getNodeName();
						if(!modifyContentList.contains(childNode)){
							modifyContentList.add(childNode);
						}
					}
				}
			}
		}else if(methodName.equals("getApplyProject")){
			for(int i=1; i<n+1; i++){
				String filename = ApplicationContainer.sc.getRealPath("/system/interfaces/sinossResource/" + methodName + "_" + projectType + "_" + i + ".xml");
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				DocumentBuilder builder = factory.newDocumentBuilder();
				Document doc=builder.parse(filename);//读取xml文件
				NodeList records =doc.getElementsByTagName("result");
				for(int k=0; k<records.getLength(); k++){
					Element result=(Element) records.item(k);
					NodeList resultNode = result.getChildNodes();
					for(int j=0; j<resultNode.getLength(); j++){
						String childNode = resultNode.item(j).getNodeName();
						if(!resultList.contains(childNode)){
							resultList.add(childNode);
						}
					}
					NodeList CheckLogNodes = result.getElementsByTagName("CheckLog");
					for(int j=0; j<CheckLogNodes.getLength(); j++){
						Element checkLog = (Element)CheckLogNodes.item(j);
						NodeList checkLogNode = checkLog.getChildNodes();
						for(int m=0; m<checkLogNode.getLength(); m++){
							String childNode = checkLogNode.item(m).getNodeName();
							if(!checkLogList.contains(childNode)){
								checkLogList.add(childNode);
							}
						}
					}
					NodeList memberNodes = result.getElementsByTagName("ProjectApplyMember");
					for(int j=0; j<memberNodes.getLength(); j++){
						Element members = (Element)memberNodes.item(j);
						NodeList memberNode = members.getChildNodes();
						for(int p=0; p<memberNode.getLength(); p++){
							String childNode = memberNode.item(p).getNodeName();
							if(!membersList.contains(childNode)){
								membersList.add(childNode);
							}
						}
					}
				}
			}
		}else return null;
		jsonMap.put("resultList", resultList);
		jsonMap.put("checkLogList", checkLogList);
		jsonMap.put("membersList", membersList);
		jsonMap.put("modifyContentList", modifyContentList);
		return jsonMap;
	}
	
	public void fileOutput(String file, String methodName, String projectType, int i) throws IOException{
		String filename;
		if(i>0){			
			filename = ApplicationContainer.sc.getRealPath("/system/interfaces/sinossResource/" + methodName + "_" + projectType + "_" + i + ".xml");
		}else filename = ApplicationContainer.sc.getRealPath("/system/interfaces/sinossResource/" + methodName + "_" + projectType + ".xml");
		FileOutputStream output = new FileOutputStream(filename);
		Writer out = new OutputStreamWriter(output, "utf-8");
		out.write(file);
		out.close();
		output.close();
	}

	public Integer getObtainCount() {
		return obtainCount;
	}

	public void setObtainCount(Integer obtainCount) {
		this.obtainCount = obtainCount;
	}

	public Integer getFileCount() {
		return fileCount;
	}

	public void setFileCount(Integer fileCount) {
		this.fileCount = fileCount;
	}

	public Integer getRecordCount() {
		return recordCount;
	}

	public void setRecordCount(Integer recordCount) {
		this.recordCount = recordCount;
	}

	public String getAttachmentType() {
		return attachmentType;
	}

	public void setAttachmentType(String attachmentType) {
		this.attachmentType = attachmentType;
	}
}
