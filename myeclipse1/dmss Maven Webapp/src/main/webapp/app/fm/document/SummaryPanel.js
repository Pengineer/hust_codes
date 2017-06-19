/**
 * 文档摘要面板
 */
Ext.define('app.fm.document.SummaryPanel',{
	id:'summaryPanel',
	extend: 'Ext.panel.Panel',
	title: '文档摘要',
	collapsible:true,
	collapsed:true,
	margin: '0 0 10 0', 	
	documentId:'',
	loaded:false,
	
	initComponent: function(){
		this.addListener('expand',function(){
			if(this.loaded==false){
				$.get(basePath+'fm/document/summary/'+this.documentId,function(r){
					Ext.getCmp('summaryPanel').update(r.data.summary);
				})
				this.loaded = true;
			}
		},this);   
		this.callParent();
    	return this;
	}

});