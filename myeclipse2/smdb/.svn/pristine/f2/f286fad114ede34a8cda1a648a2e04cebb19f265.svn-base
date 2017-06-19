package csdc.dao.listener;

import org.hibernate.HibernateException;
import org.hibernate.event.spi.FlushEntityEvent;
import org.hibernate.event.spi.FlushEntityEventListener;

import csdc.bean.EntrustEndinspectionReview;
import csdc.bean.GeneralEndinspectionReview;
import csdc.bean.InstpEndinspectionReview;
import csdc.bean.KeyEndinspectionReview;
import csdc.bean.PostEndinspectionReview;
import csdc.bean.ProjectEndinspectionReview;

public class ProjectEndinspectionReviewFlushListener implements FlushEntityEventListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void onFlushEntity(FlushEntityEvent event) throws HibernateException {
		Object entity = event.getEntity();
		if (!(entity instanceof ProjectEndinspectionReview)) {
			return;
		}

		if (entity instanceof GeneralEndinspectionReview) {
			GeneralEndinspectionReview endinspectionReview = (GeneralEndinspectionReview) entity;
			endinspectionReview.setEndinspectionId(endinspectionReview.getEndinspection().getId());
		} else if (entity instanceof InstpEndinspectionReview) {
			InstpEndinspectionReview endinspectionReview = (InstpEndinspectionReview) entity;
			endinspectionReview.setEndinspectionId(endinspectionReview.getEndinspection().getId());
		} else if (entity instanceof PostEndinspectionReview) {
			PostEndinspectionReview endinspectionReview = (PostEndinspectionReview) entity;
			endinspectionReview.setEndinspectionId(endinspectionReview.getEndinspection().getId());
		} else if (entity instanceof KeyEndinspectionReview) {
			KeyEndinspectionReview endinspectionReview = (KeyEndinspectionReview) entity;
			endinspectionReview.setEndinspectionId(endinspectionReview.getEndinspection().getId());
		} else if (entity instanceof EntrustEndinspectionReview) {
			EntrustEndinspectionReview endinspectionReview = (EntrustEndinspectionReview) entity;
			endinspectionReview.setEndinspectionId(endinspectionReview.getEndinspection().getId());
		} else {
			throw new RuntimeException();
		}
	}
}
