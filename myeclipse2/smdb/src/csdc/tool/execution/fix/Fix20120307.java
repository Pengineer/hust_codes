package csdc.tool.execution.fix;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.bean.Academic;
import csdc.bean.Agency;
import csdc.bean.Department;
import csdc.bean.Person;
import csdc.bean.Teacher;
import csdc.tool.beanutil.mergeStrategy.Append;
import csdc.tool.beanutil.mergeStrategy.MergePhoneNumber;
import csdc.tool.execution.finder.DepartmentFinder;
import csdc.tool.execution.finder.UnivPersonFinder;
import csdc.tool.execution.finder.UniversityFinder;
import csdc.tool.execution.importer.Importer;
import csdc.tool.execution.importer.tool.Tool;

/**
 * 有45个academic指向同一个人，这是因为早期导入《[修改]专家表.xls》的时候，有45个人的身份证号
 * 相同，都是“无”，因此找人时出错。
 * 现在需要将这45个academic实体删除，然后重新导入这45个人的学术信息。
 * 
 * @author xuhan
 *
 */
public class Fix20120307 extends Importer {
	
	@Autowired
	private Tool tool;

	@Autowired
	private UnivPersonFinder univPersonFinder;

	@Autowired
	private DepartmentFinder departmentFinder;
	
	@Autowired
	private UniversityFinder universityFinder;
	
	@Autowired
	private Append append;
	
	@Autowired
	private MergePhoneNumber mergePhoneNumber;
	
	public Fix20120307() {}
	
	public Fix20120307(File file) {
		super(file);
	}

	@Override
	protected void work() throws Throwable {
		dao.execute("delete from Academic academic where academic.person.id = '4028d88a2d354302012d3549164750e8'");
		
		getContentFromExcel(0);
		while (next()) {
			if (!D.equals("无")) {
				continue;
			}
			System.out.println(curRowIndex);
			
			Agency university = universityFinder.getUnivByName(B);
			Department department = departmentFinder.getDepartment(university, H, true);
			Teacher teacher = univPersonFinder.findTeacher(C, department);
			
			Person person = teacher.getPerson();
			Academic academic = person.getAcademicEntity();
			
			if (!I.equals("无")) {
				teacher.setPosition(I);
			}
			
			person.setHomePhone(mergePhoneNumber.merge(person.getHomePhone(), N));
			person.setMobilePhone(mergePhoneNumber.merge(person.getMobilePhone(), O));
			person.setOfficePhone(mergePhoneNumber.merge(person.getOfficePhone(), P));
			person.setEmail(append.merge(person.getEmail(), Q));
			person.setOfficeAddress(AC);
			person.setOfficePostcode(AD);

			academic.setSpecialityTitle(G);
			academic.setPositionLevel(K);
			academic.setLastDegree(L);
			academic.setLanguage(append.merge(academic.getLanguage(), M));
			academic.setParttimeJob(R);
			academic.setDiscipline(tool.transformDisc(S + " " + T + " " + U + " " + V + " " + W + " " + X));
		}
	}

}
