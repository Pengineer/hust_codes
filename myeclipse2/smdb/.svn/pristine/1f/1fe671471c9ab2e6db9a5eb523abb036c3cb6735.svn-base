package csdc.tool.execution.importer;

import java.util.Date;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.bean.Agency;
import csdc.bean.Nssf;
import csdc.tool.execution.finder.UniversityFinder;
import csdc.tool.execution.importer.tool.Tool;
import csdc.tool.reader.ExcelReader;

/**
 * 《全国教育科学“十二五”规划2013年度课题立项名单.xls》
 * @author maowh
 * @status 
 */
public class NssfNespGranted2013Importer extends Importer {
	
	private ExcelReader excelReader;
		
	@Autowired
	private Tool tool;
	
	@Autowired
	private UniversityFinder universityFinder;
	
	public NssfNespGranted2013Importer() {}
	
	public NssfNespGranted2013Importer(String filePath) {
		excelReader = new ExcelReader(filePath);
	}
	

	@Override
	public void work() throws Exception {
		//validate();
		importData();
	}

	/**
	 * 导入数据
	 * @throws Exception 
	 */
	private void importData() throws Exception {
		
		excelReader.readSheet(0);		
		
		while (next(excelReader)) {
			
			Nssf nssf = new Nssf();
			nssf.setName(C);
			nssf.setApplicant(D);
			nssf.setNumber(A);
			nssf.setType(B);
//			if (E.length() > 0) {
//				nssf.setDisciplineType(E);
//			}
//			if (F.length() > 0) {
//				nssf.setTopic(F);
//			}
			nssf.setUnit(E);
			Agency university = universityFinder.getUnivByName(E);
			if (university != null) {
				nssf.setUniversityId(university.getId());
			}
			nssf.setStartDate(tool.getDate("2014-01-17"));
			nssf.setSubject("年度课题");
			nssf.setSingleSubject("教育学");
			nssf.setImportDate(new Date());
			dao.add(nssf);

		}
	}
	
	private void validate() throws Exception {
		excelReader.readSheet(0);
		
		HashSet 问题数据 = new HashSet();
		while (next(excelReader)) if (!A.isEmpty()) {
			Agency university = universityFinder.getUnivByName(E);		
			
			if (university == null) {
				问题数据.add("不存在的高校: " + E);
			}
		}
		
		if (问题数据.size() > 0) {
			System.out.println(问题数据.toString().replaceAll(",\\s+", "\n"));
			throw new RuntimeException();
		}
	}

}

