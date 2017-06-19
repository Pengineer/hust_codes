package csdc.tool.execution.importer;

import java.util.HashMap;
import java.util.Map;

import csdc.tool.reader.JxlExcelReader;

/**
 * 职称对应的excel读取
 * @author suwb
 *
 */
public class TitleImporter extends Importer{
	
	private JxlExcelReader reader;

	private Map<String, String> titleMap;
	
	public TitleImporter(String file){
		this.reader =  new JxlExcelReader(file);
	}
	/**
	 * reader重置
	 * @throws Exception
	 */
	private void resetReader() throws Exception {
		reader.readSheet(0);//读取excel的sheet表,下标从0开始
	}
	
	@Override
	public  void work() throws Exception {
		importTitle();
	}
	
	private void importTitle() throws Exception {
		resetReader();
		
		//职称标准
		titleMap = new HashMap<String, String>();
		
		while (next(reader)) {
			titleMap.put(F, E);
		}
	}
	
	public Map<String, String> run() throws Throwable{
		work();
		return titleMap;
	}
}
