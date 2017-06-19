/**
 * 索引管理面板
 */
Ext.define('app.sm.search.IndexManagePanel',{
	extend: 'Ext.panel.Panel',
	id:'indexManagePanel',
	
	initComponent: function(){
		this.items =[
			{xtype:'button',text:'更新索引',scope:this,handler:this.onUpdateHandler},
			{xtype:'button',text:'重建索引',scope:this,handler:this.onRebuildHandler},
			{xtype:'button',text:'清空索引',scope:this,handler:this.onDropHandler}
		];
	    this.callParent();
    	return this;
	},
	
	onUpdateHandler : function(){
		$.get(basePath+'sm/search/index/update',function(data){
			if(data.success){
    			$.popMsg("alert-success","更新索引成功");    			
    		}else{
    			$.popMsg("alert-warn","更新索引失败");
    		}
		});
	},
	
	onRebuildHandler : function(){
		$.get(basePath+'sm/search/index/rebuild',function(data){
			if(data.success){
				$.popMsg("alert-success","重建索引成功");    			
			}else{
				$.popMsg("alert-warn","重建索引失败");
			}
		});
	},
	
	onDropHandler : function(){
		$.get(basePath+'sm/search/index/drop',function(data){
			if(data.success){
				$.popMsg("alert-success","删除索引成功");    			
			}else{
				$.popMsg("alert-warn","删除索引失败");
			}
		});
	}
});
