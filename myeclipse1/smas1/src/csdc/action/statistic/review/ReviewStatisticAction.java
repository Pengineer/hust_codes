package csdc.action.statistic.review;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletResponse;

import jxl.read.biff.BiffException;

import org.apache.struts2.ServletActionContext;

import csdc.action.BaseAction;
import csdc.bean.ProjectApplication;
import csdc.bean.Tes;
import csdc.tool.HSSFExport;

/**
 * 参评统计
 * @author Administrator
 *
 */
@SuppressWarnings("unchecked")
public class ReviewStatisticAction extends BaseAction {
	
	private static final long serialVersionUID = 1L;
	private Integer jumpType;
	private Integer jumpPage;
	public static String sbsString;
	protected static final String DATE_FORMAT = "yyyy-MM-dd";// 列表时间格式
	protected int isContainP;// 是否包含备评0不包含，1包含
	protected String universityCode;// 高校代码
	
	/**
	 * 负责人参与项目总数（含本人项目）
	 * @return
	 * @throws Exception
	 */
	public String statisticOne() throws Exception {
		System.out.println(">>>>>>>>>>>>>>0");
		HttpServletResponse response = ServletActionContext.getResponse();
		Vector v = new Vector();
		String[] oo = {"导出", "姓名", "高校名称", "院系名称", "职称","校内数目", "校外数目", "总数目"};
		int sum = 0;
		int[] re;
		List<ProjectApplication> list =  baseService.query(" select p from ProjectApplication p where p.type = 'general' and p.isGranted = 1 ");
		System.out.println(list.size());
		StringBuffer sb = new StringBuffer();
		sb.append("， ");
		for(int i = 0; i < list.size(); i++) {
			if(list.get(i).getMembers()!= null) {
				String[] yyStrings = list.get(i).getMembers().split("，");
				for(int j = 0; j < yyStrings.length; j++) {
					yyStrings[j] = yyStrings[j].trim();
					if(yyStrings[j] != null && yyStrings[j].indexOf('(') >= 0 && yyStrings[j].indexOf(')') >= 0)
						if(yyStrings[j].indexOf(list.get(i).getUniversityName()) >= 0) {
							sb.append(yyStrings[j] + "1， ");
						} else {
							sb.append(yyStrings[j] + "0， ");
						}
				}
			}
		}
		sbsString = sb.toString();
		for(int i = 0; i < list.size(); i++) {
			re = finder1("， " + list.get(i).getDirector() + "(" + list.get(i).getUniversityName());
			if(re[0] > 1) {
				sum++;
				List pList = new ArrayList<Object>();
				pList.add(list.get(i).getDirector());
				pList.add(list.get(i).getUniversityName());
				pList.add(list.get(i).getDepartment());
				pList.add(list.get(i).getTitle());
				pList.add(re[1]);
				pList.add(re[0] - re[1]);
				pList.add(re[0]);
				Object obj[] = pList.toArray(new Object[0]);
				for (int j = 0; j < obj.length; j++){
					if (obj[j] == null){
						obj[j] = "";
					}
				}
				v.add(obj);
			}
		}
		
		System.out.println("sum=" + sum);
		
		HSSFExport.commonExportData(oo, v, response);
		System.out.println(">>>>>>>>>>>>>>1");
		return SUCCESS;
	}
	
	public String statisticTwo() throws IndexOutOfBoundsException, BiffException, FileNotFoundException, IOException {
		int c = 0;
		List<ProjectApplication> el = (List<ProjectApplication>) baseService.query("select p from ProjectApplication p where p.type = 'general' and p.director like '% %' ");
		System.out.println(">>> " + el.size());
		for(int i = 0; i < el.size(); i++) {
			ProjectApplication e = el.get(i);
			String mString = e.getDirector();
			if(mString != null && !mString.isEmpty()) {
				mString = mString.replace(" ", "");
				//mString = mString.replace("  ", "");
				//mString = mString.replace("   ", "");
				
				//if(mString.endsWith("，"))
				//	mString = mString.substring(0, mString.length() - 1);
				e.setDirector(mString);
			    
				baseService.modify(e);
				System.out.println(++c);
			}
		}
		
		
		/*File xls = new File("E:\\gshn.xls");
		Sheet rs = Workbook.getWorkbook(new FileInputStream(xls)).getSheet(0);
		int c = 0;
		List<String> notFoundList = new ArrayList();
		for (int i = 1; i < rs.getRows(); i++) {
			//String unicString = rs.getCell(0, i).getContents();
			String nameString = rs.getCell(5, i).getContents();
			List<Project> el = (List<Project>) baseService.query(" select p from Project p  where p.projectName like '" + nameString + "' ");
			if(el != null && el.size() == 1) {
				//System.out.println(++c);
				//System.out.println(nameString + " " + unicString);
				Project e = el.get(0);
				e.setIsGranted(1);
				String mString = e.getMembers();
				if(mString != null && !mString.isEmpty()) {
					mString = mString.replace("　", "");
					mString = mString.replace("  ", "");
					mString = mString.replace("   ", "");
					
					if(mString.endsWith("，"))
						mString = mString.substring(0, mString.length() - 1);
					e.setMembers(mString);
				
					baseService.modify(e);
					System.out.println(++c);
				}
				//System.out.println(e.getName() + " " + e.getMobilePhone() + " " + e.getUniversityCode());
			} else {
				notFoundList.add(nameString);
			}
			for(int j = 0; j < notFoundList.size(); j++) {
				System.out.println(notFoundList.get(j));
			}*/
		
		return SUCCESS;
	}
	
	/**
	 * 参与人（不含负责人）参与项目总数
	 * @return
	 * @throws Exception
	 */
	public String statisticThree() throws Exception {
		HttpServletResponse response = ServletActionContext.getResponse();
		Vector v = new Vector();
		String[] oo = {"导出", "姓名", "高校院系", "校内数目", "校外数目", "总数目"};
		List<ProjectApplication> list = baseService.query(" select p from ProjectApplication p where p.type = 'general' and p.isGranted = 1 ");
		System.out.println(list.size());
		StringBuffer sb = new StringBuffer();
		sb.append("， ");
		for(int i = 0; i < list.size(); i++) {
			if(list.get(i).getMembers()!= null) {
				String[] yyStrings = list.get(i).getMembers().split("，");
				for(int j = 0; j < yyStrings.length; j++) {
					yyStrings[j] = yyStrings[j].trim();
					if(yyStrings[j].indexOf('(') >= 0 && yyStrings[j].indexOf(')') >= 0)
					if(yyStrings[j].indexOf(list.get(i).getUniversityName()) >= 0) {
						sb.append(yyStrings[j] + "1， ");
					} else {
						sb.append(yyStrings[j] + "0， ");
					}
				}
			}
			//sb.append(list.get(i).getMembers() + ", ");
		}
		System.out.println(">>>>>>>>>>>>>>0");
		sbsString = sb.toString();
		System.out.println(sbsString.length());
		int count = 0, cc = 0;
		for(int i = 0; i < list.size(); i++) {
			count = finder("， " + list.get(i).getDirector() + "(" + list.get(i).getUniversityName());
			if(count > 1) {
				System.out.println(cc++);
				sbsString = sbsString.replace(list.get(i).getDirector() + "(" + list.get(i).getUniversityName() + ")0， ", "");
				sbsString = sbsString.replace(list.get(i).getDirector() + "(" + list.get(i).getDepartment() + ")0， ", "");
				sbsString = sbsString.replace(list.get(i).getDirector() + "(" + list.get(i).getUniversityName() + ")1， ", "");
				sbsString = sbsString.replace(list.get(i).getDirector() + "(" + list.get(i).getDepartment() + ")1， ", "");
			}
		}
		String[] l = sbsString.split("，");
		System.out.println(l.length);
		System.out.println(">>>>>>>>>>>>>>1");
		System.out.println(l[1]);
		System.out.println(l[10]);
		System.out.println(l[100]);
		System.out.println(l[1000]);
		
		String ns;
		List<Tes> tList = new ArrayList<Tes>();
		Tes t = null;
		for(int i = 0; i < l.length; i++) {
			l[i] = l[i].trim();
			if(l[i] != null && !l[i].isEmpty()) {
				ns = l[i].replace(')', '，');
				ns = ns.replace('(', '，');
				if(ns.indexOf('，') >= 0) {
					String[] xStrings = ns.split("，");
					int num = 0;
					try {
						num = Integer.parseInt(xStrings[2].trim());
					} catch (Exception e) {
						// TODO: handle exception
					}
					t = new Tes(xStrings[0].trim(), xStrings[1].trim(), num);
					tList.add(t); 
				}
			}
		}
		System.out.println(">>>>>>>>>>>>>>1.5");
		Comparator comparator = new Comparator() {  
			public int compare(Object arg0, Object arg1) {
				Tes user0 = (Tes) arg0;
				Tes user1 = (Tes) arg1;
				int flag = user0.getName().compareTo(user1.getName());
				if (flag == 0) {
					return user0.getUni().compareTo(user1.getUni());
				} else {
					return flag;
				}
			}
		};
		Collections.sort(tList, comparator);
		System.out.println(tList.get(0).getName()  + " " + tList.get(0).getUni());
		System.out.println(tList.get(10).getName()  + " " + tList.get(10).getUni());
		System.out.println(tList.get(100).getName()  + " " + tList.get(100).getUni());
		System.out.println(tList.get(1000).getName()  + " " + tList.get(100).getUni());
		System.out.println(">>>>>>>>>>>>>>2");
		String prename = "xxxx";
		String preuni = "xxxx";
		int cnnnt = 1;
		int sumn = 0;
		for(int i = 0; i < tList.size(); i++) {
			Tes ttt = tList.get(i);
			if(ttt.getName().equals(prename) && preuni.indexOf(ttt.getUni()) >= 0)
				preuni = ttt.getUni();
			if(ttt.getName().equals(prename) && ttt.getUni().indexOf(preuni) >= 0) {
				cnnnt++;
				sumn += ttt.getFlag();
			} else {
				
				if(cnnnt > 1) {
					List pList = new ArrayList<Object>();
					pList.add(prename);
					pList.add(preuni);
					pList.add(sumn);
					pList.add(cnnnt - sumn);
					pList.add(cnnnt);
					
					Object obj[] = pList.toArray(new Object[0]);
					for (int j = 0; j < obj.length; j++){
						if (obj[j] == null){
							obj[j] = "";
						}
					}
					v.add(obj);
				}
				prename = ttt.getName();
				preuni = ttt.getUni();
				cnnnt = 1;
				sumn = ttt.getFlag();
			}
		}
		System.out.println(">>>>>>>>>>>>>>3");
		HSSFExport.commonExportData(oo, v, response);
		return SUCCESS;
	}
	
	public static int finder(String str) {
		int count = 0;
		int m = sbsString.indexOf(str);
		while (m != -1) {
			m = sbsString.indexOf(str, m + 1);
			count++;
		}
		return count;
	}
	
	public static int[] finder1(String str) {
		int count = 0;
		int m = sbsString.indexOf(str);
		int pk;
		int pd;
		int num = 0;
		if(m != -1) {
			pk = sbsString.indexOf(')', m + 1);
			pd = sbsString.indexOf('，', m + 1);
			if(pk > 0 && pd - pk == 2 )
				num += sbsString.charAt(pk + 1) - '0';
		}
		while (m != -1) {
			m = sbsString.indexOf(str, m + 1);
			if(m != -1) {
				pk = sbsString.indexOf(')', m + 1);
				pd = sbsString.indexOf('，', m + 1);
				if(pk > 0 && pd - pk == 2)
					num += sbsString.charAt(pk + 1) - '0';
			}
			count++;
		}
		int[] x = new int[2];
		x[0] = count + 1;
		x[1] = num + 1;
		return x;
	}
	
	
	@Override
	public String dateFormat() {
		// TODO Auto-generated method stub
		return DATE_FORMAT;
	}
	@Override
	public String[] column() {
		return null;
	}
	@Override
	public String pageBufferId() {
		return null;
	}
	@Override
	public String pageName() {
		return null;
	}
	@Override
	public Object[] advSearchCondition() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Object[] simpleSearchCondition() {
		return null;
	}
	
	public int getIsContainP() {
		return isContainP;
	}
	public void setIsContainP(int isContainP) {
		this.isContainP = isContainP;
	}
	public String getUniversityCode() {
		return universityCode;
	}
	public void setUniversityCode(String universityCode) {
		this.universityCode = universityCode;
	}
	public Integer getJumpType() {
		return jumpType;
	}
	public void setJumpType(Integer jumpType) {
		this.jumpType = jumpType;
	}
	public Integer getJumpPage() {
		return jumpPage;
	}
	public void setJumpPage(Integer jumpPage) {
		this.jumpPage = jumpPage;
	}
}
