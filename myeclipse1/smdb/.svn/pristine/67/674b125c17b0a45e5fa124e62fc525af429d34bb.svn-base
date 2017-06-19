define(function(require, exports, module) {
	require('validate');
	
	exports.valid = function(){
		$("#form_dm").validate({
			errorElement: "span",
			event: "blur",
			rules:{
				"predicType" : {selected:true},
				"predictYear" : {required:true},
				"predictorVariables" : {required:true, minlength:1}
			},
			errorPlacement: function(error, element) {
				if (element.is(":checkbox")){
//					error.appendTo( element.parent("td").parent("tr").parent("tbody").parent("table").parent("td").next("td") );
					error.appendTo( $("#cb_error") );
				}else{
					error.appendTo( element.parent("td").next("td") ); 
				}
			}
		});
	};
});
