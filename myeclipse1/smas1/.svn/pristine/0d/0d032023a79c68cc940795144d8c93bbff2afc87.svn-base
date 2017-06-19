package csdc.tool.execution.importer;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.bean.ProjectApplication;
import csdc.tool.execution.finder.GeneralFinder;
import csdc.tool.reader.JxlExcelReader;

/**
 * 一般项目退评
 * @author fengcl
 *
 */
public class GeneralNotReview2013Importer extends Importer{

	private JxlExcelReader reader;
	
	@Autowired
	private GeneralFinder generalFinder;
	
	@Override
	protected void work() throws Throwable {
		importData();
	}
	
	private void importData() throws Exception {
		resetReader();
		
		next(reader);
		while (next(reader)) {
			System.out.println(reader.getCurrentRowIndex() + "/" + reader.getRowNumber());
			
			//获取项目
			ProjectApplication project = generalFinder.findGeneral(D, E, 2013, false);
			if (null == project) {
				System.out.println(D + "["+ E + "]");
			}
			
			project.setIsReviewable(0);
			project.setReason(B);
		}
	}

	/**
	 * reader重置
	 * @throws Exception
	 */
	private void resetReader() throws Exception {
		reader.readSheet(0);
	}
	
	public GeneralNotReview2013Importer() {
	}
	
	public GeneralNotReview2013Importer(String file) {
		reader = new JxlExcelReader(file);
	}
}
