/**
 * 账号编辑表单
 */
Ext.define('model.sm.security.account.Account', {
    extend: 'Ext.data.Model',
    fields: [
    	'id',
        'accountName',
        'startDate',
        'expireDate',
        'maxSession',
        'lastLoginDate',
        'lastLoginIp',
        'allowedIp',
        'status',
        'refusedIp',
        'loginCount',
        'retrivePasswordStartDate',
        'passwordWarning',
        'securityQuestion',
        'securityAnswer',
        'theme',
        
        'personId',
        'name',
        'mobilePhone',
        'email',
        'agency',
        'duty',
        'idCard',
        'qq',
        'lastModifiedDate'
    ]
});

Ext.define('app.sm.security.account.EditAccountForm',{	
	extend: 'app.sm.security.account.AddAccountForm',
	id:'editAccountForm',
	formType:'toModify',//自定义属性	
	reader: new Ext.data.JsonReader({
		model:'model.sm.security.account.Account',
		root:'form'
	}),
	
	initComponent: function(){
		this.addListener('afterrender',this.afterRenderHandler,this);
	    this.callParent();
    	return this;
	},
	
	/**
	 * 初始化表单数据
	 */
	afterRenderHandler:function(){
		this.getForm().load({
			url: basePath+'sm/security/account/toModify',
			params:{
				id:this.accountId
			},
			waitMsg: '加载中...'
		});
		
	},
	
	/**
	 * 保存账号事件处理函数
	 */
	onSaveHandler:function(){		
		form = this.getForm();
		if (!form.isValid()) return;
		var roleIds = '';
		var roleStore = this.chooseRoleGrid.getStore();
		roleStore.each(function(record){
			if(record.data.checked==true){
				roleIds += record.data.id+',';
			}
		});
		Ext.getCmp('addAccountForm_roleIds').setValue(roleIds);
		form.submit({
			url:basePath+'sm/security/account/modify',
			method : 'post',   
			waitMsg: '保存中...',
			scope:this,
			success:function(){
				$.popMsg('alert-success',"账号修改成功");
				this.onReturnHandler();
			}
		});
	}
	
});