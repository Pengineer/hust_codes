package eoas.service.imp;

import eoas.bean.Account;
import eoas.bean.Resume;
import eoas.dao.AccountDao;
import eoas.dao.ResumeDao;
import eoas.service.IResumeService;

public class ResumeService implements IResumeService  {
	
	private ResumeDao resumeDao;
	
	public int add(Resume resume) {
		return this.resumeDao.insert(resume);
		
	}

	public ResumeDao getResumeDao() {
		return resumeDao;
	}

	public void setResumeDao(ResumeDao resumeDao) {
		this.resumeDao = resumeDao;
	}

}