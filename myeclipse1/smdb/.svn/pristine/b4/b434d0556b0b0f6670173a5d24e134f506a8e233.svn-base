package csdc.tool.execution.importer.tool;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import csdc.bean.Agency;
import csdc.bean.Department;
import csdc.bean.Expert;
import csdc.bean.Institute;
import csdc.bean.Teacher;
import csdc.tool.execution.finder.DepartmentFinder;
import csdc.tool.execution.finder.ExpertFinder;
import csdc.tool.execution.finder.InstituteFinder;
import csdc.tool.execution.finder.UnivPersonFinder;
import csdc.tool.execution.finder.UniversityFinder;

/**
 * 解析形如 姓名1（机构名）, 姓名2（机构名） 的成员格式
 * 
 * 注意，可能出现如下情况:
 * 张三(原名张四)(A大学(B学院)), ......
 * 则按如下解析：
 * 		姓名:张三(原名张四)
 * 		机构名:A大学(B学院)
 * 
 * @author xuhan
 */
@Component
public class GeneralApplicationMemberParser {
	
	@Autowired
	private UniversityFinder universityFinder;

	@Autowired
	private UnivPersonFinder univPersonFinder;
	
	@Autowired
	private InstituteFinder instituteFinder;

	@Autowired
	private DepartmentFinder departmentFinder;

	@Autowired
	private ExpertFinder expertFinder;
	
	
	/**
	 * 解析形如 姓名1（机构名）, 姓名2（机构名） 的成员格式
	 * 
	 * 注意，可能出现如下情况:
	 * 张三(原名张四)(A大学(B学院)), ......
	 * 则按如下解析：
	 * 		姓名:张三(原名张四)
	 * 		机构名:A大学(B学院)
	 * @param input
	 * @return 元素类型为Teacher或Expert的List
	 * @throws Exception 
	 */
	public List getMembers(String memberString) throws Exception {
		List result = new ArrayList();
//		String[] memberInfos = (memberString + ",").split("[\\)）][,，]\\s*");   //以“），”分割
		//解析形如 姓名1（机构名）姓名2（机构名） 的成员格式
		memberString = memberString.replace("））", "@）"); 
		memberString = memberString.replace(")）", "@）"); 
		memberString = memberString.replace("))", "@）"); 
		memberString = memberString.replace("）)", "@）");
		String[] memberInfos = (memberString).split("[\\)）][,，]?\\s*");
		for (String memberInfo : memberInfos) {
			memberInfo = memberInfo.replace("@", "）");
			int splitIndex = -1;
			int stackHeight = 1;
			for (int i = memberInfo.length() - 1; i >= 0; i--) {
				if (memberInfo.charAt(i) == ')' || memberInfo.charAt(i) == '）') {
					++stackHeight;
				} else if (memberInfo.charAt(i) == '(' || memberInfo.charAt(i) == '（') {
					--stackHeight;
				}
				if (stackHeight == 0) {
					splitIndex = i;
					break;
				}
			}
			if (splitIndex >= 0) {
				String memberName = memberInfo.substring(0, splitIndex);
				String unitName = memberInfo.substring(splitIndex + 1);
				if (null==memberName || memberName.isEmpty()) {
					System.out.println("出现null！！！");
				}

				Agency memberUniv = null;
				Department memberDept = null;
				Institute memberInst = null;
				String divisionName = null;
				for (int len = unitName.length(); memberUniv == null && len >= 1; len--) {
					for (int j = 0; memberUniv == null && j + len <= unitName.length(); j++) {
						memberUniv = universityFinder.getUnivByName(unitName.substring(j, j + len));
						divisionName = unitName.substring(j + len);
					}
				}
				
				if (memberUniv == null) {
					Expert member = expertFinder.findExpert(memberName, unitName);
					result.add(member);
				} else {
					memberInst = instituteFinder.getInstitute(memberUniv, divisionName, false);
					if (memberInst == null) {
						memberDept = departmentFinder.getDepartment(memberUniv, divisionName, true);
					}
					Teacher member = univPersonFinder.findTeacher(memberName, memberUniv, memberDept, memberInst);
					result.add(member);
				}
			}
		}
		return result;
	}
	
}
