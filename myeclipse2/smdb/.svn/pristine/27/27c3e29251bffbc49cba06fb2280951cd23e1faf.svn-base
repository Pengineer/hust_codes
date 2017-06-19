define(function(require, exports, module) {
	require('pop-init');
	
	exports.init = function() {
		$(document).ready(function(){
			var ethnicLanguage = thisPopLayer.inData, disp = "";

			if (ethnicLanguage.match(/([\u0391-\uFFE5]+\/[\u0391-\uFFE5]+; )*[\u0391-\uFFE5]+\/[\u0391-\uFFE5]+/g)){
				var tmp = ethnicLanguage.split(/; */g);
				for (var item in tmp){
					disp += "<span style='margin-right:15px'>" + tmp[item] + "<a class='del'><img src='image/del.gif' /></a></span>";
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
						disp += "<span style='margin-right:15px'>" + $("td:first-child", $(this)).html() + "/" + $level.html() + "<a class='del'><img src='image/del.gif' /></a></span>";
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

			$("#confirm").unbind("click").click(function(){
				var ethnicLanguage = "";
				$("#disp > span").each(function(){
					ethnicLanguage += (ethnicLanguage.length ? "; " : "") + $(this).text();
				});
				thisPopLayer.outData = {data : {"ethnicLanguage" : ethnicLanguage}};
				thisPopLayer.callBack(thisPopLayer.outData);
				thisPopLayer.destroy();
			});
		});
	};
});
