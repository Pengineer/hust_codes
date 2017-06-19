package csdc.tool.execution.importer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import csdc.bean.Instp;
import csdc.bean.InstpReviewer;

/**
 * 临时功能导入器
 * @author xuhan
 *
 */
@SuppressWarnings("unchecked")
public class TempImporter extends Importer {
	
	private List dataList = new ArrayList();

	public TempImporter(File file) {
		super(file);
	}
	
	
	public void work() throws Exception {
		getContentFromExcel(0);
		
		StringBuffer exceptionMsg = new StringBuffer();
		
		for (String[] row : content) {
			getRowData(row);

			Instp project = new Instp();
			project.setId(B);
			
			InstpReviewer ipr = new InstpReviewer();
			ipr.setId(Integer.parseInt(A));
			ipr.setProject(project);
			ipr.setReviewer(tools.getExpertByNumber(Integer.parseInt(C)));
			ipr.setIsManual(Integer.parseInt(D));
			ipr.setPriority(Integer.parseInt(E));
			ipr.setYear(Integer.parseInt(F));
			ipr.setMatchTime(Tools.getDate(G));

			dataList.add(ipr);
		}
		
		if (exceptionMsg.length() > 0) {
			throw new RuntimeException(exceptionMsg.toString());
		}
		
		baseService.addOrModify(dataList);
	}

}
