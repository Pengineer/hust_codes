define(function(require, exports, module) {

	exports.init = function() {
		$("input[type='radio'][name='thirdUplodState'][value=" + $("#thirdUplodState").val() + "]").attr("checked", true);
	};

});