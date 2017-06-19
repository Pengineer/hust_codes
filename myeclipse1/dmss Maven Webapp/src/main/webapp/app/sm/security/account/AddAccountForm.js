/**
 * 账号添加表单
 */
Ext.define('app.sm.security.account.AddAccountForm',{
	extend: 'Ext.form.FormPanel',
	id:'addAccountForm',
	waitMsgTarget: true,
	autoScroll:true,
	bodyPadding: '20 10 10 40', 
	border: false,
	fieldDefaults: {
        anchor:"0",
        labelWidth:80
    },
    chooseRoleGrid:'', //自定义属性
    formType:'toAdd',//自定义属性
    accountId:'',//自定义属性
	initComponent: function(){
		this.chooseRoleGrid = Ext.create('app.sm.security.account.ChooseRoleGrid',{formType:this.formType,accountId:this.accountId});
		
		var securityQuestions = [
			['班主任姓名?','班主任姓名?'],
			['大学室友?','大学室友?'],
			['配偶生日?','配偶生日?']
		];
		var securityQuestionStore = new Ext.data.SimpleStore({
			fields:['value','text'],
			data:securityQuestions
		});
		
		var themesData = [
			['春天','春天'],
			['夏天','夏天'],
			['秋天','秋天'],
			['冬天','冬天']
		];
		var themesStore = new Ext.data.SimpleStore({
			fields:['value','text'],
			data:themesData
		});
		this.items = [
			{
				xtype:'fieldset',
				title:'账号信息',
				defaults: {
					type:'form',
					msgTarget: 'side',
					flex:1,
					border: false
				},
				layout: {
			    	type:'hbox',
			    	align:'stretch'
			    },
			    items: [{
					defaults: {
						msgTarget: 'side',
						xtype:'textfield'
					},
					items:[
					{xtype:'textfield',name:'id',hidden:true},
					{xtype:'textfield',fieldLabel:'账号名',name:'accountName',allowBlank:false},
					{xtype:'datefield',fieldLabel:'开始时间',name:'startDate'},
					{xtype:'textfield',fieldLabel:'允许IP',name:'allowedIp'},
					{xtype:'combobox',fieldLabel:'密保问题',name:'securityQuestion',
						emptyText:'请选择',
						store:securityQuestionStore,
						typeAhead: true,
						queryMode: 'local',
                        forceSelection: true
					},
					{xtype:'combobox',fieldLabel:'主题',name:'theme',
						emptyText:'请选择',
						store:themesStore,
						typeAhead: true,
						queryMode: 'local',
                        forceSelection: true
					},
					{xtype:'displayfield',fieldLabel:'登录次数',name:'loginCount',readOnly:true}
				]},{
					defaults: {
						msgTarget: 'side',
						xtype:'textfield'
					},
					items:[
					{xtype:'radiogroup',fieldLabel:'状态',
						columns: [100,100],
						items: [
			                {boxLabel: '启用', checked: true,name:'status',inputValue:1},
			                {boxLabel: '停用',name:'status',inputValue:0}
			            ]
					},
					{xtype:'datefield',fieldLabel:'过期时间',name:'expireDate'},
					{xtype:'textfield',fieldLabel:'拒绝IP',name:'refusedIp'},
					{xtype:'textfield',fieldLabel:'密保答案',name:'securityAnswer'},
					{xtype:'textfield',fieldLabel:'最大连接数',name:'maxSession',vtype:'number',value:'5'},				
					{xtype:'displayfield',fieldLabel:'上次登录时间',name:'lastLoginDate',readOnly:true}
					]}
				]				
			},{
				xtype:'fieldset',
				title:'人员信息',
				defaults: {
					type:'form',
					msgTarget: 'side',
					flex:1,
					border: false
				},
				layout: {
			    	type:'hbox',
			    	align:'stretch'
			    },
			    items: [{
					defaults: {
						msgTarget: 'side',
						xtype:'textfield'
					},
					items:[
					{xtype:'textfield',fieldLabel:'姓名',name:'name',allowBlank:false},
					{xtype:'textfield',fieldLabel:'单位',name:'agency'},
					{xtype:'textfield',fieldLabel:'手机',name:'mobilePhone',vtype:'phone'},
					{xtype:'textfield',fieldLabel:'QQ',name:'qq'}

				]},{
					defaults: {
						msgTarget: 'side',
						xtype:'textfield'
					},
					items:[
					{xtype:'textfield',fieldLabel:'身份证',name:'idCard',vtype:'IDCard'},
					{xtype:'textfield',fieldLabel:'职务',name:'duty'},
					{xtype:'textfield',fieldLabel:'邮箱',name:'email',vtype:'email',vtypeText:'邮箱地址无效',allowBlank:false},
					{xtype:'displayfield',fieldLabel:'上次修改时间',name:'lastModifiedDate',readOnly:true},
					{
			            xtype: 'textfield',
			            id:'addAccountForm_roleIds',
			            name:'roleIds',
			            hidden:true,
			            value: ""
				     }
					]}
				]
			},{
				xtype:'fieldset',
				title:'选择角色',
				items:[this.chooseRoleGrid]
			}];
		this.buttons = [
			{xtype:'button',text:'返回',scope:this,handler:this.onReturnHandler},
			{ xtype: 'tbfill' },
			{xtype:'button',text:'保存',scope:this,handler:this.onSaveHandler}
		],
		this.addListener('afterrender',this.afterRenderHandler,this);
	    this.callParent();
    	return this;
	},
	afterRenderHandler:function(){
		this.getForm().findField('startDate').setValue(new Date());
		this.getForm().findField('expireDate').setValue('2019-12-31');
		this.getForm().findField('theme').setValue('冬天');
		
	},
	
	/**
	 * 账号保存事件处理函数
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
			url:basePath+'sm/security/account/add',
			method : 'post',  
			scope:this,
			waitMsg: '保存中...',
			success:function(){
				$.popMsg('alert-success',"添加成功");
				this.onReturnHandler();
			},
			failure:function(){
				$.popMsg('alert-success',"添加成功");
				this.onReturnHandler();
			}
		});
	},
	
	/**
	 * 返回
	 */
	onReturnHandler:function(){
		Ext.getCmp('systemPanel').changePanel('app.sm.security.account.AccountGrid');
	}
	
	
});