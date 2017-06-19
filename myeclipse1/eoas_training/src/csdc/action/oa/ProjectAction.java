package csdc.action.oa;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.Session;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import jodd.util.StringUtil;
import csdc.tool.DateConverter;
import csdc.tool.PropertyType;

import org.activiti.engine.ActivitiException;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.identity.User;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import csdc.service.IBaseService;
import csdc.service.imp.ProjectWorkflowService;
import csdc.tool.bean.Page;
import csdc.tool.bean.PageUtil;
import csdc.bean.Account;
import csdc.bean.Project;
import csdc.bean.common.Visitor;
import csdc.dao.BaseDao;

public class ProjectAction extends ActionSupport {
	private IBaseService baseService;
	private Project project = new Project();
	private Visitor visitor;
	private Logger logger = LoggerFactory.getLogger(getClass());
    protected Map jsonMap = new HashMap();
    private String projectId;
    private String taskId;
	private String keys, values, types;

    @Autowired
    protected ProjectWorkflowService projectWorkflowService;

    @Autowired
    protected RuntimeService runtimeService;

    @Autowired
    protected TaskService taskService;
	  
	public String toApply() {
		return SUCCESS;
	}

	public String apply() {
	
/*	    try {
	    	Map session = ActionContext.getContext().getSession();
	        Account account = ((Visitor) session.get("visitor")).getAccount();
	        // 用户未登陆不能操作，实际应用使用权限框架实现，例如Spring Security、Shiro等
	        if (account == null) {
	          return "redirect:/login?timeout=true";
	        }
	        project.setAccount(account);
	        Map<String, Object> variables = new HashMap<String, Object>();
	        ProcessInstance processInstance = projectWorkflowService.startWorkflow(project, variables);
	        redirectAttributes.addFlashAttribute("message", "流程已启动，流程ID：" + processInstance.getId());
	      } catch (ActivitiException e) {
	        if (e.getMessage().indexOf("no processes deployed with key") != -1) {
	          logger.warn("没有部署流程!", e);
	          redirectAttributes.addFlashAttribute("error", "没有部署流程，请在[工作流]->[流程管理]页面点击<重新部署流程>");
	        } else {
	          logger.error("启动请假流程失败：", e);
	          redirectAttributes.addFlashAttribute("error", "系统内部错误！");
	        }
	      } catch (Exception e) {
	        logger.error("启动请假流程失败：", e);
	        redirectAttributes.addFlashAttribute("error", "系统内部错误！");
	      }*/
    	Map session = ActionContext.getContext().getSession();
        Account account = null;
		try {
			account = ((Visitor) session.get("visitor")).getAccount();
		} catch (Exception e) {
			e.printStackTrace();
		}
        // 用户未登陆不能操作，实际应用使用权限框架实现，例如Spring Security、Shiro等
        if (account == null) {
          return "redirect:/login?timeout=true";
        }
        project.setAccount(account);
        project.setApplyDate(new Date());
        Map<String, Object> variables = new HashMap<String, Object>();
        try {
			ProcessInstance processInstance = projectWorkflowService.startWorkflow(project, variables);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	
	public String claim() {
		Map session = ActionContext.getContext().getSession();
		Account account = ((Visitor) session.get("visitor")).getAccount();
		try {
			taskService.claim(taskId, account.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		jsonMap.put("result", 1);
		System.out.println(jsonMap.get("result"));
	    return SUCCESS;
	}
	
	public String load() {
	    try {
			project = (Project) baseService.load(Project.class, projectId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String view() {
	    try {
			project = (Project) baseService.load(Project.class, projectId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	

	public String toTaskList() {
		return SUCCESS;
	}
	
	public String taskList() {
		HttpServletRequest request = ServletActionContext.getRequest();
		List<Object[]> workflowList = new ArrayList<Object[]>();	
	    Page<Project> page = new Page<Project>(PageUtil.PAGE_SIZE);
	    int[] pageParams = PageUtil.init(page, request);	    
	    Map session = ActionContext.getContext().getSession();
	    Account account = ((Visitor) session.get("visitor")).getAccount();
	    projectWorkflowService.findTodoTasks(account.getId(), page, pageParams);
	    String[] item;
	    for (Project project : page.getResult()) {
			item = new String[8];
			item[0] = project.getId();
			item[1] = project.getName();
			item[2] = project.getDirector();
			item[3] = project.getTask().getName();
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss E");
			item[4] = sdf.format(project.getTask().getCreateTime());
			
			
			if(((ProcessDefinitionEntity) project.getProcessDefinition()).getSuspensionState() == 1) {
				item[5] = "已挂起";
			}else {
				item[5] = "正常";
			}
			String a = "{'id':'" + project.getTask().getId() +
					"','assignee':'" + project.getTask().getAssignee() +
					"','taskDefinitionKey':'" + project.getTask().getTaskDefinitionKey() +
					"','name':'" + project.getTask().getName() +
					"'}";
			item[6] = a;
			item[7] = project.getId();
			workflowList.add(item);
	    }
	    jsonMap.put("aaData", workflowList);
		return SUCCESS;
	}
	
	/**
	 * 读取运行中的流程实例
	 * 
	 * @return
	 */
	public String viewRunning() {
	    Page<Project> page = new Page<Project>(PageUtil.PAGE_SIZE);
	    HttpServletRequest request = ServletActionContext.getRequest();
	    int[] pageParams = PageUtil.init(page, request);
	    projectWorkflowService.findRunningProcessInstaces(page, pageParams);
		return SUCCESS;
	}

	public String refuse() {
		return SUCCESS;
	}
	public String confirm() {
		return SUCCESS;
	}
	
	  /**
	   * 完成任务
	   * 
	   * @param id
	   * @return
	   */
	  public String complete() {
	    try {
			Map<String, Object> variables = new HashMap<String, Object>();
			ConvertUtils.register(new DateConverter(), java.util.Date.class);
			String[] arrayKey = keys.split(",");
			String[] arrayValue = values.split(",");
			String[] arrayType = types.split(",");
			for (int i = 0; i < arrayKey.length; i++) {
				String key = arrayKey[i];
				String value = arrayValue[i];
				String type = arrayType[i];

				Class<?> targetType = Enum.valueOf(PropertyType.class, type).getValue();
				Object objectValue = ConvertUtils.convert(value, targetType);
				variables.put(key, objectValue);
			}
	      taskService.complete(taskId, variables);
	      jsonMap.put("result", 1);
	      return SUCCESS;
	    } catch (Exception e) {
	      logger.error("error on complete task {}, variables={}", new Object[] { taskId, keys + values + types, e });
	      return ERROR;
	    }
	  }
	
	public IBaseService getBaseService() {
		return baseService;
	}

	public void setBaseService(IBaseService baseService) {
		this.baseService = baseService;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Map getJsonMap() {
		return jsonMap;
	}

	public void setJsonMap(Map jsonMap) {
		this.jsonMap = jsonMap;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getKeys() {
		return keys;
	}

	public void setKeys(String keys) {
		this.keys = keys;
	}

	public String getValues() {
		return values;
	}

	public void setValues(String values) {
		this.values = values;
	}

	public String getTypes() {
		return types;
	}

	public void setTypes(String types) {
		this.types = types;
	}

	
}