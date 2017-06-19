function prevStep() {
	var ob = document.getElementById("next");
	ob.style.display = "none";
	ob = document.getElementById("prev");
	ob.style.display = "block";
}

function nextStep() {
	var flag = $("#form_expert").valid();
	if(flag == false){return false}
	else{
		var ob = document.getElementById("prev");
		ob.style.display = "none";
		ob = document.getElementById("next");
		ob.style.display = "block";
	}
}


var toggleKey = function(obj, nameSpace) {// 设置是否关键人
	var isKey = 1 - $(obj).attr("isKey");
	$.ajax({
		url: "expert/toggleKey.action",
		type: "post",
		data: "expertid=" + $(obj).attr("alt") + "&isKey=" + isKey,
		dataType: "json",
		success: function(result) {
			if (result.errorInfo == null || result.errorInfo == "") {
				$(obj).attr("isKey", isKey);
				if (isKey) {
					$(obj).find("img").eq(0).attr("src", "image/error2.png");
					$(obj).find("img").eq(0).attr("title", "是重点人，点击可切换");
				} else {
					$(obj).find("img").eq(0).attr("src", "image/highlight.png");
					$(obj).find("img").eq(0).attr("title", "非重点人，点击可切换");
				}
			} else {
				alert(result.errorInfo);
			}
		}
	});
};

/**
 *导出专家（如果isReviewer是1，则打开弹出层，导出参评专家；如果isReviewer是0，则直接导出退评专家）
 */
function exportExpert(){
	var expertType = $("#expertType").val();
	var isReviewer = $("#isReviewer").val();
	
	if(isReviewer==1){
		popExportExpert({
			title : "导出专家",
			src : "expert/popExport.action",
			callBack : function(result){
				//window.location.href = "expert/toList.action?update=1&expertType=0&isReviewer=1";//跳转到内部参评专家
			}
		});
	}else{
		if (confirm('您确定要导出当前专家数据吗？')){
			//导出的href。之前的地址如下：window.location.href = nameSpace + "/exportExpert.action" + "?exportAll=0&countReviewer=1";
			window.location.href = nameSpace + "/exportExpert.action";
		};
	}
	
}