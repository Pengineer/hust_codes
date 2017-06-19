package csdc.tool.tableKit.imp;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import csdc.bean.Academic;
import csdc.bean.Agency;
import csdc.bean.Department;
import csdc.bean.Expert;
import csdc.bean.Person;
import csdc.bean.SystemOption;
import csdc.bean.Teacher;
import csdc.service.IBaseService;
import csdc.tool.HSSFExport;
import csdc.tool.tableKit.ITableKit;
import csdc.tool.validator.IValidator;
import csdc.tool.validator.imp.DummyValidator;



import jxl.Cell;
import jxl.CellType;
import jxl.DateCell;
import jxl.NumberCell;
import jxl.Sheet;
import jxl.Workbook;

/**
 * 专家表工具包
 * @author 徐涵
 *
 */
@SuppressWarnings("unchecked")
public class ReviewingExpertKit implements ITableKit{

	private List<List> list;

	static private IBaseService baseService;


	static private List<String> stdTitle = new ArrayList<String>();
	static {
		stdTitle.add("学校代码");
		stdTitle.add("学校名称");
		stdTitle.add("姓名");
		stdTitle.add("身份证号码");
		stdTitle.add("性别");
		stdTitle.add("出生日期");
		stdTitle.add("职称");
		stdTitle.add("所在院（系、所）");
		stdTitle.add("行政职务");
		stdTitle.add("是否博导");
		stdTitle.add("岗位等级");
		stdTitle.add("最后学位");
		stdTitle.add("外语语种");
		stdTitle.add("家庭电话");
		stdTitle.add("手机");
		stdTitle.add("办公电话");
		stdTitle.add("E-mail");
		stdTitle.add("学术兼职");
		stdTitle.add("一级学科代码");
		stdTitle.add("一级学科名称");
		stdTitle.add("二级学科代码");
		stdTitle.add("二级学科名称");
		stdTitle.add("三级学科代码");
		stdTitle.add("三级学科名称");
		stdTitle.add("承担教育部项目情况");
		stdTitle.add("承担国家社科项目情况");
		stdTitle.add("人才奖励资助情况");
		stdTitle.add("学术研究方向");
		stdTitle.add("通讯地址");
		stdTitle.add("邮政编码");
		stdTitle.add("备注");
	}

	static private List<IValidator> vlist = new ArrayList<IValidator>();
	static {
		vlist.add(new DummyValidator());
		vlist.add(new DummyValidator());
		vlist.add(new DummyValidator());
		vlist.add(new DummyValidator());
		vlist.add(new DummyValidator());
		vlist.add(new DummyValidator());
		vlist.add(new DummyValidator());
		vlist.add(new DummyValidator());
		vlist.add(new DummyValidator());
		vlist.add(new DummyValidator());
		vlist.add(new DummyValidator());
		vlist.add(new DummyValidator());
		vlist.add(new DummyValidator());
		vlist.add(new DummyValidator());
		vlist.add(new DummyValidator());
		vlist.add(new DummyValidator());
		vlist.add(new DummyValidator());
		vlist.add(new DummyValidator());
		vlist.add(new DummyValidator());
		vlist.add(new DummyValidator());
		vlist.add(new DummyValidator());
		vlist.add(new DummyValidator());
		vlist.add(new DummyValidator());
		vlist.add(new DummyValidator());
		vlist.add(new DummyValidator());
		vlist.add(new DummyValidator());
		vlist.add(new DummyValidator());
		vlist.add(new DummyValidator());
		vlist.add(new DummyValidator());
		vlist.add(new DummyValidator());
		vlist.add(new DummyValidator());
	}


////////////////////////////////
	HashMap<String, Agency> universityMap;
	HashMap<String, Person> personMap;
	HashMap<String, Department> departmentMap;
	HashMap<String, SystemOption> discMap;

	List<Department> dList = new ArrayList<Department>();
	List<Teacher> tList = new ArrayList<Teacher>();
	List<Expert> eList = new ArrayList<Expert>();
	List<Person> pList = new ArrayList<Person>();
	List<Academic> aList = new ArrayList<Academic>();


	{
		universityMap = new HashMap<String, Agency>();
		List<Agency> aList = baseService.query("select a from Agency a");
		for (Agency agency : aList) {
			universityMap.put(agency.getCode(), agency);
		}

		personMap = new HashMap<String, Person>();
		List<Person> pList = baseService.query("select p from Person p left join fetch p.academic");
		for (Person person : pList) {
			if (person.getIdcardNumber() != null && !person.getIdcardNumber().isEmpty()){
				personMap.put(person.getIdcardNumber(), person);
			}
		}

		departmentMap = new HashMap<String, Department>();
		List<Department> dList = baseService.query("select d from Department d left join fetch d.university");
		for (Department department : dList) {
			departmentMap.put(department.getUniversity().getName() + department.getName(), department);
		}

		discMap = new HashMap<String, SystemOption>();
		List<SystemOption> d1List = baseService.query("select so from SystemOption so where so.standard like '%GBT13745%' ");
		for (SystemOption so : d1List) {
			discMap.put(so.getCode(), so);
		}

	}




///////////////////////////////////////
	private void getList(File xls) throws Exception{
		Sheet rs = Workbook.getWorkbook(new FileInputStream(xls)).getSheet(0);
		list = new ArrayList<List>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		for (int i = 0; i < rs.getRows(); i++) {
			List row = new ArrayList();
			for (int j = 0; j < stdTitle.size(); j++) {
				Cell cell = rs.getCell(j, i);
				Object data = cell.getContents();
				if(cell.getType() == CellType.DATE){
					DateCell dc = (DateCell)cell;
					data = dc.getDate();
				} else if (((String)data).matches("\\d{4}-\\d\\d-\\d\\d")){
					data = sdf.parse((String) data);
				} else if(data.toString().matches("\\d*\\.\\d*") && (cell.getType() == CellType.NUMBER || cell.getType() == CellType.NUMBER_FORMULA)){
					NumberCell nc = (NumberCell)cell;
					data = "" + nc.getValue();
				}
				row.add(data);
			}
			list.add(row);
		}
		/*
		for (int j = 0; j < stdTitle.size(); j++) {
			System.out.println(j + " - " + stdTitle.get(j) + " " + maxst[j].length() + " :\t\t" + maxst[j]);
		}*/




		for (int i = 1; i < list.size(); i++){
			System.out.println(i + " / " + list.size());

			Teacher teacher = new Teacher();
			Expert expert = new Expert();
			Person person = personMap.get(list.get(i).get(3));
			Academic academic = null;
			Agency university = universityMap.get(list.get(i).get(0));

			if (person == null){
				person = new Person();
				if (list.get(i).get(3) != null && !((String) list.get(i).get(3)).trim().isEmpty()){
					personMap.put((String) list.get(i).get(3), person);
				}
			} else {
				if (person.getAcademic() != null){
					for (Iterator iterator = person.getAcademic().iterator(); iterator.hasNext();) {
						academic = (Academic) iterator.next();
					}
				}
			}
			if (academic == null){
				academic = new Academic();
				academic.setPerson(person);
			}

			if (person.getName() == null){
				person.setName((String) list.get(i).get(2));
			}

			if (person.getIdcardNumber() == null && ((String) list.get(i).get(3)).getBytes("UTF-8").length < 40){
				person.setIdcardNumber((String) list.get(i).get(3));
			}
			if (person.getGender() == null){
				person.setGender((String) list.get(i).get(4));
			}
			if (person.getGender() == null || person.getGender().isEmpty()){
				person.setGender("男");
			}
			if (person.getBirthday() == null){
				person.setBirthday((Date) list.get(i).get(5));
			}
			if (academic.getSpecialityTitle() == null){
				academic.setSpecialityTitle((String) list.get(i).get(6));
			}
			if (academic.getTutorType() == null){
				String tt = (String) list.get(i).get(9);
				academic.setTutorType(tt.contains("博导") || tt.contains("是") ? "博士生导师" : tt.contains("硕") ? "硕士生导师" : null);
			}
			if (academic.getPositionLevel() == null && ((String) list.get(i).get(10)).getBytes("UTF-8").length < 40) {
				academic.setPositionLevel((String) list.get(i).get(10));
			}
			if (academic.getLastDegree() == null && ((String) list.get(i).get(11)).getBytes("UTF-8").length < 40) {
				academic.setLastDegree((String) list.get(i).get(11));
			}
			if (academic.getLanguage() == null && ((String) list.get(i).get(12)).getBytes("UTF-8").length < 400){
				academic.setLanguage((String) list.get(i).get(12));
			}
			if (person.getHomePhone() == null && ((String) list.get(i).get(13)).getBytes("UTF-8").length < 40){
				person.setHomePhone((String) list.get(i).get(13));
			}
			if (person.getMobilePhone() == null && ((String) list.get(i).get(14)).getBytes("UTF-8").length < 40){
				person.setMobilePhone((String) list.get(i).get(14));
			}
			if (person.getOfficePhone() == null && ((String) list.get(i).get(15)).getBytes("UTF-8").length < 40){
				person.setOfficePhone((String) list.get(i).get(15));
			}
			if (person.getEmail() == null && ((String) list.get(i).get(16)).getBytes("UTF-8").length < 200) {
				person.setEmail((String) list.get(i).get(16));
			}
			if (academic.getParttimeJob() == null && ((String) list.get(i).get(17)).getBytes("UTF-8").length < 2000){
				academic.setParttimeJob((String) list.get(i).get(17));
			}

			String disc[], cur = "";
			List<String> dlist = new ArrayList<String>();

			disc = ((String) list.get(i).get(18)).split("; ");
			for (String string : disc) {
				if (!string.isEmpty()){
					dlist.add(string);
				}
			}
			disc = ((String) list.get(i).get(20)).split("; ");
			for (String string : disc) {
				if (!string.isEmpty()){
					dlist.add(string);
				}
			}
			disc = ((String) list.get(i).get(22)).split("; ");
			for (String string : disc) {
				if (!string.isEmpty()){
					dlist.add(string);
				}
			}

			Collections.sort(dlist, new Comparator() {
				public int compare(Object o1, Object o2) {
					String a = (String)o1, b = (String)o2;
					return a.compareTo(b) < 0 ? -1 : 1;
				}
			});
			for (int j = 0; j < dlist.size(); j++){
				if (j == dlist.size() - 1 || !dlist.get(j+1).startsWith(dlist.get(j))){
					cur += (cur.isEmpty() ? "" : "; ") + dlist.get(j) + "/" + discMap.get(dlist.get(j)).getName();
					if (discMap.get(dlist.get(j)) == null){
						System.out.println(dlist.get(j) + "   cnm!!!!");
						throw new Exception();
					}
				}
			}
			if (academic.getDiscipline() == null){
				academic.setDiscipline(cur);
			}
			if (academic.getResearchField() == null && ((String) list.get(i).get(27)).getBytes("UTF-8").length < 800){
				academic.setResearchField((String) list.get(i).get(27));
			}
			if (person.getOfficeAddress() == null && ((String) list.get(i).get(28)).getBytes("UTF-8").length < 200){
				person.setOfficeAddress((String) list.get(i).get(28));
			}
			if (person.getOfficePostcode() == null && ((String) list.get(i).get(29)).getBytes("UTF-8").length < 40){
				person.setOfficePostcode((String) list.get(i).get(29));
			}
			if (person.getIntroduction() == null && ((String) list.get(i).get(30)).getBytes("UTF-8").length < 800){
				person.setIntroduction((String) list.get(i).get(30));
			}

			if (list.get(i).get(7) == null || ((String) list.get(i).get(7)).isEmpty()){
				list.get(i).set(7, "未知院系");
			}

			if (university == null){
				expert.setAgencyName((String) list.get(i).get(1));
				expert.setDivisionName((String) list.get(i).get(7));
				if (((String) list.get(i).get(8)).getBytes("UTF-8").length < 80){
					expert.setPosition((String) list.get(i).get(8));
				}
				expert.setType("专职人员");
				expert.setPerson(person);
				eList.add(expert);
			} else {
				teacher.setUniversity(university);
				String depName = university.getName() + list.get(i).get(7);
				Department department = departmentMap.get(depName);
				if (department == null){
					department = new Department();
					department.setName((String) list.get(i).get(7));
					department.setUniversity(university);
					if (!department.getName().trim().isEmpty()){
						departmentMap.put(depName, department);
					}
					dList.add(department);
				}
				if (((String) list.get(i).get(8)).getBytes("UTF-8").length < 80){
					teacher.setPosition((String) list.get(i).get(8));
				}
				teacher.setDepartment(department);
				teacher.setPerson(person);
				teacher.setType("专职人员");
				tList.add(teacher);
			}

			pList.add(person);
			aList.add(academic);

		}
	}

	public StringBuffer validate(File xls) throws Exception{
		StringBuffer tmpMsg, errorMsg = new StringBuffer();
		try {
			getList(xls);
		} catch (Exception e) {
			e.printStackTrace();
			errorMsg.append("文件格式错误,请确认和模板格式一致!");
			return errorMsg;
		}
		//System.out.println("文件格式正确!!");

		for (int i = 0; i < stdTitle.size(); i++){
			if (!list.get(0).get(i).equals(stdTitle.get(i))){
				errorMsg.append("标题第" + (i + 1) + "列应为" + stdTitle.get(i) + "<br />");
			}
		}

		for (int k = 1; k < list.size(); k++) {
			for (int i = 0; i < stdTitle.size(); i++){
				tmpMsg = vlist.get(i).validate(list.get(k).get(i));
				if (tmpMsg != null && tmpMsg.length() > 0){
					errorMsg.append("<tr><td>第" + (k + 1) + "行</td><td>第 " + (i + 1) + "列</td><td>&nbsp;&nbsp;&nbsp;" + tmpMsg + "</td></tr>");
				}
			}
		}
		return errorMsg;
	}

	public List display(File xls) throws Exception {
		getList(xls);
		return list;
	}

	public void imprt() throws Exception {
		List blist = new ArrayList();
		blist.add(dList);
		blist.add(pList);
		blist.add(tList);
		blist.add(eList);
		blist.add(aList);
		baseService.addOrModify(blist);
	}

	public void exprtTemplate() throws Exception {
		HttpServletResponse response = ServletActionContext.getResponse();
		Vector v = new Vector();
		String[] oo = stdTitle.toArray(new String[0]);
		HSSFExport.commonExportData(oo, v, response);
	}

	public void exportExpert(int exportAll, int countReviewer) throws Exception {
	}


	static public void setBaseService(IBaseService baseService) {
		ReviewingExpertKit.baseService = baseService;
	}

	static public IBaseService getBaseService() {
		return baseService;
	}
}
