define(function(require, exports, module) {
	var view = require('javascript/view');
	var list = require('javascript/list');
	var nameSpace = "project/general/application/applyStrict";
	
	var minYear = 2005;
	
	var showList = function() {
		list.pageShow({
			"nameSpace":nameSpace,
			"sortColumnId":["sortcolumn0","sortcolumn1","sortcolumn2","sortcolumn3","sortcolumn4","sortcolumn5","sortcolumn6",
			                "sortcolumn7","sortcolumn8","sortcolumn9","sortcolumn10"],
			"sortColumnValue":{"sortcolumn0":0,"sortcolumn1":1,"sortcolumn2":2,"sortcolumn3":3,"sortcolumn4":4,"sortcolumn5":5,
								"sortcolumn6":6,"sortcolumn7":7,"sortcolumn8":8,"sortcolumn9":9,"sortcolumn10":10}
		});
	}
	
	var parmsParse = function() {
		$("#moeTilt1").attr('value', $("#moeTilt").val());
		$("#westTilt1").attr('value', $("#westTilt").val());
		$("#grweight1").attr('value', $("#grweight").val());
		$("#moweight1").attr('value', $("#moweight").val());
		$("#eoweight1").attr('value', $("#eoweight").val());
		$("#asweight1").attr('value', $("#asweight").val());
		$("#strictAppYear").attr('value', $("#strictAppYear").val());
		$("#univStrictLB1").attr('value', $("#univStrictLB").val());
		$("#annStrictTarget1").attr('value', $("#annStrictTarget").val());
		$("#graYearScope1").attr('value', $("#graYearScope").val());
		$("#graStartYear1").attr('value', $("#graStartYear").val());
		$("#graEndYear1").attr('value', $("#graEndYear").val());
		$("#midYearScope1").attr('value', $("#midYearScope").val());
		$("#midStartYear1").attr('value', $("#midStartYear").val());
		$("#midEndYear1").attr('value', $("#midEndYear").val());
		$("#endYearScope1").attr('value', $("#endYearScope").val());
		$("#endStartYear1").attr('value', $("#endStartYear").val());
		$("#endEndYear1").attr('value', $("#endEndYear").val());
		$("#bookAwardFir1").attr('value', $("#bookAwardFir").val());
		$("#paperAwardFir1").attr('value', $("#paperAwardFir").val());
		$("#achPopuAward1").attr('value', $("#achPopuAward").val());
		$("#bookAwardSec1").attr('value', $("#bookAwardSec").val());
		$("#paperAwardSec1").attr('value', $("#paperAwardSec").val());
		$("#bookAwardThi1").attr('value', $("#bookAwardThi").val());
		$("#paperAwardThi1").attr('value', $("#paperAwardThi").val());
		$("#ResAdoAwardFir1").attr('value', $("#ResAdoAwardFir").val());
		$("#ResAdoAwardSec1").attr('value', $("#ResAdoAwardSec").val());
		$("#ResAdoAwardThi1").attr('value', $("#ResAdoAwardThi").val());
	}
	
	var compare = function (obj1, obj2) {
		if(obj1.val() > obj2.val()) {
			obj2.val(obj1.val());
		}
	}
	
	var initData = function () {
		$("#graStartYear").attr('value', (($("#strictAppYear").val() - $("#graYearScope").val()) > minYear) ? ($("#strictAppYear").val() - $("#graYearScope").val()) : minYear);
		$("#graEndYear").attr('value', (($("#strictAppYear").val() - 1) > minYear) ? ($("#strictAppYear").val() - 1) : minYear);
		$("#midStartYear").attr('value', (($("#strictAppYear").val() - $("#midYearScope").val() - 3) > minYear) ? ($("#strictAppYear").val() - $("#midYearScope").val() - 3) : minYear);
		$("#midEndYear").attr('value', (($("#strictAppYear").val() - 4) > minYear) ? ($("#strictAppYear").val() - 4) : minYear);
		$("#endStartYear").attr('value', (($("#strictAppYear").val() - $("#endYearScope").val() - 6) > minYear) ? ($("#strictAppYear").val() - $("#endYearScope").val() - 6) : minYear);
		$("#endEndYear").attr('value', (($("#strictAppYear").val() - 7) > minYear) ? ($("#strictAppYear").val() - 7) : minYear);
	}
	
	exports.init = function() {
		initData();
		parmsParse();
		view.inittabs();
		showList();
		$("#submit").live("click", function() {
			$("#flag").attr('value', 1);
			parmsParse();
			$("#search").submit();
		});
		$("#clear").live("click", function() {
			$("#flag").attr('value', 2);
			parmsParse();
			$("#search").submit();
		});
		
		$("#setDefault").live("click", function() {
			$("#flag").attr('value', 0);
			parmsParse();
			$("#search").attr("action", nameSpace + "/setDefault.action");
			$("#search").submit();
		});
		$("#default").live("click", function() {
			window.location.href = basePath + "/project/general/application/applyStrict/toList.action";
		});
		$("#reset").live("click", function() {
			$("#moeTilt, #westTilt, #grweight, #moweight, #eoweight, #asweight, #bookAwardFir, #paperAwardFir, #achPopuAward, " +
					"#bookAwardSec, #paperAwardSec, #bookAwardThi, #paperAwardThi, #ResAdoAwardFir, " +
					"#ResAdoAwardSec, #ResAdoAwardThi, #moeTilt1, #westTilt1, #grweight1, #moweight1, #eoweight1, #asweight1, " +
					"#bookAwardFir1, #paperAwardFir1, #achPopuAward1, #bookAwardSec1, #paperAwardSec1, #bookAwardThi1, #paperAwardThi1, " +
					"#ResAdoAwardFir1, #ResAdoAwardSec1, #ResAdoAwardThi1").attr('value', 0.0);
			$("#univStrictLB, #annStrictTarget, #univStrictLB1, #annStrictTarget1").attr('value', 0);
			$("#graYearScope, #midYearScope, #endYearScope, #graYearScope1, #midYearScope1, #endYearScope1").attr('value', 1);
			$("#graStartYear, #graEndYear, #midStartYear, #midEndYear, #endStartYear, #endEndYear, #graStartYear1, #graEndYear1, " +
					"#midStartYear1, #midEndYear1, #endStartYear1, #endEndYear1").attr('value', minYear);
		});
		$("#strictAppYear, #graYearScope, #midYearScope, #endYearScope").change(function(){
			initData();
		});
		$("#graStartYear, #graEndYear").change(function(){
			compare($("#graStartYear"), $("#graEndYear"));
			$("#graYearScope").attr('value', $("#graEndYear").val() - $("#graStartYear").val());
		});
		$("#midStartYear, #midEndYear").change(function(){
			compare($("#midStartYear"), $("#midEndYear"));
			$("#midYearScope").attr('value', $("#midEndYear").val() - $("#midStartYear").val());
		});
		$("#endStartYear, #endEndYear").change(function(){
			compare($("#endStartYear"), $("#endEndYear"));
			$("#endYearScope").attr('value', $("#endEndYear").val() - $("#endStartYear").val());
		});
		$(".viewDetail").live("mouseover",function() {
			$(this).css({
				"cursor":"default"
			});
			var offBottom = (($(document).height() - $(this).offset().top - 190) > 0) ? ($(document).height() - $(this).offset().top - 190) : 0;
			$(this).children().css({
				"display":"",
				"bottom":offBottom 
			});
		}).live("mouseout",function(){
			$(this).css({
				"cursor":""
			});
			$(this).children().css({
				"display":"none"
			});
		});
		$("#list_query").live("click", function() {
			var keyword = $("#keyword").val().trim();
			$("#keyword").val(keyword);
			parmsParse();
			$("#list_first, #list_previous, #list_next, #list_last").attr("disabled", true);
			$("#search").submit();
		});
		$("#export").live("click", function(){
			window.location.href = basePath + "/project/general/application/applyStrict/confirmExportOverView.action";
		});
	};
});