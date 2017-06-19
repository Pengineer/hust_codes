/**
 * 相似文档推荐列表
 */
Ext.define('model.fm.document.SimilarDocModel',{
    extend: 'Ext.data.Model',
    fields: [
    	{name: 'name', type: 'string'},
    	{name: 'title', type: 'string'},
    	{name: 'operation',type: 'string'},
    	{name: 'date', type: 'date'},
    	{name: 'comments', type: 'string'},
    	{name: 'path', type: 'string'},
    	{name: 'type', type: 'string'},
    	{name: 'sourceAuthor', type: 'string'}
    ]
});

Ext.define('app.fm.document.SimilarDocGrid',{
	id:'simliarDocGrid',
	extend: 'Ext.grid.Panel',
	title: '相似文档',
	collapsible:true,
	collapsed:true,
	store:'model.fm.document.SimilarDocModel',
	margin: '0 0 10 0', 
	loadMask: true,
	formType:'toList',
	viewConfig: {
        	forceFit: true // 注意不要用autoFill:true,那样设置的话当GridPanel的大小变化（比如你resize了它）时不会自动调整column的宽度
        },
	
	initComponent: function(){
		
        Ext.apply(this, {
            store: new Ext.data.Store({
            	autoDestroy: true,
            	model: 'model.fm.document.SimilarDocModel',
            	proxy:{
	        		reader: {
		                root: 'data',
		                totalProperty: 'totalCount'
	            	},
		            type: 'ajax',
		            url: basePath+'fm/document/similar/'+this.documentId	            
        		},
        		sorters: [{
                    property: 'common',
                    direction:'ASC'
                }]
            }),
			columns:[
				{xtype: 'rownumberer',width: 30, sortable: false},
				{text: '标题', dataIndex: 'title',flex:1},
				{text: '类型', dataIndex: 'type',flex:1},
				{text: '作者', dataIndex: 'sourceAuthor',flex:1},
				{text: '分类', dataIndex: 'path',flex:1}
			]
        });
	    this.callParent();
	    this.on('afterlayout', this.loadStore, this, {
            delay: 1,
            single: true
        })
	},
	loadStore: function() {
        this.getStore().load({
            callback: this.onStoreLoad
        });
    }
    
   
    
});