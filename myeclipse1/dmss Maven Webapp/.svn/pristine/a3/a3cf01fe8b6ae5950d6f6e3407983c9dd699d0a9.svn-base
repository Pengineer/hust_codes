/**
 * 文档历史版本列表
 */
Ext.define('model.fm.version.VersionModel',{
    extend: 'Ext.data.Model',
    fields: [
    	{name:'id',mapping:0},
    	{name:'type',mapping:1},
    	{name:'title',mapping:2},
    	{name:'fileSize',mapping:3},
    	{name:'path',mapping:4},
    	{name:'account',mapping:5},
    	{name:'date',mapping:6},
    	{name:'version',mapping:7},
    	{name:'blockId',mapping:8},
    	{name:'fileId',mapping:9},
    	{name:'comment',mapping:10}	
    ]
});

Ext.define('app.fm.version.VersionGrid',{
	id:'docHistoryGrid',
	extend: 'Ext.grid.Panel',
	title: '版本记录',
	collapsed:true,
	collapsible:true,
	margin: '0 0 10 0', 
	store:'model.fm.version.VersionModel',
	loadMask: true,
	formType:'toList',
	viewConfig: {
        	forceFit: true // 注意不要用autoFill:true,那样设置的话当GridPanel的大小变化（比如你resize了它）时不会自动调整column的宽度
        },
	
    
	constructor: function(config){
		Ext.apply(this,config);		
		this.initConfig(config);
		this.callParent([config]);
		return this;
	},
	
	initComponent: function(){
		
        Ext.apply(this, {
            store: new Ext.data.Store({
            	autoDestroy: true,
            	model: 'model.fm.version.VersionModel',
            	proxy:{
	        		reader: {
		                root: 'data',
		                totalProperty: 'totalCount'
	            	},
		            type: 'ajax',
		            url: basePath+'fm/version/list/'+this.documentId	            
        		},
        		sorters: [{
                    property: 'common',
                    direction:'ASC'
                }]
            }),
			columns:[		
				{text: '文件名', dataIndex: 'title',flex:1},
				{text: '类型', dataIndex: 'type',flex:1},
				{text: '大小', dataIndex: 'fileSize',flex:1,renderer:function(v){
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
		        	}
		        },
				{text: '版本', dataIndex: 'version', xtype: 'numbercolumn',flex:1},
				{text: '上传者', dataIndex: 'account',flex:1},				
				{text: '备注', dataIndex: 'comment',flex:1},
				{text: '时间', dataIndex: 'date',flex:1},
				{
					text: '下载', 
					xtype: 'actioncolumn', 
					dataIndex: 'download',
					items: [{
		                icon: 'resources/images/icons-16/page_download.png',
		                tooltip: '下载',
		                scope: this,
		                handler: this.onDownloadHandler
		            }],
		            flex:1
				}
			]
        });
	    this.callParent();
	    this.on('afterlayout', this.loadStore, this, {
            delay: 1,
            single: true
        })
	},
	loadStore: function() {
        this.getStore().load({
            callback: this.onStoreLoad
        });
    },
   
    
    /*
     * 文件历史版本下载接口
    */
    onDownloadHandler: function(grid,rowIndex){
		me = this;
		var node = grid.getStore().getAt(rowIndex);		
	    window.location.href = basePath+'fm/version/download/'+node.data.id  ;
	}
    
});