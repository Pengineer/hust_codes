define(function(require, exports, module) {
	require('validate');
	
	exports.valid = function() {
		$("#form_config").validate({
			errorElement: "span",
			event: "blur",
			rules:{
				"awardSession":{digits:true},
				"specialAwardScore":{number:true},
				"firstAwardScore":{number:true},
				"secondAwardScore":{number:true},
				"thirdAwardScore":{number:true},
				"popularAwardScore":{number:true},
				"allowedIp":{},
				"refusedIp":{},
				"maxSession":{digits:true},
				"tempUploadPath":{},
				"UserPictureUploadPath":{},
				"NewsFileUploadPath":{},
				"NoticeFileUploadPath":{},
				"MailFileUploadPath":{},
				"teacherRegister":{selected:true},
				"pages":{digits:true},
				"rows":{digits:true}
				
				
			},
			errorPlacement: function(error, element) { 
				error.appendTo( element.parent("td").next("td") ); 
			}
		});
	};
});
