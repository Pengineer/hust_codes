/**
 * 文件安全配置列表
 */
Ext.define('model.fm.FileSecurityModel',{
	extend: 'Ext.data.Model',
	fields: [
		{name: 'role',type: 'string'},
		{name: 'read',type: 'boolean'},
		{name: 'write',type: 'boolean'},
		{name: 'addCategory',type: 'boolean'},
		{name: 'download',type: 'boolean'},
		{name: 'deleted',type: 'boolean'},
		{name: 'rename',type: 'boolean'},
		{name: 'security',type: 'boolean'},
		{name: 'checkOut',type: 'boolean'},
		{name: 'checkIn',type: 'boolean'}
	]	
});

Ext.define('app.fm.RoleModel',{
	extend: 'Ext.data.Model',
	fields: [
		{name:'id'},
		{name:'name'}
        ]
});


Ext.define('app.fm.FileSecurityGrid',{
	extend: 'Ext.grid.Panel',
    
    requires: [
        'Ext.selection.CellModel',
        'Ext.grid.*',
        'Ext.data.*',
        'Ext.util.*',
        'Ext.form.*'
    ],
    xtype: 'cell-editing',
    title: '安全设置',
    frame: true,
    id: 'fileSecurityGridId',
    documentId: '',//用于保存文件夹id
    
    initComponent: function() {
    	this.cellEditing = new Ext.grid.plugin.CellEditing({
            clicksToEdit: 1
        });
        
        var rolesStore = new Ext.data.Store({
            model: 'app.fm.RoleModel',
            //autoLoad: true,
            proxy: {
                type: 'ajax',
                url: basePath+'sm/security/role/list',
                reader: {
                    type: 'json',
	                root: 'data'
                }
            }
        });
        
        Ext.apply(this,{
        	width: 680,
        	height: 350,
        	plugins: [this.cellEditing],
        	
        	store: new Ext.data.Store({
        		autoDestroy: true,
        		model: 'model.fm.FileSecurityModel',
        		proxy: {
        			type: 'ajax',
        			url: basePath + "fm/document/toSecurity?documentId=" + this.documentId,
        			reader: {
        				type: 'json',
        				root: 'data'
        			}
        		}
        	}),
        	
        	columns: [{
        		header: '角色',
        		dataIndex: 'role',
        		flex: 2,
        		align: 'center',
        		editor: new Ext.form.field.ComboBox({
                    typeAhead: true,
                    triggerAction: 'all',
                    store: rolesStore,
                    emptyText: '请选择角色...',
                    displayField: 'name',
                    typeAhead: true,
                    queryMode: 'local',
                    allowBlank: false,
                    forceSelection: true,
    				listeners: {
    					render: function(e) {
    						this.getStore().load();
    					},
    					click: function(e) {
    						this.getStore().load();
    					}
    				},
					valueField: 'name'
                })
        	},{
        		header: '读',
        		dataIndex: 'read',
        		flex: 1,
        		xtype: 'checkcolumn',
				stopSelection: false
        	},{
        		header: '写',
        		dataIndex: 'write',
        		flex: 1,
        		xtype: 'checkcolumn',
				stopSelection: false
        	},{
        		header: '添加文件夹',
        		dataIndex: 'addCategory',
        		flex: 2,
        		xtype: 'checkcolumn',
				stopSelection: false
        	},{
        		header: '下载',
        		dataIndex: 'download',
        		flex: 1,
        		xtype: 'checkcolumn',
				stopSelection: false
        	},{
        		header: '删除',
        		dataIndex: 'deleted',
        		flex: 1,
        		xtype: 'checkcolumn',
				stopSelection: false
        	},{
        		header: '重命名',
        		dataIndex: 'rename',
        		flex: 1,
        		xtype: 'checkcolumn',
				stopSelection: false
        	},{
        		header: '安全',
        		dataIndex: 'security',
        		flex: 1,
        		xtype: 'checkcolumn',
				stopSelection: false
        	},{
        		header: '检出',
        		dataIndex: 'checkOut',
        		flex: 1,
        		xtype: 'checkcolumn',
				stopSelection: false
        	},{
        		header: '检入',
        		dataIndex: 'checkIn',
        		flex: 1,
        		xtype: 'checkcolumn',
				stopSelection: false
        	},{
                xtype: 'actioncolumn',
                flex: 1,
                sortable: false,
                menuDisabled: true,
                items: [{
                    icon: 'resources/images/icons-16/delete.png',
                    tooltip: '删除',
                    scope: this,
                    handler: this.onDelClick
                }]
        	}],
            selModel: {
                selType: 'cellmodel'
            },
            tbar: [{
                text: '添加',
                scope: this,
                handler: this.onAddClick
            },{
            	text: '保存',
            	scope: this,
            	handler: this.onSaveClick
            }]
        });
        this.callParent();
        
        this.on('afterlayout', this.loadStore, this, {
            delay: 1,
            single: true
        })
    },
    
    loadStore: function(){
	    this.getStore().load({
            // store loading is asynchronous, use a load listener or callback to handle results
            callback: this.onStoreLoad
        });
    },
    
    onStoreLoad: function(){
	    Ext.Msg.show({
            title: 'Store Load Callback',
            msg: 'store was loaded, data available for processing',
            icon: Ext.Msg.INFO,
            buttons: Ext.Msg.OK
        });
    },
    
     onAddClick: function(){
		/*if(securityRoleWindow == null){
			securityRoleWindow = Ext.create('app.fm.SecurityRoleWindow');
			securityRoleWindow.show();
		}*/
		
		//alert(Ext.getCmp('roleCombobox').getRawValue());
     	
        // Create a model instance
        var rec = new model.fm.FileSecurityModel({
            //role: 'New role',
            read: false,
            write: false,
            addCategory: false,
            download: false,
            deleted: false,
            rename: false,
            security: false,
            checkOut: false,
            checkIn: false
        });
        
        this.getStore().insert(0, rec);
        this.cellEditing.startEditByPosition({
            row: 0, 
            column: 0
        });
    },
    
    onDelClick: function(grid, rowIndex){
        this.getStore().removeAt(rowIndex);
    },
    
    onSaveClick: function(){		
		/*
		 * 获取安全列表里所有的数据封装为一个json格式的数据，然后发送给服务器
		*/
		var fileSecurityStore = Ext.getCmp('fileSecurityGridId').getStore();
		var fileSecurityData = fileSecurityStore.getRange();
		var fileSecurityJson = [];
		for(var i in fileSecurityData){
			fileSecurityJson.push({
				'role': fileSecurityData[i].get('role'),
				'read': fileSecurityData[i].get('read'),
				'write': fileSecurityData[i].get('write'),
				'addCategory': fileSecurityData[i].get('addCategory'),
				'download': fileSecurityData[i].get('download'),
				'deleted': fileSecurityData[i].get('deleted'),
				'rename': fileSecurityData[i].get('rename'),
				'security': fileSecurityData[i].get('security'),
				'checkOut': fileSecurityData[i].get('checkOut'),
				'checkIn': fileSecurityData[i].get('checkIn')
			});
		};
		
		Ext.Ajax.request({
		    url: basePath + "fm/document/security" ,
		    params: {
		        documentId: this.documentId,
		        fileSecurityJson: Ext.encode(fileSecurityJson)
		    },
		    success: function(response){
		        var text = response.responseText;
		        alert(text);
		        // process server response here
		    }
		});
    }
	
})