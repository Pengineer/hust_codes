/**
 * 模板列表
 */
Ext.define('TemplateGridModel',{
	extend: 'Ext.data.Model',
	fields: [
		{name:'id',mapping:0},
		{name:'name',mapping:1},
		{name:'description',mapping:2},
		{name:'category',mapping:3},
		{name:'docCount',mapping:4},
		{name:'lastModifuedDate',mapping:5}
        ],
    idProperty: 'id'
});
  
Ext.define('app.sm.docdata.TemplateGrid',{
	id:'templateGrid',
	extend: 'Ext.grid.Panel',
	title: '文档模板列表',
	loadMask: true,
	height:500,
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
		//定义store
		this.store = Ext.create('Ext.data.Store', {
		pageSize: app.pageSize,
        model: 'TemplateGridModel',
        remoteSort: true, //启用排序
        proxy: {
	        	reader: {
	                root: 'data',
	                totalProperty: 'totalCount'
	            },
	            type: 'ajax',
	            url: basePath+'sm/docdata/template/list'
	        }
	    });
	    
	    this.columns=[
			{xtype: 'rownumberer', width: 30,sortable: false},
			{text: "模板名",dataIndex: 'name',sortable: false},
			{text: "描述",dataIndex: 'description',sortable: false,width:200},
			{text: "类别",dataIndex: 'category',sortable: false},
			{text: "文档数",dataIndex: 'docCount',sortable: false},
		    {text: "最近修改",dataIndex: 'lastModifuedDate',sortable: true},
		    {
                xtype: 'actioncolumn',
                width: 30,
                sortable: false,
                menuDisabled: true,
                items: [{
                    icon: 'resources/images/icons-16/detail.png',
                    tooltip: '修改',
                    scope: this,
                    handler: this.onEditHandler
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
		],
	    
	    //头部工具栏
	    this.tbar = Ext.create('Ext.panel.Panel',{
	    	layout: {
		        type: 'hbox',
		        align: 'stretch'
		    },
		    border:false,
		    padding:2,
	    	items:[
	    		{xtype:'tbspacer',width:5},
	    		{xtype:'button',text:'新建',handler:this.createTemplate},		    		
	    		{xtype: 'tbfill' },
	    		{xtype:'textfield',emptyText:'模板名、描述、类别...',width:160,id:'queryText'},
	    		{xtype:'tbspacer',width:5},
	    		{xtype:'button',text:'查询',handler:this.queryStore}
	    	]
	    });
	    
	    //底部分页工具栏
	    this.bbar = Ext.create('Ext.PagingToolbar', {
            store: this.store,
            displayInfo: true,
            displayMsg: '显示用记录{0} - {1} 共 {2}',
            emptyMsg: "没有记录",
            items:[
                '-', {
                text: '显示之前',
                pressed: true,
                enableToggle: true,
                toggleHandler: function(btn, pressed) {
                    /*var preview = Ext.getCmp('gv').getPlugin('preview');
                    preview.toggleExpanded(pressed);*/
                }
            }]
        });
        this.store.loadPage(1);
	    this.callParent();
    	return this;
	},
	
	queryStore : function(){
		var store = Ext.getCmp('templateGrid').store;
		var targetString = Ext.getCmp('queryText').getValue();
		if(targetString == ''){
			return;
		}
		var lastOptions = store.lastOptions;
		Ext.apply(lastOptions,{params:{'search':targetString}}); //附加url请求参数
		store.reload(lastOptions);
	},
	
	/*
	 * 创建新的模板
	*/
	createTemplate: function(){
		Ext.getCmp('systemPanel').changePanel('app.sm.docdata.AddTemplateForm');
	},
	
	/*
	 * 点击模板详情的时候，跳转到编辑详情表单
	*/
	onEditHandler: function(grid,rowIndex){
		me = this;
		var node = me.getStore().getAt(rowIndex);
		Ext.getCmp('systemPanel').changePanel('app.sm.docdata.EditTemplateForm',{templateId:node.data.id});
	},
	
	onDeleteHandler: function(grid,rowIndex){
		me = this;
		var node = me.getStore().getAt(rowIndex);	
		$.get(basePath+'sm/docdata/template/delete/'+node.data.id,function(data){
    		if(data.success){
    			me = Ext.getCmp('templateGrid');
    			me.getStore().removeAt(rowIndex);
    		}else{
    			$.popMsg("alert-warn","删除失败");
    		}
    	});
	}
});
