package csdc.tool.tableKit.imp;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import csdc.bean.Academic;
import csdc.bean.Agency;
import csdc.bean.Department;
import csdc.bean.GeneralGranted;
import csdc.bean.GeneralApplication;
import csdc.bean.GeneralMember;
import csdc.bean.Person;
import csdc.bean.SystemOption;
import csdc.bean.Teacher;
import csdc.service.IBaseService;
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
 * 项目导入_工具包
 * @author 徐涵
 *
 */
@SuppressWarnings("unchecked")
public class GeneralProjectKit implements ITableKit{
	/**
	 * 用于在页面显示list
	 */
	private List<List> list;

	/**
	 * 用于导入到数据库
	 */
	private List dataToAdd;
	private List<GeneralGranted> blist;
	@SuppressWarnings("unused")
	private List<String> title;
	static private IBaseService baseService;

	static private List<String> stdTitle = new ArrayList<String>();
	static {
		stdTitle.add("项目编号ID");
		stdTitle.add("添加日期");
		stdTitle.add("审核状态");
		stdTitle.add("申报书文件名");
		stdTitle.add("学校编号");
		stdTitle.add("学校名称");
		stdTitle.add("项目名称");
		stdTitle.add("项目类别");
		stdTitle.add("学科门类");
		stdTitle.add("申报日期");
		stdTitle.add("研究方向及代码");
		stdTitle.add("研究类别");
		stdTitle.add("计划完成日期");
		stdTitle.add("申请经费（万元）");
		stdTitle.add("其他来源经费");
		stdTitle.add("最终成果形式");
		stdTitle.add("申请人");
		stdTitle.add("性别");
		stdTitle.add("出生日期");
		stdTitle.add("职称");
		stdTitle.add("所在部门");
		stdTitle.add("职务");
		stdTitle.add("最后学历");
		stdTitle.add("最后学位");
		stdTitle.add("外语语种");
		stdTitle.add("通讯地址");
		stdTitle.add("邮编");
		stdTitle.add("固定电话");
		stdTitle.add("手机号");
		stdTitle.add("EMAIL");
		stdTitle.add("身份证号");
		stdTitle.add("成员信息[姓名(工作单位)]");
		stdTitle.add("高校类型");
		stdTitle.add("学校名称＋申请人");
		stdTitle.add("职称");
		stdTitle.add("出生年");
		stdTitle.add("年龄");
		stdTitle.add("年龄段");
		stdTitle.add("是否立项");
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
	private HashMap<String, Agency> universityMap;

	private void initUniversity() {
		universityMap = new HashMap<String, Agency>();
		List<Agency> list = baseService.query("select a from Agency a");
		for (Agency agency : list) {
			universityMap.put(agency.getCode(), agency);
		}
	}

	private String tranDiscipline(String o) {
		String s[] = o.split("、");
		String res = "";
		for (String each : s) {
			String sb[] = each.split("（");
			if (sb.length >= 2){
				res += res.isEmpty() ? "" : "; ";
				res += sb[1].replaceAll("[（）]", "") + "/" + sb[0];
			}
		}
		return res.isEmpty() ? "无" : res;
	}

///////////////////////////////////////

	private HashMap<String, Department> departmentMap;

	private void getList(File xls) throws Exception{
		Sheet rs = Workbook.getWorkbook(new FileInputStream(xls)).getSheet(0);
		list = new ArrayList<List>();
		for (int i = 0; i < rs.getRows(); i++) {
			List row = new ArrayList<String>();
			for (int j = 0; j < stdTitle.size(); j++) {
				Cell cell = rs.getCell(j, i);
				Object data = cell.getContents();
				if(cell.getType() == CellType.DATE){
					DateCell dc = (DateCell)cell;
					data = dc.getDate();
				} else if(data.toString().matches("\\d*\\.\\d*") && (cell.getType() == CellType.NUMBER || cell.getType() == CellType.NUMBER_FORMULA)){
					NumberCell nc = (NumberCell)cell;
					data = "" + nc.getValue();
				}
				row.add(data);
			}
			list.add(row);
		}

		initUniversity();
		departmentMap = new HashMap<String, Department>();


		List<Department> dList = new ArrayList<Department>();
		List<Teacher> tList = new ArrayList<Teacher>();
		List<Person> pList = new ArrayList<Person>();
		List<Academic> aList = new ArrayList<Academic>();
		List<GeneralApplication> gaList = new ArrayList<GeneralApplication>();
		List<GeneralMember> gmList = new ArrayList<GeneralMember>();


		SystemOption guihuajijin = (SystemOption) baseService.query(SystemOption.class, "planFund");
		SystemOption qingnianjijin = (SystemOption) baseService.query(SystemOption.class, "youngFund");
		SystemOption zichoujingfei = (SystemOption) baseService.query(SystemOption.class, "noFeeFund");

		SystemOption jichuyanjiu = (SystemOption) baseService.query(SystemOption.class, "baseResearch");
		SystemOption shiyanyufazhan = (SystemOption) baseService.query(SystemOption.class, "experimentAndDevelopment");
		SystemOption yingyongyanjiu = (SystemOption) baseService.query(SystemOption.class, "application");

		blist = new ArrayList<GeneralGranted>();
		for (int i = 1; i < list.size(); i++){
			System.out.println(i);

			if (list.get(i).get(0) == null || ((String) list.get(i).get(0)).isEmpty()){
				continue;
			}

			GeneralGranted general = new GeneralGranted();
			GeneralApplication ga = new GeneralApplication();

			String univCode = (String) list.get(i).get(4);
			if (univCode.equals("10090")){
				univCode = "10081";
			}
			System.out.println("---" + list.get(i).get(4) + "---");
			ga.setUniversity(universityMap.get(univCode));
			general.setName((String) list.get(i).get(6));
			general.setStatus(1);
			ga.setName((String) list.get(i).get(6));
			ga.setDisciplineType((String) list.get(i).get(8));
			ga.setDiscipline(tranDiscipline((String) list.get(i).get(10)));

			try {
				ga.setResearchType(list.get(i).get(11).equals("基础研究") ? jichuyanjiu : list.get(i).get(11).equals("应用研究") ? yingyongyanjiu : shiyanyufazhan);
			} catch (Exception e) {
				e.printStackTrace();
			}

			try {
				ga.setPlanEndDate((Date) list.get(i).get(12));
			} catch (Exception e) {
				e.printStackTrace();
			}

			ga.setApplyFee(Double.parseDouble((String) list.get(i).get(13)));
			ga.setOtherFee(Double.parseDouble((String) list.get(i).get(14)));
			ga.setProductType((String) list.get(i).get(15));
			ga.setSubtype(list.get(i).get(7).equals("青年基金项目") ? qingnianjijin : list.get(i).get(7).equals("规划基金项目") ? guihuajijin : zichoujingfei);
			ga.setYear(2010);
			ga.setAgencyName(ga.getUniversity().getName());
			ga.setIsReviewable(1);

			String LiXiang = (String)list.get(i).get(38);
			ga.setApplyStatus(LiXiang.equals("是") ? 9 : LiXiang.equals("取消") ? 8 : 7);
			ga.setApplicantSubmitStatus(3);
			try {
				ga.setApplicantSubmitDate((Date) list.get(i).get(9));
			} catch (Exception e) {
				e.printStackTrace();
			}
			ga.setDeptInstAuditStatus(3);
			ga.setDeptInstAuditResult(2);
			ga.setUniversityAuditStatus(3);
			ga.setUniversityAuditResult(2);
			if (!ga.getUniversity().getOrganizer().contains("360")){
				ga.setProvinceAuditStatus(3);
				ga.setProvinceAuditResult(2);
			}
			ga.setMinistryAuditStatus(3);
			ga.setMinistryAuditResult(2);
			ga.setReviewStatus(3);
			ga.setReviewResult(LiXiang.equals("是") || LiXiang.equals("取消") ? 2 : 1);
			ga.setReviewAuditStatus(3);
			ga.setReviewAuditResult(2);
			ga.setGrantedAuditStatus(3);
			if (LiXiang.equals("是")){
				ga.setGrantedAuditResult(2);
			} else if (LiXiang.equals("取消")){
				ga.setGrantedAuditResult(1);
			}


			general.setApplication(ga);

			Person person = new Person();
			Teacher teacher = new Teacher();
			Academic academic = new Academic();

			teacher.setUniversity(ga.getUniversity());
			teacher.setPerson(person);
			teacher.setType("专职人员");
			person.setName((String) list.get(i).get(16));
			person.setGender((String) list.get(i).get(17));
			try {
				person.setBirthday((Date) list.get(i).get(18));
			} catch (Exception e) {
				e.printStackTrace();
			}
			academic.setSpecialityTitle((String) list.get(i).get(19));
			academic.setPerson(person);

			String udName = ga.getUniversity().getName() + list.get(i).get(20);
			Department department = new Department();
			if (!departmentMap.containsKey(udName)){
				department.setName((String) list.get(i).get(20));
				department.setUniversity(ga.getUniversity());
				departmentMap.put(udName, department);
				dList.add(department);
			}
			teacher.setDepartment(departmentMap.get(udName));
			teacher.setPosition((String) list.get(i).get(21));

			academic.setLastEducation((String) list.get(i).get(22));
			academic.setLastDegree((String) list.get(i).get(23));
			academic.setLanguage((String) list.get(i).get(24));
			person.setOfficeAddress((String) list.get(i).get(25));
			person.setOfficePostcode((String) list.get(i).get(26));
			person.setOfficePhone((String) list.get(i).get(27));
			person.setMobilePhone((String) list.get(i).get(28));
			person.setEmail((String) list.get(i).get(29));
			person.setIdcardNumber((String) list.get(i).get(30));
			person.setIdcardType("身份证");
			ga.setApplicant(person);
			ga.setApplicantName(person.getName());

			String[] members = ((String)list.get(i).get(31)).split("，");
			int sn = 2;
			for (String string : members) {
				String[] tmp = string.split("\\(");
				if (tmp.length < 2){
					continue;
				}
				GeneralMember gm = new GeneralMember();
				gm.setMemberName(tmp[0].replaceAll("\\s+", ""));
				gm.setAgencyName(tmp[1].replaceAll("[\\s\\(\\)]", ""));
				if (gm.getAgencyName() == null || gm.getAgencyName().isEmpty()){
					gm.setAgencyName("未知机构");
				}
				gm.setIsDirector(0);
				gm.setMemberSn(sn);
				++sn;
				gm.setApplication(ga);

				gmList.add(gm);
			}
			GeneralMember director = new GeneralMember();
			director.setUniversity(ga.getUniversity());
			if (department.getName() != null){
				director.setDepartment(department);
			}
			director.setApplication(ga);
			director.setMember(person);
			director.setMemberName(person.getName());
			director.setAgencyName(ga.getUniversity().getName());
			director.setDivisionName(department.getName());
			director.setSpecialistTitle(academic.getSpecialityTitle());
			director.setMajor(academic.getMajor());
			director.setIsDirector(1);
			director.setMemberSn(1);
			gmList.add(director);


			tList.add(teacher);
			pList.add(person);
			aList.add(academic);
			gaList.add(ga);

			if (LiXiang.equals("是")){
				blist.add(general);
			}

		}

		for (Department d : dList) {
			if (d.getUniversity() == null){
				System.out.println(d + "universityID为空!");
			}
			if (d.getName() == null){
				System.out.println(d + "name为空!");
			}
		}
		System.out.println();

		for (Person p : pList) {
			if (p.getName() == null){
				System.out.println(p + "name为空!");
			}
			if (p.getGender() == null){
				System.out.println(p + "gender为空!");
			}
			if (p.getIdcardNumber() == null){
				System.out.println(p + "idcardnum为空!");
			}
			if (p.getIdcardType() == null){
				System.out.println(p + "idcardtype为空!");
			}
		}
		System.out.println();

		dataToAdd = new ArrayList();
		dataToAdd.add(dList);
		dataToAdd.add(pList);
		dataToAdd.add(tList);
		dataToAdd.add(aList);
		dataToAdd.add(blist);
		dataToAdd.add(gaList);
		dataToAdd.add(gmList);

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
		System.out.println("文件格式正确!!");

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
		System.out.println(errorMsg);
		return errorMsg;
	}

	public List display(File xls) throws Exception {
		return list;
	}

	public void imprt() throws Exception {
		baseService.add(dataToAdd);
	}

	public void exprtTemplate() throws Exception {
	}






	static public void setBaseService(IBaseService baseService) {
		GeneralProjectKit.baseService = baseService;
	}




}
