// ========================================================================
// 文件名：resume_validate.js

// ========================================================================

$(document).ready(function(){	
	$("#resume_form").validate({
		errorElement: "label",
		event: "blur",
//		onblur: true,
		rules:{
			"resume.resumeName":{required:true, isCnChar:true, rangelength:[2,20]},
			"resume.name":{required:true, isCnName:true, rangelength:[2,20]},
			"resume.birthday":{dateISO:true},
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