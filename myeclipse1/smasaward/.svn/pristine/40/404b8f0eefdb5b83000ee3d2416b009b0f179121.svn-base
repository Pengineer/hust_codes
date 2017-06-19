package csdc.tool.execution.importer;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import csdc.bean.University;

@SuppressWarnings("unchecked")
public class GeneralApplication2011Importer extends Importer {
	
	/**
	 * 项目编号到项目实体的映射
	 */
	private Map<String, Project> idProjectMap = new HashMap<String, Project>();
	
	private List dataList = new ArrayList();


	public GeneralApplication2011Importer(File file) {
		super(file);
	}
	
	
	public void work() throws Exception {
		getContentFromExcel(0);
		
		StringBuffer exceptionMsg = new StringBuffer();
		
		for (String[] row : content) {
			if (row[6].isEmpty()) {
				continue;
			}
			University university = tools.getUnivByCode(row[6]);
			if (university == null) {
				exceptionMsg.append(row[6] + " " + row[7] + " 找不到\n");
				continue;
			}
			
			Project project = new Project();

			project.setId(row[2]);
			project.setAddDate(Tools.getDate(row[3]));
			project.setAuditStatus(row[4]);
			project.setFile(row[5]);
			project.setUniversityCode(row[6]);
			project.setUniversityName(row[7]);
			project.setProjectName(row[8]);
			project.setProjectType(row[9]);
			project.setDisciplineType(row[10]);
			project.setApplyDate(Tools.getDate(row[11]));
			project.setDiscipline(tools.transformDisc(row[12]));
			project.setResearchType(row[13]);
			project.setPlanEndDate(Tools.getDate(row[14]));
			project.setApplyFee(row[15]);
			project.setOtherFee(row[16]);
			project.setFinalResultType(row[17]);
			project.setDirector(row[18]);
			project.setGender(row[19]);
			project.setBirthday(Tools.getDate(row[20]));
			project.setTitle(row[21]);
			project.setDepartment(row[22]);
			project.setJob(row[23]);
			project.setEducation(row[24]);
			project.setDegree(row[25]);
			project.setForeign(row[26]);
			project.setAddress(row[27]);
			project.setPostcode(row[28]);
			project.setPhone(row[29]);
			project.setMobile(row[30]);
			project.setEmail(row[31]);
			project.setIdcard(row[32]);
			project.setMembers(row[33]);

			project.setIsReviewable(1);
			project.setYear(2011);

			idProjectMap.put(project.getId(), project);
			dataList.add(project);
		}
		
		getContentFromExcel(1);

		for (String[] row : content) {
			String projectId = row[2];
			Project project = idProjectMap.get(projectId);
			project.setWarning(row[0] + " —— " + row[1]);
		}
		
		if (exceptionMsg.length() > 0) {
			throw new RuntimeException(exceptionMsg.toString());
		}
		
		baseService.addOrModify(dataList);
	}

}
