package csdc.tool.tableKit.imp;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;

import csdc.bean.Test;
import csdc.service.IBaseService;
import csdc.tool.HSSFExport;
import csdc.tool.SpringBean;
import csdc.tool.tableKit.ITableKit;
import csdc.tool.validator.IValidator;
import csdc.tool.validator.imp.StringValidator;



import jxl.Sheet;
import jxl.Workbook;

/**
 * 测试表工具包
 * @author 徐涵
 *
 */
public class TestKit implements ITableKit{
	/**
	 * 用于在页面显示list
	 */
	private List<List<String> > list;

	/**
	 * 用于导入到数据库
	 */
	private List<Test> blist;
	@SuppressWarnings("unused")
	private List<String> title;
	private IBaseService baseService;

	static private List<String> stdTitle = new ArrayList<String>();
	static {
		stdTitle.add("编号");
		stdTitle.add("一级学科");
		stdTitle.add("二级学科");
		stdTitle.add("三级学科");
		stdTitle.add("高校");
		stdTitle.add("负责人");
	}
	
	static private List<IValidator> vlist = new ArrayList<IValidator>();
	static {
		vlist.add(new StringValidator(40));
		vlist.add(new StringValidator(30));
		vlist.add(new StringValidator(30));
		vlist.add(new StringValidator(30));
		vlist.add(new StringValidator(30));
		vlist.add(new StringValidator(30));
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
	}
	
	public StringBuffer validate(File xls) throws Exception{
		StringBuffer tmpMsg, errorMsg = new StringBuffer();
		try {
			getList(xls);
		} catch (Exception e) {
			//System.out.println(e);
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
		blist = new ArrayList<Test>();
		for (int i = 1; i < list.size(); i++){
			Test test = new Test();
			test.setCategery1(list.get(i).get(1));
			test.setCategery2(list.get(i).get(2));
			test.setCategery3(list.get(i).get(3));
			test.setUniversity(list.get(i).get(4));
			test.setUniversity(list.get(i).get(5));
			blist.add(test);
		}
		Map session = ActionContext.getContext().getSession();
		session.put("list", blist);
		return list;
	}

	@SuppressWarnings("unchecked")
	public void imprt() throws Exception {

		ServletContext sc = ServletActionContext.getServletContext();
		baseService = (IBaseService) SpringBean.getBean("baseService", sc);

		Map session = ActionContext.getContext().getSession();
		List list = (List) session.get("list");
		baseService.add(list);
		session.remove("list");
	}
	
	@SuppressWarnings("unchecked")
	public void exprtTemplate() throws Exception {
		HttpServletResponse response = ServletActionContext.getResponse();
		Vector v = new Vector();
		String[] oo = new String[]{"测试表", "编号", "一级学科", "二级学科", "三级学科", "高校", "负责人"};
		HSSFExport.commonExportData(oo, v, response);
	}

/*	@SuppressWarnings("unchecked")
	public List list() {

		ServletContext sc = ServletActionContext.getServletContext();
		baseService = (IBaseService) SpringBean.getBean("baseService", sc);

		List<String[]> ret = new ArrayList<String[]>();
		ret.add((String[]) stdTitle.toArray());
		blist = baseService.query("select test from Test test");
		for (int i = 0; i < blist.size(); i++){
			Test test = (Test) blist.get(i);
			ret.add(new String[]{
					test.getId(),
					test.getCategery1(),
					test.getCategery2(),
					test.getCategery3(),
					test.getUniversity(),
					test.getName()
			});
		}
		return ret;
	}
*/
	

	
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
