package csdc.service.imp;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.bean.Applicant;
import csdc.bean.Job;
import csdc.dao.IHibernateBaseDao;
import csdc.service.IPortalService;

/**
 * 门户网站业务处理
 * @author suwb
 *
 */
public class PortalService implements IPortalService {
	
	@Autowired
	protected IHibernateBaseDao dao;

	public List<String[]> searchJob() {
		List<String[]> list = (List<String[]>)dao.query("select j.id, j.name, j.number, j.publishDate, j.endDate from Job j order by j.publishDate desc");
		return list;
	}

	public Job viewJob(String jobId) {
		Map map = new HashMap();
		map.put("jobId", jobId);
		Job job = (Job)dao.queryUnique("select j from Job j where j.id=:jobId", map);
		return job;
	}
	
	public Map initInfo(Map a, Applicant b){
		a.put("birthday", b.getBirthday());
		a.put("birthPlace", b.getBirthplace());
		a.put("ethnic", b.getEthnic());
		a.put("gender", b.getGender());
		a.put("major", b.getMajor());
		a.put("membership", b.getMembership());
		a.put("mobilePhone", b.getMobilePhone());
		a.put("name", b.getName());
		a.put("qq", b.getQq());
		a.put("status", b.getStatus());
		a.put("address", b.getAddress());
		a.put("education", b.getEducation());
		return a;
	}

	public Map initAppDate(Map a, Applicant b) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		a.put("applicantDate", b.getApplicantDate()!=null?sdf.format(b.getApplicantDate()):"");
		a.put("birthday", b.getBirthday()!=null?sdf.format(b.getBirthday()):"");
		a.put("auditDate", b.getAuditDate()!=null?sdf.format(b.getAuditDate()):"");
		a.put("birthPlace", b.getBirthplace());
		a.put("ethnic", b.getEthnic());
		a.put("gender", b.getGender());
		a.put("major", b.getMajor());
		a.put("membership", b.getMembership());
		a.put("mobilePhone", b.getMobilePhone());
		a.put("name", b.getName());
		a.put("qq", b.getQq());
		a.put("status", b.getStatus());
		a.put("address", b.getAddress());
		a.put("education", b.getEducation());
		return a;
	}
	
	public String accquireFileSize(long fileLength) {
		DecimalFormat df = new DecimalFormat("#.00");
		String fileSizeString = "";
		if (fileLength < 1024) {
			fileSizeString = df.format((double) fileLength) + "B";
		} else if (fileLength < 1048576) {
			fileSizeString = df.format((double) fileLength / 1024) + "K";
		} else if (fileLength < 1073741824) {
			fileSizeString = df.format((double) fileLength / 1048576) + "M";
		} else {
			fileSizeString = df.format((double) fileLength / 1073741824) + "G";
		}
		return fileSizeString;
	}
}
