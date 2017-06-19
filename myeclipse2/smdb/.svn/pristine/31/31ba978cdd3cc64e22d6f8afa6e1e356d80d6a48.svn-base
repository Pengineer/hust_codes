package csdc.tool.execution.fix;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.bean.Agency;
import csdc.bean.Institute;
import csdc.bean.SystemOption;
import csdc.tool.execution.finder.InstituteFinder;
import csdc.tool.execution.finder.UniversityFinder;
import csdc.tool.execution.importer.Importer;

/**
 * 修正部级基地、省部共建基地的学科门类
 * @author xuhan
 *
 */
public class Fix20120228 extends Importer {
	
	@Autowired
	private InstituteFinder instituteFinder;

	@Autowired
	private UniversityFinder universityFinder;
	
	public Fix20120228() {}
	
	public Fix20120228(File file) {
		super(file);
	}

	@Override
	protected void work() throws Throwable {
		
		List<SystemOption> disciplineTypes = dao.query("select so.name from SystemOption so where so.systemOption.id = 'disciplineType'");

		getContentFromExcel(0);
		while (next()) {
			System.out.println(curRowIndex);
			if (F.isEmpty()) {
				continue;
			}
			
			Agency university = universityFinder.getUnivByName(A);
			Institute institute = instituteFinder.getInstitute(university, B, false);

			if (!disciplineTypes.contains(F)) {
				throw new RuntimeException("非法的学科门类:" + F);
			}
			institute.setDisciplineType(F);
		}

		
	}

}
