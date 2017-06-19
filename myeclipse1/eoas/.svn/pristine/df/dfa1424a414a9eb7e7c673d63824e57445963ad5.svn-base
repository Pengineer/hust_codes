package eoas.service.imp;

import eoas.bean.Experience;
import eoas.dao.ExperienceDao;
import eoas.service.IExperienceService;

public class ExperienceService extends BaseService implements IExperienceService {

	public ExperienceDao experienceDao;


	public int add(Experience experience) {

		return this.experienceDao.insert(experience);
	}



	public ExperienceDao getExperienceDao() {
		return experienceDao;
	}


	public void setExperienceDao(ExperienceDao experienceDao) {
		this.experienceDao = experienceDao;
	}


}
