define(function(require, exports, module){
	var nameSpace = "system/interfaces/sinossClient";
	function geneSourceYear(start, end){
		var htmlSegment = "";
		for(var i = start; i <= end; i++) {
			htmlSegment += "<option value = " + i + ">" + i + "</option>";
		}
		return htmlSegment;
	}
	$("#submit").click(function(){
		$.ajax({
			url: nameSpace + "/searchXml.action",
			data: $("#form_search").serialize(),
			dataType: "json",
			success: function(result){
					$("#showInfo").html(TrimPath.processDOMTemplate("viewXml_template", result));
				setOddEvenLine("showInfo", 0);//设置奇偶行效果
			}
		})
	});
	$(".downloadXml").live("click",function(){
		window.location.href = nameSpace + "/downloadXml.action?" + $("#form_search").serialize() + "&fileName=" +$(this).attr("id");
	});
	exports.init = function(){
		$(function(){
			$("#sourceYear").append(geneSourceYear(2013,(new Date()).getFullYear()));
			$("#sourceYear").val((new Date()).getFullYear());
		});
	}
});