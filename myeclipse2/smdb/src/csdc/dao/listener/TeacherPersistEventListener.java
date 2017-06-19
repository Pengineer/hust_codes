package csdc.dao.listener;

import org.hibernate.HibernateException;
import org.hibernate.event.spi.FlushEntityEvent;
import org.hibernate.event.spi.FlushEntityEventListener;

import csdc.bean.Teacher;

public class TeacherPersistEventListener implements FlushEntityEventListener {

	private static final long serialVersionUID = 1L;

	public void onFlushEntity(FlushEntityEvent event) throws HibernateException {
		Object entity = event.getEntity();
		
		if (entity instanceof Teacher) {
			Teacher teacher = (Teacher) entity;
			if (teacher.getDepartment() != null) {
				teacher.setDivisionName(teacher.getDepartment().getName());
			} else if (teacher.getInstitute() != null) {
				teacher.setDivisionName(teacher.getInstitute().getName());
			}
			teacher.setAgencyName(teacher.getUniversity().getName());
		}
	}

}
