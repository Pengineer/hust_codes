package eoas.action.recruitment;

import com.opensymphony.xwork2.ActionSupport;

import eoas.service.IRecordService;

public class RecordAction extends ActionSupport {
	
	private IRecordService  recordService;
	

	public String listRecord() {
		
		return SUCCESS;	
	}
	
	public String toAddRecord() {
		 return SUCCESS;
	}
	
	public IRecordService getRecordService() {
		return recordService;
	}

	public void setRecordService(IRecordService recordService) {
		this.recordService = recordService;
	}

}