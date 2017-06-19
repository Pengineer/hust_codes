package csdc.tool.execution.fix;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.bean.Agency;
import csdc.bean.Department;
import csdc.bean.GeneralApplication;
import csdc.bean.GeneralGranted;
import csdc.bean.GeneralMember;
import csdc.bean.Teacher;
import csdc.tool.execution.finder.DepartmentFinder;
import csdc.tool.execution.finder.GeneralProjectFinder;
import csdc.tool.execution.finder.UniversityFinder;
import csdc.tool.execution.importer.Importer;
import csdc.tool.reader.ExcelReader;

/**
 * 检查2007、2008、2009年一般项目学校名称和学校代码不匹配的情况
 * 并修复
 * 
 * @author xuhan
 */
public class FixGeneralApplicationUniv070809 extends Importer {
	
	private ExcelReader excelReader2007;
	private ExcelReader excelReader2008;
	private ExcelReader excelReader2009;
	
	@Autowired
	private UniversityFinder universityFinder;
	
	@Autowired
	private DepartmentFinder departmentFinder;
	
	@Autowired
	private GeneralProjectFinder generalProjectFinder;
	
	/**
	 * list中每个元素:
	 * 0:申报
	 * 1:应改为的正确学校
	 * 2:原来的错误学校
	 */
	private List<Object[]> list = new ArrayList<Object[]>();
	
	public void work() throws Exception {
		findErrorApplications();
		fix();
	}


	/**
	 * 修复有问题的项目的相关数据
	 */
	private void fix() {
		for (Object[] objects : list) {
			GeneralApplication application = (GeneralApplication) objects[0];
			Agency univ = (Agency) objects[1];
			Agency wrongUniv = (Agency) objects[2];
			Department dept = departmentFinder.getDepartment(univ, application.getDepartment().getName(), true);
			
			//application
			application.setUniversity(univ);
			application.setDepartment(dept);
			application.setAgencyName(univ.getName());
			
			//granted
			GeneralGranted granted = application.getGranted().isEmpty() ? null : application.getGranted().iterator().next();
			if (granted != null) {
				granted.setUniversity(univ);
				granted.setDepartment(dept);
				granted.setAgencyName(univ.getName());
			}
			
			//member
			Iterator<GeneralMember> iter = application.getMember().iterator();
			GeneralMember member = null;
			while (member == null || member.getIsDirector() == 0) {
				//找到是负责人的那个成员
				member = iter.next();
			}
			member.setUniversity(univ);
			member.setDepartment(dept);
			member.setAgencyName(univ.getName());
			
			//teacher
			for (Teacher teacher : member.getMember().getTeacher()) {
				if (teacher.getDepartment() != null && teacher.getDepartment().getUniversity().getId().equals(wrongUniv.getId())) {
					if (teacher.getInstitute() != null) {
						System.out.println(application.getYear() + " " + application.getName());
						throw new RuntimeException("该教师属于基地");
					}
					teacher.setDepartment(dept);
					teacher.setUniversity(univ);
					teacher.setAgencyName(univ.getName());
				}
			}
		}
	}
	
	/**
	 * 查找有问题的项目
	 * @throws Exception
	 */
	private void findErrorApplications() throws Exception {
		excelReader2007.readSheet(0);
		while (next(excelReader2007)) {
			String projectName = C;
			String applicantName = B;
			String name = A;
			String code = O;
			Agency univByName = universityFinder.getUnivByName(name);
			Agency univByCode = universityFinder.getUnivByName(code);
			if (univByName != null && univByCode != null && !univByName.getId().equals(univByCode.getId())) {
				GeneralApplication application = generalProjectFinder.findApplication(projectName, applicantName, 2007);
				System.out.println("2007 [" + application.getUniversity().getName() + " " + application.getUniversity().getCode() + "] [" + univByName.getName() + " " + univByName.getCode() + "] " + "(" + applicantName + ")" + projectName);
				list.add(new Object[] {
					application,
					univByName,
					univByCode
				});
			}
		}
		
		excelReader2008.readSheet(0);
		while (next(excelReader2008)) {
			String projectName = C;
			String applicantName = H;
			String name = B;
			String code = A;
			Agency univByName = universityFinder.getUnivByName(name);
			Agency univByCode = universityFinder.getUnivByName(code);
			if (univByName != null && univByCode != null && !univByName.getId().equals(univByCode.getId())) {
				GeneralApplication application = generalProjectFinder.findApplication(projectName, applicantName, 2008);
				System.out.println("2008 [" + application.getUniversity().getName() + " " + application.getUniversity().getCode() + "] [" + univByName.getName() + " " + univByName.getCode() + "] " + "(" + applicantName + ")" + projectName);
				list.add(new Object[] {
					application,
					univByName,
					univByCode
				});
			}
		}
		
		excelReader2009.readSheet(0);
		while (next(excelReader2009)) {
			String projectName = G;
			String applicantName = Q;
			String name = F;
			String code = E;
			Agency univByName = universityFinder.getUnivByName(name);
			Agency univByCode = universityFinder.getUnivByName(code);
			if (univByName != null && univByCode != null && !univByName.getId().equals(univByCode.getId())) {
				GeneralApplication application = generalProjectFinder.findApplication(projectName, applicantName, 2009);
				System.out.println("2009 [" + application.getUniversity().getName() + " " + application.getUniversity().getCode() + "] [" + univByName.getName() + " " + univByName.getCode() + "] " + "(" + applicantName + ")" + projectName);
				list.add(new Object[] {
					application,
					univByName,
					univByCode
				});
			}
		}	
	}

	
	public FixGeneralApplicationUniv070809() {
	}

	public FixGeneralApplicationUniv070809(String filePath1, String filePath2, String filePath3) {
		excelReader2007 = new ExcelReader(filePath1);
		excelReader2008 = new ExcelReader(filePath2);
		excelReader2009 = new ExcelReader(filePath3);
	}
}
