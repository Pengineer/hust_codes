package csdc.dao.listener;

import org.hibernate.HibernateException;
import org.hibernate.event.spi.FlushEntityEvent;
import org.hibernate.event.spi.FlushEntityEventListener;

import csdc.bean.EntrustFunding;
import csdc.bean.GeneralFunding;
import csdc.bean.InstpFunding;
import csdc.bean.KeyFunding;
import csdc.bean.PostFunding;
import csdc.bean.ProjectFunding;

public class ProjectFundingFlushListener implements FlushEntityEventListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void onFlushEntity(FlushEntityEvent event) throws HibernateException {
		Object entity = event.getEntity();
		if (!(entity instanceof ProjectFunding)) {
			return;
		}

		if (entity instanceof GeneralFunding) {
			GeneralFunding funding = (GeneralFunding) entity;
			funding.setGrantedId(funding.getGranted().getId());
		} else if (entity instanceof InstpFunding) {
			InstpFunding funding = (InstpFunding) entity;
			funding.setGrantedId(funding.getGranted().getId());
		} else if (entity instanceof PostFunding) {
			PostFunding funding = (PostFunding) entity;
			funding.setGrantedId(funding.getGranted().getId());
		} else if (entity instanceof KeyFunding) {
			KeyFunding funding = (KeyFunding) entity;
			funding.setGrantedId(funding.getGranted().getId());
		} else if (entity instanceof EntrustFunding) {
			EntrustFunding funding = (EntrustFunding) entity;
			funding.setGrantedId(funding.getGranted().getId());
		} else {
			throw new RuntimeException();
		}
	}
}
