/**
 * 文档右键菜单
 */
Ext.define('app.fm.DocumentMenu', {
    extend: 'Ext.menu.Menu',
	id:'documentMenu',

    
    /*constructor: function(config){ //测试表明和默认函数一致的
		Ext.apply(this,config);		
		this.initConfig(config);
		this.callParent([config]);
		return this;
	},*/
	
    initComponent: function(){
    	this.items = [
	    	{text:'预览',icon:'resources/images/icons-16/page_world.png',handler:this.onPreviewClick,hidden:!RIGHTS.ROLE_FM_DOCUMENT_PREVIEW},
	    	{text:'详情',icon:'resources/images/icons-16/detail.png',handler:this.onDetailClick},
	    	{text:'下载',icon:'resources/images/icons-16/page_download.png',handler:this.onDownloadClick,hidden:!RIGHTS.ROLE_FM_DOCUMENT_DOWNLOAD},
	    	{text:'自动分类',icon:'resources/images/icons-16/page_delete.png',handler:this.onClassifyClick,hidden:!RIGHTS.ROLE_FM_DOCUMENT_CLASSIFY},
	    	{text:'索引更新',icon:'resources/images/icons-16/page_delete.png',handler:this.onIndexUpdateClick,hidden:!RIGHTS.ROLE_FM_DOCUMENT_INDEXU},
	    	{text:'相似检索',icon:'resources/images/icons-16/page_find.png',handler:this.onSimilarSearchClick,hidden:!RIGHTS.ROLE_FM_DOCUMENT_SIMILAR},
	    	{text:'剪切',icon:'resources/images/icons-16/page_cut.png',handler:this.onCutClick,hidden:!RIGHTS.ROLE_FM_DOCUMENT_CUT},
	    	{text:'复制',icon:'resources/images/icons-16/page_copy.png',handler:this.onCopyClick,hidden:!RIGHTS.ROLE_FM_DOCUMENT_COPY},
	    	{text:'删除文档',icon:'resources/images/icons-16/page_delete.png',handler:this.onDeleteClick,hidden:!RIGHTS.ROLE_FM_DOCUMENT_DELETE},
	    	{text:'彻底删除',icon:'resources/images/icons-16/page_delete.png',handler:this.onDeletemClick,hidden:!RIGHTS.ROLE_FM_DOCUMENT_DELETE},
	    	{text:'从分类删除',icon:'resources/images/icons-16/page_delete.png',handler:this.onDeleteFromCategoryClick,hidden:!RIGHTS.ROLE_FM_DOCUMENT_DETETE},
	    	{text:'添加书签',icon:'resources/images/icons-16/bookmark.png',scope:this,handler:this.onAddBookmarkClick,hidden:!RIGHTS.ROLE_FM_BOOKMARK_ADD},
	    	{text:'邮件发送',icon:'resources/images/icons-16/mail_yellow.png',handler:this.onSendMailClick,hidden:!RIGHTS.ROLE_FM_DOCUMENT_MAIL},
	    	{text:'检出',icon:'resources/images/icons-16/check_in.png',handler:this.onCheckOutClick,hidden:!RIGHTS.ROLE_FM_VERSION_CHECKOUT},
	    	{text:'检入',icon:'resources/images/icons-16/check_out.png',handler:this.onCheckInClick,hidden:!RIGHTS.ROLE_FM_VERSION_CHECKIN},
	    	{text:'上锁',icon:'resources/images/icons-16/lock.png',handler:this.onLockClick,hidden:!RIGHTS.ROLE_FM_VERSION_LOCK},
	    	{text:'解锁',icon:'resources/images/icons-16/lock_open.png',handler:this.onUnlockClick,hidden:!RIGHTS.ROLE_FM_VERSION_UNLOCK}
	    ];
	    this.callParent();
    	return this;
	},
	
	onPreviewClick:function(){
		var r = Ext.getCmp('documentMenu').record;
		/*var imgFormat = ['jpg','gif','png','bmp','tiff','psd'];//创建常见图片格式数组
		if(fileData.type == "txt"){
				$.get(
				    basePath+ 'fm/document/preview/text/'+ fileData.id,//文件预览地址
				    function(response){
						Ext.create('app.fm.ImgPreviewWindow',{thisString:response.data}).show();		    	
				    }
				);
		}else if(imgFormat.indexOf(fileData.type) != -1){
				imgAndTxtViewWindow = Ext.create('app.fm.ImgPreviewWindow',{thisUrl: basePath+"fm/document/preview/image/"+fileData.id}).show();
		}else{
			window.open(basePath+'fm/document/preview/'+Ext.getCmp('documentMenu').rowId); //在新页面打开
		}	*/
		window.open(basePath+'fm/document/preview/'+ r.data.id); //在新页面打开
    },
    
    onDetailClick:function(){
    	var record = Ext.getCmp('documentMenu').record; 
    	Ext.getCmp('fileManagementPanel').changePanel('app.fm.EditDocumentForm',{documentId:record.data.id});
    },
    
    onDownloadClick:function(){
    	window.location.href = basePath+'fm/document/download/'+Ext.getCmp('documentMenu').record.data.id
    	$.get(basePath+'fm/document/download/'+Ext.getCmp('documentMenu').record.data.id,function(data){
    		if(data.success){
    			$.popMsg("alert-success","下载成功");
    		}else{
    			$.popMsg("alert-warn","下载失败：当前文档资源未找到！")
    		}
    	});
    },
    
    onSimilarSearchClick:function(){
    },
    
    /**
     * 剪切事件
     */
    onCutClick:function(){
    	var record = Ext.getCmp('documentMenu').record;   	
    	var cRecord =  Ext.getCmp('categoryTree').getSelectionModel().getSelection()[0];
    	$.get(basePath+'fm/document/cut/'+record.data.id,{curCid:cRecord.data.id},function(data){
    		if(data.success){
    			$.popMsg("alert-success","已剪切");    			
    		}else{
    			$.popMsg("alert-warn","剪切失败");
    		}
    	});
    },
    
    onCopyClick:function(){
    	var record = Ext.getCmp('documentMenu').record;   	
    	var cRecord =  Ext.getCmp('categoryTree').getSelectionModel().getSelection()[0];
    	$.get(basePath+'fm/document/copy/'+record.data.id,{curCid:cRecord.data.id},function(data){
    		if(data.success){
    			$.popMsg("alert-success","已复制");    			
    		}else{
    			$.popMsg("alert-warn","复制失败");
    		}
    	});
    },
    
    /**
     * 自动分类事件
     */
    onClassifyClick: function(){
    	var record = Ext.getCmp('documentMenu').record;   
    	$.get(basePath+'fm/document/classify/'+record.data.id,function(data){
    		if(data.data.id.length > 0){
    			var cRecord = Ext.getCmp('categoryTree').getSelectionModel().getSelection()[0]; 
    			if(cRecord.data.id == data.data.id){
    				Ext.Msg.alert('提示', '当前分类和推荐分类一致');
    			}else{
					Ext.MessageBox.confirm('确认', '推荐分类:'+data.data.name+'<br>是否移动至推荐目录', function(e){
                		if(e=="yes"){
                			$.get(basePath+'fm/document/move/'+record.data.id,{'destId':data.data.id},function(r){
								$.popMsg("alert-success","移动成功"); 
                			});
                		}
                	});
    			} 			
    		}else{
    			Ext.Msg.alert('提示', '自动分类未找到合适的分类');
    		}
    	});    	
    },
    
    /**
     * 文档删除事件
     */
    onDeleteClick:function(){
    	var record = Ext.getCmp('documentMenu').record;   
    	$.get(basePath+'fm/document/delete/'+record.data.id,function(data){
    		if(data.success){
    			var node = Ext.getCmp('documentGrid').getSelectionModel().getSelection()[0];
        		Ext.getCmp('documentGrid').getStore().remove(node);
    			$.popMsg("alert-success","删除成功");    			
    		}else{
    			$.popMsg("alert-warn","删除失败");
    		}
    	});    	
    },
    
    onIndexUpdateClick:function(){
    	var record = Ext.getCmp('documentMenu').record;   
    	$.get(basePath+'fm/document/index/update/'+record.data.id,function(data){
    		if(data.success){
    			var node = Ext.getCmp('documentGrid').getSelectionModel().getSelection()[0];
        		Ext.getCmp('documentGrid').getStore().remove(node);
    			$.popMsg("alert-success","索引更新成功");    			
    		}else{
    			$.popMsg("alert-warn","索引更新失败");
    		}
    	});    	
    },
    
    onDeletemClick:function(){
    	var record = Ext.getCmp('documentMenu').record;   
    	$.get(basePath+'fm/document/deletem/'+record.data.id,function(data){
    		if(data.success){
    			var node = Ext.getCmp('documentGrid').getSelectionModel().getSelection()[0];
        		Ext.getCmp('documentGrid').getStore().remove(node);
    			$.popMsg("alert-success","彻底删除成功");    			
    		}else{
    			$.popMsg("alert-warn","彻底删除失败");
    		}
    	});    	
    },
    
    onDeleteFromCategoryClick:function(){
    	var record = Ext.getCmp('documentMenu').record;  
    	var cRecord = Ext.getCmp('categoryTree').getSelectionModel().getSelection()[0];
    	$.get(basePath+'fm/document/deleteFromCategory',{"documentId":record.data.id,"categoryId":cRecord.data.id},function(data){
    		if(data.success){
    			var node = Ext.getCmp('documentGrid').getSelectionModel().getSelection()[0];
        		Ext.getCmp('documentGrid').getStore().remove(node);
    			$.popMsg("alert-success","已从该分类中删除");    			
    		}else{
    			$.popMsg("alert-warn","删除失败");
    		}
    	});    	
    },
    
    onAddBookmarkClick:function(){
    	var cRecord = Ext.getCmp('categoryTree').getSelectionModel().getSelection()[0];
    	var record = Ext.getCmp('documentMenu').record;   
    	$.get(basePath+'fm/bookmark/add/'+record.data.id+'?categoryId='+cRecord.data.id,function(data){		
    		if(data.success == true){
    			$.popMsg("alert-success",data.msg);  
    			Ext.getCmp('bookmarkGrid').refresh();
    		}else{
    			$.popMsg("alert-warn",data.msg);
    		}
    	});
    },
    
    onSendMailClick:function(){
    },
    
    onCheckInClick:function(){
    	if(checkInWindow==null){
			checkInWindow = Ext.create('app.fm.version.CheckInWindow');
		}
		checkInWindow.show();
    },
    
    onCheckOutClick:function(){
    	var record = Ext.getCmp('documentMenu').record;   
    	if(record.data.locked==true){
    		$.popMsg("alert-warn","锁定状态无法检出");
    		return false;
    	}
    	$.get(basePath+'fm/version/lock/'+record.data.id,function(data){
    		if(data.success){
    			var node = Ext.getCmp('documentGrid').getSelectionModel().getSelection()[0];
    			node.set('locked',true); 
    			window.location.href = basePath+'fm/document/download/'+node.data.id;  	
    		}else{
    			$.popMsg("alert-warn","检出失败");
    		}
    	}); 
    	
    },
    
    onLockClick:function(){
    	var record = Ext.getCmp('documentMenu').record;   
    	if(record.data.locked==true){
    		$.popMsg("alert-warn","已经处于上锁状态");
    		return false;
    	}
    	$.get(basePath+'fm/version/lock/'+record.data.id,function(data){
    		if(data.success){
    			var node = Ext.getCmp('documentGrid').getSelectionModel().getSelection()[0];
    			node.set('locked',true);
    			$.popMsg("alert-success","加锁成功");    			
    		}else{
    			$.popMsg("alert-warn","加锁失败");
    		}
    	});  
    },
    
    onUnlockClick:function(){
    	var record = Ext.getCmp('documentMenu').record;   
    	if(record.data.locked==false){
    		$.popMsg("alert-warn","已经处于解锁状态");
    		return false;
    	}
    	$.get(basePath+'fm/version/unlock/'+record.data.id,function(data){
    		if(data.success){
    			var node = Ext.getCmp('documentGrid').getSelectionModel().getSelection()[0];
    			node.set('locked',false);
    			$.popMsg("alert-success","解锁成功");    			
    		}else{
    			$.popMsg("alert-warn","解锁失败");
    		}
    	});  
    }
});

