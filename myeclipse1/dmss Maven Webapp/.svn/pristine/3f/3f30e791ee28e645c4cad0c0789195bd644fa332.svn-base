/**
 * 文档检入窗口
 */
Ext.define('app.fm.version.CheckInForm',{
	extend:'Ext.form.Panel',
	id:'checkInForm',
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
	            xtype: 'textareafield',
	            fieldLabel: '评论',
	            name:'comment'
	        },{
	        	xtype:'panel',
	        	border:false,
	        	height:100,
	        	autoScroll:true,
	        	loader:{
	        		url:'fm/version/toCheckIn',
	        		autoLoad:true,
	        		scripts:true
	        	}
	        }
		];
		this.buttons=[
			{
            text: '保存',
            handler: function(){
            	form =Ext.getCmp('checkInForm').getForm();
            	if (!form.isValid()) return false;
            	if($(".uploadify-queue-item").length==0){
            		Ext.Msg.alert("警告","你还未上传文件");
            		return false;
            	}
            	if($(".uploadify-queue-item").length>1){
            		Ext.Msg.alert("警告","只能上传一个文件");
            		return false;
            	}
            	var node = Ext.getCmp('documentGrid').getSelectionModel().getSelection()[0];
            	form.submit({
            		submitEmptyText: false,
    				url:basePath+'fm/version/checkin/'+node.data.id,
    				method : 'post',   
	    			waitMsg: '保存中...',
	    			failure:function(form,action){
	    				//validateFormErrors(action);
	    			},
	    			success:function(form,action){	    				
	    				Ext.getCmp('checkInForm').onResetClickHandler();
	    				Ext.getCmp('checkInWindow').close();
	    				Ext.getCmp("documentGrid").refresh();//刷新文档列表
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
    	Ext.getCmp('checkInForm').getForm().reset();
        $("a.discard").trigger("click");
        $("a.discard").click();
    }
    
});

Ext.define('app.fm.version.CheckInWindow',{
	extend:'Ext.window.Window',
	id:'checkInWindow',
	title:'检入文档',
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
		var form = Ext.create('app.fm.version.CheckInForm');
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

