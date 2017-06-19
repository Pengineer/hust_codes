$(document).ready(function(){
	//举报信息提交更新
	$("#submit").click(function(){
		$("#form_inform").submit();
	});
	
	$("#form_inform").submit(function(){
		$(this).ajaxSubmit({
			dataType : "json",
			data : {"entityId" : thisPopLayer.inData.entityId},
			success : function(result){
				thisPopLayer.destroy();
			}
		});
	});
	return false;
});