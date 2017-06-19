define(function(require, exports, module) {
	require('validate');
	
	exports.valid = function() {
		$("#form_mail").validate({
			errorElement: "span",
			event: "blur",
			rules:{
				"mail.subject":{required:true, maxlength:100},
				"mail.sendTo":{required:true},
				"mail.body":{required:true},
				"keyword1":{maxlength:40},
				"keyword2":{},
				"keyword3":{maxlength:40},
				"createDate1":{dateSMDB:true},
				"createDate2":{dateSMDB:true},
				"status":{}
				
			},
			errorPlacement: function(error, element) { 
				error.appendTo( element.parent("td").next("td") ); 
			}
		});
	};
});
