package csdc.tool.execution;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import csdc.bean.GeneralMember;
import csdc.bean.Teacher;
import csdc.service.IBaseService;
import csdc.tool.ApplicationContainer;
import csdc.tool.SpringBean;

/**
 * 还原smdb一般项目成员表项目负责人的机构信息为其对应Teacher表里的信息
 * @author xuhan
 *
 */
@SuppressWarnings("unchecked")
public class GeneralProjectMajorMemberAgencyResetter {
	
	public static IBaseService baseService = (IBaseService) SpringBean.getBean("baseService", ApplicationContainer.sc);
	
	public void work() throws Exception {
		Session session = baseService.getSession();
		Transaction tx = session.getTransaction();
		tx.begin();
		
		try {
			List<GeneralMember> gms = session.createQuery("select gm from GeneralMember gm where gm.isDirector = 1 and exists (select gv from GeneralVariation gv where gv.granted.application.id = gm.application.id)").list();
			int cnt = 0;
			for (GeneralMember gm : gms) {
				System.out.println((cnt++) + " / " + gms.size());
				System.out.println(gm.getId());
				if (gm.getMember() == null || gm.getMember().getTeacher() == null) {
					continue;
				}
				for (Iterator iterator = gm.getMember().getTeacher().iterator(); iterator.hasNext();) {
					Teacher teacher = (Teacher) iterator.next();
					if (teacher.getType().equals("专职人员")) {
						if (!gm.getUniversity().getId().equals(teacher.getUniversity().getId())) {
							System.out.println(gm.getApplication().getId() + " " + gm.getApplication().getName());
						}
						gm.setUniversity(teacher.getUniversity());
						gm.setDepartment(teacher.getDepartment());
						gm.setInstitute(teacher.getInstitute());
						gm.setAgencyName(teacher.getUniversity().getName());
						gm.setDivisionName(teacher.getDepartment() != null ? teacher.getDepartment().getName() : teacher.getInstitute() != null ? teacher.getInstitute().getName() : null);
						session.update(gm);
						break;
					}
				}
			}
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
		} finally {
			baseService.releaseSession(session);
		}
	}



}
