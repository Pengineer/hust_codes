/**
 * @author 肖雅
 */

define(function(require, exports, module) {
	var timeValidate = function(deadline) {
		var deadline= new Date(Date.parse(deadline.replace(/-/g, "/"))); //转换成Date();
		var date = new Date();
		date.setDate(date.getDate()-1);
		if (date > deadline){
			alert("当前时间超过截止时间，不能进行此操作！"); 
		 	return false;
		}
		return true;
	};
	
	exports.timeValidate = function(deadline) {
		return timeValidate(deadline);
	};
});

