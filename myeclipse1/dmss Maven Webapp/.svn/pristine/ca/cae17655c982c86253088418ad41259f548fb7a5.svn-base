/**
 * 左侧手风琴菜单栏
 */
Ext.define('app.fm.Accordion',{
	extend: 'Ext.panel.Panel',
	title: '分类',
    region : 'west',
    width : 300,
    tools :[{type:'refresh',handler:function(){
		Ext.getCmp('categoryTree').refresh();
	}},
		{type:'plus'}],
    collapsible: true,
    layout: 'accordion',

	initComponent: function(){
	    this.callParent();
    	return this;
	}
});