package csdc.dao.listener;

import org.hibernate.HibernateException;
import org.hibernate.event.spi.FlushEntityEvent;
import org.hibernate.event.spi.FlushEntityEventListener;

import csdc.bean.EntrustMember;
import csdc.bean.GeneralMember;
import csdc.bean.InstpMember;
import csdc.bean.KeyMember;
import csdc.bean.PostMember;
import csdc.bean.ProjectMember;

public class ProjectMemberFlushListener implements FlushEntityEventListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void onFlushEntity(FlushEntityEvent event) throws HibernateException {
		Object entity = event.getEntity();
		if (!(entity instanceof ProjectMember)) {
			return;
		}

		if (entity instanceof GeneralMember) {
			GeneralMember member = (GeneralMember) entity;
			member.setApplicationId(member.getApplication().getId());
		} else if (entity instanceof InstpMember) {
			InstpMember member = (InstpMember) entity;
			member.setApplicationId(member.getApplication().getId());
		} else if (entity instanceof PostMember) {
			PostMember member = (PostMember) entity;
			member.setApplicationId(member.getApplication().getId());
		} else if (entity instanceof KeyMember) {
			KeyMember member = (KeyMember) entity;
			member.setApplicationId(member.getApplication().getId());
		} else if (entity instanceof EntrustMember) {
			EntrustMember member = (EntrustMember) entity;
			member.setApplicationId(member.getApplication().getId());
		} else {
			throw new RuntimeException();
		}
	}
	

	
}
