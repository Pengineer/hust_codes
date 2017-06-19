package csdc.tool;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import csdc.bean.University;

/**
 * 评审人项目匹配器 Ver3
 * 用三级学科匹配，默认评审专家和评审对象的学科代码都已用7或5或3位数字表示
 * 有多学科的评专家人或评审对象的代号用'; '分开
 * @author xuhan
 *
 */
@SuppressWarnings("unchecked")
public class ProjectReviewerMatcher {
	
	private List<Object> el;	//待匹配评审专家列表
	private List<Object> pl;	//待匹配评审对象列表
	private List<Object> existingPrList;	//已有评审信息列表
	private int revBackupLimit;		//评审专家的最多评审数(备用)
	private int revOfficalLimit;	//评审专家的最多评审数(正式)
	private int revOfficalBottom;	//评审人若参与正审，最少要正式评审的数量
	private int supRevNum;			//每个评审对象需要的部属高校评审人数
	private int ordRevNum;			//每个评审对象需要的一般高校评审人数
	private int revNum;				//每个评审对象需要评审人总数量(包括备用)

	private Set<String> supUniv = new HashSet<String>();	//部属高校代码集合
	private List<RFeat> rList = new ArrayList<RFeat>();		//拆分后的评审人学科
	private List<PFeat> pList = new ArrayList<PFeat>();		//拆分后的项目学科
	private List<University> uList;	//高校列表
	private HashMap<String, Interval> iMap = new HashMap<String, Interval>();	//学科号->区间 映射
	private HashMap<String, Integer> ePos = new HashMap<String, Integer>();	//专家ID->el下标映射
	private HashMap<String, Integer> pPos = new HashMap<String, Integer>();	//项目ID->pl下标映射
	private	int rBackupBurden[];	//每个评审人已经被分配了多少评审对象(备选)
	private	int rOfficalBurden[];	//每个评审人已经被分配了多少评审对象(正式)
	HashMap<String, Integer> discCnt;	//每个学科有多少专家
	HashSet<String> failSet = new HashSet<String>();	//记录查找专家失败的{学科,限制级别}集合,加速后续的查找
	HashMap<String, Set> revSet = new HashMap<String, Set>();	//每个学科的已有多少专家已选
	private	int stage;	//表示当前匹配的阶段数(分两段: 0、1)
	
	private	HashSet[] selectedRev;			//某评审对象已选定的评审人
	private	HashSet[] selectedUniversity;	//某评审对象已选定的评审人所属高校集合
	private	Integer supCnt[];				//某评审对象已选定的评审人所属高校中部属高校数量
	private	Integer ordCnt[];				//某评审对象已选定的评审人所属高校中地方高校数量
	
	private List<Object> prList = new ArrayList<Object>();	//评审信息列表，用于写数据库
	
	private Class reviewerClass;
	private Class revieweeClass;
	
	private Method reviewerGetIdMethod; 
	private Method revieweeGetIdMethod; 
	private Method reviewerGetDisciplineMethod; 
	private Method revieweeGetDisciplineMethod; 
	private Method reviewerGetUniversityCodeMethod;
	private Method revieweeGetUniversityCodeMethod; 
	private Method reviewerGetNameMethod; 
	private Method revieweeGetMembersMethod; 
	private Method revieweeGetWarningReviewer;
	private Method revieweeSetWarningReviewer;

		
	/**
	 * 匹配主方法，调用前需要将el、pl、RevLimit、RevNum初始化
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public void match() throws IllegalArgumentException, IllegalAccessException, InvocationTargetException{
		//Date begin = new Date();
		
		initMethods();
		
		RFeat rFeat;
		PFeat pFeat;
		Interval now = new Interval();
	
		
		/**
		 * Step - 1:
		 * 初始化部属高校代码集合，将部属高校代码放入集合supUniv
		 */
		for (University u : uList) {
			if ("360".equals(u.getFounderCode())){
				supUniv.add(u.getCode());
			}
		}
		

		/**
		 * Step - 2:
		 * 初始化专家信息，包括：
		 * 1. 建立专家编号 -> 专家在el列表中下标的映射
		 * 2. 将专家按学科拆分，并存入rList列表
		 * 3. 将rList按学科编号字典序排序
		 * 4. 分别按一二三及学科建立 学科编号 -> 专家信息区间(rList中的，包含起止下标) 映射
		 */
		for (int i = 0; i < el.size(); i++) {
			Object expert = el.get(i);
			String id = (String) reviewerGetIdMethod.invoke(expert);
			ePos.put(id, i);
			String disc = (String) reviewerGetDisciplineMethod.invoke(expert);
			if (disc == null){
				continue;
			}
			String metaDisc[] = disc.split("\\D");
			for (String st : metaDisc) {
				if (!st.isEmpty()){
					rFeat = new RFeat();
					rFeat.id = i;
					while (st.length() < 7){
						st += "0";
					}
					rFeat.disc = st;
					rList.add(rFeat);
				}
			}
		}
		rFeat = new RFeat();
		rFeat.disc = "zzzzzzz";
		rList.add(rFeat);
		
		Collections.sort(rList, new Comparator() {  
			public int compare(Object o1, Object o2) {
				RFeat a = (RFeat)o1, b = (RFeat)o2;
				if (!a.disc.equals(b.disc)){
					return a.disc.compareTo(b.disc) < 0 ? -1 : 1;
				} else {
					String d1 = null, d2 = null;
					try {
						d1 = (String) reviewerGetDisciplineMethod.invoke(el.get(a.id));
						d2 = (String) reviewerGetDisciplineMethod.invoke(el.get(b.id));
					} catch (Exception e) {
						e.printStackTrace();
					}
					return d1.length() > d2.length() ? -1 : 1;
				}
			}
		});
		
		//初始化评审人一级学科
		now.disc = "000";
		for (int i = 0; i < rList.size(); i++) {
			rFeat = rList.get(i);

			String disc = rFeat.disc.substring(0, 3);
			String ldisc = now.disc.substring(0, 3);
			if (!disc.equals(ldisc)){
				if (!"000".equals(ldisc)){
					now.right = i;
					iMap.put(ldisc, now);
				}
				now = new Interval();
				now.left = now.cur = i;
				now.disc = disc;
			}
		}

		//初始化评审人二级学科
		now.disc = "00000";
		for (int i = 0; i < rList.size(); i++) {
			rFeat = rList.get(i);

			String disc = rFeat.disc.substring(0, 5);
			String ldisc = now.disc.substring(0, 5);
			if (!disc.equals(ldisc)){
				if (!"00000".equals(ldisc)){
					now.right = i;
					iMap.put(ldisc, now);
				}
				now = new Interval();
				now.left = now.cur = i;
				now.disc = disc;
			}
		}

		//初始化评审人三级学科
		now.disc = "0000000";
		for (int i = 0; i < rList.size(); i++) {
			rFeat = rList.get(i);

			String disc = rFeat.disc.substring(0, 7);
			String ldisc = now.disc.substring(0, 7);
			if (!disc.equals(ldisc)){
				if (!"0000000".equals(ldisc)){
					now.right = i;
					iMap.put(ldisc, now);
				}
				now = new Interval();
				now.left = now.cur = i;
				now.disc = disc;
			}
		}
		

		/**
		 * Step - 3:
		 * 读入原有评审信息，并初始化相关的专家和评审对象信息，包括：
		 * 1. 专家已正式和备选的评审数
		 * 2. 每个学科还有多少空余专家评审人次
		 * 3. 将已有评审对象的专家放入集合revSet，用于后续挑选时优先选择
		 */
		initExistingProjectReviewer();


		/**
		 * Step - 4:
		 * 初始化评审对象信息，包括：
		 * 1. 建立评审对象编号 -> 项目在pl列表中下标的映射
		 * 2. 每个评审对象的已评信息
		 * 3. 将评审对象按学科拆分，放入pList列表中
		 * 4. 将评审对象信息，按学科专家稀缺度排序（稀缺的排在前面）
		 */
		for (int i = 0; i < pl.size(); i++) {
			String id = (String) revieweeGetIdMethod.invoke(pl.get(i));
			pPos.put(id, i);
			Object project = pl.get(i);
			revieweeSetWarningReviewer.invoke(project, new Object[]{""});
			String disc = (String) revieweeGetDisciplineMethod.invoke(project);
			if (disc == null){
				continue;
			}
			String metaDisc[] = disc.replaceAll("[\\s\\S]*\\(", "").split("\\D");
			for (String st : metaDisc) {
				if (!st.isEmpty()){
					pFeat = new PFeat();
					pFeat.id = i;
					pFeat.disc = st;
					pFeat.oDisc = st;
					pFeat.constraintLevel = 3;
					pList.add(pFeat);
				}
			}
		}

		
		/**
		 * Step - 5:
		 * 第一次匹配：
		 * 每轮每个评审对象挑选一个专家，直到所有评审对象都找不到专家为止
		 */
		//Date init = new Date();
		prList.clear();
		boolean find;
		int round = 0;
		stage = 0;
		do {
			round++;
			find = false;
			for (int i = 0; i < pList.size(); i++){
				//System.out.println("Round: " + round + "\t\t" + "Project: " + i);
				if (findReviewer(pList.get(i))){
					find = true;
				}
			}
		} while (find);
		
		
		/**
		 * Step - 6:
		 * 第一次匹配结束后，删掉正审评审对象少于rOfficalBottom的专家的评审信息，并将这些评审信息对应的评审对象的学科都只保留一级学科信息
		 * 剩余评审信息的priority字段向前顺延，
		 */
		HashSet<String> subRevBottom = new HashSet<String>();	//正式参评数未超过revOfficalBottom的专家编号集合
		for (int i = 0; i < el.size(); i++){
			if (rOfficalBurden[i] < revOfficalBottom){
				subRevBottom.add((String) reviewerGetIdMethod.invoke(el.get(i)));
			}
		}
		HashSet<Integer> subPrjBottom = new HashSet<Integer>();	//正式参评数未超过revOfficalBottom的评审对象下标集合
		for (Object pr : prList) {
			Object[] obj = (Object[]) pr;
			if ((Integer)obj[2] != 1 && subPrjBottom.contains((String)obj[0])){
				subPrjBottom.add(pPos.get((String)obj[1]));
			}
		}
		for (PFeat pFeat1 : pList) {
			pFeat1.constraintLevel = 3;
			if (subPrjBottom.contains(pFeat1.id)){
				pFeat1.oDisc = pFeat1.disc = pFeat1.oDisc.substring(0, 3);
			}
		}
		initExistingProjectReviewer();

		
		/**
		 * Step - 7:
		 * 第二次匹配，每轮每个评审对象挑选一个专家，直到所有评审对象都找不到专家为止
		 */
		prList.clear();
		round = 0;
		stage = 1;
		do {
			round++;
			find = false;
			for (int i = 0; i < pList.size(); i++){
				System.out.println("Round: " + round + "\t\t" + "Project: " + i);
				if (findReviewer(pList.get(i))){
					find = true;
				}
			}
		} while (find);


		/**
		 * Step - 8:
		 * 更新评审对象警告信息
		 */
		if (revieweeGetWarningReviewer != null && revieweeSetWarningReviewer != null){
			for (int i = 0; i < pList.size(); i++) {
				PFeat pf = pList.get(i);
				String wr = (String) revieweeGetWarningReviewer.invoke(pl.get(pf.id));
				if (wr == null){
					wr = "";
				}
				if ((supCnt[pf.id] > supRevNum && ordCnt[pf.id] < ordRevNum || ordCnt[pf.id] > ordRevNum && supCnt[pf.id] < supRevNum) && !wr.contains("个评审人部属地方")){
					wr += (wr.isEmpty() ? "" : "; ") + "突破前" + (supRevNum+ordRevNum) + "个评审人部属地方"+supRevNum+":"+ordRevNum+"限制";
				}
				if (selectedUniversity[pf.id].size() < supRevNum + ordRevNum && selectedUniversity[pf.id].size() < supCnt[pf.id] + ordCnt[pf.id] && !wr.contains("高校不同限制")){
					wr += (wr.isEmpty() ? "" : "; ") + "突破评审人高校不同限制";
				}
				revieweeSetWarningReviewer.invoke(pl.get(pf.id), new Object[]{wr});
			}
		}
		
		
		/**
		 * Step - 9:
		 * 执行数据的增加、更新操作，包括：
		 * 1. 插入评审信息
		 * 2. 更新评审对象警告信息
		 */
		//Date insert = new Date();
		

		//System.out.println("Initia time: " + (init.getTime() - begin.getTime()) + " ms");
		//System.out.println("Match  time: " + (insert.getTime() - init.getTime()) + " ms");
		//System.out.println("Insert time: " + (new Date().getTime() - insert.getTime()) + " ms");
	}
	
	private void initMethods() {
		try {
			reviewerGetIdMethod = reviewerClass.getMethod("getId", new Class[]{});
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		try {
			revieweeGetIdMethod = revieweeClass.getMethod("getId", new Class[]{});
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		try {
			reviewerGetDisciplineMethod = reviewerClass.getMethod("getDiscipline", new Class[]{});
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		try {
			revieweeGetDisciplineMethod = revieweeClass.getMethod("getDiscipline", new Class[]{});
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		try {
			reviewerGetNameMethod = reviewerClass.getMethod("getName", new Class[]{});
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		try {
			revieweeGetMembersMethod = revieweeClass.getMethod("getMembers", new Class[]{});
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		try {
			revieweeGetWarningReviewer = revieweeClass.getMethod("getWarningReviewer", new Class[]{});
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		try {
			revieweeSetWarningReviewer = revieweeClass.getMethod("setWarningReviewer", new Class[]{String.class});
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		try {
			reviewerGetUniversityCodeMethod = reviewerClass.getMethod("getUniversityCode", new Class[]{});
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		try {
			revieweeGetUniversityCodeMethod = revieweeClass.getMethod("getUniversityCode", new Class[]{});
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 初始化已有的评审信息
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	private void initExistingProjectReviewer() throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {

		rBackupBurden = new int[el.size() + 10];
		rOfficalBurden = new int[el.size() + 10];

		for (Object pr : existingPrList) {
			Object[] obj = (Object[]) pr;
			Integer rIdx = ePos.get((String)obj[0]);
			if (rIdx == null){
				continue;
			}
			if ((Integer)obj[3] <= supRevNum + ordRevNum){
				rOfficalBurden[rIdx]++;
			} else {
				rBackupBurden[rIdx]++;
			}
		}
		
		discCnt = new HashMap<String, Integer>();
		for (RFeat rf : rList) {
			Integer cnt = discCnt.containsKey(rf.disc) ? discCnt.get(rf.disc) + (revOfficalLimit - rOfficalBurden[rf.id]) : (revOfficalLimit - rOfficalBurden[rf.id]);
			discCnt.put(rf.disc, cnt);
		}

		Collections.sort(pList, new Comparator() {  
			public int compare(Object o1, Object o2) {
				PFeat a = (PFeat)o1, b = (PFeat)o2;
				Integer ca = discCnt.get(a.disc);
				Integer cb = discCnt.get(b.disc);
				if (ca == null) ca = 0;
				if (cb == null) cb = 0;
				return (a.disc.length() > b.disc.length() || a.disc.length() == b.disc.length() && ca < cb) ? -1 : 1;
			}
		});
		
		revSet.clear();
		for (Object pr : existingPrList) {
			Object[] obj = (Object[]) pr;
//			System.out.println(obj[0] + " " + obj[1] + " " + obj[2] + " " + obj[3]);
			Integer rIdx = ePos.get((String)obj[0]);
			if (rIdx == null){
				continue;
			}
			
			Object exp = el.get(rIdx);
			String fullDisc = (String) reviewerGetDisciplineMethod.invoke(exp);
			String disc[] = fullDisc == null ? null : fullDisc.split("\\D+");
			for (String st : disc) {
				while (st.length() >= 3){
					if (!revSet.containsKey(st)){
						revSet.put(st, new TreeSet<Integer>(new Comparator<Integer>() {
							public int compare(Integer o1, Integer o2) {
								int a = rOfficalBurden[o1], b = rOfficalBurden[o2];
								if (o1.equals(o2)){
									return 0;
								} else if (a == b){
									return o1 < o2 ? -1 : 1;
								} else if (a < revOfficalBottom && b < revOfficalBottom){
									return a > b ? -1 : 1;
								} else {
									return a < b ? -1 : 1;
								}

							};
						}));
					}
					int rIdx1 = ePos.get((String) reviewerGetIdMethod.invoke(exp));
					revSet.get(st).remove(rIdx1);
					if (rOfficalBurden[rIdx1] < revOfficalLimit){
						revSet.get(st).add(rIdx1);
					}
					st = st.substring(0, st.length() - 2);
				}
			}
		}
		
		selectedRev = new HashSet[pl.size()];			//某评审对象已选定的评审人
		selectedUniversity = new HashSet[pl.size()];	//某评审对象已选定的评审人所属高校集合
		supCnt = new Integer[pl.size()];				//某评审对象已选定的评审人所属高校中部属数量
		ordCnt = new Integer[pl.size()];				//某评审对象已选定的评审人所属高校中普通数量
		int prIdx = 0;
		for (int i = 0; i < pl.size(); i++) {
			Object project = pl.get(i);
			selectedRev[i] = new HashSet<String>();
			selectedUniversity[i] = new HashSet<String>();
			supCnt[i] = 0;
			ordCnt[i] = 0;
			String id = (String) revieweeGetIdMethod.invoke(project);
			
			while (prIdx < existingPrList.size() && ((String) ((Object[])existingPrList.get(prIdx))[1]).compareTo(id) < 0){
				prIdx++;
			}
			while (prIdx < existingPrList.size() && ((String) ((Object[])existingPrList.get(prIdx))[1]).equals(id)){
				Object[] pr = (Object[]) existingPrList.get(prIdx);
				selectedRev[i].add(ePos.get((String)pr[0]));
				if (reviewerGetUniversityCodeMethod != null && ePos.get((String)pr[0]) != null){
					Object expert = el.get(ePos.get((String)pr[0]));
					selectedUniversity[i].add((String)reviewerGetUniversityCodeMethod.invoke(expert));
					if (supUniv.contains((String)reviewerGetUniversityCodeMethod.invoke(expert))){
						supCnt[i]++;
					} else {
						ordCnt[i]++;
					}
				}
				prIdx++;
			}
		}
	}
	
	
	/**
	 * 为项目匹配评审人
	 * @param project 待匹配的项目
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	private boolean findReviewer(PFeat pFeat) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		//如果该项目已经选足了专家
		if (selectedRev[pFeat.id].size() >= revNum){
			return false;
		}

		//如果相同学科、相同限制级已经失败过
		if (failSet.contains(pFeat.disc + "," + pFeat.constraintLevel)){
			return false;
		}
		
		//如果该学科已经有专家参选，优先选这些人
		if (revSet.containsKey(pFeat.disc) && !revSet.get(pFeat.disc).isEmpty()){
			Set set = revSet.get(pFeat.disc);
			boolean someoneBelowBottom = false;
			for (Iterator iterator = set.iterator(); iterator.hasNext();) {
				int rIdx = (Integer) iterator.next();
				if (rOfficalBurden[rIdx] < revOfficalBottom){
					someoneBelowBottom = true;
				}
				if (matchPR(pFeat, rIdx)){
					return true;
				}
			}
			if (stage == 0 && someoneBelowBottom){
				compromise(pFeat);
				return false;
			}
		}
		
		//在已参选的专家里没有找到符合条件的，则遍历这个学科所有专家，寻找符合条件的专家
		Interval itv = iMap.get(pFeat.disc);
		if (itv == null){
			compromise(pFeat);
			return false;
		}
		for (itv.cur = itv.left; itv.cur < itv.right; itv.cur++){
			RFeat rFeat = rList.get(itv.cur);
			if (matchPR(pFeat, rFeat.id)){
				return true;
			}
		}
		failSet.add(pFeat.disc + "," + pFeat.constraintLevel);

		//挑选失败，折衷匹配规则，下一轮再匹配
		compromise(pFeat);
		return false;
	}
	
	/**
	 * 判断某个评审对象和某个专家之间是否能够建立评审关系，并处理之
	 * @param pFeat 评审对象信息
	 * @param rIdx 专家在el中的下标
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	private boolean matchPR(PFeat pFeat, int rIdx) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		if (rOfficalBurden[rIdx] >= revOfficalLimit && rBackupBurden[rIdx] >= revBackupLimit){
			return false;
		}
		
		Object expert = el.get(rIdx);
		Object project = pl.get(pFeat.id);
		
		String uCode1 = "", uCode2 = "";
		boolean isSupUniv = false;

		if (reviewerGetUniversityCodeMethod != null && revieweeGetUniversityCodeMethod != null){
			uCode1 = (String) reviewerGetUniversityCodeMethod.invoke(expert);
			uCode2 = (String) revieweeGetUniversityCodeMethod.invoke(project);
			if (uCode1.equals(uCode2)){
				return false;
			}

			//判断是否允许突破高校不同限制
			if (pFeat.constraintLevel > 1){
				if (selectedUniversity[pFeat.id].contains(uCode1)){
					return false;
				}
			}

			//判断是否允许突破部属地方3:2限制
			isSupUniv = supUniv.contains(uCode1);
			if (pFeat.constraintLevel == 1 || pFeat.constraintLevel == 3){
				if (isSupUniv && supCnt[pFeat.id] >= supRevNum && ordCnt[pFeat.id] < ordRevNum || !isSupUniv && ordCnt[pFeat.id] >= ordRevNum && supCnt[pFeat.id] < supRevNum){
					return false;
				}
			}
		}
		
		if (selectedRev[pFeat.id].contains(rIdx)){
			return false;
		}
		
		if (reviewerGetNameMethod != null && revieweeGetMembersMethod != null){
			String members = (String) revieweeGetMembersMethod.invoke(project);
			String name = (String) reviewerGetNameMethod.invoke(expert);
			if (members != null && members.replaceAll("\\s+", "").contains(name)){
				return false;
			}
		}


		//新建一个评审信息
		Object[] obj = new Object[4];
		obj[0] = reviewerGetIdMethod.invoke(expert);
		obj[1] = revieweeGetIdMethod.invoke(project);
		obj[2] = 0;
		obj[3] = selectedRev[pFeat.id].size() + 1;
		
		
		//是正审专家
		if ((Integer)obj[3] <= supRevNum + ordRevNum && rOfficalBurden[rIdx] < revOfficalLimit){
			String fullDisc = (String) reviewerGetDisciplineMethod.invoke(el.get(rIdx));
			String disc[] = fullDisc == null ? null : fullDisc.split("\\D+");
			for (String st : disc) {
				while (st.length() >= 3){
					if (!revSet.containsKey(st)){
						revSet.put(st, new TreeSet<Integer>(new Comparator<Integer>() {
							public int compare(Integer o1, Integer o2) {
								int a = rOfficalBurden[o1], b = rOfficalBurden[o2];
								if (o1.equals(o2)){
									return 0;
								} else if (a == b){
									return o1 < o2 ? -1 : 1;
								} else if (a < revOfficalBottom && b < revOfficalBottom){
									return a > b ? -1 : 1;
								} else {
									return a < b ? -1 : 1;
								}

							};
						}));
					}
					revSet.get(st).remove(rIdx);
					st = st.substring(0, st.length() - 2);
				}
			}
			rOfficalBurden[rIdx]++;
			if (rOfficalBurden[rIdx] < revOfficalLimit){
				for (String st : disc) {
					while (st.length() >= 3){
						revSet.get(st).add(rIdx);
						st = st.substring(0, st.length() - 2);
					}
				}
			}
		} else if ((Integer)obj[3] > supRevNum + ordRevNum && rBackupBurden[rIdx] < revBackupLimit){
			//是备审专家
			rBackupBurden[rIdx]++;
		} else {
			return false;
		}

		if (reviewerGetUniversityCodeMethod != null && revieweeGetUniversityCodeMethod != null){
			//更新评审对象已选的专家信息
			selectedUniversity[pFeat.id].add(uCode1);
			if (isSupUniv){
				supCnt[pFeat.id]++;
			} else {
				ordCnt[pFeat.id]++;
			}
		}
		selectedRev[pFeat.id].add(rIdx);

		prList.add(obj);
		return true;
	}
	
	/**
	 * 折衷规则
	 * @param pFeat
	 */
	private void compromise(PFeat pFeat){
		if (pFeat.disc.length() >= 5){
			pFeat.disc = pFeat.disc.substring(0, pFeat.disc.length() - 2);
		} else {
			pFeat.disc = pFeat.oDisc;
			--pFeat.constraintLevel;
		}
	}
	
	public List<Object> getEl() {
		return el;
	}
	public void setEl(List<Object> el) {
		this.el = el;
	}
	public List<Object> getPl() {
		return pl;
	}
	public void setPl(List<Object> pl) {
		this.pl = pl;
	}
	public int getRevBackupLimit() {
		return revBackupLimit;
	}
	public void setRevBackupLimit(int revBackupLimit) {
		this.revBackupLimit = revBackupLimit;
	}
	public int getRevOfficalLimit() {
		return revOfficalLimit;
	}
	public void setRevOfficalLimit(int revOfficalLimit) {
		this.revOfficalLimit = revOfficalLimit;
	}
	public int[] getROfficalBurden() {
		return rOfficalBurden;
	}
	public void setROfficalBurden(int[] officalBurden) {
		rOfficalBurden = officalBurden;
	}
	public int getSupRevNum() {
		return supRevNum;
	}
	public void setSupRevNum(int supRevNum) {
		this.supRevNum = supRevNum;
	}
	public int getOrdRevNum() {
		return ordRevNum;
	}
	public void setOrdRevNum(int ordRevNum) {
		this.ordRevNum = ordRevNum;
	}
	public int getRevNum() {
		return revNum;
	}
	public void setRevNum(int revNum) {
		this.revNum = revNum;
	}
	public List<RFeat> getRList() {
		return rList;
	}
	public void setRList(List<RFeat> list) {
		rList = list;
	}
	public int getRevOfficalBottom() {
		return revOfficalBottom;
	}
	public void setRevOfficalBottom(int revOfficalBottom) {
		this.revOfficalBottom = revOfficalBottom;
	}
	public Class getReviewerClass() {
		return reviewerClass;
	}
	public void setReviewerClass(Class reviewerClass) {
		this.reviewerClass = reviewerClass;
	}
	public Class getRevieweeClass() {
		return revieweeClass;
	}
	public void setRevieweeClass(Class revieweeClass) {
		this.revieweeClass = revieweeClass;
	}
	public List<Object> getExistingPrList() {
		return existingPrList;
	}
	public void setExistingPrList(List<Object> existingPrList) {
		this.existingPrList = existingPrList;
	}
	public List<Object> getPrList() {
		return prList;
	}
	public void setPrList(List<Object> prList) {
		this.prList = prList;
	}
	public List<University> getUList() {
		return uList;
	}
	public void setUList(List<University> list) {
		uList = list;
	}



	/**
	 * 将学科技能拆分后的评审人
	 * @author 徐涵
	 */
	class RFeat{
		public int id;	//所属评审人(对应el中的下标)
		public String disc; //学科代码
	}

	/**
	 * 将学科技能拆分后的评审对象
	 * @author 徐涵
	 */
	class PFeat{
		public int id;	//所属项目(对应pl中的下标)
		public int constraintLevel;	
		//限制等级		3:2			 高校不同
		//   3				遵守				 遵守
		//   2										   遵守
		//   1				遵守						
		//   0					
		public String oDisc; //原始学科代码
		public String disc; //学科代码
	}

	/**
	 * 排序后，能胜任某个学科的评审人的区间
	 * @author 徐涵
	 */
	class Interval{
		public String disc; //学科代码
		public int left;	//排序后区间左端点
		public int right;	//排序后区间右端点+1
		public int cur;		//当前下标
	}
}
