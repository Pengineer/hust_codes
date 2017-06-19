$(document).ready(function() {
	
	$("#confirm").unbind("click").live('click', function(){
		var notReviewReason = $("#notReviewReason").val().trim();
		if("" != notReviewReason){
			$.ajax({
				url: "project/instp/disableReview.action",
				type: "post",
				data: "notReviewReason=" + $("#notReviewReason").val(),
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
