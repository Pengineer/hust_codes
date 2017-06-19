package csdc.action.papers;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ActionSupport;

import csdc.action.BaseAction;
import csdc.model.SolicitPapers;
import csdc.service.imp.UploadService;
import csdc.tool.ApplicationContainer;
import csdc.tool.FileTool;
import csdc.tool.bean.FileRecord;

import csdc.service.ISolicitPapersService;
import csdc.service.IUploadService;
import freemarker.core.ReturnInstruction.Return;


@Component
@Scope(value = "prototype")
public class SolicitPapersAction extends BaseAction{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5907082392532922869L;

	private String flag;
	
	private SolicitPapers solicitPapers;
	
	@Autowired
	private IUploadService uploadService;
	
	@Autowired
	private ISolicitPapersService  solicitPapersService ;
	
	private String entityId;
	

	


	
	public String toAdd() {
		
		if(flag.equals("0")){
			
			return "forum";
		} else if(flag.equals("1")){
			
			return "topic";
		}else {
			
			return INPUT;
		}
	}

	public String add() {
		String fileGroupId = "solicitPapersUpload";
		int type = Integer.valueOf(solicitPapers.getType());
		String fileSavePath = null;
        if (type == 1) {
        	fileSavePath = (String) ApplicationContainer.sc.getAttribute("FileUploadPath1");//设置论坛附件的路径
		}else {
			fileSavePath = (String) ApplicationContainer.sc.getAttribute("FileUploadPath2");//设置课题附件的路径
		}
		//String fileSavePath = (String) ApplicationContainer.sc.getAttribute("FileUploadPath");//设置附件的路径
		for (FileRecord fileRecord : uploadService.getGroupFiles(fileGroupId)) {
			String newFilePath = FileTool.getAvailableFilename(fileSavePath, fileRecord.getOriginal());
			getSolicitPapers().setFile(newFilePath);
			fileRecord.setDest(new File(ApplicationContainer.sc.getRealPath(getSolicitPapers().getFile())));//将文件移至新的位置(不立刻执行，而在uploadService.flush时再执行)
		}
		uploadService.flush(fileGroupId);
		entityId = solicitPapersService.addDocument(getSolicitPapers());
		jsonMap.put("tag", "1");
		
		return SUCCESS;
	}
	
	
	


	
	

	public IUploadService getUploadService() {
		return uploadService;
	}
	public void setUploadService(IUploadService uploadService) {
		this.uploadService = uploadService;
	}
	public String getEntityId() {
		return entityId;
	}
	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public String toList() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String list() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Object[] simpleSearchCondition() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String simpleSearch() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Object[] advSearchCondition() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String advSearch() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String changePageSize() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String toPage() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String toEntity() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String sort() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String pageName() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String pageBufferId() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String[] sortColumn() {
		// TODO Auto-generated method stub
		return null;
	}


	
	@Override
	public String dateFormat() {
		// TODO Auto-generated method stub
		return null;
	}


	public SolicitPapers getSolicitPapers() {
		return solicitPapers;
	}


	public void setSolicitPapers(SolicitPapers solicitPapers) {
		this.solicitPapers = solicitPapers;
	}














	@Override
	public String delete() {
		// TODO Auto-generated method stub
		return null;
	}








	@Override
	public String modify() {
		// TODO Auto-generated method stub
		return null;
	}






	@Override
	public String toModify() {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public String view() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String toView() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
	
}
