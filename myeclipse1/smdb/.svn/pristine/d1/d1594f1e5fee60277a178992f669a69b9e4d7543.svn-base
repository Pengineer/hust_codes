package csdc.tool.execution.importer;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.bean.Nssf;
import csdc.tool.execution.importer.tool.Tool;
import csdc.tool.reader.ExcelReader;

/**
 * Excel：《20013-2014年艺术学结项名单.xls》
 * @author pengliang
 * @status 
 * 备注：数据在立项名单中存在的进行更新，不存在的在数据库中新建。
 */
public class NssfArtEndinspection2013_2014Importer extends Importer {
	
	private ExcelReader excelReader;
	
	@Autowired
	private Tool tool;
	
	public NssfArtEndinspection2013_2014Importer() {}
	
	public NssfArtEndinspection2013_2014Importer(String filePath) {
		excelReader = new ExcelReader(filePath);
	}
	

	@Override
	public void work() throws Exception {
		importData();
	}

	/**
	 * 正式导入数据
	 * @throws Exception 
	 */
	private void importData() throws Exception {
		
		excelReader.readSheet(0);
		Map<String,String> para = new HashMap<String, String>();
		
		while (next(excelReader)) {
			if (A == null || A.length() == 0) {
				break;
		    }
			System.out.println(excelReader.getCurrentRowIndex() + "/"+excelReader.getRowNumber() + ":" + B);
			para.put("number", B.trim());
			Nssf nssf = (Nssf) dao.queryUnique("select nssf from Nssf nssf where nssf.number = :number", para);
			if (nssf == null) {
				Nssf newNssf = new Nssf();
				newNssf.setDisciplineType(A);
				newNssf.setNumber(B);
				newNssf.setName(C);
				newNssf.setProductName(D);
				newNssf.setUnit(E);
				newNssf.setApplicant(F.replaceAll("\\s+", ""));
				newNssf.setCertificate(G);
				newNssf.setYear(H.trim());
				newNssf.setImportDate(new Date());
				newNssf.setIsDupCheckGeneral(0);
				newNssf.setSingleSubject("艺术学");
				newNssf.setStatus(2);
				dao.add(newNssf);
			} else {
				nssf.setDisciplineType(A);
				nssf.setProductName(D);
				nssf.setCertificate(G);
				nssf.setEndDate(tool.getDate(H));
				nssf.setImportDate(new Date());
				nssf.setIsDupCheckGeneral(0);
				nssf.setStatus(2);
				saveOrUpdate(nssf);
			}

		}
		System.out.println("over");
		
	}
}
