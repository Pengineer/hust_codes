/*
 * @author:Liujia
 * @description: 权限添加/修改
 * @date:2015/09/01
 */
define(function(require, exports, module){
	var nameSpace = "right";
	require("validation");
	require("validationInit");
	require('dhtmlxtree');
	require("form");
	$("#cancel").click(function(){
		window.history.go(-1);
	});
	exports.init = function(){
		$("#form_right").validationEngine({
			scroll: false,
			isOverflown:true,
			validateNonVisibleFields: true,
			promptPosition: 'centerRight',
			addPromptClass: 'formError-noArrow formError-text'
		}).ajaxForm({
			type: "post",
//			dataType: "json",
			beforeSubmit: function(){
			},
			success: function(data) {
				if (data.tag == "1") {
					dialog({
						title: '提示',
						content: '权限添加成功!',
						okValue: '确定',
						width: 260,
						ok: function() {
							window.location.href = nameSpace + "/toView.action?entityId=" + $("#entityId").val();
						},
						onclose: function(){
							window.location.href = nameSpace + "/toList.action";
						}
					}).showModal();
				} else {
					dialog({
						title: '提示',
						content: '权限添加失败!',
						width: 260,
						okValue: '确定',
						ok: function() {
//							window.location.reload();
						}
					}).showModal();
				}
			}
		});
	}
});