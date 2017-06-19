package csdc.tool.execution.importer;

import java.util.Date;

import csdc.bean.GeneralSpecial;
import csdc.tool.reader.ExcelReader;

/**
 * 《教育部人文社会科学专项任务项目在研数据（截至2013年1月21日）_修正导入.xls》
 * @author wangyi
 * 备注：
 * 
 */
public class GeneralSpecial20130121Importer extends Importer {
	
	private ExcelReader excelReader;
		
	
	public GeneralSpecial20130121Importer() {}
	
	public GeneralSpecial20130121Importer(String filePath) {
		excelReader = new ExcelReader(filePath);
	}
	

	@Override
	public void work() throws Exception {
		importData();
	}

	/**
	 * 导入数据
	 * @throws Exception 
	 */
	private void importData() throws Exception {
		
		excelReader.readSheet(0);		
		
		while (next(excelReader)) {
			
			GeneralSpecial generalSpecial = new GeneralSpecial();
			generalSpecial.setUnit(A);
			if (B.length() > 0) {
				generalSpecial.setType(B);
			}
			if (C.length() > 0) {
				generalSpecial.setName(C);
			}
			if (D.length() > 0) {
				generalSpecial.setNumber(D);
			}
			generalSpecial.setApplicant(E);
			generalSpecial.setTopic(F);
			if (G.length() > 0) {
				generalSpecial.setYear(G);
			}
			generalSpecial.setImportDate(new Date());
			generalSpecial.setIsDupCheckGeneral(1);
			dao.add(generalSpecial);

		}
	}

}
