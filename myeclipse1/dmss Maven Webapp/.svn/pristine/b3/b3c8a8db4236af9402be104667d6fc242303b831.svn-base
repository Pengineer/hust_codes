var basePath = '';
Ext.Loader.setConfig({  
      enabled: true,  
      paths: {  
          'NS': 'app'  
      }  
});

/**
 * 弹出窗口变量定义
 */
var systemPanel = Ext.create('app.sm.SystemPanel');
var fileManagementPanel = Ext.create("app.fm.FileManagementPanel");
var documentMenu;
var folderMenu;
var uploadWindow; //上传文档弹出窗
var imgAndTxtViewWindow;//图片和文本预览弹出窗
var checkInWindow; //检入文档弹出窗
var createCategoryWindow; //后台导入窗口
var chooseCategoryWindow; //分类选择窗口
var addAccountWindow; //账号添加窗口
//系统管理窗口
var backgroundImportWindow; //后台导入窗口
var win;  
var RIGHTS; //extjs权限控制
Ext.onReady(function(){	
	Ext.application({
	    name: 'app',
	    launch: function() {
	        var webFrame = Ext.create('Ext.container.Viewport', {
	        	layout: 'border',
	        	height:2000,
	        	padding : '0 0 0 0',
	        	items : [{
	        		xtype : 'panel',
	        		border : false,
	        		height : '30',
	        		region : 'south',
	        		html : '<div class="footer"><div class="inner">中国高校社会科学数据中心 Chinese University Social Sciences Data Center</div>\
							<div class="inner">Copyright©2009-2011 All Rights Reserved.</div>'		
	        	}]
	        });
	        
	        //标题栏
	        var header = Ext.create('Ext.Panel',{
	        	region : 'north',
				height : 60,
				layout : {
			        type: 'hbox',
			        align: 'left'
		    	},
				border : false,
				items : [{
					xtype : 'container',
					html : '<h1 class="title">文档管理服务系统</h1>'
				}]
			});
			webFrame.add(header);
			
			//内容区
			var content = Ext.widget('tabpanel', {
				id : 'content',
				region:'center',
				tabBar:{            
		            items:[  
		            { xtype: 'tbfill' },
		            { xtype:'label',
		            text:'欢迎您登录 ['+username+']',
		            padding:"10 10 auto auto"
		            },
		               {
		                xtype:'button',               
		                text:'注销',
		                margin:"5 10 auto auto",
		                listeners:{
		                    'click':function(){
		                    	Ext.MessageBox.confirm('确认', '你确认要退出?', function(e){
		                    		if(e=="yes"){
		                    			window.location.href = basePath+"logout";
		                    		}
		                    	});
		                    }
		                }
		            }] 
		        },
		        activeTab: 0,
		        plain: true,
		        defaults :{
		            bodyPadding: 0
		        },
		        items: [{
		        	xtype : 'panel',
		        	border : false,
		        	layout : 'border',
		            title: app.string.fileManagement,
		            items : [fileManagementPanel]
		        },{ 
		            title: app.string.fileSearch,
		            html : '<iframe id="s_frame" width="100%" height="100%" frameborder=0 scrolling=auto src="search.html"></iframe>' 
		        },{ 
		        	xtype : 'panel',
		        	border : false,
		        	layout : 'border',
		            title: app.string.systemManagement,
		            items: [systemPanel]
		        }]
		    });
		    webFrame.add(content);
		    
	    }
	});
	
});


/**
 * 验证表单错误
 * @param {} action
 */
function validateFormErrors(action){
	var s="";
	for (var error in action.result.data.errors){
		s+=action.result.data.errors[error]+"<br>";
		
	}
	Ext.Msg.alert('警告', s);
}

//check-tree 
/*向上遍历父结点*/     
var nodep= function(node){            
	    var bnode=true;     
	    Ext.Array.each(node.childNodes,function(v){   
		    if(!v.data.checked){          
		    	bnode=false;       
				return;         	
			}      
		});     
		 return bnode;     
	};
	
var parentnode= function(node){     
	 if(node.parentNode != null){     
		  if(nodep(node.parentNode)){      
			  node.parentNode.set('checked', true);    
		  }else{      
			  node.parentNode.set('checked', false);     
     	  }       
   		  parentnode(node.parentNode);       
  	 }      
};
	
var chd=function(node,check){     
    node.set('checked',check);      
	if(node.isNode){        
		node.eachChild(function (child) {                
		chd(child,check);         
		});      
	}      
}
    