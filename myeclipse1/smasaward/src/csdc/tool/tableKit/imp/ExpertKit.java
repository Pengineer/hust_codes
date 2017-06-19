package csdc.tool.tableKit.imp;

import java.io.File;
import java.io.FileInputStream;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Sheet;
import jxl.Workbook;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ActionContext;

import csdc.bean.Expert;
import csdc.bean.SystemOption;
import csdc.bean.University;
import csdc.service.IBaseService;
import csdc.service.IGeneralService;
import csdc.tool.DatetimeTool;
import csdc.tool.HSSFExport;
import csdc.tool.tableKit.ITableKit;
import csdc.tool.validator.IValidator;
import csdc.tool.validator.imp.DummyValidator;

/**
 * 专家表工具包
 * @author 徐涵
 *
 */
@Component
@Scope("prototype")
public class ExpertKit implements ITableKit{
	/**
	 * 用于在页面显示list
	 */
	private List<List<String> > list;

	/**
	 * 用于导入到数据库
	 */
	private List<Expert> blist;
	//private List<String> title;
	@Autowired
	private IBaseService baseService;
	@Autowired
	private IGeneralService generalService;

	
	static private List<String> stdTitle = new ArrayList<String>();
	static {
		stdTitle.add("高校代码");
		stdTitle.add("高校名称");
		stdTitle.add("姓名");
		stdTitle.add("身份证号码");
		stdTitle.add("性别");
		stdTitle.add("出生年月");
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
		stdTitle.add("申报年份");
		stdTitle.add("参评年份");
		stdTitle.add("评价等级");
		stdTitle.add("参评情况");
		stdTitle.add("退评原因");
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
		
		HashMap<String, List> discMap = new HashMap<String, List>();
		blist = new ArrayList<Expert>();
		for (int i = 1; i < list.size(); i++){
			//System.out.println(i + " / " + list.size());
			
			Expert expert = new Expert();
			
			String warning = "";

			List<University> ulist = baseService.query("select u from University u where u.code = '" + list.get(i).get(0).trim() + "'");
			University uByCode = null, uByName = null;
			if (ulist.size() > 0){
				uByCode = ulist.get(0);
			}
			ulist = baseService.query("select u from University u where u.name = '" + list.get(i).get(1).trim() + "'");
			if (ulist.size() > 0){
				uByName = ulist.get(0);
			}
			if (uByName != null){
				expert.setUniversityCode(uByName.getCode());
			} else if (uByCode != null){
				expert.setUniversityCode(uByCode.getCode());
				warning = "高校名称错误，以高校代码为准 ";
			} else {
				warning = "高校名称、代码均错";
			}
			
			expert.setName(list.get(i).get(2).replaceAll("[^\\u4e00-\\u9fa5·]", ""));
			expert.setIdCardNumber(list.get(i).get(3).replaceAll("[,'，‘\\s]", ""));
			expert.setGender(list.get(i).get(4).trim());
			expert.setBirthday(DatetimeTool.getDate(list.get(i).get(5)));
			expert.setSpecialityTitle(list.get(i).get(6).trim());
			expert.setDepartment(list.get(i).get(7).trim());
			expert.setJob(list.get(i).get(8).trim());
			expert.setIsDoctorTutor(list.get(i).get(9).trim());
			expert.setPositionLevel(list.get(i).get(10).trim());
			expert.setDegree(list.get(i).get(11).trim());
			expert.setLanguage(list.get(i).get(12).trim());
			expert.setHomePhone(list.get(i).get(13).trim());
			expert.setMobilePhone(list.get(i).get(14).replaceAll("\\s+", ";"));
			expert.setOfficePhone(list.get(i).get(15).trim());
			expert.setEmail(list.get(i).get(16).trim());
			expert.setPartTime(list.get(i).get(17).trim());
			
			String disc[], cur;
			List<String> dlist = new ArrayList<String>();
			for (int j = 18; j <= 27; j++){
				if (j == 24){
					j = 27;
				}
				disc = list.get(i).get(j).split("[^\\.\\d\\w\\u4e00-\\u9fa5]");
				for (int k = 0; k < disc.length; k++){
					cur = disc[k].replaceAll("\\.", "");
					//System.out.println("cur = " + cur);
					if (cur.matches("\\d+")){
						if (cur.length() > 7 || cur.length() < 3)
							continue;
						while (cur.length() < 7)
							cur += "0";
						while (cur.length() >= 2 && cur.substring(cur.length()-2).equals("00"))
							cur = cur.substring(0, cur.length()-2);
						if (!cur.isEmpty())
							dlist.add(cur);
					} else if (cur.matches("[\\u4e00-\\u9fa5]+")){
						List<SystemOption> soList;
						if (discMap.containsKey(cur)){
							soList = discMap.get(cur);
						} else {
							soList = baseService.query("select so from SystemOption so where so.standard like '%GBT13745%' and so.name = '" + cur + "'");
							discMap.put(cur, soList);
						}
						for (SystemOption so : soList) {
							dlist.add(so.getCode());
						}
					}
				}
			}
			
			Collections.sort(dlist, new Comparator() {  
				public int compare(Object o1, Object o2) {
					String a = (String)o1, b = (String)o2;
					return a.compareTo(b) < 0 ? -1 : 1;
				}
			});
			cur = "";
			for (int j = 0; j < dlist.size(); j++){
				if (j == dlist.size() - 1 || !dlist.get(j+1).startsWith(dlist.get(j))){
					if (baseService.query("select so.id from SystemOption so where so.standard like '%GBT13745%' and so.code = '" + dlist.get(j) + "'").size() == 0){
						warning += (warning.isEmpty() ? "" : "; ") + dlist.get(j) + "不存在";
					} else {
						cur += (cur.isEmpty() ? "" : "; ") + dlist.get(j);
					}
				}
			}
			expert.setDiscipline(cur);
			
			expert.setMoeProject(list.get(i).get(24).trim());
			expert.setNationalProject(list.get(i).get(25).trim());
			expert.setAward(list.get(i).get(26).trim());
			expert.setResearchField(list.get(i).get(27).trim());
			expert.setOfficeAddress(list.get(i).get(28).trim());
			expert.setOfficePostcode(list.get(i).get(29).trim());
			expert.setRemark(list.get(i).get(30));
			expert.setWarning(warning);
			expert.setIsReviewer(1);
			
			blist.add(expert);
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

	/**
	 * 仅导出专家姓名、编号、高校代码、高校名称、学科代码及名称、手机、邮箱
	 * @param exportAll
	 * @param countReviewer
	 * @throws Exception
	 */
	public void exportExpert(int exportAll, int countReviewer ,HttpServletRequest request) throws Exception {
		Map session = ActionContext.getContext().getSession();
		Map application = ActionContext.getContext().getApplication();
		Map map4ExpertExport = (Map) session.get("Map4ExpertExport");
		
		HttpServletResponse response = ServletActionContext.getResponse();
		Vector v = new Vector();
		List<String> titleList = new ArrayList();
		titleList.add("专家表");
		titleList.add("专家代码");
		titleList.add("专家姓名");
		if((Integer) map4ExpertExport.get("expertType") == 1) {
			titleList.add("高校代码");
			titleList.add("高校名称");			
		} else if((Integer) map4ExpertExport.get("expertType") == 2) {
			titleList.add("单位代码");
			titleList.add("单位名称");			
		}
		titleList.add("手机");
		titleList.add("邮箱");
		titleList.add("一级学科代码");
		titleList.add("一级学科名称");
		titleList.add("二级学科代码");
		titleList.add("二级学科名称");
		titleList.add("三级学科代码");
		titleList.add("三级学科名称");
		String[] oo = titleList.toArray(new String[0]);

		HashMap<String, String> univMap = (HashMap<String, String>) application.get("univCodeNameMap");
		HashMap<String, String> discMap = (HashMap<String, String>) application.get("disMap");
		
		if (exportAll != 0){
			list = baseService.query("select expert.number, expert.name, expert.universityCode, expert.mobilePhone, expert.email, expert.discipline from Expert expert order by expert.universityCode asc, expert.name asc");
		} else {
			if((Integer) map4ExpertExport.get("isReviewer") == 0) {//如果是退评专家列表
				list = baseService.query((String) session.get("HQL4ExpertExport"), (Map)session.get("Map4ExpertExport"));				
			} else {				
				//如果是参评专家列表
				String specialityTitles  = request.getParameter("specialityTitles");//专业职称
				specialityTitles = URLDecoder.decode(specialityTitles, "UTF-8");
				String disciplines   = request.getParameter("disciplines");//学科代码
				disciplines  = URLDecoder.decode(disciplines, "UTF-8");
				String reviewExpertType = request.getParameter("reviewExpertType");
				reviewExpertType = URLDecoder.decode(reviewExpertType, "UTF-8");//参评专家类型
				int universityType = Integer.parseInt(request.getParameter("universityType"));//0，所有高校;1，部属高校;2，地方高校				
				Integer maximunNumber = null; //导出专家最大数量
				if(request.getParameter("maximunNumber") != null && !request.getParameter("maximunNumber").isEmpty()) {
					maximunNumber = Integer.parseInt(request.getParameter("maximunNumber"));
				} 
				String reviewExpertProjectType  = request.getParameter("reviewExpertProjectType");//评审专家项目类型
				reviewExpertProjectType = URLDecoder.decode(reviewExpertProjectType, "UTF-8");
				
				Map map = map4ExpertExport;
				String expertHQL = null;
				if (reviewExpertProjectType.equals("所有项目")) {
					expertHQL = "select expert.number, expert.name, expert.universityCode, expert.mobilePhone, expert.email, expert.discipline, count(pr.id) from Expert expert left join expert.applicationReview pr with pr.year = :defaultYear, University u where expert.universityCode = u.code and expert.isReviewer = 1 and expert.expertType = :expertType";
				} else {
					if (reviewExpertProjectType.equals("一般项目")) {
						map.put("reviewExpertProjectType", "general");
					} else if (reviewExpertProjectType.equals("基地项目")) {
						map.put("reviewExpertProjectType", "instp");
					}
					expertHQL = "select expert.number, expert.name, expert.universityCode, expert.mobilePhone, expert.email, expert.discipline, count(pr.id) from Expert expert left join expert.applicationReview pr with pr.year = :defaultYear and pr.type = :reviewExpertProjectType, University u where expert.universityCode = u.code and expert.isReviewer = 1 and expert.expertType = :expertType";
				}
//				StringBuffer hql4Export = new StringBuffer("select expert.number, expert.name, expert.universityCode, expert.mobilePhone, expert.email, expert.discipline, count(pr.id) from Expert expert left join expert.applicationReview pr with pr.year = :defaultYear and pr.type = 'general', University u where expert.universityCode = u.code and expert.isReviewer = 1 and expert.expertType = :expertType");
				//（1）专家评审项目类型过滤条件
				StringBuffer hql4Export = new StringBuffer(expertHQL);

				//（2）专家职称过滤条件
				String[] speTitlesArray = specialityTitles.split(";");
				if(speTitlesArray != null && speTitlesArray.length > 0 && !speTitlesArray[0].isEmpty()) {
					if(!speTitlesArray[0].equals("所有")) {
						List<String> speTitlesList = Arrays.asList(speTitlesArray);
						List<Object[]> systemOptionList = baseService.query("select so.code,so.name,so.description from SystemOption so where so.standard = 'GBT8561-2001' and so.description is not null");
						HashMap<String, String> codeName2Description = new HashMap<String, String>();
						for (Object[] objects : systemOptionList) {
							String code = objects[0].toString();
							String name = objects[1].toString();
							String description = objects[2].toString();
							codeName2Description.put(code + "/" + name,description);
						}
						List<Object> expertSeciality = baseService.query("select distinct expert.specialityTitle from Expert expert where expert.specialityTitle is not null");
						List<String> selectedSpecTitles = new ArrayList<String>();//已选中专业分类职称专家
						for(Object object : expertSeciality) {
							String specialityTitle = object.toString();
							if (codeName2Description.get(specialityTitle) != null) {
								if (speTitlesList.contains(codeName2Description.get(specialityTitle))) {
									selectedSpecTitles.add(specialityTitle);
								}
							}else{
								System.out.println("没找到该专业职称对应的级别" + specialityTitle);
							}					
						}
						if(selectedSpecTitles != null && !selectedSpecTitles.isEmpty()) {
							map.put("selectedSpecTitles", selectedSpecTitles);
							hql4Export.append(" and expert.specialityTitle in (:selectedSpecTitles)");
						} else {
							hql4Export.append(" and 1 = 0");
						}						
					}
				} else {
					hql4Export.append(" and 1 = 0");
				}			
				//（3）专家学科过滤条件
				String[] discArray = disciplines.split(";");
				map.put("discArray", discArray);				
				if(discArray != null && discArray.length > 0 && !discArray[0].isEmpty()) {
					hql4Export.append(" and ( expert.discipline like '" + discArray[0] + "%' or expert.discipline like '%; " + discArray[0] + "%' ");
					for(int i = 1; i < discArray.length; i++) {
						hql4Export.append(" or expert.discipline like '" + discArray[i] + "%' or expert.discipline like '%; " + discArray[i] + "%' ");
					}
					hql4Export.append(" ) ");					
				} else {
					hql4Export.append(" and 1 = 0");
				}
				
				//（4）专家所在高校过滤条件
				if(universityType == 0) {}
				else if(universityType == 1) {
					hql4Export.append(" and u.founderCode in ('308', '339', '360', '435')");
				} else if(universityType == 2) {
					hql4Export.append(" and u.founderCode not in ('308', '339', '360', '435')");
				} else {
					hql4Export.append(" and 1 = 0");
				}
				
				//（5）专家参评情况过滤条件
				String[] reTypeArray = reviewExpertType.split(";");
				if(reTypeArray != null && reTypeArray.length > 0 && !reTypeArray[0].isEmpty()) {
					if(!reTypeArray[0].equals("所有参评专家")) {
						List<String> reTypeList = Arrays.asList(reTypeArray);
						if(reTypeList.contains("参与匹配专家") && reTypeList.contains("已匹配专家")) {
							Map disciplineMap = generalService.selectSpecialityTitleInfo();
							map.put("aboveSubSeniorTitles", disciplineMap.get("aboveSubSeniorTitles"));
							map.put("seniorTitles", disciplineMap.get("seniorTitles"));
							hql4Export.append(" and (1 = 1");
							hql4Export.append(generalService.selectReviewMatchExpert());
							hql4Export.append(" or pr.id is not null)");
						} else if(reTypeList.contains("参与匹配专家")) {
							Map disciplineMap = generalService.selectSpecialityTitleInfo();
							map.put("aboveSubSeniorTitles", disciplineMap.get("aboveSubSeniorTitles"));
							map.put("seniorTitles", disciplineMap.get("seniorTitles"));
//							map.put("selectedUnivCodes", disciplineMap.get("selectedUnivCodes"));
							hql4Export.append(generalService.selectReviewMatchExpert());
						} else if(reTypeList.contains("已匹配专家")) {
							hql4Export.append(" and pr.id is not null");							
						} else {
							hql4Export.append(" and 1 = 0");							
						}
					}					
				} else {
					hql4Export.append(" and 1 = 0");					
				}					
				hql4Export.append(" group by expert.number, expert.name, expert.universityCode, expert.mobilePhone, expert.email, expert.discipline, expert.id");
				hql4Export.append(" order by count(pr.id) desc, expert.name asc, expert.id asc");
				list = baseService.query(hql4Export.toString(), map);
				//数量上限过滤条件
				if(maximunNumber != null) {
					if(list.size() > maximunNumber ) {
						list = list.subList(0, maximunNumber);
					}
				}																										
			}
		}
		//查询出的数据做预处理：将所有的数据类型转换为字符串，若为null，则转换成“”
		List<List<String>> strList = new ArrayList<List<String>>();
		for(Object object : list) {
			Object[] o = (Object[]) object;
			List<String> aList = new ArrayList<String>();
			for(Object o1 : o ) {
				aList.add(o1 == null ? "" : o1.toString());
			}
			strList.add(aList);
		}
		for (List<String> object : strList) {			
			List eList = new ArrayList<Object>();
			eList.add(object.get(0));//专家代码
			eList.add(object.get(1));//专家姓名
			eList.add(object.get(2));//高校代码
			eList.add(univMap.get(object.get(2)));//高校名称
			String mobilePhone = object.get(3);//手机号码
			if(mobilePhone.indexOf(";") == -1) {
				eList.add(mobilePhone);
			} else {
				eList.add(mobilePhone.substring(0, mobilePhone.indexOf(";")));					
			}
			String email = object.get(4);//邮箱
			if(email.indexOf(";") == -1) {
				eList.add(email);
			} else {
				eList.add(email.substring(0, email.indexOf(";")));					
			}
			String discipline = object.get(5);//学科代码
			String disc[] = discipline.split("; ");
			Set<String> discSet = new HashSet<String>();
			Set<String> tmpSet = new HashSet<String>();
			for (String d : disc) {
				discSet.add(d);
			}
			String d3Code="", d3Name="", d2Code="", d2Name="", d1Code="", d1Name="";
			for(Iterator i = discSet.iterator(); i.hasNext();){ 
				String st = (String)i.next();
				if (st.length() == 7)
					tmpSet.add(st.substring(0, 5));
				if (st.length() >= 5)
					tmpSet.add(st.substring(0, 3));
			}
			discSet.addAll(tmpSet);
			for(Iterator i = discSet.iterator(); i.hasNext();){ 
				String st = (String)i.next();
				if (st.length() == 7){
					d3Code += (d3Code.isEmpty()?"":"; ") + st;
					d3Name += (d3Name.isEmpty()?"":"、") + discMap.get(st);
				} else if (st.length() == 5){
					d2Code += (d2Code.isEmpty()?"":"; ") + st;
					d2Name += (d2Name.isEmpty()?"":"、") + discMap.get(st);
				} else if (st.length() == 3){
					d1Code += (d1Code.isEmpty()?"":"; ") + st;
					d1Name += (d1Name.isEmpty()?"":"、") + discMap.get(st);
				}
			}
			eList.add(d1Code);
			eList.add(d1Name);
			eList.add(d2Code);
			eList.add(d2Name);
			eList.add(d3Code);
			eList.add(d3Name);
			
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
	
	/**
	 * 专家信息全面
	 * @param exportAll
	 * @param countReviewer
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void exportExpert_old(int exportAll, int countReviewer) throws Exception {
		Map session = ActionContext.getContext().getSession();
		Integer defaultYear = (Integer) session.get("defaultYear");
		
		HttpServletResponse response = ServletActionContext.getResponse();
		Vector v = new Vector();
		List<String> titleList = new ArrayList();
		titleList.add("专家表");
		titleList.add("专家代码");
		titleList.addAll(stdTitle);
		if (countReviewer != 0){
			titleList.add("参评项目总数");
		}
		String[] oo = titleList.toArray(new String[0]);

		List list = baseService.query("select u from University u");
		HashMap<String, String> univMap = new HashMap<String, String>();
		for (Object object : list) {
			University university = (University) object;
			univMap.put(university.getCode(), university.getName());
		}
		HashMap<String, String> discMap = new HashMap<String, String>();
		list = baseService.query("select so from SystemOption so where so.standard like '%GBT13745%'");
		for (Object object : list) {
			SystemOption so = (SystemOption) object;
			discMap.put(so.getCode(), so.getName());
		}
		
		Map cntMap1 = new HashMap<String, Integer>();
		if (countReviewer != 0){
			List revList = baseService.query("select pr.reviewer.id, count(pr.project.id) from ProjectApplicationReview pr where pr.type = 'general' and pr.year = " + defaultYear + " group by pr.reviewer.id");
			for (Object object : revList) {
				Object[] o = (Object[]) object;
				cntMap1.put(o[0], ((Long) o[1]).intValue());
			}
		}
		
		if (exportAll != 0){
			list = baseService.query("select e from Expert e order by e.universityCode asc, e.name asc");
		} else {
//			----临时导出------
//			//筛选所属高校办学类型为11和12，属性为参评、非重点人、专职人员，手机和邮箱全非空，当前年没申报项目， 评价等级大于限制阈值的专家
//			StringBuffer hql = new StringBuffer("select e from Expert e, University u where e.universityCode = u.code");
//			hql.append(" and (u.style like '11%' or u.style like '12%')");							//所属高校办学类型为11和12
//			hql.append(" and e.isReviewer = 1 and e.isKey = 0 and e.type = '专职人员'");				//参评，非重点人，专职人员
//			hql.append(" and e.email is not null and e.mobilePhone is not null");						//手机和邮箱全非空
//			hql.append(" and (e.generalApplyYears is null or e.generalApplyYears not like '%" + defaultYear + "%')");	//当前年没申报项目
//			hql.append(" and e.rating > 0 ");
//			list = baseService.query(hql.toString());
			list = baseService.query((String) session.get("HQL4ExpertExport"), (Map)session.get("Map4ExpertExport"));
		}
		
		for (Object object : list) {
			Expert e = (Expert) object;
			
			List eList = new ArrayList<Object>();
			eList.add(e.getNumber());
			eList.add(e.getUniversityCode());
			eList.add(univMap.get(e.getUniversityCode()));
			eList.add(e.getName());
			eList.add(e.getIdCardNumber());
			eList.add(e.getGender());
			eList.add(DatetimeTool.getYearMonthDateString(e.getBirthday()));
			eList.add(e.getSpecialityTitle());
			eList.add(e.getDepartment());
			eList.add(e.getJob());
			eList.add(e.getIsDoctorTutor());
			eList.add(e.getPositionLevel());
			eList.add(e.getDegree());
			eList.add(e.getLanguage());
			eList.add(e.getHomePhone());
			eList.add(e.getMobilePhone());
			eList.add(e.getOfficePhone());
			eList.add(e.getEmail());
			eList.add(e.getPartTime());
			
			if (e.getDiscipline() == null)
				e.setDiscipline("");
			String disc[] = e.getDiscipline().split("; ");
			Set<String> discSet = new HashSet<String>();
			Set<String> tmpSet = new HashSet<String>();
			for (String d : disc) {
				discSet.add(d);
			}
			String d3Code="", d3Name="", d2Code="", d2Name="", d1Code="", d1Name="";
			for(Iterator i = discSet.iterator(); i.hasNext();){ 
				String st = (String)i.next();
				if (st.length() == 7)
					tmpSet.add(st.substring(0, 5));
				if (st.length() >= 5)
					tmpSet.add(st.substring(0, 3));
			}
			discSet.addAll(tmpSet);
			for(Iterator i = discSet.iterator(); i.hasNext();){ 
				String st = (String)i.next();
				if (st.length() == 7){
					d3Code += (d3Code.isEmpty()?"":"; ") + st;
					d3Name += (d3Name.isEmpty()?"":"、") + discMap.get(st);
				} else if (st.length() == 5){
					d2Code += (d2Code.isEmpty()?"":"; ") + st;
					d2Name += (d2Name.isEmpty()?"":"、") + discMap.get(st);
				} else if (st.length() == 3){
					d1Code += (d1Code.isEmpty()?"":"; ") + st;
					d1Name += (d1Name.isEmpty()?"":"、") + discMap.get(st);
				}
			}
			eList.add(d1Code);
			eList.add(d1Name);
			eList.add(d2Code);
			eList.add(d2Name);
			eList.add(d3Code);
			eList.add(d3Name);
			eList.add(e.getMoeProject());
			eList.add(e.getNationalProject());
			eList.add(e.getAward());
			eList.add(e.getResearchField());
			eList.add(e.getOfficeAddress());
			eList.add(e.getOfficePostcode());
			eList.add(e.getRemark());
			eList.add(e.getGeneralApplyYears());
			eList.add(e.getGeneralReviewYears());
			eList.add(e.getRating());
			eList.add(Integer.valueOf(1).equals(e.getIsReviewer()) ? "参评" : "退评");
			eList.add(e.getReason());
			if (countReviewer != 0){
				Integer cnt1 = cntMap1.get(e.getId()) == null ? 0 : (Integer)cntMap1.get(e.getId());
				eList.add(cnt1);
			}
			
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
	
	/**
	 * 导出专家参评项目数
	 * @param hql
	 * @param map
	 * @throws Exception
	 */
	public void exportExpertStatistic(String hql,Map map) throws Exception {
		Map application = ActionContext.getContext().getApplication();
		
		HttpServletResponse response = ServletActionContext.getResponse();
		Vector v = new Vector();
		List<String> titleList = new ArrayList();
		titleList.add("专家参评项目数统计表");
		titleList.add("专家姓名");
		titleList.add("高校名称");
//		titleList.add("学科代码及名称");
//		titleList.add("是否参评");
//		titleList.add("评价等级");
		titleList.add("参评项目数");
		String[] oo = titleList.toArray(new String[0]);
		list = baseService.query(hql, map);	
		//获取学科名称与学科代码的映射
		List disList = (List) application.get("disall");//获取所有学科对象的list
		Map<String, String> disname2discode = new HashMap<String, String>();//学科名称与学科代码的映射
		for (Iterator iterator = disList.iterator(); iterator.hasNext();) {
			SystemOption so = (SystemOption) iterator.next();
			String disname = so.getName();
			String discode = so.getCode();
			disname2discode.put(discode, disname);
		}
		//学科代码
		String disciplineCode = "";
		//组装后的值：学科代码+学科名称
		String value = "";
		for (Object object : list) {
			Object[] o = (Object[]) object;			
			List eList = new ArrayList<Object>();
			eList.add(o[0]);
			eList.add(o[1]);
/*			if (o[6] != null) {
				disciplineCode = o[6].toString();//学科代码
				value = generalService.getDisciplineInfo(disname2discode, disciplineCode);
			}
			eList.add(value);
			if(o[7].equals(1)) {
				o[7] = "是";
			} else if (o[7].equals(0)) {
				o[7] = "否";
			}
			eList.add(o[7]);
			eList.add(o[4]);*/
			eList.add(o[2]);			
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
			
}
