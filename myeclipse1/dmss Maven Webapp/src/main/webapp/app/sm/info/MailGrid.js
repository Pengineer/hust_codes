/**
 * 邮件列表
 */
Ext.define('MailGridModel',{
	extend: 'Ext.data.Model',
	fields: [
		{name:'id',mapping:0},
		{name:'accountName',mapping:1},
		{name:'subject',mapping:2},
		{name:'send',mapping:3},
		{name:'sendto',mapping:4},
		{name:'sendDate',mapping:5},
		{name:'status',mapping:6}
        ],
    idProperty: 'id'
});
  
Ext.define('app.sm.info.MailGrid',{
	id:'mailGrid',
	extend: 'Ext.grid.Panel',
	title: '邮件列表',
	loadMask: true,
	height:500,
	viewConfig: {
        	forceFit: true // 注意不要用autoFill:true,那样设置的话当GridPanel的大小变化（比如你resize了它）时不会自动调整column的宽度
        },
	columns:[
			{xtype: 'rownumberer', width: 30,sortable: false},
			{text: "账号名",dataIndex: 'accountName',sortable: false},
			{text: "标题",dataIndex: 'subject',sortable: false,width:200},
			{text: "发送地址",dataIndex: 'send',sortable: false},
			{text: "接收地址",dataIndex: 'sendto',sortable: false},
			{text: "发送日期",dataIndex: 'sendDate',sortable: false},
		    {text: "状态",dataIndex: 'status',sortable: true}
	
	],
  

        
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
        model: 'MailGridModel',
        remoteSort: true, //启用排序
        proxy: {
	        	reader: {
	                root: 'data',
	                totalProperty: 'totalCount'
	            },
	            type: 'ajax',
	            url: basePath+'sm/info/mail/list'
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
	    		{xtype:'textfield',emptyText:'账号名、标题、收件人...',width:160,id:'queryText'},
	    		{xtype:'tbspacer',width:5},
	    		{xtype:'button',text:'查询',handler:this.queryStore}
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
        this.store.loadPage(1);
	    this.callParent();
    	return this;
	},
	
	queryStore : function(){
		var store = Ext.getCmp('mailGrid').store;
		var targetString = Ext.getCmp('queryText').getValue();
		if(targetString == ''){
			return;
		}
		var lastOptions = store.lastOptions;
		Ext.apply(lastOptions,{params:{'search':targetString}}); //附加url请求参数
		store.reload(lastOptions);
	}
});
