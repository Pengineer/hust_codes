package csdc.tool.execution.fix;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import csdc.tool.execution.importer.Importer;
import csdc.tool.execution.importer.tool.Tool;

/**
 * 修复申请表中申请人和成员表中负责人格式不一致的数据
 * @author pengliang
 *
 */
@Component
public class Fix20150421 extends Importer {

	Map<String, String> appToMemMap;
	
	@Autowired
	private Tool tool;
	
	@Override
	protected void work() throws Throwable {
		initApplicant();
		fixDate();
	}
	
	@SuppressWarnings("unchecked")
	public void fixDate(){
		Set<String> Msg = new HashSet<String>();
		
		List<Object[]> applicationList = (List<Object[]>)dao.query("select app.id, app.applicantName from ProjectApplication app where app.year = 2014");
		String applicantName ="";
		String memberName = "";
		int total = applicationList.size();
		int cnt = 0;
		for(Object[] application: applicationList){
			System.out.println((++cnt) + "/" + total);
			applicantName = tool.getName((String)application[1]);
			memberName = appToMemMap.get((String)application[0]);
			if (memberName == null){
				System.out.println("bug");
			}
			if(!applicantName.equals(memberName)) {
				Msg.add((String)application[0] + ":项目表：" + applicantName + "——— 成员表:" + memberName);
			}
		}
		if(Msg.size() > 0){
			System.out.println(Msg.toString().replaceAll(",\\s+", "\n"));
		}
	}
	
	@SuppressWarnings("unchecked")
	public void initApplicant(){
		Date begin = new Date();
		appToMemMap = new HashMap<String, String>();
		List<Object[]> memberList = dao.query("select mem.applicationId, mem.memberName from ProjectMember mem, ProjectApplication app where mem.applicationId=app.id and mem.isDirector=1 and mem.groupNumber=1 and app.year=2014");
		for (Object[] applicant : memberList) {
			appToMemMap.put((String)applicant[0], (String)applicant[1]);
		}
		System.out.println("init applicant complete! use time " + (new Date().getTime() - begin.getTime()) + "ms");
	}

}
