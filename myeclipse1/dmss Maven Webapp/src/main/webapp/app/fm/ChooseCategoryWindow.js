/**
 * 分类目录选择窗口
 */
Ext.define('app.fm.ChooseCategoryPanel',{
	extend:'app.fm.CategoryTree',
	title:null,
	id:'chooseCategoryPanel',
	initComponent: function(){
	    this.buttons = [
		    { text: '确定',handler:function(){
		    	 	chooseCategoryWindow.getFullPath();
		    	 	Ext.getCmp('backgroundImportForm-categoryText').setValue(chooseCategoryWindow.choosedPath);
		    	 	Ext.getCmp('backgroundImportForm-categoryId').setValue(chooseCategoryWindow.choosedId);
		    		chooseCategoryWindow.hide();
		    	} 
		    }
		];
		this.callParent();
    	return this;
	}
	
	
});


Ext.define('app.fm.ChooseCategoryWindow',{
	extend:'Ext.window.Window',
	id:'chooseCategoryWindow',
	title:'选择分类目录',
	width:500,
	layout:'fit',//设置窗口内部布局

	plain:true,//true则主体背景透明，false则和主体背景有些差别
	collapsible:true,//是否可收缩
	modal:true,//是否为模式窗体
	choosedId:"",  //自定属性
	choosedPath:"", //自定属性
	initComponent: function(){
		this.items = [Ext.create('app.fm.ChooseCategoryPanel')];		
	    this.callParent();
    	return this;
	},
	
	close:function(){
		this.hide();
	},
	
	/**
	 * 获取分类目录的全路径
	 */
	getFullPath:function(){
		var path="";
		var record =  this.down('treepanel').getSelectionModel().getSelection()[0];
		this.choosedId = record.data.id;
		while(!record.isRoot()){			
			path = "/"+record.data.text+path;
			record = record.parentNode;
		}
		this.choosedPath = path;
	}
});