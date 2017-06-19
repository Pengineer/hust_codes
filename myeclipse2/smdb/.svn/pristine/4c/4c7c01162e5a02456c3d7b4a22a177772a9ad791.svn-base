$(function() {
	//全部移到右边
	$("#addall").click(function() {
		var options = $("#leftselect option");
		$("#rightselect").append(options);
	});
	//全部移动左边
	$("#removeall").click(function() {
		var options = $("#rightselect option");
		$("#leftselect").append(options);
	});
	//移到右边
	$("#add").click(function() {
		$("#leftselect option:selected").remove().appendTo("#rightselect");
	});
	//移到左边
	$("#remove").click(function() {
		$("#rightselect option:selected").remove().appendTo("#leftselect");
	});
	//双击选项
	$("#leftselect").dblclick(function() {
		$("option:selected",this).remove().appendTo("#rightselect");
	});
	//双击选项
	$("#rightselect").dblclick(function() {
		$("option:selected",this).remove().appendTo("#leftselect");
	});
});