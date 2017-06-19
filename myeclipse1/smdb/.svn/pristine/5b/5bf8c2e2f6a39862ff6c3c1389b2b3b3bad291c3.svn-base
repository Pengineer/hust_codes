package csdc.tool.execution.fix;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import csdc.bean.ProjectMember;
import csdc.tool.execution.importer.Importer;

/**
 * 
 * @author pengliang
 *
 */
@Component
public class Fix20150415_2 extends Importer {
	
	
	@SuppressWarnings("unchecked")
	public void work() throws Exception {
		int num=0;
		List<ProjectMember> projectMembers = dao.query("select pm from ProjectMember pm where pm.isDirector=1");
		for(ProjectMember pm : projectMembers) {
			System.out.println(++num);
			Date birth = pm.getMember().getBirthday();
			if(birth != null && pm.getBirthday() ==null){
				pm.setBirthday(birth);
			}
			dao.modify(pm);
		}
	}
}