package csdc.action.oa;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import csdc.tool.WorkflowUtils;
import csdc.tool.bean.Page;
import csdc.tool.bean.PageUtil;
import csdc.service.imp.WorkflowTraceService;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.identity.User;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import csdc.bean.Account;
import csdc.bean.Expert;
import csdc.bean.Project;
import csdc.bean.common.Visitor;
import csdc.service.IBaseService;
import csdc.service.imp.ProjectWorkflowService;

@Component
@Transactional
public class WorkflowAction extends ActionSupport{

	protected Logger logger = LoggerFactory.getLogger(getClass());
	protected RepositoryService repositoryService;
	private IBaseService baseService;
    private File file;//对应文件域和页面中file input的name保持一致 
    private String fileFileName;//上传文件名
    private String fileContentType; //获取上传文件类型,命名格式：表单file控件名+ContentType(固定)
    private String deploymentId;
    private String processDefinitionId;
    private String resourceType;
    private ByteArrayInputStream inputStream;  
    private HttpServletResponse response;
    @Autowired
    protected ProjectWorkflowService workflowService;
    @Autowired
    protected TaskService taskService;
    
    protected WorkflowTraceService traceService;
    
    
    protected static Map<String, ProcessDefinition> PROCESS_DEFINITION_CACHE = new HashMap<String, ProcessDefinition>();
    
	String  exportDir = "/tmp/kft-activiti-demo";
	Map jsonMap = new HashMap();
	
	public String toList() {
		return SUCCESS;
	}
	
	public String list()  {	
		HttpServletRequest request = ServletActionContext.getRequest();
		List<Object[]> workflowList = new ArrayList<Object[]>();
	    /*
	     * 保存两个对象，一个是ProcessDefinition（流程定义），一个是Deployment（流程部署）
	     */
		
	    List<Object[]> objects = new ArrayList<Object[]>();

	    Page<Object[]> page = new Page<Object[]>(PageUtil.PAGE_SIZE);
	    int[] pageParams = PageUtil.init(page, request);

	    ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery().orderByDeploymentId().desc();
	    List<ProcessDefinition> processDefinitionList = processDefinitionQuery.listPage(pageParams[0], pageParams[1]);
	    String[] item;
	    for (ProcessDefinition processDefinition : processDefinitionList) {
/*	      String deploymentId = processDefinition.getDeploymentId();
	      Deployment deployment = repositoryService.createDeploymentQuery().deploymentId(deploymentId).singleResult();
	      objects.add(new Object[]{processDefinition, deployment});*/
			item = new String[9];
			String deploymentId = processDefinition.getDeploymentId();
		    Deployment deployment = repositoryService.createDeploymentQuery().deploymentId(deploymentId).singleResult();
			item[0] = processDefinition.getId();
			item[1] = deployment.getId();
			item[2] = processDefinition.getName();
			item[3] = processDefinition.getVersion() + "";
			item[4] = processDefinition.getResourceName();
			item[5] = processDefinition.getDiagramResourceName();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			item[6] = sdf.format(deployment.getDeploymentTime());
			item[7] = ((ProcessDefinitionEntity)processDefinition).getSuspensionState() + "";
			item[8] = processDefinition.getId();
			workflowList.add(item);
	    }
	    jsonMap.put("aaData", workflowList);
		return SUCCESS;
	}
	
	public String runningList()  {	
		HttpServletRequest request = ServletActionContext.getRequest();
		List<Object[]> workflowList = new ArrayList<Object[]>();
	    /*
	     * 保存两个对象，一个是ProcessDefinition（流程定义），一个是Deployment（流程部署）
	     */
		
	    List<Object[]> objects = new ArrayList<Object[]>();

	    Page<Project> page = new Page<Project>(PageUtil.PAGE_SIZE);
	    int[] pageParams = PageUtil.init(page, request);
	    workflowService.findRunningProcessInstaces(page, pageParams);
	    
	    String[] item;
	    for (Project project : page.getResult()) {
/*	      String deploymentId = processDefinition.getDeploymentId();
	      Deployment deployment = repositoryService.createDeploymentQuery().deploymentId(deploymentId).singleResult();
	      objects.add(new Object[]{processDefinition, deployment});*/
			item = new String[8];
			
			item[0] = project.getAccount().getName();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				item[1] = sdf.format(project.getApplyDate());
			} catch (Exception e) {
				e.printStackTrace();
			}
			item[2] = project.getName();
			item[3] = project.getTask().getName();
			
			if(((ProcessDefinitionEntity) project.getProcessDefinition()).getSuspensionState() == 1) {
				item[4] = "激活";
			}else {
				item[4] = "挂起";
			}
			item[5] = project.getId();
			item[6] = project.getProcessInstance().getId();
			item[7] = project.getProcessDefinition().getId();
			/*item[3] = ((ProcessDefinitionEntity) project.getProcessDefinition()).getSuspensionState() + "";*/
			workflowList.add(item);
	    }
	    jsonMap.put("aaData", workflowList);
		return SUCCESS;
	}
	
	public String toRunningList() {
		return SUCCESS;
	}
	
	public String toFinishedList() {
		return SUCCESS;
	}
	
	public String finishedList() {
		HttpServletRequest request = ServletActionContext.getRequest();
		List<Object[]> workflowList = new ArrayList<Object[]>();
	    /*
	     * 保存两个对象，一个是ProcessDefinition（流程定义），一个是Deployment（流程部署）
	     */
		
	    List<Object[]> objects = new ArrayList<Object[]>();

	    Page<Project> page = new Page<Project>(PageUtil.PAGE_SIZE);
	    int[] pageParams = PageUtil.init(page, request);
	    try {
			workflowService.findFinishedProcessInstaces(page, pageParams);
		} catch (Exception e1) {
			
			e1.printStackTrace();
		}
	    String[] item;
	    for (Project project : page.getResult()) {
			item = new String[6];
			
			item[0] = project.getAccount().getName();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				item[1] = sdf.format(project.getApplyDate());
			} catch (Exception e) {
				e.printStackTrace();
			}
			item[2] = project.getName();
			item[3] = project.getDirector();
			item[4] = project.getFee();

			item[5] = Integer.toString(project.getProcessDefinition().getVersion());
			/*item[3] = ((ProcessDefinitionEntity) project.getProcessDefinition()).getSuspensionState() + "";*/
			workflowList.add(item);
	    }
	    jsonMap.put("aaData", workflowList);
		return SUCCESS;
	}
	
	public String deploy() {
		
        //设置上传文件目录    
        String realpath = ServletActionContext.getServletContext().getRealPath("/tmp/eoas");  
		

/*		try {
			fileName = files.getOriginalFilename();
		} catch (Exception e1) {
			e1.printStackTrace();
		}*/
	    try {
	      /*InputStream fileInputStream = file.getInputStream();*/
	    	FileInputStream fileInputStream = new FileInputStream(file);
	      
	      Deployment deployment = null;

	      String extension = FilenameUtils.getExtension(fileFileName);
	      if (extension.equals("zip") || extension.equals("bar")) {
	        ZipInputStream zip = new ZipInputStream(fileInputStream);
	        deployment = repositoryService.createDeployment().addZipInputStream(zip).deploy();
	      } else {
	        deployment = repositoryService.createDeployment().addInputStream(fileFileName, fileInputStream).deploy();
	      }
	      List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().deploymentId(deployment.getId()).list();
	      for (ProcessDefinition processDefinition : list) {
	        WorkflowUtils.exportDiagramToFile(repositoryService, processDefinition, exportDir);
	      }

	    } catch (Exception e) {
	      logger.error("error on deploy process, because of file input stream", e);
	    }
		return 	SUCCESS;
	}
	
	  /**
	   * 读取资源，通过部署ID
	   *
	   * @param processDefinitionId 流程定义
	   * @param resourceType 资源类型(xml|image)
	   * @throws Exception
	   */
	  public void view() throws Exception {
		response = ServletActionContext.getResponse();
	    ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult();
	    String resourceName = "";
	    if (resourceType.equals("image")) {
	      resourceName = processDefinition.getDiagramResourceName();
	    } else if (resourceType.equals("xml")) {
	      resourceName = processDefinition.getResourceName();
	    }
	    InputStream resourceAsStream = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(), resourceName);
	    byte[] b = new byte[1024];
	    int len = -1;
	    response.setHeader("Content-Type", "image/jpeg");
	    while ((len = resourceAsStream.read(b, 0, 1024)) != -1) {
	      response.getOutputStream().write(b, 0, len);
	    }
	  }
	  
	  

	  /**  
	   * 
	   * 删除部署的流程，级联删除流程实例
	   *
	   * @param deploymentId 流程部署ID
	   */

	  public String delete() {
	    try {
			repositoryService.deleteDeployment(deploymentId, true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	    Deployment deployment = repositoryService.createDeploymentQuery().deploymentId(deploymentId).singleResult();
		if(null != deployment) {
			jsonMap.put("result", 0);
		} else {
			jsonMap.put("result", 1);
		}
		return SUCCESS;
	  }
	
	  
	  /**
	   * 输出跟踪流程信息
	   *
	   * @param processInstanceId
	   * @return
	   * @throws Exception
	   */
	  @SuppressWarnings("unused")
	public String trace() throws Exception {
		List<Map<String, Object>> activityInfos = traceService.traceProcess(processDefinitionId);
		jsonMap.put("activityInfos", activityInfos);
		return SUCCESS;
	}
	  
	  /**
	   * 待办任务--Portlet
	   */
	  public String todoList() {
	    Map session = ActionContext.getContext().getSession();
	    Account account  = ((Visitor) session.get("visitor")).getAccount();
	    List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
	    
	    // 已经签收的任务
	    List<Task> todoList = taskService.createTaskQuery().taskAssignee(account.getId()).active().list();
	    for (Task task : todoList) {
	      String processDefinitionId = task.getProcessDefinitionId();
	      ProcessDefinition processDefinition = getProcessDefinition(processDefinitionId);

	      Map<String, Object> singleTask = packageTaskInfo(sdf, task, processDefinition);
	      singleTask.put("status", "todo");
	      result.add(singleTask);
	    }

	    // 等待签收的任务
	    List<Task> toClaimList = taskService.createTaskQuery().taskCandidateUser(account.getId()).active().list();
	    for (Task task : toClaimList) {
	      String processDefinitionId = task.getProcessDefinitionId();
	      ProcessDefinition processDefinition = getProcessDefinition(processDefinitionId);

	      Map<String, Object> singleTask = packageTaskInfo(sdf, task, processDefinition);
	      singleTask.put("status", "claim");
	      result.add(singleTask);
	    }
	    jsonMap.put("result", result);
	    return SUCCESS;
	  }
	  
	  private Map<String, Object> packageTaskInfo(SimpleDateFormat sdf, Task task, ProcessDefinition processDefinition) {
		    Map<String, Object> singleTask = new HashMap<String, Object>();
		    singleTask.put("id", task.getId());
		    singleTask.put("name", task.getName());
		    singleTask.put("createTime", sdf.format(task.getCreateTime()));
		    singleTask.put("pdname", processDefinition.getName());
		    singleTask.put("pdversion", processDefinition.getVersion());
		    singleTask.put("pid", task.getProcessInstanceId());
		    return singleTask;
		  }

		  private ProcessDefinition getProcessDefinition(String processDefinitionId) {
		    ProcessDefinition processDefinition = PROCESS_DEFINITION_CACHE.get(processDefinitionId);
		    if (processDefinition == null) {
		      processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult();
		      PROCESS_DEFINITION_CACHE.put(processDefinitionId, processDefinition);
		    }
		    return processDefinition;
		  }
	  
	  
	public IBaseService getBaseService() {
		return baseService;
	}

	public void setBaseService(IBaseService baseService) {
		this.baseService = baseService;
	}

	
	public Map getJsonMap() {
		return jsonMap;
	}

	public RepositoryService getRepositoryService() {
		return repositoryService;
	}

	public void setRepositoryService(RepositoryService repositoryService) {
		this.repositoryService = repositoryService;
	}

	public void setJsonMap(Map jsonMap) {
		this.jsonMap = jsonMap;
	}











	public ByteArrayInputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(ByteArrayInputStream inputStream) {
		this.inputStream = inputStream;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	public String getProcessDefinitionId() {
		return processDefinitionId;
	}

	public void setProcessDefinitionId(String processDefinitionId) {
		this.processDefinitionId = processDefinitionId;
	}

	public String getResourceType() {
		return resourceType;
	}

	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}

	public String getDeploymentId() {
		return deploymentId;
	}

	public void setDeploymentId(String deploymentId) {
		this.deploymentId = deploymentId;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

	public String getFileContentType() {
		return fileContentType;
	}

	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}

	public String getExportDir() {
		return exportDir;
	}

	public void setExportDir(String exportDir) {
		this.exportDir = exportDir;
	}
	
	public TaskService getTaskService() {
		return taskService;
	}
	@Autowired
	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}

	  @Autowired
	  public void setTraceService(WorkflowTraceService traceService) {
	    this.traceService = traceService;
	  }
}