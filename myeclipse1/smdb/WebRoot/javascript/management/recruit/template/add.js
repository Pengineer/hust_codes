/**
 * 招聘的模板添加页面
 * @author liujia
 *
 */
define(function(require, exports, module){
	require("uploadify");
	require("uploadify-ext");
	var validate = require("./validate.js")
	var nameSpace = "management/recruit/template/";
	var validate = require("./validate.js");
	$("#template_add").uploadifyExt({
		uploadLimitExt: 1, //文件上传个数的限制
		fileSizeLimit: '20000MB', //文件上传大小的限制
		fileTypeDesc: '附件', //可以不用管
		fileTypeExts: '*.zip;*.rar;*.doc;*.docx;*.pdf',
		buttonClass: "upload",
        onUploadSuccess:function(file,data,response){
             $("#file").val("upload success!");
        }
	});

    $(".cancel .discard").live("click",function(){
   	 $("#file").val("");
    });
	$("#cancel").click(function(){
		history.back();
	});//取消函数，返回列表
	exports.init = function(){
		validate.valid();
	}
})