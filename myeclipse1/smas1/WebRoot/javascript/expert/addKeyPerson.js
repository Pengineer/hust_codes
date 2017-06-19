/*
 * 同步重点人页面js
 * @author xujw
 * 2015-06-25
*/
$(function(){
	//如果表头选中，则将所有checkbox标为选中状态
	$("#check").live("click", function() {
		var e = this.checked;
		var sel_box = $(".personToDeal");
		// 设置复选框状态
		for ( var i = 0; i < sel_box.length; i += 1) {
			sel_box[i].checked = e;
		}
	});
	
	//选中的专家对应的hidden input增加selectPerson class
	$(".personToDeal").live("click",function(){
		var e = this.checked;
		if (e === true){
			var selectInput = $(this).parent().parent().find("input[type=hidden]");
			
			$(selectInput).addClass("selectPerson");
		}else{
			$(selectInput).removeClass("selectPerson");
		}
		
	});
	
	$("#submit1").live("click", function () {
		var sel_box_length = $(".personToDeal:checked").length;
		if(sel_box_length === 0){
			alert("您未选中任何专家！");
			return false;
		}
		sortNameNum();
		console.log($(".selectPerson").val());
		$("#addKeyPersonForm").submit();
	});
	
});

/*
 * 将专家重新编号,方便向后台传递
*/
var sortNameNum = function(projectType){
	$('.selectPerson').each(function(key1,value){
		value.name = value.name.replace(/\[.*\]/, "[" + key1 + "]");
	});
};