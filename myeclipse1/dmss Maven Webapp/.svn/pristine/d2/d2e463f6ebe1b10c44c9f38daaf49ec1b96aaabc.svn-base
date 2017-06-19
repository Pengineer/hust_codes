/**
 * 模板添加表单
 */
Ext.define('app.sm.docdata.TemplateModel', {
	extend:'Ext.data.Model',
	fields:[
		{name:'templateName', type:'string' },
		{name:'category', type:'string' },
		{name:'description', type:'string' }
	]
});

Ext.define('app.sm.docdata.AddTemplateForm',{
	extend: 'Ext.form.FormPanel',
	autoScroll:true,
	width:850,
	title: '添加模板',
	id:'addTemplateForm',
	buttonAlign:'left',
	waitMsgTarget: true,
	bodyPadding: '20 10 0 20', 
	border: false,
	fieldDefaults: {
		labelAlign: 'right'
	},
	defaults: {
		msgTarget: 'side',
		xtype: 'textfield'
	},

	initComponent: function(){
		var templateExtGrid = Ext.create('app.sm.docdata.TemplateExtGrid');
		this.items = [{
			xtype : 'hidden',
			name : 'templateExts',
			value: '',
	        id:'addTemplateForm_extIds'
			},{
				fieldLabel : '模板名',
				name : 'templateName',
				allowBlank:false
			},{
				fieldLabel : '类别',
				name : 'category',
				allowBlank:false
			},{
				xtype : 'textarea',
				width: 300,
				fieldLabel : '模板描述',
				name : 'description',
				allowBlank:false
			},{
				xtype: 'panel',			
				items:[templateExtGrid]
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
	 * 模板保存事件处理
	 */
	saveHandler:function(){		
		form = this.getForm();
		if (!form.isValid()) return;
		
		/*
		 * 获取属性列表里所有的数据封装为一个json格式的数据，然后放到表单addTemplateForm_extIds里发送给服务器
		*/
		var templateEStore = Ext.getCmp('templateExtGrid').getStore();
		var templateExts = templateEStore.getRange();
		var templateExtsJson = [];
		for(var i in templateExts){
			templateExtsJson.push({
				'id': templateExts[i].get('id'),
				'name': templateExts[i].get('name'),
				'mandatory': templateExts[i].get('mandatory'),
				'label': templateExts[i].get('label'),
				'type': templateExts[i].get('type'),
				'stringValue': templateExts[i].get('stringValue')
			});
		};
		Ext.getCmp('addTemplateForm_extIds').setValue(Ext.encode(templateExtsJson));
		
		/*
		templateEStore.each(function(record){
			//alert(record.data.id + '  ' + record.data.name + '  ' + record.data.mandatory + ' ');
			if(record != null){
				templateExtIds += record.data.id+',';
			}
		});
		Ext.getCmp('addTemplateForm_extIds').setValue(templateExtIds);
		*/
		form.submit({
			url:basePath+'sm/docdata/template/add',
			method : 'post',  
			waitMsg: '保存中...',
			success:function(){
				$.popMsg('alert-success',"添加成功");
				Ext.getCmp('systemPanel').changePanel('app.sm.docdata.TemplateGrid');
			}
		});
	},
	
	returnHandler:function(){
		Ext.getCmp('systemPanel').changePanel('app.sm.docdata.TemplateGrid');
	}
});