/**
 * 文档列表
 */
Ext.define('DocumentModel', {
        extend: 'Ext.data.Model',
        fields: [
        {name:'id',mapping:0},
        {name:'type',mapping:1},
        {name:'title',mapping:2},
        {name:'fileSize',mapping:3},
        {name:'accountName',mapping:4},
        {name:'createdDate',mapping:5},
        {name:'version',mapping:6},
        {name:'locked',mapping:7},
        {name:'blockId',mapping:8},
        {name:'fileId',mapping:9,type: 'string'},
        {name:'indexed',mapping:10}
        ],
        idProperty: 'id'
});
Ext.define('app.fm.DocumentGrid', {
    extend: 'Ext.grid.Panel',
	id:'documentGrid',
	region : 'center',
    margin : 'auto auto auto 10',
    defaults: {
		align: 'center'
    },
    collapsible: true,
    title: app.string.fm.documentList,
    loadMask: true,
    selModel: {
        pruneRemoved: false
    },
    multiSelect: true,
    viewConfig: {
    	forceFit: true // 注意不要用autoFill:true,那样设置的话当GridPanel的大小变化（比如你resize了它）时不会自动调整column的宽度
    },
    selected:[],//自定义属性
    
    initComponent: function(){
    	var cRecord = Ext.getCmp('categoryTree').getSelectionModel().getSelection()[0];	
    	this.columns = [{
	        xtype: 'rownumberer',
	        width: 30,
	        sortable: false
	    },{
	        text: app.string.type,
	        dataIndex: 'type',
	        width: 50,
	        sortable: false,
	       	renderer:function(v){
	       		return '<img src="resources/images/file_extension/file_extension_'+v+'.png"/>'
	       	}
	    },{
	        text: app.string.title,
	        dataIndex: 'title',
	        width: 400, 
	        sortable: false
	    },{
	        text: "状态",
	        dataIndex: 'locked',
	        width: 40, 
	        sortable: false,
	        renderer:function(v){
	        	if(v==true){
	        		return '<img src="resources/images/icons-16/lock.png"/>'
	        	}else{
	        		return '<img src="resources/images/icons-16/lock_open.png"/>'
	        	}
	        }
	    },
	    	{
	        text: "大小",
	        dataIndex: 'fileSize',
	        width: 80, 
	        sortable: false,
	        renderer:function(v){
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
	    },{
	        text: "上传者",
	        dataIndex: 'accountName',
	        width: 70,
	        sortable: false
	    },{
	        text: "上传时间",
	        dataIndex: 'createdDate',
	        width: 160,
	        sortable: false
	    },{
	        text: app.string.version,
	        dataIndex: 'version',
	        width: 50,
	        sortable: false
	    },{
	    	text: "块号",
	        dataIndex: 'blockId',
	        width: 50,
	        sortable: false
	    },{
	    	text: "文件号",
	        dataIndex: 'fileId',
	        width: 50,
	        sortable: false
	    },{
	    	text: "是否被索引",
	    	xtype: 'booleancolumn',
	        dataIndex: 'indexed',
	        width: 100,
	        sortable: false,
	        trueText: '是',
            falseText: '否'
	    },{
	    	xtype: 'actioncolumn',
            width: 50,
            sortable: false,
            menuDisabled: true,
            text: "详情",
            items: [{
                icon: 'resources/images/icons-16/detail.png',
                tooltip: '修改',
                scope: this,
                handler: this.onEditHandler
            }]
	    }],
    	/*this.store = Ext.create('Ext.data.Store', {
	        id: 'documentStore',
	        model: 'DocumentModel',
	        buffered: true,
	        leadingBufferZone: 300,
	        pageSize: 100,
	        proxy: {
	        	reader: {
	                root: 'data',
	                totalProperty: 'totalCount'
	            },
	            type: 'ajax',
	            url: basePath+'fm/document/list/5'
	        },
	        filterParam: 'search',

            encodeFilters: function(filters) {
                return filters[0].value;
            },
            remoteFilter: true,
        	autoLoad: true
	    });*/
	    
	    this.store = Ext.create('Ext.data.Store', {
			pageSize: 10,
	        model: 'DocumentModel',
	        remoteSort: true, //启用排序
	        proxy: {
	        	reader: {
	                root: 'data',
	                totalProperty: 'totalCount'
	            },
	            type: 'ajax',
	            url: basePath+'fm/document/list/'+cRecord.data.id
	        }
	    });
	    this.store.on("load",function(){
	    	var categoryId = Ext.getCmp('categoryTree').selectedId;
	    	if(this.selected[categoryId]){
				var record = Ext.getCmp('documentGrid').getStore().getById(this.selected[categoryId]);
		        this.getSelectionModel().select(record,true);  
		        this.selected =[];//置空
	    	}
	    },this); 
	     //头部工具栏
	    this.tbar = Ext.create('Ext.panel.Panel',{
	    	layout: {
		        type: 'hbox',
		        align: 'stretch'
		    },
		    border:false,
		    bodyStyle: 'background:white; padding:2px;',
	    	items:[
	    		{xtype:'tbspacer',width:5},
	    		{xtype:'button',text:'上传',handler:this.onUploadClickHandler},	    		
	    		{ xtype: 'tbfill' },
	    		{xtype:'textfield',emptyText:'请输入标题...',width:260,id:'searchDocument'},
	    		{xtype:'tbspacer',width:5},
	    		{xtype:'button',text:'查询',handler:this.queryHandler,scope:this}
	    		
	    	]
	    });
	    
	    //底部分页工具栏
	    this.bbar = Ext.create('Ext.PagingToolbar', {
            store: this.store,
            displayInfo: true,
            displayMsg: '显示记录 {0} - {1} 共 {2}',
            emptyMsg: "没有记录",
            items:[
                '-', {
                text: '显示之前',
                pressed: true,
                enableToggle: true,
                toggleHandler: function(btn, pressed) {

                }
            }]
        });
        
	    //添加监听器
	    this.addListener('itemcontextmenu',this.onRightCilckHandler);
	    this.callParent();
    	return this;
	},
	
	queryHandler:function(){
		var targetString = Ext.getCmp('searchDocument').getValue();
		var lastOptions = this.store.lastOptions;
		lastOptions.start = 0;
		Ext.apply(lastOptions,{params:{'search':targetString}}); //附加url请求参数
		this.store.reload(lastOptions);
	},
	
	/**
	 * 右键弹出菜单
	 */
	onRightCilckHandler: function (grid, record, item, index, e, eOpts ){
		e.preventDefault();
		if(documentMenu==null){
			documentMenu = Ext.create('app.fm.DocumentMenu');
			documentMenu.showAt(e.getXY());
		}
		documentMenu.showAt(e.getXY());
		documentMenu.record = record;
	},
	
	/**
	 * 点击上传按钮
	 */
	onUploadClickHandler: function(){
		if(uploadWindow==null){
			uploadWindow = Ext.create('app.fm.UploadWindow');
		}
		uploadWindow.show();
	},
	
	refresh: function(id){
		var selectedId = Ext.getCmp('categoryTree').selectedId;
		this.store.load({
			url: basePath+'fm/document/list/'+selectedId
		});
	},
	
	/**
	 *设置存选中行记录
	 * @param {} id
	 */
	setSelectedRow:function(id){
		categoryId = Ext.getCmp('categoryTree').selectedId;
		this.selected[categoryId] = id; 
	},
	
	/**
	 *查看详情，修改详情 
	*/
	onEditHandler: function(grid,rowIndex){
		//alert(rowIndex);
		me = this;
		var node = me.getStore().getAt(rowIndex);	
		//alert(node.data.id);
		Ext.getCmp('fileManagementPanel').changePanel('app.fm.EditDocumentForm',{documentId:node.data.id});
	}
	
	
	
});
