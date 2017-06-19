package csdc.action.system.interfaces;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import csdc.service.IInterfaceService;
import csdc.service.IProjectService;
import csdc.service.webService.client.IWebServiceService;
import csdc.tool.ApplicationContainer;
import csdc.tool.FileTool;
import csdc.tool.info.GlobalInfo;

public class SinossClientAction extends BaseAction {

	private static final long serialVersionUID = 1L;

	@Autowired
	private IWebServiceService webServiceService;
	
	@Autowired
	private IInterfaceService interfaceService;
	
	private IProjectService projectService;
	
	//社科网接口参数
	private String projectType;//项目类型
	private String methodName;//方法名
	private Integer year;//项目年度
	private String projectCode;//项目代码
	private Integer count;//申报数据每次取的数量
	private String attachmentDirection;//附件下载路径
	
	//变更数据的已知字段
	private String[] modifyResult = {"id", "projectId", "projectModifyType", "applyDate", "modifyReason", 
			"checkStatus", "checkDate", "checker", "modifyContent", "members", "checkLogs", "code", "typeCode", 
			"name", "smdbProjectId"};
	private String[] modifyChechLog = {"id", "checkStatus", "checkDate", "checker", "checkInfo"};
	private String[] modifyGenerMember = {"id", "name", "unitName", "title", "birthDay", 
			"specialty", "divide", "orders", "resAdvantage"};
	private String[] modifyGenerContent = {"id", "modifyFieIdMean", "beforeValue", "afterValue"};
	private String[] modifyBaseMember = {"id", "name", "unitName", "title", "divide", "orders", "resAdvantage"};
	private String[] modifyBaseContent = {"id", "modifyFieIdMean", "beforeValue", "afterValue", "idNumber"};
	
	//申报数据的已知字段
	private String[] applyChechLog = {"id", "checkStatus", "checkDate", "checker", "checkInfo"};
	private String[] applyGenerResult = {"id", "projectName", "typeCode", "projectType", "applyDate", 
			"planFinishDate", "subject", "researchDirection", "researchType", "lastProductMode", 
			"applyTotalFee", "otherFeeSource", "applyer", "gender", "birthday", "IDCardNo", "title", 
			"division", "duty", "eduLevel", "eduDegree", "language", "email", "address", "postalCode", 
			"tel", "checkStatus", "applyDocName", "members", "checkLogs", "unitCode", "unitName", 
			"batchId", "year", "batchName", "beginDate", "endDate", "telOffice", "checkDate", "checker"};
	private String[] applyGenerMember = {"id", "memberCode", "memberName", "memberUnit", "memberTitle", 
			"memberBirthday", "memberSpecialty", "memberDivide", "orders"};
	private String[] applyBaseResult = {"id", "projectName", "typeCode", "applyDate", "planFinishDate", 
			"subject", "subject1_1", "subject1_2", "lastProductMode", "words", "applyTotalFee", "applyer", 
			"birthday", "IDCardNo", "title", "division", "duty", "checkStatus", "checkDate", "checker", "applyDocName", 
			"attachmentName", "members", "checkLogs", "projectJdId", "projectJdName", "unitCode", "unitName", 
			"batchId", "year", "batchName", "beginDate", "endDate"};
	private String[] applyBaseMember = {"id", "memberName", "memberUnit", "memberTitle", 
		"memberBirthday", "memberSpecialty", "memberEduDegree", "orders"};

	public String toObtain(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		String y = sdf.format(new Date());
		year = Integer.parseInt(y);
		count = 200;
		return SUCCESS;
	}
	
	//数据获取
	public String obtain() throws IOException, ParseException{
		webServiceService.obtain(projectType, year, count, methodName, projectCode);
		return SUCCESS;
	}
	
	public String toDownload(){
		return SUCCESS;
	}
	
	//附件下载
	public String download() throws ParseException{
		webServiceService.download(projectType, methodName);
		return SUCCESS;
	}
	
	public String toImporter(){
		return SUCCESS;
	}
	
	//数据入库
	@Transactional
	public String importer() throws DOMException, XPathExpressionException, SAXException, 
	IOException, ParseException, ParserConfigurationException{
		if(methodName.equals("getModifyRecord") || methodName.equals("getMidCheckRecords")){
			webServiceService.importer(methodName, projectType, 1);
			return SUCCESS;
		}else if(methodName.equals("getApplyProject")){
			int i = (Integer)session.get("i");
			for(int j=1; j<i+1; j++){
				webServiceService.importer(methodName, projectType, j);
			}
			return SUCCESS;
		}else {
			jsonMap.put(GlobalInfo.ERROR_INFO, "未知数据类型！！！");
			return INPUT;
		}
	}
	
	/**
	 * 数据入库前检测文件是否存在
	 * @return
	 */
	public String fileExist(){
		String fileName;
		if("getApplyProject".equals(methodName)){//申报数据有多个文件
			int i = 0;
			int j = 1;
			String file = ApplicationContainer.sc.getRealPath("/system/interfaces/sinossResource/" + methodName + "_" + projectType + "_" + j + ".xml");
			while(FileTool.isExsits(file)){
				j++;
				file = ApplicationContainer.sc.getRealPath("/system/interfaces/sinossResource/" + methodName + "_" + projectType + "_" + j + ".xml");
			}
			i = j-1;
			session.put("i", i);
			fileName = ApplicationContainer.sc.getRealPath("/system/interfaces/sinossResource/" + methodName + "_" + projectType + "_" + i + ".xml");
		}else {//其他数据(变更、中检)都只有一个文件
			fileName = ApplicationContainer.sc.getRealPath("/system/interfaces/sinossResource/" + methodName + "_" + projectType + ".xml");
		}
		if (!FileTool.isExsits(fileName)) {
			jsonMap.put(GlobalInfo.ERROR_INFO, "文件不存在，请先获取相应数据！");
		}
		return SUCCESS;
	}
	
	/**
	 * 获取当前数据同步的文件数
	 * @return
	 */
	public String getCurrentObtain(){
		Map map = webServiceService.getCurrentTask();
		if(map.get("obtainCount") != null){
			if(Integer.parseInt(map.get("obtainCount").toString()) == 1000){
				jsonMap.put("result", "数据获取结束");
			}else {
				jsonMap.put("obtainCount", map.get("obtainCount"));				
			}
		}else jsonMap.put("obtainCount",0);
		return SUCCESS;
	}
	
	/**
	 * 获取当前数据入库的数据条数
	 * @return
	 */
	public String getCurrentImporter(){
		Map map = webServiceService.getCurrentTask();
		if(map.get("fileCount") != null){
			if(Integer.parseInt(map.get("fileCount").toString()) == 1000){
				jsonMap.put("result", "数据获取结束");
			}else {
				jsonMap.put("fileCount", map.get("fileCount"));				
			}
		}else jsonMap.put("fileCount",0);
		jsonMap.put("recordCount", map.get("recordCount") == null ? 0 : map.get("recordCount"));
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
	 * 命名由[validate+action名]组成，系统能自动识别并在该action执行前执行此校验方法
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public void validateImporter() throws ParserConfigurationException, SAXException, IOException{
		Map jsonMap = new HashedMap();
		int i = (session.get("i") != null) ? (Integer)session.get("i") : 0;
		jsonMap = webServiceService.xmlValidate(methodName, projectType, i);
		List<String> resultList = (List<String>)jsonMap.get("resultList");
		List<String> checkLogList = (List<String>)jsonMap.get("checkLogList");
		List<String> membersList = (List<String>)jsonMap.get("membersList");
		List<String> modifyContentList = (List<String>)jsonMap.get("modifyContentList");
		if(methodName.equals("getApplyProject")){
			for(String checkLog:applyChechLog){
				if(checkLogList.contains(checkLog)){
					checkLogList.remove(checkLog);
				}else this.addActionError(checkLog + "字段在数据中不存在");
			}
			if(projectType.equals("gener")){
				for(String result:applyGenerResult){
					if(resultList.contains(result)){
						resultList.remove(result);
					}else this.addActionError(result + "字段在数据中不存在");
				}
				if(!resultList.isEmpty()) this.addActionError("基础数据中存在未知的字段");
				if(!checkLogList.isEmpty()) this.addActionError("审核记录数据中存在未知的字段");
				for(String member:applyGenerMember){
					if(membersList.contains(member)){
						membersList.remove(member);
					}else this.addActionError(member + "字段在数据中不存在");
				}
				if(!membersList.isEmpty()) this.addActionError("人员数据中存在未知的字段");
			}
			if(projectType.equals("base")){
				for(String result:applyBaseResult){
					if(resultList.contains(result)){
						resultList.remove(result);
					}else this.addActionError(result + "字段在数据中不存在");
				}
				if(!resultList.isEmpty()) this.addActionError("基础数据中存在未知的字段");
				if(!checkLogList.isEmpty()) this.addActionError("审核记录数据中存在未知的字段");
				for(String member:applyBaseMember){
					if(membersList.contains(member)){
						membersList.remove(member);
					}else this.addActionError(member + "字段在数据中不存在");
				}
				if(!membersList.isEmpty()) this.addActionError("人员数据中存在未知的字段");	
			}
		}else if(methodName.equals("getModifyRecord") || methodName.equals("getMidCheckRecords")){
			for(String result:modifyResult){
				if(resultList.contains(result)){
					resultList.remove(result);
				}else this.addActionError(result + "字段在数据中不存在");
			}
			if(!resultList.isEmpty()) this.addActionError("基础数据中存在未知的字段");
			for(String checkLog:modifyChechLog){
				if(checkLogList.contains(checkLog)){
					checkLogList.remove(checkLog);
				}else this.addActionError(checkLog + "字段在数据中不存在");
			}
			if(!checkLogList.isEmpty()) this.addActionError("审核记录数据中存在未知的字段");
			if(projectType.equals("gener")){
				for(String member:modifyGenerMember){
					if(membersList.contains(member)){
						membersList.remove(member);
					}else this.addActionError(member + "字段在数据中不存在");
				}
				if(!membersList.isEmpty()) this.addActionError("人员数据中存在未知的字段");
				for(String content:modifyGenerContent){
					if(modifyContentList.contains(content)){
						modifyContentList.remove(content);
					}else this.addActionError(content + "字段在数据中不存在");
				}
				if(!modifyContentList.isEmpty()) this.addActionError("变更数据中存在未知的字段");
			}
			if(projectType.equals("base")){
				for(String member:modifyBaseMember){
					if(membersList.contains(member)){
						membersList.remove(member);
					}else this.addActionError(member + "字段在数据中不存在");
				}
				if(!membersList.isEmpty()) this.addActionError("人员数据中存在未知的字段");
				for(String content:modifyBaseContent){
					if(modifyContentList.contains(content)){
						modifyContentList.remove(content);
					}else this.addActionError(content + "字段在数据中不存在");
				}
				if(!modifyContentList.isEmpty()) this.addActionError("变更数据中存在未知的字段");
			}
		}else this.addActionError("未知数据类型");		
	}
	
	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
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
	
	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String getAttachmentDirection() {
		return attachmentDirection;
	}

	public void setAttachmentDirection(String attachmentDirection) {
		this.attachmentDirection = attachmentDirection;
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] column() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String dateFormat() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object[] simpleSearchCondition() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object[] advSearchCondition() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String pageBufferId() {
		// TODO Auto-generated method stub
		return null;
	}

	public IProjectService getProjectService() {
		return projectService;
	}

	public void setProjectService(IProjectService projectService) {
		this.projectService = projectService;
	}

}
