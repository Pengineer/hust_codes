/**
 * 角色选择列表
 */
Ext.define('ChooseRoleModel',{
	extend: 'Ext.data.Model',
	fields: [
		{name:'id',mapping:0},
		{name:'name',mapping:1},
		{name:'description',mapping:2},
		{name:'checked',mapping:3}
        ],
    idProperty: 'id'
});
  
Ext.define('app.sm.security.account.ChooseRoleGrid',{
	id:'chooseRoleGrid',
	extend: 'Ext.grid.Panel',
	store:'ChooseRoleModel',
	loadMask: true,
	width:500,
	height:300,
	margin:'0 0 10 0',
	formType:'',//自定义属性
	accountId:'',//自定义属性
	viewConfig: {
        	forceFit: true // 注意不要用autoFill:true,那样设置的话当GridPanel的大小变化（比如你resize了它）时不会自动调整column的宽度
        },
	columns:[
			{xtype:'checkcolumn',dataIndex:'checked', width: 30,sortable: false},
			{text: "角色",dataIndex: 'name',width: 220,sortable: false},
		    {text: "描述",dataIndex: 'description',width: 220,sortable: true}
		   	
	],

	initComponent: function(){
		this.store = Ext.create('Ext.data.Store', {
			pageSize:65535,
        	model: 'ChooseRoleModel',
        	proxy:{
        		reader: {
	                root: 'data',
	                totalProperty: 'totalCount'
	            },
	            type: 'ajax',
	            url: basePath+'sm/security/account/'+this.formType+'/roleList?accountId='+this.accountId	            
        	}
		});
        this.store.loadPage(1);
	    this.callParent();
    	return this;
	}

});
