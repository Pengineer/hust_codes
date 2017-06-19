/**
 * 文档域列表
 */
Ext.define('app.sm.security.DomainModel',{
	extend: 'Ext.data.Model',
	fields: [
		{name:'id',mapping:0},
		{name:'name',mapping:1},
		{name:'description',mapping:2},
		{name:'checked',mapping:3}
        ],
    idProperty: 'id'
});
  
Ext.define('app.sm.security.DomainGrid',{
	id:'domainGrid',
	extend: 'Ext.grid.Panel',
	title: '管理域列表',
	store:'app.sm.security.DomainModel',
	loadMask: true,
	width:500,
	height:360,
	formType:'toAdd',
	viewConfig: {
        	forceFit: true // 注意不要用autoFill:true,那样设置的话当GridPanel的大小变化（比如你resize了它）时不会自动调整column的宽度
        },
	columns:[
			{xtype:'checkcolumn',dataIndex:'checked', width: 30,sortable: false},
			{text: "管理域",dataIndex: 'name',width: 220,sortable: false},
		    {text: "描述",dataIndex: 'description',width: 220,sortable: true}
		   	
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
			pageSize:65535,
        	model: 'app.sm.security.DomainModel',
        	proxy:{
        		reader: {
	                root: 'data',
	                totalProperty: 'totalCount'
	            },
	            type: 'ajax',
	            url: basePath+'sm/security/role/'+this.formType+'/domainList?roleId='+this.roleId	            
        	}
		});
        this.store.loadPage(1);
	    this.callParent();
    	return this;
	}

});
