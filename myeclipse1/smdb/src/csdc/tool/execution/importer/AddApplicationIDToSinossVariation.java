package csdc.tool.execution.importer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import csdc.bean.GeneralGranted;
import csdc.bean.InstpGranted;
import csdc.bean.SinossProjectVariation;
import csdc.dao.IHibernateBaseDao;
import csdc.tool.execution.finder.GeneralProjectFinder;
import csdc.tool.execution.finder.InstpProjectFinder;
/**
 * 向SinossVariation表中补充导入smdb项目申请ID
 * @author pengliang
 */
@Component
public class AddApplicationIDToSinossVariation extends Importer{
	
	@Autowired
	private IHibernateBaseDao dao;
	
	@Autowired
	private GeneralProjectFinder generalProjectFinder;
	
	@Autowired
	private InstpProjectFinder instpProjectFinder;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void work() throws Throwable {
		List<SinossProjectVariation> spvs = dao.query("select sv from SinossProjectVariation sv where sv.projectApplication is null");
		for(SinossProjectVariation spv : spvs){
			if("gener".equals(spv.getTypeCode())){
				GeneralGranted gg = generalProjectFinder.findGranted(spv.getCode());
				spv.setProjectApplication(gg.getApplication());
			}
			if("base".equals(spv.getTypeCode())){
				InstpGranted gg = instpProjectFinder.findGranted(spv.getCode());
				spv.setProjectApplication(gg.getApplication());
			}
		}
	}

}
