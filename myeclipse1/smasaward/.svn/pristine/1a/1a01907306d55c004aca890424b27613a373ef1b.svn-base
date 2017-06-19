/**
 * 弹层初始化
 */
$(function(){
	//该层对象
	thisPopLayer = top.PopLayer.instances[top.PopLayer.id];
	
	//取消关闭
	$("#cancel").live("click", function(){
		thisPopLayer.destroy();
	});
	
	//取消关闭
	$(".cancel").live("click", function(){
		thisPopLayer.destroy();
	});
	
	//查看确定关闭
	$("#okclosebutton").click(function(){
		thisPopLayer.destroy();
	});
	
	//确定回调
	$("#confirm").click(function(){
		thisPopLayer.callBack(thisPopLayer.outData);
		thisPopLayer.destroy();
	});
});