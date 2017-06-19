package csdc.tool.execution.importer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import csdc.bean.Instp;
import csdc.bean.University;

/**
 * 2011年基地项目导入器
 * @author xuhan
 *
 */
@SuppressWarnings("unchecked")
public class InstpApplication2011Importer extends Importer {
	
	private List dataList = new ArrayList();


	public InstpApplication2011Importer(File file) {
		super(file);
	}
	
	
	public void work() throws Exception {
		getContentFromExcel(1);
		
		StringBuffer exceptionMsg = new StringBuffer();
		
		String disciplineType = null;
		String universityName = null;
		String instituteName = null;
		Integer id = 201100001;
		
		for (String[] row : content) {
			getRowData(row);

			if (B != null && !B.isEmpty()) {
				disciplineType = B;
			}
			if (C != null && !C.isEmpty()) {
				universityName = C;
			}
			if (D != null && !D.isEmpty()) {
				instituteName = D;
			}

			University university = tools.getUnivByName(universityName);
			if (university == null) {
				exceptionMsg.append(universityName + " 找不到\n");
				continue;
			}
			
			
			Instp project = new Instp();
			
			project.setId("inst_" + (id++));
			project.setDisciplineType(disciplineType);
			project.setUniversityName(universityName);
			project.setUniversityCode(university.getCode());
			project.setInstituteName(instituteName);
			project.setProjectName(F);
			project.setDisciplineDirection(G);
			project.setDiscipline(tools.transformDisc(G + " " + H));
			project.setDirector(I);
			project.setDirectorUniversity(J);
			project.setTitle(K);
			project.setNote(L);
			
			project.setIsReviewable(1);
			project.setYear(2011);

			dataList.add(project);
		}
		
		if (exceptionMsg.length() > 0) {
			throw new RuntimeException(exceptionMsg.toString());
		}
		
		baseService.addOrModify(dataList);
	}

}
