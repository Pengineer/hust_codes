function agree(id){
	var url = "linkedin/checkFriend.action?entityId=" + id;
	$.ajax({
		  url: url,
		  type: "post",
		  data: "&type=" + 2,
		  dataType: 'json',
		  success: back
		});
};
function back() {
	window.location.href = "login/ucenterRight.action";
}
$(document).ready(function() {
	$("#agree").live("click", function() {//同意
		agree(this.name);
		return false;
	});
	
	$("#refuse").live("click", function() {//拒绝
		popRefuse(this.name);
		return false;
	});
	
});