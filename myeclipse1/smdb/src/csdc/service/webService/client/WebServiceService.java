package csdc.service.webService.client;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
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
import csdc.dao.IHibernateBaseDao;
import csdc.service.imp.BaseService;
import csdc.tool.ApplicationContainer;
import csdc.tool.FileTool;
import csdc.tool.SpringBean;
import csdc.tool.crawler.AttachmentUpdater;
import csdc.tool.crawler.Crawler;
import csdc.tool.crawler.CrawlerExecutor;
import csdc.tool.execution.importer.tool.Tool;
import csdc.tool.webService.ApplyProjectResolver;
import csdc.tool.webService.ClientHandler;
import csdc.tool.webService.FinishRecordResolver;
import csdc.tool.webService.MidCheckRecordsResolver;
import csdc.tool.webService.ModifyRecordResolver;
import csdc.tool.webService.sinoss.SinossServiceService;

public class WebServiceService extends BaseService implements IWebServiceService {

	@Autowired
	private CrawlerExecutor crawlerExecutor;
	
	@Autowired
	protected IHibernateBaseDao dao;
	
	private Integer obtainCount = 0;//正在同步的文件数
	private Integer fileCount = 0;//正在入库的文件数
	private Integer recordCount = 0;//当前文件正在入库的记录数
	private Integer flag = 0;//循环停止的标记
	private String attachmentType;//下载项目类型
	private boolean isLinked = true;//服务器是否可连接的标志
	
	public void obtain(String projectType, int year, int count, String methodName) 
			throws IOException, ParserConfigurationException, SAXException {
		flag = 0;
		obtainCount = 0;
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
    	
    	if("getFinishRecord".equals(methodName)){
    		try {
//    			result = service.getSinossServicePort().getFinishRecord(projectType);				
			} catch (Exception e) {
				setLinked(false);
			}
    		j++;
    		fileOutput(result, methodName, projectType, year, j);
    		setObtainCount(j);//记录当前正在同步的文件数
    	}else if("getMidCheckRecords".equals(methodName)){
    		try {
    			result = service.getSinossServicePort().getMidCheckProjectMsg(projectType, year);//获取中检批准号				
			} catch (Exception e) {
				setLinked(false);
			}
    		InputStream input = new ByteArrayInputStream(result.getBytes("UTF-8"));
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc=builder.parse(input);//读取输入流
			List projectCodeList = new ArrayList();
			NodeList records = doc.getElementsByTagName("result");
			for (int i=0;i<records.getLength();i++){
				Element element =(Element) records.item(i);
				if(null != element.getElementsByTagName("code").item(0)){
					projectCodeList.add(element.getElementsByTagName("code").item(0).getFirstChild().getNodeValue());					
				}
			}
			final int DIVIDE = 500;//被除数
			int a = projectCodeList.size()/DIVIDE;
			for(int b=0; b<a; b++){//前整数次每固定条数生成一个文件
				StringBuffer midCheck = new StringBuffer();
				midCheck.append("<response><records>");
				for(int c=b*DIVIDE; c<(b+1)*DIVIDE; c++){
					String singleResult = service.getSinossServicePort().getMidCheckRecords(projectType, year, projectCodeList.get(c).toString());
					if(!singleResult.startsWith("<")){
						System.out.println(c + "、" + projectCodeList.get(c));
						continue;
					}
					String[] e = singleResult.split("<result>");
					StringBuffer sb = new StringBuffer();
					sb.append("<result>");
					sb.append(e[1].substring(0, e[1].length()-21));
					midCheck.append(sb);
				}
				midCheck.append("</records></response>");
				j++;
				fileOutput(midCheck.toString(), methodName, projectType, year, b+1);
				setObtainCount(j);//记录当前正在同步的文件数
			}
			StringBuffer lastCheck = new StringBuffer();
			lastCheck.append("<response><records>");
			for(int d=a*DIVIDE; d<projectCodeList.size(); d++){//最后一次，剩下的条数生成一个文件
				String singleResult = service.getSinossServicePort().getMidCheckRecords(projectType, year, projectCodeList.get(d).toString());
				if(!singleResult.startsWith("<")){
					System.out.println(d + "、" + projectCodeList.get(d));
					continue;
				}
				String[] e = singleResult.split("<result>");
				StringBuffer sb = new StringBuffer();
				sb.append("<result>");
				sb.append(e[1].substring(0, e[1].length()-21));
				lastCheck.append(sb);				
			}
			lastCheck.append("</records></response>");
			if(lastCheck.length()>40){
				j++;
				fileOutput(lastCheck.toString(), methodName, projectType, year, a+1);					
				setObtainCount(j);//记录当前正在同步的文件数
			}
    	}else if("getApplyProject".equals(methodName) || "getModifyRecord".equals(methodName)){
    		NodeList records = null;
			do {
				StringBuffer sinossIds = new StringBuffer();
				//得到结果
				try {
					if("getApplyProject".equals(methodName)){
						result =service.getSinossServicePort().getApplyProject(projectType, year, count);
					}else result =service.getSinossServicePort().getModifyRecord(projectType, count);
				} catch (Exception e) {
					setLinked(false);
				}
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
						if("getApplyProject".equals(methodName)){
							service.getSinossServicePort().updateMsg("updateApplyProject", sinossId);
						}else service.getSinossServicePort().updateMsg("updateModifyRecord", sinossId);
					}
					j++;
					fileOutput(result, methodName, projectType, year, j);
					setObtainCount(j);//记录当前正在同步的文件数
				}
			} while (result.startsWith("<"));
    	}else result = "";
    	setFlag(1);//循环停止的标志
	}

	public void importer(String methodName, String projectType, int year, int i) 
			throws SAXException, IOException, DOMException, ParseException, 
			XPathExpressionException, ParserConfigurationException {
		flag = 0;
		fileCount = 0;
		recordCount = 0;
		String project = "";
		if(methodName.equals("getApplyProject")){
			project = "app";
		}else if(methodName.equals("getModifyRecord")){
			project = "var";
		}else if(methodName.equals("getMidCheckRecords")){
			project = "mid";
		}else project = "end";
		String filePath = "upload/sinoss/source/" + project + "/" + year +"/";
		for(int j=1; j<i+1; j++){
			try {
				String file = ApplicationContainer.sc.getRealPath(filePath + methodName + "_" + projectType + "_" + j + ".xml");
				file = file.replace('\\', '/');
				FileInputStream fInputStream = new FileInputStream(file);
				InputStreamReader inputStreamReader = new InputStreamReader(fInputStream, "utf-8");
				BufferedReader in = new BufferedReader(inputStreamReader);
				StringBuffer sBuffer = new StringBuffer();
				String line = in.readLine();
				while (line != null) {
					sBuffer.append(line);
					line = in.readLine();
				}
				fInputStream.close();//必须释放资源，才能后续进行文件更名操作
				inputStreamReader.close();//必须释放资源，才能后续进行文件更名操作
				in.close();//必须释放资源，才能后续进行文件更名操作
				String[] a = sBuffer.toString().split("</result>");
				Tool tool = new Tool();//徐涵写的转成规律日期格式的方法,只有年月日
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//转成规律日期格式的方法
				for(int k=0; k<a.length-1; k++){
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
					if("getApplyProject".equals(methodName)){//项目申请信息接口
						ApplyProjectResolver apply = new ApplyProjectResolver(doc, sdf, tool, dao);
						apply.parse();
					}else if("getMidCheckRecords".equals(methodName)){//中检数据及相关中期成果信息接口
						MidCheckRecordsResolver midCheck = new MidCheckRecordsResolver(doc, sdf, tool, dao);
						midCheck.parse();
					}else if("getModifyRecord".equals(methodName)){//变更数据
						ModifyRecordResolver modify = new ModifyRecordResolver(doc, sdf, tool, dao);
						modify.parse();
					}else if("getFinishRecord".equals(methodName)){//结项数据
						FinishRecordResolver finish = new FinishRecordResolver(doc, sdf, tool, dao);
						finish.parse();
					}				
					setRecordCount(k+1);//记录当前已经入库的条数
//					System.out.println("当前文档的第" + (k+1) + "条数据解析入库完毕");
				}
				setFileCount(j);//记录当前已经完成入库的文件数
				System.out.println("当前已经解析到第" + j + "个文档");
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		fileReanme(filePath, i, projectType, methodName);
		setFlag(1);//循环结束的标志
	}
	
	public void download(String projectType, String methodName) {
		AttachmentUpdater updater = (AttachmentUpdater) SpringBean.getBean("attachmentUpdater");
		updater.setMethodType(methodName);
		if(methodName.equals("getApplyProject")){
			updater.setAttachmentType(projectType + "Apply");
			setAttachmentType(projectType + "Apply");
		}else if(methodName.equals("getMidCheckRecords")){
			updater.setAttachmentType(projectType + "Mid");
			setAttachmentType(projectType + "Mid");
		}else if(methodName.equals("getFinishRecord")){
			updater.setAttachmentType(projectType + "End");
			setAttachmentType(projectType + "End");
		}
		crawlerExecutor.execute(updater);
	}
	
	//入库完成后重命名xml文件为[methodName + "_" + projectType + "_" + j + "_imported.xml"]
	public void fileReanme(String filePath, int i, String projectType, String methodName){
		int m = 0;
		int n = 1;
		String existedFile = ApplicationContainer.sc.getRealPath(filePath + methodName + "_" + projectType + "_" + n + "_imported.xml");
		existedFile = existedFile.replace('\\', '/');
		while(FileTool.isExsits(existedFile)){
			n++;
			existedFile = ApplicationContainer.sc.getRealPath(filePath + methodName + "_" + projectType + "_" + n + "_imported.xml");
			existedFile = existedFile.replace('\\', '/');
		}
		m = n-1;
		for(int j=1; j<i+1; j++){
			String oldFile = ApplicationContainer.sc.getRealPath(filePath + methodName + "_" + projectType + "_" + j + ".xml");
			String newFile = ApplicationContainer.sc.getRealPath(filePath + methodName + "_" + projectType + "_" + (m+j) + "_imported.xml");
			if(FileTool.isExsits(oldFile)){
				File file = new File(oldFile.replace('\\', '/'));
				file.renameTo(new File(newFile.replace('\\', '/')));
			}
		}
	}
	
	public int isFileExist(String projectType, String methodName, int year){
		String project = "";
		if(methodName.equals("getApplyProject")){
			project = "app";
		}else if(methodName.equals("getModifyRecord")){
			project = "var";
		}else if(methodName.equals("getMidCheckRecords")){
			project = "mid";
		}else project = "end";
		String filePath = "upload/sinoss/source/" + project + "/" + year +"/";
		int i = 0;
		int j = 1;
		String file = ApplicationContainer.sc.getRealPath(filePath + methodName + "_" + projectType + "_" + j + ".xml");
		file = file.replace('\\', '/');
		while(FileTool.isExsits(file)){
			j++;
			file = ApplicationContainer.sc.getRealPath(filePath + methodName + "_" + projectType + "_" + j + ".xml");
			file = file.replace('\\', '/');
		}
		i = j-1;
		return i;
	}

	public Map getCurrentTask(){
		Map jsonMap = new HashMap();
		jsonMap.put("isLinked", isLinked);
		jsonMap.put("obtainCount", getObtainCount());
		jsonMap.put("fileCount", getFileCount());
		jsonMap.put("recordCount", getRecordCount());
		jsonMap.put("flag", getFlag());
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
	
	public Map xmlValidate(String methodName, String projectType, int year, Integer n) 
			throws ParserConfigurationException, SAXException, IOException{
		Map jsonMap = new HashMap();
		List<String> resultList = new ArrayList<String>();//用于存放主表数据出现的所有节点
		List<String> checkLogList = new ArrayList<String>();//用于存放子表数据[审核记录]出现的所有节点
		List<String> membersList = new ArrayList<String>();//用于存放子表数据[成员信息]出现的所有节点
		List<String> modifyContentList = new ArrayList<String>();//用于存放子表数据[变更内容]出现的所有节点
		List<String> paperList = new ArrayList<String>();//用于存放子表数据[论文信息]出现的所有节点
		List<String> bookList = new ArrayList<String>();//用于存放子表数据[著作信息]出现的所有节点
		List<String> patentList = new ArrayList<String>();//用于存放子表数据[专利信息]出现的所有节点
		List<String> researchList = new ArrayList<String>();//用于存放子表数据[研究咨询报告信息]出现的所有节点
		List<String> videoProductList = new ArrayList<String>();//用于存放子表数据[电子出版物信息]出现的所有节点
		String project = "";
		if(methodName.equals("getApplyProject")){
			project = "app";
		}else if(methodName.equals("getModifyRecord")){
			project = "var";
		}else if(methodName.equals("getMidCheckRecords")){
			project = "mid";
		}else project = "end";
		String filePath = "upload/sinoss/source/" + project + "/" + year +"/";
		if(methodName.equals("getFinishRecord")){
			for(int m=1; m<n+1; m++){
				String filename = ApplicationContainer.sc.getRealPath(filePath + methodName + "_" + projectType + "_" + m + ".xml");
				filename = filename.replace('\\', '/');
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
				}
			}
		}else if(methodName.equals("getModifyRecord")){
			for(int m=1; m<n+1; m++){
				String filename = ApplicationContainer.sc.getRealPath(filePath + methodName + "_" + projectType + "_" + m + ".xml");
				filename = filename.replace('\\', '/');
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
			}
		}else if(methodName.equals("getApplyProject")){
			for(int i=1; i<n+1; i++){
				String filename = ApplicationContainer.sc.getRealPath(filePath + methodName + "_" + projectType + "_" + i + ".xml");
				filename = filename.replace('\\', '/');
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
		}else if(methodName.equals("getMidCheckRecords")){
			for(int i=1; i<n+1; i++){
				String filename = ApplicationContainer.sc.getRealPath(filePath + methodName + "_" + projectType + "_" + i + ".xml");
				filename = filename.replace('\\', '/');
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
					NodeList paperNodes = result.getElementsByTagName("Paper");
					for(int j=0; j<paperNodes.getLength(); j++){
						Element papers = (Element)paperNodes.item(j);
						NodeList paperNode = papers.getChildNodes();
						for(int m=0; m<paperNode.getLength(); m++){
							String childNode = paperNode.item(m).getNodeName();
							if(!paperList.contains(childNode)){
								paperList.add(childNode);
							}
						}
					}
					NodeList bookNodes = result.getElementsByTagName("Book");
					for(int j=0; j<bookNodes.getLength(); j++){
						Element books = (Element)bookNodes.item(j);
						NodeList bookNode = books.getChildNodes();
						for(int m=0; m<bookNode.getLength(); m++){
							String childNode = bookNode.item(m).getNodeName();
							if(!bookList.contains(childNode)){
								bookList.add(childNode);
							}
						}
					}
					NodeList patentNodes = result.getElementsByTagName("Patent");
					for(int j=0; j<patentNodes.getLength(); j++){
						Element patents = (Element)patentNodes.item(j);
						NodeList patentNode = patents.getChildNodes();
						for(int m=0; m<patentNode.getLength(); m++){
							String childNode = patentNode.item(m).getNodeName();
							if(!patentList.contains(childNode)){
								patentList.add(childNode);
							}
						}
					}
					NodeList researchNodes = result.getElementsByTagName("Researche");
					for(int j=0; j<researchNodes.getLength(); j++){
						Element researches = (Element)researchNodes.item(j);
						NodeList researchNode = researches.getChildNodes();
						for(int m=0; m<researchNode.getLength(); m++){
							String childNode = researchNode.item(m).getNodeName();
							if(!researchList.contains(childNode)){
								researchList.add(childNode);
							}
						}
					}
					NodeList videoProductNodes = result.getElementsByTagName("VideoProduct");
					for(int j=0; j<videoProductNodes.getLength(); j++){
						Element videoProducts = (Element)videoProductNodes.item(j);
						NodeList videoProductNode = videoProducts.getChildNodes();
						for(int m=0; m<videoProductNode.getLength(); m++){
							String childNode = videoProductNode.item(m).getNodeName();
							if(!videoProductList.contains(childNode)){
								videoProductList.add(childNode);
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
		jsonMap.put("paperList", paperList);
		jsonMap.put("bookList", bookList);
		jsonMap.put("patentList", patentList);
		jsonMap.put("researchList", researchList);
		jsonMap.put("videoProductList", videoProductList);
		return jsonMap;
	}
	
	public void fileOutput(String file, String methodName, String projectType, int year, int i) throws IOException{
		String project = "";
		if(methodName.equals("getApplyProject")){
			project = "app";
		}else if(methodName.equals("getModifyRecord")){
			project = "var";
		}else if(methodName.equals("getMidCheckRecords")){
			project = "mid";
		}else project = "end";
		String filename = ApplicationContainer.sc.getRealPath("upload/sinoss/source/" +project+ "/" +year+"/" + methodName + "_" + projectType + "_" + i + ".xml");
		filename = filename.replace('\\', '/');
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

	public boolean isLinked() {
		return isLinked;
	}

	public void setLinked(boolean isLinked) {
		this.isLinked = isLinked;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}
}
