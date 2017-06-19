/**
 * 角色添加表单
 */
Ext.define('app.sm.security.model.Person', {
    extend: 'Ext.data.Model',
    fields: [
        'name',
        'description',
        'categories',
        'rights'
    ]
});

Ext.define('app.sm.security.AddRoleForm',{
	extend: 'Ext.form.FormPanel',
	autoScroll:true,
	width:850,
	title: '添加角色',
	id:'addRoleForm',
	buttonAlign:'left',
	waitMsgTarget: true,
	bodyPadding: '20 10 0 40', 
	border: false,
	defaults: {
		msgTarget: 'side',
		xtype: 'textfield'
	},

	initComponent: function(){
		var rightTree = Ext.create('app.sm.security.RightTree');
		var domainGrid = Ext.create('app.sm.security.DomainGrid');
		this.items = [
			{fieldLabel:'角色名',name:'name',allowBlank:false},		
			{fieldLabel:'描述',name:'description',allowBlank:false},
			{
	            xtype: 'textfield',
	            id:'addRoleForm_domainIds',
	            name:'domains',
	            hidden:true,
	            value: ""
		     },
		     {
	            xtype: 'textfield',
	            id:'addRoleForm_rightIds',
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
		Ext.getCmp('addRoleForm_domainIds').setValue(doaminIds);
		var rightIds = '';
		var rightRecords = Ext.getCmp('rightTree').getView().getChecked();                
        Ext.Array.each(rightRecords, function(rec){
            rightIds += rec.get('id')+",";
        });
		Ext.getCmp('addRoleForm_rightIds').setValue(rightIds);
		form.submit({
			url:basePath+'sm/security/role/add',
			method : 'post',  
			waitMsg: '保存中...',
			success:function(){
				$.popMsg('alert-success',"添加成功");
				Ext.getCmp('systemPanel').changePanel('app.sm.security.RoleGrid');
			}
		});
	},
	
	/**
	 * 返回
	 */
	returnHandler:function(){
		Ext.getCmp('systemPanel').changePanel('app.sm.security.RoleGrid');
	}
});