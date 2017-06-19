/**
 * 文件管理右侧面板
*/
var fmContent=Ext.create('Ext.panel.Panel',{ xtype: 'panel',
			  id: 'fmContent',
			  border:0,
			  margin : 'auto auto auto 10',
			  region:'center',
			  layout:{type:'card'}
			 // html:'<div id="pt-main"></div>'
			  });





Ext.define('app.fm.FileManagementPanel',{
	extend: 'Ext.panel.Panel',
	id:'fileManagementPanel',
	region: 'center',
	layout: 'border',

	
	initComponent: function(){
		
		this.items = [getAccordion(),
			fmContent
		];
		this.callParent();
		return this;
	},
	
	
	changePanel: function(name,params){
		var panel = Ext.create(name,params);					
		fmContent.removeAll();
		fmContent.add(panel);
		fmContent.doLayout();	
	}

});

/**
 * 文档页面左侧风琴栏
 */
function getAccordion () {
    return {
        title : "导航区",
        region : 'west',
        width : 300,
        tools :[{type:'refresh',handler:function(){
			Ext.getCmp('categoryTree').refresh();
		}},
			{type:'plus'}],
        collapsible: true,
        layout: 'accordion',

        items: [
            Ext.create('app.fm.CategoryTree'), 
            Ext.create('app.fm.bookmark.BookmarkGrid'), 
           	Ext.create('app.fm.recycle.RecycleGrid')
        ]
    };
}
