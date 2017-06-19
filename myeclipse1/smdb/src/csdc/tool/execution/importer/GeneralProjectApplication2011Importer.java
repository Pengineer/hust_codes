package csdc.tool.execution.importer;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import csdc.bean.Academic;
import csdc.bean.Agency;
import csdc.bean.Department;
import csdc.bean.GeneralApplication;
import csdc.bean.GeneralMember;
import csdc.bean.Person;
import csdc.bean.SystemOption;
import csdc.bean.Teacher;

/**
 * 导入《2011年一般项目申请一览表_修正导入.xls》
 * @author xuhan
 *
 */
public class GeneralProjectApplication2011Importer extends Importer {
	
	/**
	 * 项目子类名称到项目子类实体的映射
	 */
	private Map<String, SystemOption> subTypeMap = new HashMap<String, SystemOption>();

	/**
	 * 研究类型名称到研究类型实体的映射
	 */
	private Map<String, SystemOption> researchTypeMap = new HashMap<String, SystemOption>();
	
	
	
	public GeneralProjectApplication2011Importer(File file) {
		super(file);
		
		subTypeMap.put("青年基金项目", (SystemOption) baseService.query("select so from SystemOption so where so.name = '青年基金项目'").get(0));
		subTypeMap.put("规划基金项目", (SystemOption) baseService.query("select so from SystemOption so where so.name = '规划基金项目'").get(0));
		subTypeMap.put("自筹经费项目", (SystemOption) baseService.query("select so from SystemOption so where so.name = '自筹经费项目'").get(0));
		
		researchTypeMap.put("基础研究", (SystemOption) baseService.query("select so from SystemOption so where so.name = '基础研究'").get(0));
		researchTypeMap.put("实验与发展", (SystemOption) baseService.query("select so from SystemOption so where so.name = '实验与发展'").get(0));
		researchTypeMap.put("应用研究", (SystemOption) baseService.query("select so from SystemOption so where so.name = '应用研究'").get(0));
	}
	
	@Override
	public void work() throws Exception {
		testUniversityNames();
		importData();
	}
	
	public void importData() throws Exception {
		getContentFromExcel(0);
		
		while (next()) {
			System.out.println(curRowIndex);
			
			String projectName = G;
			String applicantName = Q;
			if (applicantName.isEmpty()) {
				applicantName = "#暂无信息#";
			}
			String univName = F;
			String univCode = E;
			String departmentName = U;
			Agency university = tools.getUnivByName(univName);
			if (university == null) {
				university = tools.getUnivByCode(univCode);
			}

			Teacher teacher = tools.getTeacher(applicantName, university);
			Person applicant = teacher.getPerson();
			Academic academic = tools.getAcademic(applicant);
			Department department = teacher.getDepartment();
			if (departmentName.length() > 0 && !departmentName.equals(department.getName())) {
				department = new Department();
				department.setName(departmentName);
				department.setUniversity(university);
				teacher.setDepartment(department);

				saveOrUpdate(department, true);
			}

			//项目申请
			GeneralApplication ga = new GeneralApplication();
			ga.setIsImported(1);
			ga.setImportedDate(new Date());
			ga.setDepartment(department);
			ga.setDivisionName(department.getName());
			ga.setYear(2011);
			ga.setUniversity(university);
			ga.setAgencyName(university.getName());
			ga.setSubtype(subTypeMap.get(H.replaceAll("\\s+", "")));
			ga.setName(projectName);
			ga.setApplicant(applicant);
			ga.setApplicantName(applicantName);
			ga.setDiscipline(tools.transformDisc(K));
			ga.setPlanEndDate(tools.getDate(M));
			
			String[] productType = tools.getNormalizedProductType(P);
			ga.setProductType(productType[0]);
			ga.setProductTypeOther(productType[1]);

			ga.setApplyFee(tools.getFee(N));
			ga.setOtherFee(tools.getFee(O));
			ga.setFile(D);
			ga.setDisciplineType(I);
			ga.setApplicantSubmitDate(tools.getDate(J));
			ga.setResearchType(researchTypeMap.get(L));

			//申请人
			applicant.setGender(R);
			applicant.setBirthday(tools.getDate(S));
			applicant.setOfficeAddress(Z);
			applicant.setOfficePostcode(AA);
			applicant.setOfficePhone(tools.mergePhoneNumber(applicant.getOfficePhone(), AB));
			applicant.setMobilePhone(tools.mergePhoneNumber(applicant.getMobilePhone(), AC));
			applicant.setEmail(AD);
			applicant.setIdcardNumber(AE.replaceAll("^\\D+", ""));
			
			//教师
			if (V.length() > 0 && !V.equals("无")) {
				teacher.setPosition(V);
			}

			//学术信息
			academic.setDiscipline(tools.transformDisc(I + " " + K + " " + academic.getDiscipline()));
			academic.setSpecialityTitle(T);
			academic.setResearchField(tools.toDBC(K).replaceAll("\\([\\s\\S]*?\\)", ""));
			academic.setLastEducation(W);
			academic.setLastDegree(X);
			academic.setLanguage(Y);
			
			//项目成员
			GeneralMember gm = new GeneralMember();
			gm.setUniversity(university);
			gm.setDepartment(department);
			gm.setDivisionName(department.getName());
			gm.setApplication(ga);
			gm.setMember(applicant);
			gm.setMemberName(applicant.getName());
			gm.setAgencyName(university.getName());
			gm.setSpecialistTitle(academic.getSpecialityTitle());
			gm.setIsDirector(1);
			gm.setMemberSn(1);

			//其他成员
			List<GeneralMember> otherMembers = tools.getGeneralMemberList(ga, AF);

			saveOrUpdate(applicant);
			saveOrUpdate(teacher);
			saveOrUpdate(academic);
			saveOrUpdate(ga);
			saveOrUpdate(gm);	//负责人
			saveOrUpdate(otherMembers);	//其他成员
		}
	}
	
	/**
	 * 检测学校名合法性
	 * @throws Exception
	 */
	private void testUniversityNames() throws Exception {
		/**
		 * 不存在的学校名称
		 */
		HashSet<String> invalidUnivNames = new HashSet<String>();
		
		getContentFromExcel(0);
		Agency university = null;
		while (next()) {
			if (F.length() > 0) {
				university = tools.getUnivByName(F);
				if (university == null) {
					invalidUnivNames.add(F);
				}
			}
		}
		
		if (invalidUnivNames.size() > 0) {
			for (Iterator<String> iterator = invalidUnivNames.iterator(); iterator.hasNext();) {
				String string = (String) iterator.next();
				System.out.println("不存在的学校: " + string);
			}
			throw new Exception();
		}
		System.out.println("学校名称正常");
		
	}

}
