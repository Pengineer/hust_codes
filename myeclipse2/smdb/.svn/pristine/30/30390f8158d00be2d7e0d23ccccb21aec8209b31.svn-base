package csdc.tool.execution.fix;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import csdc.bean.Academic;
import csdc.bean.Agency;
import csdc.bean.Department;
import csdc.bean.Institute;
import csdc.bean.Person;
import csdc.bean.Student;
import csdc.bean.Teacher;
import csdc.dao.HibernateBaseDao;
import csdc.tool.execution.Execution;

/**
 * 修正学术表人员的专业职称，统一采用国标（GBT8561-2001）规范化
 * 1、完善专业职称描述为：教授 -> 011/教授
 * 2、专业职称为博士后、博士研究生、硕士研究生、研究生班的教师表记录转移至学生表，
 * 3、从教师表删除转移的教师记录
 * @author fengcl
 */
@Component
public class FixSpecialityTitle extends Execution {

	@Autowired
	private HibernateBaseDao dao;
	
	private Map<String, String> titleMap = null;
	
	/**
	 * 待完善的Student实体
	 * 学生映射：(人员ID + [学校ID] + [院系ID] + [基地ID]) -> Student实体
	 */
	private Map<String, Student> studentMap = null;
	
	/**
	 * 待修正人员的PersonIds
	 */
	private List<String> personIds = new ArrayList<String>();
	
	/**
	 * 待排除人员的PersonIds
	 */
	private List<String> outPersonIds = null;
	
	/**
	 * 待删除的教师
	 */
	private List<Teacher> teachers2Del = new ArrayList<Teacher>();

	@Override
	protected void work() throws Throwable {
		
		initTitleMap();
		
		List<String> titles = new ArrayList<String>();
		for (Entry<String, String> title : titleMap.entrySet()) {
			if(!title.getKey().equals(title.getValue())){
				titles.add(title.getKey());
			}
		}
		HashMap parMap = new HashMap();
		parMap.put("titles", titles);
		List<Academic> acs = dao.query("select ac from Academic ac where ac.specialityTitle is not null and ac.specialityTitle in (:titles)", parMap);
		
		Map<String, String> pId2TitleMap = new HashMap<String, String>();//personId 到 专业职称的映射
		String title = null;	//原职称
		String newTitle = null;	//新职称
		//1、更新专业职称
		for (Academic ac : acs) {
			title = ac.getSpecialityTitle(); 
			//职称为博士后、博士研究生、硕士研究生、研究生班 的人员从教师表调整至学生表
			if (title != null && (title.contains("研究生") || title.contains("博士后")) && !getOutPersonIds().contains(ac.getPerson().getId())) {
				pId2TitleMap.put(ac.getPerson().getId(), title);
				personIds.add(ac.getPerson().getId());
				ac.setSpecialityTitle(null);
			}else{
				newTitle = getNewTitle(title);
				if (newTitle != null && !newTitle.equals(title)) {
					ac.setSpecialityTitle(newTitle);
				}else if(newTitle == null){
					System.out.println(title);
				}
			}
		}
		System.out.println("待处理的人员数量：" + personIds.size());// 共2300条

		//2、完善Student表：将Teacher表中属于学生的数据转移至Student表
		adjustTeachers2Students(pId2TitleMap);
		
		//3、删除教师
		System.out.println("教师（删除）：" + teachers2Del.size());//2410条
		for (Teacher teacher : teachers2Del) {
			dao.delete(teacher);
		}
	}
	
	/**
	 * 更新学生表的信息
	 * @param personId
	 * @param ac
	 */
	private void adjustTeachers2Students(Map<String, String> pId2TitleMap) {
		// 找出当前personId对应的教师信息
		List<Object[]> objs = getTeachers(personIds);
		
		System.out.println("待转移的教师数量：" + objs.size());
		
		for (Object[] obj : objs) {
			Teacher teacher = (Teacher) obj[0];			//教师实体
			Agency university = (Agency) obj[1];		//所属高校
			Department department = (Department) obj[2];//所属院系
			Institute institute = (Institute) obj[3];	//所属基地
			Person person = (Person) obj[4];			//人员实体
			
			// 组装key：人员ID + [学校ID] + [院系ID] + [基地ID]
			String key = person.getId();
			if (university != null) {
				key += university.getId(); 
			}
			if (department != null) {
				key += department.getId();
			}
			if (institute != null) {
				key += institute.getId();
			}
			Student student = findStudent(key);
			if (student == null) {
				student = new Student();
				student.setPerson(person);
				student.setUniversity(university);
				student.setDepartment(department);
				student.setInstitute(institute);
				student.setStartDate(teacher.getStartDate());
				student.setEndDate(teacher.getEndDate());
				// 完善学生类型信息
				String title = pId2TitleMap.get(person.getId());
				if (title.contains("博士研究生")) {
					student.setType("博士生");
				}else if (title.contains("博士后")) {
					student.setType("博士后");
				}else if(title.contains("研究生班")){
					student.setType("研究生");
				}else {
					student.setType("硕士生");
				}
				dao.add(student);
			}
			
			// 暂存待删除的教师
			teachers2Del.add(teacher);
		}
	}
	
	/**
	 * 获取所有要修正的人员ID
	 * @return
	 */
	private List<String> getOutPersonIds(){
		// 这几个人特殊处理，不需转移至学生表
		if(outPersonIds == null){
			outPersonIds = new ArrayList<String>();
			outPersonIds.add("4028d88a2d354302012d3546c4b56907");	outPersonIds.add("4028d89231523c1101315258ae6c11d6");
			outPersonIds.add("4028d89231523c110131524d3857673f");	outPersonIds.add("4028d89231523c11013152535d281c9b");
			outPersonIds.add("4028d89231523c1101315256a56d1d0b");	outPersonIds.add("4028d89231523c110131525917d4294e");
		}
		return outPersonIds;
	}
	
	/**
	 * 获取所有待转移至学生表的教师记录
	 * @return
	 */
	private List<Object[]> getTeachers(List<String> personIds){
		List<List<String>> allPIds = new ArrayList<List<String>>();
		// 所有要修正的人员ID
        int size = personIds.size();
		int n = size / 999;
        for (int i = 0; i < n; i++) {
        	allPIds.add(personIds.subList(i * 999, (i + 1) * 999));
		}
        allPIds.add(personIds.subList(n * 999, size));
        
        List<Object[]> teachers = new ArrayList<Object[]>();
        HashMap parMap = new HashMap();
        for (List<String> pIds : allPIds) {
        	parMap.put("pIds", pIds);
        	List<Object[]> objs = dao.query("select t, ag, dept, inst, p from Teacher t left join t.university ag left join t.department dept left join t.institute inst left join t.person p where t.position is null and p.id in (:pIds)", parMap);
        	teachers.addAll(objs);
		}
        
        return teachers;
	}
	
	/**
	 * 初始化studentMap
	 */
	private void initStudentMap(){
		studentMap = new HashMap<String, Student>();
		
		// 所有要修正的人员ID
		List<Student> sts = dao.query("select st from Student st");
		for (Student student : sts) {
			//只保留要修正的人员对应的Student
			if (personIds.contains(student.getPerson().getId())) {
				String key = student.getPerson().getId();
				if (student.getUniversity() != null) {
					key += student.getUniversity().getId(); 
				}
				if (student.getDepartment() != null) {
					key += student.getDepartment().getId();
				}
				if (student.getInstitute() != null) {
					key += student.getInstitute().getId();
				}
				studentMap.put(key, student); 
			}
		}
	}
	
	/**
	 * 根据key找到Student
	 * @param key
	 * @return
	 */
	private Student findStudent(String key){
		if (studentMap == null) {
			initStudentMap();
		}
		return studentMap.get(key);
	}
	
	/**
	 * 初始化专业职称Map
	 */
	private void initTitleMap(){
		titleMap = new HashMap<String, String>();
		
		titleMap.put("高级统计师", "142/高级统计师");				titleMap.put("高级经济师", "122/高级经济师");	
		titleMap.put("高级政工师", "982/高级政工师");				titleMap.put("高级工程师", "082/高级工程师");
		titleMap.put("高级审计师", "682/高级审计师");				titleMap.put("高级实验师", "072/高级实验师");		
		titleMap.put("高级农艺师", "092/高级农艺师");				titleMap.put("高级会计师", "132/高级会计师");
		titleMap.put("高级兽医师", "102/高级兽医师");  			titleMap.put("统计师", "143/统计师");					
		titleMap.put("高工", "082/高级工程师");					titleMap.put("工程师", "083/工程师");
		titleMap.put("经济师", "123/经济师");					titleMap.put("实验师", "073/实验师");
		titleMap.put("助理经济师", "124/助理经济师");				titleMap.put("助理工程师", "084/助理工程师");
		titleMap.put("农艺师", "093/农艺师");					titleMap.put("会计师", "133/会计师");
		titleMap.put("高级记者", "191/高级记者");				titleMap.put("记者", "193/记者");
		titleMap.put("译审", "181/译审");						titleMap.put("编审", "151/编审");
		titleMap.put("副译审", "182/副译审");					titleMap.put("副编审", "152/副编审");
		titleMap.put("主管药师", "243/主管药师");				titleMap.put("主管护师", "253/主管护师");
		titleMap.put("主管技师", "263/主管技师");				titleMap.put("主治医师", "233/主治医师");
		titleMap.put("主任编辑", "主任编辑");					titleMap.put("主任护师", "251/主任护师");
		titleMap.put("副主任药师", "242/副主任药师");				titleMap.put("副主任护师", "252/副主任护师");
		titleMap.put("副主任技师", "262/副主任技师");				titleMap.put("副主任医师", "232/副主任医师");
		titleMap.put("主任医师", "231/主任医师");				titleMap.put("一级演员", "281/一级演员");
		titleMap.put("翻译", "183/翻译");						titleMap.put("一级律师", "391/一级律师");
		titleMap.put("二级律师", "392/二级律师");				titleMap.put("三级律师", "393/三级律师");
		titleMap.put("四级律师", "394/四级律师");				titleMap.put("实验师; 讲师（高校）", "073/实验师");
		titleMap.put("正高级; 教授", "011/教授");				titleMap.put("教授", "011/教授");
		titleMap.put("副教授; 副编审", "152/副编审");				titleMap.put("副教授", "012/副教授");
		titleMap.put("讲师", "013/讲师（高校）");				titleMap.put("讲师（高校）", "013/讲师（高校）");
		titleMap.put("助教", "014/助教（高校）");				titleMap.put("助教（高校）", "014/助教（高校）");
		titleMap.put("研究员", "621/研究员（社会科学）");			titleMap.put("研究员（社会科学）", "621/研究员（社会科学）");
		titleMap.put("副研究员", "622/副研究员（社会科学）");		titleMap.put("副研究员（社会科学）", "622/副研究员（社会科学）");
		titleMap.put("助理研究员", "623/助理研究员（社会科学）");	titleMap.put("助理研究员（社会科学）", "623/助理研究员（社会科学）");
		titleMap.put("研究实习员", "624/研究实习员（社会科学）");	titleMap.put("研究实习员（社会科学）", "624/研究实习员（社会科学）");
		titleMap.put("研究馆员", "651/研究馆员（文博）");			titleMap.put("研究馆员（文博）", "651/研究馆员（文博）");
		titleMap.put("副研究馆员", "652/副研究馆员（文博）");		titleMap.put("副研究馆员（文博）", "652/副研究馆员（文博）");
		titleMap.put("馆员", "653/馆员（文博）");				titleMap.put("馆员（文博）", "653/馆员（文博）");	
		titleMap.put("正高级", "011/教授");						titleMap.put("高级", "012/副教授");
		titleMap.put("中级", "013/讲师（高校）");				titleMap.put("辅助人员", "624/研究实习员（社会科学）");
		titleMap.put("初级Ⅰ", "014/助教（高校）");				titleMap.put("政工员", "985/政工员");	
		titleMap.put("153/编辑", "153/编辑");					titleMap.put("154/助理编辑", "154/助理编辑");
		titleMap.put("203/编辑", "203/编辑");					titleMap.put("204/助理编辑", "204/助理编辑");
		titleMap.put("教员（中专）", "025/教员（中专）");			titleMap.put("教员（技校）", "035/教员（技校）");
		
		titleMap.put("博士研究生", null);						titleMap.put("博士后", null);
		titleMap.put("硕士研究生", null);						titleMap.put("研究生班", null);
	}

	/**
	 * 根据原职称获取新职称
	 * @param oldTitle
	 * @return
	 */
	private String getNewTitle(String oldTitle) {
		if (titleMap == null) {
			initTitleMap();
		}
		return titleMap.get(oldTitle);
	}
}
