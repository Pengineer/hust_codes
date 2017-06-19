package csdc.bean;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

public class SmdbProjectMidinspection  implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private String id;//主键id
	
	private String smdbMidinspectionID;//smdb端原id
	private String smasMidinspectionID;//smas入库新生成的id,便于关联
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
	@JSON(format="yyyy-MM-dd")
	public Date getImportedDate() {
		return importedDate;
	}
	
	public void setImportedDate(Date importedDate) {
		this.importedDate = importedDate;
	}

	public String getSmdbMidinspectionID() {
		return smdbMidinspectionID;
	}


	public void setSmdbMidinspectionID(String smdbMidinspectionID) {
		this.smdbMidinspectionID = smdbMidinspectionID;
	}


	public String getSmasMidinspectionID() {
		return smasMidinspectionID;
	}


	public void setSmasMidinspectionID(String smasMidinspectionID) {
		this.smasMidinspectionID = smasMidinspectionID;
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

	@JSON(serialize=false)
	public SmdbProjectGranted getSmdbGranted() {
		return smdbGranted;
	}

	public void setSmdbGranted(SmdbProjectGranted smdbGranted) {
		this.smdbGranted = smdbGranted;
	}
}
