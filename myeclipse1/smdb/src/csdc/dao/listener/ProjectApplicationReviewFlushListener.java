package csdc.dao.listener;

import org.hibernate.HibernateException;
import org.hibernate.event.spi.FlushEntityEvent;
import org.hibernate.event.spi.FlushEntityEventListener;

import csdc.bean.EntrustApplicationReview;
import csdc.bean.GeneralApplicationReview;
import csdc.bean.InstpApplicationReview;
import csdc.bean.KeyApplicationReview;
import csdc.bean.PostApplicationReview;
import csdc.bean.ProjectApplicationReview;
import csdc.bean.SpecialApplicationReview;

public class ProjectApplicationReviewFlushListener implements FlushEntityEventListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void onFlushEntity(FlushEntityEvent event) throws HibernateException {
		Object entity = event.getEntity();
		if (!(entity instanceof ProjectApplicationReview)) {
			return;
		}

		if (entity instanceof GeneralApplicationReview) {
			GeneralApplicationReview applicationReview = (GeneralApplicationReview) entity;
			applicationReview.setApplicationId(applicationReview.getApplication().getId());
		} else if (entity instanceof InstpApplicationReview) {
			InstpApplicationReview applicationReview = (InstpApplicationReview) entity;
			applicationReview.setApplicationId(applicationReview.getApplication().getId());
		} else if (entity instanceof PostApplicationReview) {
			PostApplicationReview applicationReview = (PostApplicationReview) entity;
			applicationReview.setApplicationId(applicationReview.getApplication().getId());
		} else if (entity instanceof KeyApplicationReview) {
			KeyApplicationReview applicationReview = (KeyApplicationReview) entity;
			applicationReview.setApplicationId(applicationReview.getApplication().getId());
		} else if (entity instanceof EntrustApplicationReview) {
			EntrustApplicationReview applicationReview = (EntrustApplicationReview) entity;
			applicationReview.setApplicationId(applicationReview.getApplication().getId());
		} else if (entity instanceof SpecialApplicationReview) {
			SpecialApplicationReview applicationReview = (SpecialApplicationReview) entity;
			applicationReview.setApplicationId(applicationReview.getApplication().getId());
		} else {
			throw new RuntimeException();
		}
	}
	

	
}
