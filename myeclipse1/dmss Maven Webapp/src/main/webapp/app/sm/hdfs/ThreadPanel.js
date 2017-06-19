/**
 * HDFS线程管理面板
 */
Ext.define('app.sm.hdfs.ThreadPanel',{
	extend: 'Ext.form.FormPanel',
	id:'threadPanel',
	title:'HDFS线程管理',
	waitMsgTarget: true,
	bodyPadding: '10 10 10 40', 
	border: false,
	defaults: {
		msgTarget: 'side',
		xtype: 'textfield'
	},
	
	initComponent: function(){
		//定义模型
		var model = Ext.define('app.sm.model.Person', {
		    extend: 'Ext.data.Model',
		    fields: [
		        'tempFileCount',
		        'tempFileSize',
		        'totalBlockCount',
		        'tempBlockCount',
		        'tempPercent',
		        'blockSize',
		        'period',
		        'garbageCount',
		        'garbagePercent'
		    ]
		});
		//设置数据源
		reader: new Ext.data.JsonReader({
			model:this.model,
			root:'form'
		}),	
		//设置form内部控件
		this.items = [
			{fieldLabel:'临时文件数',name:'tempFileCount'},
			{fieldLabel:'临时文件总大小',name:'tempFileSize'},
			{fieldLabel:'总块数',name:'totalBlockCount'},
			{fieldLabel:'临时块数',name:'tempBlockCount'},
			{fieldLabel:'临时块比例(%)',name:'tempPercent'},
			{fieldLabel:'块大小(M)',name:'blockSize'},
			{fieldLabel:'检测周期(小时)',name:'period'},
			{xtype:'button',text:'同步',scope:this,handler:this.mergeHandler},
			{xtype:'button',text:'全同步',scope:this,handler:this.mergeAllHandler},
			{xtype:'button',text:'垃圾清理',scope:this,handler:this.collectGarbageHandler}
			
		],
		this.addListener('afterrender',this.afterRenderHandler,this);
	    this.callParent();
    	return this;
	},
	
	mergeHandler : function(){
		$.get(basePath+'sm/hdfs/thread/merge',function(data){
			if(data.success){
    			$.popMsg("alert-success","同步至HDFS成功");    			
    		}else{
    			$.popMsg("alert-warn","同步至HDFS失败");
    		}
		});
	},
	
	mergeAllHandler : function(){
		$.get(basePath+'sm/hdfs/thread/mergeAll',function(data){
			if(data.success){
    			$.popMsg("alert-success","同步至HDFS成功");    			
    		}else{
    			$.popMsg("alert-warn","同步至HDFS失败");
    		}
		});
	},
	
	collectGarbageHandler : function(){
		$.get(basePath+'sm/hdfs/thread/collectGarbage',function(data){
			if(data.success){
    			$.popMsg("alert-success","同步至HDFS成功");    			
    		}else{
    			$.popMsg("alert-warn","同步至HDFS失败");
    		}
		});
	},
	
	afterRenderHandler:function(){
		this.getForm().load({
			url: basePath+'sm/hdfs/thread/toModify',
			waitMsg: '加载中...'
		});
	}
	
});
