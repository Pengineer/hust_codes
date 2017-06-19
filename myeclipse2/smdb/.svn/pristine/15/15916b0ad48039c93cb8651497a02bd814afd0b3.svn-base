package csdc.tool.execution.importer;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java_cup.internal_error;

import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;

import csdc.bean.Agency;
import csdc.bean.Nssf;
import csdc.tool.StringTool;
import csdc.tool.execution.finder.UniversityFinder;
import csdc.tool.execution.importer.tool.Tool;
import csdc.tool.reader.ExcelReader;

/**
 * Excel：《国家社科基金艺术学在研名单（截至2014年3月19日）.xls》，经核查，仅一条数据需要更新：“艺术与科学的互补问题研究（刘军）”。当库中申请人与excel不一致时，将excel中的信息更新到applicant_new中
 * @author maowh
 * @status 
 */
public class NssfArtGranted20140324Importer extends Importer {
	
	private ExcelReader excelReader;
	
	@Autowired
	private Tool tool;
	
	@Autowired
	private UniversityFinder universityFinder;
	
	/**
	 * [项目名称 + 负责人] -> [NssfId]
	 */
	private Map<String, String> map;
	
	public NssfArtGranted20140324Importer() {}
	
	public NssfArtGranted20140324Importer(String filePath) {
		excelReader = new ExcelReader(filePath);
	}
	

	@Override
	public void work() throws Exception {
//		importData();
		validate();//校验库中是否已存在这样的数据
	}

	private void validate() throws Exception {
		
		excelReader.readSheet(0);
		int i = 0;
		int k = 0;
		if (map == null) {
			initMap();
		}
		
		while (next(excelReader)) {
			String key = C;
			k++;
			String nssfId = map.get(key);
			Nssf nssf = dao.query(Nssf.class, nssfId);
			nssf.setSingleSubject("艺术学");
			nssf.setStatus(1);
			if (nssfId == null) {
				i++;
				System.out.println("该项目库中不存在：" + C + D);
				System.out.println("i=" + i);
			}
			
//			Agency university = universityFinder.getUnivByName(E);
//			if (university == null) {
//				k++;
//				System.out.println("该高校不存在：" + E);
//				System.out.println("k=" + k);
//			}
			
		}	
		System.out.println("k=" + k);		
	}

	private void initMap() {

		Date begin = new Date();
		
		map = new HashMap<String, String>(); 
		List<Object[]> list = dao.query("select nssf.name ,nssf.applicant, nssf.id from Nssf nssf");
		for (Object[] o : list) {
			String name = (String) o[0];
			String applicant = (String) o[1];
			String id = (String) o[2];
			
			map.put(name, id);
		}

		System.out.println("initMap complete! Used time: " + (new Date().getTime() - begin.getTime()) + "ms");		
	
		
	}

	/**
	 * 正式导入数据
	 * @throws Exception 
	 */
	private void importData() throws Exception {
		
		excelReader.readSheet(0);
		
		if (map == null) {
			initMap();
		}
		
		while (next(excelReader)) {		
			if ("刘军".equals(D)) {
				Nssf nssf = new Nssf();
				nssf.setName(C);
				nssf.setType("国家" + B);
				String test = "国家" + B;
				nssf.setApplicant(D);
				nssf.setUnit(E);
				Agency university = universityFinder.getUnivByName(E);
				if (university != null) {
					nssf.setUniversity(E);
					nssf.setUniversityId(university.getId());
				};
				nssf.setImportDate(new Date());
				nssf.setSingleSubject("艺术学");
				dao.add(nssf);
			}
			
	

		}
	}

}

