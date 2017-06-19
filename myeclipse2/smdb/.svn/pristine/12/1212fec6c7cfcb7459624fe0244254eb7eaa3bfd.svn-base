/**
 * 用于添加奖励申请
 * @author 王燕
 */
define(function(require, exports, module) {
	require('javascript/engine');
	require('dwr/util');
	require('dwr/interface/awardService')
	require('tool/poplayer/js/pop');
	require('pop-self');
	
	var app = function(data){//选择以个人名义申报或以团队等名义申报
		if(data==1){
			$('#individual').show();
			$('#organization').hide();
		}else{
			$('#individual').hide();
			$('#organization').show();
		}
	};
	var applyType = function(data){//选择在线申报或者离线申报
		if(data==1){
			$('#basic_main').show();
			$('#award_main').hide();
		}else{
			$('#basic_main').hide();
			$('#award_main').show();
		}
		$.cookie("onLineOrNot", data);
	};
	var addOrNot = function(data){//是否从已有成果中选择
		if(data==1){
			$('#exist_product').show();
			$('#add_product').css("visibility", "hidden");
		}else{
			$('#exist_product').hide();
			$('#add_product').css("visibility", "visible");
		}
	};
	var loadSession = function(id){//获得获奖届次下拉框
		for(var i=1;i<30;i++){
			DWRUtil.addOptions(id, [{name:'第'+Num2Chinese(parseInt(i))+'届 ',id:i}], 'id', 'name');
		}
	};
	module.exports = {
		app: function(data){app(data)},
		applyType: function(data){applyType(data)},
		addOrNot: function(data){addOrNot(data)},
		loadSession: function(id){loadSession(id)}
	};
});
