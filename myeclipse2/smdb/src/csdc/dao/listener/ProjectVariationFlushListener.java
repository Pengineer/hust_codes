package csdc.dao.listener;

import org.hibernate.HibernateException;
import org.hibernate.event.spi.FlushEntityEvent;
import org.hibernate.event.spi.FlushEntityEventListener;

import csdc.bean.EntrustVariation;
import csdc.bean.GeneralVariation;
import csdc.bean.InstpVariation;
import csdc.bean.KeyVariation;
import csdc.bean.PostVariation;
import csdc.bean.ProjectVariation;

public class ProjectVariationFlushListener implements FlushEntityEventListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void onFlushEntity(FlushEntityEvent event) throws HibernateException {
		Object entity = event.getEntity();
		if (!(entity instanceof ProjectVariation)) {
			return;
		}

		if (entity instanceof GeneralVariation) {
			GeneralVariation variation = (GeneralVariation) entity;
			variation.setGrantedId(variation.getGranted().getId());
		} else if (entity instanceof InstpVariation) {
			InstpVariation variation = (InstpVariation) entity;
			variation.setGrantedId(variation.getGranted().getId());
		} else if (entity instanceof PostVariation) {
			PostVariation variation = (PostVariation) entity;
			variation.setGrantedId(variation.getGranted().getId());
		} else if (entity instanceof KeyVariation) {
			KeyVariation variation = (KeyVariation) entity;
			variation.setGrantedId(variation.getGranted().getId());
		} else if (entity instanceof EntrustVariation) {
			EntrustVariation variation = (EntrustVariation) entity;
			variation.setGrantedId(variation.getGranted().getId());
		} else {
			throw new RuntimeException();
		}
	}
}
