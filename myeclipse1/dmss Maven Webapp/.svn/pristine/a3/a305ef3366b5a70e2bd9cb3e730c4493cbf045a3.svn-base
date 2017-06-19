/**
 * 数据处理面板
 */
Ext.define('app.sm.docdata.DataProcessPanel',{
	extend: 'Ext.panel.Panel',
	id:'dataProcessPanel',
	
	initComponent: function(){
		var backgroundImportButton = Ext.create('Ext.Button', {
		    text: '后台导入',
		    handler: function() {
		    	if(backgroundImportWindow==null){
		    		backgroundImportWindow = new Ext.create('app.sm.docdata.BackgroundImportWindow');
		    	}
		        backgroundImportWindow.show();
		    	
		    }
		});
		this.items =[backgroundImportButton];
	    this.callParent();
    	return this;
	}
});
