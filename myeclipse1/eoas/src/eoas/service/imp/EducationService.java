package eoas.service.imp;

import eoas.bean.Education;
import eoas.dao.EducationDao;
import eoas.service.IEducationService;


public class EducationService extends BaseService implements IEducationService  {
	
	private EducationDao educationDao;
	
	public EducationDao getEducationDao() {
		return educationDao;
	}

	public void setEducationDao(EducationDao educationDao) {
		this.educationDao = educationDao;
	}

	public int add(Education education){
		return this.educationDao.insert(education);
	}
}

