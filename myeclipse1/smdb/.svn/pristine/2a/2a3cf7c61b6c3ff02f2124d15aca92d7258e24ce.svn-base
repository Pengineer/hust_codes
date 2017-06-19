define(function(require, exports, module) {
	// 在页面加载前生成a标签，用于显示列表成员信息
	var director = function() {
		$(".directors").each(function(){
			var $obj = $(this).parent("td");//当前所属td
			var memberIds = $(this).attr("value");
			var memberNames = $(this).attr("name");
			var arrayIds = new Array();
			var arrayNames = new Array();
			arrayIds = memberIds.split("; ");
			arrayNames = memberNames.split("; ");
			var arrayLen = arrayNames.length;
			for(var i = 0; i < arrayLen-1; i++){
				if(arrayIds[i] != ""){//人员id不为空
					$obj.append("<a id='" + arrayIds[i] + "' class='linkDirectors' href=''>"+ arrayNames[i]+ "</a>").append("; ");
				}else{//id为空无链接
					$obj.append(arrayNames[i]).append("; ");
				}
			}
			if(arrayIds[arrayLen-1] != ""){//人员id不为空
				$obj.append("<a id='" + arrayIds[arrayLen-1] + "' class='linkDirectors' href=''>"+ arrayNames[arrayLen-1]+ "</a>");
			}else{//id为空无链接
				$obj.append(arrayNames[arrayLen-1]);
			}
		});
	};
	var varDirector = function(){
		$(".var_director").each(function(){
			var $obj = $(this).parent("td");//当前所属td
			var directorIds = $(this).attr("value");
			var directorNames = $(this).attr("name");
			var arrayIds = new Array();
			var arrayNames = new Array();
			arrayIds = directorIds.split("; ");
			arrayNames = directorNames.split("; ");
			var arrayLen = arrayNames.length;
			for(var i = 0; i < arrayLen-1; i++){
				$obj.append("<a id='" + arrayIds[i] + "' class='linkVarDirectors' href=''>"+ arrayNames[i]+ "</a>").append("; ");
			}
			$obj.append("<a id='" + arrayIds[arrayLen-1] + "' class='linkVarDirectors' href=''>"+ arrayNames[arrayLen-1]+ "</a>");
		});
	};
	
	// 在页面加载前生成a标签，用于显示成员信息
	exports.director = function(){
		director();
	}
	exports.varDirector = function(){
		varDirector();
	}
});