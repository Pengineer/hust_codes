package csdc.service.imp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.activiti.engine.impl.cmd.GetStartFormCmd;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import csdc.bean.Project;
import csdc.dao.IBaseDao;
import csdc.tool.bean.Page;

/**
 * 请假流程Service
 * 
 * @author HenryYan
 */
@Component
@Transactional
public class ProjectWorkflowService {

  private static Logger logger = LoggerFactory.getLogger(ProjectWorkflowService.class);

  private IBaseDao baseDao;

  private RuntimeService runtimeService;

  protected TaskService taskService;

  protected HistoryService historyService;

  protected RepositoryService repositoryService;

  @Autowired
  private IdentityService identityService;

  /**
   * 启动流程
   * 
   * @param entity
   */
  public ProcessInstance startWorkflow(Project entity, Map<String, Object> variables) {
	  
	/*projectManager.add(entity);*/
	baseDao.add(entity);
	
	logger.debug("save entity: {}", entity);
	String businessKey = entity.getId().toString();
	// 用来设置启动流程的人员ID，引擎会自动把用户ID保存到activiti:initiator中
	identityService.setAuthenticatedUserId(entity.getAccount().getId());
	ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("project", businessKey, variables);
	String processInstanceId = processInstance.getId();
	entity.setProcessInstanceId(processInstanceId);
	logger.debug("start process of {key={}, bkey={}, pid={}, variables={}}", new Object[] { "project", businessKey, processInstanceId, variables });
	return processInstance;
  }

  /**
   * 查询待办任务
   * 
   * @param userId
   *          用户ID
   * @return
   */
  @Transactional(readOnly = true)
  public List<Project> findTodoTasks(String userId, Page<Project> page, int[] pageParams) {
    List<Project> results = new ArrayList<Project>();
    List<Task> tasks = new ArrayList<Task>();

    // 根据当前人的ID查询
    TaskQuery todoQuery = taskService.createTaskQuery().processDefinitionKey("project").taskAssignee(userId).active().orderByTaskId().desc()
            .orderByTaskCreateTime().desc();
    List<Task> todoList = todoQuery.listPage(pageParams[0], pageParams[1]);

    for (Task task : todoList) {  
        System.out.println("ID:"+task.getId()+",姓名:"+task.getName()+",接收人:"+task.getAssignee()+",开始时间:"+task.getCreateTime());  
    }  
    
    
    // 根据当前人未签收的任务
    TaskQuery claimQuery = taskService.createTaskQuery().processDefinitionKey("project").taskCandidateUser(userId).active().orderByTaskId().desc()
            .orderByTaskCreateTime().desc();
    List<Task> unsignedTasks = claimQuery.listPage(pageParams[0], pageParams[1]);

    for (Task task : unsignedTasks) {  
        System.out.println("ID:"+task.getId()+",姓名:"+task.getName()+",接收人:"+task.getAssignee()+",开始时间:"+task.getCreateTime());  
    }  
    // 合并
    tasks.addAll(todoList);
    tasks.addAll(unsignedTasks);

    // 根据流程的业务ID查询实体并关联
    for (Task task : tasks) {
      String processInstanceId = task.getProcessInstanceId();
      ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).active().singleResult();
      String businessKey = processInstance.getBusinessKey();
      System.out.println(businessKey);
      Map map = new HashMap();
      map.put("businessKey", businessKey);
      Project project = null;
	try {
		project = (Project) baseDao.list(Project.class, map).get(0);
	} catch (Exception e) {
		
		e.printStackTrace();
	}
      /*Project project = projectManager.getProject(new Long(businessKey));*/
      project.setTask(task);
      project.setProcessInstance(processInstance);
      project.setProcessDefinition(getProcessDefinition(processInstance.getProcessDefinitionId()));
      results.add(project);
    }
    
    page.setTotalCount(todoQuery.count() + claimQuery.count());
    page.setResult(results);
    return results;
  }

  /**
   * 读取运行中的流程
   * 
   * @return
   */
  @Transactional(readOnly = true)
  public List<Project> findRunningProcessInstaces(Page<Project> page, int[] pageParams) {
    List<Project> results = new ArrayList<Project>();
    ProcessInstanceQuery query = runtimeService.createProcessInstanceQuery().processDefinitionKey("project").active().orderByProcessInstanceId().desc();
    List<ProcessInstance> list = query.listPage(pageParams[0], pageParams[1]);

    // 关联业务实体
    for (ProcessInstance processInstance : list) {
      String businessKey = processInstance.getBusinessKey();
      Map map = new HashMap();
      map.put("businessKey", businessKey);
      Project project = (Project) (baseDao.list(Project.class, map).get(0));

      /*Project project = projectManager.getProject(new Long(businessKey));*/
      
      
      project.setProcessInstance(processInstance);
      project.setProcessDefinition(getProcessDefinition(processInstance.getProcessDefinitionId()));
      results.add(project);

      // 设置当前任务信息
      List<Task> tasks = taskService.createTaskQuery().processInstanceId(processInstance.getId()).active().orderByTaskCreateTime().desc().listPage(0, 1);
      project.setTask(tasks.get(0));
    }
    
    page.setTotalCount(query.count());
    page.setResult(results);
    return results;
  }

  /**
   * 读取已结束中的流程
   * 
   * @return
   */
  @Transactional(readOnly = true)
  public List<Project> findFinishedProcessInstaces(Page<Project> page, int[] pageParams) {
    List<Project> results = new ArrayList<Project>();
    HistoricProcessInstanceQuery query = historyService.createHistoricProcessInstanceQuery().processDefinitionKey("project").finished().orderByProcessInstanceEndTime().desc();
    List<HistoricProcessInstance> list = query.listPage(pageParams[0], pageParams[1]);

    // 关联业务实体
    for (HistoricProcessInstance historicProcessInstance : list) {
      String businessKey = historicProcessInstance.getBusinessKey();
      
      Map map = new HashMap();
      map.put("businessKey", businessKey);
      Project project = (Project) baseDao.list(Project.class, map).get(0);
      /*Project project = projectManager.getProject(new Long(businessKey));*/
      project.setProcessDefinition(getProcessDefinition(historicProcessInstance.getProcessDefinitionId()));
      project.setHistoricProcessInstance(historicProcessInstance);
      results.add(project);
    }
    page.setTotalCount(query.count());
    page.setResult(results);
    return results;
  }

  /**
   * 查询流程定义对象
   * 
   * @param processDefinitionId
   *          流程定义ID
   * @return
   */
  protected ProcessDefinition getProcessDefinition(String processDefinitionId) {
    ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult();
    return processDefinition;
  }




	public IBaseDao getBaseDao() {
		return baseDao;
	}
	
	public void setBaseDao(IBaseDao baseDao) {
		this.baseDao = baseDao;
	}

@Autowired
  public void setRuntimeService(RuntimeService runtimeService) {
    this.runtimeService = runtimeService;
  }

  @Autowired
  public void setTaskService(TaskService taskService) {
    this.taskService = taskService;
  }

  @Autowired
  public void setHistoryService(HistoryService historyService) {
    this.historyService = historyService;
  }

  @Autowired
  public void setRepositoryService(RepositoryService repositoryService) {
    this.repositoryService = repositoryService;
  }

}
