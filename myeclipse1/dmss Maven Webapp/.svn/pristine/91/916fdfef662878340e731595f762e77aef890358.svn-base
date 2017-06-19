/**
 * 文档编辑表单
 */
Ext.define('model.fm.document.DocumentModel', {
    extend: 'Ext.data.Model',
    fields: [
    	'id',
        'categoryString',
        'categoryId',
        'lastModifiedDate',
        'locked',
        'indexed',
        'fingerprint',
        'title',
        'type',
        'version',
        'createdDate',
        'accountName',
        'lockedAccount',
        'sourceAuthor',
        'fileSize',
        'templateName',
        'templateId',
        'rating',
        'content'
    ]
});

Ext.define('app.fm.EditDocumentForm',{
	extend: 'Ext.form.FormPanel',
	autoScroll:true,
	width:850,
	title: '文档详情',
	id:'editDocumentForm',
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
	documentId:"",
	reader: new Ext.data.JsonReader({
		model:'model.fm.document.DocumentModel',
		root:'form'
	}),
	initComponent: function(){
		var summaryPanel =  Ext.create('app.fm.document.SummaryPanel',{documentId:this.documentId});
		var versionGrid = Ext.create('app.fm.version.VersionGrid',{documentId:this.documentId});
		var similarDocGrid = Ext.create('app.fm.document.SimilarDocGrid',{documentId:this.documentId});
		this.items = [
			{
				xtype: 'fieldset',
				title: '文档详情',
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
			    items:[{
				   
						defaults: {
							msgTarget: 'side',
							xtype: 'textfield'
						},
					items:[
					{xtype: 'hidden',name:'id'},
					{xtype: 'displayfield',fieldLabel: '文档分类',name:'categoryString'},
					{xtype: 'textfield',fieldLabel: '文档标题',name:'title'},
					{xtype: 'displayfield',fieldLabel: '文件大小',name:'fileSize',renderer:function(v){
			        	if(v/1024/1024/1024>=1){
			        		var num = new Number(v/1024/1024/1024);
			        		return num.toFixed(2)+"GB";
			        	}else if(v/1024/1024>=1){
			        		var num = new Number(v/1024/1024);
			        		return num.toFixed(2)+"MB";
			        	}else if(v/1024>=1){
			        		var num = new Number(v/1024);
			        		return num.toFixed(2)+"KB";
			        	}else{
			        		return v+"B";
			        	}
			        }},
					{xtype:'radiogroup',fieldLabel:'是否锁定',readOnly: true,
						columns: [100,100],
						items: [
			                {boxLabel: '是', checked: true,name:'locked',inputValue:1},
			                {boxLabel: '否',name:'locked',inputValue:0}
			            ]
					},
					{xtype: 'textfield',fieldLabel: '作者',name:'sourceAuthor'},
					{xtype: 'textfield',fieldLabel: '文档模板',name:'template'},
					{xtype: 'displayfield',fieldLabel: '创建者',name:'accountName'},
					{xtype: 'displayfield',fieldLabel: '版本号',name:'version'},
					{xtype: 'textfield',fieldLabel: '文档标签',name:'tags'}
					]
					
			    },{
						defaults: {
							msgTarget: 'side',
							xtype: 'textfield'
						},
					items:[
						{xtype: 'displayfield',fieldLabel: 'ID',name:'id'},
						{xtype: 'displayfield',fieldLabel: '文件类型',name:'type'},
						{xtype: 'displayfield',fieldLabel: '指纹信息',name:'fingerprint'},
						{xtype: 'displayfield',fieldLabel: '锁定者',name:'lockedAccount'},
						{xtype: 'numberfield',fieldLabel: '评级',name:'rating'},
						
						{xtype: 'displayfield',fieldLabel: '索引',name:'indexed',renderer:function(v){
							if(v==true){
								return "已索引";
							}else{
								return "未索引";
							}
						}},
						{xtype:'displayfield',fieldLabel: '创建日期',name:'createdDate'},
						{xtype:'displayfield',fieldLabel: '上次修改日期',name:'lastModifiedDate'}
					]
			    
			    }
			    ]
			},
			summaryPanel,
			versionGrid,
			similarDocGrid
			
		],
		
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
	
	saveHandler:function(){	
		form = this.getForm();
		form.submit({
			url:basePath+'fm/document/modify/',
			method : 'post',   
			waitMsg: '保存中...',
			scope:this,
			success:function(){
				$.popMsg('alert-success',"修改成功");
				this.onReturnHandler();
			}
		});
	},
	
	afterRenderHandler:function(){
		this.getForm().load({
			url: basePath+'fm/document/toModify/'+this.documentId,
			waitMsg: '加载中...',
			success:function(form){
				//Ext.getCmp('editDocumentFormContent').update(form.reader.jsonData.form.content);
			}
		});
	},
	
	returnHandler:function(){
	    Ext.getCmp('fileManagementPanel').changePanel('app.fm.DocumentGrid');
        Ext.getCmp("documentGrid").refresh();
	}
});