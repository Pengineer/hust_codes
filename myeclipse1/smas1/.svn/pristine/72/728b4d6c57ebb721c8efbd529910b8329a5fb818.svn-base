package csdc.service.imp;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jxl.demo.XML;

import oracle.net.aso.a;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFFooter;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.dao.support.DaoSupport;
import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.ActionContext;

import csdc.bean.Expert;
import csdc.bean.ProjectApplication;
import csdc.bean.ProjectApplicationReview;
import csdc.bean.ProjectApplicationReviewUpdate;
import csdc.bean.ProjectEndinspection;
import csdc.bean.ProjectFee;
import csdc.bean.ProjectFunding;
import csdc.bean.ProjectGranted;
import csdc.bean.ProjectMidinspection;
import csdc.bean.ProjectVariation;
import csdc.bean.SmdbProjectApplication;
import csdc.bean.SystemOption;
import csdc.bean.common.ExpertLink;
import csdc.service.IProjectService;
import csdc.tool.ApplicationContainer;
import csdc.tool.DoubleTool;
import csdc.tool.ExpertTreeItem;
import csdc.tool.FileTool;
import csdc.tool.HSSFExport;
import csdc.tool.SpringBean;
import csdc.tool.WordTool;
import csdc.tool.matcher.GeneralReviewerMatcher;
import csdc.tool.matcher.InstpReviewerMatcher;
import csdc.tool.matcher.MatcherWarningUpdater;

public class ProjectService extends BaseService implements IProjectService{

	/**
	 * 专家树中根据选中的专家id","的字符串，获取响应的专家信息（用于右侧已经选择专家列表显示）
	 */
	public String getExpInfo(String projectType, String ids) {
		
		if(ids == null || ids.isEmpty() || ids.equals("null"))
			return null;
		String[] str = ids.split("[^a-zA-Z0-9]");
		String re = "";
		ActionContext context = ActionContext.getContext();
		Map session = context.getSession();
		Map expDetailMap = (Map) session.get("allExp");
		Integer defaultYear = (Integer) session.get("defaultYear");
		//高校代码 -> 高校名称
		HashMap<String, String> univMap = (HashMap<String, String>) context.getApplication().get("univCodeNameMap");
				
		ExpertTreeItem ExpItem = null;
		for(int i = 0; i < str.length; i++) {
			if(!str[i].equals("null")) {
				if(expDetailMap != null && expDetailMap.size() >= 0) {
					ExpItem = (ExpertTreeItem) expDetailMap.get(str[i]);
					if(ExpItem != null) {
						re = re + str[i] + "," + "[" + ExpItem.discipline + "]^" + ExpItem.name + "^" + ExpItem.university + ExpItem.specialityTitle + "[参评" + ExpItem.pojcnt + "项]" + "%";
					}
					else {
						Expert tmp = (Expert) this.query(Expert.class, str[i]);
						int xx = this.query("select pr from Expert e left join e.applicationReview pr with pr.year = " + defaultYear + " and pr.type = '" + projectType + "' where e.isReviewer = 1 and e.id = '" + str[i] + "' ").size();
//						List tmpp = this.query("select p from GeneralReviewer p left outer join p.reviewer r where p.year = " + defaultYear + " and p.priority <= 5 and r.isReviewer = 1 and r.id = '" + str[i] + "' ");
//						List tmpp = this.query("select p from GeneralReviewer p left outer join p.reviewer r where p.year = " + defaultYear + " and r.isReviewer = 1 and r.id = '" + str[i] + "' ");
//						int yy = 0;
//						if(tmpp == null || tmpp.isEmpty())
//							yy = 0;
//						else
//							yy = tmpp.size();
						re = re + str[i] + "," + "[" + tmp.getDiscipline() + "]^" + tmp.getName() + "^" + univMap.get(tmp.getUniversityCode()) + tmp.getSpecialityTitle() + "[参评" + xx + "项]" + "%";
					}
				}
				else {
					Expert tmp = (Expert) this.query(Expert.class, str[i]);
					int xx = this.query("select pr from Expert e left join e.applicationReview pr with pr.type = '" + projectType + "' where e.isReviewer = 1 and e.id = '" + str[i] + "' and pr.year = " + defaultYear ).size();
//					List tmpp2 =  this.query("select p from GeneralReviewer p left outer join p.reviewer r where p.year = " + defaultYear + " and p.priority <= 5 and r.isReviewer = 1 and r.id = '" + str[i] + "' ");
//					List tmpp2 =  this.query("select p from GeneralReviewer p left outer join p.reviewer r where p.year = " + defaultYear + " and r.isReviewer = 1 and r.id = '" + str[i] + "' ");
//					if(tmpp2 == null || tmpp2.isEmpty())
//						yy = 0;
//					else 
//						yy = tmpp2.size();
					re = re + str[i] + "," + "[" + tmp.getDiscipline() + "]^" + tmp.getName() + "^" + univMap.get(tmp.getUniversityCode()) + tmp.getSpecialityTitle() + "[参评" + xx + "项]" + "%";
				}
			}
		}
		if(re.length() > 0)
			re = re.substring(0, re.length() - 1);
		return re;
	}
	/**
	 * 转化专家树所需的数据格式
	 */
	public List getNodesInfo(Map dataMap) {
		Date start = new Date();
		Date mid = new Date();
		List<ExpertTreeItem> expertTreeItems = (List<ExpertTreeItem>) dataMap.get("expertTreeItems");
		int totalExpert = (Integer) dataMap.get("totalExpert");
		System.out.println("查询tree数据耗时："+(mid.getTime() - start.getTime())+"ms");
		
		System.out.println("开始生成treeNodes...");
		//专家树的各级节点
		List treeNodesInfo = new ArrayList();	//根节点
		List nodesInfo4LevelOne = new ArrayList();	//一级学科节点
		List nodesInfo4LevelTwo = null;				//二级学科节点
		List nodesInfo4LevelThree = null;			//三级学科节点
		List nodesInfo4Expert = null;				//叶子节点（专家信息）
		
		//根节点
		Map treeNode = new HashMap();
		treeNode.put("id", "root");
		treeNode.put("name", "所有专家[" + totalExpert + "人]");
		treeNode.put("isParent", true);//是否父节点
		treeNode.put("iconOpen", "tool/ztree/css/img/folder_open.gif");//展开图标
		treeNode.put("iconClose", "tool/ztree/css/img/folder_closed.gif");//关闭图标
		treeNode.put("open", true);//根节点展开
		treeNode.put("children", nodesInfo4LevelOne);//添加子节点(根节点 -> 一级学科节点)
		treeNodesInfo.add(treeNode);//添加根节点
		
		if (null == expertTreeItems || expertTreeItems.isEmpty()) {//如果expertTreeItems为空，直接返回
			return treeNodesInfo;
		}

		//学科代码与学科名称的映射
		Map disMap = (Map) ActionContext.getContext().getApplication().get("disMap");
		//已选择的专家
		List<String> selectExpertIds = new ArrayList();
		String selectExpIds = (String) ActionContext.getContext().getSession().get("expertTree_selectExpertIds");
		if(null != selectExpIds && !selectExpIds.isEmpty()) {
			String[] selectExpIdArray = selectExpIds.split("[^a-zA-Z0-9]+");
			for (String selectExpId : selectExpIdArray) {
				selectExpertIds.add(selectExpId.trim());
			}
		}
		
		Map levelOneNode = null;	//一级学科节点
		Map levelTwoNode = null;	//二级学科节点
		Map levelThreeNode = null;	//三级学科节点
		Map nodeInfo = null;		//叶子节点（专家信息）
		String lastLevelOneCode = null;		//一级学科上一个学科代码
		String lastLevelTwoCode = null;		//二级学科上一个学科代码
		String lastLevelThreeCode = null;	//三级学科上一个学科代码
		Set levelOneSet = new HashSet();	//所有一级学科不同专家集合
		Set levelTwoSet = new HashSet();	//所有二级学科不同专家集合
		Set levelThreeSet = new HashSet();	//所有三级学科不同专家集合
		for (ExpertTreeItem expertTreeItem : expertTreeItems) {
			// step1：组装一级学科代码节点
			String levelOneCode = expertTreeItem.discipline.substring(0, 3);//一级学科代码
			if(!levelOneCode.equals(lastLevelOneCode)){
				nodesInfo4LevelTwo = new ArrayList();//针对每个不同的一级学科节点初始化一个二级学科节点数据的list
				levelOneSet.clear();
				levelTwoSet.clear();
				levelThreeSet.clear();
				String title = levelOneCode + getDisciplineName(levelOneCode, disMap);
				if(levelOneCode.equals("000")) {title = "其他";}
				levelOneNode = new HashMap();
				levelOneNode.put("id", levelOneCode);
				levelOneNode.put("name", title);
				levelOneNode.put("isParent", true);//是否父节点
				levelOneNode.put("iconOpen", "tool/ztree/css/img/folder_open.gif");//展开图标
				levelOneNode.put("iconClose", "tool/ztree/css/img/folder_closed.gif");//关闭图标
				levelOneNode.put("children", nodesInfo4LevelTwo);//添加子节点(一级学科节点 -> 二级学科节点)
				nodesInfo4LevelOne.add(levelOneNode);//添加一级学科节点
				lastLevelOneCode = levelOneCode;//上一个一级学科代码
			}
			if(levelOneCode.equals("000")) {//一级学科为"000"的专家放入根节点的"其他"目录下
				nodeInfo = new HashMap();
				nodeInfo.put("id", expertTreeItem.id);
				nodeInfo.put("name", expertTreeItem.name + "（" + expertTreeItem.university + "[参评"+ expertTreeItem.universityReviewerCnt +"人]，" + expertTreeItem.specialityTitle + "）[参评" + expertTreeItem.pojcnt + "项]");
				nodeInfo.put("isParent", false);//是否父节点
				nodeInfo.put("icon", "tool/ztree/css/img/zj.gif");//节点图标
				if(selectExpertIds.contains(expertTreeItem.id.substring(0, 32))){
					nodeInfo.put("checked", true);
					levelOneNode.put("checked", true);
					treeNode.put("checked", true);					
				}
				nodesInfo4LevelTwo.add(nodeInfo);//添加一级学科
				//更新一级学科节点(其他)
				levelOneSet.add(expertTreeItem.id.substring(0, 32));
				levelOneNode.put("name", "其他[" + levelOneSet.size() + "人]");
				continue;
			}
			// step2：组装二级学科代码节点
			String levelTwoCode = expertTreeItem.discipline.substring(0, 5);//二级学科代码 
			if(!(levelTwoCode.equals(lastLevelTwoCode)) ) {//二级学科节点
				nodesInfo4LevelThree = new ArrayList();//针对每个不同的二级学科节点初始化一个三级学科节点数据的list
				levelTwoSet.clear();
				levelThreeSet.clear();
				String title = levelTwoCode + getDisciplineName(levelTwoCode, disMap);
				if(levelTwoCode.substring(3, 5).equals("00")) {title = "其他";}
				levelTwoNode = new HashMap();//构建二级学科节点
				levelTwoNode.put("id", levelTwoCode);
				levelTwoNode.put("name", title);
				levelTwoNode.put("isParent", true);//是否父节点
				levelTwoNode.put("iconOpen", "tool/ztree/css/img/folder_open.gif");//展开图标
				levelTwoNode.put("iconClose", "tool/ztree/css/img/folder_closed.gif");//关闭图标
				levelTwoNode.put("children", nodesInfo4LevelThree);//添加子节点(二级学科节点 -> 三级学科节点)
				nodesInfo4LevelTwo.add(levelTwoNode);//添加一级学科节点
				lastLevelTwoCode = levelTwoCode;
			}
			if(levelTwoCode.substring(3, 5).equals("00") ) {//学科代码直接为二级学科的专家放入当前二级学科的"其他"目录下
				nodeInfo = new HashMap();
				nodeInfo.put("id", expertTreeItem.id);
				nodeInfo.put("name", expertTreeItem.name + "（" + expertTreeItem.university + "[参评"+ expertTreeItem.universityReviewerCnt +"人]，" + expertTreeItem.specialityTitle + "）[参评" + expertTreeItem.pojcnt + "项]");
				nodeInfo.put("isParent", false);//是否父节点
				nodeInfo.put("icon", "tool/ztree/css/img/zj.gif");
				if(selectExpertIds.contains(expertTreeItem.id.substring(0, 32))){
					nodeInfo.put("checked", true);
					levelTwoNode.put("checked", true);
					levelOneNode.put("checked", true);
					treeNode.put("checked", true);					
				}
				nodesInfo4LevelThree.add(nodeInfo);//添加二级学科
				//更新一级学科节点
				levelOneSet.add(expertTreeItem.id.substring(0, 32));
				levelOneNode.put("name", lastLevelOneCode + getDisciplineName(lastLevelOneCode, disMap) + "[" + levelOneSet.size() + "人]");
				//更新二级学科节点(其他)
				levelTwoSet.add(expertTreeItem.id.substring(0, 32));
				levelTwoNode.put("name", "其他[" + levelTwoSet.size() + "人]");
				continue;
			}
			// step3：组装三级学科代码节点
			String levelThreeCode = expertTreeItem.discipline.substring(0, 7);//三级学科代码 
			if( !(levelThreeCode.equals(lastLevelThreeCode)) ) {//三级学科节点
				nodesInfo4Expert = new ArrayList();//针对每个不同的三级学科节点初始化一个专家节点数据的list
				levelThreeSet.clear();
				String title = levelThreeCode + getDisciplineName(levelThreeCode, disMap);
				if(levelThreeCode.substring(5, 7).equals("00")) {title = "其他";}
				levelThreeNode = new HashMap();//构建二级学科节点
				levelThreeNode.put("id", levelThreeCode);
				levelThreeNode.put("name", title);
				levelThreeNode.put("isParent", true);//是否父节点
				levelThreeNode.put("iconOpen", "tool/ztree/css/img/folder_open.gif");//展开图标
				levelThreeNode.put("iconClose", "tool/ztree/css/img/folder_closed.gif");//关闭图标
				levelThreeNode.put("children", nodesInfo4Expert);//添加子节点(三级学科节点 -> 专家信息节点)
				nodesInfo4LevelThree.add(levelThreeNode);//添加三级学科节点
				lastLevelThreeCode = levelThreeCode;
			}
			if(levelThreeCode.substring(5, 7).equals("00") ) {//学科代码直接为三级学科的专家放入当前三级学科的"其他"目录下
				nodeInfo = new HashMap();
				nodeInfo.put("id", expertTreeItem.id);
				nodeInfo.put("name", expertTreeItem.name + "（" + expertTreeItem.university + "[参评"+ expertTreeItem.universityReviewerCnt +"人]，" + expertTreeItem.specialityTitle + "）[参评" + expertTreeItem.pojcnt + "项]");
				nodeInfo.put("isParent", false);//是否父节点
				nodeInfo.put("icon", "tool/ztree/css/img/zj.gif");
				if(selectExpertIds.contains(expertTreeItem.id.substring(0, 32))){
					nodeInfo.put("checked", true);
					levelThreeNode.put("checked", true);
					levelTwoNode.put("checked", true);
					levelOneNode.put("checked", true);
					treeNode.put("checked", true);
				}
				nodesInfo4Expert.add(nodeInfo);//添加三级学科
				//更新一级学科节点
				levelOneSet.add(expertTreeItem.id.substring(0, 32));
				levelOneNode.put("name", lastLevelOneCode + getDisciplineName(lastLevelOneCode, disMap) + "[" + levelOneSet.size() + "人]");
				//更新二级学科节点
				levelTwoSet.add(expertTreeItem.id.substring(0, 32));
				levelTwoNode.put("name", lastLevelTwoCode + getDisciplineName(lastLevelTwoCode, disMap) + "[" + levelTwoSet.size() + "人]");
				//更新三级学科节点(其他)
				levelThreeSet.add(expertTreeItem.id.substring(0, 32));
				levelThreeNode.put("name", "其他" + "[" + levelThreeSet.size() + "人]");
				continue;
			}
			// step4：处理不属于"其他"目录的专家信息
			nodeInfo = new HashMap();
			nodeInfo.put("id", expertTreeItem.id);
			nodeInfo.put("name", expertTreeItem.name + "（" + expertTreeItem.university + "[参评"+ expertTreeItem.universityReviewerCnt +"人]，" + expertTreeItem.specialityTitle + "）[参评" + expertTreeItem.pojcnt + "项]");
			nodeInfo.put("isParent", false);//是否父节点
			nodeInfo.put("icon", "tool/ztree/css/img/zj.gif");//是否父节点
			if(selectExpertIds.contains(expertTreeItem.id.substring(0, 32))){
				nodeInfo.put("checked", true);
				levelThreeNode.put("checked", true);
				levelTwoNode.put("checked", true);
				levelOneNode.put("checked", true);
				treeNode.put("checked", true);				
			}
			nodesInfo4Expert.add(nodeInfo);//添加专家信息
			//更新一级学科节点
			levelOneSet.add(expertTreeItem.id.substring(0, 32));
			levelOneNode.put("name", lastLevelOneCode + getDisciplineName(lastLevelOneCode, disMap) + "[" + levelOneSet.size() + "人]");
			//更新二级学科节点
			levelTwoSet.add(expertTreeItem.id.substring(0, 32));
			levelTwoNode.put("name", lastLevelTwoCode + getDisciplineName(lastLevelTwoCode, disMap) + "[" + levelTwoSet.size() + "人]");
			//更新三级学科节点
			levelThreeSet.add(expertTreeItem.id.substring(0, 32));
			levelThreeNode.put("name", lastLevelThreeCode + getDisciplineName(lastLevelThreeCode, disMap) + "[" + levelThreeSet.size() + "人]");
		}
		System.out.println("结束生成treeNodes...");
		System.out.println("生成treeNodes耗时："+(new Date().getTime() - mid.getTime())+"ms");
		return treeNodesInfo;
	}

//	public String unionProjectRev(String ids) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	public String unionProjectDis(String ids) {
//		// TODO Auto-generated method stub
//		return null;
//	}

	public String getDisciplineName(String code, Map map) {
		return (map.get(code) != null) ? (String) map.get(code) : "未知"; 
	}

	public boolean checkDisciplineLegal(String[] discipline1Strings, String disciplines) {
		if(disciplines != null && !disciplines.isEmpty() && discipline1Strings != null) {
			String[] disStr = disciplines.split("\\D+");
			for(int i = 0; i < disStr.length; i++) {
				for(int j = 0; j < discipline1Strings.length; j++) {
					if(disStr[i].startsWith(discipline1Strings[j])) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	/**
	 * 获取评审专家的各类评审项目统计信息<br>
	 * @param year
	 * @return
	 * 返回 projectType --> Map（专家id --> cnt）
	 */
	public Map getReviewersProjectsInfo(Integer year) {
		// projectType --> Map
		Map stasticMap = new HashMap<String, Map>();
		String HQL4ReviewerCnt = "select pr.reviewer.id, count(pr.project.id) from ProjectApplicationReview pr where pr.type = :type and pr.year = :defaultYear group by pr.reviewer.id ";
		//评审者id --> projectIdSet
		Map reviewer2GeneralCnt = new HashMap<String, String>();
		Map reviewer2instpCnt = new HashMap<String, String>();
	
		Map paraMap = new HashMap();
		paraMap.put("defaultYear", year);
		paraMap.put("type", "general");
		List<Object[]> resultGeneral = this.query(HQL4ReviewerCnt, paraMap);
		paraMap.put("type", "instp");
		List<Object[]> resultInstp = this.query(HQL4ReviewerCnt, paraMap);
		Set reviewerIDSet = new HashSet<String>();
		for (Object o : resultGeneral) {
			Object[] object = (Object[]) o;
			String id = (String) object[0];
			String cnt = object[1].toString();
			reviewer2GeneralCnt.put(id, cnt);
		}
		for (Object o : resultInstp) {
			Object[] object = (Object[]) o;
			String id = (String) object[0];
			String cnt = object[1].toString();
			reviewer2instpCnt.put(id, cnt);
		}
		stasticMap.put("general", reviewer2GeneralCnt);
		stasticMap.put("instp", reviewer2instpCnt);
		return stasticMap;
	}
	
	/**
	 * 获取当前年高校对应的评审专家的跨类项目统计信息<br>
	 * @param year
	 * @return
	 * 返回 projectType --> Map（高校code --> cnt）
	 */
	public Map getUnivReviewersProjectsInfo(Integer year) {
		// projectType --> Map
		Map stasticMap = new HashMap<String, Map>();
		String HQL4UnivReviewerCnt = "select u.code, count(distinct pr.reviewer.id) from Expert e, University u, ProjectApplicationReview pr where u.code = e.universityCode and e.id = pr.reviewer.id and pr.year = :defaultYear and pr.type = :projectType group by u.code";
		//评审者id --> projectIdSet
		Map univRevi2GeneralCnt = new HashMap<String, String>();
		Map univRevi2instpCnt = new HashMap<String, String>();
		Map paraMap = new HashMap();
		paraMap.put("defaultYear", year);
		paraMap.put("projectType", "general");
		List<Object[]> resultGeneral = this.query(HQL4UnivReviewerCnt, paraMap);
		paraMap.put("projectType", "instp");
		List<Object[]> resultInstp = this.query(HQL4UnivReviewerCnt, paraMap);
		Set reviewerIDSet = new HashSet<String>();
		for (Object o : resultGeneral) {
			Object[] object = (Object[]) o;
			String id = (String) object[0];
			String cnt = object[1].toString();
			univRevi2GeneralCnt.put(id, cnt);
		}
		for (Object o : resultInstp) {
			Object[] object = (Object[]) o;
			String id = (String) object[0];
			String cnt = object[1].toString();
			univRevi2instpCnt.put(id, cnt);
		}
		stasticMap.put("general", univRevi2GeneralCnt);
		stasticMap.put("instp", univRevi2instpCnt);
		return stasticMap;
	}
	/**
	 * 群删项目
	 * 同时要删除项目相关的其他表信息：
	 * @param projectids 待删项目Id
	 * @param clazz 项目类型
	 */
	@Transactional
	public void deleteProjects(List<String> projectids, Class clazz) {
		if (projectids != null) {
			//申请项目的删除
			if (clazz.getName().equals(ProjectApplication.class.getName())) {
				//如果是删除项目申请表数据，同时要删除相关联的：匹配、匹配更新、中间表数据的关联信息
				String[] deleteProjectIds = new String[projectids.size()];
			 	for (int i = 0; i < projectids.size(); i++) {
			 		deleteProjectIds[i] = projectids.get(i);
				}
				Map argsMap = new HashMap<String, Object>();
				argsMap.put("deleteProjectIds", deleteProjectIds);
				//删除中间表
				this.execute("delete from SmdbProjectApplication smdbPA where smdbPA.smasApplID in (:deleteProjectIds)  ", argsMap);
				//删除项目申请表相关记录, 关联删除匹配表、 删除匹配更新表相关记录
				if (projectids != null) {
					for (int i = 0; i < projectids.size(); i++) {
						this.delete(clazz, projectids.get(i));
					}
				}
			} else {
				//其他项目删除
				if (projectids != null) {
					for (int i = 0; i < projectids.size(); i++) {
						this.delete(clazz, projectids.get(i));
					}
				}
			}
		}
		
	}
	
	/**
	 * 设置项目参评状态
	 * @param projectids待操作的项目集合
	 * @param label项目状态0退评，1参评
	 * @param notReviewReason 退评原因
	 */
	public void setReview(List<String> projectids, int label, String notReviewReason) {
		try {
			if ((label == 0 || label == 1) && projectids != null) {
				ProjectApplication project;
				for (int i = 0; i < projectids.size(); i++) {
					project = (ProjectApplication) this.query(ProjectApplication.class, projectids.get(i));
					project.setIsReviewable(label);
					project.setReason(notReviewReason);
					project.setWarningReviewer(null);
					this.modify(project);
					
					if (label == 0) {// 如果是退评
						List<String> re = this.query("select pr.id from ProjectApplicationReview pr where pr.project.id = ? ", project.getId());
						if (re != null && !re.isEmpty()) {
							for (int j = 0; j < re.size(); j++) {
								ProjectApplicationReview pr = (ProjectApplicationReview) this.query(ProjectApplicationReview.class, re.get(j));
								ProjectApplicationReviewUpdate pru = new ProjectApplicationReviewUpdate(pr, 0, 1);
								this.add(pru);
								this.delete(pr);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 清除项目某年匹配信息
	 * 
	 * @param deleteAutoMatches 是否清除自动匹配对，1是，0否
	 * @param deleteManualMatches 是否清除手工匹配对，1是，0否
	 * @param year 待清除年
	 */
	public void deleteProjectMatchInfos(int deleteAutoMatches,int deleteManualMatches,int year,String projectType){
		long begin = System.currentTimeMillis();
		//1.清除匹配对信息
		try {
			if (deleteAutoMatches == 1) {
				this.execute("delete from ProjectApplicationReview p where p.type = '" + projectType + "' and p.year=? and p.isManual = 0",year);
			}
			if (deleteManualMatches == 1) {
				this.execute("delete from ProjectApplicationReview p where p.type = '" + projectType + "' and p.year=? and p.isManual = 1",year);
			}
			this.execute("delete from ProjectApplicationReviewUpdate p where p.type = '" + projectType + "' and p.year=?",year);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//2.清除项目警告信息(如果不清除匹配对即，则匹配警告信息不清空)
		if (!(deleteAutoMatches == 0 && deleteManualMatches == 0)) {
			try {
				StringBuffer hql = new StringBuffer();
				Map map = new HashMap();
				hql.append("update ProjectApplication p set p.warningReviewer = null where p.type = '" + projectType + "' and p.year =:year and p.isReviewable = 1");
				map.put("year", year);
				this.execute(hql.toString(), map);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		//执行一次匹配更新警告操作
		long end = System.currentTimeMillis();
		System.out.println("清空匹配完成，用时 ：" + (end - begin) + " ms" );
	}
	
	/**
	 * 组装学科信息
	 */
	public String getDiscipline(String discipline) {
		// map对象中用于value存学科
		String value = "";
		if (discipline != null) {
			// 拆分后的学科代码
			List<String> disids = new ArrayList<String>();;
			discipline = discipline.replaceAll("\\s+", "");
			// 临时
			String disvalue = "";
			// 学科值
			List<Object> disvas;
			// 将学科代码组装成list
			do {
				if (discipline.indexOf(";") < 0) {// 没有了
					disvalue = discipline;
					disids.add(disvalue);
					break;
				} else {
					disvalue = discipline.substring(0, discipline.indexOf(";"));
					discipline = discipline.substring(discipline.indexOf(";") + 1);
					//System.out.println(disvalue);
					//System.out.println(discipline);
					//System.out.println("");
					disids.add(disvalue);
				}
			} while (!discipline.isEmpty());// 直到为空
			// 遍历disids找到其名称并拼接为一个字符串，以逗号隔开
			if (!disids.isEmpty()) {// 如果学科不为空
				String test ="select s.code, min(s.name) from SystemOption s where s.code = '" + disids.get(0) + "'";// 测试
				for (int j = 1; j < disids.size(); j++) {
					test += " or s.code = '" + disids.get(j) + "'";
				}
				test += " group by s.code";
				//System.out.println(test);
				disvas = this.query(test);
				if (disvas != null && !disvas.isEmpty()) {
					Object[] o;
					int disSize = disvas.size();
					for (int j = 0; j < disSize-1; j++ ) {//除去最后一个
						o = (Object[]) disvas.get(j);
						value += o[0].toString() + o[1].toString() + "; ";
					}
					o = (Object[]) disvas.get(disSize-1);//最后一个，不需要分号
					value += o[0].toString() + o[1].toString();
				}else{
					value = discipline;//如果没有，则返回原数字
				}
				
			}
		}
		return value;
	}
	
	/**
	 * 初始化专家树
	 * @param projectIds 项目id，用分号空格隔开，如果不涉及项目则填null
	 */
	public void initExpertTree(String projectIds) {
		Map session = ActionContext.getContext().getSession();
		if(projectIds != null && !projectIds.isEmpty()) {
			session.remove("expertTree_universityName");//高校名称
			session.remove("expertTree_expertName");//专家姓名
			session.put("expertTree_discipline1s", unionProjectDis(projectIds));//专家树中的学科代码
			session.put("expertTree_selectExpertIds", unionProjectRev(projectIds));//专家树中的已选专家ID
			session.put("expertTree_projectIds", projectIds);//选中的项目ids
			session.put("expertTree_useProjects", 1);
		} else {
			session.remove("expertTree_discipline1s");
			session.remove("expertTree_selectExpertIds");
			session.remove("expertTree_universityName");
			session.remove("expertTree_expertName");
			session.put("expertTree_useProjects", 0);
		}
	}
	
	/**
	 * 联合项目的各个学科代码（找到各个项目共有的学科代码）
	 * @param ids 项目ids
	 */
	public String unionProjectDis(String ids) {
		String[] pojids = ids.split("[^a-zA-Z0-9]+");
		List<String> allLevelOnes = new ArrayList();//所以项目的一级学科代码
		for(int i = 0; i < pojids.length; i++) {//对每个项目进行遍历
			ProjectApplication p = (ProjectApplication)this.query(ProjectApplication.class, pojids[i]);
			String dis = p.getDiscipline();
			if(null == dis){
				continue;
			}
			String[] diss = dis.split("\\D+");//得到该项目的各个学科代码
			Set<String> levelOneSet = new HashSet<String>();
			for(int j = 0; j < diss.length; j++) {
				if(diss[j].length() < 3){
					continue;
				}
				levelOneSet.add(diss[j].substring(0, 3));
			}
			StringBuffer levelOneStr = new StringBuffer();
			for (String levelOne : levelOneSet) {
				levelOneStr.append(levelOne).append(";");
			}
			int len = levelOneStr.length();
			if(len >0){//去除最后一个分号
				levelOneStr.deleteCharAt(len-1);
			}
			allLevelOnes.add(levelOneStr.toString());
		}
		String value = "";
		if (null != allLevelOnes && allLevelOnes.size() != 0){
			value = allLevelOnes.get(0);
			for(int i = 1; i < allLevelOnes.size(); i++) {
				value = unionString(value, allLevelOnes.get(i));
			}
		}
		//System.out.println("disci " + disci);
		return value;
	}
	
	/**
	 * 连接字符串（取两个项目的学科代码的交集，用于找到相同学科类属的专家）
	 * @param str1	项目1的学科代码字符串
	 * @param str2	项目2的学科代码字符串
	 * @return	两个项目的学科代码的交集
	 */
	public String unionString(String str1, String str2) {
		String[] s1 = str1.split(";");
		String[] s2 = str2.split(";");
		String re = "";
		for(int i = 0; i < s1.length; i++) {
			for(int j = 0; j < s2.length; j++) {
				if(s1[i].equals(s2[j])) {
					re += s1[i] + ";";
					break;
				}
			}
		}
		if(re.length() > 0)
			re = re.substring(0, re.length() - 1);
		return re;
	}
	
	/**
	 * 联合项目评审专家
	 * @param ids 项目ids
	 */
	public String unionProjectRev(String ids) {
		String[] pojids = ids.split("[^a-zA-Z0-9]+");
		List<String> ei = new ArrayList();
		for(int i = 0; i < pojids.length; i++) {
			List<String> l = this.query("select p.reviewer.id from ProjectApplicationReview p where p.project.id = '" + pojids[i] + "' order by p.priority asc ");
			String re = "";
			for(int j = 0; j < l.size(); j++) {
				re += l.get(j) + ";";
			}
			if(re.length() > 0)
				re = re.substring(0, re.length() - 1);
			ei.add(re);
		}
		String exps = "";
		if(null != ei && ei.size() != 0){
			exps = ei.get(0);
			for(int i = 1; i < ei.size(); i++) {
				exps = unionString(exps, ei.get(i));
			}
		}
		//System.out.println("exps " + exps);
		return exps;
	}
	/**
	 * 项目导出
	 * @param exportAll：1，导出全部项目（参评项目+退评项目）；0，导出参评项目
	 * @param containReviewer：是否包含专家评审信息
	 * @param projectType:项目类型
	 * @return
	 */
	public InputStream exportProject(int exportAll, boolean containReviewer, String projectType) {
		ActionContext context = ActionContext.getContext();
		Map session = context.getSession();
		int year = Integer.parseInt(session.get("defaultYear").toString());	
		//高校代码 -> 高校名称
		HashMap<String, String> univMap = (HashMap<String, String>) context.getApplication().get("univCodeNameMap");
		//学科代码 -> 学科名称
		HashMap<String, String> discMap = (HashMap<String, String>) context.getApplication().get("disMap");
		HashMap<String, Expert> expMap = null;	//专家ID -> 专家实体
		HashMap<String, List<String>> irsMap = null; //项目ID -> 评审专家列表
		if (containReviewer) {
			//专家ID -> 专家实体
			expMap = new HashMap<String, Expert>();
			
			List<Expert> experts = this.query("from Expert");
			for (Expert expert : experts) {
				expMap.put(expert.getId(), expert);
			}
		
		
			//项目ID -> 评审专家列表
		try {
	
			List<ProjectApplicationReview> prList = this.query("from ProjectApplicationReview pr where pr.type = '" + projectType + "' and pr.year = ? order by pr.project.id asc, pr.priority asc", year);
			irsMap = new HashMap<String, List<String>>();//项目ID与该项目的评审专家的映射
			for (ProjectApplicationReview pr : prList) {
				if(null != pr.getPriority() && pr.getPriority() > 5){//只取前5个专家，不考虑备评
					continue;
				}
				List<String> expertIds = irsMap.get(pr.getProject().getId());
				if (null == expertIds) {
					expertIds = new ArrayList<String>();
				}
				expertIds.add(pr.getReviewer().getId());
				irsMap.put(pr.getProject().getId(), expertIds);
			}
			
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		Map parMap = new HashMap();
		parMap.put("year", year);
		parMap.put("univMap", univMap);
		parMap.put("discMap", discMap);
		parMap.put("expMap", expMap);
		parMap.put("irsMap", irsMap);
		Map resultMap = exportProjectDealWith(exportAll, containReviewer, parMap);		
		List<Object[]> dataList = (List<Object[]>) resultMap.get("dataList");		
		String header = (String) resultMap.get("header");
		String[] title = (String[]) resultMap.get("title");
		return HSSFExport.commonExportExcel(dataList, header, title);
	}

	/**
	 * 在父类中定义项目导出的数据处理，子类处理数据
	 * @return
	 */
	public Map exportProjectDealWith(int exportAll, boolean containReviewer, Map container) {
		return null;
	}
	/**
	 * 导出基地项目专家调整更新表
	 * @param year	匹配年度
	 * @param start	更新开始时间
	 * @param end	更新结束时间
	 * @param projectType 项目类型
	 */
	@Transactional
	public InputStream exportMatchUpdate(Integer year, Date start, Date end, String projectType) throws IOException {
		List<String []> v = new ArrayList();
		List<Object[]> prus = this.query(
				"select " +
				"	pru.project.id, " +
				"	pru.project.projectName, " +
				"	pru.project.number, " +
				"	pru.reviewer.name, " +
				"	university.name, " +
				"	university.code, " +
				"	pru.isAdd, " +
				"	pru.reviewer.number " +
				"from " +
				"	ProjectApplicationReviewUpdate pru, " +
				"	University university " +
				"where pru.type = '" + projectType + "' and" +
				"	university.code = pru.reviewer.universityCode and " +
				"	pru.year = ? and " +
				"	pru.matchTime >= ? and " +
				"	pru.matchTime <= ? " +
				"order by pru.project.id, pru.matchTime", year, start, end);
		HashSet<String> originReviewers = null;
		HashSet<String> updatedReviewers = null;
		HashSet<Integer> updatedReviewerNumbers = new HashSet<Integer>();
		for (int i = 0; i < prus.size(); i++) {
			String prevProjectId = (String) (i > 0 ? prus.get(i - 1)[0] : null);
			String curProjectId = (String) prus.get(i)[0];
			String nextProjectId = (String) (i < prus.size() - 1 ? prus.get(i + 1)[0] : null);
			String projectName = (String) prus.get(i)[1];
			String projectNumber = (String) prus.get(i)[2];
			String reviewerName = (String) prus.get(i)[3];
			String reviewerUnivName = (String) prus.get(i)[4];
			String reviewerUnivCode = (String) prus.get(i)[5];
			Integer isAdd = (Integer) prus.get(i)[6];
			Integer reviewerNumber = (Integer) prus.get(i)[7];
			
			String curReviewer = reviewerNumber + "/" + reviewerName + "（" + reviewerUnivCode + "/" + reviewerUnivName + "）";
//			System.out.println(curProjectId + " " + projectName + " " + curReviewer + " - " + isAdd);
			
			if (prevProjectId == null || !curProjectId.equals(prevProjectId)) {
				originReviewers = new HashSet<String>();
				updatedReviewers = new HashSet<String>();
			}
			if (isAdd == 1) {
				updatedReviewers.add(curReviewer);
				updatedReviewerNumbers.add(reviewerNumber);
			} else if (updatedReviewers.contains(curReviewer)) {
				updatedReviewers.remove(curReviewer);
				updatedReviewerNumbers.remove(reviewerNumber);
			} else {
				originReviewers.add(curReviewer);
			}
			if (nextProjectId == null || !curProjectId.equals(nextProjectId)) {
				String[] row = new String[4];
				row[0] = projectNumber;
				row[1] = projectName;
				
				String originReviewersInfo = "";
				for (String reviewer : originReviewers) {
					if (updatedReviewers.contains(reviewer)) {
						continue;
					}
					if (!originReviewersInfo.isEmpty()) {
						originReviewersInfo += "; ";
					}
					originReviewersInfo += reviewer;
				}
				row[2] = originReviewersInfo;
				
				String updatedReviewersInfo = "";
				for (String reviewer : updatedReviewers) {
					if (originReviewers.contains(reviewer)) {
						continue;
					}
					if (!updatedReviewersInfo.isEmpty()) {
						updatedReviewersInfo += "; ";
					}
					updatedReviewersInfo += reviewer;
				}
				row[3] = updatedReviewersInfo;
				
				if (!row[2].isEmpty() || !row[3].isEmpty()) {
					v.add(row);
				}
			}
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String[] tableHeader = new String[]{
			"项目编号",
			"项目名称",
			"删除评审专家",
			"新增评审专家"
		};
		
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet1 = wb.createSheet();
		wb.setSheetName(0, "项目专家匹配更新表");
		sheet1.setDefaultRowHeightInPoints(13);
		sheet1.setDefaultColumnWidth(18);
		//设置页脚
		HSSFFooter footer = sheet1.getFooter();
		footer.setRight("Page " + HSSFFooter.page() + " of " + HSSFFooter.numPages());
		//设置样式 表头
		HSSFCellStyle style1 = wb.createCellStyle();
		style1.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		HSSFFont font1 = wb.createFont();
		font1.setFontHeightInPoints((short) 10);
		font1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		style1.setFont(font1);
		//设置样式 正式数据
		HSSFCellStyle style2 = wb.createCellStyle(); 
		style2.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		style2.setWrapText(false);
		
		HSSFCell tmpCell;
		HSSFRow rowTime1 = sheet1.createRow(0);

		tmpCell = rowTime1.createCell(0);
		tmpCell.setCellStyle(style1);
		tmpCell.setCellValue("起始时间");
		
		tmpCell = rowTime1.createCell(1);
		tmpCell.setCellStyle(style1);
		tmpCell.setCellValue("截止时间");
		
		HSSFRow rowTime2 = sheet1.createRow(1);

		tmpCell = rowTime2.createCell(0);
		tmpCell.setCellStyle(style2);
		tmpCell.setCellValue(sdf.format(start));
		
		tmpCell = rowTime2.createCell(1);
		tmpCell.setCellStyle(style2);
		tmpCell.setCellValue(sdf.format(end));

		//设置表头
		HSSFRow row1 = sheet1.createRow(3);
		row1.setHeightInPoints(13);
		for(int i = 0; i < tableHeader.length; i++){
			HSSFCell cell1 = row1.createCell(i);
			cell1.setCellValue(tableHeader[i]);
			cell1.setCellStyle(style1);
		}
		//填充数据
		for(int j = 0; j < v.size(); j++){
			HSSFRow row2 = sheet1.createRow(j + 4);
			for(int k = 0; k < v.get(j).length; k++){
				HSSFCell cell = row2.createCell(k);
				cell.setCellValue(v.get(j)[k]);
				cell.setCellStyle(style2);
			}
		}
		
		//新增一个标签页“专家更新表”，包括“匹配更新表”标签页中“新增评审专家”的所有专家清单，具体内容按导出专家表的格式进行组织。
		List<Object[]> updatedReviewersInfoList = new ArrayList<Object[]>();
		List<Object[]> tmpUpdateReviewersInfoList = new ArrayList<Object[]>();
		if (updatedReviewerNumbers.size() != 0) {
			tmpUpdateReviewersInfoList = this.query("select expert.number, expert.name, expert.universityCode, expert.mobilePhone, expert.email, expert.discipline, expert.id from Expert expert");
			for (Object[] objects : tmpUpdateReviewersInfoList) {
				if (updatedReviewerNumbers.contains((Integer) objects[0])) {
					updatedReviewersInfoList.add(objects);
				}
			}
		}
		String[] tableHeader1 = new String[]{
			"专家代码",
			"专家姓名",
			"单位代码",
			"单位名称",
			"手机",
			"邮箱",
			"一级学科代码",
			"一级学科名称",
			"二级学科代码",
			"二级学科名称",
			"三级学科代码",
			"三级学科名称"
		};
		sheet1 = wb.createSheet();
		wb.setSheetName(1, "专家更新表");
		sheet1.setDefaultRowHeightInPoints(13);
		sheet1.setDefaultColumnWidth(18);
		//设置页脚
		footer = sheet1.getFooter();
		footer.setRight("Page " + HSSFFooter.page() + " of " + HSSFFooter.numPages());
		//设置样式 表头
		style1 = wb.createCellStyle();
		style1.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		font1 = wb.createFont();
		font1.setFontHeightInPoints((short) 10);
		font1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		style1.setFont(font1);
		//设置样式 正式数据
		style2 = wb.createCellStyle(); 
		style2.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		style2.setWrapText(false);
		
		//设置表头
		row1 = sheet1.createRow(0);
		row1.setHeightInPoints(13);
		for(int i = 0; i < tableHeader1.length; i++){
			HSSFCell cell1 = row1.createCell(i);
			cell1.setCellValue(tableHeader1[i]);
			cell1.setCellStyle(style1);
		}
		Map application = ActionContext.getContext().getApplication();
		HashMap<String, String> univMap = (HashMap<String, String>) application.get("univCodeNameMap");
		HashMap<String, String> discMap = (HashMap<String, String>) application.get("disMap");
		List<String []> z = new ArrayList();
		for (Object[] object : updatedReviewersInfoList) {
			List eList = new ArrayList<String>();
			eList.add(object[0].toString());//专家代码
			eList.add(object[1].toString());//专家姓名
			eList.add(object[2].toString());//高校代码
			eList.add(univMap.get(object[2].toString()));//高校名称
			if(object[3] == null) {
				eList.add("");
			} else {
				String mobilePhone = (String) object[3].toString();//手机
				if(mobilePhone.indexOf(";") == -1) {
					eList.add(mobilePhone);
				} else {
					eList.add(mobilePhone.substring(0, mobilePhone.indexOf(";")));					
				}
			}
			if(object[4] == null) {
				eList.add("");
			} else {
				String email = (String) object[4].toString();//邮箱
				if(email.indexOf(";") == -1) {
					eList.add(email);
				} else {
					eList.add(email.substring(0, email.indexOf(";")));					
				}
			}
			String discipline;//学科代码
			if (object[5] == null) {
				discipline = "";
			} else {
				discipline = (String) object[5].toString();
			}
			String disc[] = discipline.split("; ");
			Set<String> discSet = new HashSet<String>();
			Set<String> tmpSet = new HashSet<String>();
			for (String d : disc) {
				discSet.add(d);
			}
			String d3Code="", d3Name="", d2Code="", d2Name="", d1Code="", d1Name="";
			for(Iterator i = discSet.iterator(); i.hasNext();){ 
				String st = (String)i.next();
				if (st.length() == 7)
					tmpSet.add(st.substring(0, 5));
				if (st.length() >= 5)
					tmpSet.add(st.substring(0, 3));
			}
			discSet.addAll(tmpSet);
			for(Iterator i = discSet.iterator(); i.hasNext();){ 
				String st = (String)i.next();
				if (st.length() == 7){
					d3Code += (d3Code.isEmpty()?"":"; ") + st;
					d3Name += (d3Name.isEmpty()?"":"、") + discMap.get(st);
				} else if (st.length() == 5){
					d2Code += (d2Code.isEmpty()?"":"; ") + st;
					d2Name += (d2Name.isEmpty()?"":"、") + discMap.get(st);
				} else if (st.length() == 3){
					d1Code += (d1Code.isEmpty()?"":"; ") + st;
					d1Name += (d1Name.isEmpty()?"":"、") + discMap.get(st);
				}
			}
			eList.add(d1Code);
			eList.add(d1Name);
			eList.add(d2Code);
			eList.add(d2Name);
			eList.add(d3Code);
			eList.add(d3Name);
			
			String obj[] = new String[13];
			for (int i = 0; i < eList.size(); i++){
				if (eList.get(i) == null){
					obj[i] = "";
				}else {
					obj[i] = eList.get(i).toString();
				}
			}
			z.add(obj);
		}
		//填充数据
		for(int j = 0; j < z.size(); j++){
			HSSFRow row2 = sheet1.createRow(j + 1);
			for(int k = 0; k < z.get(j).length; k++){
				HSSFCell cell = row2.createCell(k);
				cell.setCellValue(z.get(j)[k]);
				cell.setCellStyle(style2);
			}
		}
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			wb.write(bos);
			bos.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		byte[] content = bos.toByteArray();
		ByteArrayInputStream bis = null;
		try{
			bis = new ByteArrayInputStream(content);
			bis.close();
		} catch (Exception e){
			e.printStackTrace();
		}
		return bis;		
	}
	
	/**
	 * 项目匹配更新子类业务直接调用
	 * （公共业务方法）
	 * @param projectType
	 * @param projectIds
	 * @param year
	 */
	public void projectUpdateWarningReviewer(String projectType, List<String> projectIds, Integer year) {
		long begin = System.currentTimeMillis();
		System.out.println(">>>开始" + projectType + "项目的匹配警告更新 ···");
		
		MatcherWarningUpdater updater = (MatcherWarningUpdater) SpringBean.getBean("matcherWarningUpdater", ApplicationContainer.sc);
		updater.update(projectType, projectIds, year);
		
		long end = System.currentTimeMillis();
		System.out.println(">>>完成" + projectType + "项目的匹配警告更新!用时 ：" + (end - begin) + " ms");
	}
	
	/**
	 * 自动匹配
	 * （公共业务方法）
	 * Action 调用 公共Service
	 * @param year
	 * @param expertIds
	 * @param projectids
	 * @param rejectExpertIds
	 * @throws Exception
	 */
	public void matchExpert(String projectType, 
			Integer year, 
			List<String> expertIds, 
			List<String> projectids, 
			List<String> rejectExpertIds) throws Exception {
		System.out.println("》》》开始" + projectType + "项目的专家匹配 ···");
		long begin = System.currentTimeMillis();
		if (projectType.equals("general")) {
			GeneralReviewerMatcher matcher = (GeneralReviewerMatcher) SpringBean.getBean("generalReviewerMatcher", ApplicationContainer.sc);
			matcher.matchExpert(year, expertIds, projectids, rejectExpertIds);
		} else if (projectType.equals("instp")) {
			InstpReviewerMatcher matcher = (InstpReviewerMatcher) SpringBean.getBean("instpReviewerMatcher", ApplicationContainer.sc);
			matcher.matchExpert(year, expertIds, projectids, rejectExpertIds);
		} 
		//扩展其他项目类型的自动匹配器处理
		long end = System.currentTimeMillis();
		System.out.println("完成" + projectType + "项目的专家匹配 ！用时 ：" + (end - begin) + " ms");
	}
	
	/**
	 * 取得专家职称（c_speciality_title）相关的集合信息:
	 * （公共方法）<br>
	 * aboveSubSeniorTitles：副高级职称以上专家<br>
	 * seniorTitles：正高级、高级职称专家<br>
	 * specialityTitle2RatingMap:职称对应的职称等级<br>
	 * 副高级:副高级的职称集合
	 * @return
	 */
	public Map selectSpecialityTitleInfo() {
		Map paraMap = new HashMap();
		/**
		 * 1.取出系统选项表中的c_name，c_code，c_description字段，组装‘c_code/c_name’与专家表中的c_speciality_title做对比
		 */
		List<Object[]> systemOptionList = (List<Object[]>) ApplicationContainer.sc.getAttribute("specialityTitleRank");
		HashMap<String, String> codeName2Description = new HashMap<String, String>();
		for (Object[] objects : systemOptionList) {
			String code = objects[0].toString();
			String name = objects[1].toString();
			String description = objects[2].toString();
			codeName2Description.put(code + "/" + name,description);
		}
		List<Object> expertSeciality = this.query("select distinct expert.specialityTitle from Expert expert where expert.specialityTitle is not null");
		List<String> seniorTitles = new ArrayList<String>();//正高级、高级职称专家
		List<String> aboveSubSeniorTitles = new ArrayList<String>();//副高级职称以上专家
		List<String> 副高级 = new ArrayList<String>();
		Map<String, Integer> specialityTitle2RatingMap = new HashMap<String, Integer>(); 
		for (Object object : expertSeciality) {
			String specialityTitle = object.toString();
			if (codeName2Description.get(specialityTitle) != null) {
				if (codeName2Description.get(specialityTitle).equals("正高级") || codeName2Description.get(specialityTitle).equals("高级")) {
					seniorTitles.add(specialityTitle);
					aboveSubSeniorTitles.add(specialityTitle);
				}
				if (codeName2Description.get(specialityTitle).equals("副高级")) {
					aboveSubSeniorTitles.add(specialityTitle);
					副高级.add(specialityTitle);
				}
				specialityTitle2RatingMap.put(specialityTitle,specialityTitle2Rating(codeName2Description.get(specialityTitle)));
			}else{
				System.out.println("没找到该专业职称对应的级别" + specialityTitle);
			}
		}
		paraMap.put("aboveSubSeniorTitles", aboveSubSeniorTitles);	//副高级职称以上专家
		paraMap.put("seniorTitles", seniorTitles);	//正高级、高级职称专家
		paraMap.put("specialityTitle2RatingMap", specialityTitle2RatingMap);
		paraMap.put("副高级", 副高级);
		
//		List<Object> selectedUnivCodeslList = this.query("select distinct u.code from GeneralReviewer gr ,Expert e,University u where u.code = e.universityCode and e.id = gr.reviewer.id and gr.year = '2014'");
//		List<String> selectedUnivCodes  = new ArrayList<String>();
//		for (Object object : selectedUnivCodeslList) {
//			selectedUnivCodes.add(object.toString());
//		}
//		paraMap.put("selectedUnivCodes", selectedUnivCodes);
		return paraMap;
	}
	
	/**
	 * 根据学科代码(code)获取学科名称(name) 
	 * @param code	学科代码
	 * @param map	学科代码与学科名称的映射（map）
	 * @return
	 */
	public String getDiscipline1Name(String code, Map map) {
		return (map.get(code) != null) ? (String) map.get(code) : "未知"; 
	}
	
	public String getExpDetail(String id) {
		Expert tmp = (Expert) this.query(Expert.class, id);
		String str = tmp.getName() + "%" + tmp.getGender() + "%" + tmp.getDepartment() + "%" + tmp.getEmail() + "%" + tmp.getUniversityCode();
		return str;
	}
	
	/**
	 * 根据项目ID找到已分配专家的名称，高校
	 * @param projectid项目ID
	 * @return 相关信息
	 */
	public List<ExpertLink> getExpert(String projectid) {
		List<ExpertLink> back = new ArrayList<ExpertLink>();// 用于返回
//		List<Object> re = this.query("select e.id, e.name, u.name, e.specialityTitle, e.universityCode, e.isReviewer from Expert e, University u, GeneralReviewer pr where pr.project.id = '" + projectid + "' and pr.reviewer.id = e.id and e.universityCode = u.code order by pr.priority asc");
		List<Object> re = this.query("select e.id, e.name, u.name, e.specialityTitle, e.universityCode, e.isReviewer from Expert e, University u, ProjectApplicationReview pr where pr.project.id = ? and pr.reviewer.id = e.id and e.universityCode = u.code order by pr.priority asc ", projectid);
		if (re.size() > 0) {
			Object[] o;
			for (int i = 0; i < re.size(); i++) {
				o = (Object[]) re.get(i);
				ExpertLink e = new ExpertLink();
				e.setId(o[0].toString());
				e.setName(o[1].toString());
				e.setUname(o[2].toString());
				e.setTitle(o[3] != null ? o[3].toString() : "");
				e.setIsInner((Integer.parseInt(o[4].toString()) < 10000 ? 1 : 0));
				e.setIsReviewer(Integer.parseInt(o[5].toString()));
				back.add(e);
			}
		}
		return back;
	}
	
	/**
	 * 将项目的学科代码拆分为list
	 * @param discipline学科字符串
	 * @return 拆分后的学科代码list
	 */
	public List<String> getProjectDiscipline(String discipline) {
		return Arrays.asList(discipline.replaceAll("\\s+", "").split(";"));
//		List<String> disids = new ArrayList<String>();;
//		if (discipline != null) {
//			// 拆分后的学科代码
//			discipline = discipline.replaceAll("\\s+", "");
//			// 临时
//			// 将学科代码组装成list
//			do {
//				if (discipline.indexOf(";") < 0) {// 没有了
//					disids.add(discipline);
//					break;
//				} else {
//					disids.add(discipline.substring(0, discipline.indexOf(";")));
//					discipline = discipline.substring(discipline.indexOf(";") + 1);
//				}
//			} while (!discipline.isEmpty());// 直到为空
//		}
//		return disids;
	}
	
	/**
	 * 根据学科名称搜索包含该名称的学科代码
	 * @param disciplineName学科名称
	 * @return 包含该关键字的学科代码集合
	 */
	public List<Object> getDisciplineCode(String disciplineName) {
		List<Object> disname = null;
		if (disciplineName != null) {
			StringBuffer hql = new StringBuffer();
			Map map = new HashMap();
			hql.append("select min(s.code) from SystemOption s where s.name like :disciplineName group by s.code");
			map.put("disciplineName", "%" + disciplineName + "%");
			disname = this.query(hql.toString(), map);
		}
		return disname;
	}
	
	/**
	 * 组装学科信息
	 */
	public String getDisciplineInfo(Map<String, String> disname2discode, String discipline){
		// map对象中用于value存学科
		String value = "";
		if (discipline != null) {
			// 拆分后的学科代码
			List<String> disCodes = new ArrayList<String>();;
			discipline = discipline.replaceAll("\\s+", "");
			// 临时
			String disvalue = "";
			// 将学科代码组装成list
			do {
				if (discipline.indexOf(";") < 0) {// 没有了
					disvalue = discipline;
					disCodes.add(disvalue);
					break;
				} else {
					disvalue = discipline.substring(0, discipline.indexOf(";"));
					discipline = discipline.substring(discipline.indexOf(";") + 1);
					disCodes.add(disvalue);
				}
			} while (!discipline.isEmpty());// 直到为空
			// 遍历disCodes找到其名称并拼接为一个字符串，以逗号隔开
			if (!disCodes.isEmpty()) {// 如果学科不为空
				int size = disCodes.size();
				String disname = "";
				for (int j = 0; j < size-1; j++) {
					disname = disname2discode.get(disCodes.get(j));
					if(null != disname && !disname.isEmpty()){
						value += disCodes.get(j) + disname + "; ";
					}
				}
				disname = disname2discode.get(disCodes.get(size-1));
				if(null != disname && !disname.isEmpty()){
					value +=  disCodes.get(size-1) + disname;//加上最后一把学科
				}
			}
		}
		return value;
	}
	
	public int saveTemp(String c1, String c2) {
		ActionContext context = ActionContext.getContext();
		Map session = context.getSession();
		session.put("uname", c1);
		session.put("ename", c2);
		return 1;
	}
	/**
	 * 获取所有人申请评审信息
	 * @param entityId
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List getAllAppReviewList(String entityId){
		if(entityId == null){
			return new ArrayList();
		}
		StringBuffer hql = new StringBuffer();
		hql.append("select appRev.id, appRev.priority, appRev.reviewerName, appRev.score, appRev.grade, appRev.opinion from ProjectApplicationReview appRev left outer join appRev.project app where app.id=? order by appRev.priority asc");
		return this.query(hql.toString(), entityId);
	}
	
	/**
	 * 根据项目申请id获取项目id
	 * @param appId 项目申请id
	 * @return 项目id
	 */
	@SuppressWarnings("unchecked")
	public String getGrantedIdByAppId(String appId){
		if(appId == null){
			return null;
		}
		String hql = "select gra.id from ProjectGranted gra where gra.application.id=?";
		List<String> list = this.query(hql, appId);		
		if(!list.isEmpty()){
			return list.get(0);
		}else{
			return null;
		}
	}
	
	/**
	 * 根据项目立项id获取项目申请id
	 * @param graId 项目立项id
	 * @return 项目申请id
	 */
	@SuppressWarnings("unchecked")
	public String getApplicationIdByGrantedId(String graId){
		if(graId == null){
			return "none";
		}
		ProjectGranted projectGranted = (ProjectGranted)this.query(ProjectGranted.class, graId);
		String hql = "select gra.application.id from ProjectGranted gra where gra.id=? ";
		List<String> list = this.query(hql, graId);
		if(!list.isEmpty()){
			return list.get(0);
		}else{
			return "none";
		}
	}
	/**
	 * 根据项目立项id获取项目
	 * @param graId 项目立项id
	 * @return 项目
	 */
	@SuppressWarnings("rawtypes")
	public ProjectGranted getGrantedFetchDetailByGrantedId(String graId){
		if(graId == null){
			return null;
		}
		String hql = "from ProjectGranted gra where gra.id =?";
		List list = this.query(hql, graId);
		if(!list.isEmpty()){
			return (ProjectGranted)list.get(0);
		}else{
			return null;
		}
	}
	/**
	 * 根据项目立项id获取通过结项
	 * @param graId 项目立项id
	 * @return 通过结项
	 */
	@SuppressWarnings("rawtypes")
	public List getPassEndinspectionByGrantedId(String graId){
		if(graId == null){
			return new ArrayList();
		}
		String hql = "select endi from ProjectEndinspection endi where endi.granted.id=? and endi.finalAuditResultEnd=2";
		List list = this.query(hql, graId);
		return list;
	}
	/**
	 * 根据项目立项id获取未审结项
	 * @param graId 项目立项id
	 * @return 未审结项
	 */
	@SuppressWarnings("rawtypes")
	public List getPendingEndinspectionByGrantedId(String graId){
		if(graId == null){
			return new ArrayList();
		}
		String hql = "select endi from ProjectEndinspection endi where endi.granted.id=? and endi.finalAuditResultEnd=0";
		List list = this.query(hql, graId);
		return list;
	}
	/**
	 * 根据项目立项id获取未审变更
	 * @param graId 项目立项id
	 * @return 未审变更
	 */
	@SuppressWarnings("rawtypes")
	public List getPendingVariationByGrantedId(String graId){
		if(graId == null){
			return new ArrayList();
		}
		String hql = "select vari from ProjectVariation vari where vari.granted.id=? and vari.finalAuditResult=0";
		List list = this.query(hql, graId);
		return list;
	}
	/**
	 * 根据项目立项id获取所有结项
	 * @param graId 项目立项id
	 * @return 所有结项
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<ProjectEndinspection> getAllEndinspectionByGrantedId(String graId) {
		if(graId == null){
			return new ArrayList();
		}
		StringBuffer hql = new StringBuffer("select endi from ProjectEndinspection endi where endi.granted.id = ? order by endi.applicantSubmitDate desc");
		List<ProjectEndinspection> list = this.query(hql.toString(), graId);
		return list;
	}
	/**
	 * 根据项目立项id获取当前结项
	 * @param graId 项目立项id
	 * @return 当前结项
	 */
	@SuppressWarnings("unchecked")
	public ProjectEndinspection getCurrentEndinspectionByGrantedId(String graId){
		if(graId == null){
			return null;
		}
		String hql = "select endi from ProjectEndinspection endi where endi.granted.id=? order by endi.applicantSubmitDate desc";
		List<ProjectEndinspection> list = this.query(hql, graId);
		if(!list.isEmpty()){
			return list.get(0);
		}
		return null;
	}
	/**
	 * 所有人结项鉴定
	 * @param endId 结项id
	 * @return 所有人结项鉴定
	 */
	@SuppressWarnings("rawtypes")
	public List getAllEndReviewList(String endId){
		if(endId == null){
			return new ArrayList();
		}
		StringBuffer hql = new StringBuffer();
		hql.append("select endRev.id, endRev.reviewerSn, endRev.reviewerName, endRev.score, endRev.grade from ProjectEndinspectionReview endRev left outer join endRev.endinspection endi where endi.id=? order by endRev.reviewerSn asc");
		return this.query(hql.toString(), endId);
	}
	/**
	 * 根据项目结项id获取结项经费
	 * @param endIds 项目结项id
	 * @return 结项经费
	 */
	@SuppressWarnings("unchecked")
	public List<Map> getProjectFeeEndByEndId(String grantedId, List<String> endIds){
		List<Map> projectFees = new ArrayList<Map>();
		ProjectGranted projectGranted = (ProjectGranted)this.query(ProjectGranted.class, grantedId);
		
		for(String endId : endIds) {
			Map feeMap = new HashMap();
			Double approveFee = 0.0;
			if (projectGranted.getApproveFee() != null) {
				approveFee = projectGranted.getApproveFee();
			}
			feeMap.put("approveFee", approveFee);
//			feeMap.put("fundedFee", this.getFundedFeeByGrantedId(grantedId));
//			feeMap.put("toFundFee", DoubleTool.sub(approveFee,this.getFundedFeeByGrantedId(grantedId)));
			ProjectEndinspection projectEndinspection = (ProjectEndinspection) this.query(ProjectEndinspection.class , endId);
			if (projectEndinspection.getProjectFee() != null) {
				ProjectFee projectFee = (ProjectFee) this.query(ProjectFee.class, projectEndinspection.getProjectFee().getId());
				feeMap.put("feeFlag", 1);
				feeMap.put("bookFee", projectFee.getBookFee());
				feeMap.put("bookNote", projectFee.getBookNote());
				feeMap.put("dataFee", projectFee.getDataFee());
				feeMap.put("dataNote", projectFee.getDataNote());
				feeMap.put("travelFee", projectFee.getTravelFee());
				feeMap.put("travelNote", projectFee.getTravelNote());
				feeMap.put("conferenceFee", projectFee.getConferenceFee());
				feeMap.put("conferenceNote", projectFee.getConferenceNote());
				feeMap.put("internationalFee", projectFee.getInternationalFee());
				feeMap.put("internationalNote", projectFee.getInternationalNote());
				feeMap.put("deviceFee", projectFee.getDeviceFee());
				feeMap.put("deviceNote", projectFee.getDeviceNote());
				feeMap.put("consultationFee", projectFee.getConsultationFee());
				feeMap.put("consultationNote", projectFee.getConsultationNote());
				feeMap.put("laborFee", projectFee.getLaborFee());
				feeMap.put("laborNote", projectFee.getLaborNote());
				feeMap.put("printFee", projectFee.getPrintFee());
				feeMap.put("printNote", projectFee.getPrintNote());
				feeMap.put("indirectFee", projectFee.getIndirectFee());
				feeMap.put("indirectNote", projectFee.getIndirectNote());
				feeMap.put("otherFee", projectFee.getOtherFee());
				feeMap.put("otherNote", projectFee.getOtherNote());
				feeMap.put("totalFee", projectFee.getTotalFee());
				feeMap.put("feeNote", projectFee.getFeeNote());
				feeMap.put("fundedFee", projectFee.getFundedFee());
				feeMap.put("toFundFee", DoubleTool.sub(approveFee, projectFee.getFundedFee()));
				feeMap.put("surplusFee", DoubleTool.sub(projectFee.getFundedFee(), projectFee.getTotalFee()));
			}else {
				feeMap.put("feeFlag", 0);
			}
			projectFees.add(feeMap);
		}
		return projectFees;
	}
	/**
	 * 根据项目立项id所有变更列表
	 * @param graId 项目立项id 
	 * @return 获取所有变更列表
	 */
	public List<ProjectVariation> getAllVariationByGrantedId(String graId) {
		if(graId == null){
			return new ArrayList();
		}
		StringBuffer hql = new StringBuffer("select vari from ProjectVariation vari where vari.granted.id = ? order by vari.applicantSubmitDate desc");
		List<ProjectVariation> varList = this.query(hql.toString(), graId);
		return varList;
	}
	/**
	 * 根据项目立项id获取当前变更
	 * @param graId 项目立项id
	 * @return 当前变更
	 */
	@SuppressWarnings("rawtypes")
	public ProjectVariation getCurrentVariationByGrantedId(String graId){
		if(graId == null){
			return null;
		}
		String hql = "select vari from ProjectVariation vari where vari.granted.id=? order by vari.applicantSubmitDate desc";
		List list = this.query(hql, graId);
		if(!list.isEmpty()){
			return (ProjectVariation)list.get(0);
		}
		return null;
	}
	
	/**
	 * 根据项目变更id获取项目id
	 * @param varId 项目变更id
	 * @return 项目id
	 */
	@SuppressWarnings("unchecked")
	public String getGrantedIdByVarId(String varId){
		if(varId == null){
			return null;
		}
		ProjectVariation projectVariation = (ProjectVariation) this.query(ProjectVariation.class, varId);
		String hql = "select vari.granted.id from ProjectVariation vari where vari.id=?";
		List<String> list = this.query(hql, varId);
		if(!list.isEmpty()){
			return list.get(0);
		}else{
			return "none";
		}
	}
	
	/**
	 * 根据项目立项id获取通过中检
	 * @param graId 项目立项id
	 * @return 通过中检
	 */
	@SuppressWarnings("rawtypes")
	public List getPassMidinspectionByGrantedId(String graId){
		if(graId == null){
			return new ArrayList();
		}
		String hql = "select midi from ProjectMidinspection midi where midi.granted.id=? and midi.finalAuditResult=2";
		List list = this.query(hql, graId);
		return list;
	}
	/**
	 * 根据项目立项id获取未审中检
	 * @param graId 项目立项id
	 * @return 未审中检
	 */
	@SuppressWarnings("rawtypes")
	public List getPendingMidinspectionByGrantedId(String graId){
		if(graId == null){
			return new ArrayList();
		}
		String hql = "select midi from ProjectMidinspection midi where midi.granted.id=? and midi.finalAuditResult=0";
		List list = this.query(hql, graId);
		return list;
	}
	/**
	 * 根据立项年份判断中检是否禁止
	 * @param grantedYear 立项年份
	 * @return 1：禁止         0：未禁止
	 */
	@SuppressWarnings("deprecation")
	public int getMidForbidByGrantedDate(int grantedYear){
		Date now = new Date();
		int midForbid = (now.getYear() + 1900 > grantedYear + 3) ? 1 : 0;
		return midForbid;
	}
	
	/**
	 * 根据立项年份判断结项起始时间是否开始
	 * @param grantedYear 立项年份
	 * @return 1：开始         0：未开始
	 */
	@SuppressWarnings("deprecation")
	public int getEndAllowByGrantedDate(int grantedYear){
		Date now = new Date();
		Date grantedDate = (Date)now.clone();
		grantedDate.setYear(grantedYear - 1900 + 3);
		grantedDate.setMonth(6);
		grantedDate.setDate(1);
		int endAllow = (now.after(grantedDate)) ? 1 : 0;
		return endAllow;
	}
	/**
	 * 根据项目立项id获取所有中检
	 * @param graId 项目立项id
	 * @return 所有中检
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<ProjectMidinspection> getAllMidinspectionByGrantedId(String graId){
		if(graId == null){
			return new ArrayList();
		}
		StringBuffer hql = new StringBuffer("select midi from ProjectMidinspection midi where midi.granted.id = ? order by midi.applicantSubmitDate desc");
		List<ProjectMidinspection> list = this.query(hql.toString(), graId);
		return list;
	}
	/**
	 * 根据项目年检id获取中检经费
	 * @param midIds 项目中检id
	 * @return 中检经费
	 */
	@SuppressWarnings("unchecked")
	public List<Map> getProjectFeeMidByMidId(String grantedId, List<String> midIds){
		List<Map> projectFees = new ArrayList<Map>();
		ProjectGranted projectGranted = (ProjectGranted)this.query(ProjectGranted.class, grantedId);
		
		for(String midId : midIds) {
			Map feeMap = new HashMap();
			Double approveFee = 0.0;
			if (projectGranted.getApproveFee() != null) {
				approveFee = projectGranted.getApproveFee();
			}
			feeMap.put("approveFee", approveFee);
//			feeMap.put("fundedFee", this.getFundedFeeByGrantedId(grantedId));
//			feeMap.put("toFundFee", DoubleTool.sub(approveFee, this.getFundedFeeByGrantedId(grantedId)));
			ProjectMidinspection projectMidinspection = (ProjectMidinspection) this.query(ProjectMidinspection.class , midId);
			if (projectMidinspection.getProjectFee() != null) {
				ProjectFee projectFee = (ProjectFee) this.query(ProjectFee.class, projectMidinspection.getProjectFee().getId());
				feeMap.put("feeFlag", 1);
				feeMap.put("bookFee", projectFee.getBookFee());
				feeMap.put("bookNote", projectFee.getBookNote());
				feeMap.put("dataFee", projectFee.getDataFee());
				feeMap.put("dataNote", projectFee.getDataNote());
				feeMap.put("travelFee", projectFee.getTravelFee());
				feeMap.put("travelNote", projectFee.getTravelNote());
				feeMap.put("conferenceFee", projectFee.getConferenceFee());
				feeMap.put("conferenceNote", projectFee.getConferenceNote());
				feeMap.put("internationalFee", projectFee.getInternationalFee());
				feeMap.put("internationalNote", projectFee.getInternationalNote());
				feeMap.put("deviceFee", projectFee.getDeviceFee());
				feeMap.put("deviceNote", projectFee.getDeviceNote());
				feeMap.put("consultationFee", projectFee.getConsultationFee());
				feeMap.put("consultationNote", projectFee.getConsultationNote());
				feeMap.put("laborFee", projectFee.getLaborFee());
				feeMap.put("laborNote", projectFee.getLaborNote());
				feeMap.put("printFee", projectFee.getPrintFee());
				feeMap.put("printNote", projectFee.getPrintNote());
				feeMap.put("indirectFee", projectFee.getIndirectFee());
				feeMap.put("indirectNote", projectFee.getIndirectNote());
				feeMap.put("otherFee", projectFee.getOtherFee());
				feeMap.put("otherNote", projectFee.getOtherNote());
				feeMap.put("totalFee", projectFee.getTotalFee());
				feeMap.put("feeNote", projectFee.getFeeNote());
				feeMap.put("fundedFee", projectFee.getFundedFee());
				feeMap.put("toFundFee", DoubleTool.sub(approveFee, projectFee.getFundedFee()));
				feeMap.put("surplusFee", DoubleTool.sub(projectFee.getFundedFee(), projectFee.getTotalFee()));
			}else {
				feeMap.put("feeFlag", 0);
			}
			projectFees.add(feeMap);
		}
		return projectFees;
	}
	
	/**
	 * 根据项目立项id获取当前中检
	 * @param graId 项目立项id
	 * @return 当前的中检
	 */
	@SuppressWarnings("unchecked")
	public ProjectMidinspection getCurrentMidinspectionByGrantedId(String graId){
		if(graId == null){
			return null;
		}
		ProjectGranted projectGranted = (ProjectGranted)this.query(ProjectGranted.class, graId);
		String hql = "select midi from ProjectMidinspection midi where midi.granted.id=? " +
				"order by midi.applicantSubmitDate desc";
		List<ProjectMidinspection> list = this.query(hql, graId);
		if(!list.isEmpty()){
			return list.get(0);
		}
		return null;
	}
	
	/**
	 * 设置时间的十分秒为当前时间的十分秒
	 * @param oriDate 原时间
	 * @return 处理后的时间
	 */
	public Date setDateHhmmss(Date oriDate){
		if(oriDate == null){
			return null;
		}else{
			Date now = new Date();
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
			String nowDateString = format.format(now);
			String oriDateString = format.format(oriDate);
			String newDateString = oriDateString.substring(0, 8) + nowDateString.substring(8);
			try {
				Date newDate = format.parse(newDateString);
				return newDate;
			} catch (ParseException e) {
				return oriDate;
			}
			
		}
	}
	
	/**
	 * 判断立项项目是否部属高校
	 * @param graId 项目立项id
	 * @return 1:部署高校, 0:地方高校
	 */
	@SuppressWarnings("rawtypes")
	public int isSubordinateUniversityGranted(String graId) {
		if(graId == null){
			return -1;
		}
		String hql = "select uni.type from ProjectGranted gra, University uni where gra.id = ? and gra.universityCode = uni.code";
		List l = this.query(hql, graId);
		int i = 0;
		if(l != null && l.size() > 0) {
			i = (Integer) l.get(0);
			if(i == 2) {
				i = 0;
			}
		}
		return i;
	}
	
	/**
	 * 根据变更对象获得可以同意的变更事项(非项目模块负责人，不允许修改)
	 * @param variation 项目变更对象
	 * @return 可以同意的变更事项
	 */
	public String getVarCanApproveItem(ProjectVariation variation){
		String resultItem = "";
		if(variation != null){
			if(variation.getChangeMember() == 1){//变更项目成员（含负责人）
				resultItem += "01,";
			}
			if(variation.getChangeAgency() == 1){//变更管理机构
				resultItem += "02,";
			}
			if(variation.getChangeProductType() == 1){//变更成果形式
				resultItem += "03,";
			}
			if(variation.getChangeName() == 1){//变更项目名称
				resultItem += "04,";
			}
			if(variation.getChangeContent() == 1){//研究内容有重大调整
				resultItem += "05,";
			}
			if(variation.getPostponement() == 1){//延期
				resultItem += "06,";
			}
			if(variation.getStop() == 1){//自行中止项目
				resultItem += "07,";
			}
			if(variation.getWithdraw() == 1){//申请撤项
				resultItem += "08,";
			}
			if(variation.getChangeFee() == 1){//申请撤项
				resultItem += "09,";
			}
			if(variation.getOther() == 1){//其他
				resultItem += "20,";
			}
			if(resultItem.length() > 0){
				resultItem = resultItem.substring(0, resultItem.length() - 1);
			}
		}
		return resultItem;
	}
	
	/**
	 * 通过审核结果详情编码获得可以同意的变更事项字串(非项目模块负责人，请勿修改。要改则只允许扩展代码、不允许修改已有代码)
	 * @param auditResultDetail	审核结果详情编码	长度为9，九位字符依次是：变更项目成员（含负责人）(0)、变更机构(1)、变更成果形式(2)、变更项目名称(3)、研究内容有重大调整(4)、延期(5)、自行终止项目(6)、申请撤项(7)、其他(19)这十类变更结果的标志位。	'1'表示同意 '0'表示不同意
	 * @return	可以同意的变更字串
	 */
	public String getVarCanApproveItem(String auditResultDetail){
		String resultItem = "";
		if(auditResultDetail != null && auditResultDetail.trim().length() >= 20){
			if(auditResultDetail.charAt(0) == '1'){//第1位变更项目成员（含负责人）结果
				resultItem += "01,";
			}
			if(auditResultDetail.charAt(1) == '1'){//第2位变更管理机构结果
				resultItem += "02,";
			}
			if(auditResultDetail.charAt(2) == '1'){//第3位变更成果形式结果
				resultItem += "03,";
			}
			if(auditResultDetail.charAt(3) == '1'){//第4位变更项目名称结果
				resultItem += "04,";
			}
			if(auditResultDetail.charAt(4) == '1'){//第5位研究内容有重大调整结果
				resultItem += "05,";
			}
			if(auditResultDetail.charAt(5) == '1'){//第6位延期
				resultItem += "06,";
			}
			if(auditResultDetail.charAt(6) == '1'){//第7位自行中止项目结果
				resultItem += "07,";
			}
			if(auditResultDetail.charAt(7) == '1'){//第8位申请撤项结果
				resultItem += "08,";
			}
			if(auditResultDetail.charAt(8) == '1'){//第9位申请经费变更
				resultItem += "09,";
			}
			if(auditResultDetail.charAt(19) == '1'){//第20位其他结果
				resultItem += "20,";
			}
			if(resultItem.length() > 0){
				resultItem = resultItem.substring(0, resultItem.length() - 1);
			}
		}
		return resultItem;
	}
	/**
	 * 获得可以同意的变更事项List
	 * @param varItemString 变更事项id，多个以,隔开
	 * @return list[code][name]
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List getVarItemList(String varItemString){
		List list = new ArrayList();
		if(varItemString != null && varItemString.trim().length() > 0){
			Map map = new HashMap();
			StringBuffer hql = new StringBuffer();
			hql.append("select s.code, s.name from SystemOption s where s.standard ='variation' and s.parent.id is not null and s.isAvailable = 1");
			String[] codes = varItemString.split(",");
			int len = codes.length;
			if(len > 0){
				hql.append(" and (");
				for(int i = 0; i < len; i++){
					map.put("code" + i, codes[i]);
					hql.append("s.code =:code" + i);
					if (i != len-1)
						hql.append(" or ");
				}hql.append(")");
			}
			hql.append(" order by s.code asc");
			list = this.query(hql.toString(), map);
		}
		return list;
	}
	
	/**
	 * 通过同意变更事项的字串拼接成同意变更事项的编码(非项目模块负责人，请勿修改。要改则只允许扩展代码、不允许修改已有代码)
	 * @param varSelectApproveIssue	同意变更事项,多个以,隔开
	 * @return 同意变更事项的编码
	 */
	public String getVarApproveItem(String varSelectApproveIssue){
		if(varSelectApproveIssue != null && varSelectApproveIssue.length() > 0){
			String approveIssue = "";
			if(varSelectApproveIssue.contains("01")){//第1位变更项目成员（含负责人）结果
				approveIssue= approveIssue + "1";
			}else{
				approveIssue= approveIssue + "0";
			}
			if(varSelectApproveIssue.contains("02")){//第2位变更管理机构结果
				approveIssue= approveIssue + "1";
			}else{
				approveIssue= approveIssue + "0";
			}
			if(varSelectApproveIssue.contains("03")){//第3位变更成果形式结果
				approveIssue= approveIssue + "1";
			}else{
				approveIssue= approveIssue + "0";
			}
			if(varSelectApproveIssue.contains("04")){//第4位变更项目名称结果
				approveIssue= approveIssue + "1";
			}else{
				approveIssue= approveIssue + "0";
			}
			if(varSelectApproveIssue.contains("05")){//第5位研究内容有重大调整结果
				approveIssue= approveIssue + "1";
			}else{
				approveIssue= approveIssue + "0";
			}
			if(varSelectApproveIssue.contains("06")){//第6位延期结果
				approveIssue= approveIssue + "1";
			}else{
				approveIssue= approveIssue + "0";
			}
			if(varSelectApproveIssue.contains("07")){//第7位自行中止项目结果
				approveIssue= approveIssue + "1";
			}else{
				approveIssue= approveIssue + "0";
			}
			if(varSelectApproveIssue.contains("08")){//第8位申请撤项结果
				approveIssue= approveIssue + "1";
			}else{
				approveIssue= approveIssue + "0";
			}
			if(varSelectApproveIssue.contains("09")){//第9位申请经费变更
				approveIssue= approveIssue + "1";
			}else{
				approveIssue= approveIssue + "0";
			}
			if(varSelectApproveIssue.contains("20")){//第20位其他结果
				for (int i = 0; i < 10; i++) {
					approveIssue= approveIssue + "0";
				}
				approveIssue= approveIssue + "1";
			}else{
				for (int i = 0; i < 11; i++) {
					approveIssue= approveIssue + "0";
				}
			}
			return approveIssue;
			
		}else{
			return null;
		}
	}
	/**
	 * 变更项目信息
	 * @param variation 变更对象
	 * @throws DocumentException 
	 */
	@SuppressWarnings("unchecked")
	public void variationProject(ProjectVariation variation) throws DocumentException{
		ProjectGranted granted = (ProjectGranted) this.query(ProjectGranted.class,this.getGrantedIdByVarId(variation.getId()));
		String appId = this.getApplicationIdByGrantedId(granted.getId());
		String approveDetail = variation.getFinalAuditResultDetail();
		if(approveDetail == null || approveDetail.trim().length() < 9){
			return;
		}
		if(variation.getChangeMember() == 1 && approveDetail.charAt(0) == '1'){//变更责任人
			//TODO 成员的相关信息
			ProjectApplication application = (ProjectApplication) this.query(ProjectApplication.class, appId);
			String xmlNewMembers = variation.getNewMembers();
			Document doc = DocumentHelper.parseText(xmlNewMembers);
			Iterator<Element> memberNodes = doc.selectNodes("//member").iterator();
			String members = "";
			while (memberNodes.hasNext()) {
				Element memberElement = memberNodes.next();
				String memberName = memberElement.elementText("memberName");
				String agencyName = memberElement.elementText("agencyName");
				String divisionName = memberElement.elementText("divisionName");
				members += memberName + "(" + agencyName + divisionName + ")" + "; ";
			}
			application.setMembers(members.substring(0, members.length()-2));
			//TODO 	如果项目负责人的信息改变了，就要对相应的申请表中的负责人信息进行更新
			this.modify(application);
		}
		if(variation.getChangeAgency()==1 && approveDetail.charAt(1) == '1'){//变更机构
			String uniName = variation.getNewAgencyName().split("; ")[0];
			String divisionName = variation.getNewAgencyName().split("; ")[1];
			//高校名称 -> 高校代码
			HashMap<String, String> univMap = (HashMap<String, String>) ActionContext.getContext().getApplication().get("univNameCodeMap");
			String uniCode = univMap.get(uniName);
			granted.setUniversityCode(uniCode);
			granted.setUniversityName(uniName);
			granted.setAgencyName(uniName);
			granted.setDivisionName(divisionName);
		}
		if(variation.getChangeProductType() == 1 && approveDetail.charAt(2) == '1'){//变更成果形式
			granted.setProductType(variation.getNewProductType());
			granted.setProductTypeOther(variation.getNewProductTypeOther());
		}
		if(variation.getChangeName() == 1 && approveDetail.charAt(3) == '1'){//变更项目名称
			granted.setName(variation.getNewName());
		}
		if(variation.getPostponement() == 1 && approveDetail.charAt(5) == '1'){//延期
			granted.setPlanEndDate(variation.getNewOnceDate());
			
		}
		if(variation.getStop() == 1 && approveDetail.charAt(6) == '1'){//自行中止项目
			granted.setStatus(3);//项目中止
			granted.setEndStopWithdrawDate(variation.getFinalAuditDate());
			granted.setEndStopWithdrawPerson(variation.getFinalAuditorName());
			granted.setEndStopWithdrawOpinion(variation.getFinalAuditOpinion());
			granted.setEndStopWithdrawOpinionFeedback(variation.getFinalAuditOpinionFeedback());
		}
		if(variation.getWithdraw() == 1 && approveDetail.charAt(7) == '1'){//申请撤项
			granted.setStatus(4);//撤项
			granted.setEndStopWithdrawDate(variation.getFinalAuditDate());
			granted.setEndStopWithdrawPerson(variation.getFinalAuditorName());
			granted.setEndStopWithdrawOpinion(variation.getFinalAuditOpinion());
			granted.setEndStopWithdrawOpinionFeedback(variation.getFinalAuditOpinionFeedback());
		}
		if(variation.getChangeFee() == 1 && approveDetail.charAt(8) == '1'){//变更经费预算
			if (granted.getApproveFee() != variation.getNewProjectFee().getTotalFee()) {
				granted.setApproveFee(variation.getNewProjectFee().getTotalFee());
			}
			granted.setProjectFee(variation.getNewProjectFee());
		}
		this.modify(granted);
			
	}
	
	/**
	 * 根据立项id获得立项年份
	 * @param graId 立项id
	 * @return 立项年份
	 */
	@SuppressWarnings("deprecation")
	public int getGrantedYear(String graId){
		if(graId == null || graId.trim().length() == 0){
			return 0;
		}
		int grantedYear = 0;
		ProjectGranted granted = (ProjectGranted) this.query(ProjectGranted.class, graId);
		if(granted != null && granted.getApproveDate() != null){
			grantedYear = granted.getApproveDate().getYear() + 1900;
		}else{
			String appId = this.getApplicationIdByGrantedId(graId);
			ProjectApplication application = (ProjectApplication)this.query(ProjectApplication.class, appId);
			if(application != null){
				grantedYear = application.getYear();
			}else{
				;
			}
		}
		return grantedYear;
	}
	/**
	 * 获得默认结项证书编号
	 * @param endinspectionClassName 结项类名称
	 * @return 默认结项证书编号
	 */
	@SuppressWarnings({ "unchecked", "deprecation", "rawtypes" })
	public String getDefaultEndCertificate(){
		Date date = new Date();
		String year = String.valueOf(date.getYear() + 1900);
		String cer = year+"JXZ";
		String endCer = cer + "0001";
		String gecer = "";
		int max = 0;
		int number = 0;
		Map map = new HashMap();
		map.put("cer", cer+"%");
		String hql = "select endi.certificate from ProjectEndinspection endi where endi.certificate like :cer order by endi.certificate desc";
		List cersF = this.query(hql,map);
		for(int i=0; i<cersF.size(); i++){
			gecer = (String) cersF.get(i);
			if(!"".equals(gecer)){
				String endNum = gecer.substring(gecer.indexOf(cer)+7);
				try {
					number = Integer.parseInt(endNum);
				} catch (NumberFormatException e) {
					continue;
				}
				if(number>max){
					max = number;
				}
			}
		}
		max += 1;
		String smax = max + "";
		while(smax.length()<4){
			smax = "0" + smax;
		}
		endCer = cer+smax;
		return endCer;
	}
	
	/**
	 * 判断项目结项号是否唯一
	 * param projectType 项目类型
	 * @param number 项目结项号
	 * @param endId 结项id
	 * @return 项目结项号是否唯一true：唯一 false：不唯一
	 */
	@SuppressWarnings("unchecked")
	public boolean isEndNumberUnique(String number, String endId){
		if(number == null){
			return false;
		}
		Map<String, String> map = new HashMap<String, String>();
		String hql;
		if(endId==null || endId.equals("")){
			map.put("number", number);
			hql = "select endi.id from ProjectEndinspection endi where endi.certificate=:number";
		}else{
			map.put("number", number);
			map.put("endId", endId);
			hql = "select endi.id from ProjectEndinspection endi where endi.certificate=:number and endi.id!=:endId";
		}
		List<String> list = this.query(hql, map);
		if(list.isEmpty()){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 根据项目结项id获取项目id
	 * @param endId 项目结项id
	 * @return 项目id
	 */
	@SuppressWarnings("unchecked")
	public String getGrantedIdByEndId(String endId){
		if(endId == null){
			return "none";
		}
		ProjectEndinspection projectEndinspection = (ProjectEndinspection)this.query(ProjectEndinspection.class, endId);
		String hql = "select endi.granted.id from ProjectEndinspection endi where endi.id=?";
		List<String> list = this.query(hql, endId);
		if(!list.isEmpty()){
			return list.get(0);
		}else{
			return "none";
		}
	}
	
	/**
	 * 管理人员录入中检报告书
	 * @param projectType 项目类别字符串
	 * @param graId 项目立项id
	 * @param uploadFile上传的文件
	 * @return 返回上传文件保存后的相对路径
	 */
	@SuppressWarnings({"unchecked", "rawtypes" })
	public String uploadMidFile(String projectType, String graId, File uploadFile) {
		try {
			String oldName = uploadFile.getName();
			String extendName = oldName.substring(oldName.lastIndexOf("."));
			String realPath = ServletActionContext.getServletContext().getRealPath("upload");
			Map map = new HashMap();
			map.put("graId", graId);
			String gnumber = (String)this.query("select gra.number from ProjectGranted gra where gra.type = '"+ projectType +"' and gra.id =:graId ", map).get(0);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			String date = sdf.format(new Date());
			Calendar cal = Calendar.getInstance(); 
			cal.setTime(new Date());
			int year = cal.get(Calendar.YEAR);
			String realName = projectType + "_mid_" + year + "_" + gnumber + "_" + date + extendName;
			String filepath = "project/" + projectType + "/mid/" + year + "/";
			realPath = realPath.replace('\\', '/');
			String path = realPath + "/" + filepath + realName;
			FileTool.mkdir_p(realPath + "/" + filepath);
			File x = new File(path);
			FileUtils.copyFile(uploadFile, x);
			filepath = "upload/" + filepath + realName;
			importMidinspectionWordXMLData(x, graId, projectType);
			return filepath;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 导入中检的word宏（更新）
	 * @param wordFile word文件
	 * @param grantedId 立项id
	 * @param projectType 项目类型
	 */
	public void importMidinspectionWordXMLData(File wordFile, String grantedId, String projectType) {
		if(projectType.equals("general")) {
			//获取上传的xml数据
			String midXMLStr = WordTool.getWordTableData(wordFile, 0, 0, 0);
			if(midXMLStr != null && midXMLStr.startsWith("<middata>")) {
				try {
					//解析成dom对象
					Document document = DocumentHelper.parseText(midXMLStr);
					//获取根元素
					Element rootElement = document.getRootElement();
					//匹配中检类
					@SuppressWarnings("unused")
					ProjectMidinspection midinspection = (ProjectMidinspection)getCurrentMidinspectionByGrantedId(grantedId);
					//获取版本号
					Element docVersion = rootElement.element("docVersion");
					if(docVersion.getText().startsWith("v1.0.")) {
						Element indexesElement = rootElement.element("Indexes");
						String midProgress = rootElement.element("Indexes").elementText("progress");
						String[] indexes = midProgress.split("; ");
						//更新研究进展
						midinspection.setProgress(WordTool.getWordTableData(wordFile, Integer.parseInt(indexes[0]) - 1, Integer.parseInt(indexes[1]) - 1, Integer.parseInt(indexes[2]) - 1));
						String midProductIntro = indexesElement.elementText("productIntroduction");
						indexes = midProductIntro.split("; ");
						//更新成果简介
						midinspection.setProductIntroduction(WordTool.getWordTableData(wordFile, Integer.parseInt(indexes[0]) - 1, Integer.parseInt(indexes[1]) - 1, Integer.parseInt(indexes[2]) - 1));
						this.modify(midinspection);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 根据项目立项id获取已审并同意的变更
	 * @param graId 项目立项id
	 * @return 未审变更
	 */
	@SuppressWarnings("rawtypes")
	public List getAuditedVariationByGrantedId(String graId){
		if(graId == null){
			return new ArrayList();
		}
		ProjectGranted projectGranted = (ProjectGranted)this.query(ProjectGranted.class, graId);
		String hql = "select vari from ProjectVariation vari where vari.granted.id=? and vari.finalAuditResult=2";
		List list = this.query(hql, graId);
		return list;
	}
	/**
	 * 获得变更次数
	 * @param graId 项目立项id
	 * @return 系统已有的变更次数
	 */
	@SuppressWarnings("rawtypes")
	public int getVarTimes(String graId){
		if(graId == null){
			return 0;
		}
		ProjectGranted projectGranted = (ProjectGranted)this.query(ProjectGranted.class, graId);
		String hql = "select vari from ProjectVariation vari where vari.granted.id=?";
		List gvs = this.query(hql, graId);
		return gvs.size();
	}
	/**
	 * 获得成果形式id，多个以逗号隔开
	 * @param productTypeNames 成果形式名称 多个以英文分号和空格隔开
	 * @return 成果id
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String getProductTypeCodes(String productTypeNames){
		String productTypeIds = "";
		if(productTypeNames != null && productTypeNames.length() > 0){
			Map map1 = new HashMap();
			String[] ptypeNames = productTypeNames.split("; ");
			for(int i=0;i<ptypeNames.length;i++){
				map1.put("ptypeName", ptypeNames[i]);
				List list = this.query("select so.code from SystemOption so where so.name=:ptypeName and so.standard='productType' and so.parent.id is not null", map1);
				if(list!=null&&!list.isEmpty()){
					String ptypeid = (String) list.get(0);
					productTypeIds += ptypeid + ",";
				}
			}
			if(productTypeIds.length() > 0){
				productTypeIds = productTypeIds.substring(0, productTypeIds.length() - 1);
			}
		}
		return productTypeIds;
	}
	/**
	 * 研究人员上传变更文件
	 * @param projectType 项目类别字符串
	 * @param graId 项目立项id
	 * @param uploadFile 上传的文件
	 * @return 返回上传文件保存后的相对路径
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String uploadVarFile(String projectType, String graId, File uploadFile){
		try{
			String oldName = uploadFile.getName();
			String extendName = oldName.substring(oldName.lastIndexOf("."));
			String realPath = ServletActionContext.getServletContext().getRealPath("upload");
			Map map = new HashMap();
			map.put("graId", graId);
			//项目编号
			String gnumber = (String)this.query("select gra.number from ProjectGranted gra where gra.type = '" + projectType + "' and gra.id = :graId ", map).get(0);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			String date = sdf.format(new Date());
			Calendar cal = Calendar.getInstance(); 
			cal.setTime(new Date());
			int year = cal.get(Calendar.YEAR);
			String realName = projectType + "_var_" + year + "_" + gnumber + "_" + date + extendName;
			String filepath = "project/" + projectType + "/var/" + year + "/";
			realPath = realPath.replace('\\', '/');
			String path = realPath + "/" + filepath + realName;
			FileTool.mkdir_p(realPath + "/" + filepath);
			File x = new File(path);
			FileUtils.copyFile(uploadFile, x);
			filepath = "upload/" + filepath + realName;
			return filepath;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 获得成果形式名称，多个以逗号隔开
	 * @param productTypeIds 成果形式id List
	 * @return 成果名称
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String getProductTypeNames(List<String> productTypeIds){
		if(productTypeIds == null){
			return null;
		}
		String productTypeNames = "";
		int size = productTypeIds.size();
		Map map = new HashMap();
		if(size > 0){
			for(int m = 0; m < size; m++){
				map.clear();
				map.put("productTypeIds", productTypeIds.get(m));
				String productTypeName = (String)this.query("select so.name from " +
						"SystemOption so where so.code=:productTypeIds and so.standard='productType'", map).get(0);
				if(!"".equals(productTypeNames)){
					productTypeNames+="; "+productTypeName;
				}else{
					productTypeNames += productTypeName;
				}
			}
		}
		return productTypeNames;
	}
	/**
	 * 上传变更延期项目研究计划文件
	 * @param projectType 项目类别字符串
	 * @param graId 项目立项id
	 * @param uploadFile 上传的文件
	 * @return 返回上传文件保存后的相对路径
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String uploadVarPlanfile(String projectType, String graId, File uploadFile){
		try{
			String oldName = uploadFile.getName();
			String extendName = oldName.substring(oldName.lastIndexOf("."));
			String realPath = ServletActionContext.getServletContext().getRealPath("upload");
			Map map = new HashMap();
			map.put("graId", graId);
			//项目编号
			String gnumber = (String)this.query("select gra.number from ProjectGranted gra where gra.type = " + projectType + " and gra.id = :graId ", map).get(0);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			String date = sdf.format(new Date());
			Calendar cal = Calendar.getInstance(); 
			cal.setTime(new Date());
			int year = cal.get(Calendar.YEAR);
			String realName = projectType + "_var_pos_" + year + "_" + gnumber + "_" + date + extendName;
			String filepath = "project/" + projectType + "/var/" + year + "/";
			realPath = realPath.replace('\\', '/');
			String path = realPath + "/" + filepath + realName;
			FileTool.mkdir_p(realPath + "/" + filepath);
			File x = new File(path);
			FileUtils.copyFile(uploadFile, x);
			filepath = "upload/" + filepath + realName;
			/*
			if(submitStatus == 3) {
				// 读取doc文件并导入
				importVarXMLData(pid, x);
			}*/
			return filepath;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	//----------以下为设置项目经费相关信息----------
	/**
	 * 保存项目申请经费的相关字段的值
	 * @param projectFee 项目经费的对象
	 */
	public ProjectFee setProjectFee(ProjectFee projectFee){
		//申请经费概算信息
		if(projectFee != null){
			if(projectFee.getBookFee() != null){
				projectFee.setBookFee(projectFee.getBookFee());
			}
			if (projectFee.getBookNote() != null) {
				projectFee.setBookNote(projectFee.getBookNote());
			}
			if(projectFee.getDataFee() != null){
				projectFee.setDataFee(projectFee.getDataFee());
			}
			if (projectFee.getDataNote() != null) {
				projectFee.setDataNote(projectFee.getDataNote());
			}
			if(projectFee.getTravelFee() != null){
				projectFee.setTravelFee(projectFee.getTravelFee());
			}
			if (projectFee.getTravelNote() != null) {
				projectFee.setTravelNote(projectFee.getTravelNote());
			}
			if(projectFee.getDeviceFee() != null){
				projectFee.setDeviceFee(projectFee.getDeviceFee());
			}
			if (projectFee.getDeviceNote() != null) {
				projectFee.setDeviceNote(projectFee.getDeviceNote());
			}
			if(projectFee.getConferenceFee() != null){
				projectFee.setConferenceFee(projectFee.getConferenceFee());
			}
			if (projectFee.getConferenceNote() != null) {
				projectFee.setConferenceNote(projectFee.getConferenceNote());
			}
			if(projectFee.getConsultationFee() != null){
				projectFee.setConsultationFee(projectFee.getConsultationFee());
			}
			if (projectFee.getConsultationNote() != null) {
				projectFee.setConsultationNote(projectFee.getConsultationNote());
			}
			if(projectFee.getLaborFee() != null){
				projectFee.setLaborFee(projectFee.getLaborFee());
			}
			if (projectFee.getLaborNote() != null) {
				projectFee.setLaborNote(projectFee.getLaborNote());
			}
			if(projectFee.getPrintFee() != null){
				projectFee.setPrintFee(projectFee.getPrintFee());
			}
			if (projectFee.getPrintNote() != null) {
				projectFee.setPrintNote(projectFee.getPrintNote());
			}
			if(projectFee.getInternationalFee() != null){
				projectFee.setInternationalFee(projectFee.getInternationalFee());
			}
			if (projectFee.getInternationalNote() != null) {
				projectFee.setInternationalNote(projectFee.getInternationalNote());
			}
			if(projectFee.getIndirectFee() != null){
				projectFee.setIndirectFee(projectFee.getIndirectFee());
			}
			if (projectFee.getIndirectNote() != null) {
				projectFee.setIndirectNote(projectFee.getIndirectNote());
			}
			if(projectFee.getOtherFee() != null){
				projectFee.setOtherFee(projectFee.getOtherFee());
			}
			if (projectFee.getOtherNote() != null) {
				projectFee.setOtherNote(projectFee.getOtherNote());
			}
			if(projectFee.getTotalFee() != null){
				projectFee.setTotalFee(projectFee.getTotalFee());
			}
			if (projectFee.getFeeNote() != null) {
				projectFee.setFeeNote(projectFee.getFeeNote());
			}
			if (projectFee.getFundedFee() != null) {
				projectFee.setFundedFee(projectFee.getFundedFee());
			}
		}
		return projectFee;
	}
	/**
	 * 输入人名时可能有word中复制出的奇怪字符“•”，前台让其校验通过，在后台替换为“·”
	 * @param nameString
	 * @return
	 */
	public String regularNames(String nameString) {
		String[] names = nameString.split("•");
		String regularedName = "";
		for(String name:names){
			regularedName += name + "·";
		}
		regularedName = regularedName.substring(0, regularedName.length() - 1);
		return regularedName;
	}
	/**
	 * 研究人员上传结项申请书
	 * @param projectType 项目类别字符串
	 * @param graId 项目立项id
	 * @param uploadFile 上传的文件
	 * @param submitStatus 提交状态
	 * @return 返回上传文件保存后的相对路径
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String uploadEndFile(String projectType, String graId, File uploadFile) {
		try {
			String oldName = uploadFile.getName();
			String extendName = oldName.substring(oldName.lastIndexOf("."));
			Map map = new HashMap();
			map.put("graId", graId);
			String realPath = ServletActionContext.getServletContext().getRealPath("upload");
			//项目编号
			String gnumber = (String)this.query("select gra.number from ProjectGranted gra where gra.type = '" + projectType + "' and gra.id =:graId ", map).get(0);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			String date = sdf.format(new Date());
			Calendar cal = Calendar.getInstance(); 
			cal.setTime(new Date());
			int year = cal.get(Calendar.YEAR);
			String realName = projectType + "_end_" + year + "_" + gnumber + "_" + date + extendName;
			String filepath = "project/" + projectType + "/end/" + year + "/";
			realPath = realPath.replace('\\', '/');
			String path = realPath + "/" + filepath + realName;
			FileTool.mkdir_p(realPath + "/" + filepath);
			File x = new File(path);
			FileUtils.copyFile(uploadFile, x);
			filepath = "upload/" + filepath + realName;
			//TODO 读取doc文件并导入
//			importEndinspectionWordXMLData(x, graId, projectType);
			//importEndXMLData(pid, x);
			return filepath;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 获得项目经费默认值
	 * @return 项目经费
	 */
	public Double getDefaultFee(ProjectApplication application){
		Map<String, Object> sc = ActionContext.getContext().getApplication();
		double approveFee = 0.0;
		if(application.getType().equals("general")) {
			String subType = application.getProjectType().trim().toString();//项目类型
			double applyFee = Double.parseDouble(application.getApplyFee().trim().toString());//申请经费
			if(subType.equals("青年基金项目")){
				approveFee = Math.min(applyFee, 8); 
			}else if(subType.equals("规划基金项目")){
				approveFee = Math.min(applyFee, 10);
			}
		} else{
			approveFee = 0.0;
		}
		return approveFee;
	}
	
	/**
	 * 根据项目id获取项目已拨款经费
	 * @param graId 项目id
	 * @return 项目拨款
	 */
	@SuppressWarnings("rawtypes")
	public Double getFundedFeeByGrantedId(String graId){
		Double fundedFee = 0.0;
		if(graId == null){
			return 0.0;
		}
		ProjectGranted projectGranted = (ProjectGranted)this.query(ProjectGranted.class, graId);
		String hql= "from ProjectFunding fun where fun.granted.id=? ";
		List list = this.query(hql, graId);
		if (!list.isEmpty()) {
			ProjectFunding projectFunding = (ProjectFunding) list.get(0);
			if (projectFunding.getStatus() ==1) {
				fundedFee = DoubleTool.sum(fundedFee, projectFunding.getFee());
			}
			return fundedFee;
		}else {
			return 0.0;
		}	
	}
	
	/**
	 * 设置项目立项信息,用于走流程申请
	 * @param application 项目申请对象
	 * @param granted 项目立项对象
	 * @return ProjectGranted
	 */
	public ProjectGranted setGrantedInfoFromApp(ProjectApplication application, ProjectGranted granted) {
		if(application != null) {
			granted.setStatus(1);
			granted.setName(application.getProjectName());
			granted.setUniversityName(application.getUniversityName());
			granted.setUniversityCode(application.getUniversityCode());
			if(granted.getType().equals("general")) {
				granted.setDivisionName(application.getDepartment());
			} else if(granted.getType().equals("instp")){
				granted.setDivisionName(application.getInstituteName());
			}
			if(application.getProjectType() != null){//项目子类
				granted.setSubtype(application.getProjectType());
			} else{
				granted.setSubtype(null);
			}
			granted.setIsImported(0);
			granted.setApplicantName(application.getDirector());
			granted.setProductType(application.getProductType());
			granted.setProductTypeOther(application.getProductTypeOther());
			granted.setPlanEndDate(application.getPlanEndDate());
		}
		return granted;
	}
}
