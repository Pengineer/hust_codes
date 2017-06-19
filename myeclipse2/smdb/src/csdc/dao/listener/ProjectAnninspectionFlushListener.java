package csdc.dao.listener;

import org.hibernate.HibernateException;
import org.hibernate.event.spi.FlushEntityEvent;
import org.hibernate.event.spi.FlushEntityEventListener;

import csdc.bean.GeneralAnninspection;
import csdc.bean.InstpAnninspection;
import csdc.bean.KeyAnninspection;
import csdc.bean.PostAnninspection;
import csdc.bean.ProjectAnninspection;

public class ProjectAnninspectionFlushListener implements FlushEntityEventListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void onFlushEntity(FlushEntityEvent event) throws HibernateException {
		Object entity = event.getEntity();
		if (!(entity instanceof ProjectAnninspection)) {
			return;
		}

		if (entity instanceof GeneralAnninspection) {
			GeneralAnninspection anninspection = (GeneralAnninspection) entity;
			anninspection.setGrantedId(anninspection.getGranted().getId());
		} else if (entity instanceof InstpAnninspection) {
			InstpAnninspection anninspection = (InstpAnninspection) entity;
			anninspection.setGrantedId(anninspection.getGranted().getId());
		} else if (entity instanceof PostAnninspection) {
			PostAnninspection anninspection = (PostAnninspection) entity;
			anninspection.setGrantedId(anninspection.getGranted().getId());
		} else if (entity instanceof KeyAnninspection) {
			KeyAnninspection anninspection = (KeyAnninspection) entity;
			anninspection.setGrantedId(anninspection.getGranted().getId());
		} else {
			throw new RuntimeException();
		}
	}
}
