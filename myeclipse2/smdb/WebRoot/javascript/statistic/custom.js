define(function(require, exports, module) {
	var cookie = require('cookie');
	
	/**
	 * 动态生成排序列
	 */
	function showSortColumn(){
		var statIndexs = $("input[type='checkbox'][name='statistic_data']:checked"),
			fragments = [];
		statIndexs.each(function(index){
			if((index)%3 == 0){
				fragments.push($("<tr></tr>"));};
			//每行显示3个
			fragments[Math.floor(index/3)].append($("<td style='width:120px;padding-bottom:0px;margin-bottom:0px;'></td>")
				.append("<input type='radio' name='statistic_sortColumn' value='" + $(this).val() + "'/>")
					.append("&nbsp;").append($(this).next().clone())
			);
		});
		$("#sort_column").empty();
		//设置选择区域的高度
		$("#custom_sort").height(25*Math.ceil(statIndexs.length/3) + 30);
		$(fragments).each(function(){
			$("#sort_column").append(this);
			$("input[type='radio'][name='statistic_sortColumn']:first").attr("checked", true);
		});
		if($(".sort_type:checked").val() == 2){
			$("#custom_sort").fadeIn("fast");//展示动态效果
		};
		if($(".sort_type:checked").val() == 2 && statIndexs.length == 0){
			$("#custom_sort").fadeOut("fast", function(){//展示动态效果
				$(".sort_type:checked").trigger("click");
			});
		};
		
		/**
		 * 取消全选
		 */
		$(".selected").each(function(){
			if(!$(this).attr("checked")){
				$("input[type='checkbox'][name='select_all']").attr("checked", false); return;};
		});
	}
	
	exports.init = function() {
		
		$("#listSearch1").click(function(){
			$("#form_stat").slideToggle();
			$("#listSearch1").hide();
			$("#listSearch2").show();
		});
		$("#listSearch2").click(function(){
			$("#form_stat").slideToggle();
			$("#listSearch2").hide();
			$("#listSearch1").show();
		});
		$("input[name=statistic_showLineNum]").live("click",function(){
			if($("#self_define").attr("checked") == true){
				$("#self_define_input").removeAttr("disabled");
			}else{
				$("#self_define_input").attr("disabled","disabled");
			}
		});
		$("#submit").live("click", function(){
			$("#self_define").attr("value", $("#self_define_input").val());
		});
		$("input[type='checkbox'][name='statistic_data']").click(showSortColumn);
		
		$(".sort_type").click(function(){
			var statIndexs = $("input[type='checkbox'][name='statistic_data']:checked");
			if(statIndexs.length == 0 && $(this).val() == 2){alert("请选择统计数据！"); return;}
			if($(this).val() == 2){$("#custom_sort").fadeIn("fast");} 
			else{$("#custom_sort").fadeOut("fast");};
		});
		
		$("#reset").click(function(){
			$("#custom_sort").fadeOut("fast");
			$("#universityShow").hide();
			$("#chartIframe").fadeOut("slow");
			$("#listSearch2").fadeOut("slow");
		});
		
		/**
		 * 选中所有
		 */
		(function(){
			$("input[type='checkbox'][name='select_all']").click(function(evt){
				var flag=$(this).attr("checked");
				$(".selected").each(function(){$(this).attr("checked",flag);});
				showSortColumn();
				evt.stopPropagation();
			}).parent("span").mouseover(function(){
				$(this).css({"color": "#FF6600", "cursor": "pointer"});
			}).mouseout(function(){
				$(this).css({"color": "#6c6c96"});
			}).click(function(){
				$(this).children("input").attr("checked",!$(this).children("input").attr("checked"));
				var flag = $(this).children("input").attr("checked");
				$(".selected").each(function(){$(this).attr("checked", flag);});
				showSortColumn();
			});
		})();
		
		/**
		 * 校验统计指标
		 */
		$("#submit").click(function(){
			$.cookie("chart_type",$("input[type='radio'][name='chartType']:checked").val());
			var statData = $("input[type='radio'][name='statistic_index']:checked");
			if(statData.length == 0){alert("统计指标不能为空！"); return false;}
			var statIndexs = $("input[type='checkbox'][name='statistic_data']:checked");
			if(statIndexs.length == 0){alert("统计数据不能为空！"); return false;}
			
			$("#chartIframe").fadeIn();
			$("#form_stat").fadeOut("slow");
			$("#listSearch2").hide();
			$("#listSearch1").show();
		});
		
		/**
		 * 页面初始化
		 */
		showSortColumn();
		if($.cookie("chart_type")){
			$("input[type='radio'][name='chartType'][value=" + $.cookie("chart_type") + "]").attr("checked", true);
		}
		
		$(".radio").click(function(){
			$(this).parent().children().eq(0).attr("checked", true);
		});
	};
});
