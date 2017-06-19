/**
 * 后台导入弹出窗口
 */
Ext.define('app.sm.docdata.BackgroundImportForm',{
	extend:'Ext.form.Panel',
	id:'backgroundImportForm',
	bodyPadding: '10 10 0',
    border:false,
    resizable:false,
    defaults: {
        anchor: '100%',
        msgTarget: 'side',
        msgTarget: 'side',
		xtype: 'textfield'
    },
    
    constructor: function(config){
		Ext.apply(this,config);		
		this.initConfig(config);
		this.callParent([config]);
		return this;
	},
	
    initComponent: function(){   	
    	this.items = [
    		{
	            xtype: 'textfield',
	            fieldLabel: '分类',
	            name:'categoryId',
	            id:'backgroundImportForm-categoryId',
	            hidden:true
	        },
    		{
	            xtype: 'textfield',
	            fieldLabel: '分类',
	            name:'categoryText',
	            id:'backgroundImportForm-categoryText',
	            readOnly:true
	        }, {
	            xtype: 'textfield',
	            fieldLabel: '后台路径',
	            name:'path'
	        },{
	            xtype: 'textfield',
	            fieldLabel: '模板',
	            name:'templateId'
	        },{
	            xtype: 'textfield',
	            fieldLabel: '作者',
	            name:'sourceAuthor'
	        },{
	            xtype: 'textfield',
	            fieldLabel: '标签',
	            name:'tags'
	        },{
	            xtype: 'textfield',
	            fieldLabel: '评级',
	            emptyText:'输入1-5之间任意整数',
	            name:'rating',
	            vtype:'rating'
	        }
		];
		this.buttons=[
			{
            text: '保存',
            handler: function(){
            	form = backgroundImportWindow.down('form').getForm();
            	if (!form.isValid()) return false;
            	form.submit({
            		submitEmptyText: false,
    				url:basePath+'sm/docdata/dataprocess/import',
    				method : 'post',   
	    			waitMsg: '保存中...',
	    			failure:function(form,action){
	    				//validateFormErrors(action);
	    			},
	    			success:function(form,action){	    				
	    				backgroundImportWindow.down('form').getForm().reset();
	    				backgroundImportWindow.close();
	    			}
    			});
            }
	        },{
	            text: '重置',
	            handler:this.onResetClickHandler
	        }];

    	this.callParent();
    	return this;
    },
    
    onResetClickHandler: function(){
    	Ext.getCmp('backgroundImportForm').getForm().reset();
    }
    
});

Ext.define('app.sm.docdata.BackgroundImportWindow',{
	extend:'Ext.window.Window',
	id:'backgroundImportWindow',
	title:'后台导入',
	width:500,
	layout:'fit',//设置窗口内部布局

	plain:true,//true则主体背景透明，false则和主体背景有些差别
	collapsible:true,//是否可收缩
	modal:true,//是否为模式窗体
	
	constructor: function(config){
		Ext.apply(this,config);		
		this.initConfig(config);
		this.callParent([config]);
		return this;
	},
	
	initComponent: function(){	
		var form = Ext.create('app.sm.docdata.BackgroundImportForm');
	    this.items=[form];
	    
	    Ext.getCmp('backgroundImportForm-categoryText').addListener('focus',function(){
	    	if(chooseCategoryWindow==null){
	    		chooseCategoryWindow = Ext.create('app.fm.ChooseCategoryWindow'); 
	    	}
	    	chooseCategoryWindow.show();
	    })
	    this.callParent();
    	return this;
	},
	
	close:function(){
		this.hide();
	}
});

