define(function(require, exports, module){
	var unit = require('javascript/unit/unit');
	
	exports.init = function() {
		//弹出层选择负责人
		$("#select_director_btn").click(function() {
			unit.initSelectOfficer("directorId","directorName");
		});
		//弹出层选择联系人
		$("#select_linkman_btn").click(function(){
			unit.initSelectOfficer("linkmanId","linkmanName");
		});
		//弹出层选择部门负责人
		$("#select_sDirector_btn").click(function() {
			unit.initSelectOfficer("sDirectorId","sDirectorName");
		});
		//弹出层选择部门联系人
		$("#select_sLinkman_btn").click(function(){
			unit.initSelectOfficer("sLinkmanId","sLinkmanName");
		});
		//弹出层选择上级管理部门
		$("#select_subjection_btn").click(function(){
			popSelect({
				type : 21,
				title : "选择上级管理部门",
				inData : {"id" : $("#subjectionId").val(), "name" : $("#subjectionName").html()},
				callBack : function(result){
					$("#subjectionId").val(result.data.id);
					$("#subjectionName").html(result.data.name);
				}
			});
		});
		
		//弹出层选择学科门类
		$(".selectDisciplineType").live('click', function(){
			unit.selectDiscilineType();
		});
		
		//弹出层选择学科
		$(".selectDiscipline").live('click', function(){
			var relyDispId = $(this).next();
			popSelect({
				type : 12,
				inData : relyDispId.attr("value"),
				callBack : function(result){
					doWithXX(result, relyDispId.attr("id"), relyDispId.next().attr("id"));
					$(document.forms[0]).valid();
				}
			});
		});
	};
});