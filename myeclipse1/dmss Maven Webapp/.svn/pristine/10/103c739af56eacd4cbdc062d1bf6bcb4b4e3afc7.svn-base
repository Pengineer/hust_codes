/**
 * 账号列表
 */
Ext.define('AccountGridModel',{
	extend: 'Ext.data.Model',
	fields: [
		{name:'id',mapping:0},
		{name:'accountName',mapping:1},
		{name:'name',mapping:2},
		{name:'email',mapping:3},
		{name:'agency',mapping:4},
		{name:'duty',mapping:5},
		{name:'loginCount',mapping:6},
		{name:'lastLoginDate',mapping:7},
		{name:'status',mapping:8}
        ],
    idProperty: 'id'
});
  
Ext.define('app.sm.security.account.AccountGrid',{
	id:'accountGrid',
	extend: 'Ext.grid.Panel',
	title: '账号列表',
	loadMask: true,
	height:500,
	viewConfig: {
        	forceFit: true // 注意不要用autoFill:true,那样设置的话当GridPanel的大小变化（比如你resize了它）时不会自动调整column的宽度
    },
	
	
	initComponent: function(){
		var smContent = Ext.getCmp('smContent');
		var flexWidth = smContent.getWidth()-4-730;
		
		this.columns = [
			{xtype: 'rownumberer', width: 30,sortable: false},
			{text: "账号名",dataIndex: 'accountName',width:70,sortable: false},
			{text: "姓名",dataIndex: 'name',width:70,sortable: false},
			{text: "注册邮箱",dataIndex: 'email',width:150,sortable: false},
			{text: "所在机构",dataIndex: 'agency',width:150,sortable: false},
			{text: "职务",dataIndex: 'duty',width:100,sortable: false},
			{text: "登陆次数",dataIndex: 'loginCount',width:60,sortable: false},
			{text: "上次登录时间",dataIndex: 'lastLoginDate',width:150,sortable: false},
		    {text: "活动",dataIndex: 'status',sortable: true,width:60,renderer:function(v){
		    	if(v==1){
		    		return '启用';
		    	}else{
		    		return '停用';
		    	}
		    }},
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
		];
		this.store = Ext.create('Ext.data.Store', {
		pageSize: app.pageSize,
        model: 'AccountGridModel',
        remoteSort: true, //启用排序
        proxy: {
	        	reader: {
	                root: 'data',
	                totalProperty: 'totalCount'
	            },
	            type: 'ajax',
	            url: basePath+'sm/security/account/list'
	        }
	    });
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
	    		{xtype:'button',text:'新建',handler:this.createAccount},	    		
	    		{ xtype: 'tbfill' },
	    		{xtype:'textfield',emptyText:'请输入关键字...',width:160,id:'queryAccountText'},
	    		{xtype:'tbspacer',width:5},
	    		{xtype:'button',text:'查询',handler:this.queryStore,scope:this}
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
        this.store.loadPage(1);
	    this.callParent();
    	return this;
	},
	
	/**
	 * 账号检索
	 */
	queryStore : function(){
		var targetString = Ext.getCmp('queryAccountText').getValue();
		var lastOptions = this.store.lastOptions;
		Ext.apply(lastOptions,{params:{'search':targetString}}); //附加url请求参数
		this.store.reload(lastOptions);
	},
	
	/**
	 * 账号创建
	 */
	createAccount: function(){
		Ext.getCmp('systemPanel').changePanel('app.sm.security.account.AddAccountForm');
	},
	
	/**
	 * 账号编辑
	 * @param {} grid
	 * @param {} rowIndex
	 */
	onEditHandler: function(grid,rowIndex){
		me = this;
		var node = me.getStore().getAt(rowIndex);	
		Ext.getCmp('systemPanel').changePanel('app.sm.security.account.EditAccountForm',{accountId:node.data.id});
	},
	
	/**
	 * 账号删除
	 * @param {} grid
	 * @param {} rowIndex
	 */
	onDeleteHandler:function(grid,rowIndex){
		me = this;
		var node = me.getStore().getAt(rowIndex);	
		$.get(basePath+'sm/security/account/delete/'+node.data.id,function(data){
    		if(data.success){
    			me = Ext.getCmp('accountGrid');
    			me.getStore().removeAt(rowIndex);
    		}else{
    			$.popMsg("alert-warn","账号删除失败");
    		}
    	}); 	
	}	
});
