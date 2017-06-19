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
 * Excel：《》《》
 * @author maowh
 * @status 
 * 备注：在国家社会科学基金T_NSSF中添加字段C_SINGLE_SUBJECT单列学科。
 */
public class NssfArtGranted2013Importer extends Importer {
	
	private ExcelReader excelReader;
	
	@Autowired
	private Tool tool;
	
	@Autowired
	private UniversityFinder universityFinder;
	
	public NssfArtGranted2013Importer() {}
	
	public NssfArtGranted2013Importer(String filePath) {
		excelReader = new ExcelReader(filePath);
	}
	

	@Override
	public void work() throws Exception {
		//validate();
		importData();
	}

	/**
	 * 正式导入数据
	 * @throws Exception 
	 */
	private void importData() throws Exception {
		
		excelReader.readSheet(0);
		
		while (next(excelReader)) {
			Nssf nssf = new Nssf();
			nssf.setNumber(A);
			nssf.setName(B);
			nssf.setType(C);
			nssf.setApplicant(D);
			nssf.setUnit(E);
			Agency university = universityFinder.getUnivByName(E);
			if (university != null) {
				nssf.setUniversityId(university.getId());
			}
			nssf.setSubject("年度课题");
			nssf.setStartDate(tool.getDate(F));
			nssf.setImportDate(new Date());
			nssf.setSingleSubject("艺术学");
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
