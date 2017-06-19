/**
 * 书签列表
 */
Ext.define('BookmarkGrid',{
	extend: 'Ext.data.Model',
	fields: [
		{name:'id',mapping:0},
		{name:'type',mapping:1},
		{name:'title',mapping:2},
		{name:'lastModifiedDate',mapping:3},
		{name:'documentId',mapping:4},
		{name:'categoryId',mapping:5}
        ],
    idProperty: 'id'
});
  
Ext.define('app.fm.bookmark.BookmarkGrid',{
	uses:['app.components.form.SearchField'],
	id:'bookmarkGrid',
	title:'书签',
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
		    {text: "时间",dataIndex: 'lastModifiedDate',width: 150,sortable: true,hidden:true},
		    {text: "文档ID",dataIndex: 'documentId',width: 150,sortable: true,hidden:true},
		    {text: "目录ID",dataIndex: 'categoryId',width: 150,sortable: true,hidden:true},
		   	{
                xtype: 'actioncolumn',
                width: 30,
                sortable: false,
                menuDisabled: true,
                items: [{
                    icon: 'resources/images/icons-16/page_go.png',
                    tooltip: '链接至',
                    scope: this,
                    handler: this.onLinkHandler
                }]
            },
		   	{
                xtype: 'actioncolumn',
                width: 30,
                sortable: false,
                menuDisabled: true,
                items: [{
                    icon: 'resources/images/icons-16/delete.png',
                    tooltip: '删除',
                    scope: this,
                    handler: this.onDeleteHandler
                }]
            }
		];
		this.store = Ext.create('Ext.data.Store', {
			pageSize: 65535,
        	model: 'BookmarkGrid',
        	proxy:{
        		reader: {
	                root: 'data',
	                totalProperty: 'totalCount'
	            },
	            filterParam: 'search',
	            type: 'ajax',
	            url: basePath+'fm/bookmark/list'	            
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
		var me = Ext.getCmp('bookmarkGrid');
        me.down('#status').update({count: me.store.getTotalCount()});
    },
	
	refresh: function(){
		this.store.reload();
	},
	
	/**
	 * 书签删除事件
	 * @param {} grid
	 * @param {} rowIndex
	 */
	onDeleteHandler :function(grid,rowIndex){
		var cRecord = Ext.getCmp('categoryTree').getSelectionModel().getSelection()[0];
		me = this;
		var node = me.getStore().getAt(rowIndex);	
		$.get(basePath+'fm/bookmark/delete/'+node.data.id+'?categoryId='+cRecord.data.id,function(data){
    		if(data.success){
    			me = Ext.getCmp('bookmarkGrid');
    			me.getStore().removeAt(rowIndex);
    			$.popMsg("alert-success","书签删除成功");
    		}else{
    			$.popMsg("alert-warn","书签删除失败");
    		}
    	}); 
	},
	
	/**
	 * 书签链接事件处理函数
	 * @param {} grid
	 * @param {} rowIndex
	 */
	onLinkHandler: function(grid,rowIndex){
		me = this;
		var node = me.getStore().getAt(rowIndex);
		$('#categoryTree_header').click();
		Ext.getCmp('categoryTree').selectedId = node.data.categoryId;	
		Ext.getCmp('documentGrid').setSelectedRow(node.data.documentId);
		$('#categoryTree tr[data-recordid="'+node.data.categoryId+'"] div').click();
	}

});
