package csdc.bean;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

public class SmdbProjectEndinspection implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private String id;//主键id
	
	private String smdbEndinspectionID;//smdb端原id
	private String smasEndinspectionID;//smas入库新生成的id,便于关联
	private int isAdded;//标志是否已入库
	private Date importedDate;//数据入库时间
	private String importPerson;//数据入库操作人员
	
	private SmdbProjectGranted smdbGranted;//项目立项
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public Date getImportedDate() {
		return importedDate;
	}
	public void setImportedDate(Date importedDate) {
		this.importedDate = importedDate;
	}

	public String getSmdbEndinspectionID() {
		return smdbEndinspectionID;
	}
	public void setSmdbEndinspectionID(String smdbEndinspectionID) {
		this.smdbEndinspectionID = smdbEndinspectionID;
	}
	public String getSmasEndinspectionID() {
		return smasEndinspectionID;
	}
	public void setSmasEndinspectionID(String smasEndinspectionID) {
		this.smasEndinspectionID = smasEndinspectionID;
	}
	public int getIsAdded() {
		return isAdded;
	}
	public void setIsAdded(int isAdded) {
		this.isAdded = isAdded;
	}
	public String getImportPerson() {
		return importPerson;
	}
	public void setImportPerson(String importPerson) {
		this.importPerson = importPerson;
	}
	/**
	 * 获取项目立项对象
	 */
	@JSON(serialize=false)
	public SmdbProjectGranted getSmdbGranted() {
		return smdbGranted;
	}
	public void setSmdbGranted(SmdbProjectGranted smdbGranted) {
		this.smdbGranted = smdbGranted;
	}
}
