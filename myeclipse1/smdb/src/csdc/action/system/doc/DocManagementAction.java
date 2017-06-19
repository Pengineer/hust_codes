package csdc.action.system.doc;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.csdc.service.imp.DmssService;
import org.springframework.beans.factory.annotation.Autowired;

import csdc.action.BaseAction;
import csdc.bean.Task;
import csdc.service.IDocService;
import csdc.service.imp.DocService;
import csdc.service.taskConfig.ITaskService;

public class DocManagementAction extends BaseAction implements ITaskService{

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private IDocService docService;
	
	@Autowired
	protected DmssService dmssService;
	
	private long costTime;//任务耗时[单位：ms]
	
	public String toView() {
		return SUCCESS;
	}
	
	private String[] paths;//需要上传文档文件夹路径 "[/upload/project/],[/upload/person]"
	
	public String view() {

		//获取数据库中C_FILE与C_DFS两个字段的关系
		Map<String, int[]> resultMap = null;
		Collection<int[]>  results = resultMap.values();
		int unfinishUpload = 0;
		int finishedUpload = 0;
		for (int[] is : results) {
			unfinishUpload += is[0];
			finishedUpload += is[3];
		}
		//所有有文件附件的表，一共
		//APPLICANT  AWARD_APPLICATION  MAIL  MESSAGE  NEWS  NOTICE  PERSON  PRODUCT  PEOJECT_ANNINSPECTION
		//PROJECT_DATA  PROJECT_ENDINSPECTION  PROJECT_GRANTED  PROJECT_MIDINSPECTION  PROJECT_VARIATION
		//STATISTIC_REPORT TEMPLATE
		if(resultMap.get("ApplicantFile")[0] > 0) {
			jsonMap.put("ApplicantFile", resultMap.get("ApplicantFile"));
		}
		jsonMap.put("unfinishUpload", unfinishUpload);
		jsonMap.put("finishedUpload", finishedUpload);
		jsonMap.put("totalFileNumber", unfinishUpload + finishedUpload);
		return SUCCESS;
	}
	
	public String toUpload() {
		//获取目录tree信息
		List<Map> tree = docService.getDirTree();
		jsonMap.put("treeMap", tree.toString());
		return SUCCESS;
	}
	public String upload() {
		if (/*dmssService.getOn() == 1*/true) {
			long begin = System.currentTimeMillis();
			try {
				docService.upload(paths);
			} catch (Exception e) {
				throw new RuntimeException(e);
			} finally {
				costTime = System.currentTimeMillis() - begin;
			}
		} else {
			jsonMap.put("errorInfo", 1);
		}
		return SUCCESS;
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

	@Override
	public void executeTask(Task task) {
		// TODO Auto-generated method stub
		if (task.getMethodName().equals("upload")) {
			String[] parameterArrays = task.getParameters().split(";");
			
		}
	}

	public String[] getPaths() {
		return paths;
	}

	public void setPaths(String[] paths) {
		this.paths = paths;
	}

}
