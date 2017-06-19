package csdc.tool.tableKit.imp;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;

import csdc.bean.Agency;
import csdc.bean.SystemOption;
import csdc.service.IBaseService;
import csdc.tool.tableKit.ITableKit;
import csdc.tool.validator.IValidator;
import csdc.tool.validator.imp.DummyValidator;



import jxl.Sheet;
import jxl.Workbook;

/**
 * 高校导入_工具包
 * 【注意】不会导入subjection信息
 * @author 徐涵
 *
 */
public class UniversityKit implements ITableKit{
	/**
	 * 用于在页面显示list
	 */
	private List<List<String> > list;

	/**
	 * 用于导入到数据库
	 */
	private List<Agency> blist;
	@SuppressWarnings("unused")
	private List<String> title;
	static private IBaseService baseService;
	
	/**
	 * 高校代码 -> 高校实体 的映射
	 */
	private Map<String, Agency> univMap = new HashMap<String, Agency>();

	static private List<String> stdTitle = new ArrayList<String>();
	static {
		stdTitle.add("学校代码");
		stdTitle.add("学校名称");
		stdTitle.add("省市代码");
		stdTitle.add("省市名称");
		stdTitle.add("办学类型");
		stdTitle.add("办学类型代码");
		stdTitle.add("性质类别");
		stdTitle.add("性质类别代码");
		stdTitle.add("学校举办者");
		stdTitle.add("学校举办者代码");
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
	}


	private String addSlash(String s, int index) {
		if (s == null){
			return null;
		} else if (s.length() < index){
			return s;
		} else {
			return new StringBuffer(s).insert(index, '/').toString();
		}
	}

	@SuppressWarnings("unchecked")
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
		
		List<Agency> univList = baseService.query("select u from Agency u ");
		for (Agency u : univList) {
			if (u.getCode() != null && !u.getCode().isEmpty()) {
				univMap.put(u.getCode(), u);
			}
		}

		List<SystemOption> adList = baseService.query("select so from SystemOption so where so.standard = 'GBT2260-2007' ");
		HashMap<String, SystemOption> adMap = new HashMap<String, SystemOption>();
		for (SystemOption so : adList) {
			String code = so.getCode();
			while (code != null && code.matches(".*00")){
				code = code.substring(0, code.length() - 2);
			}
			adMap.put(code, so);
		}

		blist = new ArrayList<Agency>();
		for (int i = 1; i < list.size(); i++){
			Agency agency = univMap.get(list.get(i).get(0));
			if (agency == null) {
				agency = new Agency();
			}
			agency.setCode(list.get(i).get(0));
			agency.setName(list.get(i).get(1));
			agency.setProvince(adMap.get(list.get(i).get(2)));
			agency.setStyle(addSlash(list.get(i).get(4), 2));
			agency.setCategory(addSlash(list.get(i).get(6), 2));
			agency.setOrganizer(addSlash(list.get(i).get(8), 3));
			agency.setType(agency.getOrganizer().contains("360") ? 3 : 4);
			System.out.println(agency.getName() + " " + agency.getCode());
			blist.add(agency);
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

	@SuppressWarnings("unchecked")
	public List display(File xls) throws Exception {
		getList(xls);
		blist = new ArrayList<Agency>();
		for (int i = 1; i < list.size(); i++){
			Agency agency = new Agency();
			agency.setName(list.get(i).get(1));
			agency.setCode(list.get(i).get(2));
			agency.setType(Integer.parseInt(list.get(i).get(3)) + 2);

			SystemOption hubei = new SystemOption();
			hubei.setId("hubei");
			agency.setProvince(hubei);

			blist.add(agency);
		}
		Map session = ActionContext.getContext().getSession();
		session.put("list", blist);
		return list;
	}

	public void imprt() throws Exception {
		baseService.addOrModify(blist);
	}

	public void exprtTemplate() throws Exception {
	}






	static public void setBaseService(IBaseService baseService) {
		UniversityKit.baseService = baseService;
	}




}
