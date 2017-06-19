package csdc.bean.common;

/**
 * 用于项目列表显示评审专家时，封装一个
 * ID和名字的对象
 * @author 龚凡
 * @version 1.0 2010.06.23
 */
public class ExpertLink {

	private String id;// 专家ID
	private String name;// 专家姓名
	private String uname;// 专家所在高校名称
	private String title;// 专家专业职称
	private int isInner;// 是否内部专家0是，1不是
	private int isReviewer;// 是否参与评审0不参与，1参与

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getIsInner() {
		return isInner;
	}
	public void setIsInner(int isInner) {
		this.isInner = isInner;
	}
	public int getIsReviewer() {
		return isReviewer;
	}
	public void setIsReviewer(int isReviewer) {
		this.isReviewer = isReviewer;
	}

}