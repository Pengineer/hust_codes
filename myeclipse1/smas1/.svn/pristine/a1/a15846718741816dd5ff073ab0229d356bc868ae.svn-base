package csdc.bean;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;


import org.apache.struts2.json.annotations.JSON;

public class SmdbProjectGranted implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	private String id;//主键id

//	private ProjectFee projectFee;//经费预算明细,直接在正式表中
	//中间表新增字段
	private String smdbGrantedID;//smdb端原id
	private String smasGrantedID;//smas入库新生成的id,便于关联
	private int isAdded;//标志是否已入库
	private Date importedDate;//数据入库时间
	private String importPerson;//数据入库操作人员
	
	private SmdbProjectApplication smdbApplication;// 项目申请，中间表中只保留smdb中的关联关系
	private Set<SmdbProjectVariation> smdbVariation;// 项目变更，中间表中只保留smdb中的关联关系
	private Set<SmdbProjectMidinspection> smdbMidinspection;//项目中检，中间表中只保留smdb中的关联关系
	private Set<SmdbProjectEndinspection> smdbEndinspection;//项目结项，中间表中只保留smdb中的关联关系

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSmdbGrantedID() {
		return smdbGrantedID;
	}
	public void setSmdbGrantedID(String smdbGrantedID) {
		this.smdbGrantedID = smdbGrantedID;
	}
	public String getSmasGrantedID() {
		return smasGrantedID;
	}
	public void setSmasGrantedID(String smasGrantedID) {
		this.smasGrantedID = smasGrantedID;
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
	public SmdbProjectApplication getSmdbApplication() {
		return smdbApplication;
	}
	public void setSmdbApplication(SmdbProjectApplication smdbApplication) {
		this.smdbApplication = smdbApplication;
	}
	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getImportedDate() {
		return importedDate;
	}
	public void setImportedDate(Date importedDate) {
		this.importedDate = importedDate;
	}
	@JSON(serialize = false)
	public Set<SmdbProjectVariation> getSmdbVariation() {
		return smdbVariation;
	}
	public void setSmdbVariation(Set<SmdbProjectVariation> smdbVariation) {
		this.smdbVariation = smdbVariation;
	}

	@JSON(serialize = false)
	public Set<SmdbProjectMidinspection> getSmdbMidinspection() {
		return smdbMidinspection;
	}
	public void setSmdbMidinspection(Set<SmdbProjectMidinspection> smdbMidinspection) {
		this.smdbMidinspection = smdbMidinspection;
	}
	@JSON(serialize = false)
	public Set<SmdbProjectEndinspection> getSmdbEndinspection() {
		return smdbEndinspection;
	}
	public void setSmdbEndinspection(Set<SmdbProjectEndinspection> smdbEndinspection) {
		this.smdbEndinspection = smdbEndinspection;
	}
	
	/**
	 * 添加一个变更
	 */
	public void addSmdbVariation(SmdbProjectVariation smdbVariation) {
		if (this.getSmdbVariation() == null) {
			this.setSmdbVariation(new HashSet<SmdbProjectVariation>());
		}
		this.getSmdbVariation().add(smdbVariation);
		smdbVariation.setSmdbGranted(this);
	}
	/**
	 * 添加项目中检
	 */
	public void addSmdbMidinspection(SmdbProjectMidinspection smdbMidinspection) {
		if (this.getSmdbMidinspection() == null) {
			this.setSmdbMidinspection((new HashSet<SmdbProjectMidinspection>()));
		}
		this.getSmdbMidinspection().add(smdbMidinspection);
		smdbMidinspection.setSmdbGranted(this);
	}
	/**
	 * 添加项目结项
	 */
	public void addSmdbEndinspection(SmdbProjectEndinspection smdbEndinspection) {
		if (this.getSmdbEndinspection() == null) {
			this.setSmdbEndinspection(new HashSet<SmdbProjectEndinspection>());
		}
		this.getSmdbEndinspection().add(smdbEndinspection);
		smdbEndinspection.setSmdbGranted(this);
	}
}
