package csdc.dao.listener;

import org.hibernate.HibernateException;
import org.hibernate.event.spi.FlushEntityEvent;
import org.hibernate.event.spi.FlushEntityEventListener;

import csdc.bean.EntrustGranted;
import csdc.bean.GeneralGranted;
import csdc.bean.InstpGranted;
import csdc.bean.KeyGranted;
import csdc.bean.PostGranted;
import csdc.bean.ProjectGranted;
import csdc.bean.DevrptGranted;
import csdc.bean.SpecialGranted;

public class ProjectGrantedFlushListener implements FlushEntityEventListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void onFlushEntity(FlushEntityEvent event) throws HibernateException {
		Object entity = event.getEntity();
		if (!(entity instanceof ProjectGranted)) {
			return;
		}

		if (entity instanceof GeneralGranted) {
			GeneralGranted granted = (GeneralGranted) entity;
			granted.setApplicationId(granted.getApplication().getId());
		} else if (entity instanceof InstpGranted) {
			InstpGranted granted = (InstpGranted) entity;
			granted.setApplicationId(granted.getApplication().getId());
		} else if (entity instanceof PostGranted) {
			PostGranted granted = (PostGranted) entity;
			granted.setApplicationId(granted.getApplication().getId());
		} else if (entity instanceof KeyGranted) {
			KeyGranted granted = (KeyGranted) entity;
			granted.setApplicationId(granted.getApplication().getId());
		} else if (entity instanceof EntrustGranted) {
			EntrustGranted granted = (EntrustGranted) entity;
			granted.setApplicationId(granted.getApplication().getId());
		} else if (entity instanceof DevrptGranted) {
			DevrptGranted granted = (DevrptGranted) entity;
			granted.setApplicationId(granted.getApplication().getId());
		} else if (entity instanceof SpecialGranted) {
			SpecialGranted granted = (SpecialGranted) entity;
			granted.setApplicationId(granted.getApplication().getId());
		} else {
			throw new RuntimeException();
		}
	}
	

	
}
