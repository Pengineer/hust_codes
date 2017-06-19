package csdc.action.mobile;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javapns.communication.exceptions.CommunicationException;
import javapns.communication.exceptions.KeystoreException;
import javapns.devices.Device;
import javapns.devices.implementations.basic.BasicDevice;
import javapns.notification.AppleNotificationServerBasicImpl;
import javapns.notification.PushNotificationManager;
import javapns.notification.PushNotificationPayload;
import javapns.notification.PushedNotification;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.opensymphony.xwork2.ActionContext;

import csdc.bean.Mail;
import csdc.bean.MobileDevice;
import csdc.bean.Passport;
import csdc.service.IAccountService;
import csdc.tool.ApplicationContainer;
import csdc.tool.MD5;
import csdc.tool.info.GlobalInfo;
import csdc.tool.mail.MailController;

/**
 * 移动端设置
 * @author suwb
 *
 */
public class MobileSettingAction extends MobileAction{

	private static final long serialVersionUID = 1L;
	private static final String PAGENAME = "mobileSettingPage";
	
	private IAccountService accountService;
	
	@Autowired
	private TransactionTemplate txTemplateRequiresNew;
	@Autowired
	private MailController mailController;
	
	private String oldPassword;//密码修改的原密码
	private String newPassword;//密码修改的新密码
	private String userVersion;//用户当前的app版本号
	private String content;//用户反馈正文
	private String userInfo;//反馈用户的联系方式
	private String deviceToken;//移动设备相关信息
	private String mobileTag;//移动设备标识
	
	private Map mobileSession = ActionContext.getContext().getSession();
	
	/**
	 * 修改密码
	 */
	public String resetPassword(){
		String username = accountService.securityUsername();
		Passport passport = accountService.getPassportByName(username);
		if (newPassword == null || newPassword.isEmpty()) {
			jsonMap.put("result", "0");
			jsonMap.put(GlobalInfo.ERROR_INFO, "密码不得为空");
			return INPUT;
		}
		else {
			if (newPassword.length() < 3 || newPassword.length() > 20) {
				jsonMap.put("result", "0");
				jsonMap.put(GlobalInfo.ERROR_INFO, "密码长度限制为3-20位，请重新输入");
				return INPUT;
			}
			else{
				if(passport.getPassword().equals(MD5.getMD5(oldPassword))){
					passport.setPassword(MD5.getMD5(newPassword));
					dao.modify(passport);
					jsonMap.put("result", "1");
					return SUCCESS;
				}
				else{
					jsonMap.put("result", "0");
					jsonMap.put(GlobalInfo.ERROR_INFO, "原始密码出错，请重新输入");
					return INPUT;
				}
			}
		}		
	}
	
	/**
	 * 检测更新
	 * @return
	 */
	public String checkUpdate(){
		String currentVersion = "1.0";
		if(currentVersion.equals(userVersion)){
			jsonMap.put("versionInfo", "0");
		}
		else jsonMap.put("versionInfo", "1"); 
		return SUCCESS;
	}
	
	/**
	 * 用户反馈
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public String getFeedback() throws UnsupportedEncodingException{
		if(content == null){
			return INPUT;
		}
		else{
//			content = URLEncoder.encode(content, "utf-8");
			txTemplateRequiresNew.execute(new TransactionCallbackWithoutResult() {
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					try {
						addEmail(content, userInfo);
					} catch (Exception e) {
						jsonMap.put("result", "0");
						status.setRollbackOnly();
					}
				}
			});
			String id = (String) mobileSession.get("mailId");
			mailController.send(id);// 发送邮件
			jsonMap.put("result", "1");
			return SUCCESS;
		}
	}
	
	/**
	 * 保存消息推送所需的设备信息
	 * @return
	 */
	public String messagePush(){
		MobileDevice mobileDevice = new MobileDevice();
		if(deviceToken.length()>0 && "iphone android".contains(mobileTag)){
			String device = deviceToken.replaceAll("[^0-9^A-Z^a-z]", "");//留下英文和数字(ios设备号的规则)
			List<String> deviceList = dao.query("select md.deviceToken from MobileDevice md");
			//保证设备唯一性
			for (String existDevice : deviceList){
				if(existDevice.equals(device)){
					jsonMap.put("result", "2");
					return INPUT;
				}
			}
			mobileDevice.setDeviceToken(device);
			mobileDevice.setMobileTag(mobileTag);
			mobileDevice.setRegisterDate(new Date());
			dao.add(mobileDevice);
			jsonMap.put("result", "1");
			return SUCCESS;
		}
		else {
			jsonMap.put("result", "0");
			return INPUT;
		}
	}
	
	/**
	 * 消息推送程序-手动推送
	 * @return
	 * @throws JSONException
	 * @throws CommunicationException
	 * @throws KeystoreException
	 */
	public String pushMessage() throws JSONException, CommunicationException, KeystoreException{
		String message = "您好，感谢您使用本应用，祝您使用愉快！";//待推送消息
		String type = "iphone";
		if(type.equals("iphone")){
			List<String> deviceList = dao.query("select md.deviceToken from MobileDevice md where md.mobileTag = 'iphone'");
			String certificatePath =  ApplicationContainer.sc.getRealPath("/mobile/Certification/PushProduction.p12");
			String certificatePassword = "Csdc20121013";//此处注意导出的证书密码不能为空因为空密码会报错
			
			PushNotificationPayload payLoad = new PushNotificationPayload();
			payLoad.addAlert(message); // 消息内容
			payLoad.addBadge(1); // iphone应用图标上小红圈上的数值
			
			PushNotificationManager pushManager = new PushNotificationManager();
			//true：表示的是产品发布推送服务 false：表示的是产品测试推送服务
			pushManager.initializeConnection(new AppleNotificationServerBasicImpl(certificatePath, certificatePassword, false));
			List<PushedNotification> notifications = new ArrayList<PushedNotification>();
			// 发送push消息
			for(int i = 0; i<deviceList.size(); i++){
				Device device = new BasicDevice();
				device.setToken(deviceList.get(i));
				PushedNotification notification = pushManager.sendNotification(device, payLoad, true);
				notifications.add(notification);	    	
			}
			
			List<PushedNotification> failedNotifications = PushedNotification.findFailedNotifications(notifications);
			List<PushedNotification> successfulNotifications = PushedNotification.findSuccessfulNotifications(notifications);
			int failed = failedNotifications.size();
			int successful = successfulNotifications.size();
			System.out.println("成功推送" + successful + "个设备；还有" + failed + "个推送失败");
			pushManager.stopConnection();
			return SUCCESS;			
		}
		else return SUCCESS;
	}

	/**
	 * 帮助
	 * @return
	 */
	public String getHelp(){
		String helpInfo = "";
		jsonMap.put("help", helpInfo);
		return SUCCESS;
	}
	
	/**
	 * 关于
	 * @return
	 */
	public String getAbout(){
		String aboutInfo = "";
		jsonMap.put("about", aboutInfo);
		return SUCCESS;
	}
	
	/*
	 * 组装邮件
	 */
	public void addEmail(String content, String userInfo)throws Exception {
		String accountBelong = "";
		if (loginer.getCurrentBelongUnitName() != null) {
			accountBelong = loginer.getCurrentBelongUnitName();
		}
		if (loginer.getCurrentPersonName() != null) {
			accountBelong = loginer.getCurrentPersonName();
		}
		Mail mail = new Mail();
		mail.setSendTo("913125735@qq.com");//用户反馈信息直接发送至我的邮箱(苏文波)
		mail.setReplyTo("serv@csdc.info");//认证地址
		mail.setSubject("[SMDB移动端用户反馈专用]来自  "+userInfo+" 的用户反馈内容");
		mail.setBody(content);
		mail.setAccountBelong(accountBelong);
		Date createDate = new Date(System.currentTimeMillis());
		mail.setCreateDate(createDate);//创建时间
		mail.setIsHtml(1);//必须以html形式发送，否则中文乱码
		mail.setFinishDate(null);
		mail.setSendTimes(0);
		mail.setStatus(0);
		dao.add(mail);//向数据库添加邮件记录
		mobileSession.put("mailId", mail.getId());
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(String userInfo) {
		this.userInfo = userInfo;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getUserVersion() {
		return userVersion;
	}

	public void setUserVersion(String userVersion) {
		this.userVersion = userVersion;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	
	public String getDeviceToken() {
		return deviceToken;
	}

	public void setDeviceToken(String deviceToken) {
		this.deviceToken = deviceToken;
	}

	public String getMobileTag() {
		return mobileTag;
	}

	public void setMobileTag(String mobileTag) {
		this.mobileTag = mobileTag;
	}

	public IAccountService getAccountService() {
		return accountService;
	}

	public void setAccountService(IAccountService accountService) {
		this.accountService = accountService;
	}
	
	@Override
	public String pageName() {
		// TODO Auto-generated method stub
		return PAGENAME;
	}	

}
