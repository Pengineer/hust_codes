package edu.hust.service.impl;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;

import edu.hust.dao.PersonDao;
import edu.hust.service.PersonService;
/* ��java������ʹ��@Autowired��@Resourceע�ⷽʽ����װ�䣬������ע��������ǣ�@Autowired Ĭ�ϰ�����װ�䣬
 * @ResourceĬ�ϰ�����װ�䣬���Ҳ���������ƥ���bean�Żᰴ����װ�䡣
 *��1��
 * @Autowiredע���ǰ�����װ����������Ĭ���������Ҫ���������������ڣ��������nullֵ������������required����Ϊfalse��
 * ���������ʹ�ð�����װ�䣬���Խ��@Qualifierע��һ��ʹ�á����£�
    @Autowired  @Qualifier("personDaoBean")
    private PersonDao  personDao;
 *��2��
 * @Resourceע���@Autowiredһ����Ҳ���Ա�ע���ֶλ����Ե�setter�����ϣ�����Ĭ�ϰ�����װ�䡣���ƿ���ͨ��@Resource��
 * name����ָ�������û��ָ��name���ԣ���ע���ע���ֶ��ϣ���Ĭ��ȡ�ֶε�������Ϊbean����Ѱ���������󣬵�ע���ע�����Ե�setter
 * �����ϣ���Ĭ��ȡ��������Ϊbean����Ѱ����������
    @Resource(name=��personDaoBean��)
    private PersonDao  personDao;//�����ֶ���

 * ע�⣺���û��ָ��name���ԣ����Ұ���Ĭ�ϵ�������Ȼ�Ҳ�����������ʱ�� @Resourceע�����˵�������װ�䡣��һ��ָ����name���ԣ�
 *     ��ֻ�ܰ�����װ���ˡ�
 *     
 * ���䣺1��ע��ע�뷽ʽһ�����ڸ������������ϣ�����ȣ���
 *     2��ʵ���о����Ƕ��ַ�ʽ����ʹ�á�
 *     3����@Autowired���Ƽ�ʹ��@Resource����Ϊ@Resource��javaEE�Դ��ģ�����������޹أ�����ʹ�ø����
 */
public class PersonServiceBean3 implements PersonService {
	@Resource
	private PersonDao personDao;  //��service��ע��Dao��Ķ���ͨ��ע��ķ�ʽ��
	
	@Resource(name="group")
	Group group1;
	
//	@Resource(name="group")
//	Group group1;
	
	public void save(){
//		personDao.add();
		group1.group();
	}
}
