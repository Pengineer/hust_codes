package csdc.tool.execution.importer;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import csdc.bean.Expert;
import csdc.bean.Project;
import csdc.bean.University;

/**
 * 导入《20111026_2012一般申报项目-(学校通过、主管单位通过)共33955条_修正导入.xls》
 * @author xuhan
 *
 */
@SuppressWarnings("unchecked")
public class GeneralApplication2012Importer extends Importer {
	
	/**
	 * 项目编号到项目实体的映射
	 */
	private Map<String, Project> idProjectMap = new HashMap<String, Project>();
	
	/**
	 * 学校编号+姓名 到 专家实体 的映射
	 */
	private Map<String, Expert> expertMap = new HashMap<String, Expert>();

	
	public GeneralApplication2012Importer(File file) {
		super(file);
	}
	
	@Override
	public void work() throws Exception {
		checkUniversity();
		initIdProjectMap();
		initExpertMap();
		importData();
	}

	
	
	private void checkUniversity() throws Exception {
		Set<String> invalicUniversityName = new HashSet<String>();
		
		getContentFromExcel(0);
		
		while (next()) {
			University university = tools.getUnivByName(F);
			if (university == null) {
				invalicUniversityName.add(F);
			}
		}
		if (invalicUniversityName.size() > 0) {
			for (Iterator iterator = invalicUniversityName.iterator(); iterator.hasNext();) {
				System.out.println("学校不存在: " + iterator.next());
			}
			throw new RuntimeException();
		}
	}

	private void initIdProjectMap() {
		List<Project> projects = baseService.query("select p from Project p where p.year = 2012");
		for (Project project : projects) {
			idProjectMap.put(project.getId(), project);
		}
	}
	
	/**
	 * 初始化expertMap
	 */
	private void initExpertMap() {
		List<Expert> experts = baseService.query("select e from Expert e");
		for (Expert expert : experts) {
			expertMap.put(expert.getUniversityCode() + expert.getName(), expert);
		}
	}


	public void importData() throws Exception {
		getContentFromExcel(0);
		
		while (next()) {
			University university = tools.getUnivByName(F);
			
			Project project = idProjectMap.get(A);
			if (project == null) {
				project = new Project();
			}

			project.setId(A);
			project.setAddDate(Tools.getDate(B));
			project.setAuditStatus(C);
			project.setFile(D);
			project.setUniversityCode(university.getCode());
			project.setUniversityName(university.getName());
			project.setProjectName(G);
			project.setProjectType(H);
			project.setDisciplineType(I);
			project.setApplyDate(Tools.getDate(J));
			project.setDiscipline(tools.transformDisc(K));
			project.setResearchType(L);
			project.setPlanEndDate(Tools.getDate(M));
			project.setApplyFee(N);
			project.setOtherFee(O);
			project.setFinalResultType(P);
			project.setDirector(Q);
			project.setGender(R);
			project.setBirthday(Tools.getDate(S));
			project.setTitle(T);
			project.setDepartment(U);
			project.setJob(V);
			project.setEducation(W);
			project.setDegree(X);
			project.setForeign(Y);
			project.setAddress(Z);
			project.setPostcode(AA);
			project.setPhone(AB);
			project.setMobile(AC);
			project.setEmail(AD);
			project.setIdcard(AE);
			project.setMembers(AF);

			project.setIsReviewable(1);
			project.setYear(2012);

			idProjectMap.put(project.getId(), project);
			addEntity(project);
			
			//为申请人添加2012申请年份
			Expert applicant = expertMap.get(university.getCode() + Q.replaceAll("\\s+", ""));
			if (applicant != null) {
				//合并过往申请年份
				List<String> ay = new ArrayList<String>(new TreeSet<String>(Arrays.asList((applicant.getGeneralApplyYears() + " 2012").split("\\D+"))));
				String newAy = "";
				for (String string : ay) {
					if (string.length() > 0) {
						newAy += (newAy.isEmpty() ? "" : "; ") + string;
					}
				}
				applicant.setGeneralApplyYears(newAy);
				addEntity(applicant);
			}
		}
	}

}
