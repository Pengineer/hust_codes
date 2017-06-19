package csdc.tool.execution.fix;

import java.text.SimpleDateFormat;
import java.util.List;


import csdc.bean.InstpVariation;
import csdc.tool.execution.importer.Importer;
import csdc.tool.reader.ExcelReader;

public class FixInstpMidInspection2013Importer extends Importer {
	
	private ExcelReader excelReader;	
	
	public FixInstpMidInspection2013Importer() {}
	
	public FixInstpMidInspection2013Importer(String filePath) {
		excelReader = new ExcelReader(filePath);
	}
	
	@Override
	public void work() throws Exception {
		fix();
	}
	
	private void fix() throws Exception {
		
		excelReader.readSheet(0);
		
		while (next(excelReader)) {
			List<InstpVariation> ivs = dao.query("select iv from InstpVariation iv where iv.grantedId = ? ",A);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			for (InstpVariation gv: ivs ) {
				if (gv.getFinalAuditDate() != null) {
					String finalAuditDate = sdf.format(gv.getFinalAuditDate());
					if ("2013-09-30".equals(finalAuditDate)) {
						dao.delete(gv);
						System.out.println("多余的中检延期变更已被删除!");
					}
				}
			}
		}
		
	}

}
