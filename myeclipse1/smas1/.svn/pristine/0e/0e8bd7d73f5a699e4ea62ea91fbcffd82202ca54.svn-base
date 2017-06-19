package csdc.tool.execution.importer;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.bean.ProjectApplication;
import csdc.tool.execution.finder.InstpFinder;
import csdc.tool.reader.JxlExcelReader;

/**
 * 导入《2014年基地项目申报一览表-简化版.xls》中的学科信息
 * @author maowh
 *
 */
public class InstpApplication2014FixImporter extends Importer{

	private JxlExcelReader reader;
	
	@Autowired
	private InstpFinder instpFinder;
	
	@Autowired
	private Tools tool;
	
	/**
	 * 项目年度
	 */
	private int year = 2014;
	
	/**
	 * 学科代码 -> 学门类
	 */
	private Map<String, String> projectTypeMap;
	
	/**
	 * reader重置
	 * @throws Exception
	 */
	private void resetReader() throws Exception {
		reader.readSheet(0);
	}
	
	@Override
	public void work() throws Exception {
//		validate();
		importData();
	}
	
	private void validate() throws Exception {
		resetReader();
		
		if(projectTypeMap == null){
			initProjectTypeMap();
		}
		
		List<String> notFoundInstpList = new ArrayList<String>();//根据项目名称和负责人找不到项目的数据
		List<String> wrongDisciplineList = new ArrayList<String>();//学科代码在库中不存在数据
		List<String> notMatchDisciplintList = new ArrayList<String>();//学科代码和学科名称不匹配的数据
		while (next(reader)) {
			if (A == null || A.isEmpty()) {
				break;
			}
			
			System.out.println(reader.getCurrentRowIndex() + "/" + reader.getRowNumber());
			ProjectApplication instp = instpFinder.findInstp(D, R, year, false);
			
			if (instp == null) {
				notFoundInstpList.add(D + "--" + R);
			}
		}
		//一级学科校验
		if (!projectTypeMap.containsKey(L)) {
			wrongDisciplineList.add(L);
		} else {
			if (!projectTypeMap.get(L).equals(K)) {
				notMatchDisciplintList.add(K);
			}
		}
		//二级学科校验
		if (!projectTypeMap.containsKey(N)) {
			wrongDisciplineList.add(N);
		} else {
			if (!projectTypeMap.get(N).equals(M)) {
				notMatchDisciplintList.add(M);
			}
		}
		//三级学科校验
		if (!projectTypeMap.containsKey(P)) {
			wrongDisciplineList.add(P);
		} else {
			if (!projectTypeMap.get(P).equals(O)) {
				notMatchDisciplintList.add(O);
			}
		}
		
		for (int i = 0; i < notFoundInstpList.size(); i++) {
			System.out.println(notFoundInstpList.get(i));
		}
		
		for (int i = 0; i < wrongDisciplineList.size(); i++) {
			System.out.println(wrongDisciplineList.get(i));
		}
		
		for (int i = 0; i < notMatchDisciplintList.size(); i++) {
			System.out.println(notMatchDisciplintList.get(i));
		}
	}


	private void importData() throws Exception {
		resetReader();

		while (next(reader)) {			
			if (A == null || A.isEmpty()) {
				break;
			}
			System.out.println(reader.getCurrentRowIndex() + "/" + reader.getRowNumber());
			List<String> disciplines = new ArrayList<String>();
			//获取项目
			ProjectApplication instp = instpFinder.findInstp(D, R, year, false);
			String orgDiscipline = instp.getDiscipline() + "; " + L + "; " + N + "; " + P;
			String discipline = tool.transformDisc(orgDiscipline);
//			System.out.println(discipline);
			instp.setDiscipline(discipline);
			dao.addOrModify(instp);								
		}

	}
	
	public InstpApplication2014FixImporter() {
	}
	
	public InstpApplication2014FixImporter(String file) {
		reader = new JxlExcelReader(file);
	}
	
	
	/**
	 * 初始化projectTypeMap
	 */
	private void initProjectTypeMap() {

		Date begin = new Date();
		
		projectTypeMap = new HashMap<String, String>();
		List<Object[]> list = dao.query("select so.code, so.name from SystemOption so where so.standard = 'projectType' or so.standard = '[GBT13745-2009]'");
		for (Object[] o : list) {
			projectTypeMap.put((String)o[0], (String) o[1]);
		}
		
		System.out.println("initProjectTypeMap complete! Used time: " + (new Date().getTime() - begin.getTime()) + "ms");		
	}
	
	public static void main(String[] args) {
		ArrayList<String> testArrayList = new ArrayList<String>();
		testArrayList.add("702");
		testArrayList.add("56473");
		int i = testArrayList.indexOf("111");
		System.out.println(i);
	}
}

