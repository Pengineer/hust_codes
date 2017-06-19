package csdc.tool.execution.importer;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.bean.Nssf;
import csdc.tool.execution.importer.Importer;
import csdc.tool.execution.importer.tool.Tool;
import csdc.tool.reader.ExcelReader;

/**
 * 按《国家社科基金2012年1月-2013年1月项目新结项数据（汇总）.xls》录入结项数据并将查重标记C_IS_DUP_CHECK_GENERAL由1改为0。 
 * 其中属于国家社科基金重大项目查重标记C_IS_DUP_CHECK_INSTP由1改为0。
 * @author wangyi
 * 
 */
public class NssfNewEndinspectionImporter extends Importer {

	private ExcelReader excelReader;
	
	@Autowired
	private Tool tool;

	public void work() throws Exception {
		excelReader.readSheet(0);

		while (next(excelReader)) {
			if (A.length() == 0) {
				break;
		    }
			
			Nssf nssf = (Nssf) dao.queryUnique("select nssf from Nssf nssf where nssf.number = ?", A);
			if (C.length() > 0) {
				nssf.setProductName(C);
			}
			nssf.setCertificate(F);
			nssf.setIsDupCheckGeneral(0);
			if (G.equals("重大项目")) {
				nssf.setIsDupCheckInstp(0);
			}
			nssf.setEndDate(tool.getDate(H));
			if (I.length() > 0) {
				nssf.setProductLevel(I);
			}
			nssf.setImportDate(new Date());
			saveOrUpdate(nssf);

		}

	}

	public NssfNewEndinspectionImporter() {
	}

	public NssfNewEndinspectionImporter(String filePath) {
		excelReader = new ExcelReader(filePath);
	}
}
