/**
 * 招聘的模板添加和修改的校验功能。
 * @author liujia
 *
 */
define(function(require, exports, module) {
	require('validate');
	exports.valid = function(submit){
		$("#template").validate({
			errorElement: "span",
			event: "blur",
			rules:{
				"description":{maxlength: 200},
				"name":{required: true},
				"file":{selectedFile: true}
			},
			errorPlacement: function(error, element){
				error.appendTo( element.parent("td").next("td") );
			},
			submitHandler:function(form){  	          
	            form.action += $("#entityId").val();
	            form.submit();  
	        }    
			
		});
		
	}
});	