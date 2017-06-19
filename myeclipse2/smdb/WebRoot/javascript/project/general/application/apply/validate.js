define(function(require, exports, module) {
	require('validate');
	
	exports.valid = function() {
		$("#form_common").validate({
			errorElement: "span",
			event: "blur",
			rules:{
				"strictAppYear":{required:true, year:true, min:2005},
				"moeTilt":{required:true, number:true, min:0},
				"westTilt":{required:true, number:true, min:0},
				"univStrictLB":{required:true, digits:true},
				"annStrictTarget":{required:true, digits:true},
				"grweight":{required:true, number:true, min:0},
				"moweight":{required:true, number:true, min:0},
				"eoweight":{required:true, number:true, min:0},
				"asweight":{required:true, number:true, min:0},
				"graYearScope":{required:true, digits:true, min:1},
				"midYearScope":{required:true, digits:true, min:1},
				"endYearScope":{required:true, digits:true, min:1},
				"bookAwardFir":{required:true, number:true, min:0},
				"paperAwardFir":{required:true, number:true, min:0},
				"achPopuAward":{required:true, number:true, min:0},
				"bookAwardSec":{required:true, number:true, min:0},
				"paperAwardSec":{required:true, number:true, min:0},
				"bookAwardThi":{required:true, number:true, min:0},
				"paperAwardThi":{required:true, number:true, min:0},
				"ResPubAwardFir":{required:true, number:true, min:0},
				"ResAdoAwardFir":{required:true, number:true, min:0},
				"ResPubAwardSec":{required:true, number:true, min:0},
				"ResAdoAwardSec":{required:true, number:true, min:0},
				"ResPubAwardThi":{required:true, number:true, min:0},
				"ResAdoAwardThi":{required:true, number:true, min:0}
			},
			errorPlacement: function(error, element) { 
				error.appendTo(element.parent());
			}
		});
	};
});
