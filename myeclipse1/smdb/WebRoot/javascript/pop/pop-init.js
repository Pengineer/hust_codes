define(function(require, exports, module) {
	
	(function(){// 匿名函数，直接执行
		//该层对象
		thisPopLayer = top.PopLayer.instances[top.PopLayer.id];
		
		//初始化数据(区分单选和多选)
		if (thisPopLayer) {
			if($("label[name='checkboxPop']").size() > 0){thisPopLayer.outData = thisPopLayer.inData;}
			else{thisPopLayer.outData = {data : thisPopLayer.inData};}
		}
		
		//查看确定关闭
		$("#okclosebutton").live('click', function(){
			thisPopLayer.destroy();
		});
		
		//取消关闭
		$("#cancel").live("click", function(){
			thisPopLayer.destroy();
		});
		
		//确定回调
		$("#confirm").live("click", function(){
			thisPopLayer.callBack(thisPopLayer.outData);
			thisPopLayer.destroy();
		});
		
		// 点击取消radio的选中
		$("#cancelSelectedRadio").live('click', function(){
			cancelSelected(0);
		});
		
		// 点击取消checkbox的选中
		$("#cancelSelectedCheckbox").live('click', function(){
			cancelSelected(1);
		});
		
		//单选列表默认回传值、可重写
		$("input[type='radio']", $("#list_container")).live("click", function(){
			writeIdAndName(this);
		});
		
		// 绑定复选框选中事件
		$(".checkbox_select", $("#list_container")).live("click", function() {
			writeIdsAndNames(this);
		});
	})();
});
