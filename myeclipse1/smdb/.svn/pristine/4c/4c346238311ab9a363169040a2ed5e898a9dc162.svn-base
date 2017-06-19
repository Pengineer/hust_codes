package csdc.service;

import java.io.File;
import java.util.List;

import csdc.bean.Mail;

public interface IMailService extends IBaseService {

	/**
	 * 添加邮件
	 * @param mail新增邮件对象
	 * @return mailId
	 */
	public String addMail(Mail mail) throws Exception;

	/**
	 * 查找邮件，包括外键创建者
	 * @param mailId
	 * @return mail邮件对象
	 */
	public Mail viewMail(String mailId);

	/**
	 * 批量删除邮件
	 * @param mailIds待删除的邮件ID集合
	 */
	public void deleteMail(List<String> mailIds);


	/**
	 * 获取接收者邮箱列表
	 * @param id待发送的邮件ID集合
	 */
	@SuppressWarnings("unchecked")
	public List<String> generateEmailList(List recieverType);
	
	/**
	 * 对附件进行改名
	 * @param id：邮件ID
	 * @param file：附件
	 * @author XN
	 */
	public String renameFile(String id, File file);
	public List getFiles(String path);
	/**
	 * 将邮件附件上传到DMSS
	 * @param notice
	 * @return 返回dfsId
	 * @throws Exception
	 */
	public String uploadToDmss(Mail mail) throws Exception;
}
