package csdc.tool.execution.importer;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.bean.GeneralApplication;
import csdc.tool.execution.finder.GeneralProjectFinder;
import csdc.tool.reader.ExcelReader;

/**
 * 导入《2015年教育部人文社会科学研究一般项目出版课题申请名单.xls》
 * @author pengliang
 * 说明：为了区分一般项目里面的出版项目，根据评价中心提供的Excel在出版项目的c_note字段置上“出版项目不参与评审”.
 */
public class AddNoteToPublishProject2015Importer extends Importer {

	ExcelReader excelReader;
	
	@Autowired
	GeneralProjectFinder generalProjectFinder;
	
	@Override
	protected void work() throws Throwable {
		excelReader.readSheet(0);
		while(next(excelReader)){
			System.out.println(A + ":" + C + "——" + E);
			GeneralApplication application = generalProjectFinder.findApplication(C.trim(), E.trim(), 2015);
			if (application == null){
				throw new RuntimeException();
			} else {
				application.setNote("出版项目不参与评审");
				dao.modify(application);
			}
		}
		System.out.println("over");
	}

	public AddNoteToPublishProject2015Importer() {
	}
	
	public AddNoteToPublishProject2015Importer(String fileName) {
		excelReader = new ExcelReader(fileName);
	}

}
