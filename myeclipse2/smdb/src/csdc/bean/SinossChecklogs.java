package csdc.bean;

import java.util.Date;


/**
 * 审核记录（与社科网对接接口类）
 *
 */
public class SinossChecklogs {
	
	private String id;//主键id
	private int type;//审核类型（0默认，1申报，2中检，3变更，4结项）
	private int checkStatus;//审核状态
	private Date checkDate;//审核时间
	private String checker;//审核人
	private String checkInfo;//审核意见
	private SinossProjectVariation projectVariation;
	private SinossProjectMidinspection projectMidinspection;
	private SinossProjectApplication projectApplication;
	private String sinossId;//社科网Id
	private String sinossProjectId;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getCheckStatus() {
		return checkStatus;
	}
	public void setCheckStatus(int checkStatus) {
		this.checkStatus = checkStatus;
	}
	public Date getCheckDate() {
		return checkDate;
	}
	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}
	public String getChecker() {
		return checker;
	}
	public void setChecker(String checker) {
		this.checker = checker;
	}
	public String getCheckInfo() {
		return checkInfo;
	}
	public void setCheckInfo(String checkInfo) {
		this.checkInfo = checkInfo;
	}
	public SinossProjectVariation getProjectVariation() {
		return projectVariation;
	}
	public void setProjectVariation(SinossProjectVariation projectVariation) {
		this.projectVariation = projectVariation;
	}
	public SinossProjectMidinspection getProjectMidinspection() {
		return projectMidinspection;
	}
	public void setProjectMidinspection(SinossProjectMidinspection projectMidinspection) {
		this.projectMidinspection = projectMidinspection;
	}
	
	public SinossProjectApplication getProjectApplication() {
		return projectApplication;
	}
	public void setProjectApplication(SinossProjectApplication projectApplication) {
		this.projectApplication = projectApplication;
	}
	public String getSinossId() {
		return sinossId;
	}
	public void setSinossId(String sinossId) {
		this.sinossId = sinossId;
	}
	public String getSinossProjectId() {
		return sinossProjectId;
	}
	public void setSinossProjectId(String sinossProjectId) {
		this.sinossProjectId = sinossProjectId;
	}

    
}
