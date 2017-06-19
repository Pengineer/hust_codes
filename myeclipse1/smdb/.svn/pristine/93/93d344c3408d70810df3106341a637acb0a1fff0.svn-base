package csdc.tool.execution.fix;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import csdc.bean.ProjectGranted;
import csdc.bean.ProjectVariation;
import csdc.tool.execution.importer.Importer;
import csdc.tool.execution.importer.tool.ProductTypeNormalizer;

/**
 * 修正立项表和变更表的成果形式（规范化）
 * @author pengliang
 *
 */
@Component
public class Fix20150720 extends Importer {

	@Autowired
	protected ProductTypeNormalizer productTypeNormalizer;
	
	@Override
	protected void work() throws Throwable {
		regulateVariationProduct();
	}
	
	public void regulateGrantedProduct() throws Throwable {
		@SuppressWarnings("unchecked")
		List<ProjectGranted> projectGranteds = dao.query("select pg from ProjectGranted pg where pg.productType is not null" + 
				" or pg.productTypeOther is not null");
		int current=0;
		int total=projectGranteds.size();
		for (ProjectGranted granted : projectGranteds) {
			System.out.println((++current) + "/" + total);
			String[] productType = productTypeNormalizer.getNormalizedProductType(granted.getProductType() + "; " + granted.getProductTypeOther());
			granted.setProductType(productType[0]);
			granted.setProductTypeOther(productType[1]);
			dao.modify(granted);
		}
	}
	
	public void regulateVariationProduct() throws Throwable {
		@SuppressWarnings("unchecked")
		List<ProjectVariation> projectVariations = dao.query("select pv from ProjectVariation pv where pv.newProductType is not null" + 
				" or pv.newProductTypeOther is not null");
		int current=0;
		int total=projectVariations.size();
		for (ProjectVariation variation : projectVariations) {
			System.out.println((++current) + "/" + total);
			String[] productType = productTypeNormalizer.getNormalizedProductType(variation.getNewProductType() + "; " + variation.getNewProductTypeOther());
			variation.setNewProductType(productType[0]);
			variation.setNewProductTypeOther(productType[1]);
			dao.modify(variation);
		}
	}

}
