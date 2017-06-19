/**
 * @author liujia
 * @date 2015/8/7
 * @descrioption 论坛投稿
 */

/*  ajax表单提交使用说明: 
 * 
var options = {
	target: '#output1', // target element(s) to beupdated with server response 
	beforeSubmit: showRequest, // 提交之前的回调
	callback success: showResponse // post-submit callback
	url: url // override for form's 'action'attribute 
	type: type // 'get' or 'post', override for form's 'method' attribute
	dataType: null // 'xml', 'script', or 'json' (expected serverresponse type) 
	clearForm: true // clear all form fields after successful submit 
	resetForm: true // reset the form after successful submit
	// $.ajax options can be used here too, for example: //timeout: 3000 };
	// bind form using 'ajaxForm' $('#myForm1').ajaxForm(options);
}

*/
define(function(require, exports, module){
	require("validation");
	require("validationInit");
	require("uploadify");
	require("uploadify-ext");
	require("form");
	require('datePicker');
	/* 初始化校验并设置表单为AJAX提交方式. */
	$("#forumContribute").validationEngine({
		scroll: false,
		isOverflown:true,
		validateNonVisibleFields: true,
		promptPosition: 'centerRight',
		addPromptClass: 'formError-noArrow formError-text',
	}).ajaxForm({
		type: "post",
		dataType: "json",
		success: function(data) {
			if (data.tag == "1") {
				dialog({
					title: '提示',
					content: '投稿成功!',
					okValue: '确定',
					width: 260,
					ok: function() {
						window.location.href = "";
					}
				}).showModal();
			} else {
				dialog({
					title: '提示',
					content: '投稿失败,请稍后再试!',
					width: 260,
					okValue: '确定',
					ok: function() {
						window.location.reload();
					}
				}).showModal();
			}
		}
	});
	
	/*初始化dataPicker */
	$("#forumContribute .dataPicker").each(function(){
		$(this).datepicker({
			language: "zh-CN",
		    autoclose: true,
		    todayHighlight: true
		});
	});
	
	/* 初始化上传控件 */
	$("#solicitPapersUpload").uploadifyExt({
		uploadLimitExt: '1', // 文件上传个数的限制
		fileSizeLimit: '100MB', // 文件上传大小的限制
		fileTypeDesc: '附件', // 可以不用管
		fileTypeExts: '*.rar;*.zip;',// 限制为压缩文件上传格式
		buttonText : '上传论文', // 按钮上的文字
		buttonClass: "btn btn-default",
		hideButton : true,
		buttonImg: " ",
		preventCaching: false,
		multi: false,
		onUploadSuccess:function(file,data,response){
			$("#" + file.id).attr("fileId", $.parseJSON(data).fileId);
			$("#" + file.id + " div.uploadify-progress").remove();
			$("#" + this.settings.id).parents("td").find(".validation-file-input").val(file.name).validationEngine('hide');
			$
		}
	});
});

