/**
 * 用于初级检索和高级检索
 * @author 余潜玉
 */
define(function(require, exports, module) {
	require('javascript/engine');
	require('dwr/util');
	require('dwr/interface/awardService');
	var list = require('javascript/list');
	require('tool/poplayer/js/pop');
	require('pop-self');
	
	var loadSession = function (id){//获得获奖届次下拉框
		for(var i=1;i<30;i++){
			DWRUtil.addOptions(id,[{name:'第'+Num2Chinese(parseInt(i))+'届 ',id:i}],'id','name');
		}
	};
	
	var init = function(){
		$(function() {
			$(".viewapplicant").live("click", function() {//查看申请人详细信息
				popPerson(this.id, 7);
				return false;
			});
			$(".viewuniversity").live("click", function() {//查看所属高校详细信息
				popAgency(this.id, 1);
				return false;
			});
		});
	};
	
	module.exports = {
		loadSession: function(id){loadSession(id)},
		init: function(){init()}
	};
});
