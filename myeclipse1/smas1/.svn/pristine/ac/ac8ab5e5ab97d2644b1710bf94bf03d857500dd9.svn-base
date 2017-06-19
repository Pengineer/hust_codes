package csdc.tool.tableKit.imp;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import csdc.bean.SystemOption;
import csdc.service.IBaseService;
import csdc.tool.HSSFExport;
import csdc.tool.PinyinCommon;
import csdc.tool.tableKit.ITableKit;
import csdc.tool.validator.IValidator;
import csdc.tool.validator.imp.DummyValidator;



import jxl.Sheet;
import jxl.Workbook;

/**
 * 学科编号工具包
 * @author 徐涵
 *
 */
public class DisciplineKit implements ITableKit{
	/**
	 * 用于在页面显示list
	 */
	private List<List<String> > list;

	/**
	 * 用于导入到数据库
	 */
	private List<SystemOption> blist;
	static private IBaseService baseService;
	static public int year = 1992;

	
	static private List<String> stdTitle = new ArrayList<String>();
	static {
		stdTitle.add("代码");
		stdTitle.add("学科名称");
		if (year == 2009)
			stdTitle.add("说明");
	}
	
	static private List<IValidator> vlist = new ArrayList<IValidator>();
	static {
		vlist.add(new DummyValidator());
		vlist.add(new DummyValidator());
		if (year == 2009)
			vlist.add(new DummyValidator());
	}

	@SuppressWarnings({ "unchecked" })
	private void getList(File xls) throws Exception{
		Sheet rs = Workbook.getWorkbook(new FileInputStream(xls)).getSheet(0);
		String maxst[] = new String[stdTitle.size()];
		list = new ArrayList<List<String> >();
		for (int i = 0; i < rs.getRows(); i++) {
			List row = new ArrayList<String>(); 
			for (int j = 0; j < stdTitle.size(); j++) {
				String str = rs.getCell(j, i).getContents().trim();
				if (maxst[j] == null || str.length() > maxst[j].length()){
					maxst[j] = str;
				}
				row.add(str);
			}
			list.add(row);
		}
		/*
		for (int j = 0; j < stdTitle.size(); j++) {
			//System.out.println(j + " - " + stdTitle.get(j) + " " + maxst[j].length() + " :\t\t" + maxst[j]);
		}*/
//		int x = 0; while (x < 1);
		
		blist = new ArrayList<SystemOption>();
		for (int i = 1; i < list.size(); i++){
			//System.out.println(i + " / " + list.size());
			
			SystemOption sysopt = new SystemOption();
			
			sysopt.setCode(list.get(i).get(0));
			sysopt.setName(list.get(i).get(1));
			if (year == 2009)
				sysopt.setDescription(list.get(i).get(2));
			sysopt.setStandard("[GBT13745-"+year+"]");
			sysopt.setAbbr(PinyinCommon.getPinYinHeadChar(sysopt.getName().replaceAll("[^\\w\\u4e00-\\u9fa5]", "").toLowerCase()));
			sysopt.setIsAvailable(1);
			
			blist.add(sysopt);
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
				if (tmpMsg.length() > 0){
					errorMsg.append("<tr><td>第" + (k + 1) + "行</td><td>第 " + (i + 1) + "列</td><td>&nbsp;&nbsp;&nbsp;" + tmpMsg + "</td></tr>");
				}
			}
		}
		return errorMsg;
	}
	
	@SuppressWarnings("unchecked")
	public List display(File xls) throws Exception {
		getList(xls);
		return list;
	}

	public void imprt() throws Exception {
		baseService.add(blist);
	}
	
	@SuppressWarnings("unchecked")
	public void exprtTemplate() throws Exception {
		HttpServletResponse response = ServletActionContext.getResponse();
		Vector v = new Vector();
		String[] oo = stdTitle.toArray(new String[0]);
		HSSFExport.commonExportData(oo, v, response);
	}

	static public void setBaseService(IBaseService baseService) {
		DisciplineKit.baseService = baseService;
	}

	

	
/*	@SuppressWarnings("unchecked")
	public void exprt() throws Exception {

		ServletContext sc = ServletActionContext.getServletContext();
		baseService = (IBaseService) SpringBean.getBean("baseService", sc);

		HttpServletResponse response = ServletActionContext.getResponse();
		List list = baseService.query("select test.id, test.categery1, test.categery2, test.categery3, test.university, test.name from Test test");
		if(list != null && list.size()>0){
			Vector v = new Vector();
			String[] oo = new String[]{"测试表", "编号", "一级学科", "二级学科", "三级学科", "高校", "负责人"};
			for(int i = 0; i < list.size(); i++){
				Object[] o = (Object[])list.get(i);
				Object[] obj = new Object[o.length];
				for(int j = 0; j < o.length; j++) {
					obj[j] = (o[j] != null) ? o[j].toString() : "";
				}
				v.add(obj);
			}
			HSSFExport.commonExportData(oo, v, response);
		}
	}
*/


	
	

}
