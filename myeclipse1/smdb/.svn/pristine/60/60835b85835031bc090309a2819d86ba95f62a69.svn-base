package csdc.tool.execution.importer;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.bean.Agency;
import csdc.bean.GeneralSpecial;
import csdc.tool.StringTool;
import csdc.tool.execution.finder.UniversityFinder;
import csdc.tool.reader.ExcelReader;

/**
 * 《教育部人文社会科学专项任务项目在研数据（截至2014年3月17日）_修正导入.xls》
 * @author maowh
 * 备注：
 * 
 */
public class GeneralSpecial20140317Importer extends Importer {
	
	private ExcelReader excelReader;
	
	@Autowired
	private UniversityFinder universityFinder;
	
	/**
	 * [项目批准号+项目名称] -> [GeneralSpecialId]
	 */
	private Map<String, String> map;
		
	
	public GeneralSpecial20140317Importer() {}
	
	public GeneralSpecial20140317Importer(String filePath) {
		excelReader = new ExcelReader(filePath);
	}
	

	@Override
	public void work() throws Exception {
		importData();
//		validate();
	}

	/**
	 * 导入数
	 * @throws Exception 
	 */
	private void importData() throws Exception {
		
		excelReader.readSheet(6);
		int i = 0;
		if (map == null) {
			initMap();
		}
		
		while (next(excelReader)) {
			
			System.out.println(excelReader.getCurrentRowIndex() + "/" + excelReader.getRowNumber());
			String key = A;
			String generalSpecialId = map.get(key);
			if (generalSpecialId == null) {
				i++;
				GeneralSpecial generalSpecial = new GeneralSpecial();
				generalSpecial.setUnit(C);
				Agency university = universityFinder.getUnivByName(C);
				if (university != null) {
					generalSpecial.setUniversity(C);
					generalSpecial.setUniversityId(university.getId());
				}
				if (B.length() > 0) {
					generalSpecial.setName(B);
				}
				if (A.length() > 0) {
					generalSpecial.setNumber(A);
				}
				generalSpecial.setApplicant(D.replaceAll("\\s+", "").replaceAll("、", "; "));
				generalSpecial.setTopic(E);
				generalSpecial.setImportDate(new Date());
				dao.add(generalSpecial);
			}	
			System.out.println("共新增：" + i);
		}
	}
	

	private void validate() throws Exception {
		
		excelReader.readSheet(6);
		int i = 0;
		if (map == null) {
			initMap();
		}
		
		while (next(excelReader)) {
			String key = A;
			String generalSpecialId = map.get(key);
			if (generalSpecialId != null) {
				i++;
				System.out.println("该项目库中存在：" + A + B);
				System.out.println("i=" + i);
			}
//			Agency university = universityFinder.getUnivByName(C);
//			if (university == null) {
//				i++;
//				System.out.println("该高校不存在：" + C);
//			}
		}		
	}

	private void initMap() {

		Date begin = new Date();
		
		map = new HashMap<String, String>(); 
		List<Object[]> list = dao.query("select gs.number,gs.id from GeneralSpecial gs");
		for (Object[] o : list) {
			String number = (String) o[0];
			String id = (String) o[1];
			
			map.put(number, id);
		}

		System.out.println("initMap complete! Used time: " + (new Date().getTime() - begin.getTime()) + "ms");		
	}

}

