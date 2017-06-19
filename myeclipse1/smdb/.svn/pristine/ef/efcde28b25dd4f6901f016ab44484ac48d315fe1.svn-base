package csdc.action.dataProcessing.dataImporter;

import java.util.HashMap;
import java.util.Map;

import com.opensymphony.xwork2.ActionSupport;

import csdc.bean.Task;
import csdc.service.taskConfig.ITaskService;
import csdc.tool.SpringBean;
import csdc.tool.execution.Execution;

/**
 * 自动化入库
 * 
 * @author maowh
 */
public class DataImporterAction extends ActionSupport implements ITaskService {

	private static final long serialVersionUID = 1L;
	private Map jsonMap = new HashMap();// json对象容器
	private String projectType;// 项目类型
	private String importType;// 导入项目类型
	// private int status;//是否取消导入（1：是；0：否）
	private int isStarted;// 是否开始导入（1：开始导入；0：查询进度）

	private String beanName = null;

	public String startImport() throws Throwable {

		if (projectType.equals("general") && importType.equals("application")) {
			beanName = "generalProjectApplicationAutoImporter";
		} else if (projectType.equals("special") && importType.equals("application")) {
			beanName = "specialProjectApplicationAutoImporter";
		} else if (projectType.equals("instp") && importType.equals("application")) {
			beanName = "instpApplicationAutoImporter";
		} else if (projectType.equals("general") && importType.equals("midInspection")) {
			beanName = "generalProjectMidInspectionAutoImporter";
		} else if (projectType.equals("instp") && importType.equals("midInspection")) {
			beanName = "instpMidInspectionAutoImporter";
		} else if (projectType.equals("general") && importType.equals("variation")) {
			beanName = "generalProjectVariationAutoImporter";
		} else if (projectType.equals("instp") && importType.equals("variation")) {
			beanName = "instpVariationAutoImporter";
		}
		Execution execute = (Execution) SpringBean.getBean(beanName);
		if (isStarted == 1) {
			execute.setStatus(0);
			execute.excute();
		} else {
			try {
				int currentNum = execute.getCurrentNum();
				int totalNum = execute.getTotalNum();
				int isFinished = execute.getIsFinished();
				jsonMap.put("currentNum", currentNum);
				jsonMap.put("totalNum", totalNum);
				jsonMap.put("isFinished", isFinished);
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException();
			}
		}
		return SUCCESS;
	}

	public String cancelImport() throws Throwable {
		 if (projectType.equals("general") && importType.equals("application")) {
			 beanName = "generalProjectApplicationAutoImporter";
		 } else if (projectType.equals("special") && importType.equals("application")) {
			 beanName = "specialProjectApplicationAutoImporter";
		 } else if (projectType.equals("instp") && importType.equals("application")) {
			 beanName = "instpApplicationAutoImporter";
		 } else if (projectType.equals("general") && importType.equals("midInspection")) {
			 beanName = "generalProjectMidInspectionAutoImporter";
		 } else if (projectType.equals("instp") && importType.equals("midInspection")) {
			 beanName = "instpMidInspectionAutoImporter";
		 } else if (projectType.equals("general") && importType.equals("variation")) {
			 beanName = "generalProjectVariationAutoImporter";
		 } else if (projectType.equals("instp") && importType.equals("variation")) {
			 beanName = "instpVariationAutoImporter";
		 }
		 Execution execute = (Execution) SpringBean.getBean(beanName);
		 execute.setStatus(1);
		 jsonMap.put("status", 1);
		return SUCCESS;
	}

	public String resultDetail() throws Throwable {
		String beanName = null;
		if (projectType.equals("general") && importType.equals("application")) {
			beanName = "generalProjectApplicationAutoImporter";
		} else if (projectType.equals("special") && importType.equals("application")) {
			beanName = "specialProjectApplicationAutoImporter";
		} else if (projectType.equals("instp") && importType.equals("application")) {
			beanName = "instpApplicationAutoImporter";
		} else if (projectType.equals("general") && importType.equals("midInspection")) {
			beanName = "generalProjectMidInspectionAutoImporter";
		} else if (projectType.equals("instp") && importType.equals("midInspection")) {
			beanName = "instpMidInspectionAutoImporter";
		} else if (projectType.equals("general") && importType.equals("variation")) {
			beanName = "generalProjectVariationAutoImporter";
		} else if (projectType.equals("instp") && importType.equals("variation")) {
			beanName = "instpVariationAutoImporter";
		}
		Execution execute = (Execution) SpringBean.getBean(beanName);
		jsonMap.put("resultDetail", execute.getIllegalException());
		jsonMap.put("totalImportNum", execute.getTotalImportNum());
		jsonMap.put("totalNum", execute.getTotalNum());
		return SUCCESS;
	}

	@Override
	public void executeTask(Task task) {
		if(task.getMethodName().equals("startImport")){
			String[] parametersArray = task.getParameters().split("; ");
			projectType = parametersArray[0];
			importType = parametersArray[1];
			try {
				startImport();
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
	}
	
	public String toApplicationView() {
		return SUCCESS;
	}

	public String toMidinspectionView() {
		return SUCCESS;
	}

	public String toVariationView() {
		return SUCCESS;
	}

	public String toEndinspectionView() {
		return SUCCESS;
	}

	public String toProgress() {
		return SUCCESS;
	}

	public Map getJsonMap() {
		return jsonMap;
	}

	public void setJsonMap(Map jsonMap) {
		this.jsonMap = jsonMap;
	}

	public String getProjectType() {
		return projectType;
	}

	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}

	public String getImportType() {
		return importType;
	}

	public void setImportType(String importType) {
		this.importType = importType;
	}

	public int getIsStarted() {
		return isStarted;
	}

	public void setIsStarted(int isStarted) {
		this.isStarted = isStarted;
	}
}
