/**
 * 角色编辑表单
 */
Ext.define('app.sm.security.role.model.Role', {
    extend: 'Ext.data.Model',
    fields: [
    	'id',
        'name',
        'description',
        'categories',
        'rights'
    ]
});

Ext.define('app.sm.security.role.EditRoleForm',{
	extend: 'Ext.form.FormPanel',
	autoScroll:true,
	width:850,
	title: '查看/编辑角色',
	id:'editRoleForm',
	buttonAlign:'left',
	waitMsgTarget: true,
	bodyPadding: '20 10 0 40', 
	border: false,
	defaults: {
		msgTarget: 'side',
		xtype: 'textfield'
	},
	roleId:"",
	reader: new Ext.data.JsonReader({
		model:'app.sm.security.role.model.Role',
		root:'role'
	}),
	initComponent: function(){
		var rightTree = Ext.create('app.sm.security.RightTree',{formType:'toModify',roleId:this.roleId});
		var domainGrid = Ext.create('app.sm.security.DomainGrid',{formType:'toModify',roleId:this.roleId});
		this.items = [
			{name:'id',hidden:true},	
			{fieldLabel:'角色名',name:'name',allowBlank:false},		
			{fieldLabel:'描述',name:'description',allowBlank:false},
			{
	            xtype: 'textfield',
	            id:'editRoleForm_domainIds',
	            name:'domains',
	            hidden:true,
	            value: ""
		     },
		     {
	            xtype: 'textfield',
	            id:'editRoleForm_rightIds',
	            name:'rights',
	            hidden:true,
	            value: ""
		     },{
		     	xtype:'panel',
		     	layout:'column',
		     	padding:'20 0 0 0',
		     	border:false,
			    items:[{items:[rightTree],
			    		cloumnWidth:0.5,
			    		padding:'0 20 0 0'
			    		},
				    	{items:[domainGrid],
				    	cloumnWidth:0.5
				    	}
			    ]
		     }
		];
		
		this.buttons = [
			{xtype:'button',text:'返回',scope:this,handler:this.returnHandler},
			{ xtype: 'tbfill' },
			{xtype:'button',text:'保存',scope:this,handler:this.saveHandler}
		];
		this.addListener('afterrender',this.afterRenderHandler,this);
	    this.callParent();
    	return this;
	},
	
	/**
	 * 角色保存事件处理函数
	 */
	saveHandler:function(){		
		form = this.getForm();
		if (!form.isValid()) return;
		var doaminIds = '';
		var domainStore = Ext.getCmp('domainGrid').getStore();
		domainStore.each(function(record){
			if(record.data.checked==true){
				doaminIds += record.data.id+',';
			}
		});
		Ext.getCmp('editRoleForm_domainIds').setValue(doaminIds);
		var rightIds = '';
		var rightRecords = Ext.getCmp('rightTree').getView().getChecked();                
        Ext.Array.each(rightRecords, function(rec){
            rightIds += rec.get('id')+",";
        });
		Ext.getCmp('editRoleForm_rightIds').setValue(rightIds);
		form.submit({
			url:basePath+'sm/security/role/modify',
			method : 'post',   
			waitMsg: '保存中...',
			success:function(){
				$.popMsg('alert-success',"角色修改成功");
				Ext.getCmp('systemPanel').changePanel('app.sm.security.RoleGrid');
			}
		});
	},
	
	afterRenderHandler:function(){
		this.getForm().load({
			url: basePath+'sm/security/role/toModify',
			params:{
				roleId:this.roleId
			},
			waitMsg: '加载中...'
		});
	},
	
	/**
	 * 返回
	 */
	returnHandler:function(){
		Ext.getCmp('systemPanel').changePanel('app.sm.security.RoleGrid');
	}
});