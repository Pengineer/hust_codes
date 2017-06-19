package csdc.action.project.key;

import csdc.service.IGeneralService;

public class ApplicationGrantedAuditAction extends csdc.action.project.ApplicationGrantedAuditAction{
	private static final long serialVersionUID = 1L;
	private static final String PROJECT_TYPE = "key";
	private static final String BUSINESS_TYPE = "041";
	private static final int CHECK_APPLICATION_FLAG = 24;
	private static final int CHECK_GRANTED_FLAG = 25;
	@Override
	public String projectType() {
		return ApplicationGrantedAuditAction.PROJECT_TYPE;
	}

	@Override
	public int checkApplicationFlag() {
		return ApplicationGrantedAuditAction.CHECK_APPLICATION_FLAG;
	}

	@Override
	public int checkGrantedFlag() {
		return ApplicationGrantedAuditAction.CHECK_APPLICATION_FLAG;
	}

	@Override
	public String businessType() {
		return ApplicationGrantedAuditAction.BUSINESS_TYPE;
	}

}
