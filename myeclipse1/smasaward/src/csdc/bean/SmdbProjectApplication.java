package csdc.bean;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

/**
 * 项目表
 */
public class SmdbProjectApplication implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private String id;
	//中间表新增字段
	private String smdbApplID;//smdb端原id   common
	private String smasApplID;//smas入库新生成的id,便于关联
	private int isAdded;//标志是否已入库
	private Date importedDate;//数据入库时间
	private String importPerson;//数据入库操作人员

	//申请经费，直接入库正式表，与正式表相关联
	public SmdbProjectApplication(){}

	public SmdbProjectApplication(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
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

	public String getSmdbApplID() {
		return smdbApplID;
	}

	public void setSmdbApplID(String smdbApplID) {
		this.smdbApplID = smdbApplID;
	}

	public String getSmasApplID() {
		return smasApplID;
	}

	public void setSmasApplID(String smasApplID) {
		this.smasApplID = smasApplID;
	}
	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getImportedDate() {
		return importedDate;
	}

	public void setImportedDate(Date importedDate) {
		this.importedDate = importedDate;
	}

}
