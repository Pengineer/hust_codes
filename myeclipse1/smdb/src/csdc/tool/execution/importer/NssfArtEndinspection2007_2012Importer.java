package csdc.tool.execution.importer;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.bean.Nssf;
import csdc.tool.execution.importer.tool.Tool;
import csdc.tool.reader.ExcelReader;

/**
 * Excel：《2007-2012年艺术学结项名单.xls》
 * @author wangyi
 * @status 
 * 备注：数据在立项名单中存在的进行更新，不存在的在数据库中新建。
 */
public class NssfArtEndinspection2007_2012Importer extends Importer {
	
	private ExcelReader excelReader;
	
	@Autowired
	private Tool tool;
	
	public NssfArtEndinspection2007_2012Importer() {}
	
	public NssfArtEndinspection2007_2012Importer(String filePath) {
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
		
		while (next(excelReader)) {
			if (A.length() == 0) {
				break;
		    }
			
			Nssf nssf = (Nssf) dao.queryUnique("select nssf from Nssf nssf where nssf.number = ?", B);
			if (nssf == null) {
				Nssf newNssf = new Nssf();
				newNssf.setDisciplineType(A);
				newNssf.setNumber(B);
				newNssf.setName(C);
				newNssf.setProductName(D);
				newNssf.setUnit(E);
				newNssf.setApplicant(F);
				newNssf.setCertificate(G);
				newNssf.setEndDate(tool.getDate(H));
				newNssf.setImportDate(new Date());
				newNssf.setIsDupCheckGeneral(0);
				newNssf.setSingleSubject("艺术学");
				dao.add(newNssf);
			} else {
				nssf.setDisciplineType(A);
				nssf.setProductName(D);
				nssf.setCertificate(G);
				nssf.setEndDate(tool.getDate(H));
				nssf.setImportDate(new Date());
				nssf.setIsDupCheckGeneral(0);
				saveOrUpdate(nssf);
			}

		}
		
	}
}
