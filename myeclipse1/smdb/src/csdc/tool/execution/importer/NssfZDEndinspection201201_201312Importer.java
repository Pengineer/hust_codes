package csdc.tool.execution.importer;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java_cup.internal_error;

import oracle.net.aso.n;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.bean.Agency;
import csdc.bean.Nssf;
import csdc.tool.StringTool;
import csdc.tool.execution.finder.UniversityFinder;
import csdc.tool.execution.importer.Importer;
import csdc.tool.execution.importer.tool.Tool;
import csdc.tool.reader.ExcelReader;

/**
 * 导入《国家社科基金重大项目2012年1月-2013年12月结项名单_修正导入.xls》
 * @author maowh
 *备注：经过校验核实，立项数据全部存在，有76条不存在结项数据，仅更新这76条的成果、证书和结项时间（smdb）
 */
public class NssfZDEndinspection201201_201312Importer extends Importer {

	private ExcelReader excelReader;
	
	/**
	 * [项目批准号+负责人] -> [NssfId]
	 */
	private Map<String, String> map;
	private Map<String, String> endMap;
	
	/**
	 * [项目批准号+项目名称] -> [NssfId]
	 */
	private Map<String, String> nameMap;
	private Map<String, String> endNameMap;
	
	
	
	@Autowired
	private Tool tool;
	
	@Autowired
	private UniversityFinder universityFinder;

	public void work() throws Exception {
//		validate();
		importData();
	}
	
	private void importData() throws Exception {
		excelReader.readSheet(0);
		
		if (map == null) {
			initMap();
		}
		
		int k=0;
		
		while (next(excelReader)) {
			
			System.out.println(excelReader.getCurrentRowIndex() + "/" + excelReader.getRowNumber());
			
			if (A == null || A.isEmpty()) {
				break;
			}
			
			String numAndName = A + B;
			
			String endProjectId = endNameMap.get(numAndName);
			String projectId = nameMap.get(numAndName);
			//对应有立项信息，但是没有结项信息，添加结项信息（经核实，应该有76条）
			if (endProjectId == null && projectId != null) {
				k++;
				Nssf nssf = dao.query(Nssf.class,projectId);
				nssf.setProductName(C);
				nssf.setCertificate(F);
				nssf.setEndDate(tool.getDate(G));
				nssf.setStatus(2);
				dao.addOrModify(nssf);
			}
		System.out.println(k);
		}
	}

	public NssfZDEndinspection201201_201312Importer() {
	}

	public NssfZDEndinspection201201_201312Importer(String filePath) {
		excelReader = new ExcelReader(filePath);
	}
	
	private void validate() throws Exception {
		
		excelReader.readSheet(0);
		int i = 0;
		int k = 0;
		int j = 0;
		if (map == null) {
			initMap();
		}
		
		while (next(excelReader)) {			
			if (A == null || A.isEmpty()) {
				break;
			}
			String key = A + D;
			String numAndName = A + B;
			String endProjectId = endMap.get(numAndName);
			String projectId = map.get(key);
			if (projectId == null) {
				k++;
				System.out.println("该项目库中不存在结项也不存在立项：" + A + D + B);
				System.out.println(excelReader.getCurrentRowIndex() + "/" + excelReader.getRowNumber());
				if (nameMap.get(numAndName) == null) {
					j++;
					System.out.println("该项目库通过批准号和申请人没有找到，但是通过批准号和项目名称找到了：" + A + D + B);
				}
			}
			if (endProjectId == null && nameMap.get(numAndName) != null) {
				i++;
				System.out.println("该项目库中不存在结项但存在立项：" + A + D + B);
				System.out.println(excelReader.getCurrentRowIndex() + "/" + excelReader.getRowNumber());
				System.out.println("i=" + i);
				if (endNameMap.get(numAndName) != null) {
					System.out.println("该项目的结项信息通过批准号和申请人没有找到，但是通过批准号和项目名称找到了：" + A + D + B);
				}
			}
			
			Agency university = universityFinder.getUnivByName(E);
			if (university == null) {
				System.out.println("该高校不存在：" + E);
			}
		}	
		System.out.println(k);
		System.out.println(i);
		System.out.println(j);
	}

	private void initMap() {

		Date begin = new Date();
		
		endMap = new HashMap<String, String>(); 
		map = new HashMap<String, String>(); 
		nameMap = new HashMap<String, String>();
		endNameMap = new HashMap<String, String>();
		
		List<Object[]> endList = dao.query("select nssf.number,nssf.id, nssf.applicant, nssf.name from Nssf nssf where nssf.endDate is not null");//已存在结项信息的项目
		List<Object[]> list = dao.query("select nssf.number,nssf.id, nssf.applicant, nssf.name from Nssf nssf");
		
		for (Object[] o : endList) {
			String number = (String) o[0];
			String id = (String) o[1];	
			String applicant = (String) o[2];
			String name = (String) o[3];
			endMap.put(number + applicant, id);
			endNameMap.put(number + name, id);
		}
		
		for (Object[] o : list) {
			String number = (String) o[0];
			String id = (String) o[1];
			String applicant = (String) o[2];	
			String name = (String) o[3];
			map.put(number + applicant, id);
			nameMap.put(number+name, id);
		}
		
		System.out.println("initMap complete! Used time: " + (new Date().getTime() - begin.getTime()) + "ms");				
	}
}


