package csdc.tool.execution.fix;

//import java.util.ArrayList;
//import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

//import csdc.bean.GeneralEndinspection;
import csdc.bean.GeneralVariation;

import csdc.service.IBaseService;
import csdc.service.IGeneralService;
import csdc.tool.execution.Execution;

public class FixFinalAuditResultDetail extends Execution {
	
	@Autowired
	public IBaseService baseService;
	@Autowired
	public IGeneralService generalService;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void work() throws Throwable {
		String hql4Variation = "select gv.id from GeneralVariation gv";
		List listV = baseService.query(hql4Variation);
		for(int i = 0; i <listV.size(); i++){
			String varId = listV.get(i).toString();
//			String varId = "4028d88a3098cbca013098cc6c330583";
			String result = "";
			GeneralVariation variation = (GeneralVariation) baseService.query(GeneralVariation.class, varId);
			if (variation.getChangeMember() == 1 && variation.getFinalAuditResult()== 2 && variation.getFinalAuditStatus()== 3 ){
				result += 1;
			} else{
				result += 0;
			}
			if(variation.getChangeAgency() == 1 && variation.getFinalAuditResult()== 2 && variation.getFinalAuditStatus()== 3 ){
				result += 1;
			} else{
				result += 0;
			}
			if(variation.getChangeProductType() == 1 && variation.getFinalAuditResult()== 2 && variation.getFinalAuditStatus()== 3 ){
				result += 1;
			} else{
				result += 0;
			}
			if(variation.getChangeName() == 1 && variation.getFinalAuditResult()== 2 && variation.getFinalAuditStatus()== 3 ){
				result += 1;
			} else{
				result += 0;
			}
			if(variation.getChangeContent() == 1 && variation.getFinalAuditResult()== 2 && variation.getFinalAuditStatus()== 3 ){
				result += 1;
			} else{
				result += 0;
			}
			if(variation.getPostponement() == 1 && variation.getFinalAuditResult()== 2 && variation.getFinalAuditStatus()== 3 ){
				result += 1;
			} else{
				result += 0;
			}
			if(variation.getStop() == 1 && variation.getFinalAuditResult()== 2 && variation.getFinalAuditStatus()== 3 ){
				result += 1;
			} else{
				result += 0;
			}
			if(variation.getWithdraw() == 1 && variation.getFinalAuditResult()== 2 && variation.getFinalAuditStatus()== 3 ){
				result += 1;
			} else{
				result += 0;
			}
			if(variation.getOther() == 1 && variation.getFinalAuditResult()== 2 && variation.getFinalAuditStatus()== 3 ){
				result += 1;
			} else{
				result += 0;
			}
			variation.setFinalAuditResultDetail(result);
		}
	}
}