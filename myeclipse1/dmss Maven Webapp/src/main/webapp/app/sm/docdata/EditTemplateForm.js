/**
 * 模板编辑表单
 */
Ext.define('app.sm.docdata.TemplateEditModel', {
	extend:'Ext.data.Model',
	fields:[
		{name:'id', type:'string'},
		{name:'templateName', type:'string' },
		{name:'category', type:'string' },
		{name:'description', type:'string' }
	]
});

Ext.define('app.sm.docdata.EditTemplateForm',{
	extend: 'Ext.form.FormPanel',
	autoScroll:true,
	width:850,
	title: '查看/编辑模板',
	id:'editTemplateForm',
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
	templateId:"",
	reader: new Ext.data.JsonReader({
		model:'app.sm.docdata.TemplateEditModel',
		root:'form'
	}),
	initComponent: function(){
		var templateExtGrid = Ext.create('app.sm.docdata.TemplateExtGrid',{formType:'toModify',templateId:this.templateId});
		this.items = [
			{name:'id',hidden:true,id:'templateId'},	
			{
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
				fieldLabel : '模板描述',
				name : 'description',
				allowBlank:false
			},templateExtGrid
		];
		
		this.buttons = [
			{xtype:'button',text:'返回',scope:this,handler:this.returnHandler},
			{ xtype: 'tbfill' },
			{xtype:'button',text:'保存',scope:this,handler:this.saveHandler}
		];
		//Ext.getCmp('templateId').setValue(this.templateId);//将模板id填到表单隐藏域中
		this.addListener('afterrender',this.afterRenderHandler,this);
	    this.callParent();
    	return this;
	},
	
	/**
	 * 保存模板事件处理函数
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
		var templateExtIds = '';
		var templateEStore = Ext.getCmp('templateExtGrid').getStore();
		templateEStore.each(function(record){
			if(record != null){
				templateExtIds += record.data.id+',';
			}
		});
		Ext.getCmp('addTemplateForm_extIds').setValue(templateExtIds);
		*/
		form.submit({
			url:basePath+'sm/docdata/template/modify',
			method : 'post',  
			waitMsg: '保存中...',
			success:function(){
				$.popMsg('alert-success',"修改成功");
				Ext.getCmp('systemPanel').changePanel('app.sm.docdata.TemplateGrid');
			}
		});
	},
	
	afterRenderHandler:function(){
		this.getForm().load({
			url: basePath+'sm/docdata/template/toModify/'+this.templateId,
			waitMsg: '加载中...'
		});
	},
	
	returnHandler:function(){
		Ext.getCmp('systemPanel').changePanel('app.sm.docdata.TemplateGrid');
	}
});