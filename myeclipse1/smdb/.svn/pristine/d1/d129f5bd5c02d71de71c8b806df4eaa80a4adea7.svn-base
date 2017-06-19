package csdc.tool.execution.fix;

//import java.util.ArrayList;
//import java.util.HashMap;
import java.util.List;
//import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

//import csdc.bean.PostVariation;
import csdc.bean.GeneralVariation;
//import csdc.bean.InstpVariation;
//import csdc.bean.PostApplication;
import csdc.bean.GeneralApplication;
//import csdc.bean.InstpApplication;
//import csdc.bean.PostGranted;
import csdc.bean.GeneralGranted;
import csdc.dao.IBaseDao;
//import csdc.bean.InstpGranted;
import csdc.service.IBaseService;
import csdc.tool.execution.Execution;

public class FixOriginalChangeInformation extends Execution {
	
	@Autowired
	public IBaseService baseService;
	
	@Autowired
	public IBaseDao hibernateBaseDao;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void work() throws Throwable {
		//1.找到所有存在变更的项目ids
		List<String> grantIds = baseService.query("select distinct gv.granted.id from GeneralVariation gv where gv.granted.id is not null");
		//2.遍历每一个项目的所有变更项目成员（含负责人）的变更，找出最早一次变更
		for(String grantId : grantIds) {
			List<GeneralVariation> generalVariations = baseService.query("select vari from GeneralVariation vari left outer join vari.granted gra where vari.changeName = 1 " +
					"and gra.id = ? and not exists (select 1 from GeneralVariation gravar where gravar.granted.id = gra.id and gravar.applicantSubmitDate < vari.applicantSubmitDate)", grantId);
			
			if(null != generalVariations && !generalVariations.isEmpty()) {
				//3.已找到当前变更项目负责人变更
				GeneralVariation generalVariation = generalVariations.get(0);
				
				//4.找到对应申请表
				GeneralGranted generalGranted = (GeneralGranted)baseService.query(GeneralGranted.class, grantId);
				GeneralApplication generalApplication = generalGranted.getApplication();
				
				//5.修改申请表
				generalApplication.setName(generalVariation.getOldName());
			}
		}
	}
				
				
				
				
				
				
				
				
	

			
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
		}