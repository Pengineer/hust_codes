package csdc.tool.execution.fix;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import csdc.bean.GeneralApplication;
import csdc.bean.SinossChecklogs;
import csdc.bean.SinossProjectApplication;
import csdc.bean.SpecialApplication;
import csdc.tool.execution.finder.GeneralProjectFinder;
import csdc.tool.execution.finder.SpecialProjectFinder;
import csdc.tool.execution.importer.Importer;

/**
 * 建立2015年一般项目和专项任务项目中间表到正式库的关联关系
 * @author pengliang
 *
 */

@Component
public class Fix20150420 extends Importer {
	
	@Autowired
	GeneralProjectFinder generalProjectFinder;
	
	@Autowired
	SpecialProjectFinder specialProjectFinder;
	
	//记录当前项目的审核数据
	protected List<SinossChecklogs> sinossTempChecklogs;
	
	//初始化审核数据
	protected Map<String, List<SinossChecklogs>> projectIdToSChecklogMap = null;

	@Override
	protected void work() throws Throwable {
		initSinossCheckData("2015-03-24");
		fixGeneralApplication();
		fixSpecialApplication();
	}
	
	@SuppressWarnings("unchecked")
	public void fixGeneralApplication() {
		List<SinossProjectApplication> sinossProjectApplications = dao.query("select spa from SinossProjectApplication spa where spa.year='2015' and spa.typeCode='gener'");
		int total = sinossProjectApplications.size();
		int current = 0;
		int checkstatus;
		for(SinossProjectApplication spa : sinossProjectApplications) {
			System.out.println("general:" + (++current) + "/" + total);
			int status = 0;
			switch (Integer.parseInt(spa.getCheckStatus())) {
				case 2: status = 4;
				        break;
				case 3: status = 3;
				        break;
				case 4: status = 5;
						break;
				case 5: status = 4;
						break;
				default:status = 3;
			}
			checkstatus = getCheckInfo(spa.getId(), status);
			status = checkstatus > status ? checkstatus : status;
			GeneralApplication generalApplication = generalProjectFinder.findApplication(spa.getProjectName(), spa.getApplyer(), 2015, status);
			if (generalApplication == null){
				System.out.println(spa.getId());
				throw new RuntimeException();
			} else {
				spa.setProjectApplication(generalApplication);
				dao.modify(spa);
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public void fixSpecialApplication() {
		List<SinossProjectApplication> sinossProjectApplications = dao.query("select spa from SinossProjectApplication spa where spa.year='2015' and spa.typeCode='special'");
		int total = sinossProjectApplications.size();
		int current = 0;
		int checkstatus;
		for(SinossProjectApplication spa : sinossProjectApplications) {
			System.out.println("special:" + (++current) + "/" + total);
			int status = 0;
			switch (Integer.parseInt(spa.getCheckStatus())) {
				case 2: status = 4;
				        break;
				case 3: status = 3;
				        break;
				case 4: status = 5;
						break;
				case 5: status = 4;
						break;
				case 8: status = 4;
						break;
				default:status = 3;
			}
			checkstatus = getCheckInfo(spa.getId(), status);
			status = checkstatus > status ? checkstatus : status;
			SpecialApplication specialApplication = specialProjectFinder.findApplication(spa.getProjectName(), spa.getApplyer(), 2015, status);
			if (specialApplication == null){
				System.out.println(spa.getId());
				throw new RuntimeException();
			} else {
				spa.setProjectApplication(specialApplication);
				dao.modify(spa);
			}
		}
	}
	
	public int getCheckInfo(String sApplicationId, int finalStatus){
		int statusNumber = 0;
		int lastCheckStatus;
		boolean hasCheckInfo = false;  //是否有审核记录
		SinossChecklogs[] lastSChecklogs = {null, null};//[0]：校级审核记录；[1]:主管部门(省级)审核记录
		//分别获取校级和省级最终审核记录（有可能先通过，后来又不通过）
		sinossTempChecklogs = projectIdToSChecklogMap.get(sApplicationId);
		if (sinossTempChecklogs != null){
			for(SinossChecklogs sicheck : sinossTempChecklogs){
				statusNumber = sicheck.getCheckStatus();
				if (statusNumber == 2 || statusNumber ==3 ){ //校级审核
					lastSChecklogs[0] = (lastSChecklogs[0] != null && sicheck.getCheckDate().before(lastSChecklogs[0].getCheckDate())) ? lastSChecklogs[0]: sicheck;
					hasCheckInfo = true;
				}else if (statusNumber == 4 || statusNumber ==5) { //省级审核
					lastSChecklogs[1] = (lastSChecklogs[1] != null && sicheck.getCheckDate().before(lastSChecklogs[1].getCheckDate())) ? lastSChecklogs[1]: sicheck;
					hasCheckInfo = true;
				}				
			}
		}
		
		if(!hasCheckInfo){
			return 0;
		}
		
		//获取最终审核状态
		if(lastSChecklogs[1] != null){ //校级必然通过
			lastCheckStatus = lastSChecklogs[1].getCheckStatus();
		}else{
			lastCheckStatus = lastSChecklogs[0].getCheckStatus();
		}
		if(lastCheckStatus%2 ==1){   
			if(lastCheckStatus == 5 && finalStatus < 4){//省级审核不通过，说明校级通过
				return 4;
			}
			if((lastCheckStatus ==3 || lastCheckStatus ==1) && finalStatus < 3){//校级审核不通过
				return 3;
			}
		}
		if(lastCheckStatus%2 == 0){
			//校级必通过
			if (finalStatus < 4){
				finalStatus = 4;
			}
			//省级审核通过
			if(lastCheckStatus == 4 && finalStatus < 5){
				finalStatus = 5;
			}
			return finalStatus;
		}
		return 3;
	}
	
	/**
	 * 初始化中间表中申请项目的审核信息
	 * @param dumpDate
	 */
	@SuppressWarnings("unchecked")
	public void initSinossCheckData(String dumpDate){
		Date begin = new Date();
		Map<String, String> sclParameters = new HashMap<String, String>();
		sclParameters.put("dumpDate", dumpDate);
		List<SinossChecklogs> sinossCheckData = dao.query("select scl from SinossChecklogs scl where to_char(scl.dumpDate,'yyyy-mm-dd') = :dumpDate", sclParameters);
		String projectIdString = null;
		for (SinossChecklogs scl : sinossCheckData) {
			if (projectIdToSChecklogMap == null) {
				projectIdToSChecklogMap = new HashMap<String, List<SinossChecklogs>>();
			}
			projectIdString = scl.getProjectApplication().getId();
			List<SinossChecklogs> tempSinossChecklogs = projectIdToSChecklogMap.get(projectIdString);
			if (tempSinossChecklogs == null) {
				tempSinossChecklogs = new ArrayList<SinossChecklogs>();
			}
			tempSinossChecklogs.add(scl);
			projectIdToSChecklogMap.put(projectIdString, tempSinossChecklogs);
		}
		System.out.println("initSinossCheckData completed! Use time : " + (new Date().getTime() - begin.getTime()) + "ms");		
	}
	
}
