package csdc.tool.execution.importer;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java_cup.internal_error;

import org.springframework.beans.factory.annotation.Autowired;

import com.sun.xml.bind.v2.model.core.ID;

import csdc.bean.Agency;
import csdc.bean.Nssf;
import csdc.tool.StringTool;
import csdc.tool.execution.finder.UniversityFinder;
import csdc.tool.execution.importer.Importer;
import csdc.tool.execution.importer.tool.Tool;
import csdc.tool.reader.ExcelReader;

/**
 * 
 * 
 */
public class NssfEndinspection200306_201403Importer extends Importer {

	private ExcelReader excelReader;
	
	/**
	 * [项目批准号] -> [NssfId]
	 */
	private Map<String, String> map;	
	private Map<String, String> endMap;
	
	/**
	 * [项目批准号+项目名称] -> [NssfId]
	 */
	private Map<String, String> nameMap;
	
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
		
		if (map == null || endMap == null) {
			initMap();
		}
		
		int i=0;
		int k=0;
		
		while (next(excelReader)) {
			if (A == null || A.isEmpty()) {
				break;
			}
			
			//不存在立项信息的项目
			if (map.get(A) == null) {
				k++;
				Nssf nssf = new Nssf();
				nssf.setNumber(A);
				nssf.setName(B);
				nssf.setProductName(C);
				nssf.setApplicant(D);
				nssf.setUnit(E);
				nssf.setCertificate(F);
				nssf.setStatus(2);
				nssf.setImportDate(new Date());				
				Agency university = universityFinder.getUniversityWithLongestName(E);
				if (university != null) {
					nssf.setUniversity(E);
					nssf.setUniversityId(university.getId());
				}
				if (G.length() > 0) {
					nssf.setProductLevel(G);
				}
				if (J.length() > 0) {
					nssf.setDisciplineType(J);
				}
				if (I.length() > 0) {
					nssf.setEndDate(tool.getDate(I));
				}
				dao.add(nssf);				
			}
			//存在立项信息但无结项信息
			if (map.get(A) != null && endMap.get(A) == null) {
				i++;
				Nssf nssf = dao.query(Nssf.class, map.get(A));
				nssf.setProductName(C);
				nssf.setCertificate(F);
				nssf.setStatus(2);
				if (G.length() > 0) {
					nssf.setProductLevel(G);
				}
				if (I.length() > 0) {
					nssf.setEndDate(tool.getDate(I));
				}
				dao.addOrModify(nssf);
			}
		}
		System.out.println("共添加立项数据：" + i);
		System.out.println("共更新结项数据：" + k);
	}

	public NssfEndinspection200306_201403Importer() {
	}

	public NssfEndinspection200306_201403Importer(String filePath) {
		excelReader = new ExcelReader(filePath);
	}
	
	//主要校验立项数据和结项数据是否存在，由项目批准号和负责人姓名去库中查找，部分数据由于负责人姓名变化未找到，经核实，已将变化的负责人手工添加到c_applicant_new中
	//处理完此类数据之后，导数据时，将以项目批准号作为找项目的依据
	private void validate() throws Exception {
		
		excelReader.readSheet(0);
		int i = 0;
		int k = 0;
		if (map == null) {
			initMap();
		}
		
		while (next(excelReader)) {
			if (A == null || A.isEmpty()) {
				break;
			}
			String id = map.get(A);
			String endId = endMap.get(A+B);
			if (id == null) {
				i++;
				System.out.println("该项目库中不存在立项：" + A + B + "负责人：" + D);
			}
			if (endId == null && id != null) {					
					k++;
					System.out.println("该项目库中不存在结项但存在立项：" + A + B + "证书号：" + F);
					System.out.println(excelReader.getCurrentRowIndex() + "/" + excelReader.getRowNumber());
			}
			
			Agency university = universityFinder.getUniversityWithLongestName(E);
			if (university == null) {
				System.out.println("该机构找不到对应高校：" + E);
			}
		}
		System.out.println("i=" + i);
		System.out.println("k=" + k);
	}

	private void initMap() {

		Date begin = new Date();
		
		map = new HashMap<String, String>(); 
		endMap = new HashMap<String, String>(); 
		nameMap = new HashMap<String, String>();
		//已存在结项信息
		List<Object[]> endList = dao.query("select nssf.number, nssf.id, nssf.applicant, nssf.name from Nssf nssf where nssf.endDate is not null");
		//存在立项信息
		List<Object[]> list = dao.query("select nssf.number,nssf.id, nssf.applicant, nssf.name from Nssf nssf");
		
		for (Object[] o : endList) {
			String number = (String) o[0];
			String id = (String) o[1];	
			String applicant = (String) o[2];
			String name = (String) o[3];
			endMap.put(number, id);
		}
		
		for (Object[] o : list) {
			String number = (String) o[0];
			String id = (String) o[1];
			String applicant = (String) o[2];	
			String name = (String) o[3];
			map.put(number, id);
			nameMap.put(number+name, id);
		}
		
		System.out.println("initMap complete! Used time: " + (new Date().getTime() - begin.getTime()) + "ms");				
	}
}

