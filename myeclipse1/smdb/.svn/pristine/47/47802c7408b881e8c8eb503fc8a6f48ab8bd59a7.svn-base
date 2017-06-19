package csdc.tool.execution.importer;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import csdc.bean.GeneralGranted;
import csdc.bean.GeneralVariation;
import csdc.bean.InstpGranted;
import csdc.bean.InstpVariation;
import csdc.bean.SinossModifyContent;
import csdc.bean.SinossProjectVariation;
import csdc.dao.IHibernateBaseDao;
import csdc.tool.execution.finder.GeneralProjectFinder;
import csdc.tool.execution.finder.InstpProjectFinder;
import csdc.tool.execution.importer.tool.Tool;
import csdc.tool.reader.ExcelReader;

/**
 * 导入遗漏的项目延期变更（有两条及以上的变更，但是第一次由于中检时的误操作没有导入延期变更）
 * @author liangjian
 * 备注：更新正式库里面之前导入的不完整变更数据
 */
public class ProjectVariationImporterAdd extends Importer {
	
	private ExcelReader reader;
	
	@Autowired
	private Tool tool;
	
	@Autowired
	private InstpProjectFinder instpProjectFinder;
	
	@Autowired
	private GeneralProjectFinder generalProjectFinder;
	
	@Autowired
	private IHibernateBaseDao dao;
	
	private int agencyError=0;
	
	@Override
	public void work() throws Throwable{
		importData();
	}

	@SuppressWarnings("unchecked")	
	public void importData() throws Throwable {
		int i=0;
		reader.readSheet(0);
		while(next(reader)){
			SinossProjectVariation sinossProjectVariation = (SinossProjectVariation) dao.queryUnique("select o from SinossProjectVariation o where o.sinossId=?",A);
			String projectType = sinossProjectVariation.getTypeCode();
			String codeString = sinossProjectVariation.getCode();//项目编号
			int checkStatus = sinossProjectVariation.getCheckStatus();//审核状态
			if(checkStatus%2 == 0){
				System.out.println(B);
			}
			if (projectType.contains("base")) {
				InstpGranted granted = instpProjectFinder.findGranted(codeString);//根据项目批准号找到立项实体(基地项目)
				String variationId = sinossProjectVariation.getId();
				Set<InstpVariation> instpVariations = granted.getInstpVariation();
				//找出中间表中对应的延期变更数据
				List<SinossModifyContent> sinossModifyContents = (List<SinossModifyContent>) dao.query("from SinossModifyContent o where o.projectVariation.id = ? ",variationId);
				SinossModifyContent smcTempOne = null;
				for(SinossModifyContent smc : sinossModifyContents){
					if(smc.getModifyFieldMean().contains("延期")){
						smcTempOne = smc;
						break;
					}
				}
								
				for(InstpVariation iv : instpVariations){
    				if(iv.getImportedDate().after(tool.getDate(2014, 10, 1)) && iv.getPostponement() == 0){
    					if(iv.getOtherInfo() != null && iv.getOtherInfo().contains("中检延期")){
    						continue;
    					}
    					System.out.println("读到第" + (++i) + "条数据" + "项目名称为：" + B);
    					iv.setPostponement(1);
    					iv.setOldOnceDate(granted.getPlanEndDate());
    					iv.setNewOnceDate(tool.getDate(smcTempOne.getAfterValue()));
    					if(iv.getDeptInstAuditResultDetail() != null){
    						StringBuffer deptAuditResultDetail = new StringBuffer(iv.getDeptInstAuditResultDetail());
    						deptAuditResultDetail.setCharAt(5, '1');
    						iv.setDeptInstAuditResultDetail(deptAuditResultDetail.toString());
    					}
    					if(iv.getUniversityAuditResultDetail() != null){
    						StringBuffer universityAuditResultDetail = new StringBuffer(iv.getUniversityAuditResultDetail());
    						universityAuditResultDetail.setCharAt(5, '1');
    						iv.setUniversityAuditResultDetail(universityAuditResultDetail.toString());
    					}
    					if(iv.getProvinceAuditResultDetail() != null){
	    					StringBuffer provinceAuditResultDetail = new StringBuffer(iv.getProvinceAuditResultDetail());
	    					provinceAuditResultDetail.setCharAt(5, '1');   					
	    					iv.setProvinceAuditResultDetail(provinceAuditResultDetail.toString());
    					}
    					saveOrUpdate(iv);
    				}
    			}
			}
			
			if (projectType.contains("gener")) {
				GeneralGranted granted = generalProjectFinder.findGranted(codeString);//根据项目批准号找到立项实体(基地项目)
				String variationId = sinossProjectVariation.getId();
				Set<GeneralVariation> generVariations = granted.getGeneralVariation();
				//找出中间表延期变更数据
				List<SinossModifyContent> sinossModifyContents = (List<SinossModifyContent>) dao.query("from SinossModifyContent o where o.projectVariation.id = ? ",variationId);
				SinossModifyContent smcTempOne = null;
				for(SinossModifyContent smc : sinossModifyContents){
					if(smc.getModifyFieldMean().contains("延期")){
						smcTempOne = smc;
						break;
					}
				}
								
				for(GeneralVariation gv : generVariations){					
    				if(gv.getImportedDate().after(tool.getDate(2014, 10, 1)) && gv.getPostponement() == 0){
    					if(gv.getOtherInfo() != null && gv.getOtherInfo().contains("中检延期")){
    						continue;
    					}
    					System.out.println("读到第" + (++i) + "条数据" + "项目名称为：" + B);
    					gv.setPostponement(1);
    					gv.setOldOnceDate(granted.getPlanEndDate());
    					gv.setNewOnceDate(tool.getDate(smcTempOne.getAfterValue()));
    					if(gv.getDeptInstAuditResultDetail() != null){
    						StringBuffer deptAuditResultDetail = new StringBuffer(gv.getDeptInstAuditResultDetail());
    						deptAuditResultDetail.setCharAt(5, '1');
    						gv.setDeptInstAuditResultDetail(deptAuditResultDetail.toString());
    					}
    					if(gv.getUniversityAuditResultDetail() != null){
    						StringBuffer universityAuditResultDetail = new StringBuffer(gv.getUniversityAuditResultDetail());
    						universityAuditResultDetail.setCharAt(5, '1');
    						gv.setUniversityAuditResultDetail(universityAuditResultDetail.toString());
    					}
    					if(gv.getProvinceAuditResultDetail() != null){
	    					StringBuffer provinceAuditResultDetail = new StringBuffer(gv.getProvinceAuditResultDetail());
	    					provinceAuditResultDetail.setCharAt(5, '1');   					
	    					gv.setProvinceAuditResultDetail(provinceAuditResultDetail.toString());
    					}
    					saveOrUpdate(gv);
    				}
    			}
			}
		}
		
		System.out.println("共入库" + i + "条数据");		
		System.out.println(agencyError);
	//	System.out.println(error.toString().replaceAll(",\\s+", "\n"));
	//	int aa = 1/0;  
	}
	
	public ProjectVariationImporterAdd(){
	}
	
	public ProjectVariationImporterAdd(String file) {
		reader = new ExcelReader(file);
	}	
}
		
		
		
		
		
			
			
		
			
		
		
			
			
			
			
			
			
	
	

