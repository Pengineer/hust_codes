package csdc.action.system.interfaces;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.DOMException;
import org.xml.sax.SAXException;

import csdc.action.BaseAction;
import csdc.bean.Task;
import csdc.service.IInterfaceService;
import csdc.service.IProjectService;
import csdc.service.taskConfig.ITaskService;
import csdc.service.webService.client.IWebServiceService;
import csdc.tool.ApplicationContainer;
import csdc.tool.info.GlobalInfo;

public class SinossClientAction extends BaseAction implements ITaskService{

	private static final long serialVersionUID = 1L;

	@Autowired
	private IWebServiceService webServiceService;
	
	@Autowired
	private IInterfaceService interfaceService;
	
	private IProjectService projectService;
	
	//社科网接口参数
	private String projectType;//项目类型
	private String methodName;//方法名
	private long costTime;//任务耗时[单位：ms]
	private static final int year = Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date()));
	//xml下载参数
	private String projectStatus;//项目情形[申请/中检/变更/结项]
	private int sourceYear;//xml文件的年度
	private String fileName;//文件名
	//变更数据的已知字段
	private final String[] modifyResult = {"id", "projectId", "projectModifyType", "applyDate", "modifyReason", 
			"checkStatus", "checkDate", "checker", "modifyContent", "members", "checkLogs", "code", "typeCode", 
			"name", "smdbProjectId"};
	private final String[] modifyCheckLog = {"id", "checkStatus", "checkDate", "checker", "checkInfo"};
	private final String[] modifyGenerMember = {"id", "name", "unitName", "title", "birthDay", 
			"specialty", "divide", "orders", "resAdvantage"};
	private final String[] modifyGenerContent = {"id", "modifyFieIdMean", "beforeValue", "afterValue"};
	private final String[] modifyBaseMember = {"id", "name", "unitName", "title", "divide", "orders", "resAdvantage"};
	private final String[] modifyBaseContent = {"id", "modifyFieIdMean", "beforeValue", "afterValue", "idNumber"};
	
	//申请数据的已知字段
	private final String[] applyCheckLog = {"id", "checkStatus", "checkDate", "checker", "checkInfo"};
	private final String[] applyGenerResult = {"id", "projectName", "typeCode", "projectType", "applyDate", 
			"planFinishDate", "subject", "researchDirection", "researchType", "lastProductMode", 
			"applyTotalFee", "otherFeeSource", "applyer", "gender", "birthday", "IDCardNo", "title", 
			"division", "duty", "eduLevel", "eduDegree", "language", "email", "address", "postalCode", 
			"tel", "checkStatus", "applyDocName", "members", "checkLogs", "unitCode", "unitName", 
			"batchId", "year", "batchName", "beginDate", "endDate", "telOffice", "checkDate", "checker"};
	private final String[] applyGenerAndSpecialMember = {"id", "memberCode", "memberName", "memberUnit", "memberTitle", 
			"memberBirthday", "memberSpecialty", "memberDivide", "orders"};
	private final String[] applySpecialResult = {"id", "projectName", "typeCode", "projectType", "projectSubType", "applyDate", 
			"planFinishDate", "subject", "researchDirection", "researchType", "lastProductMode", "applyTotalFee", 
			"otherFeeSource", "applyer", "gender", "birthday", "IDCardNo", "title", "division", "duty", "eduLevel", 
			"eduDegree", "language", "email", "address", "postalCode", "tel", "checkStatus", "checkDate", "checker", 
			"applyDocName", "members", "checkLogs", "unitCode", "unitName", "batchId", "year", "batchName", "beginDate", 
			"endDate", "telOffice"};
	private final String[] applyBaseResult = {"id", "projectName", "typeCode", "applyDate", "planFinishDate", 
			"subject", "subject1_1", "subject1_2", "lastProductMode", "words", "applyTotalFee", "applyer", 
			"birthday", "IDCardNo", "title", "division", "duty", "checkStatus", "checkDate", "checker", "applyDocName", 
			"members", "checkLogs", "projectJdId", "projectJdName", "unitCode", "unitName", 
			"batchId", "year", "batchName", "beginDate", "endDate"};
	private final String[] applyBaseMember = {"id", "memberName", "memberUnit", "memberTitle", 
		"memberBirthday", "memberSpecialty", "memberEduDegree", "orders"};
					
	//中检数据的已知字段
	private final String[] midResult = {"id", "storagefileName", "projectId", "applyDate", "checkStatus", "books",
			"papers", "patents", "researches", "videoProducts", "checkLogs", "code", "typeCode", "name", "smdbProjectId",
			"batchYear", "midReportName", "checkDate", "checker", "deferReason", "deferDate"};
	private final String[] midCheckLog = {"id", "checkStatus", "checkDate", "checker", "checkInfo"};
	private final String[] midPaper = {"id", "name", "firstAuthor", "subject", "isMark", "paperType", "releaseTime", "paperBook",
			"paperBookType", "publishScope", "qihao", "pageNumScope", "wordNumber", "isTranslate", "ISSN", "CN", "note",
			"otherAuthor", "juanhao"};
	private final String[] midBook = {"id", "name", "firstAuthor", "subject", "isMark", "press", "pressTime", "pressAddress",
			"bookType", "wordNumber", "isEnglish", "ISBN", "CIP", "otherAuthor", "note"};
	private final String[] midResearch = {"id", "name", "firstAuthor", "isMark", "commitDate", "isAccept", "acceptObj",
			"otherAuthor", "note"};
	private final String[] midGenerPatent = {"id", "name", "firstAuthor", "otherAuthor", "isMark", "patentType",
			"patentScope", "patentNo", "authorizeDate", "patentPerson", "note"};//仅一般项目存在
	private final String[] midGenerVideoProduct = {"id", "name", "firstAuthor", "subject", "isMark", "press", "pressTime",
			"pressAddress", "ISBN", "otherAuthor", "note"};//仅一般项目存在
		
	//结项数据的已知字段
	private final String[] finishResult = {"id", "projectId", "checkStatus", "checkDate", "checker", "checkLogs",
			"code", "typeCode", "name", "smdbProjectId", "finishReportId"};
	private final String[] finishCheckLog = {"id", "checkStatus", "checkDate", "checker", "checkInfo"};
			
	public String toObtain(){
		return SUCCESS;
	}
	//数据获取
	public String obtain() throws IOException, ParseException, ParserConfigurationException, SAXException{
		int count = 500;
		long begin = System.currentTimeMillis();
		try {
			webServiceService.obtain(projectType, year, count, methodName);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			costTime = System.currentTimeMillis() - begin;
		}
		return SUCCESS;
	}
	
	public String toImporter(){
		return SUCCESS;
	}
	
	//数据入库
	@Transactional
	public String importer() throws DOMException, XPathExpressionException, SAXException, 
	IOException, ParseException, ParserConfigurationException{
		long begin = System.currentTimeMillis();
		try {
			int i = (Integer)session.get("i");
			webServiceService.importer(methodName, projectType, year, i);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			costTime = System.currentTimeMillis() - begin;
		}
		return SUCCESS;
	}
	
	public String toDownload(){
		return SUCCESS;
	}
	
	//附件下载
	public String download() throws ParseException{
		long begin = System.currentTimeMillis();
		try {
			webServiceService.download(projectType, methodName);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			costTime = System.currentTimeMillis() - begin;
		}
		return SUCCESS;
	}
	
	//进入xml下载页
	public String toSearchXml(){
		return SUCCESS;
	}
	
	//按条件搜索相应的xml
	public String searchXml(){
		String path = "upload/sinoss/source/" + projectStatus + "/" + sourceYear +"/";
		path = ApplicationContainer.sc.getRealPath(path);
		File file = new File(path.replace('\\', '/'));
		File[] files = file.listFiles();
		ArrayList<String> fileNameList = new ArrayList<String>();
		if(files!=null){
			for(File xmlFile:files){
				fileNameList.add(xmlFile.getName());
			}
		}
		Collections.sort(fileNameList);
		jsonMap.put("fileNameList", fileNameList);
		return SUCCESS;
	}
	
	//特定的xml下载
	public String downloadXml(){
		return SUCCESS;
	}
	
	//附件流[getResourceAsStream接受工程相对路径，如需下载指定硬盘上的绝对文件[即提供以'/'开头的绝对路径]，需自建输入流（new FileInputStream（new File（fileName）））]
	public InputStream getTargetFile() throws Exception{
		String filePath = "upload/sinoss/source/" + projectStatus + "/" + sourceYear +"/" + new String(fileName.getBytes("ISO8859-1"),"UTF-8");
		return ApplicationContainer.sc.getResourceAsStream(filePath.replace('\\', '/'));
	}
	
	/**
	 * 数据入库前检测文件是否存在
	 * @return
	 */
	public String fileExist(){
		int i = webServiceService.isFileExist(projectType, methodName, year);
		if(i==0){
			jsonMap.put(GlobalInfo.ERROR_INFO, "文件不存在，请先获取相应数据！");
		}else {
			session.put("i", i);
		}
		return SUCCESS;
	}
	
	/**
	 * 获取当前数据同步的文件数
	 * @return
	 */
	public String getCurrentObtain(){
		Map map = webServiceService.getCurrentTask();
		if(!(Boolean) map.get("isLinked")){
			jsonMap.put("obtainCount",0);
			jsonMap.put("isLinked", false);
		}else{
			int obtainCount = map.get("obtainCount") == null ? 0 : Integer.parseInt(map.get("obtainCount").toString());
			if(Integer.parseInt(map.get("flag").toString()) == 1){
				jsonMap.put("result", "数据获取结束");
				jsonMap.put("beginCount", obtainCount==0?(obtainCount*500):(obtainCount-1)*500);
				jsonMap.put("endCount", obtainCount*500);
			} else {
				jsonMap.put("beginCount", obtainCount*500);
				jsonMap.put("endCount", (obtainCount+1)*500);
			}
		}
		return SUCCESS;
	}
	
	/**
	 * 获取当前数据入库的数据条数
	 * @return
	 */
	public String getCurrentImporter(){
		Map map = webServiceService.getCurrentTask();
		int recordCount = map.get("recordCount") == null ? 0 : Integer.parseInt(map.get("recordCount").toString());
		int fileCount = map.get("fileCount") == null ? 0 : Integer.parseInt(map.get("fileCount").toString());
		if(Integer.parseInt(map.get("flag").toString()) == 1){
			jsonMap.put("count1", (fileCount-1)*500+recordCount);
			jsonMap.put("result", "数据获取结束");
		}else {
			jsonMap.put("count1", fileCount*500+recordCount);
		}
		return SUCCESS;
	}
	
	/**
	 * 获取当前正在进行的下载任务和当前已完成的任务数
	 * @return
	 */
	public String getCurrentAttachment(){
		Map map = webServiceService.getCurrentTask();
		jsonMap.put("finishedAttachment", map.get("finishedAttachment"));
		jsonMap.put("totalAttachment", map.get("totalAttachment"));
		jsonMap.put("currentAttachment", map.get("currentAttachment"));
		return SUCCESS;
	}
	
	/**
	 * 入库前的xml校验
	 * 原则上每次同步的数据中，相关的节点应该是不变化的，但是实际上对于经常同步的数据（如变更），可能存在某次数据量较小导致部分字段不存在的情况，这是正常的，此时可以跳过此校验。
	 * 命名由[validate+action名]组成，系统能自动识别并在该action执行前执行此校验方法
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public void validateImporter() throws ParserConfigurationException, SAXException, IOException{
		Map json = new HashedMap();
		int i = (session.get("i") != null) ? (Integer)session.get("i") : 0;
		json = webServiceService.xmlValidate(methodName, projectType, year, i);
		List<String> resultList = (List<String>)json.get("resultList");
		List<String> checkLogList = (List<String>)json.get("checkLogList");
		List<String> membersList = (List<String>)json.get("membersList");
		List<String> modifyContentList = (List<String>)json.get("modifyContentList");
		List<String> paperList = (List<String>)json.get("paperList");
		List<String> bookList = (List<String>)json.get("bookList");
		List<String> patentList = (List<String>)json.get("patentList");
		List<String> researchList = (List<String>)json.get("researchList");
		List<String> videoProductList = (List<String>)json.get("videoProductList");
		if(methodName.equals("getApplyProject")){
			for(String checkLog:applyCheckLog){
				if(checkLogList.contains(checkLog)){
					checkLogList.remove(checkLog);
				}else {
					this.addActionError(checkLog + "字段在数据中不存在");
					jsonMap.put("importError", checkLog + "字段在数据中不存在");
				}
			}
			if(!checkLogList.isEmpty()) {
				this.addActionError("审核记录数据中存在未知的字段");
				jsonMap.put("importError", "审核记录数据中存在未知的字段");
			}
			if(projectType.equals("gener") || projectType.equals("special")){
				for(String member:applyGenerAndSpecialMember){
					if(membersList.contains(member)){
						membersList.remove(member);
					}else {
						this.addActionError(member + "字段在数据中不存在");
						jsonMap.put("importError", member + "字段在数据中不存在");
					}
				}
				if(!membersList.isEmpty()) {
					this.addActionError("人员数据中存在未知的字段");
					jsonMap.put("importError", "人员数据中存在未知的字段");
				}
				if(projectType.equals("gener")){
					for(String result:applyGenerResult){
						if(resultList.contains(result)){
							resultList.remove(result);
						}else {
							this.addActionError(result + "字段在数据中不存在");
							jsonMap.put("importError", result + "字段在数据中不存在");
						}
					}
					if(!resultList.isEmpty()) {
						this.addActionError("基础数据中存在未知的字段");
						jsonMap.put("importError", "基础数据中存在未知的字段");
					}
				}
				if(projectType.equals("special")){
					for(String result:applySpecialResult){
						if(resultList.contains(result)){
							resultList.remove(result);
						}else {
							this.addActionError(result + "字段在数据中不存在");
							jsonMap.put("importError", result + "字段在数据中不存在");
						}
					}
					if(!resultList.isEmpty()) {
						this.addActionError("基础数据中存在未知的字段");
						jsonMap.put("importError", "基础数据中存在未知的字段");
					}
				}
			}
			if(projectType.equals("base")){
				for(String result:applyBaseResult){
					if(resultList.contains(result)){
						resultList.remove(result);
					}else {
						this.addActionError(result + "字段在数据中不存在");
						jsonMap.put("importError", result + "字段在数据中不存在");
					}
				}
				if(!resultList.isEmpty()) {
					this.addActionError("基础数据中存在未知的字段");
					jsonMap.put("importError", "基础数据中存在未知的字段");
				}
				for(String member:applyBaseMember){
					if(membersList.contains(member)){
						membersList.remove(member);
					}else {
						this.addActionError(member + "字段在数据中不存在");
						jsonMap.put("importError", member + "字段在数据中不存在");
					}
				}
				if(!membersList.isEmpty()) {
					this.addActionError("人员数据中存在未知的字段");	
					jsonMap.put("importError", "人员数据中存在未知的字段");
				}
			}
		}else if(methodName.equals("getModifyRecord")){//变更数据频繁同步，因此只判断是否多字段，不判断是否少字段
			for(String result:modifyResult){
				if(resultList.contains(result)){
					resultList.remove(result);
				}
			}
			if(!resultList.isEmpty()){
				this.addActionError("基础数据中存在未知的字段");
				jsonMap.put("importError", "基础数据中存在未知的字段");				
			}
			for(String checkLog:modifyCheckLog){
				if(checkLogList.contains(checkLog)){
					checkLogList.remove(checkLog);
				}
			}
			if(!checkLogList.isEmpty()){
				this.addActionError("审核记录中存在未知的字段");
				jsonMap.put("importError", "审核记录中存在未知的字段");
			}
			if(projectType.equals("gener")){
				for(String member:modifyGenerMember){
					if(membersList.contains(member)){
						membersList.remove(member);
					}
				}
				if(!membersList.isEmpty()){
					this.addActionError("人员数据中存在未知的字段");
					jsonMap.put("importError", "人员数据中存在未知的字段");
				}
				for(String content:modifyGenerContent){
					if(modifyContentList.contains(content)){
						modifyContentList.remove(content);
					}
				}
				if(!modifyContentList.isEmpty()) {
					this.addActionError("变更数据中存在未知的字段");
					jsonMap.put("importError", "变更数据中存在未知的字段");
				}
			}
			if(projectType.equals("base")){
				for(String member:modifyBaseMember){
					if(membersList.contains(member)){
						membersList.remove(member);
					}
				}
				if(!membersList.isEmpty()) {
					this.addActionError("人员数据中存在未知的字段");
					jsonMap.put("importError", "人员数据中存在未知的字段");
				}
				for(String content:modifyBaseContent){
					if(modifyContentList.contains(content)){
						modifyContentList.remove(content);
					}
				}
				if(!modifyContentList.isEmpty()) {
					this.addActionError("变更数据中存在未知的字段");
					jsonMap.put("importError", "变更数据中存在未知的字段");
				}
			}
		}else if(methodName.equals("getFinishRecord")){
			for(String result:finishResult){
				if(resultList.contains(result)){
					resultList.remove(result);
				}else {
					this.addActionError(result + "字段在数据中不存在");
					jsonMap.put("importError", result + "字段在数据中不存在");
				}
			}
			if(!resultList.isEmpty()){
				this.addActionError("基础数据中存在未知的字段");
				jsonMap.put("importError", "基础数据中存在未知的字段");				
			}
			for(String checkLog:finishCheckLog){
				if(checkLogList.contains(checkLog)){
					checkLogList.remove(checkLog);
				}else {
					this.addActionError(checkLog + "字段在数据中不存在");
					jsonMap.put("importError", checkLog + "字段在数据中不存在");
				}
			}
			if(!checkLogList.isEmpty()) {
				this.addActionError("审核记录中存在未知的字段");
				jsonMap.put("importError", "审核记录中存在未知的字段");
			}
		}else if(methodName.equals("getMidCheckRecords")){
			for(String result : midResult){
				if(resultList.contains(result)){
					resultList.remove(result);
				}else {
					this.addActionError(result + "字段在数据中不存在");
					jsonMap.put("importError", result + "字段在数据中不存在");
				}
			}
			if(!resultList.isEmpty()){
				this.addActionError("基础数据中存在未知的字段");
				jsonMap.put("importError", "基础数据中存在未知的字段");				
			}
			for(String checkLog : midCheckLog){
				if(checkLogList.contains(checkLog)){
					checkLogList.remove(checkLog);
				}else {
					this.addActionError(checkLog + "字段在数据中不存在");
					jsonMap.put("importError", checkLog + "字段在数据中不存在");
				}
			}
			if(!checkLogList.isEmpty()) {
				this.addActionError("审核记录中存在未知的字段");
				jsonMap.put("importError", "审核记录中存在未知的字段");
			}
			for(String paper : midPaper){
				if(paperList.contains(paper)){
					paperList.remove(paper);
				}else {
					this.addActionError(paper + "字段在数据中不存在");
					jsonMap.put("importError", paper + "字段在数据中不存在");
				}
			}
			if(!paperList.isEmpty()) {
				this.addActionError("论文信息中存在未知的字段");
				jsonMap.put("importError", "论文信息中存在未知的字段");
			}
			for(String book : midBook){
				if(bookList.contains(book)){
					bookList.remove(book);
				}else {
					this.addActionError(book + "字段在数据中不存在");
					jsonMap.put("importError", book + "字段在数据中不存在");
				}
			}
			if(!bookList.isEmpty()) {
				this.addActionError("著作信息中存在未知的字段");
				jsonMap.put("importError", "著作信息中存在未知的字段");
			}
			for(String research : midResearch){
				if(researchList.contains(research)){
					researchList.remove(research);
				}else {
					this.addActionError(research + "字段在数据中不存在");
					jsonMap.put("importError", research + "字段在数据中不存在");
				}
			}
			if(!researchList.isEmpty()) {
				this.addActionError("研究咨询报告信息中存在未知的字段");
				jsonMap.put("importError", "研究咨询报告信息中存在未知的字段");
			}
			if(projectType.equals("gener")){
				for(String patent : midGenerPatent){
					if(patentList.contains(patent)){
						patentList.remove(patent);
					}else {
						this.addActionError(patent + "字段在数据中不存在");
						jsonMap.put("importError", patent + "字段在数据中不存在");
					}
				}
				if(!patentList.isEmpty()) {
					this.addActionError("专利信息中存在未知的字段");
					jsonMap.put("importError", "专利信息中存在未知的字段");
				}
				for(String videoProduct : midGenerVideoProduct){
					if(videoProductList.contains(videoProduct)){
						videoProductList.remove(videoProduct);
					}else {
						this.addActionError(videoProduct + "字段在数据中不存在");
						jsonMap.put("importError", videoProduct + "字段在数据中不存在");
					}
				}
				if(!videoProductList.isEmpty()) {
					this.addActionError("电子出版物信息中存在未知的字段");
					jsonMap.put("importError", "电子出版物信息中存在未知的字段");
				}	
			}
		}
		if(jsonMap.get("importError") != null){
			System.out.println("警告，有错误如下：" + jsonMap.get("importError"));
		}
	}

	@Override
	public void executeTask(Task task) {
		if (task.getMethodName().equals("obtain")) {
			String[] parametersArray = task.getParameters().split("; ");
			projectType = parametersArray[0];
			methodName = parametersArray[1];
			try {
				obtain();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			} catch (SAXException e) {
				e.printStackTrace();
			}
		}else if (task.getMethodName().equals("importer")) {
			String[] parametersArray = task.getParameters().split("; ");
			methodName = parametersArray[0];
			projectType = parametersArray[1];
			try {
				importer();
			} catch (DOMException e) {
				e.printStackTrace();
			} catch (XPathExpressionException e) {
				e.printStackTrace();
			} catch (SAXException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			}
		}else if (task.getMethodName().equals("download")) {
			String[] parametersArray = task.getParameters().split("; ");
			projectType = parametersArray[0];
			methodName = parametersArray[1];
			try {
				download();
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
	}
	
	public String getProjectType() {
		return projectType;
	}

	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getProjectStatus() {
		return projectStatus;
	}
	
	public void setProjectStatus(String projectStatus) {
		this.projectStatus = projectStatus;
	}

	public int getSourceYear() {
		return sourceYear;
	}
	
	public void setSourceYear(int sourceYear) {
		this.sourceYear = sourceYear;
	}
	
	public String getFileName() {
		return fileName;
	}
	
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public long getCostTime() {
		return costTime;
	}

	public void setCostTime(long costTime) {
		this.costTime = costTime;
	}

	public IWebServiceService getWebServiceService() {
		return webServiceService;
	}

	public void setWebServiceService(IWebServiceService webServiceService) {
		this.webServiceService = webServiceService;
	}

	public IInterfaceService getInterfaceService() {
		return interfaceService;
	}

	public void setInterfaceService(IInterfaceService interfaceService) {
		this.interfaceService = interfaceService;
	}

	@Override
	public String pageName() {
		return null;
	}

	@Override
	public String[] column() {
		return null;
	}

	@Override
	public String dateFormat() {
		return null;
	}

	@Override
	public Object[] simpleSearchCondition() {
		return null;
	}

	@Override
	public Object[] advSearchCondition() {
		return null;
	}

	@Override
	public String pageBufferId() {
		return null;
	}

	public IProjectService getProjectService() {
		return projectService;
	}

	public void setProjectService(IProjectService projectService) {
		this.projectService = projectService;
	}

}
