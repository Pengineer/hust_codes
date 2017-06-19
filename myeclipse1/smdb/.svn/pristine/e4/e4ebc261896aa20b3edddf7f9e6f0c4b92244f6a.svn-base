function Template_tool(){
	var templateObjects;

	// 在textarea之后插入容器，并将json数据灌入模板
	Template_tool.init = function() {
		templateObjects = [];
		$("textarea.view_template").each(function() {
			var $container = $("<div id='view_container_" + templateObjects.length +"' />");
			$(this).after($container);
			templateObjects.push(TrimPath.parseTemplate($(this).text()));
		});
	};

	// 将模板内容在页面显示
	Template_tool.populate = function(result) {
		for (var i = 0; i < templateObjects.length; ++i) {
			$("#view_container_" + i).html(templateObjects[i].process(result));
		}
		$("#tabcontent").show();
	};
};

Template_tool();
