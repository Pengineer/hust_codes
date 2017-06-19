package csdc.service.imp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.bean.Applicant;
import csdc.dao.IHibernateBaseDao;
import csdc.service.IManagementService;

/**
 * 内部管理系统业务处理
 * @author suwb
 *
 */
public class ManagementService implements IManagementService {

	@Autowired
	protected IHibernateBaseDao dao;
	
	public Applicant getAppById(String id) {
		Map map = new HashMap();
		map.put("id", id);
		Applicant app = (Applicant)dao.queryUnique("select app from Applicant app where app.id=:id", map);
		return app;
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
		a.put("photoFile", b.getPhotoFile());
		a.put("file", b.getFile());
		a.put("email", b.getEmail());
		a.put("idCardNumber", b.getIdCardNumber());
		return a;
	}
	
	public List<String[]> listTemplate(){
		return (List<String[]>)dao.query("select t.id, t.name from Template t");
	}

}
