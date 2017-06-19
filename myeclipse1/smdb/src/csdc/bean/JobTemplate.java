package csdc.bean;

import org.apache.struts2.json.annotations.JSON;

/**
 * 职位-模板表对应表
 * @author suwb
 *
 */
public class JobTemplate implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private String id;//主键	
	private Job job;//职位id
	private Template template;//模板id

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@JSON(serialize=false)
	public Job getJob() {
		return job;
	}
	public void setJob(Job job) {
		this.job = job;
	}
	@JSON(serialize=false)
	public Template getTemplate() {
		return template;
	}
	public void setTemplate(Template template) {
		this.template = template;
	}
}
