/**
 * 分类树
 */
Ext.define('app.fm.CategoryTree', {
	requires: ['app.components.TreeFilter'],  
    extend: 'Ext.tree.Panel',
    xtype: 'tree-reorder',
    id:'categoryTree',
   	title:'分类树',
    deferRowRender: true,
    rootVisible:false,
    mask:true,
   // rowData:'', //当前选中节点{id:,text:}
    selectedId:'',//选中节点
    plugins: [{
        ptype: 'treefilter'
        , allowParentFolders: true
    }],
	viewConfig: {
                plugins: {
                    ptype: 'treeviewdragdrop',
                    containerScroll: true
                }
            },
    //selType : 'cellmodel',
    /*listeners: {
    	itemdbclick: function(node,record){
    		alert("aaaaa");
    	}
    },*/
    columns:[{
    	xtype: 'treecolumn',
    	text: '文档目录',
    	flex: 3,
    	dataIndex: 'text'
    },{
    	xtype: 'actioncolumn',
    	text: '安全设置',
    	flex: 1,
    	align: 'center',
    	icon:'resources/images/icons-16/page_edit.png',
    	//cls: "z-index: 1000",
    	/*listeners: {
    		click: function(grid, rowIndex, colIndex, actionItem, event, record, row) {
	        	//event.un('click');
	            Ext.getCmp('fileManagementPanel').changePanel('app.fm.FileSecurityGrid',{documentId:record.data.id});
	            //event.stopPropagation();
	        }
    	},*/
        handler: function(grid, rowIndex, colIndex, actionItem, event, record, row) {
        	//event.stopPropagation();
            Ext.getCmp('fileManagementPanel').changePanel('app.fm.FileSecurityGrid',{documentId:record.data.id});
            return false;
//            event.stopPropagation();
        },
        // Only leaf level tasks may be edited
        isDisabled: function(view, rowIdx, colIdx, item, record) {
        	//alert(record.parentNode.data.id);
        	return !('root' == record.parentNode.data.id);
            //return !record.data.leaf;
        }
    }],

    initComponent: function() {       
        this.store = Ext.create('Ext.data.TreeStore', {
	        model: 'Task',
	        proxy: {
	            type: 'ajax',
	            url: basePath+'fm/category/tree'
	        }
        });
        
       this.addListener('itemclick', function(node, record, item, index, event, eOpts){
        	//alert(node.getDepth());
        	var colIdx = event.getTarget('.x-grid-cell').cellIndex;
        	//alert(colIdx);
        	if(colIdx == 0){
		    	this.selectedId = record.data.id;
		    	Ext.getCmp('fileManagementPanel').changePanel('app.fm.DocumentGrid');
	        	Ext.getCmp("documentGrid").refresh();		 		
        	}
		});
		this.addListener('itemcontextmenu',	this.onTreeRightCilckHandler);
		this.addListener('itemmove',this.onMoveCategory);
		this.addListener('beforeitemmove',this.onBeforeMove);
		
		if (this.mask) { this.on('render', this.createMask, this); }
    	
		/*this.tools=[{type:'refresh',handler:function(){
			Ext.getCmp('categoryTree').refresh();
		}},
			{type:'plus',tooltip:'新建域'}];*/
		//设置工具栏
		this.tbar= [{
	            xtype: 'trigger'
	            , triggerCls: 'x-form-clear-trigger'
	            , onTriggerClick: function () {
	                this.reset();
	                this.focus();
	            }
	            , listeners: {
	                change: function (field, newVal) {
	                    var tree = field.up('treepanel');
	                    
	                    tree.filter(newVal);
	                }
	                , buffer: 250
	            }
	        },{
	        	xtype:'button',
                icon:'resources/images/icons-16/toggle_expand.png',
                enableToggle:true,
                pressed:true,
                scope: this,
                listeners:{'toggle':this.onTogglePressed
                }
            }, {
                icon: 'resources/images/icons-16/refresh.png',
                tooltip:'刷新',
                scope: this,
                handler: this.refresh
            },{
            	icon:'resources/images/icons-16/domain.png',
                tooltip: '新建域',
                scope: this,
                handler: this.onCreateDomain
            }
            ];
			
        this.callParent();
    },
    
    onTreeRightCilckHandler: function(tree, record, item, index, e, eOpts ){
		e.preventDefault();
		if(folderMenu==null){
			folderMenu = Ext.create('app.fm.CategoryMenu');
			folderMenu.showAt(e.getXY());
		}
		folderMenu.showAt(e.getXY());
	},
	
	/**
	 * 移动分类目录节点
	 */
	onMoveCategory: function(node, oldParent, newParent, index, eOpts){
		//var record = Ext.getCmp('categoryTree').getSelectionModel().getSelection()[0];
		Ext.Ajax.request({
		    url: basePath+'fm/category/move/'+node.data.id,
		    params:{
		    	destId: newParent.data.id,
		    	index: index
		    }
		});
	},
	
	refresh: function(){
		this.store.reload();
	},
	createMask: function() {
        var mask = new Ext.LoadMask(this.getEl(), this.maskConfig);    
        this.on('beforeload', mask.show, mask);
        this.on('load', mask.hide, mask);
    },
    
    onTogglePressed: function(button, pressed){
    	if(!pressed){
    		button.setIcon('resources/images/icons-16/toggle.png');
    		Ext.getCmp('categoryTree').onExpandAllClick();
    	}else{
    		button.setIcon('resources/images/icons-16/toggle_expand.png');
    		Ext.getCmp('categoryTree').onCollapseAllClick();
    	}
    },
    onExpandAllClick: function(){
        var me = this,
            toolbar = me.down('toolbar');
            
        me.getEl().mask('展开中...');
        toolbar.disable();
                    
        this.expandAll(function() {
            me.getEl().unmask();
            toolbar.enable();
        });
    },
    
    onCollapseAllClick: function(){
        var toolbar = this.down('toolbar');
        
        toolbar.disable();
        this.collapseAll(function() {
            toolbar.enable();
        });
    },
    
    onCreateDomain: function(){
    	if(createDomainWindow==null){
    			createDomainWindow = Ext.create('app.fm.CreateDomainWindow');
    		}
	    createDomainWindow.show();
    },
    getFullPath:function(){
		var path="";
		var record =  this.down('treepanel').getSelectionModel().getSelection()[0];
		this.choosedId = record.data.id;
		while(!record.isRoot()){			
			path = "/"+record.data.text+path;
			record = record.parentNode;
		}
		this.choosedPath = path;
		alert(this.choosedPath);
	},
	
	/*节点移动前触发*/
    onBeforeMove: function(node, oldParent, newParent, index, eOpts){
    	Ext.Ajax.request({
		    url: basePath+'fm/category/isExist',
		    params:{
		    	pid: newParent.data.id,
		    	id:node.data.id
		    },
		    success: function(response){
				if(response.responseText=="failure"){
					Ext.MessageBox.prompt('分类目录重命名', '存在同名分类目录，请输入新名字:', function(btn,text){    			;
		    			if(node.data.text!=text){ //新名字和原名字不相等
		    				Ext.Ajax.request({
							    url:basePath+'fm/category/rename/'+node.data.id,
							    params:{
							    	name:text
							    },
							    success: function(response,opts){
							    	var data = Ext.decode(response.responseText);
									if(data.success==false){
										Ext.MessageBox.show({
								           title: '警告',
								           msg: data.msg,
								           buttons: Ext.MessageBox.OK,
								           animateTarget: 'categoryTree',
								           icon: Ext.MessageBox.WARNING
								       });
									}
									node.set('text', data.data.node.text);
							    }					   
							});
		    			}
		    		});
				}
		    }
		});
    }
    
});
var createDomainWindow;
var selectNode;