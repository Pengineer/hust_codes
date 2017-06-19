/**
 * 文件上传窗口
 */
Ext.define('app.fm.UploadForm',{
	extend:'Ext.form.Panel',
	id:'uploadForm',
	bodyPadding: '10 10 0',
    border:false,
    resizable:false,
    defaults: {
        anchor: '100%',
        msgTarget: 'side',
        msgTarget: 'side',
		xtype: 'textfield'
    },
    
    constructor: function(config){
		Ext.apply(this,config);		
		this.initConfig(config);
		this.callParent([config]);
		return this;
	},
	
    initComponent: function(){   	
    	this.items = [
    		{
	            xtype: 'textfield',
	            fieldLabel: '分类',
	            id:'categoryId_field',
	            name:'categoryId',
	            hidden:true,
	            value: ""
	        },
	        {
	            xtype: 'textfield',
	            fieldLabel: '模板',
	            name:'templateId'
	        },{
	            xtype: 'textfield',
	            fieldLabel: '作者',
	            name:'sourceAuthor'
	        },{
	            xtype: 'textfield',
	            fieldLabel: '标签',
	            name:'tags'
	        },{
	            xtype: 'textfield',
	            fieldLabel: '评级',
	            emptyText:'输入1-5之间任意整数',
	            name:'rating',
	            vtype:'rating'
	        },{
	            xtype: 'checkbox',
	            fieldLabel: '自动解压',
	            name:'autoUnZip'
	        },{
	        	xtype:'panel',
	        	border:false,
	        	height:200,
	        	autoScroll:true,
	        	loader:{
	        		url:'fm/document/toUpload',
	        		autoLoad:true,
	        		scripts:true
	        	}
	        }
		];
		this.buttons=[
			{
            text: '保存',
            handler: function(){
            	Ext.getCmp('categoryId_field').setValue(selectNode.data.id);
            	form =Ext.getCmp('uploadForm').getForm();
            	if (!form.isValid()) return false;
            	if($(".uploadify-queue-item").length==0){
            		Ext.Msg.alert("警告","你还未上传文件");
            		return false;
            	}
            	form.submit({
            		submitEmptyText: false,
    				url:basePath+'fm/document/upload',
    				method : 'post',   
	    			waitMsg: '保存中...',
	    			failure:function(form,action){
	    				//validateFormErrors(action);
	    			},
	    			success:function(form,action){	    				
	    				Ext.getCmp('uploadForm').onResetClickHandler();
	    				Ext.getCmp('uploadWindow').close();
	    				Ext.getCmp("documentGrid").refresh(selectNode.data.id);//刷新文档列表
	    			}
    			});
            }
	        },{
	            text: '重置',
	            handler:this.onResetClickHandler
	        }];

    	this.callParent();
    	return this;
    },
    
    onResetClickHandler: function(){
    	Ext.getCmp('uploadForm').getForm().reset();
        $("a.discard").trigger("click");
        $("a.discard").click();
    }
    
});

Ext.define('app.fm.UploadWindow',{
	extend:'Ext.window.Window',
	id:'uploadWindow',
	title:'上传文档',
	width:500,
	layout:'fit',//设置窗口内部布局

	plain:true,//true则主体背景透明，false则和主体背景有些差别
	collapsible:true,//是否可收缩
	modal:true,//是否为模式窗体
	
	constructor: function(config){
		Ext.apply(this,config);		
		this.initConfig(config);
		this.callParent([config]);
		return this;
	},
	
	initComponent: function(){	
		var form = Ext.create('app.fm.UploadForm');
	    this.items=[form];
	    this.addListener("beforeshow",function(){
	    	selectNode = Ext.getCmp('categoryTree').getSelectionModel().getSelection()[0];	   	
	    	var aaa=1;
	    });
	    this.callParent();
    	return this;
	},
	
	close:function(){
		this.hide();
	}
	
	
});

