/**
 * 密码重置表单
 */
Ext.define('app.sm.ModifyPasswordForm',{
	extend: 'Ext.form.FormPanel',
	title: '密码修改',
	id:'modifyPasswordForm',
	url:basePath+'sm/selfspace/password/modify',
	waitMsgTarget: true,
	bodyPadding: '10 10 10 40', 
	border: false,
	defaults: {
		msgTarget: 'side',
		xtype: 'textfield'
	},
    
	items: [
		{fieldLabel:'原密码',id:'oldPassword',name:'oldPassword',inputType:'password',vtype:'password',allowBlank: false},
		{fieldLabel:'新密码',id:'newPassword', name:'newPassword',inputType:'password',vtype:'password',allowBlank: false},
		{fieldLabel:'确认新密码',id:'newPassword2',name:'newPassword2',inputType:'password',confirmTo:'newPassword',vtype:'passwordConfirm'}
	],
	buttons:[
		{xtype:'button',text:'保存',scope:this,handler:function(){
					    				form =Ext.getCmp('modifyPasswordForm').getForm();
										if (!form.isValid()) return;
					    				form.submit({
						    				url:basePath+'sm/selfspace/password/modify',
						    				method : 'post',   
						    				params:{
							    				id:2
							    			},
							    			waitMsg: '保存中...',
							    			failure:function(form,action){
							    				validateFormErrors(action);
							    			},
							    			success:function(){
							    				Ext.Msg.alert("已保存");
							    			}
						    			});
					               }}
	]

});