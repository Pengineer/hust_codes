package csdc.tool.execution.importer;

import java.util.Date;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.bean.Agency;
import csdc.bean.InstpGranted;
import csdc.bean.InstpEndinspection;
import csdc.tool.execution.finder.InstpProjectFinder;
import csdc.tool.execution.importer.tool.Tool;
import csdc.tool.reader.ExcelReader;


/**
 * 导入《2011-2012年度基地项目已结项名单（截至2014年5月27日）.xls》
 * @author maowh
 *
 */
public class InstpEndInspection2011_2013Importer extends Importer {
	
	private ExcelReader excelReader;
	
	@Autowired
	private Tool tool;

	@Autowired
	private InstpProjectFinder instpProjectFinder;
	
	public InstpEndInspection2011_2013Importer() {}
	
	public InstpEndInspection2011_2013Importer(ExcelReader excelReader) {
		this.excelReader = excelReader;
	}
	
	@Override
	public void work() throws Exception {
		importData();
//		validate();
	}
	
	private void resetReader() {
		excelReader.readSheet(0);		
	}
	
	private void importData() throws Exception {
		resetReader();
		int i = 0;
		
		while (next(excelReader)) {
			InstpGranted ig = instpProjectFinder.findGranted(G, C);
			if (ig != null) {
				i++;
				ig.setStatus(2);
				dao.addOrModify(ig);
				InstpEndinspection ie = new InstpEndinspection();
				ie.setGranted(ig);
				ie.setIsImported(1);
				ie.setImportedDate(new Date());
				dao.add(ie);
			}
		}
		
		System.out.println("共导入结项数据：" + i + "条");		
	}
	
	private void validate() throws Exception {

		resetReader();
		
		HashSet exMsg = new HashSet();
		while (next(excelReader)) {
			InstpGranted ig = instpProjectFinder.findGranted(G, C);
			if (ig == null) {
				exMsg.add("不存在的该项目的立项信息: " + C);
			}
		}

		if (exMsg.size() > 0) {
			System.out.println(exMsg.toString().replaceAll(",\\s+", "\n"));
			throw new RuntimeException();
		}
			
	}




}
