/**
 * 回收站列表
 */
Ext.define('RecycleGrid',{
	extend: 'Ext.data.Model',
	fields: [
		{name:'id',mapping:0},
		{name:'type',mapping:1},
		{name:'title',mapping:2},
		{name:'lastModifiedDate',mapping:3}
        ],
    idProperty: 'id'
});
  
Ext.define('app.fm.recycle.RecycleGrid',{
	uses:['app.components.form.SearchField'],
	id:'recycleGrid',
	title:'回收站',
	extend: 'Ext.grid.Panel',
	loadMask: true,
	width:300,
	height:300,
	margin:'0 0 10 0',
	selModel: {
        pruneRemoved: false
    },
    multiSelect: true,
	viewConfig: {
        	forceFit: true // 注意不要用autoFill:true,那样设置的话当GridPanel的大小变化（比如你resize了它）时不会自动调整column的宽度
        },

	initComponent: function(){
		this.columns = [
			{text: "类型",dataIndex: 'type',width: 40,sortable: true,
				renderer:function(v){
		       		return '<img src="resources/images/icons-16/file_extension/file_extension_'+v+'.png"/>'
		       	}
	       	},
		    {text: "文件名",dataIndex: 'title',width: 190,sortable: true},
		    {text: "时间",dataIndex: 'lastModifiedDate',width: 150,sortable: true,hidden:false},
		   	{
                xtype: 'actioncolumn',
                width: 30,
                sortable: false,
                menuDisabled: true,
                items: [{
                    icon: 'resources/images/icons-16/drive_restore.png',
                    tooltip: '还原',
                    scope: this,
                    handler: this.onRecoverHandler
                }]
            },
		   	{
                xtype: 'actioncolumn',
                width: 30,
                sortable: false,
                menuDisabled: true,
                items: [{
                    icon: 'resources/images/icons-16/delete.png',
                    tooltip: '彻底删除',
                    scope: this,
                    handler: this.onDeleteHandler
                }]
            }
		];
		this.store = Ext.create('Ext.data.Store', {
			pageSize: 65535,
        	model: 'RecycleGrid',
        	proxy:{
        		reader: {
	                root: 'data',
	                totalProperty: 'totalCount'
	            },
	            filterParam: 'search',
	            type: 'ajax',
	            url: basePath+'fm/recycle/list'	            
        	},
        	listeners: {
	            totalcountchange: this.onStoreSizeChange
	        },
	        remoteFilter: true,
	        autoLoad: true
		});
		
		this.tbar= [
			{
                width: 140,
                xtype: 'searchfield',
                store: this.store
            },
			{
                icon: 'resources/images/icons-16/refresh.png',
                tooltip:'刷新',
                scope: this,
                handler: this.refresh
            },{
                icon: 'resources/images/icons-16/drive_delete.png',
                tooltip:'清空回收站',
                scope: this,
                handler: this.onDeleteAllHandler
            },'->',
            {
                xtype: 'component',
                itemId: 'status',
                tpl: '共: {count}条',
                style: 'margin-right:5px'
            }
        ];
        this.store.loadPage(1);
	    this.callParent();
    	return this;
	},
	
	onStoreSizeChange: function(){
		var me = Ext.getCmp('recycleGrid');
        me.down('#status').update({count: me.store.getTotalCount()});
    },
	
	refresh: function(){
		this.store.reload();
	},
	
	/**
	 * 删除回收站文件事件处理函数
	 * 
	 * @param {}
	 *            grid
	 * @param {}
	 *            rowIndex
	 */
	onDeleteHandler :function(grid,rowIndex){
		var cRecord = Ext.getCmp('categoryTree').getSelectionModel().getSelection()[0];
		me = this;
		var node = me.getStore().getAt(rowIndex);	
		$.get(basePath+'fm/recycle/delete/'+node.data.id,function(data){
    		if(data.success){
    			me = Ext.getCmp('recycleGrid');
    			me.getStore().removeAt(rowIndex);
    			$.popMsg("alert-success","删除成功");
    		}else{
    			$.popMsg("alert-warn","删除失败");
    		}
    	}); 
	},
	
	/**
	 * 清空回收站事件处理函数
	 */
	onDeleteAllHandler : function(){
		$.get(basePath+'fm/recycle/deleteAll',function(data){
    		if(data.success){
    			$.popMsg("alert-success","回收站已清空");
    			Ext.getCmp('recycleGrid').refresh();
    		}else{
    			$.popMsg("alert-warn","回收站清空失败");
    		}
    	}); 
	},
	
	onRecoverHandler : function(grid,rowIndex){
		var node = this.getStore().getAt(rowIndex);
		 $.get(basePath+'fm/recycle/restore/'+node.data.id,function(data){
		 if(data.success){
			 $.popMsg("alert-success","文档还原成功")
		 }else{
			 $.popMsg("alert-warn","文档还原失败")
		 }
		 });
	},
	
	

});
