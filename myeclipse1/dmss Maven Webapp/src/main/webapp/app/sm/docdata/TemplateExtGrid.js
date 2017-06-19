/**
 * 模板扩展属性列表
 */
Ext.define('app.sm.docdata.TemplateExtModel', {
	extend:'Ext.data.Model',
	fields:[
		{name:'id', type:'string',mapping:0},
		{name:'name', type:'string' ,mapping:1},
		{name:'mandatory', type: 'bool', defaultValue: false,mapping:5 },
		{name:'label', type:'string',mapping:2 },
		{name:'type', type:'string',mapping:3 },
		{name:'stringValue', type:'string',mapping:4 }
	],
    idProperty: 'id'
    
});

Ext.define('app.sm.docdata.TemplateExtGrid',{
	id:'templateExtGrid',
	extend: 'Ext.grid.Panel',
	title: '属性列表',
	store:'app.sm.docdata.TemplateExtModel',
	requires: [
        'Ext.selection.CellModel',
        'Ext.grid.*',
        'Ext.data.*',
        'Ext.util.*',
        'Ext.form.*',
        'app.sm.docdata.TemplateExtModel'
    ],
    xtype: 'cell-editing',
	loadMask: true,
	//width:700,
	//height:360,
	formType:'toList',
	viewConfig: {
        	forceFit: true // 注意不要用autoFill:true,那样设置的话当GridPanel的大小变化（比如你resize了它）时不会自动调整column的宽度
        },
	
    
	constructor: function(config){
		Ext.apply(this,config);		
		this.initConfig(config);
		this.callParent([config]);
		return this;
	},
	
	initComponent: function(){
		
		this.cellEditing = new Ext.grid.plugin.CellEditing({
            clicksToEdit: 1
        });
        
        Ext.apply(this, {
        	width: 680,
            height: 350,
            plugins: [this.cellEditing],
            store: new Ext.data.Store({
            	autoDestroy: true,
            	model: 'app.sm.docdata.TemplateExtModel',
            	proxy:{
	        		reader: {
		                root: 'data',
		                totalProperty: 'totalCount'
	            	},
		            type: 'ajax',
		            url: basePath+'sm/docdata/template/ext/'+this.templateId	            
        		},
        		sorters: [{
                    property: 'common',
                    direction:'ASC'
                }]
            }),
			columns:[
				{text:'属性名',dataIndex:'name',align: 'center',flex:1,editor: {allowBlank: false}},
				{text:'标签',dataIndex:'label',align: 'center',flex:1,editor: {allowBlank: false}},
				{
					text:'字段类型',
					dataIndex:'type',
					align: 'center',
					flex:1,
					editor: new Ext.form.field.ComboBox({
	                    typeAhead: true,
	                    triggerAction: 'all',
	                    store: [
	                        ['String','String'],
	                        ['Date','Date'],
	                        ['Number','Number'],
	                        ['Boolean','Boolean']
	                    ]
	                })
				},
				{
					text:'默认值',
					dataIndex:'stringValue',
					align: 'center',flex:1,
					editor: {allowBlank: false},
           			renderer: function(value, metaData, record, rowIdx, colIdx, store) {
           				//alert(record.data.type);
           				if(record.data.type == "Date") {
	   						metaData.tdAttr = 'data-qtip="' + '格式:2014/01/01' + '"';
							return value;
           				}else{
							return value;
           				}
     			}
					},
				{
					text:'是否必填',
					xtype: 'checkcolumn',
					dataIndex:'mandatory',
					align: 'center',
					flex:1,
					stopSelection: false
				},
			    {
	                xtype: 'actioncolumn',
	                width: 30,
	                sortable: false,
	                menuDisabled: true,
	                items: [{
	                    icon: 'resources/images/icons-16/delete.png',
	                    tooltip: '删除',
	                    scope: this,
	                    handler: this.onDelClick
	                }]
	            }
			],
            selModel: {
                selType: 'cellmodel'
            },
            tbar: [{
                text: '增加新属性',
                scope: this,
                handler: this.onAddClick
            }]
        });
        
        
        
        
        
		/*//定义store
		this.store = Ext.create('Ext.data.Store', {
			pageSize:65535,
        	model: 'app.sm.docdata.TemplateExtModel',
        	proxy:{
        		reader: {
	                root: 'data',
	                totalProperty: 'totalCount'
	            },
	            type: 'ajax',
	            url: basePath+'sm/security/template/'+this.formType+'/templateExtList?templateId='+this.templateId	            
        	}
		});
        this.store.loadPage(1);*/
	    this.callParent();
    	//return this;
	    this.on('afterlayout', this.loadStore, this, {
            delay: 1,
            single: true
        })
	},
	loadStore: function() {
		if(this.templateId != null && (typeof this.templateId != 'undefined')){
	        this.getStore().load({
	            callback: this.onStoreLoad
	        });
		}
    },
    onStoreLoad: function(){
        
    },
    
    onAddClick: function(){
        // Create a model instance
        var rec = new app.sm.docdata.TemplateExtModel({
            id: '',
            name: 'New name',
            mandatory: false,
            label: 'New label',
            type: 'String',
            stringValue: 'Value'
        });
        
        this.getStore().insert(0, rec);
        this.cellEditing.startEditByPosition({
            row: 0, 
            column: 0
        });
    },
    
    onDelClick: function(grid, rowIndex, colIndex){
		me = this;
		var node = me.getStore().getAt(rowIndex);
		me.getStore().removeAt(rowIndex);
    }
    
    

});