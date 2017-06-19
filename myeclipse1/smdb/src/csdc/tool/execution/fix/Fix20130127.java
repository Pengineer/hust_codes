package csdc.tool.execution.fix;

import csdc.bean.Nssf;
import csdc.tool.execution.importer.Importer;
import csdc.tool.reader.ExcelReader;

/**
 * 按《国家社科基金2012年10-12月后期项目结项名单.xls》将数据库中的对应数据查重标记C_IS_DUP_CHECK_GENERAL由1改为0。 
 * @author wangyi
 * 
 */
public class Fix20130127 extends Importer {

	private ExcelReader excelReader;

	public void work() throws Exception {
		excelReader.readSheet(0);

		int i = 0;
		int j = 0;
		int k = 0;
		while (next(excelReader)) {
			if (A.length() == 0) {
				break;
		    }
			
			Nssf nssf = (Nssf) dao.queryUnique("select nssf from Nssf nssf where nssf.number = ?", C);
			if (nssf == null) {
			    System.out.println("找不到的项目:" + C);
			    i++;
			} else {
				if (E.equals(nssf.getApplicant())) {
					nssf.setIsDupCheckGeneral(0);
					saveOrUpdate(nssf);
					j++;
				} else {
					System.out.println("项目负责人错误:" + C);
					k++;
				}
				
			}

		}
		System.out.println(i);
		System.out.println(j);
		System.out.println(k);
	}

	public Fix20130127() {
	}

	public Fix20130127(String filePath) {
		excelReader = new ExcelReader(filePath);
	}
}
