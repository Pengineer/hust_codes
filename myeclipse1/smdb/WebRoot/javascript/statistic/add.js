define(function(require, exports, module) {
	var datepick = require("datepick-init");
	require('validate');
	
	/**
	 * 解析MDX字符串
	 * @param {Object} MDX
	 * @return Measure Array
	 */
	function parseMDX(mdx){
		if(typeof mdx != "string"){return false;}
		var measures=[],meaStr;
			if(!mdx.match(/(select)[\s\S]*(columns)/gi)){
				alert("请填入正确的mdx语句！"); return false;
			};
			meaStr = mdx.match(/(select)[\s\S]*(columns)/gi)[0].match(/\{[\s\S]*\}/g)[0].replace(/[\{\}\[\]]/g,"").split(",");
		for(var i in meaStr){
			var temp = meaStr[i].split(".");
			measures.push(temp[temp.length-1]);
		};		
		return measures;
	};
	
	exports.init = function() {
		datepick.init();
		//校验MDX查询语句合法性
		$(".config_choose").click(function(){
			if($(this).val() == 1){
				var mdx;
				if((mdx = $.trim($("textarea[name='mdxQuery.mdx']").val())) == ""){
					alert("mdx语句不能为空！"); $(".config_choose[value=0]").attr("checked", true); return false;};
				if(!parseMDX(mdx)){return false;}
				var measures = parseMDX(mdx);
				$("#chart_column").empty().append("<input type='hidden' name='measureNum' value=" + measures.length + "></input>");
				//将mdx语句中选择的栏位动态的生成在页面下方
				$(measures).each(function(index){
					$("#chart_column").append($("<div style='float:left;'></div>")
						.append("<input type='radio' name='chart_column' value=" + index + "></input>")
							.append("<span style='margin-right:10px;'>" + this + "<span>"));
					if(index == 0){
						$("input[type='radio'][name='chart_column']").attr("checked", true);
					};
				});
				$("#config").fadeIn("fast");
			} else{
				$("#config").fadeOut("fast");
			};
		});
		
		//mdx语言的编辑区域离开事件，触发动态生成排序选择
		$("textarea[name='mdxQuery.mdx']").blur(function(){
			if($.trim($(this).val()) == "" && $(".config_choose:checked").val() == 1){
				$(".config_choose[value=1]").trigger("click");
				$(".config_choose[value=0]").trigger("click");
			};
		});
		
		//自定义排序的初始和显示
		if($(".config_choose:checked").val() == 1){
			var mdx = $.trim($("textarea[name='mdxQuery.mdx']").val());
			measures = parseMDX(mdx);//解析mdx
			$("#chart_column").append("<input type='hidden' name='measureNum' value=" + measures.length + "></input>");
			//遍历所有的选择栏位
			$(measures).each(function(index){
				$("#chart_column").append($("<div style='float:left;'></div>")
					.append("<input type='radio' name='chart_column' value=" + index + "></input>")//添加一个单选按钮
						.append("<span style='margin-right:10px;'>" + this + "<span>"));
			});
			$("input[type='radio'][name='chart_column'][value=" + $("#column").val() + "]").attr("checked", true);
			$("#config").show();
		};
	};
});
