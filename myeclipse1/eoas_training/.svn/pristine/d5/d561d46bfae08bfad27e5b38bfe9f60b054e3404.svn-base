package csdc.action.recruitment;

import com.opensymphony.xwork2.ActionSupport;
import csdc.bean.Account;
import csdc.bean.Position;
import csdc.bean.PositionResume;
import csdc.bean.Record;
import csdc.bean.Resume;
import csdc.service.IBaseService;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecordAction extends ActionSupport {
	private Resume resume = new Resume();
	private Account account = new Account();
	private PositionResume positionResume = new PositionResume();
	private Position position = new Position();
	private Record record = new Record();
	private String positionResumeId;
	private String recordId;
	private IBaseService baseService;
	private Map jsonMap = new HashMap();

	public String toList() {
		return SUCCESS;
	}
	
	public String list() {
		ArrayList<Record> recordList = new ArrayList<Record>();	
		recordList = (ArrayList<Record>) baseService.list(Record.class, null);
		String[] item;
		List<Object> rList = new ArrayList<Object>();
		for(Record r:recordList) {
			item = new String[6];
			positionResume = (PositionResume)baseService.load(PositionResume.class, r.getPositionResume().getId());
			resume = (Resume)baseService.load(Resume.class,positionResume.getResume().getId());
			position = (Position)baseService.load(Position.class,positionResume.getPosition().getId());
			item[0]=resume.getName();
			item[1]=resume.getResumeName();
			item[2]=position.getName();
			item[3]=r.getWrittenScore();
			item[4]=r.getId();
			item[5]=resume.getId();
			rList.add(item);
		}	
		jsonMap.put("aaData", rList); 
	    return SUCCESS; 	
	}
	
	public String toAdd() {
		positionResume = (PositionResume)baseService.load(PositionResume.class,positionResumeId);
		resume = (Resume)baseService.load(Resume.class,positionResume.getResume().getId());
		account = (Account)baseService.load(Account.class,resume.getAccount().getId());
		position = (Position)baseService.load(Position.class,positionResume.getPosition().getId());
		return SUCCESS;
	}
	
	public String add() {
		record.setDressup(record.getDressup());
		record.setCommunication(record.getCommunication());
		record.setAnalysis(record.getAnalysis());
		record.setFlexibility(record.getFlexibility());
		record.setRelationships(record.getRelationships());
		record.setResponsibility(record.getResponsibility());
		record.setAntipressure(record.getAntipressure());
		record.setExecution(record.getExecution());
		record.setSpecialty(record.getSpecialty());
		record.setWillingness(record.getWillingness());
		record.setCharacter(record.getCharacter());
		record.setWrittenScore(record.getWrittenScore());
		record.setNote(record.getNote());
		record.setDate(new Date());
		positionResume = (PositionResume)baseService.load(PositionResume.class,positionResumeId);
		record.setPositionResume(positionResume);
		baseService.add(record);
		return SUCCESS;
	}
	
	public String view() {
		record = (Record)baseService.load(Record.class,recordId);
		return SUCCESS;
	}
	
	public Record getRecord() {
		return record;
	}

	public void setRecord(Record record) {
		this.record = record;
	}



	public IBaseService getBaseService() {
		return baseService;
	}

	public void setBaseService(IBaseService baseService) {
		this.baseService = baseService;
	}

	
	public Resume getResume() {
		return resume;
	}

	public void setResume(Resume resume) {
		this.resume = resume;
	}

	public PositionResume getPositionResume() {
		return positionResume;
	}

	public void setPositionResume(PositionResume positionResume) {
		this.positionResume = positionResume;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public String getPositionResumeId() {
		return positionResumeId;
	}

	public void setPositionResumeId(String positionResumeId) {
		this.positionResumeId = positionResumeId;
	}
	
	public Map getJsonMap() {
		return jsonMap;
	}

	public void setJsonMap(Map jsonMap) {
		this.jsonMap = jsonMap;
	}

	public String getRecordId() {
		return recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}
	
	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}
}