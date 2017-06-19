define(function(require, exports, module) {
	require('pop-init');
	
	exports.init = function() {
		$(document).ready(function(){
			var foreignLanguage = thisPopLayer.inData, disp = "";

			if (foreignLanguage.match(/(\S+\/\S+; )*\S+\/\S+/g)){
				var tmp = foreignLanguage.split(/; */g);
				for (var item in tmp){
					disp += "<span style='margin-right:15px'>" + tmp[item] + "<a class='del'><img src='"+basePath+"image/del.gif' /></a></span>";
					var a = tmp[item].split(/\//g);
					var $td = $("tr.el > td:first-child:contains('" + a[0] + "')").next();
					$("span", $td).removeClass("selected");
					$("span:contains('" + a[1] + "')", $td).addClass("selected");
				}
			}
			$("#disp").html(disp || '无');

			$(".table_view").show();

			$("td.level > span").click(function(){
				$("span", $(this).parent()).removeClass("selected");
				$(this).addClass("selected");

				var disp = "";
				$("tr.el").each(function(key, value){
					$("td:first-child", $(this)).html();
					var $level = $("span.selected:eq(0)", $(this));
					if ($level.html() != "不会"){
						disp += "<span style='margin-right:15px'>" + $("td:first-child", $(this)).html() + "/" + $level.html() + "<a class='del'><img src='"+basePath+"image/del.gif' /></a></span>";
					}
				});
				$("#disp").html(disp || '无');
			});

			$(".del").live("click", function(){
				var a = $(this).parent().text().split(/\//g);
				var $td = $("tr.el > td:first-child:contains('" + a[0] + "')").next();
				$("span", $td).removeClass("selected");
				$("span:eq(0)", $td).addClass("selected");
				$(this).parent().remove();
				if (!$(".del").length){
					$("#disp").html("无");
				}
			});
				
			$(".anchor").live("click",function(){
				var anchorId = $(this).attr("name");
//				var cd=getElementTop(document.getElementById(anchorId));
//				var cc = $("#"+anchorId);
				var ee = $("#"+anchorId).offset().top;
				var ff=$("#foreignScroll").scrollTop();
				document.getElementById("foreignScroll").scrollTop=ff+ee-24; //减去div上面内容的高度
				return false;
			});

			$("#confirm").unbind("click").click(function(){
				var foreignLanguage = "";
				$("#disp > span").each(function(){
					foreignLanguage += (foreignLanguage.length ? "; " : "") + $(this).text();
				});
				thisPopLayer.outData = {data : {"foreignLanguage" : foreignLanguage || "无"}};
				thisPopLayer.callBack(thisPopLayer.outData);
				thisPopLayer.destroy();
			});
		});
	};
});