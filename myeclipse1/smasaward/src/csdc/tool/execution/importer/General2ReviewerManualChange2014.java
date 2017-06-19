package csdc.tool.execution.importer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;

import csdc.bean.Expert;
import csdc.bean.ProjectApplication;
import csdc.bean.ProjectApplicationReview;
import csdc.bean.ProjectApplicationReviewUpdate;
import csdc.service.imp.BaseService;
import csdc.tool.execution.finder.ExpertFinder;
import csdc.tool.reader.PoiExcelReader;

/**
 * 一般项目，根据服务中心修改意见，批量替换项目与专家的匹配对
 * 2015年度已经进行适当调整，程序整体与2014年度保持一致。
 * @author wang
 *
 */
public class General2ReviewerManualChange2014 extends Importer{
	
	private PoiExcelReader reader;
	
//	@Autowired
//	private BeanFieldUtils beanFieldUtils;
//	
//	@Autowired
//	private Tools tool;
	
	@Autowired
	private ExpertFinder expertFinder;
	
	@Autowired
	private BaseService baseService;
	
	private HashMap<String, List<Object[]>> projectName2Infos = new HashMap<String, List<Object[]>>();//项目名称与该项目的信息的映射
	private HashMap<String, List<Object[]>> expertNum2Infos = new HashMap<String, List<Object[]>>();//专家姓名与该专家信息的映射
	private Set<Object[]> notReviewExpert = new HashSet<Object[]>();//未参评专家
	private Set<Object[]> notFindExpert = new HashSet<Object[]>();//未找到专家
	private Set<Object[]> notReviewProject = new HashSet<Object[]>();//未参评项目
	private Set<Object[]> notFindProject = new HashSet<Object[]>();//未找到项目
	
	/**
	 * reader重置
	 * @throws Exception
	 */
	private void resetReader() throws Exception {
		reader.readSheet(0);
	}
	
	@Override
	public void work() throws Exception {
		importData();
	}
	
	private void importData() throws Exception {
		resetReader();
		
		expertFinder.initData();
		
		Map session = ActionContext.getContext().getSession();
		int year = Integer.parseInt(session.get("defaultYear").toString());
		
		List<Object[]> projectInfosList = baseService.query("select g.universityCode,g.universityName,g.projectName,g.director,g.id,g.isReviewable from ProjectApplication g where g.type = 'general' and g.year = ?",year);
		for (Object[] objects : projectInfosList) {
			List<Object[]> projectInfos = projectName2Infos.get(objects[2].toString());
			if (projectInfos == null) {
				projectInfos = new ArrayList<Object[]>();
			}
			projectInfos.add(objects);
			projectName2Infos.put(objects[2].toString(), projectInfos);
		}
		
		List<Object[]> expertInfosList = baseService.query("select u.name,e.universityCode,e.name,e.number,e.id,e.isReviewer from Expert e,University u where e.universityCode = u.code ");
		for (Object[] objects : expertInfosList) {
			List<Object[]> expertInfos = expertNum2Infos.get(objects[2].toString());
			if (expertInfos == null) {
				expertInfos = new ArrayList<Object[]>();
			}
			expertInfos.add(objects);
			expertNum2Infos.put(objects[2].toString(), expertInfos);
		}
		
		List<ProjectApplicationReview> generalReviewList = baseService.query("select pr from ProjectApplicationReview pr where pr.type = 'general' and pr.year = ? order by pr.project.id asc", year);
		HashMap<String, List<String>> general2ReviewersMap = new HashMap<String, List<String>>();//项目ID与该项目的评审专家的映射
		for (ProjectApplicationReview pr : generalReviewList) {
			List<String> expertIds = general2ReviewersMap.get(pr.getProject().getId());
			if (null == expertIds) {
				expertIds = new ArrayList<String>();
			}
			expertIds.add(pr.getReviewer().getId());
			general2ReviewersMap.put(pr.getProject().getId(), expertIds);
		}
		
		HashMap<String, List<String>> general2ReviewersMapFromExcel = new HashMap<String, List<String>>();//EXCEL导入（项目ID与该项目的评审专家的映射）
		//A项目ID;B项目名称;C项目学校编号;D项目学校名称;E申请人
		while (next(reader)) {
			System.out.println(reader.getCurrentRowIndex() + "/" + reader.getRowNumber());	
			if (reader.getCurrentRowIndex() == 374) {
				int stop;
				stop = 1;
			}
			String projectId = null;
			String reviewerId = null;
			List<String> reviewerIdsFromExcel = null;
			if (projectName2Infos.containsKey(B.trim())) {
				projectId = findProject(C.trim(),D.trim(),B.trim(),E.trim());
				if (projectId == null) {
//					System.out.println("EXCEL中的项目--" + B + "--匹配不上(可能退评或被修改)");
					continue;
				}
				reviewerIdsFromExcel = new ArrayList<String>();
				String expertNumString = null;
				Integer expertNumInteger = null;
				for (int i = 0; i < 5; i++) {
					switch (i) {
					case 0:
						reviewerId = new String();
						expertNumString = specialNum2String(I);
						expertNumInteger = expertNumString != null ? Integer.parseInt(expertNumString) : null;
						reviewerId = findReviewer(F,specialNum2String(G),H,expertNumInteger);
						break;
					case 1:
						reviewerId = new String();
						expertNumString = specialNum2String(M);
						expertNumInteger = expertNumString != null ? Integer.parseInt(expertNumString) : null;
						reviewerId = findReviewer(J,specialNum2String(K),L,expertNumInteger);
						break;
					case 2:
						reviewerId = new String();
						expertNumString = specialNum2String(Q);
						expertNumInteger = expertNumString != null ? Integer.parseInt(expertNumString) : null;
						reviewerId = findReviewer(N,specialNum2String(O),P,expertNumInteger);
						break;
					case 3:
						reviewerId = new String();
						expertNumString = specialNum2String(U);
						expertNumInteger = expertNumString != null ? Integer.parseInt(expertNumString) : null;
						reviewerId = findReviewer(R,specialNum2String(S),T,expertNumInteger);
						break;
					case 4:
						reviewerId = new String();
						expertNumString = specialNum2String(Y);
						expertNumInteger = expertNumString != null ? Integer.parseInt(expertNumString) : null;
						reviewerId = findReviewer(V,specialNum2String(W),X,expertNumInteger);						
						break;
					}
					if (reviewerId != null && !reviewerId.isEmpty()) {
						reviewerIdsFromExcel.add(reviewerId);
					}
				}
				
				List<String> reviewerIdsFromDB = general2ReviewersMap.get(projectId);
				//循环数据库中，当前项目对应的参评专家；如果EXCEL中，存在该专家，则不处理；如果EXCEL中不存在该专家，则删除该匹配对，并添加匹配更新器记录（手工操作）
				if (reviewerIdsFromDB != null) {
					for (String string2 : reviewerIdsFromDB) {
						if (!reviewerIdsFromExcel.contains(string2)) {
							Map paraMap = new HashMap();
							paraMap.put("projectId", projectId);	//项目ID
							paraMap.put("reviewerId", string2); //评审专家ID
							List<String> re = baseService.query("select pr.id from ProjectApplicationReview pr where pr.type = 'general' and pr.project.id = :projectId and pr.reviewer.id = :reviewerId", paraMap);
							if (re != null && !re.isEmpty()) {
								ProjectApplicationReview pr = (ProjectApplicationReview) baseService.query(ProjectApplicationReview.class, re.get(0));
								ProjectApplicationReviewUpdate pru = new ProjectApplicationReviewUpdate(pr, 0, 1);
								pru.setType("general");
								baseService.add(pru);
								baseService.delete(pr);
							}
						}
					}
				} else {
					reviewerIdsFromDB = new LinkedList<String>();
				}
			
				//循环EXCEL中，当前项目对应的参评专家；如果数据库中，存在该专家，则不处理；如果数据库中不存在该专家，则添加该匹配对，并添加匹配更新器记录（手工操作）
				for (String string2 : reviewerIdsFromExcel) {
					if (!reviewerIdsFromDB.contains(string2)) {
						ProjectApplicationReview pr = new ProjectApplicationReview();
						pr.setIsManual(1);
						ProjectApplication generalProject = (ProjectApplication) baseService.query(ProjectApplication.class, projectId);
						pr.setProject(generalProject);
						Expert expert = (Expert) baseService.query(Expert.class, string2);					
						pr.setReviewer(expert);
						pr.setYear(year);
						pr.setType("general");
						
						baseService.add(pr);
						
						ProjectApplicationReviewUpdate pru = new ProjectApplicationReviewUpdate(pr, 1, 1);
						pru.setType("general");
						baseService.add(pru);
					}
				}
				general2ReviewersMapFromExcel.put(projectId, reviewerIdsFromExcel);
			}else{
				Object[] objects = new Object[4];
				objects[0] = C;
				objects[1] = D;
				objects[2] = B;
				objects[3] = E;
				notFindProject.add(objects);
				System.out.println("------------------------------------------EXCEL中的项目--" + B.trim() + "--匹配不上(可能退评或被修改)");
			}
		}
		System.out.println("notFindProject:");
		if (notFindProject.size() != 0) {
			Iterator<Object[]> iterator = notFindProject.iterator();
			while (iterator.hasNext()) {
				Object[] aObjects = iterator.next();
				for (int i = 0; i < aObjects.length; i++) {
					System.out.print(aObjects[i]);
				}
				System.out.println(";");
			}
		}
		System.out.println("notReviewProject:");
		if (notReviewProject.size() != 0) {
			Iterator<Object[]> iterator = notReviewProject.iterator();
			while (iterator.hasNext()) {
				Object[] aObjects = iterator.next();
				for (int i = 0; i < aObjects.length; i++) {
					System.out.print(aObjects[i]);
				}
				System.out.println(";");
			}
		}
		System.out.println("notFindExpert:");
		if (notFindExpert.size() != 0) {
			Iterator<Object[]> iterator = notFindExpert.iterator();
			while (iterator.hasNext()) {
				Object[] aObjects = iterator.next();
				for (int i = 0; i < aObjects.length; i++) {
					System.out.print(aObjects[i]);
				}
				System.out.println(";");
			}
		}
		
		System.out.println("总共处理" + general2ReviewersMapFromExcel.size() + "条数据");
		System.out.println("notReviewExpert:");
		Iterator<Object[]> iterator = notReviewExpert.iterator();
		while (iterator.hasNext()) {
			Object[] aObjects = iterator.next();
			System.out.println(aObjects[2] + "--------------" + aObjects[3]);
		}
	}
	
	/**
	 * 指数形式、小数形式等不规范输入转换为规范输入
	 * @param orginal
	 * @return
	 */
	public String specialNum2String(String orginal) {
		if (orginal == null || orginal.isEmpty()) {
			return null;
		}
		Double double1 = Double.parseDouble(orginal);
		Integer aInteger = double1.intValue();
		return aInteger.toString();
	}
	
	/**
	 * 判断EXCEL中的项目学校编号、项目学校名称、项目名称、项目申请人是否和数据库一致
	 * @param projectUnivCode 项目学校编号
	 * @param projectUnivName 项目学校名称
	 * @param projectName 项目名称
	 * @param projectDirector 项目申请人
	 * @return 如果和数据库中的一致，则返回该项目ID
	 */
	public String findProject(String projectUnivCode,String projectUnivName,String projectName,String projectDirector) {
		List<Object[]> projectInfos = projectName2Infos.get(projectName);
		if (projectInfos == null) {
			Object[] objects = new Object[4];
			objects[0] = projectUnivCode;
			objects[1] = projectUnivName;
			objects[2] = projectName;
			objects[3] = projectDirector;
			notFindProject.add(objects);
			return null;
		}
		/**
		 * 1.通过项目所在高校代码、项目所在高校名称、项目名称、项目负责人查找，找到，且项目为参评，则返回项目；
		 * 2.通过项目所在高校代码、项目所在高校名称、项目名称、项目负责人查找，找到，且项目为退评，则记录该项目为退评项目；
		 * 3.通过项目所在高校代码、项目所在高校名称、项目名称、项目负责人查找，未找到，则记录该项目为未找到项目
		 */
		for (Object[] objects : projectInfos) {
			if (objects[0] != null && objects[0].equals(projectUnivCode) && objects[1] != null && objects[1].equals(projectUnivName) && objects[2] != null && objects[2].equals(projectName) && objects[3] != null && objects[3].equals(projectDirector)) {
				if ((Integer) objects[5] == 0) {
					notReviewProject.add(objects);
					return null;
				}else {
					return objects[4].toString();
				}
			}
		}
		Object[] objects = new Object[4];
		objects[0] = projectUnivCode;
		objects[1] = projectUnivName;
		objects[2] = projectName;
		objects[3] = projectDirector;
		notFindProject.add(objects);
		return null;
	}
	
	/**
	 * 判断EXCEL中的专家学校、专家学校代码、专家姓名、专家编号是否和数据库一致
	 * @param revUnivName 专家学校代码
	 * @param revUnivCode 专家学校
	 * @param revName 专家姓名
	 * @param revNum 专家编号
	 * @return 如果和数据库中的一致，则返回该专家ID
	 */
	public String findReviewer(String revUnivName,String revUnivCode,String revName,Integer revNum) {
		List<Object[]> reviewerInfos = expertNum2Infos.get(revName);
		if (reviewerInfos == null) {
			Object[] objects = new Object[4];
			objects[0] = revUnivName;
			objects[1] = revUnivCode;
			objects[2] = revName;
			objects[3] = revNum;
			notFindExpert.add(objects);
			return null;
		}
		/**
		 * 1.通过专家姓名和专家编号进行查找，如果找到而且该专家属于参评专家，则返回该专家；
		 * 2.通过专家姓名和专家编号进行查找，如果找到而且该专家属于退评专家，记录该专家为退评专家；
		 * 3.通过专家姓名和专家编号进行查找，如果没找到该专家，记录该专家为未找到专家
		 * 4.特殊：通过专家姓名、专家所在高校进行二次查找，如果同名同校的专家数为1，且专家为参评状态，则默认找到该专家；如果该专家为退评状态，则记录该专家为退评专家
		 * 如果同名同校专家数大于1，则默认为未找到专家，则记录该专家为未找到专家
		 */
		for (Object[] objects : reviewerInfos) {
			if (objects[2] != null && objects[2].equals(revName) && objects[3] != null && Integer.parseInt(objects[3].toString()) == revNum) {
				if ((Integer)objects[5] == 0) {
					notReviewExpert.add(objects);
					return null;
				}else {
					return objects[4].toString();		
				}
			}
		}
		List<Object[]> currentSuitReviewerList = new ArrayList<Object[]>();
		for (Object[] objects : reviewerInfos) {
			if (objects[0] != null && objects[0].equals(revUnivName) && objects[2] != null && objects[2].equals(revName)) {
				currentSuitReviewerList.add(objects);
			}
		}
		if (currentSuitReviewerList.size() == 1) {
			Object[] objects = currentSuitReviewerList.get(0);
			if ((Integer)objects[5] == 0) {
				notReviewExpert.add(objects);
				return null;
			}else {
				return objects[4].toString();		
			}
		}
		Object[] objects = new Object[4];
		objects[0] = revUnivName;
		objects[1] = revUnivCode;
		objects[2] = revName;
		objects[3] = revNum;
		notFindExpert.add(objects);
		return null;
	}
	
	public General2ReviewerManualChange2014(){
	}
	
	public General2ReviewerManualChange2014(String file) {
		reader = new PoiExcelReader(file);
	}
}
