package csdc.tool.execution;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import csdc.bean.SinossProjectApplication;
import csdc.dao.IHibernateBaseDao;
import csdc.tool.ApplicationContainer;

/**
 * 处理2015年一般项目与专项项目申请书的更名
 * @author suwb
 *
 */
public class FileRename extends Execution{

	@Autowired
	private IHibernateBaseDao dao;
	
	@Override
	@Transactional
	protected void work() throws Throwable {
		applyRename();
	}
	//申请申请书处理
	private void applyRename(){
		List<Object[]> sinossP = dao.query("select spa.id, spa.applyDocName, spa.file, spa.typeCode from SinossProjectApplication spa where spa.year='2015' and spa.applyDocName is not null");
		for(Object[] one : sinossP){
			String realName = one[1].toString();
			String filepath = "upload/sinoss/" + one[3].toString() + "/app/2015/" + realName;
			String thisFile = one[2].toString();
			File file = new File(ApplicationContainer.sc.getRealPath(thisFile.replace('\\', '/')));
			file.renameTo(new File(ApplicationContainer.sc.getRealPath(filepath.replace('\\', '/'))));
			SinossProjectApplication spa = dao.query(SinossProjectApplication.class, one[0].toString());
			spa.setFile(filepath);
			dao.modify(spa);
		}
	}
}
