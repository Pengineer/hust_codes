package csdc.dao.listener;

import org.hibernate.HibernateException;
import org.hibernate.event.spi.FlushEntityEvent;
import org.hibernate.event.spi.FlushEntityEventListener;

import csdc.bean.GeneralMidinspection;
import csdc.bean.InstpMidinspection;
import csdc.bean.KeyMidinspection;
import csdc.bean.ProjectMidinspection;
import csdc.bean.SpecialMidinspection;

public class ProjectMidinspectionFlushListener implements FlushEntityEventListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void onFlushEntity(FlushEntityEvent event) throws HibernateException {
		Object entity = event.getEntity();
		if (!(entity instanceof ProjectMidinspection)) {
			return;
		}

		if (entity instanceof GeneralMidinspection) {
			GeneralMidinspection midinspection = (GeneralMidinspection) entity;
			midinspection.setGrantedId(midinspection.getGranted().getId());
		} else if (entity instanceof InstpMidinspection) {
			InstpMidinspection midinspection = (InstpMidinspection) entity;
			midinspection.setGrantedId(midinspection.getGranted().getId());
		} else if (entity instanceof KeyMidinspection) {
			KeyMidinspection midinspection = (KeyMidinspection) entity;
			midinspection.setGrantedId(midinspection.getGranted().getId());
		} else if (entity instanceof SpecialMidinspection) {
			SpecialMidinspection midinspection = (SpecialMidinspection) entity;
			midinspection.setGrantedId(midinspection.getGranted().getId());
		} else {
			throw new RuntimeException();
		}
	}
	

	
}
