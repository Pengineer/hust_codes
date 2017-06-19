/**
 * 集群节点状态查看窗口
 */
Ext.define('app.sm.cluster.overview.ServerStatus',{
	extend:'Ext.window.Window',	
	width:500,
	layout:'fit',//设置窗口内部布局
	closeAction:'hide',
	plain:true,//true则主体背景透明，false则和主体背景有些差别
	collapsible:true,//是否可收缩
	modal:true,//是否为模式窗体
	
	nodeName:'',
	initComponent: function(){
		//定义模型
		var statusModel = Ext.define('StatusModel',{
			extend: 'Ext.data.Model',
			fields: [
				{name:'key',mapping:0},
				{name:'value',mapping:1}
		        ],
		    idProperty: 'id'
		});
		//配置数据源
		var gridStore = Ext.create('Ext.data.Store', {
			pageSize:65535,
        	model: statusModel,
        	proxy:{
        		reader: {
	                root: 'data',
	                totalProperty: 'totalCount'
	            },
	            type: 'ajax',
	            url: basePath+'sm/cluster/overview/nodeStatus/'+this.nodeName
        	}
		});
        gridStore.loadPage(1);
        //创建panel
        var grid = Ext.create('Ext.grid.Panel',{
        	store: gridStore,
	        loadMask: true,
			viewConfig: {
		        	forceFit: true // 注意不要用autoFill:true,那样设置的话当GridPanel的大小变化（比如你resize了它）时不会自动调整column的宽度
		        },
			columns:[
					{xtype:'rownumberer', width: 30,sortable: false},
					{text: "状态项",dataIndex: 'key',width: 220,sortable: false},
				    {text: "状态值",dataIndex: 'value',width: 220,sortable: true}
				   	
			]
        });
		this.items = [grid];
	    this.callParent();
    	return this;
	}
});
