package csdc.tool.execution.importer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import csdc.bean.Agency;
import csdc.bean.GeneralGranted;
import csdc.bean.GeneralMember;
import csdc.bean.GeneralVariation;
import csdc.bean.InstpMember;
import csdc.dao.IHibernateBaseDao;
import csdc.tool.execution.Execution;
import csdc.tool.execution.finder.UniversityFinder;

/**
 * 
 * 
 */
@Component
public class FixOtherInfoImporter extends Execution {
	
	@Autowired
	private IHibernateBaseDao dao;
	
	@Autowired
	private UniversityFinder universityFinder;
	
	private Map<String, GeneralMember> nameMemberMap1;
	private Map<String, InstpMember> nameMemberMap2;
	private Map<String, String> leftKey;
	private Map<String, String> rightMap;	
	
	private List<GeneralVariation> errorChangeMemberList;
	private List<GeneralVariation> errorModifyMemberList;
	private List<GeneralVariation> mutableOtherInfoList;
	
	
	@Override
	public void work() throws Throwable{
		fixGeneralInfo();//修正一般项目
		fixInstpInfo();//修正基地项目
	}
	

	private void fixGeneralInfo() throws CloneNotSupportedException {
		//有完整的成员信息（形如变更项目成员#）
		List<GeneralVariation> gvs1 = dao.query("from GeneralVariation gv where gv.projectType = 'general' and gv.otherInfo like '变更项目成员：#%'");
		int currentRow = 1;
		boolean isChangeMember  = false;
		for (GeneralVariation gv : gvs1) {
			System.out.println("当前处理的————————" + currentRow + "/" + gvs1.size());
			GeneralGranted gg = gv.getGranted();
			List<GeneralVariation> generalVariations = dao.query("from GeneralVariation gv where gv.granted.id = ?",gg.getId());
			isChangeMember = false;
			for (GeneralVariation generalVariation : generalVariations) {
				if (generalVariation.getChangeMember() == 1) {
					isChangeMember = true;
					break;
				}
			}
			if (isChangeMember) {
				if (errorChangeMemberList == null) {
					errorChangeMemberList = new ArrayList<GeneralVariation>();
				}
				errorChangeMemberList.add(gv);
			}else{
				//仅有一个“变更为其他”的变更数据，直接为该项目添加一条“变更项目成员”的变更数据，并根据“变更为其他”的审核信息设置该条变更的审核信息。
//				GeneralVariation gv1 = gv;
				String memberInfo = gv.getOtherInfo().replace("变更项目成员：#", "");
				String[] memebers = memberInfo.split("; ");
				List<GeneralMember> gms = dao.query("from GeneralMember gm where gm.application.id = ?", gg.getApplication().getId());//该项目下所有的成员
				GeneralMember director = (GeneralMember) dao.queryUnique("from GeneralMember gm where gm.isDirector = 1 and gm.application.id = ?", gg.getApplication().getId());//该项目的负责人
				boolean containsDirectorOrNot = false;
				int containsDirectorIndex = -1;
				int newGroupNumber = director.getGroupNumber() + 1;
				for (int i = 0; i < memebers.length; i++) {
					if (memebers[i].equals(director.getMemberName())) {
						containsDirectorOrNot = true;
						containsDirectorIndex = i;
						break;
					}
				}
				//判断新成员中是否包含项目负责人
				//包含
				if (containsDirectorOrNot) {
					GeneralMember newDirector = director.clone();
					newDirector.setGroupNumber(newGroupNumber);
					dao.add(newDirector);
					int index = 2;
					for (int i = 0; i < memebers.length; i++) {
						if (i == containsDirectorIndex) {
							continue;
						}else {
							GeneralMember newGeneralMemeber = addOrCopy(memebers[i],gms);
							newGeneralMemeber.setGroupNumber(newGroupNumber);
							newGeneralMemeber.setMemberSn(index);
							dao.add(newGeneralMemeber);
							index++;
						}
					}
				}else{
				//不包含
					int index = 1;
					//如果没有，则取出之前的负责人，放入新的组里
					for (int i = 0; i < memebers.length; i++) {
						GeneralMember newGeneralMemeber = addOrCopy(memebers[i],gms);
						newGeneralMemeber.setGroupNumber(newGroupNumber);
						newGeneralMemeber.setMemberSn(index);
						dao.add(newGeneralMemeber);
						index++;
					}
				}
				gv.setChangeMember(1);
				gv.setOldMemberGroupNumber(newGroupNumber - 1);//此处应该为？
				gv.setNewMemberGroupNumber(newGroupNumber);//此处应该为？
				gv.setOther(0);
				char[] charArray = gv.getFinalAuditResultDetail().toCharArray();
				if (charArray[19] == 1 ) {
					charArray[0] = 1;
					charArray[19] = 0;
					//修改立项中的组编号
					gg.setMemberGroupNumber(newGroupNumber);
				}
				gv.setFinalAuditResultDetail(charArray.toString());
				gg.setMemberGroupNumber(newGroupNumber);//此处应该为？
				gv.setOtherInfo(null);
				dao.addOrModify(gg);
				dao.addOrModify(gv);
			}
			currentRow++;
		}
		
		//增加、减少项目成员（形如：变更项目成员：任玲>潘翠云）
		List<GeneralVariation> gvs2 = dao.query("from GeneralVariation gv where gv.projectType = 'general' and gv.otherInfo like '%>%'");
//		List<GeneralVariation> gvs2 = dao.query("from GeneralVariation gv where gv.projectType = 'general' and gv.otherInfo = '变更项目成员：>刘秋瑞'");
		currentRow = 1;
		for (GeneralVariation gv : gvs2) {
			System.out.println("当前处理的————————" + currentRow + "/" + gvs2.size() + "—————————(增加或删除)");
			GeneralGranted gg = gv.getGranted();
			List<GeneralVariation> generalVariations = dao.query("from GeneralVariation gv where gv.granted.id = ?",gg.getId());
			isChangeMember = false;
			int numberOfOtherInfo = 0;
			for (GeneralVariation generalVariation : generalVariations) {
				if (generalVariation.getOtherInfo() != null && generalVariation.getOtherInfo().contains(">")) {
					numberOfOtherInfo++;
				}
			}
			for (GeneralVariation generalVariation : generalVariations) {
				if (generalVariation.getChangeMember() == 1) {
					isChangeMember = true;
					break;
				}
			}
			if (numberOfOtherInfo >= 2) {
				if (mutableOtherInfoList == null) {
					mutableOtherInfoList = new ArrayList<GeneralVariation>();
				}
				mutableOtherInfoList.add(gv);
			}else if (isChangeMember) {
				if (errorModifyMemberList == null) {
					errorModifyMemberList = new ArrayList<GeneralVariation>();
				}
				errorModifyMemberList.add(gv);
			}else{
				List<GeneralMember> gms = dao.query("from GeneralMember gm where gm.application.id = ? order by gm.memberSn asc", gg.getApplication().getId());//该项目下所有的成员
				List<GeneralMember> newGmsGeneralMembers = new ArrayList<GeneralMember>();
				for (GeneralMember generalMember : gms) {
					newGmsGeneralMembers.add(generalMember.clone());
				}
				GeneralMember director = (GeneralMember) dao.queryUnique("from GeneralMember gm where gm.isDirector = 1 and gm.application.id = ?", gg.getApplication().getId());//该项目的负责人
				Map<String, GeneralMember> nameMemeberMap = new HashMap<String, GeneralMember>();
				for (GeneralMember generalMember : newGmsGeneralMembers) {
//					GeneralMember newGeneralMember = generalMember.clone();
					nameMemeberMap.put(generalMember.getMemberName(), generalMember);
				}
				GeneralVariation generalVariation = gv;
				String memberInfoString = generalVariation.getOtherInfo().replace("变更项目成员：", "");
				String[] otherInfo = memberInfoString.split(">");
				String toAddString = otherInfo[1];//“>”号左侧的为减少成员，右侧的为新增成员
				if (toAddString.length() > 0) {
					String[] toAddGeneralMembers = toAddString.split("; ");
					Map<String, String> memberNameAndAgencynameMap = new HashMap<String, String>();
					for (int i = 0; i < toAddGeneralMembers.length; i++) {
						String memberNameAndAgencyname = toAddGeneralMembers[i];
						if (memberNameAndAgencyname.contains("（")) {
							memberNameAndAgencynameMap.put(memberNameAndAgencyname.substring(0,memberNameAndAgencyname.indexOf("（")),memberNameAndAgencyname.substring(memberNameAndAgencyname.indexOf("（") + 1,memberNameAndAgencyname.indexOf("）")));
						}else {
							memberNameAndAgencynameMap.put(memberNameAndAgencyname,"");
						}
					}
					Set<String> memberNameAndAgencynameKeySet = memberNameAndAgencynameMap.keySet();
					for (String memberNameAndAgencynameKey : memberNameAndAgencynameKeySet) {
						if (!nameMemeberMap.containsKey(memberNameAndAgencynameKey)) {
							GeneralMember newGeneralMember = new GeneralMember();
							newGeneralMember.setMemberName(memberNameAndAgencynameKey);
							newGeneralMember.setApplication(newGmsGeneralMembers.get(0).getApplication());
							newGeneralMember.setMemberType(1);
							//添加Agency信息
							String agencyName = memberNameAndAgencynameMap.get(memberNameAndAgencynameKey);
							Agency agency = universityFinder.getUnivByName(agencyName);
							if (agency != null) {
								newGeneralMember.setAgencyName(agencyName);
								newGeneralMember.setUniversity(agency);
							}else{
								newGeneralMember.setAgencyName(agencyName);
							}
							newGmsGeneralMembers.add(newGeneralMember);
						}
					}
				}
				
				String toDeleteString = otherInfo[0];//“>”号左侧的为减少成员，右侧的为新增成员
				//是根据加入成员之后来删除需要删除的成员？还是在最初的基础上，删除需要删除的成员
				if (toDeleteString.length() > 0) {
					String[] toDeleteGeneralMembers = toDeleteString.split("; ");
					nameMemeberMap = new HashMap<String, GeneralMember>();
					for (GeneralMember generalMember : newGmsGeneralMembers) {
						nameMemeberMap.put(generalMember.getMemberName(), generalMember);
					}
					for (int i = 0; i < toDeleteGeneralMembers.length; i++) {
						if (nameMemeberMap.containsKey(toDeleteGeneralMembers[i])) {
							newGmsGeneralMembers.remove(nameMemeberMap.get(toDeleteGeneralMembers[i]));
						}
					}
				}
				int newGroupNumber = director.getGroupNumber() + 1;
				//将新的成员groupNumber、MemberSN修改
				int index = 1;
				for (GeneralMember generalMember : newGmsGeneralMembers) {
					generalMember.setGroupNumber(newGroupNumber);
					generalMember.setMemberSn(index);
					if (index == 1) {
						generalMember.setIsDirector(1);//因为最初是按照memberSn的顺序插入到gms中的，所以第一个有两种情况：1.本身就是director;2.本身不是director,但是director被删除了，所以选择为director
					}
					index++;
					dao.add(generalMember);
				}

				gv.setChangeMember(1);
				gv.setOldMemberGroupNumber(newGroupNumber - 1);//此处应该为？
				gv.setNewMemberGroupNumber(newGroupNumber);//此处应该为？
				gv.setOther(0);
				char[] charArray = gv.getFinalAuditResultDetail().toCharArray();
				if (charArray[19] == 1 ) {
					charArray[0] = 1;
					charArray[19] = 0;
					//修改立项中的组编号
					gg.setMemberGroupNumber(newGroupNumber);
				}
				gv.setFinalAuditResultDetail(charArray.toString());
				gg.setMemberGroupNumber(newGroupNumber);//此处应该为？
				gv.setOtherInfo(null);
				dao.addOrModify(gg);
				dao.addOrModify(gv);
			}
			currentRow++;
		}
		System.out.println("-------------------------变更项目成员'#'号------------------------");
		for (GeneralVariation generalVariation : errorChangeMemberList) {
			System.out.println(generalVariation.getOtherInfo());
		}
		System.out.println("-------------------------变更项目成员'>'号------------------------");
		for (GeneralVariation generalVariation : errorModifyMemberList) {
			System.out.println(generalVariation.getOtherInfo());
		}
		System.out.println("-------------------------多条记录均包含'>'符号------------------------");
		for (GeneralVariation generalVariation : mutableOtherInfoList) {
			System.out.println(generalVariation.getOtherInfo());
		}
		System.out.println("ok");
		System.out.println("hello");
		System.out.println("heihei");
	}
	
	public GeneralMember addOrCopy(String newMemberName, List<GeneralMember> oldMembers) throws CloneNotSupportedException{
		GeneralMember newGeneralMember = null;
		boolean containsOrNot = false;
		for (GeneralMember generalMember : oldMembers) {
			if (generalMember.getMemberName().equals(newMemberName)) {
				newGeneralMember = generalMember.clone();
			}
		}
		if (!containsOrNot) {
			newGeneralMember = new GeneralMember();
			newGeneralMember.setMemberName(newMemberName);
			newGeneralMember.setApplication(oldMembers.get(0).getApplication());
			newGeneralMember.setMemberType(1);
		}
		return newGeneralMember;
	}
		
	private void fixInstpInfo() {
		
	}
	public static void main(String[] args) {
//		String aString = ">wang";
//		String[] aStrings = aString.split(">");
//		System.out.println(aStrings.length);
//		System.out.println(aStrings[0]);
//		System.out.println(aStrings[1]);
//		if (aStrings[0].equals("")) {
//			System.out.println("heihei ");
//		}
		String memberNameAndAgencyname = "钱国富（广东外语外贸大学）";
		System.out.println(memberNameAndAgencyname.substring(0,memberNameAndAgencyname.indexOf("（")));
		System.out.println(memberNameAndAgencyname.substring(memberNameAndAgencyname.indexOf("（") + 1,memberNameAndAgencyname.indexOf("）")));
//		memberNameAndAgencynameMap.put(memberNameAndAgencyname.substring(0,memberNameAndAgencyname.indexOf("（")),memberNameAndAgencyname.substring(memberNameAndAgencyname.indexOf("（"),memberNameAndAgencyname.indexOf("）")));
	}
}
		
		
		
		
		
			
			
		
			
		
		
			
			
			
			
			
			
	
	

