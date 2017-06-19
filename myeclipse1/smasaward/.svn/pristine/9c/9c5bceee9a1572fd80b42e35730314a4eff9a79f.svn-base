$(document).ready(function() {
	
	/*
	 * 取得退评的原因和退评当年评审项目的值传递到后台
	*/
	$("#confirm").unbind("click").live('click', function(){
		var notReviewerReason = $("#notReviewerReason").val().trim();
		var isNotReviewAll = $("input[name='isNotReviewAll']:checked").val();
		if("" != notReviewerReason){
			$.ajax({
				url: "expert/disableReview.action",
				type: "post",
				data: "notReviewerReason=" + $("#notReviewerReason").val() + "&isNotReviewAll=" + isNotReviewAll,
				dataType: "json",
				success: function(result){
					thisPopLayer.callBack(result);
					thisPopLayer.destroy();
				}
			});
		}else{
			alert("退评原因不能为空");
		}
		
	})
	
});
