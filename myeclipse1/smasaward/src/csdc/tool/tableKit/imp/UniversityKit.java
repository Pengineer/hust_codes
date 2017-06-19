package csdc.tool.tableKit.imp;

import java.io.File;
import java.io.FileInputStream;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;

import csdc.bean.Expert;
import csdc.bean.University;
import csdc.service.IBaseService;
import csdc.tool.HSSFExport;
import csdc.tool.PinyinCommon;
import csdc.tool.tableKit.ITableKit;
import csdc.tool.validator.IValidator;
import csdc.tool.validator.imp.DummyValidator;



import jxl.Sheet;
import jxl.Workbook;

/**
 * 高校表工具包
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
	private List<University> blist;
	//private List<String> title;
	static private IBaseService baseService;

	
	static private List<String> stdTitle = new ArrayList<String>();
	static {
		stdTitle.add("高校代码");
		stdTitle.add("高校名称");
		stdTitle.add("省市代码");
		stdTitle.add("省市名称");
		stdTitle.add("办学类型");
		stdTitle.add("办学类型代码");
		stdTitle.add("性质类别");
		stdTitle.add("性质类别代码");
		stdTitle.add("高校举办者");
		stdTitle.add("高校举办者代码");
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
	}

	@SuppressWarnings("unchecked")
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
			System.out.println(j + " - " + stdTitle.get(j) + " " + maxst[j].length() + " :\t\t" + maxst[j]);
		}*/
		
		blist = new ArrayList<University>();
		for (int i = 1; i < list.size(); i++){
			//System.out.println(i + " / " + list.size());
			
			University university = new University();
			
			university.setCode(list.get(i).get(0));
			university.setName(list.get(i).get(1));
			university.setFounderCode(list.get(i).get(9));
			university.setAbbr(PinyinCommon.getPinYinHeadChar(university.getName().replaceAll("[^\\u4e00-\\u9fa5]", "")));
			if (university.getFounderCode().equals("360")){
				university.setType(1);
			} else {
				university.setType(2);
			}
			university.setRemark(list.get(i).get(10));
					
			blist.add(university);
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
	/**
	 * 导出高校专家参评数
	 * @param hql
	 * @param map
	 * @throws Exception
	 */
	public void exportUniversityStatistic(String hql,Map map) throws Exception {
		Map session = ActionContext.getContext().getSession();
		Map application = ActionContext.getContext().getApplication();
		
		HttpServletResponse response = ServletActionContext.getResponse();
		Vector v = new Vector();
		List<String> titleList = new ArrayList();
		titleList.add("高校专家参评数统计表");
		titleList.add("高校名称");
		titleList.add("高校代码");
//		titleList.add("是否参评");
		titleList.add("参评专家数");
		String[] oo = titleList.toArray(new String[0]);		
		list = baseService.query(hql, map);						
		for (Object object : list) {
			Object[] o = (Object[]) object;			
			List eList = new ArrayList<Object>();
			eList.add(o[0]);
			eList.add(o[1]);
/*			if(o[2].equals(1)) {
				o[2] = "是";
			} else if (o[2].equals(0)) {
				o[2] = "否";
			}
			eList.add(o[2]);*/			
			eList.add(o[3]);			
			Object obj[] = eList.toArray(new Object[0]);
			for (int i = 0; i < obj.length; i++){
				if (obj[i] == null){
					obj[i] = "";
				}
			}
			v.add(obj);
		}
		HSSFExport.commonExportData(oo, v, response);
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
		UniversityKit.baseService = baseService;
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
