/**
 * Tomcat节点新能监控窗口
 */
Ext.define('app.sm.cluster.overview.TomcatMonitor',{
	extend : 'Ext.window.Window',
	width:600,
	height:500,
	
	nodeName:'',
	initComponent: function(){
		this.items =[
			{
				xtype:'panel',
				html:'<iframe width="600" height="500" scrolling="no" frameborder="no" border="0" src="sm/cluster/monitor/tomcat/'+this.nodeName+'"/>'
			}
		];
		this.callParent();
		return this;
	}
});