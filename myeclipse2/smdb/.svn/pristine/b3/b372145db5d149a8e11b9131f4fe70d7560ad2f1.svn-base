package csdc.dao.listener;

import org.hibernate.HibernateException;
import org.hibernate.event.spi.FlushEntityEvent;
import org.hibernate.event.spi.FlushEntityEventListener;

import csdc.bean.EntrustEndinspection;
import csdc.bean.GeneralEndinspection;
import csdc.bean.InstpEndinspection;
import csdc.bean.KeyEndinspection;
import csdc.bean.PostEndinspection;
import csdc.bean.ProjectEndinspection;

public class ProjectEndinspectionFlushListener implements FlushEntityEventListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void onFlushEntity(FlushEntityEvent event) throws HibernateException {
		Object entity = event.getEntity();
		if (!(entity instanceof ProjectEndinspection)) {
			return;
		}

		if (entity instanceof GeneralEndinspection) {
			GeneralEndinspection endinspection = (GeneralEndinspection) entity;
			endinspection.setGrantedId(endinspection.getGranted().getId());
		} else if (entity instanceof InstpEndinspection) {
			InstpEndinspection endinspection = (InstpEndinspection) entity;
			endinspection.setGrantedId(endinspection.getGranted().getId());
		} else if (entity instanceof PostEndinspection) {
			PostEndinspection endinspection = (PostEndinspection) entity;
			endinspection.setGrantedId(endinspection.getGranted().getId());
		} else if (entity instanceof KeyEndinspection) {
			KeyEndinspection endinspection = (KeyEndinspection) entity;
			endinspection.setGrantedId(endinspection.getGranted().getId());
		} else if (entity instanceof EntrustEndinspection) {
			EntrustEndinspection endinspection = (EntrustEndinspection) entity;
			endinspection.setGrantedId(endinspection.getGranted().getId());
		} else {
			throw new RuntimeException();
		}
	}
}
