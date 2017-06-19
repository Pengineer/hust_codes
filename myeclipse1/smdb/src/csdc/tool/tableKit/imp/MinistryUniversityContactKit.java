package csdc.tool.tableKit.imp;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import csdc.bean.Agency;
import csdc.bean.Officer;
import csdc.bean.Person;
import csdc.service.IBaseService;
import csdc.tool.tableKit.ITableKit;
import csdc.tool.validator.IValidator;
import csdc.tool.validator.imp.DummyValidator;



import jxl.Sheet;
import jxl.Workbook;

/**
 * 部属高校联系方式导入_工具包
 * [修改]20080922_部属高校、地方厅局通讯录.xls
 * @author 徐涵
 *
 */
@SuppressWarnings("unchecked")
public class MinistryUniversityContactKit implements ITableKit{

	private List<List<String> > list;
	private List<Agency> aList;
	private List<Person> pList;
	private List<Officer> oList;
	private HashMap<String, Agency> aMap;


	static private IBaseService baseService;

	static private List<String> stdTitle = new ArrayList<String>();
	static {
		stdTitle.add("序号");
		stdTitle.add("主管单位");
		stdTitle.add("高校代码");
		stdTitle.add("高校名称");
		stdTitle.add("高校名称");
		stdTitle.add("对口部门");
		stdTitle.add("负责人");
		stdTitle.add("职务");
		stdTitle.add("区号");
		stdTitle.add("办公电话");
		stdTitle.add("住宅电话");
		stdTitle.add("手机");
		stdTitle.add("传真");
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
	}

	////////////////////////////////////////////////////////

	{

		aMap = new HashMap<String, Agency>();
		List<Agency> tmpList = baseService.query("select a from Agency a");
		for (Agency agency : tmpList) {
			aMap.put(agency.getCode(), agency);
		}

	}


	private String getFirst(String s){
		String tmp[] = s.split("\\s+");
		return tmp.length == 0 ? null : tmp[0];
	}


	private void getList(File xls) throws Exception{
		Sheet rs = Workbook.getWorkbook(new FileInputStream(xls)).getSheet(0);
		list = new ArrayList<List<String> >();
		for (int i = 0; i < rs.getRows(); i++) {
			List row = new ArrayList<String>();
			for (int j = 0; j < stdTitle.size(); j++) {
				row.add(rs.getCell(j, i).getContents().trim());
			}
			list.add(row);
		}

		aList = new ArrayList<Agency>();
		pList = new ArrayList<Person>();
		oList = new ArrayList<Officer>();

		for (int i = 1; i < list.size(); i++){

			Agency agency = aMap.get(list.get(i).get(2));
			agency.setSname(list.get(i).get(5));


			Officer officer = new Officer();
			officer.setType("专职人员");
			Person person = new Person();
			agency.setSdirector(person);
			person.setName(getFirst(list.get(i).get(6)));
			officer.setPerson(person);
			officer.setAgency(agency);
			officer.setPosition(getFirst(list.get(i).get(7)));
			person.setOfficePhone(getFirst(list.get(i).get(8)) + "-" + getFirst(list.get(i).get(9)));
			agency.setSphone(getFirst(list.get(i).get(8)) + "-" + getFirst(list.get(i).get(9)));
			person.setHomePhone(getFirst(list.get(i).get(8)) + "-" + getFirst(list.get(i).get(10)));
			person.setMobilePhone(getFirst(list.get(i).get(11)));
			person.setOfficeFax(getFirst(list.get(i).get(12)));
			agency.setFax(getFirst(list.get(i).get(12)));


			aList.add(agency);
			pList.add(person);
			oList.add(officer);
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
		return errorMsg;
	}

	public List display(File xls) {
		return null;
	}

	public void imprt() throws Exception {
		List bList = new ArrayList();
		bList.add(aList);
		bList.add(pList);
		bList.add(oList);
		baseService.addOrModify(bList);
	}

	public void exprtTemplate() throws Exception {
	}



	static public void setBaseService(IBaseService baseService) {
		MinistryUniversityContactKit.baseService = baseService;
	}




}
