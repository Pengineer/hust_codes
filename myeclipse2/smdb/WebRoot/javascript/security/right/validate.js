define(function(require, exports, module) {
	require('validate');
	
	exports.valid = function(){
		$("#form_right").validate({
			errorElement: "span",
			event: "blur",
			rules:{
				"right.name":{required:true, maxlength:200},
				"right.code":{required:true, maxlength:200},
				"right.description":{required:true, maxlength:800},
				"right.nodevalue":{required:true, nodeValue:true},
				"keyword1":{maxlength:200},
				"keyword2":{maxlength:800},
				"keyword3":{maxlength:40},
				"keyword4":{nodeValue:true}
			},
			errorPlacement: function(error, element) { 
				error.appendTo( element.parent("td").next("td") ); 
			}
		});
	};
});
