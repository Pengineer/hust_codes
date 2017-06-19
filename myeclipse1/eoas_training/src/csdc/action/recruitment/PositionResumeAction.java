package csdc.action.recruitment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.ActionSupport;

import csdc.bean.AccountPosition;
import csdc.bean.Position;
import csdc.bean.PositionResume;
import csdc.service.IBaseService;
import csdc.service.imp.BaseService;


public class PositionResumeAction extends ActionSupport {
	private IBaseService baseService;


	private Map jsonMap = new HashMap();

	public String toAllApplyList() {
		return SUCCESS;
	}
	
	public String listAllApply() {
		ArrayList<PositionResume> positionResumeList = new ArrayList<PositionResume>();
		try {
			positionResumeList = (ArrayList<PositionResume>) baseService.list(PositionResume.class.getName()+".listAllApply", null);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		String[] item;
		List<Object[]> prList = new ArrayList<Object[]>();
		for(PositionResume pr:positionResumeList) {
			item = new String[7];
			item[0] = pr.getResume().getName();
			item[1] = pr.getResume().getResumeName();
			item[2] = pr.getPosition().getName();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			if(null != pr.getPosition().getReleaseTime()){
				item[3] = sdf.format(pr.getPosition().getReleaseTime());
			}
			if(null != pr.getApplyDate()){
				item[4] = sdf.format(pr.getApplyDate());
			}
			item[5] = pr.getResume().getId();
			item[6] = pr.getId();
			prList.add(item);
		}
		jsonMap.put("aaData",prList);
		return SUCCESS;
	}
	
	public String toAllFavoriteList() {
		return SUCCESS;
	}
	
	public String listAllFavorite() {
		ArrayList<AccountPosition> accountPositionList = new ArrayList<AccountPosition>();
		accountPositionList = (ArrayList<AccountPosition>) baseService.list(AccountPosition.class.getName()+".listAllCollect", null);
		String[] item;
		List<Object[]> prList = new ArrayList<Object[]>();
		for(AccountPosition ap:accountPositionList) {
			item = new String[6];
			item[0] = ap.getAccount().getEmail();
			item[1] = ap.getPosition().getName();
			item[2] = ap.getPosition().getPlace();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
			if(null != ap.getCollectDate()){
				item[3] = sdf.format(ap.getCollectDate());
			}
			item[4] = ap.getId();
			prList.add(item);
		}
		jsonMap.put("aaData",prList);
		return SUCCESS;
	}
	
	public Map getJsonMap() {
		return jsonMap;
	}

	public void setJsonMap(Map jsonMap) {
		this.jsonMap = jsonMap;
	}
	
	public IBaseService getBaseService() {
		return baseService;
	}

	public void setBaseService(IBaseService baseService) {
		this.baseService = baseService;
	}
}