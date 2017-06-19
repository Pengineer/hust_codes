package csdc.tool.execution.fix;

import java.util.ArrayList;
import java.util.List;

import csdc.bean.GeneralApplication;
import csdc.bean.GeneralVariation;
import csdc.service.IBaseService;
import csdc.tool.ApplicationContainer;
import csdc.tool.SpringBean;
import csdc.tool.execution.importer.tool.Tool;

/**
 * 成果形式规整器
 * @author xuhan
 *
 */
@SuppressWarnings("unchecked")
public class FixProductType {
	
	public static IBaseService baseService = (IBaseService) SpringBean.getBean("baseService", ApplicationContainer.sc);
	
	private Tool tools = new Tool();
	
	public void work() throws Exception {
		
		List dataList = new ArrayList();
		
		List<GeneralApplication> gaList = baseService.query("select ga from GeneralApplication ga");
		for (GeneralApplication ga : gaList) {
			if (ga.getProductType() != null) {
				String pt[] = tools.getNormalizedProductType(ga.getProductType() + " " + ga.getProductTypeOther());
				ga.setProductType(pt[0]);
				ga.setProductTypeOther(pt[1]);
				dataList.add(ga);
			}
		}
		
		List<GeneralVariation> gvList = baseService.query("select gv from GeneralVariation gv");
		for (GeneralVariation gv : gvList) {
			if (gv.getOldProductType() != null || gv.getNewProductType() != null) {
				String pt[] = tools.getNormalizedProductType(gv.getOldProductType() + " " + gv.getOldProductTypeOther());
				gv.setOldProductType(pt[0]);
				gv.setOldProductTypeOther(pt[1]);

				pt = tools.getNormalizedProductType(gv.getNewProductType() + " " + gv.getNewProductTypeOther());
				gv.setNewProductType(pt[0]);
				gv.setNewProductTypeOther(pt[1]);
				
				dataList.add(gv);
			}
		}
		baseService.addOrModify(dataList);
	}



}
