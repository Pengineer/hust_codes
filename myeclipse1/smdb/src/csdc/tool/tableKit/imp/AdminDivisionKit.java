package csdc.tool.tableKit.imp;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import csdc.bean.SystemOption;
import csdc.service.IBaseService;
import csdc.tool.tableKit.ITableKit;
import csdc.tool.validator.IValidator;
import csdc.tool.validator.imp.DummyValidator;


import jxl.Sheet;
import jxl.Workbook;

/**
 * 行政区划导入_工具包
 * @author 徐涵
 *
 */
public class AdminDivisionKit implements ITableKit{
	/**
	 * 用于在页面显示list
	 */
	private List<List<String> > list;

	/**
	 * 用于导入到数据库
	 */
	private List<SystemOption> blist;
	static private IBaseService baseService;

	static private List<String> stdTitle = new ArrayList<String>();
	static {
		stdTitle.add("代码");
		stdTitle.add("名称");
	}

	static private List<IValidator> vlist = new ArrayList<IValidator>();
	static {
		vlist.add(new DummyValidator());
		vlist.add(new DummyValidator());
	}

////////////////////////////////

///////////////////////////////////////

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

		blist = new ArrayList<SystemOption>();
		SystemOption god = new SystemOption();
		god.setName("GBT2260-2007中华人民共和国行政区划代码");
		god.setStandard("GBT2260-2007");
		god.setCode("");
		god.setIsAvailable(1);
		blist.add(god);

		for (int i = 1; i < list.size(); i++){
			SystemOption so = new SystemOption();
			so.setCode(list.get(i).get(0));
			so.setName(list.get(i).get(1));
			so.setStandard("GBT2260-2007");
			so.setIsAvailable(1);
			if (!so.getCode().trim().isEmpty() && !so.getName().trim().isEmpty()){
				blist.add(so);
			}
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
		System.out.println(errorMsg);
		return errorMsg;
	}

	@SuppressWarnings("unchecked")
	public List display(File xls) throws Exception {
		return list;
	}

	public void imprt() throws Exception {
		baseService.add(blist);

		HashMap<String, SystemOption> map = new HashMap<String, SystemOption>();

		for (SystemOption so : blist) {
			String code = so.getCode();
			while (code.matches(".*00")){
				code = code.substring(0, code.length() - 2);
			}
			map.put(code, so);
			if (code.length() >= 2){
				code = code.substring(0, code.length() - 2);
				so.setSystemOption(map.get(code));
				System.out.println(so.getName() + " - " + so.getSystemOption().getName());
			}
		}
		baseService.addOrModify(blist);
	}

	public void exprtTemplate() throws Exception {
	}



	static public void setBaseService(IBaseService baseService) {
		AdminDivisionKit.baseService = baseService;
	}




}
