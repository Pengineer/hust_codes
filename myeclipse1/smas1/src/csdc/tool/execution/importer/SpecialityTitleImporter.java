package csdc.tool.execution.importer;

import java.util.HashMap;
import java.util.Map;

import csdc.bean.SystemOption;
import csdc.tool.reader.PoiExcelReader;

/**
 * 导入专业职称标准《GBT8561-2001专业技术职务代码.xls》
 * @author xuhan
 *
 */
public class SpecialityTitleImporter extends Importer {
	
	private PoiExcelReader excelReader;
	
	/**
	 * code -> SystemOption
	 */
	private Map<String, SystemOption> map = new HashMap<String, SystemOption>();

	
	public void work() throws Exception {
		excelReader.readSheet(0);
		
		SystemOption root = new SystemOption();
		root.setName("专业技术职务");
		root.setDescription("专业技术职务");
		root.setCode("000");
		root.setIsAvailable(1);
		root.setStandard("GBT8561-2001");
		dao.add(root);
		
		while (next(excelReader)) {
			if (A.length() != 3) {
				throw new RuntimeException();
			}
			
			SystemOption so = new SystemOption();
			so.setName(B);
			so.setDescription(B);
			so.setCode(A);
			so.setIsAvailable(1);
			so.setStandard("GBT8561-2001");
			
			if (A.endsWith("0")) {
				so.setParent(root);
			} else {
				String parentCode = A.substring(0, 2) + "0";
				SystemOption parent = map.get(parentCode);
				parent.getId();
				so.setParent(parent);
			}
			map.put(A, so);
			dao.add(so);
		}
	}

	
	public SpecialityTitleImporter() {
	}

	public SpecialityTitleImporter(String filePath) {
		excelReader = new PoiExcelReader(filePath);
	}
}
