package csdc.bean;


public class FieldConfig implements java.io.Serializable {

	private static final long serialVersionUID = 5677443935062851128L;
	private String id;// ID
	private String xmlField;//XML中字段名称
	private String field;//转储中间表中字段名称
	private String type;//数据类型（申请、中检、变更、结项）
	private String source;//XML数据来源（SINOSS）
	private String tableName;//smdb中间表表名（T_SINOSS_APPLICATION）
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getXmlField() {
		return xmlField;
	}
	public void setXmlField(String xmlField) {
		this.xmlField = xmlField;
	}
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
}
