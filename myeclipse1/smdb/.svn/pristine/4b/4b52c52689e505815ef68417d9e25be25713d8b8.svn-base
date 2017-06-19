package csdc.tool.ontimeTask;

import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import csdc.service.IBaseService;
import csdc.service.webService.client.IWebServiceService;
import csdc.tool.SpringBean;
import csdc.tool.execution.importer.GeneralProjectVariationAutoImporter;
import csdc.tool.execution.importer.InstpVariationAutoImporter;

/**
 * SINOSS定时任务
 * @author suwb
 *
 */
public class SinossTask {

    @Autowired
    private IWebServiceService webServiceService;
    @Autowired
    private IBaseService baseService;
    private static final int year = Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date()));
    private static final String OPTIONALHOST = "csdc.hust.edu.cn";//只能在特定域名下执行
    private static final String OPTIONALDATABASE = "smdb";//只能在特定数据源中执行
    
    //变更数据的已知字段
  	private String[] modifyResult = {"id", "projectId", "projectModifyType", "applyDate", "modifyReason", 
  			"checkStatus", "checkDate", "checker", "modifyContent", "members", "checkLogs", "code", "typeCode", 
  			"name", "smdbProjectId"};
  	private String[] modifyCheckLog = {"id", "checkStatus", "checkDate", "checker", "checkInfo"};
  	private String[] modifyGenerMember = {"id", "name", "unitName", "title", "birthDay", 
  			"specialty", "divide", "orders", "resAdvantage"};
  	private String[] modifyGenerContent = {"id", "modifyFieIdMean", "beforeValue", "afterValue"};
  	private String[] modifyBaseMember = {"id", "name", "unitName", "title", "divide", "orders", "resAdvantage"};
  	private String[] modifyBaseContent = {"id", "modifyFieIdMean", "beforeValue", "afterValue", "idNumber"};
  	
  	//定时同步变更数据
    @Transactional
	public void getModifyRecord() throws Exception {
    	System.out.println("定时器开始执行");
    	String myHostName = InetAddress.getLocalHost().getHostName();
    	String myDataBase = baseService.getPropertiesValue(getClass(), "/init.properties", "jndiname").split("/")[2];
    	if(OPTIONALHOST.contains(myHostName)&&OPTIONALDATABASE.contains(myDataBase)){
    		String projectType = "gener";
    		webServiceService.obtain(projectType, year, 500, "getModifyRecord");
    		int i = webServiceService.isFileExist(projectType, "getModifyRecord", year);
    		if(i>0){
    			Map json = webServiceService.xmlValidate("getModifyRecord", projectType, year, i);
    			if(validateModifyData(json, projectType, i)){
    				webServiceService.importer("getModifyRecord", projectType, year, i);
    			}
    		}
    		GeneralProjectVariationAutoImporter gpv = (GeneralProjectVariationAutoImporter)SpringBean.getBean("generalProjectVariationAutoImporter");
    		gpv.excuteNoTransaction();
    		projectType = "base";
    		webServiceService.obtain(projectType, year, 500, "getModifyRecord");
    		int j = webServiceService.isFileExist(projectType, "getModifyRecord", year);
    		if(j>0){
    			Map json = webServiceService.xmlValidate("getModifyRecord", projectType, year, i);
    			if(validateModifyData(json, projectType, i)){
    				webServiceService.importer("getModifyRecord", projectType, year, i);
    			}
    		}
    		InstpVariationAutoImporter ipv = (InstpVariationAutoImporter)SpringBean.getBean("instpVariationAutoImporter");
    		ipv.excuteNoTransaction();
    		System.out.println("定时器执行成功");
    	}else {
    		System.out.println("非可选域名或可选数据源，执行取消");
    	}
	}
    
    private boolean validateModifyData(Map json, String projectType, int i){
    	List<String> resultList = (List<String>)json.get("resultList");
    	List<String> modifyContentList = (List<String>)json.get("modifyContentList");
    	List<String> checkLogList = (List<String>)json.get("checkLogList");
    	List<String> membersList = (List<String>)json.get("membersList");
    	for(String result:modifyResult){
			if(resultList.contains(result)){
				resultList.remove(result);
			}
		}
		if(!resultList.isEmpty()){
			return false;
		}
		for(String checkLog:modifyCheckLog){
			if(checkLogList.contains(checkLog)){
				checkLogList.remove(checkLog);
			}
		}
		if(!checkLogList.isEmpty()){
			return false;
		}
		if(projectType.equals("gener")){
			for(String member:modifyGenerMember){
				if(membersList.contains(member)){
					membersList.remove(member);
				}
			}
			if(!membersList.isEmpty()){
				return false;
			}
			for(String content:modifyGenerContent){
				if(modifyContentList.contains(content)){
					modifyContentList.remove(content);
				}
			}
			if(!modifyContentList.isEmpty()) {
				return false;
			}
		}
		if(projectType.equals("base")){
			for(String member:modifyBaseMember){
				if(membersList.contains(member)){
					membersList.remove(member);
				}
			}
			if(!membersList.isEmpty()) {
				return false;
			}
			for(String content:modifyBaseContent){
				if(modifyContentList.contains(content)){
					modifyContentList.remove(content);
				}
			}
			if(!modifyContentList.isEmpty()) {
				return false;
			}
		}
		return true;
    }
}
