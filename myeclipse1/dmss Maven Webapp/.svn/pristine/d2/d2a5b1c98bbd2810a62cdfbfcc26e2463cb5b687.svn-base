/**
 * 分类树右键菜单
 */
Ext.define('app.fm.CategoryMenu', {
    extend: 'Ext.menu.Menu',
    id:'categoryMenu',
    items:[
    	{text:'刷新',icon:'resources/images/icons-16/refresh.png',
    		handler:function(){
    			Ext.getCmp('categoryTree').refresh();
    		}
    	},
    	{text:'新建',icon:'resources/images/icons-16/folder_add.png',hidden:!RIGHTS.ROLE_FM_CATEGORY_ADD,
    		handler:function(){
    		if(createCategoryWindow==null){
    			createCategoryWindow = Ext.create('app.fm.CreateCategoryWindow');
    		}
	        createCategoryWindow.show();
    	}},
    	{text:'重命名',icon:'resources/images/icons-16/folder_rename.png',hidden:!RIGHTS.ROLE_FM_CATEGORY_MODIFY,
    		handler:function(){
    		Ext.MessageBox.prompt('分类目录重命名', '请输入新名字:', function(btn,text){
    			if(btn=='ok'){
	    			var node = Ext.getCmp('categoryTree').getSelectionModel().getSelection()[0];
	    			if(Ext.getCmp('categoryTree').rowData.text!=text){ //新名字和原名字不相等
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
							           icon: Ext.MessageBox.WARNING
							       });
								}else{
									node.set('text', data.data.node.text);
								}
						    }					   
						});
	    			}
    			}
    		},this,false,Ext.getCmp('categoryTree').rowData.text);
    	}},
    	{text:'删除',icon:'resources/images/icons-16/folder_delete.png',hidden:!RIGHTS.ROLE_FM_CATEGORY_DELETE,
    		handler:function(){
    			Ext.MessageBox.confirm('确认', '删除分类会级联删除其所有子分类及文件，不可恢复，继续吗？', function(e){
            		if(e=="yes"){
            			var record = Ext.getCmp('categoryTree').getSelectionModel().getSelection()[0];
						Ext.Ajax.request({
						    url: basePath+'fm/category/delete/'+record.data.id,
						    success: function(data){
								record.remove(true);
						    }
						}); 
            		}
            	});			  
    	}},
    	{text:'粘贴',icon:'resources/images/icons-16/page_paste.png',handler:function(){
		    	var cRecord =  Ext.getCmp('categoryTree').getSelectionModel().getSelection()[0];
		    	$.get(basePath+'fm/document/paste',{destCid:cRecord.data.id},function(data){
		    		if(data.success){
		    			$.popMsg("alert-success","粘贴成功");  
		    			Ext.getCmp('documentGrid').refresh();
		    		}else{
		    			$.popMsg("alert-warn","粘贴失败");
		    		}
		    	});
    		}},
    	{text:'导出ZIP',icon:'resources/images/icons-16/export_zip.png',hidden:!RIGHTS.ROLE_FM_CATEGORY_EXPORT,handler:function(){
    			
    		}}

    ]

});

