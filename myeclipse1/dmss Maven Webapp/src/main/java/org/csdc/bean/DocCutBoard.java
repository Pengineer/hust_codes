package org.csdc.bean;

/**
 * 文档剪切板
 * @author jintf
 * @date 2014-6-15
 */
public class DocCutBoard {
	private String id; //文档ID
	private String curCId; //文档所在分类目录ID
	private boolean isCut; //true:剪切,false:黏贴
	
	
	public DocCutBoard(String id, String curCId, boolean isCut) {
		super();
		this.id = id;
		this.curCId = curCId;
		this.isCut = isCut;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCurCId() {
		return curCId;
	}
	public void setCurCId(String curCId) {
		this.curCId = curCId;
	}
	public boolean isCut() {
		return isCut;
	}
	public void setCut(boolean isCut) {
		this.isCut = isCut;
	}
	
	
}
