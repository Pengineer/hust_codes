package csdc.action.person;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;

import csdc.action.BaseAction;
import csdc.bean.Friend;
import csdc.bean.InBox;
import csdc.bean.Person;
import csdc.tool.bean.LoginInfo;
import csdc.tool.info.GlobalInfo;


/**
 * 科研人员交流、 好友管理
 * @author Crystal
 *
 */
public class LinkedinAction extends BaseAction {
	
	private static final long serialVersionUID = 1L;

	private static final String HQL = "select f.id, f.friendName, f.type, f.date, f.refuse from Friend f where 1=1 ";// 查询语句主体
	private static final String[] COLUMN = { "f.type desc", "f.friendName", "f.date desc"};// 用于拼接的排序列
	private static final String PAGE_NAME = "friendPage";// 列表页面名称
	private static final String DATE_FORMAT = "yyyy-MM-dd";// 列表时间格式
	private static final String PAGE_BUFFER_ID = "f.id";// 上下条查看时用于查找缓存的字段
	protected Friend friend;
	protected int type;
	protected String reason;
	protected String refuse;

	protected InBox inBox;
	
	protected List<InBox> inBoxs;
	
	protected int flag;//0:从好友列表发起聊天；1：从首页发起聊天
	
	public String pageName() {
		return PAGE_NAME;
	}

	public String[] column() {
		return COLUMN;
	}
	public String HQL() {
		return HQL;
	}
	public String dateFormat() {
		return LinkedinAction.DATE_FORMAT;
	}
	public String pageBufferId() {
		return LinkedinAction.PAGE_BUFFER_ID;
	}

	public Object[] simpleSearchCondition() {
		Map session = ActionContext.getContext().getSession();
		LoginInfo loginer = (LoginInfo) session.get(GlobalInfo.LOGINER);
		StringBuffer hql = new StringBuffer(HQL());
		Map map = new HashMap();
		hql.append(" and f.person.id = :personId ");
		map.put("personId", baseService.getBelongIdByLoginer(loginer));
		if (keyword == null) {// 预处理关键字
			keyword = "";
		} else {
			keyword = keyword.toLowerCase();
			hql.append(" LOWER(f.friendName) like :keyword ");
			map.put("keyword", "%" + keyword + "%");
		}
		return new Object[] { hql.toString(), map, 0, null };
	}

	public Object[] advSearchCondition() {
		return null;
	}
	
	/**
	 * 检测当前添加的好友
	 */
	public String checkToAddFriend(){
		if (entityId.isEmpty() || entityId == null) {
			jsonMap.put("errorInfo", "当前人员不存在！");
		}
		Map paraMap = new HashMap();
		paraMap.put("entityId", entityId);
		paraMap.put("personId", baseService.getBelongIdByLoginer(loginer));
		List friendId = dao.query("select f.id from Friend f where f.person.id = :personId and f.friend.id = :entityId", paraMap);
		if (friendId.size() != 0) {
			jsonMap.put("errorInfo", "当前人员已经是您的好友了");
		}
		return SUCCESS;
	}
	
	/**
	 * 进入到添加好友页面
	 * @return
	 */
	public String toAddFriend(){
		return SUCCESS;
	}
	
	/**
	 * 添加好友
	 * @return
	 */
	public String addFriend(){
		Map session = ActionContext.getContext().getSession();
		LoginInfo loginer = (LoginInfo) session.get(GlobalInfo.LOGINER);
		Person logPerson = dao.query(Person.class, loginer.getPerson().getId());//当前登陆者
		Friend friend = new Friend();
		friend.setPerson(logPerson);
		Person person = dao.query(Person.class, entityId);//要添加的好友
		friend.setFriend(person);
		friend.setFriendName(person.getName());
		friend.setReason(reason);
		friend.setType(0);//等待验证
		dao.add(friend);
		return SUCCESS;
	}
	
	/**
	 * 拒绝好友请求
	 */
	public String refuse(){
		return SUCCESS;
	}
	
	/**
	 * 好友验证
	 * @return
	 */
	public String checkFriend(){
		Map session = ActionContext.getContext().getSession();
		LoginInfo loginer = (LoginInfo) session.get(GlobalInfo.LOGINER);
		Person logPerson = dao.query(Person.class, loginer.getPerson().getId());//当前登陆者
		friend = dao.query(Friend.class,entityId);
		friend.setType(type);
		if (type == 2) {//同意加为好友
			friend.setDate(new Date());
			Friend nFrieend = new Friend();
			nFrieend.setDate(new Date());
			Person person = dao.query(Person.class, friend.getPerson().getId());
			nFrieend.setFriend(person);
			nFrieend.setPerson(logPerson);
			nFrieend.setReason(friend.getReason());
			nFrieend.setFriendName(person.getName());
			nFrieend.setType(type);
			dao.add(nFrieend);
		} else if (type == 1) {//拒绝
			friend.setRefuse(refuse);
		}
		dao.modify(friend);
		return SUCCESS;
	}
	
	/**
	 * 发送消息
	 * @param flag = 0 entityId Friend的ID；从列表进入
	 * @param flag = 1 entityId 当前好友的personID；从首页进入
	 */
	public String toSend(){
		Map session = ActionContext.getContext().getSession();
		LoginInfo loginer = (LoginInfo) session.get(GlobalInfo.LOGINER);
		Map paraMap = new HashMap();
		paraMap.put("personId", baseService.getBelongIdByLoginer(loginer));
		List<String> inboxIds = new ArrayList<String>();
		if (flag == 1) {//通过persoId查找得到friendID
			paraMap.put("entityId", entityId);
			entityId = (String) dao.query("select f.id from Friend f where f.friend.id = :entityId and f.person.id = :personId ",paraMap).get(0);
			inboxIds = dao.query("select ib.id from InBox ib where ib.viewStatus = 0 and ib.friend = :personId and ib.person = :entityId ",paraMap);
		}
		//将当前好友的所有的未读消息置为已读
		//通过Friend的ID 找到ch.person.id
		if (flag == 0) {
			String chPersonId = (String) dao.query("select f.friend.id from Friend f where f.id = ?",entityId).get(0);
			paraMap.remove("entityId");
			paraMap.put("entityId", chPersonId);
			inboxIds = dao.query("select ib.id from InBox ib where ib.viewStatus = 0 and ib.friend = :personId and ib.person = :entityId ",paraMap);
		}
		for (String id : inboxIds) {
			inBox = dao.query(InBox.class, id);
			inBox.setViewStatus(1);
			dao.modify(inBox);
		}
		return SUCCESS;
	}
	
	/**
	 * 显示历史消息
	 */
	public String view(){
		Map session = ActionContext.getContext().getSession();
		LoginInfo loginer = (LoginInfo) session.get(GlobalInfo.LOGINER);
		Map paraMap = new HashMap();
		paraMap.put("personId", baseService.getBelongIdByLoginer(loginer));
		if (flag == 0) {
			entityId = (String) dao.query("select f.friend.id from Friend f where f.id = ?",entityId).get(0);
		}
		paraMap.put("entityId",entityId);
		inBoxs = dao.query("select c from InBox c where (c.friend = :entityId and c.person = :personId) or (c.friend = :personId and c.person = :entityId) order by c.pDate ", paraMap);
		jsonMap.put("chats", inBoxs.size() == 0 ? null: inBoxs);
		return SUCCESS;
	}
	
	
	
	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public Map getJsonMap() {
		return jsonMap;
	}

	public void setJsonMap(Map jsonMap) {
		this.jsonMap = jsonMap;
	}

	public Friend getFriend() {
		return friend;
	}

	public void setFriend(Friend friend) {
		this.friend = friend;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	
	public String getRefuse() {
		return refuse;
	}

	public void setRefuse(String refuse) {
		this.refuse = refuse;
	}

	public InBox getInBox() {
		return inBox;
	}

	public void setInBox(InBox inBox) {
		this.inBox = inBox;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public List<InBox> getInBoxs() {
		return inBoxs;
	}

	public void setInBoxs(List<InBox> inBoxs) {
		this.inBoxs = inBoxs;
	}


	
}
