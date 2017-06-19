/**
 * 分类目录创建窗口
 */
Ext.define('app.fm.CreateCategoryWindow',{
	extend:'Ext.window.Window',
	id:'createCategoryWindow',
	title:'新建分类',
	width:300,
	layout:'fit',//设置窗口内部布局

	plain:true,//true则主体背景透明，false则和主体背景有些差别
	collapsible:true,//是否可收缩
	modal:true,//是否为模式窗体
	items:new Ext.FormPanel({//窗体中中是一个一个TabPanel
        bodyPadding: '10 10 0',
        border:false,
		
        defaults: {
            anchor: '100%',
            msgTarget: 'side',
            msgTarget: 'side',
			xtype: 'textfield'
        },

        items: [
	        {fieldLabel:'分类目录名',name:'name',allowBlank: false},
			{fieldLabel:'描述',name:'description'}
		],

        buttons: [{
            text: '保存',
            handler: function(){
            	var record = Ext.getCmp('categoryTree').getSelectionModel().getSelection()[0];
                var form = this.up('form').getForm();
                if(form.isValid()){
                    form.submit({
                        url: basePath+'fm/category/add',
                        params: {
					        pid: record.data.id
					    },
                        success: function(form, action) {
                            var node = Ext.getCmp('categoryTree').getSelectionModel().getSelection()[0];
                            node.appendChild(action.result.data.node);
                        },
                        failure:function(form,action){
                        	Ext.MessageBox.show({
					           title: '警告',
					           msg: action.result.msg,
					           buttons: Ext.MessageBox.OK,
					           animateTarget: 'categoryTree',
					           icon: Ext.MessageBox.WARNING
					       });
                        }
                    });
                }
                Ext.getCmp('createCategoryWindow').hide();
            }
        },{
            text: '重置',
            handler: function() {
                this.up('form').getForm().reset();
            }
        }]
	})
});