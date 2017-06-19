define(function(require, exports, module) {
	require('javascript/step_tools');
	require('validate');
	var validate = require('javascript/person/validate');
	require('tool/poplayer/js/pop');
	require('pop-self');
	
	var setting = new Setting({

		id: "academic",

		on_in_opt: function(){
			validate.valid_academic();
		},

		out_check: function(){
			return $("#form_person").valid();
		}
	});
	
	var init = function(){
		// 子页面初始化接口
		$("#select_ethnicLanguage_btn").click(function(){
			var $here = $(this);
			popSelect({
				type : 9,
				inData : $("[name=academic.ethnicLanguage]").val(),
				callBack : function(result){
					$("[name=academic.ethnicLanguage]").val(result.data.ethnicLanguage);
					$("#ethnicLanguage").html(result.data.ethnicLanguage);
					$(document.forms[0]).valid();
				}
			});
		});

		$("#select_language_btn").click(function(){
			var $here = $(this);
			popSelect({
				type : 10,
				inData : $("[name=academic.language]").val(),
				callBack : function(result){
					$("[name=academic.language]").val(result.data.foreignLanguage);
					$("#language").html(result.data.foreignLanguage);
					$(document.forms[0]).valid();
				}
			});
		});

		doWithXX($("[name=academic.disciplineType]").val(), $("#selectDisciplineType").next().attr("id"), $("#selectDisciplineType").next().next().attr("id"));
		$("#selectDisciplineType").click(function(){
			var disp = $(this).next();
			popSelect({
				type : 11,
				inData : disp.attr("value"),
				callBack : function(result){
					doWithXX(result, disp.attr("id"), disp.next().attr("id"));
					$(document.forms[0]).valid();
				}
			});
		});
		
		doWithXX($("[name=academic.discipline]").val(), $("#selectDiscipline").next().attr("id"), $("#selectDiscipline").next().next().attr("id"));
		$("#selectDiscipline").click(function(){
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

		doWithXX($("[name=academic.relativeDiscipline]").val(), $("#selectRelativeDiscipline").next().attr("id"), $("#selectRelativeDiscipline").next().next().attr("id"));
		$("#selectRelativeDiscipline").click(function(){
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
	
	module.exports = {
		setting : setting,
		init : function(){init()}
	};
});
