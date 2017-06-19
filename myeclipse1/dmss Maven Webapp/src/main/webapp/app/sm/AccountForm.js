/**
 * 账号表单
 */
Ext.define('app.sm.model.Person', {
    extend: 'Ext.data.Model',
    fields: [
        'name',
        'mobilePhone',
        'email',
        'qq',
        'agency',
        'duty',
        'idCard'
    ]
});

Ext.define('app.sm.AccountForm',{
	extend: 'Ext.form.FormPanel',
	title: '个人信息',
	id:'accountForm',
	waitMsgTarget: true,
	bodyPadding: '10 10 10 40', 
	border: false,
	url: basePath+'sm/selfspace/modify',
	defaults: {
		msgTarget: 'side',
		xtype: 'textfield'
	},
    
	items: [
		{fieldLabel:'姓名',name:'name'},
		{fieldLabel:'移动电话',name:'mobilePhone'},
		{fieldLabel:'邮箱',name:'email',vtype:'email',vtypeText:'邮箱地址无效'},
		{fieldLabel:'QQ',name:'qq'},
		{fieldLabel:'所在单位',name:'agency'},
		{fieldLabel:'职务',name:'duty'},
		{fieldLabel:'身份证号',name:'idCard',vtype:'IDCard'}
	],
	buttons:[
		{xtype:'button',text:'保存',scope:this,handler:function(){
										form =Ext.getCmp('accountForm').getForm();
										if (!form.isValid()) return;
					    				form.submit({
						    				url:basePath+'sm/selfspace/modify',
						    				method : 'post',   
						    				params:{
							    				id:2
							    			},
							    			waitMsg: '保存中...',
							    			failure:function(form,action){
							    				validateFormErrors(action);
							    			}
						    			});
					               }}
	],
	reader: new Ext.data.JsonReader({
		model:'app.sm.model.Person',
		root:'person'
	}),
	
	constructor: function(config){
		Ext.apply(this,config);		
		this.initConfig(config);
		this.callParent([config]);
		return this;
	},
	
	initComponent: function(){
		this.addListener('afterrender',this.afterRenderHandler,this);
	    this.callParent();	    
    	return this;
	},
	
	afterRenderHandler:function(){
		this.getForm().load({
			url: basePath+'sm/selfspace/toModify',
			waitMsg: '加载中...'
		});
	}
});