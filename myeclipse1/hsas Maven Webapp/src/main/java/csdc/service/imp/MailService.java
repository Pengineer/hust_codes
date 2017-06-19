package csdc.service.imp;

import java.io.File;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import csdc.service.IMailService;
import csdc.tool.bean.Mail;

@Service
@Transactional
public class MailService extends BaseService implements IMailService {

	@Override
	public String addMail(Mail mail) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mail viewMail(String mailId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteMail(List<String> mailIds) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<String> generateEmailList(List recieverType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String renameFile(String id, File file) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List getFiles(String path) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String uploadToDmss(Mail mail) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}


}
