package edu.hust.action;

/**����ע�룺ΪAction������ע��ֵ
 *         
 *struts2ΪAction�е������ṩ������ע�빦�ܣ���struts2�������ļ��У����ǿ��Ժܷ����ΪAction�е�����ע��ֵ��
 *
 *ע�⣺���Ա����ṩsetter�����������ϣ����JSPҳ���л�ȡ���Ե�ֵ��Ӧ�ṩgetter������
 */
public class DependencyInjectionAction {
	private String savepath;

	public String getSavepath() {
		return savepath;
	}

	public void setSavepath(String savepath) {
		this.savepath = savepath;
	}
	
	public String execute(){
		
		return "success";	
	}
}
