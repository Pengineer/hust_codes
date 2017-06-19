package csdc.service.imp;


import java.util.Date;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import csdc.model.SolicitPapers;
import csdc.service.ISolicitPapersService;


@Service
@Transactional
public class SolicitPapersService extends BaseService implements ISolicitPapersService {

	@Override
	public SolicitPapers getDocumentByName(String documentName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String addDocument(SolicitPapers document) {
		// TODO Auto-generated method stub
		
		document.setCreateDate(new Date());
	    return dao.add(document).toString();
	}

	@Override
	public String modifyDocument(SolicitPapers document) {
		// TODO Auto-generated method stub
		return null;
	}

	

	
	

}
