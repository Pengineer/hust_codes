package csdc.action.project.special;

public class MidinspectionApplyRequiredAction extends csdc.action.project.MidinspectionApplyRequiredAction {

	private static final long serialVersionUID = -5382106351870716640L;

	private static final String PAGE_NAME = "specialMidInspectionRequiredPage";//列表页面名称
	private static final String PROJECT_TYPE = "special";//项目类别
	
	public String projectType(){
		return MidinspectionApplyRequiredAction.PROJECT_TYPE;
	}
	public String pageName() {
		return MidinspectionApplyRequiredAction.PAGE_NAME;
	}
}
