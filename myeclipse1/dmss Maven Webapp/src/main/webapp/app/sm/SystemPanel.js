/**
 * 系统管理面板
 */
var smContent=Ext.create('Ext.panel.Panel',{ xtype: 'panel',
			  id: 'smContent',
			  border:0,
			  margin : 'auto auto auto 10',
			  region:'center',
			  layout:{type:'card'}
			 // html:'<div id="pt-main"></div>'
			  });  //系统菜单右侧面板
			  
Ext.define('app.sm.SystemPanel',{
	extend: 'Ext.panel.Panel',
	id:'systemPanel',
	region: 'center',
	layout: 'border',
	
	constructor: function(config){
		Ext.apply(this,config);		
		this.initConfig(config);
		this.callParent([config]);
		return this;
	},
	
	initComponent: function(){
		//加载用户
		$.ajax({url:basePath + 'sm/security/account/getRights',type:'GET',async:false,success:function(data){
			RIGHTS = data;
		}});
		var menu = Ext.create('Ext.tree.Panel',{
				region: 'west'	,
				title: '系统模块菜单',
				width:300,	
				rootVisible:false,
				root:{
					text: '',
					expanded: true,
					children: [
						{text: '个人中心',expanded: true,cls:!RIGHTS.ROLE_SM_SELFSPACE,children: [
								{id:'menu11',text: '个人信息', leaf: true ,cls:!RIGHTS.ROLE_SM_SELFSPACE_MODIFY},
								{id:'menu12',text: '修改密码', leaf: true ,cls:!RIGHTS.ROLE_SM_SELFSPACE_PASSWORD}
							] },
						{text: '安全管理',expanded: true,cls:!RIGHTS.ROLE_SM_SECURITY,children: [
								{id:'menu21',text: '用户账号', leaf: true ,cls:!RIGHTS.ROLE_SM_SECURITY_ACCOUNT},
								{id:'menu22',text: '系统角色', leaf: true ,cls:!RIGHTS.ROLE_SM_SECURITY_ROLE},
								{id:'menu23',text: '会话管理', leaf: true ,cls:!RIGHTS.ROLE_SM_SECURITY_SESSION},
								{id:'menu24',text: '异常管理', leaf: true ,cls:!RIGHTS.ROLE_SM_SECURITY_EXCEPTION}
							] },
						{text: '搜索管理',expanded: true,cls:!RIGHTS.ROLE_SM_SEARCH,children: [
							{id:'menu71',text: '索引管理', leaf: true ,cls:!RIGHTS.ROLE_SM_SEARCH_INDEX}
						] },
						{text: '集群管理',expanded: true,children: [
							{id:'overview',text: '概览', leaf: true }
						] },
						{text: 'HDFS管理',expanded: true,cls:!RIGHTS.ROLE_SM_HDFS,children: [
							{id:'hdfs_thread',text: 'HDFS线程', leaf: true ,cls:!RIGHTS.ROLE_SM_HDFS_THREAD}
						] },
						{text: '文档数据',expanded: true,cls:!RIGHTS.ROLE_SM_DOCDATA,children: [
							{id:'menu31',text: '模板配置', leaf: true ,cls:!RIGHTS.ROLE_SM_DOCDATA_TEMPLATE },
							{id:'menu33',text: '数据处理', leaf: true ,cls:!RIGHTS.ROLE_SM_DOCDATA_DATAPROCESS}
						] },
						{text: '系统设置',expanded: true,cls:!RIGHTS.ROLE_SM_SYSTEM,children: [
							{id:'menu41',text: '参数配置', leaf: true ,cls:!RIGHTS.ROLE_SM_SYSTEM_PARAMETER},
							{id:'menu42',text: '邮箱配置', leaf: true ,cls:!RIGHTS.ROLE_SM_SYSTEM_MAIL}
						] },
						{text: '统计分析',expanded: true,cls:!RIGHTS.ROLE_SM_STATISTIC,children: [
							{id:'menu51',text: '概要分析', leaf: true ,cls:!RIGHTS.ROLE_SM_STATISTIC_GENERAL },
							{id:'menu52',text: '性能分析', leaf: true ,cls:!RIGHTS.ROLE_SM_STATISTIC_PERFORMANCE },
							{id:'menu53',text: '用户访问', leaf: true ,cls:!RIGHTS.ROLE_SM_STATISTIC_USER }
						] },
						{text: '消息管理',expanded: true,cls:!RIGHTS.ROLE_SM_INFO,children: [
							{id:'menu61',text: '邮箱', leaf: true ,cls:!RIGHTS.ROLE_SM_INFO_MAIL},
							{id:'menu62',text: '站内信', leaf: true ,cls:!RIGHTS.ROLE_SM_INFO_MESSAGE}
						] }
						
					]
				}
		});	
		//隐藏没有权限的节点
		var root = menu.getRootNode();
		var tmp =[];//存放要删除的节点
		var i =0;
		root.cascadeBy(function(r){
			if(r.data.cls){
				tmp[i++] = r;
			}
		});
		for(var j=0;j<i;j++){
			tmp[j].remove();
		}
		
		menu.addListener('itemclick',this.itemChanged);
		this.items = [menu,
			smContent
		];
		this.callParent();
		return this;
	},

	itemChanged : function(node,record){
					switch(record.data.id){
						case 'menu11':
							var accountForm = Ext.create('app.sm.AccountForm');										
							smContent.removeAll();
							smContent.add(accountForm);
							
							smContent.doLayout();
							break;
						case 'menu12':
							Ext.getCmp('systemPanel').changePanel('app.sm.ModifyPasswordForm');
							break;
						case 'menu21':
							Ext.getCmp('systemPanel').changePanel('app.sm.security.account.AccountGrid');
							break;
						case 'menu22':
							Ext.getCmp('systemPanel').changePanel('app.sm.security.RoleGrid');
							break;
						case 'menu23':
							Ext.getCmp('systemPanel').changePanel('app.sm.security.SessionGrid');
							break;
						case 'menu31':
							Ext.getCmp('systemPanel').changePanel('app.sm.docdata.TemplateGrid');
							break;
						case 'menu33':
							Ext.getCmp('systemPanel').changePanel('app.sm.docdata.DataProcessPanel');							
							break;
						case 'menu61':
							Ext.getCmp('systemPanel').changePanel('app.sm.info.MailGrid');
							break;
						case 'menu62':
							Ext.getCmp('systemPanel').changePanel('app.sm.info.MessageGrid');
							break;
						case 'menu51':
							var panel =Ext.create('Ext.panel.Panel',{
								layout:'fit',
								html:'aaa',
								bodyStyle: {
								    background: 'red'
								}
							});
							smContent.removeAll();
							smContent.add(panel);
							smContent.doLayout();
							break;
						case 'menu52':
							var panel =Ext.create('Ext.panel.Panel',{
								layout:'fit',
								html:'bbb',
								bodyStyle: {
								    background: 'green'
								}
							});
							smContent.removeAll();
							smContent.add(panel);
							smContent.doLayout();
							break;
						case 'menu53':
							var panel =Ext.create('Ext.panel.Panel',{
								layout:'fit',
								html:'ccc',
								bodyStyle: {
								    background: 'orange'
								}
							});
							smContent.removeAll();
							smContent.add(panel);
							smContent.doLayout();
							break;
						case 'menu71':
							Ext.getCmp('systemPanel').changePanel('app.sm.search.IndexManagePanel');
							break;
						case 'hdfs_thread':
							Ext.getCmp('systemPanel').changePanel('app.sm.hdfs.ThreadPanel');
							break;
						case 'overview':
							Ext.getCmp('systemPanel').changePanel('app.sm.cluster.Overview');
							break;
						default:
							break;
					}	
	},
	
	changePanel: function(name,params){
		var panel = Ext.create(name,params);					
		smContent.removeAll();
		smContent.add(panel);
		smContent.doLayout();	
	}
});

