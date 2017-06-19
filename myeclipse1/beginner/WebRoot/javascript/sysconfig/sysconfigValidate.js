// ========================================================================
// 文件名：selfspaceValidate.js
//
// 文件说明：
//     本文件主要实现个人资料模块中的修改密码验证。
//
// 历史记录：
// 2010-04-22  周中坚     创建文件
// ========================================================================

$(document).ready(function(){
	$("#form_sysconfig").validate({
		event: "blur",
		rules:{
			DisplayNumberEachPage:{required:true, digits:true, range:[1,100]},
			UserPictureUploadPath:{required:true},
			NewsFileUploadPath:{required:true},
			NoticeFileUploadPath:{required:true},
			SelfFileUploadPath:{required:true},
			value:{required:true, maxlength:10}//下拉框选项名称
			}
	});
});
