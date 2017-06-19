//package csdc.dao.listener;
//
//import org.hibernate.HibernateException;
//import org.hibernate.event.spi.FlushEntityEvent;
//import org.hibernate.event.spi.FlushEntityEventListener;
//
//import csdc.bean.EntrustFee;
//import csdc.bean.GeneralFee;
//import csdc.bean.InstpFee;
//import csdc.bean.KeyFee;
//import csdc.bean.PostFee;
//import csdc.bean.ProjectFee;
//
//public class ProjectFeeFlushListener implements FlushEntityEventListener {
//
//	/**
//	 * 
//	 */
//	private static final long serialVersionUID = 1L;
//
//	public void onFlushEntity(FlushEntityEvent event) throws HibernateException {
//		Object entity = event.getEntity();
//		if (!(entity instanceof ProjectFee)) {
//			return;
//		}
//
//		if (entity instanceof GeneralFee) {
//			GeneralFee fee = (GeneralFee) entity;
//			if (fee.getApplication() != null) {
//				fee.setApplicationId(fee.getApplication().getId());
//			} else if (fee.getAnninspection() != null) {
//				fee.setAnninspectionId(fee.getAnninspection().getId());
//			} else if (fee.getMidinspection() != null) {
//				fee.setMidinspectionId(fee.getMidinspection().getId());
//			} else if (fee.getEndinspection() != null) {
//				fee.setEndinspectionId(fee.getEndinspection().getId());
//			}
//		} else if (entity instanceof InstpFee) {
//			InstpFee fee = (InstpFee) entity;
//			if (fee.getApplication() != null) {
//				fee.setApplicationId(fee.getApplication().getId());
//			} else if (fee.getAnninspection() != null) {
//				fee.setAnninspectionId(fee.getAnninspection().getId());
//			} else if (fee.getMidinspection() != null) {
//				fee.setMidinspectionId(fee.getMidinspection().getId());
//			} else if (fee.getEndinspection() != null) {
//				fee.setEndinspectionId(fee.getEndinspection().getId());
//			}
//		} else if (entity instanceof PostFee) {
//			PostFee fee = (PostFee) entity;
//			if (fee.getApplication() != null) {
//				fee.setApplicationId(fee.getApplication().getId());
//			} else if (fee.getAnninspection() != null) {
//				fee.setAnninspectionId(fee.getAnninspection().getId());
//			} else if (fee.getEndinspection() != null) {
//				fee.setEndinspectionId(fee.getEndinspection().getId());
//			}
//		} else if (entity instanceof KeyFee) {
//			KeyFee fee = (KeyFee) entity;
//			if (fee.getApplication() != null) {
//				fee.setApplicationId(fee.getApplication().getId());
//			} else if (fee.getAnninspection() != null) {
//				fee.setAnninspectionId(fee.getAnninspection().getId());
//			} else if (fee.getMidinspection() != null) {
//				fee.setMidinspectionId(fee.getMidinspection().getId());
//			} else if (fee.getEndinspection() != null) {
//				fee.setEndinspectionId(fee.getEndinspection().getId());
//			}
//		} else if (entity instanceof EntrustFee) {
//			EntrustFee fee = (EntrustFee) entity;
//			if (fee.getApplication() != null) {
//				fee.setApplicationId(fee.getApplication().getId());
//			} else if (fee.getEndinspection() != null) {
//				fee.setEndinspectionId(fee.getEndinspection().getId());
//			}
//		} else {
//			throw new RuntimeException();
//		}
//	}
//}
