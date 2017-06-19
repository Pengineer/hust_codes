/*
 * @徐江伟 2014-04-09 创建文件  实现对弹出层的操作控制
*/
$(document).ready(function() {
	/*
	 *默认“清空匹配变更记录器”永远选中，而且不可修改；
	 *只要选中“清空匹配对”，则“自动匹配对”一定会选中且不可修改，而“手动匹配对”可选；
	 *当勾选了“手动匹配对”，则“清空匹配对”和“自动匹配对”都勾选中。
	*/
	$("#deleteMatches").live("click",function(){
		if($("#deleteMatches").is(":checked") == true){
			$("#deleteAutoMatches").attr("checked",true);	
			//$("#deleteAutoMatches").attr("disabled","disabled");	
			//$("#deleteManualMatches").attr("disabled","disabled");		
			//$("#deleteManualMatches").attr("checked",true);
		}else{
			$("#deleteManualMatches").attr("checked",false);
			$("#deleteAutoMatches").attr("checked",false);	
		}
	});
	$("#deleteManualMatches").live("click",function(){
		if($("#deleteManualMatches").is(":checked") == true){
			$("#deleteMatches").attr("checked",true);
			$("#deleteAutoMatches").attr("checked",true);
		}
	});
	
	/*
	 * 确认发送清除匹配操作
	*/
	$("#confirm").unbind("click").live('click', function(){
		if(confirm("清空现有匹配结果后不可恢复，是否确认？")){
			$.ajax({
				url: "project/general/deleteMatches.action",
				type: "post",
				//data传递给后台的参数，对checkbox进行判断，选中则传递1，否则传递0
				data: "deleteManualMatches=" + (($("#deleteManualMatches").is(":checked")) == true?1:0) + "&deleteAutoMatches=" + (($("#deleteAutoMatches").is(":checked")) == true?1:0),
				dataType: "json",
				complete: function(data, textStatus){
					thisPopLayer.callBack(data);
					thisPopLayer.destroy();
				}
			});
		}
	})
});
