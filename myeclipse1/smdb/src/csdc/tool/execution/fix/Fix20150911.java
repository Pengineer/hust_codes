package csdc.tool.execution.fix;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import csdc.bean.Agency;
import csdc.bean.ProjectMidinspection;
import csdc.bean.SinossChecklogs;
import csdc.bean.SinossProjectMidinspection;
import csdc.tool.execution.importer.Importer;

/**
 * 修正2015年中检申报数据的入库错误，部分没有审核详情信息的数据
 * 的审核状态没有录入正式库，以及中检在校级审核通过，省级没审核的情况下推送至部级
 * @author huangjun
 */
@Component
public class Fix20150911 extends Importer {
	protected Map<String, List<SinossChecklogs>> sinossChecklogs;
	protected Map<String, ProjectMidinspection> projectMidMaps;
	protected Map<String, Agency> agencyMap;

	public Fix20150911(){
	}
	
	@Override
	protected void work() throws Throwable {
		initCheckInfo();
		init2015ProjectMid();
		deal2015ProjectMid();
	}

	private void deal2015ProjectMid(){
		List<SinossProjectMidinspection> spMidinspections = dao.query("select spm from SinossProjectMidinspection spm where to_char(spm.dumpDate, 'yyyymmdd')='20150805'");
		
		for (SinossProjectMidinspection spm: spMidinspections) {
			List<SinossChecklogs> spmChecklogs = sinossChecklogs.get(spm.getId());
			if(null == spmChecklogs){
				if (spm.getCheckStatus() != null) {
					ProjectMidinspection projectMid = spm.getProjectMidinspection();
					
					if(null != projectMid){
						int finalStatus = 0;
						switch (Integer.parseInt(spm.getCheckStatus())) {
						case 0: finalStatus = 0;
								projectMid.setApplicantSubmitStatus(0);
								break;
						case 1: finalStatus = 0;
								projectMid.setApplicantSubmitStatus(1);
								break;
						case 2: finalStatus = 5;
								projectMid.setStatus(5);
								projectMid.setApplicantSubmitStatus(3);
								projectMid.setUniversityAuditResult(2);
								projectMid.setUniversityAuditStatus(3);
								projectMid.setUniversityAuditDate(spm.getCheckDate());
								projectMid.setUniversityAuditorName(spm.getChecker().trim());
								projectMid.setUniversityAuditorAgency(getAgencyByName(spm.getChecker().trim()));
								projectMid.setUniversityAuditOpinion(spm.getCheckInfo());
								break;
						case 3: finalStatus = 3;
								projectMid.setStatus(3);
								projectMid.setApplicantSubmitStatus(3);
								projectMid.setUniversityAuditResult(1);
								projectMid.setUniversityAuditStatus(3);
								
								projectMid.setUniversityAuditDate(spm.getCheckDate());
								projectMid.setUniversityAuditorName(spm.getChecker().trim());
								projectMid.setUniversityAuditorAgency(getAgencyByName(spm.getChecker().trim()));
								projectMid.setUniversityAuditOpinion(spm.getCheckInfo());
								
								projectMid.setFinalAuditResult(1);
								projectMid.setFinalAuditStatus(3);
								projectMid.setFinalAuditDate(spm.getCheckDate());
								projectMid.setFinalAuditorName(spm.getChecker().trim());
								projectMid.setFinalAuditorAgency(getAgencyByName(spm.getChecker().trim()));
								projectMid.setFinalAuditOpinion(spm.getCheckInfo());
								break;
						case 4: finalStatus = 5;
								projectMid.setStatus(5);
								
								projectMid.setUniversityAuditResult(2);//[0：未审核；1：不同意；2：同意]
								projectMid.setUniversityAuditStatus(3);//[0：默认；1：退回；2：暂存；3：提交]
								
								projectMid.setProvinceAuditResult(2);
								projectMid.setProvinceAuditStatus(3);
								projectMid.setProvinceAuditDate(spm.getCheckDate());
								projectMid.setProvinceAuditorName(spm.getChecker().trim());
								projectMid.setProvinceAuditorAgency(getAgencyByName(spm.getChecker().trim()));
								projectMid.setProvinceAuditOpinion(spm.getCheckInfo());
								break;
						case 5: finalStatus = 5;
								projectMid.setStatus(5);
								
								projectMid.setUniversityAuditResult(2);//[0：未审核；1：不同意；2：同意]
								projectMid.setUniversityAuditStatus(3);//[0：默认；1：退回；2：暂存；3：提交]
								
								projectMid.setProvinceAuditResult(1);
								projectMid.setProvinceAuditStatus(3);
								projectMid.setProvinceAuditDate(spm.getCheckDate());
								projectMid.setProvinceAuditorName(spm.getChecker().trim());
								projectMid.setProvinceAuditorAgency(getAgencyByName(spm.getChecker().trim()));
								projectMid.setProvinceAuditOpinion(spm.getCheckInfo());
								
								/*projectMid.setFinalAuditResult(1);
								projectMid.setFinalAuditStatus(3);
								projectMid.setFinalAuditDate(spm.getCheckDate());
								projectMid.setFinalAuditorName(spm.getChecker().trim());
								projectMid.setFinalAuditorAgency(getAgencyByName(spm.getChecker().trim()));
								projectMid.setFinalAuditOpinion(spm.getCheckInfo());*/
								
								break;
						case 6: finalStatus = 5;
								projectMid.setStatus(5);
								projectMid.setApplicantSubmitStatus(3);
								break;
						case 7: finalStatus = 5;
								projectMid.setStatus(5);
								projectMid.setApplicantSubmitStatus(3);
								break;
						case 8: finalStatus = 3; 
								projectMid.setStatus(3);
								projectMid.setApplicantSubmitStatus(2);
								projectMid.setDeptInstAuditResult(2);//院系审核都通过
								projectMid.setDeptInstAuditStatus(3);
								projectMid.setUniversityAuditDate(spm.getCheckDate());
								projectMid.setUniversityAuditorName(spm.getChecker().trim());
								projectMid.setUniversityAuditorAgency(getAgencyByName(spm.getChecker().trim()));
								projectMid.setUniversityAuditOpinion(spm.getCheckInfo());
								break;
						case 9: finalStatus = 3; 
								projectMid.setStatus(3);
								projectMid.setApplicantSubmitStatus(3);
								projectMid.setDeptInstAuditResult(2);//院系审核都通过
								projectMid.setDeptInstAuditStatus(3);
								break;
						default:finalStatus = 3; 
								projectMid.setStatus(3);
						}
						if(finalStatus > 1 && finalStatus < 6){
							projectMid.setDeptInstAuditResult(2);
							projectMid.setDeptInstAuditStatus(3);
							//projectMid.setDeptInstAuditDate(spm.getApplyDate());
						}
						dao.modify(projectMid);
					}
				}
			}
		}
	}
	
	private void initCheckInfo(){
		Long begin = System.currentTimeMillis();
		List<SinossChecklogs> tempSinossChecklogs = dao.query("select o from SinossChecklogs o where to_char(o.dumpDate, 'yyyymmdd') =  '20150805'");
		if (sinossChecklogs == null) {
			sinossChecklogs = new HashMap<String, List<SinossChecklogs>>();
		}
		List<SinossChecklogs> tempList;
		for (SinossChecklogs sc : tempSinossChecklogs) {
			tempList = sinossChecklogs.get(sc.getProjectMidinspection().getId());
			if (tempList == null) {
				tempList = new ArrayList<SinossChecklogs>();
			}
			tempList.add(sc);
			sinossChecklogs.put(sc.getProjectMidinspection().getId(), tempList);
		}
		//sinossChecklogs = dao.query("select o from SinossChecklogs o where o.type=2");
		System.out.println("InitCheckInfo Complete! Used time:" + (System.currentTimeMillis() - begin) + "ms");
	}
	
	private void init2015ProjectMid(){
		Long begin = System.currentTimeMillis();
		List<ProjectMidinspection> tempProjectMids = dao.query("select pm from ProjectMidinspection pm where to_char(pm.createDate, 'yyyymmdd') =  '20150811'");
		if (projectMidMaps == null) {
			projectMidMaps = new HashMap<String, ProjectMidinspection>();
		}

		for (ProjectMidinspection pm : tempProjectMids) {
			projectMidMaps.put(pm.getId(), pm);
		}
		//sinossChecklogs = dao.query("select o from SinossChecklogs o where o.type=2");
		System.out.println("InitCheckInfo Complete! Used time:" + (System.currentTimeMillis() - begin) + "ms");
	}
	
	private Agency getAgencyByName(String agencyName) {
		if (agencyMap == null) {
			initUnivMap();
		}
		return agencyMap.get(agencyName);
	}
	
	private void initUnivMap() {
		long beginTime  = new Date().getTime();

		agencyMap = new HashMap<String, Agency>();
		List<Agency> agencyList = dao.query("select agency from Agency agency");
		for (Agency agency : agencyList) {
			agencyMap.put(agency.getName().trim(), agency);
		}
		
		System.out.println("initUnivMap complete! Used time: " + (new Date().getTime() - beginTime) + "ms");
	}
}
