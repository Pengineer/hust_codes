package csdc.service.imp;

import java.util.HashMap;
import java.util.Map;

import csdc.bean.Account;
import csdc.service.IMobileProjectService;
import csdc.tool.bean.AccountType;
import csdc.tool.bean.LoginInfo;
import csdc.tool.info.GlobalInfo;

public class MobileProjectService extends ProjectService implements IMobileProjectService{

	public void getSimpleSearchHqlAndMapOfApp(StringBuffer hql, HashMap parMap, String hqlDiff4Manage, String hqlDiff4Research, Map session, String keyword) {
		LoginInfo loginer = (LoginInfo) session.get(GlobalInfo.LOGINER);
		AccountType accountType = loginer.getCurrentType();
		Account account = loginer.getAccount(); 
		
		if(accountType.equals(AccountType.EXPERT) || accountType.equals(AccountType.TEACHER) || accountType.equals(AccountType.STUDENT)){//研究人员使用
			hql.append(hqlDiff4Research);	
		}else {//管理人员使用
			hql.append(hqlDiff4Manage);
		}
		hql.append(getAppSimpleSearchHQLAdd(accountType));
		//处理查询范围
		session.put("applicationMap", parMap);
		hql.append(applicationInSearch(account));
		parMap = (HashMap) session.get("applicationMap");
	}

	public void getSimpleSearchHqlAndMapOfGra(StringBuffer hql, HashMap parMap, String hqlDiff4Manage, String hqlDiff4Research, Map session, String keyword) {
		LoginInfo loginer = (LoginInfo) session.get(GlobalInfo.LOGINER);
		AccountType accountType = loginer.getCurrentType();
		Account account = loginer.getAccount(); 
		if(accountType.equals(AccountType.EXPERT) || accountType.equals(AccountType.TEACHER) || accountType.equals(AccountType.STUDENT)){//研究人员使用
			hql.append(hqlDiff4Research);	
		}else {//管理人员使用
			hql.append(hqlDiff4Manage);
		}
		//处理查询范围
		session.put("grantedMap", parMap);
		hql.append(this.grantedInSearch(account));
		parMap = (HashMap) session.get("grantedMap");
	}

	public void getSimpleSearchHqlAndMapOfAnn(StringBuffer hql, HashMap parMap, String hqlDiff4Manage, String hqlDiff4Research, Map session, String keyword) {
		LoginInfo loginer = (LoginInfo) session.get(GlobalInfo.LOGINER);
		AccountType accountType = loginer.getCurrentType();
		Account account = loginer.getAccount(); 
		if(accountType.equals(AccountType.EXPERT) || accountType.equals(AccountType.TEACHER) || accountType.equals(AccountType.STUDENT)){//研究人员使用
			hql.append(hqlDiff4Research);	
		}else {//管理人员使用
			hql.append(hqlDiff4Manage);
		}
		hql.append(this.getAnnSimpleSearchHQLAdd(accountType));
		//处理查询范围
		session.put("grantedMap", parMap);
		hql.append(this.grantedInSearch(account));
		parMap = (HashMap) session.get("grantedMap");	
	}
	
	public void getSimpleSearchHqlAndMapOfMid(StringBuffer hql, HashMap parMap, String hqlDiff4Manage, String hqlDiff4Research, Map session, String keyword) {
		LoginInfo loginer = (LoginInfo) session.get(GlobalInfo.LOGINER);
		AccountType accountType = loginer.getCurrentType();
		Account account = loginer.getAccount(); 
		if(accountType.equals(AccountType.EXPERT) || accountType.equals(AccountType.TEACHER) || accountType.equals(AccountType.STUDENT)){//研究人员使用
			hql.append(hqlDiff4Research);	
		}else {//管理人员使用
			hql.append(hqlDiff4Manage);
		}
		hql.append(this.getMidSimpleSearchHQLAdd(accountType));
		//处理查询范围
		session.put("grantedMap", parMap);
		hql.append(this.grantedInSearch(account));
		parMap = (HashMap) session.get("grantedMap");
	}

	public void getSimpleSearchHqlAndMapOfEnd(StringBuffer hql, HashMap parMap, String hqlDiff4Manage, String hqlDiff4Research, Map session, String keyword) {
		LoginInfo loginer = (LoginInfo) session.get(GlobalInfo.LOGINER);
		AccountType accountType = loginer.getCurrentType();
		Account account = loginer.getAccount(); 
		if(accountType.equals(AccountType.EXPERT) || accountType.equals(AccountType.TEACHER) || accountType.equals(AccountType.STUDENT)){//研究人员使用
			hql.append(hqlDiff4Research);	
		}else {//管理人员使用
			hql.append(hqlDiff4Manage);
		}
		hql.append(this.getEndSimpleSearchHQLAdd(accountType));
		//处理查询范围
		session.put("grantedMap", parMap);
		hql.append(this.grantedInSearch(account));
		parMap = (HashMap) session.get("grantedMap");
	}

	public void getSimpleSearchHqlAndMapOfVar(StringBuffer hql, HashMap parMap, String hqlSelect, String hqlFrom, String hqlDiff4Manage, String hqlDiff4Research, Map session, String keyword) {
		LoginInfo loginer = (LoginInfo) session.get(GlobalInfo.LOGINER);
		AccountType accountType = loginer.getCurrentType();
		Account account = loginer.getAccount(); 
		if(accountType.equals(AccountType.EXPERT) || accountType.equals(AccountType.TEACHER) || accountType.equals(AccountType.STUDENT)){//研究人员
			hql.append(this.getVarHql(hqlSelect, hqlFrom + hqlDiff4Research, accountType));
			parMap.put("belongId", this.getBelongIdByAccount(account));
		}else if(accountType.compareWith(AccountType.MINISTRY) == -1){//部级以下管理人员
			hql.append(this.getVarHql(hqlSelect, hqlFrom + hqlDiff4Manage, accountType));
			parMap.put("belongId", loginer.getCurrentBelongUnitId());
		}else{//教育部及系统管理员
			hql.append(hqlSelect + hqlFrom + hqlDiff4Manage);
//			hql.append(this.getVarHql(hqlSelect, hqlFrom + hqlDiff4Manage, accountType));
		}
	}

	public void getAdvSearchHqlAndMapOfApp(StringBuffer hql, HashMap parMap, String hqlDiff4Manage, String hqlDiff4Research, Map session, String projName, String projSubType, String projDirector, String projUniversity) {
		LoginInfo loginer = (LoginInfo) session.get(GlobalInfo.LOGINER);
		AccountType accountType = loginer.getCurrentType();
		Account account = loginer.getAccount();
		if(accountType.equals(AccountType.EXPERT) || accountType.equals(AccountType.TEACHER) || accountType.equals(AccountType.STUDENT)){//研究人员使用
			hql.append(hqlDiff4Research);	
		}else {//管理人员使用
			hql.append(hqlDiff4Manage);
		}
		//处理查询范围
		session.put("applicationMap", parMap);
		hql.append(this.applicationInSearch(account));
		parMap = (HashMap) session.get("applicationMap");
		//高级检索条件
		if(null != projName && !projName.isEmpty()){
			hql.append(" and LOWER(app.name) like :projectName");
			parMap.put("projectName", "%" + projName.toLowerCase() + "%");
		}
		if(null != projSubType && !projSubType.equals("--请选择--") && !projSubType.isEmpty()){
			hql.append(" and LOWER(so.name) like :projectSubtype");
			parMap.put("projectSubtype", "%" + projSubType.toLowerCase() + "%");
		}
		if(null != projDirector && !projDirector.isEmpty()){
			hql.append(" and LOWER(app.applicantName) like :applicant");
			parMap.put("applicant", "%" + projDirector.toLowerCase() + "%");
		}
		if(null != projUniversity && !projUniversity.isEmpty()){
			hql.append(" and LOWER(app.agencyName) like :university");
			parMap.put("university", "%" + projUniversity.toLowerCase() + "%");
		}
		hql.append(" order by app.name ");//默认按照一般名称排序
	}

	public void getAdvSearchHqlAndMapOfGra(StringBuffer hql, HashMap parMap, String hqlDiff4Manage, String hqlDiff4Research, Map session, String projName, String projSubType, String projDirector, String projUniversity) {
		LoginInfo loginer = (LoginInfo) session.get(GlobalInfo.LOGINER);
		AccountType accountType = loginer.getCurrentType();
		Account account = loginer.getAccount();
		if(accountType.equals(AccountType.EXPERT) || accountType.equals(AccountType.TEACHER) || accountType.equals(AccountType.STUDENT)){//研究人员使用
			hql.append(hqlDiff4Research);	
		}else {//管理人员使用
			hql.append(hqlDiff4Manage);
		}
		//处理查询范围
		session.put("grantedMap", parMap);
		hql.append(this.grantedInSearch(account));
		parMap = (HashMap) session.get("grantedMap");
		if(null != projName && !projName.isEmpty()){
			hql.append(" and LOWER(gra.name) like :projectName ");
			parMap.put("projectName", "%" + projName.toLowerCase() + "%");
		}
		if(null != projSubType && !projSubType.equals("--请选择--")){
			hql.append(" and LOWER(so.name) like :projectSubtype");
			parMap.put("projectSubtype", "%" + projSubType.toLowerCase() + "%");
		}
		if(null != projDirector && !projDirector.isEmpty()){
			hql.append(" and LOWER(gra.applicantName) like :applicant ");
			parMap.put("applicant", "%" + projDirector.toLowerCase() + "%");
		}
		if(null != projUniversity && !projUniversity.isEmpty()){
			hql.append(" and LOWER(uni.name) like :university ");
			parMap.put("university", "%" + projUniversity.toLowerCase() + "%");
		}
		hql.append(" order by gra.name");
	}
	
	

	public void getAdvSearchHqlAndMapOfAnn(StringBuffer hql, HashMap parMap, String hqlDiff4Manage, String hqlDiff4Research, Map session, String projName, String projSubType, String projDirector, String projUniversity) {
		LoginInfo loginer = (LoginInfo) session.get(GlobalInfo.LOGINER);
		AccountType accountType = loginer.getCurrentType();
		Account account = loginer.getAccount();
		if(accountType.equals(AccountType.EXPERT) || accountType.equals(AccountType.TEACHER) || accountType.equals(AccountType.STUDENT)){//研究人员使用
			hql.append(hqlDiff4Research);	
		}else {//管理人员使用
			hql.append(hqlDiff4Manage);
		}
		hql.append(this.getAnnSimpleSearchHQLAdd(accountType));
		//处理查询范围
		session.put("grantedMap", parMap);
		hql.append(this.grantedInSearch(account));
		parMap = (HashMap) session.get("grantedMap");
		//高级检索条件
		if(null != projName && !projName.isEmpty()){
			hql.append(" and LOWER(gra.name) like :projectName ");
			parMap.put("projectName", "%" + projName.toLowerCase() + "%");
		}
		if(null != projSubType && !projSubType.equals("--请选择--")){
			hql.append(" and LOWER(so.name) like :projectSubtype");
			parMap.put("projectSubtype", "%" + projSubType.toLowerCase() + "%");
		}
		if(null != projDirector && !projDirector.isEmpty()){
			hql.append(" and LOWER(gra.applicantName) like :applicant ");
			parMap.put("applicant", "%" + projDirector.toLowerCase() + "%");
		}
		if(null != projUniversity && !projUniversity.isEmpty()){
			hql.append(" and LOWER(uni.name) like :university ");
			parMap.put("university", "%" + projUniversity.toLowerCase() + "%");
		}
		//hql.append(" ");
		hql.append(" order by gra.name");
	}
	
	public void getAdvSearchHqlAndMapOfMid(StringBuffer hql, HashMap parMap, String hqlDiff4Manage, String hqlDiff4Research, Map session, String projName, String projSubType, String projDirector, String projUniversity) {
		LoginInfo loginer = (LoginInfo) session.get(GlobalInfo.LOGINER);
		AccountType accountType = loginer.getCurrentType();
		Account account = loginer.getAccount();
		if(accountType.equals(AccountType.EXPERT) || accountType.equals(AccountType.TEACHER) || accountType.equals(AccountType.STUDENT)){//研究人员使用
			hql.append(hqlDiff4Research);	
		}else {//管理人员使用
			hql.append(hqlDiff4Manage);
		}
		hql.append(this.getMidSimpleSearchHQLAdd(accountType));
		//处理查询范围
		session.put("grantedMap", parMap);
		hql.append(this.grantedInSearch(account));
		parMap = (HashMap) session.get("grantedMap");
		if(null != projName && !projName.isEmpty()){
			hql.append(" and LOWER(gra.name) like :projectName ");
			parMap.put("projectName", "%" + projName.toLowerCase() + "%");
		}
		if(null != projSubType && !projSubType.equals("--请选择--")){
			hql.append(" and LOWER(so.name) like :projectSubtype");
			parMap.put("projectSubtype", "%" + projSubType.toLowerCase() + "%");
		}
		if(null != projDirector && !projDirector.isEmpty()){
			hql.append(" and LOWER(gra.applicantName) like :applicant ");
			parMap.put("applicant", "%" + projDirector.toLowerCase() + "%");
		}
		if(null != projUniversity && !projUniversity.isEmpty()){
			hql.append(" and LOWER(uni.name) like :university ");
			parMap.put("university", "%" + projUniversity.toLowerCase() + "%");
		}
		hql.append(" group by app.id, gra.id, gra.name, uni.id, uni.name, gra.applicantId, gra.applicantName, so.name, app.disciplineType, app.year, midi.status, midi.file, midi.id, midi.finalAuditStatus, midi.finalAuditResult, midi.finalAuditDate, midi.finalAuditResult, midi.applicantSubmitDate having midi.applicantSubmitDate = max(all_midi.applicantSubmitDate)");
		hql.append(" order by gra.name");
	}

	public void getAdvSearchHqlAndMapOfEnd(StringBuffer hql, HashMap parMap, String hqlDiff4Manage, String hqlDiff4Research, Map session, String projName, String projSubType, String projDirector, String projUniversity) {
		LoginInfo loginer = (LoginInfo) session.get(GlobalInfo.LOGINER);
		AccountType accountType = loginer.getCurrentType();
		Account account = loginer.getAccount();
		if(accountType.equals(AccountType.EXPERT) || accountType.equals(AccountType.TEACHER) || accountType.equals(AccountType.STUDENT)){//研究人员使用
			hql.append(hqlDiff4Research);	
		}else {//管理人员使用
			hql.append(hqlDiff4Manage);
		}
		hql.append(this.getEndSimpleSearchHQLAdd(accountType));
		//处理查询范围
		session.put("grantedMap", parMap);
		hql.append(this.grantedInSearch(account));
		parMap = (HashMap) session.get("grantedMap");
		//高级检索条件
		if(null != projName && !projName.isEmpty()){
			hql.append(" and LOWER(gra.name) like :projectName ");
			parMap.put("projectName", "%" + projName.toLowerCase() + "%");
		}
		if(null != projSubType && !projSubType.equals("--请选择--")){
			hql.append(" and LOWER(so.name) like :projectSubtype");
			parMap.put("projectSubtype", "%" + projSubType.toLowerCase() + "%");
		}
		if(null != projDirector && !projDirector.isEmpty()){
			hql.append(" and LOWER(gra.applicantName) like :applicant ");
			parMap.put("applicant", "%" + projDirector.toLowerCase() + "%");
		}
		if(null != projUniversity && !projUniversity.isEmpty()){
			hql.append(" and LOWER(uni.name) like :university ");
			parMap.put("university", "%" + projUniversity.toLowerCase() + "%");
		}
		hql.append(" group by app.id, gra.id, gra.name, uni.id, uni.name, gra.applicantId, gra.applicantName, so.name, app.disciplineType, app.year, endi.status, endi.file, endi.id, endi.finalAuditStatus, endi.finalAuditResultEnd, endi.ministryAuditStatus, endi.ministryResultEnd, endi.reviewStatus, endi.reviewResult, endi.finalAuditDate, endi.applicantSubmitDate having endi.applicantSubmitDate = max(all_endi.applicantSubmitDate)");
		hql.append(" order by gra.name");
	}

	public void getAdvSearchHqlAndMapOfVar(StringBuffer hql, HashMap parMap, String hqlSelect, String hqlFrom, String hqlDiff4Manage, String hqlDiff4Research, Map session, String projName, String projSubType, String projDirector, String projUniversity) {
		LoginInfo loginer = (LoginInfo) session.get(GlobalInfo.LOGINER);
		AccountType accountType = loginer.getCurrentType();
		Account account = loginer.getAccount();
		if(accountType.equals(AccountType.EXPERT) || accountType.equals(AccountType.TEACHER) || accountType.equals(AccountType.STUDENT)){//研究人员
			hql.append(this.getVarHql(hqlSelect, hqlFrom + hqlDiff4Research, accountType));
			parMap.put("belongId", this.getBelongIdByAccount(account));
		}else if(accountType.compareWith(AccountType.MINISTRY) == -1){//部级以下管理人员
			hql.append(this.getVarHql(hqlSelect, hqlFrom + hqlDiff4Manage, accountType));
			parMap.put("belongId", loginer.getCurrentBelongUnitId());
		}else{//教育部及系统管理员
			hql.append(hqlSelect + hqlFrom + hqlDiff4Manage);
//			hql.append(this.getVarHql(hqlSelect, hqlFrom + hqlDiff4Manage, accountType));
		}
		if(null != projName && !projName.isEmpty()){
			hql.append(" and LOWER(gra.name) like :projectName ");
			parMap.put("projectName", "%" + projName.toLowerCase() + "%");
		}
		if(null != projSubType && !projSubType.equals("--请选择--")){
			hql.append(" and LOWER(so.name) like :projectSubtype");
			parMap.put("projectSubtype", "%" + projSubType.toLowerCase() + "%");
		}
		if(null != projDirector && !projDirector.isEmpty()){
			hql.append(" and LOWER(gra.applicantName) like :applicant ");
			parMap.put("applicant", "%" + projDirector.toLowerCase() + "%");
		}
		if(null != projUniversity && !projUniversity.isEmpty()){
			hql.append(" and LOWER(uni.name) like :university ");
			parMap.put("university", "%" + projUniversity.toLowerCase() + "%");
		}
		hql.append(" group by app.id, gra.id, gra.name, gra.applicantId, gra.applicantName, uni.id, uni.name, so.name, app.disciplineType, app.year, vari.status, vari.file, vari.id, vari.finalAuditStatus,vari.finalAuditResult , vari.finalAuditDate, vari.applicantSubmitDate having vari.applicantSubmitDate = max(all_vari.applicantSubmitDate)");
		hql.append(" order by gra.name");
	}

	@Override
	public void getTeacherSearchHqlAndMapOfGra(StringBuffer hql,HashMap parMap, String hqlDiff4Manage,Map session, String projName, String projDirector,
			String projUniversity, String projYear, String projDes) {
		// TODO Auto-generated method stub
		LoginInfo loginer = (LoginInfo) session.get(GlobalInfo.LOGINER);
		AccountType accountType = loginer.getCurrentType();
		Account account = loginer.getAccount();
		//if(accountType.equals(AccountType.EXPERT) || accountType.equals(AccountType.TEACHER) || accountType.equals(AccountType.STUDENT)){//研究人员使用
			hql.append(hqlDiff4Manage);	
		//}else {//管理人员使用
			//hql.append(hqlDiff4Manage);
		//}
		//处理查询范围
		session.put("grantedMap", parMap);
		//hql.append(this.grantedInSearch(account));
		parMap = (HashMap) session.get("grantedMap");
		if(null != projName && !projName.isEmpty()){
			hql.append(" and LOWER(gra.name) like :projectName ");
			parMap.put("projectName", "%" + projName.toLowerCase() + "%");
		}

		if(null != projDirector && !projDirector.isEmpty()){
			hql.append(" and LOWER(gra.applicantName) like :applicant ");
			parMap.put("applicant", "%" + projDirector.toLowerCase() + "%");
		}
		if(null != projUniversity && !projUniversity.isEmpty()){
			hql.append(" and LOWER(uni.name) like :university ");
			parMap.put("university", "%" + projUniversity.toLowerCase() + "%");
		}
		if(null != projYear && !projYear.isEmpty()){
			hql.append(" and LOWER(app.year) like :year ");
			parMap.put("year", Integer.parseInt(projYear));
		}
		if(null !=projDes&& !projDes.isEmpty()){
			hql.append(" and LOWER(app.disciplineType) like :decipline ");
			parMap.put("decipline", "%" + projDes.toLowerCase() + "%");
		}
		hql.append(" order by gra.name");
	}
	
}
