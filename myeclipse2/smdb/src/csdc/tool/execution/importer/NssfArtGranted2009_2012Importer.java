package csdc.tool.execution.importer;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.bean.Nssf;
import csdc.tool.execution.importer.tool.Tool;
import csdc.tool.reader.ExcelReader;

/**
 * Excel：《2009-2012年艺术学立项名单.xls》
 * @author wangyi
 * @status 
 * 备注：在国家社会科学基金T_NSSF中添加字段C_SINGLE_SUBJECT单列学科。
 */
public class NssfArtGranted2009_2012Importer extends Importer {
	
	private ExcelReader excelReader;
	
	@Autowired
	private Tool tool;
	
	public NssfArtGranted2009_2012Importer() {}
	
	public NssfArtGranted2009_2012Importer(String filePath) {
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
			Nssf nssf = new Nssf();
			nssf.setNumber(A);
			nssf.setName(B);
			nssf.setType(C);
			nssf.setApplicant(D);
			nssf.setUnit(E);
			nssf.setStartDate(tool.getDate(F));
			nssf.setImportDate(new Date());
			nssf.setIsDupCheckGeneral(1);
			nssf.setSingleSubject("艺术学");
			dao.add(nssf);

		}
	}

}
