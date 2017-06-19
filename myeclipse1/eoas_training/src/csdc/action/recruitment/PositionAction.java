package csdc.action.recruitment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.support.StaticApplicationContext;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.sun.org.apache.bcel.internal.generic.NEW;

import csdc.bean.Account;
import csdc.bean.AccountPosition;
import csdc.bean.Position;
import csdc.bean.PositionResume;
import csdc.bean.Resume;
import csdc.service.IAccountPositionService;
import csdc.service.IBaseService;
import csdc.service.IPositionService;

import csdc.service.imp.AccountService;
import csdc.service.imp.PositionResumeService;


public class PositionAction extends ActionSupport {
	private String place, name, releaseTime, number, responsibility, requirement;
	private IBaseService baseService;
	
	private IAccountPositionService accountPositionService;
	private Position position;
	private String positionId;
	private String resumeId;
	private Resume resume;//简历
	private PositionResumeService positionResumeService;
	private PositionResume positionResume = new PositionResume();
	private AccountPosition accountPosition = new AccountPosition();
	private Date applyDate;//申请时间
	private int admitStatus;//应聘状态(0未录取,1笔试,2面试,3录取)
	private int positionStatus;//职位状态(0截止,1发布中)
	private int applyStatus;//申请状态(0未申请,1已申请)
	private Date collectDate;//收藏时间
	String message;
	Map jsonMap = new HashMap();
	//进入职位申请页面
	public String toMyApplyList() {
		return SUCCESS;
	}
	//获取职位申请数据的列表
	public String listMyApplyPosition() {
		Map map = ActionContext.getContext().getSession();
		Account account = (Account) map.get("account");
		map.put("accountId", account.getId());
	    ArrayList<Resume> resumeList = new ArrayList <Resume> ();
	    resumeList = (ArrayList<Resume>) baseService.list(Resume.class, map);
	    
	    ArrayList<Position> positionList = new ArrayList <Position> ();
	    if(resumeList.size() != 0) {
		    map.put("resumeId", resumeList.get(0).getId());
		    positionList =  (ArrayList<Position>) baseService.list(Position.class.getName()+".listMyApply", map);
	    }
	    
	    List<Object[]> pList = new ArrayList<Object[]>();
		String[] item;
		for(Resume r:resumeList ) {
			for(Position p : positionList){
				map.put("positionId", p.getId());
				map.put("resumeId", r.getId());
				PositionResume pr;
				pr = (PositionResume) (baseService.list(PositionResume.class, map)).get(0);
				if(pr != null) {
					item = new String[6];
					item[0] = p.getName();
					item[1] = p.getPlace();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					if(null != p.getReleaseTime()){
						item[2] = sdf.format(p.getReleaseTime());
					}
					item[3] = pr.getPositionStatus() + "";
					switch(Integer.parseInt(item[3])) {
						case 0:
							item[3] = "截止";
							break;
						case 1:
							item[3] = "发布中";
							break;
					}
					item[4] = pr.getAdmitStatus() + "";
					switch(Integer.parseInt(item[4])) {
						case -1:
							item[4] = "申请审核中";
							break;
						case 0:
							item[4] = "筛选未通过";
							break;
						case 1:
							item[4] = "笔试";
							break;
						case 2:
							item[4] = "面试";
							break;
						case 3:
							item[4] = "录取";
							break;
					}
					item[5] = p.getId();
					pList.add(item);
				}		
			}
		}
		jsonMap.put("aaData", pList); 
	    return SUCCESS; 
	}
	//进入职位收藏页面
	public String toMyFavoriteList() {
		return SUCCESS;
	}
	//我的职位收藏
	public String listMyFavoritePosition() {
		Map map = ActionContext.getContext().getSession();
		Account account = (Account) map.get("account");
		map.put("accountId", account.getId());
	    ArrayList<AccountPosition> accountPositionList = new ArrayList <AccountPosition> ();    
	    List<Object[]> apList = new ArrayList<Object[]>();
	    accountPositionList =  (ArrayList<AccountPosition>) baseService.list(AccountPosition.class.getName()+".listMyCollect", map);	
		String[] item;
		for(AccountPosition ap : accountPositionList) {
			item = new String[5];
			item[0] = ap.getPosition().getName();
			item[1] = ap.getPosition().getPlace();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			if(null != ap.getPosition().getReleaseTime()){
				item[2] = sdf.format(ap.getPosition().getReleaseTime());
			}
			Date expireDate = ap.getPosition().getExpireDate();
			Date nowDate = new Date();
			if((expireDate).after(nowDate)) {
				item[3] = "发布中";
			} else {
				item[3] = "已截止";
			}
			item[4] = ap.getId();
			apList.add(item);
		}
		jsonMap.put("aaData", apList); 
	    return SUCCESS; 
	}
	//进入所有的职位页面
	public String toList() {
		return SUCCESS;
	}
	
	public String list() {
	    ArrayList<Position> positionList = new ArrayList <Position> ();    
	    List<Object[]> pList = new ArrayList<Object[]>();
		positionList =  (ArrayList<Position>) baseService.list(Position.class, null);
		String[] item;
		for(Position p : positionList){
			item = new String[4];
			item[0] = p.getName();
			item[1] = p.getPlace();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			if(null != p.getReleaseTime()){
				item[2] = sdf.format(p.getReleaseTime());
			}

			item[3] = p.getId();
			pList.add(item);
		}
		jsonMap.put("aaData", pList); 
	    return SUCCESS;  
	}
	//进入职位发布页面
	public String toAddPosition() {
		return SUCCESS;
	}
	
	public String addPosition() {
		position.setName(position.getName());
		position.setType(position.getType());
		position.setPlace(position.getPlace());
		position.setNumber(position.getNumber());
		position.setResponsibility(position.getResponsibility());
		position.setRequirement(position.getRequirement());
		position.setReleaseTime(new Date());
		baseService.add(position);
		return SUCCESS;
	}
	//职位删除
	public String delete(){
		/*List<PositionResume> prList = positionResumeService.selectPositionResumeByPosition(positionId);*/
		Map map = new HashMap();
		map.put("positionId", positionId);
		List<PositionResume> prList = baseService.list(PositionResume.class, map);
		for(PositionResume pr: prList) {
			System.out.println(pr.getId());
			baseService.delete(PositionResume.class, pr.getId());
		}
		baseService.delete(Position.class, positionId);

		return SUCCESS;
	}
	//查看职位详情
	public String view() {
		position = (Position)baseService.load(Position.class, positionId);
		return SUCCESS;
	}
	//进行职位申请
	public String apply() {
		Map map = new HashMap();
		map.put("positionId", positionId);
		map.put("resumeId", resumeId);
		position = (Position)baseService.load(Position.class, positionId);
		resume = (Resume)baseService.load(Resume.class, resumeId);
		positionResume = (PositionResume)baseService.list(PositionResume.class, map);
		if(positionResume != null) {
			jsonMap.put("message", positionResume.getApplyStatus());
		} else {
	    	PositionResume positionResume = new PositionResume();
	    	positionResume.setPosition(position);
	    	positionResume.setResume(resume);
	    	positionResume.setApplyDate(new Date());
	    	positionResume.setApplyStatus(1);
			baseService.add(positionResume);
		}
		return SUCCESS;
	}
	//进行职位收藏
	public String collect() {
		position = (Position)baseService.load(Position.class,positionId);
		Map map = ActionContext.getContext().getSession();
		Account account = (Account)map.get("account");
		map.put("accountId", account.getId());
		map.put("positionId", positionId);
		try {
			accountPosition = (AccountPosition)(baseService.list(AccountPosition.class, map).get(0));
		} catch (Exception e) {
			e.printStackTrace();
		}
	    if(accountPosition != null) {
	    	jsonMap.put("message", accountPosition.getCollectStatus());
	    } else {
	    	AccountPosition accountPosition = new AccountPosition();
	    	accountPosition.setAccount(account);
	    	accountPosition.setPosition(position);
	    	accountPosition.setCollectDate(new Date());
	    	accountPosition.setCollectStatus(1);
	    	baseService.add(accountPosition);
	    }
		return SUCCESS;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getReleaseTime() {
		return releaseTime;
	}

	public void setReleaseTime(String releaseTime) {
		this.releaseTime = releaseTime;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getResponsibility() {
		return responsibility;
	}

	public void setResponsibility(String responsibility) {
		this.responsibility = responsibility;
	}

	public String getRequirement() {
		return requirement;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public void setRequirement(String requirement) {
		this.requirement = requirement;
	}
	
	public Map getJsonMap() {
		return jsonMap;
	}

	public void setJsonMap(Map jsonMap) {
		this.jsonMap = jsonMap;
	}
	
	public String getPositionId() {
		return positionId;
	}

	public void setPositionId(String positionId) {
		this.positionId = positionId;
	}

	public String getResumeId() {
		return resumeId;
	}

	public void setResumeId(String resumeId) {
		this.resumeId = resumeId;
	}

	public Resume getResume() {
		return resume;
	}

	public void setResume(Resume resume) {
		this.resume = resume;
	}

	public PositionResumeService getPositionResumeService() {
		return positionResumeService;
	}

	public void setPositionResumeService(PositionResumeService positionResumeService) {
		this.positionResumeService = positionResumeService;
	}

	public PositionResume getPositionResume() {
		return positionResume;
	}

	public void setPositionResume(PositionResume positionResume) {
		this.positionResume = positionResume;
	}

	public Date getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}

	public int getAdmitStatus() {
		return admitStatus;
	}

	public void setAdmitStatus(int admitStatus) {
		this.admitStatus = admitStatus;
	}

	public int getPositionStatus() {
		return positionStatus;
	}

	public void setPositionStatus(int positionStatus) {
		this.positionStatus = positionStatus;
	}

	public int getApplyStatus() {
		return applyStatus;
	}

	public void setApplyStatus(int applyStatus) {
		this.applyStatus = applyStatus;
	}

	public Date getCollectDate() {
		return collectDate;
	}

	public void setCollectDate(Date collectDate) {
		this.collectDate = collectDate;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public IAccountPositionService getAccountPositionService() {
		return accountPositionService;
	}
	public void setAccountPositionService(
			IAccountPositionService accountPositionService) {
		this.accountPositionService = accountPositionService;
	}
	public AccountPosition getAccountPosition() {
		return accountPosition;
	}
	public void setAccountPosition(AccountPosition accountPosition) {
		this.accountPosition = accountPosition;
	}
	public IBaseService getBaseService() {
		return baseService;
	}
	public void setBaseService(IBaseService baseService) {
		this.baseService = baseService;
	}
}