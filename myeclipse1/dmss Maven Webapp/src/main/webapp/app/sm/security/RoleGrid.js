/**
 * 角色列表
 */
Ext.define('app.sm.security.RoleGrid',{
	extend: 'Ext.data.Model',
	fields: [
		{name:'id',mapping:0},
		{name:'name',mapping:1},
		{name:'description',mapping:2}
        ],
    idProperty: 'id'
});
  
Ext.define('app.sm.security.RoleGrid',{
	id:'roleGrid',
	extend: 'Ext.grid.Panel',
	title: '角色列表',
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
		var smContent = Ext.getCmp('smContent');
		var flexWidth = smContent.getWidth()-4-320;
		//定义store
		this.store = Ext.create('Ext.data.Store', {
		pageSize: app.pageSize,
        model: 'app.sm.security.RoleGrid',
        remoteSort: true, //启用排序
        proxy: {
	        	reader: {
	                root: 'data',
	                totalProperty: 'totalCount'
	            },
	            type: 'ajax',
	            url: basePath+'sm/security/role/list'
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
	    		{xtype:'button',text:'新建',handler:this.createRoleHandler},	    		
	    		{ xtype: 'tbfill' },
	    		{xtype:'textfield',emptyText:'请输入角色名或描述...',width:160,id:'queryRoleText'},
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
                    /*var preview = Ext.getCmp('gv').getPlugin('preview');
                    preview.toggleExpanded(pressed);*/
                }
            }]
        });
        //定义列
        this.columns = [
			{xtype: 'rownumberer', width: 60,sortable: false},
			{text: "角色名",dataIndex: 'name',width: 200,sortable: false},
		    {text: "描述",dataIndex: 'description',width: flexWidth,sortable: true},
		    {
                xtype: 'actioncolumn',
                width: 30,
                sortable: false,
                menuDisabled: true,
                items: [{
                    icon: 'resources/images/icons-16/detail.png',
                    tooltip: '修改',
                    scope: this,
                    handler: this.editRoleHandler
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
      
        this.store.loadPage(1);
	    this.callParent();
    	return this;
	},
	
	
	queryStore : function(){
		var targetString = Ext.getCmp('queryRoleText').getValue();
		var lastOptions = this.store.lastOptions;
		Ext.apply(lastOptions,{params:{'search':targetString}}); //附加url请求参数
		this.store.reload(lastOptions);
	},
	
	createRoleHandler: function(){
		Ext.getCmp('systemPanel').changePanel('app.sm.security.AddRoleForm');
	},
	
	editRoleHandler: function(grid,rowIndex){
		me = this;
		var node = me.getStore().getAt(rowIndex);	
		Ext.getCmp('systemPanel').changePanel('app.sm.security.role.EditRoleForm',{roleId:node.data.id});
	},
	
	onDeleteHandler:function(grid,rowIndex){
		me = this;
		var node = me.getStore().getAt(rowIndex);	
		$.get(basePath+'sm/security/role/delete/'+node.data.id,function(data){
    		if(data.success){
    			me = Ext.getCmp('roleGrid');
    			me.getStore().removeAt(rowIndex);
    		}else{
    			$.popMsg("alert-warn","删除失败");
    		}
    	}); 	
	}
});
