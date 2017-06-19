package csdc.tool.execution.fix;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import csdc.bean.ProjectApplication;
import csdc.bean.ProjectMember;
import csdc.bean.SinossProjectApplication;
import csdc.tool.execution.importer.Importer;
import csdc.tool.execution.importer.tool.Tool;

/**
 * 修复负责人的birthday信息：projectMember -> ProjectApplication -> SinossProjectApplication
 * @author pengliang
 *
 */
@Component
public class Fix20150415_3 extends Importer {
	
	@Autowired
	protected Tool tool;
	
	@SuppressWarnings("unchecked")
	public void work() throws Exception {
		int num=0;
		List<ProjectMember> projectMembers = dao.query("select pm from ProjectMember pm, ProjectApplication pa where pa.id = pm.applicationId and pm.isDirector=1 and pa.year=2015 and (pa.type='general' or pa.type='special')");
		
		List<SinossProjectApplication> spas = dao.query("select spa from SinossProjectApplication spa where spa.year='2015'");
		Map<String, SinossProjectApplication> smap = new HashMap<String, SinossProjectApplication>();
		for(SinossProjectApplication spa : spas){
			smap.put(spa.getProjectName().trim() + tool.getName(spa.getApplyer()), spa);
		}
		
		List<ProjectApplication> pas = dao.query("select pa from ProjectApplication pa where pa.year=2015 and (pa.type='general' or pa.type='special')");
		Map<String, ProjectApplication> map = new HashMap<String, ProjectApplication>();
		for(ProjectApplication pa : pas ){
			map.put(pa.getId(), pa);
		}
		
		Set temp = new HashSet<String>();
		Date birth = null;
		for(ProjectMember pm : projectMembers) {
			System.out.println(++num);
			ProjectApplication paTemp = map.get(pm.getApplicationId());
			if(paTemp == null){
				System.out.println("1111");
			}
			SinossProjectApplication spaTemp = smap.get(paTemp.getName() + paTemp.getApplicantName());
			if(spaTemp == null){
				temp.add(paTemp.getName());
				continue;
			}
			birth = spaTemp.getBirthday();
			if(birth == null){
				System.out.println("33333");
			}
			if(birth != null){
				pm.setBirthday(birth);
				dao.modify(pm);
			}
		}
		System.out.println(temp.toString().replaceAll(",\\s+", "\n"));
	}
}