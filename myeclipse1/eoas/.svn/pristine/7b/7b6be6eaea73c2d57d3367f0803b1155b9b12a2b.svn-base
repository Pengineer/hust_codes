// ========================================================================
// 文件名：resume_validate.js

// ========================================================================

$(document).ready(function(){	
//	alert("aaaaa");
	$("#form_resume").validate({
		errorElement: "label",
		event: "blur",
//		onblur: true,
		rules:{
			"resume.resumeName":{required:true},
			"resume.name":{required:true, isName:true},
			"resume.mobilephone":{required:true, isCellPhone:true},	
			"resume.hometown":{maxlength:40},
			"resume.idcardNumber":{required:true, isIdCardNumber:true},
		}
	});
	
	
	
/*	function readURL(input) {

	    if (input.files && input.files[0]) {
	        var reader = new FileReader();

	        reader.onload = function (e) {
	            $('#blah').attr('src', e.target.result);
	        }

	        reader.readAsDataURL(input.files[0]);
	    }
	}

	$("#imgInp").change(function(){
	    readURL(this);
	});*/
	
	
});